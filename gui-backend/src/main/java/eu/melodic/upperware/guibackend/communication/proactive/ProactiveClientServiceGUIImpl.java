package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import cloud.morphemic.connectors.exception.ProactiveClientException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.*;

import java.util.Collections;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProactiveClientServiceGUIImpl implements ProactiveClientServiceGUI {

    private final ProactiveClientConnectorService proactiveClientConnectorService;

    @Override
    public int getNumberOfCurrentOffers() {
        try {
            return proactiveClientConnectorService.getLengthOfNodeCandidates().intValue();
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<PACloud> getAllClouds() {
        try {
            return proactiveClientConnectorService.fetchClouds();
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Image> getAllCloudImages(String cloudID) {
        try {
            return proactiveClientConnectorService.fetchImages(cloudID);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Hardware> getAllHardware() {
        try {
            return proactiveClientConnectorService.fetchHardware();
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Location> getAllLocation() {
        try {
            return proactiveClientConnectorService.fetchLocations();
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public EdgeNode registerNewEdgeNode(EdgeDefinition edgeDefinition, String jobId) {
        try {
            return proactiveClientConnectorService.registerNewEdgeNode(edgeDefinition, jobId);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public ByonNode registerNewByonNode(ByonDefinition byonNodeDefinition, String jobId, boolean automate) {
        try {
            return proactiveClientConnectorService.registerNewByonNode(byonNodeDefinition, jobId,automate);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<ByonNode> getByonNodeList(String jobId) {
        try {
            return proactiveClientConnectorService.fetchByonNodes(jobId);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<EdgeNode> getEdgeNodeList(String jobId) {
        try {
            return proactiveClientConnectorService.fetchEdgeNodes(jobId);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Job> getAllJobs() {
        try {
            return proactiveClientConnectorService.fetchJobs();
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<Deployment> getAllNodes() {
        try {
            return proactiveClientConnectorService.fetchNodes();
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    @Override
    public List<EmsDeploymentRequest> getAllMonitors() {
        try {
            return proactiveClientConnectorService.fetchMonitors();
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }    }

    @Override
    public boolean deleteByonNode(String byonId) {
        try {
            return proactiveClientConnectorService.deleteByonNodes(byonId);
        } catch (ProactiveClientException e) {
            e.printStackTrace();
            return false;
        }
    }
}
