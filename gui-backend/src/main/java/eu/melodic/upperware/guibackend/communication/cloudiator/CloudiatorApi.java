package eu.melodic.upperware.guibackend.communication.cloudiator;

import io.github.cloudiator.rest.model.*;

import java.util.List;

public interface CloudiatorApi {
    Integer getDiscoveryStatusTotal();

    List<Hardware> getHardwareList();

    List<Location> getLocationList();

    List<Image> getImageList();

    List<Cloud> getCloudList();

    List<Function> getFunctionList();

    List<Node> getVMFromNodeList();

    List<Node> getFaasFromNodeList();

    void storeSecureVariable(String key, String value);
}
