/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.common.recovery;

import eu.melodic.event.util.EventBus;
import eu.melodic.event.util.PasswordUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static eu.melodic.event.common.recovery.RecoveryConstant.SELF_HEALING_RECOVERY_COMPLETED;

/**
 * Local-node Self-Healing using Shell
 */
@Slf4j
@Component
public class ShellRecoveryTask extends AbstractRecoveryTask {
    public ShellRecoveryTask(EventBus<String,Object,Object> eventBus, PasswordUtil passwordUtil, TaskScheduler taskScheduler, SelfHealingProperties selfHealingProperties) {
        super(eventBus, passwordUtil, taskScheduler, selfHealingProperties);
    }

    @SneakyThrows
    public List<RECOVERY_COMMAND> getRecoveryCommands() {
        throw new Exception("Method not implemented. Use 'runNodeRecovery(List<RECOVERY_COMMAND>)' instead");
    }

    public void runNodeRecovery() throws Exception {
        throw new Exception("Method not implemented. Use 'runNodeRecovery(List<RECOVERY_COMMAND>)' instead");
    }

    public void runNodeRecovery(List<RECOVERY_COMMAND> recoveryCommands) throws Exception {
        log.debug("ShellRecoveryTask: runNodeRecovery(): node-info={}", nodeInfo);

        // Carrying out recovery commands
        log.info("ShellRecoveryTask: runNodeRecovery(): Executing {} recovery commands", recoveryCommands.size());
        for (RECOVERY_COMMAND command : recoveryCommands) {
            if (command==null || StringUtils.isBlank(command.getCommand())) continue;

            waitFor(command.getWaitBefore(), command.getName());

            // Run command as a local process
            log.warn("##############  {}...", command.getName());
            Process process = Runtime.getRuntime().exec(command.getCommand());

            // Redirect SSH output to standard output
            final AtomicBoolean closed = new AtomicBoolean(false);
            redirectShellOutput(process.getInputStream(), "OUT", closed);
            redirectShellOutput(process.getErrorStream(), "ERR", closed);

            waitFor(command.getWaitAfter(), command.getName());

            closed.set(true);
            //if (process.isAlive()) process.destroyForcibly();
        }
        log.info("ShellRecoveryTask: runNodeRecovery(): Executed {} recovery commands", recoveryCommands.size());

        // Send recovery complete event
        eventBus.send(SELF_HEALING_RECOVERY_COMPLETED, "");
    }

    private void redirectShellOutput(InputStream in, String id, AtomicBoolean closed) {
        redirectOutput(in, id, closed,
                "ShellRecoveryTask: redirectShellOutput(): Connection closed: id={}",
                "ShellRecoveryTask: redirectShellOutput(): Exception while copying Process IN stream: id={}\n");
        //IoUtils.copy(in, System.out);
    }
}
