/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.metasolver.properties;

import eu.melodic.upperware.metasolver.metricvalue.TopicType;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Validated
@Configuration
@ConfigurationProperties
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.upperware.metaSolver.properties")
public class MetaSolverProperties {

	@Valid
	@NotNull
	private Pubsub pubsub;

	@Getter
	@Setter
	public static class Pubsub {
		private String on;

		private List<Topic> topics;

		public boolean isOn() {
			return booleanValue(on);
		}

		@Getter
		@Setter
		@ToString
		public static class Topic {
			@NotBlank
			private String name;
			@NotBlank
			private String url;
			private String clientId;
			private TopicType type;
		}
	}
	
	public static boolean booleanValue(String str) {
		return booleanValue(str, false);
	}
	
	public static boolean booleanValue(String str, boolean defVal) {
		if (str==null || (str=str.trim()).isEmpty()) return defVal;
		return (str.equalsIgnoreCase("true") || str.equalsIgnoreCase("yes") || str.equalsIgnoreCase("on"));
	}

	// --------------------------------------------------------------
	
	@Valid
	@NotNull
	private CdoConfig cdo;

	@Getter
	@Setter
	@ToString
	public static class CdoConfig {
		private String host = "localhost";
		private int port = 2036;
		private String repositoryName = "repo1";
		private boolean secure = false;
		private String username;
		private String password;
	}
}
