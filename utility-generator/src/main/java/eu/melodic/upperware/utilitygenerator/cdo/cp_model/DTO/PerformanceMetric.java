package eu.melodic.upperware.utilitygenerator.cdo.cp_model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PerformanceMetric {
    private String name;
    private String performanceIndicatorName;
    private String formula;
}
