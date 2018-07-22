/* Copyright (C) 2015 KYRIAKOS KRITIKOS <kritikos@ics.forth.gr> */

/* This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/ 
 */

package eu.paasage.mddb.camel.examples;

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

import camel.core.ActionType;
import camel.core.Application;
import camel.core.Attribute;
import camel.core.CamelModel;
import camel.core.CoreFactory;
import camel.core.MeasurableAttribute;
import camel.requirement.LocationRequirement;
import camel.requirement.OSRequirement;
import camel.requirement.ProviderRequirement;
import camel.requirement.ResourceRequirement;
import camel.requirement.RequirementFactory;
import camel.requirement.RequirementModel;
import camel.unit.SingleUnit;
import camel.unit.Unit;
import camel.unit.UnitDimension;
import camel.unit.UnitFactory;
import camel.unit.UnitModel;
import camel.deployment.Communication;
import camel.deployment.CommunicationInstance;
import camel.deployment.Configuration;
import camel.deployment.DeploymentFactory;
import camel.deployment.DeploymentModel;
import camel.deployment.Hosting;
import camel.deployment.HostingInstance;
import camel.deployment.SoftwareComponent;
import camel.deployment.SoftwareComponentInstance;
import camel.deployment.ProvidedCommunication;
import camel.deployment.ProvidedCommunicationInstance;
import camel.deployment.ProvidedHost;
import camel.deployment.ProvidedHostInstance;
import camel.deployment.RequiredCommunication;
import camel.deployment.RequiredCommunicationInstance;
import camel.deployment.RequiredHost;
import camel.deployment.RequiredHostInstance;
import camel.deployment.VM;
import camel.deployment.VMInstance;
import camel.deployment.RequirementSet;
import camel.deployment.ScriptConfiguration;
import camel.execution.ExecutionFactory;
import camel.execution.ExecutionModel;
import camel.organisation.CloudCredentials;
import camel.organisation.CloudProvider;
import camel.organisation.Credentials;
import camel.organisation.OrganisationFactory;
import camel.organisation.OrganisationModel;
import camel.organisation.PlatformCredentials;
import camel.organisation.User;
import camel.location.GeographicalRegion;
import camel.location.LocationFactory;
import camel.location.LocationModel;
import camel.constraint.ComparisonOperatorType;
import camel.constraint.ConstraintContext;
import camel.constraint.ConstraintFactory;
import camel.constraint.ConstraintModel;
import camel.scalability.HorizontalScalingAction;
import camel.requirement.HorizontalScaleRequirement;
import camel.metric.CompositeMetric;
import camel.metric.CompositeMetricContext;
import camel.metric.MetricInstance;
import camel.metric.Metric;
import camel.constraint.MetricConstraint;
import camel.metric.MetricContext;
import camel.metric.MetricFactory;
import camel.metric.MetricModel;
import camel.metric.MetricObjectBinding;
import camel.metric.MetricTemplate;
import camel.metric.MetricTypeModel;
import camel.metric.ObjectContext;
import camel.metric.RawMetric;
import camel.metric.RawMetricContext;
import camel.scalability.NonFunctionalEvent;
import camel.scalability.ScalabilityFactory;
import camel.scalability.ScalabilityModel;
import camel.scalability.ScalabilityRule;
import camel.metric.Sensor;
import camel.mms.MetaDataModel;
import camel.mms.MmsConcept;
import camel.mms.MmsFactory;
import camel.mms.MmsPackage;
import camel.mms.MmsProperty;
import camel.mms.MmsPropertyType;
import camel.scalability.VerticalScalingAction;
import camel.requirement.VerticalScaleRequirement;
import camel.type.FloatValue;
import camel.type.IntValue;
import camel.type.Limit;
import camel.type.PrimitiveType;
import camel.type.Range;
import camel.type.TypeFactory;
import camel.type.TypeModel;


public class SensAppCDO {

	public static EObject getSensAppCamelModel() {
		// complete mapping of the SensApp example
		CamelModel camelModel = CoreFactory.eINSTANCE.createCamelModel();
		camelModel.setName("Sensapp Camel Model");
		UnitModel unitModel = UnitFactory.eINSTANCE.createUnitModel();
		unitModel.setName("Sensapp Unit Model");
		camelModel.getUnitModels().add(unitModel);
		TypeModel typeModel = TypeFactory.eINSTANCE.createTypeModel();
		typeModel.setName("Sensapp Type Model");
		camelModel.getTypeModels().add(typeModel);
		LocationModel locModel = LocationFactory.eINSTANCE.createLocationModel();
		locModel.setName("Sensapp Location Model");
		camelModel.getLocationModels().add(locModel);
		MetaDataModel mdm = MmsFactory.eINSTANCE.createMetaDataModel();
		mdm.setName("MetaDataModel");
		camelModel.getMetadataModels().add(mdm);
		EList<OrganisationModel> orgModels = camelModel.getOrganisationModels();

		Application sensAppApplication = CoreFactory.eINSTANCE.createApplication();
		
		// START DEFINITION OF METADATAMODEL
		MmsConcept conc = MmsFactory.eINSTANCE.createMmsConcept();
		conc.setName("Root");
		conc.setId("Root");
		conc.setUri("mms:Root");
		mdm.getMetadataElements().add(conc);
		
		MmsProperty minCores = MmsFactory.eINSTANCE.createMmsProperty();
		minCores.setName("MINCORES");
		minCores.setId("MINCORES");
		minCores.setUri("mms:MINCORES");
		minCores.setPropertyType(MmsPropertyType.DATA_PROPERTY);
		minCores.setRangeUri("xsd:int");
		conc.getProperty().add(minCores);
		
		MmsProperty minRAM = MmsFactory.eINSTANCE.createMmsProperty();
		minRAM.setName("MINRAM");
		minRAM.setId("MINRAM");
		minRAM.setUri("mms:MINRAM");
		minRAM.setPropertyType(MmsPropertyType.DATA_PROPERTY);
		minRAM.setRangeUri("xsd:int");
		conc.getProperty().add(minRAM);
		
		MmsProperty minStorage = MmsFactory.eINSTANCE.createMmsProperty();
		minStorage.setName("MINSTORAGE");
		minStorage.setId("MINSTORAGE");
		minStorage.setUri("mms:MINSTORAGE");
		minStorage.setPropertyType(MmsPropertyType.DATA_PROPERTY);
		minStorage.setRangeUri("xsd:int");
		conc.getProperty().add(minStorage);
		
		// //// START definition of Sintef Nova Organisation model

		OrganisationModel sintefOrgModel = OrganisationFactory.eINSTANCE
				.createOrganisationModel();
		sintefOrgModel.setName("SINTEF Organisation Model");
		EList<User> sintefUsers = sintefOrgModel.getUsers();

		User user1 = OrganisationFactory.eINSTANCE.createUser();
		user1.setEmail("user@sintef.no");
		user1.setFirstName("User1");
		user1.setLastName("User");
		user1.setName("User1");

		sintefUsers.add(user1);
		
		PlatformCredentials user1Credentials = OrganisationFactory.eINSTANCE.createPlatformCredentials();
		user1Credentials.setName("user1_pcreds");
		user1Credentials.setPassword("user1_at_sintef_dot_no");
		user1.setPlatformCredentials(user1Credentials);

		CloudProvider sintefNovaProvider = OrganisationFactory.eINSTANCE
				.createCloudProvider();
		sintefNovaProvider.setEmail("contact@sintef.no");
		sintefNovaProvider.setIaaS(true);
		sintefNovaProvider.setName("Sintef-Nova");
		sintefNovaProvider.setPaaS(true);
		sintefNovaProvider.setPublic(false);
		sintefNovaProvider.setSaaS(false);

		sintefOrgModel.setOrganisation(sintefNovaProvider);
		
		CloudCredentials user1AmazonCredentials = OrganisationFactory.eINSTANCE
				.createCloudCredentials();
		user1AmazonCredentials.setName("User1_Amazon_Credentials");
		user1AmazonCredentials.setCloudProviderName("AWS");
		sintefNovaProvider.getCloudCredentials().add(user1AmazonCredentials);
		CloudCredentials user1FlexiantCredentials = OrganisationFactory.eINSTANCE
				.createCloudCredentials();
		user1FlexiantCredentials.setName("User1_Flexiant_Credentials");
		user1FlexiantCredentials.setCloudProviderName("Flexiant");
		sintefNovaProvider.getCloudCredentials().add(user1FlexiantCredentials);

		CloudCredentials user1SintefNovaCredentials = OrganisationFactory.eINSTANCE
				.createCloudCredentials();
		user1SintefNovaCredentials.setName("User1_Amazon_Credentials");
		user1SintefNovaCredentials.setCloudProviderName("SintefNovaProvider");
		sintefNovaProvider.getCloudCredentials().add(user1SintefNovaCredentials);

		// //// END definition of Sintef Nova Organisation model

		orgModels.add(sintefOrgModel);

		// //// START definition of Deployment model

		DeploymentModel sensAppDeploymentModel = DeploymentFactory.eINSTANCE.createDeploymentTypeModel();

		sensAppDeploymentModel.setName("SensApp");
		
		RequirementModel rm = RequirementFactory.eINSTANCE.createRequirementModel();
		rm.setName("SensAPP-Requirement Model");
		camelModel.getRequirementModels().add(rm);
		ProviderRequirement pr = RequirementFactory.eINSTANCE.createProviderRequirement();
		pr.setName("Provider_Requirements_SensApp");
		rm.getRequirements().add(pr);
		pr.getProviderNames().add("AWS");
		pr.getProviderNames().add("Flexiant");
		pr.getProviderNames().add("SintefNovaProvider");
		RequirementSet globalReqs = DeploymentFactory.eINSTANCE.createRequirementSet();
		globalReqs.setName("Global_Reqs_Sens_App");
		globalReqs.setProviderRequirement(pr);
		OSRequirement osReq = RequirementFactory.eINSTANCE.createOSRequirement();
		osReq.setName("GLOBAL_OS_REQ");
		osReq.setIs64os(true);
		osReq.setOs("ubuntu");
		rm.getRequirements().add(osReq);
		globalReqs.setOsRequirement(osReq);
		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).setGlobalRequirementSet(globalReqs);
		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getRequirementSets().add(globalReqs);

		SoftwareComponent sensApp = DeploymentFactory.eINSTANCE
				.createSoftwareComponent();
		sensApp.setName("SensApp");

		ScriptConfiguration sensAppRes = DeploymentFactory.eINSTANCE
				.createScriptConfiguration();
		sensAppRes
				.setDownloadCommand("wget -P ~ http://github.com/downloads/SINTEF-9012/sensapp/sensapp.war; wget -P ~ http://cloudml.org/scripts/linux/ubuntu/sensapp/install_start_sensapp.sh");
		sensAppRes
				.setInstallCommand("cd ~; sudo bash install_start_sensapp.sh");
		sensAppRes.setName("SensAppRes");
		sensApp.getConfigurations().add(sensAppRes);

		ProvidedCommunication restProv = DeploymentFactory.eINSTANCE
				.createProvidedCommunication();
		restProv.setName("RESTProv");
		restProv.setPortNumber(8080);

		sensApp.getProvidedCommunications().add(restProv);

		RequiredCommunication mongoDBReq = DeploymentFactory.eINSTANCE
				.createRequiredCommunication();
		mongoDBReq.setIsMandatory(true);
		mongoDBReq.setName("MongoDBReq");
		mongoDBReq.setPortNumber(0);

		sensApp.getRequiredCommunications().add(mongoDBReq);

		RequiredHost servletContainerSensAppReq = DeploymentFactory.eINSTANCE
				.createRequiredHost();
		servletContainerSensAppReq.setName("ServletContainerSensAppReq");

		sensApp.setRequiredHost(servletContainerSensAppReq);

		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getSoftwareComponents().add(sensApp);

		RequirementSet mongoDBRS = DeploymentFactory.eINSTANCE.createRequirementSet();
		mongoDBRS.setName("MongoDBRS");
		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getRequirementSets().add(mongoDBRS);
		
		GeographicalRegion norway = LocationFactory.eINSTANCE.createGeographicalRegion();
		norway.setId("NO");
		norway.setName("Norway");
		locModel.getRegions().add(norway);
		LocationRequirement norReq = RequirementFactory.eINSTANCE.createLocationRequirement();
		norReq.setName("NorwayReq");
		norReq.getLocations().add(norway);
		rm.getRequirements().add(norReq);
		mongoDBRS.setLocationRequirement(norReq);
		ResourceRequirement mongoDBRR = RequirementFactory.eINSTANCE.createResourceRequirement();
		mongoDBRR.setName("MongoDBRR");
		Attribute mongoDBCores = CoreFactory.eINSTANCE.createAttribute();
		mongoDBCores.setName("MongoDBMinCores");
		mongoDBCores.getAnnotations().add(minCores);
		IntValue fourCores = TypeFactory.eINSTANCE.createIntValue();
		fourCores.setValue(4);
		mongoDBCores.setValue(fourCores);
		mongoDBRR.getAttributes().add(mongoDBCores);
		Attribute mongoDBRAM = CoreFactory.eINSTANCE.createAttribute();
		mongoDBRAM.setName("MongoDBMinRAM");
		mongoDBRAM.getAnnotations().add(minRAM);
		IntValue f_4096 = TypeFactory.eINSTANCE.createIntValue();
		f_4096.setValue(4096);
		mongoDBRAM.setValue(f_4096);
		mongoDBRR.getAttributes().add(mongoDBRAM);
		Attribute mongoDBStorage = CoreFactory.eINSTANCE.createAttribute();
		mongoDBStorage.setName("MongoDBMinStorage");
		mongoDBStorage.getAnnotations().add(minStorage);
		IntValue f_512 = TypeFactory.eINSTANCE.createIntValue();
		f_512.setValue(512);
		mongoDBStorage.setValue(f_512);
		mongoDBRR.getAttributes().add(mongoDBStorage);
		rm.getRequirements().add(mongoDBRR);
		mongoDBRS.setResourceRequirement(mongoDBRR);
		
		SoftwareComponent mongoDB = DeploymentFactory.eINSTANCE
				.createSoftwareComponent();
		mongoDB.setName("MongoDB");
		mongoDB.setRequirementSet(mongoDBRS);

		ScriptConfiguration mongoDBRes = DeploymentFactory.eINSTANCE
				.createScriptConfiguration();
		mongoDBRes
				.setDownloadCommand("wget -P ~ http://cloudml.org/scripts/linux/ubuntu/mongoDB/install_mongoDB.sh");
		mongoDBRes.setInstallCommand("cd ~; sudo bash install_mongoDB.sh");
		mongoDBRes.setName("MongoDBRes");
		mongoDB.getConfigurations().add(mongoDBRes);

		ProvidedCommunication mongoDBProv = DeploymentFactory.eINSTANCE
				.createProvidedCommunication();
		mongoDBProv.setName("MongoDBProv");
		mongoDBProv.setPortNumber(0);

		mongoDB.getProvidedCommunications().add(mongoDBProv);

		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getSoftwareComponents().add(mongoDB);

		RequirementSet sensAppJRS = DeploymentFactory.eINSTANCE.createRequirementSet();
		sensAppJRS.setName("sensAppJRS");
		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getRequirementSets().add(sensAppJRS);
		
		GeographicalRegion scotland = LocationFactory.eINSTANCE.createGeographicalRegion();
		scotland.setId("SC");
		scotland.setName("Scotland");
		locModel.getRegions().add(scotland);
		LocationRequirement scotReq = RequirementFactory.eINSTANCE.createLocationRequirement();
		scotReq.setName("ScotlandReq");
		scotReq.getLocations().add(scotland);
		rm.getRequirements().add(scotReq);
		sensAppJRS.setLocationRequirement(scotReq);
		ResourceRequirement sensAppJRR = RequirementFactory.eINSTANCE.createResourceRequirement();
		sensAppJRR.setName("sensAppJRR");
		Attribute sensAppJCores = CoreFactory.eINSTANCE.createAttribute();
		sensAppJCores.setName("sensAppJMinCores");
		sensAppJCores.getAnnotations().add(minCores);
		IntValue twoCores = TypeFactory.eINSTANCE.createIntValue();
		twoCores.setValue(2);
		sensAppJCores.setValue(twoCores);
		sensAppJRR.getAttributes().add(sensAppJCores);
		Attribute sensAppJRAM = CoreFactory.eINSTANCE.createAttribute();
		sensAppJRAM.setName("sensAppJMinRAM");
		sensAppJRAM.getAnnotations().add(minRAM);
		f_4096 = TypeFactory.eINSTANCE.createIntValue();
		f_4096.setValue(4096);
		sensAppJRAM.setValue(f_4096);
		sensAppJRR.getAttributes().add(sensAppJRAM);
		Attribute sensAppJStorage = CoreFactory.eINSTANCE.createAttribute();
		sensAppJStorage.setName("sensAppJMinStorage");
		sensAppJStorage.getAnnotations().add(minStorage);
		f_512 = TypeFactory.eINSTANCE.createIntValue();
		f_512.setValue(512);
		sensAppJStorage.setValue(f_512);
		sensAppJRR.getAttributes().add(sensAppJStorage);
		rm.getRequirements().add(sensAppJRR);
		sensAppJRS.setResourceRequirement(sensAppJRR);

		
		SoftwareComponent jettySC = DeploymentFactory.eINSTANCE
				.createSoftwareComponent();
		jettySC.setName("JettySC");
		jettySC.setRequirementSet(sensAppJRS);

		ScriptConfiguration jettySCRes = DeploymentFactory.eINSTANCE
				.createScriptConfiguration();
		jettySCRes
				.setDownloadCommand("wget -P ~ http://cloudml.org/scripts/linux/ubuntu/jetty/install_jetty.sh");
		jettySCRes.setInstallCommand("cd ~; sudo bash install_jetty.sh");
		jettySCRes.setName("JettySCRes");
		jettySCRes.setStopCommand("sudo service jetty stop");
		
		jettySC.getConfigurations().add(jettySCRes);

		ProvidedHost servletContainerJettyProv = DeploymentFactory.eINSTANCE
				.createProvidedHost();
		servletContainerJettyProv.setName("ServletContainerJettyProv");

		jettySC.getProvidedHosts().add(servletContainerJettyProv);

		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getSoftwareComponents().add(jettySC);

		RequirementSet adminJRS = DeploymentFactory.eINSTANCE.createRequirementSet();
		adminJRS.setName("adminAppJRS");
		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getRequirementSets().add(adminJRS);
		
		GeographicalRegion ireland = LocationFactory.eINSTANCE.createGeographicalRegion();
		ireland.setId("IE");
		ireland.setName("Ireland");
		locModel.getRegions().add(ireland);
		LocationRequirement ireReq = RequirementFactory.eINSTANCE.createLocationRequirement();
		ireReq.setName("IrelandReq");
		ireReq.getLocations().add(ireland);
		rm.getRequirements().add(ireReq);
		adminJRS.setLocationRequirement(ireReq);
		ResourceRequirement adminJRR = RequirementFactory.eINSTANCE.createResourceRequirement();
		adminJRR.setName("adminJRR");
		Attribute adminJCores = CoreFactory.eINSTANCE.createAttribute();
		adminJCores.setName("adminJMinCores");
		adminJCores.getAnnotations().add(minCores);
		IntValue oneCores = TypeFactory.eINSTANCE.createIntValue();
		oneCores.setValue(1);
		adminJCores.setValue(oneCores);
		adminJRR.getAttributes().add(adminJCores);
		Attribute adminJRAM = CoreFactory.eINSTANCE.createAttribute();
		adminJRAM.setName("adminJMinRAM");
		adminJRAM.getAnnotations().add(minRAM);
		IntValue f_1024 = TypeFactory.eINSTANCE.createIntValue();
		f_1024.setValue(1024);
		adminJRAM.setValue(f_1024);
		adminJRR.getAttributes().add(adminJRAM);
		Attribute adminJStorage = CoreFactory.eINSTANCE.createAttribute();
		adminJStorage.setName("adminJMinStorage");
		adminJStorage.getAnnotations().add(minStorage);
		IntValue f_200 = TypeFactory.eINSTANCE.createIntValue();
		f_200.setValue(200);
		adminJStorage.setValue(f_200);
		adminJRR.getAttributes().add(adminJStorage);
		rm.getRequirements().add(adminJRR);
		adminJRS.setResourceRequirement(adminJRR);
		
		SoftwareComponent jettySC2 = DeploymentFactory.eINSTANCE
				.createSoftwareComponent();
		jettySC2.setName("JettySC2");
		jettySC2.setRequirementSet(adminJRS);

		ScriptConfiguration jettySCRes2 = DeploymentFactory.eINSTANCE
				.createScriptConfiguration();
		jettySCRes2
				.setDownloadCommand("wget -P ~ http://cloudml.org/scripts/linux/ubuntu/jetty/install_jetty.sh");
		jettySCRes2.setInstallCommand("cd ~; sudo bash install_jetty.sh");
		jettySCRes2.setName("JettySCRes2");
		jettySCRes2.setStopCommand("sudo service jetty stop");
		
		jettySC2.getConfigurations().add(jettySCRes);

		ProvidedHost servletContainerJettyProv2 = DeploymentFactory.eINSTANCE
				.createProvidedHost();
		servletContainerJettyProv2.setName("ServletContainerJettyProv2");

		jettySC2.getProvidedHosts().add(servletContainerJettyProv2);

		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getSoftwareComponents().add(jettySC2);

		
		SoftwareComponent admin = DeploymentFactory.eINSTANCE
				.createSoftwareComponent();
		admin.setName("Admin");

		ScriptConfiguration adminRes = DeploymentFactory.eINSTANCE
				.createScriptConfiguration();
		adminRes.setDownloadCommand("wget -P ~ http://cloudml.org/resources/sensappAdmin/SensAppAdmin.tar; wget -P ~ http://cloudml.org/scripts/linux/ubuntu/sensappAdmin/start_sensappadmin.sh ; wget -P ~ http://cloudml.org/scripts/linux/ubuntu/sensappAdmin/install_sensappadmin.sh ; wget -P ~ http://cloudml.org/resources/sensappAdmin/localTopology.json");
		adminRes.setInstallCommand("cd ~; sudo bash install_sensappadmin.sh");
		adminRes.setName("AdminRes");
		adminRes.setStartCommand("cd ~; sudo bash start_sensappadmin.sh");
		adminRes.setStopCommand("sudo rm -rf /opt/jetty/webapps/SensAppGUI ; sudo service jetty restart");
		
		admin.getConfigurations().add(adminRes);

		RequiredCommunication restReq = DeploymentFactory.eINSTANCE
				.createRequiredCommunication();
		restReq.setIsMandatory(false);
		restReq.setName("RESTReq");
		restReq.setPortNumber(8080);

		admin.getRequiredCommunications().add(restReq);

		RequiredHost servletContainerAdminReq = DeploymentFactory.eINSTANCE
				.createRequiredHost();
		servletContainerAdminReq.setName("ServletContainerAdminReq");

		admin.setRequiredHost(servletContainerAdminReq);

		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getSoftwareComponents().add(admin);

		
		Communication sensAppToAdmin = DeploymentFactory.eINSTANCE
				.createCommunication();
		sensAppToAdmin.setName("SensAppToAdmin");
		sensAppToAdmin.setProvidedCommunication(restProv);
		sensAppToAdmin.setRequiredCommunication(restReq);

		ScriptConfiguration sensAppToAdminRes = DeploymentFactory.eINSTANCE
				.createScriptConfiguration();
		sensAppToAdminRes
				.setDownloadCommand("get -P ~ http://cloudml.org/scripts/linux/ubuntu/sensappAdmin/configure_sensappadmin.sh");
		sensAppToAdminRes
				.setInstallCommand("cd ~; sudo bash configure_sensappadmin.sh");
		sensAppToAdminRes.setName("SensAppToAdminRes");
		
		sensAppToAdmin.setProvidedPortConfiguration(sensAppToAdminRes);

		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getCommunications().add(sensAppToAdmin);

		Communication sensAppToMongoDB = DeploymentFactory.eINSTANCE
				.createCommunication();
		sensAppToMongoDB.setName("SensAppToMongoDB");
		sensAppToMongoDB.setProvidedCommunication(mongoDBProv);
		sensAppToMongoDB.setRequiredCommunication(mongoDBReq);

		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getCommunications().add(sensAppToMongoDB);

		Hosting adminToServletContainer = DeploymentFactory.eINSTANCE.createHosting();
		adminToServletContainer.setName("AdminToServletContainer");
		adminToServletContainer.setProvidedHost(servletContainerJettyProv2);
		adminToServletContainer.getRequiredHosts().add(servletContainerAdminReq);
		
		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getHostings().add(adminToServletContainer);
		
		Hosting sensAppToServletContainer = DeploymentFactory.eINSTANCE.createHosting();
		sensAppToServletContainer.setName("SensAppToServletContainer");
		sensAppToServletContainer.setProvidedHost(servletContainerJettyProv);
		sensAppToServletContainer.getRequiredHosts().add(servletContainerSensAppReq);
		
		((camel.deployment.DeploymentTypeModel)sensAppDeploymentModel).getHostings().add(sensAppToServletContainer);
		
		
		// //// END definition of Deployment model

		camelModel.getDeploymentModels().add(sensAppDeploymentModel);

		// //// START definition of Scalability model

		ScalabilityModel scalabilityModel = ScalabilityFactory.eINSTANCE
				.createScalabilityModel();
		scalabilityModel.setName("SensApp Scalability Model");
		
		MetricTypeModel metricModel = MetricFactory.eINSTANCE
				.createMetricTypeModel();
		metricModel.setName("SensApp Metric Model");
		camelModel.getMetricModels().add(metricModel);

		MeasurableAttribute execTime = CoreFactory.eINSTANCE.createMeasurableAttribute();
		execTime.setName("Execution Time");
		camelModel.getAttributes().add(execTime);
		
		MetricTemplate etTemplate = MetricFactory.eINSTANCE.createMetricTemplate();
		etTemplate.setName("ExecutionTimeTemplate");
		etTemplate.setAttribute(execTime);
		metricModel.getTemplates().add(etTemplate);
		
		RawMetric rawExecTime = MetricFactory.eINSTANCE
				.createRawMetric();
		rawExecTime.setName("RAW_EXEC_TIME");
		rawExecTime.setMetricTemplate(etTemplate);
		metricModel.getMetrics().add(rawExecTime);

		UnitDimension time = UnitFactory.eINSTANCE.createUnitDimension();
		time.setName("TIME");
		unitModel.getDimensions().add(time);
		SingleUnit timeInterval = UnitFactory.eINSTANCE
				.createSingleUnit();
		timeInterval.setName("seconds");
		timeInterval.setDimension(time);
		unitModel.getUnits().add(timeInterval);

		etTemplate.setUnit(timeInterval);
		etTemplate.setValueDirection((short)0);
		
		Range rawEtMetricRange = TypeFactory.eINSTANCE.createRange();
		rawEtMetricRange.setPrimitiveType(PrimitiveType.FLOAT_TYPE);
		rawEtMetricRange.setName("ETMetricTemplateRange");

		Limit rawEtMetricMin = TypeFactory.eINSTANCE.createLimit();
		rawEtMetricMin.setIncluded(false);

		FloatValue rawEtMetricMinValue = TypeFactory.eINSTANCE
				.createFloatValue();
		rawEtMetricMinValue.setValue(0);

		rawEtMetricMin.setValue(rawEtMetricMinValue);

		rawEtMetricRange.setLowerLimit(rawEtMetricMin);

		etTemplate.setValueType(rawEtMetricRange);
		typeModel.getValueTypes().add(rawEtMetricRange);


		metricModel.getMetrics().add(rawExecTime);

		CompositeMetric avgExecTime = MetricFactory.eINSTANCE
				.createCompositeMetric();

		avgExecTime.setFormula("mean(RAW_EXEC_TIME)");
		avgExecTime.setName("AVG_EXEC_TIME");
		avgExecTime.setMetricTemplate(etTemplate);
		
		metricModel.getMetrics().add(avgExecTime);
		
		UnitDimension storage = UnitFactory.eINSTANCE.createUnitDimension();
		storage.setName("STORAGE");
		unitModel.getDimensions().add(storage);

		SingleUnit storageUnit = UnitFactory.eINSTANCE.createSingleUnit();
		storageUnit.setName("gigabytes");
		storageUnit.setDimension(storage);
		unitModel.getUnits().add(storageUnit);
		
		MetricTemplate storageTemplate = MetricFactory.eINSTANCE.createMetricTemplate();
		storageTemplate.setName("StorageTemplate");
		metricModel.getTemplates().add(storageTemplate);

		RawMetric storageMetricTemp = MetricFactory.eINSTANCE
				.createRawMetric();
		storageMetricTemp.setName("Storage");
		storageMetricTemp.setMetricTemplate(storageTemplate);
		
		Range rawStorageMetricRange = TypeFactory.eINSTANCE.createRange();
		rawStorageMetricRange.setPrimitiveType(PrimitiveType.INT_TYPE);
		rawStorageMetricRange.setName("StorageMetricTemplateRange");

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

		storageTemplate.setValueType(rawStorageMetricRange);
		typeModel.getValueTypes().add(rawStorageMetricRange);


		MeasurableAttribute storageProperty = CoreFactory.eINSTANCE
				.createMeasurableAttribute();
		storageProperty.setName("Storage");
		camelModel.getAttributes().add(storageProperty);

		storageTemplate.setAttribute(storageProperty);
		storageTemplate.setUnit(storageUnit);
		storageTemplate.setValueDirection((short) 0);

		metricModel.getMetrics().add(storageMetricTemp);

		Sensor sensor1 = MetricFactory.eINSTANCE.createSensor();
		sensor1.setName("RawETSensor");
		sensor1.setIsPush(false);
		sensor1.setConfiguration("");

		metricModel.getSensors().add(sensor1);

		Sensor sensor2 = MetricFactory.eINSTANCE.createSensor();
		sensor2.setName("RawStorageSensor");
		sensor2.setIsPush(false);
		sensor2.setConfiguration("");

		metricModel.getSensors().add(sensor2);

		Sensor sensor3 = MetricFactory.eINSTANCE.createSensor();
		sensor3.setName("Sensor3");
		sensor3.setIsPush(false);
		sensor3.setConfiguration("");

		metricModel.getSensors().add(sensor3);

		ScalabilityRule avgEtScalabilityRule = ScalabilityFactory.eINSTANCE
				.createScalabilityRule();

		HorizontalScalingAction verticalScalingSensApp = ScalabilityFactory.eINSTANCE
				.createHorizontalScalingAction();
		verticalScalingSensApp.setSoftwareComponent(sensApp);
		verticalScalingSensApp.setCount(1);
		verticalScalingSensApp.setName("HorizScaleSensApp");
		scalabilityModel.getActions().add(verticalScalingSensApp);

		avgEtScalabilityRule.getActions().add(verticalScalingSensApp);

		NonFunctionalEvent avgExecutionTimeViolated = ScalabilityFactory.eINSTANCE
				.createNonFunctionalEvent();
		avgExecutionTimeViolated.setIsViolation(true);
		
		CompositeMetricContext sensAppContext = MetricFactory.eINSTANCE.createCompositeMetricContext();
		sensAppContext.setName("AVG_ET_GT_10");
		sensAppContext.setMetric(avgExecTime);
		metricModel.getMetricContexts().add(sensAppContext);
		
		RawMetricContext rawETContext = MetricFactory.eINSTANCE.createRawMetricContext();
		rawETContext.setName("RAW_ET_CONTEXT");
		rawETContext.setMetric(rawExecTime);
		rawETContext.setSensor(sensor1);
		metricModel.getMetricContexts().add(rawETContext);
		sensAppContext.getComposingMetricContexts().add(rawETContext);
		
		ConstraintModel constrModel = ConstraintFactory.eINSTANCE.createConstraintModel();
		constrModel.setName("SensAppConstrModel");
		camelModel.getConstraintModels().add(constrModel);
		
		ConstraintContext cc = ConstraintFactory.eINSTANCE.createConstraintContext();
		cc.setName("MetricConstraintContext");
		constrModel.getContexts().add(cc);

		MetricConstraint avgEtMetricConstraint = ConstraintFactory.eINSTANCE
				.createMetricConstraint();
		avgEtMetricConstraint
				.setComparisonOperator(ComparisonOperatorType.GREATER_THAN);
		avgEtMetricConstraint.setThreshold(10);
		avgEtMetricConstraint.setName("AVG_ET_GT_10");
		avgEtMetricConstraint.setMetricContext(sensAppContext);
		avgEtMetricConstraint.setConstraintContext(cc);

		constrModel.getConstraints().add(avgEtMetricConstraint);

		avgExecutionTimeViolated.setMetricConstraint(avgEtMetricConstraint);
		avgExecutionTimeViolated.setName("NFAvgETViol");

		scalabilityModel.getEvents().add(avgExecutionTimeViolated);

		avgEtScalabilityRule.setEvent(avgExecutionTimeViolated);
		avgEtScalabilityRule.setName("AvgETRule");

		scalabilityModel.getRules().add(avgEtScalabilityRule);

		NonFunctionalEvent rawStorageViolated = ScalabilityFactory.eINSTANCE
				.createNonFunctionalEvent();
		rawStorageViolated.setIsViolation(true);
		
		ObjectContext oo = MetricFactory.eINSTANCE.createObjectContext();
		oo.setComponent(mongoDB);
		oo.setName("MongoDBContext");
		metricModel.getObjectContexts().add(oo);
		
		RawMetricContext mlContext = MetricFactory.eINSTANCE.createRawMetricContext();
		mlContext.setName("RAW_STORAGE_NUM_CONTEXT");
		mlContext.setObjectContext(oo);
		mlContext.setMetric(storageMetricTemp);
		metricModel.getMetricContexts().add(mlContext);
		mlContext.setSensor(sensor2);

		MetricConstraint rawStorageMetricConstraint = ConstraintFactory.eINSTANCE
				.createMetricConstraint();
		rawStorageMetricConstraint
				.setComparisonOperator(ComparisonOperatorType.GREATER_EQUAL_THAN);
		rawStorageMetricConstraint.setThreshold(500);
		rawStorageMetricConstraint.setName("RAW_STORAGE_NUM_GET_500");
		rawStorageMetricConstraint.setMetricContext(mlContext);
		rawStorageMetricConstraint.setConstraintContext(cc);

		constrModel.getConstraints().add(rawStorageMetricConstraint);

		rawStorageViolated.setMetricConstraint(rawStorageMetricConstraint);
		rawStorageViolated.setName("NFRawStorageViol");

		scalabilityModel.getEvents().add(rawStorageViolated);

		HorizontalScaleRequirement horizPolicySensApp = RequirementFactory.eINSTANCE
				.createHorizontalScaleRequirement();
		horizPolicySensApp.setName("HorizPolicySensApp");
		horizPolicySensApp.setMaxInstances(4);
		horizPolicySensApp.setMinInstances(1);
		rm.getRequirements().add(horizPolicySensApp);
		
		user1.getRequirementModels().add(rm);
		
		rm.getRequirements().add(horizPolicySensApp);

		// //// END definition of Scalability model

		camelModel.getScalabilityModels().add(scalabilityModel);

		return camelModel;
	}

}
