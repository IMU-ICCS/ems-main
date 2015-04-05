package eu.paasage.camel.examples;

import java.util.Date;

import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.net4j.CDONet4jSession;
import org.eclipse.emf.cdo.net4j.CDONet4jSessionConfiguration;
import org.eclipse.emf.cdo.net4j.CDONet4jUtil;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.util.ConcurrentAccessException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.net4j.Net4jUtil;
import org.eclipse.net4j.connector.IConnector;
import org.eclipse.net4j.tcp.TCPUtil;
import org.eclipse.net4j.util.container.ContainerUtil;
import org.eclipse.net4j.util.container.IManagedContainer;

import eu.paasage.camel.ActionType;
import eu.paasage.camel.Application;
import eu.paasage.camel.CamelFactory;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.unit.MonetaryUnit;
import eu.paasage.camel.requirement.LocationRequirement;
import eu.paasage.camel.requirement.OSRequirement;
import eu.paasage.camel.requirement.ProviderRequirement;
import eu.paasage.camel.requirement.QuantitativeHardwareRequirement;
import eu.paasage.camel.requirement.RequirementFactory;
import eu.paasage.camel.requirement.RequirementGroup;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.requirement.RequirementOperatorType;
import eu.paasage.camel.unit.StorageUnit;
import eu.paasage.camel.unit.TimeIntervalUnit;
import eu.paasage.camel.unit.UnitDimensionType;
import eu.paasage.camel.unit.UnitFactory;
import eu.paasage.camel.unit.UnitType;
import eu.paasage.camel.deployment.Communication;
import eu.paasage.camel.deployment.CommunicationInstance;
import eu.paasage.camel.deployment.CommunicationType;
import eu.paasage.camel.deployment.Configuration;
import eu.paasage.camel.deployment.DeploymentFactory;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.Hosting;
import eu.paasage.camel.deployment.HostingInstance;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.ProvidedCommunication;
import eu.paasage.camel.deployment.ProvidedCommunicationInstance;
import eu.paasage.camel.deployment.ProvidedHost;
import eu.paasage.camel.deployment.ProvidedHostInstance;
import eu.paasage.camel.deployment.RequiredCommunication;
import eu.paasage.camel.deployment.RequiredCommunicationInstance;
import eu.paasage.camel.deployment.RequiredHost;
import eu.paasage.camel.deployment.RequiredHostInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.deployment.VMRequirementSet;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.Credentials;
import eu.paasage.camel.organisation.DataCenter;
import eu.paasage.camel.organisation.OrganisationFactory;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.AttributeConstraint;
import eu.paasage.camel.provider.FeatCardinality;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.Implies;
import eu.paasage.camel.provider.ProviderFactory;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.location.Country;
import eu.paasage.camel.location.LocationFactory;
import eu.paasage.camel.location.LocationModel;
import eu.paasage.camel.metric.ComparisonOperatorType;
import eu.paasage.camel.scalability.HorizontalScalingAction;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.LayerType;
import eu.paasage.camel.metric.CompositeMetric;
import eu.paasage.camel.metric.CompositeMetricContext;
import eu.paasage.camel.metric.CompositeMetricInstance;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricCondition;
import eu.paasage.camel.metric.MetricContext;
import eu.paasage.camel.metric.MetricFactory;
import eu.paasage.camel.metric.MetricFormula;
import eu.paasage.camel.metric.MetricFunctionArityType;
import eu.paasage.camel.metric.MetricFunctionType;
import eu.paasage.camel.metric.MetricInstance;
import eu.paasage.camel.metric.MetricModel;
import eu.paasage.camel.metric.MetricObjectBinding;
import eu.paasage.camel.metric.MetricType;
import eu.paasage.camel.metric.MetricVMBinding;
import eu.paasage.camel.metric.RawMetric;
import eu.paasage.camel.metric.RawMetricContext;
import eu.paasage.camel.metric.RawMetricInstance;
import eu.paasage.camel.scalability.NonFunctionalEvent;
import eu.paasage.camel.metric.Property;
import eu.paasage.camel.metric.PropertyType;
import eu.paasage.camel.scalability.ScalabilityFactory;
import eu.paasage.camel.scalability.ScalabilityModel;
import eu.paasage.camel.scalability.ScalabilityRule;
import eu.paasage.camel.metric.Sensor;
import eu.paasage.camel.scalability.VerticalScalingAction;
import eu.paasage.camel.requirement.VerticalScaleRequirement;
import eu.paasage.camel.type.EnumerateValue;
import eu.paasage.camel.type.Enumeration;
import eu.paasage.camel.type.FloatValue;
import eu.paasage.camel.type.IntValue;
import eu.paasage.camel.type.Limit;
import eu.paasage.camel.type.PositiveInf;
import eu.paasage.camel.type.Range;
import eu.paasage.camel.type.StringValue;
import eu.paasage.camel.type.TypeEnum;
import eu.paasage.camel.type.TypeFactory;


public class SensAppCDO {

	public static EObject getSensAppCamelModel() {
		// complete mapping of the SensApp example
		CamelModel camelModel = CamelFactory.eINSTANCE.createCamelModel();
		camelModel.setName("Sensapp Camel Model");
		EList<OrganisationModel> orgModels = camelModel.getOrganisationModels();

		Application sensAppApplication = CamelFactory.eINSTANCE
				.createApplication();
		// ///// START of definition of the Provider model

		ProviderModel providerModel = ProviderFactory.eINSTANCE
				.createProviderModel();
		providerModel.setName("Provider Model");

		Feature vmFeature = ProviderFactory.eINSTANCE.createFeature();
		vmFeature.setName("VM");
		FeatCardinality vmCardinality = ProviderFactory.eINSTANCE
				.createFeatCardinality();
		vmCardinality.setValue(1);
		vmCardinality.setCardinalityMin(1);
		vmCardinality.setCardinalityMax(8);
		vmFeature.setFeatureCardinality(vmCardinality);

		providerModel.setRootFeature(vmFeature);

		Attribute vmType = ProviderFactory.eINSTANCE.createAttribute();
		vmType.setName("vmType");

		Enumeration vmTypes = TypeFactory.eINSTANCE.createEnumeration();

		EnumerateValue smallVm = TypeFactory.eINSTANCE.createEnumerateValue();
		smallVm.setName("SMALL");
		smallVm.setValue(0);
		vmTypes.getValues().add(smallVm);

		EnumerateValue mediumVm = TypeFactory.eINSTANCE.createEnumerateValue();
		mediumVm.setName("MEDIUM");
		mediumVm.setValue(1);
		vmTypes.getValues().add(mediumVm);

		EnumerateValue largeVm = TypeFactory.eINSTANCE.createEnumerateValue();
		largeVm.setName("LARGE");
		largeVm.setValue(2);
		vmTypes.getValues().add(largeVm);

		vmType.setValueType(vmTypes);

		vmFeature.getAttributes().add(vmType);

		Attribute vmCPU = ProviderFactory.eINSTANCE.createAttribute();
		vmCPU.setName("vmCPU");
		Range vmCPURange = TypeFactory.eINSTANCE.createRange();

		vmCPURange.setPrimitiveType(TypeEnum.FLOAT_TYPE);

		Limit minCPU = TypeFactory.eINSTANCE.createLimit();
		minCPU.setIncluded(true);
		FloatValue minCPUValue = TypeFactory.eINSTANCE.createFloatValue();
		minCPUValue.setValue(1);
		minCPU.setValue(minCPUValue);

		Limit maxCPU = TypeFactory.eINSTANCE.createLimit();
		maxCPU.setIncluded(true);
		FloatValue maxCPUValue = TypeFactory.eINSTANCE.createFloatValue();
		maxCPUValue.setValue(5);
		maxCPU.setValue(maxCPUValue);

		vmCPURange.setLowerLimit(minCPU);
		vmCPURange.setUpperLimit(maxCPU);

		vmCPU.setValueType(vmCPURange);

		vmFeature.getAttributes().add(vmCPU);

		Attribute vmMemory = ProviderFactory.eINSTANCE.createAttribute();
		vmMemory.setName("vmMemory");

		Range vmMemoryRange = TypeFactory.eINSTANCE.createRange();

		vmMemoryRange.setPrimitiveType(TypeEnum.INT_TYPE);

		Limit minMemory = TypeFactory.eINSTANCE.createLimit();
		minMemory.setIncluded(true);
		IntValue minMemoryValue = TypeFactory.eINSTANCE.createIntValue();
		minMemoryValue.setValue(2048);
		minMemory.setValue(minMemoryValue);

		Limit maxMemory = TypeFactory.eINSTANCE.createLimit();
		maxMemory.setIncluded(true);
		IntValue maxMemoryValue = TypeFactory.eINSTANCE.createIntValue();
		maxMemoryValue.setValue(16384);
		maxMemory.setValue(maxMemoryValue);

		vmMemoryRange.setLowerLimit(minMemory);
		vmMemoryRange.setUpperLimit(maxMemory);

		vmMemory.setValueType(vmMemoryRange);

		vmFeature.getAttributes().add(vmMemory);

		Attribute vmStorage = ProviderFactory.eINSTANCE.createAttribute();
		vmStorage.setName("vmStorage");

		Range vmStorageRange = TypeFactory.eINSTANCE.createRange();

		vmStorageRange.setPrimitiveType(TypeEnum.INT_TYPE);

		Limit minStorage = TypeFactory.eINSTANCE.createLimit();
		minStorage.setIncluded(true);
		IntValue minStorageValue = TypeFactory.eINSTANCE.createIntValue();
		minStorageValue.setValue(200);
		minStorage.setValue(minStorageValue);

		Limit maxStorage = TypeFactory.eINSTANCE.createLimit();
		maxStorage.setIncluded(true);
		IntValue maxStorageValue = TypeFactory.eINSTANCE.createIntValue();
		maxStorageValue.setValue(2048);
		maxStorage.setValue(maxStorageValue);

		vmStorageRange.setLowerLimit(minStorage);
		vmStorageRange.setUpperLimit(maxStorage);

		vmStorage.setValueType(vmStorageRange);

		vmFeature.getAttributes().add(vmStorage);

		Attribute vmCores = ProviderFactory.eINSTANCE.createAttribute();
		vmCores.setName("vmCores");

		Range vmCoresRange = TypeFactory.eINSTANCE.createRange();

		vmCoresRange.setPrimitiveType(TypeEnum.INT_TYPE);

		Limit minCores = TypeFactory.eINSTANCE.createLimit();
		minCores.setIncluded(true);
		IntValue minCoresValue = TypeFactory.eINSTANCE.createIntValue();
		minCoresValue.setValue(1);
		minCores.setValue(minCoresValue);

		Limit maxCores = TypeFactory.eINSTANCE.createLimit();
		maxCores.setIncluded(true);
		IntValue maxCoresValue = TypeFactory.eINSTANCE.createIntValue();
		maxCoresValue.setValue(128);
		maxCores.setValue(maxCoresValue);

		vmCoresRange.setLowerLimit(minCores);
		vmCoresRange.setUpperLimit(maxCores);

		vmCores.setValueType(vmCoresRange);

		vmFeature.getAttributes().add(vmCores);

		Implies smallVmConstraint = ProviderFactory.eINSTANCE.createImplies();
		smallVmConstraint.setName("SMALL_VM_Type_Constraint");

		smallVmConstraint.setFrom(vmFeature);
		smallVmConstraint.setTo(vmFeature);

		AttributeConstraint smallVmCPUConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		smallVmCPUConstraint.setFrom(vmType);
		StringValue smallVmCPUConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		smallVmCPUConstraintFrom.setValue("SMALL");
		smallVmCPUConstraint.setFromValue(smallVmCPUConstraintFrom);

		smallVmCPUConstraint.setTo(vmCPU);
		FloatValue smallCPUConstraintTo = TypeFactory.eINSTANCE
				.createFloatValue();
		smallCPUConstraintTo.setValue(1);
		smallVmCPUConstraint.setToValue(smallCPUConstraintTo);

		smallVmConstraint.getAttributeConstraints().add(smallVmCPUConstraint);

		AttributeConstraint smallVmMemoryConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		smallVmMemoryConstraint.setFrom(vmType);
		StringValue smallVmMemoryConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		smallVmMemoryConstraintFrom.setValue("SMALL");
		smallVmMemoryConstraint.setFromValue(smallVmMemoryConstraintFrom);

		smallVmMemoryConstraint.setTo(vmMemory);
		IntValue smallMemoryConstraintTo = TypeFactory.eINSTANCE
				.createIntValue();
		smallMemoryConstraintTo.setValue(2048);
		smallVmMemoryConstraint.setToValue(smallMemoryConstraintTo);

		smallVmConstraint.getAttributeConstraints()
				.add(smallVmMemoryConstraint);

		AttributeConstraint smallVmStorageConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		smallVmStorageConstraint.setFrom(vmType);
		StringValue smallVmStorageConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		smallVmStorageConstraintFrom.setValue("SMALL");
		smallVmStorageConstraint.setFromValue(smallVmStorageConstraintFrom);

		smallVmStorageConstraint.setTo(vmStorage);
		IntValue smallVmStorageConstraintTo = TypeFactory.eINSTANCE
				.createIntValue();
		smallVmStorageConstraintTo.setValue(200);
		smallVmStorageConstraint.setToValue(smallVmStorageConstraintTo);

		smallVmConstraint.getAttributeConstraints().add(
				smallVmStorageConstraint);

		AttributeConstraint smallVmCoresConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		smallVmCoresConstraint.setFrom(vmType);
		StringValue smallVmCoresConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		smallVmCoresConstraintFrom.setValue("SMALL");
		smallVmCoresConstraint.setFromValue(smallVmCoresConstraintFrom);

		smallVmCoresConstraint.setTo(vmCores);
		IntValue smallVmCoresConstraintTo = TypeFactory.eINSTANCE
				.createIntValue();
		smallVmCoresConstraintTo.setValue(1);
		smallVmCoresConstraint.setToValue(smallVmCoresConstraintTo);

		smallVmConstraint.getAttributeConstraints().add(smallVmCoresConstraint);

		providerModel.getConstraints().add(smallVmConstraint);

		Implies mediumVmConstraint = ProviderFactory.eINSTANCE.createImplies();
		mediumVmConstraint.setName("MEDIUM_VM_Type_Constraint");

		mediumVmConstraint.setFrom(vmFeature);
		mediumVmConstraint.setTo(vmFeature);

		AttributeConstraint mediumVmCPUConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		mediumVmCPUConstraint.setFrom(vmType);
		StringValue mediumVmCPUConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		mediumVmCPUConstraintFrom.setValue("MEDIUM");
		mediumVmCPUConstraint.setFromValue(mediumVmCPUConstraintFrom);

		mediumVmCPUConstraint.setTo(vmCPU);
		FloatValue mediumCPUConstraintTo = TypeFactory.eINSTANCE
				.createFloatValue();
		mediumCPUConstraintTo.setValue(2);
		mediumVmCPUConstraint.setToValue(mediumCPUConstraintTo);

		mediumVmConstraint.getAttributeConstraints().add(mediumVmCPUConstraint);

		AttributeConstraint mediumVmMemoryConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		mediumVmMemoryConstraint.setFrom(vmType);
		StringValue mediumVmMemoryConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		mediumVmMemoryConstraintFrom.setValue("MEDIUM");
		mediumVmMemoryConstraint.setFromValue(mediumVmMemoryConstraintFrom);

		mediumVmMemoryConstraint.setTo(vmMemory);
		IntValue mediumMemoryConstraintTo = TypeFactory.eINSTANCE
				.createIntValue();
		mediumMemoryConstraintTo.setValue(4096);
		mediumVmMemoryConstraint.setToValue(mediumMemoryConstraintTo);

		mediumVmConstraint.getAttributeConstraints().add(
				mediumVmMemoryConstraint);

		AttributeConstraint mediumVmStorageConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		mediumVmStorageConstraint.setFrom(vmType);
		StringValue mediumVmStorageConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		mediumVmStorageConstraintFrom.setValue("MEDIUM");
		mediumVmStorageConstraint.setFromValue(mediumVmStorageConstraintFrom);

		mediumVmStorageConstraint.setTo(vmStorage);
		IntValue mediumVmStorageConstraintTo = TypeFactory.eINSTANCE
				.createIntValue();
		mediumVmStorageConstraintTo.setValue(512);
		mediumVmStorageConstraint.setToValue(mediumVmStorageConstraintTo);

		mediumVmConstraint.getAttributeConstraints().add(
				mediumVmStorageConstraint);

		AttributeConstraint mediumVmCoresConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		mediumVmCoresConstraint.setFrom(vmType);
		StringValue mediumVmCoresConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		mediumVmCoresConstraintFrom.setValue("MEDIUM");
		mediumVmCoresConstraint.setFromValue(mediumVmCoresConstraintFrom);

		mediumVmCoresConstraint.setTo(vmCores);
		IntValue mediumVmCoresConstraintTo = TypeFactory.eINSTANCE
				.createIntValue();
		mediumVmCoresConstraintTo.setValue(6);
		mediumVmCoresConstraint.setToValue(mediumVmCoresConstraintTo);

		mediumVmConstraint.getAttributeConstraints().add(
				mediumVmCoresConstraint);

		providerModel.getConstraints().add(mediumVmConstraint);

		Implies largeVmConstraint = ProviderFactory.eINSTANCE.createImplies();
		largeVmConstraint.setName("LARGE_VM_Type_Constraint");
		
		largeVmConstraint.setFrom(vmFeature);
		largeVmConstraint.setTo(vmFeature);

		AttributeConstraint largeVmCPUConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		largeVmCPUConstraint.setFrom(vmType);
		StringValue largeVmCPUConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		largeVmCPUConstraintFrom.setValue("LARGE");
		largeVmCPUConstraint.setFromValue(largeVmCPUConstraintFrom);

		largeVmCPUConstraint.setTo(vmCPU);
		FloatValue largeCPUConstraintTo = TypeFactory.eINSTANCE
				.createFloatValue();
		largeCPUConstraintTo.setValue((float) 3.2);
		largeVmCPUConstraint.setToValue(largeCPUConstraintTo);

		largeVmConstraint.getAttributeConstraints().add(largeVmCPUConstraint);

		AttributeConstraint largeVmMemoryConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		largeVmMemoryConstraint.setFrom(vmType);
		StringValue largeVmMemoryConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		largeVmMemoryConstraintFrom.setValue("LARGE");
		largeVmMemoryConstraint.setFromValue(largeVmMemoryConstraintFrom);

		largeVmMemoryConstraint.setTo(vmMemory);
		IntValue largeMemoryConstraintTo = TypeFactory.eINSTANCE
				.createIntValue();
		largeMemoryConstraintTo.setValue(8192);
		largeVmMemoryConstraint.setToValue(largeMemoryConstraintTo);

		largeVmConstraint.getAttributeConstraints()
				.add(largeVmMemoryConstraint);

		AttributeConstraint largeVmStorageConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		largeVmStorageConstraint.setFrom(vmType);
		StringValue largeVmStorageConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		largeVmStorageConstraintFrom.setValue("LARGE");
		largeVmStorageConstraint.setFromValue(largeVmStorageConstraintFrom);

		largeVmStorageConstraint.setTo(vmStorage);
		IntValue largeVmStorageConstraintTo = TypeFactory.eINSTANCE
				.createIntValue();
		largeVmStorageConstraintTo.setValue(2048);
		largeVmStorageConstraint.setToValue(largeVmStorageConstraintTo);

		largeVmConstraint.getAttributeConstraints().add(
				largeVmStorageConstraint);

		AttributeConstraint largeVmCoresConstraint = ProviderFactory.eINSTANCE
				.createAttributeConstraint();
		largeVmCoresConstraint.setFrom(vmType);
		StringValue largeVmCoresConstraintFrom = TypeFactory.eINSTANCE
				.createStringValue();
		largeVmCoresConstraintFrom.setValue("LARGE");
		largeVmCoresConstraint.setFromValue(largeVmCoresConstraintFrom);

		largeVmCoresConstraint.setTo(vmCores);
		IntValue largeVmCoresConstraintTo = TypeFactory.eINSTANCE
				.createIntValue();
		largeVmCoresConstraintTo.setValue(12);
		largeVmCoresConstraint.setToValue(largeVmCoresConstraintTo);

		largeVmConstraint.getAttributeConstraints().add(largeVmCoresConstraint);

		providerModel.getConstraints().add(largeVmConstraint);

		// ///// END definition of Provider model

		camelModel.getProviderModels().add(providerModel);

		// //// BEGIN definition of Amazon Organization model

		OrganisationModel amazonOrgModel = OrganisationFactory.eINSTANCE
				.createOrganisationModel();
		LocationModel lm = LocationFactory.eINSTANCE.createLocationModel();
		lm.setName("Location Model");
		camelModel.getLocationModels().add(lm);
		Country ireland = LocationFactory.eINSTANCE.createCountry();
		ireland.setName("Ireland");
		ireland.setAbbreviation("IE");
		lm.getCountries().add(ireland);
		Country scotland = LocationFactory.eINSTANCE.createCountry();
		scotland.setName("Scotland");
		scotland.setAbbreviation("SC");
		lm.getCountries().add(scotland);
		Country norway = LocationFactory.eINSTANCE.createCountry();
		norway.setName("Norway");
		norway.setAbbreviation("NO");
		lm.getCountries().add(norway);
		amazonOrgModel.setName("Amazon Organisation Model");
		EList<DataCenter> amazonDCs = amazonOrgModel.getDataCentres();

		CloudProvider amazonProvider = OrganisationFactory.eINSTANCE
				.createCloudProvider();
		amazonProvider.setEmail("contact@amazon.com");
		amazonProvider.setIaaS(true);
		amazonProvider.setName("Amazon");
		amazonProvider.setPaaS(true);
		amazonProvider.setProviderModel(providerModel);
		amazonProvider.setPublic(true);
		amazonProvider.setSaaS(true);

		amazonOrgModel.setProvider(amazonProvider);

		DataCenter amazonEuDataCenter = OrganisationFactory.eINSTANCE
				.createDataCenter();
		amazonEuDataCenter.setCloudProvider(amazonProvider);
		amazonEuDataCenter.setCodeName("amazon-eu");
		amazonEuDataCenter.setLocation(ireland);
		amazonEuDataCenter.setName("European Amazon Data Centre");

		amazonDCs.add(amazonEuDataCenter);

		// //// END definition of Amazon Organisation model

		orgModels.add(amazonOrgModel);

		// //// START definition of Flexiant Organisation model

		OrganisationModel flexiantOrgModel = OrganisationFactory.eINSTANCE
				.createOrganisationModel();
		flexiantOrgModel.setName("Flexiant Organisation Model");
		EList<DataCenter> flexiantDCs = flexiantOrgModel.getDataCentres();

		CloudProvider flexiantProvider = OrganisationFactory.eINSTANCE
				.createCloudProvider();
		flexiantProvider.setEmail("contact@flexiant.com");
		flexiantProvider.setIaaS(true);
		flexiantProvider.setName("Flexiant");
		flexiantProvider.setPaaS(true);
		flexiantProvider.setProviderModel(providerModel);
		flexiantProvider.setPublic(true);
		flexiantProvider.setSaaS(false);

		flexiantOrgModel.setProvider(flexiantProvider);

		DataCenter flexiantEuDataCenter = OrganisationFactory.eINSTANCE
				.createDataCenter();
		flexiantEuDataCenter.setCloudProvider(flexiantProvider);
		flexiantEuDataCenter.setCodeName("flexiant");
		flexiantEuDataCenter.setLocation(scotland);
		flexiantEuDataCenter.setName("Flexiant Data Centre");

		flexiantDCs.add(flexiantEuDataCenter);

		// //// END definition of Flexiant Organisation model

		orgModels.add(flexiantOrgModel);

		// //// START definition of Sintef Nova Organisation model

		OrganisationModel sintefOrgModel = OrganisationFactory.eINSTANCE
				.createOrganisationModel();
		sintefOrgModel.setName("SINTEF Organisation Model");
		EList<DataCenter> sintefDCs = sintefOrgModel.getDataCentres();
		EList<User> sintefUsers = sintefOrgModel.getUsers();
		EList<Credentials> sintefCredentials = sintefOrgModel.getCredentials();

		User user1 = OrganisationFactory.eINSTANCE.createUser();
		user1.setEmail("user@sintef.no");
		user1.setFirstName("User1");
		user1.setLastName("User");

		sintefUsers.add(user1);

		Credentials user1AmazonCredentials = OrganisationFactory.eINSTANCE
				.createCredentials();
		user1AmazonCredentials.setCloudProvider(amazonProvider);
		sintefCredentials.add(user1AmazonCredentials);
		Credentials user1FlexiantCredentials = OrganisationFactory.eINSTANCE
				.createCredentials();
		user1FlexiantCredentials.setCloudProvider(flexiantProvider);
		sintefCredentials.add(user1FlexiantCredentials);

		CloudProvider sintefNovaProvider = OrganisationFactory.eINSTANCE
				.createCloudProvider();
		sintefNovaProvider.setEmail("contact@sintef.no");
		sintefNovaProvider.setIaaS(true);
		sintefNovaProvider.setName("Sintef-Nova");
		sintefNovaProvider.setPaaS(true);
		sintefNovaProvider.setProviderModel(providerModel);
		sintefNovaProvider.setPublic(false);
		sintefNovaProvider.setSaaS(false);

		sintefOrgModel.setProvider(sintefNovaProvider);

		DataCenter sintefDataCenter = OrganisationFactory.eINSTANCE
				.createDataCenter();
		sintefDataCenter.setCloudProvider(sintefNovaProvider);
		sintefDataCenter.setCodeName("nova");
		sintefDataCenter.setLocation(norway);
		sintefDataCenter.setName("Sintef Nova Data Centre");

		sintefDCs.add(sintefDataCenter);

		Credentials user1SintefNovaCredentials = OrganisationFactory.eINSTANCE
				.createCredentials();
		user1SintefNovaCredentials.setCloudProvider(sintefNovaProvider);
		sintefCredentials.add(user1SintefNovaCredentials);

		// //// END definition of Sintef Nova Organisation model

		orgModels.add(sintefOrgModel);

		// //// START definition of Deployment model

		DeploymentModel sensAppDeploymentModel = DeploymentFactory.eINSTANCE
				.createDeploymentModel();

		sensAppDeploymentModel.setName("SensApp");
		
		RequirementModel rm = RequirementFactory.eINSTANCE.createRequirementModel();
		rm.setName("SensAPP-Requirement Model");
		camelModel.getRequirementModels().add(rm);
		ProviderRequirement pr = RequirementFactory.eINSTANCE.createProviderRequirement();
		pr.setId("Provider_Requirements_SensApp");
		rm.getRequirements().add(pr);
		pr.getProviders().add(amazonProvider);
		pr.getProviders().add(flexiantProvider);
		pr.getProviders().add(sintefNovaProvider);
		VMRequirementSet globalReqs = DeploymentFactory.eINSTANCE.createVMRequirementSet();
		globalReqs.setName("Global_Reqs_Sens_App");
		globalReqs.setProviderRequirement(pr);
		OSRequirement osReq = RequirementFactory.eINSTANCE.createOSRequirement();
		osReq.setId("GLOBAL_OS_REQ");
		osReq.setIs64os(true);
		osReq.setOs("ubuntu");
		rm.getRequirements().add(osReq);
		globalReqs.setOsOrImageRequirement(osReq);
		sensAppDeploymentModel.setGlobalVMRequirementSet(globalReqs);
		sensAppDeploymentModel.getVmRequirementSets().add(globalReqs);

		InternalComponent sensApp = DeploymentFactory.eINSTANCE
				.createInternalComponent();
		sensApp.setName("SensApp");

		Configuration sensAppRes = DeploymentFactory.eINSTANCE
				.createConfiguration();
		sensAppRes
				.setDownloadCommand("wget -P ~ http://github.com/downloads/SINTEF-9012/sensapp/sensapp.war; wget -P ~ http://cloudml.org/scripts/linux/ubuntu/sensapp/install_start_sensapp.sh");
		sensAppRes
				.setInstallCommand("cd ~; sudo bash install_start_sensapp.sh");
		sensAppRes.setName("SensAppRes");
		sensApp.getConfigurations().add(sensAppRes);

		ProvidedCommunication restProv = DeploymentFactory.eINSTANCE
				.createProvidedCommunication();
		restProv.setOwner(sensApp);
		restProv.setName("RESTProv");
		restProv.setPortNumber(8080);

		sensApp.getProvidedCommunications().add(restProv);

		RequiredCommunication mongoDBReq = DeploymentFactory.eINSTANCE
				.createRequiredCommunication();
		mongoDBReq.setOwner(sensApp);
		mongoDBReq.setIsMandatory(true);
		mongoDBReq.setName("MongoDBReq");
		mongoDBReq.setPortNumber(0);

		sensApp.getRequiredCommunications().add(mongoDBReq);

		RequiredHost servletContainerSensAppReq = DeploymentFactory.eINSTANCE
				.createRequiredHost();
		servletContainerSensAppReq.setOwner(sensApp);
		servletContainerSensAppReq.setName("ServletContainerSensAppReq");

		sensApp.setRequiredHost(servletContainerSensAppReq);

		sensAppDeploymentModel.getInternalComponents().add(sensApp);

		InternalComponent mongoDB = DeploymentFactory.eINSTANCE
				.createInternalComponent();
		mongoDB.setName("MongoDB");

		Configuration mongoDBRes = DeploymentFactory.eINSTANCE
				.createConfiguration();
		mongoDBRes
				.setDownloadCommand("wget -P ~ http://cloudml.org/scripts/linux/ubuntu/mongoDB/install_mongoDB.sh");
		mongoDBRes.setInstallCommand("cd ~; sudo bash install_mongoDB.sh");
		mongoDBRes.setName("MongoDBRes");
		mongoDB.getConfigurations().add(mongoDBRes);

		ProvidedCommunication mongoDBProv = DeploymentFactory.eINSTANCE
				.createProvidedCommunication();
		mongoDBProv.setOwner(mongoDB);
		mongoDBProv.setName("MongoDBProv");
		mongoDBProv.setPortNumber(0);

		mongoDB.getProvidedCommunications().add(mongoDBProv);

		RequiredHost vmMongoDBReq = DeploymentFactory.eINSTANCE
				.createRequiredHost();
		vmMongoDBReq.setOwner(mongoDB);
		vmMongoDBReq.setName("VMMongoDBReq");

		mongoDB.setRequiredHost(vmMongoDBReq);

		sensAppDeploymentModel.getInternalComponents().add(mongoDB);

		InternalComponent jettySC = DeploymentFactory.eINSTANCE
				.createInternalComponent();
		jettySC.setName("JettySC");

		Configuration jettySCRes = DeploymentFactory.eINSTANCE
				.createConfiguration();
		jettySCRes
				.setDownloadCommand("wget -P ~ http://cloudml.org/scripts/linux/ubuntu/jetty/install_jetty.sh");
		jettySCRes.setInstallCommand("cd ~; sudo bash install_jetty.sh");
		jettySCRes.setName("JettySCRes");
		jettySCRes.setStopCommand("sudo service jetty stop");
		
		jettySC.getConfigurations().add(jettySCRes);

		ProvidedHost servletContainerJettyProv = DeploymentFactory.eINSTANCE
				.createProvidedHost();
		servletContainerJettyProv.setOwner(jettySC);
		servletContainerJettyProv.setName("ServletContainerJettyProv");

		jettySC.getProvidedHosts().add(servletContainerJettyProv);

		RequiredHost vmJettySCReq = DeploymentFactory.eINSTANCE
				.createRequiredHost();
		vmJettySCReq.setOwner(jettySC);
		vmJettySCReq.setName("VMJettySCReq");

		jettySC.setRequiredHost(vmJettySCReq);

		sensAppDeploymentModel.getInternalComponents().add(jettySC);

		InternalComponent admin = DeploymentFactory.eINSTANCE
				.createInternalComponent();
		admin.setName("Admin");

		Configuration adminRes = DeploymentFactory.eINSTANCE
				.createConfiguration();
		adminRes.setDownloadCommand("wget -P ~ http://cloudml.org/resources/sensappAdmin/SensAppAdmin.tar; wget -P ~ http://cloudml.org/scripts/linux/ubuntu/sensappAdmin/start_sensappadmin.sh ; wget -P ~ http://cloudml.org/scripts/linux/ubuntu/sensappAdmin/install_sensappadmin.sh ; wget -P ~ http://cloudml.org/resources/sensappAdmin/localTopology.json");
		adminRes.setInstallCommand("cd ~; sudo bash install_sensappadmin.sh");
		adminRes.setName("AdminRes");
		adminRes.setStartCommand("cd ~; sudo bash start_sensappadmin.sh");
		adminRes.setStopCommand("sudo rm -rf /opt/jetty/webapps/SensAppGUI ; sudo service jetty restart");
		
		admin.getConfigurations().add(adminRes);

		RequiredCommunication restReq = DeploymentFactory.eINSTANCE
				.createRequiredCommunication();
		restReq.setOwner(admin);
		restReq.setIsMandatory(false);
		restReq.setName("RESTReq");
		restReq.setPortNumber(8080);

		admin.getRequiredCommunications().add(restReq);

		RequiredHost servletContainerAdminReq = DeploymentFactory.eINSTANCE
				.createRequiredHost();
		servletContainerAdminReq.setOwner(admin);
		servletContainerAdminReq.setName("ServletContainerAdminReq");

		admin.setRequiredHost(servletContainerAdminReq);

		sensAppDeploymentModel.getInternalComponents().add(admin);

		VM ml = DeploymentFactory.eINSTANCE.createVM();
		VMRequirementSet mlReqs = DeploymentFactory.eINSTANCE.createVMRequirementSet();
		mlReqs.setName("ML_VM_REQS");
		ml.setVmRequirementSet(mlReqs);
		sensAppDeploymentModel.getVmRequirementSets().add(mlReqs);
		QuantitativeHardwareRequirement mlHardReq = RequirementFactory.eINSTANCE.createQuantitativeHardwareRequirement();
		mlHardReq.setId("ML_VM_HARD_REQS");
		mlHardReq.setMaxCores(0);
		mlHardReq.setMaxRAM(0);
		mlHardReq.setMaxStorage(0);
		mlHardReq.setMinCores(2);
		mlHardReq.setMinRAM(4096);
		mlHardReq.setMinStorage(512);
		rm.getRequirements().add(mlHardReq);
		mlReqs.setQuantitativeHardwareRequirement(mlHardReq);
		LocationRequirement mlLocReq = RequirementFactory.eINSTANCE.createLocationRequirement();
		mlLocReq.setId("ML_LOC_REC");
		mlLocReq.getLocations().add(scotland);
		rm.getRequirements().add(mlLocReq);
		mlReqs.setLocationRequirement(mlLocReq);
		ml.setName("ML");

		Attribute mlKeyPath = ProviderFactory.eINSTANCE.createAttribute();
		mlKeyPath.setName("KeyPath");
		StringValue mlKeyPathValue = TypeFactory.eINSTANCE.createStringValue();
		mlKeyPathValue.setValue(".");
		mlKeyPath.setValue(mlKeyPathValue);

		ml.getProperties().add(mlKeyPath);

		ProvidedHost vmMLProv = DeploymentFactory.eINSTANCE
				.createProvidedHost();
		vmMLProv.setOwner(ml);
		vmMLProv.setName("VMMLProv");

		ml.getProvidedHosts().add(vmMLProv);

		sensAppDeploymentModel.getVms().add(ml);

		VM sl = DeploymentFactory.eINSTANCE.createVM();
		VMRequirementSet slReqs = DeploymentFactory.eINSTANCE.createVMRequirementSet();
		slReqs.setName("SL_VM_REQS");
		sl.setVmRequirementSet(slReqs);
		sensAppDeploymentModel.getVmRequirementSets().add(slReqs);
		QuantitativeHardwareRequirement slHardReq = RequirementFactory.eINSTANCE.createQuantitativeHardwareRequirement();
		slHardReq.setId("SL_VM_HARD_REQS");
		slHardReq.setMaxCores(0);
		slHardReq.setMaxRAM(0);
		slHardReq.setMaxStorage(0);
		slHardReq.setMinCores(1);
		slHardReq.setMinRAM(1024);
		slHardReq.setMinStorage(200);
		rm.getRequirements().add(slHardReq);
		slReqs.setQuantitativeHardwareRequirement(slHardReq);
		LocationRequirement slLocReq = RequirementFactory.eINSTANCE.createLocationRequirement();
		slLocReq.setId("SL_LOC_REC");
		slLocReq.getLocations().add(ireland);
		rm.getRequirements().add(slLocReq);
		slReqs.setLocationRequirement(slLocReq);
		sl.setName("SL");

		Attribute slKeyPath = ProviderFactory.eINSTANCE.createAttribute();
		slKeyPath.setName("KeyPath");
		StringValue slKeyPathValue = TypeFactory.eINSTANCE.createStringValue();
		slKeyPathValue.setValue(".");
		slKeyPath.setValue(slKeyPathValue);

		sl.getProperties().add(slKeyPath);

		ProvidedHost vmSLProv = DeploymentFactory.eINSTANCE
				.createProvidedHost();
		vmSLProv.setOwner(sl);
		vmSLProv.setName("VMSLProv");

		sl.getProvidedHosts().add(vmSLProv);

		sensAppDeploymentModel.getVms().add(sl);

		VM ll = DeploymentFactory.eINSTANCE.createVM();
		VMRequirementSet llReqs = DeploymentFactory.eINSTANCE.createVMRequirementSet();
		llReqs.setName("LL_VM_REQS");
		ll.setVmRequirementSet(llReqs);
		sensAppDeploymentModel.getVmRequirementSets().add(llReqs);
		QuantitativeHardwareRequirement llHardReq = RequirementFactory.eINSTANCE.createQuantitativeHardwareRequirement();
		llHardReq.setId("LL_VM_HARD_REQS");
		llHardReq.setMaxCores(0);
		llHardReq.setMaxRAM(0);
		llHardReq.setMaxStorage(0);
		llHardReq.setMinCores(4);
		llHardReq.setMinRAM(4096);
		llHardReq.setMinStorage(512);
		rm.getRequirements().add(llHardReq);
		llReqs.setQuantitativeHardwareRequirement(llHardReq);
		LocationRequirement llLocReq = RequirementFactory.eINSTANCE.createLocationRequirement();
		llLocReq.setId("LL_LOC_REC");
		llLocReq.getLocations().add(norway);
		rm.getRequirements().add(llLocReq);
		llReqs.setLocationRequirement(llLocReq);
		ll.setName("LL");

		Attribute llKeyPath = ProviderFactory.eINSTANCE.createAttribute();
		llKeyPath.setName("KeyPath");
		StringValue llKeyPathValue = TypeFactory.eINSTANCE.createStringValue();
		llKeyPathValue.setValue(".");
		llKeyPath.setValue(llKeyPathValue);

		ll.getProperties().add(llKeyPath);

		ProvidedHost vmLLProv = DeploymentFactory.eINSTANCE
				.createProvidedHost();
		vmLLProv.setOwner(ll);
		vmLLProv.setName("VMLLProv");

		ll.getProvidedHosts().add(vmLLProv);

		sensAppDeploymentModel.getVms().add(ll);

		Communication sensAppToAdmin = DeploymentFactory.eINSTANCE
				.createCommunication();
		sensAppToAdmin.setName("SensAppToAdmin");
		sensAppToAdmin.setProvidedCommunication(restProv);
		sensAppToAdmin.setRequiredCommunication(restReq);

		Configuration sensAppToAdminRes = DeploymentFactory.eINSTANCE
				.createConfiguration();
		sensAppToAdminRes
				.setDownloadCommand("get -P ~ http://cloudml.org/scripts/linux/ubuntu/sensappAdmin/configure_sensappadmin.sh");
		sensAppToAdminRes
				.setInstallCommand("cd ~; sudo bash configure_sensappadmin.sh");
		sensAppToAdminRes.setName("SensAppToAdminRes");
		
		sensAppToAdmin.setProvidedPortConfiguration(sensAppToAdminRes);

		sensAppDeploymentModel.getCommunications().add(sensAppToAdmin);

		Communication sensAppToMongoDB = DeploymentFactory.eINSTANCE
				.createCommunication();
		sensAppToMongoDB.setName("SensAppToMongoDB");
		sensAppToMongoDB.setProvidedCommunication(mongoDBProv);
		sensAppToMongoDB.setRequiredCommunication(mongoDBReq);
		sensAppToMongoDB.setCommunicationType(CommunicationType.LOCAL);

		sensAppDeploymentModel.getCommunications().add(sensAppToMongoDB);

		InternalComponentInstance jettySC1 = DeploymentFactory.eINSTANCE
				.createInternalComponentInstance();
		jettySC1.setName("JettySC1");
		jettySC1.setType(jettySC);

		ProvidedHostInstance servletContainerJettyProv1 = DeploymentFactory.eINSTANCE
				.createProvidedHostInstance();
		servletContainerJettyProv1.setOwner(jettySC1);
		servletContainerJettyProv1.setName("ServletContainerJettyProv1");
		servletContainerJettyProv1.setType(servletContainerJettyProv);

		jettySC1.getProvidedHostInstances().add(servletContainerJettyProv1);

		RequiredHostInstance vmJettySCReq1 = DeploymentFactory.eINSTANCE
				.createRequiredHostInstance();
		vmJettySCReq1.setOwner(jettySC1);
		vmJettySCReq1.setName("VMJettySCReq1");
		vmJettySCReq1.setType(vmJettySCReq);

		jettySC1.setRequiredHostInstance(vmJettySCReq1);

		sensAppDeploymentModel.getInternalComponentInstances().add(jettySC1);

		InternalComponentInstance sensApp1 = DeploymentFactory.eINSTANCE
				.createInternalComponentInstance();
		sensApp1.setName("SensApp1");
		sensApp1.setType(sensApp);

		ProvidedCommunicationInstance restProv1 = DeploymentFactory.eINSTANCE
				.createProvidedCommunicationInstance();
		restProv1.setOwner(sensApp1);
		restProv1.setName("RESTProv1");
		restProv1.setType(restProv);

		sensApp1.getProvidedCommunicationInstances().add(restProv1);

		RequiredCommunicationInstance mongoDBReq1 = DeploymentFactory.eINSTANCE
				.createRequiredCommunicationInstance();
		mongoDBReq1.setOwner(sensApp1);
		mongoDBReq1.setName("MongoDBReq1");
		mongoDBReq1.setType(mongoDBReq);

		sensApp1.getRequiredCommunicationInstances().add(mongoDBReq1);

		RequiredHostInstance servletContainerSensAppReq1 = DeploymentFactory.eINSTANCE
				.createRequiredHostInstance();
		servletContainerSensAppReq1.setOwner(sensApp1);
		servletContainerSensAppReq1.setName("ServletContainerSensAppReq1");
		servletContainerSensAppReq1.setType(servletContainerSensAppReq);

		sensApp1.setRequiredHostInstance(servletContainerSensAppReq1);

		sensAppDeploymentModel.getInternalComponentInstances().add(sensApp1);

		InternalComponentInstance mongoDB1 = DeploymentFactory.eINSTANCE
				.createInternalComponentInstance();
		mongoDB1.setName("MongoDB1");
		mongoDB1.setType(mongoDB);

		ProvidedCommunicationInstance mongoDBProv1 = DeploymentFactory.eINSTANCE
				.createProvidedCommunicationInstance();
		mongoDBProv1.setOwner(mongoDB1);
		mongoDBProv1.setName("MongoDBProv1");
		mongoDBProv1.setType(mongoDBProv);

		mongoDB1.getProvidedCommunicationInstances().add(mongoDBProv1);

		RequiredHostInstance vmMongoDBReq1 = DeploymentFactory.eINSTANCE
				.createRequiredHostInstance();
		vmMongoDBReq1.setOwner(mongoDB1);
		vmMongoDBReq1.setName("VMMongoDBReq1");
		vmMongoDBReq1.setType(vmMongoDBReq);

		mongoDB1.setRequiredHostInstance(vmMongoDBReq1);

		sensAppDeploymentModel.getInternalComponentInstances().add(mongoDB1);

		InternalComponentInstance jettySC2 = DeploymentFactory.eINSTANCE
				.createInternalComponentInstance();
		jettySC2.setName("JettySC2");
		jettySC2.setType(jettySC);

		ProvidedHostInstance servletContainerJettyProv2 = DeploymentFactory.eINSTANCE
				.createProvidedHostInstance();
		servletContainerJettyProv2.setOwner(jettySC2);
		servletContainerJettyProv2.setName("ServletContainerJettyProv2");
		servletContainerJettyProv2.setType(servletContainerJettyProv);

		jettySC2.getProvidedHostInstances().add(servletContainerJettyProv2);

		RequiredHostInstance vmJettySCReq2 = DeploymentFactory.eINSTANCE
				.createRequiredHostInstance();
		vmJettySCReq2.setOwner(jettySC2);
		vmJettySCReq2.setName("VMJettySCReq2");
		vmJettySCReq2.setType(vmJettySCReq);

		jettySC2.setRequiredHostInstance(vmJettySCReq2);

		sensAppDeploymentModel.getInternalComponentInstances().add(jettySC2);

		InternalComponentInstance admin1 = DeploymentFactory.eINSTANCE
				.createInternalComponentInstance();
		admin1.setName("Admin1");
		admin1.setType(admin);

		RequiredCommunicationInstance restReq1 = DeploymentFactory.eINSTANCE
				.createRequiredCommunicationInstance();
		restReq1.setOwner(admin1);
		restReq1.setName("RESTReq1");
		restReq1.setType(restReq);

		admin1.getRequiredCommunicationInstances().add(restReq1);

		RequiredHostInstance servletContainerAdminReq1 = DeploymentFactory.eINSTANCE
				.createRequiredHostInstance();
		servletContainerAdminReq1.setOwner(admin1);
		servletContainerAdminReq1.setName("ServletContainerAdminReq1");
		servletContainerAdminReq1.setType(servletContainerAdminReq);

		admin1.setRequiredHostInstance(servletContainerAdminReq1);

		sensAppDeploymentModel.getInternalComponentInstances().add(admin1);

		VMInstance vmML1 = DeploymentFactory.eINSTANCE.createVMInstance();
		vmML1.setVmType(vmType);
		vmML1.setVmTypeValue(mediumVm);


		MonetaryUnit costMonetaryUnit = UnitFactory.eINSTANCE
				.createMonetaryUnit();
		//costMonetaryUnit.setDimensionType(UnitDimensionType.COST);
		costMonetaryUnit.setUnit(UnitType.EUROS);
		costMonetaryUnit.setName("euros");

		camelModel.getUnits().add(costMonetaryUnit);

		vmML1.setName("VMML1");
		vmML1.setType(ml);

		ProvidedHostInstance vmMLProv1 = DeploymentFactory.eINSTANCE
				.createProvidedHostInstance();
		vmMLProv1.setOwner(vmML1);
		vmMLProv1.setName("VMMLProv1");
		vmMLProv1.setType(vmMLProv);

		vmML1.getProvidedHostInstances().add(vmMLProv1);

		sensAppDeploymentModel.getVmInstances().add(vmML1);

		VMInstance vmSL1 = DeploymentFactory.eINSTANCE.createVMInstance();

		vmSL1.setName("VMSL1");
		vmSL1.setType(sl);
		vmSL1.setVmType(vmType);
		vmSL1.setVmTypeValue(smallVm);

		ProvidedHostInstance vmSLProv1 = DeploymentFactory.eINSTANCE
				.createProvidedHostInstance();
		vmSLProv1.setOwner(vmSL1);
		vmSLProv1.setName("VMSLProv1");
		vmSLProv1.setType(vmSLProv);

		vmSL1.getProvidedHostInstances().add(vmSLProv1);

		sensAppDeploymentModel.getVmInstances().add(vmSL1);

		VMInstance vmLL1 = DeploymentFactory.eINSTANCE.createVMInstance();

		vmLL1.setName("VMLL1");
		vmLL1.setType(ll);
		vmLL1.setVmType(vmType);
		vmLL1.setVmTypeValue(largeVm);

		ProvidedHostInstance vmLLProv1 = DeploymentFactory.eINSTANCE
				.createProvidedHostInstance();
		vmLLProv1.setOwner(vmLL1);
		vmLLProv1.setName("VMLLProv1");
		vmLLProv1.setType(vmLLProv);

		vmLL1.getProvidedHostInstances().add(vmLLProv1);

		sensAppDeploymentModel.getVmInstances().add(vmLL1);

		CommunicationInstance sensAppToAdmin1 = DeploymentFactory.eINSTANCE
				.createCommunicationInstance();
		sensAppToAdmin1.setName("SensAppToAdmin1");
		sensAppToAdmin1.setProvidedCommunicationInstance(restProv1);
		sensAppToAdmin1.setRequiredCommunicationInstance(restReq1);
		sensAppToAdmin1.setType(sensAppToAdmin);

		sensAppDeploymentModel.getCommunicationInstances().add(sensAppToAdmin1);

		CommunicationInstance sensAppToMongoDB1 = DeploymentFactory.eINSTANCE
				.createCommunicationInstance();
		sensAppToMongoDB1.setName("SensAppToMongoDB1");
		sensAppToMongoDB1.setProvidedCommunicationInstance(mongoDBProv1);
		sensAppToMongoDB1.setRequiredCommunicationInstance(mongoDBReq1);
		sensAppToMongoDB1.setType(sensAppToMongoDB);

		sensAppDeploymentModel.getCommunicationInstances().add(
				sensAppToMongoDB1);

		Hosting adminToServletContainer = DeploymentFactory.eINSTANCE.createHosting();
		adminToServletContainer.setName("AdminToServletContainer");
		adminToServletContainer.setProvidedHost(servletContainerJettyProv);
		adminToServletContainer.setRequiredHost(servletContainerAdminReq);
		
		sensAppDeploymentModel.getHostings().add(adminToServletContainer);
		
		Hosting jettySCToVMSL = DeploymentFactory.eINSTANCE.createHosting();
		jettySCToVMSL.setName("JettySCToVMSL");
		jettySCToVMSL.setProvidedHost(vmSLProv);
		jettySCToVMSL.setRequiredHost(vmJettySCReq);
		
		sensAppDeploymentModel.getHostings().add(jettySCToVMSL);
		
		/*Hosting jettySCToVMML = DeploymentFactory.eINSTANCE.createHosting();
		jettySCToVMML.setName("JettySCToVMML");
		jettySCToVMML.setProvidedHost(vmMLProv);
		jettySCToVMML.setRequiredHost(vmJettySCReq);
		
		sensAppDeploymentModel.getHostings().add(jettySCToVMML);
		
		Hosting jettySCToVMLL = DeploymentFactory.eINSTANCE.createHosting();
		jettySCToVMLL.setName("JettySCToVMLL");
		jettySCToVMLL.setProvidedHost(vmLLProv);
		jettySCToVMLL.setRequiredHost(vmJettySCReq);
		
		sensAppDeploymentModel.getHostings().add(jettySCToVMLL);*/
		
		Hosting mongoDBToVMSL = DeploymentFactory.eINSTANCE.createHosting();
		mongoDBToVMSL.setName("MongoDBToVMSL");
		mongoDBToVMSL.setProvidedHost(vmSLProv);
		mongoDBToVMSL.setRequiredHost(vmMongoDBReq);
		
		sensAppDeploymentModel.getHostings().add(mongoDBToVMSL);
		
		/*Hosting mongoDBToVMML = DeploymentFactory.eINSTANCE.createHosting();
		mongoDBToVMML.setName("MongoDBToVMML");
		mongoDBToVMML.setProvidedHost(vmMLProv);
		mongoDBToVMML.setRequiredHost(vmMongoDBReq);
		
		sensAppDeploymentModel.getHostings().add(mongoDBToVMML);
		
		Hosting mongoDBToVMLL = DeploymentFactory.eINSTANCE.createHosting();
		mongoDBToVMLL.setName("MongoDBToVMLL");
		mongoDBToVMLL.setProvidedHost(vmLLProv);
		mongoDBToVMLL.setRequiredHost(vmMongoDBReq);
		
		sensAppDeploymentModel.getHostings().add(mongoDBToVMLL);*/
		
		Hosting sensAppToServletContainer = DeploymentFactory.eINSTANCE.createHosting();
		sensAppToServletContainer.setName("SensAppToServletContainer");
		sensAppToServletContainer.setProvidedHost(servletContainerJettyProv);
		sensAppToServletContainer.setRequiredHost(servletContainerSensAppReq);
		
		sensAppDeploymentModel.getHostings().add(sensAppToServletContainer);
		
		HostingInstance admin1ToJettySC2 = DeploymentFactory.eINSTANCE
				.createHostingInstance();
		admin1ToJettySC2.setName("Admin1ToJettySC2");
		admin1ToJettySC2.setProvidedHostInstance(servletContainerJettyProv2);
		admin1ToJettySC2.setRequiredHostInstance(servletContainerAdminReq1);
		admin1ToJettySC2.setType(adminToServletContainer);

		sensAppDeploymentModel.getHostingInstances().add(admin1ToJettySC2);

		HostingInstance jettySC2ToSL1 = DeploymentFactory.eINSTANCE
				.createHostingInstance();
		jettySC2ToSL1.setName("JettySC2ToVMSL1");
		jettySC2ToSL1.setProvidedHostInstance(vmSLProv1);
		jettySC2ToSL1.setRequiredHostInstance(vmJettySCReq2);
		jettySC2ToSL1.setType(jettySCToVMSL);

		sensAppDeploymentModel.getHostingInstances().add(jettySC2ToSL1);

		/*HostingInstance jettySC1ToVMLL1 = DeploymentFactory.eINSTANCE
				.createHostingInstance();
		jettySC1ToVMLL1.setName("JettySC1ToVMLL1");
		jettySC1ToVMLL1.setProvidedHostInstance(vmLLProv1);
		jettySC1ToVMLL1.setRequiredHostInstance(vmJettySCReq1);
		jettySC1ToVMLL1.setType(jettySCToVMLL);

		sensAppDeploymentModel.getHostingInstances().add(jettySC1ToVMLL1);

		HostingInstance mongoDB1ToVMML1 = DeploymentFactory.eINSTANCE
				.createHostingInstance();
		mongoDB1ToVMML1.setName("MongoDB1ToVMML1");
		mongoDB1ToVMML1.setProvidedHostInstance(vmMLProv1);
		mongoDB1ToVMML1.setRequiredHostInstance(vmMongoDBReq1);
		mongoDB1ToVMML1.setType(mongoDBToVMML);

		sensAppDeploymentModel.getHostingInstances().add(mongoDB1ToVMML1);*/

		HostingInstance sensApp1ToJettySC1 = DeploymentFactory.eINSTANCE
				.createHostingInstance();
		sensApp1ToJettySC1.setName("SensApp1ToJettySC1");
		sensApp1ToJettySC1.setProvidedHostInstance(servletContainerJettyProv1);
		sensApp1ToJettySC1.setRequiredHostInstance(servletContainerSensAppReq1);
		sensApp1ToJettySC1.setType(sensAppToServletContainer);

		sensAppDeploymentModel.getHostingInstances().add(sensApp1ToJettySC1);

		// //// END definition of Deployment model

		camelModel.getDeploymentModels().add(sensAppDeploymentModel);

		// //// START definition of Scalability model

		ScalabilityModel scalabilityModel = ScalabilityFactory.eINSTANCE
				.createScalabilityModel();
		scalabilityModel.setName("SensApp Scalability Model");
		
		MetricModel metricModel = MetricFactory.eINSTANCE
				.createMetricModel();
		metricModel.setName("SensApp Metric Model");
		camelModel.getMetricModels().add(metricModel);

		RawMetric rawExecTime = MetricFactory.eINSTANCE
				.createRawMetric();

		rawExecTime.setLayer(LayerType.SAA_S);
		rawExecTime.setName("RAW_EXEC_TIME");

		Property execTime = MetricFactory.eINSTANCE.createProperty();
		execTime.setName("Execution Time");
		execTime.setType(PropertyType.MEASURABLE);
		metricModel.getProperties().add(execTime);

		rawExecTime.setProperty(execTime);

		TimeIntervalUnit timeInterval = UnitFactory.eINSTANCE
				.createTimeIntervalUnit();
		//timeInterval.setDimensionType(UnitDimensionType.TIME_INTERVAL);
		timeInterval.setUnit(UnitType.SECONDS);
		timeInterval.setName("seconds");
		metricModel.getUnits().add(timeInterval);

		rawExecTime.setUnit(timeInterval);
		rawExecTime.setValueDirection((short) 0);
		
		Range rawEtMetricRange = TypeFactory.eINSTANCE.createRange();
		rawEtMetricRange.setPrimitiveType(TypeEnum.FLOAT_TYPE);

		Limit rawEtMetricMin = TypeFactory.eINSTANCE.createLimit();
		rawEtMetricMin.setIncluded(false);

		FloatValue rawEtMetricMinValue = TypeFactory.eINSTANCE
				.createFloatValue();
		rawEtMetricMinValue.setValue(0);

		rawEtMetricMin.setValue(rawEtMetricMinValue);

		rawEtMetricRange.setLowerLimit(rawEtMetricMin);

		Limit rawEtMetricMax = TypeFactory.eINSTANCE.createLimit();
		rawEtMetricMax.setIncluded(false);

		PositiveInf rawEtMetricMaxValue = TypeFactory.eINSTANCE
				.createPositiveInf();

		rawEtMetricMax.setValue(rawEtMetricMaxValue);

		rawEtMetricRange.setUpperLimit(rawEtMetricMax);

		rawExecTime.setValueType(rawEtMetricRange);


		metricModel.getMetrics().add(rawExecTime);

		CompositeMetric avgExecTime = MetricFactory.eINSTANCE
				.createCompositeMetric();

		MetricFormula avgExecTimeFormula = MetricFactory.eINSTANCE
				.createMetricFormula();
		avgExecTimeFormula.setName("AVG_ET_METRIC_FORMULA");
		avgExecTimeFormula.setFunction(MetricFunctionType.MEAN);
		avgExecTimeFormula.setFunctionArity(MetricFunctionArityType.UNARY);
		avgExecTimeFormula.getParameters().add(rawExecTime);

		avgExecTime.setFormula(avgExecTimeFormula);
		avgExecTime.setLayer(LayerType.SAA_S);
		avgExecTime.setName("AVG_EXEC_TIME");

		avgExecTime.setProperty(execTime);
		
		Range avgEtMetricRange = TypeFactory.eINSTANCE.createRange();
		avgEtMetricRange.setPrimitiveType(TypeEnum.FLOAT_TYPE);

		Limit avgEtMetricMin = TypeFactory.eINSTANCE.createLimit();
		avgEtMetricMin.setIncluded(false);

		FloatValue avgEtMetricMinValue = TypeFactory.eINSTANCE
				.createFloatValue();
		avgEtMetricMinValue.setValue(0);

		avgEtMetricMin.setValue(avgEtMetricMinValue);

		avgEtMetricRange.setLowerLimit(avgEtMetricMin);

		Limit avgEtMetricMax = TypeFactory.eINSTANCE.createLimit();
		avgEtMetricMax.setIncluded(false);

		PositiveInf avgEtMetricMaxValue = TypeFactory.eINSTANCE
				.createPositiveInf();

		avgEtMetricMax.setValue(avgEtMetricMaxValue);

		avgEtMetricRange.setUpperLimit(avgEtMetricMax);

		avgExecTime.setValueType(avgEtMetricRange);

		StorageUnit storageUnit = UnitFactory.eINSTANCE.createStorageUnit();
		//storageUnit.setDimensionType(UnitDimensionType.STORAGE);
		storageUnit.setUnit(UnitType.GIGABYTES);
		storageUnit.setName("gigabytes");

		metricModel.getUnits().add(storageUnit);

		avgExecTime.setUnit(storageUnit);
		avgExecTime.setValueDirection((short) 0);

		metricModel.getMetrics().add(avgExecTime);

		RawMetric storageMetricTemp = MetricFactory.eINSTANCE
				.createRawMetric();
		storageMetricTemp.setLayer(LayerType.IAA_S);
		storageMetricTemp.setName("Storage");
		
		Range rawStorageMetricRange = TypeFactory.eINSTANCE.createRange();
		rawStorageMetricRange.setPrimitiveType(TypeEnum.INT_TYPE);

		Limit rawStorageMetricMin = TypeFactory.eINSTANCE.createLimit();
		rawStorageMetricMin.setIncluded(true);

		IntValue rawStorageMetricMinValue = TypeFactory.eINSTANCE
				.createIntValue();
		rawStorageMetricMinValue.setValue(200);

		rawStorageMetricMin.setValue(rawStorageMetricMinValue);

		rawStorageMetricRange.setLowerLimit(rawStorageMetricMin);

		Limit rawStorageMetricMax = TypeFactory.eINSTANCE.createLimit();
		rawStorageMetricMax.setIncluded(true);

		IntValue rawStorageMetricMaxValue = TypeFactory.eINSTANCE
				.createIntValue();
		rawStorageMetricMaxValue.setValue(2048);

		rawStorageMetricMax.setValue(rawStorageMetricMaxValue);

		rawStorageMetricRange.setUpperLimit(rawStorageMetricMax);

		storageMetricTemp.setValueType(rawStorageMetricRange);


		Property storageProperty = MetricFactory.eINSTANCE
				.createProperty();
		storageProperty.setName("Storage");
		storageProperty.setType(PropertyType.MEASURABLE);
		metricModel.getProperties().add(storageProperty);

		storageMetricTemp.setProperty(storageProperty);
		storageMetricTemp.setUnit(storageUnit);
		storageMetricTemp.setValueDirection((short) 0);

		metricModel.getMetrics().add(storageMetricTemp);

		RawMetricInstance rawEtMetric = MetricFactory.eINSTANCE.createRawMetricInstance();
		rawEtMetric.setId("RawETMetric1");

		MetricObjectBinding rawEtMetricAIB = MetricFactory.eINSTANCE
				.createMetricApplicationBinding();
		rawEtMetricAIB.setName("SensAppCompBinding");

		ExecutionContext sensAppExecutionContext = ExecutionFactory.eINSTANCE
				.createExecutionContext();

		rawEtMetricAIB.setExecutionContext(sensAppExecutionContext);

		metricModel.getBindings().add(rawEtMetricAIB);

		rawEtMetric.setObjectBinding(rawEtMetricAIB);

		Sensor sensor1 = MetricFactory.eINSTANCE.createSensor();
		sensor1.setId("RawETSensor");
		sensor1.setIsPush(false);

		metricModel.getSensors().add(sensor1);

		rawEtMetric.setSensor(sensor1);
		rawEtMetric.setMetric(rawExecTime);

		metricModel.getMetricInstances().add(rawEtMetric);

		CompositeMetricInstance avgEtMetric1 = MetricFactory.eINSTANCE.createCompositeMetricInstance();
		avgEtMetric1.getComposingMetricInstances().add(rawEtMetric);
		avgEtMetric1.setId("AVGETMetric1");

		avgEtMetric1.setObjectBinding(rawEtMetricAIB);

		Sensor sensor2 = MetricFactory.eINSTANCE.createSensor();
		sensor2.setId("RawStorageSensor");
		sensor2.setIsPush(false);

		metricModel.getSensors().add(sensor2);

		avgEtMetric1.setMetric(avgExecTime);

		metricModel.getMetricInstances().add(avgEtMetric1);

		RawMetricInstance rawStorageMetric = MetricFactory.eINSTANCE.createRawMetricInstance();
		rawStorageMetric.setId("RawStorageNum");

		MetricVMBinding vmInstBinding = MetricFactory.eINSTANCE
				.createMetricVMBinding();
		vmInstBinding.setName("SensAppVMBinding");
		vmInstBinding.setExecutionContext(sensAppExecutionContext);
		vmInstBinding.setVmInstance(vmML1);

		metricModel.getBindings().add(vmInstBinding);

		rawStorageMetric.setObjectBinding(vmInstBinding);

		Sensor sensor3 = MetricFactory.eINSTANCE.createSensor();
		sensor3.setId("Sensor3");
		sensor3.setIsPush(false);

		metricModel.getSensors().add(sensor3);

		rawStorageMetric.setSensor(sensor3);

		rawStorageMetric.setMetric(storageMetricTemp);

		metricModel.getMetricInstances().add(rawStorageMetric);

		ScalabilityRule avgEtScalabilityRule = ScalabilityFactory.eINSTANCE
				.createScalabilityRule();

		HorizontalScalingAction verticalScalingSensApp = ScalabilityFactory.eINSTANCE
				.createHorizontalScalingAction();
		verticalScalingSensApp.setInternalComponent((InternalComponent)sensApp1.getType());
		verticalScalingSensApp.setCount(1);
		verticalScalingSensApp.setName("HorizScaleSensApp");
		verticalScalingSensApp.setType(ActionType.SCALE_OUT);
		verticalScalingSensApp.setVm((VM)vmLL1.getType());
		scalabilityModel.getActions().add(verticalScalingSensApp);

		avgEtScalabilityRule.getActions().add(verticalScalingSensApp);

		NonFunctionalEvent avgExecutionTimeViolated = ScalabilityFactory.eINSTANCE
				.createNonFunctionalEvent();
		avgExecutionTimeViolated.setIsViolation(true);
		
		CompositeMetricContext sensAppContext = MetricFactory.eINSTANCE.createCompositeMetricContext();
		sensAppContext.setName("AVG_ET_GT_10");
		sensAppContext.setApplication(sensAppApplication);
		sensAppContext.setMetric(avgEtMetric1.getMetric());
		metricModel.getContexts().add(sensAppContext);
		
		RawMetricContext rawETContext = MetricFactory.eINSTANCE.createRawMetricContext();
		rawETContext.setName("RAW_ET_CONTEXT");
		rawETContext.setApplication(sensAppApplication);
		rawETContext.setMetric(rawEtMetric.getMetric());
		rawETContext.setSensor(sensor1);
		metricModel.getContexts().add(rawETContext);
		sensAppContext.getComposingMetricContexts().add(rawETContext);

		MetricCondition avgEtMetricCondition = MetricFactory.eINSTANCE
				.createMetricCondition();
		avgEtMetricCondition
				.setComparisonOperator(ComparisonOperatorType.GREATER_THAN);
		avgEtMetricCondition.setThreshold(10);
		avgEtMetricCondition.setName("AVG_ET_GT_10");
		avgEtMetricCondition.setMetricContext(sensAppContext);

		metricModel.getConditions().add(avgEtMetricCondition);

		avgExecutionTimeViolated.setMetricCondition(avgEtMetricCondition);
		avgExecutionTimeViolated.setName("NFAvgETViol");

		scalabilityModel.getEvents().add(avgExecutionTimeViolated);

		avgEtScalabilityRule.setEvent(avgExecutionTimeViolated);
		avgEtScalabilityRule.setName("AvgETRule");

		scalabilityModel.getRules().add(avgEtScalabilityRule);

		ScalabilityRule storageViolationScalabilityRule = ScalabilityFactory.eINSTANCE
				.createScalabilityRule();

		VerticalScalingAction horizontalScaleMongoDBVm = ScalabilityFactory.eINSTANCE
				.createVerticalScalingAction();
		horizontalScaleMongoDBVm.setCoreUpdate(0);
		horizontalScaleMongoDBVm.setCPUUpdate(0);
		horizontalScaleMongoDBVm.setIoUpdate(0);
		horizontalScaleMongoDBVm.setMemoryUpdate(0);
		horizontalScaleMongoDBVm.setName("HorizScaleMongoDBVM");
		horizontalScaleMongoDBVm.setNetworkUpdate(0);
		horizontalScaleMongoDBVm.setStorageUpdate(512);
		horizontalScaleMongoDBVm.setType(ActionType.SCALE_UP);
		horizontalScaleMongoDBVm.setVm((VM)vmML1.getType());

		storageViolationScalabilityRule.getActions().add(
				horizontalScaleMongoDBVm);
		scalabilityModel.getActions().add(horizontalScaleMongoDBVm);

		NonFunctionalEvent rawStorageViolated = ScalabilityFactory.eINSTANCE
				.createNonFunctionalEvent();
		rawStorageViolated.setIsViolation(true);
		
		RawMetricContext mlContext = MetricFactory.eINSTANCE.createRawMetricContext();
		mlContext.setName("RAW_STORAGE_NUM_CONTEXT");
		mlContext.setComponent(ml);
		mlContext.setMetric(rawStorageMetric.getMetric());
		metricModel.getContexts().add(mlContext);
		mlContext.setSensor(sensor2);

		MetricCondition rawStorageMetricCondition = MetricFactory.eINSTANCE
				.createMetricCondition();
		rawStorageMetricCondition
				.setComparisonOperator(ComparisonOperatorType.GREATER_EQUAL_THAN);
		rawStorageMetricCondition.setThreshold(500);
		rawStorageMetricCondition.setName("RAW_STORAGE_NUM_GET_500");
		rawStorageMetricCondition.setMetricContext(mlContext);

		metricModel.getConditions().add(rawStorageMetricCondition);

		rawStorageViolated.setMetricCondition(rawStorageMetricCondition);
		rawStorageViolated.setName("NFRawStorageViol");

		scalabilityModel.getEvents().add(rawStorageViolated);

		storageViolationScalabilityRule.setEvent(rawStorageViolated);
		storageViolationScalabilityRule.setName("StorageViolRule");

		scalabilityModel.getRules().add(storageViolationScalabilityRule);

		HorizontalScaleRequirement horizPolicySensApp = RequirementFactory.eINSTANCE
				.createHorizontalScaleRequirement();
		horizPolicySensApp.setComponent(sensApp);
		horizPolicySensApp.setId("HorizPolicySensApp");
		horizPolicySensApp.setMaxInstances(4);
		horizPolicySensApp.setMinInstances(1);
		
		user1.getRequirements().add(horizPolicySensApp);
		
		scalabilityModel.getScaleRequirements().add(horizPolicySensApp);

		VerticalScaleRequirement verticalPolicyMongoDb = RequirementFactory.eINSTANCE
				.createVerticalScaleRequirement();
		verticalPolicyMongoDb.setId("VertPolMongoDB");
		verticalPolicyMongoDb.setMaxCores(0);
		verticalPolicyMongoDb.setMaxCPU(0);
		verticalPolicyMongoDb.setMaxRAM(0);
		verticalPolicyMongoDb.setMaxStorage(2048);
		verticalPolicyMongoDb.setMinCores(0);
		verticalPolicyMongoDb.setMinCPU(0);
		verticalPolicyMongoDb.setMinRAM(0);
		verticalPolicyMongoDb.setMinStorage(512);
		verticalPolicyMongoDb.setVm(ml);
		
		user1.getRequirements().add(verticalPolicyMongoDb);

		scalabilityModel.getScaleRequirements().add(verticalPolicyMongoDb);

		// //// END definition of Scalability model

		camelModel.getScalabilityModels().add(scalabilityModel);

		// //// START definition of Execution model

		ExecutionModel execModel = ExecutionFactory.eINSTANCE
				.createExecutionModel();
		execModel.setName("SensApp Execution Model");

		sensAppApplication.getDeploymentModels().add(sensAppDeploymentModel);
		sensAppApplication.setName("SensApp");
		sensAppApplication.setOwner(user1);
		sensAppApplication.setVersion("v1.0");

		camelModel.getApplications().add(sensAppApplication);

		sensAppExecutionContext.setApplication(sensAppApplication);
		sensAppExecutionContext.setDeploymentModel(sensAppDeploymentModel);
		sensAppExecutionContext.setId("SensAppEC1");

		RequirementGroup user1RG = RequirementFactory.eINSTANCE
				.createRequirementGroup();

		user1RG.setId("");
		user1RG.setRequirementOperator(RequirementOperatorType.AND);
		user1RG.getRequirements().add(verticalPolicyMongoDb);
		user1RG.getRequirements().add(horizPolicySensApp);
		user1RG.setUser(user1);

		rm.getRequirements().add(user1RG);

		sensAppExecutionContext.setRequirementGroup(user1RG);
		sensAppExecutionContext.setTotalCost(0);

		execModel.getExecutionContexts().add(sensAppExecutionContext);

		// END definition of Execution model

		camelModel.getExecutionModels().add(execModel);
		return camelModel;
	}

	public static void main(String[] args) {
		// initialize and activate a container
		final IManagedContainer container = ContainerUtil.createContainer();
		Net4jUtil.prepareContainer(container);
		TCPUtil.prepareContainer(container);
		// CDONet4jUtil.prepareContainer(container);
		container.activate();

		// create a Net4j TCP connector
		final IConnector connector = (IConnector) TCPUtil.getConnector(
				container, "localhost:2036");

		// create the session configuration
		CDONet4jSessionConfiguration config = CDONet4jUtil
				.createNet4jSessionConfiguration();
		config.setConnector(connector);
		config.setRepositoryName("repo1");

		// create the actual session with the repository
		CDONet4jSession cdoSession = config.openNet4jSession();

		// obtain a transaction object
		CDOTransaction transaction = cdoSession.openTransaction();

		// create a CDO resource object
		CDOResource resource = transaction
				.getOrCreateResource("/sensAppResource");

		EObject camelModel = getSensAppCamelModel();
		try {
			resource.getContents().add(camelModel);
			transaction.commit();
		} catch (ConcurrentAccessException e) {
			e.printStackTrace();
		} catch (CommitException e) {
			e.printStackTrace();
		}
	}
}
