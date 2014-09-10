package eu.paasage.camel.examples.model.submodels;

import eu.paasage.camel.*;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.Location;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Constraint;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderFactory;
import eu.paasage.camel.type.StringValue;
import eu.paasage.camel.type.TypeFactory;
import org.javatuples.Septet;

import java.util.Date;

/**
 * Created by orzech on 27/07/14.
 */
public class DeploymentModelClass {

    public static Septet<eu.paasage.camel.deployment.DeploymentModel, InternalComponent, InternalComponentInstance, InternalComponentInstance, VMInstance, VMInstance, VM> createMyAppDeploymentModel(CloudProvider amazonProvider, CloudProvider flexiantProvider, CloudProvider sintefNovaProvider,
                                                                                                                                                                          Location amazonEuLocation, Location flexiantLocation, Location osloNovaLocation, CamelModel camelModel,
                                                                                                                                                                          Feature vmFeature, Constraint smallVmConstraint, Constraint mediumVmConstraint, Constraint largeVmConstraint) {
        // //// START definition of Deployment model

        eu.paasage.camel.deployment.DeploymentModel sensAppDeploymentModel = DeploymentFactory.eINSTANCE.createDeploymentModel();

        sensAppDeploymentModel.setName("SensApp");
        sensAppDeploymentModel.getProviders().add(amazonProvider);
        sensAppDeploymentModel.getProviders().add(flexiantProvider);
        sensAppDeploymentModel.getProviders().add(sintefNovaProvider);

        InternalComponent sensApp = DeploymentFactory.eINSTANCE.createInternalComponent();
        sensApp.setName("SensApp");

        ComputationalResource sensAppRes = DeploymentFactory.eINSTANCE.createComputationalResource();
        sensAppRes.setDownloadCommand("wget -P ~ http://github.com/downloads/SINTEF-9012/model/model.war; wget -P ~ http://cloudml.org/scripts/linux/ubuntu/model/install_start_sensapp.sh");
        sensAppRes.setExecuteLocally(false);
        sensAppRes.setInstallCommand("cd ~; sudo bash install_start_sensapp.sh");
        sensAppRes.setName("SensAppRes");
        sensAppRes.setRequireCredentials(false);

        sensApp.getResources().add(sensAppRes);

        ProvidedCommunication restProv = DeploymentFactory.eINSTANCE.createProvidedCommunication();
        restProv.setOwner(sensApp);
        restProv.setIsLocal(false);
        restProv.setName("RESTProv");
        restProv.setPortNumber(8080);

        sensApp.getProvidedCommunications().add(restProv);

        RequiredCommunication mongoDBReq = DeploymentFactory.eINSTANCE.createRequiredCommunication();
        mongoDBReq.setOwner(sensApp);
        mongoDBReq.setIsLocal(true);
        mongoDBReq.setIsMandatory(true);
        mongoDBReq.setName("MongoDBReq");
        mongoDBReq.setPortNumber(0);

        sensApp.getRequiredCommunications().add(mongoDBReq);

        RequiredHost servletContainerSensAppReq = DeploymentFactory.eINSTANCE.createRequiredHost();
        servletContainerSensAppReq.setOwner(sensApp);
        servletContainerSensAppReq.setName("ServletContainerSensAppReq");

        sensApp.setRequiredHost(servletContainerSensAppReq);

        sensAppDeploymentModel.getInternalComponents().add(sensApp);

        InternalComponent mongoDB = DeploymentFactory.eINSTANCE.createInternalComponent();
        mongoDB.setName("MongoDB");

        ComputationalResource mongoDBRes = DeploymentFactory.eINSTANCE.createComputationalResource();
        mongoDBRes.setDownloadCommand("wget -P ~ http://cloudml.org/scripts/linux/ubuntu/mongoDB/install_mongoDB.sh");
        mongoDBRes.setExecuteLocally(false);
        mongoDBRes.setInstallCommand("cd ~; sudo bash install_mongoDB.sh");
        mongoDBRes.setName("MongoDBRes");
        mongoDBRes.setRequireCredentials(false);
        mongoDB.getResources().add(mongoDBRes);

        ProvidedCommunication mongoDBProv = DeploymentFactory.eINSTANCE.createProvidedCommunication();
        mongoDBProv.setOwner(mongoDB);
        mongoDBProv.setIsLocal(false);
        mongoDBProv.setName("MongoDBProv");
        mongoDBProv.setPortNumber(0);

        mongoDB.getProvidedCommunications().add(mongoDBProv);

        RequiredHost vmMongoDBReq = DeploymentFactory.eINSTANCE.createRequiredHost();
        vmMongoDBReq.setOwner(mongoDB);
        vmMongoDBReq.setName("VMMongoDBReq");

        mongoDB.setRequiredHost(vmMongoDBReq);

        sensAppDeploymentModel.getInternalComponents().add(mongoDB);

        InternalComponent jettySC = DeploymentFactory.eINSTANCE.createInternalComponent();
        jettySC.setName("JettySC");

        ComputationalResource jettySCRes = DeploymentFactory.eINSTANCE.createComputationalResource();
        jettySCRes.setDownloadCommand("wget -P ~ http://cloudml.org/scripts/linux/ubuntu/jetty/install_jetty.sh");
        jettySCRes.setExecuteLocally(false);
        jettySCRes.setInstallCommand("cd ~; sudo bash install_jetty.sh");
        jettySCRes.setName("JettySCRes");
        jettySCRes.setRequireCredentials(false);
        jettySCRes.setStopCommand("sudo service jetty stop");

        jettySC.getResources().add(mongoDBRes);

        ProvidedHost servletContainerJettyProv = DeploymentFactory.eINSTANCE.createProvidedHost();
        servletContainerJettyProv.setOwner(jettySC);
        servletContainerJettyProv.setName("ServletContainerJettyProv");

        jettySC.getProvidedHosts().add(servletContainerJettyProv);

        RequiredHost vmJettySCReq = DeploymentFactory.eINSTANCE.createRequiredHost();
        vmJettySCReq.setOwner(jettySC);
        vmJettySCReq.setName("VMJettySCReq");

        jettySC.setRequiredHost(vmJettySCReq);

        sensAppDeploymentModel.getInternalComponents().add(jettySC);

        InternalComponent admin = DeploymentFactory.eINSTANCE.createInternalComponent();
        admin.setName("Admin");

        ComputationalResource adminRes = DeploymentFactory.eINSTANCE.createComputationalResource();
        adminRes.setDownloadCommand("wget -P ~ http://cloudml.org/resources/sensappAdmin/SensAppAdmin.tar; wget -P ~ http://cloudml.org/scripts/linux/ubuntu/sensappAdmin/start_sensappadmin.sh ; wget -P ~ http://cloudml.org/scripts/linux/ubuntu/sensappAdmin/install_sensappadmin.sh ; wget -P ~ http://cloudml.org/resources/sensappAdmin/localTopology.json");
        adminRes.setExecuteLocally(false);
        adminRes.setInstallCommand("cd ~; sudo bash install_sensappadmin.sh");
        adminRes.setName("AdminRes");
        adminRes.setRequireCredentials(false);
        adminRes.setStartCommand("cd ~; sudo bash start_sensappadmin.sh");
        adminRes.setStopCommand("sudo rm -rf /opt/jetty/webapps/SensAppGUI ; sudo service jetty restart");

        admin.getResources().add(adminRes);

        RequiredCommunication restReq = DeploymentFactory.eINSTANCE.createRequiredCommunication();
        restReq.setOwner(admin);
        restReq.setIsLocal(false);
        restReq.setIsMandatory(false);
        restReq.setName("RESTReq");
        restReq.setPortNumber(8080);

        admin.getRequiredCommunications().add(restReq);

        RequiredHost servletContainerAdminReq = DeploymentFactory.eINSTANCE.createRequiredHost();
        servletContainerAdminReq.setOwner(admin);
        servletContainerAdminReq.setName("ServletContainerAdminReq");

        admin.setRequiredHost(servletContainerAdminReq);

        sensAppDeploymentModel.getInternalComponents().add(admin);

        VM ml = DeploymentFactory.eINSTANCE.createVM();
        ml.setImageId("RegionOne/9e2877b8-799e-4c87-a9f7-48140b021ba4");
        ml.setIs64os(true);
        ml.setLocation(flexiantLocation);
        ml.setMaxCores(0);
        ml.setMaxRam(0);
        ml.setMaxStorage(0);
        ml.setMinCores(2);
        ml.setMinRam(4096);
        ml.setMinStorage(512);
        ml.setName("ML");
        ml.setOs("ubuntu");
        ml.setProvider(flexiantProvider);

        Attribute mlKeyPath = ProviderFactory.eINSTANCE.createAttribute();
        mlKeyPath.setName("KeyPath");
        StringValue mlKeyPathValue = TypeFactory.eINSTANCE.createStringValue();
        mlKeyPathValue.setValue(".");
        mlKeyPath.setValue(mlKeyPathValue);

        ml.getProperties().add(mlKeyPath);

        ProvidedHost vmMLProv = DeploymentFactory.eINSTANCE.createProvidedHost();
        vmMLProv.setOwner(ml);
        vmMLProv.setName("VMMLProv");

        ml.getProvidedHosts().add(vmMLProv);

        sensAppDeploymentModel.getVms().add(ml);

        VM sl = DeploymentFactory.eINSTANCE.createVM();
        sl.setImageId("RegionOne/9e2877b8-799e-4c87-a9f7-48140b021ba4");
        sl.setIs64os(true);
        sl.setLocation(amazonEuLocation);
        sl.setMaxCores(0);
        sl.setMaxRam(0);
        sl.setMaxStorage(0);
        sl.setMinCores(1);
        sl.setMinRam(1024);
        sl.setMinStorage(200);
        sl.setName("SL");
        sl.setOs("ubuntu");
        sl.setProvider(amazonProvider);

        Attribute slKeyPath = ProviderFactory.eINSTANCE.createAttribute();
        slKeyPath.setName("KeyPath");
        StringValue slKeyPathValue = TypeFactory.eINSTANCE.createStringValue();
        slKeyPathValue.setValue(".");
        slKeyPath.setValue(slKeyPathValue);

        sl.getProperties().add(slKeyPath);

        ProvidedHost vmSLProv = DeploymentFactory.eINSTANCE.createProvidedHost();
        vmSLProv.setOwner(sl);
        vmSLProv.setName("VMSLProv");

        sl.getProvidedHosts().add(vmSLProv);

        sensAppDeploymentModel.getVms().add(sl);

        VM ll = DeploymentFactory.eINSTANCE.createVM();
        ll.setImageId("RegionOne/9e2877b8-799e-4c87-a9f7-48140b021ba4");
        ll.setIs64os(true);
        ll.setLocation(osloNovaLocation);
        ll.setMaxCores(0);
        ll.setMaxRam(0);
        ll.setMaxStorage(0);
        ll.setMinCores(4);
        ll.setMinRam(4096);
        ll.setMinStorage(512);
        ll.setName("LL");
        ll.setOs("ubuntu");
        ll.setProvider(sintefNovaProvider);

        Attribute llKeyPath = ProviderFactory.eINSTANCE.createAttribute();
        llKeyPath.setName("KeyPath");
        StringValue llKeyPathValue = TypeFactory.eINSTANCE.createStringValue();
        llKeyPathValue.setValue(".");
        llKeyPath.setValue(llKeyPathValue);

        ll.getProperties().add(llKeyPath);

        ProvidedHost vmLLProv = DeploymentFactory.eINSTANCE.createProvidedHost();
        vmLLProv.setOwner(ll);
        vmLLProv.setName("VMLLProv");

        ll.getProvidedHosts().add(vmLLProv);

        sensAppDeploymentModel.getVms().add(ll);

        Communication sensAppToAdmin = DeploymentFactory.eINSTANCE.createCommunication();
        sensAppToAdmin.setName("SensAppToAdmin");
        sensAppToAdmin.setProvidedCommunication(restProv);
        sensAppToAdmin.setRequiredCommunication(restReq);

        ComputationalResource sensAppToAdminRes = DeploymentFactory.eINSTANCE.createComputationalResource();
        sensAppToAdminRes.setDownloadCommand("get -P ~ http://cloudml.org/scripts/linux/ubuntu/sensappAdmin/configure_sensappadmin.sh");
        sensAppToAdminRes.setExecuteLocally(false);
        sensAppToAdminRes.setInstallCommand("cd ~; sudo bash configure_sensappadmin.sh");
        sensAppToAdminRes.setName("SensAppToAdminRes");
        sensAppToAdminRes.setRequireCredentials(false);

        sensAppToAdmin.getResources().add(sensAppToAdminRes);

        sensAppDeploymentModel.getCommunications().add(sensAppToAdmin);

        Communication sensAppToMongoDB = DeploymentFactory.eINSTANCE.createCommunication();
        sensAppToMongoDB.setName("SensAppToMongoDB");
        sensAppToMongoDB.setProvidedCommunication(mongoDBProv);
        sensAppToMongoDB.setRequiredCommunication(mongoDBReq);

        sensAppDeploymentModel.getCommunications().add(sensAppToMongoDB);

        InternalComponentInstance jettySC1 = DeploymentFactory.eINSTANCE.createInternalComponentInstance();
        jettySC1.setName("JettySC1");
        jettySC1.setType(jettySC);

        ProvidedHostInstance servletContainerJettyProv1 = DeploymentFactory.eINSTANCE.createProvidedHostInstance();
        servletContainerJettyProv1.setOwner(jettySC1);
        servletContainerJettyProv1.setName("ServletContainerJettyProv1");
        servletContainerJettyProv1.setType(servletContainerJettyProv);

        jettySC1.getProvidedHostInstances().add(servletContainerJettyProv1);

        RequiredHostInstance vmJettySCReq1 = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
        vmJettySCReq1.setOwner(jettySC1);
        vmJettySCReq1.setName("VMJettySCReq1");
        vmJettySCReq1.setType(vmJettySCReq);

        jettySC1.setRequiredHostInstance(vmJettySCReq1);

        sensAppDeploymentModel.getInternalComponentInstances().add(jettySC1);

        InternalComponentInstance sensApp1 = DeploymentFactory.eINSTANCE.createInternalComponentInstance();
        sensApp1.setName("SensApp1");
        sensApp1.setType(sensApp);

        ProvidedCommunicationInstance restProv1 = DeploymentFactory.eINSTANCE.createProvidedCommunicationInstance();
        restProv1.setOwner(sensApp1);
        restProv1.setName("RESTProv1");
        restProv1.setType(restProv);

        sensApp1.getProvidedCommunicationInstances().add(restProv1);

        RequiredCommunicationInstance mongoDBReq1 = DeploymentFactory.eINSTANCE.createRequiredCommunicationInstance();
        mongoDBReq1.setOwner(sensApp1);
        mongoDBReq1.setName("MongoDBReq1");
        mongoDBReq1.setType(mongoDBReq);

        sensApp1.getRequiredCommunicationInstances().add(mongoDBReq1);

        RequiredHostInstance servletContainerSensAppReq1 = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
        servletContainerSensAppReq1.setOwner(sensApp1);
        servletContainerSensAppReq1.setName("ServletContainerSensAppReq1");
        servletContainerSensAppReq1.setType(servletContainerSensAppReq);

        sensApp1.setRequiredHostInstance(servletContainerSensAppReq1);

        sensAppDeploymentModel.getInternalComponentInstances().add(sensApp1);

        InternalComponentInstance mongoDB1 = DeploymentFactory.eINSTANCE.createInternalComponentInstance();
        mongoDB1.setName("MongoDB1");
        mongoDB1.setType(mongoDB);

        ProvidedCommunicationInstance mongoDBProv1 = DeploymentFactory.eINSTANCE.createProvidedCommunicationInstance();
        mongoDBProv1.setOwner(mongoDB1);
        mongoDBProv1.setName("MongoDBProv1");
        mongoDBProv1.setType(mongoDBProv);

        mongoDB1.getProvidedCommunicationInstances().add(mongoDBProv1);

        RequiredHostInstance vmMongoDBReq1 = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
        vmMongoDBReq1.setOwner(mongoDB1);
        vmMongoDBReq1.setName("VMMongoDBReq1");
        vmMongoDBReq1.setType(vmMongoDBReq);

        mongoDB1.setRequiredHostInstance(vmMongoDBReq1);

        sensAppDeploymentModel.getInternalComponentInstances().add(mongoDB1);

        InternalComponentInstance jettySC2 = DeploymentFactory.eINSTANCE.createInternalComponentInstance();
        jettySC2.setName("JettySC2");
        jettySC2.setType(jettySC);

        ProvidedHostInstance servletContainerJettyProv2 = DeploymentFactory.eINSTANCE.createProvidedHostInstance();
        servletContainerJettyProv2.setOwner(jettySC2);
        servletContainerJettyProv2.setName("ServletContainerJettyProv2");
        servletContainerJettyProv2.setType(servletContainerJettyProv);

        jettySC2.getProvidedHostInstances().add(servletContainerJettyProv2);

        RequiredHostInstance vmJettySCReq2 = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
        vmJettySCReq2.setOwner(jettySC2);
        vmJettySCReq2.setName("VMJettySCReq2");
        vmJettySCReq2.setType(vmJettySCReq);

        jettySC2.setRequiredHostInstance(vmJettySCReq2);

        sensAppDeploymentModel.getInternalComponentInstances().add(jettySC2);

        InternalComponentInstance admin1 = DeploymentFactory.eINSTANCE.createInternalComponentInstance();
        admin1.setName("Admin1");
        admin1.setType(admin);

        RequiredCommunicationInstance restReq1 = DeploymentFactory.eINSTANCE.createRequiredCommunicationInstance();
        restReq1.setOwner(admin1);
        restReq1.setName("RESTReq1");
        restReq1.setType(restReq);

        admin1.getRequiredCommunicationInstances().add(restReq1);

        RequiredHostInstance servletContainerAdminReq1 = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
        servletContainerAdminReq1.setOwner(admin1);
        servletContainerAdminReq1.setName("ServletContainerAdminReq1");
        servletContainerAdminReq1.setType(servletContainerAdminReq);

        admin1.setRequiredHostInstance(servletContainerAdminReq1);

        sensAppDeploymentModel.getInternalComponentInstances().add(admin1);

        VMInstance vmML1 = DeploymentFactory.eINSTANCE.createVMInstance();

        VMInfo mediumVmInfo = CamelFactory.eINSTANCE.createVMInfo();
        mediumVmInfo.setBenchmarkRate(0);
        mediumVmInfo.setClassifiedOn(new Date());
        mediumVmInfo.setCostPerHour(1);

        MonetaryUnit costMonetaryUnit = CamelFactory.eINSTANCE.createMonetaryUnit();
        costMonetaryUnit.setDimensionType(UnitDimensionType.COST);
        costMonetaryUnit.setUnit(UnitType.EUROS);

        camelModel.getUnits().add(costMonetaryUnit);

        mediumVmInfo.setCostUnit(costMonetaryUnit);
        mediumVmInfo.setEvaluatedOn(new Date());
        mediumVmInfo.setName("MediumVmInfo");

        VMType mediumVmType = CamelFactory.eINSTANCE.createVMType();
        mediumVmType.setFeature(vmFeature);
        mediumVmType.setName("VM_Medium");
        mediumVmType.getConstraints().add(mediumVmConstraint);

        mediumVmInfo.setType(mediumVmType);
        camelModel.getVmTypes().add(mediumVmType);

        vmML1.setHasInfo(mediumVmInfo);

        camelModel.getVmInfos().add(mediumVmInfo);

        vmML1.setName("VMML1");
        vmML1.setType(ml);

        ProvidedHostInstance vmMLProv1 = DeploymentFactory.eINSTANCE.createProvidedHostInstance();
        vmMLProv1.setOwner(vmML1);
        vmMLProv1.setName("VMMLProv1");
        vmMLProv1.setType(vmMLProv);

        vmML1.getProvidedHostInstances().add(vmMLProv1);

        sensAppDeploymentModel.getVmInstances().add(vmML1);

        VMInstance vmSL1 = DeploymentFactory.eINSTANCE.createVMInstance();

        VMInfo smallVmInfo = CamelFactory.eINSTANCE.createVMInfo();
        smallVmInfo.setBenchmarkRate(0);
        smallVmInfo.setClassifiedOn(new Date());
        smallVmInfo.setCostPerHour(0.5);

        smallVmInfo.setCostUnit(costMonetaryUnit);
        smallVmInfo.setEvaluatedOn(new Date());
        smallVmInfo.setName("SmallVmInfo");

        VMType smallVmType = CamelFactory.eINSTANCE.createVMType();
        smallVmType.setFeature(vmFeature);
        smallVmType.setName("VMSmall");
        smallVmType.getConstraints().add(smallVmConstraint);

        smallVmInfo.setType(smallVmType);

        camelModel.getVmTypes().add(smallVmType);

        vmSL1.setHasInfo(smallVmInfo);

        camelModel.getVmInfos().add(smallVmInfo);

        vmSL1.setName("VMSL1");
        vmSL1.setType(sl);

        ProvidedHostInstance vmSLProv1 = DeploymentFactory.eINSTANCE.createProvidedHostInstance();
        vmSLProv1.setOwner(vmSL1);
        vmSLProv1.setName("VMSLProv1");
        vmSLProv1.setType(vmSLProv);

        vmSL1.getProvidedHostInstances().add(vmSLProv1);

        sensAppDeploymentModel.getVmInstances().add(vmSL1);

        VMInstance vmLL1 = DeploymentFactory.eINSTANCE.createVMInstance();

        VMInfo largeVmInfo = CamelFactory.eINSTANCE.createVMInfo();
        largeVmInfo.setBenchmarkRate(0);
        largeVmInfo.setClassifiedOn(new Date());
        largeVmInfo.setCostPerHour(2.0);

        largeVmInfo.setCostUnit(costMonetaryUnit);
        largeVmInfo.setEvaluatedOn(new Date());
        largeVmInfo.setName("LargeVmInfo");

        VMType largeVmType = CamelFactory.eINSTANCE.createVMType();
        largeVmType.setFeature(vmFeature);
        largeVmType.setName("VMLarge");
        largeVmType.getConstraints().add(largeVmConstraint);

        largeVmInfo.setType(largeVmType);
        camelModel.getVmTypes().add(largeVmType);

        vmLL1.setHasInfo(largeVmInfo);

        camelModel.getVmInfos().add(largeVmInfo);

        vmLL1.setName("VMLL1");
        vmLL1.setType(ll);

        ProvidedHostInstance vmLLProv1 = DeploymentFactory.eINSTANCE.createProvidedHostInstance();
        vmLLProv1.setOwner(vmLL1);
        vmLLProv1.setName("VMLLProv1");
        vmLLProv1.setType(vmLLProv);

        vmLL1.getProvidedHostInstances().add(vmLLProv1);

        sensAppDeploymentModel.getVmInstances().add(vmLL1);

        CommunicationInstance sensAppToAdmin1 = DeploymentFactory.eINSTANCE.createCommunicationInstance();
        sensAppToAdmin1.setName("SensAppToAdmin1");
        sensAppToAdmin1.setProvidedCommunicationInstance(restProv1);
        sensAppToAdmin1.setRequiredCommunicationInstance(restReq1);
        sensAppToAdmin1.setType(sensAppToAdmin);

        sensAppDeploymentModel.getCommunicationInstances().add(sensAppToAdmin1);

        CommunicationInstance sensAppToMongoDB1 = DeploymentFactory.eINSTANCE.createCommunicationInstance();
        sensAppToMongoDB1.setName("SensAppToMongoDB1");
        sensAppToMongoDB1.setProvidedCommunicationInstance(mongoDBProv1);
        sensAppToMongoDB1.setRequiredCommunicationInstance(mongoDBReq1);
        sensAppToMongoDB1.setType(sensAppToMongoDB);

        sensAppDeploymentModel.getCommunicationInstances().add(sensAppToMongoDB1);

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

        Hosting jettySCToVMML = DeploymentFactory.eINSTANCE.createHosting();
        jettySCToVMML.setName("JettySCToVMML");
        jettySCToVMML.setProvidedHost(vmMLProv);
        jettySCToVMML.setRequiredHost(vmJettySCReq);

        sensAppDeploymentModel.getHostings().add(jettySCToVMML);

        Hosting jettySCToVMLL = DeploymentFactory.eINSTANCE.createHosting();
        jettySCToVMLL.setName("JettySCToVMLL");
        jettySCToVMLL.setProvidedHost(vmLLProv);
        jettySCToVMLL.setRequiredHost(vmJettySCReq);

        sensAppDeploymentModel.getHostings().add(jettySCToVMLL);

        Hosting mongoDBToVMSL = DeploymentFactory.eINSTANCE.createHosting();
        mongoDBToVMSL.setName("MongoDBToVMSL");
        mongoDBToVMSL.setProvidedHost(vmSLProv);
        mongoDBToVMSL.setRequiredHost(vmMongoDBReq);

        sensAppDeploymentModel.getHostings().add(mongoDBToVMSL);

        Hosting mongoDBToVMML = DeploymentFactory.eINSTANCE.createHosting();
        mongoDBToVMML.setName("MongoDBToVMML");
        mongoDBToVMML.setProvidedHost(vmMLProv);
        mongoDBToVMML.setRequiredHost(vmMongoDBReq);

        sensAppDeploymentModel.getHostings().add(mongoDBToVMML);

        Hosting mongoDBToVMLL = DeploymentFactory.eINSTANCE.createHosting();
        mongoDBToVMLL.setName("MongoDBToVMLL");
        mongoDBToVMLL.setProvidedHost(vmLLProv);
        mongoDBToVMLL.setRequiredHost(vmMongoDBReq);

        sensAppDeploymentModel.getHostings().add(mongoDBToVMLL);

        Hosting sensAppToServletContainer = DeploymentFactory.eINSTANCE.createHosting();
        sensAppToServletContainer.setName("SensAppToServletContainer");
        sensAppToServletContainer.setProvidedHost(servletContainerJettyProv);
        sensAppToServletContainer.setRequiredHost(servletContainerSensAppReq);

        sensAppDeploymentModel.getHostings().add(sensAppToServletContainer);

        HostingInstance admin1ToJettySC2 = DeploymentFactory.eINSTANCE.createHostingInstance();
        admin1ToJettySC2.setName("Admin1ToJettySC2");
        admin1ToJettySC2.setProvidedHostInstance(servletContainerJettyProv2);
        admin1ToJettySC2.setRequiredHostInstance(servletContainerAdminReq1);
        admin1ToJettySC2.setType(adminToServletContainer);

        sensAppDeploymentModel.getHostingInstances().add(admin1ToJettySC2);

        HostingInstance jettySC2ToSL1 = DeploymentFactory.eINSTANCE.createHostingInstance();
        jettySC2ToSL1.setName("JettySC2ToVMSL1");
        jettySC2ToSL1.setProvidedHostInstance(vmSLProv1);
        jettySC2ToSL1.setRequiredHostInstance(vmJettySCReq2);
        jettySC2ToSL1.setType(jettySCToVMSL);

        sensAppDeploymentModel.getHostingInstances().add(jettySC2ToSL1);

        HostingInstance jettySC1ToVMLL1 = DeploymentFactory.eINSTANCE.createHostingInstance();
        jettySC1ToVMLL1.setName("JettySC1ToVMLL1");
        jettySC1ToVMLL1.setProvidedHostInstance(vmLLProv1);
        jettySC1ToVMLL1.setRequiredHostInstance(vmJettySCReq1);
        jettySC1ToVMLL1.setType(jettySCToVMLL);

        sensAppDeploymentModel.getHostingInstances().add(jettySC1ToVMLL1);

        HostingInstance mongoDB1ToVMML1 = DeploymentFactory.eINSTANCE.createHostingInstance();
        mongoDB1ToVMML1.setName("MongoDB1ToVMML1");
        mongoDB1ToVMML1.setProvidedHostInstance(vmMLProv1);
        mongoDB1ToVMML1.setRequiredHostInstance(vmMongoDBReq1);
        mongoDB1ToVMML1.setType(mongoDBToVMML);

        sensAppDeploymentModel.getHostingInstances().add(mongoDB1ToVMML1);

        HostingInstance sensApp1ToJettySC1 = DeploymentFactory.eINSTANCE.createHostingInstance();
        sensApp1ToJettySC1.setName("SensApp1ToJettySC1");
        sensApp1ToJettySC1.setProvidedHostInstance(servletContainerJettyProv1);
        sensApp1ToJettySC1.setRequiredHostInstance(servletContainerSensAppReq1);
        sensApp1ToJettySC1.setType(sensAppToServletContainer);

        sensAppDeploymentModel.getHostingInstances().add(sensApp1ToJettySC1);

        ////// END definition of Deployment model
        ////// END definition of Deployment model
        return new Septet<eu.paasage.camel.deployment.DeploymentModel, InternalComponent, InternalComponentInstance, InternalComponentInstance, VMInstance, VMInstance, VM>(
                sensAppDeploymentModel, sensApp, sensApp1, mongoDB1, vmML1, vmLL1, ml);
    }
}
