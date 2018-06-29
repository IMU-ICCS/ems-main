/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver.properties;

import eu.melodic.upperware.metasolver.metricvalue.TopicType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@ToString
@Validated
@Configuration
@ConfigurationProperties
@Slf4j
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.metaSolver.properties")
public class MetaSolverProperties {

	@Valid
	@NotNull
	private Esb esb;
	
	@Getter
	@Setter
	public static class Esb {
		@NotBlank
		private String url;
	}
	
	// --------------------------------------------------------------
	
	@Valid
	@NotNull
	private Pubsub pubsub;

	@Getter
	@Setter
	@ToString
	public static class Pubsub {
		private String on;

		private List<Topic> topics;

		public boolean isOn() {
			return booleanValue(on);
		}

		@Getter
		@Setter
		@ToString
		public static class Topic {
			@NotBlank
			private String name;
			@NotBlank
			private String url;
			private String clientId;
			private TopicType type;
		}
	}
	
	public static boolean booleanValue(String str) {
		return booleanValue(str, false);
	}
	
	public static boolean booleanValue(String str, boolean defVal) {
		if (str==null || (str=str.trim()).isEmpty()) return defVal;
		return (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("on"));
	}
	
	// --------------------------------------------------------------
	
	/*@Valid
	@NotNull
	private CdoConfig cdo;

	@Getter
	@Setter
	@ToString(exclude="password")
	public static class CdoConfig {
		private String host = "localhost";
		private int port = 2036;
		private String repositoryName = "repo1";
		private boolean logging = false;
		private boolean secure = false;
		private String username;
		private String password;
	}*/
	
	public static boolean parseBoolean(String s, boolean def) {
		if (s==null || s.trim().isEmpty()) return def;
		s = s.toLowerCase();
		return (s.equals("true") || s.equals("on") || s.equals("yes") || s.equals("enabled"));
	}
	
	/*protected static String getCdoConfigFile() {
		String configPath = System.getProperty("eu.paasage.configdir", System.getenv("PAASAGE_CONFIG_DIR"));
		if (configPath==null || configPath.trim().isEmpty()) configPath = "/";
		if (!configPath.endsWith("/")) configPath += "/";
		String configFile = configPath + "eu.paasage.mddb.cdo.client.properties";
		return configPath;
	}
	
	public void loadCdoConfig() {
		// Get CDO client configuration path using the PaaSage style
		// i.e. the -Deu.paasage.configdir system property or the PAASAGE_CONFIG_DIR environment variable
		String configPath = System.getProperty("eu.paasage.configdir", System.getenv("PAASAGE_CONFIG_DIR"));
		if (configPath==null || configPath.trim().isEmpty()) configPath = "/";
		if (!configPath.endsWith("/")) configPath += "/";
		String configFile = configPath + "eu.paasage.mddb.cdo.client.properties";
		
		// Load CDO client configuration from file
		log.debug("MetaSolverProperties: Loading CDO config from file: {}", configFile);
		java.util.Properties cdoCfg = new java.util.Properties();
		try (java.io.Reader reader = new java.io.FileReader(new java.io.File(configFile))) { cdoCfg.load(reader); }
		catch (java.io.FileNotFoundException ex) { log.error("MetaSolverProperties: CDO config file not found: {}", configFile); }
		catch (java.io.IOException ex) { log.error("MetaSolverProperties: Error while reading CDO config file: {}: {}", configFile, ex); }
		catch (Exception ex) { log.error("MetaSolverProperties: Error while loading CDO config from file: {}: {}", configFile, ex); }
		log.debug("MetaSolverProperties: Loaded CDO config from file: {}", configFile);
		log.debug("MetaSolverProperties: Loaded CDO config from file: {}", cdoCfg);
		
		// Set CDO config. properties
		cdo.setHost( cdoCfg.getProperty("host") );
		cdo.setPort( Integer.parseInt(cdoCfg.getProperty("port", "2036")) );
		cdo.setRepositoryName( cdoCfg.getProperty("repository") );
		cdo.setLogging( parseBoolean(cdoCfg.getProperty("logging", "off"), false) );
		cdo.setSecure( parseBoolean(cdoCfg.getProperty("secure", "off"), false) );
		cdo.setUsername( cdoCfg.getProperty("username") );
		cdo.setPassword( cdoCfg.getProperty("password") );
		log.debug("MetaSolverProperties: CDO config updated: {}", cdo);
	}*/
			
	// --------------------------------------------------------------
	
	@Valid
	@NotNull
	private double utilityThresholdFactor;
}
