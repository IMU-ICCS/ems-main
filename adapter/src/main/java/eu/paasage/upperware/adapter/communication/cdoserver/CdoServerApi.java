/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.communication.cdoserver;

import org.eclipse.emf.cdo.transaction.CDOTransaction;

public interface CdoServerApi<T> {

  T loadDeploymentModel(String resourceName, int deploymentModelIndex, CDOTransaction trans);

  void setExecutionContext(T deploymentModel, String executionContextName, CDOTransaction trans);

  CDOTransaction openTransaction();

  void closeTransaction(CDOTransaction trans);

}
