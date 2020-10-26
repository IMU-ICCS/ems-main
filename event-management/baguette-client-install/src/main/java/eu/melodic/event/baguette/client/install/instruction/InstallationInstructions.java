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

import java.util.*;

@Data
public class InstallationInstructions {
    private String os;
    private String description;
    private String fileName;
    private Map<String,String> valueMap = new HashMap<>();
    private List<Instruction> instructions = new ArrayList<>();

    public Map<String,String> getValueMap() {
        return Collections.unmodifiableMap(valueMap);
    }
    public void setValueMap(Map<String,String> valueMap) {
        this.valueMap = new HashMap<>(valueMap);
    }

    public List<Instruction> getInstructions() { return Collections.unmodifiableList(instructions); }
    public void setInstructions(List<Instruction> ni) { instructions = new ArrayList<>(ni); }

    public InstallationInstructions appendInstruction(Instruction i) {
        instructions.add(i);
        return this;
    }

    public InstallationInstructions appendLog(String message) {
        instructions.add(Instruction.createLog(message));
        return this;
    }

    public InstallationInstructions appendExec(String command) {
        instructions.add(Instruction.createShellCommand(command));
        return this;
    }

    public InstallationInstructions appendWriteFile(String file, String contents, boolean executable) {
        instructions.add(Instruction.createWriteFile(file, contents, executable));
        return this;
    }

    public InstallationInstructions appendUploadFile(String localFile, String remoteFile) {
        instructions.add(Instruction.createUploadFile(localFile, remoteFile));
        return this;
    }

    public InstallationInstructions appendCheck(String command, int exitCode, boolean match, String message) {
        instructions.add(Instruction.createCheck(command, exitCode, match, message));
        return this;
    }
}
