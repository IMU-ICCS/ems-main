/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.communication;

/**
 * Created by Frank on 17.11.2015.
 */
public class CdoConfigTuple {
    private final String resourceName;
    private final String deploymentModelName;
    private final String executionContext;

    public CdoConfigTuple(String resourceName, String deploymentModelName, String executionContext) {
        this.resourceName = resourceName;
        this.deploymentModelName = deploymentModelName;
        this.executionContext = executionContext;
    }

    public String getResourceName() {
        return resourceName;
    }

    public String getDeploymentModelName() {
        return deploymentModelName;
    }

    public String getExecutionContext() {
        return executionContext;
    }

    @Override public String toString() {
        return "CdoConfigTuple{" +
            "resourceName='" + resourceName + '\'' +
            ", deploymentModelName='" + deploymentModelName + '\'' +
            ", executionContext='" + executionContext + '\'' +
            '}';
    }
}
