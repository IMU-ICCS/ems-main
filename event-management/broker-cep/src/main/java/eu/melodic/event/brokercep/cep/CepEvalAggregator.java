/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.brokercep.cep;

import com.espertech.esper.epl.agg.aggregator.AggregationMethod;
import eu.melodic.event.brokercep.event.EventMap;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class CepEvalAggregator implements AggregationMethod {
    private List<Object[]> entries = new ArrayList<>();

    public void clear() {
        log.debug("CepEvalAggregator.clear(): aggregator-hash={}", hashCode());
        entries.clear();
    }

    public void enter(Object value) {
        log.debug("CepEvalAggregator.enter(): aggregator-hash={}, input={}, hash={}", hashCode(), value, value.hashCode());
        entries.add((Object[]) value);        // 0:formula, 1:stream-names, 2+:EventMap
    }

    public void leave(Object value) {
        Object[] oldValue = entries.remove(0);
        log.debug("CepEvalAggregator.leave(): aggregator-hash={}, input={}, removed={}, hash={}", hashCode(), value, oldValue, value.hashCode());
    }

    public Object getValue() {
        log.debug("CepEvalAggregator.getValue(): BEGIN");
        synchronized (entries) {
            if (entries.size() == 0) {
                log.debug("CepEvalAggregator.getValue(): END_0: aggregator-hash={}, result=0", hashCode());
                return new Double(0);
            }

            // get formula and stream names (they must be identical for all entries)
            Object[] first = entries.get(0);
            String formula = (String) first[0];
            String[] streamNames = ((String) first[1]).split(",");

            // initialize event lists for each stream
            List<List<EventMap>> lists = new ArrayList<>();
            for (int i = 0; i < streamNames.length; i++) {
                lists.add(new ArrayList<EventMap>());
            }

            // append events from entries into sream event lists
            for (Object[] entry : entries) {
                if (!((String) entry[0]).equals(formula) && !((String) entry[1]).equals(streamNames))
                    throw new IllegalArgumentException("Aggregator entries do not contain the same formula or stream names in arguments #0 or #1");
                for (int i = 0; i < streamNames.length; i++) {
                    if (EventMap.class.isAssignableFrom(entry[i + 2].getClass())) {
                        lists.get(i).add((EventMap) entry[i + 2]);
                    } else if (HashMap.class.isAssignableFrom(entry[i + 2].getClass())) {
                        EventMap eventMap = new EventMap((HashMap) entry[i + 2]);
                        lists.get(i).add(eventMap);
                    } else {
                        throw new RuntimeException("Event type is not supported: " + entry[i + 2].getClass().getName());
                    }
                }
            }

            // extract values from events
            log.debug("CepEvalAggregator.getValue(): formula: {}", formula);
            log.debug("CepEvalAggregator.getValue(): streams: {}", java.util.Arrays.asList(streamNames));
            log.debug("CepEvalAggregator.getValue(): stream-event-lists: {}", lists.size());
            List<List<Double>> dataLists = new ArrayList<>();
            for (int i = 0, n = lists.size(); i < n; i++) {
                log.trace("CepEvalAggregator.getValue(): event-list-{}: {}", i, lists.get(i));
                List<Double> data = lists.get(i).stream().map(event -> (Double) event.get("metricValue")).collect(Collectors.toList());
                log.trace("CepEvalAggregator.getValue(): data-list-{}: {}", i, data);
                dataLists.add(data);
            }

            // prepare arguments of MathParser
            Map<String, List<Double>> args = new HashMap<>();
            for (int i = 0; i < streamNames.length; i++) {
                args.put(streamNames[i].trim(), dataLists.get(i));
            }
            log.debug("CepEvalAggregator.getValue(): stream-data-lists: {}", args);

            // use MathParser to evaluate formula using stream data lists
            double result = MathUtil.evalAgg(formula, args);
            log.debug("CepEvalAggregator.getValue(): END: aggregator-hash={}, result={}", hashCode(), result);
            return new Double(result);
        }
    }
}
