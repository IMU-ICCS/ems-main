package eu.melodic.upperware.guibackend.controller.simulation;


import eu.melodic.models.interfaces.metaSolver.MetricsNamesResponse;
import eu.melodic.models.interfaces.metaSolver.SimulatedMetricValuesRequest;
import eu.melodic.models.interfaces.metaSolver.SimulatedMetricValuesResponse;
import eu.melodic.upperware.guibackend.controller.simulation.request.SimulationRequest;
import eu.melodic.upperware.guibackend.service.simulation.SimulationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/simulation")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SimulationController {

    private SimulationService simulationService;

    @GetMapping("/metric")
    @ResponseStatus(HttpStatus.OK)
    public MetricsNamesResponse getMetricNames(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        log.info("GET request for metric names");
        return simulationService.getMetricNames(token);
    }

    @PostMapping("/metric")
    @ResponseStatus(HttpStatus.CREATED)
    public SimulatedMetricValuesResponse simulateMetricValues(@RequestBody SimulationRequest simulationRequest,
                                                              @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) {
        log.info("POST request for simulating metric values");
        return simulationService.simulateMetricValues(simulationRequest, token);
    }

}
