package eu.paasage.upperware.solvertodeployment.derivator.lib;

import camel.deployment.*;

public class CloudMLHelper {


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
    // Software Component Instance
    //////////////////////////////////////////////////////////////////////////////////////

    public static SoftwareComponent findProvidedComponentFromCommunication(Communication com) {
        return (SoftwareComponent) (com.getProvidedCommunication().eContainer());
    }

    public static SoftwareComponent findRequiredComponentFromCommunication(Communication com) {
        return (SoftwareComponent) (com.getRequiredCommunication().eContainer());
    }

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
    // Hosting Instance
    //////////////////////////////////////////////////////////////////////////////////////

    public static HostingInstance buildNewHostingInstance(String acName, VMInstance vmInstance, SoftwareComponentInstance softwareComponentInstance, Hosting hosting) {
        // CreateHostingInstance
        HostingInstance hostingInstance = DeploymentFactory.eINSTANCE.createHostingInstance();
        hostingInstance.setName("VMto" + acName + "HostingInstance_" + getGlobalSuffix());
        hostingInstance.setProvidedHostInstance(vmInstance.getProvidedHostInstances().get(0));
        hostingInstance.getRequiredHostInstances().add(softwareComponentInstance.getRequiredHostInstance());
        hostingInstance.setType(hosting);
        return hostingInstance;
    }
}
