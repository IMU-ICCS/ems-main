/*
 * Copyright (c) 2014-2016 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metasolver.util;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.cp.cloner.CDOClientExtended;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;

/**
 * A singleton utility to help interact with the CDO server.
 * <p> 
 * @author Shirley Crompton 
 * org UK Science and Technology Facilities Council
 */
public final class CdoTool {
	/** log4j message logger */
	protected static Logger log = Logger.getLogger(CdoTool.class);
	/** singleton instance of CDOUtils */
	private static CdoTool instance = null;
	/** cdo client */
	private static CDOClientExtended client = null;
	/** CP Cloner */
	private static CPCloner cloner = null;
	/**
	private String resId = null;*/
	

	/**
	 * Private constructor 
	 */
	private CdoTool() {
		// default private constructor
	}
	/**
	 * Construct an instance.
	 * <p>
	 * @return  an instance of the class.
	 */
	public static CdoTool getInstance(){
		if(instance == null){
			instance = new CdoTool();
		}
		if(cloner == null){
			cloner = new CPCloner();
		}
		return instance;
	}
	
	
	/**
	 * An explicit method to get a client to work with CDO server
	 */
	public void openCDOSession(){
		if(client != null){
			log.info("client already instantiated...");
			return;
		}
		client = CPCloner.createCDOClient();
	}
	/**
	 * An explicit method to close the client CDO session and
	 * terminate the client.
	 */
	public void closeCDOSession(){
		client.closeSession();
		client = null;
		log.info("closed client session and removed client...");
	}
	/**
	 * Get a copy of the CDO resource using the provided resource id.
	 * <p>
	 * @param resId	Identifier of the target CDO resource
	 * @return the cloned resource as a {@link java.util.List <em>List</em>} of the 
	 * 			{@link org.eclipse.emf.ecore.EObject <em>EObject</em>}s
	 */
	public List<org.eclipse.emf.ecore.EObject> cloneModel(String resId){
		if(resId == null){
			log.error("Cannot clone model without a resId....");
			return null;
		}
		return cloner.cloneModel(resId);
	}
	/**
	 * Commit the clone model to the CDO server.
	 * <p>
	 * @param contents	a {@link java.util.List <em>List</em>} of the 
	 * 			{@link org.eclipse.emf.ecore.EObject <em>EObject</em>}s
	 * @param cloneResId	the target CDO resource id
	 */
	public void commitCloneModelToCDO(List<EObject> contents, String cloneResId){
		if(cloneResId == null){
			log.error("Cannot commit clone model without a resId....");
			return;			
		}
		if(contents.isEmpty()){
			log.error("Cannot clone model, no contents....");
			return;
		}
		if(client == null){
			openCDOSession();
		}
		client.storeModels(contents, cloneResId);//cdo exception trapped by client		
	}
	/**
	 * Create a cloned resource id based on the original resource id.  We 
	 * append &#96;1 to the original id.
	 * <p>
	 * @param resId	the original resource id
	 * @return	the cloned resource id
	 
	public String getClonedResId(String resId){
		if(resId == null){
			log.error("Cannot get cloned resource id without a resource id....");
			return null;		
		}
		//there is an assumption here that the pattern will be e.g.
		//uperware-models/OpenFoamApplication1445419635068
		//we take the second part 
		String[] arString = resId.split("/");
//		int versionNum = 2;
//		if(arString[1].contains("v")){
//			String version = arString[1].substring(arString[1].lastIndexOf("v"));
//			versionNum = Integer.parseInt(version) + 1;	
//			if(versionNum > 0){
//				clonedResourceID = arString[1].substring(0,arString[1].lastIndexOf("v")) + String.valueOf(versionNum);
//			}else{
//				clonedResourceID = arString[1] + "_1"; //failed to parse the version number
//			}
//		}else{//no version number 
		String clonedResourceID = arString[1] + "_1"; //just append a 1 
		log.debug("the cloned resource id is : " + clonedResourceID);
		
		return clonedResourceID;	
	}*/
	
//	public static List<MetricVariable> getMetricVariables(ConstraintProblem cpModel){
//		return cpModel.getMetricVariables();
//
//		The CPModelTool provide loads of useful methods:
//			searchLastSolution, createSolution, searchMetricValue, searchMetricVariableValue,
//			and to create different types of constants (need to check variable domain)
//		
//	}
}
