/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.properties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties
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

  @Value("${activemq.broker.url}")
  private String melodicMqAddress;

  @Value("${activemq.restartinterval:10000}")
  private long melodicMqRestartInterval;

  @Value("${activemq.connectionretryinterval:5000}")
  private long melodicMqConnectionRetryInterval;

  @Value("${activemq.connectionretrymax:10}")
  private long melodicMqConnectionRetryMax;

  @Value("${checkIfComponentBusyActiveMQTopic}")
  private String checkIfComponentBusyActiveMQTopic = "busy.>";

  @Getter
  @Setter
  public static class Esb {

    @NotBlank
    private String url;
  }

  @Getter
  @Setter
  public static class Ems {

    private String url;
    private boolean enabled = true;
  }

  @Getter
  @Setter
  public static class TaskExecutor {

    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer queueCapacity;

  }

  @Getter
  @Setter
  @ToString
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
}
