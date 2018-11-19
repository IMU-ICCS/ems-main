package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@Builder
public class AdapterDockerInterface extends AdapterTaskInterface {

    private String dockerImage;
    private Map<String, String> environment;
}
