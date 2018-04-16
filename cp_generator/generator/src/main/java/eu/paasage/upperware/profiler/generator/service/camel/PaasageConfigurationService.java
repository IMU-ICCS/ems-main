package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.metamodel.application.*;
import eu.paasage.upperware.metamodel.cp.Variable;

public interface PaasageConfigurationService {

    PaasageConfiguration createPaasageConfiguration(CamelModel camelModel, String appId);

    PaaSageVariable createPaaSageVariable(ApplicationComponent ac, VirtualMachineProfile vm, Provider provider, Variable var);


}
