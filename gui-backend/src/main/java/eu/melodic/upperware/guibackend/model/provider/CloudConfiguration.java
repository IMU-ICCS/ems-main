package eu.melodic.upperware.guibackend.model.provider;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CloudConfiguration {
    private long id;

    private String nodeGroup;

    private List<ParentProperty> properties;

}
