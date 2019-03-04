package eu.melodic.upperware.solvertodeployment.utils;

import camel.deployment.CommunicationInstance;
import camel.deployment.SoftwareComponentInstance;
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

}
