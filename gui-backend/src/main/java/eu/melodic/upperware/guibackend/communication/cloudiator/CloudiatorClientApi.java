package eu.melodic.upperware.guibackend.communication.cloudiator;

import eu.melodic.upperware.guibackend.exception.SecureVariableNotFoundException;
import io.github.cloudiator.rest.ApiException;
import io.github.cloudiator.rest.api.*;
import io.github.cloudiator.rest.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CloudiatorClientApi implements CloudiatorApi {

    private CloudApi cloudApi;
    private SecurityApi securityApi;
    private NodeApi nodeApi;
    private ProcessApi processApi;
    private QueueApi queueApi;
    private JobApi jobApi;
    private final String CLOUDIATOR_ERROR_MESSAGE = "Problem in communication with Cloudiator. Cloudiator not working. Please try again.";

    @Override
    public Integer getDiscoveryStatusTotal() {
        try {
            return Integer.valueOf(cloudApi.discoveryStatus().getOrDefault("total", "0"));
        } catch (ApiException e) {
            log.error("Error by getting total number of offers.", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Hardware> getHardwareList() {
        try {
            List<Hardware> hardware = cloudApi.findHardware(null);
            log.info("Number of hardware in response = {}", hardware.size());
            return hardware;
        } catch (ApiException e) {
            log.error("Error by getting hardware list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Location> getLocationList() {
        try {
            List<Location> locations = cloudApi.findLocations(null);
            log.info("Number of locations in response: {}", locations.size());
            return locations;
        } catch (ApiException e) {
            log.error("Error by getting location list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Image> getImageList() {
        try {
            List<Image> images = cloudApi.findImages(null);
            log.info("Number of images in response: {}", images.size());
            return images;
        } catch (ApiException e) {
            log.error("Error by getting images list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Cloud> getCloudList() {
        try {
            List<Cloud> clouds = cloudApi.findClouds();
            log.info("Number of clouds in response: {}", clouds.size());
            return clouds;
        } catch (ApiException e) {
            log.error("Error by getting clouds list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Function> getFunctionList() {
        try {
            List<Function> functions = cloudApi.findFunctions();
            log.info("Number of functions in response: {}", functions.size());
            return functions;
        } catch (ApiException e) {
            log.error("Error by getting functions list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public void storeSecureVariable(String key, String value) {
        Text cloudiatorText = new Text();
        try {
            securityApi.storeSecure(key, cloudiatorText.content(value));
        } catch (ApiException ex) {
            log.error("Error by secure storing of variable with name: {}", key, ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Error by storing secure variable with name: %s in Cloudiator's secure store", key));
        }
    }

    @Override
    public String getSecureVariable(String key) {
        try {
            log.info("GET secure variable with key: {}", key);
            return securityApi.retrieveSecure(key).getContent();
        } catch (ApiException e) {
            if (e.getResponseBody() != null && e.getResponseBody().startsWith("Response code 404")) {
                log.error("Secure variable not found error");
                throw new SecureVariableNotFoundException(String.format("Secure variable with key %s not found in secure store", key), key);
            }
            log.error("Error by getting secure variable with name: {}", key, e);
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, String.format("Error by getting secure variable with name: %s from Cloudiator's secure store", key));
        }
    }

    @Override
    public void deleteSecureVariable(String key) {
        try {
            securityApi.deleteSecure(key);
        } catch (ApiException e) {
            log.error("Error by deleting secure variable with name: {}", key, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("Error by getting secure variable with name: %s from Cloudiator's secure store", key));
        }
    }

    @Override
    public List<Node> getVMFromNodeList() {
        return this.getNodeWithTypeFromNodeList(Node.NodeTypeEnum.VM);
    }

    @Override
    public List<Node> getFaasFromNodeList() {
        return this.getNodeWithTypeFromNodeList(Node.NodeTypeEnum.FAAS);
    }

    private List<Node> getNodeWithTypeFromNodeList(Node.NodeTypeEnum nodeTypeEnum) {
        try {
            List<Node> vmsFromNode = nodeApi.findNodes()
                    .stream()
                    .filter(node -> nodeTypeEnum.equals(node.getNodeType()))
                    .collect(Collectors.toList());
            log.info("Number of {} nodes in response: {}", nodeTypeEnum, vmsFromNode.size());
            return vmsFromNode;
        } catch (ApiException e) {
            log.error("Error by getting {} nodes list: ", nodeTypeEnum, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Node> getNodeList() {
        try {
            return nodeApi.findNodes();
        } catch (ApiException e) {
            log.error("Error by getting nodes list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteNode(String nodeId) {
        try {
            nodeApi.deleteNode(nodeId);
        } catch (ApiException e) {
            log.error("Error by deleting node with id: {}", nodeId, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<CloudiatorProcess> getProcessList() {
        try {
            return processApi.getProcesses(null);
        } catch (ApiException e) {
            log.error("Error by getting Cloudiator processes list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public void deleteCloudiatorProcess(String processId) {
        try {
            processApi.deleteProcess(processId);
        } catch (ApiException e) {
            log.error("Error by deleting process with id: {}", processId, e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Queue> getQueueList() {
        try {
            return queueApi.getQueuedTasks();
        } catch (ApiException e) {
            log.error("Error by getting Cloudiator queues list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Job> getJobList() {
        try {
            return jobApi.findJobs();
        } catch (ApiException e) {
            log.error("Error by getting Cloudiator jobs list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }

    @Override
    public List<Schedule> getScheduleList() {
        try {
            return processApi.getSchedules();
        } catch (ApiException e) {
            log.error("Error by getting Cloudiator schedules list: ", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CLOUDIATOR_ERROR_MESSAGE);
        }
    }
}
