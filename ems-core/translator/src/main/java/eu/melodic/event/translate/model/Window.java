package eu.melodic.event.translate.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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
    @Builder.Default
    private List<WindowProcessing> processings = null;
}
