/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp.util;

import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import eu.paasage.upperware.profiler.rp.Constants;

public class PropertiesReader {
	/** Log4j logger instance */
	private static Logger log = Logger.getLogger(PropertiesReader.class);

	/** Properties instance */
	private static Properties paasageProperties = null;

	/**
	 * Retrieves the full directory path from the default PaaSage environment
	 * config
	 * 
	 * @see eu.paasage.rp.Constants#PAASAGE_ENV_CONFIG
	 * @return full directory path
	 */
	private static String retrieveConfigurationDirectoryFullPath() {
		String paasageConfigurationFullPath = System
				.getenv(Constants.PAASAGE_ENV_CONFIG);
		if (paasageConfigurationFullPath == null) {
			paasageConfigurationFullPath = Constants.PAASAGE_CONFIG_DIR;
		}

		return paasageConfigurationFullPath;
	}

	/**
	 * Retrieves the full directory path of the given properties file
	 * 
	 * @param propertiesFileName
	 *            the properties filename
	 * @return full directory path
	 */
	private static String retrievePropertiesFilePath(String propertiesFileName) {
		Path configPath = Paths.get(retrieveConfigurationDirectoryFullPath());
		return configPath.resolve(propertiesFileName).toAbsolutePath()
				.toString();
	}

	/**
	 * Loads the PaaSage's properties file, which also includes the log4j
	 * properties. If no log4j properties are found, then the default ones will
	 * be created.
	 * 
	 * @return a Properties instance
	 */
	public static Properties loadPropertyFile() {
		String propertyPath = retrievePropertiesFilePath(Constants.PROFILER_CONFIG_FILE);
		System.out.println("\nproperty path " + propertyPath);
		if (paasageProperties == null) {
			System.out.println("** Empty paasageProperties");
			paasageProperties = new Properties();

			// Try to configure log4j using the component property file else use
			// a default configuration
			PropertyConfigurator.configure(paasageProperties);

			if (log == null) {
				System.out.println("** Empty logger!");
				log = Logger.getLogger(PropertiesReader.class);
			}
		}

		try {
			paasageProperties.load(new FileInputStream(propertyPath));
			System.out.println("** load properties");
		} catch (java.io.IOException e) {
			paasageProperties.put("log4j.rootLogger", "debug, stdout");
			paasageProperties.put("log4j.appender.stdout",
					"org.apache.log4j.ConsoleAppender");
			paasageProperties.put("log4j.appender.stdout.Target", "System.out");
			paasageProperties.put("log4j.appender.stdout.layout",
					"org.apache.log4j.PatternLayout");
			paasageProperties.put(
					"log4j.appender.stdout.layout.ConversionPattern",
					"%d{ABSOLUTE} %5p %c{1}:%L - %m%n");
			log.debug("eu.paasage.rp.util.PropertiesReader.loadPropertyFile() using default log4j properties.");
			System.out
					.println("eu.paasage.rp.util.PropertiesReader.loadPropertyFile() using default log4j properties.");
		}

		log.debug("eu.paasage.rp.util.PropertiesReader.loadPropertyFile() loading "
				+ propertyPath);
		System.out
				.println("eu.paasage.rp.util.PropertiesReader.loadPropertyFile() loading "
						+ propertyPath);
		return paasageProperties;
	}

	/**
	 * Gets the property name of the given configuration name. The config names
	 * are defined in {@link eu.paasage.upperware.profiler.rp.Constants}.
	 * 
	 * @param configName name of configuration file
	 * @see eu.paasage.upperware.profiler.rp.Constants#PAASAGE_ENV_CONFIG
	 * @return the property name or <tt>null</tt> if not found
	 */
	public static String getProperty(String configName) {
		if (paasageProperties == null) {
			paasageProperties = loadPropertyFile();

			// Try to configure log4j using the component property file else use
			// a default configuration
			PropertyConfigurator.configure(paasageProperties);

			if (log == null) {
				log = Logger.getLogger(PropertiesReader.class);
			}
		}

		log.debug("eu.paasage.rp.util.PropertiesReader.getProperty() for "
				+ configName);
		return paasageProperties.getProperty(configName);
	}

	// //////////////////////////////////////////////////////////////////
	// The properties file need to be stored first in
	// Constants.PAASAGE_CONFIG_DIR
	// before running this program
	public static void main(String[] args) {
		Properties paasageProperties = PropertiesReader.loadPropertyFile();

		PropertyConfigurator.configure(paasageProperties);

		log.debug("Hello World!");
		log.debug("PAASAGE_CONFIG_DIR = "
				+ paasageProperties.getProperty(Constants.PAASAGE_CONFIG_DIR));
		System.out.println("-- PAASAGE_CONFIG_DIR = "
				+ paasageProperties.getProperty(Constants.PAASAGE_CONFIG_DIR));

		log.debug("RP_TEMP_DIR = "
				+ paasageProperties.getProperty("RP_TEMP_DIR"));
		System.out.println("-- RP_TEMP_DIR = "
				+ paasageProperties.getProperty("RP_TEMP_DIR"));
		log.debug("PROFILER_QUEUE_NAME = "
				+ paasageProperties.getProperty("PROFILER_QUEUE_NAME"));
		System.out.println("-- PROFILER_QUEUE_NAME = "
				+ paasageProperties.getProperty("PROFILER_QUEUE_NAME"));
	}
}
