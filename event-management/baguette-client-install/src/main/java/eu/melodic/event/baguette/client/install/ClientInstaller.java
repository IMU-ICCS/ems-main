/*
 * Copyright (C) 2017-2019 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.baguette.client.install;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Client installer
 */
@Slf4j
@Service
@NoArgsConstructor
public class ClientInstaller implements InitializingBean, Runnable {
    private static ClientInstaller singleton;

    @Autowired
    private ClientInstallationProperties properties;

    private BlockingQueue<ClientInstallationTask> taskQueue = new LinkedBlockingQueue<>();
    private Thread thread;
    private boolean running;
    private AtomicLong taskCounter = new AtomicLong();

    @Override
    public void afterPropertiesSet() {
        singleton = this;
        startThread();
    }

    public static ClientInstaller instance() { return singleton; }

    public void addTask(@NotNull ClientInstallationTask task) {
        taskQueue.add(task);
    }

    public synchronized void startThread() {
        if (running) return;
        running = true;
        thread = new Thread(this);
        thread.setDaemon(true);
        thread.start();
    }

    public synchronized void stopThread() {
        if (!running) return;
        running = false;
        thread.interrupt();
    }

    public void run() {
        try {
            log.info("ClientInstaller: Starting task execution thread");
            while (running) {
                ClientInstallationTask task = taskQueue.take();
                long taskCnt = taskCounter.getAndIncrement();
                log.info("ClientInstaller: Executing Client installation task #{}: {}", taskCnt, task);
                long startTm = System.currentTimeMillis();
                boolean result = executeTask(task, taskCnt);
                long endTm = System.currentTimeMillis();
                log.info("ClientInstaller: Client installation task #{}: success={}, duration={}ms",
                        taskCnt, result, endTm-startTm);
            }
        } catch (InterruptedException ex) {
            log.warn("ClientInstaller: Stopping task execution thread");
        }
    }

    private boolean executeTask(ClientInstallationTask task, long taskCounter) {
        if ("VM".equalsIgnoreCase(task.getType())) {
            return executeVmTask(task, taskCounter);
        } else {
            log.error("ClientInstaller: UNSUPPORTED TASK TYPE: {}", task.getType());
        }
        return false;
    }

    private boolean executeVmTask(ClientInstallationTask task, long taskCounter) {
        return SshClientInstaller.builder()
                .task(task)
                .taskCounter(taskCounter)
                .maxRetries(properties.getMaxRetries())
                .authenticationTimeout(properties.getAuthenticateTimeout())
                .connectTimeout(properties.getConnectTimeout())
                .heartbeatInterval(properties.getHeartbeatInterval())
                .simulateConnection(properties.isSimulateConnection())
                .simulateExecution(properties.isSimulateExecution())
                .build()
                .execute();
    }
}
