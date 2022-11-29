/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate.generate;

import camel.constraint.Constraint;
import camel.constraint.IfThenConstraint;
import camel.constraint.LogicalConstraint;
import camel.constraint.MetricConstraint;
import camel.core.Attribute;
import camel.core.Feature;
import camel.core.NamedElement;
import camel.data.Data;
import camel.deployment.Component;
import camel.metric.*;
import camel.metric.impl.MetricVariableImpl;
import camel.requirement.OptimisationRequirement;
import camel.requirement.ServiceLevelObjective;
import camel.scalability.BinaryEventPattern;
import camel.scalability.NonFunctionalEvent;
import camel.scalability.UnaryEventPattern;
import camel.type.StringValue;
import eu.melodic.event.brokercep.cep.MathUtil;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.translate.model.tools.metadata.CamelMetadataTool;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;
import eu.melodic.event.translate.properties.RuleTemplateProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.spring5.dialect.SpringStandardDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.StringTemplateResolver;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RuleGenerator {
    private final static String TRANSLATION_CONFIG = "translation_config";
    private final static String EPL_VALUE = "epl_value";

    @Autowired
    private CamelToEplTranslatorProperties properties;
    @Autowired
    private RuleTemplateProperties ruleTemplatesRegistry;

    private SpringTemplateEngine templateEngine;

    public RuleGenerator(CamelToEplTranslatorProperties properties, RuleTemplateProperties ruleTemplatesRegistry) {
        this.properties = properties;
        this.ruleTemplatesRegistry = ruleTemplatesRegistry;
        initTemplateEngine();
    }

    // ================================================================================================================
    // Public API

    public void generateRules(TranslationContext _TC) {
        log.debug("RuleGenerator.ruleTemplates:\n{}", ruleTemplatesRegistry.getRuleTemplates());
        _generateRules(_TC);
        _TC.getTopicConnections();    // force topicConnections population
    }

    // ================================================================================================================
    // Rule generation methods

    protected String camelToRule(MapType mapType, ElemType elemType, String camelStr) {
        if (MapType.OPERATOR == mapType && ElemType.CONSTR == elemType) {
            if ("GREATER_THAN".equalsIgnoreCase(camelStr)) return ">";
            if ("GREATER_EQUAL_THAN".equalsIgnoreCase(camelStr)) return ">=";
            if ("LESS_THAN".equalsIgnoreCase(camelStr)) return "<";
            if ("LESS_EQUAL_THAN".equalsIgnoreCase(camelStr)) return "<=";
            if ("EQUAL".equalsIgnoreCase(camelStr)) return "=";
            if ("NOT_EQUAL".equalsIgnoreCase(camelStr)) return "<>";
            if ("AND".equalsIgnoreCase(camelStr)) return "AND";
            if ("OR".equalsIgnoreCase(camelStr)) return "OR";
            //XXX: EPL does not support XOR event patterns:
            // if ("XOR".equalsIgnoreCase(camelStr)) return "XOR";
            throw new IllegalArgumentException(String.format("Illegal argument in 'camelStr': MapType=%s, ElemType=%s, camel-str=%s", mapType, elemType, camelStr));
        }
        return camelStr.toUpperCase().trim();
    }

    protected void _generateRule(TranslationContext _TC, String type, String grouping, NamedElement elem, Context context) {
        String elemName = elem.getName();

        // Check for sub-feature attribute 'translation_config.epl_value'. If it exists then the rule EPL statement is
        // taken from its value (provided it is not null or blank, and it is a string value).
        // Further rule generation actions are skipped.
        log.trace("RuleGenerator._generateRule():      Checking if element is a feature: {} -- {}", elemName, elem.getClass());
        if (elem instanceof Feature) {
            log.trace("RuleGenerator._generateRule():      Checking element for overriding translation sub-feature: {}", elemName);
            String eplStmt = getEplValueFromSubfeatures((Feature) elem);
            log.trace("RuleGenerator._generateRule():      Element overriding translation sub-feature: {} -- sub-feature: {}", elemName, eplStmt);

            if (StringUtils.isNotBlank(eplStmt)) {
                log.info("RuleGenerator._generateRule():      Element '{}' has '{}' set. EPL statement: {}", elemName, EPL_VALUE, eplStmt = eplStmt.trim());

                // Store the generated rule in _TC
                _TC.addGroupingRulePair(grouping, elemName, eplStmt);
                log.info("RuleGenerator._generateRule():      + Added EPL statement at Grouping {}: {}", grouping, eplStmt);
                log.trace("RuleGenerator._generateRule():      Skipping further element rule processing: {}", elemName);
                return;
            }
        }

        // Generate rule EPL statement using the configured templates
        log.info("RuleGenerator._generateRule():      Generating rules for Graph node: {} {} at Grouping: {}", type, elemName, grouping != null ? grouping : "-");
        String[] groupingLabels = {grouping, "__ANY__"};
        for (String label : groupingLabels) {
            log.debug("RuleGenerator._generateRule():      Getting rule templates for: type={}, grouping={}", type, label);
            for (String ruleTpl : ruleTemplatesRegistry.getTemplatesFor(type, label)) {
                log.debug("RuleGenerator._generateRule():      Rule template for: type={}, grouping={} => {}", type, label, ruleTpl);
                if (ruleTpl != null) {
                    // Use template engine to process the selected rule template
                    context.setVariable("outputStream", elemName);
                    String ruleStr = templateEngine.process(ruleTpl.trim(), context);

                    // Store the generated rule in _TC
                    _TC.addGroupingRulePair(grouping, elemName, ruleStr);
                    log.info("RuleGenerator._generateRule():      + Added rule at Grouping {}: {}", grouping, ruleStr);
                } else {
                    log.warn("RuleGenerator._generateRule():      - No rule template found for '{}' at Grouping '{}': node={}", type, grouping, elemName);
                }
            }
        }
    }

    protected String _generateWindowClause(Window win) {
        // No window specified
        if (win==null)
            return ".std:lastevent()";
            //return "";

        // Sub-feature attribute 'translation_config.epl_value' value provides EPL statement
        // and overrides Window processing
        String eplStmt = getEplValueFromSubfeatures(win);
        if (StringUtils.isNotBlank(eplStmt)) {
            log.info("RuleGenerator._generateWindowClause(): Window '{}' has 'epl_value' set. EPL value: {}", win.getName(), eplStmt = eplStmt.trim());
            return eplStmt;
        }

        // Process Window settings for generating EPL statement
        StringBuilder sb = new StringBuilder();

        // Process 'groupwin' window group processings
        win.getProcessings().stream()
                .filter(p->p.getProcessingType()==WindowProcessingType.GROUP)
                .filter(this::groupingMustBeBeforeSizeOrTimeView)
                .forEach(p->{
                    _processGroupWindowProcessing(sb, p);
                });

        // Process 'time/size' window specifications
        _processSizeOrTimeView(sb, win);

        // Process 'non-groupwin' window group processings
        win.getProcessings().stream()
                .filter(p->p.getProcessingType()==WindowProcessingType.GROUP)
                .filter(this::groupingMustBeAfterSizeOrTimeView)
                .forEach(p->{
                    _processGroupWindowProcessing(sb, p);
                });

        // Process window sort and rank processings
        win.getProcessings().stream()
                .filter(p->p.getProcessingType()==WindowProcessingType.SORT || p.getProcessingType()==WindowProcessingType.RANK)
                .filter(this::groupingMustBeAfterSizeOrTimeView)
                .forEach(p->{
                    if (p.getProcessingType()==WindowProcessingType.SORT)
                        _processSortWindowProcessing(sb, p);
                    else if (p.getProcessingType()==WindowProcessingType.RANK)
                        _processRankWindowProcessing(sb, p);
                });

        String eplValue = sb.toString();
        log.info("RuleGenerator._generateWindowClause(): Window '{}' generated EPL value: {}", win.getName(), eplValue);
        return eplValue;
    }

    private boolean groupingMustBeBeforeSizeOrTimeView(WindowProcessing windowProcessing) {
        String eplView = getEplValueFromSubfeatures(windowProcessing);
        log.debug("RuleGenerator.groupingMustBeBeforeSizeOrTimeView: processing={}, epl-view={}", windowProcessing.getName(), eplView);
        boolean result = StringUtils.isBlank(eplView);
        log.debug("RuleGenerator.groupingMustBeBeforeSizeOrTimeView: processing={}, result={}", windowProcessing.getName(), result);
        return result;
    }

    private boolean groupingMustBeAfterSizeOrTimeView(WindowProcessing windowProcessing) {
        boolean result = !groupingMustBeBeforeSizeOrTimeView(windowProcessing);
        log.debug("RuleGenerator.groupingMustBeAfterSizeOrTimeView(): processing={}, result={}", windowProcessing.getName(), result);
        return result;
    }

    private static String getEplValueFromSubfeatures(Feature feature) {
        String result = feature.getSubFeatures().stream()
                .peek(p->log.trace("RuleGenerator.getEplViewFromSubfeatures(): ..... feature--BEFORE-FILTER: {}", p.getName()))
                .filter(f -> TRANSLATION_CONFIG.equals(f.getName()))
                .peek(p->log.trace("RuleGenerator.getEplViewFromSubfeatures(): ..... feature--AFTER-FILTER: {}", p.getName()))
                .map(Feature::getAttributes)
                .peek(a->log.trace("RuleGenerator.getEplViewFromSubfeatures(): .....  attribute--BEFORE-FLATMAP: {}", a))
                .flatMap(Collection::stream)
                .peek(a->log.trace("RuleGenerator.getEplViewFromSubfeatures(): .....  attribute--AFTER-FLATMAP: {}", a))
                .filter(a -> EPL_VALUE.equals(a.getName()))
                .peek(a->log.trace("RuleGenerator.getEplViewFromSubfeatures(): .....  attribute--AFTER-FILTER: {}", a))
                .map(Attribute::getValue)
                .peek(o->log.trace("RuleGenerator.getEplViewFromSubfeatures(): .....  value--BEFORE-FILTERS: {}", o))
                .filter(Objects::nonNull)
                .peek(o->log.trace("RuleGenerator.getEplViewFromSubfeatures(): .....  value--MID-FILTERS: {}", o))
                .filter(o -> o instanceof StringValue)
                .peek(o->log.trace("RuleGenerator.getEplViewFromSubfeatures(): .....  value--AFTER-FILTERS: {}", o))
                .map(o -> (StringValue) o)
                .peek(o->log.trace("RuleGenerator.getEplViewFromSubfeatures(): .....  string-value: {}", o))
                .map(StringValue::getValue)
                .peek(o->log.trace("RuleGenerator.getEplViewFromSubfeatures(): .....  value: {}", o))
                .findFirst().orElse(null);
        log.debug("RuleGenerator.getEplViewFromSubfeatures(): Processing={}, epl-view={}", feature.getName(), result);
        return result;
    }

    private void _processSizeOrTimeView(StringBuilder sb, Window win) {
        // WindowType: FIXED (Batch) or SLIDING
        boolean isBatchWin = (WindowType.FIXED==win.getWindowType());
        boolean isSlidingWin = ! isBatchWin;

        // WindowSizeType: MEASUREMENTS_ONLY, TIME_ONLY, FIRST_MATCH, BOTH_MATCH
        WindowSizeType winSizeType = win.getSizeType();
        boolean isFirstMatch = winSizeType==WindowSizeType.FIRST_MATCH;
        boolean isBothMatch = winSizeType==WindowSizeType.BOTH_MATCH;
        boolean isTimeOnly = winSizeType==WindowSizeType.TIME_ONLY;
        boolean isEventsOnly = winSizeType==WindowSizeType.MEASUREMENTS_ONLY;

        // Window size(s)
        long winTimeSize = win.getTimeSize();
        long winMeasurementSize = win.getMeasurementSize();

        // Checks
        String winTimeUnit = null;
        if (isFirstMatch || isBothMatch || isTimeOnly) {
            winTimeUnit = win.getTimeUnit() != null ? win.getTimeUnit().getName() : null;
            if (StringUtils.isBlank(winTimeUnit) || winTimeSize <= 0) {
                log.error("RuleGenerator._processSizeOrTimeView(): ERROR: Invalid or missing window-time-size or window-time-unit: window={}, window-time-size={}, window-time-unit={}", win.getName(), winTimeSize, winTimeUnit);
                throw new IllegalArgumentException(String.format("ERROR: Invalid or missing window-time-size or window-time-unit: window=%s, window-time-size=%d, window-time-unit=%s", win.getName(), winTimeSize, winTimeUnit));
            }
            winTimeUnit = camelToRule(MapType.UNIT, ElemType.TIME, winTimeUnit);
        }

        if (isFirstMatch || isBothMatch || isEventsOnly) {
            if (winMeasurementSize <= 0) {
                log.error("RuleGenerator._processSizeOrTimeView(): ERROR: Invalid window-measurement-size: window={}, window-measurement-size={}", win.getName(), winMeasurementSize);
                throw new IllegalArgumentException(String.format("ERROR: Invalid window-measurement-size: window=%s, window-measurement-size=%s", win.getName(), winMeasurementSize));
            }
        }

        // Generate the window length or time view
        String s;
        if (isFirstMatch) {
            if (isSlidingWin) {    // Sliding window
                long millis = _winTimeToMillis(winTimeSize, winTimeUnit);
                s = String.format(".win:expr(oldest_timestamp > newest_timestamp - %d and current_count <= %d)", millis, winMeasurementSize);
//                s = String.format(".win:expr(oldest_event.includes(newest_event, %d %s) and current_count <= %d)%s", winTimeSize, winTimeUnit, winMeasurementSize, winViews);
            } else {    // Batch window
                s = String.format(".win:time_length_batch(%d %s, %d)", winTimeSize, winTimeUnit, winMeasurementSize);
            }
        } else if (isBothMatch) {
            long millis = _winTimeToMillis(winTimeSize, winTimeUnit);
            s = String.format(".win:expr%s(oldest_timestamp > newest_timestamp - %d or current_count <= %d)", isBatchWin?"_batch":"", millis, winMeasurementSize);
//            s = String.format(".win:expr%s(oldest_event.includes(newest_event, %d %s) or current_count <= %d)", isBatchWin?"_batch":"", winTimeSize, winTimeUnit, winMeasurementSize);
        } else if (isTimeOnly) {
            s = String.format(".win:time%s(%d %s)", isBatchWin?"_batch":"", winTimeSize, winTimeUnit);
        } else if (isEventsOnly) {
            s = String.format(".win:length%s(%d)", isBatchWin?"_batch":"", winMeasurementSize);
        } else {
            log.error("RuleGenerator._processSizeOrTimeView(): ERROR: Invalid or Unsupported window-size-type: window={}, window-size-type={}", win.getName(), winSizeType);
            throw new IllegalArgumentException(String.format("ERROR: Invalid or Unsupported window-size-type: window=%s, window-size-type=%s", win.getName(), winSizeType));
        }

        sb.append(s);
    }

    /*// Old implementation....
    protected String _generateWindowClause(Window win) {
        if (win == null)
            return ".std:lastevent()";

        // WindowType: FIXED or SLIDING
        String winType;
        if (WindowType.FIXED==win.getWindowType()) winType = "_batch";
        else winType = "";

        // WindowSizeType: MEASUREMENTS_ONLY, TIME_ONLY, FIRST_MATCH, BOTH_MATCH
        WindowSizeType winSizeType = win.getSizeType();
        boolean isFirstMatch = winSizeType==WindowSizeType.FIRST_MATCH;
        boolean isBothMatch = winSizeType==WindowSizeType.BOTH_MATCH;
        boolean isTimeOnly = winSizeType==WindowSizeType.TIME_ONLY;
        boolean isEventsOnly = winSizeType==WindowSizeType.MEASUREMENTS_ONLY;

        long winTimeSize = win.getTimeSize();
        String winTimeUnit = null;
        if (isFirstMatch || isBothMatch || isTimeOnly) {
            winTimeUnit = win.getTimeUnit() != null ? win.getTimeUnit().getName() : null;
            if (StringUtils.isBlank(winTimeUnit) || winTimeSize <= 0) {
                log.error("RuleGenerator._generateWindowClause(): ERROR: Invalid or missing window-time-size or window-time-unit: window={}, window-time-size={}, window-time-unit={}", win.getName(), winTimeSize, winTimeUnit);
                throw new IllegalArgumentException(String.format("ERROR: Invalid or missing window-time-size or window-time-unit: window=%s, window-time-size=%d, window-time-unit=%s", win.getName(), winTimeSize, winTimeUnit));
            }
            winTimeUnit = camelToRule(MapType.UNIT, ElemType.TIME, winTimeUnit);
        }

        long winMeasurementSize = win.getMeasurementSize();
        if (isFirstMatch || isBothMatch || isEventsOnly) {
            if (winMeasurementSize <= 0) {
                log.error("RuleGenerator._generateWindowClause(): ERROR: Invalid window-measurement-size: window={}, window-measurement-size={}", win.getName(), winMeasurementSize);
                throw new IllegalArgumentException(String.format("ERROR: Invalid window-measurement-size: window=%s, window-measurement-size=%s", win.getName(), winMeasurementSize));
            }
        }

        // Process Window processing's
        final StringBuilder sb = new StringBuilder();
        if (win.getProcessings()!=null) {
            log.debug("RuleGenerator._generateWindowClause(): Processing Window processings for window: {}...", win.getName());
            log.debug("RuleGenerator._generateWindowClause(): Num of Window processings: {}", win.getProcessings().size());

            win.getProcessings().forEach(p->{
                WindowProcessingType pType = p.getProcessingType();
                log.debug("RuleGenerator._generateWindowClause(): Window processing: window={}, processing={}, type={}", win.getName(), p.getName(), pType);
                if (pType==WindowProcessingType.GROUP) {
                    _processGroupWindowProcessing(sb, p);
                } else
                if (pType==WindowProcessingType.SORT) {
                    _processSortWindowProcessing(sb, p);
                } else
                if (pType==WindowProcessingType.RANK) {
                    _processRankWindowProcessing(sb, p);
                } else {
                    log.error("RuleGenerator._generateWindowClause(): Unsupported window processing type: window={}, processing={}, type={}", win.getName(), p.getName(), pType);
                    throw new RuntimeException("Unsupported window processing type: window="+win.getName()+", processing="+p.getName()+", type="+pType);
                }
            });
            log.debug("RuleGenerator._generateWindowClause(): Processing window processings for window: {}...done", win.getName());
            log.trace("RuleGenerator._generateWindowClause(): Window processing result: {}", sb);
        } else {
            log.debug("RuleGenerator._generateWindowClause(): No Window processings in window: {}...", win.getName());
        }
        String winViews = sb.toString();

        // Generate window EPL part
        if (isFirstMatch) {
            if (winType.isEmpty()) {    // Sliding window
                long millis = _winTimeToMillis(winTimeSize, winTimeUnit);
                return String.format(".win:expr(oldest_timestamp > newest_timestamp - %d and current_count <= %d)%s", millis, winMeasurementSize, winViews);
//                return String.format(".win:expr(oldest_event.includes(newest_event, %d %s) and current_count <= %d)%s", winTimeSize, winTimeUnit, winMeasurementSize, winViews);
            } else {    // Batch window
                return String.format(".win:time_length_batch(%d %s, %d)%s", winTimeSize, winTimeUnit, winMeasurementSize, winViews);
            }
        } else if (isBothMatch) {
            long millis = _winTimeToMillis(winTimeSize, winTimeUnit);
            return String.format(".win:expr%s(oldest_timestamp > newest_timestamp - %d or current_count <= %d)%s", winType, millis, winMeasurementSize, winViews);
//            return String.format(".win:expr%s(oldest_event.includes(newest_event, %d %s) or current_count <= %d)%s", winType, winTimeSize, winTimeUnit, winMeasurementSize, winViews);
        } else if (isTimeOnly) {
            return String.format(".win:time%s(%d %s)%s", winType, winTimeSize, winTimeUnit, winViews);
        } else if (isEventsOnly) {
            return String.format(".win:length%s(%d)%s", winType, winMeasurementSize, winViews);
        } else {
            log.error("RuleGenerator._generateWindowClause(): ERROR: Invalid or Unsupported window-size-type: window={}, window-size-type={}", win.getName(), winSizeType);
            throw new IllegalArgumentException(String.format("ERROR: Invalid or Unsupported window-size-type: window=%s, window-size-type=%s", win.getName(), winSizeType));
        }
    }*/

    private long _winTimeToMillis(long time, String unit) {
        switch (unit.toLowerCase()) {
            case "msec":
            case "millisecond":
            case "milliseconds":
                return time;
            case "sec":
            case "second":
            case "seconds":
                return TimeUnit.MILLISECONDS.convert(time, TimeUnit.SECONDS);
            case "min":
            case "minute":
            case "minutes":
                return TimeUnit.MILLISECONDS.convert(time, TimeUnit.MINUTES);
            case "hour":
            case "hours":
                return TimeUnit.MILLISECONDS.convert(time, TimeUnit.HOURS);
            case "day":
            case "days":
                return TimeUnit.MILLISECONDS.convert(time, TimeUnit.DAYS);
            case "week":
            case "weeks":
                return TimeUnit.MILLISECONDS.convert(7 * time, TimeUnit.DAYS);
            case "month":
            case "months":
                log.warn("RuleGenerator._winTimeToMillis: NOTE: 'Months' time unit equals to 30 days!");
                return TimeUnit.MILLISECONDS.convert(30 * time, TimeUnit.DAYS);
            case "year":
            case "years":
                log.warn("RuleGenerator._winTimeToMillis: NOTE: 'Years' time unit equals to 365 days!");
                return TimeUnit.MILLISECONDS.convert(365 * time, TimeUnit.DAYS);
            default:
                throw new RuntimeException("Unsupported time unit: \"" + unit + "\"");
        }
    }

    private void _processGroupWindowProcessing(StringBuilder sb, WindowProcessing p) {
        if (p.getGroupingCriteria()!=null && p.getGroupingCriteria().size()>0) {
            // Get grouping view from annotations
            String view = getEplValueFromSubfeatures(p);
            if (StringUtils.isBlank(view)) view = ".std:groupwin";
            else if (! view.trim().startsWith(".")) view = "."+view.trim();
            else view = view.trim();

            sb.append(view).append("(");
            final boolean[] first = {true};
            p.getGroupingCriteria().forEach(c->{
                if (first[0]) first[0] = false; else sb.append(", ");
                log.debug("RuleGenerator._processGroupWindowProcessing(): Group processing criterion: group-processing={}, group-criterion={}, type={}, custom={}, metric={}",
                        p.getName(), c.getName(), c.getType(), c.getCustom(), c.getMetric());
                if (CriterionType.CUSTOM==c.getType()) {
                    if (StringUtils.isNotBlank(c.getCustom())) {
                        sb.append(c.getCustom().trim());
                    } else {
                        log.error("RuleGenerator._processGroupWindowProcessing(): CUSTOM group processing Criterion type must provide 'custom' property: {}", c.getName());
                        throw new IllegalArgumentException("CUSTOM group processing Criterion type must provide 'custom' property: " + c.getName());
                    }
                } else
                /*if (CriterionType.INSTANCE==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.HOST==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.ZONE==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.REGION==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.CLOUD==c.getType()) {
                    sb.append("propext('producer-source')");
                } else*/
                if (CriterionType.TIMESTAMP==c.getType()) {
                    log.error("RuleGenerator._generateWindowClause(): TIMESTAMP processing Criterion type MUST NOT be used for grouping: {}", c.getName());
                    throw new IllegalArgumentException("TIMESTAMP processing Criterion type MUST NOT be used for grouping: " + c.getName());
                } else
                {
                    log.error("RuleGenerator._processGroupWindowProcessing(): Unsupported Processing Criterion type: {}", c.getType());
                    throw new IllegalArgumentException("Unsupported Processing Criterion type: " + c.getType());
                }
            });
            sb.append(")");
        } else {
            log.warn("RuleGenerator._processGroupWindowProcessing: GROUP window processing with NO grouping criteria. Processing is ignored: {}", p.getName());
        }
    }

    private void _processSortWindowProcessing(StringBuilder sb, WindowProcessing p) {
        if (p.getRankingCriteria()!=null && p.getRankingCriteria().size()>0) {
            sb.append(".ext:sort(").append(Integer.MAX_VALUE);
            p.getRankingCriteria().forEach(c-> {
                sb.append(", ");
                log.debug("RuleGenerator._processSortWindowProcessing(): Sort processing criterion: sort-processing={}, sort-criterion={}, type={}, custom={}, metric={}",
                        p.getName(), c.getName(), c.getType(), c.getCustom(), c.getMetric());
                if (CriterionType.CUSTOM==c.getType()) {
                    if (StringUtils.isNotBlank(c.getCustom())) {
                        sb.append(c.getCustom().trim());
                    } else {
                        log.error("RuleGenerator._processSortWindowProcessing(): CUSTOM sort processing Criterion type must provide 'custom' property: {}", c.getName());
                        throw new IllegalArgumentException("CUSTOM sort processing Criterion type must provide 'custom' property: " + c.getName());
                    }
                } else
                /*if (CriterionType.INSTANCE==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.HOST==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.ZONE==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.REGION==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.CLOUD==c.getType()) {
                    sb.append("propext('producer-source')");
                } else*/
                if (CriterionType.TIMESTAMP==c.getType()) {
                    sb.append("timestamp");
                } else
                {
                    log.error("RuleGenerator._processSortWindowProcessing(): Unsupported Processing Criterion type: {}", c.getType());
                    throw new IllegalArgumentException("Unsupported Processing Criterion type: " + c.getType());
                }
                sb.append(c.isAscending() ? " ASC" : " DESC");
            });
            sb.append(")");
        } else {
            log.warn("RuleGenerator._processSortWindowProcessing: SORT window processing with NO ranking criteria. Processing is ignored: {}", p.getName());
        }
    }

    private void _processRankWindowProcessing(StringBuilder sb, WindowProcessing p) {
        if (p.getRankingCriteria()!=null && p.getRankingCriteria().size()>0) {
            sb.append(".ext:rank(");
            // Unique criteria
            p.getRankingCriteria().forEach(c-> {
                log.debug("RuleGenerator._processRankWindowProcessing(): Rank processing criterion: rank-processing={}, rank-criterion={}, type={}, custom={}, metric={}",
                        p.getName(), c.getName(), c.getType(), c.getCustom(), c.getMetric());
                if (CriterionType.CUSTOM==c.getType()) {
                    if (StringUtils.isNotBlank(c.getCustom())) {
                        sb.append(c.getCustom().trim());
                    } else {
                        log.error("RuleGenerator._processSortWindowProcessing(): CUSTOM rank processing Criterion type must provide 'custom' property: {}", c.getName());
                        throw new IllegalArgumentException("CUSTOM rank processing Criterion type must provide 'custom' property: " + c.getName());
                    }
                } else
                /*if (CriterionType.INSTANCE==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.HOST==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.ZONE==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.REGION==c.getType()) {
                    sb.append("propext('producer-source')");
                } else
                if (CriterionType.CLOUD==c.getType()) {
                    sb.append("propext('producer-source')");
                } else*/
                if (CriterionType.TIMESTAMP==c.getType()) {
                    sb.append("timestamp");
                } else
                {
                    log.error("RuleGenerator._processRankWindowProcessing(): Unsupported Processing Criterion type: {}", c.getType());
                    throw new IllegalArgumentException("Unsupported Processing Criterion type: " + c.getType());
                }
                sb.append(", ");
            });
            // Size
            sb.append(Integer.MAX_VALUE);
            // Sort criteria
            sb.append(")");
        } else {
            log.warn("RuleGenerator._processRankWindowProcessing: RANK window processing with NO ranking criteria. Processing is ignored: {}", p.getName());
        }
    }

    protected String _generateScheduleClause(Schedule sched) {
        if (sched == null) return "";

        int schedRepetitions = sched.getRepetitions();
        long schedInterval = sched.getInterval();
        if (schedRepetitions <= 0 && schedInterval <= 0) return "";

        if (schedRepetitions > 0 && schedInterval > 0) {
            log.error("RuleGenerator._generateScheduleClause(): ERROR: Schedule has both 'repetitions' and 'interval' properties non-zero: repetitions={}, interval={}", schedRepetitions, schedInterval);
            throw new IllegalArgumentException(String.format("ERROR: Schedule has both 'repetitions' and 'interval' properties non-zero: repetitions=%s, interval=%s", schedRepetitions, schedInterval));
        }

        long schedPeriod = -1;
        String schedUnit = "";
        if (schedInterval > 0) {
            schedPeriod = schedInterval;
            schedUnit = sched.getTimeUnit().getName();
            schedUnit = camelToRule(MapType.UNIT, ElemType.TIME, schedUnit);
        } else if (schedRepetitions > 0) {
            schedPeriod = schedRepetitions;
            schedUnit = "EVENTS";
        }

        String schedTpl = ruleTemplatesRegistry.getTemplatesFor("SCHEDULE", "__ANY__").stream().findFirst().orElse(null);
        log.trace("RuleGenerator._generateScheduleClause(): schedule-tpl: {}", schedTpl);
        if (schedTpl != null) {
            // Use template engine to process the selected schedule template
            Context context = new Context();
            context.setVariable("period", schedPeriod);
            context.setVariable("unit", schedUnit);
            String schedStr = templateEngine.process(schedTpl.trim(), context);
            log.debug("RuleGenerator._generateScheduleClause(): schedule-clause: {}", schedStr);
            return schedStr;
        } else {
            return String.format("\nOUTPUT LAST EVERY %d %s", schedPeriod, schedUnit);
        }
    }

    protected void _generateRules(TranslationContext _TC) {
        // traverse DAG and generate EPL rules
        log.info("RuleGenerator.generateRules(): Traversing DAG...");
        _TC.DAG.traverseDAG(node -> {
            String grouping = node.getGrouping() != null ? node.getGrouping().toString() : null;
            NamedElement elem = node.getElement();
            String elemName = elem != null ? elem.getName() : null;
            Class elemClass = elem != null ? elem.getClass() : null;
            log.warn("RuleGenerator.generateRules():  node: {}, grouping={}, elem-name={}, elem-class={}", node, grouping, elemName, elemClass);

            // Generate rules depending on the type of element
            boolean providesTopic = true;
            if (elem == null) {
                // ignore this element
                log.warn("RuleGenerator.generateRules():      IGNORE NODE. No element specified: node={}", node);
                providesTopic = false;
            } else
            // Generate rules for Events and Event Patterns (and Metric Constraints)
            if (elem instanceof camel.scalability.BinaryEventPattern) {
                log.warn("RuleGenerator.generateRules():      Found a Binary-Event-Pattern element: node={}, elem-name={}", node, elemName);
                BinaryEventPattern bep = (BinaryEventPattern) elem;

                // Get event pattern operator
                String camelOp = bep.getOperator().toString();
                String ruleOp = camelToRule(MapType.OPERATOR, ElemType.BEP, camelOp);

                // Get event pattern composing events (just the names)
                String leName = bep.getLeftEvent().getName();
                String reName = bep.getRightEvent().getName();
                log.warn("RuleGenerator.generateRules():      Binary-Event-Pattern: node={}, elem-name={}, operator={}->{}, left-event={}, right-event={}", node, elemName, camelOp, ruleOp, leName, reName);

//XXX: ASK: Do we need 'lowOccurrenceBound', 'upperOccurrenceBound' and 'timer' ??

                // Write rule for BEP
                Context context = new Context();
                context.setVariable("operator", ruleOp);
                context.setVariable("leftEvent", leName);
                context.setVariable("rightEvent", reName);
                _generateRule(_TC, "BEP-" + ruleOp, grouping, elem, context);
            } else if (elem instanceof camel.scalability.UnaryEventPattern) {
                log.warn("RuleGenerator.generateRules():      Found a Unary-Event-Pattern element: node={}, elem-name={}", node, elemName);
                UnaryEventPattern uep = (UnaryEventPattern) elem;

                // Get event pattern operator
                String camelOp = uep.getOperator().toString();
                String ruleOp = camelToRule(MapType.OPERATOR, ElemType.UEP, camelOp);

                // Get event pattern composing event (just the name)
                String eventName = uep.getEvent().getName();
                log.warn("RuleGenerator.generateRules():      Unary-Event-Pattern: node={}, elem-name={}, operator={}->{}, event={}", node, elemName, camelOp, ruleOp, eventName);

//XXX: ASK: Do we need 'occurrenceNum' and 'timer' ??

                // Write rule for UEP
                Context context = new Context();
                context.setVariable("operator", ruleOp);
                context.setVariable("event", eventName);
                _generateRule(_TC, "UEP-" + ruleOp, grouping, elem, context);
            } else if (elem instanceof camel.scalability.NonFunctionalEvent) {
                log.warn("RuleGenerator.generateRules():      Found a Non-Functional-Event element: node={}, elem-name={}", node, elemName);
                NonFunctionalEvent nfe = (NonFunctionalEvent) elem;

                // Get event is-violation
                boolean isViolation = nfe.isIsViolation();

                // Get event's metric constraint (just the name)
                MetricConstraint constr = nfe.getMetricConstraint();
                log.warn("RuleGenerator.generateRules():      Non-Functional-Event: node={}, elem-name={}, is-violation={}, metric-constraint={}", node, elemName, isViolation, constr.getName());

                // Write rule for NFE
                Context context = new Context();
                context.setVariable("metricConstraint", constr.getName());
                _generateRule(_TC, "NFE", grouping, elem, context);
            } else

            // Generate rules for Constraints
            if (elem instanceof camel.constraint.MetricConstraint) {
                log.warn("RuleGenerator.generateRules():      Found a Metric-Constraint element: node={}, elem-name={}", node, elemName);
                MetricConstraint constr = (MetricConstraint) elem;

                // Get metric constraint operator and threshold
                String camelOp = constr.getComparisonOperator().toString();
                String ruleOp = camelToRule(MapType.OPERATOR, ElemType.CONSTR, camelOp);
                double threshold = constr.getThreshold();

                // Get constraint's metric context (just the name)
                MetricContext mc = constr.getMetricContext();
                log.warn("RuleGenerator.generateRules():      Metric-Constraint: node={}, elem-name={}, operator={}->{}, threshold={}, metric-context={}", node, elemName, camelOp, ruleOp, threshold, mc.getName());

                // Require context topic in this level
                _TC.requireGroupingTopicPair(grouping, mc.getName());

                // Write rule for CONSTR-MET
                Context context = new Context();
                context.setVariable("metricContext", mc.getName());
                context.setVariable("operator", ruleOp);
                context.setVariable("threshold", threshold);
                _generateRule(_TC, "CONSTR-MET", grouping, elem, context);
            } else if (elem instanceof camel.constraint.IfThenConstraint) {
                log.warn("RuleGenerator.generateRules():      Found an If-Then-Constraint element: node={}, elem-name={}", node, elemName);
                IfThenConstraint constr = (IfThenConstraint) elem;

                // Get constraint If, Then, Else child constraints
                String ifConstr = constr.getIf().getName();
                String thenConstr = constr.getThen().getName();
                String elseConstr = constr.getElse()!=null ? constr.getElse().getName() : null;

                log.warn("RuleGenerator.generateRules():      If-Then-Constraint: node={}, elem-name={}, If={}, Then={}, Else={}",
                        node, elemName, ifConstr, thenConstr, elseConstr);

                // Require context topic in this level
                _TC.requireGroupingTopicPair(grouping, constr.getName());

                // Write rule for CONSTR-IF-THEN
                Context context = new Context();
                context.setVariable("ifConstraint", ifConstr);
                context.setVariable("thenConstraint", thenConstr);
                context.setVariable("elseConstraint", elseConstr);
                _generateRule(_TC, "CONSTR-IF-THEN", grouping, elem, context);
            } else if (elem instanceof camel.constraint.MetricVariableConstraint) {
                // Not used in EMS
                log.warn("RuleGenerator.generateRules():      Found an Metric-Variable-Constraint element and ignoring it: node={}, elem-name={}", node, elemName);
            } else if (elem instanceof camel.constraint.LogicalConstraint) {
                log.warn("RuleGenerator.generateRules():      Found a Logical-Constraint element: node={}, elem-name={}", node, elemName);
                LogicalConstraint constr = (LogicalConstraint) elem;

                // Get logical constraint operator and component constraints
                String camelOp = constr.getLogicalOperator().getName();
                String ruleOp = camelToRule(MapType.OPERATOR, ElemType.CONSTR, camelOp);
                List<String> componentConstraintsNamesList = constr.getConstraints().stream().map(con -> con.getName()).collect(Collectors.toList());

                log.warn("RuleGenerator.generateRules():      Logical-Constraint: node={}, elem-name={}, operator={}->{}, component-constraints={}", node, elemName, camelOp, ruleOp, componentConstraintsNamesList);

                // Require context topic in this level
                _TC.requireGroupingTopicPair(grouping, constr.getName());

                // Write rule for CONSTR-LOG
                Context context = new Context();
                context.setVariable("operator", ruleOp);
                context.setVariable("constraints", componentConstraintsNamesList);
                _generateRule(_TC, "CONSTR-LOG", grouping, elem, context);
            } else

            // Generate rules for Metrics Contexts
            if (elem instanceof camel.metric.CompositeMetricContext) {
                log.warn("RuleGenerator.generateRules():      Found a Composite-Metric-Context element: node={}, elem-name={}", node, elemName);
                CompositeMetricContext cmc = (CompositeMetricContext) elem;

                // Get composite metric context's metric parameters
                CompositeMetric metric = (CompositeMetric) cmc.getMetric();
                String formula = metric.getFormula();
                EList<Metric> components = metric.getComponentMetrics();
                List<String> componentNames = components.stream().map(item -> item.getName()).collect(Collectors.toList());

                // Get composite metric context's window and schedule parameters
                Window win = cmc.getWindow();
                String winClause = _generateWindowClause(win);
                Schedule sched = cmc.getSchedule();
                String schedClause = _generateScheduleClause(sched);

                // Get composite metric context's component or data parameters
                String[] compAndDataName = getComponentAndDataName(cmc);
                String compName = compAndDataName[0];
                String dataName = compAndDataName[1];

                // Get composite metric context's composing metric contexts (just the names)
                EList<MetricContext> composingCtxList = cmc.getComposingMetricContexts();
                List<String> composingMetricNamesList = composingCtxList.stream().map(item -> item.getMetric().getName()).collect(Collectors.toList());
                List<String> composingCtxNamesList = composingCtxList.stream().map(item -> item.getName()).collect(Collectors.toList());

                // Check that component metrics' names (from composite metric) and metric names from component contexts match
                if (checkIfListsAreEqual(componentNames, composingCtxNamesList)) {
                    log.error("RuleGenerator.generateRules():      Component metrics of composite metric '{}' do not match to component contexts of Composite-Metric-Context '{}': component-metrics={}, component-context-metrics={}", metric.getName(), cmc.getName(), componentNames, composingCtxNamesList);
                    throw new IllegalArgumentException(String.format("Component metrics of composite metric '%s' do not match to component contexts of Composite-Metric-Context: %s", metric.getName(), cmc.getName()));
                }

                log.warn("RuleGenerator.generateRules():      Composite-Metric-Context: node={}, elem-name={}, metric={}, formula={}, components={}, win={}, sched={}, component={}, data={}, composing-metrics={}, composing-metric-contexts={}",
                        node, elemName, metric.getName(), formula, componentNames, winClause, schedClause, compName, dataName, composingMetricNamesList, composingCtxNamesList);

                // Require topics in this level
                _TC.requireGroupingTopicPairs(grouping, composingCtxNamesList);

                // Select rule tag, depending on whether an Aggregator function is used in formula
                // (a) COMP-CTX: when no Aggregator function is used in formula, (b) COMP-CTX-AGG: when an Aggregator function is used in formula
                String ruleTag = "COMP-CTX";
                if (MathUtil.containsAggregator(formula)) ruleTag = "AGG-COMP-CTX";
                log.warn("RuleGenerator.generateRules():      CMC-tag={}", ruleTag);

                // Write rule for CMC or CMC-AGG
                Context context = new Context();
                context.setVariable("formula", formula);
                context.setVariable("metric", metric.getName());
                context.setVariable("components", composingMetricNamesList);
                context.setVariable("contexts", composingCtxNamesList);
                context.setVariable("windowClause", winClause);
                context.setVariable("scheduleClause", schedClause);
                _generateRule(_TC, ruleTag, grouping, elem, context);
            } else if (elem instanceof camel.metric.RawMetricContext) {
                log.warn("RuleGenerator.generateRules():      Found a Raw-Metric-Context element: node={}, elem-name={}", node, elemName);
                RawMetricContext rmc = (RawMetricContext) elem;

                // Get raw metric context's metric parameters
                RawMetric metric = (RawMetric) rmc.getMetric();
                Sensor sensor = rmc.getSensor();
                String sensorName = sensor != null ? sensor.getName() : null;

                // Get raw metric context's schedule parameters
                Schedule sched = rmc.getSchedule();
                String schedClause = _generateScheduleClause(sched);

                // Get raw metric context's component or data parameters
                String[] compAndDataName = getComponentAndDataName(rmc);
                String compName = compAndDataName[0];
                String dataName = compAndDataName[1];

                log.warn("RuleGenerator.generateRules():      Raw-Metric-Context: node={}, elem-name={}, metric={}, sensor={}, sched={}, component={}, data={}",
                        node, elemName, metric.getName(), sensorName, schedClause, compName, dataName);

                // Require topics in this level
                _TC.requireGroupingTopicPair(grouping, rmc.getName());

                // Write rule for RMC
                Context context = new Context();
                context.setVariable("metric", metric.getName());
                context.setVariable("sensor", sensorName);
                context.setVariable("scheduleClause", schedClause);
                _generateRule(_TC, "RAW-CTX", grouping, elem, context);
            } else

            // Generate rules for Metrics and Metric Variables
            if ((elem instanceof camel.metric.RawMetric || elem instanceof camel.metric.CompositeMetric) && _TC.DAG.isTopLevelNode(node)) {
                log.warn("RuleGenerator.generateRules():      Found a Top-Level Metric element: node={}, elem-name={}", node, elemName);
                providesTopic = true;
                Metric m = (Metric) elem;

                // Get metric's context
                Set<MetricContext> mc = _TC.M2MC.get(m);
                List<String> mcList = mc.stream().map(NamedElement::getName).collect(Collectors.toList());
                if (mc.size() != 1) {
                    log.error("RuleGenerator.generateRules():      Top-Level Metric has 0 or >1 contexts: metric={}, component-contexts={}", m.getName(), mcList);
                    throw new IllegalArgumentException(String.format("Top-Level Metric has 0 or >1 contexts: metric=%s, component-contexts=%s", m.getName(), mcList));
                }
                MetricContext cmc = mc.stream().findFirst().get();

                log.warn("RuleGenerator.generateRules():      Top-Level Metric: node={}, elem-name={}, context={}",
                        node, elemName, cmc.getName());

                // Require topics in this level
                _TC.requireGroupingTopicPair(grouping, cmc.getName());

                // Write rule for MET
                Context context = new Context();
                context.setVariable("context", cmc.getName());
                _generateRule(_TC, "TL-MET", grouping, elem, context);

            } else if (elem instanceof camel.metric.CompositeMetric) {
                log.warn("RuleGenerator.generateRules():      Found a Composite-Metric element: node={}, elem-name={}", node, elemName);
                providesTopic = false;
                // Nothing to do here
            } else if (elem instanceof camel.metric.RawMetric) {
                log.warn("RuleGenerator.generateRules():      Found a Raw-Metric element: node={}, elem-name={}", node, elemName);
                providesTopic = false;
                // Nothing to do here
            } else if (elem instanceof camel.metric.MetricVariable) {
                log.warn("RuleGenerator.generateRules():      Found a Metric-Variable element: node={}, elem-name={}", node, elemName);
                MetricVariable mvar = (MetricVariable) elem;
                boolean isCurrConfig = mvar.isCurrentConfiguration();
                boolean isOnNodeCand = mvar.isOnNodeCandidates();
                Component comp = mvar.getComponent();
                String compName = comp != null ? comp.getName() : null;
                String formula = mvar.getFormula();
                EList<Metric> _componentMetrics = mvar.getComponentMetrics();
                List<String> _componentMetricNames = _componentMetrics.stream().map(NamedElement::getName).collect(Collectors.toList());
                boolean _containsMetrics = _componentMetrics.size() > 0;

                log.warn("RuleGenerator.generateRules():      Metric-Variable: node={}, elem-name={}, is-current-config={}, is-on-node-candidates={}, component={}, formula={}, component-metrics={}, contains-metrics={}",
                        node, elemName, isCurrConfig, isOnNodeCand, compName, formula, _componentMetricNames, _containsMetrics);

                // Remove CP model variables from component metrics
                List<Metric> componentMetrics = new ArrayList<>();
                for (Metric m : _componentMetrics) {
                    if (m instanceof MetricVariable) {
                        if (CamelMetadataTool.isFromVariable((MetricVariableImpl) m)) {
                            log.warn("RuleGenerator.generateRules():        - CP model variable found and will be excluded from processing: {}", m.getName());
                            continue;
                        }
                    }
                    componentMetrics.add(m);
                }
                List<String> componentMetricNames = componentMetrics.stream().map(item -> item.getName()).collect(Collectors.toList());
                boolean containsMetrics = (componentMetrics.size() > 0);

                log.warn("RuleGenerator.generateRules():      Metric-Variable after removing CP model variables: node={}, elem-name={}, is-current-config={}, is-on-node-candidates={}, component={}, formula={}, component-metrics={}, contains-metrics={}",
                        node, elemName, isCurrConfig, isOnNodeCand, compName, formula, componentMetricNames, containsMetrics);

                // Select rule tag, depending on whether an Aggregator function is used in formula
                // (a) VAR: when no Aggregator function is used in formula, (b) VAR-AGG: when an Aggregator function is used in formula
                String ruleTag = "VAR";
                if (MathUtil.containsAggregator(formula)) ruleTag = "AGG-VAR";
                log.warn("RuleGenerator.generateRules():      VAR-tag={}", ruleTag);

                if (componentMetrics.size() > 0) {
                    // Write rule for VAR
                    List<MetricContext> contexts = componentMetrics.stream().map(item -> _TC.getMetricContextForMetric(item)).filter(Objects::nonNull).collect(Collectors.toList());
                    List<String> metricNames = contexts.stream().map(item -> item.getMetric().getName()).collect(Collectors.toList());
                    List<String> contextNames = contexts.stream().map(item -> item.getName()).collect(Collectors.toList());
                    log.warn("RuleGenerator.generateRules():      Metric-Variable: node={}, elem-name={}, component-metrics={}, component-metric-contexts={}",
                            node, elemName, metricNames, contextNames);

                    // Check that component metrics' names (from metric variable) and metric names from component contexts match
                    if (! checkIfListsAreEqual(componentMetricNames, metricNames)) {
                        log.error("RuleGenerator.generateRules():      Component metrics of metric variable '{}' do not match to component contexts' metrics: component-metrics={}, component-context-metrics={}", mvar.getName(), componentMetricNames, metricNames);
                        throw new IllegalArgumentException(String.format("Component metrics of metric variable '%s' do not match to component contexts' metrics", mvar.getName()));
                    }

                    if (contexts.size() > 0) {
                        // Require topics in this level
                        _TC.requireGroupingTopicPairs(grouping, contextNames);

                        // Write rule for VAR
                        Context context = new Context();
                        context.setVariable("formula", formula);
                        context.setVariable("variable", mvar.getName());
                        context.setVariable("components", metricNames);
                        context.setVariable("contexts", contextNames);
                        _generateRule(_TC, ruleTag, grouping, elem, context);
                    } else {
                        // All component metrics for this metric variable do not have related metric contexts (i.e. the metric variable is MVV)
                        // No rules will be generated
                        log.info("RuleGenerator.generateRules():      Metric-Variable has no metric contexts related to its component metrics. No rules will be generated: node={}, elem-name={}", node, elemName);
                    }
                } else {
                    // No component metrics for this metric variable (i.e. it is a MVV)
                    // No rules will be generated
                    log.info("RuleGenerator.generateRules():      Metric-Variable has no component metrics. No rules will be generated: node={}, elem-name={}", node, elemName);
                }
            } else

            // Generate rules for Templates and Sensors
            if (elem instanceof camel.metric.MetricTemplate) {
                log.warn("RuleGenerator.generateRules():      Found a Metric-Template element: node={}, elem-name={}", node, elemName);
            } else if (elem instanceof camel.metric.Sensor) {
                log.warn("RuleGenerator.generateRules():      Found a Sensor element: node={}, elem-name={}", node, elemName);
//XXX:TODO: Do we need to do something here?? (e.g. create schema??)
            } else

            // Generate rules for Optimisation Requirements and SLO's
            if (elem instanceof camel.requirement.OptimisationRequirement) {
                log.warn("RuleGenerator.generateRules():      Found an Optimisation-Requirement element: node={}, elem-name={}", node, elemName);
                OptimisationRequirement optr = (OptimisationRequirement) elem;
                MetricContext mc = optr.getMetricContext();
                MetricVariable mv = optr.getMetricVariable();

                // Write rules for OPT-REQ
                if (mc != null) {
                    Context context = new Context();
                    context.setVariable("context", mc.getName());
                    _generateRule(_TC, "OPT-REQ-CTX", grouping, elem, context);
                }
                if (mv != null) {
                    Context context = new Context();
                    context.setVariable("variable", mv.getName());
                    _generateRule(_TC, "OPT-REQ-VAR", grouping, elem, context);
                }
            } else if (elem instanceof camel.requirement.ServiceLevelObjective) {
                log.warn("RuleGenerator.generateRules():      Found a Service-Level-Objective element: node={}, elem-name={}", node, elemName);
                ServiceLevelObjective slo = (ServiceLevelObjective) elem;
                Constraint constr = slo.getConstraint();

                // Write rule for SLO
                Context context = new Context();
                context.setVariable("constraint", constr.getName());
                _generateRule(_TC, "SLO", grouping, elem, context);
            } else

            {
                log.warn("RuleGenerator.generateRules():      ERROR in NODE. Unsupported element type: node={}, elem-name={}, elem-class={}", node, elemName, elemClass);
                providesTopic = false;
            }

            // Add provided topic (i.e. this node generates the rule(s) that will create the events to be published in topic)
            if (providesTopic) {
                _TC.provideGroupingTopicPair(grouping, elem.getName());
            }
        });
        log.info("RuleGenerator.generateRules(): Traversing DAG... done");
    }

    protected boolean checkIfListsAreEqual(List<String> list1, List<String> list2) {
        if (list1 == null && list2 == null) return true;
        else if ((list1 != null && list2 == null) || (list1 == null && list2 != null) || (list1.size() != list2.size()))
            return false;
        //else
        List<String> sorted1 = new ArrayList<>(list1);
        List<String> sorted2 = new ArrayList<>(list2);
        java.util.Collections.sort(sorted1);
        java.util.Collections.sort(sorted2);
        return sorted1.equals(sorted2);
    }

    protected void initTemplateEngine() {
        StringTemplateResolver templateResolver = new StringTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.TEXT);
        SpringStandardDialect dialect = new SpringStandardDialect();
        dialect.setEnableSpringELCompiler(true);

        SpringTemplateEngine engine = new SpringTemplateEngine();
        engine.setDialect(dialect);
        engine.setEnableSpringELCompiler(true);
        engine.setTemplateResolver(templateResolver);

        this.templateEngine = engine;
        log.info("RuleGenerator.initTemplateEngine(): Template engine initialized: {}", engine.getClass().getName());
    }

    // ================================================================================================================
    // Helper methods

    protected String[] getComponentAndDataName(MetricContext mc) {
        String compName = null;
        String dataName = null;
        if (mc != null) {
            ObjectContext objCtx = mc.getObjectContext();
            if (objCtx != null) {
                Component comp = objCtx.getComponent();
                Data data = objCtx.getData();
                compName = comp != null ? comp.getName() : null;
                dataName = data != null ? data.getName() : null;
            }
        }
        String[] result = new String[2];
        result[0] = compName;
        result[1] = dataName;
        return result;
    }

    protected static enum MapType {OPERATOR, UNIT}

    protected static enum ElemType {BEP, UEP, NFE, FE, CONSTR, CMC, RCM, CM, RM, MT, SENSOR, TIME, EVENT}
}