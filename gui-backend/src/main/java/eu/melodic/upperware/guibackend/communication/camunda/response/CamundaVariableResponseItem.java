package eu.melodic.upperware.guibackend.communication.camunda.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CamundaVariableResponseItem {
    private String type;
    private String value;
    private Object valueInfo;
}
