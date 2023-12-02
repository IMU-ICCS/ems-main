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
import gr.iccs.imu.ems.translate.yaml.analyze.MetricModelAnalyzer;
import gr.iccs.imu.ems.translate.yaml.generate.RuleGenerator;
import gr.iccs.imu.ems.translate.yaml.transform.GraphTransformer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
		Object modelObj;
		try {
			// -- Load model ------------------------------------------------------
			Path inputFile = Paths.get(properties.getModelsDir(), metricModelPath);
			String yamlStr = Files.readString(inputFile);

			// Parsing YAML file with SnakeYAML, since Jackson Parser does not support Anchors and references
			Yaml yaml = new Yaml();
			modelObj = yaml.loadAs(yamlStr, Object.class);
			log.trace("NebulousEmsTranslator: YAML model contents:\n{}", modelObj);

			// -- Translate model ---------------------------------------------
			log.info("NebulousEmsTranslator: Translating metric model: {}", metricModelPath);
			TranslationContext _TC = translate(modelObj, metricModelPath);
			log.info("NebulousEmsTranslator: Translating metric model completed: {}", metricModelPath);

			return _TC;
		} catch (Exception e) {
			throw new NebulousEmsTranslationException("Error while translating metric model file: "+metricModelPath, e);
		}
	}

	// ================================================================================================================
	// Private methods

	private TranslationContext translate(Object modelObj, String modelName) throws Exception {
		log.debug("NebulousEmsTranslator.translate():  BEGIN: metric-model={}", modelObj);

		// initialize data structures
		TranslationContext _TC = new TranslationContext(modelName);

		// Analyze metric model
		log.debug("NebulousEmsTranslator.translate():  Analyzing model...");
		MetricModelAnalyzer modelAnalyzer = applicationContext.getBean(MetricModelAnalyzer.class);
		modelAnalyzer.analyzeModel(_TC, modelObj, modelName);
		log.debug("NebulousEmsTranslator.translate():  Analyzing model... done");

		// transform graph
		//XXX:TODO: Not sure if it is needed in Nebulous (removes MVVs and adds TL metrics above TL metric contexts,
		//XXX:TODO: ... but MVVs are not used, neither metrics (only metric contexts)).
		log.debug("NebulousEmsTranslator.translate():  Transforming DAG...");
		GraphTransformer transformer = applicationContext.getBean(GraphTransformer.class);
		transformer.transformGraph(_TC.getDAG());
		log.debug("NebulousEmsTranslator.translate():  Transforming DAG... done");

		// generate EPL rules
		log.debug("NebulousEmsTranslator.translate():  Generating EPL rules...");
		RuleGenerator generator = applicationContext.getBean(RuleGenerator.class);
		generator.generateRules(_TC);
		log.debug("NebulousEmsTranslator.translate():  Generating EPL rules... done");

		log.debug("NebulousEmsTranslator.translate():  END: result={}", _TC);
		return _TC;
	}

}