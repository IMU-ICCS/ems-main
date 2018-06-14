package eu.paasage.mddb.cdo.client.exp;

import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.execution.ExecutionPackage;
import eu.paasage.camel.location.LocationPackage;
import eu.paasage.camel.metric.MetricPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.requirement.RequirementPackage;
import eu.paasage.camel.scalability.ScalabilityPackage;
import eu.paasage.camel.security.SecurityPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.camel.unit.UnitPackage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.EresourcePackage;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.session.CDOSession;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
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
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.IManagedContainer;
import org.eclipse.net4j.util.container.IPluginContainer;
import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
import org.eclipse.net4j.util.om.OMPlatform;
import org.eclipse.net4j.util.om.log.PrintLogHandler;
import org.eclipse.net4j.util.om.trace.PrintTraceHandler;
import org.eclipse.net4j.util.security.IPasswordCredentialsProvider;
import org.eclipse.net4j.util.security.PasswordCredentialsProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class CDOClientXImpl implements CDOClientX {


    //A TCP Connector to the CDOServer
    private org.eclipse.net4j.internal.tcp.TCPClientConnector connector;
    private org.eclipse.net4j.internal.tcp.TCPSelector selector;
    //private org.eclipse.net4j.internal.jvm.JVMClientConnector connector;

    //Parameters representing the required connection information in order to connect to the CDOServer
    private String host, port, repositoryName, userName, password;
    private boolean logging = false;

    //A static parameter that maps to the configuration directory that contains the properties file of the CDOClient
    private static final String ENV_CONFIG = "PAASAGE_CONFIG_DIR";
    //A static parameter that maps to a default path where the properties file of the CDOClient can be found
    private static final String DEFAULT_PAASAGE_CONFIG_DIR = ".paasage";
    //A static parameter that maps to the name of the properties file
    private static final String PROPERTY_FILENAME = "eu.paasage.mddb.cdo.client.properties";

    private String propertyFilePath;

    private List<EPackage> packagesToRegister;

    private static HashMap<String, Object> opts = new HashMap<String, Object>();

    private CDONet4jSessionConfiguration configuration;

    static {
        XMIResToResFact();
        opts.put(XMIResource.OPTION_SCHEMA_LOCATION, true);
    }

    /* This method is required for loading/exporting XMI resources*/
    private static void XMIResToResFact() {
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put
                ("*",
                        new XMIResourceFactoryImpl() {
                            public Resource createResource(URI uri) {
                                return new XMIResourceImpl(uri);
                            }
                        });
    }

    /* Default constructor for the client which initiates a CDO session*/
    public CDOClientXImpl() {
        this(Collections.emptyList());
    }

    public CDOClientXImpl(List<EPackage> packagesToRegister) {
        this.propertyFilePath = retrieveConfigurationDirectoryFullPath();
        this.packagesToRegister = Objects.isNull(packagesToRegister) ? Collections.emptyList() : packagesToRegister;
        initClient();
    }

    /* This method is used in order to retrieve the full path to the
     * configuration directory which contains the properties file of the
     * CDOClient (which contains information to connect to the CDO Server)
     */
    private String retrieveConfigurationDirectoryFullPath() {
        String propertyFilePath = System.getenv(ENV_CONFIG);
        log.info("Got path: " + propertyFilePath);

        // enable passing the configuration directory through -Deu.paasage.configdir=PATH JVM option
        if (propertyFilePath == null) {
            propertyFilePath = System.getProperty("eu.paasage.configdir");
            log.info("Got path: " + propertyFilePath);
        }

        if (propertyFilePath == null) {
            String home = System.getProperty("user.home");
            Path homePath = Paths.get(home);
            propertyFilePath = homePath.resolve(DEFAULT_PAASAGE_CONFIG_DIR).toAbsolutePath().toString();
        }
        return propertyFilePath;
    }

    /* This method is used to find the path to the property file which specifies
     * the connection information to the CDO Server
     */
    private String retrievePropertiesFilePath(String propertiesFileName) {
        Path configPath = Paths.get(propertyFilePath);
        return configPath.resolve(propertiesFileName).toAbsolutePath().toString();
    }

    /* This method is used in order to load a property file of the CDOClient
     * which contains the information needed to connect to the CDOServer
     */
    private Properties loadPropertyFile() {
        String propertyPath = retrievePropertiesFilePath(PROPERTY_FILENAME);
        Properties props = new Properties();
        try {
            try (InputStream is = new FileInputStream(propertyPath)){
                props.load(is);
            }
        } catch (IOException ioException) {
            //do nothing??
        }
        return props;
    }

    /* This method is called in order to get the connection information
     * that will be used in order to be able to connect correctly to the
     * CDO Server and create the respective CDOSession
     */
    private void getConnectionInformation() {
        Properties props = loadPropertyFile();
        this.host = props.getProperty("host");
        this.port = props.getProperty("port");
        this.userName = props.getProperty("userName");
        this.password = props.getProperty("password");
        this.repositoryName = props.getProperty("repository", "repo1");

        String logging = props.getProperty("logging", "off");
        if (logging.equals("off"))
            this.logging = false;
        else if (logging.equals("on"))
            this.logging = true;

        if (this.logging)
            log.info("Got host: " + host + " port: " + port + " repository:" + repositoryName);
    }

    /*This method is used for initiating a CDO Session starting by obtaining
    connection information from a property file*/
    private void initClient() {
        getConnectionInformation();

        OMPlatform.INSTANCE.setDebugging(logging);
        OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
        OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);

        // Prepare receiveExecutor
        final ThreadGroup threadGroup = new ThreadGroup("net4j"); //$NON-NLS-1$
        ExecutorService receiveExecutor = Executors.newCachedThreadPool(r -> {
            Thread thread = new Thread(threadGroup, r);
            thread.setDaemon(true);
            return thread;
        });

        // Prepare bufferProvider
        IBufferProvider bufferProvider = Net4jUtil.createBufferPool();
        LifecycleUtil.activate(bufferProvider);

        IProtocolProvider protocolProvider = new FactoriesProtocolProvider(
                new org.eclipse.emf.cdo.internal.net4j.protocol.CDOClientProtocolFactory());

        // Prepare selector
        selector = new org.eclipse.net4j.internal.tcp.TCPSelector();
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

        IManagedContainer container = IPluginContainer.INSTANCE;
        Net4jUtil.prepareContainer(container);
        TCPUtil.prepareContainer(container);
        CDONet4jUtil.prepareContainer(container);
        configuration = CDONet4jUtil.createReconnectingSessionConfiguration(host + ":" + port, repositoryName, container);
        configuration.setConnector(connector);
        configuration.setRepositoryName(repositoryName); //$NON-NLS-1$

        //Provide security information, if supplied by user
        //authentication, if succeeds last for the whole session - lifetime of CDOClient object
        if (userName != null && password != null) {
            IPasswordCredentialsProvider credentialsProvider = new PasswordCredentialsProvider(userName, password);
            configuration.setCredentialsProvider(credentialsProvider);
        }

    }

    @Override
    public CDOSessionX getSession() {
        CDONet4jSession session = configuration.openNet4jSession();
        session.options().setCommitTimeout(1000);

        registerCamelPackages(session);
        registerAdditionalPackages(session);

        return new CDOSessionXImpl(session, this.logging);
    }

    @Override
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
            log.error("Something went wrong while exporting model: {} at path: {}", model, filePath, e);
        }
        return false;
    }

    /* This method is used to load a model from a particular xmi resource. The model
     * can then be stored to the CDO Server/Repository. The method takes as input
     * the path (as a String) where the XML file resides.
     */
    @Override
    public EObject loadModel(String pathName){
        final ResourceSet rs = new ResourceSetImpl();
        rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
        Resource res = rs.getResource(URI.createFileURI(pathName), true);
        log.info("Got resource: {}", res);
        EList<EObject> contents = res.getContents();
        log.info("Contents are: {}", contents);

        return contents.get(0);
    }

    /* This method is used to save a model into the file system in a specific path given as input
     * The input parameters are: the model to store and the file path to store it in the file system.
     * The output indicates whether the model saving was successful or not. The log file must be
     * inspected in the latter negative case.
     */
    //TODO - is this metod necessary?
    @Override
    public boolean saveModel(EObject model, String pathName) {
        final ResourceSet rs = new ResourceSetImpl();
        rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
        Resource res;
        File f = new File(pathName);
        EList<EObject> contents;
        if (f.exists()) {
            res = rs.getResource(URI.createFileURI(pathName), true);
            contents = res.getContents();
            contents.clear();
        } else {
            res = rs.createResource(URI.createFileURI(pathName));
            contents = res.getContents();
        }
        if (logging) log.info("Got resource: " + res);
        contents.add(model);
        try {
            res.save(null);
            return true;
        } catch (Exception e) {
            log.error("Something went wrong while storing model: {} at path: {}", model, pathName, e);
        }
        return false;
    }

    private void registerAdditionalPackages(CDOSession session) {
        registerPackages(session, this.packagesToRegister);
    }

    /* This method is used to register all Packages of Camel meta-model
     */
    private void registerCamelPackages(CDOSession session){
        registerPackages(session, Arrays.asList(EresourcePackage.eINSTANCE, CamelPackage.eINSTANCE, ScalabilityPackage.eINSTANCE,
                DeploymentPackage.eINSTANCE, OrganisationPackage.eINSTANCE, ProviderPackage.eINSTANCE, SecurityPackage.eINSTANCE,
                ExecutionPackage.eINSTANCE, TypePackage.eINSTANCE, RequirementPackage.eINSTANCE, MetricPackage.eINSTANCE,
                UnitPackage.eINSTANCE, LocationPackage.eINSTANCE
                ));
    }

    /* This method is used for registering an EPackage mapping to the domain
     * model that will be exploited for creating or manipulating the respective
     * domain objects. Input parameter: the EPackage to register
     */
    private void registerPackages(CDOSession session, List<EPackage> packagesToRegister){
        packagesToRegister.forEach(packageToRegister -> session.getPackageRegistry().putEPackage(packageToRegister));
    }


}
