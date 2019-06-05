package eu.melodic.upperware.guibackend.controller.process.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpModelResponse {

    private String id;
    private List<CpMetricResponse> metrics;
    private List<CpVariableResponse> variables;
    private List<CpConstantResponse> constants;
    private CpSolutionResponse solution;
    private String utilityFormula;
}
