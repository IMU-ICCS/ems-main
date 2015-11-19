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
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.solvertodeployment.utils.DataHolder;
import eu.paasage.upperware.solvertodeployment.utils.DataUtils;
//import eu.paasage.upperware.solvertodeployment.zeromq.S2D_ZMQ_Service;

public class SolverToDeployment {

	private static Logger log = Logger.getLogger(SolverToDeployment.class);
	
	public static boolean doWorkTS(String paasageConfigurationID, String camelModelID, long solutionTS) throws S2DException
	{
		try {

			CDODatabaseProxy cdoProxy = CDODatabaseProxy.getInstance();
			CDOView cdoView = cdoProxy.getCdoClient().openView();

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

			// Computing solutionId from solutionTS
			int solutionId=0;
			try {
				for(Solution sol : constraintProblem.getSolution()) {
					if (sol.getTimestamp() == solutionTS) {
						break;
					}
					solutionId++;
				}
				log.info("Timestamp not found ... using last solution entry");
				solutionId = constraintProblem.getSolution().size()-1;
			} catch(IndexOutOfBoundsException e)
			{
				log.fatal("SolutionId "+Integer.toString(solutionId)+" is not valid! It has to be between 0 and "+Integer.toString(+constraintProblem.getSolution().size()-1));
				return false;
			}
		
			// Do Work
			try {
				// copy provider to source camel doc
				String results[] = paasageConfigurationID.split("/");
				String fmsId = results[1];
				DataUtils.copyCloudProviders(camelModel, camelModelID, fmsId, paasageConfiguration, constraintProblem, solutionId);

				// Generate new stuff into camel
				DataHolder dataholder  = DataUtils.computeDatasToRegister(paasageConfiguration, deploymentModel, constraintProblem, solutionId);
				DataUtils.registerDataHolderToCDO(camelModelID, dataholder);

				// export to debug output
//				log.info("Saving modified CAMEL model to /tmp/Mod.xmi");
//				client.exportModelWithRefRec(camelModelID, "/tmp/Mod2.xmi", false);
//				client.exportModel(camelModelID, "/tmp/Mod.xmi");
//				log.info("Saving modified CAMEL model to /tmp/Mod.xmi done");

			} catch (S2DException e) {
				e.printStackTrace();
				log.error("Unable to complete data model instances registration");
				return false;
			}

			log.info("Camel contain " + deploymentModel.getInternalComponentInstances().size() + " internal component instance");
			log.info("Camel contain " + deploymentModel.getVmInstances().size() + " vm instance");
			log.info("Camel contain " + deploymentModel.getHostingInstances().size() + " hosting instance");
			log.info("Camel contain " + deploymentModel.getCommunicationInstances().size() + " communication instance");

		} catch (RuntimeException exception) {
			exception.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {

//		if ((args.length == 1)&&(args[0].equals("-d")))
//		{
//			S2D_ZMQ_Service.getInstance().run();
//		}
		
		if (args.length != 3)
		{
			System.out.println("Wrong usage : args1=paasageconfiguration arg2=camelModel arg3=SslutionTS");
			return ;
		} 

		String paasageConfigurationID = args[0];
		String camelModelID = args[1];
		long solutionTS = Long.valueOf(args[2]);
		boolean res;
		try
		{
			res = doWorkTS(paasageConfigurationID, camelModelID, solutionTS);
		} catch (S2DException e)
		{
			e.printStackTrace();
			log.info("Solver to deployment done");
			res=false;
		}
		if (res) {
			log.info("Solver to deployment done");
			System.exit(0);
		} else
			System.exit(-1);
	}
}
