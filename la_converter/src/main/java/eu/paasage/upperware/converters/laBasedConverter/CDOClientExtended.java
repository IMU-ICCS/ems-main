/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.converters.laBasedConverter;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.mddb.cdo.client.CDOClient;

public class CDOClientExtended extends CDOClient 
{
	//Log
	
	public static Logger logger= Logger.getLogger("paasage-converters-log");
	
	
	
	
	public CDOClientExtended()
	{
		super(); 
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
	
	public List<EObject> getResourceContentsWithTransanction(String path){
		CDOView view= openTransaction();
		
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
}
