package eu.melodic.upperware.guibackend.service.simulation;

import eu.melodic.models.interfaces.metaSolver.*;
import eu.melodic.upperware.guibackend.communication.metasolver.MetaSolverApi;
import eu.melodic.upperware.guibackend.controller.simulation.request.SimulationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class SimulationService {
    private MetaSolverApi metaSolverApi;

    public MetricsNamesResponse getMetricNames(String token) {
        return metaSolverApi.getMetricNames(token);
    }

    public SimulatedMetricValuesResponse simulateMetricValues(SimulationRequest simulationRequest,
                                                              String token){
        SimulatedMetricValuesRequest simulatedMetricValuesRequest = new SimulatedMetricValuesRequestImpl();
        simulatedMetricValuesRequest.setApplicationId(simulationRequest.getApplicationId());
        simulatedMetricValuesRequest.setMetricValues(simulationRequest.getMetricValues().entrySet().stream().map(e ->
        {
            KeyValuePair p = new KeyValuePairImpl();
            p.setKey(e.getKey());
            p.setValue(e.getValue());
            return p;
        }).collect(Collectors.toList()));

        return metaSolverApi.simulateMetricValues(simulatedMetricValuesRequest, token);
    }
}
