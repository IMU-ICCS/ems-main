package eu.melodic.upperware.guibackend.controller.simulation;


import eu.melodic.models.interfaces.metaSolver.MetricsNamesResponse;
import eu.melodic.upperware.guibackend.service.simulation.SimulationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/simulation")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SimulationController {

    private SimulationService simulationService;

    @GetMapping(value = "/{processId}")
    @ResponseStatus(HttpStatus.OK)
    public MetricsNamesResponse checkProcessVariables(@PathVariable("processId") String processId) {
        log.info("GET request for metric names with process id: {}", processId);
        return simulationService.getMetricNames();
    }
}
