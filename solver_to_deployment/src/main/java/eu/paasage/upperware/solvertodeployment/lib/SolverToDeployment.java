/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.paasage.upperware.solvertodeployment.lib;

import camel.core.CamelModel;
import camel.core.Feature;
import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentTypeModel;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.CacheUtils;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.models.commons.NotificationResult;
import eu.melodic.models.commons.NotificationResultImpl;
import eu.melodic.models.commons.Watermark;
import eu.melodic.models.commons.WatermarkImpl;
import eu.melodic.models.services.solverToDeployment.ApplySolutionNotificationRequest;
import eu.melodic.models.services.solverToDeployment.ApplySolutionNotificationRequestImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.solvertodeployment.db.lib.CDODatabaseProxy2;
import eu.paasage.upperware.solvertodeployment.derivator.lib.CloudMLHelper;
import eu.paasage.upperware.solvertodeployment.properties.SolverToDeploymentProperties;
import eu.paasage.upperware.solvertodeployment.utils.DataHolder;
import eu.paasage.upperware.solvertodeployment.utils.DataUtils;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
	private SolverToDeploymentProperties solverToDeploymentProperties;
		
	@Async
	public void doWorkTS(String camelModelID, String paasageConfigurationID, String notificationUri,  String requestUuid)
					throws S2DException {
		log.info("Application ID: {}", camelModelID);
		log.info("CDO models path: {}", paasageConfigurationID);
		log.info("Notification URI: {}", notificationUri);
		log.info("UUID: {}", requestUuid);

		CDOSessionX session = CDODatabaseProxy.getInstance().getCdoClient().getSession();
		CDOTransaction transaction = session.openTransaction();

		try {
			NodeCandidates nodeCandidates = Objects.requireNonNull(cacheService.load(CacheUtils.createCacheKey(paasageConfigurationID)));

			EList<EObject> contentsCM = transaction.getResource(camelModelID).getContents();

			CamelModel camelModel = getLastCamelModel(contentsCM)
                .orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelID));

			
			EList<EObject> contentsPC = transaction.getResource(paasageConfigurationID).getContents();
			ConstraintProblem constraintProblem = (ConstraintProblem) contentsPC.get(0);

			// Checking if there is a solution
			if (constraintProblem.getSolution().size()==0) {
				log.info("No solution available in Constraint Problem!");
				notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
				return;
			}

			Solution solution = CPModelTool.searchLastSolution(constraintProblem.getSolution());

			// Do Work
			try {
			    log.warn("Starting...");

				DeploymentTypeModel deploymentTypeModel = (DeploymentTypeModel) camelModel.getDeploymentModels().get(0);

				int dmId = CDODatabaseProxy2.saveNewDeploymentInstanceModel(transaction, camelModelID);

				CloudMLHelper.setGlobalDMIdx(dmId);
				CloudMLHelper.resetGlobalCount();

				// Generate new instances into this new DM of camel

				DeploymentInstanceModel deploymentInstanceModel = (DeploymentInstanceModel) camelModel.getDeploymentModels().get(dmId);
				DataHolder dataholder = DataUtils.computeDatasToRegister(deploymentTypeModel, deploymentInstanceModel, constraintProblem, solution,
						camelModel, camelModelID, nodeCandidates, solverToDeploymentProperties, transaction);
				if (dataholder==null) {
					notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
					return;
				}

				dataholder.setDmId(dmId);
				DataUtils.registerDataHolderToCDO(camelModelID, dataholder, transaction); // COPY TO CDO

			} catch (CommitException e) {
				log.error("Unable to complete data model instances registration", e);
				notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
				return;
			}
			dumpDM(camelModel, 2);
		} catch (RuntimeException exception) {
			log.error("RuntimeException", exception);
			notifySolutionNotApplied(camelModelID, notificationUri, requestUuid);
			return;
		} finally {
			if (transaction != null && !transaction.isClosed()){
				transaction.close();
			}
			session.closeSession();
		}
		notifySolutionApplied(camelModelID, notificationUri, requestUuid);
	}

	public static void dumpDM(CamelModel cm, int level) {
		log.info("Camel doc contains " + cm.getDeploymentModels().size() + " Deployment Model");
		if (level > 1)
			for (int i = 1; i < cm.getDeploymentModels().size(); i++) {
				DeploymentInstanceModel dm = (DeploymentInstanceModel) cm.getDeploymentModels().get(i);
				log.info("  DeploymentInstanceModel " + i + " :" +
						" SoftwareComponentInstances: " + dm.getSoftwareComponentInstances().size() +
						" CommInstances: " + dm.getCommunicationInstances().size()
						+ " VmInstances: " + dm.getVmInstances());
				if (level > 2) {
					// ICI
					log.info("SoftwareComponentInstances: " + getAsString(dm.getSoftwareComponentInstances()));
					// CI
					log.info("CommInstances: " + getAsString(dm.getCommunicationInstances()));
					// VMI
					log.info("VmInstances: " + getAsString(dm.getVmInstances()));
				}
			}

	}

	private static <T extends Feature> String getAsString(EList<T> features) {
		return features.stream().map(Feature::getName).collect(Collectors.joining(" "));
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


	//TODO - move this to commons
	public Optional<CamelModel> getLastCamelModel(List<EObject> contentsCM){
		return getLastElement(contentsCM)
				.filter(CamelModel.class::isInstance)
				.map(CamelModel.class::cast);
	}

	private <T extends EObject> Optional<T> getLastElement(List<T> collection) {
		return Optional.ofNullable(CollectionUtils.isNotEmpty(collection) ? collection.get(collection.size()-1) : null);
	}

}
