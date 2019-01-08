/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.util;

import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.types.*;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.common.util.EList;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Slf4j
public class CloudiatorHelper {

    public enum INSTRUCTION_TYPE { LOG, CMD, FILE }

    public static class InstallationInstructions {
        private String os;
        private List<Instruction> instructions = new ArrayList<>();

        public String getOs() { return os; }
        public List<Instruction> getInstructions() { return (List<Instruction>) Collections.unmodifiableCollection(instructions); }
        public void setOs(String os) { this.os = os; }
        public void setInstructions(List<Instruction> ni) { instructions = new ArrayList<>(ni); }

        public void appendInstruction(Instruction i) { instructions.add(i); }
        public void appendInstruction(INSTRUCTION_TYPE type, String cmd) { instructions.add(new Instruction(type, cmd)); }
        public void appendInstruction(String file, String contents, boolean executable) { instructions.add(new Instruction(file, contents, executable)); }
    }

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

        public INSTRUCTION_TYPE getTaskType() { return taskType; }
        public String getCommand() { return command; }
        public String getFileName() { return fileName; }
        public String getContents() { return contents; }
        public boolean isExecutable() { return executable; }

        public String toString() {
            return String.format("Instruction { task-type=%s, command=%s, file-name=%s, contents=%s, executable=%b }",
                    taskType, command, fileName, contents, executable);
        }
    }
}
