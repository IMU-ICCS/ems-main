/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.input;

import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.execution.ExecutionPackage;
import eu.paasage.camel.location.LocationPackage;
import eu.paasage.camel.metric.MetricPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.requirement.RequirementPackage;
import eu.paasage.camel.scalability.ScalabilityPackage;
import eu.paasage.camel.security.SecurityPackage;
//import eu.paasage.camel.sla.SlaPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.camel.unit.UnitPackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.adapter.adaptationmanager.core.AdaptationManager;

public class ReasonerInterfacer {
	//old members
	private String inputFile;
	private String host, port, repositoryName;
	private MyCDOClient cl;
	private CDOView view;
	private static EObject currentModel;
	//common members
	private String resourceName;
	private boolean usingCDO = false;
	//new members
	private boolean usingCDOClientLib = false;
	private CDOClientUtil cdoClientUtil;
	private CDOClient client;
	private CDOTransaction transaction;
	

	private final static Logger LOGGER = Logger
			.getLogger(ReasonerInterfacer.class.getName());

	public ReasonerInterfacer() {
		Properties properties = AdaptationManager.getProperties();
		host = properties.getProperty("CDO.host");
		port = properties.getProperty("CDO.port");
		repositoryName = properties.getProperty("CDO.repositoryName");
		if (repositoryName == null)
			repositoryName = "repo1";
		resourceName = properties.getProperty("CDO.resourceName");
		if (resourceName == null)
			resourceName = "Scalarm";
		inputFile = properties.getProperty("inputFile");
		if (host != null) {
			usingCDO = true;
		} else if (inputFile != null) {
			usingCDO = false;
		} else {
			LOGGER.log(Level.SEVERE,
					"Missing configuration for obtaining deployment model");
			throw new AssertionError();
		}
	}

	/**
	 * constructor
	 * @param resource file path or name in the CDO
	 * @param fromFile true if providing file path else false if from CDO
	 */
	public ReasonerInterfacer(String resource, boolean fromFile) {
		if(fromFile){
			this.inputFile = resource;
			usingCDO = false;
		}else{
			client = new CDOClient();
			usingCDO = usingCDOClientLib = true;
			if(resource != null)
				this.resourceName = resource;
			else
				LOGGER.log(Level.WARNING, "CDO Resource name is NULL");
		}
	}
	
	public ReasonerInterfacer(String path, boolean usingCDOClientLib, String resourceName){
		this.inputFile = path;
		this.usingCDO = this.usingCDOClientLib = true;//use param usingCDOClientLib when considering option for files
		if(usingCDOClientLib)
			client = new CDOClient();
		if(resourceName != null)
			this.resourceName = resourceName;
		else
			LOGGER.log(Level.WARNING, "CDO Resource name is NULL");
		
		//This will not be used currently, as not storing models. Just using live transactions
		Properties properties = AdaptationManager.loadAndGetProperties();
		String destDirectory = properties.getProperty("CDO.models.storage");
		this.cdoClientUtil = new CDOClientUtil(destDirectory);
	}
	
	/**
	 * Constructor using cdo_client
	 * @param resourceName the model to be fetched
	 */
	public ReasonerInterfacer(String resourceName){
		client = new CDOClient();
		if(resourceName != null)
			this.resourceName = resourceName;
		else
			LOGGER.log(Level.WARNING, "CDO Resource name is NULL");
		
		//This will not be used currently, as not storing models. Just using live transactions
		Properties properties = AdaptationManager.loadAndGetProperties();
		String destDirectory = properties.getProperty("CDO.models.storage");
		this.cdoClientUtil = new CDOClientUtil(destDirectory);
	}

	public ReasonerInterfacer(String host, String port, String repositoryName, String resourceName) {
		this.host= host;
		this.port= port;
		this.repositoryName= repositoryName;
		this.resourceName=resourceName;
		usingCDO = true;
	}
	
	public boolean isModelFromCDO(){return usingCDO;}
	
	public void openTransaction(){
		//if(usingCDOClientLib)
		if(client != null)
			this.transaction = client.openTransaction();
	}
	
	public DeploymentModel getLiveDeploymentModel(int dmIndex){
		if(!usingCDO && !usingCDOClientLib && client != null && transaction != null)
			return null;
		
		CDOResource cdoRes = null;
		if(resourceName != null)
			cdoRes = transaction.getOrCreateResource(resourceName);
		List<EObject> results = cdoRes.getContents();
		System.out.println("The results of the query are:" + results); //CamelModel@OID:http://www.paasage.eu/2015/06/camel#CamelModel#1
		CamelModel model = (CamelModel) results.get(0);
		if(model == null){
			try {
				throw new Exception("Failed to get camel model");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		LOGGER.log(Level.INFO, "#Deployment_Models in CDO : " + model.getDeploymentModels().size() + ". Getting model# " + dmIndex);
		DeploymentModel depModel = null;
		if(dmIndex <= model.getDeploymentModels().size())
			depModel = model.getDeploymentModels().get(dmIndex);
		else{
			try {
				throw new Exception("Inexistent deployment model index");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return depModel;
	}
	
	public int getDeploymentModelsSize(){
		if(!usingCDO && !usingCDOClientLib && client != null && transaction != null)
			return 0;
		
		CDOResource cdoRes = null;
		if(resourceName != null)
			cdoRes = transaction.getOrCreateResource(resourceName);
		List<EObject> results = cdoRes.getContents();
		System.out.println("The results of the query are:" + results); //CamelModel@OID:http://www.paasage.eu/2015/06/camel#CamelModel#1
		CamelModel model = (CamelModel) results.get(0);
		if(model == null){
			try {
				throw new Exception("Failed to get camel model");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model.getDeploymentModels().size();
	}
	
	public void closeTransaction(){
		if(this.transaction != null){
			client.closeTransaction(transaction);
			LOGGER.log(Level.INFO, "...stopping proxy, closed session etc.");
		}else{
			LOGGER.log(Level.INFO, "No transaction, just stopping session....");
		}
	}
	
	private DeploymentModel loadFromFile() {
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",
				new XMIResourceFactoryImpl() {
					public Resource createResource(URI uri) {
						XMIResource xmiResource = new XMIResourceImpl(uri);
						return xmiResource;
					}
				});

		final ResourceSet rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(CamelPackage.eNS_URI,
				CamelPackage.eINSTANCE);
		Resource res = rs.getResource(URI.createFileURI(this.inputFile), true);
		LOGGER.log(Level.INFO, "Obtained resource: " + res.getURI());
		EList<EObject> contents = res.getContents();
		CamelModel cm = (CamelModel) contents.get(0);
		DeploymentModel model = cm.getDeploymentModels().get(0);
		return model;
	}
	
	public DeploymentModel loadFromFileTest(){
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",
				new XMIResourceFactoryImpl() {
					public Resource createResource(URI uri) {
						XMIResource xmiResource = new XMIResourceImpl(uri);
						return xmiResource;
					}
				});

		final ResourceSet rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(CamelPackage.eNS_URI,
				CamelPackage.eINSTANCE);
		Resource res = rs.getResource(URI.createFileURI(this.inputFile), true);
		LOGGER.log(Level.INFO, "Obtained resource: " + res.getURI());
		EList<EObject> contents = res.getContents();
		CamelModel cm = (CamelModel) contents.get(0);
		System.out.println("# deployment models in CAMEL file model " + cm.getDeploymentModels().size() );
		DeploymentModel model = cm.getDeploymentModels().get(1);
		return model;
	}
	
	public DeploymentModel loadNthFromFile(int dmIndex){
		Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*",
				new XMIResourceFactoryImpl() {
					public Resource createResource(URI uri) {
						XMIResource xmiResource = new XMIResourceImpl(uri);
						return xmiResource;
					}
				});

		final ResourceSet rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(CamelPackage.eNS_URI,
				CamelPackage.eINSTANCE);
		Resource res = rs.getResource(URI.createFileURI(this.inputFile), true);
		LOGGER.log(Level.INFO, "Obtained resource: " + res.getURI());
		EList<EObject> contents = res.getContents();
		CamelModel cm = (CamelModel) contents.get(0);
		System.out.println("# deployment models in CAMEL file model " + cm.getDeploymentModels().size() + ". Getting model# " + dmIndex);
		DeploymentModel model = null;
		if(dmIndex <= cm.getDeploymentModels().size())
			model = cm.getDeploymentModels().get(dmIndex);
		else{
			try {
				throw new Exception("Inexistent deployment model index");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return model;
	}

	public DeploymentModel getDeploymentModel(boolean current) {

		DeploymentModel model = null;
		if (usingCDO) {
			if (current){
				LOGGER.log(Level.INFO, "Obtaining current deployment model from CDO server");
				model = getCurrentFromCDO();
			} else{
				LOGGER.log(Level.INFO, "Obtaining target deployment model from CDO server");
				model = getFromCDO();
			}
			
		} else {
			LOGGER.log(Level.INFO, "Obtaining deployment model from input file: "+inputFile);
			model = loadFromFile();
		}
		return model;
	}

	private DeploymentModel getFromCDO() {
		// Assumes that the model obtained from CDO is a CamelModel that
		// contains the DeploymentModel
		cl = new MyCDOClient(host, port, repositoryName);
		cl.registerPackage(CamelPackage.eINSTANCE);
		cl.registerPackage(OrganisationPackage.eINSTANCE);
		cl.registerPackage(DeploymentPackage.eINSTANCE);
		cl.registerPackage(ProviderPackage.eINSTANCE);
		cl.registerPackage(TypePackage.eINSTANCE);
		cl.registerPackage(ScalabilityPackage.eINSTANCE);
		cl.registerPackage(SecurityPackage.eINSTANCE);
//		cl.registerPackage(SlaPackage.eINSTANCE);
		cl.registerPackage(ExecutionPackage.eINSTANCE);
		cl.registerPackage(RequirementPackage.eINSTANCE);
		cl.registerPackage(MetricPackage.eINSTANCE);
		cl.registerPackage(UnitPackage.eINSTANCE);
		cl.registerPackage(LocationPackage.eINSTANCE);
		
		view = cl.openView();
		Resource res = view.getResource(resourceName);
		LOGGER.log(Level.INFO, "Obtained resource: " + res.getURI());
		EList<EObject> contents = res.getContents();
		CamelModel cm = (CamelModel) contents.get(0);
		DeploymentModel model = cm.getDeploymentModels().get(0);
		return model;
	}
	
	private DeploymentModel getCurrentFromCDO() {
		// Assumes that the model obtained from CDO is a CamelModel that
		// contains the DeploymentModel
		cl = new MyCDOClient(host, port, repositoryName);
		cl.registerPackage(CamelPackage.eINSTANCE);
		cl.registerPackage(OrganisationPackage.eINSTANCE);
		cl.registerPackage(DeploymentPackage.eINSTANCE);
		cl.registerPackage(ProviderPackage.eINSTANCE);
		cl.registerPackage(TypePackage.eINSTANCE);
		cl.registerPackage(ScalabilityPackage.eINSTANCE);
		cl.registerPackage(SecurityPackage.eINSTANCE);
//		cl.registerPackage(SlaPackage.eINSTANCE);
		cl.registerPackage(ExecutionPackage.eINSTANCE);
		cl.registerPackage(RequirementPackage.eINSTANCE);
		cl.registerPackage(MetricPackage.eINSTANCE);
		cl.registerPackage(UnitPackage.eINSTANCE);
		cl.registerPackage(LocationPackage.eINSTANCE);
		view = cl.openView();
		Resource res = view.getResource(resourceName);
		LOGGER.log(Level.INFO, "Obtained resource: " + res.getURI());
		EList<EObject> contents = res.getContents();
		CamelModel cm = (CamelModel) contents.get(1);
		DeploymentModel model = cm.getDeploymentModels().get(0);
		return model;
	}

	public void close() {
		if (usingCDO) {
			cl.closeView(view);
			cl.closeSession();
			System.out.println("Closed session");
		}
	}

}
