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
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.utils.DataHolder;
import eu.paasage.upperware.solvertodeployment.utils.DataUtils;
//import eu.paasage.upperware.solvertodeployment.zeromq.S2D_ZMQ_Service;
import eu.paasage.upperware.solvertodeployment.zeromq.S2D_ZMQ_Service;
import eu.paasage.upperware.solvertodeployment.zeromq.S2D_ZeroMQServer;

public class SolverToDeployment {

	private static Logger log = Logger.getLogger(SolverToDeployment.class);
		
	public static void dumpDM(CamelModel cm, int level)
	{
		log.info("Camel doc contains " + cm.getDeploymentModels().size() + " Deployment Model");
		if (level > 1)
		for(int i=0; i<cm.getDeploymentModels().size(); i++) 
		{
			DeploymentModel dm = cm.getDeploymentModels().get(i);
			log.info("  DM"+i+" :" +
					" InternalComponentInstances: " + dm.getInternalComponentInstances().size()+
					"  VMInstances: "+ dm.getVmInstances().size() +
					"  HostingInstances: "+ dm.getHostingInstances().size() +
					"  CommInstances: " + dm.getCommunicationInstances().size());
			if (level > 2)
			{
				String out="";
				// ICI
				for(InternalComponentInstance ici : dm.getInternalComponentInstances())
					out+=ici.getName()+" ";
				log.info("    InternalComponentInstances: "+out);
				// VMI
				out="";
				for(VMInstance vm : dm.getVmInstances())
					out+=vm.getName()+" ";
				log.info("    VMInstances: "+out);
				// HI
				out="";
				for(HostingInstance hi : dm.getHostingInstances())
					out+=hi.getName()+" ";
				log.info("    HostingInstances: "+out);
				// CI
				out="";
				for(CommunicationInstance comi : dm.getCommunicationInstances())
					out+=comi.getName()+" ";
				log.info("    CommIntances: "+out);

			}
		}

	}
	
	public static boolean doWorkTS(String paasageConfigurationID, String camelModelID, String CPDirID,
			long solutionTS, boolean TSavailable, int dstDMId, boolean overwriteDM, int dumpDMLevel)
					throws S2DException
	{
		log.info("CPID: "+paasageConfigurationID);
		log.info("CamelID: "+camelModelID);
		log.info("CPDirID: "+CPDirID);
		log.info("Timestamp: "+TSavailable+" ts="+solutionTS);
		log.info("OverwriteDM: "+overwriteDM+" ts="+dstDMId);
		log.info("DumpDM: "+dumpDMLevel);

		try {

			CDODatabaseProxy cdoProxy = CDODatabaseProxy.getInstance();
			CDOView cdoView = cdoProxy.getCdoClient().openView();

			EList<EObject> contentsCM = cdoView.getResource(camelModelID).getContents();
			CamelModel camelModel= (CamelModel)contentsCM.get(0);
			
			if (dumpDMLevel>0)
			{
				dumpDM(camelModel, dumpDMLevel);
				return false;
			}

			EList<EObject> contentsPC = cdoView.getResource(paasageConfigurationID).getContents();
			PaasageConfiguration paasageConfiguration = (PaasageConfiguration) contentsPC.get(0);
			ConstraintProblem constraintProblem = (ConstraintProblem) contentsPC.get(1);

			// Checking if there is a solution
			if (constraintProblem.getSolution().size()==0)
			{
				log.info("No solution available in Constraint Problem!");
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
				int newDmId = CDODatabaseProxy2.copyDeploymentModel(camelModelID, 0, overwriteDM, dstDMId);
				newDm = (DeploymentModel) camelModel.getDeploymentModels().get(newDmId);
				
				// Generate new instances into this new DM of camel
				DataHolder dataholder  = DataUtils.computeDatasToRegister(paasageConfiguration, newDm, constraintProblem, solutionId);
				if (dataholder==null) return false;

				dataholder.setDM(newDm);
				dataholder.setDmId(camelModel.getDeploymentModels().size()-1);
				DataUtils.registerDataHolderToCDO(camelModelID, dataholder); // COPY TO CDO

			} catch (S2DException | CommitException e) {
				e.printStackTrace();
				log.error("Unable to complete data model instances registration");
				return false;
			}
			dumpDM(camelModel, 2);
		} catch (RuntimeException exception) {
			exception.printStackTrace();
			return false;
		}
		return true;
	}

	private static void usage()
	{
		System.out.println("[-o dstDMid (or -1 for last one)] [-t SolutionTimeStamp] [-d level] ConfigurationCDOId CamelCDOId CloudProviderCDODirID");
		System.out.println("[-daemon] [-daemonold]");
	}
	
	enum S2D_ARGS_CMD { DEFAULT, OVERVRITE_DM, TIMESTAMP, DUMPDM };
	public static void main(String[] args) {

		if (args.length == 0) {
			usage();
			System.exit(-1);
		}
		if ((args.length == 1)&&(args[0].equals("-daemonold")))
		{
			S2D_ZMQ_Service.getInstance().run();
			System.exit(0);
		}
		if ((args.length == 1)&&(args[0].equals("-daemon")))
		{
			S2D_ZeroMQServer.getInstance().run();
			System.exit(0);
		}
		
		S2D_ARGS_CMD next_op=S2D_ARGS_CMD.DEFAULT;	
		int dmID=-1;
		boolean overwriteDM = false;
		long solutionTS=-1;
		boolean TSavailable=false;
		String param[] = new String[3];
		int param_idx=0;
		int dumpDMLevel = 0;
		for(int i=0; i<args.length; i++) 
		{
			String a = args[i];
			log.info("arg: "+a);
			switch (next_op) {
			case OVERVRITE_DM:	dmID = Integer.valueOf(a); overwriteDM = true; next_op = S2D_ARGS_CMD.DEFAULT; continue;
			case TIMESTAMP:   	solutionTS = Long.valueOf(a); TSavailable = true; next_op = S2D_ARGS_CMD.DEFAULT; continue;
			case DUMPDM:   		dumpDMLevel = Integer.valueOf(a); next_op = S2D_ARGS_CMD.DEFAULT; continue;
			default:
				if (a.substring(0, 2).equals("-o")) {
					next_op = S2D_ARGS_CMD.OVERVRITE_DM;
					log.info("Next op: "+next_op);
				}
				else if (a.equals("-t")) next_op = S2D_ARGS_CMD.TIMESTAMP;
				else if (a.equals("-d")) next_op = S2D_ARGS_CMD.DUMPDM;
				else param[param_idx++] = a;
			}
		}
		
		if (param_idx!=3)
		{
			usage();
			System.exit(-1);
		}

		// RETRIEVING VALUES
		String paasageConfigurationID = param[0];
		String camelModelID = param[1];
		String CPDirId = param[2];

		boolean res;
		try
		{
			res = doWorkTS(paasageConfigurationID, camelModelID, CPDirId, solutionTS, TSavailable, dmID, overwriteDM, dumpDMLevel);
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
