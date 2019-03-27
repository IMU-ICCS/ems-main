package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode
public class AdapterFaasTrigger {
    private String type;
    private String httpPath;
    private String httpMethod;
}
