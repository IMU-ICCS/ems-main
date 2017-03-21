/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*package eu.paasage.upperware.adapter.adaptationmanager.plangeneration;

import java.util.ArrayList;
import java.util.List;

import eu.paasage.camel.deployment.ComponentInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VMInstance;

public final class ModelUtil {

    // private constructor to avoid unnecessary instantiation of the class
    private ModelUtil() {
    }

    public static List<VMInstance> getVMInstances(DeploymentModel dm) {
		List<VMInstance> vms=new ArrayList<VMInstance>();
        for (ComponentInstance c:dm.getComponentInstances()){
        	if (c instanceof VMInstance)
        		vms.add((VMInstance) c);
        }
		return vms;
	}

	public static List<InternalComponentInstance> getInternalComponentInstances(DeploymentModel dm) {
		List<InternalComponentInstance> vms=new ArrayList<InternalComponentInstance>();
        for (ComponentInstance c:dm.getComponentInstances()){
        	if (c instanceof InternalComponentInstance)
        		vms.add((InternalComponentInstance) c);
        }
		return vms;
	}
}*/