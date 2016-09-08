/*
 * Copyright (c) 2014 INRIA, INSA Rennes
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.adaptationmanager.core;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.net4j.util.event.IEvent;
import org.eclipse.net4j.util.event.IListener;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecInterfacer;
import eu.paasage.upperware.adapter.adaptationmanager.REST.ExecutionwareError;
import eu.paasage.upperware.adapter.adaptationmanager.input.ReasonerInterfacer;
//import eu.paasage.upperware.adapter.adaptationmanager.plangeneration.PlanGenerator;
import eu.paasage.upperware.plangenerator.PlanGenerator;
import eu.paasage.upperware.adapter.adaptationmanager.validation.IValidator;
import eu.paasage.upperware.adapter.adaptationmanager.validation.ValidatorImpl;
import eu.paasage.upperware.adapter.adaptationmanager.input.MyCDOClient;

import org.zeromq.ZMQ;

import eu.paasage.camel.deployment.DeploymentModel;

public class AdaptationManager {
	static ReasonerInterfacer reasonerInterfacer;
	static ExecInterfacer execInterfacer;
	static IValidator validator;
	static PlanGenerator planGenerator;
	static Properties properties;
	private static boolean cleaning = false;
	public static boolean flag = false;
	public static boolean isDaemon = false;
	public static DeploymentModel current = null;
	private static Coordinator c;
	private final static Logger LOGGER = Logger
			.getLogger(AdaptationManager.class.getName());

	private static ZeromqServer zmqS = null;
	
	private static final int SUB_S2D_PORT_DEFAULT=5584;
	private static final String SUB_S2D_TOPIC_DEFAULT="newCamelDeploymentAvailable";
	private static final int SUB_TERMINATION_PORT_DEFAULT=5588;
	private static final String SUB_TERMINATION_TOPIC_DEFAULT="terminate";
	private static final int SUB_SCALING_PORT_DEFAULT=5589;
	
	private static String fakeDeployment = null;
	private static int fakeDeploymentTimeout = -1;
	
	private static int CDOUpdatePollTimeInMins = 10;

	public static void main(String[] args) {
		Properties props = System.getProperties();
		props.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tH:%1$tM:%1$tS] %4$s: %5$s%6$s (%3$s)%n%n");

		properties = loadProperties(args);

		String resourceName = properties.getProperty("CDO.resourceName");
		if (resourceName == null)
			resourceName = "test";
		
		fakeDeployment = properties.getProperty("FakeDeploymentTimeoutSeconds");
		if (fakeDeployment != null)
			fakeDeploymentTimeout = Integer.parseInt(fakeDeployment);
		
		String time = null;
		time = properties.getProperty("CDOUpdatePollTimeForEWScaling");
		if(time != null)
			CDOUpdatePollTimeInMins = Integer.parseInt(time);

		/*
		 * //Running the 0MQ Server try { new ZeromqServer().start(); } catch
		 * (Exception e){ LOGGER.log(Level.SEVERE, "0MQ Server has failed"); }
		 */

		execInterfacer = new ExecInterfacer();
		if (cleaning) {
			try {
				execInterfacer.clean();
			} catch (ExecutionwareError e) {
				LOGGER.log(Level.SEVERE, "Cleaning has failed");
			}
			return;
		}
		// reasonerInterfacer = new ReasonerInterfacer();
		reasonerInterfacer = new ReasonerInterfacer(resourceName, false);
		validator = new ValidatorImpl();
		planGenerator = new PlanGenerator();
		// c = new Coordinator(reasonerInterfacer, execInterfacer, validator,
		// planGenerator);
		c = new Coordinator(reasonerInterfacer, execInterfacer, validator);
		LOGGER.log(Level.INFO, "Adaptation manager: starting");

		try {
			// c.runStep();
			if (isDaemon || args[0].equals("daemon")) {
				//runListener();
				daemonMode();
			} else {
				
				if(fakeDeploymentTimeout >= 0){//Performing a fake deployment
					Thread.sleep(fakeDeploymentTimeout*1000);
					LOGGER.log(Level.INFO, "Successfully faked a deployed model");
				} else{
					
					if(c.deployModelIDThreaded(1))// threaded execution of plan
						LOGGER.log(Level.INFO, "Successfully deployed model");
					else
						LOGGER.log(Level.SEVERE, "Failed to deploy model");
				}
				

			}
		} catch (Exception ex) {
			if (ex instanceof ArrayIndexOutOfBoundsException)
				System.out.println("Run as deamon if you want to run continuously");
			else if(ex instanceof InterruptedException)
				System.out.println("Interrupted sleep during fake deployment");
			ex.printStackTrace();
		} finally {
			LOGGER.log(Level.INFO, "Adaptation manager: stopping");
			// c.terminate();
			LOGGER.log(Level.INFO, "Adaptation manager: stopped");
			System.exit(0);
		}
		// System.exit(1);
	}
	
	public static void daemonMode(){
		LOGGER.log(Level.INFO, "Running in deamon mode");
		
		///////////////////////////////////////////////////
		//// ZMQ CONFIGURATION
		///////////////////////////////////////////////////

		String subs2dportstr = properties.getProperty("ZMQ.SubS2DPort");
		int subs2dport;
		if (subs2dportstr==null)
			subs2dport=SUB_S2D_PORT_DEFAULT;
		else
			subs2dport = Integer.valueOf(subs2dportstr);
		LOGGER.log(Level.INFO, "==> ZMQ S2D PORT: "+subs2dport);

		String subs2dtopic = properties.getProperty("ZMQ.SubS2DTopic");
		if (subs2dtopic==null)
			subs2dtopic=SUB_S2D_TOPIC_DEFAULT;
		LOGGER.log(Level.INFO, "==> ZMQ S2D topic: "+subs2dtopic);

		String subterminationportstr = properties.getProperty("ZMQ.SubTerminationPort");
		int subterminationport;
		if (subterminationportstr==null)
			subterminationport=SUB_TERMINATION_PORT_DEFAULT;
		else
			subterminationport = Integer.valueOf(subterminationportstr);
		LOGGER.log(Level.INFO, "==> ZMQ termination PORT: "+subterminationport);

		String subterminationtopic = properties.getProperty("ZMQ.SubTerminationTopic");
		if (subterminationtopic==null)
			subterminationtopic=SUB_TERMINATION_TOPIC_DEFAULT;
		LOGGER.log(Level.INFO, "==> ZMQ Termination topic: "+subterminationtopic);
		
		String subscalingportstr = properties.getProperty("ZMQ.SubTerminationPort");
		int subscalingport;
		if (subscalingportstr==null)
			subscalingport=SUB_TERMINATION_PORT_DEFAULT;
		else
			subscalingport = Integer.valueOf(subscalingportstr);
		LOGGER.log(Level.INFO, "==> ZMQ scaling PORT: " + subscalingport);

		///////////////////////////////////////////////////
		
		/*ZeroMQSubscriber zmsModelSub = new ZeroMQSubscriber("Solver2DeploySub", "localhost", "newDeploymentCAMELModel", 5546, 30000);
		zmsModelSub.start();*/
		ZeroMQSubscriberSimple zmsModelSub = new ZeroMQSubscriberSimple("Solver2DeploySub", "localhost", subs2dtopic, subs2dport);
		
		ZeroMQSubscriberSimple zmsScaleSub = new ZeroMQSubscriberSimple("ScaleSub", "localhost", "", subscalingport);
		
		/*ZeroMQSubscriber zmsTermSub = new ZeroMQSubscriber("TerminationSub", "localhost", "terminate", 5555, 60000);
		zmsTermSub.start();*/
		ZeroMQSubscriberSimple zmsTermSub = new ZeroMQSubscriberSimple("TerminationSub", "localhost", subterminationtopic, subterminationport);
		
		boolean terminate = false;
		boolean taskInProgress = false;
		
		int depModelIndex = 0;
		
		long lastTime =  System.currentTimeMillis();
		
		while(!terminate){
			
			if(!zmsScaleSub.readMessage(true).equalsIgnoreCase("")){//an auto-scale event has happened
				String message = zmsScaleSub.getLastMessage();
				//Query ExecutionWare for the VM instances and then update Adapter mapping as well as the CAMEL model
			}
			
			//if(zmsModelSub.readResetMessage().contains("newDeploymentCAMELModel")){
			if(zmsModelSub.readMessage(true).contains(subs2dtopic)){//multipart message contains a camel ID later
				
				//new Deployment model available, so take decision and deploy
				//depModelIndex++;
				
				/*if(zmsModelSub.hasMoreMessage()){
					String newModelID = zmsModelSub.readMessage(false);
					depModelIndex = Integer.parseInt(newModelID);
				} else{
					depModelIndex++;
				}*/
				
				if(zmsModelSub.hasMoreMessage()){
					
					String resourceName = zmsModelSub.readMessage(false);
					LOGGER.log(Level.INFO, "Received topic \"" + subs2dtopic + "\" from S2D with resource name : " + resourceName);
					ReasonerInterfacer newReasonerInterfacer = new ReasonerInterfacer(resourceName, false);
					reasonerInterfacer = newReasonerInterfacer;
					c.updateReasonerInterfacer(reasonerInterfacer);
					depModelIndex++;
					
				} else{
					
					depModelIndex++;
				}
				
				taskInProgress = true;
				
				try {
					
					if(fakeDeploymentTimeout >= 0){//Performing a fake deployment not successful
						
						if(!c.fakeDeployment(depModelIndex, fakeDeploymentTimeout)){
							terminate = true;
							LOGGER.log(Level.SEVERE, "Failed to fake deploy model");
						}
					} else{
						
						if(!c.deployModelIDThreaded(depModelIndex)){//deployment was not successful
							terminate = true;
							LOGGER.log(Level.SEVERE, "Failed to deploy model");
						}					
					}
					
				} catch (Exception ex) {
					if (ex instanceof IndexOutOfBoundsException)
						System.out.println("Could not reach indexed CDO solution #" + depModelIndex);
					else if(ex instanceof InterruptedException)
						System.out.println("Interrupted sleep during fake deployment");
					ex.printStackTrace();
				}

				taskInProgress = false;
			}
			
			long current = System.currentTimeMillis();
			if( !taskInProgress && !terminate && depModelIndex > 0 && (((current - lastTime)/60000) >= CDOUpdatePollTimeInMins)){//check for CDO update occasionally (every 10 minutes as default or as set in Adapter property)
				
				taskInProgress = true;
				
				if(c.updateRunningCDOModel(depModelIndex)){
					
					depModelIndex = c.getNewDMIndexAndReset();
					LOGGER.log(Level.INFO, "CDO updated with the deployment model written at index = " + depModelIndex);
				}
				LOGGER.log(Level.INFO, "----------------------------\n");
				lastTime = current;
				taskInProgress = false;
			}
			
			if(zmsTermSub.readMessage(true).contains("terminate") || depModelIndex > 100)
				terminate = true;
		}
		
		LOGGER.log(Level.INFO, "Adaptation manager: stopped");
		System.exit(0);
	}

	public static void runListener() {
		LOGGER.log(Level.INFO, "Listener: starting");
		LOGGER.log(Level.INFO, "Running in deamon mode");
		flag = false;
		// Need to rewrite the code here

		// Running the 0MQ Server
		try {
			zmqS = new ZeromqServer();
			zmqS.start();
			zmqS.join();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "0MQ Server has failed");
		} finally {
			LOGGER.log(Level.INFO, "Adaptation manager: stopped");
			System.exit(0);
		}

		/*
		 * System.out.println(properties.getProperty("CDO.host")); MyCDOClient
		 * cdocl = new
		 * MyCDOClient(properties.getProperty("CDO.host"),properties.
		 * getProperty("CDO.port"),
		 * properties.getProperty("CDO.repositoryName")); CDOView view =
		 * cdocl.openView(); view.getSession().addListener( new IListener(){
		 * public void notifyEvent(IEvent arg0){ System.out.println("***EVENT: "
		 * + arg0); flag = true; try { c.getCurrentFromCDO(); //c.runStep();
		 * c.startThreaded();//threaded execution of plan } finally {
		 * LOGGER.log(Level.INFO, "Adaptation manager: stopping");
		 * //c.terminate(); LOGGER.log(Level.INFO,
		 * "Adaptation manager: stopped"); } } } );
		 */
	}

	public static void zMQResponder(String command) {
		if (command.equals("event")) {
			try {
				c.getCurrentFromCDO();
				c.runStep();
			} finally {
				LOGGER.log(Level.INFO, "Adaptation manager: stopping");
				c.terminate();
				LOGGER.log(Level.INFO, "Adaptation manager: stopped");
			}
		} else if (command.equals("terminate")) {
			LOGGER.log(Level.INFO, "Adaptation manager: stopping");
			c.terminate();
			LOGGER.log(Level.INFO, "Adaptation manager: stopped");
			System.exit(0);
		}
	}

	private static final String ENV_CONFIG = "PAASAGE_CONFIG_DIR";
	private static final String DEFAULT_PAASAGE_CONFIG_DIR = ".paasage";

	private static String retrieveConfigurationDirectoryFullPath() {
		String paasageConfigurationFullPath = System.getenv(ENV_CONFIG);
		if (paasageConfigurationFullPath == null) {
			String home = System.getProperty("user.home");
			Path homePath = Paths.get(home);
			paasageConfigurationFullPath = homePath
					.resolve(DEFAULT_PAASAGE_CONFIG_DIR).toAbsolutePath()
					.toString();
		}
		return paasageConfigurationFullPath;
	}

	private static String retrievePropertiesFilePath(String propertiesFileName) {
		Path configPath = Paths.get(retrieveConfigurationDirectoryFullPath());
		return configPath.resolve(propertiesFileName).toAbsolutePath()
				.toString();
	}

	public static Properties getProperties() {
		return properties;
	}

	public static Properties loadAndGetProperties() {
		String propertyPath = retrievePropertiesFilePath("eu.paasage.upperware.adapter.properties");
		Properties fileprops = new Properties();
		try {
			fileprops.load(new FileInputStream(propertyPath));
		} catch (java.io.IOException e) {
			LOGGER.log(Level.SEVERE,
					"Failed to load eu.paasage.upperware.adapter.properties");
		}
		return fileprops;
	}
	
	public static Properties loadAndGetCredentials() {
		String credentialPath = retrievePropertiesFilePath("eu.paasage.upperware.cloudcredentials.properties");
		Properties filecreds = new Properties();
		try {
			filecreds.load(new FileInputStream(credentialPath));
		} catch (java.io.IOException e) {
			LOGGER.log(Level.SEVERE,
					"Failed to load eu.paasage.upperware.cloudcredentials.properties");
		}
		return filecreds;
	}

	public static Properties loadProperties(String[] args) {
		String propertyPath = retrievePropertiesFilePath("eu.paasage.upperware.adapter.properties");
		Properties fileprops = new Properties();
		try {
			fileprops.load(new FileInputStream(propertyPath));
		} catch (java.io.IOException e) {
			LOGGER.log(Level.SEVERE,
					"Failed to load eu.paasage.upperware.adapter.properties");
		}
		
		String credentialPath = retrievePropertiesFilePath("eu.paasage.upperware.cloudcredentials.properties");
		Properties filecreds = new Properties();
		try {
			filecreds.load(new FileInputStream(credentialPath));
		} catch (java.io.IOException e) {
			LOGGER.log(Level.SEVERE,
					"Failed to load eu.paasage.upperware.cloudcredentials.properties");
		}
		
		// Read properties from command line (they have priority over previous)
		Options options = new Options();
		Option help = new Option("help", "print this message");
		Option clean = new Option("clean", "clean Executionware");
		Option daemon = new Option("daemon", "run AM continuously");
		Option property = OptionBuilder.withArgName("property=value")
				.hasArgs(2).withValueSeparator()
				.withDescription("set given property").create("c");
		options.addOption(property);
		options.addOption(help);
		options.addOption(clean);
		options.addOption(daemon);
		CommandLineParser parser = new GnuParser();
		CommandLine cmd = null;
		try {
			cmd = parser.parse(options, args);
			if (cmd.hasOption("help")) {
				HelpFormatter formatter = new HelpFormatter();
				formatter.printHelp("adaptationmanager", options);
				System.exit(0);
			} else if (cmd.hasOption("clean")) {
				cleaning = true;
			} else if (cmd.hasOption("daemon")) {
				isDaemon = true;
			}
			;
		} catch (ParseException e) {
			System.err.println("Parsing failed. Reason: " + e.getMessage());
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("adaptationmanager", options);
			System.exit(0);
		}

		Properties commandlineprops = cmd.getOptionProperties("c");
		Properties result = new Properties();
		result.putAll(fileprops);
		result.putAll(filecreds);
		result.putAll(commandlineprops);
		LOGGER.log(Level.INFO, "Retrieved #" + result.size() + " info from Adapter property, credential file and command line args");
		//LOGGER.log(Level.INFO, "Properties:" + result);
		return result;
	}
}
