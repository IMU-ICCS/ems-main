/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.execution;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.mddb.cdo.client.CDOClient;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.ow2.paasage.camel.srl.adapter.test.CouchbaseExample;
import org.ow2.paasage.camel.srl.adapter.utils.CamelFinder;
import org.ow2.paasage.camel.srl.adapter.utils.Instantiator;

/**
 * Created by Frank on 18.11.2015.
 */
public class CdoModelSource implements ImportModelSource {
    private static org.apache.log4j.Logger logger;

    static {
        logger = org.apache.log4j.Logger.getLogger(CdoModelSource.class);
    }


    private final CDOClient cl;
    private CDOTransaction trans;

    public CdoModelSource(final CommandLinePropertiesAccessor config) {

        String cdoUser = config.getCdoUser();
        String cdoPassword = config.getCdoPassword();

        //Create the CDOClient
        logger.info("Create CDO client...");
        cl = RetryingCDOClientFactory.client(cdoUser, cdoPassword);

        trans = cl.openTransaction();
    }


    @Override public EList<EObject> getResources(String resourceName) {
        return trans.getResource(resourceName).getContents();
    }

    @Override public void createMetricInstances(FrontendCommunicator fc, CamelFinder finder,
        ExecutionContext ec, CamelModel model, EList<EObject> objs) {
        logger.info("Start creating MetricInstances.");

        Instantiator.createMetricInstances(model, ec, objs);

        // TODO THIS IS A HACK AND NOT MEANT TO BE HERE
        for (VMInstance instance : finder.getVMInstances(ec)) {
            if (instance.getIp() == null || "".equals(instance.getIp())) {
                String ip = fc.getPublicIpOfVmByName(instance.getName());
                instance.setIp(ip);
            }
        }

        // Save them
        try {
            logger.info("Store MetricInstances to CDO.");
            trans.commit();
        } catch (CommitException e) {
            e.printStackTrace();
            throw new RuntimeException("MetricInstances could not be saved!");
        }
    }

    @Override public void createExampleModel(String resourceName) {
        cl.storeModel(CouchbaseExample.get(null), resourceName, false);
    }

    @Override public void terminate() {
        if (cl != null) {
            cl.closeTransaction(trans);
            cl.closeSession();
        }
    }
}
