package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
public class AdapterDockerInterface extends AdapterTaskInterface {

    private String dockerImage;
    private Map<String, String> environment;
}
