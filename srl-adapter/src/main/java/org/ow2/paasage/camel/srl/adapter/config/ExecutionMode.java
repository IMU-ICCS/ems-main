/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.config;

/**
 * Created by Frank on 16.11.2015.
 */
public enum ExecutionMode {
    STATIC("STATIC"),
    ZMQ_LISTEN("ZMQ_LISTEN"),
    ZMQ_HOST("ZMQ_HOST");

    private final String text;

    private ExecutionMode(final String text) {
        this.text = text;
    }

    @Override public String toString() {
        return text;
    }
}
