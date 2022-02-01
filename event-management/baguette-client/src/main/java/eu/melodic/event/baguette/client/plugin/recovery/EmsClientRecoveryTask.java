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
 * EMS client (client-side) Self-Healing
 */
@Slf4j
@Component
public class EmsClientRecoveryTask extends VmNodeRecoveryTask {
    @Getter
    private final List<RECOVERY_COMMAND> recoveryCommands = Collections.unmodifiableList(Arrays.asList(
            new RECOVERY_COMMAND("Initial wait...",
                    "pwd",0, 10000),
            new RECOVERY_COMMAND("Sending baguette client kill command...",
                    "/opt/baguette-client/bin/kill.sh",0, 2000),
            new RECOVERY_COMMAND("Sending baguette client start command...",
                    "/opt/baguette-client/bin/run.sh",0, 2000)
    ));

    @Value("${self.healing.recovery.file.baguette:}")
    private String emsRecoveryFile;

    public EmsClientRecoveryTask(@NonNull EventBus<String, Object, Object> eventBus, @NonNull PasswordUtil passwordUtil, @NonNull TaskScheduler taskScheduler) {
        super(eventBus, passwordUtil, taskScheduler);
    }

    public void runNodeRecovery() throws Exception {
        if (StringUtils.isNotBlank(emsRecoveryFile))
            runNodeRecovery(emsRecoveryFile);
        else
            runNodeRecovery(recoveryCommands);
    }
}
