package eu.melodic.upperware.guibackend.controller.deployment.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecureVariableRequest {
    private String name;
    private String value;
}
