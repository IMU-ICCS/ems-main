/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.plangenerator.tasks;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public abstract class ConfigurationTask<T> implements Task<T> {

  private String name;
  private Type type;
  private T data;

  @Override
  public String getName() {
    return name;
  }

  @Override
  public Type getType() {
    return type;
  }

  @Override
  public T getData() {
    return data;
  }
}
