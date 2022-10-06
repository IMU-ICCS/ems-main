package eu.passage.upperware.commons.model.edge;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EdgeEnums {
    private List<String> ipVersions;
    private List<String> systemArch;
    private List<String> ipAddressTypes;
    private List<String> osFamilies;
    private List<String> osArchitectures;
}
