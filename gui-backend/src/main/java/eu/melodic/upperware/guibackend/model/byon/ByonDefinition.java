package eu.melodic.upperware.guibackend.model.byon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ByonDefinition {
    private long id;
    private String name;
    private LoginCredential loginCredential;
    private List<IpAddress> ipAddresses;
    private NodeProperties nodeProperties;
}
