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
    private List<CpItemResponse> metrics;
    private List<CpVariableResponse> variables;
    private List<CpItemResponse> constants;
    private CpSolutionResponse solution;
    private String utilityFormula;
}
