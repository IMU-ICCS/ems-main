package eu.paasage.upperware.solvertodeployment.utils;

import camel.deployment.CommunicationInstance;
import camel.deployment.HostingInstance;
import camel.deployment.SoftwareComponentInstance;
import camel.deployment.VMInstance;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DataHolderNew {

    @Setter
    private int dmId;

    private List<SoftwareComponentInstance> componentInstancesToRegister = new ArrayList<>();

    private List<CommunicationInstance> communicationInstances = new ArrayList<>();

    private List<VMInstance> vmInstancesToRegister = new ArrayList<>();

    private List<HostingInstance> hostingInstancesToRegister = new ArrayList<>();

    //private List<ProviderModel> providerModel = new ArrayList<>();
}
