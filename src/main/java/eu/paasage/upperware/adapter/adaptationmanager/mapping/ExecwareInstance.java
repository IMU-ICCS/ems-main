/*
 * Copyright (c) 2016 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.mapping;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ExecwareInstance {
	
	public static enum State {OK, INPROGRESS, ERROR};
	public static enum scaledState {NEW, EXISTS, NOTFOUND, REMOVED};//NOTFOUND state means that the VM is neither new nor created as a result of scaling. Indicates some problem.
	
	//from API_instance and API_virtualMachine
	State remoteState;//filled
	int applicationComponent;//filled
	String applicationComponentName;//filled
	int applicationInstanceId;//filled
	String applicationInstanceName;//to be filled from Mapping as it is discarded in ExecutionWare
	int virtualMachineId;//Execware VM id filled
	String virtualMachineName;//filled
	String InstanceId = "-1";//for EW Instance id - updated from ExecWare
	String instanceName = "not_found";
	int cloud, image, hardware, location;
	String cloudName, imageSwordId, hardwareSwordId, locationSwordId;
	
	//to be fetched by matching from the running Deployment model in CDO
	String VMTemplateName;
	scaledState scState;
	int IciInDmOrSimilar = -1;//stores the pointer to a (similar) InternalComponentInstance in the current Deployment model, if scaledState==(NEW) or EXISTS
	//String newVirtualMachineName;//to be set after the new Deployment model is generated
	
	private final static Logger LOGGER = Logger.getLogger(ExecwareInstance.class
			.getName());
	
	public void setInstance(String remoteState, String applicationComponent, String applicationInstanceId, int virtualMachineId, String instanceId){
		
		if(remoteState.equalsIgnoreCase("OK"))
			this.remoteState = State.OK;
		else if(remoteState.equalsIgnoreCase("INPROGRESS"))
			this.remoteState = State.INPROGRESS;
		else if(remoteState.equalsIgnoreCase("ERROR"))
			this.remoteState = State.ERROR;
		else
			this.remoteState = State.ERROR;
		
		this.applicationComponent = Integer.parseInt(applicationComponent);
		this.applicationInstanceId = Integer.parseInt(applicationInstanceId);
		this.virtualMachineId = virtualMachineId;
		this.InstanceId = instanceId;
	}
	
	public boolean isStateOK(){return (this.remoteState == State.OK);}
	
	public boolean isStateInprogress(){return (this.remoteState == State.INPROGRESS);}
	
	public void setScaledState(scaledState sState){this.scState = sState;}
	
	public scaledState getScaledState(){return this.scState;}
	
	public void setIciPointerinDm(int pointer){this.IciInDmOrSimilar = pointer;}
	
	public int getIciPointerinDm(){return this.IciInDmOrSimilar;}
	
	public int getVirtualMachineId(){return this.virtualMachineId;}
	
	public String getVirtualMachineName(){return this.virtualMachineName;}
	
	public int getApplicationComponent(){return this.applicationComponent;}
	
	public String getApplicationComponentName(){return this.applicationComponentName;}
	
	public String getVMTemplateName(){return this.VMTemplateName;}
	
	public String getInstanceId(){return this.InstanceId;}
	
	public String getInstanceName(){return this.instanceName;}
	
	public void setApplicationInstanceName(String applicationInstanceName){this.applicationInstanceName = applicationInstanceName;}
	
	public String getApplicationInstanceName(){return this.applicationInstanceName;}
	
	//public String getNewVirtualMachineName(){return this.newVirtualMachineName;}
	
	public void setVirtualMachineName(int virtualMachineId, String virtualMachineName){
		if(this.virtualMachineId == virtualMachineId)
			this.virtualMachineName = virtualMachineName;
	}
	
	public void setApplicationComponentName(int applicationComponent, String applicationComponentName){
		if(this.applicationComponent == applicationComponent)
			this.applicationComponentName = applicationComponentName;
	}
	
	public void setVMTemplateName(String VMTemplateName){
		this.VMTemplateName = VMTemplateName;
	}
	
	public void setInstanceName(String instanceId, String instanceName){
		if(this.InstanceId.equalsIgnoreCase(instanceId))
			this.instanceName = instanceName;
	}
	
	/*public void setNewVirtualMachineName(int virtualMachine, String newVirtualMachineName){
		if(this.virtualMachineId == virtualMachine)
			this.newVirtualMachineName = newVirtualMachineName;
	}*/

	public void setInstanceId(String InstanceId){
		this.InstanceId = InstanceId;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
