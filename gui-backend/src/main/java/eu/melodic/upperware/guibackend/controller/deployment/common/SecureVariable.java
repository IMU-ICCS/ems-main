package eu.melodic.upperware.guibackend.controller.deployment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SecureVariable {
    private String name;
    private String value;
}
