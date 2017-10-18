/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.executioncontext.cdoserver;

import eu.melodic.upperware.adapter.communication.cdoserver.CdoServerApi;
import eu.paasage.camel.deployment.DeploymentModel;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServerClientUpdater implements CdoServerUpdater {

  private CdoServerApi cdoServerApi;

  @Override
  public void updateCamelModel(String resourceName) {
    log.info("Updating CAMEL model in CDO Server");
    setExecutionContext(resourceName);
    log.info("CAMEL model has been updated");
  }

  private void setExecutionContext(String resourceName) {
    CDOTransaction tr = cdoServerApi.openTransaction();
    try {
      DeploymentModel camelModel = cdoServerApi.getModelToDeploy(resourceName, tr);
      String executionContextName = getRandomExecutionContextName();
      String requirementGroupName = getRandomRequirementGroupName();
      cdoServerApi.setExecutionContext(camelModel, executionContextName, requirementGroupName, tr);
      tr.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      cdoServerApi.closeTransaction(tr);
    }
  }

  private String getRandomExecutionContextName() {
    return ("ExecutionContext_" + getUniqueId());
  }

  private String getRandomRequirementGroupName() {
    return ("RequirementGroup_" + getUniqueId());
  }

  private String getUniqueId() {
    return Long.toString((new Date()).getTime());
  }
}
