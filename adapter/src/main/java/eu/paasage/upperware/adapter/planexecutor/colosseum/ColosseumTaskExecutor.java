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
import eu.paasage.upperware.adapter.planexecutor.TaskExecutor;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ColosseumTaskExecutor<T> implements TaskExecutor<T> {

  protected ColosseumApi api;

  protected ColosseumContext executionContext;

}
