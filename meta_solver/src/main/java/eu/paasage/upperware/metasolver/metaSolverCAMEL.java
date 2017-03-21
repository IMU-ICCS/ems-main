
/*
 * Copyright (c) 2014-5 UK Science and Technology Facilities Council
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metasolver;


	import org.eclipse.emf.cdo.net4j.CDONet4jSession;
	import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
	import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
	import org.eclipse.emf.cdo.transaction.CDOTransaction;
	import org.eclipse.emf.cdo.view.CDOQuery;
	import org.eclipse.emf.cdo.view.CDOView;
	import org.eclipse.net4j.FactoriesProtocolProvider;
	import org.eclipse.net4j.Net4jUtil;
	import org.eclipse.net4j.buffer.IBufferProvider;
	import org.eclipse.net4j.protocol.IProtocolProvider;
	import org.eclipse.net4j.util.lifecycle.LifecycleUtil;
	import org.eclipse.net4j.util.om.OMPlatform;
	import org.eclipse.net4j.util.om.log.PrintLogHandler;
	import org.eclipse.net4j.util.om.trace.PrintTraceHandler;
	import org.eclipse.net4j.util.security.IPasswordCredentialsProvider;
	import org.eclipse.net4j.util.security.PasswordCredentialsProvider;
	import org.eclipse.emf.ecore.EObject;
	import org.eclipse.emf.ecore.EPackage;

	import eu.paasage.camel.CamelPackage;
	import eu.paasage.camel.deployment.DeploymentPackage;
	import eu.paasage.camel.execution.ExecutionPackage;
	import eu.paasage.camel.location.LocationPackage;
	import eu.paasage.camel.metric.MetricPackage;
	import eu.paasage.camel.organisation.OrganisationPackage;
	import eu.paasage.camel.provider.ProviderPackage;
	import eu.paasage.camel.scalability.ScalabilityPackage;
	import eu.paasage.camel.security.SecurityPackage;
	import eu.paasage.camel.requirement.RequirementPackage;
	import eu.paasage.camel.type.TypePackage;
	import eu.paasage.camel.unit.UnitPackage;
   // import eu.paasage.mddb.cdo.client.MyCDOTransactionHandler;

	
	
	import java.util.List;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
	import java.util.concurrent.ThreadFactory;

public class metaSolverCAMEL{
	
	
	
	

	/**
	 * @author Eike Stepper
	 */
	//A TCP Connector to the CDOServer
		private org.eclipse.net4j.internal.tcp.TCPClientConnector connector;
		//private org.eclipse.net4j.internal.jvm.JVMClientConnector connector;
		//The CDOSession that is created by the CDOClient which will be used to create CDO transactions or views
		private CDONet4jSession session;
		//Parameters representing the required connection information in order to connect to the CDOServer
		private String host = "pipeline-1.esc.rl.ac.uk";
		private String port = "2036";
		private String repositoryName = "tomcdo";
		private boolean logging = false;
		private String userName = "root";
		private String password = "paasage";

		/*Default constructor for the client which initiates a CDO session*/
		public metaSolverCAMEL(){
			initSession();
		}

		/*Constructor for the client which initiates a CDO session with the authentication 
		 * information provided*/
		public metaSolverCAMEL(String userName,String password){
			this.userName = userName;
			this.password = password;
			initSession();
		}



		/* This method is called in order to get the connection information
		 * that will be used in order to be able to connect correctly to the
		 * CDO Server and create the respective CDOSession
		 */
		private void getConnectionInformation(){
		}

		/*This method is used for initiating a CDO Session starting by obtaining
		connection information from a property file*/
		private void initSession(){
			getConnectionInformation();

			OMPlatform.INSTANCE.setDebugging(logging);
			OMPlatform.INSTANCE.addLogHandler(PrintLogHandler.CONSOLE);
			OMPlatform.INSTANCE.addTraceHandler(PrintTraceHandler.CONSOLE);

			// Prepare receiveExecutor
			final ThreadGroup threadGroup = new ThreadGroup("net4j"); //$NON-NLS-1$
			ExecutorService receiveExecutor = Executors.newCachedThreadPool(new ThreadFactory()
			{
				public Thread newThread(Runnable r)
				{
					Thread thread = new Thread(threadGroup, r);
					thread.setDaemon(true);
					return thread;
				}
			});


			// Prepare bufferProvider
			IBufferProvider bufferProvider = Net4jUtil.createBufferPool();
			LifecycleUtil.activate(bufferProvider);

			IProtocolProvider protocolProvider = new FactoriesProtocolProvider(
					new org.eclipse.emf.cdo.internal.net4j.protocol.CDOClientProtocolFactory());

			// Prepare selector
			org.eclipse.net4j.internal.tcp.TCPSelector selector = new org.eclipse.net4j.internal.tcp.TCPSelector();
			//org.eclipse.net4j.internal.jvm.JVMSelector selector = new org.eclipse.net4j.internal.jvm.JVMSelector();
			selector.activate();

			// Prepare connector
			connector = new org.eclipse.net4j.internal.tcp.TCPClientConnector();
			connector.getConfig().setBufferProvider(bufferProvider);
			connector.getConfig().setReceiveExecutor(receiveExecutor);
			connector.getConfig().setProtocolProvider(protocolProvider);
			connector.getConfig().setNegotiator(null);
			connector.setSelector(selector);
			connector.setHost(host); //$NON-NLS-1$
			connector.setPort(Integer.parseInt(port.trim()));
			connector.activate();

			//ITCPConnector connector = TCPUtil.getConnector(container, "0.0.0.0:2036");
			//IConnector connector = SSLUtil.getConnector(container, "0.0.0.0:2036");
			//JVMUtil.getAcceptor(container, "default");
			//connector.activate();

			// Create configuration
			CDONet4jSessionConfiguration configuration = CDONet4jUtil.createNet4jSessionConfiguration();
			configuration.setConnector(connector);
			configuration.setRepositoryName(repositoryName); //$NON-NLS-1$

			//Provide security information, if supplied by user
			//authentication, if succeeds last for the whole session - lifetime of CDOClient object
			if (userName != null && password != null){
				IPasswordCredentialsProvider credentialsProvider = new PasswordCredentialsProvider(userName, password);
				configuration.setCredentialsProvider(credentialsProvider);
			}

			// Open session
			session = configuration.openNet4jSession();
			registerCamelPackages();
		}

		/* This method is used to register all Packages of Camel meta-model
		 */
		public void registerCamelPackages(){
			registerPackage(CamelPackage.eINSTANCE);
			registerPackage(ScalabilityPackage.eINSTANCE);
			registerPackage(DeploymentPackage.eINSTANCE);
			registerPackage(OrganisationPackage.eINSTANCE);
			registerPackage(ProviderPackage.eINSTANCE);
			registerPackage(SecurityPackage.eINSTANCE);
			registerPackage(ExecutionPackage.eINSTANCE);
			registerPackage(TypePackage.eINSTANCE);
			registerPackage(RequirementPackage.eINSTANCE);
			registerPackage(MetricPackage.eINSTANCE);
			registerPackage(UnitPackage.eINSTANCE);
			registerPackage(LocationPackage.eINSTANCE);
		}

		/* This method is used for registering an EPackage mapping to the domain
		 * model that will be exploited for creating or manipulating the respective
		 * domain objects. Input parameter: the EPackage to register
		 */
		public void registerPackage(EPackage pack){
			session.getPackageRegistry().putEPackage(pack);
		}

		/* This method can be used to open a CDO transaction and return it to
		 * the developer/user. The developer/user should not forget to close 
		 * the respective cdo transaction in the end.
		 */
		public CDOTransaction openTransaction(){
			CDOTransaction trans = session.openTransaction();

			return trans;
		}

		/* This method can be used to open a CDO transaction and return it to
		 * the developer/user. If the user/developer desires a validation of the
		 * models/objects updated, then he/she must set the respective boolean input 
		 * parameter with the value of true. The user should not forget to close 
		 * the respective cdo transaction in the end.
		 */

		
		/*tk
		public CDOTransaction openTransaction(boolean validate){
			CDOTransaction trans = session.openTransaction();
			System.out.println("Opened transaction!");
			if (validate) trans.addTransactionHandler(new MyCDOTransactionHandler());
			return trans;
		}

		*/
		
		
		
		/* This method can be used to open a CDO view and return it to
		 * the developer/user. The developer/user should not forget to close 
		 * the respective cdo view in the end.
		 */
		public CDOView openView(){
			CDOView view = session.openView();
			System.out.println("Opened view!");
			return view;
		}


		/* This method is used for closing a CDO transaction. 
		 */
		public void closeTransaction(CDOTransaction trans){
			trans.close();
		}

		/* This method is used for closing a CDO view. 
		 */
		public void closeView(CDOView view){
			view.close();
		}

		/* This method is used to run a query over the contents stored in the 
		 * CDO Store. You do not have to create a view before running the query
		 * as the view is created before the query transparently by this method 
		 * and closed when the query is finished. The user has the optional
		 * choice to store the first result of the query in a XMI file whose name
		 * is given by him/her. The input parameters for this method are: (a) the 
		 * query dialect (OCL, SQL, HQL), (b) the query String itself and (c) the
		 * name of the XMI file in which the first query result will be stored - it
		 * can be null if the user does not want to export the result.    
		 */
		public String[] runNameQuery(String dialect, String queryStr, String fileName){
			List<EObject> results = null;
			String[] output = null;
			if (fileName == null){
				CDOView view = openView();
				CDOQuery query = null;
				query = view.createQuery(dialect, queryStr);
				results = query.getResult(EObject.class);
				output = new String[results.size()];
				int i = 0;
				while (i < results.size()){
					EObject eob = results.get(i);			  
					Object VALname = eob.eGet(eob.eClass().getEStructuralFeature(0));
					String FEval = VALname.toString();
					output[i]=FEval;

					i++;
				}





				view.close();
			}
			else{
				CDOTransaction trans = openTransaction();
				CDOQuery query = null;
				query = trans.createQuery(dialect, queryStr);
				results = query.getResult(EObject.class);
				//  		  exportModel(results.get(0),fileName);
				trans.close();
			}
			return output;
		}


		public String[] runEidQuery(String dialect, String queryStr, String fileName){
			List<EObject> results = null;
			String[] output = null;
			if (fileName == null){
				CDOView view = openView();
				CDOQuery query = null;
				query = view.createQuery(dialect, queryStr);
				results = query.getResult(EObject.class);
				output = new String[results.size()];
				int i = 0;

				while (i < results.size()){
					EObject eob = results.get(i);			  
					String SFname = eob.eClass().getName();
					Object VALname = eob.eGet(eob.eClass().getEStructuralFeature(0));
					String FEval = VALname.toString();
					output[i]=FEval;
					System.out.println(" NUMBER " + i + "  GO .... NAME = " + SFname + " VAL = " + FEval );

					i++;
				}

				view.close();
			}
			else{
				CDOTransaction trans = openTransaction();
				CDOQuery query = null;
				query = trans.createQuery(dialect, queryStr);
				results = query.getResult(EObject.class);
				//  		  exportModel(results.get(0),fileName);
				trans.close();
			}
			return output;
		}

		/* This method is used to close the CDOSession that was opened when creating
		 * an object of this class - CDOClient. 
		 */
		public void closeSession(){
			session.close();
			connector.deactivate();
		}



		/* Main method which demonstrates the functionality of the CDOClient through
		 * the usage of the various methods offered by it, when no arguments are given 
		 * to it. Otherwise, it takes as input the name of the method plus any additional
		 * parameter required by it and executes it. The latter exploitation is useful for
		 * a command line usage of the CDOClient API. */
		public boolean checkSecurityLevel(String organisation){
			//Create the CDOClient
			metaSolverCAMEL cl = new metaSolverCAMEL("root","paasage");
			System.out.println("***************************************************************"); 

			int h = 0;
			System.out.println(" now query 2 ");
			String[] results4 = cl.runEidQuery("hql","from OrganisationModel where securitylevel = 'HIGH'", null);  

			while (h < results4.length){

				if (results4[h].toString().equals(organisation)) {

					return true;
				}

				else {
					System.out.println("no match with " + results4[h]);
				}

				h++;
			}

			cl.closeSession();
			return false;

			//		System.exit(1);

		}
	}
	
	
	
