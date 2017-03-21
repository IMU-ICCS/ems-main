/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.test;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentFactory;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.requirement.RequirementFactory;
import eu.paasage.camel.requirement.RequirementGroup;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.requirement.RequirementOperatorType;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import java.sql.Date;
import java.time.Instant;

/**
 * Created by Frank on 13.09.2015.
 */
public class BeWanExtender {
    public static CamelModel get(EList<EObject> resourceContents, CamelModel model) {


        /*


            RequirementGroup Missing in use case


         */
        RequirementGroup couchRequirementGroup =
            RequirementFactory.eINSTANCE.createRequirementGroup();
        couchRequirementGroup.setName("couchRequirementGroup");
        couchRequirementGroup.setRequirementOperator(RequirementOperatorType.AND);
        couchRequirementGroup.getApplication().add(getBewanApplication(model));
        //        couchRequirementGroup.getRequirements().add(CPUIntensive);
        //        couchRequirementGroup.getRequirements().add(UbuntuReq);
        //        couchRequirementGroup.getRequirements().add(CouchScaleRequirement);
        //        couchRequirementGroup.getRequirements().add(GermanyReq);
        getBewanRequirementModel(model).getRequirements().add(couchRequirementGroup);
        if (resourceContents != null)
            resourceContents.add(couchRequirementGroup);


        /*


            Execution model


         */
        ExecutionModel CouchExecutions = ExecutionFactory.eINSTANCE.createExecutionModel();
        CouchExecutions.setName("CouchExecutions");
        model.getExecutionModels().add(CouchExecutions);
        if (resourceContents != null)
            resourceContents.add(CouchExecutions);

        ExecutionContext ec = ExecutionFactory.eINSTANCE.createExecutionContext();
        ec.setName("ExecutionContext");
        ec.setStartTime(Date.from(Instant.now()));
        ec.setApplication(getBewanApplication(model));
        ec.setDeploymentModel(getBewanDeploymentModel(model));
        ec.setRequirementGroup(couchRequirementGroup);
        CouchExecutions.getExecutionContexts().add(ec);
        if (resourceContents != null)
            resourceContents.add(ec);


        /*

            Instances
            TODO Just hard-coded for show-case

         */
        VMInstance vmApp = DeploymentFactory.eINSTANCE.createVMInstance();
        vmApp.setName("vmApp");
        vmApp.setType(getApplicationComponent(model));
        vmApp.setIp("134.60.64.159");
        model.getDeploymentModels().get(0).getVmInstances().add(vmApp);
        if (resourceContents != null)
            resourceContents.add(vmApp);

        VMInstance vmDb = DeploymentFactory.eINSTANCE.createVMInstance();
        vmDb.setName("vmDb");
        vmDb.setType(getDatabaseComponent(model));
        vmDb.setIp("134.60.64.168");
        model.getDeploymentModels().get(0).getVmInstances().add(vmDb);
        if (resourceContents != null)
            resourceContents.add(vmDb);

        return model;
    }

    public static DeploymentModel getBewanDeploymentModel(CamelModel model) {
        return model.getDeploymentModels().get(0);
    }

    public static RequirementModel getBewanRequirementModel(CamelModel model) {
        return model.getRequirementModels().get(0);
    }

    public static Application getBewanApplication(CamelModel model) {
        for (Application app : model.getApplications()) {
            if ("bewanApplication".equals(app.getName())) {
                return app;
            }
        }
        return null;
    }

    public static InternalComponent getApplicationComponent(CamelModel model) {
        return getComponentByName(model, "bewanApplicationComponent");
    }

    public static InternalComponent getDatabaseComponent(CamelModel model) {
        return getComponentByName(model, "bewanDatabaseComponent ");
    }

    public static InternalComponent getComponentByName(CamelModel model, String name) {
        for (InternalComponent ic : model.getDeploymentModels().get(0).getInternalComponents()) {
            if (ic.getName().equals(name)) {
                return ic;
            }
        }

        return null;
    }
}
