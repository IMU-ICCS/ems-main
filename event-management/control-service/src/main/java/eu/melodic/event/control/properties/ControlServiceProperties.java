/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.control.properties;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "control")
@PropertySource("file:${MELODIC_CONFIG_DIR}/eu.melodic.event.control.properties")
@Slf4j
public class ControlServiceProperties {
	@Value("${control.upperware-grouping}")
	private String upperwareGrouping;
	@Value("${control.metasolver-configuration-url}")
	private String metasolverConfigurationUrl;
	@Value("${control.esb-url}")
	private String esbUrl;
	
	@Value("${control.preload.camel-model:}")
	private String preloadCamelModel;
	@Value("${control.preload.cp-model:}")
	private String preloadCpModel;
	
	@Value("${control.skip-translation:false}")
	private boolean skipTranslation;
	@Value("${control.skip-mvv-retrieve:false}")
	private boolean skipMvvRetrieve;
	@Value("${control.skip-broker-cep:false}")
	private boolean skipBrokerCep;
	@Value("${control.skip-baguette:false}")
	private boolean skipBaguette;
	@Value("${control.skip-metasolver:false}")
	private boolean skipMetasolver;
	@Value("${control.skip-esb-notification:false}")
	private boolean skipEsbNotification;
	
	@Value("${control.tc-load-file:}")
	private String tcLoadFile;
	@Value("${control.tc-save-file:}")
	private String tcSaveFile;
	@Value("${control.event-debug-enabled:false}")
	private boolean eventDebugEnabled;
}
