/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0, unless
 * Esper library is used, in which case it is subject to the terms of General Public License v2.0.
 * If a copy of the MPL was not distributed with this file, you can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate;

import camel.core.CamelModel;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;
import eu.melodic.event.translate.translator.CamelToEplTranslatorCdo;
import eu.melodic.event.translate.translator.CamelToEplTranslatorFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CamelToEplTranslator")
@RequiredArgsConstructor
public class CamelToEplTranslator implements Translator, InitializingBean {

	private final ApplicationContext applicationContext;
	private final CamelToEplTranslatorProperties properties;

	private Translator translator;

	@Override
	public void afterPropertiesSet() throws Exception {
		switch (properties.getTranslatorType()) {
			case CAMEL_CDO:
				translator = applicationContext.getBean(CamelToEplTranslatorCdo.class); break;
			case CAMEL_FILE:
				translator = applicationContext.getBean(CamelToEplTranslatorFile.class); break;
			default:
				throw new CamelToEplTranslationException("Unsupported translator type: "+properties.getTranslatorType());
		}
		log.info("CamelToEplTranslator: {} initialized", translator.getClass().getSimpleName());
	}

	// ================================================================================================================
	// Public API

	@Override
	public TranslationContext translate(String camelModelPath) {
		return translator.translate(camelModelPath);
	}

	@Override
	public TranslationContext translate(CamelModel camelModel) {
		return translator.translate(camelModel);
	}

	@Override
	public void printResults(TranslationContext translationContext, String exportName) {
		translator.printResults(translationContext, exportName);
	}
}