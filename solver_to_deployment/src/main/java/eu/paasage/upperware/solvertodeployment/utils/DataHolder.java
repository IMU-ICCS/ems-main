package eu.paasage.upperware.solvertodeployment.utils;

import camel.deployment.CommunicationInstance;
import camel.deployment.HostingInstance;
import camel.deployment.SoftwareComponentInstance;
import camel.deployment.VMInstance;
import camel.location.GeographicalRegion;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class DataHolder {

    @Setter
    private int dmId;

    private List<SoftwareComponentInstance> componentInstancesToRegister = new ArrayList<>();

    private List<CommunicationInstance> communicationInstances = new ArrayList<>();

    private List<GeographicalRegion> locationsToRegister = new ArrayList<>();

}
