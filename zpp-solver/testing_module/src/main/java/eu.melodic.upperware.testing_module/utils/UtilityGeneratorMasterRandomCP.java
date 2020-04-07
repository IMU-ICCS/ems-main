package eu.melodic.upperware.testing_module.utils;

import cp_wrapper.utility_provider.ParallelUtilityProviderImpl;
import cp_wrapper.utility_provider.UtilityProvider;
import cp_wrapper.utility_provider.UtilityProviderImpl;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.utility_function.utility_templates_provider.TemplateProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class UtilityGeneratorMasterRandomCP implements UtilityGeneratorMaster {
    private String cpModelFilePath;
    @Getter
    private NodeCandidates nodeCandidates;
    private List<Map.Entry<TemplateProvider.AvailableTemplates, Double>> utilityTemplate;

    @Override
    public UtilityProvider createParallelUtilityProvider(int size) {
        return new ParallelUtilityProviderImpl(IntStream.range(0, size).mapToObj(index -> new UtilityGeneratorApplication(cpModelFilePath, nodeCandidates, utilityTemplate))
                .collect(Collectors.toList()));
    }

    @Override
    public UtilityProvider create() {
        return new UtilityProviderImpl(new UtilityGeneratorApplication(cpModelFilePath, nodeCandidates, utilityTemplate));
    }
}
