/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.lib;

import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;

import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.utils.DataHolder;
import eu.paasage.upperware.solvertodeployment.utils.DataUtils;
//import eu.paasage.upperware.solvertodeployment.zeromq.S2D_ZMQ_Service;

public class SolverToDeployment {

	private static Logger log = Logger.getLogger(SolverToDeployment.class);
	
	public static boolean doWorkTS(String paasageConfigurationID, String camelModelID, String CPDirID, long solutionTS, boolean TSavailable) throws S2DException
	{
		try {

			CDODatabaseProxy cdoProxy = CDODatabaseProxy.getInstance();
			CDOView cdoView = cdoProxy.getCdoClient().openView();

			EList<EObject> contents = cdoView.getResource(paasageConfigurationID).getContents();
			PaasageConfiguration paasageConfiguration = (PaasageConfiguration) contents.get(0);

			EList<EObject> contents2 = cdoView.getResource(camelModelID).getContents();
			CamelModel camelModel= (CamelModel)contents2.get(0);
//			DeploymentModel srcDm = camelModel.getDeploymentModels().get(0);

//			log.info("Saving original CAMEL model to /tmp/Ori.xmi");
//			client.exportModel (camelModelID, "/tmp/Ori.xmi");
//			client.exportModelWithRefRec(camelModelID, "/tmp/Ori2.xmi", false);
//			log.info("Saving original CAMEL model to /tmp/Ori.xmi done");

			ConstraintProblem constraintProblem = (ConstraintProblem) contents.get(1);

			// Checking if there is a solution
			if (constraintProblem.getSolution().size()==0)
			{
				log.info("No solution available!");
				return false;
			}
			
			// Computing solutionId from solutionTS
			int solutionId=-1;
			Long maxTS= -1L;
			int maxID=-1;
			for(int i =0; i<constraintProblem.getSolution().size(); i++) {
				Solution sol = constraintProblem.getSolution().get(i);
				if (sol.getTimestamp() == solutionTS) {
					solutionId = i;
					break;
				} else if (sol.getTimestamp() > maxTS)
				{
					maxTS = sol.getTimestamp();
					maxID=i;
				}
			}
			if (TSavailable)
			{
				if (solutionId==-1)
				{
					log.info("Timestamp "+solutionTS+" not found");
					return false;
				}
			} else 
			{
				log.info("Using the solution with highest TS: "+maxTS);
				solutionId = maxID;
			}
			log.info("Using entry: "+solutionId);
			
			DeploymentModel newDm=null;				
			// Do Work
			try {
				// copy provider to source camel doc
				String results[] = CPDirID.split("/");
				String fmsId = results[1];
				DataUtils.copyCloudProviders(camelModel, camelModelID, fmsId, paasageConfiguration, constraintProblem, solutionId);

				// Create a new DM to store the instances from solution
				int newDmId = CDODatabaseProxy2.copyDeploymentModel(camelModelID, 0);
				newDm = (DeploymentModel) camelModel.getDeploymentModels().get(newDmId);
				
				// Generate new instances into this new DM of camel
				DataHolder dataholder  = DataUtils.computeDatasToRegister(paasageConfiguration, newDm, constraintProblem, solutionId);
				if (dataholder==null) return false;

				dataholder.setDM(newDm);
				dataholder.setDmId(camelModel.getDeploymentModels().size()-1);
				DataUtils.registerDataHolderToCDO(camelModelID, dataholder); // COPY TO CDO

				// export to debug output
//				log.info("Saving modified CAMEL model to /tmp/Mod.xmi");
////				new CDOClient().exportModelWithRefRec(camelModelID, "/tmp/Mod2.xmi", false);
//				new CDOClient().exportModel(camelModelID, "/tmp/Mod.xmi");
//				log.info("Saving modified CAMEL model to /tmp/Mod.xmi done");

			} catch (S2DException | CommitException e) {
				e.printStackTrace();
				log.error("Unable to complete data model instances registration");
				return false;
			}

			log.info("Camel doc contains " + newDm.getInternalComponentInstances().size() + " internal component instance");
			log.info("Camel doc contains " + newDm.getVmInstances().size() + " vm instance");
			log.info("Camel doc contains " + newDm.getHostingInstances().size() + " hosting instance");
			log.info("Camel doc contains " + newDm.getCommunicationInstances().size() + " communication instance");

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
		
		if ((args.length < 3)||(args.length>4))
		{
			System.out.println("Wrong usage: S2D CPCdoId CamelCdoId CloudProviderCdoDirID [SolutionTS]");
			return ;
		} 

		String paasageConfigurationID = args[0];
		String camelModelID = args[1];
		String CPDirId = args[2];
		long solutionTS=-1;
		boolean TSavailable;
		if (args.length==4)
		{
			 solutionTS = Long.valueOf(args[3]);
			 TSavailable=true;
		} else
		{
			solutionTS=-1;
			TSavailable=false;
		}
		boolean res;
		try
		{
			res = doWorkTS(paasageConfigurationID, camelModelID, CPDirId, solutionTS, TSavailable);
		} catch (S2DException e)
		{
			e.printStackTrace();
			log.info("Solver to deployment: Failed.");
			res=false;
		}
		if (res) {
			log.info("Solver to deployment: All done.");
			System.exit(0);
		} else
			System.exit(-1);
	}
}
