/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.db.lib;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.upperware.solvertodeployment.lib.S2DException;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CDODatabaseProxy2 {
	
	//////////////////////////////////////////////////////////////////////////////////////
	// Class member variables
	//////////////////////////////////////////////////////////////////////////////////////

	private static CDODatabaseProxy2 cdoDatabaseProxy2 = new CDODatabaseProxy2();

	private static String FMS_APP_CDO_SERVER_PATH = "upperware-models/fms/";


	//////////////////////////////////////////////////////////////////////////////////////
	// Helper class for transactions
	//////////////////////////////////////////////////////////////////////////////////////

	class CamelAndDeploymentModelTransactionManager {

		DeploymentModel deploymentModel;
		CamelModel camelModel;
		CDOTransaction transaction;
		int _dmId;

		public CamelAndDeploymentModelTransactionManager(String camelModelID, int dmId) {
			transaction = CDODatabaseProxy.getInstance().getCdoClient().openTransaction();
			camelModel = (CamelModel) transaction.getResource(camelModelID).getContents().get(0);
			deploymentModel = camelModel.getDeploymentModels().get(dmId);
			_dmId = dmId;
		}

		public void commitAndClose() {
			camelModel.getDeploymentModels().set(_dmId, deploymentModel);
			try {
				transaction.commit();
			} catch (CommitException e) {
				e.printStackTrace();
			} finally {
				if (transaction != null && !transaction.isClosed()) {
					transaction.close();
				}
			}
		}
	}

	class CamelAndProviderModelTransactionManager {

		CamelModel newCloudCamelModel;
		CamelModel camelModel;
		CDOTransaction transaction;

		public CamelAndProviderModelTransactionManager(String camelModelID) {
			transaction = CDODatabaseProxy.getInstance().getCdoClient().openTransaction();
			camelModel = (CamelModel) transaction.getResource(camelModelID).getContents().get(0);
			newCloudCamelModel=null;
		}

		public void commitAndClose() throws CommitException {
			// TYPE
			// val type.TypeModel[*] typeModels;
			camelModel.getTypeModels().addAll(newCloudCamelModel.getTypeModels());

			// LOCATIONS
			// val location.LocationModel[*] locationModels;
			camelModel.getLocationModels().addAll(newCloudCamelModel.getLocationModels());

			// ORGANIZATION
			// val organisation.OrganisationModel[*] organisationModels;
			camelModel.getOrganisationModels().addAll(newCloudCamelModel.getOrganisationModels());

			// PROVIDER -- ALWAYS
			// val provider.ProviderModel[*] providerModels;
			camelModel.getProviderModels().add(newCloudCamelModel.getProviderModels().get(0));

			try {
				transaction.commit();
			} catch (ConcurrentAccessException e) {
				log.error("ConcurentAccessException when commiting for copy cloud provider", e);
				throw e;
			} catch (CommitException e) {
				log.error("CommitException when commiting for copy cloud provider", e);
				throw e;
			} finally {
				if (transaction != null && !transaction.isClosed()) {
					transaction.close();
				}
			}
		}
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Copy Cloud Providers into CAMEL CDO + Helper function
	//////////////////////////////////////////////////////////////////////////////////////
	
	public static void copyAllCloudProviderModel(String providerId, String cloudVMId, CamelModel cm, String camelModelID) throws CommitException {
		
		CamelAndProviderModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndProviderModelTransactionManager(camelModelID);

		// CREATING A COPY
		CamelModel cmCopy = EcoreUtil.copy(cm);

		// FIXING ITS NAME TO BE UNIQUE
		cmCopy.getProviderModels().get(0).setName(providerId+"#"+cloudVMId);
		
		transactionManager.newCloudCamelModel = cmCopy;
		transactionManager.commitAndClose(); // COPY TO CDO!
	}

	public static void copyAllCloudProviderModel(String pmId, String cloudVMid, String camelModelID, String appId) throws S2DException, CommitException {
		try {
			log.info("appId={} pmId={} cloudVMd={}", appId, pmId, cloudVMid);
			List<CamelModel> cms = findAllCamelProviderModel(appId, pmId);
			log.info("#Camel CloudProvider Model(s) loaded: {}", cms.size());
			for(CamelModel cm : cms) {
//				if (cloudVMid.equals(cm.getName())) {
				log.info("Copying {}", cm.getName());
				copyAllCloudProviderModel(pmId, cloudVMid, cm, camelModelID);
//				}
			}
			log.info("Done");
		} catch (S2DException e) {
			e.printStackTrace();
			log.error("Error when copying PM into CAMEL", e);
			throw e;
		}
	}
	
	public static int copyDeploymentModel(String camelModelID, int srcId, boolean overwriteDM, int dstId) throws CommitException {
		CDOTransaction transaction = CDODatabaseProxy.getInstance().getCdoClient().openTransaction();
		CamelModel camelModel = (CamelModel) transaction.getResource(camelModelID).getContents().get(0);
		EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();
		DeploymentModel dm = deploymentModels.get(srcId);
		DeploymentModel dmCopy = EcoreUtil.copy(dm);
		
		int dmId;
		if (overwriteDM) {
			// Checking that dmId exists
			try {
				if (dstId==-1) dstId = deploymentModels.size()-1; // LAST IF -1
				log.info("Trying to overwrite DM entry {}", dstId);
				deploymentModels.set(dstId, dmCopy);
			} catch (IndexOutOfBoundsException e) {
				log.error("DemploymentModel dst id (overwriten) is not corect! #DM: {}", deploymentModels.size());
			}
			dmId = dstId;
		} else {
			deploymentModels.add(dmCopy);
			dmId = deploymentModels.size()-1;
		}

		try {
			transaction.commit();
		} catch (CommitException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (transaction != null && !transaction.isClosed()) {
				transaction.close();
			}
		}
		return dmId;
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Registering stuff into CDO
	//////////////////////////////////////////////////////////////////////////////////////

	public static void registerInternalComponentInstance(InternalComponentInstance internalComponentInstance, String camelModelID, int dmId) {
		CamelAndDeploymentModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeploymentModelTransactionManager(camelModelID, dmId);
		transactionManager.deploymentModel.getInternalComponentInstances().add(internalComponentInstance);
		transactionManager.commitAndClose();
	}

	public static void registerVMInstance(VMInstance vmInstance, String camelModelID, int dmId) {
		CamelAndDeploymentModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeploymentModelTransactionManager(camelModelID,dmId);
		transactionManager.deploymentModel.getVmInstances().add(vmInstance);
		transactionManager.commitAndClose();
	}

	public static void registerHostingInstance(HostingInstance hostingInstance, String camelModelID, int dmId) {
		CamelAndDeploymentModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeploymentModelTransactionManager(camelModelID, dmId);
		transactionManager.deploymentModel.getHostingInstances().add(hostingInstance);
		transactionManager.commitAndClose();
	}

	public static void registerCommunicationInstance(CommunicationInstance communicationInstance, String camelModelID, int dmId) {
		CamelAndDeploymentModelTransactionManager transactionManager = cdoDatabaseProxy2.new CamelAndDeploymentModelTransactionManager(camelModelID, dmId);
		transactionManager.deploymentModel.getCommunicationInstances().add(communicationInstance);
		transactionManager.commitAndClose();
	}

	//////////////////////////////////////////////////////////////////////////////////////
	// Helper function
	//////////////////////////////////////////////////////////////////////////////////////

	private static String getFMResourceId(String appId, String providerId) {
		return appId+"/"+providerId; 
	}

	static private List<CamelModel> findAllCamelProviderModel(String appId, String providerId) throws S2DException {
		String componentURI = FMS_APP_CDO_SERVER_PATH+getFMResourceId(appId, providerId);

		try{
			CDODatabaseProxy cdoProxy = CDODatabaseProxy.getInstance();

			log.info("loading PM model: "+componentURI);
			CDOView view = cdoProxy.getCdoClient().openView();
			EList<EObject> res= view.getResource(componentURI).getContents();

			return res.stream().map(eObject -> (CamelModel) eObject).collect(Collectors.toList());

		} catch(Exception e) {
			throw new S2DException("Unable to find provider model with name " + providerId + " . Is there something wrong in your original model ? The uri looking was : " + componentURI + 
					". Message : "+ e.getMessage());
		}
	}

}
