/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.lib;

import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.solvertodeployment.utils.DataHolder;
import eu.paasage.upperware.solvertodeployment.utils.DataUtils;

public class SolverToDeployment {

	private static Logger log = Logger.getLogger(SolverToDeployment.class);

	public static void main(String paasageConfigurationID, String camelModelID) throws S2DException
	{
		try {

			CDODatabaseProxy cdoProxy = CDODatabaseProxy.getInstance();
			CDOView cdoView = cdoProxy.getCdoClient().openView();

			CDOClient client = new CDOClient();

			EList<EObject> contents = cdoView.getResource(paasageConfigurationID).getContents();
			PaasageConfiguration paasageConfiguration = (PaasageConfiguration) contents.get(0);

			EList<EObject> contents2 = cdoView.getResource(camelModelID).getContents();
			CamelModel camelModel= (CamelModel)contents2.get(0);
			DeploymentModel deploymentModel = camelModel.getDeploymentModels().get(0);

//			log.info("Saving original CAMEL model to /tmp/Ori.xmi");
//			client.exportModel (camelModelID, "/tmp/Ori.xmi");
//			client.exportModelWithRefRec(camelModelID, "/tmp/Ori2.xmi", false);
//			log.info("Saving original CAMEL model to /tmp/Ori.xmi done");

			ConstraintProblem constraintProblem = (ConstraintProblem) contents.get(1);
			try {
				// copy provider to source camel doc
				String results[] = paasageConfigurationID.split("/");
				String fmsId = results[1];
				DataUtils.copyCloudProviders(camelModelID, fmsId, paasageConfiguration, constraintProblem);

				// Generate new stuff into camel
				DataHolder dataholder  = DataUtils.computeDatasToRegister(paasageConfiguration, deploymentModel, constraintProblem);
				DataUtils.registerDataHolderToCDO(camelModelID, dataholder);

				// export to debug output
//				log.info("Saving modified CAMEL model to /tmp/Mod.xmi");
//				client.exportModelWithRefRec(camelModelID, "/tmp/Mod2.xmi", false);
//				client.exportModel(camelModelID, "/tmp/Mod.xmi");
//				log.info("Saving modified CAMEL model to /tmp/Mod.xmi done");

			} catch (S2DException e) {
				e.printStackTrace();
				log.error("Unable to complete data model instances registration");
			}

			log.info("Camel contain " + deploymentModel.getInternalComponentInstances().size() + " internal component instance");
			log.info("Camel contain " + deploymentModel.getVmInstances().size() + " vm instance");
			log.info("Camel contain " + deploymentModel.getHostingInstances().size() + " hosting instance");
			log.info("Camel contain " + deploymentModel.getCommunicationInstances().size() + " communication instance");

		} catch (RuntimeException exception) {
			exception.printStackTrace();
			System.exit(0);
		}
	}

	public static void main(String[] args) {

		if (args.length != 2) {
			System.out.println("Wrong usage : args1=paasageconfiguration, arg2=camelModel");
		} 

		String paasageConfigurationID = args[0];
		String camelModelID = args[1];
		try {
			main(paasageConfigurationID, camelModelID);
		} catch (S2DException e) {

			e.printStackTrace();
			log.info("Solver to deployment done");
			System.exit(1);
		}
		log.info("Solver to deployment done");
		System.exit(0);

	}
}
