package eu.melodic.upperware.guibackend.controller.process.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpVariableResponse {
    private String variableType;
    private String id;
    private String componentId;
    private CpDomainResponse domain;

}
