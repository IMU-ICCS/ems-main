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
import camel.core.CorePackage;
import eu.melodic.event.translate.TranslationContext;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;
import eu.melodic.event.translate.properties.RuleTemplateProperties;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

@Slf4j
public class CamelToEplTranslatorCdo extends AbstractCamelTranslator {

	private CDOClientX cdoClient;

	public CamelToEplTranslatorCdo(ApplicationContext applicationContext, CamelToEplTranslatorProperties properties, RuleTemplateProperties ruleTemplates) {
		super(applicationContext, properties, ruleTemplates);
	}

	private void initCdoClient() {
		if (cdoClient!=null) return;
		this.cdoClient = new CDOClientXImpl(Arrays.asList(CorePackage.eINSTANCE, CpPackage.eINSTANCE));
		log.debug("CamelToEplTranslatorCdo.initCdoClient():  Initialized cdo-client");
	}

	// ================================================================================================================
	// Public API

	public TranslationContext translate(String camelModelPath) {
		log.debug("CamelToEplTranslator.translate():  BEGIN: camel-model-path: {}", camelModelPath);
		initCdoClient();
		CDOSessionX session = null;
		CDOView view = null;
		try {
			// Open CDO session
			session = cdoClient.getSession();
			view = session.openView();
			
			// Retrieve CAMEL and CP models
			log.debug("CamelToEplTranslator.translate():  Retrieving models...");
			CamelModel camelModel = null;
			if (!StringUtils.isBlank(camelModelPath)) {
				CDOResource camelModelRes = view.getResource(camelModelPath);
				EList<EObject> contents = camelModelRes.getContents();
				camelModel = (CamelModel) contents.get(contents.size()-1);
				log.debug("CamelToEplTranslator.translate():  Retrieved CAMEL model at path: {}", camelModelPath);
			}
			
			// Process models
			return translate(camelModel);
			
		} catch (Exception ex) {
			log.error("CamelToEplTranslator.translate(): EXCEPTION: ", ex);
			throw new RuntimeException(ex);
		} finally {
			if (view!=null) view.close();
			if (session!=null) session.closeSession();
			log.debug("CamelToEplTranslator.translate():  END: camel-model-path: {}", camelModelPath);
		}
	}
}