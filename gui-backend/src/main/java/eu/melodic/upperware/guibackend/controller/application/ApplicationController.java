package eu.melodic.upperware.guibackend.controller.application;

import eu.melodic.upperware.guibackend.communication.cloudiator.CloudiatorClientApi;
import io.github.cloudiator.rest.model.Function;
import io.github.cloudiator.rest.model.Node;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/application")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationController {

    private CloudiatorClientApi cloudiatorClientApi;

    @GetMapping("/node/vm")
    @ResponseStatus(HttpStatus.OK)
    public List<Node> getVMNodeList() {
        log.info("GET request for VM list");
        return cloudiatorClientApi.getVMFromNodeList();
    }

    @GetMapping("/node/faas")
    @ResponseStatus(HttpStatus.OK)
    public List<Node> getFaasNodeList() {
        log.info("GET request for FAAS list");
        return cloudiatorClientApi.getFaasFromNodeList();
    }

    @GetMapping("/function")
    @ResponseStatus(HttpStatus.OK)
    public List<Function> getFunctionList() {
        log.info("GET functions list");
        return cloudiatorClientApi.getFunctionList();
    }
}
