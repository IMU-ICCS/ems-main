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
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Slf4j
@Component
@RequiredArgsConstructor
public abstract class AbstractRecoveryTask implements RecoveryTask {
    @NonNull protected final EventBus<String,Object,Object> eventBus;
    @NonNull protected final PasswordUtil passwordUtil;
    @NonNull protected final TaskScheduler taskScheduler;
    @NonNull protected final SelfHealingProperties selfHealingProperties;

    @NonNull
    @Getter @Setter
    protected Map nodeInfo = Collections.emptyMap();

    public abstract List<RECOVERY_COMMAND> getRecoveryCommands();
    public abstract void runNodeRecovery() throws Exception;
    public abstract void runNodeRecovery(List<RECOVERY_COMMAND> recoveryCommands) throws Exception;

    protected void waitFor(long millis, String description) {
        if (millis>0) {
            log.warn("##############  Waiting for {}ms after {}...", millis, description);
            try { Thread.sleep(millis); } catch (InterruptedException e) { }
        }
    }

    protected void redirectOutput(InputStream in, String id, AtomicBoolean closed, String connectionClosedMessageFormatter, String exceptionMessageFormatter) {
        taskScheduler.schedule(() -> {
                    try {
                        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
                            while (reader.ready()) {
                                log.info(" {}> {}", id, reader.readLine());
                            }
                        }
                    } catch (IOException e) {
                        if (closed.get()) {
                            log.info(connectionClosedMessageFormatter, id);
                        } else {
                            log.error(exceptionMessageFormatter, id, e);
                        }
                    }
                },
                Instant.now()
        );
    }
}
