/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.db.lib;

/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import eu.passage.upperware.commons.model.tools.ModelTool;
import lombok.extern.slf4j.Slf4j;
import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;

import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;

/**
 * @author Daniel Romero
 */
@Slf4j
public class CDOClientExtended extends CDOClient
{
	
	protected static PrintStream originalErrOutput; 
	
	
    /*Default constructor for the client which initiates a CDO session*/
	public CDOClientExtended(){		
		super(); 
	}
	

	public void storeModel(EObject model, String resourceName){
		CDOTransaction trans = openTransaction();
		CDOResource cdo = trans.getOrCreateResource(resourceName);
		EList<EObject> list = cdo.getContents();
		list.add(model);
		try{
			  trans.commit();
			  trans.close();
		}
		catch(Exception e){
			e.printStackTrace();
		}
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
	
	public void storeModelsWithCrossReferences(EList<EObject> models, String resourceName){
		
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
	
	public void storeModels(String resourceName){
		CDOTransaction trans = openTransaction();
		CDOResource cdo = trans.getResource(resourceName);
		EList<EObject> list = cdo.getContents();
		
		ResourceSet rs= new ResourceSetImpl();

		File pcFile= new File("/temp/appModel.xmi");

		try {
			Resource pcResource = rs.createResource(URI.createFileURI(pcFile.getCanonicalPath()));
			pcResource.getContents().add(list.get(0)); 
			ModelTool.saveModel(pcResource, pcFile.getCanonicalPath());
			

			File cpFile= new File("/temp/cpModel.xmi"); 
			Resource cpResource =  rs.createResource(URI.createFileURI(cpFile.getCanonicalPath()));
			cpResource.getContents().add(list.get(1)); 
			ModelTool.saveModel(cpResource, cpFile.getCanonicalPath());
			
			trans.close();
			
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		} 
	}
	
	/* This method is used to obtain the content of a CDOResource with a 
	 * particular path/name. You should open a view before using this method
	 * and then close it. Input parameter: the name/path of the CDOResource.
	 */
	public List<EObject> getResourceContents(String path){
		CDOView view= openTransaction();//openView();
		CDOResource resource = view.getResource(path);
		EList<EObject> content = resource.getContents();
		
		List<EObject> qr= new ArrayList<EObject>(); 
		log.debug("CDOClientExtended - getResourceContents - Retrieved Resource "+resource +" path "+path );
		
		if(!content.isEmpty())
		{
			log.debug("CDOClientExtended - getResourceContents - Resource path "+path+ " size "+content.size() );
			
			for(EObject o:content)
			{
				log.debug("CDOClientExtended - getResourceContents - Content "+o );
				qr.add(o); 
			}
		
		}
		else
			log.warn("CDOClientExtended - getResourceContents - Resource path "+path+ " is empty ");
		
		//closeView(view);
		
		return qr;
	}
	
	public boolean existResource(String path)
	{	try
		{
			CDOView view= openView();
			
			CDOResource resource = view.getResource(path);
					
			return resource!=null; 
		}
		catch(RuntimeException ex)
		{
			//return false; 
		}

		
		return false; 
	}
	
	public boolean existResourceOfType(String type)
	{	
		CDOView view = openView();
		CDOQuery query = null;
		query = view.createQuery("sql", "SELECT resource_id FROM "+"camel_"+type.toLowerCase());
		
		log.debug("CDOClientExtended - existResourceOfType - Query "+query+ " type: "+type);
		List<EObject> result= query.getResult();
		log.debug("CDOClientExtended - existResourceOfType - Result "+result);
		view.close();
	
		return result!=null && result.size()>0; 
	}
	
	public List<EObject> getResourceContents(String path, CDOView view)
	{
		
		CDOResource resource = view.getResource(path);
		EList<EObject> content = resource.getContents();
		
		List<EObject> qr= new ArrayList<EObject>(); 
		log.debug("CDOClientExtended - getResourceContents - Retrieved Resource "+resource +" path "+path );
		
		if(!content.isEmpty())
		{
			log.debug("CDOClientExtended - getResourceContents - Resource path "+path+ " size "+content.size() );
			
			for(EObject o:content)
			{
				log.debug("CDOClientExtended - getResourceContents - Content "+o );
				qr.add(o); 
			}
		
		}
		else
			log.warn("CDOClientExtended - getResourceContents - Resource path "+path+ " is empty ");
		
		return qr;
	}
	
	public static void changeStandardOutputErr()
	{
		OutputStream output;
		try {
			output = new FileOutputStream("."+File.separator+"system.err.txt");
			
			PrintStream printErr = new PrintStream(output);
			originalErrOutput= System.err; 
			System.setErr(printErr);
		} catch (FileNotFoundException e) {
		
			e.printStackTrace();
		}
	}
	
	
	public static void setDefaultStandardOutputErr()
	{
		if(originalErrOutput!=null)
		{
			OutputStream out= System.err; 
			
			System.setErr(originalErrOutput);
			originalErrOutput= null; 
			
			try {
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}