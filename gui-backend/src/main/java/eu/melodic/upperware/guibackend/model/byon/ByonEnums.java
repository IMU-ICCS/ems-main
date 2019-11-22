package eu.melodic.upperware.guibackend.model.byon;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ByonEnums {
    private List<String> ipAddressTypes;
    private List<String> ipVersions;
    private List<String> osFamilies;
    private List<String> osArchitectures;
}
