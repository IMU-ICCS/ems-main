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
import camel.deployment.DeploymentModel;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import org.eclipse.emf.cdo.transaction.CDOTransaction;

public interface CdoServerApi {

  DeploymentInstanceModel getModelToDeploy(String resourceName, CDOTransaction tr);

  DeploymentInstanceModel getDeployedModel(String resourceName, CDOTransaction tr);

  void setExecutionContext(DeploymentModel deploymentModel, String execContextName, String requirementGroupName, CDOTransaction tr);

  CDOSessionX openSession();
}
