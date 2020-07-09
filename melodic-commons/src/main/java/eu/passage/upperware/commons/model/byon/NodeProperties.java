package eu.passage.upperware.commons.model.byon;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NodeProperties {
    private long id;
    private String providerId;
    private Integer numberOfCores;
    private Long memory;
    private Float disk;
    private OperatingSystem operatingSystem;
    private GeoLocation geoLocation;
}
