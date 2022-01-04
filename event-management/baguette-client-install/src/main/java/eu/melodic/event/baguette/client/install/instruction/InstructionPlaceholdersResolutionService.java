/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install.instruction;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class InstructionPlaceholdersResolutionService {
    private final Environment environment;
    private static InstructionPlaceholdersResolutionService INSTANCE;

    public static InstructionPlaceholdersResolutionService getInstance() {
        if (INSTANCE==null) throw new IllegalStateException("InstructionPlaceholdersResolutionService singleton instance has not yet been initialized");
        return INSTANCE;
    }

    public InstructionPlaceholdersResolutionService(@NonNull Environment environment) {
        if (INSTANCE!=null) throw new IllegalStateException("InstructionPlaceholdersResolutionService singleton instance has already been initialized");
        this.environment = environment;
        INSTANCE = this;
    }

    public Instruction resolvePlaceholders(Instruction instruction, Map<String,String> valueMap) {
        return instruction.toBuilder()
                .description(processPlaceholders(instruction.description(), valueMap))
                .message(processPlaceholders(instruction.message(), valueMap))
                .command(processPlaceholders(instruction.command(), valueMap))
                .fileName(processPlaceholders(instruction.fileName(), valueMap))
                .localFileName(processPlaceholders(instruction.localFileName(), valueMap))
                .contents(processPlaceholders(instruction.contents(), valueMap))
                .build();
    }

    public String processPlaceholders(String s, Map<String,String> valueMap) {
        if (StringUtils.isBlank(s)) return s;
        s = StringSubstitutor.replace(s, valueMap);
        s = environment.resolvePlaceholders(s);
        //s = environment.resolveRequiredPlaceholders(s);
        s = s.replace('\\', '/');
        return s;
    }
}