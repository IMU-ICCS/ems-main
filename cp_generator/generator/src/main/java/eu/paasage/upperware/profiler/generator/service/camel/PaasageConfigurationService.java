package eu.paasage.upperware.profiler.generator.service.camel;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.profiler.cp.generator.model.lib.PaaSageConfigurationWrapper;

public interface PaasageConfigurationService {

    PaaSageConfigurationWrapper createPaasageConfigurationWrapper(CamelModel camelModel);

    PaaSageVariable createPaaSageVariable(ApplicationComponent ac, VirtualMachineProfile vm, Provider provider, Variable var);


}
