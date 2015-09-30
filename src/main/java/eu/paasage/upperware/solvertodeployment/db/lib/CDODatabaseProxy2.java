/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */



package eu.paasage.upperware.solvertodeployment.db.lib;

import java.util.List;

import org.apache.log4j.Logger;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunication;
import eu.paasage.camel.deployment.ProvidedCommunicationInstance;
import eu.paasage.camel.deployment.RequiredCommunication;
import eu.paasage.camel.deployment.RequiredCommunicationInstance;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;

public class CDODatabaseProxy2 {
	
	class CamelAndDeployementModelTransactionManager {

		DeploymentModel deploymentModel;
		CamelModel camelModel;
		CDOTransaction transaction;

		public CamelAndDeployementModelTransactionManager(String camelModelID) {
			transaction = CDODatabaseProxy.getInstance().getCdoClient()
					.openTransaction();
			camelModel = (CamelModel) transaction.getResource(camelModelID)
					.getContents().get(0);
			deploymentModel = camelModel.getDeploymentModels().get(0);
		}

		public void commitAndClose() {

			camelModel.getDeploymentModels().set(0, deploymentModel);

			try {
				transaction.commit();
			} catch (ConcurrentAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (CommitException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {

				if (transaction != null && !transaction.isClosed()) {
					transaction.close();
				}
			}
		}
	}

	private static Logger log = Logger.getLogger(CDODatabaseProxy2.class);

	public static CamelModel findCamelModel(CDODatabaseProxy cdoProxy,
			String cloudMLId) {
		cdoProxy = CDODatabaseProxy.getInstance();

		CDOView view = cdoProxy.getCdoClient().openView();

		CDOResource resource = view.getResource(cloudMLId);
		EList<EObject> content = resource.getContents();
		return (CamelModel) content.get(0);

	}

	public static DeploymentModel findDeployementModel(
			CDODatabaseProxy cdoProxy, String cloudMLId) {
		return findCamelModel(cdoProxy, cloudMLId).getDeploymentModels().get(0);
	}

	public static PaasageConfiguration findPC(CDODatabaseProxy cdoProxy,
			String pcID) {
		cdoProxy = CDODatabaseProxy.getInstance();

		CDOView view = cdoProxy.getCdoClient().openView();

		CDOResource resource = view.getResource(pcID);
		EList<EObject> content = resource.getContents();

		return (PaasageConfiguration) content.get(0);

	}
	public static ConstraintProblem findConstraintsProblem(CDODatabaseProxy cdoProxy,
			String pcID) {
		cdoProxy = CDODatabaseProxy.getInstance();

		CDOView view = cdoProxy.getCdoClient().openView();

		CDOResource resource = view.getResource(pcID);
		EList<EObject> content = resource.getContents();

		return (ConstraintProblem) content.get(1);

	}
	public static ApplicationComponent findAppComponentInConfiguration(
			PaasageConfiguration pc, String applicationId) {

		for (ApplicationComponent apc : pc.getComponents()) {
			log.debug("Looking component " + apc.getCloudMLId()
					+ " in paasaconfiguration");
			if (apc.getCloudMLId().equals(applicationId)) {
				log.debug("OK finding component " + apc.getCloudMLId()
						+ " in paasaconfiguration");

				return apc;
			}
		}
		log.error("Unable to find application component !! Looking for: "
				+ applicationId);
		return null;
	}

	public static VirtualMachineProfile findVirtualMachineProfileInConfiguration(
			PaasageConfiguration pc, String applicationId) throws S2DException {

		for (VirtualMachineProfile vmp : pc.getVmProfiles()) {
			log.debug("Looking component " + vmp.getCloudMLId()
					+ " in paasaconfiguration. Compare with "+ applicationId);
			if (applicationId.startsWith(vmp.getCloudMLId())) {
				log.debug("OK finding vm profile " + vmp.getCloudMLId()
						+ " in paasaconfiguration");
				return vmp;
			}
		}
		throw new S2DException("Unable to find vm profile !! Looking for: " + applicationId);


	}

	private static CDODatabaseProxy2 cdoDatabaseProxy2 = new CDODatabaseProxy2();

	public static void registerInternalComponentInstance(
			InternalComponentInstance internalComponentInstance,
			String camelModelID) {

		CamelAndDeployementModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeployementModelTransactionManager(
				camelModelID);
		transactionManager.deploymentModel.getInternalComponentInstances().add(
				internalComponentInstance);
		transactionManager.commitAndClose();

	}

	public static void registerVMInstance(VMInstance vmInstance,
			String camelModelID) {

		CamelAndDeployementModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeployementModelTransactionManager(
				camelModelID);
		transactionManager.deploymentModel.getVmInstances().add(vmInstance);
		transactionManager.commitAndClose();

	}

	public static void registerHostingInstance(HostingInstance hostingInstance,
			String camelModelID) {

		CamelAndDeployementModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeployementModelTransactionManager(
				camelModelID);
		transactionManager.deploymentModel.getHostingInstances().add(
				hostingInstance);
		transactionManager.commitAndClose();
	}

	public static void registerCommunicationInstance(
			CommunicationInstance communicationInstance, String camelModelID) {
		CamelAndDeployementModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeployementModelTransactionManager(
				camelModelID);
		transactionManager.deploymentModel.getCommunicationInstances().add(
				communicationInstance);
		transactionManager.commitAndClose();

	}

	/*
	 * public static VMInfo getFirstVMInfo(CDODatabaseProxy cdoProxy,String
	 * cloudMLId) { CamelModel cm = findCamelModel(cdoProxy,cloudMLId); return
	 * cm.getVmInfos().get(0);
	 * 
	 * }
	 */

	public static Hosting getHostingContainString(DeploymentModel dm,
			String acName) {

		

		List<Hosting> hostings = dm.getHostings();
		for (Hosting hosting : hostings) {
			log.debug("Looking hosting with name " + hosting.getName());
			if (hosting.getName().contains(acName)) {
				return hosting;
			}

		}
		log.error("Unable to hosting with name containing string " + acName);
		return null;
	}


	public static RequiredCommunicationInstance findRequiredCommunicationInstance(DeploymentModel deploymentModel, 
			Communication communication)	throws S2DException {
		RequiredCommunicationInstance requiredCommunicationInstanceResult = null;
		RequiredCommunication requiredCommunicationExpected = communication.getRequiredCommunication();

		for(InternalComponentInstance internalComponentInstance : deploymentModel.getInternalComponentInstances())
		{
			for(RequiredCommunicationInstance  requiredCommunicationInstance : internalComponentInstance.getRequiredCommunicationInstances())
			{
			
				if( requiredCommunicationInstance.getType().getName().equals(requiredCommunicationExpected.getName()))
				{
					requiredCommunicationInstanceResult =  requiredCommunicationInstance;
				}
			}
		}
		if (requiredCommunicationInstanceResult == null) {
			log.error("Unable to find required communication instance for "
					+ communication.getName());
		}
		return requiredCommunicationInstanceResult;
	}

	public static ProvidedCommunicationInstance findProvidedCommunicationInstance(
			DeploymentModel deploymentModel, Communication communication)
					throws S2DException {
		
			ProvidedCommunicationInstance providedCommunicationInstanceResult = null;
		ProvidedCommunication providedCommunicationExpected = communication.getProvidedCommunication();

		for(InternalComponentInstance internalComponentInstance : deploymentModel.getInternalComponentInstances())
		{
			for(ProvidedCommunicationInstance  providedCommunicationInstance : internalComponentInstance.getProvidedCommunicationInstances())
			{
			
				if( providedCommunicationInstance.getType().getName().equals(providedCommunicationExpected.getName()))
				{
					providedCommunicationInstanceResult =  providedCommunicationInstance;
				}
			}
		}
		if (providedCommunicationInstanceResult == null) {
			log.error("Unable to find provided communication instance for"
					+ communication.getName());
		}
		return providedCommunicationInstanceResult;
		
		
	}
	
	
	static 		String FMS_APP_CDO_SERVER_PATH = "upperware-models/fms/";
	
	
	public static String getFMResourceId(String appId, String providerId)
	{
		return appId+"/"+providerId; 
	}
	
	

	static public ProviderModel findProviderModel(String appId, String providerId) throws S2DException 
	{
		String componentURI = FMS_APP_CDO_SERVER_PATH+getFMResourceId(appId, providerId);

		try{
		CDODatabaseProxy cdoProxy = CDODatabaseProxy.getInstance();

		CDOView view = cdoProxy.getCdoClient().openView();
		EList<EObject> res= view.getResource(componentURI).getContents(); 
		
		CamelModel pm = (CamelModel) res.get(0); 
		
		System.err.println("CDODatabaseProxy- loadPM- PM "+pm.getProviderModels().get(0).getRootFeature().getName());
		
		return pm.getProviderModels().get(0); 
		}catch(Exception e)
		{
			throw new S2DException("Unable to find provider model with name " + providerId + " . Is there something wrong in your original model ? The uri looking was : " + componentURI + 
					". Message : "+ e.getMessage());
		}
}  

}
