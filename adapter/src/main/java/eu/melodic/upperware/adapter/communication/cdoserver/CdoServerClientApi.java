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
import eu.paasage.mddb.cdo.client.CDOClient;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServerClientApi implements CdoServerApi {

  private CDOClient cdoClient;

  @Override
  public DeploymentModel getModelToDeploy(@NonNull String resourceName, CDOTransaction tr) {
    EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();
    if (CollectionUtils.isNotEmpty(contents)) {
      CamelModel model = (CamelModel) contents.get(0);
      if (model != null) {
        EList<DeploymentModel> deploymentModels = model.getDeploymentModels();
        if (CollectionUtils.isNotEmpty(deploymentModels)) {
          return deploymentModels.get(deploymentModels.size() - 1);
        }
      }
    }
    throw new IllegalArgumentException(String.format("Cannot load Camel Deployment Model for resourceName=%s. " +
      "Check the value is valid and the model is available in CDO Server.", resourceName));
  }

  @Override
  public DeploymentModel getDeployedModel(String resourceName, CDOTransaction tr) {
    EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();
    if (CollectionUtils.isNotEmpty(contents)) {
      CamelModel model = (CamelModel) contents.get(0);
      if (model != null) {
        List<ExecutionModel> execModels = model.getExecutionModels();
        int numberOfExecModels = execModels.size();
        if (numberOfExecModels < 1) {
          return null;
        }
        EList<ExecutionContext> execContexts = execModels.get(numberOfExecModels - 1).getExecutionContexts();
        int numberOfExecContexts = execContexts.size();
        if (numberOfExecContexts < 1) {
          return null;
        }
        return execContexts.get(numberOfExecContexts - 1).getDeploymentModel();
      }
    }
    throw new IllegalArgumentException(String.format("Cannot load Camel Deployment Model for resourceName=%s. " +
      "Check the value is valid and the model is available in CDO Server.", resourceName));
  }

  @Override
  public void setExecutionContext(DeploymentModel deploymentModel, String execContextName, CDOTransaction tr) {
    CamelModel camelModel = (CamelModel) deploymentModel.eContainer();
    Collection<ExecutionModel> execModels = camelModel.getExecutionModels();
    Application app = camelModel.getApplications().get(0);

    ExecutionModel newExecModel = ExecutionFactory.eINSTANCE.createExecutionModel();
    newExecModel.setName(execContextName);

    ExecutionContext execContext = ExecutionFactory.eINSTANCE.createExecutionContext();
    execContext.setName(execContextName);
    execContext.setApplication(app);
    execContext.setDeploymentModel(deploymentModel);

    newExecModel.getExecutionContexts().add(execContext);

    execModels.add(newExecModel);
  }

  @Override
  public CDOTransaction openTransaction() {
    return cdoClient.openTransaction();
  }

  @Override
  public void closeTransaction(CDOTransaction tr) {
    tr.close();
  }
}
