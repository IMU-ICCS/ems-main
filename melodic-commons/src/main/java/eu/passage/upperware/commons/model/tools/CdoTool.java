/*
 * Copyright (c) 2014-2016 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.passage.upperware.commons.model.tools;


import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;

/**
 * A utility to help interaction with the CDO server.
 * <p>
 *
 * @author Shirley Crompton
 *         org UK Science and Technology Facilities Council
 */
public final class CdoTool {

    /**
     * log4j message logger
     */
    protected static Logger log = Logger.getLogger(CdoTool.class);

    /**
     * Register the PaaSage Upperware Model CDO packages with the {@link eu.paasage.mddb.cdo.client.CDOClient <em>CDOClient</em>}.
     * <p>
     *
     * @param cdoClient the target {@link eu.paasage.mddb.cdo.client.CDOClient <em>CDOClient</em>}
     */
    public static void registerPackages(CDOClient cdoClient) {
        //add the cp packages
        log.debug("Instantiating cdoClient and registering packages...");
        cdoClient = new CDOClient();
//			cdoClient.registerPackage(TypesPackage.eINSTANCE);
        cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
        log.info("Init ApplicationPackag");
        cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
        log.info("Init TypesPaasagePackage");
        cdoClient.registerPackage(TypesPackage.eINSTANCE);
        log.info("Init TypesPackage");
        cdoClient.registerPackage(CpPackage.eINSTANCE);
        log.info("Init CpPackage");
//		cdoClient.registerPackage(OntologyPackage.eINSTANCE);
//		log.info("Init OntologyPackage");
//		cdoClient.registerPackage(MappingPackage.eINSTANCE);
//		log.info("Init MappingPackage");

    }

    /**
     * CP models are stored in CDO server under the upperware_models/ resource path.
     * This function prefix model id with the required resource path.
     * <p>
     *
     * @param current the current CP model identifier {@link java.lang.String <em>String</em>}
     * @return the cleaned identifier {@link java.lang.String <em>String</em>}
     * or null if current is null or empty
     */
    public static String addCdoPrefix(String current) {
        if (StringUtils.isBlank(current)) {
            log.error("cannot addCdoPrefix, current is null/empty!");
            return null;
        }

        return current.startsWith(CDO_SERVER_PATH) ? current : CDO_SERVER_PATH + current;
    }

    public static String removeCdoPrefix(String current) {
        if (StringUtils.isBlank(current)) {
            log.error("cannot removeCdoPrefix, current is null/empty!");
            return null;
        }

        return current.startsWith(CDO_SERVER_PATH) ? current.substring(CDO_SERVER_PATH.length()) : current;
    }
    
}
