/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.config;

/**
 * Created by Frank on 09.08.2015.
 */
public interface CommandLinePropertiesAccessor {
    String getCdoUser();

    String getCdoPassword();

    String getModelName();

    String getResourceName();

    String getExecutionContextName();

    String getColosseumUser();

    String getColosseumTenant();

    String getColosseumPassword();

    String getColosseumUrl();

    String getVisorEndpoint();

    boolean getSaveExample();

    boolean getCreateMetricInstances();

    boolean getCreateMonitorSubscriptions();

    boolean getCleanMonitoring();

    ExecutionMode getExecutionMode();

    int getZeroMqPort();

    String getZeroMqUri();

    String getZeroMqQueue();

    String getZeroMqTestmessage();

    ModelSourceType getModelSourceType();

    String getDeploymentNamePrefix();

    int getMcaZeroMqPort();

    String getMcaZeroMqQueue();
}
