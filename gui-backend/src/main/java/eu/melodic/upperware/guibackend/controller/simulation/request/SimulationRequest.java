package eu.melodic.upperware.guibackend.controller.simulation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SimulationRequest {

    private String applicationId;
    private Map<String, String> metricValues;
}
