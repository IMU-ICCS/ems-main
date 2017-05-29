/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.communication.cdoserver;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServerCamelClientApi implements CdoServerCamelApi {

  private CDOClient cdoClient;

  @Override
  public DeploymentModel loadDeploymentModel(@NonNull String resourceName, int deploymentModelIndex, CDOTransaction trans) {
    EList<EObject> contents = trans.getOrCreateResource(resourceName).getContents();
    if (CollectionUtils.isNotEmpty(contents)) {
      CamelModel model = (CamelModel) contents.get(0);
      if (model != null) {
        EList<DeploymentModel> deploymentModels = model.getDeploymentModels();
        if (CollectionUtils.size(deploymentModels) > deploymentModelIndex) {
          return deploymentModels.get(deploymentModelIndex);
        }
      }
    }
    throw new IllegalArgumentException(String.format("Cannot load Camel Deployment Model for resourceName=%s, deploymentModelIndex=%s. " +
      "Check both values are valid and the model is available in CDO Server.", resourceName, deploymentModelIndex));
  }

  @Override
  public void setExecutionContext(DeploymentModel deploymentModel, String executionContextName, CDOTransaction trans) {
    // TODO
  }

  @Override
  public CDOTransaction openTransaction() {
    return cdoClient.openTransaction();
  }

  @Override
  public void closeTransaction(CDOTransaction trans) {
    trans.close();
  }
}
