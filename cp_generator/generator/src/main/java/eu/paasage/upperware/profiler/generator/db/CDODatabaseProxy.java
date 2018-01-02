/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */

package eu.paasage.upperware.profiler.generator.db;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentPackage;
import eu.paasage.camel.organisation.OrganisationPackage;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.provider.ProviderPackage;
import eu.paasage.camel.type.*;
import eu.paasage.camel.type.impl.EnumerationImpl;
import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.typesPaasage.*;
import eu.paasage.upperware.profiler.cp.generator.model.tools.Constants;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;
import fr.inria.paasage.saloon.camel.mapping.MappingPackage;
import fr.inria.paasage.saloon.camel.ontology.OntologyPackage;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.List;

import static eu.passage.upperware.commons.MelodicConstants.*;

/**
 * Database proxy for CDO
 */
@Slf4j
//@Service
public class CDODatabaseProxy implements IDatabaseProxy {

	private CPCloner cloner;
	private CDOClientExtended cdoClient;

	@Autowired
	public CDODatabaseProxy(CDOClientExtended cdoClient) {
		this.cdoClient = cdoClient;
		cloner = new CPCloner();

		registerPackages();
	}

	/**
	 * Registers the package of the used models in the database
	 */
	private void registerPackages() {
		cdoClient.registerPackage(ApplicationPackage.eINSTANCE);
		cdoClient.registerPackage(CpPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPackage.eINSTANCE);
		cdoClient.registerPackage(TypesPaasagePackage.eINSTANCE);
		cdoClient.registerPackage(OntologyPackage.eINSTANCE);
		cdoClient.registerPackage(MappingPackage.eINSTANCE);
		cdoClient.registerPackage(TypePackage.eINSTANCE);
		cdoClient.registerPackage(CamelPackage.eINSTANCE);
		cdoClient.registerPackage(ProviderPackage.eINSTANCE);
		cdoClient.registerPackage(OrganisationPackage.eINSTANCE);
		cdoClient.registerPackage(DeploymentPackage.eINSTANCE);
	}

	public CamelModel getCamelModel(String modelPath) {
		List<EObject> camelModels = cloner.cloneModel(modelPath);
		CamelModel camelModel = (CamelModel) getLastElement(camelModels);
		if (camelModel != null) {
			EList<ProviderModel> providerModels = camelModel.getProviderModels();
			for (ProviderModel providerModel : providerModels) {
				populateFields(providerModel, camelModel.getTypeModels());
			}
		}

		return camelModel;
	}

	private EObject getLastElement(List<EObject> collection){
		return CollectionUtils.isNotEmpty(collection) ? collection.get(collection.size()-1) : null;
	}

	public ProviderModel loadPM(String cloud) {
		//Try to load the Model from CDO if it exists
		if(cdoClient.existResource(FMS_APP_CDO_SERVER_PATH+cloud)) {
			List<EObject> cloned= cloner.cloneModel(FMS_APP_CDO_SERVER_PATH+cloud);
			CamelModel pm= (CamelModel) cloned.get(cloned.size()-1); //We pick the last added model. It is necessary as it is not possible to delete models from CDO.
			log.debug("CDODatabaseProxy- The PM "+cloud+" has been cloaned!");

			ProviderModel providerModel = pm.getProviderModels().get(0);
			populateFields(providerModel, pm.getTypeModels());

			return providerModel;
		}
		return null;
	}

	//TODO - remove in the future
	private void populateFields(ProviderModel providerModel, EList<TypeModel> typeModels) {
		populateField(providerModel, typeModels, ATTRIB_LOCATION, ATTRIB_LOCATION_ID);
		populateField(providerModel, typeModels, ATTRIB_VM, ATTRIB_VM_IMAGE_ID);
	}

	private void populateField(ProviderModel providerModel, EList<TypeModel> typeModels, String subFeatureName, String attributeName){
		Attribute type = getType(providerModel, subFeatureName, attributeName);

		if (type == null) {
			log.warn("Could not find attributes for subfeature {} and attribute {}", subFeatureName, attributeName);
			return;
		}

		String valueStr = null;
		SingleValue value = type.getValue();
		if (value != null && value instanceof StringsValue ){
			valueStr = ((StringsValue) value).getValue();
		}

		if (StringUtils.isEmpty(valueStr)){
			log.warn("Could not find String value for attribute {}", type.getName());
			return;
		}

		ValueType valueType = type.getValueType();
		if (valueType != null) {
			List<EnumerateValue> enumerationValues = getEnumerationValues(typeModels, type.getValueType().getName());
			EnumerateValue correctEnumerationValue = getCorrectEnumerationValue(enumerationValues, valueStr);

			if (correctEnumerationValue != null){
				type.setValue(correctEnumerationValue);
			} else {
				log.warn("Could not find String value for subfeature {} and attribute {}", subFeatureName, attributeName);
			}
		} else {
			log.info("Value for subfeature {} and attribute {} are set as it is", subFeatureName, attributeName);
		}

	}

	private Attribute getType(ProviderModel providerModel, String subFeatureName, String attributeName) {

		return providerModel.getRootFeature()
				.getSubFeatures()
				.stream()
				.filter(feature -> subFeatureName.equals(feature.getName()))
				.map(Feature::getAttributes)
				.flatMap(Collection::stream)
				.filter(attribute -> attributeName.equals(attribute.getName()))
				.findFirst().orElse(null);
	}

	private List<EnumerateValue> getEnumerationValues(EList<TypeModel> typeModels, String dataTypeName) {

		ValueType valueType = typeModels
				.stream()
				.map(TypeModel::getDataTypes)
				.flatMap(Collection::stream)
				.filter(vt -> dataTypeName.equals(vt.getName()))
				.findFirst().orElse(null);

		if (valueType != null && valueType instanceof EnumerationImpl){
			return ((EnumerationImpl) valueType).getValues();
		} else {
			log.warn("Could not find values for {} ", dataTypeName);
		}
		return new ArrayList<>();
	}

	private EnumerateValue getCorrectEnumerationValue(List<EnumerateValue> enumerationValues, String strValue){
		return enumerationValues.stream()
				.filter(enumerateValue -> enumerateValue.getName().equals(strValue))
				.findFirst().orElse(null);
	}

	/**
	 * Loads a provider model
	 *
	 * @param appId      The paasage id
	 * @param providerId The provider id related to the provider model
	 * @return The provider model
	 */
	public ProviderModel loadPM(String appId, String providerId) {

		List<EObject> res = cloner.cloneModel(FMS_APP_CDO_SERVER_PATH + getFMResourceId(appId, providerId));
		CamelModel pm = (CamelModel)getLastElement(res);

		log.debug("CDODatabaseProxy- loadPM- PM " + pm.getProviderModels().get(0).getRootFeature().getName());

		return pm.getProviderModels().get(0);
	}

	/**
	 * Loads a provider model
	 *
	 * @param appId      The application id
	 * @param providerId The provider id related to the provider model
	 * @param vmId       The virtual machine identifier
	 * @return The provider model
	 */
	//TODO - is this used anywhere?
	public ProviderModel loadPM(String appId, String providerId, String vmId) {
		List<EObject> res = cloner.cloneModel(FMS_APP_CDO_SERVER_PATH + getFMResourceId(appId, providerId));

		//The Name of the camel model is the Id of the VM

		CamelModel pm = null;

		log.debug("CDODatabaseProxy- loadPM- VMId " + vmId);

		for (EObject r : res) {
			CamelModel provider = (CamelModel) r;

			log.debug("CDODatabaseProxy- loadPM- Camel Name " + provider.getName());

			if (vmId.contains(provider.getName())) {
				pm = provider;
				break;
			}
		}


		log.debug("CDODatabaseProxy- loadPM- PM " + pm);

		return pm.getProviderModels().get(0);
	}

	/**
	 * Load the function types from the CDO database
	 */
	public FunctionTypes loadFunctionTypes() {
		log.debug("Loading FunctionTypes");
		return loadFromCdo(FUNCTION_TYPES_ID);
	}

	/**
	 * Loads the operating systems from the CDO database
	 */
	public OperatingSystems loadOperatingSystems() {
		log.debug("Loading OperatingSystems");
		return loadFromCdo(OPERATING_SYSTEMS_ID);
	}

	/**
	 * Load locations from the CDO database
	 */
	public Locations loadLocations() {
		log.debug("Loading Locations");
		return loadFromCdo(LOCATIONS_ID);
	}

	/**
	 * Loads the provider types.
	 */
	public ProviderTypes loadProviderTypes() {
		log.debug("Loading ProviderTypes");
		return loadFromCdo(PROVIDER_TYPES_ID);
	}

	@SuppressWarnings("unchecked")
	private <T extends EObject> T loadFromCdo(String cdoName) {
		List<EObject> providerTypesList = getResourceWithID(cdoName);
		if (!CollectionUtils.isEmpty(providerTypesList)) {
			log.info("Getting resource for {} path from CDO", cdoName);
			return (T) getLastElement(providerTypesList);
		}
		log.warn("Empty resource for {} path.", cdoName);
		return null;
	}

	/**
	 * Retrieves the resource from the CDO database with the specified id
	 * @param id Id of the resource
	 * @return The list of EObject relagted to the resource.
	 */
	protected List<EObject> getResourceWithID(String id) {
		List<EObject> result= null;

		if(cdoClient.existResource(id)) {
			try{
//				result = cloner.cloneModel(id);
				result= cdoClient.getResourceContents(id);
				log.info("CDODatabaseProxy - getResourceWithID - The resource "+ id+" loaded");
			} catch(org.eclipse.emf.cdo.util.InvalidURIException ex) {
				log.info("CDODatabaseProxy - getResourceWithID - The resource "+ id+" does not exist");
			}
		}
		return result;
	}

	public void saveModels(PaasageConfiguration pc, ConstraintProblem cp) {
		String pcId= cp.getId();

		log.debug("CDODatabaseProxy - saveModels - Storing Models ");
		String cpPath = CDO_SERVER_PATH + pcId;

		cdoClient.storeModels(Arrays.asList(pc, cp), cpPath);
		log.debug("CDODatabaseProxy - saveModels - Models stored! ");

		File paasageConfigurationDir= PaasageModelTool.getGenerationDirForPaasageAppConfiguration(pcId);
		File paasageConfigModel= new File(paasageConfigurationDir, Constants.PAASAGE_CONFIGURATION_MODEL_FILE_NAME);
		File cpModel= new File(paasageConfigurationDir, Constants.CP_MODEL_FILE_NAME);

		List<EObject> content = cloner.cloneModel(cpPath);

		log.debug("CDODatabaseProxy - saveModels - Saving file "+paasageConfigModel.getAbsolutePath());
		cdoClient.exportModel(content.get(0), paasageConfigModel.getAbsolutePath());
		cdoClient.exportModel(content.get(1), cpModel.getAbsolutePath());
	}

	public void savePM(ProviderModel pm, String paasageConfigurationId, String providerId) {
		String providerResourceId = getFMResourceId(paasageConfigurationId, providerId);
		log.info("CDODatabaseProxy - savePM - Saving PM Configuration Id {} Provider id {} under id: {}", paasageConfigurationId, providerId, providerResourceId);
		cdoClient.storeModel(pm.eContainer(), FMS_APP_CDO_SERVER_PATH+ providerResourceId);
		log.info("CDODatabaseProxy - savePM - Saving PM Configuration Id {} Provider id {} under id: {} - saved", paasageConfigurationId, providerId, providerResourceId);
	}

	private String getFMResourceId(String paasageConfigurationId, String providerId) {
		return paasageConfigurationId+"/"+providerId;
	}

}
