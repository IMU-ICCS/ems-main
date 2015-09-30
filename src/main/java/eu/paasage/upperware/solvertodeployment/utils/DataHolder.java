/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */



package eu.paasage.upperware.solvertodeployment.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;

public class DataHolder {

	private static Logger log = Logger.getLogger(DataHolder.class);
	
	private List<InternalComponentInstance> componentInstancesToRegister = new ArrayList<>();
	private List<VMInstance> vmInstancesToRegister = new ArrayList<>();
	private List<HostingInstance> hostingInstancesToRegister = new ArrayList<>();
	private List<CommunicationInstance> communicationInstances = new ArrayList<>();
	
	public List<InternalComponentInstance> getComponentInstancesToRegister() {
		return componentInstancesToRegister;
	}
	

	public List<VMInstance> getVmInstancesToRegister() {
		return vmInstancesToRegister;
	}


	public List<HostingInstance> getHostingInstancesToRegisters() {
		return hostingInstancesToRegister;
	}
	
	public List<CommunicationInstance> getCommunicationInstances() {
		return communicationInstances;
	}
		
	
	
}

