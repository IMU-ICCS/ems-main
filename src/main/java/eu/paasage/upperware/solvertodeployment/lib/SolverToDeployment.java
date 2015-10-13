/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */



package eu.paasage.upperware.solvertodeployment.lib;


import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
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

			EList<EObject> contents = cdoView.getResource(paasageConfigurationID).getContents();
			PaasageConfiguration paasageConfiguration = (PaasageConfiguration) contents.get(0);

			EList<EObject> contents2 = cdoView.getResource(camelModelID).getContents();
			CamelModel camelModel= (CamelModel)contents2.get(0);
			DeploymentModel deploymentModel = camelModel.getDeploymentModels().get(0);

			try {
				DataHolder dataholder  = DataUtils.computeDatasToRegister(paasageConfiguration, deploymentModel);
				DataUtils.registerDataHolderToCDO(camelModelID, dataholder);

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

	private static String zmqServerddress = "tcp://127.0.0.1:5546";
	private static void ZMQServerStart()
	{
	     
	
		log.info("lets go for a subscription .....");
	
		log.info("setting context ....");
	
	              
	
	        Context cntx = ZMQ.context(1);
	
	        log.info("context set .....");
	
	       
	
	        Socket subscriber = cntx.socket (ZMQ.SUB);
	
	        log.info("socket set .....");
	        
	        subscriber.connect (zmqServerddress);
	
	        log.info("connection set .....");
	
	        subscriber.subscribe("B3".getBytes());//listening to meessages on ...
	
	        log.info("subscription done .....");
	
	        while (!Thread.currentThread ().isInterrupted ()) {
	
	        		log.info("listening on " + zmqServerddress);
	
			      // Read envelope with address
			
			      String address = subscriber.recvStr();
			
			      // Read message contents
			
			      String contents = subscriber.recvStr();
			      System.out.println(address + " : " + contents);

			      String paasageConfigurationID = contents.split(" ")[0];
			      String camelModelID = contents.split(" ")[1];
					
			
			      try {
						main(paasageConfigurationID, camelModelID);
					} catch (S2DException e) {

						e.printStackTrace();
						log.info("Solver to deployment done with errors");
					}
	     }
	}


	private static void printUsage()
	{
		System.out.println("Bad usage : args1=paasageconfiguration, arg2=camelModel args3=deamon(optionnal)");
		System.out.println("Or : args1=deamon, running in ZMQ mode");

	}
	//FIXME : doing something  with named args

	public static void main(String[] args) {
		if(args.length == 1)
		{
			ZMQServerStart();
		}
		else if (args.length == 2) {
			String paasageConfigurationID = args[0];
			String camelModelID = args[1];
			
			paasageConfigurationID = args[0];
			camelModelID = args[1];
			try {
				main(paasageConfigurationID, camelModelID);
			} catch (S2DException e) {
				log.error("Solver to deployment done with errors :");
				e.printStackTrace();
				System.exit(1);
			}
			log.info("Solver to deployment done");
			System.exit(0);

			
		} 
		else{
			printUsage();
		}
		

	}
}
