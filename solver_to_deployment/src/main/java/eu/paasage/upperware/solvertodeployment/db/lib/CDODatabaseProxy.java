/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.db.lib;

import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CDODatabaseProxy {

	private CDOClient cdoClient;

	/**
	 * Default constructor
	 */
	private CDODatabaseProxy() {
		cdoClient = new CDOClient();
		registerPackages();
	}

	public static CDODatabaseProxy getInstance() {
		return new CDODatabaseProxy();
	}

	/**
	 * Registers the package of the used models in the database
	 */
	private void registerPackages() {
		cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
		cdoClient.registerPackage(CpPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
		cdoClient.registerPackage(TypePackage.eINSTANCE);

		cdoClient.registerPackage(CamelPackage.eINSTANCE);
		cdoClient.registerPackage(ProviderPackage.eINSTANCE);

		cdoClient.registerPackage(OrganisationPackage.eINSTANCE);
		cdoClient.registerPackage(DeploymentPackage.eINSTANCE);
	}

	public CDOClient getCdoClient() {
		return cdoClient;
	}
}
