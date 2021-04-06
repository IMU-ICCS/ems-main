/*
 * Copyright (C) 2017-2020 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.penaltycalculator;

import lombok.Data;

@Data
public class PenaltyFunctionResult {
    private final double penaltyValue;
    private final double startupTime;
}
