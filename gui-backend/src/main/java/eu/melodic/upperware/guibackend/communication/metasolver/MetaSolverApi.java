package eu.melodic.upperware.guibackend.communication.metasolver;

import eu.melodic.models.interfaces.metaSolver.MetricsNamesResponse;
import eu.melodic.models.interfaces.metaSolver.SimulatedMetricValuesRequest;
import eu.melodic.models.interfaces.metaSolver.SimulatedMetricValuesResponse;

public interface MetaSolverApi {

    MetricsNamesResponse getMetricNames(String applicationId, String token);

    SimulatedMetricValuesResponse simulateReconfiguration(
            SimulatedMetricValuesRequest simulatedMetricValuesRequest, String token);
}
