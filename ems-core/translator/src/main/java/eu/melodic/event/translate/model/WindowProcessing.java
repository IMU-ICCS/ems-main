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
public class WindowProcessing extends Feature {
    private WindowProcessingType processingType;
    private List<WindowCriterion> groupingCriteria = new ArrayList<>();
    private List<WindowCriterion> rankingCriteria = new ArrayList<>();
}
