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
public class WindowProcessing extends Feature {
    private WindowProcessingType processingType;
    @Builder.Default
    private List<WindowCriterion> groupingCriteria = new ArrayList<>();
    @Builder.Default
    private List<WindowCriterion> rankingCriteria = new ArrayList<>();
}
