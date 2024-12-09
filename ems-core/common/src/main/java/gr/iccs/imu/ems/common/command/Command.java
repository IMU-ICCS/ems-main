/*
 * Copyright (C) 2017-2025 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.common.command;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@Data
@Builder
public class Command {
    private long receivedTimestamp;
    private long startExecutionTimestamp;
    private long endExecutionTimestamp;
    private String command;
    private List<String> args;
    @JsonIgnore
    @ToString.Exclude
    private transient Consumer<Object> callback;

    public void splitArgs() {
        if (args==null && StringUtils.isNotBlank(command)) {
            String[] part = command.trim().split("[ \t\r\n]+");
            command = part[0];
            args = Arrays.asList(Arrays.copyOfRange(part, 1, part.length));
        }
    }
}
