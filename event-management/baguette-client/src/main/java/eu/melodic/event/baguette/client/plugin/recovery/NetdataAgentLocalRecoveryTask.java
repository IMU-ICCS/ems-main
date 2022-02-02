/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.plugin.recovery;

import eu.melodic.event.util.EventBus;
import eu.melodic.event.util.PasswordUtil;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Netdata agent (client-side) Self-Healing
 */
@Slf4j
@Component
public class NetdataAgentLocalRecoveryTask extends ShellRecoveryTask {
    @Getter
    private final List<RECOVERY_COMMAND> recoveryCommands = Collections.unmodifiableList(Arrays.asList(
            new RECOVERY_COMMAND("Initial wait...",
                    "pwd",0, 5000),
            new RECOVERY_COMMAND("Sending Netdata agent kill command...",
                    "sudo sh -c  'ps -U netdata -o \"pid\" --no-headers | xargs kill -9' ",0, 2000),
            new RECOVERY_COMMAND("Sending Netdata agent start command...",
                    "sudo netdata",0, 2000)
    ));

    @Value("${self.healing.recovery.file.netdata:}")
    private String netdataRecoveryFile;

    public NetdataAgentLocalRecoveryTask(@NonNull EventBus<String, Object, Object> eventBus, @NonNull PasswordUtil passwordUtil, @NonNull TaskScheduler taskScheduler) {
        super(eventBus, taskScheduler);
    }

    public void runNodeRecovery() throws Exception {
        if (StringUtils.isNotBlank(netdataRecoveryFile))
            runNodeRecovery(netdataRecoveryFile);
        else
            runNodeRecovery(recoveryCommands);
    }
}
