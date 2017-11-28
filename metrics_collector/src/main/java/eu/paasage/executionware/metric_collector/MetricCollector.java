/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.executionware.metric_collector;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.eclipse.emf.cdo.common.id.CDOID;

import eu.paasage.executionware.metric_collector.pubsub.MeasurementInitiationSubscriptionClient;
import eu.paasage.executionware.metric_collector.pubsub.PublicationServer;
import eu.paasage.mddb.cdo.client.CDOClient;

public class MetricCollector implements MetricCollection{
	private CDOClient cl;
	private Hashtable<CDOID,ExecutionContextHandler> idToECH;
	private ThreadPoolExecutor tpe;
	private static final int CORE_POOL_SIZE = 10, MAX_POOL_SIZE = 20, ALIVE_TIME = 100;
	
	private static int ID = 1;
	private int myId;
	
	private static org.apache.log4j.Logger logger;
	
	//A static parameter that maps to the configuration directory that contains the properties file of the CDOClient
	private final String ENV_CONFIG="PAASAGE_CONFIG_DIR";
	//A static parameter that maps to a default path where the properties file of the CDOClient can be found
    private final String DEFAULT_PAASAGE_CONFIG_DIR=".paasage";
    //A static parameter that maps to the name of the properties file
    private final String PROPERTY_FILENAME="eu.paasage.executionware.metric-collector.properties";
    //A static parameter that maps to the name of the command file
    private final String COMMAND_FILENAME="eu.paasage.executionware.metric-collector.command";
    
    private final String propertyFilePath;
    
    private String host = "localhost", port = "8080";
    private Mode mode = Mode.LOCAL;
    private DBType dbType = DBType.KAIROS;
    private PublicationServer publicationServer = null;
    private MeasurementInitiationSubscriptionClient subscriptionClient = null;
    private boolean runServer = false;
    private boolean runClient = false;
    private boolean directCall = false;
    private boolean dynamic = false;
    private FileWatcher fw = null;
    
    public enum Mode{
    	//MetricCollector operates in a specific cloud
    	LOCAL,
    	//MetricCollector operates globally at the level of Executionware
    	GLOBAL
    }
    
    public enum DBType{
    	//KairosDB is used
    	KAIROS,
    	//InfluxDB is used
    	INFLUX
    }
    
    static {
    	logger = org.apache.log4j.Logger.getLogger(MetricCollector.class);
    }
	
	public MetricCollector(){
		propertyFilePath = retrieveConfigurationDirectoryFullPath();
		init();
	}
	
	public MetricCollector(String propertyFilePath){
		this.propertyFilePath = propertyFilePath;
		init();
	}
	
	private void init(){
		myId = ID++;
		getConnectionInformation();
		logger.info("Metric Collector is initiated: " + myId + " mode: " + mode);
		cl = new CDOClient();
		idToECH = new Hashtable<CDOID,ExecutionContextHandler>();
		tpe = new ThreadPoolExecutor(CORE_POOL_SIZE,MAX_POOL_SIZE,ALIVE_TIME,TimeUnit.SECONDS,new ArrayBlockingQueue<Runnable>(CORE_POOL_SIZE));
		if (!directCall){
			fw = new FileWatcher(new File(propertyFilePath + File.separator + COMMAND_FILENAME), 20000, this);
			if (dynamic) tpe.execute(fw);
			else fw.getCommands();
		}
	}
	
	/* This method is used in order to retrieve the full path to the 
	 * configuration directory which contains the properties file of the 
	 * CDOClient (which contains information to connect to the CDO Server)
	 */
	private String retrieveConfigurationDirectoryFullPath()
    {
        String propertyFilePath = System.getenv(ENV_CONFIG);
        
     // enable passing the configuration directory through -Deu.paasage.configdir=PATH JVM option
        if (propertyFilePath == null) {
          propertyFilePath = System.getProperty("eu.paasage.configdir");
        }
        
        if (propertyFilePath == null)
        {
            String home = System.getProperty("user.home");
            Path homePath = Paths.get(home);
            propertyFilePath = homePath.resolve(DEFAULT_PAASAGE_CONFIG_DIR).toAbsolutePath().toString();
        }
        logger.info("Got path: " + propertyFilePath);
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
            props.put("log4j.rootLogger","info, stdout");
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
		String db = props.getProperty("db");
		if (db == null || db.equals("kairos")) dbType = DBType.KAIROS;
		else if (db.equals("influx")) dbType = DBType.INFLUX;
		host = props.getProperty("host");
		port = props.getProperty("port");
		String modeStr = props.getProperty("mode");
		if (modeStr == null || modeStr.equals("local")) mode = Mode.LOCAL;
		else if (modeStr.equals("global")) mode = Mode.GLOBAL;
		logger.info("Mode is: " + mode);
		String pubStr = props.getProperty("runServer");
		if (pubStr == null || pubStr.equals("false")) runServer = false;
		else if (pubStr.equals("true")) runServer = true;
		String subStr = props.getProperty("runClient");
		if (subStr == null || subStr.equals("false")) runClient = false;
		else if (subStr.equals("true")) runClient = true;
		String dirCallStr = props.getProperty("directCall");
		if (dirCallStr == null || dirCallStr.equals("false")) directCall = false;
		else if (dirCallStr.equals("true")) directCall = true;
		String dynamicStr = props.getProperty("dynamic");
		if (dynamicStr == null || dynamicStr.equals("false")) dynamic = false;
		else if (dynamicStr.equals("true")) dynamic = true;
		logger.info("Got host: " + host + " port: " + port + " mode:" + modeStr + " runServer: " + runServer + " directCall: " + directCall + " dynamic: " + dynamic);
		if (runServer) publicationServer = new PublicationServer();
		if (runClient){
			subscriptionClient = new MeasurementInitiationSubscriptionClient(this);
			new Thread(subscriptionClient).start();
		}
	}


	public synchronized void readMetrics(Set<CDOID> metricIDs, CDOID execContextId) {
		//logger.debug("MC: " + myId + " is reading metrics: " + metricIDs);
		logger.info("MC: " + myId + " is reading metrics: " + metricIDs);
		ExecutionContextHandler ech = new ExecutionContextHandler(metricIDs,execContextId,host,port,mode,dbType,publicationServer);
		idToECH.put(execContextId, ech);
		tpe.execute(ech);
	}

	public synchronized void updateMetrics(Set<CDOID> metricIDs, CDOID execContextId) {
		
	}

	public synchronized void deleteMetrics(CDOID execContextId) {
		//logger.debug("MC: " + myId + " is deleting metrics for EC: " + execContextId);
		logger.info("MC: " + myId + " is deleting metrics for EC: " + execContextId);
		ExecutionContextHandler ech = idToECH.get(execContextId);
		ech.terminate();
		tpe.remove(ech);
	}
	
	public synchronized void terminate(){
		for (ExecutionContextHandler ech: idToECH.values()){
			ech.terminate();
			tpe.remove(ech);
		}
		tpe.shutdown();
		tpe = null;
		idToECH.clear();
		idToECH = null;
		if (publicationServer != null) publicationServer.terminate();
		if (subscriptionClient != null) subscriptionClient.terminate();
	}	
}