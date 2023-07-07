package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
// See: eu.melodic.event.models.interfaces.Monitor
public class Monitor extends AbstractInterfaceRootObject {
    private String metric;
    private String component;
    private Sensor sensor;
    private List<Sink> sinks;
    private Map<String, String> tags;
}
