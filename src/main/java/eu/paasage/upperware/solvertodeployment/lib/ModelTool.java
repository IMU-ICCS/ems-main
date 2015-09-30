/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */



package eu.paasage.upperware.solvertodeployment.lib;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Hashtable;
import java.util.Map;

import org.apache.log4j.Logger;
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
	
	/*
	 * ATTRIBUTES
	 */
	/*
	 * THE LOGGER 
	 */
	public static Logger logger= Logger.getLogger("paasage-std-log");

	/*
	 * METHODS
	 */
	/**
	 * Loads a file in an EMF resource
	 * @param path location of the file
	 * @return the resource loaded
	 */
	public static Resource loadModel(String path) 
	{
        ResourceSet rs = new ResourceSetImpl();
        Resource r = rs.getResource(URI.createFileURI((new File(path)).getAbsolutePath()), true);
        
        //Resource r = rs.getResource(URI.createFileURI(path), true);
        
        try {
            r.load(null);
            EcoreUtil.resolveAll(r); 
            
         
            for (Resource.Diagnostic d : r.getWarnings()) 
            {
                logger.info(d.toString());
            }
            
            for (Resource.Diagnostic d : r.getErrors()) 
            {
                logger.info(d.toString());
            }
        } catch (IOException e) {
        	e.printStackTrace(); 
        	return null; 
        }
        return r;
	}
	
	/**
	 * Loads a file in an EMF resource
	 * @param path location of the file
	 * @return the resource loaded
	 */
	public static Resource loadModelFromInputStream(String path, InputStream is) 
	{
		
        ResourceSet rs = new ResourceSetImpl();
        Resource r = rs.createResource(URI.createFileURI((new File(path)).getAbsolutePath())); //new ResourceImpl(); //rs.getResource(URI.createFileURI((new File(path)).getAbsolutePath()), true);
       
        try {
            r.load(is, null);
            EcoreUtil.resolveAll(r); 
            
         
            for (Resource.Diagnostic d : r.getWarnings()) 
            {
                logger.info(d.toString());
            }
            
            for (Resource.Diagnostic d : r.getErrors()) 
            {
                logger.info(d.toString());
            }
        } catch (IOException e) {
        	e.printStackTrace(); 
        	return null; 
        }
        return r;
	}
	
	/**
	 * Loads a file in an EMF resource
	 * @param path location of the file
	 * @return the resource loaded
	 */
	public static Resource loadModelFromInputStream(ResourceSet rs, String path, InputStream is) 
	{
		
        //ResourceSet rs = new ResourceSetImpl();
        Resource r = rs.createResource(URI.createFileURI((new File(path)).getAbsolutePath())); //new ResourceImpl(); //rs.getResource(URI.createFileURI((new File(path)).getAbsolutePath()), true);
        
        try {
            r.load(is, null);
            EcoreUtil.resolveAll(r); 
            
         
            for (Resource.Diagnostic d : r.getWarnings()) 
            {
                logger.info(d.toString());
            }
            
            for (Resource.Diagnostic d : r.getErrors()) 
            {
                logger.info(d.toString());
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
	
	/**
	 * Saves a model using a given resource
	 * @param resourceToSave The resource containing the model 
	 * @param path To save the model
	 * @param options Related to the save operation
	 */
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
			logger.error("WARNING:"+e.getMessage()); 
			
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
	
	/**
	 * PRE: The dir containing the resource exists. 
	 * @param resourceToSave
	 */
	public static void saveModel(Resource resourceToSave)
	{
		Map options = new Hashtable();
		options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
		
		try 
		{
			resourceToSave.save(null);
		} 
		catch (IOException e) 
		{
			
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads a model using a given path and resource
	 * @param resSet The resource to load the model
	 * @param path The file path
	 * @return the resource with the laoded model
	 */
	public static Resource loadModel(ResourceSet resSet, File path)
	{
		Resource res= resSet.getResource(URI.createFileURI(path.getPath()), true); 
		
		return res; 
	}
	
}

