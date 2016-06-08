/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.adapter;

import eu.paasage.camel.scalability.BinaryEventPattern;
import eu.paasage.camel.scalability.EventPattern;
import eu.paasage.camel.scalability.UnaryEventPattern;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;

/**
 * Created by Frank on 06.09.2015.
 */
public class EventPatternAdapterFactoryImpl implements EventPatternAdapterFactory {

    @Override public Adapter create(FrontendCommunicator fc, EventPattern eventPattern) {
        if (eventPattern instanceof BinaryEventPattern) {
            return new BinaryEventPatternAdapter(fc, (BinaryEventPattern) eventPattern);
        } else if (eventPattern instanceof UnaryEventPattern) {
            throw new RuntimeException("UnaryEventPattern not yet implemented!");
        } else {
            throw new RuntimeException("EventPatternType not yet implemented!");
        }
    }
}
