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
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.cp.cloner.CDOClientExtended;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;

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
	/** CP Cloner CDO client */
	private static CDOClientExtended extendedClient = null;
	/** CP Cloner */
	private static CPCloner cloner = null;
	/** CDO Client 
	private static CDOClient cdoClient = null;*/
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
		ApplicationPackage.eINSTANCE.eClass();
		log.info("Init ApplicationPackag");
		TypesPaasagePackage.eINSTANCE.eClass(); 
		log.info("Init TypesPaasagePackage");
		TypesPackage.eINSTANCE.eClass(); 
		log.info("Init TypesPackage");
		CpPackage.eINSTANCE.eClass();
		log.info("Init CpPackage");
		OntologyPackage.eINSTANCE.eClass();
		log.info("Init OntologyPackage");
		MappingPackage.eINSTANCE.eClass();
		log.info("Init MappingPackage");		
		//CamelPackage.eINSTANCE.eClass();	this is done by the underlying cdo client
		//log.info("Init CamelPackage");	
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
		//
		return instance;
	}
	
	
	/**
	 * An explicit method to get a cp cloner client to work with CDO server
	 */
	public void openCDOSession(){
		if(extendedClient != null){
			log.info("client already instantiated...");
			return;
		}
		extendedClient = CPCloner.createCDOClient();
	}
	/**
	 * An explicit method to close the cloner client CDO session and
	 * terminate the client.
	 */
	public void closeCDOSession(){
		extendedClient.closeSession();
		extendedClient = null;
		log.info("closed cloner client session and removed client...");
	}
	/**
	 * Retrieve the resource from the cdo server
	 * <p>
	 * @param resId	resId	Identifier of the target CDO resource
	 * @return the cloned resource as a {@link java.util.List <em>List</em>} of the 
	 * 			{@link org.eclipse.emf.ecore.EObject <em>EObject</em>}
	 */
	public List<EObject> getResource(String resId){
		return extendedClient.getResourceContents(mapCdoId(resId));
	}
	
	/**
	 * Get a copy of the CDO resource in memory using the provided resource id.
	 * <p>
	 * @param resId	Identifier of the target CDO resource
	 * @return the cloned resource as a {@link java.util.List <em>List</em>} of the 
	 * 			{@link org.eclipse.emf.ecore.EObject <em>EObject</em>}
	 */
	public List<org.eclipse.emf.ecore.EObject> cloneModel(String resId){
		if(resId == null){
			log.error("Cannot clone model without a resId....");
			return null;
		}
		//the model is contained in the upperware_models/ path		
		return cloner.cloneModel(mapCdoId(resId));
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
		if(extendedClient == null){
			openCDOSession();
		}
		extendedClient.storeModels(contents, mapCdoId(cloneResId));//cdo exception trapped by client	
	}
	/**
	 * Overwrite the model in the CDO server.
	 * <p>
	 * @param contents	a {@link java.util.List <em>List</em>} of the 
	 * 			{@link org.eclipse.emf.ecore.EObject <em>EObject</em>}s
	 * @param resId	the target CDO resource id
	 */
	public void overwriteCPModelinCDO(List<EObject> contents, String resId){
		//cloner client handles the transaction
		if(resId == null){
			log.error("Cannot overwrite model without a resId....");
			return;			
		}
		if(contents.isEmpty()){
			log.error("Cannot overwrite model in CDO, no contents....");
			return;
		}		
		if(extendedClient == null){
			openCDOSession();
		}
		//extended client method does not actually overwrites, it just add
//		extendedClient.storeModelOverwritten(contents, mapCdoId(resId));
		CDOTransaction trans = extendedClient.openTransaction();
		CDOResource cdo = trans.getOrCreateResource(mapCdoId(resId));
		EList<EObject> contents1 = cdo.getContents();
		try{
			if(contents1.size() > 0){
				//do this via CDO id
				CDOObject cdoObj = (CDOObject) contents1.get(0);
				trans.getObject(cdoObj.cdoID());
				extendedClient.deleteObject(cdoObj, trans, true);	
				//the extendedClient will commit and close;
			}
			//now try to add the new one using a new transaction
			trans = extendedClient.openTransaction();
			cdo = trans.getOrCreateResource(mapCdoId(resId));
			contents1 = cdo.getContents();
			log.debug("retrieved " + contents1.size() + " objects in the new transaction session for " + resId);
			contents1.addAll(contents);
			trans.commit();
			trans.close();			
		}catch(Exception e){
			log.error("Error overwriting "  + resId + ": " + e.getMessage());
		}
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
	/**
	 * CP models are stored in CDO server under the upperware_models/ resource path.
	 * This function prefix model id with the required resource path.
	 * <p>
	 * @param current	the current CP model identifier {@link java.lang.String <em>String</em>}
	 * @return	the cleaned identifier {@link java.lang.String <em>String</em>} 
	 * 			or null if current is null or empty
	 */
	private String mapCdoId(String current){
		if(current == null || current.isEmpty()){
			log.error("cannot map Cdo Id, resId is null/empty!");
			return null;
		}
		//
		if(current.startsWith(CPCloner.CDO_SERVER_PATH)){
			return current;
		}else{
			return CPCloner.CDO_SERVER_PATH + current;
		}
	}
}
