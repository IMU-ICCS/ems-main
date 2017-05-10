/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.planexecutor.colosseum;

import de.uniulm.omi.cloudiator.colosseum.client.entities.PortProvided;
import de.uniulm.omi.cloudiator.colosseum.client.entities.PortRequired;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.paasage.upperware.adapter.communication.colosseum.ColosseumConfigApi;
import eu.paasage.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.paasage.upperware.adapter.plangenerator.model.Communication;
import eu.paasage.upperware.adapter.plangenerator.tasks.CommunicationTask;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;
import static java.lang.String.format;

@Slf4j
public class CommunicationTaskExecutor extends ColosseumTaskExecutor<Communication> {

  CommunicationTaskExecutor(CommunicationTask task, Collection<Future> predecessors,
                            ColosseumApi api, ColosseumConfigApi configApi, ColosseumContext context) {
    super(task, predecessors, api, configApi, context);
  }

  @Override
  public void create(Communication comm) {
    String name = comm.getName();
    checkNotNull(name);

    String portProvName = comm.getPortProvName();
    checkNotNull(portProvName);
    String portReqName = comm.getPortReqName();
    checkNotNull(portReqName);

    log.info("Executing Create Communication task for communication {}", name);

    PortProvided portProvEntity = context.getPortProvided(portProvName)
      .orElseThrow(() -> new IllegalStateException(format(
        "Port provided %s does not exist in Colosseum - communication cannot be created", portProvName)));

    PortRequired portReqEntity = context.getPortRequired(portReqName)
      .orElseThrow(() -> new IllegalStateException(format(
        "Port required %s does not exist in Colosseum - communication cannot be created", portReqName)));

    Long portProvId = portProvEntity.getId();
    checkNotNull(portProvId);
    Long portReqId = portReqEntity.getId();
    checkNotNull(portReqId);

    de.uniulm.omi.cloudiator.colosseum.client.entities.Communication commEntity =
      new de.uniulm.omi.cloudiator.colosseum.client.entities.Communication(portReqId, portProvId);
    commEntity = api.createCommunication(commEntity);
    context.addCommunication(commEntity);

    log.info("Communication {} was successfully created at {}", name, commEntity.getSelfLink());
  }

  @Override
  public void update(Communication comm) {
    // TODO
  }

  @Override
  public void delete(Communication comm) {
    // TODO
  }
}
