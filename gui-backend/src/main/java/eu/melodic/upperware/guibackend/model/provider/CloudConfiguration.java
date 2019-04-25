package eu.melodic.upperware.guibackend.model.provider;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CloudConfiguration {
    private long id;

    private String nodeGroup;

    private List<ParentProperty> properties;

}
