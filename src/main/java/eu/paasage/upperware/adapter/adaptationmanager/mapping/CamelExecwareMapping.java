/*
 * Copyright (c) 2015 INRIA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.mapping;

import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;

import eu.paasage.upperware.adapter.adaptationmanager.actions.ApplicationCreationAction;

public class CamelExecwareMapping {
	
	public void testFunct(){}
	
	private static final Logger LOGGER = Logger
			.getLogger(CamelExecwareMapping.class.getName());
	/**
	 * The cloud lookup is for connecting a cloud to ExecWare for the first time.
	 * This is a one time process when there's a new cloud
	 */
	static boolean cloudLookup = false;
	
	public static synchronized boolean isCloudLookupInProgress(){
		return cloudLookup;
	}
	
	public static synchronized boolean cloudLookingUp(){
		if(!cloudLookup)
			cloudLookup = true;
		return cloudLookup;
	}
	
	public static synchronized boolean CloudLookedUp(){
		if(cloudLookup)
			cloudLookup = false;
		return (!cloudLookup);
	}
	
	//BiMap<String, String> components = HashBiMap.create();
	Application appli;
	ApplicationInstance appliInst;
	
	public void createApplication(String applicationId_Exec, String name_Camel){
		appli = new Application(applicationId_Exec, name_Camel);
		LOGGER.log(Level.INFO, "Application created : " + name_Camel);
	}
	
	public Application getApplication(String name_Camel){
		if(appli.name_Camel.equalsIgnoreCase(name_Camel))
			return appli;
		else
			return null;
	}
	
	public String getApplicationId(){
		LOGGER.log(Level.INFO, "Application " + appli.getApplicationId_Exec());
		return appli.getApplicationId_Exec();
	}
	
	public String getApplicationName_Camel(){
		LOGGER.log(Level.INFO, "Application " + appli.getName_Camel());
		return appli.getName_Camel();
	}
	
	public String getApplicationId(String name_Camel){
		if(appli.name_Camel.equalsIgnoreCase(name_Camel)){
			LOGGER.log(Level.INFO, "Application " + appli.getApplicationId_Exec());
			return appli.getApplicationId_Exec();
		} else
			return null;
	}
	
	public String updateApplication(String applicationId_Exec, String name_Camel){
		if(appli.getApplicationId_Exec().equalsIgnoreCase(applicationId_Exec)){
			appli.setApplicationName(name_Camel);
			LOGGER.log(Level.INFO, "Application name updated " + appli.getApplicationId_Exec());
			return appli.getApplicationId_Exec();
		} else
			return null;
	}
	
	public boolean deleteApplication(String name_Camel){
		if(appli.name_Camel.equalsIgnoreCase(name_Camel)){
			LOGGER.log(Level.INFO, "Application deleted : " + name_Camel);
			appli = null;
			return true;
		} else
			return false;
	}
	
//------------------------------------------	
	
	public void createApplicationInstance(Application app, String applicationInstanceId_Exec, String name_Camel){
		appliInst = new ApplicationInstance(app, applicationInstanceId_Exec, name_Camel);
		LOGGER.log(Level.INFO, "Application Instance " + name_Camel);
	}
	
	public ApplicationInstance getApplicationInstance(String name_Camel){
		if(appliInst.name_Camel.equalsIgnoreCase(name_Camel))
			return appliInst;
		else
			return null;
	}
	
	public String getApplicationInstanceId(){
		LOGGER.log(Level.INFO, "Application Instance " + appliInst.getApplicationInstanceId_Exec());
		return appliInst.getApplicationInstanceId_Exec();
	}
	
	public String getApplicationInstanceName_Camel(){
		LOGGER.log(Level.INFO, "Application " + appliInst.getAppInstName());
		return appliInst.getAppInstName();
	}
	
	public boolean updateApplicationInstanceName(String ApplicationInstanceId, String name_Camel){
		if(appliInst.getApplicationInstanceId_Exec().equalsIgnoreCase(ApplicationInstanceId)){
			appliInst.setApplicationInstanceName(name_Camel);
			LOGGER.log(Level.INFO, "Updated Application Instance name " + name_Camel);
			return true;
		}
		return false;
	}
	
	public String getApplicationInstanceId(String name_Camel){
		if(appliInst.name_Camel.equalsIgnoreCase(name_Camel)){
			LOGGER.log(Level.INFO, "Application " + appliInst.getApplicationInstanceId_Exec());
			return appliInst.getApplicationInstanceId_Exec();
		} else
			return null;
	}
	
	public boolean deleteApplicationInstance(String ApplicationInstanceId){
		if(appliInst.getApplicationInstanceId_Exec().equalsIgnoreCase(ApplicationInstanceId)){
			appliInst = null;
			return true;
		}
		else
			return false;
	}
	
	//Stores all the VM type entities created
	LinkedList<VirtualMachineTemplate> VMTs = new LinkedList<CamelExecwareMapping.VirtualMachineTemplate>();
	
	public void addVMT(String name_Camel){
		VirtualMachineTemplate VMtemplate = new VirtualMachineTemplate(name_Camel);
		synchronized(VMTs){
			VMTs.add(VMtemplate);
			LOGGER.log(Level.INFO, "Added VMT with name_Camel " + name_Camel);
		}
	}
	
	public boolean removeVMT(String name_Camel){
		boolean status = false;
		synchronized(VMTs){
			ListIterator<VirtualMachineTemplate> it = VMTs.listIterator();
			while(it.hasNext()){
				VirtualMachineTemplate temp = it.next();
				if(temp.getVMType().equalsIgnoreCase(name_Camel)){
					it.remove();
					status = true;
					LOGGER.log(Level.INFO, "Removed VMT with name_Camel " + name_Camel);
				}
			}
		}
		return status;
	}
	
	public void addImageNametoVMT(String name_Camel, String imageName){
		synchronized(VMTs){
			for(VirtualMachineTemplate VMtemplate : VMTs){
				if(VMtemplate.getVMType().equalsIgnoreCase(name_Camel)){
					VMtemplate.setImageName(imageName);
					LOGGER.log(Level.INFO, "VMTimage_name name_Camel " + VMtemplate.getImageName() + " " + VMtemplate.getVMType());
				}
			}
		}
	}
	
	public String getImageNameFromVMT(String name_Camel){
		synchronized(VMTs){
			for(VirtualMachineTemplate VMtemplate : VMTs)
				if(VMtemplate.getVMType().equalsIgnoreCase(name_Camel))
					return VMtemplate.getImageName();
			return null;
		}
	}
	
	public boolean checkEntityVMT(String name_Camel){
		boolean status = false;//entity does not exist yet
		synchronized(VMTs){
			for(VirtualMachineTemplate VMtemplate : VMTs)
				if(VMtemplate.getVMType().equalsIgnoreCase(name_Camel) && VMtemplate.getId()!=null)
					status = true;
			return status;
		}
	}
	
	public String getEntityVMTid(String name_Camel){
		synchronized(VMTs){
			for(VirtualMachineTemplate VMtemplate : VMTs)
				if(VMtemplate.getVMType().equalsIgnoreCase(name_Camel) && VMtemplate.getId()!=null)
					return VMtemplate.getId();
			return null;
		}
	}
	
	public VirtualMachineTemplate getEntityVMT(String name_Camel){
		LOGGER.log(Level.INFO, "Fetching VMT entity named " + name_Camel);
		synchronized(VMTs){
			for(VirtualMachineTemplate VMtemplate : VMTs)
				if(VMtemplate.getVMType().equalsIgnoreCase(name_Camel) && VMtemplate.getId()!=null)
					return VMtemplate;
			return null;
		}
	}
	
	public void setVMTIDs(String name_Camel, String id, String cloud, String image, String location, String hardware){
		synchronized(VMTs){
			for(VirtualMachineTemplate VMtemplate : VMTs)
				if(VMtemplate.getVMType().equalsIgnoreCase(name_Camel) && VMtemplate.getId()==null)
					VMtemplate.setAllIDs(id, cloud, image, location, hardware);
		}
	}
	
	/**
	 * returns an String array of ExecWare IDs for VM template, cloud, image, location, hardware
	 * @param name_Camel is the VM type name from CAMEL
	 * @return an String array of ExecWare IDs for VM template, cloud, image, location, hardware (as in ordered)
	 */
	public String[] getEntityVMTIDs(String name_Camel){
		
		String[] ids = new String[5];
		synchronized(VMTs){
			for(VirtualMachineTemplate VMtemplate : VMTs)
				if(VMtemplate.getVMType().equalsIgnoreCase(name_Camel) && VMtemplate.getId()!=null){
					ids[0] = VMtemplate.getId();
					ids[1] = VMtemplate.getCloud();
					ids[2] = VMtemplate.getImageID();
					ids[3] = VMtemplate.getLocation();
					ids[4] = VMtemplate.getHardware();
				}
		}
		return ids;
	}
	
	//Stores all the VM Instance entities created
	LinkedList<VirtualMachineInstance> VMIs = new LinkedList<CamelExecwareMapping.VirtualMachineInstance>();
	
	public boolean addVMI(String name_Camel, String vmt_name_Camel){
		
		VirtualMachineInstance VMInst = null;
		VirtualMachineTemplate vmt = null;
		
		synchronized(VMTs){
			for(VirtualMachineTemplate VMtemplate : VMTs)
				if(VMtemplate.getVMType().equalsIgnoreCase(vmt_name_Camel) && VMtemplate.getId()!=null)
					vmt = VMtemplate;
		}
		
		synchronized(VMIs){
			if(vmt != null){
				VMInst = new VirtualMachineInstance(name_Camel, vmt);
				VMIs.add(VMInst);
				return true;
			}else
				return false;
		}
	}
	
	public boolean deleteVMI(String name_Camel){
		boolean status = false;
		synchronized(VMIs){
			ListIterator<VirtualMachineInstance> it = VMIs.listIterator();
			while (it.hasNext()) {
				VirtualMachineInstance vmi = it.next();
				if(vmi.getVMIname().equalsIgnoreCase(name_Camel)){
					it.remove();
					status = true;
				}
			}
		}
		return status;
	}
	
	public boolean setVMIID(String name_Camel, String id){
		
		synchronized(VMIs){
			for(VirtualMachineInstance vmi : VMIs)
				if(vmi.getVMIname().equalsIgnoreCase(name_Camel) && vmi.getId()==null){
					vmi.setId(id);
					return true;
				}
		}
		return false;
	}
	
	public VirtualMachineInstance getEntityVMInstance(String name_Camel){
		synchronized(VMIs){
			for(VirtualMachineInstance VMI : VMIs)
				if(VMI.getVMIname().equalsIgnoreCase(name_Camel) && VMI.getId()!=null)
					return VMI;
			return null;
		}
	}
	
	public String getEntityVMIid(String name_Camel){
		synchronized(VMIs){
			for(VirtualMachineInstance VMI : VMIs)
				if(VMI.getVMIname().equalsIgnoreCase(name_Camel) && VMI.getId()!=null)
					return VMI.getId();
			return null;
		}
	}
	
	//Stores all the Lifecycle & Application Comp Instance entities created
	LinkedList<LCAppComponent> LCACs = new LinkedList<CamelExecwareMapping.LCAppComponent>();
	
	public boolean addLCAC(String intCompId_Camel, String download, String install, String start, String stop, Application app, VirtualMachineTemplate vmt){
		boolean status = false;
		LCAppComponent LCAComp;
		synchronized (LCACs) {
			for(LCAppComponent lcac : LCACs){
				if(lcac.getLCACType().equalsIgnoreCase(intCompId_Camel) && app != null && vmt != null){
					status = true;
					LCAComp = lcac;
					LOGGER.log(Level.WARNING, "Lifecycle Component " + intCompId_Camel +" already exists");
				}
			}
			
			if(app == null){
				LOGGER.log(Level.WARNING, "Application is NULL! Could not create " + intCompId_Camel);
				return status;
			}
			if(vmt == null){
				LOGGER.log(Level.WARNING, "VM Type is NULL! Could not create " + intCompId_Camel);
				return status;
			}
			
			if(!status && app != null && vmt != null){
				LCAComp = new LCAppComponent(intCompId_Camel, download, install, start, stop, app, vmt);
				status = LCACs.add(LCAComp);
				LOGGER.log(Level.INFO, "LC Comp Name " + intCompId_Camel + " status " + status + " app " + app.getName_Camel() + " vmt " + vmt.getVMType());
			}
		}
		return status;
	}
	
	public boolean addLCAC(String intCompId_Camel, String preInstall, String postInstall, String start, Application app, VirtualMachineTemplate vmt){
		boolean status = false;
		LCAppComponent LCAComp;
		synchronized (LCACs) {
			for(LCAppComponent lcac : LCACs){
				if(lcac.getLCACType().equalsIgnoreCase(intCompId_Camel) && app != null && vmt != null){
					status = true;
					LCAComp = lcac;
					LOGGER.log(Level.WARNING, "Lifecycle Component " + intCompId_Camel +" already exists");
				}
			}
			
			if(app == null){
				LOGGER.log(Level.WARNING, "Application is NULL! Could not create " + intCompId_Camel);
				return status;
			}
			if(vmt == null){
				LOGGER.log(Level.WARNING, "VM Type is NULL! Could not create " + intCompId_Camel);
				return status;
			}
			
			if(!status && app != null && vmt != null){
				LCAComp = new LCAppComponent(intCompId_Camel, preInstall, postInstall, start, app, vmt);
				status = LCACs.add(LCAComp);
				LOGGER.log(Level.INFO, "LC Comp Name " + intCompId_Camel + " status " + status + " app " + app.getName_Camel() + " vmt " + vmt.getVMType());
			}
		}
		return status;
	}
	
	
	public boolean setLCACID(String intCompId_Camel, String id){
		synchronized (LCACs) {
			for(LCAppComponent lcac : LCACs){
				if(lcac.getLCACType().equalsIgnoreCase(intCompId_Camel) && lcac.getLifeCycleCompId()==null){
					lcac.setLifeCycleCompId(id);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean setAppCompID(String intCompId_Camel, String id){
		synchronized (LCACs) {
			for(LCAppComponent lcac : LCACs){
				if(lcac.getLCACType().equalsIgnoreCase(intCompId_Camel) && lcac.getAppCompID()==null){
					lcac.setAppComponentId(id);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean updateLCAC(String intCompId_Camel, String download, String install, String start, String stop){
		boolean status = false;
		LCAppComponent lcac = getEntityLCAppComponent(intCompId_Camel);
		synchronized (LCACs) {
			status = lcac.setLCParams(download, install, start, stop);
		}
		return status;
	}
	
	public boolean updateACAC(String intCompId_Camel, Application app, VirtualMachineTemplate vmt){
		boolean status = false;
		LCAppComponent lcac = getEntityLCAppComponent(intCompId_Camel);
		synchronized (LCACs) {
			status = lcac.setACParams(app, vmt);
		}
		return status;
	}
	
	public LCAppComponent getEntityLCAppComponent(String intCompId_Camel){
		synchronized (LCACs) {
			for(LCAppComponent lcac : LCACs){
				if(lcac.getLCACType().equalsIgnoreCase(intCompId_Camel) && lcac.getAppCompID()!=null){
					return lcac;
				}
			}
		}
		return null;
	}
	
	public String getAppCompID(String intCompId_Camel){
		synchronized (LCACs) {
			for(LCAppComponent lcac : LCACs)
				if(lcac.getLCACType().equalsIgnoreCase(intCompId_Camel))
					return lcac.getAppCompID();
		}
		return null;
	}
	
	
	public String getLCCompID(String intCompId_Camel){
		synchronized (LCACs) {
			for(LCAppComponent lcac : LCACs)
				if(lcac.getLCACType().equalsIgnoreCase(intCompId_Camel))
					return lcac.getLifeCycleCompId();
		}
		return null;
	}
	
	public boolean deleteLCAC(String intCompId_Camel){
		boolean status = false;
		synchronized (LCACs) {
			
			ListIterator<LCAppComponent> it = LCACs.listIterator();
			while(it.hasNext()){
				LCAppComponent lcac = it.next();
				if(lcac.getLCACType().equalsIgnoreCase(intCompId_Camel)){
					it.remove();
					status = true;
				}
			}
		}
		return status;
	}
	
	
	//LCAppComponent appCompPrv;
	//Instance provider; 
	
	//LCAppComponent appCompCons;
	//Instance consumer;
	
	//Communication comm;//between the two AppComponents
	//CommChannel commChan;//between the two Instances

	private class LCAppComponent{

		String id;//ExecWare App Comp ID
		
		//Lifecycle Comp parameters
		String lifeCycleCompId_Exec;
		String intCompId_Camel;//mapped to Lifecycle Comp name in Execware
	    String download;//"wget www.mysql.de"
	    String install;//"install.sh"
	    String start;//"start.sh"
	    String stop;//"stop.sh"
	    //new changes for the demo
	    String preInstall;
	    String postInstall;
		
		//VirtualMachineTemplate VMTemplate_Exec;
		//String appID_Exec;
		//String vmtID_Exec;
		
		Application app;
		VirtualMachineTemplate vmt;
		
		public LCAppComponent(String intCompId_Camel, String download, String install, String start, String stop, Application app, VirtualMachineTemplate vmt){
			this.intCompId_Camel = intCompId_Camel;
			this.download = download;
			this.install = install;
			this.start = start;
			this.stop = stop;
			this.lifeCycleCompId_Exec = null;
			this.id = null;
			this.app = app;
			this.vmt = vmt;
		}
		
		public LCAppComponent(String intCompId_Camel, String preInstall, String postInstall, String start, Application app, VirtualMachineTemplate vmt){
			this.intCompId_Camel = intCompId_Camel;
			this.preInstall = preInstall;
			this.postInstall = postInstall;
			this.start = start;
			this.lifeCycleCompId_Exec = null;
			this.id = null;
			this.app = app;
			this.vmt = vmt;
		}
		
/*		public LCAppComponent(String lifeCycleCompId_Exec, String intCompId_Camel, VirtualMachineTemplate VMTemplate_Exec) {
			this.lifeCycleCompId_Exec = lifeCycleCompId_Exec;
			this.intCompId_Camel = intCompId_Camel;
			this.VMTemplate_Exec = VMTemplate_Exec;
		}*/
		
		String getAppCompID(){return this.id;}
		
		String getLifeCycleCompId(){return this.lifeCycleCompId_Exec;}
		
		String getLCACType(){return this.intCompId_Camel;}
		
		String getVMTemplateType(){return this.vmt.getVMType();}
		
		boolean setLCParams(String download, String install, String start, String stop){
			this.download = download;
			this.install = install;
			this.start = start;
			this.stop = stop;
			return true;
		}
		
		boolean setACParams(Application app, VirtualMachineTemplate vmt){
			this.app = app;
			this.vmt = vmt;
			return true;
		}
		
		/*private void setLifeCycleCompId(String intCompId_Camel, String lifeCycleCompId_Exec){
			this.lifeCycleCompId_Exec = lifeCycleCompId_Exec;
			this.intCompId_Camel = intCompId_Camel;
		}*/
		
/*		private void setAppComp(String applicationCompId_Exec, String applicationId_Exec, String VMTemplateId_Exec){
			appCompPrv.id = applicationCompId_Exec;
			//appCompPrv.applicationId_Exec = applicationId_Exec;
			appCompPrv.VMTemplateId_Exec = VMTemplateId_Exec;
		}*/

		private void setAppComponentId(String id){
			this.id = id;
		}
		
		private void setLifeCycleCompId(String lifeCycleCompId_Exec){
			this.lifeCycleCompId_Exec = lifeCycleCompId_Exec;
		}
		
		private void setApplicationParams(String ApplicationId_Exec, String AppName_Camel){
			appli.setApplicationParams(ApplicationId_Exec, AppName_Camel);
		}
	}
	
	private class VirtualMachineTemplate{
		String id;
		String cloud;
		String imageName;
		String imageID;
		String location;
		String hardware;
		
		String name_Camel;
		
		public VirtualMachineTemplate(String name_Camel) {
			this.name_Camel = name_Camel;
			this.id = null;
		}
		
		String getId(){return this.id;}
		
		String getVMType(){return name_Camel;}
		
		String getCloud(){
			return cloud;
		}
		
		String getImageName(){
			return imageName;
		}
		
		String getImageID(){
			return imageID;
		}
		
		String getLocation(){
			return location;
		}
		
		String getHardware(){
			return hardware;
		}
		
		void setAllIDs(String id, String cloud, String image, String location, String hardware){
			setId(id);
			setCloud(cloud);
			setImageID(image);
			setLocation(location);
			setHardware(hardware);
		}
		
		void setId(String id){
			this.id = id;
		}
		
		void setCloud(String cloud){
			this.cloud = cloud;
		}
		
		void setImageName(String imageName){
			this.imageName = imageName;
		}
		
		void setImageID(String imageID){
			this.imageID = imageID;
		}
		
		void setLocation(String location){
			this.location = location;
		}
		
		void setHardware(String hardware){
			this.hardware = hardware;
		}
	}
	
	private class VirtualMachineInstance{
		String id;
		/*String cloud;
		String image;
		String location;
		String hardware;*/
		
		VirtualMachineTemplate vmt;
		String name_Camel;
		
		public VirtualMachineInstance(String name_Camel, VirtualMachineTemplate vmt) {
			this.name_Camel = name_Camel;
			this.vmt = vmt;
			this.id = null;
		}
		
		String getId(){return this.id;}
		
		String getVMIname(){return this.name_Camel;}
		
		String getCloud(){
			return vmt.getCloud();
		}
		
		String getImage(){
			return vmt.getImageName();
		}
		
		String getLocation(){
			return vmt.getLocation();
		}
		
		String getHardware(){
			return vmt.getHardware();
		}
		
		void setId(String id){
			this.id = id;
		}
		
		/*void setAll(String cloud, String image, String location, String hardware){
			setCloud(cloud);
			setImage(image);
			setLocation(location);
			setHardware(hardware);
		}
		
		void setCloud(String cloud){
			vmt.setCloud(cloud);
		}
		
		void setImage(String image){
			vmt.setImageName(image);
		}
		
		void setLocation(String location){
			vmt.setLocation(location);
		}
		
		void setHardware(String hardware){
			vmt.setHardware(hardware);
		}*/
	}
	
	private class Application{
		String id;//ExecWare id
		
		String name_Camel;
		
		Application(String applicationId_Exec, String name_Camel){
			this.id = applicationId_Exec;
			this.name_Camel = name_Camel;
		}
		
		private void setApplicationParams(String applicationId_Exec, String name_Camel){
			this.id = applicationId_Exec;
			this.name_Camel = name_Camel;
		}
		
		private void setApplicationName(String name_Camel){
			this.name_Camel = name_Camel;
		}
		
		public String getApplicationId_Exec(){
			return id;
		}
		
		public String getName_Camel(){
			return name_Camel;
		}
	}
	
	private class ApplicationInstance{
		String id;//ExecWare id
		
		String name_Camel;
		
		//Application app=appli;
		Application app;
		
		public ApplicationInstance() {
			// TODO Auto-generated constructor stub
		}
		
		public ApplicationInstance(Application app, String applicationInstanceId_Exec, String name_Camel){
			this.id = applicationInstanceId_Exec;
			this.name_Camel = name_Camel;
			this.app = app;
		}
		
		String getAppInstName(){return name_Camel;}
		
		String getApplicationInstanceId_Exec(){return this.id;}
		
		void setApplicationInstanceName(String name_Camel){this.name_Camel = name_Camel;}
	}
	
/*	//Stores all the Communication entities created
	LinkedList<Communication> Comms = new LinkedList<CamelExecwareMapping.Communication>();
	
	public boolean addComm(String CommType_Camel, LCAppComponent appCompPrv, LCAppComponent appCompCons, String provPort, String reqPort){
		boolean status = false;
		Communication commEntity;
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(CommType_Camel) && appCompPrv != null && appCompCons != null && provPort != null && reqPort != null){
					status = true;
					commEntity = comm;
					LOGGER.log(Level.WARNING, "Communication Component " + CommType_Camel +" already exists");
				}
			}
			
			if(appCompPrv == null){
				LOGGER.log(Level.WARNING, "Provider Application Component is NULL! Could not create " + CommType_Camel);
				return status;
			}
			if(appCompCons == null){
				LOGGER.log(Level.WARNING, "Consumer Application Component is NULL! Could not create " + CommType_Camel);
				return status;
			}
			if(provPort == null){
				LOGGER.log(Level.WARNING, "Communication provider port is NULL! Could not create " + CommType_Camel);
				return status;
			}
			
			if(reqPort == null){
				LOGGER.log(Level.WARNING, "Communication consumer port is NULL! Could not create " + CommType_Camel);
				return status;
			}
			
			if(!status && appCompPrv != null && appCompCons != null && provPort != null && reqPort != null){
				commEntity = new Communication(CommType_Camel, appCompPrv, appCompCons, provPort, reqPort);
				status = Comms.add(commEntity);
				LOGGER.log(Level.INFO, "Communication Name " + CommType_Camel + " Prov: " + appCompPrv.getLCACType() + " Cons: " + appCompCons.getLCACType() + " provPort " + provPort + " consPort " + reqPort);
			}
		}
		return status;
	}
	
	public boolean setCommID(String commId_Camel, String id){
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(commId_Camel) && comm.getCommID()==null){
					comm.setCommId(id);
					return true;
				}
			}
		}
		return false;
	}
	
	public Communication getEntityCommunication(String commId_Camel){
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(commId_Camel) && comm.getCommID()!=null){
					return comm;
				}
			}
		}
		return null;
	}
	
	public String getCommID(String commId_Camel){
		synchronized (Comms) {
			for(Communication comm : Comms)
				if(comm.getCommType().equalsIgnoreCase(commId_Camel))
					return comm.getCommID();
		}
		return null;
	}

	
	private class Communication{

		String id;
		String provPort;
		String consPort;
		
		LCAppComponent appliCompPrv;
		LCAppComponent appliCompCons;
		
		String CommType_Camel;
		//String providerPortNumber_Camel;
		
		public Communication(String CommType_Camel, LCAppComponent appCompPrv, LCAppComponent appCompCons, String provPort, String consPort){
			this.CommType_Camel = CommType_Camel;
			this.appliCompPrv = appCompPrv;
			this.appliCompCons = appCompCons;
			this.provPort = provPort;
			this.consPort = consPort;
			this.id = null;
		}
		
		public Communication(LCAppComponent appCompPrv, LCAppComponent appCompCons) {
			this.appliCompPrv = appCompPrv;
			this.appliCompCons = appCompCons;
		}
		
		private void setCommnicationCamelParams(String CommType_Camel, String providerPortNumber_Camel){
			this.CommType_Camel = CommType_Camel;
			//this.providerPortNumber_Camel = providerPortNumber_Camel;
		}
		
		private void setCommunicationParams(String id, String provPort, String consPort){
			this.id = id;
			this.provPort = provPort;
			this.consPort = consPort;
		}
		
		private void setCommId(String id){
			this.id = id;
		}
		
		private String getCommType(){return CommType_Camel;}
		
		private String getCommID(){return this.id;}
	}*/
	
	//Stores all the Communication entities created
	LinkedList<Communication> Comms = new LinkedList<CamelExecwareMapping.Communication>();
	
	public boolean addComm(String CommType_Camel, ProvidedPort prvPort, RequiredPort reqPort){
		boolean status = false;
		Communication commEntity;
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(CommType_Camel) && prvPort != null && reqPort != null){
					status = true;
					commEntity = comm;
					LOGGER.log(Level.WARNING, "Communication Component " + CommType_Camel +" already exists");
				}
			}
			
			if(prvPort == null){
				LOGGER.log(Level.WARNING, "Communication provider port entity is NULL! Could not create " + CommType_Camel);
				return status;
			}
			
			if(reqPort == null){
				LOGGER.log(Level.WARNING, "Communication consumer port entity is NULL! Could not create " + CommType_Camel);
				return status;
			}
			
			if(!status && prvPort != null && reqPort != null){
				commEntity = new Communication(CommType_Camel, prvPort, reqPort);
				status = Comms.add(commEntity);
				LOGGER.log(Level.INFO, "Communication Name " + CommType_Camel + " provPort " + prvPort.getProvidedPortName() + " consPort " + reqPort.getRequiredPortName());
			}
		}
		return status;
	}
	
	public boolean addOrphanComm(String CommType_Camel, ProvidedPort prvPort){
		boolean status = false;
		Communication commEntity;
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(CommType_Camel) && prvPort != null){
					status = true;
					commEntity = comm;
					LOGGER.log(Level.WARNING, "Communication Component " + CommType_Camel +" already exists");
				}
			}
			
			if(prvPort == null){
				LOGGER.log(Level.WARNING, "Communication provider port entity is NULL! Could not create " + CommType_Camel);
				return status;
			}
			
			if(!status && prvPort != null){
				commEntity = new Communication(CommType_Camel, prvPort, null);
				status = Comms.add(commEntity);
				LOGGER.log(Level.INFO, "Communication Name " + CommType_Camel + " provPort " + prvPort.getProvidedPortName() + " consPort NONE");
			}
		}
		return status;
	}
	
	public boolean setCommID(String commId_Camel, String id){
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(commId_Camel) && comm.getCommID()==null){
					comm.setCommId(id);
					return true;
				}
			}
		}
		return false;
	}
	
	public Communication getEntityCommunication(String commId_Camel){
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(commId_Camel) && comm.getCommID()!=null){
					return comm;
				}
			}
		}
		return null;
	}
	
	public String getCommProvPortName(String commId_Camel){
		String prvPortName = "";
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(commId_Camel) && comm.getCommID()!=null){
					//return comm;
					ProvidedPort prvPort = comm.getProvPort();
					if(prvPort == null)
						LOGGER.log(Level.INFO, "Provided port NULL for Communication Component " + commId_Camel);
					else
						prvPortName = prvPort.getProvidedPortName();
				}
			}
		}
		return prvPortName;
	}
	
	public String getOrphanCommProvPortName(String commId_Camel){
		String prvPortName = "";
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(commId_Camel) && comm.getCommID().equalsIgnoreCase("0")){
					//return comm;
					ProvidedPort prvPort = comm.getProvPort();
					if(prvPort == null)
						LOGGER.log(Level.INFO, "Provided port NULL for Communication Component " + commId_Camel);
					else
						prvPortName = prvPort.getProvidedPortName();
				}
			}
		}
		return prvPortName;
	}
	
	public String getCommReqPortName(String commId_Camel){
		String reqPortName = "";
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(commId_Camel) && comm.getCommID()!=null){
					//return comm;
					RequiredPort reqPort = comm.getReqPort();
					if(reqPort == null)
						LOGGER.log(Level.INFO, "Provided port NULL for Communication Component " + commId_Camel);
					else
						reqPortName = reqPort.getRequiredPortName();
				}
			}
		}
		return reqPortName;
	}
	
	public boolean updateComm(String CommType_Camel, ProvidedPort prvPort, RequiredPort reqPort){
		boolean status = false;
		Communication commEntity = null;
		synchronized (Comms) {
			for(Communication comm : Comms){
				if(comm.getCommType().equalsIgnoreCase(CommType_Camel) && prvPort != null && reqPort != null){
					status = true;
					commEntity = comm;
					LOGGER.log(Level.INFO, "Found Communication Component " + CommType_Camel +" already exists");
				}
			}
			
			if(prvPort == null){
				LOGGER.log(Level.WARNING, "Communication provider port entity is NULL! Could not create " + CommType_Camel);
				return status;
			}
			
			if(reqPort == null){
				LOGGER.log(Level.WARNING, "Communication consumer port entity is NULL! Could not create " + CommType_Camel);
				return status;
			}
			
			if(status && prvPort != null && reqPort != null){
				commEntity.setCommunicationParams(prvPort, reqPort);
				LOGGER.log(Level.INFO, "Updated Communication Name " + CommType_Camel + " provPort " + prvPort.getProvidedPortName() + " consPort " + reqPort.getRequiredPortName());
			}
		}
		return status;
	}
	
	public String getCommID(String commId_Camel){
		synchronized (Comms) {
			for(Communication comm : Comms)
				if(comm.getCommType().equalsIgnoreCase(commId_Camel))
					return comm.getCommID();
		}
		return null;
	}
	
	public boolean deleteComm(String commId_Camel){
		boolean status = false;
		
		synchronized (Comms) {
			ListIterator<Communication> it = Comms.listIterator();
			while(it.hasNext()){
				Communication comm = it.next();
				if(comm.getCommType().equalsIgnoreCase(commId_Camel)){
					it.remove();
					status = true;
				}
			}
		}
		return status;
	}
	
	private class Communication{

		String id;
		String provPort;
		String consPort;
		
		LCAppComponent appliCompPrv;
		LCAppComponent appliCompCons;
		
		ProvidedPort prvPort;
		RequiredPort reqPort;
		
		String CommType_Camel;
		//String providerPortNumber_Camel;
		
		public Communication(String CommType_Camel, ProvidedPort prvPort, RequiredPort reqPort){
			this.CommType_Camel = CommType_Camel;
			this.prvPort = prvPort;
			this.reqPort = reqPort;
			this.id = null;
		}
		
		public Communication(String CommType_Camel, LCAppComponent appCompPrv, LCAppComponent appCompCons, String provPort, String consPort){
			this.CommType_Camel = CommType_Camel;
			this.appliCompPrv = appCompPrv;
			this.appliCompCons = appCompCons;
			this.provPort = provPort;
			this.consPort = consPort;
			this.id = null;
		}
		
		private void setCommId(String id){
			this.id = id;
		}
		
		private String getCommType(){return CommType_Camel;}
		
		private String getCommID(){return this.id;}
		
		private ProvidedPort getProvPort(){return this.prvPort;}
		
		private RequiredPort getReqPort(){return this.reqPort;}
		
		public boolean setCommunicationParams(ProvidedPort prvPort, RequiredPort reqPort){
			this.prvPort = prvPort;
			this.reqPort = reqPort;
			return true;
		}
	}
	
	//Stores all the Provided Port entities created
	LinkedList<ProvidedPort> provPorts = new LinkedList<CamelExecwareMapping.ProvidedPort>();
	
	public boolean existsProvPort(String providedPortName_Camel){
		boolean status = false;
		
		synchronized (provPorts) {
			for(ProvidedPort pPort : provPorts){
				if(pPort.getProvidedPortName().equalsIgnoreCase(providedPortName_Camel)){
					status = true;
				}
			}
		}
		return status;
	}
	
	public boolean addProvPort(String providedPortName_Camel, LCAppComponent appCompPrv, String provPortNum){
		boolean status = false;
		
		ProvidedPort provPortEntity;
		
		synchronized (provPorts) {
			for(ProvidedPort pPort : provPorts){
				if(pPort.getProvidedPortName().equalsIgnoreCase(providedPortName_Camel) && appCompPrv != null && provPortNum != null){
					status = true;
					provPortEntity = pPort;
					LOGGER.log(Level.WARNING, "Provided Port Component " + providedPortName_Camel +" already exists");
				}
			}
			
			if(appCompPrv == null){
				LOGGER.log(Level.WARNING, "Provider Application Component is NULL! Could not create " + providedPortName_Camel);
				return status;
			}
			
			if(provPortNum == null){
				LOGGER.log(Level.WARNING, "Communication provider port is NULL! Could not create " + providedPortName_Camel);
				return status;
			}
			
			if(!status && appCompPrv != null && provPortNum != null){
				provPortEntity = new ProvidedPort(providedPortName_Camel, appCompPrv, provPortNum);
				status = provPorts.add(provPortEntity);
				LOGGER.log(Level.INFO, "Provided Port Name " + providedPortName_Camel + " Prov: " + appCompPrv.getLCACType() + " provPort " + provPortNum);
			}
		}
		return status;
	}
	
	public boolean updateProvPort(String providedPortName_Camel, LCAppComponent appCompPrv, String provPortNum){
		boolean status = false;
		
		ProvidedPort provPortEntity = null;
		
		synchronized (provPorts) {
			for(ProvidedPort pPort : provPorts){
				if(pPort.getProvidedPortName().equalsIgnoreCase(providedPortName_Camel) && appCompPrv != null && provPortNum != null){
					status = true;
					provPortEntity = pPort;
					LOGGER.log(Level.INFO, "Provided Port Component " + providedPortName_Camel +" located");
				}
			}
			
			if(appCompPrv == null){
				LOGGER.log(Level.WARNING, "Provider Application Component is NULL! Could not update " + providedPortName_Camel);
				return status;
			}
			
			if(provPortNum == null){
				LOGGER.log(Level.WARNING, "Communication provider port is NULL! Could not update " + providedPortName_Camel);
				return status;
			}
			
			if(status && appCompPrv != null && provPortNum != null){
				status = status && provPortEntity.setProvidedPortParams(appCompPrv, provPortNum);
				LOGGER.log(Level.INFO, "Provided Port Name " + providedPortName_Camel + " Prov: " + appCompPrv.getLCACType() + " provPort " + provPortNum);
			}
		}
		return status;
	}
	
	public boolean setProvPortID(String providedPortName_Camel, String id){
		synchronized (provPorts) {
			for(ProvidedPort pPort : provPorts){
				if(pPort.getProvidedPortName().equalsIgnoreCase(providedPortName_Camel) && pPort.getProvidedPortId()==null){
					pPort.setProvidedPortId(id);
					return true;
				}
			}
		}
		return false;
	}
	
	public ProvidedPort getEntityProvPort(String providedPortName_Camel){
		synchronized (provPorts) {
			for(ProvidedPort pPort : provPorts){
				if(pPort.getProvidedPortName().equalsIgnoreCase(providedPortName_Camel) && pPort.getProvidedPortId()!=null){
					return pPort;
				}
			}
		}
		return null;
	}
	
	public String getProvPortID(String providedPortName_Camel){
		synchronized (provPorts) {
			for(ProvidedPort pPort : provPorts)
				if(pPort.getProvidedPortName().equalsIgnoreCase(providedPortName_Camel) && pPort.getProvidedPortId()!=null)
					return pPort.getProvidedPortId();
		}
		return null;
	}
	
	public boolean deleteProvPort(String providedPortName_Camel){
		boolean status = false;
		
		synchronized (provPorts) {
			
			ListIterator<ProvidedPort> it = provPorts.listIterator();
			
			while(it.hasNext()){
				ProvidedPort pPort = it.next();
				if(pPort.getProvidedPortName().equalsIgnoreCase(providedPortName_Camel) && pPort.getProvidedPortId()!=null){
					it.remove();status = true;
				}
			}
		}
		return status;
	}
	
	
	private class ProvidedPort{
		String id;
		String provPortNum;
		
		LCAppComponent appliCompPrv;
		String providedPortName_Camel;
		
		public ProvidedPort(String providedPortName_Camel, LCAppComponent appCompPrv, String provPortNum){
			this.providedPortName_Camel = providedPortName_Camel;
			this.appliCompPrv = appCompPrv;
			this.provPortNum = provPortNum;
			this.id = null;
		}
		
		public boolean setProvidedPortParams(LCAppComponent appCompPrv, String provPortNum){
			this.appliCompPrv = appCompPrv;
			this.provPortNum = provPortNum;
			return true;
		}
		
		public void setProvidedPortId(String id){
			this.id = id;
		}
		
		public String getProvidedPortName(){return this.providedPortName_Camel;}
		
		public String getProvidedPortId(){return this.id;}
	}
	
	
	//Stores all the Required Port entities created
	LinkedList<RequiredPort> reqPorts = new LinkedList<CamelExecwareMapping.RequiredPort>();
	
	public boolean existsReqPort(String requiredPortName_Camel){
		boolean status = false;
		
		synchronized (reqPorts) {
			for(RequiredPort rPort : reqPorts){
				if(rPort.getRequiredPortName().equalsIgnoreCase(requiredPortName_Camel)){
					status = true;
				}
			}
		}
		return status;
	}
	
	public boolean addReqPort(String requiredPortName_Camel, LCAppComponent appCompCons, String reqPortNum, String isMandatory, String requiredPortstartCmd){
		boolean status = false;
		
		RequiredPort reqPortEntity;
		
		synchronized (reqPorts) {
			for(RequiredPort rPort : reqPorts){
				if(rPort.getRequiredPortName().equalsIgnoreCase(requiredPortName_Camel) && appCompCons != null && reqPortNum != null){
					status = true;
					reqPortEntity = rPort;
					LOGGER.log(Level.WARNING, "Required Port Component " + requiredPortName_Camel +" already exists");
				}
			}
			
			if(appCompCons == null){
				LOGGER.log(Level.WARNING, "Required Application Component is NULL! Could not create " + requiredPortName_Camel);
				return status;
			}
			
			if(reqPortNum == null){
				LOGGER.log(Level.WARNING, "Communication consumer port is NULL! Could not create " + requiredPortName_Camel);
				return status;
			}
			
			if(!status && appCompCons != null && reqPortNum != null){
				reqPortEntity = new RequiredPort(requiredPortName_Camel, appCompCons, reqPortNum, isMandatory, requiredPortstartCmd);
				status = reqPorts.add(reqPortEntity);
				LOGGER.log(Level.INFO, "Required Port Name " + requiredPortName_Camel + " Cons: " + appCompCons.getLCACType() + " reqPort " + reqPortNum);
			}
		}
		return status;
	}
	
	public boolean updateReqPort(String requiredPortName_Camel, LCAppComponent appCompCons, String reqPortNum, String isMandatory, String requiredPortstartCmd){
		boolean status = false;
		
		RequiredPort reqPortEntity = null;
		
		synchronized (reqPorts) {
			for(RequiredPort rPort : reqPorts){
				if(rPort.getRequiredPortName().equalsIgnoreCase(requiredPortName_Camel) && appCompCons != null && reqPortNum != null){
					status = true;
					reqPortEntity = rPort;
					LOGGER.log(Level.INFO, "Port Component " + requiredPortName_Camel +" already exists");
				}
			}
			
			if(appCompCons == null){
				LOGGER.log(Level.WARNING, "Required Application Component is NULL! Could not create " + requiredPortName_Camel);
				return status;
			}
			
			if(reqPortNum == null){
				LOGGER.log(Level.WARNING, "Communication consumer port is NULL! Could not create " + requiredPortName_Camel);
				return status;
			}
			
			if(status && appCompCons != null && reqPortNum != null && isMandatory != null){
				status = status && reqPortEntity.setRequiredPort(appCompCons, reqPortNum, isMandatory, requiredPortstartCmd);
				LOGGER.log(Level.INFO, "Required Port Name " + requiredPortName_Camel + " Cons: " + appCompCons.getLCACType() + " reqPort " + reqPortNum);
			}
		}
		return status;
	}
	
	public boolean setReqPortID(String requiredPortName_Camel, String id){
		synchronized (reqPorts) {
			for(RequiredPort rPort : reqPorts){
				if(rPort.getRequiredPortName().equalsIgnoreCase(requiredPortName_Camel) && rPort.getRequiredPortId()==null){
					rPort.setRequiredPortId(id);
					return true;
				}
			}
		}
		return false;
	}
	
	public RequiredPort getEntityReqPort(String requiredPortName_Camel){
		synchronized (reqPorts) {
			for(RequiredPort rPort : reqPorts){
				if(rPort.getRequiredPortName().equalsIgnoreCase(requiredPortName_Camel) && rPort.getRequiredPortId()!=null){
					return rPort;
				}
			}
		}
		return null;
	}
	
	public String getReqPortID(String requiredPortName_Camel){
		synchronized (reqPorts) {
			for(RequiredPort rPort : reqPorts)
				if(rPort.getRequiredPortName().equalsIgnoreCase(requiredPortName_Camel) && rPort.getRequiredPortId()!=null)
					return rPort.getRequiredPortId();
		}
		return null;
	}
	
	public boolean deleteReqPort(String requiredPortName_Camel){
		boolean status = false;
		
		synchronized (reqPorts) {
			
			ListIterator<RequiredPort> it = reqPorts.listIterator();
			
			while(it.hasNext()){
				RequiredPort rPort = it.next();
				if(rPort.getRequiredPortName().equalsIgnoreCase(requiredPortName_Camel) && rPort.getRequiredPortId()!=null){
					it.remove();
					status = true;
				}
			}
		}
		return status;
	}
	
	private class RequiredPort{
		String id;
		String reqPortNum;
		String isMandatory;
		String requiredPortstartCmd;
		
		LCAppComponent appliCompCons;
		String requiredPortName_Camel;
		
		public RequiredPort(String requiredPortName_Camel, LCAppComponent appCompCons, String reqPortNum, String isMandatory, String requiredPortstartCmd){
			this.requiredPortName_Camel = requiredPortName_Camel;
			this.appliCompCons = appCompCons;
			this.reqPortNum = reqPortNum;
			this.isMandatory = isMandatory;
			this.requiredPortstartCmd = requiredPortstartCmd;
			this.id = null;
		}
		
		public boolean setRequiredPort(LCAppComponent appCompCons, String reqPortNum, String isMandatory, String requiredPortstartCmd){
			this.appliCompCons = appCompCons;
			this.reqPortNum = reqPortNum;
			this.isMandatory = isMandatory;
			this.requiredPortstartCmd = requiredPortstartCmd;
			return true;
		}
		
		public void setRequiredPortId(String id){
			this.id = id;
		}
		
		public String getRequiredPortName(){return this.requiredPortName_Camel;}
		
		public String getRequiredPortId(){return this.id;}
	}
	
	
	//Stores all the Component Instance entities created
	LinkedList<ComponentInstance> CompInsts = new LinkedList<CamelExecwareMapping.ComponentInstance>();
	
	public boolean addCompInst(String compInstName_Camel, ApplicationInstance appInstance, LCAppComponent appComp, VirtualMachineInstance vmi){
		boolean status = false;
		ComponentInstance compInstEntity;
		synchronized (CompInsts) {
			for(ComponentInstance compInst : CompInsts){
				if(compInst.getCompInstType().equalsIgnoreCase(compInstName_Camel) && appInstance != null && appComp != null && vmi != null){
					status = true;
					compInstEntity = compInst;
					LOGGER.log(Level.WARNING, "Component Instance " + compInstName_Camel +" already exists");
				}
			}
			
			if(appInstance == null){
				LOGGER.log(Level.WARNING, "Application Instance Component is NULL! Could not create " + compInstName_Camel);
				return status;
			}
			if(appComp == null){
				LOGGER.log(Level.WARNING, "Application Component is NULL! Could not create " + compInstName_Camel);
				return status;
			}
			if(vmi == null){
				LOGGER.log(Level.WARNING, "VM Instance is NULL! Could not create " + compInstName_Camel);
				return status;
			}
			
			if(!status && appInstance != null && appComp != null && vmi != null){
				compInstEntity = new ComponentInstance(compInstName_Camel, appInstance, appComp, vmi);
				status = CompInsts.add(compInstEntity);
				LOGGER.log(Level.INFO, "Component Instance Name " + compInstName_Camel + " AppInst: " + appInstance.getAppInstName() + " AppComp: " + appComp.getLCACType() + " VM Instance " + vmi.getVMIname());
			}
		}
		return status;
	}
	
	public ComponentInstance getEntityComponentInstance(String compInstName_Camel){
		synchronized (CompInsts) {
			for(ComponentInstance compInst : CompInsts){
				if(compInst.getCompInstType().equalsIgnoreCase(compInstName_Camel) && compInst.getCompInstID()!=null){
					return compInst;
				}
			}
		}
		return null;
	}
	
	public String getCompInstID(String intCompId_Camel){
		synchronized (CompInsts) {
			for(ComponentInstance compInst : CompInsts)
				if(compInst.getCompInstType().equalsIgnoreCase(intCompId_Camel))
					return compInst.getCompInstID();
		}
		return null;
	}
	
	public boolean setCompInstID(String intCompId_Camel, String id){
		synchronized (CompInsts) {
			for(ComponentInstance compInst : CompInsts){
				if(compInst.getCompInstType().equalsIgnoreCase(intCompId_Camel) && compInst.getCompInstID()==null){
					compInst.setId(id);
					LOGGER.log(Level.INFO, "Component Instance name: " + intCompId_Camel + " id: " + id);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean updateCompInst(String intCompId_Camel, ApplicationInstance appInstance, LCAppComponent appComp, VirtualMachineInstance vmi){
		synchronized (CompInsts) {
			for(ComponentInstance compInst : CompInsts){
				if(compInst.getCompInstType().equalsIgnoreCase(intCompId_Camel) && compInst.getCompInstID()==null){
					compInst.setComponentInstanceParams(appInstance, appComp, vmi);
					LOGGER.log(Level.INFO, "Updated Component Instance : " + intCompId_Camel);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean deleteCompInst(String intCompId_Camel){
		boolean status = false;
		ListIterator<ComponentInstance> it = CompInsts.listIterator();
		
		synchronized (CompInsts) {
			
			while(it.hasNext()){
				ComponentInstance compInst = it.next();
				if(compInst.getCompInstType().equalsIgnoreCase(intCompId_Camel)){
					it.remove();
					LOGGER.log(Level.INFO, "Deleted Component Instance : " + intCompId_Camel);
					status = true;
				}			
			}
		}
		return status;
	}
	
	
	private class ComponentInstance{

		String id;

		ApplicationInstance appInstance;
		LCAppComponent appComp;
		VirtualMachineInstance vmi;
		String compInstName_Camel;
		
		public ComponentInstance(String compInstName_Camel, ApplicationInstance appInstance, LCAppComponent appComp, VirtualMachineInstance vmi) {
			this.compInstName_Camel = compInstName_Camel;
			this.appInstance = appInstance;
			this.appComp = appComp;
			this.vmi = vmi;
		}

		private void setId(String id){
			this.id = id;
		}
		
		private String getCompInstType(){return this.compInstName_Camel;}
		
		private String getCompInstID(){return this.id;}
		
		boolean setComponentInstanceParams(ApplicationInstance appInstance, LCAppComponent appComp, VirtualMachineInstance vmi) {
			this.appInstance = appInstance;
			this.appComp = appComp;
			this.vmi = vmi;
			return true;
		}
	}
	
	
	
	
	//Stores all the Communication Instance entities created
	LinkedList<CommChannel> CommInsts = new LinkedList<CamelExecwareMapping.CommChannel>();
	
	public boolean addCommInst(String CommInstanceName_Camel, Communication commType, ComponentInstance intCompInstProvider, ComponentInstance intCompInstConsumer){
		boolean status = false;
		CommChannel compInstEntity;
		synchronized (CompInsts) {
			for(CommChannel commInst : CommInsts){
				if(commInst.getCommInstType().equalsIgnoreCase(CommInstanceName_Camel) && intCompInstProvider != null && intCompInstConsumer != null && commType != null){
					status = true;
					compInstEntity = commInst;
					LOGGER.log(Level.WARNING, "Communication Instance " + CommInstanceName_Camel +" already exists");
				}
			}
			
			if(intCompInstProvider == null){
				LOGGER.log(Level.WARNING, "Internal Component Instance Provider is NULL! Could not create " + CommInstanceName_Camel + " of type: " + commType.getCommType());
				return status;
			}
			if(intCompInstConsumer == null){
				LOGGER.log(Level.WARNING, "Internal Component Instance Consumer is NULL! Could not create " + CommInstanceName_Camel + " of type: " + commType.getCommType());
				return status;
			}
			if(commType == null){
				LOGGER.log(Level.WARNING, "Communication Instance is NULL! Could not create " + CommInstanceName_Camel);
				return status;
			}
			
			if(!status && intCompInstProvider != null && intCompInstConsumer != null && commType != null){
				compInstEntity = new CommChannel(CommInstanceName_Camel, commType, intCompInstProvider, intCompInstConsumer);
				status = CommInsts.add(compInstEntity);
				LOGGER.log(Level.INFO, "Communication Instance Name " + CommInstanceName_Camel + " Prov CompInst: " + intCompInstProvider.getCompInstType() + " Cons CompInst: " + intCompInstConsumer.getCompInstType() + " Comm Type " + commType.getCommType());
			}
		}
		return status;
	}
	
	public CommChannel getEntityCommunicationInstance(String commInstName_Camel){
		synchronized (CommInsts) {
			for(CommChannel commInst : CommInsts){
				if(commInst.getCommInstType().equalsIgnoreCase(commInstName_Camel) && commInst.getCommInstID()!=null){
					return commInst;
				}
			}
		}
		return null;
	}
	
	public String getCommInstID(String commInstName_Camel){
		synchronized (CommInsts) {
			for(CommChannel commInst : CommInsts)
				if(commInst.getCommInstType().equalsIgnoreCase(commInstName_Camel))
					return commInst.getCommInstID();
		}
		return null;
	}
	
	public boolean setCommInstID(String commInstName_Camel, String id){
		synchronized (CommInsts) {
			for(CommChannel commInst : CommInsts){
				if(commInst.getCommInstType().equalsIgnoreCase(commInstName_Camel) && commInst.getCommInstID()==null){
					commInst.setId(id);
					return true;
				}
			}
		}
		return false;
	}
	
	
	private class CommChannel{
		
		String id;
		
		ComponentInstance instancePrv;
		ComponentInstance instanceCons;
		Communication commType;
		
		String CommInstance_Camel;
		
		public CommChannel(String CommInstanceName_Camel, Communication commType, ComponentInstance intCompInstProvider, ComponentInstance intCompInstConsumer){
			this.CommInstance_Camel = CommInstanceName_Camel;
			this.instancePrv = intCompInstProvider;
			this.instanceCons = intCompInstConsumer;
			this.commType = commType;
			this.id = null;
		}
		
/*		public CommChannel(ComponentInstance instancePrv, ComponentInstance instanceCons, Communication comm, String CommInstance_Camel){
			this.instancePrv = instancePrv;
			this.instanceCons = instanceCons;
			this.commctn = comm;
			this.CommInstance_Camel = CommInstance_Camel;
		}*/
		
		private void setId(String id){
			this.id = id;
		}
		
		String getCommInstType(){return this.CommInstance_Camel;}
		
		String getCommInstID(){return this.id;}
	}
	
/*	public void setProvLifeCycleCompId(String lifeCycleCompId_Exec, String intCompId_Camel){
		//appCompPrv.lifeCycleCompId_Exec = lifeCycleCompId_Exec;
		//appCompPrv.intCompId_Camel = intCompId_Camel;
		appCompPrv.setLifeCycleCompId(lifeCycleCompId_Exec, intCompId_Camel);
	}*/
	
/*	public void setProvAppComp(String applicationCompId_Exec, String applicationId_Exec, VirtualMachineTemplate VMTemplate_Exec){
		//appCompPrv.id = applicationCompId_Exec;
		//appCompPrv.applicationId_Exec = applicationId_Exec;
		//appCompPrv.VMTemplateId_Exec = VMTemplateId_Exec;
		appCompPrv.setAppComponentParams(applicationCompId_Exec, applicationId_Exec, VMTemplate_Exec);
	}*/
	
/*	public void setConsLifeCycleCompId(String lifeCycleCompId_Exec, String intCompId_Camel){
		//appCompCons.lifeCycleCompId_Exec = lifeCycleCompId_Exec;
		//appCompCons.intCompId_Camel = intCompId_Camel;
		appCompCons.setLifeCycleCompId(lifeCycleCompId_Exec, intCompId_Camel);
	}*/
	
/*	public void setConsAppComp(String applicationCompId_Exec, String applicationId_Exec, VirtualMachineTemplate VMTemplate_Exec){
		//appCompCons.id = applicationCompId_Exec;
		//appCompCons.applicationId_Exec = applicationId_Exec;
		//appCompCons.VMTemplateId_Exec = VMTemplateId_Exec;
		appCompCons.setAppComponentParams(applicationCompId_Exec, applicationId_Exec, VMTemplate_Exec);
	}*/
	
/*	public void setComm(String CommId_Exec, String CommType_Camel, String port_Exec, String providerPortNumber_Camel){
		comm.id = CommId_Exec;
		comm.CommType_Camel = CommType_Camel;
		comm.port = port_Exec;
		comm.providerPortNumber_Camel = providerPortNumber_Camel;
	}
	
	public void setCommChannel(String CommChannelId_Exec, String CommType_Camel){
		commChan.id = CommChannelId_Exec;
		commChan.CommInstance_Camel = CommType_Camel;
	}*/

	public CamelExecwareMapping(){
		// TODO Auto-generated constructor stub
	}

}
