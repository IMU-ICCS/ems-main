package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
// Based on: eu.melodic.event.models.interfaces.PushSensor
public class PushSensor extends Sensor {
    private int port;
}
