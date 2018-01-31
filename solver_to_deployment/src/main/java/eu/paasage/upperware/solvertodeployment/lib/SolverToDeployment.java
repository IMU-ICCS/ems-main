/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.lib;

import eu.melodic.cache.CacheService;
import eu.melodic.cache.CacheUtils;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.solverToDeployment.ApplySolutionNotificationRequest;
import eu.melodic.models.services.solverToDeployment.ApplySolutionNotificationRequestImpl;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentElement;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;
import eu.paasage.upperware.solvertodeployment.utils.DataHolder;
import eu.paasage.upperware.solvertodeployment.utils.DataUtils;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

import static eu.melodic.models.commons.NotificationResult.StatusType.ERROR;
import static eu.melodic.models.commons.NotificationResult.StatusType.SUCCESS;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class SolverToDeployment {

	private RestTemplate restTemplate;
	private Environment env;
	private CacheService<NodeCandidates> cacheService;
		
	@Async
	public void doWorkTS(String camelModelID, String paasageConfigurationID, String notificationUri,  String requestUuid)
					throws S2DException {
		log.info("Application ID: {}", camelModelID);
		log.info("CDO models path: {}", paasageConfigurationID);
		log.info("Notification URI: {}", notificationUri);
		log.info("UUID: {}", requestUuid);

		try {
			NodeCandidates nodeCandidates = Objects.requireNonNull(cacheService.load(CacheUtils.createCacheKey(paasageConfigurationID)));

			CDODatabaseProxy cdoProxy = CDODatabaseProxy.getInstance();
			CDOTransaction cdoTransaction = cdoProxy.getCdoClient().openTransaction();

			EList<EObject> contentsCM = cdoTransaction.getResource(camelModelID).getContents();

			CamelModel camelModel = CdoTool.getLastCamelModel(contentsCM)
                .orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));

			
			EList<EObject> contentsPC = cdoTransaction.getResource(paasageConfigurationID).getContents();
			ConstraintProblem constraintProblem = (ConstraintProblem) contentsPC.get(1);

			// Checking if there is a solution
			if (constraintProblem.getSolution().size()==0) {
				log.info("No solution available in Constraint Problem!");
				notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
				return;
			}

			Solution solution = CPModelTool.searchLastSolution(constraintProblem.getSolution());

			// Do Work
			try {
				int dmId = CDODatabaseProxy2.copyFirstDeploymentModel(camelModelID);

				CloudMLHelper.setGlobalDMIdx(dmId);
				CloudMLHelper.resetGlobalCount();

				DeploymentModel deploymentModel = camelModel.getDeploymentModels().get(dmId);

				// Generate new instances into this new DM of camel
				DataHolder dataholder  = DataUtils.computeDatasToRegister(deploymentModel, constraintProblem, solution, camelModelID, nodeCandidates);
				if (dataholder==null) {
					notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
					return;
				}

				dataholder.setDmId(dmId);
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
