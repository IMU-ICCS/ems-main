/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.lib;

import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.solverToDeployment.ApplySolutionNotificationRequest;
import eu.melodic.models.services.solverToDeployment.ApplySolutionNotificationRequestImpl;
import eu.paasage.camel.deployment.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;
import eu.paasage.upperware.solvertodeployment.utils.DataHolder;
import eu.paasage.upperware.solvertodeployment.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.stream.Collectors;

import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class SolverToDeployment {

	private RestTemplate restTemplate;
	private Environment env;
		
	@Async
	public void doWorkTS(String camelModelID, String paasageConfigurationID, String notificationUri,  String requestUuid)
					throws S2DException {
		log.info("Application ID: "+camelModelID);
		log.info("CDO models path: "+paasageConfigurationID);
		log.info("Notification URI: "+notificationUri);
		log.info("UID: "+requestUuid);

		try {
			CDODatabaseProxy cdoProxy = CDODatabaseProxy.getInstance();
			CDOView cdoView = cdoProxy.getCdoClient().openView();

			EList<EObject> contentsCM = cdoView.getResource(camelModelID).getContents();
			CamelModel camelModel= (CamelModel)contentsCM.get(0);
			
			EList<EObject> contentsPC = cdoView.getResource(paasageConfigurationID).getContents();
			PaasageConfiguration paasageConfiguration = (PaasageConfiguration) contentsPC.get(0);
			ConstraintProblem constraintProblem = (ConstraintProblem) contentsPC.get(1);

			// Checking if there is a solution
			if (constraintProblem.getSolution().size()==0) {
				log.info("No solution available in Constraint Problem!");
				notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
				return;
			}
			
			// Computing solutionId from solutionTS
			int solutionId=-1;
			long maxTS= -1L;

			for(int i =0; i<constraintProblem.getSolution().size(); i++) {
				Solution sol = constraintProblem.getSolution().get(i);
				long timestamp = sol.getTimestamp();
				if (timestamp == 0) {
					solutionId = i;
					break;
				} else if (timestamp > maxTS) {
					maxTS = timestamp;
					solutionId=i;
				}
			}

			log.info("Using the solution with highest TS: "+maxTS);
			log.info("Using entry: "+solutionId);
			

			// Do Work
			try {
				// copy provider to source camel doc
				String results[] = paasageConfigurationID.split("/");
				String fmsId = results[1];
				DataUtils.copyCloudProviders(camelModel, camelModelID, fmsId, paasageConfiguration, constraintProblem, solutionId);

				// Create a new DM to store the instances from solution
				int newDmId = CDODatabaseProxy2.copyDeploymentModel(camelModelID, 0, false, 0);
				DeploymentModel newDm = camelModel.getDeploymentModels().get(newDmId);

				CloudMLHelper.setGlobalDMIdx(newDmId);
				CloudMLHelper.resetGlobalCount();

				// Generate new instances into this new DM of camel
				DataHolder dataholder  = DataUtils.computeDatasToRegister(paasageConfiguration, newDm, constraintProblem, solutionId, camelModelID);
				if (dataholder==null) {
					notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
					return;
				}

				dataholder.setDmId(camelModel.getDeploymentModels().size()-1);
				DataUtils.registerDataHolderToCDO(camelModelID, dataholder); // COPY TO CDO

			} catch (S2DException | CommitException e) {
				log.error("Unable to complete data model instances registration", e);
				notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
				return;
			}
			dumpDM(camelModel, 2);
		} catch (RuntimeException exception) {
			log.error("RuntimeException", exception);
			notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
			return;
		}
		notifySolutionApplied(camelModelID, notificationUri, requestUuid);
	}

	public static void dumpDM(CamelModel cm, int level) {
		log.info("Camel doc contains " + cm.getDeploymentModels().size() + " Deployment Model");
		if (level > 1)
			for(int i=0; i<cm.getDeploymentModels().size(); i++) {
				DeploymentModel dm = cm.getDeploymentModels().get(i);
				log.info("  DM"+i+" :" +
								" InternalComponentInstances: " + dm.getInternalComponentInstances().size()+
								" VMInstances: "+ dm.getVmInstances().size() +
								" HostingInstances: "+ dm.getHostingInstances().size() +
								" CommInstances: " + dm.getCommunicationInstances().size());
				if (level > 2) {
					// ICI
					log.info("InternalComponentInstances: " + getAsString(dm.getInternalComponentInstances()));
					// VMI
					log.info("VMInstances: " + getAsString(dm.getVmInstances()));
					// HI
					log.info("HostingInstances: "+getAsString(dm.getHostingInstances()));
					// CI
					log.info("CommIntances: "+getAsString(dm.getCommunicationInstances()));
				}
			}

	}

	private static <T extends DeploymentElement> String getAsString(EList<T> deploymentElements){
		return deploymentElements.stream().map(DeploymentElement::getName).collect(Collectors.joining(" "));
	}

	private void notifySolutionApplied(String camelModelID, String notificationUri, String uuid) {
		log.info("Sending solution applied notification");
		NotificationResult result = prepareSuccessNotificationResult();
		ApplySolutionNotificationRequest notification = prepareNotification(camelModelID, result, uuid);
		sendNotification(notification, notificationUri);
	}

	private void notifySolutionNotApplied(String camelModelID, String notificationUri, String uuid)  {
		log.info("Sending solution NOT applied notification");
		NotificationResult result = prepareErrorNotificationResult("Solution was not applied.");
		ApplySolutionNotificationRequest notification = prepareNotification(camelModelID, result, uuid);
		sendNotification(notification, notificationUri);
	}

	private void sendNotification(ApplySolutionNotificationRequest notification, String notificationUri) {
		String esbUrl = env.getProperty("esb.url");

		if (esbUrl.endsWith("/")) {
			esbUrl = esbUrl.substring(0, esbUrl.length() - 1);
		}
		if (notificationUri.startsWith("/")) {
			notificationUri = notificationUri.substring(1);
		}
		try {
			log.info("Sending notification to: "+esbUrl);
			restTemplate.postForEntity(esbUrl + "/" + notificationUri, notification, String.class);
		} catch (RestClientException restException){
			log.error("Error sending notification: " +restException.getMessage());
		}
		log.info("Notification sent.");
	}
	private Watermark prepareWatermark(String uuid) {
		Watermark watermark = new WatermarkImpl();
		watermark.setUser("SolverToDeployment");
		watermark.setSystem("SolverToDeployment");
		watermark.setDate(new Date());
		watermark.setUuid(uuid);
		return watermark;
	}

	private NotificationResult prepareSuccessNotificationResult() {
		NotificationResult result = new NotificationResultImpl();
		result.setStatus(SUCCESS);
		return result;
	}

	private NotificationResult prepareErrorNotificationResult(String errorMsg) {
		NotificationResult result = new NotificationResultImpl();
		result.setErrorDescription(errorMsg);
		result.setStatus(ERROR);
		return result;
	}

	private ApplySolutionNotificationRequest prepareNotification(String camelModelID, NotificationResult result, String uuid) {
		ApplySolutionNotificationRequest notification = new ApplySolutionNotificationRequestImpl();
		notification.setApplicationId(camelModelID);
		notification.setResult(result);
		notification.setWatermark(prepareWatermark(uuid));
		return notification;
	}
}
