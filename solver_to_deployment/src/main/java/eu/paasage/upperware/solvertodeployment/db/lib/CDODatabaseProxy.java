///* This Source Code Form is subject to the terms of the Mozilla Public
// * License, v. 2.0. If a copy of the MPL was not distributed with this
// * file, You can obtain one at http://mozilla.org/MPL/2.0/. */
//
//package eu.paasage.upperware.solvertodeployment.db.lib;
//
//import camel.deployment.DeploymentPackage;
//import camel.organisation.OrganisationPackage;
//import camel.type.TypePackage;
//import eu.paasage.mddb.cdo.client.exp.CDOClientX;
//import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
//import eu.paasage.upperware.metamodel.cp.CpPackage;
//import eu.paasage.upperware.metamodel.types.TypesPackage;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//
//import java.util.Arrays;
//
//@Slf4j
//public class CDODatabaseProxy {
//
//	private static CDODatabaseProxy INSTANCE = new CDODatabaseProxy();
//
//	@Getter
//	private CDOClientX cdoClient;
//
//	private CDODatabaseProxy() {
//		cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE, TypesPackage.eINSTANCE,
//				TypePackage.eINSTANCE, CamelPackage.eINSTANCE, ProviderPackage.eINSTANCE,
//				OrganisationPackage.eINSTANCE, DeploymentPackage.eINSTANCE));
//	}
//
//	public static CDODatabaseProxy getInstance() {
//		return INSTANCE;
//	}
//
//}
