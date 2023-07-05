package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.List;
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
    private Map<String, String> configuration = new HashMap<>();

    //XXX:TODO: Try to remove
    private Sensor sensor;
    //XXX:TODO: Try to remove
    private List<Sink> sinks;
}
