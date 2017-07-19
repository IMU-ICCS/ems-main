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

/**
 * This interfaces defines common constants used in the CP Generator
 * @author danielromero
 *
 */
public interface Constants 
{
	
	public static final String CLOUD_ML_MODEL_TYPE="cloudML"; 
	
	public static final String SALOON_ONTOLOGY_MODEL_TYPE="saloonOntology"; 
	
	public static final String CAMEL_MODEL_TYPE="camel"; 
	
	public static final String MODELS_DEFAULT_PATH= "."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"model"+File.separator;
	
	public static final String WAR_CONFIG_PATH=File.separator+"config"+File.separator; 
	
	public static final String JAR_CONFIG_PATH=File.separator+"config"+File.separator; 
	
	public static final String FM_DEFAULT_PATH= File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"model"+File.separator+"fm"+File.separator; 
	
	public static final String CONFIG_FILES_DEFAULT_PATH= "."+File.separator+"src"+File.separator+"main"+File.separator+"resources"+File.separator+"config"+File.separator;
	
	public static final String CONFIG_FILES_DEFAULT_PATH_TEST= File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator+"config"+File.separator;
	
	public static final String MB_UNIT="MB"; 
	
	public static final String GB_UNIT="GB"; 
	
	public static final String TB_UNIT="TB";
	
	public static final String MHZ_UNIT="MHz";
	
	public static final String GHZ_UNIT="GHz"; 
	
	public static final String PROVIDER_ID_SEPARATOR="_"; 
	
	public static final int MAX_CARDINALITY_UPPER_VALUE=100; 
	
	public static final String TEMP_DIR= "."+File.separator+"temp"+File.separator; 
	
	public static final String GENERATION_DIR= "paasage"+File.separator+"configurations"+File.separator; 
	
	public static final String TOMCAT_TEM_DIR= ".."+File.separator+"temp"+File.separator; 
	
	public static final String TOMCAT_ALT_TEM_DIR= File.separator+"tmp"+File.separator; 
	
	public static final String TOMCAT_GENERATION_DIR= TOMCAT_TEM_DIR+"paasage"+File.separator+"configurations"+File.separator; 

	public static final String TOMCAT_ALT_GENERATION_TEMP_DIR= TOMCAT_ALT_TEM_DIR+"paasage"+File.separator+"configurations"+File.separator; 
	
	public static final String CLOUD_PRICING_FILE= CONFIG_FILES_DEFAULT_PATH+"cloudPricing.txt"; 
	
	public static final String PAASAGE_CONFIGURATION_MODEL_FILE_NAME= "paasageConfigurationModel.xmi"; 
	
	public static final String CP_MODEL_FILE_NAME= "cpModel.xmi"; 
	
	public static final String UPLOAD_FILES_DIR= "uploadFile";
	
	public static final String UPLOAD_FILES_DIR_PATH= GENERATION_DIR+UPLOAD_FILES_DIR; 
	
	public static final String PAASAGE_MODEL_DESCRIPTOR_FILE="descriptor.paasage"; 
		
	public static final String TOMCAT_CONFIG_PATH=".."+File.separator+"webapps"+File.separator+"cp-generator-service"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"config"+File.separator; 

	public static final String TOMCAT_MODEL_PATH=".."+File.separator+"webapps"+File.separator+"cp-generator-service"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"model"+File.separator; 
	
	public static final String TOMCAT_ALT_MODEL_PATH="."+File.separator+"webapps"+File.separator+"cp-generator-service"+File.separator+"WEB-INF"+File.separator+"classes"+File.separator+"model"+File.separator; 

	public static final String UPLOAD_FILES_DIR_TOMCAT=TOMCAT_GENERATION_DIR+"uploadFile"; 	
	
	public static final String UPLOAD_FILES_DIR_TOMCAT_ALT=TOMCAT_ALT_GENERATION_TEMP_DIR+"uploadFile"; 	
	
	public static final String WAR_MODEL_PATH=File.separator+"model"+File.separator; 
	
	public static final String CP_DIR="cp"; 
	
	public static final String ONTOLOGY_DIR="ontology"; 
	
	public static final String WAR_ONTOLOGY_PATH=WAR_MODEL_PATH+File.separator+ONTOLOGY_DIR+File.separator; 
	
	public static final String WAR_CP_DIR_PATH=WAR_MODEL_PATH+File.separator+CP_DIR; 
	
	public static final String OPERATING_SYSTEMS_MODEL_FILE_NAME= "OperatingSystems.xmi"; 
	
	public static final String FUNCTION_TYPES_MODEL_FILE_NAME= "FunctionTypes.xmi"; 
	
	public static final String LOCATIONS_MODEL_FILE_NAME= "Locations.xmi"; 
	
	public static final String PROVIDER_TYPES_MODEL_FILE_NAME= "ProviderTypes.xmi"; 
	
	public static final String FILE_NAME_SENDER_PROPERTY_NAME= "fileToSend"; 
	
	
	
	
}
