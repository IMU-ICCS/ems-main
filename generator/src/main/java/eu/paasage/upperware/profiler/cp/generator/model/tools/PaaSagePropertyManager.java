/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.cp.generator.model.tools;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Properties;

/**
 * This class provides services to deal with paasage configuration properties
 * @author danielromero
 *
 */
public class PaaSagePropertyManager 
{
	
	/*
	 * ATTRIBUTES
	 */
	/*
	 * Upperware properties
	 */
	protected Map upperwareProperties; 
	
	/*
	 * Generator properties
	 */
	protected Map cpGeneratorProperties;
	
	/*
	 * Singleton 
	 */
	private static PaaSagePropertyManager manager;
	
	/*
	 * CONSTANTS
	 */
	private static final String ENV_CONFIG="PAASAGE_CONFIG_DIR";
	
	private static final String JAVA_CONFIG="eu.paasage.config.dir";
	
	private static final String DEFAULT_PAASAGE_CONFIG_DIR =".paasage";

	
	/*
	 * CONSTRUCTOR
	 */
	/**
	 * Default constructor
	 */
	private PaaSagePropertyManager()
	{
		loadPropertyFiles();
	}
	
	/*
	 * METHODS
	 */
	/**
	 * Singleton pattern. Provides the instace of the manager
	 * @return The manager instance
	 */
	public static PaaSagePropertyManager getInstance()
	{
		if(manager==null)
		{
			manager= new PaaSagePropertyManager(); 
		}
		
		return manager; 
	}
	
	/**
	 * Searches for paasage configuration directory
	 * @return The paasage configuration directory
	 */
    private static String retrieveConfigurationDirectoryFullPath()
    {
    	//First try the Environment Variable
        String paasageConfigurationFullPath = System.getenv(ENV_CONFIG);
        if (paasageConfigurationFullPath == null)
        {
        	//Java property
        	paasageConfigurationFullPath= System.getProperty(JAVA_CONFIG); 
        	
        	//Try the home
        	if(paasageConfigurationFullPath==null)
        	{
        	
	            String home = System.getProperty("user.home");
	            Path homePath = Paths.get(home);
	            paasageConfigurationFullPath = homePath.resolve(DEFAULT_PAASAGE_CONFIG_DIR).toAbsolutePath().toString();
        	} 
        }
        return paasageConfigurationFullPath;
    }
    
    /**
     * Retrieves properties file path
     * @param propertiesFileName The name of the properties file
     * @return The properties file path
     */
    private static String retrievePropertiesFilePath(String propertiesFileName)
    {
        Path configPath = Paths.get(retrieveConfigurationDirectoryFullPath());
        return configPath.resolve(propertiesFileName).toAbsolutePath().toString();
    }
	
    /**
     * Loads a given properties file
     * @param propertyFile The properties file
     * @return The properties file
     */
    public static Properties loadPropertyFile(String propertyFile)
    {
        String propertyPath = retrievePropertiesFilePath(propertyFile);
        
        File pf= new File(propertyPath); 
        
        Properties props = new Properties();
        
        if(pf.isFile())
	        try {
	            props.load(new FileInputStream(propertyPath));
	        } catch (java.io.IOException e) {
	            //TODO: fill props with default values for component
	        	e.printStackTrace();
	           /*props.put("log4j.rootLogger","debug, stdout");
	            props.put("log4j.appender.stdout"                         ,"org.apache.log4j.ConsoleAppender");
	            props.put("log4j.appender.stdout.Target"                  ,"System.out");
	            props.put("log4j.appender.stdout.layout"                  ,"org.apache.log4j.PatternLayout");
	            props.put("log4j.appender.stdout.layout.ConversionPattern","%d{ABSOLUTE} %5p %c{1}:%L - %m%n");*/
	        }
        return props;
    }
    
    /**
     * Loads the properties files
     */
    public void loadPropertyFiles()
    {
    	upperwareProperties= loadPropertyFile("wp3_config.properties");
  
    	cpGeneratorProperties= loadPropertyFile("wp3_cp_generator.properties"); 
    }
    
    /**
     * Retrieves a property related to the upperware
     * @param key The property key 
     * @return The property value
     */
    public String getUpperwareProperty(String key)
    {
    	return (String) upperwareProperties.get(key); 
    }
    
    /**
     * Retrieves a property related to the CP Generator
     * @param key The property key 
     * @return The property value
     */
    public String getCPGeneratorProperty(String key)
    {
    	return (String) cpGeneratorProperties.get(key); 
    }
    
    public void addCPGeneratorProperty(String key, String value)
    {
    	cpGeneratorProperties.put(key, value);
    }
    
}
