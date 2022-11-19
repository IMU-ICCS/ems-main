/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate.translator;

import camel.core.CamelModel;
import eu.melodic.event.translate.CamelToEplTranslationException;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;
import eu.melodic.event.translate.properties.RuleTemplateProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class CamelToEplTranslatorWeb extends CamelToEplTranslatorFile {

	private String baseUrl;
	private String modelsDir = "";
	private RestTemplate restTemplate;
	private boolean deleteFile;

	public CamelToEplTranslatorWeb(ApplicationContext applicationContext, CamelToEplTranslatorProperties properties, RuleTemplateProperties ruleTemplates) {
		super(applicationContext, properties, ruleTemplates);
		initialize();
	}

	public void initialize() {
		restTemplate = new RestTemplate();

		// Base URL for downloading models
		String url = "";
		if (properties.getTranslatorProperties()!=null)
			url = properties.getTranslatorProperties().getOrDefault("camelWeb.baseUrl", "");
		if (url!=null) url = url.trim();
		if (StringUtils.isBlank(url))
			throw new IllegalArgumentException("Translator property 'camelWeb.baseUrl' is empty");
		if (!url.endsWith("/")) url += "/";
		baseUrl = url;
		log.info("CamelToEplTranslatorWeb: baseUrl: {}", baseUrl);

		// Directory where models will be downloaded
		modelsDir = getModelsDir("camelWeb.modelsDir", modelsDir);
		log.info("CamelToEplTranslatorWeb: modelsDir: {}", modelsDir);

		// Delete downloaded file after processing
		String del = "false";
		if (properties.getTranslatorProperties()!=null)
			del = properties.getTranslatorProperties().getOrDefault("camelWeb.deleteFile", del).trim();
		deleteFile = Boolean.parseBoolean(del);
		log.info("CamelToEplTranslatorWeb: deleteFile: {}", deleteFile);
	}

	// ================================================================================================================
	// Public API

	public TranslationContext translate(String camelModelPath) {
		log.debug("CamelToEplTranslatorWeb.translate():  BEGIN: camel-model-path: {}", camelModelPath);
		try {
			// Get model file path
			if (StringUtils.isBlank(camelModelPath))
				throw new IllegalArgumentException("Argument 'camelModelPath' is blank");

			boolean hasExt = StringUtils.endsWithIgnoreCase(camelModelPath, ".xmi");
			camelModelPath = camelModelPath.replaceAll("^/+", "");
			if (StringUtils.isBlank(camelModelPath))
				throw new IllegalArgumentException("Argument 'camelModelPath' is not valid");
			String modelUrl = baseUrl + camelModelPath;
			log.debug("CamelToEplTranslatorWeb.translate():  Model URL: {}", modelUrl);

			// Download model
			ResponseEntity<String> response;
			HttpStatus responseStatus;
			try {
				log.info("CamelToEplTranslatorWeb.translate():  Downloading Model from: {}", modelUrl);
				response = restTemplate.getForEntity(modelUrl, String.class);
				responseStatus = response.getStatusCode();
			} catch (HttpClientErrorException e) {
				if (e.getStatusCode().is4xxClientError()) {
					log.warn("CamelToEplTranslatorWeb.translate():  Model URL: Not found: {}", modelUrl);

					if (hasExt) modelUrl = StringUtils.removeEndIgnoreCase(modelUrl, ".xmi");
					else modelUrl += ".xmi";
					log.info("CamelToEplTranslatorWeb.translate():  Also trying to download Model from: {}", modelUrl);
					response = restTemplate.getForEntity(modelUrl, String.class);
					responseStatus = response.getStatusCode();
				} else {
					throw e;
				}
			}
			if (!responseStatus.is2xxSuccessful()) {
				log.error("CamelToEplTranslatorWeb.translate(): Error while downloading model: http-status={}", responseStatus);
				throw new CamelToEplTranslationException("Error while downloading model: http-status: "+responseStatus);
			}

			// Store model in a file
			String main = hasExt ? StringUtils.removeEndIgnoreCase(camelModelPath, ".xmi") : camelModelPath;
			main += "-"+System.currentTimeMillis()+"-";
			String ext = !hasExt ? ".xmi" : "";
			Path modelFile = StringUtils.isNotBlank(modelsDir)
					? Files.createTempFile(Paths.get(modelsDir), main, ext)
					: Files.createTempFile(main, ext) ;

			Files.write(modelFile, response.getBody().getBytes(StandardCharsets.UTF_8));
			log.debug("CamelToEplTranslatorWeb.translate():  Model stored in: {}", modelFile);

			// Read CAMEL model
			CamelModel camelModel = readModelFromFile(camelModelPath, modelFile.toString());

			if (deleteFile)
				modelFile.toFile().delete();

			// Process model
			return translate(camelModel);

		} catch (Exception ex) {
			log.error("CamelToEplTranslatorWeb.translate(): EXCEPTION: ", ex);
			throw new RuntimeException(ex);
		} finally {
			log.debug("CamelToEplTranslatorWeb.translate():  END: camel-model-path: {}", camelModelPath);
		}
	}
}