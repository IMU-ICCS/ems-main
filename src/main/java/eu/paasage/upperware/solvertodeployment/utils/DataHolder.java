/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VMInstance;

public class DataHolder {

	@SuppressWarnings("unused")
	private static Logger log = Logger.getLogger(DataHolder.class);
	
	private List<InternalComponentInstance> componentInstancesToRegister = new ArrayList<>();
	private List<VMInstance> vmInstancesToRegister = new ArrayList<>();
	private List<HostingInstance> hostingInstancesToRegister = new ArrayList<>();
	private List<CommunicationInstance> communicationInstances = new ArrayList<>();
	
	private DeploymentModel dm;
	private int dmId;
	
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
	
	public void setDM(DeploymentModel d) { dm = d; }
	public DeploymentModel getDM() { return dm; }

	public void setDmId(int d) { dmId = d; }
	public int getDmId() { return dmId; }

}

