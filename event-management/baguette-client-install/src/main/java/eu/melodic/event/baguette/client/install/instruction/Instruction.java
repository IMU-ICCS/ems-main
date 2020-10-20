/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install.instruction;

import lombok.Data;

@Data
public class Instruction {
    private INSTRUCTION_TYPE taskType;
    private String command;
    private String fileName;
    private String localFileName;
    private String contents;
    private boolean executable;
    private int exitCode;
    private boolean match;

    public Instruction(INSTRUCTION_TYPE type, String cmd) {
        taskType = type;
        command = cmd;
    }

    public Instruction(String file, String contents, boolean executable) {
        taskType = INSTRUCTION_TYPE.FILE;
        fileName = file;
        this.contents = contents;
        this.executable = executable;
    }

    public Instruction(String command, int exitCode, boolean match, String message) {
        taskType = INSTRUCTION_TYPE.CHECK;
        this.command = command;
        this.exitCode = exitCode;
        this.match = match;
        this.contents = message;
    }
}