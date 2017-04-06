/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.planexecutor.colosseum;

import eu.paasage.upperware.adapter.communication.colosseum.ColosseumApi;
import eu.paasage.upperware.adapter.executioncontext.colosseum.ColosseumContext;
import eu.paasage.upperware.adapter.plangenerator.model.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationTaskExecutor extends ColosseumTaskExecutor<Application> {

  // TODO this is example of implementation - finish it

  @Autowired
  public ApplicationTaskExecutor(ColosseumApi api, ColosseumContext executionContext) {
    super(api, executionContext);
  }

  @Override
  public void create(Application application) {
    // api.createApplication(application.getName());
    // add response object to executionContext
  }

  @Override
  public void update(Application application) {
    // api.updateApplication(application.getName());
  }

  @Override
  public void delete(Application application) {

  }
}
