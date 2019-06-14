package eu.melodic.upperware.guibackend.controller.process.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpVariableValueResponse {
    private CpVariableResponse variable;
    private Object value;
}
