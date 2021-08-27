/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver.util;

import eu.melodic.upperware.metasolver.properties.MetaSolverProperties;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;

@Slf4j
@Component
@RequiredArgsConstructor
public class PredictionHelper {
    private final MetaSolverProperties properties;

    public String getPredictionTopicNameFor(@NonNull String topicName) {
        return String.format(properties.getPredictionTopicFormatter(), topicName);
    }

    public String getTopicNameForPrediction(@NonNull String predictionTopicName) {
        Matcher matcher = properties.getPredictionTopicPattern().matcher(predictionTopicName);
        if (matcher.matches())
            return matcher.group(1);
        return null;
    }

    public long approximatePredictionTime(long ts) {
        return ts;
    }
}
