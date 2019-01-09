/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.baguette.client.install;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class OrchestrationHelper {

    public enum INSTRUCTION_TYPE { LOG, CMD, FILE }

    @Data
    public static class InstallationInstructions {
        private String os;
        private List<Instruction> instructions = new ArrayList<>();

        public List<Instruction> getInstructions() { return Collections.unmodifiableList(instructions); }
        public void setInstructions(List<Instruction> ni) { instructions = new ArrayList<>(ni); }

        public void appendInstruction(Instruction i) { instructions.add(i); }
        public void appendInstruction(INSTRUCTION_TYPE type, String cmd) { instructions.add(new Instruction(type, cmd)); }
        public void appendInstruction(String file, String contents, boolean executable) { instructions.add(new Instruction(file, contents, executable)); }
    }

    @Data
    public static class Instruction {
        private INSTRUCTION_TYPE taskType;
        private String command;
        private String fileName;
        private String contents;
        private boolean executable;

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
    }
}
