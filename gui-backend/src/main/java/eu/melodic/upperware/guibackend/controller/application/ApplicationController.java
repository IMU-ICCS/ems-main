package eu.melodic.upperware.guibackend.controller.application;

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

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth/application")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ApplicationController {

//    private CloudiatorApi cloudiatorApi;

    @GetMapping("/node/vm")
    @ResponseStatus(HttpStatus.OK)
    public List<Node> getVMByonNodeList() {
        log.info("GET request for VM and Byon list");
//        return cloudiatorApi.getVMByonFromNodeList();
        return Collections.emptyList();
    }

    @GetMapping("/node/faas")
    @ResponseStatus(HttpStatus.OK)
    public List<Node> getFaasNodeList() {
        log.info("GET request for FAAS list");
//        return cloudiatorApi.getFaasFromNodeList();
        return Collections.emptyList();
    }

    @GetMapping("/function")
    @ResponseStatus(HttpStatus.OK)
    public List<Function> getFunctionList() {
        log.info("GET functions list");
//        return cloudiatorApi.getFunctionList();
        return Collections.emptyList();
    }
}
