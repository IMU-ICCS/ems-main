/*
 * Copyright (c) 2015 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.input;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.net4j.FactoriesProtocolProvider;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.buffer.IBufferProvider;
import org.eclipse.net4j.protocol.IProtocolProvider;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.execution.ExecutionPackage;
import eu.paasage.camel.location.LocationPackage;
import eu.paasage.camel.metric.MetricPackage;
import eu.paasage.camel.organisation.OrganisationFactory;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.requirement.RequirementPackage;
import eu.paasage.camel.scalability.ScalabilityModel;
import eu.paasage.camel.scalability.ScalabilityPackage;
import eu.paasage.camel.security.SecurityModel;
import eu.paasage.camel.security.SecurityPackage;
//import eu.paasage.camel.sla.AgreementType;
//import eu.paasage.camel.sla.SlaPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.camel.unit.UnitPackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOCase;
import org.apache.commons.io.filefilter.FileFileFilter;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;

/**
 * Contains all the utility tools to manage the deployment model files (rename, move etc..)
 * @author Arnab Sinha
 */
public class CDOClientUtil {
	
	private final static Logger LOGGER = Logger.getLogger(CDOClientUtil.class
			.getName());
	
	CDOClient client;//Already in Reasoner Interfacer.. So delete eventually.
	String destDirectory;
	
	//IOFileFilter xmiSuffixFilter, xmiFiles;
	
	public CDOClientUtil(String destDirectory) {
		client = new CDOClient();
		this.destDirectory = destDirectory;

		
	}
	
	public boolean exportModelWithRefRecToDir(String resourcePath, boolean xtext){
		boolean status = false;
		if(xtext == false)
			status = client.exportModelWithRefRec(resourcePath, destDirectory, xtext);
		
		/*if(status && copyXMIFiles())
			System.out.println("Exported resources and moved successfully");*/
		
		return status;
	}
	
	private boolean copyXMIFiles(){
		//FileUtils.copyDirectory(srcDir, destDirectory, xmiFiles, false);
		boolean status = true;
		
		// Create a filter for ".xmi" files
		IOFileFilter xmiSuffixFilter = FileFilterUtils.suffixFileFilter(".xmi");
		@SuppressWarnings("deprecation")
		IOFileFilter xmiFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, xmiSuffixFilter);
		
		File src = new File(".");
		File dest = new File(destDirectory);
		
		System.out.println("Dest: " + dest.getAbsolutePath());
		
		Iterator<File> it = FileUtils.iterateFiles(src, xmiFiles, null);
		while(it.hasNext()){
			File cur = it.next();
			System.out.println("=>" + cur.getPath());
			try {
				FileUtils.copyFileToDirectory(cur, dest);
				FileUtils.deleteQuietly(cur);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				status = false;
			}
		}
		return status;
	}

	public void prependToFilename(File resource, String filenameContainsText, boolean whenContainsText, String filenamePrefixText){
		if(resource.isFile() && resource.exists() && filenameContainsText!=null && filenamePrefixText!=null){
			String absPath = resource.getAbsolutePath();
			String path = absPath.substring(0, absPath.lastIndexOf(File.separatorChar)+1);
			String fileName = absPath.substring(absPath.lastIndexOf(File.separatorChar)+1,absPath.lastIndexOf('.'));
			String fileExtension = absPath.substring(absPath.lastIndexOf('.')+1);
			
			String newFilename = fileName;
			boolean changed = false;
			if(fileName.toLowerCase().indexOf(filenameContainsText.toLowerCase())>=0 && whenContainsText){
				newFilename = filenamePrefixText + newFilename;
				changed = true;
			}
			else if(fileName.toLowerCase().indexOf(filenameContainsText.toLowerCase())<0 && (!whenContainsText)){
				newFilename = filenamePrefixText + newFilename;
				changed = true;
			}
			if(changed){
				String newPath = path + newFilename + fileExtension;
				resource.renameTo(new File(newPath));
				LOGGER.log(Level.INFO, "Changed file name to " + newFilename);
			}
			
			System.out.println(path + " " + fileName + " " + fileExtension + " " + newFilename);
		}
	}
	
	public void appendToFilename(File resource, String filenameContainsText, boolean whenContainsText, String filenameSuffixText){
		if(resource.isFile() && resource.exists() && filenameContainsText!=null && filenameSuffixText!=null){
			String absPath = resource.getAbsolutePath();
			String path = absPath.substring(0, absPath.lastIndexOf(File.separatorChar)+1);
			String fileName = absPath.substring(absPath.lastIndexOf(File.separatorChar)+1,absPath.lastIndexOf('.'));
			String fileExtension = absPath.substring(absPath.lastIndexOf('.')+1);
			
			String newFilename = fileName;
			boolean changed = false;
			if(fileName.toLowerCase().indexOf(filenameContainsText.toLowerCase())>=0 && whenContainsText){
				newFilename = newFilename + filenameSuffixText;
				changed = true;
			}
			else if(fileName.toLowerCase().indexOf(filenameContainsText.toLowerCase())<0 && (!whenContainsText)){
				newFilename = newFilename + filenameSuffixText;
				changed = true;
			}
			if(changed){
				String newPath = path + newFilename + fileExtension;
				resource.renameTo(new File(newPath));
				LOGGER.log(Level.INFO, "Changed file name to " + newFilename);
			}
			
			System.out.println(path + " " + fileName + " " + fileExtension + " " + newFilename);
		}
	}
	
	private Iterator<File> getFileCollectionIterator(String directoryPath, String filePrefixName){
		File directory = new File(directoryPath);
		if(!directory.isDirectory()){
			LOGGER.log(Level.WARNING, "Not a directory " + directoryPath);
			return null;
		}
		
		// Create a filter for prefixed ".xmi" files
		IOFileFilter xmiSuffixFilter = FileFilterUtils.suffixFileFilter(".xmi");
		IOFileFilter prefixFilter = FileFilterUtils.prefixFileFilter(filePrefixName, IOCase.INSENSITIVE);
		IOFileFilter combinedFilter = FileFilterUtils.andFileFilter(xmiSuffixFilter, prefixFilter);
		@SuppressWarnings("deprecation")
		IOFileFilter xmiFiles = FileFilterUtils.andFileFilter(FileFileFilter.FILE, combinedFilter);
		
		Iterator<File> it = FileUtils.iterateFiles(directory, xmiFiles, null);
		return it;
	}
	
	public DeploymentModel getDeploymentModel(String directoryPath, String filePrefixName){
		DeploymentModel dm = null;
		
		Iterator<File> it = getFileCollectionIterator(directoryPath, filePrefixName);

		while(it.hasNext()){
			File cur = it.next();
			System.out.println("=>" + cur.getPath());
			
		}
		
		return dm;
	}
	
	public static DeploymentModel tryLoadTwoFiles(String camel, String provider){
		System.out.println("...inside tryLoadTwoFiles....");
		CamelModel cModel;
		CamelModel pModel;
		DeploymentModel dm = null;
		try{
			//loadModel is a static method
			cModel = (CamelModel) CDOClient.loadModel(camel);
			pModel = (CamelModel) CDOClient.loadModel(provider);
			dm = cModel.getDeploymentModels().get(0);
		}catch(Exception e){
			System.out.println("Exception trying to load two files for one model : " + e.getMessage());
		}
		return dm;
	}
	
	public boolean deleteDirOrDest(String dir){
		boolean status = true;
		File dirDel;
		if(dir==null)
			dirDel = new File(destDirectory);
		else
			dirDel = new File(dir);
		try {
			FileUtils.deleteDirectory(dirDel);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			status = false;
		}		
		return status;
	}
}
