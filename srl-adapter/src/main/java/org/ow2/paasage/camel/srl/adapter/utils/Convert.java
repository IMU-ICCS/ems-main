/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.utils;

import de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule;
import de.uniulm.omi.cloudiator.colosseum.client.entities.TimeWindow;
import eu.paasage.camel.unit.TimeIntervalUnit;
import eu.paasage.camel.unit.UnitType;

import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 09.06.2015.
 */
public class Convert {
    public static double hertz(Long interval, TimeUnit unit) throws Exception {
        Long intervalInSeconds = timeToSeconds(unit, interval);
        if (intervalInSeconds == 0) {
            return 0;
        }
        return 1 / intervalInSeconds;
    }

    public static double hertz(TimeWindow t) throws Exception {
        return hertz(t.getInterval(), t.getTimeUnit());
    }

    public static double hertz(Schedule s) throws Exception {
        return hertz(s.getInterval(), s.getTimeUnit());
    }

    public static long timeToMilliseconds(Object unit, long interval) {
        if (unit instanceof TimeUnit) {
            switch ((TimeUnit) unit) {
                case HOURS:
                    return interval * 1000 * 60 * 60;
                case MINUTES:
                    return interval * 1000 * 60;
                case SECONDS:
                    return interval * 1000;
                case MILLISECONDS:
                    return interval;
                default:
                    throw new RuntimeException("TimeUnit for Schedule not implemented!");
            }
        } else if (unit instanceof UnitType) {
            switch ((UnitType) unit) {
                case HOURS:
                    return interval * 1000 * 60 * 60;
                case MINUTES:
                    return interval * 1000 * 60;
                case SECONDS:
                    return interval * 1000;
                case MILLISECONDS:
                    return interval;
                default:
                    throw new RuntimeException("TimeUnit for Schedule not implemented!");
            }
        } else {
            switch (unit.toString()) {
                case "HOURS":
                    return interval * 1000 * 60 * 60;
                case "MINUTES":
                    return interval * 1000 * 60;
                case "SECONDS":
                    return interval * 1000;
                default:
                    throw new RuntimeException("TimeUnit for Schedule not implemented!");
            }
        }
    }

    public static long timeToSeconds(Object unit, long interval) {
        return (long) (timeToMilliseconds(unit, interval) / 1000);
    }

    public static TimeUnit toJavaTimeUnit(TimeIntervalUnit unit) {
        switch (unit.getUnit()) {
            case MILLISECONDS:
                return TimeUnit.MILLISECONDS;
            case SECONDS:
                return TimeUnit.SECONDS;
            case MINUTES:
                return TimeUnit.MINUTES;
            case HOURS:
                return TimeUnit.HOURS;
            default:
                throw new RuntimeException("TimeIntervalUnit not yet implemented!");
        }
    }
}
