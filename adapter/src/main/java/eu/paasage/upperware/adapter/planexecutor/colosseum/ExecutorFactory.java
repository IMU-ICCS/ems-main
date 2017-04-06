/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.planexecutor.colosseum;

import eu.paasage.upperware.adapter.plangenerator.tasks.ApplicationTask;
import eu.paasage.upperware.adapter.plangenerator.tasks.Task;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class ExecutorFactory {

  private ApplicationTaskExecutor applicationTaskExecutor;

  public ColosseumTaskExecutor getExecutor(Class<? extends Task> aClass) {
    if (ApplicationTask.class.equals(aClass)) {
      return applicationTaskExecutor;
    }

    // TODO create rest of mapping block here

    return null;
  }
}
