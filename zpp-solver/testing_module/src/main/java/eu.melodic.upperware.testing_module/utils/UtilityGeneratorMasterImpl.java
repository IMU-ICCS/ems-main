package eu.melodic.upperware.testing_module.utils;

import cp_wrapper.utility_provider.ParallelUtilityProviderImpl;
import cp_wrapper.utility_provider.UtilityProvider;
import cp_wrapper.utility_provider.UtilityProviderImpl;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.AllArgsConstructor;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

@AllArgsConstructor
public class UtilityGeneratorMasterImpl implements UtilityGeneratorMaster {
    private MelodicSecurityProperties melodicSecurityProperties;
    private PenaltyFunctionProperties penaltyFunctionProperties;
    private UtilityGeneratorProperties utilityGeneratorProperties;
    private JWTService jwtService;
    NodeCandidates nodeCandidates;
    String camelModelFilePath;
    String cpProblemFilePath;


    @Override
    public UtilityProvider create() {
        return new UtilityProviderImpl(new UtilityGeneratorApplication(camelModelFilePath, cpProblemFilePath,
                true, nodeCandidates, utilityGeneratorProperties, melodicSecurityProperties, jwtService, penaltyFunctionProperties));
    }

    @Override
    public UtilityProvider createParallelUtilityProvider(int size) {
        return new ParallelUtilityProviderImpl(
                IntStream.range(0,size).mapToObj(number ->
                        new UtilityGeneratorApplication(camelModelFilePath, cpProblemFilePath, true, nodeCandidates, utilityGeneratorProperties, melodicSecurityProperties, jwtService, penaltyFunctionProperties)
                ).collect(Collectors.toList()));
    }

}
