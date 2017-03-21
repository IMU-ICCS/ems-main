/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package fr.inria.paasage.saloon.price.model.tools;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;



/**
 * Methods to load and save a model from and to an XMI
 */
public class ModelTool 
{

	/**
	 * Loads a file in an EMF resource
	 * @param path location of the file
	 * @return the resource loaded
	 */
	public static Resource loadModel(String path) 
	{
        ResourceSet rs = new ResourceSetImpl();
        Resource r = rs.createResource(URI.createFileURI((new File(path)).getAbsolutePath()));
        
        //Resource r = rs.getResource(URI.createFileURI(path), true);
        
        try {
            r.load(null);
            EcoreUtil.resolveAll(r); 
            
         
            for (Resource.Diagnostic d : r.getWarnings()) 
            {
                System.out.println(d.toString());
            }
            
            for (Resource.Diagnostic d : r.getErrors()) 
            {
                System.out.println(d.toString());
            }
        } catch (IOException e) {
        	e.printStackTrace(); 
        	return null; 
        }
        return r;
	}
	
	public static Resource loadModel(File modelFile) 
	{
		return loadModel(modelFile.getAbsolutePath());
	}
	
	/**
	 * Writes a file with the model in the parameter
	 * @param resourceToSave the  model to save
	 * @param path the path were the file is created
	 */
	public static void saveModel(Resource resourceToSave, String path)
	{	
		Map options = new Hashtable();
		options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		saveModel(resourceToSave, path, options); 
		 
	}
	
	public static void saveModel(Resource resourceToSave, String path, Map options)
	{
		FileOutputStream fos=null;
		try 
		{   
			File pathFile= new File(path); 
			File dirs= pathFile.getParentFile();
			dirs.mkdirs(); 
			fos = new FileOutputStream(pathFile);
			resourceToSave.save(fos, options);
		    fos.close();
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		} 
		catch (IOException e) {
			e.printStackTrace();
			System.err.println("WARNING:"+e.getMessage()); 
			
		}
		finally{
			try {
				if(fos!=null)
					fos.close();
			} catch (IOException e) {
				
				e.printStackTrace();
			}
		}
		 
	}
	
}
