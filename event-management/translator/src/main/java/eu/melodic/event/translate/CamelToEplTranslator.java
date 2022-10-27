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
import camel.core.CorePackage;
import camel.core.NamedElement;
import eu.melodic.event.util.FunctionDefinition;
import eu.melodic.event.translate.analyze.ModelAnalyzer;
import eu.melodic.event.translate.generate.RuleGenerator;
import eu.melodic.event.translate.properties.CamelToEplTranslatorProperties;
import eu.melodic.event.translate.properties.RuleTemplateProperties;
import eu.melodic.event.translate.transform.GraphTransformer;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service("CamelToEplTranslator")
@NoArgsConstructor
public class CamelToEplTranslator implements Translator {
	
	@Autowired
	private ApplicationContext applicationContext;
	@Autowired
	private CamelToEplTranslatorProperties properties;
	@Autowired
	private RuleTemplateProperties ruleTemplatesRegistry;

	private CDOClientX cdoClient;
	
	public CamelToEplTranslator(CDOClientX client) {
		this.cdoClient = Objects.requireNonNull(client, "CamelToEplTranslator(CDOClientX) : Argument cannot be null");
		log.debug("CamelToEplTranslator.<init>():  Set cdo-client");
	}

	private void initCdoClient() {
		if (cdoClient!=null) return;
		this.cdoClient = new CDOClientXImpl(Arrays.asList(CorePackage.eINSTANCE, CpPackage.eINSTANCE));
		log.debug("CamelToEplTranslator.initCdoClient():  Initialized cdo-client");
	}

	// ================================================================================================================
	// Public API

	public TranslationContext translate(String camelId) {
		log.debug("CamelToEplTranslator.translate():  BEGIN: camel-model-id={}", camelId);
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
			if (!StringUtils.isBlank(camelId)) {
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
		ModelAnalyzer modelAnalyzer = applicationContext.getBean(ModelAnalyzer.class);
		modelAnalyzer.analyzeModel(_TC, camelModel);
		log.debug("CamelToEplTranslator.translate():  Analyzing models... done");
		
		// transform graph
		log.debug("CamelToEplTranslator.translate():  Transforming DAG...");
		GraphTransformer transformer = applicationContext.getBean(GraphTransformer.class);
		transformer.transformGraph(_TC.DAG);
		log.debug("CamelToEplTranslator.translate():  Transforming DAG... done");
		
		// generate EPL rules
		log.debug("CamelToEplTranslator.translate():  Generating EPL rules...");
		RuleGenerator generator = applicationContext.getBean(RuleGenerator.class);
		generator.generateRules(_TC);
		log.debug("CamelToEplTranslator.translate():  Generating EPL rules... done");

		// print results
		printResults(_TC, camelModel != null ? camelModel.getName() : "");

		log.debug("CamelToEplTranslator.translate():  END: result={}", _TC);
		return _TC;
	}
	
	// ================================================================================================================
	// Helper methods
	
	public void printResults(TranslationContext _TC, String exportName) {
		if (! properties.isPrintResults()) {
			log.debug("CamelToEplTranslator.printResults(): Translation results printing is disabled");
			return;
		}

		// Print analysis results
		log.info("*********************************************************");
		log.info("****      T R A N S L A T I O N   R E S U L T S      ****");
		log.info("*********************************************************");
		log.info("");

		// Print DAG
		String dot = null;
		if (properties.getDag().isExportToDotEnabled()) {
			log.info("Decomposition Graph:\n{}", _TC.DAG);
			log.info("*********************************************************");
			try {
				dot = _TC.DAG.exportToDot();
				log.info("Decomposition Graph in DOT format:\n{}", dot);
			} catch (Exception ex) {
				log.error("Decomposition Graph in DOT format: EXCEPTION: ", ex);
			}
		}
		// Export DAG to files
		if (properties.getDag().isExportToFileEnabled()) {
			log.info("*********************************************************");
			log.info("Decomposition Graph export to file(s)");
			try {
				// Get graph export configuration
				String exportPath = properties.getDag().getExportPath();
				String[] exportFormats = properties.getDag().getExportFormats();
				int imageWidth = properties.getDag().getExportImageWidth();

				// Get base name and path of export files
				if (exportPath == null) exportPath = "";
				exportName = StringUtils.stripToEmpty(exportName);
				if (exportName.isEmpty()) exportName = "noname";
				String baseFileName = String.format("%s/%s-%d", exportPath, exportName, System.currentTimeMillis());
				if (dot!=null) {
					_TC.DAG.exportDAG(dot, baseFileName, exportFormats, imageWidth);
				} else {
					_TC.DAG.exportDAG(baseFileName, exportFormats, imageWidth);
				}
				//log.info("Decomposition Graph export to file(s): ok");
			} catch (Exception ex) {
				log.error("Decomposition Graph export to file(s): EXCEPTION: ", ex);
			}
		}

		// Print other translation results
		log.info("*********************************************************");
		log.info("Event-to-Action map:\n{}", map2string( _TC.E2A ));
		log.info("*********************************************************");
		log.info("SLO set:\n{}", _TC.SLO );
		log.info("*********************************************************");
		log.info("Component-to-Sensor map:\n{}", map2string( _TC.C2S ));
		log.info("*********************************************************");
		log.info("Data-to-Sensor map:\n{}", map2string( _TC.D2S ));
		log.info("*********************************************************");
		log.info("Grouping-to-EPL Rules map:\n{}", prettifyG2R(_TC.G2R, ""));
		log.info("*********************************************************");
		log.info("Grouping-to-Topics map:\n{}", _TC.G2T);
		log.info("*********************************************************");
		log.info("Topics-Connections map:\n{}", _TC.getTopicConnections());
		log.info("*********************************************************");
		log.info("Metric-to-Metric Context map:\n{}", map2string(_TC.M2MC));
		log.info("*********************************************************");
		log.info("MVV set:\n{}", _TC.MVV);
		log.info("*********************************************************");
		log.info("MVV_CP map:\n{}", _TC.MVV_CP);
		log.info("*********************************************************");
		log.info("CMVAR set:\n{}", _TC.CMVAR);
		log.info("*********************************************************");
		log.info("Function Definitions set:\n{}", getFunctionNames(_TC.FUNC));
		log.info("*********************************************************");
		log.info("Metric Constraints:\n{}", _TC.getMetricConstraints());
		log.info("*********************************************************");
		log.info("Load-Annotated Metrics:\n{}", _TC.getLoadAnnotatedMetricsSet());
		log.info("*********************************************************");
	}

	public String prettifyG2R(Map<String, Map<String, Set<String>>> map, String startIdent) {
		StringBuilder sb = new StringBuilder();
		String ident2 = startIdent+"  ";
		String ident3 = startIdent+"    ";
		String ident4 = startIdent+"\n      ";
		map.forEach((groupingName, groupingTopics) -> {
			sb.append(startIdent).append("-----------------------\n");
			sb.append(startIdent).append(groupingName).append(": \n");
			groupingTopics.forEach((topicName, topicRules) -> {
				sb.append(ident2).append(topicName).append(": \n");
				topicRules.forEach(
						ruleStr -> {
							ruleStr = ruleStr
									.replace("\r\n", ident4)
									.replace("\n", ident4);
							sb.append(ident3).append("- ").append(ruleStr).append("\n");
						}
				);
			});
		});
		return sb.toString();
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
	
	protected Collection<String> getFunctionNames(Collection<FunctionDefinition> col) {
		return col.stream()
				.map(FunctionDefinition::getName)
				.collect(Collectors.toList());
	}
}