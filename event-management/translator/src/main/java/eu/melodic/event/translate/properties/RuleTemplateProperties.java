/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Validated
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "generator")
@Slf4j
public class RuleTemplateProperties {
	@Value("${generator.language:}")
	private String language;
	
    private Map<String,Map<String,List<String>>> ruleTemplates;

    public Map<String,Map<String,List<String>>> getRuleTemplates() {
        return ruleTemplates;
    }

    public void setRuleTemplates(Map<String,Map<String,List<String>>> map) {
        this.ruleTemplates = map;
    }
	
	public List<String> getTemplatesFor(String type, String grouping) {
		List<String> list = Optional.ofNullable( ruleTemplates.get(type) ).map(groupings -> groupings.get(grouping)).orElse(null);
		if (list==null) list = new ArrayList<>();
		return list;
	}
}
