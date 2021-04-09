package eu.melodic.upperware.cp_wrapper.utility_provider.implementations;

import eu.melodic.cache.NodeCandidates;
import eu.melodic.upperware.cp_wrapper.utility_provider.UtilityProvider;
import eu.melodic.upperware.cp_wrapper.utility_provider.UtilityProviderFactory;
import eu.melodic.upperware.penaltycalculator.PenaltyFunctionProperties;
import eu.melodic.upperware.utilitygenerator.UtilityGeneratorApplication;
import eu.paasage.upperware.security.authapi.properties.MelodicSecurityProperties;
import eu.paasage.upperware.security.authapi.token.JWTService;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UtilityProviderFromFileFactory implements UtilityProviderFactory {
    private MelodicSecurityProperties melodicSecurityProperties;
    private PenaltyFunctionProperties penaltyFunctionProperties;
    private JWTService jwtService;
    private NodeCandidates nodeCandidates;
    private String camelModelFilePath;
    private String cpProblemFilePath;

    @Override
    public UtilityProvider create() {
        return new UtilityProviderImpl(new UtilityGeneratorApplication(camelModelFilePath, cpProblemFilePath,
                true, nodeCandidates, melodicSecurityProperties, jwtService, penaltyFunctionProperties));
    }
}
