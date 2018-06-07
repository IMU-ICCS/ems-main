/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.generator.db;

import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;
import eu.passage.upperware.commons.MelodicConstants;
import eu.passage.upperware.commons.model.tools.ModelTool;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;

/**
 * Database proxy for CDO
 */
@Slf4j
@Service
public class CDODatabaseProxy implements IDatabaseProxy {

	private CPCloner cloner;
	private CDOClientExtended cdoClient;

	@Autowired
	public CDODatabaseProxy(ApplicationContext applicationContext) {
		this.cdoClient = (CDOClientExtended) applicationContext.getBean(CDOClient.class);
		cloner = new CPCloner();

		registerPackages();
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

	public void saveModels(PaasageConfiguration pc, ConstraintProblem cp) {
		String pcId= cp.getId();

		log.debug("CDODatabaseProxy - saveModels - Storing Models ");
		String cpPath = CDO_SERVER_PATH + pcId;

		cdoClient.exportModel(pc, "/logs/pc_model_"+cpPath+".xmi");
		cdoClient.exportModel(cp, "/logs/cp_model_"+cpPath+".xmi");

		cdoClient.storeModels(Arrays.asList(pc, cp), cpPath);
		log.debug("CDODatabaseProxy - saveModels - Models stored! ");

		File configurationDir= ModelTool.getGenerationDirForPaasageAppConfiguration(pcId);

		File paasageConfigModel= new File(configurationDir, MelodicConstants.PAASAGE_CONFIGURATION_MODEL_FILE_NAME);
		File cpModel= new File(configurationDir, MelodicConstants.CP_MODEL_FILE_NAME);

		List<EObject> content = cloner.cloneModel(cpPath);
		log.debug("CDODatabaseProxy - saveModels - Saving file {0}", paasageConfigModel.getAbsolutePath());
		cdoClient.exportModel(content.get(0), paasageConfigModel.getAbsolutePath());
		cdoClient.exportModel(content.get(1), cpModel.getAbsolutePath());
	}

}
