/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep.cep;

import com.espertech.esper.client.EventBean;
import eu.melodic.event.brokercep.event.EventMap;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

//import eu.melodic.event.brokercep.event.MetricEvent;

@Slf4j
public class CepEvalFunction {
    public static double eval(String formula, String streamNames, Map e) {
        return _eval(formula, streamNames, e);
    }

    public static double eval(String formula, String streamNames, Map e1, Map e2) {
        return _eval(formula, streamNames, e1, e2);
    }

    public static double eval(String formula, String streamNames, Map e1, Map e2, Map e3) {
        return _eval(formula, streamNames, e1, e2, e3);
    }

    public static double eval(String formula, String streamNames, Map e1, Map e2, Map e3, Map e4) {
        return _eval(formula, streamNames, e1, e2, e3, e4);
    }

    protected static double _eval(String formula, String streamNames, Map... maps) {
        log.debug(">> ---------------------------------------------------------------------------");
        log.debug(">> eval(map):   formula: {}", formula);
        log.debug(">> eval(map):   streams: {}", streamNames);
        log.debug(">> eval(map):   entries: {}", maps.length);
        log.debug(">> eval(map):   maps:    {}", java.util.Arrays.asList(maps));

        String[] names = streamNames.split(",");
        if (names.length != maps.length)
            throw new IllegalArgumentException("The num. of stream names provided is not equal to the num. of values provided");
        Map<String, Double> args = new HashMap<>();
        for (int i = 0; i < names.length; i++) {
            String entryName = names[i].trim();
            Object entryValue = maps[i].get("metricValue");
            log.debug(">> eval(map):   maps-entry: {} = {} / {}", entryName, entryValue, entryValue.getClass().getName());
            if (entryValue instanceof String) entryValue = Double.parseDouble((String)entryValue);
            args.put(entryName, (Double) entryValue);
        }
        log.debug(">> eval(map):   map-args: {}", args);

        double result = MathUtil.eval(formula, args);
        log.debug(">> eval(map):   result:  {}", result);

        return result;
    }

    public static double eval(String formula, String streamNames, double... v) {
        log.debug(">> ---------------------------------------------------------------------------");
        log.debug(">> eval(double):   formula: {}", formula);
        log.debug(">> eval(double):   streams: {}", streamNames);
        log.debug(">> eval(double):   entries: {}", v.length);
        log.debug(">> eval(double):   values:  {}", v);

        String[] names = streamNames.split(",");
        if (names.length != v.length)
            throw new IllegalArgumentException("The num. of stream names provided is not equal to the num. of values provided");
        Map<String, Double> args = new HashMap<>();
        for (int i = 0; i < names.length; i++) args.put(names[i].trim(), v[i]);
        log.debug(">> eval(double):   map-args: {}", args);

        double result = MathUtil.eval(formula, args);
        log.debug(">> eval(double):   result:  {}", result);

        return result;
    }

    public static EventMap/*MetricEvent*/ newEvent(double metricValue, String... params) {
        return newEvent(metricValue, 1);
    }

    public static EventMap/*MetricEvent*/ newEvent(double metricValue, int level, String... params) {
        log.debug(">> ---------------------------------------------------------------------------");
        log.debug(">> newEvent:   metric-value:  {}", metricValue);
        log.debug(">> newEvent:   params-length: {}", params.length);

        // Add metric value
        EventMap event = new EventMap(metricValue, level, System.currentTimeMillis());
        //MetricEvent event = new MetricEvent(metricValue, level, System.currentTimeMillis());

        // Add extra parameters
        for (int i = 0; i < params.length; i += 2) {
            String paramName = params[i];
            String paramValue = params[i + 1];
            event.put(paramName, paramValue);
        }
        log.debug(">> newEvent:   new-event: {}", event);

        return event;
    }
	
/*	public static double eval(String formula, EPLMethodInvocationContext context) {
		log.debug(">>>>>>>>>>>>>>>>>>   formula: {}", formula);
		log.debug(">>>>>>>>>>>>>>>>>>   statement-name: {}", context.getStatementName());
		String stmtName = context.getStatementName();
		EPStatement stmt = CepExtensions.cepService.getStatementByName(stmtName);
		String stmtText = stmt.getText();
		log.debug(">>>>>>>>>>>>>>>>>>   statement-text: {}", stmtText);
		
		log.debug(">>>>>>>>>>>>>>>>>>   statement-streams: {}", CepExtensions.cepService.getStatementStreams(stmtText) );
		
		double value = -100*Math.random();
		log.debug(">>>>>>>>>>>>>>>>>>   EVAL RESULT: {}", value);
		return value;
	}*/

    public static Object prop(Object eventObj, String propertyName) {
        EventMap event = eventObj instanceof EventMap ? ((EventMap) eventObj) : null;
        log.warn(">> ---------------------------------------------------------------------------");
        log.warn(">> prop:   event-object:  {}", eventObj);
        log.warn(">> prop:    event-class:  {}", eventObj!=null ? eventObj.getClass() : null);
        log.warn(">> prop:      event-map:  {}", event);
        log.warn(">> prop:       property:  {}", propertyName);

        // Retrieve event property
        Object ret = null;
        if (event!=null) {
            Map<String, Object> props = event.getEventProperties();
            if (props != null) {
                log.warn(">> prop:   properties: {}", props);
                ret = props.get(propertyName);
            }
        }

        log.warn(">> prop:       value: {}", ret);
        return ret;
    }
}
