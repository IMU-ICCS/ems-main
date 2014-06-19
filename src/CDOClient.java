
import org.eclipse.emf.cdo.CDOObject;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.CDOResourceFolder;
import org.eclipse.emf.cdo.eresource.impl.CDOResourceFactoryImpl;
import org.eclipse.emf.cdo.eresource.impl.CDOResourceImpl;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.net4j.FactoriesProtocolProvider;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.buffer.IBufferProvider;
import org.eclipse.net4j.protocol.IProtocolProvider;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;
import org.eclipse.net4j.util.security.PasswordCredentialsProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.EClassImpl;
import org.eclipse.emf.ecore.impl.EPackageRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.emf.internal.cdo.CDOObjectImpl;

import camel.cerif.AllowedActions;
import camel.cerif.CerifFactory;
import camel.cerif.CerifModel;
import camel.cerif.CerifPackage;
import camel.cerif.CloudProvider;
import camel.cerif.DataCenter;
import camel.cerif.Entity;
import camel.cerif.ExternalIdentifier;
import camel.cerif.Location;
import camel.cerif.Organization;
import camel.cerif.Permission;
import camel.cerif.ResourceGroup;
import camel.cerif.Role;
import camel.cerif.RoleAssignment;
import camel.cerif.User;
import camel.cerif.UserGroup;
import camel.Application;
import camel.CamelModel;
import camel.CamelPackage;
import camel.CamelFactory;
import camel.Action;
import camel.ActionType;
import camel.DataObject;
import camel.MonetaryUnit;
import camel.Requirement;
import camel.RequirementGroup;
import camel.RequirementOperatorType;
import camel.ScalabilityPolicy;
import camel.ScalabilityType;
import camel.TimeIntervalUnit;
import camel.Unit;
import camel.UnitType;
import camel.Unitless;

import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit.*;

import javax.xml.parsers.DocumentBuilderFactory;

/**
 * @author Eike Stepper
 */
public class CDOClient
{	
	//A CDOView to be used to run queries over the CDOStore
	private CDOView view;
	//A CDOTransaction to be used for storing models into the CDOStore
	private CDOTransaction trans;
	//A TCP Connector to the CDOServer
	private org.eclipse.net4j.internal.tcp.TCPClientConnector connector;
	//The CDOSession that is created by the CDOClient which will be used to create CDO transactions or views
	private CDOSession session;
	//Configuration object for the CDO session
	private CDONet4jSessionConfiguration configuration = null;
	//Parameters representing the required connection information in order to connect to the CDOServer
	private String host, port;
	
	//A static parameter that maps to the configuration directory that contains the properties file of the CDOClient
	private static final String ENV_CONFIG="PAASAGE_CONFIG_DIR";
	//A static parameter that maps to a default path where the properties file of the CDOClient can be found
    private static final String DEFAULT_PAASAGE_CONFIG_DIR =".paasage";
	
    /*Default constructor for the client which initiates a CDO session*/
	public CDOClient(){
		initSession();
	}
	
	/* This method is used in order to retrieve the full path to the 
	 * configuration directory which contains the properties file of the 
	 * CDOClient (which contains information to connect to the CDO Server)
	 */
	private static String retrieveConfigurationDirectoryFullPath()
    {
        String paasageConfigurationFullPath = System.getenv(ENV_CONFIG);
        System.out.println("Got path: " + paasageConfigurationFullPath);
        if (paasageConfigurationFullPath == null)
        {
            String home = System.getProperty("user.home");
            Path homePath = Paths.get(home);
            paasageConfigurationFullPath = homePath.resolve(DEFAULT_PAASAGE_CONFIG_DIR).toAbsolutePath().toString();
        }
        return paasageConfigurationFullPath;
    }
	
	/* This method is used to find the path to the property file which specifies
	 * the connection information to the CDO Server 
	 */
    private static String retrievePropertiesFilePath(String propertiesFileName)
    {
        Path configPath = Paths.get(retrieveConfigurationDirectoryFullPath());
        return configPath.resolve(propertiesFileName).toAbsolutePath().toString();
    }
    
    /* This method is used in order to load a property file of the CDOClient
     * which contains the information needed to connect to the CDOServer
     */
    public static Properties loadPropertyFile()
    {
        String propertyPath = retrievePropertiesFilePath("eu.paasage.wp4.client.properties");
        Properties props = new Properties();
        try {
            props.load(new FileInputStream(propertyPath));
        } catch (java.io.IOException e) {
            //TODO: fill props with default values for componenet
            props.put("log4j.rootLogger","debug, stdout");
            props.put("log4j.appender.stdout"                         ,"org.apache.log4j.ConsoleAppender");
            props.put("log4j.appender.stdout.Target"                  ,"System.out");
            props.put("log4j.appender.stdout.layout"                  ,"org.apache.log4j.PatternLayout");
            props.put("log4j.appender.stdout.layout.ConversionPattern","%d{ABSOLUTE} %5p %c{1}:%L - %m%n");
        }
        return props;
    }

	/* This method is called in order to get the connection information
	 * that will be used in order to be able to connect correctly to the
	 * CDO Server and create the respective CDOSession
	 */
	private void getConnectionInformation(){
		Properties props = loadPropertyFile();
		host = props.getProperty("host");
		port = props.getProperty("port");
		System.out.println("Got host: " + host + " port: " + port);
	}
	
	/*This method is used for initiating a CDO Session starting by obtaining
	connection information from a property file*/
	private void initSession(){
		getConnectionInformation();
		OMPlatform.INSTANCE.setDebugging(true);
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

	    // Prepare bufferProvider
	    IBufferProvider bufferProvider = Net4jUtil.createBufferPool();
	    LifecycleUtil.activate(bufferProvider);

	    IProtocolProvider protocolProvider = new FactoriesProtocolProvider(
	        new org.eclipse.emf.cdo.internal.net4j.protocol.CDOClientProtocolFactory());

	    // Prepare selector
	    org.eclipse.net4j.internal.tcp.TCPSelector selector = new org.eclipse.net4j.internal.tcp.TCPSelector();
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

	    // Create configuration
	    CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
	    configuration.setConnector(connector);
	    configuration.setRepositoryName("repo1"); //$NON-NLS-1$

	    // Open session
	    session = configuration.openNet4jSession();
	    /*session.getPackageRegistry().putEPackage(CamelPackage.eINSTANCE);*/
	    /*session.getPackageRegistry().putEPackage(CerifPackage.eINSTANCE);*/
	    //session.getPackageRegistry().putEPackage(EcorePackage.eINSTANCE);
	}
	
	/* This method is used for registering an EPackage mapping to the domain
	 * model that will be exploited for creating or manipulating the respective
	 * domain objects. Input parameter: the EPackage to register
	 */
	public void registerPackage(EPackage pack){
		session.getPackageRegistry().putEPackage(pack);
	}
	
	/* This method can be used to open a CDO transaction or view. The developer
	 * should not forget to close the respective cdo transaction or view 
	 * in the end. The boolean input parameter indicates whether a CDO transaction
	 * needs to be opened or not (and thus a view in this latter case).
	 */
	public void open(boolean transaction){
		// Open view or transaction
	    if (transaction){
	    	trans = session.openTransaction();
	    	System.out.println(" opened transaction!");
	    	//trans.addTransactionHandler(new MyCDOTransactionHandler());
	    }
	    else{
	    	view = session.openView();
	    	System.out.println(" opened view!");
	    }
	}
	
	/* This method is used for closing a CDO transaction or view. The developer
	 * should be careful to close only transactions or views that have been
	 * created. This means that if a transaction has been opened, then this
	 * should be closed and not a view. Input parameter: a boolean parameter
	 * indicating whether a CDO transaction or view should be closed. 
	 */
	public void close(boolean transaction){
		if (transaction && trans != null) trans.close();
		else if (!transaction && view != null) view.close();
	}
	
	/* This method is used to store a model into a CDOResource with a particular
	 * name. Do not need to open or close a transaction for this as the
	 * method performs them for you in a transparent manner. The input parameters are: the model to store and the name of the
	 * CDOResource to contain it.
	 */
	public void storeModel(EObject model, String resourceName){
		open(true);
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
	
	/* This method is used to obtain the content of a CDOResource with a 
	 * particular path/name. You should open a view before using this method
	 * and then close it. Input parameter: the name/path of the CDOResource.
	 */
	public EList<EObject> getResourceContents(String path){
		open(false);
		CDOResource resource = view.getResource(path);
		EList<EObject> content = resource.getContents();
		return content;
	}
	
	/* This method is used to create a particular model based on the CERIF
	 * meta-model in order to be able to test the functionality of the 
	 * CDOClient in terms of storing and querying about the objects defined
	 * by this model. 
	 */
	public EObject createCerifModel(){
		CerifModel cm = CerifFactory.eINSTANCE.createCerifModel();
		EList<User> users = cm.getUsers();
		EList<Organization> orgs = cm.getOrganization();
		EList<UserGroup> ugroups = cm.getUserGroups();
		EList<Role> roles = cm.getRoles();
		EList<RoleAssignment> assigns = cm.getRoleAssigments();
		EList<Permission> permissions = cm.getPermissions();
		EList<camel.cerif.Resource> rgroups = cm.getResources();
		EList<ExternalIdentifier> ids = cm.getExternalIdentifiers();
		EList<AllowedActions> aActs = cm.getAllowedActions();
		EList<DataCenter> dcs = cm.getDataCentres();
		EList<Location> locs = cm.getLocations();
		
		ExternalIdentifier id1 = CerifFactory.eINSTANCE.createExternalIdentifier();
		id1.setName("ID1");
		id1.setIdentifier("ID1");
		ids.add(id1);
		
		ExternalIdentifier id2 = CerifFactory.eINSTANCE.createExternalIdentifier();
		id2.setName("ID2");
		id2.setIdentifier("ID2");
		ids.add(id2);
		
		ExternalIdentifier id3 = CerifFactory.eINSTANCE.createExternalIdentifier();
		id3.setName("ID3");
		id3.setIdentifier("ID3");
		ids.add(id3);
		
		User user1 = CerifFactory.eINSTANCE.createUser();
		user1.setLastName("User1");
		EList<Organization> worksFor = user1.getWorksFor();
		EList<ExternalIdentifier> exIDs1 = user1.getHasExternalIdentifier();
		exIDs1.add(id1);
		exIDs1.add(id2);
		users.add(user1);
		
		User user2 = CerifFactory.eINSTANCE.createUser();
		user2.setLastName("User2");
		users.add(user2);
		exIDs1 = user2.getHasExternalIdentifier();
		//exIDs1.add(id2);
		exIDs1.add(id3);
		
		Organization org1 = CerifFactory.eINSTANCE.createOrganization();
		org1.setEmail("email1");
		org1.setName("Org1");
		org1.setWww("www1");
		worksFor.add(org1);
		orgs.add(org1);
		
		CloudProvider org2 = CerifFactory.eINSTANCE.createCloudProvider();
		org2.setEmail("email2");
		org2.setName("Org2");
		org2.setWww("www2");
		org2.setPublic(true);
		orgs.add(org2);
		
		UserGroup ug1 = CerifFactory.eINSTANCE.createUserGroup();
		ug1.setName("ug1");
		EList<User> members = ug1.getMember();
		members.add(user1);
		ugroups.add(ug1);
		
		Role r1 = CerifFactory.eINSTANCE.createRole();
		r1.setName("role1");
		roles.add(r1);

		Role r2 = CerifFactory.eINSTANCE.createRole();
		r2.setName("role2");
		roles.add(r2);
		
		RoleAssignment ra1 = CerifFactory.eINSTANCE.createRoleAssignment();
		ra1.setToRole(r1);
		ra1.setOfUser(user1);
		ra1.setAssignedBy(org1);
		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd");
		try{
			ra1.setAssignedOn(ft.parse("1976-12-16"));
			ra1.setStart(ft.parse("1977-12-16"));
			ra1.setEnd(ft.parse("1978-12-16"));
			System.out.println("End date: " + ra1.getEnd());
		}
		catch(Exception e){
			e.printStackTrace();
		}
		assigns.add(ra1);
			
		ResourceGroup rg1 = CerifFactory.eINSTANCE.createResourceGroup();
		
		ResourceGroup rg3 = CerifFactory.eINSTANCE.createResourceGroup();
		rg3.setName("RG3");
		//res3.add(rg1);
		rgroups.add(rg3);
		
		ResourceGroup rg2 = CerifFactory.eINSTANCE.createResourceGroup();
		rg2.setName("RG2");
		EList<camel.cerif.Resource> res2 = rg2.getContainsResource();
		res2.add(rg3);
		rgroups.add(rg2);
		
		rg1.setName("RG1");
		EList<camel.cerif.Resource> res = rg1.getContainsResource();
		res.add(rg2);
		//res.add(rg3);
		rgroups.add(rg1);
		
		Location l1 = CerifFactory.eINSTANCE.createLocation();
		l1.setLatitude(80);
		l1.setLongitude(175);
		l1.setCity("City1");
		locs.add(l1);
		
		Location l2 = CerifFactory.eINSTANCE.createLocation();
		l2.setLatitude(88);
		l2.setLongitude(120);
		l2.setCity("City1");
		locs.add(l2);
		
		DataCenter dc1 = CerifFactory.eINSTANCE.createDataCenter();
		dc1.setName("DC1");
		dc1.setCodeName("DC1");
		dc1.setOfCloudProvider(org2);
		dc1.setHasLocation(l1);
		dcs.add(dc1);
		
		DataCenter dc2 = CerifFactory.eINSTANCE.createDataCenter();
		dc2.setName("DC2");
		dc2.setCodeName("DC2");
		dc2.setOfCloudProvider(org2);
		dc2.setHasLocation(l2);
		dcs.add(dc2);
		
		return cm;
	}
	
	/* This method is used to run a query over the contents stored in the 
	 * CDO Store. You do not have to create a view before running the query
	 * as the view is created before the query transparently by this method 
	 * and closed when the query is finished. The input parameters are: the query
	 * dialect (OCL, SQL, HQL) and the query String itself.   
	 */
  public List<Object> runQuery(String dialect, String queryStr){
	  //CDOQuery query = view.createQuery("ocl", "self.copies.>=(1).not()",constraints.get(0).getContextVariable());
	  open(false);
	  CDOQuery query = null;
	  query = view.createQuery(dialect, queryStr);
  	  List<Object> results = query.getResult(Object.class);
  	  view.close();
  	  return results;
  }
  
  /* This method is used to close the CDOSession that was opened when creating
   * an object of this class - CDOClient. 
   */
  public void closeSession(){
	  session.close();
	  connector.deactivate();
  }
  
  /* Main method which demonstrates the functionality of the CDOClient through
   * storing a model into a CDOResource and running queries in order to 
   * obtain particular objects specified by it*/
  public static void main(String[] args){
	  //Create the CDOClient
	  CDOClient cl = new CDOClient();
	  //Register the required packages
	  cl.registerPackage(CamelPackage.eINSTANCE);
	  cl.registerPackage(CerifPackage.eINSTANCE);
	  //Create a particular model
	  EObject cerifModel = cl.createCerifModel();
	  //Store the model under a particular CDOResource
	  cl.storeModel(cerifModel,"cerif2");
	  /*Run a query - three ways are shown here: (i) ocl query, 
	   * (ii) hql query and (iii) get all contents of a CDO Resource
	   * and process them to e.g. find the one you are looking for. Please
	   * notice that for the third way, the user/developer has to first create
	   * a view, get the contents of the CDOResource and process them and 
	   * finally close the view.  
	   */
	  List<Object> results = cl.runQuery("ocl","camel::cerif::User.allInstances()");
	  System.out.println("The results of the query are:" + results);
	  results = cl.runQuery("hql","select u from User u");
	  System.out.println("The results of the query are:" + results);
	  cl.open(false);
	  EList<EObject> objs = cl.getResourceContents("cerif2");
	  System.out.println("The objs stored are: " + objs);
	  cl.close(false);
	  //Close the CDOSession once you are done
	  cl.closeSession();
  }
}