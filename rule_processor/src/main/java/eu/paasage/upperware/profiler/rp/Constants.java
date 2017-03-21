/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp;

/**
 * @author Anthony Sulistio
 * @author Dennis Hoppe
 */
public final class Constants {

	/** Name of the properties file */
	public static final String PROFILER_CONFIG_FILE = "wp3_profiler.properties";
	
	/** PaaSage environmental variable */
	public static final String PAASAGE_ENV_CONFIG = "PAASAGE_CONFIG_DIR";

	/** The default location of the PaaSage configuration directory */
	public static final String PAASAGE_CONFIG_DIR = "/etc/paasage";

	/*
	 * ZeroMQ: Subscriber
	 */
	public static String SUBSCRIBER_PROTOCOL_PROPERTY = "SUBSCRIBER_PROTOCOL";
	public static String SUBSCRIBER_HOST_PROPERTY = "SUBSCRIBER_HOST";
	public static String SUBSCRIBER_PORT_PROPERTY = "SUBSCRIBER_PORT";
	public static String SUBSCRIBER_TOPIC_PROPERTY = "SUBSCRIBER_TOPIC";
	
	/*
	 * ZeroMQ: Publisher
	 */
	public static String PUBLISHER_PROTOCOL_PROPERTY = "PUBLISHER_PROTOCOL";
	public static String PUBLISHER_HOST_PROPERTY = "PUBLISHER_HOST";
	public static String PUBLISHER_PORT_PROPERTY = "PUBLISHER_PORT";
	public static String PUBLISHER_TOPIC_PROPERTY =	"PUBLISHER_TOPIC";
	
	/*
	 * ZeroMQ: Defaults
	 */
	public static String DEFAULT_SUBSCRIBER_PROTOCOL="tcp://";
	public static String DEFAULT_SUBSCRIBER_HOST="localhost";
	public static String DEFAULT_SUBSCRIBER_PORT="5544"; 
	public static String DEFAULT_SUBSCRIBER_TOPIC="startSolving";

	/*
	 * ZeroMQ: Defaults
	 */
	public static String DEFAULT_PUBLISHER_PROTOCOL="tcp://"; 
	public static String DEFAULT_PUBLISHER_HOST="*";
	public static String DEFAULT_PUBLISHER_PORT="5545";  
	public static String DEFAULT_PUBLISHER_TOPIC="RPSolutionAvailable";

}
