package eu.melodic.upperware.guibackend.controller.application;

import eu.melodic.upperware.guibackend.communication.proactive.ProactiveClientServiceGUI;
import eu.melodic.upperware.guibackend.domain.converter.DomainConverterFactory;
import eu.melodic.upperware.guibackend.domain.converter.GenericConverter;
import io.github.cloudiator.rest.model.Function;
import io.github.cloudiator.rest.model.Node;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ow2.proactive.sal.model.Deployment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth/application")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationController {

//    private CloudiatorApi cloudiatorApi;
    private ProactiveClientServiceGUI proactiveClientServiceGUI;
    private final DomainConverterFactory domainConverterFactory;

    @GetMapping("/node/vm")
    @ResponseStatus(HttpStatus.OK)
    public List<eu.passage.upperware.commons.model.internal.Node> getVMByonNodeList() {
        log.info("GET request for VM and Byon list");
        final List<eu.passage.upperware.commons.model.internal.Node> domains = ((GenericConverter<Deployment, eu.passage.upperware.commons.model.internal.Node>) domainConverterFactory.getNodeConverter()).createDomains(proactiveClientServiceGUI.getAllNodes());
        log.info("ApplicationController->getVMByonNodeList converted to internal/domain nodes: {}", domains);
        return domains;
    }

    @GetMapping("/node/faas")
    @ResponseStatus(HttpStatus.OK)
    public List<Node> getFaasNodeList() {
        log.info("GET request for FAAS list");
        log.warn("Fetching FAAS nodes list is not implemented yet.");
//        return cloudiatorApi.getFaasFromNodeList();
        return Collections.emptyList();
    }

    @GetMapping("/function")
    @ResponseStatus(HttpStatus.OK)
    public List<Function> getFunctionList() {
        log.info("GET functions list");
        log.warn("Fetching functions list is not implemented yet.");
//        return cloudiatorApi.getFunctionList();
        return Collections.emptyList();
    }
}
