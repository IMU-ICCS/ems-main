/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.executioncontext.cdoserver;

import camel.deployment.DeploymentInstanceModel;
import eu.melodic.upperware.adapter.communication.cdoserver.CdoServerApi;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServerClientUpdater implements CdoServerUpdater {

  private CdoServerApi cdoServerApi;

  @Override
  public void updateCamelModel(String resourceName) {
    log.info("Updating CAMEL model in CDO Server");
    setHistoryRecord(resourceName);
    log.info("CAMEL model has been updated");
  }

  private void setHistoryRecord(String resourceName) {
    CDOSessionX cdoSessionX = cdoServerApi.openSession();
    CDOTransaction tr = cdoSessionX.openTransaction();

    try {
      DeploymentInstanceModel modelToDeploy = cdoServerApi.getModelToDeploy(resourceName, tr);
      cdoServerApi.setExecutionContext(modelToDeploy);
      tr.commit();
    } catch (Exception e) {
      throw new RuntimeException(e);
    } finally {
      cdoSessionX.closeTransaction(tr);
      cdoSessionX.closeSession();
    }
  }
}
