package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Window extends Feature {
    private String timeUnit;
    private WindowType windowType;
    private WindowSizeType sizeType;
    private long measurementSize;
    private long timeSize;
    private List<WindowProcessing> processings = new ArrayList<>();
}
