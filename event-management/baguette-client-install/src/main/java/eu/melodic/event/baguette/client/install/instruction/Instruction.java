/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install.instruction;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.env.Environment;

import javax.validation.constraints.NotNull;
import java.util.Map;
import java.util.regex.Pattern;

@Data
@Builder
public class Instruction {
    private INSTRUCTION_TYPE taskType;
    private String description;
    private String message;
    private String command;
    private String fileName;
    private String localFileName;
    private String contents;
    private boolean executable;
    private int exitCode;
    private boolean match;
    private long executionTimeout;
    private int retries;

    private Map<String, Pattern> patterns;

    // Fluent API
    public Instruction taskType(INSTRUCTION_TYPE taskType) { this.taskType = taskType; return this; }
    public Instruction description(String description) { this.description = description; return this; }
    public Instruction message(String message) { this.message = message; return this; }
    public Instruction command(String command) { this.command = command; return this; }
    public Instruction fileName(String fileName) { this.fileName = fileName; return this; }
    public Instruction localFileName(String localFileName) { this.localFileName = localFileName; return this; }
    public Instruction contents(String contents) { this.contents = contents; return this; }
    public Instruction executable(boolean executable) { this.executable = executable; return this; }
    public Instruction exitCode(int exitCode) { this.exitCode = exitCode; return this; }
    public Instruction match(boolean match) { this.match = match; return this; }
    public Instruction executionTimeout(long executionTimeout) { this.executionTimeout = executionTimeout; return this; }
    public Instruction retries(int retries) { this.retries = retries; return this; }

    public Instruction patterns(Map<String, Pattern> patterns) { this.patterns = patterns; return this; }
    public Instruction pattern(String varName, Pattern pattern) { this.patterns.put(varName, pattern); return this; }

    // Process placeholders
    public Instruction prepareInstruction(Map<String,String> valueMap, Environment environment) {
        return Instruction.builder()
                .taskType(taskType)
                .description(processPlaceholders(description, valueMap, environment))
                .message(processPlaceholders(message, valueMap, environment))
                .command(processPlaceholders(command, valueMap, environment))
                .fileName(processPlaceholders(fileName, valueMap, environment))
                .localFileName(processPlaceholders(localFileName, valueMap, environment))
                .contents(processPlaceholders(contents, valueMap, environment))
                .executable(executable)
                .exitCode(exitCode)
                .match(match)
                .executionTimeout(executionTimeout)
                .retries(retries)
                .patterns(patterns)
                .build();
    }

    private String processPlaceholders(String s, Map<String,String> valueMap, Environment environment) {
        if (StringUtils.isBlank(s)) return s;
        s = StringSubstitutor.replace(s, valueMap);
        s = environment.resolvePlaceholders(s);
        //s = environment.resolveRequiredPlaceholders(s);
        s = s.replace('\\', '/');
        return s;
    }

    // Creators API
    public static Instruction createLog(@NotNull String message) {
        return Instruction.builder()
                .taskType(INSTRUCTION_TYPE.LOG)
                .command(message)
                .build();
    }

    public static Instruction createShellCommand(@NotNull String command) {
        return Instruction.builder()
                .taskType(INSTRUCTION_TYPE.CMD)
                .command(command)
                .build();
    }

    public static Instruction createWriteFile(@NotNull String file, String contents, boolean executable) {
        return Instruction.builder()
                .taskType(INSTRUCTION_TYPE.FILE)
                .fileName(file)
                .contents(contents==null ? "" : contents)
                .executable(executable)
                .build();
    }

    public static Instruction createUploadFile(@NotNull String localFile, @NotNull String remoteFile) {
        return Instruction.builder()
                .taskType(INSTRUCTION_TYPE.COPY)
                .fileName(remoteFile)
                .localFileName(localFile)
                .build();
   }

    public static Instruction createDownloadFile(@NotNull String remoteFile, @NotNull String localFile) {
        return Instruction.builder()
                .taskType(INSTRUCTION_TYPE.DOWNLOAD)
                .fileName(remoteFile)
                .localFileName(localFile)
                .build();
   }

    public static Instruction createCheck(@NotNull String command, @NotNull int exitCode, boolean match, String message) {
        return Instruction.builder()
                .taskType(INSTRUCTION_TYPE.CHECK)
                .command(command)
                .exitCode(exitCode)
                .match(match)
                .contents(message)
                .build();
    }
}