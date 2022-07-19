/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.control.util;

import com.google.gson.*;
import eu.melodic.models.interfaces.ems.*;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class TranslationContextMonitorGsonDeserializer implements JsonDeserializer<Monitor> {
    @Override
    public Monitor deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        log.debug("TranslationContextMonitorGsonDeserializer: INPUT:  jsonElement={}, type={}, context={}", jsonElement, type, jsonDeserializationContext);

        JsonObject jsonObject = (JsonObject) jsonElement;
        Monitor monitor = new MonitorImpl();

        String metricName = jsonObject.getAsJsonPrimitive("metric").getAsString();
        monitor.setMetric(metricName);

        String _componentName = null;
        if (jsonObject.has("component")) {
            JsonPrimitive compNameElem = jsonObject.getAsJsonPrimitive("component");
            _componentName = compNameElem!=null ? compNameElem.getAsString() : null;
            monitor.setComponent(_componentName);
        }
        final String componentName = _componentName;

        // Find and initialize sensor
        JsonObject jsonSensorObject = jsonObject.getAsJsonObject("sensor");
        if (jsonSensorObject.has("anyType") && jsonSensorObject.get("anyType").isJsonObject()) {
            jsonSensorObject = jsonSensorObject.getAsJsonObject("anyType");
        }

        boolean isPull = jsonSensorObject.has("className")
                || jsonSensorObject.has("configuration")
                || jsonSensorObject.has("interval");
        boolean isPush = jsonSensorObject.has("port");
        if (isPull && isPush)
            throw new JsonParseException("Monitor.Sensor contains fields of both PullSensor and PushSensor class: "
                    + "metric=" + metricName + ", component=" + componentName);
        if (!isPull && !isPush)
            throw new JsonParseException("Monitor.Sensor contain no fields of either PullSensor or PushSensor class: "
                    + "metric=" + metricName + ", component=" + componentName);

        Sensor sensor;
        if (isPull) {
            PullSensor pullSensor = new PullSensorImpl();
            if (jsonSensorObject.has("className") && !jsonSensorObject.get("className").isJsonNull()) {
                JsonPrimitive classNameElem = jsonSensorObject.getAsJsonPrimitive("className");
                String className = classNameElem!=null ? classNameElem.getAsString() : null;
                pullSensor.setClassName(className);
            }

            pullSensor.setConfiguration( getConfiguration(jsonSensorObject, metricName, componentName, "PullSensor") );

            pullSensor.setInterval( getInterval(jsonSensorObject, metricName, componentName, "PullSensor") );

            sensor = new Sensor(pullSensor);
        } else {
            PushSensor pushSensor = new PushSensorImpl();
            int port = jsonSensorObject.getAsJsonPrimitive("port").getAsInt();
            pushSensor.setPort(port);
            sensor = new Sensor(pushSensor);
        }
        monitor.setSensor(sensor);

        // Get sinks
        if (jsonObject.has("sinks")) {
            if (!jsonObject.get("sinks").isJsonNull()) {
                if (!jsonObject.get("sinks").isJsonArray())
                    throw new JsonParseException("Monitor.sinks must be an array or null: "
                            + "metric=" + metricName + ", component=" + componentName);

                List<Sink> sinks = new ArrayList<>();
                JsonArray jsonSinkArray = jsonObject.getAsJsonArray("sinks");
                jsonSinkArray.forEach(elem -> {
                    if (!elem.isJsonNull()) {
                        JsonObject jsonSinkElem = elem.getAsJsonObject();
                        Sink sink = new SinkImpl();
                        sink.setType(Sink.TypeType.valueOf(jsonSinkElem.getAsJsonPrimitive("type").getAsString()));
                        sink.setConfiguration(getConfiguration(jsonSinkElem, metricName, componentName, "PullSensor.sinks[]"));
                        sinks.add(sink);
                    }
                });

                monitor.setSinks(sinks);
            }
        }

        log.debug("TranslationContextMonitorGsonDeserializer: OUTPUT: monitor={}", monitor);
        return monitor;
    }

    public List<KeyValuePair> getConfiguration(JsonObject jsonObject, String metricName, String componentName, String field) {
        if (!jsonObject.has("configuration")) return null;
        if (jsonObject.get("configuration").isJsonNull()) return null;
        if (!jsonObject.get("configuration").isJsonArray())
            throw new JsonParseException("Monitor."+field+".configuration must be an array or null: "
                    + "metric=" + metricName + ", component=" + componentName);

        JsonArray jsonConfigArray = jsonObject.getAsJsonArray("configuration");
        List<KeyValuePair> configPairs = new ArrayList<>();
        jsonConfigArray.forEach(elem -> {
            if (!elem.isJsonNull()) {
                if (!elem.isJsonObject())
                    throw new JsonParseException("Monitor."+field+".configuration[] element must be an object: "
                            + "metric=" + metricName + ", component=" + componentName);

                JsonObject jsonElemObject = elem.getAsJsonObject();
                JsonElement keyElem = jsonElemObject.get("key");
                JsonElement valueElem = jsonElemObject.get("value");

                if (keyElem.isJsonNull())
                    throw new JsonParseException("Monitor."+field+".configuration[].key cannot be null: "
                            + "metric=" + metricName + ", component=" + componentName);
                if (!keyElem.isJsonPrimitive())
                    throw new JsonParseException("Monitor."+field+".configuration[].key must be an string: "
                            + "metric=" + metricName + ", component=" + componentName);

                if (!keyElem.isJsonNull() && !keyElem.isJsonPrimitive())
                    throw new JsonParseException("Monitor."+field+".configuration[].value must be an string or null: "
                            + "metric=" + metricName + ", component=" + componentName);

                String key = jsonElemObject.get("key").getAsString();
                String value = valueElem != null && valueElem.isJsonPrimitive() ? jsonElemObject.get("value").getAsString() : null;

                KeyValuePair keyValuePair = new KeyValuePairImpl();
                keyValuePair.setKey(key);
                keyValuePair.setValue(value);
                configPairs.add(keyValuePair);
            }
        });

        return configPairs;
    }

    public Interval getInterval(JsonObject jsonObject, String metricName, String componentName, String field) {
        if (!jsonObject.has("interval")) return null;
        if (jsonObject.get("interval").isJsonNull()) return null;
        if (!jsonObject.get("interval").isJsonObject())
            throw new JsonParseException("Monitor.Sensor."+field+".interval must be an object or null: "
                    + "metric=" + metricName + ", component=" + componentName);

        JsonObject jsonIntervalObject = jsonObject.getAsJsonObject("interval");
        JsonElement unitElem = jsonIntervalObject.get("unit");
        JsonElement periodElem = jsonIntervalObject.get("period");

        if (unitElem.isJsonNull())
            throw new JsonParseException("Monitor."+field+".interval.unit cannot be null: "
                    + "metric=" + metricName + ", component=" + componentName);
        if (!unitElem.isJsonPrimitive())
            throw new JsonParseException("Monitor."+field+".interval.unit must be a member of Interval.UnitType enum: "
                    + "metric=" + metricName + ", component=" + componentName);

        if (periodElem.isJsonNull())
            throw new JsonParseException("Monitor."+field+".interval.period cannot be null: "
                    + "metric=" + metricName + ", component=" + componentName);
        if (!periodElem.isJsonPrimitive())
            throw new JsonParseException("Monitor."+field+".interval.period must be an integer: "
                    + "metric=" + metricName + ", component=" + componentName);

        Interval interval = new IntervalImpl();
        interval.setUnit(Interval.UnitType.valueOf(unitElem.getAsString()));
        interval.setPeriod(periodElem.getAsInt());
        return interval;
    }
}
