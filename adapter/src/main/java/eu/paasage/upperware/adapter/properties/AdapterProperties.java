/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.properties;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties
@PropertySource("file:${PAASAGE_CONFIG_DIR}/eu.paasage.upperware.adapter.properties")
public class AdapterProperties {

  @Valid
  @NotNull
  private Colosseum colosseum;

  @Getter
  @Setter
  public static class Colosseum {

    @NotBlank
    private String url;

    @Valid
    @NotNull
    private Auth auth;

    @Getter
    @Setter
    public static class Auth {

      @NotBlank
      private String email;

      @NotBlank
      private String password;

      @NotBlank
      private String tenant;
    }
  }
}
