package eu.melodic.event.translate.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
// See: eu.melodic.event.models.interfaces.Sink
public class Sink extends AbstractInterfaceRootObject {
    public enum Type { /*CLI, KAIROS_DB,*/ INFLUX, JMS }

    private Type type;
    private String component;
    @Builder.Default
    private Map<String, String> configuration = new HashMap<>();
}
