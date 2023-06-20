package eu.melodic.upperware.guibackend.communication.proactive;

import cloud.morphemic.connectors.ProactiveClientConnectorService;
import cloud.morphemic.connectors.exception.ProactiveClientException;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
public class ProactiveClientServiceGUIImpl implements ProactiveClientServiceGUI {

    private final ProactiveClientConnectorService proactiveClientConnectorService;

    @Override
    public int getNumberOfCurrentOffers() {
        try {
            return proactiveClientConnectorService.getLengthOfNodeCandidates().intValue();
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return -1;
        }
    }

    @Override
    public List<PACloud> getAllClouds() {
        try {
            return proactiveClientConnectorService.fetchClouds();
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Image> getAllCloudImages(String cloudID) {
        try {
            return proactiveClientConnectorService.fetchImages(cloudID);
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Hardware> getAllHardware() {
        try {
            return proactiveClientConnectorService.fetchHardware();
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Location> getAllLocation() {
        try {
            return proactiveClientConnectorService.fetchLocations();
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public EdgeNode registerNewEdgeNode(EdgeDefinition edgeDefinition, String jobId) {
        try {
            return proactiveClientConnectorService.registerNewEdgeNode(edgeDefinition, jobId);
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public ByonNode registerNewByonNode(ByonDefinition byonNodeDefinition, String jobId, boolean automate) {
        try {
            return proactiveClientConnectorService.registerNewByonNode(byonNodeDefinition, jobId,automate);
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public List<ByonNode> getByonNodeList(String jobId) {
        try {
            return proactiveClientConnectorService.fetchByonNodes(jobId);
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<EdgeNode> getEdgeNodeList(String jobId) {
        try {
            return proactiveClientConnectorService.fetchEdgeNodes(jobId);
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
    }

    @Override
    public List<Job> getAllJobs() {
        Map<String, Deployment> deploymentsById = getAllNodes().stream()
                .collect(Collectors.toMap(
                        Deployment::getNodeName,
                        Function.identity()
                ));
        if (deploymentsById.isEmpty()) {
            return Collections.emptyList();
        }
        List<Job> jobs;
        try {
            jobs = proactiveClientConnectorService.fetchJobs();
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
        jobs.forEach(job -> job.getTasks().forEach(task -> {
            task.setDeployments(task.getDeployments().stream()
                    .map(deployment -> deploymentsById.get(deployment.getNodeName()))
                    .collect(Collectors.toList()));
        }));

        return jobs;
    }

    @Override
    public List<Deployment> getAllNodes() {
        Map<String, PACloud> cloudsById = getAllClouds().stream()
                .collect(Collectors.toMap(
                        PACloud::getCloudId,
                        Function.identity()
                ));
        if (cloudsById.isEmpty()) {
            return Collections.emptyList();
        }
        List<Deployment> deployments;
        try {
            deployments = proactiveClientConnectorService.fetchNodes();
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }
        return deployments.stream()
                .peek(deployment -> deployment.setPaCloud(cloudsById.get(deployment.getPaCloud().getCloudId())))
                .collect(Collectors.toList());
    }

    @Override
    public List<EmsDeploymentRequest> getAllMonitors() {
        try {
            return proactiveClientConnectorService.fetchMonitors();
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return Collections.emptyList();
        }    }

    @Override
    public boolean deleteByonNode(String byonId) {
        try {
            return proactiveClientConnectorService.deleteByonNodes(byonId);
        } catch (ProactiveClientException e) {
            log.error("Error message body: {}", e.getMessage());
            return false;
        }
    }
}
