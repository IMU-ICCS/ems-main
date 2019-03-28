package eu.melodic.upperware.guibackend.model.provider;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class CloudConfiguration {
    private long id;

    private String nodeGroup;

    private List<ParentProperty> properties;

}
