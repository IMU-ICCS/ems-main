/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.brokercep.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = false)
public class EventMap extends LinkedHashMap<String, Object> implements Serializable {

    // Standard Event fields configuration
    @Data
    @RequiredArgsConstructor
    public static class EventField {
        private final String name;
        private final Class<?> type;
        private final boolean nullable;
        private final boolean skillIfNull;
        private final Function<EventMap,Object> defaultValue;
    }

    public final static List<EventField> STANDARD_EVENT_FIELDS = Collections.unmodifiableList(Arrays.asList(
            new EventField("metricValue", Double.class, false, false, null),
            new EventField("level", Integer.class, true, true, null),
            new EventField("timestamp", Long.class, true, true, (f)->System.currentTimeMillis())
    ));

    public final static Map<String,EventField> STANDARD_EVENT_FIELDS_MAP = STANDARD_EVENT_FIELDS.stream()
            .collect(Collectors.toMap(EventField::getName, x->x));

    public final static String[] PROPERTY_NAMES_ARRAY = STANDARD_EVENT_FIELDS.stream()
            .map(EventField::getName)
            .collect(Collectors.toList())
            .toArray(new String[0]);
    public final static Class<?>[] PROPERTY_CLASSES_ARRAY = STANDARD_EVENT_FIELDS.stream()
            .map(EventField::getType)
            .collect(Collectors.toList())
            .toArray(new Class[0]);

    public static String[] getPropertyNames() {
        return PROPERTY_NAMES_ARRAY;
    }

    public static Class<?>[] getPropertyClasses() {
        return PROPERTY_CLASSES_ARRAY;
    }


    // Constructors
    public EventMap() {
        super();
    }

    public EventMap(Map<String, Object> map) {
        super(map);
    }

    public EventMap(double metricValue) {
        put("metricValue", metricValue);
        put("timestamp", System.currentTimeMillis());
    }

    public EventMap(double metricValue, long timestamp) {
        put("metricValue", metricValue);
        put("timestamp", timestamp);
    }

    public EventMap(double metricValue, int level, long timestamp) {
        put("metricValue", metricValue);
        put("level", level);
        put("timestamp", timestamp);
    }


    // Parse from string
    public static EventMap parseEventMap(String s) {
        if (s==null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        if (s.startsWith("{") && s.endsWith("}")) s = s.substring(1, s.length()-1).trim();
        String[] pairs = s.split(",");
        EventMap eventMap = new EventMap();
        for (String pair : pairs) {
            if (StringUtils.isBlank(pair))
                continue;
            String[] kv = pair.split("[:=]", 2);
            if (kv.length==2)
                eventMap.put(kv[0], kv[1]);
            else
                eventMap.put(kv[0], null);
        }
        return eventMap;
    }


    // Methods overridden
    @Override
    public Object put(String key, Object value) {
        log.trace("EventMap.put(): BEGIN: key={}, value={}", key, value);
        key = removeQuotes(key);
        log.trace("EventMap.put(): KEY with Quotes Stripped: key={}", key);
        EventField field = STANDARD_EVENT_FIELDS_MAP.get(key);
        if (field!=null) {
            log.trace("EventMap.put(): STANDARD_EVENT_FIELD: key={}, value={}", key, value);
            if (value==null) {
                log.trace("EventMap.put(): NULL VALUE: key={}, field-is-nullable={}", key, field.isNullable());
                if (!field.isNullable())
                    throw new NullPointerException("Event field cannot be null: " + key);
                log.trace("EventMap.put(): NULL VALUE: key={}, skip-if-null={}", key, field.isSkillIfNull());
                if (field.isSkillIfNull())
                    return null;
                log.trace("EventMap.put(): BEFORE DEFAULT: key={}, this={}", key, this);
                value = field.getDefaultValue().apply(this);
                log.trace("EventMap.put(): AFTER DEFAULT: key={}, default={}", key, value);
            }
            Class<?> c = field.getType();
            Class<?> vc = value.getClass();
            log.trace("EventMap.put(): VALUE TYPE: key={}, value={}, value-type={}", key, value, vc);
            if (!c.isAssignableFrom(vc) && value instanceof Double) {
                log.trace("EventMap.put(): NOT ASSIGNABLE VALUE TYPE: key={}, field-type={}, value-type={}", key, c, vc);
                if (c==String.class)
                    value = value.toString();
                else if (c==Integer.class)
                    value = (int) Double.parseDouble(removeQuotes(value));
                else if (c==Long.class)
                    value = (long) Double.parseDouble(removeQuotes(value));
                else if (c==Double.class)
                    value = Double.parseDouble(removeQuotes(value));
                else
                    throw new IllegalArgumentException(String.format(
                            "Event field type differs from provided value: field-type=%s, value-type=%s, value=%s",
                                    c.getName(), value.getClass().getName(), value));
                log.trace("EventMap.put(): VALUE AFTER CONVERSION: key={}, value={}, value-type={}", key, value, value.getClass());
            }
        }
        log.debug("EventMap.put(): PUTTING: Key={}, Value={}", key, value);
        return super.put(key, value);
    }

    protected static String removeQuotes(@NonNull Object o) {
        String s = o.toString();
        int l = s.length()-1;
        s = (s.charAt(0)=='"' && s.charAt(l)=='"' || s.charAt(0)=='\'' && s.charAt(l)=='\'')
                ? s.substring(1, l) : s;
        log.trace("EventMap.removeQuotes(): INPUT={}, RESULT={}", o, s);
        return s;
    }

    public String toString() {
        return super.toString();
    }
}