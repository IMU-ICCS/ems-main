package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
// Based on: eu.melodic.event.models.interfaces.PullSensor
public class PullSensor extends Sensor {
    private String className;
    private Map<String, String> configuration = new HashMap<>();
    private Interval interval;
}
