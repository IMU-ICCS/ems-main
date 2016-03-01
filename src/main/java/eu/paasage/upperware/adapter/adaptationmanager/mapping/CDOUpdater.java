/*
 * Copyright (c) 2016 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.mapping;

import java.util.Iterator;

import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.VMInstance;

public class CDOUpdater {
	
	DeploymentModel model;
	VMInstance vmInst;
	
	public CDOUpdater(DeploymentModel runningModel) {
		// TODO Auto-generated constructor stub
		this.model = runningModel;
	}
	
	public void printVMInstances(){
		EList<VMInstance> vmiList = model.getVmInstances();
		for (Iterator iterator = vmiList.iterator(); iterator.hasNext();) {
			VMInstance vmInst = (VMInstance) iterator.next();
			System.out.println(vmInst.getVmType().toString());
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
