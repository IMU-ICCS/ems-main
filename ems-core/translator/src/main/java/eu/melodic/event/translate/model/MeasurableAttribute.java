package eu.melodic.event.translate.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MeasurableAttribute extends Attribute {
    @Builder.Default
    private List<Sensor> sensors = new ArrayList<>();
}
