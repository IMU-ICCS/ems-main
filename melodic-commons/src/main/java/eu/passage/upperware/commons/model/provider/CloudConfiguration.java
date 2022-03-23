package eu.passage.upperware.commons.model.provider;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CloudConfiguration {
    private long id;

    private String nodeGroup;

    private List<ParentProperty> properties;

    private String scopePrefix;

    private String scopeValue;

    private String identityVersion;
}
