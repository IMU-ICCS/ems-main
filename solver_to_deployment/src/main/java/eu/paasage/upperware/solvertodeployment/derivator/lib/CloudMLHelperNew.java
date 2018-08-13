package eu.paasage.upperware.solvertodeployment.derivator.lib;

import camel.deployment.*;

public class CloudMLHelperNew {


    private static int _globalCount = 0;
    private static int _newDMIdx = -1;

    private static int getGlobalCount() {
        return _globalCount++;
    }

    public static void resetGlobalCount() {
        _globalCount = 0;
    }

    public static void setGlobalDMIdx(int idx) {
        _newDMIdx = idx;
    }

    public static int getGlobalDMIdx() {
        return _newDMIdx;
    }

    private static String getGlobalSuffix() {
        return getGlobalDMIdx() + "_" + getGlobalCount();
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Internal Component Comnunication
    //////////////////////////////////////////////////////////////////////////////////////

    public static SoftwareComponent findProvidedComponentFromCommunication(Communication com) {
        return (SoftwareComponent) (com.getProvidedCommunication().eContainer());
    }

    public static SoftwareComponent findRequiredComponentFromCommunication(Communication com) {
        return (SoftwareComponent) (com.getRequiredCommunication().eContainer());
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Software Component Instance
    //////////////////////////////////////////////////////////////////////////////////////

    public static SoftwareComponentInstance createSCInstance(SoftwareComponent softwareComponent) {
        // Create Instance + name + type
        SoftwareComponentInstance softwareComponentInstance = DeploymentFactory.eINSTANCE.createSoftwareComponentInstance();
        softwareComponentInstance.setName(softwareComponent.getName() + "Instance_" + getGlobalSuffix());
        softwareComponentInstance.setType(softwareComponent);

        //Create ProvidedCommunicationInstance
        for (ProvidedCommunication providedCommunication : softwareComponent.getProvidedCommunications()) {
            ProvidedCommunicationInstance providedCommunicationInstance = DeploymentFactory.eINSTANCE.createProvidedCommunicationInstance();
            providedCommunicationInstance.setType(providedCommunication);
            providedCommunicationInstance.setName(providedCommunication.getName() + "ProvidedCommunicationInstance_" + getGlobalCount());
            softwareComponentInstance.getProvidedCommunicationInstances().add(providedCommunicationInstance);
        }

        //Create RequiredCommunicationInstance
        for (RequiredCommunication requiredCommunication : softwareComponent.getRequiredCommunications()) {
            RequiredCommunicationInstance requiredCommunicationInstance = DeploymentFactory.eINSTANCE.createRequiredCommunicationInstance();
            requiredCommunicationInstance.setType(requiredCommunication);
            requiredCommunicationInstance.setName(requiredCommunication.getName() + "ReqCommunicationInstance_" + getGlobalCount());
            softwareComponentInstance.getRequiredCommunicationInstances().add(requiredCommunicationInstance);
        }

        //Create RequiredHostInstance
        RequiredHostInstance requiredHostInstance = DeploymentFactory.eINSTANCE.createRequiredHostInstance();
        requiredHostInstance.setType(softwareComponent.getRequiredHost());
        requiredHostInstance.setName(softwareComponent.getName() + "RequiredHostInstance_" + getGlobalCount());
        softwareComponentInstance.setRequiredHostInstance(requiredHostInstance);

        return softwareComponentInstance;
    }

    //////////////////////////////////////////////////////////////////////////////////////
    // VM Instance
    //////////////////////////////////////////////////////////////////////////////////////

//    public static VMInstance createVMInstance(VM vm) {
//        // Create VMi
//        VMInstance vmInstance = DeploymentFactory.eINSTANCE.createVMInstance();
//        vmInstance.setType(vm);
//        vmInstance.setName(vm.getName() + "VMInstance_" + getGlobalSuffix());
//
//        // Create ProviderHostInstance
//        for (ProvidedHost providedHost : vm.getProvidedHosts()) {
//            ProvidedHostInstance providedHostInstance = DeploymentFactory.eINSTANCE.createProvidedHostInstance();
//            providedHostInstance.setName(providedHost.getName() +"ProvidedHostInstance_" + getGlobalCount());
//            providedHostInstance.setType(providedHost);
//            vmInstance.getProvidedHostInstances().add(providedHostInstance);
//        }
//
//        return vmInstance;
//    }
//
//    public static Attribute findVMType(ProviderModel providerModel) throws S2DException {
//        Attribute result = null;
//        if(providerModel == null ) {
//            throw new S2DException("Bad calling . Provider musn't not be null");
//        }
//        EList<Feature> subFeatures = providerModel.getRootFeature().getSubFeatures();
//        StringBuilder logTxt = new StringBuilder("\n * Start looking vmType for providerModel " + providerModel.getName());
//        logTxt.append(" \n  ** Scan SubFeature of provider model. Scanning ").append(subFeatures.size()).append(" elements");
//        for (Feature feature : subFeatures) {
//            EList<Attribute> attributes = feature.getAttributes();
//            logTxt.append(" ** Will scanning all attributes for feature ").append(feature.getName()).append(". Number of attributes : ").append(attributes.size());
//
//            for (Attribute attribute : attributes) {
//                logTxt.append("\n    *** Is attribute name equals vmType ? : ").append(attribute.getName()).append(" bla ").append(attribute.getValue());
//
//                if("VMType".equals(attribute.getName())) {
//                    result = attribute;
//                }
//            }
//        }
//        if(result == null)
//            throw new S2DException("Unable to find VMType . There is error in original model ! .Details :" + logTxt);
//        return result;
//    }

    //////////////////////////////////////////////////////////////////////////////////////
    // Hosting Instance
    //////////////////////////////////////////////////////////////////////////////////////

//    public static HostingInstance buildNewHostingInstance(String acName,VMInstance vmInstance, SoftwareComponentInstance softwareComponentInstance, Hosting hosting) {
//        // CreateHostingInstance
//        HostingInstance hostingInstance = DeploymentFactory.eINSTANCE.createHostingInstance();
//        hostingInstance.setName("VMto" + acName + "HostingInstance_" + getGlobalSuffix());
//        hostingInstance.setProvidedHostInstance(vmInstance.getProvidedHostInstances().get(0));
//        hostingInstance.getRequiredHostInstances().add(softwareComponentInstance.getRequiredHostInstance());
//        hostingInstance.setType(hosting);
//        return hostingInstance;
//    }
}
