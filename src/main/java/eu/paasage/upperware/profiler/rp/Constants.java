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
 *
 */
public final class Constants {
	/** Name of the resource bundle that holds the service's configuration. */
	public static final String BUNDLE_NAME = "paasage";

	/** The default filename of an application's CP model. */
	public static final String DEFAULT_CP_MODEL_FILENAME = "cpModel.xmi";

	/** Property in the resource bundle mentioning the Profiler's queue name. */
	public static final String INCOMING_QUEUE_NAME_PROPERTY = "PROFILER_QUEUE_NAME";

	/** Property in the resource bundle mentioning the Profiler's host name. */
	public static final String PROFILER_HOST_NAME_PROPERTY = "PROFILER_HOST_NAME";

	/** Property in the resource bundle mentioning the Profiler's port number. */
	public static final String PROFILER_PORT_NUM_PROPERTY = "PROFILER_PORT_NUM";

	/**
	 * Property in the resource bundle mentioning the outgoing queue name for
	 * sending the result to Metasolver.
	 */
	public static final String OUTGOING_QUEUE_NAME_PROPERTY = "OUTGOING_QUEUE_NAME";

	/** Property in the resource bundle mentioning the temporary directory */
	public static final String TEMP_DIRECTORY_PROPERTY = "RP_TEMP_DIR";

	/** Property in the resource bundle mentioning the host name for Metasolver */
	public static final String METASOLVER_HOST_NAME_PROPERTY = "METASOLVER_HOST_NAME";

	/**
	 * Property in the resource bundle mentioning the port number for Metasolver
	 */
	public static final String METASOLVER_PORT_NUM_PROPERTY = "METASOLVER_PORT_NUM";

	/** Property in the resource bundle mentioning the host name for CDO server */
	public static final String CDO_HOST_PROPERTY = "CD0_HOST";

	/**
	 * Property in the resource bundle mentioning the port number for CDO server
	 */
	public static final String CDO_PORT_PROPERTY = "CDO_PORT";

	/**
	 * Property in the resource bundle mentioning the repository name for CDO server
	 */
	public static final String CDO_REPO_PROPERTY = "CD0_REPO";

	public static final String DEFAULT_HOST = "localhost";
	public static final int DEFAULT_CDO_PORT = 2036;
	public static final String DEFAULT_CDO_REPO = "repo1";

	/** The default environment config */
	public static final String PAASAGE_ENV_CONFIG = "PAASAGE_CONFIG_DIR";

	/** The default location of the PaaSage configuration directory */
	public static final String PAASAGE_CONFIG_DIR = "/etc/paasage";

	/** The default location of the PaaSage directory */
	public static final String PAASAGE_HOME_DEFAULT = PAASAGE_CONFIG_DIR;

	/**
	 * Location of the Image Creation Service's property file (inside the
	 * PAASAGE_HOME_DEFAULT directory)
	 */
	public static final String PROFILER_CONFIG_FILE = "wp3_profiler.properties";
}
