/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate.properties;

import eu.melodic.event.util.EmsConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Data
@Validated
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = EmsConstant.EMS_PROPERTIES_PREFIX + "generator")
public class RuleTemplateProperties implements InitializingBean {
    @Override
    public void afterPropertiesSet() {
        log.debug("RuleTemplateProperties: {}", this);
    }

    private String language;

    private Map<String, Map<String, List<String>>> ruleTemplates;

    public void setRuleTemplates(Map<String, Map<String, List<String>>> map) {
        log.debug("RuleTemplateProperties.setRuleTemplates: {}", ruleTemplates);
        this.ruleTemplates = map;
    }

    public List<String> getTemplatesFor(String type, String grouping) {
        log.trace("RuleTemplateProperties.getTemplatesFor: type={}, grouping={}", type, grouping);
        List<String> list = Optional.ofNullable(ruleTemplates.get(type)).map(groupings -> groupings.get(grouping)).orElse(Collections.emptyList());
        log.trace("RuleTemplateProperties.getTemplatesFor: results={}", list);
        return list;
    }
}
