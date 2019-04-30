package eu.melodic.upperware.guibackend.model.provider;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CloudConfiguration {
    private long id;

    private String nodeGroup;

    private List<ParentProperty> properties;

}
