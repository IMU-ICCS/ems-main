package eu.melodic.upperware.testing_module.utils;

import eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider.TemplateProvider;
import lombok.Data;

import java.util.Map;

@Data
public class TemplateUtilityComponent {
    private String name;
    private double weight;

    public Map.Entry<TemplateProvider.AvailableTemplates, Double> parse() {
        return Map.entry(TemplateProvider.AvailableTemplates.valueOf(name), weight);
    }
}
