package eu.melodic.upperware.guibackend.controller.process.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpSolutionResponse {
    private Object utilityValue;
    private Long timestamp;
    private List<CpVariableValueResponse> variableValue;
}
