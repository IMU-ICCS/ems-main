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

	public static void main(String[] args) {
		Properties props = System.getProperties();
		props.setProperty("java.util.logging.SimpleFormatter.format",
				"[%1$tH:%1$tM:%1$tS] %4$s: %5$s%6$s (%3$s)%n%n");

		properties = loadProperties(args);

		String resourceName = properties.getProperty("CDO.resourceName");
		if (resourceName == null)
			resourceName = "test";

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
				if(c.deployModelIDThreaded(1))// threaded execution of plan
					LOGGER.log(Level.INFO, "Successfully deployed model");
				else
					LOGGER.log(Level.SEVERE, "Failed to deploy model");
			}
		} catch (Exception ex) {
			if (ex instanceof ArrayIndexOutOfBoundsException)
				System.out
						.println("Run as deamon if you want to run continuously");
			else
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
		
		/*ZeroMQSubscriber zmsModelSub = new ZeroMQSubscriber("Solver2DeploySub", "localhost", "newDeploymentCAMELModel", 5546, 30000);
		zmsModelSub.start();*/
		ZeroMQSubscriberSimple zmsModelSub = new ZeroMQSubscriberSimple("Solver2DeploySub", "localhost", "newDeploymentCAMELModel", 5546);
		
		ZeroMQSubscriberSimple zmsScaleSub = new ZeroMQSubscriberSimple("ScaleSub", "localhost", "", 5555);
		
		/*ZeroMQSubscriber zmsTermSub = new ZeroMQSubscriber("TerminationSub", "localhost", "terminate", 5555, 60000);
		zmsTermSub.start();*/
		ZeroMQSubscriberSimple zmsTermSub = new ZeroMQSubscriberSimple("TerminationSub", "localhost", "terminate", 5555);
		
		boolean terminate = false;
		boolean taskInProgress = false;
		
		int depModelIndex = 0;
		
		while(!terminate){
			
			if(!zmsScaleSub.readMessage().equalsIgnoreCase("")){//an auto-scale event has happened
				String message = zmsScaleSub.getLastMessage();
				//Query ExecutionWare for the VM instances and then update Adapter mapping as well as the CAMEL model
			}
			
			//if(zmsModelSub.readResetMessage().contains("newDeploymentCAMELModel")){
			if(zmsModelSub.readMessage().contains("newDeploymentCAMELModel")){
				//new Deployment model available, so take decision and deploy
				depModelIndex++;
				taskInProgress = true;
				if(!c.deployModelIDThreaded(depModelIndex)){//deployment was not successful
					terminate = true;
					LOGGER.log(Level.SEVERE, "Failed to deploy model");
				}
				taskInProgress = false;
			}
			
			if(zmsTermSub.readMessage().contains("terminate") || depModelIndex > 100)
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

	public static Properties loadProperties(String[] args) {
		String propertyPath = retrievePropertiesFilePath("eu.paasage.upperware.adapter.properties");
		Properties fileprops = new Properties();
		try {
			fileprops.load(new FileInputStream(propertyPath));
		} catch (java.io.IOException e) {
			LOGGER.log(Level.SEVERE,
					"Failed to load eu.paasage.upperware.adapter.properties");
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
		result.putAll(commandlineprops);
		LOGGER.log(Level.INFO, "Retrieved #" + result.size() + " info from Adapter property file and command line args");
		//LOGGER.log(Level.INFO, "Properties:" + result);
		return result;
	}
}
