/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.mddb.cdo.client;

import camel.core.CorePackage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.CDOObjectReference;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.CDOResourceFolder;
import org.eclipse.emf.cdo.eresource.CDOResourceNode;
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.cdo.internal.common.id.CDOIDObjectLongWithClassifierImpl;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.net4j.FactoriesProtocolProvider;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.buffer.IBufferProvider;
import org.eclipse.net4j.protocol.IProtocolProvider;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.event.IListener;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;
import org.eclipse.net4j.util.security.IPasswordCredentialsProvider;
import org.eclipse.net4j.util.security.PasswordCredentialsProvider;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.SaveOptions.Builder;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EStructuralFeature.Setting;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import com.google.inject.Injector;

import eu.paasage.camel.CamelFactory;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.Model;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.dsl.CamelDslStandaloneSetup;
import eu.paasage.camel.examples.SensAppCDO;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.execution.ExecutionPackage;
import eu.paasage.camel.location.Country;
import eu.paasage.camel.location.Location;
import eu.paasage.camel.location.LocationFactory;
import eu.paasage.camel.location.LocationModel;
import eu.paasage.camel.location.LocationPackage;
import eu.paasage.camel.metric.MetricModel;
import eu.paasage.camel.metric.MetricPackage;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.DataCenter;
import eu.paasage.camel.organisation.ExternalIdentifier;
import eu.paasage.camel.organisation.OrganisationFactory;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.organisation.PaaSageCredentials;
import eu.paasage.camel.organisation.Role;
import eu.paasage.camel.organisation.RoleAssignment;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.organisation.UserGroup;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.scalability.ScalabilityModel;
import eu.paasage.camel.scalability.ScalabilityPackage;
import eu.paasage.camel.security.SecurityModel;
import eu.paasage.camel.security.SecurityPackage;
import eu.paasage.camel.requirement.RequirementPackage;
import eu.paasage.camel.type.TypeModel;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.camel.unit.UnitModel;
import eu.paasage.camel.unit.UnitPackage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * @author Eike Stepper
 */
@Slf4j
public class CDOClient
{	
	//A TCP Connector to the CDOServer
	private org.eclipse.net4j.internal.tcp.TCPClientConnector connector;
	private org.eclipse.net4j.internal.tcp.TCPSelector selector;
	//private org.eclipse.net4j.internal.jvm.JVMClientConnector connector;
	//The CDOSession that is created by the CDOClient which will be used to create CDO transactions or views
	private CDONet4jSession session;
	//Parameters representing the required connection information in order to connect to the CDOServer
	private String host, port, repositoryName;
	private boolean logging = false;
	private String userName, password;
//	private static org.apache.log4j.Logger logger;
			
	//A static parameter that maps to the configuration directory that contains the properties file of the CDOClient
	private static final String ENV_CONFIG="PAASAGE_CONFIG_DIR";
	//A static parameter that maps to a default path where the properties file of the CDOClient can be found
    private static final String DEFAULT_PAASAGE_CONFIG_DIR=".paasage";
    //A static parameter that maps to the name of the properties file
    private static final String PROPERTY_FILENAME="eu.paasage.mddb.cdo.client.properties";
    
    private String propertyFilePath;
    
    private static HashMap<String, Object> opts = new HashMap<String, Object>();
    
    static {
//    	logger = org.apache.log4j.log.getLogger(CDOClient.class);
    	XMIResToResFact();
    	opts.put(XMIResource.OPTION_SCHEMA_LOCATION, true);
    }
    
    /* This method is required for loading/exporting XMI resources*/
    private static void XMIResToResFact(){
    	Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put
		("*", 
		new XMIResourceFactoryImpl()
		{
		public Resource createResource(URI uri)
		{
		XMIResource xmiResource = new XMIResourceImpl(uri);
		return xmiResource;
		}
		});
    }
	
    /* Default constructor for the client which initiates a CDO session*/
	public CDOClient(){
		propertyFilePath = retrieveConfigurationDirectoryFullPath();
		initSession();
	}
	
	/* Constructor for the client which initiates a CDO session with the authentication 
	 * information provided*/
	public CDOClient(String userName,String password){
		this.userName = userName;
		this.password = password;
		propertyFilePath = retrieveConfigurationDirectoryFullPath();
		initSession();
	}
	
	/* Constructor for the client which obtains the path to the properties file 
	 * which will be used to read the necessary information for initiating a CDO session*/
	public CDOClient(String propertyFilePath){
		this.propertyFilePath = propertyFilePath;
		initSession();
	}
	
	/* This method is used in order to retrieve the full path to the 
	 * configuration directory which contains the properties file of the 
	 * CDOClient (which contains information to connect to the CDO Server)
	 */
	private String retrieveConfigurationDirectoryFullPath()
    {
        String propertyFilePath = System.getenv(ENV_CONFIG);
		log.info("Got path: " + propertyFilePath);
        
     // enable passing the configuration directory through -Deu.paasage.configdir=PATH JVM option
        if (propertyFilePath == null) {
          propertyFilePath = System.getProperty("eu.paasage.configdir");
          log.info("Got path: " + propertyFilePath);
        }
        
        if (propertyFilePath == null)
        {
            String home = System.getProperty("user.home");
            Path homePath = Paths.get(home);
            propertyFilePath = homePath.resolve(DEFAULT_PAASAGE_CONFIG_DIR).toAbsolutePath().toString();
        }
        return propertyFilePath;
    }
	
	/* This method is used to find the path to the property file which specifies
	 * the connection information to the CDO Server 
	 */
    private String retrievePropertiesFilePath(String propertiesFileName)
    {
        Path configPath = Paths.get(propertyFilePath);
        return configPath.resolve(propertiesFileName).toAbsolutePath().toString();
    }
    
    /* This method is used in order to load a property file of the CDOClient
     * which contains the information needed to connect to the CDOServer
     */
    private Properties loadPropertyFile()
    {
        String propertyPath = retrievePropertiesFilePath(PROPERTY_FILENAME);
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propertyPath));
        } catch (java.io.IOException e) {
            //TODO: fill props with default values for componenet
//            props.put("log4j.rootLogger","info, stdout");
//            props.put("log4j.appender.stdout"                         ,"org.apache.log4j.ConsoleAppender");
//            props.put("log4j.appender.stdout.Target"                  ,"System.out");
//            props.put("log4j.appender.stdout.layout"                  ,"org.apache.log4j.PatternLayout");
//            props.put("log4j.appender.stdout.layout.ConversionPattern","%d{ABSOLUTE} %5p %c{1}:%L - %m%n");
        }
        return props;
    }

	/* This method is called in order to get the connection information
	 * that will be used in order to be able to connect correctly to the
	 * CDO Server and create the respective CDOSession
	 */
	private void getConnectionInformation(){
		Properties props = loadPropertyFile();
		this.host = props.getProperty("host");
		this.port = props.getProperty("port");
		this.repositoryName = props.getProperty("repository", "repo1");

		String logging = props.getProperty("logging", "off");
		if (logging.equals("off"))
			this.logging = false;
		else if (logging.equals("on"))
			this.logging = true;

		if (this.logging) log.info("Got host: " + host + " port: " + port + " repository:" + repositoryName);
	}
	
	/*This method is used for initiating a CDO Session starting by obtaining
	connection information from a property file*/
	private void initSession(){
		log.warn("Creating new client {}", this);

		getConnectionInformation();

		OMPlatform.INSTANCE.setDebugging(logging);
	    OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
	    OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);

	    // Prepare receiveExecutor
	    final ThreadGroup threadGroup = new ThreadGroup("net4j"); //$NON-NLS-1$
	    ExecutorService receiveExecutor = Executors.newCachedThreadPool(new ThreadFactory()
	    {
	      public Thread newThread(Runnable r)
	      {
	        Thread thread = new Thread(threadGroup, r);
	        thread.setDaemon(true);
	        return thread;
	      }
	    });
	    
	    //IManagedContainer container = ContainerUtil.createContainer(); 
	    /*IManagedContainer container = IPluginContainer.INSTANCE;
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		//SSLUtil.prepareContainer(container);
		//LifecycleUtil.activate(container);
		CDONet4jUtil.prepareContainer(container); // Register CDO factories*/
		//container.activate();

	    // Prepare bufferProvider
	    IBufferProvider bufferProvider = Net4jUtil.createBufferPool();
	    LifecycleUtil.activate(bufferProvider);

	    IProtocolProvider protocolProvider = new FactoriesProtocolProvider(
	        new org.eclipse.emf.cdo.internal.net4j.protocol.CDOClientProtocolFactory());

	    // Prepare selector
	    selector = new org.eclipse.net4j.internal.tcp.TCPSelector();
	    //org.eclipse.net4j.internal.jvm.JVMSelector selector = new org.eclipse.net4j.internal.jvm.JVMSelector();
	    selector.activate();

	    // Prepare connector
	    connector = new org.eclipse.net4j.internal.tcp.TCPClientConnector();
	    connector.getConfig().setBufferProvider(bufferProvider);
	    connector.getConfig().setReceiveExecutor(receiveExecutor);
	    connector.getConfig().setProtocolProvider(protocolProvider);
	    connector.getConfig().setNegotiator(null);
	    connector.setSelector(selector);
	    connector.setHost(host); //$NON-NLS-1$
	    connector.setPort(Integer.parseInt(port.trim()));
	    connector.activate();
	    
	    //ITCPConnector connector = TCPUtil.getConnector(container, "0.0.0.0:2036");
	    //IConnector connector = SSLUtil.getConnector(container, "0.0.0.0:2036");
	    //JVMUtil.getAcceptor(container, "default");
	    //connector.activate();

	    // Create configuration
//	    CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
//	    configuration.setConnector(connector);
//	    configuration.setRepositoryName(repositoryName); //$NON-NLS-1$

//        IManagedContainer container = IPluginContainer.INSTANCE;
//        Net4jUtil.prepareContainer(container);
//        TCPUtil.prepareContainer(container);
//        CDONet4jUtil.prepareContainer(container);
//        CDONet4jSessionConfiguration configuration = CDONet4jUtil.createReconnectingSessionConfiguration(host + ":" + port, repositoryName, container);

		CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();

        configuration.setConnector(connector);
        configuration.setRepositoryName(repositoryName); //$NON-NLS-1$

	    //Provide security information, if supplied by user
	    //authentication, if succeeds last for the whole session - lifetime of CDOClient object
	    if (userName != null && password != null){
		    IPasswordCredentialsProvider credentialsProvider = new PasswordCredentialsProvider(userName, password);
		    configuration.setCredentialsProvider(credentialsProvider);
	    }

	    // Open session
	    session = configuration.openNet4jSession();

	    session.options().setCommitTimeout(1000);
//	    session.options().getNet4jProtocol().setTimeout(1000000000000L);

	    registerCamelPackages();
	}
	
	/* This method is used to register all Packages of Camel meta-model
	 */
	public void registerCamelPackages(){
		registerPackage(EresourcePackage.eINSTANCE);
		registerPackage(CamelPackage.eINSTANCE);
		registerPackage(ScalabilityPackage.eINSTANCE);
		registerPackage(DeploymentPackage.eINSTANCE);
		registerPackage(OrganisationPackage.eINSTANCE);
		registerPackage(ProviderPackage.eINSTANCE);
		registerPackage(SecurityPackage.eINSTANCE);
		registerPackage(ExecutionPackage.eINSTANCE);
		registerPackage(TypePackage.eINSTANCE);
		registerPackage(RequirementPackage.eINSTANCE);
		registerPackage(MetricPackage.eINSTANCE);
		registerPackage(UnitPackage.eINSTANCE);
		registerPackage(LocationPackage.eINSTANCE);
	}

	/* This method is used for registering an EPackage mapping to the domain
	 * model that will be exploited for creating or manipulating the respective
	 * domain objects. Input parameter: the EPackage to register
	 */
	public void registerPackage(EPackage pack){
		session.getPackageRegistry().putEPackage(pack);
	}

	/* This method can be used to open a CDO transaction and return it to
	 * the developer/user. The developer/user should not forget to close
	 * the respective cdo transaction in the end.
	 */
	public CDOTransaction openTransaction(){
	    	CDOTransaction trans = session.openTransaction();
	    	if (logging) log.info("Opened transaction!");
	    	return trans;
	}

	/* This method can be used to open a CDO transaction and return it to
	 * the developer/user. If the user/developer desires a validation of the
	 * models/objects updated, then he/she must set the respective boolean input
	 * parameter with the value of true. The user should not forget to close
	 * the respective cdo transaction in the end.
	 */
	public CDOTransaction openTransaction(boolean validate){
	    	CDOTransaction trans = session.openTransaction();
	    	if (logging) log.info("Opened transaction!");
	    	if (validate) trans.addTransactionHandler(new MyCDOTransactionHandler());
	    	return trans;
	}

	/* This method can be used to open a CDO view and return it to
	 * the developer/user. The developer/user should not forget to close
	 * the respective cdo view in the end.
	 */
	public CDOView openView(){
	    	CDOView view = session.openView();
	    	if (logging) log.info("Opened view!");
	    	return view;
	}

	/* This method can be used to delete an object provided that its cdoID is given
	 * as input parameter. A return of false will indicate that something went wrong with the deletion
	 * of the object. Then the respective log file must be checked to see the error message
	 */
	public boolean deleteObject(CDOID uri){
		try{
			CDOTransaction trans = session.openTransaction();
			//CDOID id = CDOIDUtil.createExternal(uri);
			//log.info("ID given: " + uri + " ID produced: " + id);
			CDOObject object = trans.getObject(uri);
			return deleteObject(object,trans,true);
		}
		catch(Exception e){
			log.error("Something went wrong while deleting object with CDOID: " + uri, e);
			//e.printStackTrace();
		}
		return false;
			
	}
	
	/* This method is used to delete an object provided that its CDOID is given
	 * as input parameter in the form of a String. The method returns true or false
	 * depending on whether the deletion was successful or not.
	 */
	public boolean deleteObject(String uri){
		CDOID id = CDOIDObjectLongWithClassifierImpl.create(uri);
		return deleteObject(id);
	}
	
	/* This method can be used to delete an object provided that it has been obtained with the
	 * transaction that is also used as input to this method. First, it obtains all
	 * references to the object and deletes them and then deletes the object from its
	 * container. Please be aware that the last input parameter dictates whether the transaction 
	 * will be committed and closed by this method in the end or not. If not, then the user
	 * should be responsible for setting this parameter as true in the last delete statement
	 * in his/her code or for committing and closing the transaction him/herself. 
	 * The method returns a value of true when the deletion of the object was successful or false
	 * otherwise. The respective log file must be inspected in the latter case.
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
					if (logging) log.info("Prev size: is: " + list.size());
					list.remove(target);
					if (logging) log.info("New size: is: " + list.size());
				}
				else{
					source.eSet(feat, null);
				}
			}
			//Get containment association and delete it
			CDOObject parent = (CDOObject)object.eContainer();
			EStructuralFeature feat = object.eContainmentFeature();
			if (logging) log.info("The feature is: " + feat);
			Object eGet = parent.eGet(feat);
			List<?> list = null;
			if (eGet instanceof List<?>){
				list = (List<?>)eGet;
				if (logging) log.info("Prev size: is: " + list.size());
				list.remove(object);
				if (logging) log.info("New size: is: " + list.size());
			}
			else{
				parent.eSet(feat, null);
			}
			if (commitAndClose){
				trans.commit();
				trans.close();
			}
			return true;
		}
		catch(Exception e){
			log.error("Something went wrong while deleting object: " + object,e);
			//e.printStackTrace();
		}
		return false;
	}
	
	/* This method is used for closing a CDO transaction. 
	 */
	public void closeTransaction(CDOTransaction trans){
		trans.close();
	}
	
	/* This method is used for closing a CDO view. 
	 */
	public void closeView(CDOView view){
		view.close();
	}
	
	/* This method is used for adding a listener to the CDOSession established. 
	 */
	public void addListener(IListener listener){
		if (!session.hasListeners()) session.options().setPassiveUpdateEnabled(true);
		session.addListener(listener);
	}
	
	/* This method is used for removing a listener to the CDOSession established. 
	 */
	public void removeListener(IListener listener){
		session.removeListener(listener);
		if (!session.hasListeners()) session.options().setPassiveUpdateEnabled(false);
	}
	
	/* This method is used to store a model into a CDOResource with a particular
	 * name. Do not need to open or close a transaction for this as the
	 * method performs them for you in a transparent manner. The input parameters 
	 * are: the model to store, the name of the CDOResource to contain it, 
	 * and a boolean indicating whether the model should be validated before being stored.
	 * If validation is on and the model is invalid, then the model will not be stored
	 * in the CDO Server/Repository. 
	 * The output of the method call indicates whether the model storage 
	 * was successful or not. In the latter, negative case, the log file must be inspected.
	 */
	public boolean storeModel(EObject model, String resourceName, boolean validate){
		CDOTransaction trans = openTransaction();
		CDOResource cdo = trans.getOrCreateResource(resourceName);
		EList<EObject> list = cdo.getContents();
		list.add(model);
		try{
			  if (validate) {
				  if (!OCLValidation.validate(model)){
					  trans.rollback();
					  trans.close();
					  return false;
				  }
			  }
			  trans.commit();
			  trans.close();
			  return true;
		}
		catch(Exception e){
			log.error("Something went wrong while storing model: " + model + " with resourceName:" + resourceName, e);
			//e.printStackTrace();
		}
		return false;
	}
	
	/* This method is used to immediately store a model from the file system to the CDO
	 * Repository at a specific resource path. The input parameters provided are the path
	 * at the file system where the model resides, the CDO resource path on which it will
	 * be stored and an indication of whether OCL constraints/domain semantics will be 
	 * checked against the model before its storage in the CDO Repository. The method
	 * returns as output true or false depending on whether the model has been successfully
	 * loaded in main memory and then stored in the CDO Repository. 
	 */
	public boolean importModel(String filePath, String resourcePath, boolean validate){
		EObject object = loadModel(filePath);
		if (object != null) return storeModel(object,resourcePath,validate);
		return false;
	}
	
	/* This method is used to immediately store a CAMEL textual model from the file system to the CDO
	 * Repository at a specific resource path. The input parameters provided are the path
	 * at the file system where the model resides, the CDO resource path on which it will
	 * be stored and an indication of whether OCL constraints/domain semantics will be 
	 * checked against the model before its storage in the CDO Repository. The method
	 * returns as output true or false depending on whether the model has been successfully
	 * loaded in main memory and then stored in the CDO Repository. 
	 */
	public boolean importTextualModel(String filePath, String resourcePath, boolean validate){
		EObject object = loadTextualModel(filePath);
		if (object != null) return storeModel(object,resourcePath,validate);
		return false;
	}
	
	/* This method is used to save a model into the file system in a specific path given as input
	 * The input parameters are: the model to store and the file path to store it in the file system.
	 * The output indicates whether the model saving was successful or not. The log file must be
	 * inspected in the latter negative case. 
	 */
	public boolean saveModel(EObject model, String pathName){
		final ResourceSet rs = new ResourceSetImpl();
		rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
		Resource res = null;
		File f = new File(pathName);
		EList<EObject> contents = null;
		if (f.exists()){
			res = rs.getResource(URI.createFileURI(pathName), true);
			contents = res.getContents();
			contents.clear();
		}
		else{
			res = rs.createResource(URI.createFileURI(pathName));
			contents = res.getContents();
		}
		if (logging) log.info("Got resource: " + res);
		contents.add(model);
		try{
			res.save(null);
			return true;
		}
		catch(Exception e){
			log.error("Something went wrong while storing model: " + model + " at path: " + pathName, e);
			//e.printStackTrace();
		}
		return false;
	}
	
	public EObject createCerifModel(){
		CamelModel cm = CamelFactory.eINSTANCE.createCamelModel();
		cm.setName("MY CAMEL MODEL");
		OrganisationModel om = OrganisationFactory.eINSTANCE.createOrganisationModel();
		om.setName("MY ORGANISATION MODEL");
		LocationModel lm = LocationFactory.eINSTANCE.createLocationModel();
		lm.setName("MY LOCATION MODEL");
		cm.getOrganisationModels().add(om);
		cm.getLocationModels().add(lm);
		EList<User> users = om.getUsers();
		EList<UserGroup> ugroups = om.getUserGroups();
		EList<Role> roles = om.getRoles();
		EList<RoleAssignment> assigns = om.getRoleAssigments();
		EList<ExternalIdentifier> ids = om.getExternalIdentifiers();
		EList<DataCenter> dcs = om.getDataCentres();
		
		
		ExternalIdentifier id1 = OrganisationFactory.eINSTANCE.createExternalIdentifier();
		id1.setIdentifier("ID1");
		ids.add(id1);
		
		ExternalIdentifier id2 = OrganisationFactory.eINSTANCE.createExternalIdentifier();
		id2.setIdentifier("ID2");
		ids.add(id2);
		
		ExternalIdentifier id3 = OrganisationFactory.eINSTANCE.createExternalIdentifier();
		id3.setIdentifier("ID3");
		ids.add(id3);
		
		User user1 = OrganisationFactory.eINSTANCE.createUser();
		user1.setLastName("User");
		user1.setFirstName("User1");
		user1.setName("User1");
		user1.setEmail("user@user1");
		EList<ExternalIdentifier> exIDs1 = user1.getExternalIdentifiers();
		exIDs1.add(id1);
		exIDs1.add(id2);
		users.add(user1);
		
		PaaSageCredentials pc1 = OrganisationFactory.eINSTANCE.createPaaSageCredentials();
		pc1.setPassword("user1");
		user1.setPaasageCredentials(pc1);
		
		User user2 = OrganisationFactory.eINSTANCE.createUser();
		user2.setFirstName("User2");
		user2.setLastName("User");
		user2.setName("User2");
		user2.setEmail("user2@User");
		users.add(user2);
		exIDs1 = user2.getExternalIdentifiers();
		//exIDs1.add(id2);
		exIDs1.add(id3);
		
		PaaSageCredentials pc2 = OrganisationFactory.eINSTANCE.createPaaSageCredentials();
		pc2.setPassword("user2");
		user2.setPaasageCredentials(pc2);
		
		CloudProvider org1 = OrganisationFactory.eINSTANCE.createCloudProvider();
		org1.setEmail("email2");
		org1.setName("Org2");
		org1.setWww("www2");
		org1.setPublic(true);
		om.setProvider(org1);
		
		UserGroup ug1 = OrganisationFactory.eINSTANCE.createUserGroup();
		ug1.setName("ug1");
		EList<User> members = ug1.getUsers();
		members.add(user1);
		ugroups.add(ug1);
		
		Role r1 = OrganisationFactory.eINSTANCE.createRole();
		r1.setName("role1");
		roles.add(r1);

		Role r2 = OrganisationFactory.eINSTANCE.createRole();
		r2.setName("role2");
		roles.add(r2);
		
		RoleAssignment ra1 = OrganisationFactory.eINSTANCE.createRoleAssignment();
		ra1.setName("MY_ROLE_ASSIGNMENT");
		ra1.setRole(r1);
		ra1.setUser(user1);
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		try{
			ra1.setAssignmentTime(ft.parse("1976-12-16"));
			ra1.setStartTime(ft.parse("1977-12-16"));
			ra1.setEndTime(ft.parse("1978-12-16"));
			log.info("End date: " + ra1.getEndTime());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		assigns.add(ra1);
		
		Country l1 = LocationFactory.eINSTANCE.createCountry();
		l1.setName("Country1");
		l1.setId("C1");
		lm.getCountries().add(l1);
		
		Country l2 = LocationFactory.eINSTANCE.createCountry();
		l2.setName("Country2");
		l2.setId("C2");
		lm.getCountries().add(l2);
		
		DataCenter dc1 = OrganisationFactory.eINSTANCE.createDataCenter();
		dc1.setName("DC1");
		dc1.setCodeName("DC1");
		dc1.setLocation(l1);
		dcs.add(dc1);
		
		DataCenter dc2 = OrganisationFactory.eINSTANCE.createDataCenter();
		dc2.setName("DC2");
		dc2.setCodeName("DC2");
		dc2.setLocation(l2);
		dcs.add(dc2);
		
		return cm;
	}
	
	/* This method is used to load a model from a particular xmi resource. The model
	 * can then be stored to the CDO Server/Repository. The method takes as input
	 * the path (as a String) where the XML file resides.   
	 */
	public static EObject loadModel(String pathName){
		  final ResourceSet rs = new ResourceSetImpl();
		  rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
		  rs.getPackageRegistry().put(CorePackage.eNS_URI, CorePackage.eINSTANCE);
		  Resource res = rs.getResource(URI.createFileURI(pathName), true);
		  log.info("Got resource: " + res);
		  EList<EObject> contents = res.getContents();
		  log.info("Contents are: " + contents);
		  
		  return contents.get(0);
	}
	
	/* This method is used to load a CAMEL textual model from the file system, whose path
	 * is provided as input. The loaded model is returned as the output of the method
	 */
	public static EObject loadTextualModel(String pathName){
		  Injector injector = new CamelDslStandaloneSetup().createInjectorAndDoEMFRegistration();
		  XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		  rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		  Resource res = rs.getResource(URI.createFileURI(pathName), true);
		  EList<EObject> contents = res.getContents();
		  log.info("Contents are: " + contents);
		  
		  return contents.get(0);
	}
	
	/* This method is used to load a model from a particular xmi resource. The model
	 * can then be stored to the CDO Server/Repository. The method takes as input
	 * the URL from which the XML file is available. This URL can point to an external
	 * web point or to an internal file or resource inside a jar file.   
	 */
	public static EObject loadModel(URL url){
		  log.info("Got url: " + url);
		  final ResourceSet rs = new ResourceSetImpl();
		  rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
		  EList<EObject> contents = null;
		  try{
			  Resource res = rs.getResource(URI.createURI(url.toURI().toString()), true);
			  log.info("Got resource: " + res);
			  contents = res.getContents();
			  log.info("Contents are: " + contents);
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  }
		  
		  return contents.get(0);
	}
	
	/* This method is used to load a model from a particular textual CAMEL resource. The model
	 * can then be stored to the CDO Server/Repository. The method takes as input
	 * the URL from which the textual CAMEL file is available. This URL can point to an external
	 * web point or to an internal file or resource inside a jar file.   
	 */
	public static EObject loadTextualModel(URL url){
		  log.info("Got url: " + url);
		  Injector injector = new CamelDslStandaloneSetup().createInjectorAndDoEMFRegistration();
		  XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
		  rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
		  EList<EObject> contents = null;
		  try{
			  Resource res = rs.getResource(URI.createURI(url.toURI().toString()), true);
			  log.info("Got resource: " + res);
			  contents = res.getContents();
			  log.info("Contents are: " + contents);
		  }
		  catch(Exception e){
			  e.printStackTrace();
		  }
		  
		  return contents.get(0);
	}
	
	/* This method is used to load a model from a particular xmi resource. The model
	 * can then be stored to the CDO Server/Repository. The method takes as input
	 * an InputStream mapping to the xmi resource to be read.   
	 */
	public static EObject loadModel(InputStream is){
		  XMIResourceImpl res = new XMIResourceImpl();
		  //res.getResourceSet().getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
		  try{
			  res.doLoad(is, null);
			  log.info("Got resource: " + res);
			  EList<EObject> contents = res.getContents();
			  log.info("Contents are: " + contents);
		  
			  return contents.get(0);
		  }
		  catch(Exception e){
			  log.error("Something went wrong while loading a model from the InputStream provided as input", e);
		  }
		  return null;
	}
	
	/* This method is used to export a model that has been stored in the CDO Server/Repository.
	 * It takes as input three parameters: (a) the name of the CDOResource, (b) the
	 * Class of the model to be exported and (c) the path of the file to be created as a String. 
	 * We must highlight that if the
	 * model required is not at the root of the CDOResource, we assume that it is 
	 * obtained from the root EObject which maps to a CamelModel and that this CamelModel
	 * does not contain other models that have the same type as the requested model (as
	 * the first model of the respective type is actually obtained). We must also
	 * note that the user is responsible of providing correct input parameters as well
	 * as ensuring that the requested model is indeed stored in the CDOResource whose
	 * name is signified in the input parameters. In case of an exception affecting the model
	 * exporting, the value of false is returned by this method and the respective log file
	 * must be inspected to check the exception raised.    
	 */
	public boolean exportModel(String resourceName, Class c, String filePath){
		  
		  CDOTransaction trans = null;
		  try{
			  FileOutputStream fos = new FileOutputStream(filePath);
			  trans = openTransaction();
			  CDOResource resource = trans.getResource(resourceName);
			  EObject obj = resource.getContents().get(0);
			  final ResourceSet rs = new ResourceSetImpl();
			  rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
			  
			  if (c.equals(CamelModel.class)){
				  resource.save(fos, opts);
			  }
			  else if (c.equals(DeploymentModel.class)){
				  if (obj instanceof DeploymentModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  DeploymentModel dm = cm.getDeploymentModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(dm);
					  res.save(fos,opts);
				  }
			  }
			  else if (c.equals(ProviderModel.class)){
				  if (obj instanceof ProviderModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  ProviderModel dm = cm.getProviderModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(dm);
					  res.save(fos,opts);
				  }
			  }
			  else if (c.equals(OrganisationModel.class)){
				  if (obj instanceof OrganisationModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  OrganisationModel dm = cm.getOrganisationModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(dm);
					  res.save(fos,opts);
				  }
			  }
			  else if (c.equals(ScalabilityModel.class)){
				  if (obj instanceof ScalabilityModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  ScalabilityModel dm = cm.getScalabilityModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(dm);
					  res.save(fos,opts);
				  }
			  }
			  else if (c.equals(ExecutionModel.class)){
				  if (obj instanceof ExecutionModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  ExecutionModel dm = cm.getExecutionModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(dm);
					  res.save(fos,opts);
				  }
			  }
			  else if (c.equals(SecurityModel.class)){
				  if (obj instanceof SecurityModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  SecurityModel dm = cm.getSecurityModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(dm);
					  res.save(fos,opts);
				  }
			  }
			  else if (c.equals(RequirementModel.class)){
				  if (obj instanceof RequirementModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  RequirementModel rm = cm.getRequirementModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(rm);
					  res.save(fos,opts);
				  }
			  }
			  else if (c.equals(MetricModel.class)){
				  if (obj instanceof MetricModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  MetricModel dm = cm.getMetricModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(dm);
					  res.save(fos,opts);
				  }
			  }
			  else if (c.equals(UnitModel.class)){
				  if (obj instanceof UnitModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  UnitModel dm = cm.getUnitModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(dm);
					  res.save(fos,opts);
				  }
			  }
			  else if (c.equals(TypeModel.class)){
				  if (obj instanceof TypeModel) resource.save(fos, opts);
				  else if (obj instanceof CamelModel){
					  CamelModel cm = (CamelModel)obj;
					  TypeModel dm = cm.getTypeModels().get(0);
					  Resource res = rs.createResource(URI.createFileURI(filePath));
					  res.getContents().add(dm);
					  res.save(fos,opts);
				  }
			  }
			  trans.close();
			  return true;
		  }
		  catch(Exception e){
			  log.error("Something went wrong while exporting resource: " + resourceName, e);
			  //e.printStackTrace();
			  if (trans != null) trans.close();
		  }
		  return false;
	  }

	/* This method is used to export a model or instance of EObject in general into a XMI file.
	 * The model/EObject must have been either created programmatically or obtained via
	 * issuing a query. The method takes as input two parameters: (a) the query results 
	 * as an EObject to be exported, (b) the path of the file to be created.
	 * Please note that this method should be called only when a respective CDO transaction 
	 * has been opened - otherwise an exception will be thrown. Any exception leads to the
	 * return of a false value and the generation of a respective error entry in the log file;
	 * otherwise, a value of true is returned.       
	 */
	public boolean exportModel(EObject model, String filePath){
		  try{
			  final ResourceSet rs = new ResourceSetImpl();
			  rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
			  Resource res = rs.createResource(URI.createFileURI(filePath));
			  res.getContents().add(model);
			  res.save(opts);
			  return true;
		  }
		  catch(Exception e){
			  log.error("Something went wrong while exporting model: " + model + " at path: " + filePath,e);
			  //e.printStackTrace();
		  }
		  return false;
	}
	
	/* This method is used to export a model or instance of EObject in general into a CAMEL textual file.
	 * The model/EObject must have been either created programmatically or obtained via
	 * issuing a query. The method takes as input two parameters: (a) the query results 
	 * as an EObject to be exported, (b) the path of the file to be created.
	 * Please note that this method should be called only when a respective CDO transaction 
	 * has been opened - otherwise an exception will be thrown. Any exception leads to the
	 * return of a false value and the generation of a respective error entry in the log file;
	 * otherwise, a value of true is returned.       
	 */
	public boolean exportTextualModel(EObject model, String filePath){
		Injector injector = new CamelDslStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		Resource resource = resourceSet.createResource(URI.createFileURI(filePath));
		EObject toSave = transformToCamelModel(model);
		resource.getContents().add(toSave);
		try {
			Builder options=SaveOptions.newBuilder();
			options.format();
			resource.save(options.getOptions().toOptionsMap());
			return true;
		} 
		catch (IOException e) {
		  log.error("Something went wrong while exporting the CDO model: " + model + " into a CAMEL textual model", e);
		}
		return false;
	}
	
	/*This method obtains a model and includes it in a CAMEL model, if it
	 * is not already. Then it returns the resulting CAMEL model. This method
	 * is useful for exporting CAMEL models in the textual-xtext form.
	 */
	private static EObject transformToCamelModel(EObject model){
		CamelModel toSave = null;
		if (! (model instanceof CamelModel)){
			toSave = CamelFactory.eINSTANCE.createCamelModel();
			String name = ((Model)model).getName();
			toSave.setName("CAMEL_" + name);
			if (model instanceof LocationModel) toSave.getLocationModels().add((LocationModel)model);
			else if (model instanceof RequirementModel) toSave.getRequirementModels().add((RequirementModel)model);
			else if (model instanceof DeploymentModel) toSave.getDeploymentModels().add((DeploymentModel)model);
			else if (model instanceof MetricModel) toSave.getMetricModels().add((MetricModel)model);
			else if (model instanceof ScalabilityModel) toSave.getScalabilityModels().add((ScalabilityModel)model);
			else if (model instanceof ExecutionModel) toSave.getExecutionModels().add((ExecutionModel)model);
			else if (model instanceof TypeModel) toSave.getTypeModels().add((TypeModel)model);
			else if (model instanceof SecurityModel) toSave.getSecurityModels().add((SecurityModel)model);
			else if (model instanceof UnitModel) toSave.getUnitModels().add((UnitModel)model);
			else if (model instanceof ProviderModel) toSave.getProviderModels().add((ProviderModel)model);
			else if (model instanceof OrganisationModel) toSave.getOrganisationModels().add((OrganisationModel)model);
		}
		else toSave = (CamelModel)model;
		return toSave;
	}
	
	/* This method exports a CDO model stored in a particular resource path into the file
	 * system at a specific path. The input parameters to be provided are the CDO resource 
	 * path from which to retrieve the model and the path in the file system to store it.
	 * The method returns as output the value of true or false depending on whether the
	 * resource exists and has been successfully saved in the file system.
	 */
	public boolean exportModel(String resourcePath, String filePath){
		CDOView view = openView();
		CDOResource resource = view.getResource(resourcePath);
		EObject model = resource.getContents().get(0);
		EObject copy = EcoreUtil.copy(model);
		boolean ok = exportModel(copy,filePath);
		view.close();
		return ok;
	}
	
	/* This method exports a CDO model stored in a particular resource path into the file
	 * system at a specific path in CAMEL textual form. The input parameters to be provided are the CDO resource 
	 * path from which to retrieve the model and the path in the file system to store it.
	 * The method returns as output the value of true or false depending on whether the
	 * resource exists and has been successfully saved in the file system.
	 */
	public boolean exportTextualModel(String resourcePath, String filePath){
		CDOView view = openView();
		CDOResource prevResource = view.getResource(resourcePath);
		EObject model = prevResource.getContents().get(0);
		EObject copy = EcoreUtil.copy(model);
		boolean ok = exportTextualModel(copy,filePath);
		view.close();
		return ok;
	}
	
	/* This method is used to run a query over the contents stored in the 
	 * CDO Store. You do not have to create a view before running the query
	 * as the view is created before the query transparently by this method 
	 * and closed when the query is finished. The user has the optional
	 * choice to store the first result of the query in a XMI file whose name
	 * is given by him/her. The input parameters for this method are: (a) the 
	 * query dialect (OCL, SQL, HQL), (b) the query String itself and (c) the
	 * name of the XMI file in which the first query result will be stored - it
	 * can be null if the user does not want to export the result.    
	 */
  public List<EObject> runQuery(String dialect, String queryStr, String fileName){
	  List<EObject> results = null;
	  if (fileName == null){
		  CDOView view = openView();
		  CDOQuery query = null;
		  query = view.createQuery(dialect, queryStr);
		  results = query.getResult(EObject.class);
		  view.close();
	  }
	  else{
		  CDOTransaction trans = openTransaction();
		  CDOQuery query = null;
		  query = trans.createQuery(dialect, queryStr);
		  results = query.getResult(EObject.class);
  		  exportModel(results.get(0),fileName);
  		  trans.close();
  	  }
  	  return results;
  }
  
  /* This method is used to close the CDOSession that was opened when creating
   * an object of this class - CDOClient. 
   */
  public void closeSession(){
	  session.close();
	  LifecycleUtil.deactivate(connector.getConfig().getBufferProvider());
	  connector.deactivate();
	  connector.close();
	  selector.deactivate();
  }
  
  /* This method is used to export a model, identified by a specific CDO resource path, 
   * along with its cross-referenced models, into the file system in a certain directory path
   * where a specific file name is given for this model. 
   * Both paths as well as the model file name are given as input to the method. The method returns true or false 
   * depending on whether the export has been successful or not. It must be highlighted here
   * that the method does not handle the cross-references of the cross-referenced models that
   * are also exported. If it is desired to also normalize these models, then the 
   * exportModelWithRefRec must be called.
   */
  public boolean exportModelWithRef(String resourcePath, String dirPath, String fileName){
	  if (logging) log.info("Exporting model identified by resource path: " + resourcePath + " along its cross-referenced models ...");
	  boolean ok = true;
	  
	  dirPath = checkDirPath(dirPath);
	  if (dirPath == null) return false;
	  
	  CDOView view = openView();
	  CDOResource res = view.getResource(resourcePath);
	  EObject obj2 = res.getContents().get(0);
	  obj2 = EcoreUtil.copy(obj2);
	  
	  Map<EObject,Collection<Setting>> map = EcoreUtil.ExternalCrossReferencer.find(obj2);
	  Hashtable<EObject,EObject> refModels = new Hashtable<EObject,EObject>();
	  for(EObject obj: map.keySet()){
		  EObject model = obj.eResource().getContents().get(0);
		  EObject newModel = null;
		  if (!refModels.containsKey(model)){
			  newModel = EcoreUtil.copy(model);
			  refModels.put(model,newModel);
			  String name = model.eResource().getURI().path();
			  if (name.charAt(0) == '/') name = name.substring(1);
			  name = name.replace('/', '_');
			  name += ".xmi";	
			  //System.out.println("fileName for cross-referenced model is: " + fileName);
			  ok = exportModel(newModel, name);
			  if (!ok){
				  log.error("exportModelWithRef: Something went wrong while attempting to export model: " + newModel + " in file path: " + name);
				  return ok;
			  }
			  File f = new File(name);
			  name = dirPath + File.separator + name;
			  f.renameTo(new File(name));
		  }
		  else newModel = refModels.get(model);
		  
		  Collection<Setting> st = map.get(obj);
		  ContainmentChain cc = new ContainmentChain(obj);
		  EObject newObj = cc.getObjectSubstitute(newModel);
		  if (logging) log.info("Previous object: " + obj + " substituted by: " + newObj + " " + newObj.eResource());
		  
		  for (Setting set: st){
			  //System.out.println("Got :" + set.getEObject() + " " + set.getEStructuralFeature() + " " + set.get(true));
			  Object target = set.get(true);
			  if (target instanceof EObject){
				  set.set(newObj);
			  }
			  else{
				  List l = (List)target;
				  int pos = l.indexOf(obj);
				  //System.out.println("Got pos: " + pos);
				  if (pos != -1){
					  l.set(pos, newObj);
				  }
			  }
		  }
	  }
	  
	  String filePath = dirPath + File.separator + fileName;
	  ok = exportModel(obj2, filePath);
	  view.close();
	  if (!ok){
		  log.error("exportModelWithRef: Something went wrong while attempting to export model: " + obj2+ " in file path: " + filePath);
	  }
	  return ok;
  }
  
  /* This method discovers all cross references models for a particular model and
   * returns them in the form of a set. It takes as input the model for which the
   * cross-referenced models must be returned and a set which indicates those
   * models that have been already discovered (which should be initially empty).
   * The latter input parameter is given also in order to avoid recursiveness
   * and enable the stack of recursive calls to finally end.
   */
  private Set<EObject> findCrossRefModels(EObject object, Set<EObject> examined){
	  if (logging) log.info("Finding cross referenced models for model: " + object + " ...");
	  Set<EObject> models = new HashSet<EObject>();
	  Map<EObject,Collection<Setting>> map = EcoreUtil.ExternalCrossReferencer.find(object);
	  examined.add(object);
	  for (EObject obj: map.keySet()){
		  EObject model = obj.eResource().getContents().get(0);
		  if (!examined.contains(model)){
			  boolean inserted = models.add(model);
			  if (inserted) models.addAll(findCrossRefModels(model,examined));
		  }
	  }
	  models.add(object);
	  if (logging) log.info("Cross references for model: " + object + " have been discovered");
	  return models;
  }
  
  /* This method fixes the external cross references of a model and then attempts to
   * export it. It takes as input the model to be processed and a table mapping
   * CDO-based models to in-memory-based corresponding ones. The latter parameter is exploited in
   * order to go (i.e., transform) from external references in terms of CDO-based models 
   * to external references with respect to in-memory-based models.    
   */
  private void handleModelExportWithRef(EObject object, Hashtable<EObject,EObject>refModels){
	  if (logging) log.info("Model : " + object + " will be exported after its cross-references are updated to map to models also exported in the file system ...");
	  EObject nModel = refModels.get(object);
	  Map<EObject,Collection<Setting>> map = EcoreUtil.ExternalCrossReferencer.find(nModel);
	  for(EObject obj: map.keySet()){
		  EObject model = obj.eResource().getContents().get(0);
		  EObject newModel = refModels.get(model);
		  
		  if (logging) log.info("Previous object: " + obj + " and new model is: " + newModel + " " + newModel.eResource());
		  Collection<Setting> st = map.get(obj);
		  ContainmentChain cc = new ContainmentChain(obj);
		  EObject newObj = cc.getObjectSubstitute(newModel);
		  if (logging) log.info("Previous object: " + obj + " substituted by: " + newObj + " " + newObj.eResource());
		  
		  for (Setting set: st){
			  //System.out.println("Got :" + set.getEObject() + " " + set.getEStructuralFeature() + " " + set.get(true));
			  Object target = set.get(true);
			  if (target instanceof EObject){
				  set.set(newObj);
			  }
			  else{
				  List l = (List)target;
				  int pos = l.indexOf(obj);
				  //System.out.println("Got pos: " + pos);
				  if (pos != -1){
					  l.set(pos, newObj);
				  }
			  }
		  }
	  }
	  
	  //exportRefModel(object, refModels.get(object));
  }
 
  /* This method returns a file name out of the resource containing the model
   * which is used as input parameter along with the postFix indicating the
   * type of model to be exported (or loaded from) in the file system. 
   */
  private String getFileNameFromPreviousModel(EObject model, String postFix){
	  String fileName = model.eResource().getURI().path();
	  if (fileName.charAt(0) == '/') fileName = fileName.substring(1);
	  fileName = fileName.replace('/', '_');
	  fileName += postFix;
	  return fileName;
  }
  
  /* This method obtains a directory path as a String and creates the respective directory
   * if it does not exist. It returns this path, upon success, with no file separator character
   * in the end
   */
  private String checkDirPath(String dirPath){
	  if (dirPath == null || dirPath.trim().equals("")) dirPath = ".";
	  else{
		  if (dirPath.charAt(dirPath.length()-1) == File.separatorChar) dirPath = dirPath.substring(0, dirPath.length()-1);
		  File dir = new File(dirPath);
		  if (!dir.exists()){
			  boolean ok = dir.mkdir();
			  if (!ok){
				  log.error("exportModelWithRefRec: Directory with path " + dirPath + " could not be created");
				  return null;
			  }
		  }
	  }
	  return dirPath;
  }
  
  /* This method attempts to export in the file system a particular model identified by
   * a certain CDO resource path along its cross-referenced models in a recursive way such
   * that all models exported do not contain cross-references to CDO models. This can enable
   * the loading and exploitation of the full information of all models without any
   * dependency on the opening of a CDO view or transaction. The method takes as input the
   * resource path of the model to be exported as well as a boolean value indicating
   * whether xtext/textual or xmi models should be generated and returns true or 
   * false depending on the result of the exporting. It must be noted that all exported models have a fixed
   * file name and are stored in the current directory. 
   */
  public boolean exportModelWithRefRec(String resourcePath, String dirPath, boolean xtext){
	  if (logging) log.info("Model in CDO resource path: " + resourcePath + " is being exported along with its (external) cross-referenced models");
	  boolean ok = true;
	  try{
		  dirPath = checkDirPath(dirPath);
		  if (dirPath == null) return false;
		  
		  CDOView view = openView();
		  CDOResource res = view.getResource(resourcePath);
		  EObject obj2 = res.getContents().get(0);
		  
		  Set<EObject> examined = new HashSet<EObject>(); 
		  Set<EObject> models = findCrossRefModels(obj2,examined);
		  Hashtable<EObject,EObject> refModels = new Hashtable<EObject,EObject>();
		  for (EObject model: models){
			  EObject newModel = EcoreUtil.copy(model);
			  refModels.put(model, newModel);
		  }
		  for (EObject model: models){
			  handleModelExportWithRef(model,refModels);
		  }
		  if (xtext) ok = exportTextualModelMassive(refModels,dirPath);
		  else ok = exportModelMassive(refModels,dirPath);
		  if (ok){
			  if (logging) log.info("Model in resource path: " + resourcePath + " successfully exported");
		  }
		  
		  view.close();
		  
	  }
	  catch(Exception e){
		  log.error("Something went wrong while exporting along with cross-referenced models the model identified by the CDO resource path: " + resourcePath, e);
	  }
	  return ok;
  }
  
  /* This method exports in memory models which cross-reference each other into 
   * the file system where their name is derived from the name of the previous
   * CDO models from which they were copied. It takes as input a mapping from
   * previous to new models to be stored as well as the file system path of the
   * directory in which they will be stored. Returns a boolean value indicating
   * whether the exporting was successful or not.
   */
  
  private boolean exportModelMassive(Hashtable<EObject,EObject> refModels, String dirPath){
		final ResourceSet resourceSet = new ResourceSetImpl();
		resourceSet.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
		for (EObject prevModel: refModels.keySet()){
			EObject newModel = refModels.get(prevModel);
			Resource resource = resourceSet.createResource(URI.createFileURI(getFileNameFromPreviousModel(prevModel,".xmi")));
			EObject toSave = transformToCamelModel(newModel);
			resource.getContents().add(toSave);
		}
		for (Resource resource: resourceSet.getResources()){
			if (logging) log.info("Attempting to save resource: " + resource + " " + resource.getContents().size());
			try {
				resource.save(opts);
			} 
			catch (IOException e) {
			  log.error("Something went wrong while exporting the CDO model: " + resource.getContents().get(0) + " into a CAMEL textual model", e);
			  return false;
			}
		}	
		if (!dirPath.equals(".")) moveFiles(refModels.keySet(),dirPath,".xmi");
		return true;
	}
  
  /* This method exports in memory models which cross-reference each other into 
   * the file system where their name is derived from the name of the previous
   * CDO models from which they were copied. It takes as input a mapping from
   * previous to new models to be stored as well as the file system path of the
   * directory in which they will be stored. Returns a boolean value indicating
   * whether the exporting was successful or not.
   */
  private boolean exportTextualModelMassive(Hashtable<EObject,EObject> refModels, String dirPath){
		Injector injector = new CamelDslStandaloneSetup().createInjectorAndDoEMFRegistration();
		XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
		for (EObject prevModel: refModels.keySet()){
			EObject newModel = refModels.get(prevModel);
			Resource resource = resourceSet.createResource(URI.createFileURI(getFileNameFromPreviousModel(prevModel,".camel")));
			EObject toSave = transformToCamelModel(newModel);
			resource.getContents().add(toSave);
		}
		for (Resource resource: resourceSet.getResources()){
			if (logging) log.info("Attempting to save resource: " + resource + " " + resource.getContents().size());
			try {
				Builder options=SaveOptions.newBuilder();
				options.format();
				resource.save(options.getOptions().toOptionsMap());
			} 
			catch (IOException e) {
			  log.error("Something went wrong while exporting the CDO model: " + resource.getContents().get(0) + " into a CAMEL textual model", e);
			  return false;
			}
  		}
		if (!dirPath.equals(".")) moveFiles(refModels.keySet(),dirPath,".camel");
		return true;
	}
  
  /* This method is used to move textual or xmi files previously generated into the
   * current directory to the directory pointed out by the second input arguments.
   * The rest of the input parameters maps to models and respective file postfix for
   * them which are needed in order to recreate the exact previous name of the file 
   * which has to be moved.
   * 
   */
  private void moveFiles(Set<EObject> refModels, String dirPath, String postfix){
	  for (EObject prevModel: refModels){
		  	String prevFileName = getFileNameFromPreviousModel(prevModel,postfix);
		  	String newFileName = dirPath + File.separator + prevFileName;
		  	//System.out.println("New fileName is: " + newFileName + " prevFileName is: " + prevFileName);
			File f = new File(prevFileName);
			f.renameTo(new File(newFileName));
		}
  }
  
  /* This method gets the content of a CDO Repository or a certain CDO Resource folder
   * and returns all the root models contained in it. 
   */
  private Set<EObject> getAllModels(CDOResourceNode[] nodes){
	  if (logging) log.info("Getting all models from CDO Repository ...");
	  Set<EObject> models = new HashSet<EObject>();
	  for (CDOResourceNode node: nodes){
		  if (node instanceof CDOResource){
			  CDOResource res = (CDOResource)node;
			  EList<EObject> contents = res.getContents();
			  if (contents != null && !contents.isEmpty())
				  models.add(contents.get(0));
		  }
		  else if (node instanceof CDOResourceFolder){
			  CDOResourceFolder folder = (CDOResourceFolder)node;
			  EList<CDOResourceNode> newNodes = folder.getNodes();
			  if (newNodes != null && !newNodes.isEmpty()){
				  CDOResourceNode[] nextNodes = new CDOResourceNode[newNodes.size()];
				  int i = 0;
				  for (CDOResourceNode nd: newNodes) nextNodes[i++] = nd;
				  models.addAll(getAllModels(nextNodes));
			  }
		  }
	  }
	  if (logging) log.info("All CDO models retrieved");
	  
	  return models;
  }
  
  /* This method export the whole content of a CDO Repository into a specific zip file that
   * is saved in the file system at a particular directory path which is given as input by
   * the user under the file name "cdo.zip". The second input parameter indicates whether
   * the exported models are in a xtext/textual or xmi form. It returns true or false 
   * depending on the outcome of the exporting.
   */
  public boolean exportCDOContent(String dirPath, boolean xtext){
	  if (logging) log.info("Exporting CDOContent as a zip file in file directory path: " + dirPath + " ...");
	  boolean ok = true;
	  
	  dirPath = checkDirPath(dirPath);
	  if (dirPath == null) return false;
	  
		  CDOView view = openView();
		  Set<EObject> models = getAllModels(view.getElements());
		  //System.out.println("The cross referenced models are: " + models);
		  Hashtable<EObject,EObject> refModels = new Hashtable<EObject,EObject>();
		  for (EObject model: models){
			  //System.out.println("Creating a copy for model: " + model);
			  EObject newModel = EcoreUtil.copy(model);
			  refModels.put(model, newModel);
			  //exportRefModel(model,refModels.get(model));
		  }
		  for (EObject model: models){
			  //System.out.println("Handling dependencies for model: " + model);
			  handleModelExportWithRef(model,refModels);
		  }
		  if (xtext) ok = exportTextualModelMassive(refModels,".");
		  else ok = exportModelMassive(refModels,".");
			  
		  view.close();
		  if (ok){
			try{  
			  if (xtext) {
				  MyIOUtils.createZipArchive(".", ".camel", false, new FileOutputStream(dirPath + File.separator + "cdo.zip"));
				  MyIOUtils.deleteFiles(".", ".camel");
			  }
			  else{
				  MyIOUtils.createZipArchive(".", ".xmi", false, new FileOutputStream(dirPath + File.separator + "cdo.zip"));
				  MyIOUtils.deleteFiles(".", ".xmi");
			  }
			  if (logging) log.info("CDOContent successfully exported in file directory path: " + dirPath);
			  return true;
			}
			catch(Exception e){
			  log.error("Something went wrong while exporting whole CDO content",e);
			  ok = false;
			}
		  }
	  return ok;
  }
  
  /* This method nullifies all external cross-references of a file-based model and
   * then stores it into the CDO repository. It takes as input the model itself,
   * the CDO resource path on which to store it and the CDO transaction used to
   * perform this storage. 
   */
  private void importModelNoRef(EObject model,String name, CDOTransaction trans){
	  if (logging) log.info("Importing model with name: " + name + " in CDO by also nulling external references ...");
	  Map<EObject,Collection<Setting>> map = EcoreUtil.ExternalCrossReferencer.find(model);
	  CDOResource res = trans.getOrCreateResource(name);
	  EList<EObject> contents = res.getContents();
	  for(EObject obj: map.keySet()){
		  Collection<Setting> st = map.get(obj);
		  for (Setting set: st){
			  //System.out.println("GotX :" + set.getEObject() + " " + set.getEStructuralFeature() + " " + set.get(true));
			  Object target = set.get(true);
			  if (target instanceof Location){
				  Location loc = (Location)target;
				  //System.out.println("The target object is location: " + loc + " with id: " + loc.getId());
				  if (target instanceof Country){
					  Country c = (Country)target;
					  //System.out.println("The target object is also a country with name: " + c.getName());
				  }
			  }
			  if (target instanceof EObject){
				  set.set(null);
			  }
			  else{
				  List l = (List)target;
				  boolean ok = l.remove(obj);
				  if (!ok) System.out.println("Could not remove object: " + obj + " from list: " + l);
				  //l.clear();
			  }
		  }
	  }
	  contents.add(model);
	  if (logging) log.info("Importing of model with name: " + name + " in CDO finished");
  }
  
  /* This method seeks to find a resource in a particular table mapping each resource to
   * a specific CDO resource path. If this resource is discovered, then the respective
   * path is returned.
   */
  private String getCDOResourceName(Resource res, Hashtable<Resource,String> modelToName){
	  for (Resource r: modelToName.keySet()){
		  if (r.getURI().equals(res.getURI())) return modelToName.get(r);
	  }
	  return null;
  }
  
  /* This method fixes the cross-references of a CDO model according to references of the 
   * file-based model from which it was derived. As each of the references maps to a model
   * which has been stored in the CDO Repository, the method just has to find corresponding
   * objects in the cross-references which map to such a CDO model. The method takes as
   * input the cdo resource path on which the model has been stored, a table mapping cdo
   * resource path to CDO models (mapping to cross-references of the CDO model), a table
   * mapping a file-based resource to the cdo resource path on which its CDO counterpart
   * has been stored and the transaction under which the cross-reference fixing is performed.  
   */
  private void fixModelRefs(String modelName, Hashtable<String,EObject> refModels, Hashtable<Resource,String> modelToName, CDOTransaction trans){
	  try{
		  if (logging) log.info("Fixing model cross references in CDO for model: " + modelName + " ...");
		  CDOResource res = trans.getOrCreateResource(modelName);
		  EObject prevModel = refModels.get(modelName);
		  Map<EObject,Collection<Setting>> map = EcoreUtil.ExternalCrossReferencer.find(prevModel);
		  EObject newModel = res.getContents().get(0);
		  
		  for(EObject obj: map.keySet()){
			  //EObject model = obj.eResource().getContents().get(0);
			  //System.out.println(obj.eResource() + " " + model + " new resource name is: " + getCDOResourceName(obj.eResource(), modelToName));
			  //System.out.println("Mapped resource is: " + obj.eResource() + " for object: " + obj);
			  EObject cdoModel = trans.getOrCreateResource(getCDOResourceName(obj.eResource(), modelToName)).getContents().get(0);
			  
			  //System.out.println("Previous object: " + obj + " and new model is: " + newModel);
			  Collection<Setting> st = map.get(obj);
			  ContainmentChain cc = new ContainmentChain(obj);
			  EObject newObj = cc.getObjectSubstitute(cdoModel);
			  //System.out.println("Previous object2: " + obj + " substituted by: " + newObj);
			  
			  for (Setting set: st){
				  //System.out.println("Got2 :" + set.getEObject() + " " + set.getEStructuralFeature() + " " + set.get(true));
				  Object target = set.get(true);
				  ContainmentChain cc2 = new ContainmentChain(set.getEObject());
				  //System.out.println("New model is: " + newModel);
				  EObject toSub = cc2.getObjectSubstitute(newModel);
				  if (target instanceof EObject){
					  toSub.eSet(set.getEStructuralFeature(), newObj);
				  }
				  else{
					  List l = (List)toSub.eGet(set.getEStructuralFeature());
					  int pos = l.indexOf(obj);
					  //System.out.println("Got pos: " + pos);
					  if (pos != -1){
						  l.set(pos, newObj);
					  }
					  else l.add(newObj);
				  }
			  }
		  }
		  if (logging) log.info("Cross references in CDO fixed for model: " + modelName);
	  }
	  catch(Exception e){
		  log.error("Something went wrong while fixing cross references for model: " + modelName, e);
	  }
  }
  
  /* This method takes a zip file with the (possible) content of a CDO Repository/Store (which has
   * been previously exported) which is identified by the file path given as input and
   * stores all XMI/textual-xtext files contained in it in the CDO Repository, where a boolean
   * input parameter indicates the form of the files to be stored. This functionality useful for restoring
   * a previous content of a CDO Repository which has been exported for back up reasons. 
   * The method either returns true or false depending on whether the content has been
   * successfully imported as a whole or not.   
   */
  public boolean loadCDOContent(String filePath, boolean xtext){
	  try{
		if (logging) log.info("Loading content into CDO Repository from file path: " + filePath + " ...");
	    ZipFile zipFile = new ZipFile(filePath);

	    Enumeration<? extends ZipEntry> entries = zipFile.entries();
	    Hashtable<String,EObject> models = new Hashtable<String,EObject>();
	    Hashtable<Resource,String> modelToName = new Hashtable<Resource,String>();
		
	    File dir = new File("temp");
	    dir.mkdir();
	    
	    while(entries.hasMoreElements()){
	        ZipEntry entry = entries.nextElement();
	        String name = entry.getName();
	        InputStream stream = zipFile.getInputStream(entry);
	        File f = new File("temp/" + name);
	        MyIOUtils.loadInputStream(f,stream);
	    }
	    if (xtext){
	    	Injector injector = new CamelDslStandaloneSetup().createInjectorAndDoEMFRegistration();
			XtextResourceSet rs = injector.getInstance(XtextResourceSet.class);
			rs.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE);
			List<String> names = new ArrayList<String>();
		    for (File f: dir.listFiles()){
		    	String name = f.getName();
		    	name = name.replace("_","/");
		    	int index = name.indexOf(".camel");
		    	name = name.substring(0,index);
		    	names.add(name);
		    	try{
					  Resource res = rs.getResource(URI.createURI(f.toURI().toString()), true);
					  if (logging) log.info("Got resource: " + res);
				  }
				  catch(Exception e){
					  e.printStackTrace();
				  }
		    }
		    EList<Resource> resources = rs.getResources();
		    for (int i = 0; i < resources.size(); i++){
		    	Resource res = resources.get(i);
		    	String name = names.get(i);
		    	EObject model = res.getContents().get(0);
		    	models.put(name,model);
		    	modelToName.put(model.eResource(), name);
		    }
	    }
	    else{
		    for (File f: dir.listFiles()){
		    	String name = f.getName();
		    	name = name.replace("_","/");
		    	int index = name.indexOf(".xmi");
		    	name = name.substring(0,index);
		    	EObject model = loadModel(f.toURI().toURL());
		    	models.put(name,model);
		    	//System.out.println("EResource is: " + model.eResource());
		    	modelToName.put(model.eResource(), name);
		    }
	    }
	    CDOTransaction trans = openTransaction();
	    for (String modelName: models.keySet()){
	    	EObject object = EcoreUtil.copy(models.get(modelName));
	    	//System.out.println("Created model: " + object + " contained in: " + object.eResource());
	    	importModelNoRef(object,modelName,trans);
	    }
	    for (String modelName: models.keySet()){
	    	fixModelRefs(modelName,models,modelToName,trans);
	    }
	    trans.commit();
	    trans.close();
	    
	    //Delete everything in the end
	    for (File f: dir.listFiles()) f.delete();
	    dir.delete();
	    
	    if (logging) log.info("Content into CDO Repository loaded ...");
	    return true;
	  }
	  catch(Exception e){
		  log.error("Something went wrong while loading content into CDO Repository", e);
	  }
	  return false;
  }
  
  /* This method transforms a XMI model to a textual CAMEL model. It takes as input
   * the path in the file system for both the XMI model to be transformed and the
   * CAMEL model to be generated and stored. The method returns the value of true
   * or false depending on whether the transformation was successful or not
   */
  public static boolean xmiToCAMEL(String xmiPath, String camelPath){
	  EObject model = loadModel(xmiPath);
	  Injector injector = new CamelDslStandaloneSetup().createInjectorAndDoEMFRegistration();
	  XtextResourceSet resourceSet = injector.getInstance(XtextResourceSet.class);
	  Resource resource = resourceSet.createResource(URI.createFileURI(camelPath));
	  EObject toSave = transformToCamelModel(model);
	  resource.getContents().add(toSave);
	  try {
		Builder options=SaveOptions.newBuilder();
		options.format();
		resource.save(options.getOptions().toOptionsMap());
		return true;
	  } 
	  catch (IOException e) {
		  log.error("Something went wrong while transforming XMI model to CAMEL one", e);
	  }
	  return false;
  }

//	public static void main(String[] args) {
//		camel.core.CamelModel camelModel = (camel.core.CamelModel) CDOClient.loadModel("C:\\Users\\Paweł Szkup\\runtime-EclipseXtext\\camel-oxygen\\src-gen\\CRMCamelModel.xmi");
//	}


  /* Main method which demonstrates the functionality of the CDOClient through
   * the usage of the various methods offered by it, when no arguments are given 
   * to it. Otherwise, it takes as input the name of the method plus any additional
   * parameter required by it and executes it. The latter exploitation is useful for
   * a command line usage of the CDOClient API. */
//  public static void main(String[] args){
//	  //Create the CDOClient
//	  CDOClient cl = new CDOClient("Administrator","0000");
//	  if (args.length == 0){
//		  //Creating & adding a listener to the session
//		  MyListener listener = new MyListener();
//		  cl.addListener(listener);
//		  //Create a particular model (CERIF)
//		  EObject model = cl.createCerifModel();
//		  //Store the model under a CDOResource with a particular name
//		  cl.storeModel(model,"cerif",true);
//		  //Create a particular model (SensApp)
//		  model = SensAppCDO.getSensAppCamelModel();
//		  //Store the model under a CDOResource with a particular name
//		  cl.storeModel(model,"sensAppResource1",true);
//		  //Load a model from a XMI resource situated inside jar file
//		  URL url = cl.getClass().getResource("/Scalarm.xmi");
//		  model = cl.loadModel(url);
//		  //Store the model under a CDOResource with a particular name
//		  cl.storeModel(model,"scalarmResource1",true);
//
//		  //Create transaction and use this to delete object
//		  CDOTransaction trans = cl.openTransaction(true);
//		  User user = trans.createQuery("hql", "select u from User u where u.firstName='User2'").getResult(User.class).get(0);
//		  cl.deleteObject(user.getPaasageCredentials(),trans,false);
//		  cl.deleteObject(user,trans,true);
//		  //Create view, get cdoID and then delete object by using this id as input
//		  CDOView view = cl.openView();
//		  ExternalIdentifier id1 = view.createQuery("hql", "select id from ExternalIdentifier id where id.identifier='ID2'").getResult(ExternalIdentifier.class).get(0);
//		  CDOID cdoID1 = id1.cdoID();
//		  view.close();
//		  cl.deleteObject(cdoID1);
//
//		  //Check that the objects have been deleted
//		  view = cl.openView();
//		  List<ExternalIdentifier> types = view.createQuery("hql","select id from ExternalIdentifier id where (id.identifier='ID2' or id.identifier='ID3')").getResult(ExternalIdentifier.class);
//		  log.info("Did we get the ids requested?: " + !(types.isEmpty()));
//		  List<User> users = view.createQuery("hql", "select u from User u where u.firstName='User2'").getResult(User.class);
//		  log.info("Did we get the users requested?: " + !(users.isEmpty()));
//		  view.close();
//
//		  /*Run a query - three ways are shown here: (i) ocl query,
//		   * (ii) hql query and (iii) get all contents of a CDO Resource
//		   * and process them to e.g. find the one you are looking for. Please
//		   * notice that for the third way, the user/developer has to first create
//		   * a view, get the contents of the CDOResource and process them and
//		   * finally close the view. If the contents have to be modified, then
//		   * a transaction should be opened instead (and finally closed when
//		   * processing has been ended).
//		   */
//		  //OCL query plus exporting of first result
//		  List<EObject> results = cl.runQuery("ocl","camel::organisation::User.allInstances()","queryResult.xmi");
//		  log.info("The results of the query are:" + results);
//		  //HQL query with no exporting
//		  results = cl.runQuery("hql","select dm from DeploymentModel dm",null);
//		  log.info("The results of the query are:" + results);
//		  //Obtaining all contents of a CDOResource
//		  view = cl.openView();
//		  EList<EObject> objs = view.getResource("sensAppResource1").getContents();
//		  log.info("The objs stored are: " + objs);
//		  cl.closeView(view);
//		  //Store the DeploymentModel of the loaded and stored CamelModel as an XMI file
//		  cl.exportModel("sensAppResource1", DeploymentModel.class, "output/SensApp_DepModel.xmi");
//		  //Remove listener as no longer needed
//		  cl.removeListener(listener);
//	  }
//	  else{
//		  String method = args[0];
//		  boolean ok = false;
//		  if (method.equals("importModel") || method.equals("importTextualModel")){
//			  if (args.length >= 3){
//				  String filePath = args[1];
//				  String resourcePath = args[2];
//				  boolean validate = true;
//				  if (args.length == 4){
//					  validate = Boolean.parseBoolean(args[3]);
//				  }
//				  else log.warn("3 or 4 arguments were expected. At most the 4 first arguments are taken into account");
//				  if (method.equals("importModel"))
//					  ok = cl.importModel(filePath, resourcePath, validate);
//				  else if (method.equals("importTextualModel"))
//					  ok = cl.importTextualModel(filePath, resourcePath, validate);
//			  }
//			  else{
//				  log.error(method + " was called with a wrong number of arguments");
//			  }
//		  }
//		  else if (method.equals("xmiToCAMEL")){
//			  if (args.length >= 3){
//				  String xmiPath = args[1];
//				  String camelPath = args[2];
//				  if (args.length > 3)
//					  log.warn("3 arguments were expected. At most the 4 first arguments are taken into account");
//					  ok = xmiToCAMEL(xmiPath, camelPath);
//			  }
//			  else{
//				  log.error(method + " was called with a wrong number of arguments");
//			  }
//		  }
//		  else if (method.equals("exportModel") || method.equals("exportTextualModel")){
//			  if (args.length == 3){
//				  String resourcePath = args[1];
//				  String filePath = args[2];
//				  if (method.equals("exportModel"))
//					  ok = cl.exportModel(resourcePath, filePath);
//				  else if (method.equals("exportTextualModel"))
//					  ok = cl.exportTextualModel(resourcePath, filePath);
//			  }
//			  else log.error(method + " was called with a wrong number of arguments");
//		  }
//		  else if (method.equals("exportModelWithRefRec") || method.equals("exportModelWithRef")){
//			  if (args.length == 4){
//				  String resourcePath = args[1];
//				  String dirPath = args[2];
//				  String arg3 = args[3];
//				  if (method.equals("exportModelWithRef"))
//					  ok = cl.exportModelWithRef(resourcePath, dirPath, arg3);
//				  else if (method.equals("exportModelWithRefRec")){
//					  boolean xtext = Boolean.parseBoolean(arg3);
//					  ok = cl.exportModelWithRefRec(resourcePath, dirPath, xtext);
//				  }
//			  }
//			  else log.error(method + " was called with a wrong number of arguments");
//		  }
//		  else if (method.equals("exportCDOContent") || method.equals("loadCDOContent")){
//			  if (args.length == 3){
//				  String arg1 = args[1];
//				  boolean xtext = Boolean.parseBoolean(args[2]);
//				  if (method.equals("exportCDOContent"))
//					  ok = cl.exportCDOContent(arg1,xtext);
//				  else if (method.equals("loadCDOContent"))
//					  ok = cl.loadCDOContent(arg1,xtext);
//			  }
//			  else log.error(method + " was called with a wrong number of arguments");
//		  }
//		  else log.error(method + " does not exist or cannot be executed in a command line manner");
//		  if (ok) log.info(method + " was successfully performed");
//	  }
//	  //Close the CDOSession once you are done
//	  cl.closeSession();
//	  System.exit(1);
//  }
}