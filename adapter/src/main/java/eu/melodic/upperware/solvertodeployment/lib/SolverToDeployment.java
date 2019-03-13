/* This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/. */

package eu.melodic.upperware.solvertodeployment.lib;

import camel.core.CamelModel;
import camel.core.Feature;
import camel.deployment.DeploymentInstanceModel;
import eu.melodic.cache.CacheService;
import eu.melodic.cache.CacheUtils;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.notification.S2DNotificationSenderImpl;
import eu.melodic.upperware.solvertodeployment.utils.DataUtils;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
@Slf4j
public class SolverToDeployment {

	private CDOClientX cdoClientX;
	private S2DNotificationSenderImpl s2DNotificationSender;
	private DataUtils dataUtils;
	private CacheService<NodeCandidates> cacheService;

	@Async
	public void doWorkTS(String camelModelId, String cdoResourcePath, String notificationUri, String requestUuid) {

		NodeCandidates nodeCandidates = Objects.requireNonNull(cacheService.load(CacheUtils.createCacheKey(cdoResourcePath)));

		CDOSessionX session = cdoClientX.getSession();
		CDOTransaction transaction = session.openTransaction();

		try {
			CamelModel camelModel = CdoTool.getLastCamelModel(transaction.getResource(camelModelId).getContents())
                .orElseThrow(() -> new IllegalStateException("Could not find camel model from camelModelID: " + camelModelId));

			EList<EObject> contentsPC = transaction.getResource(cdoResourcePath).getContents();
			ConstraintProblem constraintProblem = (ConstraintProblem) CdoTool.getFirstElement(contentsPC);

			// Checking if there is a solution
			if (CollectionUtils.isEmpty(constraintProblem.getSolution())) {
				log.info("No solution available in Constraint Problem!");
				s2DNotificationSender.notifySolutionNotApplied(camelModelId, notificationUri, requestUuid);
				return;
			}

			Solution solution = CPModelTool.searchLastSolution(constraintProblem.getSolution());

			try {
				DeploymentInstanceModel newDeploymentInstanceModel = dataUtils.computeDatasToRegister(camelModel, solution, nodeCandidates);

				if (newDeploymentInstanceModel == null) {
					s2DNotificationSender.notifySolutionNotApplied(camelModelId, notificationUri, requestUuid);
					return;
				}
				camelModel.getDeploymentModels().add(newDeploymentInstanceModel);
				transaction.commit();

			} catch (CommitException e) {
				log.error("Error during commit transaction, Unable to complete data model instances registration", e);
				s2DNotificationSender.notifySolutionNotApplied(camelModelId, notificationUri, requestUuid);
				return;
			}
			dumpDM(camelModel, 2);
		} catch (RuntimeException exception) {
			log.error("RuntimeException", exception);
			s2DNotificationSender.notifySolutionNotApplied(camelModelId, notificationUri, requestUuid);
			return;
		} finally {
			if (transaction != null && !transaction.isClosed()){
				transaction.close();
			}
			session.closeSession();
		}
		s2DNotificationSender.notifySolutionApplied(camelModelId, notificationUri, requestUuid);
	}

	private void dumpDM(CamelModel cm, int level) {
		log.info("Camel doc contains {} Deployment Model", cm.getDeploymentModels().size());
		if (level > 1)
			for (int i = 1; i < cm.getDeploymentModels().size(); i++) {
				DeploymentInstanceModel dm = (DeploymentInstanceModel) cm.getDeploymentModels().get(i);
				log.info("  DeploymentInstanceModel {} : SoftwareComponentInstances: {} CommInstances: {}",
						i, dm.getSoftwareComponentInstances().size(), dm.getCommunicationInstances().size());
				if (level > 2) {
					// ICI
					log.info("SoftwareComponentInstances: {}", getAsString(dm.getSoftwareComponentInstances()));
					// CI
					log.info("CommInstances: {}", getAsString(dm.getCommunicationInstances()));
				}
			}

	}

	private <T extends Feature> String getAsString(EList<T> features) {
		return features.stream().map(Feature::getName).collect(Collectors.joining(" "));
	}

}
