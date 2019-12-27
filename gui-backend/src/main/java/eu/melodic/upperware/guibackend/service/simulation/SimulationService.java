package eu.melodic.upperware.guibackend.service.simulation;

import eu.melodic.models.interfaces.metaSolver.MetricsNamesResponse;
import eu.melodic.models.services.adapter.DifferenceResponse;
import eu.melodic.upperware.guibackend.communication.metasolver.MetaSolverApi;

public class SimulationService {
    MetaSolverApi metaSolverApi;

    public MetricsNamesResponse getMetricNames() {
        return metaSolverApi.getMetricNames();
    }
}
