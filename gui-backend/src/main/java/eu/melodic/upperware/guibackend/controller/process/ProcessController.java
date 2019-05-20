package eu.melodic.upperware.guibackend.controller.process;

import eu.melodic.upperware.guibackend.controller.process.response.ProcessInstanceResponse;
import eu.melodic.upperware.guibackend.controller.process.response.ProcessVariables;
import eu.melodic.upperware.guibackend.service.process.ProcessService;
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

    private ProcessService processService;

    @GetMapping(value = "/{processId}")
    @ResponseStatus(HttpStatus.OK)
    public ProcessVariables checkProcessVariables(@PathVariable("processId") String processId) {
        log.info("GET request for check process variables with process id: {}", processId);
        return processService.getProcessVariables(processId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProcessInstanceResponse> getAllProcessData() {
        log.info("GET request for all processes data");
        return processService.getAllProcessesData();
    }
}
