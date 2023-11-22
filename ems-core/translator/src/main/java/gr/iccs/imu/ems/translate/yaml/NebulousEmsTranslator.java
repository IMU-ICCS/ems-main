/*
 * Copyright (C) 2023-2025 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package gr.iccs.imu.ems.translate.yaml;

import gr.iccs.imu.ems.translate.TranslationContext;
import gr.iccs.imu.ems.translate.Translator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NebulousEmsTranslator implements Translator, InitializingBean {

	private final ApplicationContext applicationContext;
	private final NebulousEmsTranslatorProperties properties;

	@Override
	public void afterPropertiesSet() throws Exception {
		log.info("NebulousEmsTranslator: initialized");
	}

	// ================================================================================================================
	// Public API

	@Override
	public TranslationContext translate(String metricModelPath) {
		if (StringUtils.isBlank(metricModelPath)) {
			log.error("NebulousEmsTranslator: No metric model specified");
			throw new NebulousEmsTranslationException("No metric model specified");
		}

		log.info("NebulousEmsTranslator: Parsing metric model file: {}", metricModelPath);
		try {
			/*final File modelFile = Paths.get(properties.getModelDir(), metricModelPath).toFile();
			log.info("NebulousEmsTranslator: Actual metric model path: {}", modelFile);
			final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
			metricModel = new MetricModel(modelFile, mapper.readTree(modelFile));*/
		} catch (Exception e) {
			throw new NebulousEmsTranslationException("Error while parsing metric model YAML file: "+metricModelPath, e);
		}
//		log.info("NebulousEmsTranslator: Metric model root: {}", metricModel);

		log.info("NebulousEmsTranslator: Translating metric model: {}", metricModelPath);
//		TranslationContext _TC = translate(metricModel);
		log.info("NebulousEmsTranslator: Translating metric model completed: {}", metricModelPath);
		return null;
	}

	// ================================================================================================================
	// Private methods

}