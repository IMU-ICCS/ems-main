package eu.melodic.upperware.guibackend.controller.process;

import eu.melodic.models.services.adapter.DifferenceResponse;
import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientService;
import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceGUI;
import eu.melodic.upperware.guibackend.controller.process.response.CpModelResponse;
import eu.melodic.upperware.guibackend.controller.process.response.CpSolutionResponse;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessInstanceResponse;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessVariables;
import eu.melodic.upperware.guibackend.domain.converter.DomainConverterFactory;
import eu.melodic.upperware.guibackend.domain.converter.GenericConverter;
import eu.melodic.upperware.guibackend.service.process.ProcessCamundaService;
import eu.melodic.upperware.guibackend.service.process.ProcessService;
import eu.passage.upperware.commons.model.SecureVariable;
import eu.passage.upperware.commons.model.internal.Cloud;
import eu.passage.upperware.commons.model.internal.Location;
import io.github.cloudiator.rest.model.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.activeeon.morphemic.model.EmsDeploymentRequest;
import org.activeeon.morphemic.model.PACloud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/process")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessController {

    private ProcessCamundaService processCamundaService;
    //    private CloudiatorApi cloudiatorApi;
    private ProcessService processService;
    private ProactiveClientServiceGUI proactiveClientServiceGUI;
    private ProactiveClientService proactiveClientService;
    private final DomainConverterFactory domainConverterFactory;

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
    public List<eu.passage.upperware.commons.model.internal.Hardware> getHardwareList() {
        log.info("GET request for hardware list");
        final List<eu.passage.upperware.commons.model.internal.Hardware> domains = ((GenericConverter<org.activeeon.morphemic.model.Hardware, eu.passage.upperware.commons.model.internal.Hardware>) domainConverterFactory.getHardwareConverter()).createDomains(proactiveClientServiceGUI.getAllHardware());
        log.info("ProcessController->getHardwareList converted to internal/domain hardware list: {}", domains);
        return domains;
    }

    @GetMapping("/offer/location")
    @ResponseStatus(HttpStatus.OK)
    public List<Location> getLocationList() {
        log.info("GET request for locations list");
        final List<Location> domains = ((GenericConverter<org.activeeon.morphemic.model.Location, Location>) domainConverterFactory.getLocationConverter()).createDomains(proactiveClientServiceGUI.getAllLocation());
        log.info("ProcessController->getLocationList converted to internal/domain location list: {}", domains);
        return domains;
    }

    @GetMapping("/offer/image")
    @ResponseStatus(HttpStatus.OK)
    public List<eu.passage.upperware.commons.model.internal.Image> getImageList() {
        log.info("GET request for images list");
        final List<org.activeeon.morphemic.model.Image> images = proactiveClientServiceGUI.getAllClouds()
                .stream()
                .map(paCloud -> {
                    log.info("ProcessController->getImageList fetching images for cloudID: {}", paCloud.getCloudID());
                    return proactiveClientServiceGUI.getAllCloudImages(paCloud.getCloudID());
                })
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        log.info("ProcessController->getImageList collected images: {}", images);
        final List<eu.passage.upperware.commons.model.internal.Image> domains = ((GenericConverter<org.activeeon.morphemic.model.Image, eu.passage.upperware.commons.model.internal.Image>) domainConverterFactory.getImageConverter()).createDomains(images);
        log.info("ProcessController->getImageList converted to internal/domain images: {}", domains);
        return domains;
    }

    @GetMapping("/offer/cloud")
    @ResponseStatus(HttpStatus.OK)
    public List<Cloud> getCloudList() {
        log.info("GET request for cloud list");
        final List<Cloud> domains = ((GenericConverter<PACloud, Cloud>) domainConverterFactory.getCloudConverter()).createDomains(proactiveClientServiceGUI.getAllClouds());
        log.info("ProcessController->getCloudList converted to internal/domain clouds: {}", domains);
        return domains;
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
    public List<eu.passage.upperware.commons.model.internal.Job> getJobsList() {
        log.info("GET Cloudiator jobs list");
        final List<org.activeeon.morphemic.model.Job> allJobs = proactiveClientServiceGUI.getAllJobs();
        log.info("ProcessController->getJobsList collected jobs: {}", allJobs);
        final List<eu.passage.upperware.commons.model.internal.Job> domains = ((GenericConverter<org.activeeon.morphemic.model.Job, eu.passage.upperware.commons.model.internal.Job>) domainConverterFactory.getJobConverter()).createDomains(allJobs);
        log.info("ProcessController->getJobsList converted to internal/domain jobs: {}", domains);
        return domains;
    }

    @GetMapping("/deployment/schedule")
    public List<Schedule> getSchedulesList() {
        log.info("GET Cloudiator schedules list");
        log.warn("Fetching schedules list is not implemented yet.");
//        return cloudiatorApi.getScheduleList();
        return Collections.emptyList();
    }

    @GetMapping("/deployment/monitor")
    public List<eu.passage.upperware.commons.model.internal.EmsDeploymentRequest> getMonitorsList() {
        log.info("GET Cloudiator monitors list");
        final List<EmsDeploymentRequest> allMonitors = proactiveClientServiceGUI.getAllMonitors();
        log.info("ProcessController->getMonitorsList collected monitors: {}", allMonitors);
        final List<eu.passage.upperware.commons.model.internal.EmsDeploymentRequest> domains = ((GenericConverter<EmsDeploymentRequest, eu.passage.upperware.commons.model.internal.EmsDeploymentRequest>) domainConverterFactory.getMonitorConverter()).createDomains(allMonitors);
        log.info("ProcessController->getMonitorsList converted to internal/domain monitors: {}", domains);
        return domains;
    }

    @PostMapping(value = "/undeploy")
    @ResponseStatus(HttpStatus.CREATED)
    public void undeployApplication(@RequestParam String applicationId) {
        log.info("POST request for undeploy of application");
        proactiveClientService.stopJob(applicationId);
        log.info("application with id:" + applicationId + " stopped");
    }
}
