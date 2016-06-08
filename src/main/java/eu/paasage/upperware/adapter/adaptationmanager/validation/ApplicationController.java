/*
 * Copyright (c) 2016 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.validation;

import java.util.Date;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.attribute.standard.DateTimeAtCompleted;

import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.core.PaaSagePublisher;
import eu.paasage.upperware.adapter.adaptationmanager.core.ZeroMQPublisher;

/**
 * This class is used to accept entities from the Action threads during deployment process and then monitor their states later
 * @author Arnab Sinha
 *
 */

public class ApplicationController {
	
	private static LinkedList<MonitorEntity> entities = new LinkedList<MonitorEntity>();
	
	private String resourceName;
	private String modelName;
	ZeroMQPublisher zmqAdap2MetricsPub;
	
	private final static Logger LOGGER = Logger.getLogger(ApplicationController.class.getName());
	
	public ApplicationController(String resourceName){
		this.resourceName = resourceName;
		zmqAdap2MetricsPub = new ZeroMQPublisher("Adaptor2MetricsPublisher", "newModelArrival", 15550);
	}
	
	private void setModelName(String modelName){
		this.modelName = modelName;
	}
	
	public void updateResourceName(String resourceName){
		this.resourceName = resourceName;
	}
	
	private String getUniqueId(){return Long.toString((new Date()).getTime());}
	
	/**
	 * Adds an entity to be monitored later
	 * @param type {@link MonitorEntity.Type} virtualMachine or instance
	 * @param execwareId of the entity
	 * @return true, if successfully added to the queue
	 */
	public synchronized static boolean addEntityToMonitor(MonitorEntity.Type type, int execwareId){
		MonitorEntity ent = new MonitorEntity(type, execwareId);
		if(entities.add(ent)){
			LOGGER.log(Level.INFO, "Added " + ent.getEntityType() + " to be monitored for status later");
			return true;
		}else{
			LOGGER.log(Level.INFO, "Could not add " + ent.getEntityType() + " to be monitored for status later");
			return false;
		}
	}
	
	/**
	 * monitor the state of all the entities listed to be monitored until TIMEOUT_MINS
	 * @param execInterfacer ExecutionWare Interfacer instance
	 * @param TIMEOUT_MINS timeout in minutes
	 * @return true if all the entities have state OK
	 */
	public synchronized static boolean monitorEntitiesStatus(ExecInterfacer execInterfacer, int TIMEOUT_MINS){
		
		int entitiesOk = 0;
		long time = 0;
		
		while(entitiesOk < entities.size() && time < TIMEOUT_MINS){
			
			ListIterator<MonitorEntity> iter = entities.listIterator();
			while(iter.hasNext()){
				MonitorEntity ent = iter.next();
				
				MonitorEntity.Type entType = ent.getEntityType();
				boolean status = false;
				
				switch (entType) {
				case virtualMachine:
					if(!ent.isStateOK()){
						status = execInterfacer.queryStateOKVM(ent.getexecWareId());
					}
					break;
					
				case instance:
					if(!ent.isStateOK())
						status = execInterfacer.queryStateOKInstance(ent.getexecWareId());
					break;
				}
				
				if(status){
					ent.setStateOK();
					LOGGER.log(Level.INFO, ent.getEntityType().toString() + " id : " + ent.getexecWareId() + " state OK");
					entitiesOk++;
				}
			}
			
			try {
				if(entitiesOk < entities.size()){
					Thread.sleep(30000);
					time += 0.5;
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}
		
		if(entitiesOk == entities.size()){
			LOGGER.log(Level.INFO, "states OK for all monitored entities");
			return true;
		}
		else{
			LOGGER.log(Level.INFO, "states OK for " + entitiesOk + " monitored entities.\nproblem detected for " + (entities.size()-entitiesOk) + " entities!");
			return false;
		}
	}
	
	public synchronized static void resetMonitorEntities(){
		entities.clear();
		return;
	}
	
	public boolean publishToMetric(String modelName){
		boolean status = false;
		
		setModelName(modelName);
		
		String msg = "";
		if(resourceName == null){//means xmi file used for deployment
			//msg = "newResourceArrival:" + "noResourceName:" + modelName;
			msg = "noResourceName:" + modelName + ":" + "ExecutionContext_" + getUniqueId();
		}else{//means CDO server is used
			msg = resourceName + ":" + modelName + ":" + "ExecutionContext_" + getUniqueId();
		}


		status = zmqAdap2MetricsPub.publishMsg(new String[] {msg});

		// *************** Please adapt to use the reald CAMEL_MODEL_ID transmitted by S2D Message
		String[] messageParts= new String[] {resourceName};
		PaaSagePublisher.publishBackend("ApplicationIsRunning",messageParts);

		return status;
	}
}

