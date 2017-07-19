/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.mddb.cdo.server;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.server.CDOServerUtil;
import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.server.db.CDODBUtil;
import org.eclipse.emf.cdo.server.db.IDBStore;
import org.eclipse.emf.cdo.server.db.mapping.IMappingStrategy;
import org.eclipse.emf.cdo.server.hibernate.internal.teneo.TeneoHibernateMappingProvider;
import org.eclipse.emf.cdo.server.internal.hibernate.HibernateStore;
import org.eclipse.emf.cdo.server.internal.net4j.protocol.CDOServerProtocolFactory;
import org.eclipse.emf.cdo.server.ocl.OCLQueryHandler;
import org.eclipse.emf.cdo.server.security.SecurityManagerUtil;
import org.eclipse.emf.cdo.server.spi.security.InternalSecurityManager;
import org.eclipse.emf.cdo.spi.server.ContainerRepositoryProvider;
import org.eclipse.emf.cdo.spi.server.InternalRepository;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.db.DBUtil;
import org.eclipse.net4j.db.IDBAdapter;
import org.eclipse.net4j.db.hsqldb.HSQLDBAdapter;
import org.eclipse.net4j.db.hsqldb.HSQLDBDataSource;
import org.eclipse.net4j.db.mysql.MYSQLAdapter;
import org.eclipse.net4j.jvm.JVMUtil;
import org.eclipse.net4j.tcp.ITCPAcceptor;
import org.eclipse.net4j.tcp.ITCPConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class MyCDOServer implements Runnable{ 

	private IManagedContainer container;
	private IRepository repository;
	private ITCPConnector connector;
	private ITCPAcceptor acceptor;
	private CDONet4jSessionConfiguration sessionConfiguration;
	private Map<String, String> properties = getProps();
	private static String repositoryName;
	private static String dbURL = null, username = null, password = null, dbType = null, storeType = null, portNum = null;
	private static boolean isHibernate = false, logging = false, securityEnabled = false;
	
	private static final String ENV_CONFIG="PAASAGE_CONFIG_DIR";
    private static final String DEFAULT_PAASAGE_CONFIG_DIR =".paasage";
    private static final String PROPERTY_FILENAME = "eu.paasage.mddb.cdo.server.properties";
    private static org.apache.log4j.Logger logger;

	public MyCDOServer() {
	}
	
	static{
		XMIResToResFact();
		logger = org.apache.log4j.Logger.getLogger(MyCDOServer.class);
		BasicConfigurator.configure();
		getConfigurationInformation();
		// Logging setting
		ConsoleAppender console = new ConsoleAppender(); //create appender
		String PATTERN = "%d [%p|%c|%C{1}] %m%n";
		console.setLayout(new PatternLayout(PATTERN)); 
		if (logging) console.setThreshold(Level.DEBUG);
		else console.setThreshold(Level.OFF);
		console.activateOptions();
		BasicConfigurator.resetConfiguration();
		Logger.getRootLogger().addAppender(console);
	}
	
	/* This method is required for loading/exporting XMI resources*/
    private static void XMIResToResFact(){
    	EPackage.Registry.INSTANCE.put(eu.paasage.camel.CamelPackage.eNS_URI, eu.paasage.camel.CamelPackage.eINSTANCE);
    	EPackage.Registry.INSTANCE.put(eu.paasage.camel.scalability.ScalabilityPackage.eNS_URI, eu.paasage.camel.scalability.ScalabilityPackage.eINSTANCE);
    	EPackage.Registry.INSTANCE.put(eu.paasage.camel.metric.MetricPackage.eNS_URI, eu.paasage.camel.metric.MetricPackage.eINSTANCE);
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


    private static String retrieveConfigurationDirectoryFullPath()
    {
        String paasageConfigurationFullPath = System.getenv(ENV_CONFIG);
        logger.debug("Got path: " + paasageConfigurationFullPath);
        
        // enable passing the configuration directory through -Deu.paasage.configdir=PATH JVM option
        if (paasageConfigurationFullPath == null) {
          paasageConfigurationFullPath = System.getProperty("eu.paasage.configdir");
        }

        if (paasageConfigurationFullPath == null)
        {
            String home = System.getProperty("user.home");
            Path homePath = Paths.get(home);
            paasageConfigurationFullPath = homePath.resolve(DEFAULT_PAASAGE_CONFIG_DIR).toAbsolutePath().toString();
        }
        return paasageConfigurationFullPath;
    }
    private static String retrievePropertiesFilePath(String propertiesFileName)
    {
        Path configPath = Paths.get(retrieveConfigurationDirectoryFullPath());
        return configPath.resolve(propertiesFileName).toAbsolutePath().toString();
    }
    public static Properties loadPropertyFile()
    {
        String propertyPath = retrievePropertiesFilePath(PROPERTY_FILENAME);
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
    
    private static void getConfigurationInformation(){
    	//Read DB properties from file

    	try{
    		//InputStream is = MyCDOServer.class.getResourceAsStream("/server.properties");
    		//logger.debug(is);
    		//Properties dbProps = new Properties();
    		Properties dbProps = loadPropertyFile();
    		//dbProps.load(is);
    		dbType = dbProps.getProperty("dbtype");
    		dbURL = dbProps.getProperty("dburl");
    		username = dbProps.getProperty("username");
    		password = dbProps.getProperty("password");
    		storeType = dbProps.getProperty("storetype");
    		repositoryName = dbProps.getProperty("repository");
    		portNum = dbProps.getProperty("port");
    		if (portNum == null || portNum.equals("")) portNum = "2036";
    		if (repositoryName == null) repositoryName = "repo1";
    		if (storeType.equals("hibernate")) isHibernate = true;
    		String log = dbProps.getProperty("logging");
    		if (log == null || log.equals("off")) logging = false;
    		else if (log.equals("on")) logging = true;
    		String security = dbProps.getProperty("security");
    		if (security == null || security.equals("off")) securityEnabled = false;
    		else if (security.equals("on")) securityEnabled = true;
    	}
    	catch(Exception e){
    		logger.error("Something went wrong while obtaining configuration information for CDOServer");
    		logger.error(e.getMessage());
    		//e.printStackTrace();
    	}
    }


/**
* Creates the repository, involves a security manager, if the security configuration option is on,
* and waits for incoming connections of CDOClient(s)
*/
public void run() {
OMPlatform.INSTANCE.setDebugging(logging);
OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);

this.container = createContainer();
initializeAcceptor(container);

IStore store = createStore();
repository = createRepository(repositoryName, store);

//Adding security
if (securityEnabled){
	InternalSecurityManager securityManager = (InternalSecurityManager)SecurityManagerUtil.createSecurityManager("/security", IPluginContainer.INSTANCE);
	securityManager.setRepository((InternalRepository) repository);
	LifecycleUtil.activate(securityManager);
}
CDOServerUtil.addRepository(container, repository);

//Add security package when security is on
if (securityEnabled){
	repository.getPackageRegistry().putEPackage(org.eclipse.emf.cdo.security.SecurityPackage.eINSTANCE);
}

try{
while (true){
	//logger.debug("Got inside");
	Thread.sleep(50);
}
}
catch (InterruptedException e) {
    // restore interrupted status
    Thread.currentThread().interrupt();
    
    // allow the thread to complete
    LifecycleUtil.deactivate(store);
    LifecycleUtil.deactivate(repository);
}
catch(Exception e){
	logger.error("Something went wrong while initiating CDO Repository");
	logger.error(e.getMessage());
	//e.printStackTrace();
}
}

private IManagedContainer createContainer() {
// Prepare container
//IManagedContainer container = ContainerUtil.createContainer();
IManagedContainer container = IPluginContainer.INSTANCE;
OCLQueryHandler.prepareContainer(container);
OCLQueryHandler.prepareContainer(IPluginContainer.INSTANCE);
Net4jUtil.prepareContainer(container); // Register Net4j factories
TCPUtil.prepareContainer(container); // Register TCP factories
JVMUtil.prepareContainer(container);
LifecycleUtil.activate(container);
CDONet4jUtil.prepareContainer(container); // Register CDO factories
CDOServerProtocolFactory factory = new CDOServerProtocolFactory(new ContainerRepositoryProvider(container));
container.registerFactory(factory);
container.activate();

return container;
}

private Map<String, String> getProps(){
	Map<String, String> props = new HashMap<String, String>();
	props.put("supportingAudits", "false");
	props.put("supportingBranches", "false");
	props.put("verifyingRevisions","false");
	props.put("supportingEcore", "true");
	props.put("overrideUUID", "");
	/*props.put("currentLRUCapacity", "10000");
	props.put("revisedLRUCapacity", "10000");
	props.put("optimisticLockingTimeout", "10000");*/
	return props;
}

private IRepository createRepository(String repositoryName,
IStore store) {
return CDOServerUtil.createRepository(repositoryName, store,
properties);
}

private IStore createStore() {
IStore store = null;

Properties teneoProps = new Properties();
teneoProps.put("teneo.mapping.cascade_policy_on_non_containment",
"PERSIST,MERGE");
teneoProps.put("teneo.mapping.inheritance", "JOINED");
//teneoProps.put("teneo.mapping.add_index_for_fk", "true");
teneoProps.put("teneo.naming.default_id_column", "e_id");
teneoProps.put("teneo.mapping.map_document_root","true");
teneoProps.put("teneo.mapping.set_generated_value_on_id_feature", "true");
teneoProps.put("teneo.mapping.default_varchar_length","500");
teneoProps.put("teneo.naming.sql_table_name_prefix","camel_");
teneoProps.put("teneo.naming.id_feature_as_primary_key",false);
teneoProps.put("teneo.naming.version_column","cdo_version");
//teneoProps.put("teneo.mapping.optimistic_locking",false);
//teneoProps.put("teneo.mapping.persistence_xml", "camel_annotations.xml");
TeneoHibernateMappingProvider mappingProvider = new TeneoHibernateMappingProvider();
mappingProvider.setMappingProviderProperties(teneoProps);

if (isHibernate){
	Properties props = new Properties();
	props.put("hibernate.hbm2ddl.auto", "update");
	if (logging) props.put("hibernate.show_sql", "true");
	else props.put("hibernate.show_sql", "false");
	props.put("hibernate.connection.pool_size", "10");
	props.put("hibernate.cache.provider_class",
			"org.hibernate.cache.HashtableCacheProvider");
	
	if (dbType.equals("mysql")){
		props.put("hibernate.dialect","org.hibernate.dialect.MySQLDialect");
		props.put("hibernate.connection.driver_class","com.mysql.jdbc.Driver");
	}
	else if (dbType.equals("hsqldb")){
		props.put("hibernate.dialect","org.hibernate.dialect.HSQLDialect");
		props.put("hibernate.connection.driver_class","org.hsqldb.jdbcDriver");
	}
	props.put("hibernate.connection.url",dbURL);
	props.put("hibernate.connection.username", username);
	props.put("hibernate.connection.password", password);
	props.put("hibernate.connection.autocommit", "true");

	store = new HibernateStore(mappingProvider, props);
}
else{
	Map<String,String> props = new HashMap<String,String>();
	props.put("qualifiedNames", "false");
	props.put("toManyReferences", "ONE_TABLE_PER_REFERENCE");
	props.put("toOneReferences", "LIKE_ATTRIBUTES");
	props.put("tableNamePrefix", "camel");
	props.putAll(props);
	IMappingStrategy mappingStrategy = CDODBUtil.createHorizontalMappingStrategy();
	mappingStrategy.setProperties(props);
	IDBAdapter adapter = null;
	DataSource source = null;
	if (dbType.equals("mysql")){
		adapter = new MYSQLAdapter();
		MysqlDataSource ds = new MysqlDataSource();
		ds.setUser(username);
		ds.setPassword(password);
		ds.setAutoReconnect(true);
		ds.setURL(dbURL);
		source = ds;
	}
	else{
		//adapter = DBUtil.getDBAdapter("hsqldb");
		adapter = new HSQLDBAdapter();
		HSQLDBDataSource ds = new HSQLDBDataSource();
		ds.setUser(username);
		ds.setPassword(password);
		ds.setUrl(dbURL);
		source = ds;
	}
	IDBStore st = CDODBUtil.createStore(mappingStrategy, adapter, DBUtil.createConnectionProvider(source));
	mappingStrategy.setStore(st);
	store = st;
}

return store;
}

private IConnector createConnector(IManagedContainer container) {
	//this.connector = JVMUtil.getConnector(container, "default");
	this.connector = TCPUtil.getConnector(container, "0.0.0.0:" + portNum);
	return connector;
}

private void initializeAcceptor(IManagedContainer container) {
// initialize acceptor
	//this.acceptor = JVMUtil.getAcceptor(container, "default");
	this.acceptor = TCPUtil.getAcceptor(container, "0.0.0.0:" + portNum);
}

private CDONet4jSessionConfiguration
createSessionConfiguration(final IConnector connector) {

final CDONet4jSessionConfiguration config =
CDONet4jUtil.createNet4jSessionConfiguration();
config.setConnector(connector);
config.setRepositoryName(repositoryName);

return config;
}

/**
* @return
*/
public CDONet4jSessionConfiguration getSessionConfiguration() {
return this.sessionConfiguration;
}

/**
* @return
*/
//public IJVMAcceptor getAcceptor() {
public ITCPAcceptor getAcceptor() {
return this.acceptor;
}

/**
* @return
*/
//public IJVMConnector getConnector() {
public ITCPConnector getConnector() {
return this.connector;
}

/**
* @return
*/
public IRepository getRepository() {
return this.repository;
}

public static void main(String[] args){
	MyCDOServer server = new MyCDOServer();
	server.run();
	logger.debug("Server started");
}

}
