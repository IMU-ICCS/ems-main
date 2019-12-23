package eu.melodic.upperware.guibackend.controller.byon;

import eu.melodic.upperware.guibackend.communication.cloudiator.CloudiatorApi;
import eu.melodic.upperware.guibackend.model.byon.ByonDefinition;
import eu.melodic.upperware.guibackend.model.byon.ByonEnums;
import eu.melodic.upperware.guibackend.service.byon.ByonService;
import io.github.cloudiator.rest.model.ByonNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/auth/byon")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ByonController {

    private ByonService byonService;
    private CloudiatorApi cloudiatorApi;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<ByonDefinition> getByonDefinitionList() {
        log.info("GET request for byon definitions list");
        return byonService.getByonDefList(true).orElseGet(ArrayList::new);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ByonDefinition createByonDefinition(@RequestBody ByonDefinition newByonDefinitionRequest) {
        log.info("POST request for new byon definition with name: {}", newByonDefinitionRequest.getName());
        ByonDefinition newByonDefinition = byonService.createByonDef(newByonDefinitionRequest);
        log.info("New byon definition with name: {} and id: {} successfully added to configuration", newByonDefinition.getName(), newByonDefinition.getId());
        return newByonDefinition;
    }

    @GetMapping("/{byonDefinitionId}")
    @ResponseStatus(HttpStatus.OK)
    public ByonDefinition getByonDefinition(@PathVariable(value = "byonDefinitionId") int byonDefinitionId) {
        log.info("GET request for byon definition with id {}", byonDefinitionId);
        return byonService.getByonDef(byonDefinitionId);
    }

    @DeleteMapping("/{byonDefinitionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByonDefinition(@PathVariable(value = "byonDefinitionId") int byonDefinitionId) {
        log.info("DELETE request for byon definition with id {}", byonDefinitionId);
        byonService.deleteByonDef(byonDefinitionId);
        log.info("Byon definition with id {} successfully deleted", byonDefinitionId);
    }

    @PutMapping("/{byonDefinitionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ByonDefinition updateByonDefinition(@PathVariable(value = "byonDefinitionId") int byonDefinitionId,
                                               @RequestBody ByonDefinition byonDefinitionToUpdate) {
        log.info("PUT request for byon definition with id {}", byonDefinitionId);
        ByonDefinition byonDefinition = byonService.updateByonDef(byonDefinitionId, byonDefinitionToUpdate);
        log.info("Byon definition with id {} successfully updated", byonDefinitionId);
        return byonDefinition;
    }

    @PostMapping("/cloudiator/{byonDefinitionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ByonNode createByonNode(@PathVariable(value = "byonDefinitionId") int byonDefinitionId) {
        log.info("POST request for creating new byon node from byon definition with id {}", byonDefinitionId);
        ByonNode byonNode = byonService.createByonNode(byonDefinitionId);
        log.info("Byon node with id {} successfully added to Cloudiator", byonNode.getId());
        return byonNode;
    }

    @GetMapping("/cloudiator")
    @ResponseStatus(HttpStatus.OK)
    public List<ByonNode> getByonNodeListFromCloudiator() {
        log.info("GET request for byon nodes available in Cloudiator");
        return cloudiatorApi.getByonsList();
    }

    @DeleteMapping("/cloudiator/{byonId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByonNodeFromCloudiator(@PathVariable(value = "byonId") String byonId) {
        log.info("DELETE request for byon Cloudiator node with id {}", byonId);
        cloudiatorApi.deleteByon(byonId);
        log.info("Byon with id {} successfully deleted from Cloudiator", byonId);
    }

    @GetMapping("/enum")
    @ResponseStatus(HttpStatus.OK)
    public ByonEnums getByonEnums() {
        log.info("GET request for byon enums");
        return byonService.getByonEnums();
    }
}
