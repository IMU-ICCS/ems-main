/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Configuration
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.adapter.properties")
public class AdapterProperties {

  @Valid
  @NotNull
  private Esb esb;

  @Valid
  @NotNull
  private Ems ems;

  private TaskExecutor taskExecutor;

  @Valid
  @NotNull
  private ProActive paConfig;

  @Valid
  @NotNull
  private ActiveMqConfig activeMq;

  @Data
  public static class Esb {

    @NotNull
    private String url;
  }

  @Data
  public static class Ems {

    private String url;
    private boolean enabled = true;
  }

  @Data
  public static class TaskExecutor {

    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer queueCapacity;

  }

  @Data
  public static class ProActive {
    @NotNull
    private String restUrl;
    @NotNull
    private String login;
    @NotNull
    private String password;
    @NotNull
    private String encryptorPw;
  }

  @Data
  public static class ActiveMqConfig {
    @NotNull
    @Min(1000)
    private long melodicMqRestartInterval;

    @NotNull
    @Min(1000)
    private long melodicMqConnectionRetryInterval;

    @NotNull
    @Min(1)
    private long melodicMqConnectionRetryMax;

    @NotNull
    private String checkIfComponentBusyActiveMQTopic;
  }
}
