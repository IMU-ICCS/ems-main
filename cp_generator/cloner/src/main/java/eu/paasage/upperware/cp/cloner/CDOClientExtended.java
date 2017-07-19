/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.cp.cloner;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.util.EcoreUtil;

import eu.paasage.mddb.cdo.client.CDOClient;

public class CDOClientExtended extends CDOClient 
{
	//Log
	public static Logger logger= Logger.getLogger("paasage-profiler");
	
	public CDOClientExtended()
	{
		super(); 
		//logger.setLevel(Level.OFF);
	}
	

	/* This method is used to obtain the content of a CDOResource with a 
	 * particular path/name. You should open a view before using this method
	 * and then close it. Input parameter: the name/path of the CDOResource.
	 */
	public List<EObject> getResourceContents(String path){
		CDOView view= openView();
		
		List<EObject> qr= new ArrayList<EObject>(); 
		
		try{
			logger.info("CDOClientExtended - getResourceContents - Retrieving resource with path "+path);
			CDOResource resource = view.getResource(path);
			EList<EObject> content = resource.getContents();
		
			logger.info("CDOClientExtended - getResourceContents - Resource path "+path+ " size "+content.size() );
		
			for(EObject o:content)
			{
				logger.info("CDOClientExtended - getResourceContents - Content "+o );
				qr.add(o); 
			}
		}
		catch(Throwable ex)
		{
			logger.error("CDOClientExtended - getResourceContents - Problems retrieving the resource with path "+path+"!\n");
			ex.printStackTrace();
		}

				
		return qr;
	}
	
	
	public void storeModels(List<EObject> models, String resourceName){
		CDOTransaction trans = openTransaction();
		CDOResource cdo = trans.getOrCreateResource(resourceName);
		EList<EObject> list = cdo.getContents();
		
		for(EObject m: models)
		{	
			list.add(m);
		}
		
		try
		{
			  trans.commit();
			  trans.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/* This method is used to store a model into a CDOResource with a particular
	 * name. Do not need to open or close a transaction for this as the
	 * method performs them for you in a transparent manner. The input parameters are: the model to store and the name of the
	 * CDOResource to contain it.
	 */
	public void storeModelOverwritten(EObject model, String resourceName){
		CDOTransaction trans = openTransaction();
		CDOResource cdo = trans.getOrCreateResource(resourceName);
		
		EList<EObject> list = cdo.getContents();
		
		try{

			if(list.size()>0)
			{
				 trans.commit();
				 trans.close();
				 
				 deleteResource(resourceName);
				 
				 trans = openTransaction();
				 cdo = trans.getOrCreateResource(resourceName);
				 list = cdo.getContents();
				  
			 }
			
			  list.add(model);
			  trans.commit();
			  trans.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void deleteResource(String resourceName)
	{
		List<EObject> objs= getResourceContents(resourceName);
		 
		 //for(EObject obj:objs)
		 {
			 CDOObject cdoO= (CDOObject) objs.get(0); 
			 deleteObject(cdoO.cdoID());
		 }
	}
	
	public void storeModelOverwritten(List<EObject> models, String resourceName){
		CDOTransaction trans = openTransaction();
		CDOResource cdo = trans.getOrCreateResource(resourceName);

		EList<EObject> list = cdo.getContents();
		
		try
		{
			if(list.size()>0)
			{
				 trans.commit();
				 trans.close();
				 
				 deleteResource(resourceName);
				 
				 trans = openTransaction();
				 cdo = trans.getOrCreateResource(resourceName);
				 list = cdo.getContents();
				  
			 }
			
			list.addAll(models);
			trans.commit();
			trans.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	
	public boolean existResource(String path)
	{	try
		{
			CDOView view= openView();
			CDOResource resource = view.getResource(path);
		
			return resource!=null; 
		}
		catch(Exception ex)
		{
			return false; 
		}
		
		//return false; 
	}
	
	public void storeModelsWithCrossReferences(List<EObject> models, String resourceName){
		
		EcoreUtil.Copier copier = new EcoreUtil.Copier();
		CDOTransaction trans = openTransaction();
		CDOResource cdo = trans.getOrCreateResource(resourceName);
		EList<EObject> list = cdo.getContents();
		
		
		
		for(EObject m: models)
		{	
			EObject cop=copier.copy(m); 
			list.add(cop);
		}
		
		try
		{
			copier.copyReferences();
			trans.commit();
			trans.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void storeModelsWithCrossReferences(ResourceSet rs, String resourceName){
		
		EcoreUtil.Copier copier = new EcoreUtil.Copier();
		CDOTransaction trans = openTransaction();
		CDOResource cdo = trans.getOrCreateResource(resourceName);
		EList<EObject> list = cdo.getContents();
		
		
		
		for(Resource r: rs.getResources())
		{	
			for(EObject o: r.getContents())
			{
				EObject cop=copier.copy(o); 
				
				
				list.add(cop);
				
			}	
		}
		
		try
		{
			copier.copyReferences();
			trans.commit();
			trans.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean deleteObject(CDOID uri){
		try{
			CDOTransaction trans = openTransaction(); 
			CDOObject object = trans.getObject(uri);
			deleteObject(object,trans,true);
			
			return true; 
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return false; 
			
	}
	
	/* This method can be used to delete an object provided that it has been obtained with the
	 * transaction that is also used as input to this method. First, it obtains all
	 * references to the object and deletes them and then deletes the object from its
	 * container. Please be aware that the last input parameter dictates whether the transaction 
	 * will be committed and closed by this method in the end or not. If not, then the user
	 * should be responsible for setting this parameter as true in the last delete statement
	 * in his/her code or for committing and closing the transaction him/herself.
	 */
	public boolean deleteObject(CDOObject object, CDOTransaction trans, boolean commitAndClose){
		try{
			//Get all references (non-containment associations) to the object
			List<CDOObjectReference> refs = trans.queryXRefs(object);
			for (CDOObjectReference ref: refs){
				CDOObject source = (CDOObject)ref.getSourceObject();
				CDOObject target = (CDOObject)ref.getTargetObject();
				EStructuralFeature feat = ref.getSourceFeature();
				Object eGet = source.eGet(feat);
				List<?> list = null;
				if(eGet instanceof List<?>){
					list = (List<?>)eGet;
					System.out.println("Prev size: is: " + list.size());
					list.remove(target);
					System.out.println("New size: is: " + list.size());
				}
				else{
					source.eSet(feat, null);
				}
			}
			//Get containment association and delete it
			CDOObject parent = (CDOObject)object.eContainer();
			EStructuralFeature feat = object.eContainmentFeature();
			System.out.println("The feature is: " + feat);
			
			if(feat!=null)
			{	
				Object eGet = parent.eGet(feat);
				List<?> list = null;
				if (eGet instanceof List<?>){
					list = (List<?>)eGet;
					System.out.println("Prev size: is: " + list.size());
					list.remove(object);
					System.out.println("New size: is: " + list.size());
				}
				else{
					parent.eSet(feat, null);
				}
			}
			
			if (commitAndClose){
				trans.commit();
				trans.close();
			}
			
			return true;
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return false; 
	}
}
