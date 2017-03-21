/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;

/**
 * Created by Frank on 03.09.2015.
 */
public abstract class AbstractAdapter<T> implements Adapter<T> {
    protected static org.apache.log4j.Logger logger;

    static {
        logger = org.apache.log4j.Logger.getLogger(AbstractAdapter.class);
    }

    private final FrontendCommunicator fc;

    public AbstractAdapter(FrontendCommunicator fc) {
        this.fc = fc;
    }

    public FrontendCommunicator getFc() {
        return fc;
    }
}
