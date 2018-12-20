package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class AdapterFaasTrigger {
    private String type;
    private String httpPath;
    private String httpMethod;
}
