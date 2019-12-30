package eu.melodic.upperware.guibackend.communication.metasolver;

import eu.melodic.models.interfaces.metaSolver.MetricsNamesResponse;
import eu.melodic.models.interfaces.metaSolver.SimulatedMetricValuesRequest;
import eu.melodic.models.interfaces.metaSolver.SimulatedMetricValuesResponse;

public interface MetaSolverApi {

    MetricsNamesResponse getMetricNames(String token);

    SimulatedMetricValuesResponse simulateMetricValues(
            SimulatedMetricValuesRequest simulatedMetricValuesRequest, String token);
}
