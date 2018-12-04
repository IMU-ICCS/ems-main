package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

@Getter
@Setter
@Builder
@ToString
public class AdapterDockerInterface extends AdapterTaskInterface {

    private String dockerImage;
    private Map<String, String> environment;
}
