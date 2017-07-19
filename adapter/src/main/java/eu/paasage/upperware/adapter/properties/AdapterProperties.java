/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.adapter.properties;

import com.google.common.collect.Maps;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

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

  @Valid
  @NotNull
  private Clouds clouds;

  private TaskExecutor taskExecutor;

  @Getter
  @Setter
  public static class Colosseum {

    @NotBlank
    private String url;

    @Valid
    @NotNull
    private Auth auth;

    @Valid
    @NotNull
    private Timeouts timeouts;

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

    @Getter
    @Setter
    public static class Timeouts {

      @NotNull
      private Long image;

      @NotNull
      private Long os;

      @NotNull
      private Long location;

      @NotNull
      private Long hardware;
    }
  }

  @Getter
  @Setter
  public static class Clouds {

    @NotEmpty
    private Map<String, String> endpoints = Maps.newHashMap();

    @NotEmpty
    private Map<String, String> logins = Maps.newHashMap();

    @NotEmpty
    private Map<String, String> passwords = Maps.newHashMap();

    private Filters filters;

    public String getEndpoint(String cloud) {
      if (cloud == null) {
        return null;
      }
      Optional<String> endpointKey = endpoints.keySet().stream()
        .filter(key -> cloud.toLowerCase().equals(key.toLowerCase())).findAny();
      return endpointKey.map(s -> endpoints.get(s)).orElse(null);
    }

    public String getLogin(String cloud) {
      if (cloud == null) {
        return null;
      }
      Optional<String> loginKey = logins.keySet().stream()
        .filter(key -> cloud.toLowerCase().equals(key.toLowerCase())).findAny();
      return loginKey.map(s -> logins.get(s)).orElse(null);
    }

    public String getPassword(String cloud) {
      if (cloud == null) {
        return null;
      }
      Optional<String> passwordKey = passwords.keySet().stream()
        .filter(key -> cloud.toLowerCase().equals(key.toLowerCase())).findAny();
      return passwordKey.map(s -> passwords.get(s)).orElse(null);
    }

    @Getter
    @Setter
    public static class Filters {

      private Map<String, List<String>> keys = Maps.newHashMap();
      private Map<String, List<String>> values = Maps.newHashMap();

      public Map<String, String> getPairs(String cloud) {
        List<String> $keys = keys.get(cloud);
        List<String> $values = values.get(cloud);
        Map<String, String> filters = Maps.newHashMap();

        if ($keys == null && $values == null) {
          return filters;
        }
        checkArgument($keys != null && $values != null && $keys.size() == $values.size(),
          format("Incorrect filters for a cloud %s - check number of keys and values", cloud));

        for (int i = 0; i < $keys.size(); i++) {
          filters.put($keys.get(i), $values.get(i));
        }
        return filters;
      }
    }
  }

  @Getter
  @Setter
  public static class TaskExecutor {

    private Integer corePoolSize;
    private Integer maxPoolSize;
    private Integer queueCapacity;

  }
}
