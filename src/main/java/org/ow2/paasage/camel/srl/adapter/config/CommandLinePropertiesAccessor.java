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
    public String getCdoUser();
    public String getCdoPassword();
    public String getModelName();
    public String getResourceName();
    public String getExecutionContextName();
    public String getColosseumUser();
    public String getColosseumTenant();
    public String getColosseumPassword();
    public String getColosseumUrl();
    public String getVisorEndpoint();
    public boolean getSaveExample();
    public boolean getCreateMetricInstances();
}
