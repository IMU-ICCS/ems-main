/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.communication.cdoserver;

import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.requirement.RequirementGroup;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServerClientApi implements CdoServerApi {

  private CDOClientX cdoClient;

  @Override
  public DeploymentModel getModelToDeploy(@NonNull String resourceName, CDOTransaction tr) {
    EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();
    if (CollectionUtils.isNotEmpty(contents)) {
      CamelModel model = CdoTool.getLastCamelModel(contents)
              .orElseThrow(() -> new IllegalStateException(String.format("Could not find Camel Model for resourceName=%s", resourceName)));

      EList<DeploymentModel> deploymentModels = model.getDeploymentModels();
      if (CollectionUtils.isNotEmpty(deploymentModels)) {
        return deploymentModels.get(deploymentModels.size() - 1);
      }
    }
    throw new IllegalArgumentException(String.format("Cannot load Camel Deployment Model for resourceName=%s. " +
      "Check the value is valid and the model is available in CDO Server.", resourceName));
  }

  @Override
  public DeploymentModel getDeployedModel(String resourceName, CDOTransaction tr) {
    EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();

    if (CollectionUtils.isNotEmpty(contents)) {
      int numberOfCamelModels = contents.size();

      for (int i = numberOfCamelModels-1; i > -1 ; i--) {
          CamelModel model = (CamelModel) contents.get(i);

          if(model != null) {
            EList<ExecutionModel> executionModels = model.getExecutionModels();
            int numberOfExecModels = executionModels.size();

            for (int j = numberOfExecModels - 1; j > -1; j--) {
              EList<ExecutionContext> executionContexts = executionModels.get(j).getExecutionContexts();
              if (!executionContexts.isEmpty()) {
                cdoClient.exportModel(model, "~/"+resourceName+".xmi");
                return executionContexts.get(executionContexts.size()-1).getDeploymentModel();
              }
            }
          }
      }
      return null;
    }
    throw new IllegalArgumentException(String.format("Cannot load Camel Deployment Model for resourceName=%s. " +
      "Check the value is valid and the model is available in CDO Server.", resourceName));
  }

  @Override
  public void setExecutionContext(DeploymentModel deploymentModel, String execContextName, String requirementGroupName, CDOTransaction tr) {
    CamelModel camelModel = (CamelModel) deploymentModel.eContainer();
    Collection<ExecutionModel> execModels = camelModel.getExecutionModels();
    Application app = camelModel.getApplications().get(0);

    Optional<RequirementGroup> requirementGroupOpt = getRequirementGroup(camelModel.getRequirementModels());
    RequirementGroup requirementGroup = requirementGroupOpt
            .orElseThrow(() -> new IllegalArgumentException(format("Could not find RequirementGroup for %s application", app.getName())));

      ExecutionModel newExecModel = ExecutionFactory.eINSTANCE.createExecutionModel();
      newExecModel.setName(execContextName);

      ExecutionContext execContext = ExecutionFactory.eINSTANCE.createExecutionContext();
      execContext.setName(execContextName);
      execContext.setApplication(app);
      execContext.setDeploymentModel(deploymentModel);
      execContext.setRequirementGroup(requirementGroup);

      newExecModel.getExecutionContexts().add(execContext);

      execModels.add(newExecModel);
  }

  @Override
  public CDOSessionX openSession() {
    return cdoClient.getSession();
  }

  private Optional<RequirementGroup> getRequirementGroup(EList<RequirementModel> requirementModels) {
    return requirementModels.stream()
            .map(RequirementModel::getRequirements)
            .flatMap(Collection::stream)
            .filter(requirement -> requirement instanceof RequirementGroup)
            .map(requirement -> (RequirementGroup) requirement)
            .findFirst();
  }

}
