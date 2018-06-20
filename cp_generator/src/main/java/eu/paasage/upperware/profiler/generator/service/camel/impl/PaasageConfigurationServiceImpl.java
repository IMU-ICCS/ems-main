package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.camel.CamelModel;
import eu.paasage.upperware.metamodel.application.*;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.types.typesPaasage.VariableElementTypeEnum;
import eu.paasage.upperware.profiler.generator.service.camel.PaasageConfigurationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PaasageConfigurationServiceImpl implements PaasageConfigurationService {

    private final ApplicationFactory applicationFactory;

    @Override
    public PaasageConfiguration createPaasageConfiguration(CamelModel camelModel, String appId) {
        log.info("** Creating PaaSageConfigurationWrapper - generated id: {}", appId);
        PaasageConfiguration paasageConfiguration = applicationFactory.createPaasageConfiguration();
        paasageConfiguration.setId(appId);

        return paasageConfiguration;
    }

    @Override
    public PaaSageVariable createPaaSageVariable(ApplicationComponent ac, VirtualMachineProfile vm, Provider provider, Variable var){
        PaaSageVariable pVar= applicationFactory.createPaaSageVariable();
        pVar.setCpVariableId(var.getId());
        pVar.setPaasageType(VariableElementTypeEnum.VIRTUAL_LOCATION);
        pVar.setRelatedComponent(ac);
        pVar.setRelatedVirtualMachineProfile(vm);
        pVar.setRelatedProvider(provider);
        return pVar;
    }

}

