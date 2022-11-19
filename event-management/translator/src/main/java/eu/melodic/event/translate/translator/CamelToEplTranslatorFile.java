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
import eu.melodic.event.translate.TranslationContext;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.nio.file.Paths;

@Slf4j
@Component
@NoArgsConstructor
public class CamelToEplTranslatorFile extends AbstractCamelTranslator implements InitializingBean {

	private String modelsDir = "./";

	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();

		String path = modelsDir;
		if (properties.getTranslatorProperties()!=null)
			path = properties.getTranslatorProperties().get("camelFile.modelsDir");
		if (StringUtils.isBlank(path))
			log.warn("CamelToEplTranslatorFile: Translator property 'CamelToEplTranslatorFile.modelsDir' is empty. Using default 'modelDir'");
		path = path==null ? modelsDir : path.trim();
		if (!path.endsWith("/")) path += "/";

		if (!Paths.get(path).toFile().exists())
			throw new IllegalArgumentException("Model dir. does not exist: "+path);
		if (!Paths.get(path).toFile().canRead())
			throw new IllegalArgumentException("Model dir. is not accessible: "+path);

		modelsDir = path;
		log.info("CamelToEplTranslatorFile: modelDir: {}", modelsDir);
	}

	// ================================================================================================================
	// Public API

	public TranslationContext translate(String camelModelPath) {
		log.debug("CamelToEplTranslatorFile.translate():  BEGIN: camel-model-path: {}", camelModelPath);
		try {
			// Get model file path
			if (StringUtils.isBlank(camelModelPath))
				throw new IllegalArgumentException("Argument 'camelModelPath' is blank");

			camelModelPath = camelModelPath.replaceAll("^/+", "");
			if (StringUtils.isBlank(camelModelPath))
				throw new IllegalArgumentException("Argument 'camelModelPath' is not valid");
			if (!StringUtils.endsWithIgnoreCase(camelModelPath, ".xmi"))
				camelModelPath += ".xmi";
			String modelFile = modelsDir + camelModelPath;
			log.debug("CamelToEplTranslatorFile.translate():  Model file: {}", modelFile);

			if (!Paths.get(modelFile).toFile().exists())
				throw new IllegalArgumentException("Model file not found: "+modelFile);
			if (!Paths.get(modelFile).toFile().canRead())
				throw new IllegalArgumentException("Model file is not readable: "+modelFile);

			// Retrieve CAMEL model
			log.debug("CamelToEplTranslatorFile.translate():  Retrieving model...");
			final org.eclipse.emf.ecore.resource.ResourceSet rs =
					new org.eclipse.emf.ecore.resource.impl.ResourceSetImpl();
			org.eclipse.emf.ecore.resource.Resource resIn =
					rs.getResource(org.eclipse.emf.common.util.URI.createFileURI(modelFile), true);
			log.debug("CamelToEplTranslatorFile.translate():  Resource: {}", resIn);

			if (resIn.getContents().size()==0)
				throw new IllegalArgumentException("Model file is empty: "+modelFile);
			if (resIn.getContents().size()>1)
				throw new IllegalArgumentException("Model file contains >1 models: "+modelFile);
			EObject contents = resIn.getContents().get(0);
			log.debug("CamelToEplTranslatorFile.translate():  Contents: {}", contents);
			CamelModel camelModel = (CamelModel) contents;
			log.debug("CamelToEplTranslatorFile.translate():  Retrieved CAMEL model at path: {}", camelModelPath);

			// Process model
			return translate(camelModel);
			
		} catch (Exception ex) {
			log.error("CamelToEplTranslatorFile.translate(): EXCEPTION: ", ex);
			throw new RuntimeException(ex);
		} finally {
			log.debug("CamelToEplTranslatorFile.translate():  END: camel-model-path: {}", camelModelPath);
		}
	}
}