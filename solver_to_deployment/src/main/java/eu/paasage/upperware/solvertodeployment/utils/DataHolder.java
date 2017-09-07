/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.utils;

import java.util.ArrayList;
import java.util.List;

import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VMInstance;
import lombok.Getter;
import lombok.Setter;

@Getter
public class DataHolder {

	@Setter
	private int dmId;

	private List<InternalComponentInstance> componentInstancesToRegister = new ArrayList<>();
	private List<VMInstance> vmInstancesToRegister = new ArrayList<>();
	private List<HostingInstance> hostingInstancesToRegister = new ArrayList<>();
	private List<CommunicationInstance> communicationInstances = new ArrayList<>();

}

