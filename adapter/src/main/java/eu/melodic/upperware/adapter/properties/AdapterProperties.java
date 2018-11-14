/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.properties;

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

import static com.google.common.base.Preconditions.checkArgument;
import static java.lang.String.format;

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
  private Clouds clouds;

  private TaskExecutor taskExecutor;

  @Valid
  @NotNull
  private CloudiatorV2 cloudiatorV2;

  @Getter
  @Setter
  public static class Esb {

    @NotBlank
    private String url;

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

  //TODO - uwspolnic z Generatorem !!!!
  @Getter
  @Setter
  public static class CloudiatorV2 {

    @NotBlank
    private String url;

    @NotBlank
    private String apiKey;

    private int httpReadTimeout = 50;
  }
}
