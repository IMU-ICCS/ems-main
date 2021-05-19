package eu.melodic.upperware.guibackend.controller.process;

import eu.melodic.models.services.adapter.DifferenceResponse;
import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceGUI;
import eu.melodic.upperware.guibackend.controller.process.response.CpModelResponse;
import eu.melodic.upperware.guibackend.controller.process.response.CpSolutionResponse;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessInstanceResponse;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessVariables;
import eu.melodic.upperware.guibackend.service.process.ProcessCamundaService;
import eu.melodic.upperware.guibackend.service.process.ProcessService;
import io.github.cloudiator.rest.model.Cloud;
import io.github.cloudiator.rest.model.CloudiatorProcess;
import io.github.cloudiator.rest.model.Hardware;
import io.github.cloudiator.rest.model.Image;
import io.github.cloudiator.rest.model.Job;
import io.github.cloudiator.rest.model.Location;
import io.github.cloudiator.rest.model.Monitor;
import io.github.cloudiator.rest.model.Node;
import io.github.cloudiator.rest.model.Queue;
import io.github.cloudiator.rest.model.Schedule;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth/process")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessController {

    private ProcessCamundaService processCamundaService;
    //    private CloudiatorApi cloudiatorApi;
    private ProcessService processService;
    private ProactiveClientServiceGUI proactiveClientServiceGUI;

    @GetMapping(value = "/{processId}")
    @ResponseStatus(HttpStatus.OK)
    public ProcessVariables checkProcessVariables(@PathVariable("processId") String processId) {
        log.info("GET request for check process variables with process id: {}", processId);
        return processCamundaService.getProcessVariables(processId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProcessInstanceResponse> getAllProcessData() {
        log.info("GET request for all processes data");
        return processCamundaService.getAllProcessesData();
    }

    @GetMapping("/offer/total")
    @ResponseStatus(HttpStatus.OK)
    public Integer getOffersTotalNumber() {
        log.info("GET request for total number of offers");
        return proactiveClientServiceGUI.getNumberOfCurrentOffers();
    }

    @GetMapping("/offer/hardware")
    @ResponseStatus(HttpStatus.OK)
    public List<Hardware> getHardwareList() {
        log.info("GET request for hardware list");
        log.warn("Fetching hardware list is not implemented yet.");
//        return cloudiatorApi.getHardwareList();
        return Collections.emptyList();
    }

    @GetMapping("/offer/location")
    @ResponseStatus(HttpStatus.OK)
    public List<Location> getLocationList() {
        log.info("GET request for locations list");
        log.warn("Fetching locations list is not implemented yet.");
//        return cloudiatorApi.getLocationList();
        return Collections.emptyList();
    }

    @GetMapping("/offer/image")
    @ResponseStatus(HttpStatus.OK)
    public List<Image> getImageList() {
        log.info("GET request for images list");
        log.warn("Fetching images list is not implemented yet.");
//        return cloudiatorApi.getImageList();
        return Collections.emptyList();
    }

    @GetMapping("/offer/cloud")
    @ResponseStatus(HttpStatus.OK)
    public List<Cloud> getCloudList() {
        log.info("GET request for cloud list");
        log.warn("Fetching clouds list is not implemented yet.");
//        return cloudiatorApi.getCloudList();
        return Collections.emptyList();
    }

    @GetMapping("/cp/model/{processId}")
    public CpModelResponse getConstraintProblem(@PathVariable("processId") String processId) {
        log.info("GET request for CP model for process with id: {}", processId);
        return processService.getCpModel(processId);
    }

    @GetMapping("/cp/solution/{processId}")
    public CpSolutionResponse getSolutionForProcess(@PathVariable("processId") String processId) {
        log.info("GET request for CP solution for process with id: {}", processId);
        return processService.getCpSolutionForProcess(processId);
    }

    @GetMapping("/deployment/difference/{processId}")
    public DifferenceResponse getDeploymentDifferenceForProcess(@PathVariable("processId") String processId,
                                                                @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        log.info("GET request for deployment difference for process with id: {}", processId);
        return processService.getDeploymentDifference(processId, token);
    }

    @GetMapping("/deployment/node")
    public List<Node> getNodesList() {
        log.info("GET request for nodes list");
        log.warn("Fetching nodes list is not implemented yet.");
//        return cloudiatorApi.getNodeList();
        return Collections.emptyList();
    }

    @DeleteMapping("/deployment/node/{nodeId}")
    public void deleteNode(@PathVariable("nodeId") String nodeId) {
        log.info("DELETE request for node with wid: {}", nodeId);
        log.warn("Deleting nodes is not implemented yet.");
//        cloudiatorApi.deleteNode(nodeId);
//        log.info("Node with id: {} successfully deleted", nodeId);
    }

    @GetMapping("/deployment/process")
    public List<CloudiatorProcess> getCloudiatorProcessList() {
        log.info("GET Cloudiator processes list");
        log.warn("Fetching processes list is not implemented yet.");
//        return cloudiatorApi.getProcessList();
        return Collections.emptyList();
    }

    @DeleteMapping("/deployment/process/{processId}")
    public void deleteCloudiatorProcess(@PathVariable("processId") String processId) {
        log.info("DELETE request for Cloudiator process with id: {}", processId);
        log.warn("Deleting processes list is not implemented yet.");
//        cloudiatorApi.deleteCloudiatorProcess(processId);
//        log.info("Cloudiator process with id: {} successfully deleted", processId);
    }

    @GetMapping("/deployment/queue")
    public List<Queue> getCloudiatorQueue() {
        log.info("GET Cloudiator queues list");
        log.warn("Fetching queues list is not implemented yet.");
//        return cloudiatorApi.getQueueList();
        return Collections.emptyList();
    }

    @GetMapping("/deployment/job")
    public List<Job> getJobsList() {
        log.info("GET Cloudiator jobs list");
        log.warn("Fetching jobs list is not implemented yet.");
//        return cloudiatorApi.getJobList();
        return Collections.emptyList();
    }

    @GetMapping("/deployment/schedule")
    public List<Schedule> getSchedulesList() {
        log.info("GET Cloudiator schedules list");
        log.warn("Fetching schedules list is not implemented yet.");
//        return cloudiatorApi.getScheduleList();
        return Collections.emptyList();
    }

    @GetMapping("/deployment/monitor")
    public List<Monitor> getMonitorsList() {
        log.info("GET Cloudiator monitors list");
        log.warn("Fetching monitors list is not implemented yet.");
//        return cloudiatorApi.getMonitorList();
        return Collections.emptyList();
    }
}
