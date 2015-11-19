/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.config;

import org.ow2.paasage.camel.srl.adapter.execution.CdoModelSource;
import org.ow2.paasage.camel.srl.adapter.execution.ImportModelSource;

/**
 * Created by Frank on 18.11.2015.
 */
public enum ModelSourceType {
    TEXTUAL("TEXTUAL"),
    CDO("CDO");

    private final String text;

    private ModelSourceType(final String text) {
        this.text = text;
    }

    @Override public String toString() {
        return text;
    }

    public static ImportModelSource mapToIms(CommandLinePropertiesAccessor config){
        ImportModelSource ims;

        switch(config.getModelSourceType()){
            case TEXTUAL:
            case CDO:
            default:
                ims = new CdoModelSource(config); //TODO only CDO is currently available.
        }

        return ims;
    }
}
