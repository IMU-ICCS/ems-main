package eu.melodic.upperware.cp_wrapper.utility_provider.implementations;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.cp_wrapper.utility_provider.UtilityProviderFactory;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.melodic.upperware.utilitygenerator.properties.UtilityGeneratorProperties;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UtilityProviderFromFileFactory implements UtilityProviderFactory {
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
}
