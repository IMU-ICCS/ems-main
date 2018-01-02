package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.camel.CamelFactory;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.location.CloudLocation;
import eu.paasage.camel.location.Country;
import eu.paasage.camel.location.GeographicalRegion;
import eu.paasage.camel.location.Location;
import eu.paasage.camel.metric.Property;
import eu.paasage.camel.organisation.CloudCredentials;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.organisation.impl.OrganisationModelImpl;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.requirement.*;
import eu.paasage.upperware.metamodel.application.*;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.types.typesPaasage.*;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;
import eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import eu.paasage.upperware.profiler.generator.filter.QuantitativeHardwareRequirementFilter;
import eu.paasage.upperware.profiler.generator.service.camel.CamelModelService;
import eu.paasage.upperware.profiler.generator.service.camel.PaasageConfigurationService;
import eu.paasage.upperware.profiler.generator.service.camel.PaasageConfigurationUtilsService;
import eu.paasage.upperware.profiler.generator.service.camel.TypesFactoryService;
import eu.paasage.upperware.profiler.generator.service.camel.model.Flavour;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

import static eu.paasage.upperware.profiler.cp.generator.model.tools.PaasageModelTool.existComponentInstance;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaasageConfigurationServiceImpl implements PaasageConfigurationService {

    private static int DEFAULT_MIN_INSTANCE_NUMBER =0;
    private static int DEFAULT_MAX_INSTANCE_NUMBER =1000;

    public static String NAME_SEPARATOR="_";
    public static String SUFFIX="VM_PROFILE";
    private static final String GLOBAL_PROVIDER_REQUIREMENTS = "GlobalProviderRequirements";

    private final ApplicationFactory applicationFactory;
    private final TypesPaasageFactory typesPaasageFactory;
    private final CamelFactory camelFactory;

    private final TypesFactoryService typesFactoryService;
    private final CamelModelService camelModelService;
    private final IDatabaseProxy database;
    private final PaasageConfigurationUtilsService paasageConfigurationUtilsService;

    private Map<String, List<VirtualMachineProfile>> vmProfiles;

    @Override
    public PaaSageConfigurationWrapper createPaasageConfigurationWrapper(CamelModel camelModel, String appId) {
        log.info("** Creating PaaSageConfigurationWrapper...");
        PaasageConfiguration paasageConfiguration = createPaasageConfigurationInstance(appId);
        PaaSageConfigurationWrapper pcw = createPaaSageConfigurationWrapperInstance(paasageConfiguration);

        parseModel(camelModel, pcw);

        return pcw;
    }

    private PaasageConfiguration createPaasageConfigurationInstance(String appId) {
        String auxId = generatePaasageAppConfigurationId(appId);
        log.info("** Creating PaaSageConfigurationWrapper - generated id: {}", auxId);
        PaasageConfiguration paasageConfiguration = applicationFactory.createPaasageConfiguration();
        paasageConfiguration.setId(auxId);
        return paasageConfiguration;
    }

    private String generatePaasageAppConfigurationId(String appId) {
        return appId+System.currentTimeMillis();
    }

    private PaaSageConfigurationWrapper createPaaSageConfigurationWrapperInstance(PaasageConfiguration pc) {
        PaaSageConfigurationWrapper wrapper = new PaaSageConfigurationWrapper(pc);
        wrapper.setFunctionTypes(database.loadFunctionTypes());
        wrapper.setLocations(database.loadLocations());
        wrapper.setOperatingSystems(database.loadOperatingSystems());
        wrapper.setProviderTypes(database.loadProviderTypes());
        return wrapper;
    }

    private Set<String> getPreferedProviders(CamelModel camelModel) {
        Set<String> foundProviders = new HashSet<String>();

        for (OrganisationModel orgModel : camelModel.getOrganisationModels()) {
            if (orgModel instanceof OrganisationModelImpl) {
                for (User user : orgModel.getUsers()) {
                    for (CloudCredentials cloudCredential : user.getCloudCredentials()) {
                        String providerName = cloudCredential.getCloudProvider().getName();
                        if (GLOBAL_PROVIDER_REQUIREMENTS.equalsIgnoreCase(providerName)) {
                            String name = cloudCredential.getName();
                            foundProviders.add(name);
                        }
                    }
                    break; // we are currently supporting only one user
                }
            }
        }

        return foundProviders;
    }

    public void parseModel(CamelModel camelModel, PaaSageConfigurationWrapper pcw) {
        DeploymentModel deploymentModel = camelModel.getDeploymentModels().get(0);

//        //TODO to nie powinno byc w zmiennej.
        vmProfiles= new Hashtable<>();

        Set<String> preferedProviders = getPreferedProviders(camelModel);

        printPreferedProviders(preferedProviders);

        log.debug("CamelModelProcessor - parseModel - Calling DeploymentModelParser!");
        log.info(" ** 	Calling DeploymentModelParser");

        parseDeploymentModel(deploymentModel, pcw, preferedProviders);

        storeRelatedProviderModels(deploymentModel, pcw);

        log.info(" ** 	Processing Opt Rerqs");
        log.info(" ** 	Processing Opt Rerqs ended");
    }

    private Property getProperty(OptimisationRequirement optReq){
        return optReq.getMetric() != null ? optReq.getMetric().getProperty() : optReq.getProperty();
    }

    private void printPreferedProviders(Set<String> preferedProviders) {
        if (CollectionUtils.isNotEmpty(preferedProviders)){
            log.info("Found {} prefered providers:", preferedProviders.size());
            for (String preferedProvider : preferedProviders) {
                log.info("{}", preferedProvider);
            }
        } else {
            log.info("Prefered providers not found");
        }
    }

    private void storeRelatedProviderModels(DeploymentModel deploymentModel,  PaaSageConfigurationWrapper pcw) {

        Map<String, List<CloudProvider>> cloudProviders = getCloudProviders(deploymentModel);
        List<CloudProvider> asList = getAsList(cloudProviders);

        EList<Provider> providers = pcw.getPaasageConfiguration().getProviders();
        if (CollectionUtils.isNotEmpty(providers)) {
            for (Provider provider: providers){
                boolean added = false;
                for (CloudProvider cloudProvider : asList){
                    ProviderModel providerModel = cloudProvider.getProviderModel();
                    if (provider.getId().contains(providerModel.getRootFeature().getName())) {
                        database.savePM(addNewCamelModelAsParent(deploymentModel, providerModel), pcw.getPaasageConfiguration().getId(), provider.getId());
                        added = true;
                        break;
                    }
                }

                if (!added){
                    ProviderModel providerModel = database.loadPM(provider.getType().getId());
                    if (providerModel != null) {
                        database.savePM(providerModel, pcw.getPaasageConfiguration().getId(), provider.getId());
                    }
                }
            }
        }
    }


    private ProviderModel addNewCamelModelAsParent(DeploymentModel deploymentModel, ProviderModel providerModel) {
        ProviderModel providerCopy = EcoreUtil.copy(providerModel);
        CamelModel camelModel = camelFactory.createCamelModel();
        camelModel.getProviderModels().add(providerCopy);
        camelModel.getTypeModels().addAll(((CamelModel)deploymentModel.eContainer()).getTypeModels());
        camelModel.setName("CamelModel" + providerModel.getRootFeature().getName());
        return providerCopy;
    }

    private Map<String, List<CloudProvider>> getCloudProviders(DeploymentModel deploymentModel) {
        Map<String, List<CloudProvider>> result = new HashMap<>();

        VMRequirementSet globalRequirements= deploymentModel.getGlobalVMRequirementSet();
        for (VM vm : getVMList(deploymentModel)) {
            result.put(vm.getName(), new ArrayList<>());
            ProviderRequirement providerRequirement = retrieveProviderRequirement(vm.getVmRequirementSet(), globalRequirements);
            EList<CloudProvider> providers = providerRequirement != null ? providerRequirement.getProviders() : null;
            if (CollectionUtils.isNotEmpty(providers)) {
                result.get(vm.getName()).addAll(providers);
            }
        }
        return result;
    }

    private List<CloudProvider> getAsList(Map<String, List<CloudProvider>> cloudProviders){
        List<CloudProvider> result = new ArrayList<>();
        for (String s : cloudProviders.keySet()) {
            result.addAll(cloudProviders.get(s));
        }
        return result;
    }

    private void parseDeploymentModel(DeploymentModel deploymentModel, PaaSageConfigurationWrapper configurationWrapper, Set<String> preferedProviders) {

        createVirtualMachines(deploymentModel, configurationWrapper, preferedProviders);
        createApplicationComponents(deploymentModel, configurationWrapper);


        //parseExternalComponents(pim.getExternalComponents(), configuration); //TODO HOW TO PROCESS EXTERNAL COMPONENTS

        resolveContaimentDependencyInstances(deploymentModel.getHostingInstances(), configurationWrapper.getPaasageConfiguration());

        resolveContaimentDependencies(deploymentModel, configurationWrapper.getPaasageConfiguration());


/*

        PSZKUP - TODO - do odkomentowania

        resolveContaimentDependencyInstances(pim.getHostingInstances(), configuration);

        resolveContaimentDependencies(pim, configuration); //TODO A DEMAND attribute is expected ending in "Host" for considering this relationship

        resolveCommunicationDependencyInstances(pim.getCommunicationInstances(), configuration);

        resolveCommunicationDependencies(pim, configuration);

        checkGivenSolutionByUser(pim, configurationWrapper);
*/

    }

    private void createVirtualMachines(DeploymentModel deploymentModel, PaaSageConfigurationWrapper configurationWrapper, Set<String> preferedProviders) {
        List<VM> vms= getVMList(deploymentModel);
        List<VMInstance> vmInstances= getVMInstancesList(deploymentModel);

        //TODO - moze to znaczyc, ze obslugujemy tylko tych providerow ??
        PaasageConfiguration configuration = configurationWrapper.getPaasageConfiguration();
        boolean addNewCandidates = configuration.getProviders().size()==0;

        VMRequirementSet globalVMRequirementSet = deploymentModel.getGlobalVMRequirementSet();

        log.info(" **		Parsing VMInstances");
        parseVMInstances(vmInstances, configurationWrapper, addNewCandidates, globalVMRequirementSet, preferedProviders);

        log.info(" **		Parsing VMs");
        parseVMs(vms, configurationWrapper, addNewCandidates, globalVMRequirementSet, preferedProviders);
    }


    private void createApplicationComponents(DeploymentModel deploymentModel, PaaSageConfigurationWrapper configurationWrapper) {
        List<eu.paasage.camel.deployment.Component> components= getComponentsList(deploymentModel);
        List<ComponentInstance> componentInstances= getComponentInstancesList(deploymentModel);

        PaasageConfiguration configuration = configurationWrapper.getPaasageConfiguration();
        RequirementModel requirements = ((CamelModel) deploymentModel.eContainer()).getRequirementModels().get(0);

        log.info("**		Parsing Component Instances");
        configuration.getComponents().addAll(parseComponentInstances(componentInstances));

        log.info("**		Parsing Components");
        configuration.getComponents().addAll(parseComponents(components, componentInstances, requirements));
    }

    private void resolveContaimentDependencyInstances(EList<HostingInstance> hostingRelationships, PaasageConfiguration configuration) {
        if (CollectionUtils.isEmpty(hostingRelationships)){
            log.warn("Empty hosting relationships");
        }

        for (HostingInstance hosting : hostingRelationships) {
            InternalComponentInstance client = (InternalComponentInstance) hosting.getRequiredHostInstance().eContainer();

            Optional<ApplicationComponent> clientAppComponentOpt = paasageConfigurationUtilsService.searchApplicationComponentById(configuration.getComponents(), client.getName());

            if (clientAppComponentOpt.isPresent()) {
                ApplicationComponent clientAppComponent = clientAppComponentOpt.get();

                if (hosting.getProvidedHostInstance().eContainer() instanceof VMInstance) {
                    VMInstance vmInstance = (VMInstance) hosting.getProvidedHostInstance().eContainer();

                    log.debug("DeployementModelParser - resolveContaimentDependencyInstances - VM Instance name " + vmInstance.getName() + "!");

                    Optional<VirtualMachine> virtualMachineOpt = paasageConfigurationUtilsService.searchVMById(configuration.getVms(), vmInstance.getName());
                    if (virtualMachineOpt.isPresent()) {
                        VirtualMachine vm = virtualMachineOpt.get();

                        log.debug("DeployementModelParser - resolveContaimentDependencyInstances - VM Instance created " + vm);
                        log.debug("DeployementModelParser - resolveContaimentDependencyInstances - Client component " + clientAppComponent + " Name " + client.getName());
                        clientAppComponent.getRequiredProfile().add(vm.getProfile());
                        clientAppComponent.setVm(vm);

                        log.debug("DeployementModelParser - resolveContaimentDependencyInstances - VM Instance dependency add between " + client.getName() + " and " + vmInstance.getName() + "!");
                    }
                } else {
                    //I assume that it is a component
                    ComponentInstance provider = (ComponentInstance) hosting.getProvidedHostInstance().eContainer();
                    Optional<ApplicationComponent> applicationComponentOpt = paasageConfigurationUtilsService.searchApplicationComponentById(configuration.getComponents(), provider.getName());
//                    if (providerAppComponent != null) {
/*
                        TODO - pszkup - begin
                        RequiredFeature rf = builProvidedContaimentPortFeature(hosting.getName(), providerAppComponent);
                        rf.setContaiment(true);

                        clientAppComponent.getRequiredFeatures().add(rf);
                        TODO - pszkup - end
*/
                        log.debug("DeployementModelParser - resolveContaimentDependencyInstances - Dependency between " + client.getName() + " and " + provider.getName() + " created!");
//                    }
                }
            }
        }
    }

    private void resolveContaimentDependencies(DeploymentModel pim, PaasageConfiguration configuration) {

        if (CollectionUtils.isEmpty(pim.getHostings())){
            log.warn("Empty Hostings");
        }

        for(Hosting hosting: pim.getHostings()) {

            InternalComponent client= (InternalComponent) hosting.getRequiredHost().eContainer();

            List<ComponentInstance> filtredComponentInstances= PaasageModelTool.getComponentInstancesByTypeId(PaasageModelTool.getComponentInstancesList(pim), client.getName());

            if(filtredComponentInstances.size()>0) {

                List<HostingInstance> filtredHostingInstances= PaasageModelTool.getHostingInstanceByTypeId(pim.getHostingInstances(), hosting.getName());
                for (ComponentInstance instance : filtredComponentInstances) {
                    if (!PaasageModelTool.existHostingInstanceForComponentInstance(filtredHostingInstances, instance)) {
                        defineContainmentDependency(instance.getName(), configuration, hosting);
                    }
                }
            } else {
                defineContainmentDependency(client.getName(), configuration, hosting);
            }
        }
    }

    protected void defineContainmentDependency(String clientId, PaasageConfiguration configuration, Hosting hosting) {
        Optional<ApplicationComponent> clientAppComponentOpt = paasageConfigurationUtilsService.searchApplicationComponentById(configuration.getComponents(), clientId);

        if (clientAppComponentOpt.isPresent()){
            ApplicationComponent clientAppComponent = clientAppComponentOpt.get();

            if (hosting.getProvidedHost().eContainer() instanceof VM) {
                VM vm = (VM) hosting.getProvidedHost().eContainer();

                log.debug("DeployementModelParser - defineContainmentDependency - VM type name " + vm.getName() + "!");

                List<VirtualMachineProfile> profiles = vmProfiles.get(vm.getName());
                for (VirtualMachineProfile vmp : profiles) {

                    //TODO CREATE A MAPPING FOR THE VM AND THEIR RELATED VM PROFILES
                    //VirtualMachineProfile vmp= PaasageModelTool.searchVMProfileById(configuration.getVmProfiles(), vm.getName()); //TODO CHANGED BY VM NAME
                    log.debug("DeployementModelParser - defineContainmentDependency - Client component " + clientAppComponent + " Name " + clientId);
                    clientAppComponent.getRequiredProfile().add(vmp);

                    log.debug("DeployementModelParser - defineContainmentDependency - VM dependency add between " + clientId + " and " + vmp.getCloudMLId() + "!");
                }
            } else {
                //I assume that it is a component
                eu.paasage.camel.deployment.Component provider = (eu.paasage.camel.deployment.Component) hosting.getProvidedHost().eContainer();

                Optional<ApplicationComponent> providerAppComponentOpt = paasageConfigurationUtilsService.searchApplicationComponentById(configuration.getComponents(), provider.getName());
/*
                TODO - pszkup - zakomentowalem
                RequiredFeature rf = builProvidedContaimentPortFeature(hosting.getName(), providerAppComponent);
                rf.setContaiment(true);

                log.debug("DeployementModelParser - resolveContaimentDependencies - Client component " + clientAppComponent + " Name " + clientId);

                clientAppComponent.getRequiredFeatures().add(rf);

                log.debug("DeployementModelParser - defineContainmentDependency - Dependency between " + clientId + " and " + provider.getName() + " created!");
                */
            }
        }
    }    
    

    private List<VM> getVMList(DeploymentModel dm) {
        List<VM> vms= new ArrayList<>();
        vms.addAll(dm.getVms());
        return vms;
    }

    private List<VMInstance> getVMInstancesList(DeploymentModel dm) {
        List<VMInstance> vms= new ArrayList<>();
        vms.addAll(dm.getVmInstances());

        for(ComponentInstance c: dm.getInternalComponentInstances()) {
            if(c instanceof VMInstance)
                vms.add((VMInstance) c);
        }
        return vms;
    }

    private List<eu.paasage.camel.deployment.Component> getComponentsList(DeploymentModel pim) {
        List<eu.paasage.camel.deployment.Component> components= new ArrayList<>();
        components.addAll(pim.getInternalComponents());
        return components;
    }

    private List<ComponentInstance> getComponentInstancesList(DeploymentModel pim) {
        List<ComponentInstance> components= new ArrayList<>();
        components.addAll(pim.getInternalComponentInstances());

        for(ComponentInstance c:pim.getInternalComponentInstances()) {
            if(!(c instanceof VMInstance )) {
                components.add(c);
            }
        }
        return components;
    }

    private void parseVMInstances(List<VMInstance> vms, PaaSageConfigurationWrapper configurationWrapper, boolean addNewCandidates, VMRequirementSet globalVMRequirements, Set<String> preferedProviders) {
        PaasageConfiguration configuration = configurationWrapper.getPaasageConfiguration();

        for (VMInstance vmInstance : vms) {
            log.info("**			Parsing VM Instance: " + vmInstance.getName());

            Optional<VirtualMachineProfile> virtualMachineProfileOpt = paasageConfigurationUtilsService.searchVMProfileById(configuration.getVmProfiles(), vmInstance.getName());

            VirtualMachineProfile vmp = virtualMachineProfileOpt.orElseGet(() -> parseVM((VM) vmInstance.getType(), configurationWrapper, addNewCandidates, globalVMRequirements, preferedProviders));
//
//
//            VirtualMachineProfile vmp =  searchVMProfileById(configuration.getVmProfiles(), vmInstance.getName());
//            if (vmp == null) {
//                vmp = parseVM((VM) vmInstance.getType(), configurationWrapper, addNewCandidates, globalVMRequirements);
//            }

            if (vmp != null) {
                VirtualMachine instance = buildVM(vmp, vmInstance);
                configuration.getVms().add(instance);
                log.debug("DeployementModelParser - parseVMInstances - Vm Instance " + instance.getId() + " added!");
            }
        }
    }

    protected VirtualMachine buildVM(VirtualMachineProfile vmp, VMInstance vmInstace) {
        VirtualMachine virtualMachine= applicationFactory.createVirtualMachine();
        virtualMachine.setId(vmInstace.getName());
        virtualMachine.setProfile(vmp);
        return virtualMachine;
    }

    private void parseVMs(List<VM> vms, PaaSageConfigurationWrapper configurationWrapper, boolean addNewCandidates, VMRequirementSet globalVMRequirements, Set<String> preferedProviders) {
        for (VM vm : vms) {
            log.info("parseVMs -	Parsing VM Type: " + vm.getName());
            parseVM(vm, configurationWrapper, addNewCandidates, globalVMRequirements, preferedProviders);
        }
    }

    protected VirtualMachineProfile parseVM(VM vm, PaaSageConfigurationWrapper configurationWrapper, boolean addNewCandidates, VMRequirementSet globalVMRequirements, Set<String> preferedProviders) {

        String pcId = configurationWrapper.getPaasageConfiguration().getId();

        List<VirtualMachineProfile> result = new ArrayList<>();

        log.info("**			Parsing VM Type: " + vm.getName());

        LocationRequirement locationRequirement = retrieveLocationRequirement(vm.getVmRequirementSet(), globalVMRequirements);
        EList<Location> locations = locationRequirement != null ? locationRequirement.getLocations() : null;

        ProviderRequirement providerRequirement = retrieveProviderRequirement(vm.getVmRequirementSet(), globalVMRequirements);
        EList<CloudProvider> providers = providerRequirement != null ? providerRequirement.getProviders() : null;

        QuantitativeHardwareRequirement hardware = retrieveQuantitativeHardwareRequirement(vm.getVmRequirementSet(), globalVMRequirements);

        OSOrImageRequirement osImage = retrieveOSOrImageRequirement(vm.getVmRequirementSet(), globalVMRequirements);

        VirtualMachineProfile vmp = null;

        if (locations != null) {
            log.debug("**			Analysing locations: " + locations.size());
            for (Location location : locations) {
                if (providers != null) {
                    for (CloudProvider provider : providers) {

                        ProviderModel providerModel = provider.getProviderModel();
                        ProviderType pt = getProviderType(configurationWrapper, provider);

                        vmp = getVirtualMachineProfile(vm, configurationWrapper, preferedProviders, hardware, osImage, location, pt, providerModel);

//                        storeRelatedProviderModel(providerModel, pcId, provider);
                    }
                } else {
                    for (ProviderType pt : database.loadProviderTypes().getTypes()) {
                        ProviderModel providerModel = database.loadPM(pt.getId());
                        vmp = getVirtualMachineProfile(vm, configurationWrapper, preferedProviders, hardware, osImage, location, pt, providerModel);
                    }
                }
            }
        } else if (providers != null) {
            for (CloudProvider provider : providers) {

                ProviderModel providerModel = provider.getProviderModel();
                ProviderType pt = getProviderType(configurationWrapper, provider);

                vmp = getVirtualMachineProfile(vm, configurationWrapper, preferedProviders, hardware, osImage, null, pt, providerModel);
            }
        } else {
            log.debug("**			Creating VM ID ");
            for (ProviderType pt : database.loadProviderTypes().getTypes()) {
                ProviderModel providerModel = database.loadPM(pt.getId());

                vmp = getVirtualMachineProfile(vm, configurationWrapper, preferedProviders, hardware, osImage, null, pt, providerModel);
            }
        }

        return vmp;
    }

    private ProviderType getProviderType(PaaSageConfigurationWrapper configurationWrapper, CloudProvider provider) {
        String providerTypeId = provider.getName();
        return searchProviderTypeById(providerTypeId, configurationWrapper);
    }

    private VirtualMachineProfile getVirtualMachineProfile(VM vm, PaaSageConfigurationWrapper configurationWrapper, Set<String> preferedProviders, QuantitativeHardwareRequirement hardware, OSOrImageRequirement osImage, Location location, ProviderType pt, ProviderModel providerModel) {
        VirtualMachineProfile vmp = null;

        if (providerModel == null) {
            log.info("Provider model is null");
            return null;
        }

        if (!checkPreferedProviders(providerModel, preferedProviders)){
            log.info("Provider model for {} not in prefered providers set", providerModel.getName());
            return null;
        }

        List<Flavour> flavours = camelModelService.convertToFlavours(providerModel);
        for (Flavour flavour : flavours) {
            if (checkRequirements(hardware, flavour)) {
                vmp = getVirtualMachineProfile(vm, configurationWrapper, hardware, osImage, location, pt, providerModel, flavour);
            } else {
                log.info("Requirements failed for flavour: " + flavour.getVmTypeName());
            }
        }
        return vmp;
    }

    private VirtualMachineProfile getVirtualMachineProfile(VM vm, PaaSageConfigurationWrapper configurationWrapper, QuantitativeHardwareRequirement hardware, OSOrImageRequirement osImage, Location location, ProviderType pt, ProviderModel providerModel, Flavour flavour) {
        String locationId = location != null ? location.getId() : "";
        String hardwareId = hardware != null ? hardware.getName() : "";
        String osImageId = osImage != null ? osImage.getName() : "";

        String vmTypeId = getVMProfileId(locationId, pt.getId(), hardwareId, osImageId, vm.getName(), flavour.getVmTypeName());
        log.debug("DeployementModelParser - parseVMs - Vm Type Id " + vmTypeId + "!");


        Optional<VirtualMachineProfile> virtualMachineProfileOpt = paasageConfigurationUtilsService.searchVMProfileById(configurationWrapper.getPaasageConfiguration().getVmProfiles(), vmTypeId);

        VirtualMachineProfile vmp = virtualMachineProfileOpt.orElse(null);

        if (vmp == null) {
            log.debug("DeployementModelParser - parseVMs - Adding Vm Type " + vmTypeId + " with provider: " + pt.getId());

            vmp = buildVMProfile(vm, location, pt, hardware, osImage, vmTypeId, configurationWrapper, flavour, providerModel);
            if (vmp != null) {
                configurationWrapper.getPaasageConfiguration().getVmProfiles().add(vmp);

                List<VirtualMachineProfile> virtualMachineProfiles = vmProfiles.computeIfAbsent(vm.getName(), k -> new ArrayList<>());
                virtualMachineProfiles.add(vmp);
            }
        }

        return vmp;
    }

    private LocationRequirement retrieveLocationRequirement(VMRequirementSet vmRequirementSet, VMRequirementSet globalVMRequirements) {
        LocationRequirement result = null;

        if (vmRequirementSet != null && vmRequirementSet.getLocationRequirement() != null && vmRequirementSet.getLocationRequirement().getLocations().size() > 0) {
            log.debug("**			Considering Location requirements");
            result = vmRequirementSet.getLocationRequirement();
        }
        if (result == null && globalVMRequirements != null && globalVMRequirements.getLocationRequirement() != null && globalVMRequirements.getLocationRequirement().getLocations().size() > 0) {
                log.debug("**			Considering Global Location requirements");
                result = globalVMRequirements.getLocationRequirement();
        }
        return result;
    }

    private ProviderRequirement retrieveProviderRequirement(VMRequirementSet vmRequirementSet, VMRequirementSet globalVMRequirements) {
        ProviderRequirement result = null;

        if (vmRequirementSet != null && vmRequirementSet.getProviderRequirement() != null && vmRequirementSet.getProviderRequirement().getProviders().size() > 0) {
            log.debug("**			Considering Provider requirements");
            result = vmRequirementSet.getProviderRequirement();
        }
        if (result == null && globalVMRequirements != null && globalVMRequirements.getProviderRequirement() != null && globalVMRequirements.getProviderRequirement().getProviders().size() > 0) {
            log.debug("**			Considering Global Provider requirements");
            result = globalVMRequirements.getProviderRequirement();
        }
        return result;
    }

    private QuantitativeHardwareRequirement retrieveQuantitativeHardwareRequirement(VMRequirementSet vmRequirementSet, VMRequirementSet globalVMRequirements) {
        QuantitativeHardwareRequirement result = null;

        if (vmRequirementSet != null && vmRequirementSet.getQuantitativeHardwareRequirement() != null) {
            log.debug("**			Considering Hardware requirements");
            result = vmRequirementSet.getQuantitativeHardwareRequirement();
        }
        if (result == null && globalVMRequirements != null && globalVMRequirements.getQuantitativeHardwareRequirement() != null) {
            log.debug("**			Considering Global Hardware requirements");
            result = globalVMRequirements.getQuantitativeHardwareRequirement();
        }
        return result;
    }

    private OSOrImageRequirement retrieveOSOrImageRequirement(VMRequirementSet vmRequirementSet, VMRequirementSet globalVMRequirements) {
        OSOrImageRequirement result = null;

        if (vmRequirementSet != null && vmRequirementSet.getOsOrImageRequirement() != null) {
            log.debug("**			Considering OS-Image requirements");
            result = vmRequirementSet.getOsOrImageRequirement();
        }
        if (result == null && globalVMRequirements != null && globalVMRequirements.getOsOrImageRequirement() != null) {
            log.debug("**			Considering Global OS-Image requirements");
            result = globalVMRequirements.getOsOrImageRequirement();
        }
        return result;
    }

    private boolean checkRequirements(QuantitativeHardwareRequirement hardware, Flavour flavour) {
        return new QuantitativeHardwareRequirementFilter(hardware).test(flavour);
    }

    private boolean checkPreferedProviders(ProviderModel providerModel, Set<String> preferedProviders) {
        //TODO - czy w preferedProviders beda nazwy providerow?? Czy moze trzeba tu pobrac cos innego??? MyAmazonPM vs AmazonEC2Provider
        return (CollectionUtils.isEmpty(preferedProviders) || preferedProviders.contains(providerModel.getName()));
    }

    private List<ApplicationComponent> parseComponentInstances(List<ComponentInstance> components) {
        log.debug("DeploymentModelProcessor - parseComponentInstances - component instace size " + components.size());

        List<ApplicationComponent> result = new ArrayList<>(components.size());
        for (ComponentInstance component : components) {
            log.info("**			Parsing component instance: " + component.getName());
            result.add(buildApplicationComponent(component));
        }

        return result;
    }

    private List<ApplicationComponent> parseComponents(List<eu.paasage.camel.deployment.Component> components, List<ComponentInstance> instances, RequirementModel requirements) {
        log.debug("DeploymentModelProcessor - parseComponents - components size " + components.size());

        List<ApplicationComponent> result = new ArrayList<>(components.size());
        for (eu.paasage.camel.deployment.Component component : components) {
            //Only processes the Component if there are not instances
            if (!existComponentInstance(component, instances)) {
                if (!(component instanceof VM)) {
                    log.info("**			Parsing component: " + component.getName());
                    result.add(buildApplicationComponent(component, requirements));
                }
            }
        }
        return result;
    }

    private ApplicationComponent buildApplicationComponent(eu.paasage.camel.deployment.Component component, RequirementModel requirements) {
        ApplicationComponent apc = applicationFactory.createApplicationComponent();
        log.debug("DeploymentModelParser- buildApplicationComponent- The component " + component.getName());

        String id = component.getName();
        apc.setCloudMLId(id);

        apc.setMin(DEFAULT_MIN_INSTANCE_NUMBER);
        apc.setMax(DEFAULT_MAX_INSTANCE_NUMBER);

        HorizontalScaleRequirement horScaleReq = getScaleRequirementForComponent(requirements.getRequirements(), component);
        if (horScaleReq != null) {
            apc.setMin(horScaleReq.getMinInstances());
            int maxInstances = horScaleReq.getMaxInstances();
            if (maxInstances > 0) {
                // we assume that 0 means not defined
                apc.setMax(maxInstances);
            }
        }

        for (ProvidedCommunication pc : component.getProvidedCommunications()) {
            apc.getFeatures().add(pc.getName());
        }

        for (ProvidedHost ph : component.getProvidedHosts()) {
            apc.getFeatures().add(ph.getName());
        }

        return apc;
    }

    private ApplicationComponent buildApplicationComponent(ComponentInstance component) {
        ApplicationComponent apc = applicationFactory.createApplicationComponent();
        log.debug("DeploymentModelParser- buildApplicationComponent- The component instance " + component.getName());

        String id = component.getName();
        apc.setCloudMLId(id);

        for (ProvidedCommunicationInstance pc : component.getProvidedCommunicationInstances()) {
            apc.getFeatures().add(pc.getName());
        }

        for (ProvidedHostInstance ph : component.getProvidedHostInstances()) {
            apc.getFeatures().add(ph.getName());
        }

        return apc;
    }

    private HorizontalScaleRequirement getScaleRequirementForComponent(EList<Requirement> reqs, eu.paasage.camel.deployment.Component component) {
        String name = component.getName();

        for (Requirement req : reqs) {
            if (req instanceof HorizontalScaleRequirement) {
                HorizontalScaleRequirement hsr = (HorizontalScaleRequirement) req;
                if (hsr.getComponent().getName().equals(name))
                    return hsr;
            }
        }

        return null;
    }

    public ProviderType searchProviderTypeById(String providerName, PaaSageConfigurationWrapper pcw) {
        for (ProviderType pt : pcw.getProviderTypes().getTypes()) {
            if (pt.getId().equals(providerName))
                return pt;
        }
        return null;
    }

    protected VirtualMachineProfile buildVMProfile(VM vm, Location location, ProviderType pt, HardwareRequirement hardwareRequirement,
                                                   OSOrImageRequirement osImagerReq, String vmTypeId, PaaSageConfigurationWrapper configurationWrapper,
                                                   Flavour flavour, ProviderModel providerModel) {

        LocationUpperware locationUpperware = null;

        log.debug("DeploymentModelParser- buildVMProfile- The VM type: " + vmTypeId);

        if (location != null) {
            log.debug("DeploymentModelParser- buildVMProfile- The Location name: " + location.getId());
            String location1 = getLocation(location);
            log.info("PSZKUP location: {}", location1);
            locationUpperware = getLocation(location, configurationWrapper);
        }

        if (location != null && locationUpperware == null) {
            log.warn("DeploymentModelParser- - buildVMProfile - The Location " + location.getId() + " does not exist in the DB. TheVM profile can not be created!");
            return null;
        } else {
            log.debug("DeploymentModelParser- buildVMProfile- Location found!");
        }


        VirtualMachineProfile vmp = applicationFactory.createVirtualMachineProfile();
        vmp.setCloudMLId(vmTypeId);
        vmp.setRelatedCloudVMId(vm.getName());

        log.debug("DeploymentModelParser- VM id " + vmTypeId);

        if (locationUpperware != null)
            vmp.setLocation(locationUpperware);

        if (pt != null) {
            String providerTypeId = pt.getId();
            PaasageConfiguration configuration = configurationWrapper.getPaasageConfiguration();

            //Look for the provider
            Provider providerPattern = applicationFactory.createProvider();
            providerPattern.setType(pt);

            Provider providerUpperware = null;

            if (locationUpperware != null) {
                providerPattern.setLocation(locationUpperware);
                providerUpperware = PaasageModelTool.searchProviderWithLocationInList(configuration.getProviders(), providerPattern);
            }

            if (providerUpperware == null) {
                providerUpperware = providerPattern;

                String locationId = (location != null && location.getId() != null) ? location.getId() : "";

                String providerId = PaasageModelTool.buildProviderId(providerTypeId, locationId);
                providerUpperware.setId(providerId);
                configuration.getProviders().add(providerUpperware);

                //PSZKUP TODO to  nie moze tutaj byc bo leci org.eclipse.net4j.util.lifecycle.LifecycleException: Not active: CDOTransactionImpl
                // na kolejnej probie pobrania z camela w tym przypadku na:
                // String os = osReq.getOs();
//                storeRelatedProviderModel(providerModel, configuration.getId(), providerId);


                log.debug("DeploymentModelParser- buildVMProfile -Provider " + providerId + " created!");
            }

            ProviderDimension pd = createProviderDimension(providerUpperware);
            vmp.getProviderDimension().add(pd);

        } else {
            log.error("DeploymentModelProcessor- buildVMProfile- The Provider does not exist in the DB. The VM profile can not be created!");
        }

        //OS
        if (osImagerReq != null){
            if (osImagerReq instanceof OSRequirement) {
                OSRequirement osReq = (OSRequirement) osImagerReq;
                String os = osReq.getOs();
                    osImagerReq.getName();
                OS theOs = getOSFromNameAndArchitecture(os, osReq.isIs64os(), configurationWrapper);
                if (theOs != null) {
                    vmp.setOs(cloneOS(theOs));
                } else {
                    log.warn("DeploymentModelParser- The OS " + os + " does not exist in the DB. The os of the VM with id " + vmTypeId + " will be not set");
                }
            } else if (osImagerReq instanceof ImageRequirement) {
                ImageRequirement imageReq = (ImageRequirement) osImagerReq;
                vmp.setImage(createImageUpperware(imageReq));
            }
        }

        if (flavour != null) {
//            vmp.setFlavourName(flavour.getVmTypeName());
            vmp.setCpu(createCpu(flavour));
            vmp.setStorage(createStorage(flavour));
            vmp.setMemory(createMemory(flavour));
        }

        return vmp;
    }


    /**
     * Searches for an operating system with a given name and architecture type
     * @param osName The operating system name
     * @param is64Bits Indicates if the OS is 64 bits
     * @param pcw The paasage configuration wrapper containing the operating systems
     * @return The operating system or null if it does not exist
     */
    private OS getOSFromNameAndArchitecture(String osName, boolean is64Bits, PaaSageConfigurationWrapper pcw) {
        log.debug("*************** new req");

        for (OS os : pcw.getOperatingSystems().getOss()) {

            boolean is64 = os.getArchitecture().getValue() == OSArchitectureEnum.SIXTY_FOUR_BITS_VALUE;

            log.debug("*************** ==> " + os.getName() + " ==? " + osName + " = " + os.getName().equals(osName));
            log.debug("*************** ==> " + os.getArchitecture().getValue() + " ==? " + OSArchitectureEnum.SIXTY_FOUR_BITS_VALUE);
            log.debug("***************==> " + is64 + " ==? " + is64Bits);

            if (os.getName().equals(osName) && is64 == is64Bits) {
                log.debug("*************** SUCCESS");
                return os;
            }
        }
        return null;
    }

    private OS cloneOS(OS os) {
        OS copy = typesPaasageFactory.createOS();

        copy.setArchitecture(os.getArchitecture());
        copy.setName(os.getName());
        copy.setTypeId(os.getTypeId());
        copy.setVers(os.getVers());

        return copy;
    }

    public static String getLocation(Location loc) {
        String locationName = loc.getId();
        log.debug("***************looking for location {}", locationName);

        if(loc instanceof CloudLocation) {
            //TODO Check CloudLocation ??
            CloudLocation cloudLocation= (CloudLocation) loc;

            GeographicalRegion geographicalRegion = cloudLocation.getGeographicalRegion();
            if(geographicalRegion !=null) {
                if(geographicalRegion instanceof Country) {
                    String countryName = geographicalRegion.getName();
                    return countryName;
                    // TODO
                } else {
                    //It is a continent //TODO TO CHECK THIS
                    String continentName= geographicalRegion.getName();
                    return continentName;
                    // TODO
                }
            }
        } else if(loc instanceof Country) {
            return locationName;
            // TODO - country name
        } else {
            //It is a continent
            return locationName;
            // TODO - continent name
        }
        return null;
    }


//TODO - cale location moze wyniesc do osobnego serwisu??
    private LocationUpperware getLocation(Location loc, PaaSageConfigurationWrapper pcw) {
        String locationName= loc.getId();
        log.debug("***************looking for location "+locationName);

        if(loc instanceof CloudLocation) {
            //TODO Check CloudLocation ??
            CloudLocation cloudLocation = (CloudLocation) loc;
            List<CityUpperware> cities = searchCityByName(locationName, pcw.getLocations().getLocations());

            GeographicalRegion geographicalRegion = cloudLocation.getGeographicalRegion();
            if (cities.size() == 1) {
                return cities.get(0);
            } else if (cities.size() > 1) {
                if (geographicalRegion != null) {
                    if (geographicalRegion instanceof eu.paasage.camel.location.Country) {
                        LocationUpperware city = getCityByCountry(cities, geographicalRegion.getName());
                        if (city != null) return city;
                    } else {
                        //It is a continent //TODO TO CHECK THIS
                        LocationUpperware city = getCityByContinent(cities, geographicalRegion.getName());
                        if (city != null) return city;
                    }
                }
            } else if(geographicalRegion !=null) {
                //Try with regions
                String regionName= geographicalRegion.getName();

                if(geographicalRegion instanceof eu.paasage.camel.location.Country) {
                    //TODO TO USE ALTERNATIVE NAMES OF theCountry
                    return searchCountryByName(regionName, pcw.getLocations().getLocations());
                } else {
                    //It is a continent
                    return searchContinentByName(regionName, pcw.getLocations().getLocations());
                }
            }
        } else if(loc instanceof eu.paasage.camel.location.Country) {
            return searchCountryByName(locationName, pcw.getLocations().getLocations());
        } else {
            //It is a continent
            return searchContinentByName(locationName, pcw.getLocations().getLocations());
        }
        return null;
    }

    private LocationUpperware getCityByContinent(List<CityUpperware> cities, String continentName) {
        for (CityUpperware city : cities) {
            ContinentUpperware continent = city.getCountry().getContinent();
            if (continent.getName().equals(continentName) || isInList(continent.getAlternativeNames(), continentName)) {
                return city;
            }
        }
        return null;
    }

    private LocationUpperware getCityByCountry(List<CityUpperware> cities, String countryName) {
        //TODO TO USE ALTERNATIVE NAMES OF theCountry
        for (CityUpperware city : cities) {
            CountryUpperware country = city.getCountry();
            if (country.getName().equals(countryName) || isInList(country.getAlternativeNames(), countryName)) {
                return city;
            }
        }
        return null;
    }

    private List<CityUpperware> searchCityByName(String cityName, EList<LocationUpperware> locations) {
        List<CityUpperware> cities = locations.stream()
                .peek(loc -> log.debug("***************Comparing location name " + loc.getName() + " with city " + cityName))
                .peek(loc -> log.debug("***************Alternative names size " + loc.getAlternativeNames().size()))
                .filter(loc -> loc instanceof CityUpperware)
                .filter(loc -> cityName.equals(loc.getName()) || isInList(loc.getAlternativeNames(), cityName))
                .map(locationUpperware -> (CityUpperware) locationUpperware)
                .collect(Collectors.toList());

        return cities;
    }

    private CountryUpperware searchCountryByName(String countryName, EList<LocationUpperware> locations) {
        Optional<CountryUpperware> first = locations.stream()
                .peek(loc -> log.debug("***************Comparing country name " + loc.getName() + " with " + countryName))
                .filter(loc -> loc instanceof CountryUpperware)
                .filter(loc -> countryName.equals(loc.getName()) || isInList(loc.getAlternativeNames(), countryName))
                .map(loc -> (CountryUpperware) loc)
                .findFirst();

        return first.orElse(null);
    }

    private ContinentUpperware searchContinentByName(String continentName, EList<LocationUpperware> locations) {
        Optional<ContinentUpperware> first = locations.stream()
                .peek(loc -> log.debug("***************Comparing continent name "+loc.getName()+" with "+continentName))
                .filter(loc -> loc instanceof ContinentUpperware)
                .filter(loc -> continentName.equals(loc.getName()) || isInList(loc.getAlternativeNames(), continentName))
                .map(loc -> (ContinentUpperware) loc)
                .findFirst();

        return first.orElse(null);
    }


    private boolean isInList(List<String> list, String value){
        return list.stream().anyMatch(s -> s.equals(value));
    }


    private ProviderDimension createProviderDimension(Provider providerUpperware) {
        ProviderDimension pd = applicationFactory.createProviderDimension(); //TODO THE METRIC ID ???
        pd.setValue(0); //TODO THIS VALUE HAS TO BE DEFINED WITH THE PRICE CALCULATION
        pd.setProvider(providerUpperware);
        return pd;
    }

    private ImageUpperware createImageUpperware(ImageRequirement imageReq) {
        String imageId = imageReq.getImageId();
        ImageUpperware image = applicationFactory.createImageUpperware();
        image.setId(imageId);
        return image;
    }

    private CPU createCpu(Flavour flavour) {
        if (flavour != null) {
            Integer coresInt = flavour.getCoresInt();
            if (coresInt != null) {
                CPU cpu = applicationFactory.createCPU();
                cpu.setCores(coresInt);
                //TODO - frequency is probably not used
                Integer cpuInt = flavour.getCpuInt();
                if (cpuInt != null) {
                    cpu.setValue(typesFactoryService.getDoubleValueUpperware(cpuInt));
                } else {
                    cpu.setValue(typesFactoryService.getDoubleValueUpperware(0));
                }
                return cpu;
            }
        }
        return null;
    }

    private Storage createStorage(Flavour flavour) {
        if (flavour !=  null) {
            Integer storageInt = flavour.getStorageInt();
            if (storageInt != null) {
                Storage storage = applicationFactory.createStorage();
                storage.setValue(typesFactoryService.getIntegerValueUpperware(storageInt));
                storage.setUnit(DataUnitEnum.GB);
                return storage;
            }
        }
        return null;
    }

    private Memory createMemory(Flavour flavour) {
        if (flavour !=  null) {
            Integer memoryInt = flavour.getMemoryInt();
            if (memoryInt != null) {
                Memory memory = applicationFactory.createMemory();
                memory.setValue(typesFactoryService.getIntegerValueUpperware(memoryInt));
                memory.setUnit(DataUnitEnum.MB);
                return memory;
            }
        }
        return null;
    }

    private String getVMProfileId(String location, String provider, String hardwareId, String osImageId, String vmID, String mappingName) {
        String id = "";

        if (!location.equals("")) {
            id += location + NAME_SEPARATOR;
        }

        if (!provider.equals("")) {
            id += provider + NAME_SEPARATOR;
        }

        if (!hardwareId.equals("")) {
            id += hardwareId + NAME_SEPARATOR;
        }

        if (!osImageId.equals("")) {
            id += osImageId + NAME_SEPARATOR;
        }

        if (!mappingName.equals("")){
            id += mappingName + NAME_SEPARATOR;
        }
//vm
        if("".equals(location) && "".equals(provider) && "".equals(hardwareId) && "".equals(osImageId) && "".equals(mappingName)) {
            id+=vmID+SUFFIX;
        } else {
            id+=NAME_SEPARATOR+vmID+SUFFIX;
        }
        return id;
    }

    public static VirtualMachineProfile searchVMProfileById(EList<VirtualMachineProfile> vmachines, String id) {
        for (VirtualMachineProfile vmp : vmachines) {
            if (vmp.getCloudMLId().equals(id))
                return vmp;
        }
        return null;
    }

    @Override
    public PaaSageVariable createPaaSageVariable(ApplicationComponent ac, VirtualMachineProfile vm, Provider provider, Variable var){
        PaaSageVariable pVar= applicationFactory.createPaaSageVariable();
        pVar.setCpVariableId(var.getId());
        pVar.setPaasageType(VariableElementTypeEnum.VIRTUAL_LOCATION);
        pVar.setRelatedComponent(ac);
        pVar.setRelatedVirtualMachineProfile(vm);
        pVar.setRelatedProvider(provider);
        return pVar;
    }

}
