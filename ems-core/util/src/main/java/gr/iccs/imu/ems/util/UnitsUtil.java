/*
 * Copyright (C) 2017-2026 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.util;

import java.time.temporal.ChronoUnit;
import java.util.Optional;

public class UnitsUtil {
    public static Optional<ChronoUnit> normalizeTimeUnit(String s) {
        s = s.trim().toLowerCase();
        return Optional.ofNullable(
                switch (s) {
                    case "ms", "msec", "milli", "millis", "millisecond", "milliseconds" -> ChronoUnit.MILLIS;
                    case "s", "sec", "second", "seconds" -> ChronoUnit.SECONDS;
                    case "m", "min", "minute", "minutes" -> ChronoUnit.MINUTES;
                    case "h", "hr", "hrs", "hour", "hours" -> ChronoUnit.HOURS;
                    case "d", "day", "days" -> ChronoUnit.DAYS;
                    case "w", "week", "weeks" -> ChronoUnit.WEEKS;
                    case "mon", "month", "months" -> ChronoUnit.MONTHS;
                    case "yr", "year", "years" -> ChronoUnit.YEARS;
                    default -> null;
                });
    }
}
