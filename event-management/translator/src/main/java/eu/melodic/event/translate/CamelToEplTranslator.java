/*
 * Copyright (C) 2017 Institute of Communication and Computer Systems (imu.iccs.com)
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.event.translate;

import camel.core.CamelModel;
import camel.core.CorePackage;
import camel.core.NamedElement;

import eu.melodic.event.translate.analyze.*;
import eu.melodic.event.translate.generate.*;
import eu.melodic.event.translate.transform.*;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;
import eu.melodic.event.translate.properties.RuleTemplateProperties;

import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

import org.eclipse.emf.cdo.eresource.CDOResource;
//import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service("CamelToEplTranslator")
public class CamelToEplTranslator implements Translator {
	
	@Autowired
	private CamelToEplTranslatorProperties properties;
	@Autowired
	private RuleTemplateProperties ruleTemplatesRegistry;
	@Value("${translator.leaf-node-grouping}")
	private String leafGrouping;
	
	private CDOClientX cdoClient;
	
	public CamelToEplTranslator() {
        this.cdoClient = new CDOClientXImpl(Arrays.asList(CorePackage.eINSTANCE, CpPackage.eINSTANCE));
		log.debug("CamelToEplTranslator.<init>():  Initialized cdo-client");
	}
	
	public CamelToEplTranslator(CDOClientX client) {
		if (client==null) throw new NullPointerException("CamelToEplTranslator(CDOClientX) : Argument cannot be null");
        this.cdoClient = client;
		log.debug("CamelToEplTranslator.<init>():  Set cdo-client");
	}
	
	// ================================================================================================================
	// Public API
	
	public TranslationContext translate(String camelId) {
		log.debug("CamelToEplTranslator.translate():  BEGIN: camel-model-id={}", camelId);
		CDOSessionX session = null;
		CDOView view = null;
		try {
			// Open CDO session
			session = cdoClient.getSession();
			view = session.openView();
			
			// Retrieve CAMEL and CP models
			log.debug("CamelToEplTranslator.translate():  Retrieving models...");
			CamelModel camelModel = null;
			if (camelId!=null && !camelId.trim().isEmpty()) {
				CDOResource camelModelRes = view.getResource(camelId);
				EList<EObject> contents = camelModelRes.getContents();
				camelModel = (CamelModel) contents.get(contents.size()-1);
				log.debug("CamelToEplTranslator.translate():  Retrieved CAMEL model: id={}", camelId);
			}
			
			// Process models
			return translate(camelModel);
			
		} catch (Exception ex) {
			log.error("CamelToEplTranslator.translate(): EXCEPTION: ", ex);
			throw new RuntimeException(ex);
		} finally {
			if (view!=null) view.close();
			if (session!=null) session.closeSession();
			log.debug("CamelToEplTranslator.translate():  END: camel-model-id={}", camelId);
		}
	}
	
	public TranslationContext translate(CamelModel camelModel) {
		log.debug("CamelToEplTranslator.translate():  BEGIN: camel-model={}", camelModel);
		if (camelModel==null) log.warn("CamelToEplTranslator.translate():  No CAMEL model specified");
		
		// initialize data structures
		TranslationContext _TC = new TranslationContext();
		
		// analyze scalability rules and metric expressions
		log.debug("CamelToEplTranslator.translate():  Analyzing models...");
		ModelAnalyzer modelAnalyzer = new ModelAnalyzer();
		modelAnalyzer.analyzeModel(_TC, leafGrouping, camelModel, properties);
		log.debug("CamelToEplTranslator.translate():  Analyzing models... done");
		
		// transform graph
		log.debug("CamelToEplTranslator.translate():  Transforming DAG...");
		GraphTransformer transformer = new GraphTransformer();
		transformer.transformGraph(_TC.DAG, properties);
		log.debug("CamelToEplTranslator.translate():  Transforming DAG... done");
		
		// generate EPL rules
		log.debug("CamelToEplTranslator.translate():  Generating EPL rules...");
		RuleGenerator generator = new RuleGenerator(ruleTemplatesRegistry);
		generator.generateRules(_TC);
		log.debug("CamelToEplTranslator.translate():  Generating EPL rules... done");
		
		printAnalysisResults(_TC, camelModel!=null ? camelModel.getName() : "");
		
		log.debug("CamelToEplTranslator.translate():  END: result={}", _TC);
		return _TC;
	}
	
	// ================================================================================================================
	// Helper methods
	
	public void printAnalysisResults(TranslationContext _TC, String exportName) {
		// Print analysis results
		log.info("*********************************************************");
		log.info("****         A N A L Y S I S   R E S U L T S         ****");
		log.info("*********************************************************");
		log.info("*********************************************************");
		log.info("Decomposition Graph:\n{}", _TC.DAG);
		log.info("*********************************************************");
		try {
			String dot = _TC.DAG.exportToDot();
			log.info("Decomposition Graph in DOT format:\n{}", dot);
		} catch (Exception ex) {
			log.error("Decomposition Graph in DOT format: EXCEPTION: ", ex);
		}
		log.info("*********************************************************");
		try {
//XXX: OPTIONAL: get export file name pattern from configuration
			// Get graph export configuration
			String exportPath = properties.getExportPath();
			String[] exportFormats = properties.getExportFormats();
			int imageWidth = properties.getExportImageWidth();
			
			// Get base name and path of export files
			if (exportPath==null) exportPath = "";
			exportName = (exportName==null || exportName.trim().isEmpty()) ? "" : exportName.trim();
			String baseFileName = String.format("%s/%s%s%d", exportPath, exportName, exportName.isEmpty()?"":"-", System.currentTimeMillis());
			_TC.DAG.exportDAG(baseFileName, exportFormats, imageWidth);
			//log.info("Decomposition Graph export to file(s): ok");
		} catch (Exception ex) {
			log.error("Decomposition Graph export to file(s): EXCEPTION: ", ex);
		}
		log.info("*********************************************************");
		log.info("Event-to-Action map:\n{}", map2string( _TC.E2A ));
		log.info("*********************************************************");
		log.info("SLO set:\n{}", _TC.SLO );
		log.info("*********************************************************");
		log.info("Component-to-Sensor map:\n{}", map2string( _TC.C2S ));
		log.info("*********************************************************");
		log.info("Data-to-Sensor map:\n{}", map2string( _TC.D2S ));
		log.info("*********************************************************");
		log.info("Grouping-to-EPL Rules map:\n{}", _TC.G2R);
		log.info("*********************************************************");
		log.info("Grouping-to-Topics map:\n{}", _TC.G2T);
		log.info("*********************************************************");
		log.info("Topics-Connections map:\n{}", _TC.getTopicConnections());
		log.info("*********************************************************");
		log.info("Metric-to-Metric Context map:\n{}", map2string(_TC.M2MC));
		log.info("*********************************************************");
		log.info("MVV set:\n{}", _TC.MVV);
		log.info("*********************************************************");
		log.info("Function Definitions set:\n{}", getElementNames(_TC.FUNC));
		log.info("*********************************************************");
	}
	
	protected Map<String,List<String>> map2string(Map map) {
		Map<String,List<String>> newMap = new HashMap<>();
		for (Object key : map.keySet()) {
			Set values = (Set) map.get(key);
			ArrayList<String> list = new ArrayList<String>();
			if (key==null) {
				newMap.put( key+"::"+key, list );
			} else
			if (key instanceof NamedElement) {
				newMap.put( key.getClass().getSimpleName()+"::"+((NamedElement)key).getName(), list );
			} else {
				newMap.put( key.getClass().getSimpleName()+"::"+key, list );
			}
			for (Object val : values) {
				if (val instanceof NamedElement) {
					list.add( val.getClass().getSimpleName()+"::"+((NamedElement)val).getName() );
				} else {
					list.add( val.getClass().getSimpleName()+"::"+val );
				}
			}
		}
		return newMap;
	}
	
	protected Collection<String> getElementNames(Collection col) {
		ArrayList<String> names = new ArrayList<>();
		for (Object elem : col) {
			if (elem!=null && NamedElement.class.isInstance(elem)) {
				names.add( ((NamedElement)elem).getName() );
			}
		}
		return names;
	}
}