package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdapterDockerInterface extends AdapterTaskInterface {

    private String dockerImage;
}
