/*
 * Copyright (C) 2017-2025 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.baguette.client.install.installer;

import gr.iccs.imu.ems.baguette.client.install.ClientInstallationProperties;
import gr.iccs.imu.ems.baguette.client.install.ClientInstallationTask;
import gr.iccs.imu.ems.baguette.client.install.ClientInstallerPlugin;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * SSH client installer on a Kubernetes cluster
 */
@Slf4j
@Getter
public class K8sClientInstaller implements ClientInstallerPlugin {
    private final ClientInstallationTask task;
    private final long taskCounter;
    private final ClientInstallationProperties properties;

    @Builder
    public K8sClientInstaller(ClientInstallationTask task, long taskCounter, ClientInstallationProperties properties) {
        this.task = task;
        this.taskCounter = taskCounter;
        this.properties = properties;
    }

    @Override
    public boolean executeTask() {
        boolean success = true;
        try {
            deployOnCluster();
        } catch (Exception ex) {
            log.error("K8sClientInstaller: Failed executing installation instructions for task #{}, Exception: ", taskCounter, ex);
            success = false;
        }

        if (success) log.info("K8sClientInstaller: Task completed successfully #{}", taskCounter);
        else log.info("K8sClientInstaller: Error occurred while executing task #{}", taskCounter);
        return true;
    }

    private void deployOnCluster() {
        task.getNodeRegistryEntry().nodeInstallationComplete(null);
    }

    @Override
    public void preProcessTask() {
        // Throw exception to prevent task exception, if task data have problem
    }

    @Override
    public boolean postProcessTask() {
        return true;
    }
}
