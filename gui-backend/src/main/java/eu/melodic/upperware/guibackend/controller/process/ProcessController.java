package eu.melodic.upperware.guibackend.controller.process;

import eu.melodic.upperware.guibackend.communication.cloudiator.CloudiatorClientApi;
import eu.melodic.upperware.guibackend.controller.process.response.CpModelResponse;
import eu.melodic.upperware.guibackend.controller.process.response.CpSolutionResponse;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessInstanceResponse;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessVariables;
import eu.melodic.upperware.guibackend.service.process.ProcessCamundaService;
import eu.melodic.upperware.guibackend.service.process.ProcessService;
import io.github.cloudiator.rest.model.Cloud;
import io.github.cloudiator.rest.model.Hardware;
import io.github.cloudiator.rest.model.Image;
import io.github.cloudiator.rest.model.Location;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/process")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProcessController {

    private ProcessCamundaService processCamundaService;
    private CloudiatorClientApi cloudiatorClientApi;
    private ProcessService processService;

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
        return cloudiatorClientApi.getDiscoveryStatusTotal();
    }

    @GetMapping("/offer/hardware")
    @ResponseStatus(HttpStatus.OK)
    public List<Hardware> getHardwareList() {
        log.info("GET request for hardware list");
        return cloudiatorClientApi.getHardwareList();
    }

    @GetMapping("/offer/location")
    @ResponseStatus(HttpStatus.OK)
    public List<Location> getLocationList() {
        log.info("GET request for locations list");
        return cloudiatorClientApi.getLocationList();
    }

    @GetMapping("/offer/image")
    @ResponseStatus(HttpStatus.OK)
    public List<Image> getImageList() {
        log.info("GET request for images list");
        return cloudiatorClientApi.getImageList();
    }

    @GetMapping("/offer/cloud")
    @ResponseStatus(HttpStatus.OK)
    public List<Cloud> getCloudList() {
        log.info("GET request for cloud list");
        return cloudiatorClientApi.getCloudList();
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
}
