/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.executioncontext.cdoserver;

import eu.paasage.upperware.adapter.communication.cdoserver.CdoServerApi;
import eu.paasage.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServerClientUpdater implements CdoServerUpdater {

  // TODO

  private CdoServerApi cdoServerApi;
  private ColosseumContext context;
  
}
