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
import org.springframework.stereotype.Service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Client installer
 */
@Slf4j
@Service
@NoArgsConstructor
public class ClientInstaller implements InitializingBean, Runnable {
    private static ClientInstaller singleton;

    private BlockingQueue<ClientInstallationTask> taskQueue = new LinkedBlockingQueue<>();
    private Thread thread;
    private boolean running;

    @Override
    public void afterPropertiesSet() {
        singleton = this;
        startThread();
    }

    public static ClientInstaller instance() { return singleton; }

    public void addTask(ClientInstallationTask task) {
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
                log.warn(">>>>> WILL EXECUTE TASK: {}", task);
            }
        } catch (InterruptedException ex) {
            log.warn("ClientInstaller: Stopping task execution thread");
        }
    }
}
