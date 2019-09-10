/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.communication.cdoserver;

import camel.deployment.DeploymentInstanceModel;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;

public interface CdoServerApi {

  //remove in the future
  DeploymentInstanceModel getModelToDeploy(String resourceName, CDOTransaction tr);

  DeploymentInstanceModel getModelToDeploy(CDOView cdoView, String resourceName, String deploymentInstanceName);

  DeploymentInstanceModel getDeployedModel(String resourceName, CDOTransaction tr);

  void setExecutionContext(DeploymentInstanceModel deploymentModel);

  CDOSessionX openSession();
}
