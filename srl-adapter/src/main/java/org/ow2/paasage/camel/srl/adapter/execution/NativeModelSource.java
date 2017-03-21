/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.execution;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.execution.ExecutionContext;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.utils.CamelFinder;

/**
 * Created by Frank on 18.11.2015.
 */
public class NativeModelSource implements ImportModelSource {
    private final CamelModel camelModel;

    public NativeModelSource(CamelModel camelModel) {
        this.camelModel = camelModel;
    }

    @Override public EList<EObject> getResources(String resourceName) {
        //ignores resourceName, since this Class only holds one model:
        EList<EObject> result = new BasicEList<>();

        result.add(camelModel);

        return result;
    }

    @Override public void createMetricInstances(FrontendCommunicator fc, CamelFinder finder,
        ExecutionContext ec, CamelModel model, EList<EObject> objs) {
        // intentionally left blank
    }

    @Override public void createExampleModel(String resourceName) {
        // intentionally left blank
    }

    @Override public void terminate() {
        // intentionally left blank
    }
}

