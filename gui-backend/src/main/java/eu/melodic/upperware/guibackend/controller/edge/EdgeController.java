package eu.melodic.upperware.guibackend.controller.edge;

import eu.melodic.upperware.guibackend.service.edge.EdgeService;
import eu.passage.upperware.commons.model.edge.EdgeDefinition;
import eu.passage.upperware.commons.model.edge.EdgeEnums;
import eu.passage.upperware.commons.model.edge.EdgeNode;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

import java.util.List;

@RestController
@RequestMapping("/auth/edge")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EdgeController {

    private EdgeService edgeService;

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<EdgeDefinition> getEdgeDefinitionList() {
        log.info("GET request for Edge definitions list");
        return edgeService.getEdgeDefList(true).orElseGet(ArrayList::new);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public EdgeDefinition createEdgeDefinition(@RequestBody EdgeDefinition newEdgeDefinitionRequest) {
        log.info("POST request for new Edge definition with name: {}", newEdgeDefinitionRequest.getName());
        EdgeDefinition newEdgeDefinition = edgeService.createEdgeDef(newEdgeDefinitionRequest);
        log.info("New Edge definition with name: {} and id: {} successfully added to configuration", newEdgeDefinition.getName(), newEdgeDefinition.getId());
        return newEdgeDefinition;
    }

    @GetMapping("/{EdgeDefinitionId}")
    @ResponseStatus(HttpStatus.OK)
    public EdgeDefinition getEdgeDefinition(@PathVariable(value = "EdgeDefinitionId") int edgeDefinitionId) {
        log.info("GET request for Edge definition with id {}", edgeDefinitionId);
        return edgeService.getEdgeDef(edgeDefinitionId);
    }

    @DeleteMapping("/{EdgeDefinitionId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteEdgeDefinition(@PathVariable(value = "EdgeDefinitionId") int edgeDefinitionId) {
        log.info("DELETE request for Edge definition with id {}", edgeDefinitionId);
        edgeService.deleteEdgeDef(edgeDefinitionId);
        log.info("Edge definition with id {} successfully deleted", edgeDefinitionId);
    }

    @PutMapping("/{EdgeDefinitionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public EdgeDefinition updateEdgeDefinition(@PathVariable(value = "EdgeDefinitionId") int edgeDefinitionId,
                                               @RequestBody EdgeDefinition edgeDefinitionToUpdate) {
        log.info("PUT request for Edge definition with id {}", edgeDefinitionId);
        EdgeDefinition edgeDefinition = edgeService.updateEdgeDef(edgeDefinitionId, edgeDefinitionToUpdate);
        log.info("Edge definition with id {} successfully updated", edgeDefinitionId);
        return edgeDefinition;
    }

    @PostMapping("/proactive/{EdgeDefinitionId}")
    @ResponseStatus(HttpStatus.CREATED)
    public eu.passage.upperware.commons.model.edge.EdgeNode createEdgeNode(@PathVariable(value = "EdgeDefinitionId") int EdgeDefinitionId, @RequestParam(value = "applicationId") String applicationId,
                                                                           @RequestParam(value = "automate") boolean automate) {
        log.info("POST request for creating new Edge node from Edge definition with id {} and for application id {}", EdgeDefinitionId, applicationId);
        final eu.passage.upperware.commons.model.edge.EdgeNode edgeNode = edgeService.createEdgeNode(EdgeDefinitionId, applicationId);
        log.info("Edge node with id {} successfully added to Proactive", edgeNode.getId());
        return edgeNode;
    }

    @GetMapping("/cloudiator") // TODO: change to "proactive", but also in frontend
    @ResponseStatus(HttpStatus.OK)
    public List<EdgeNode> getEdgeNodesListFromProactive() {
        log.info("GET request for edge nodes available in Proactive");
        return edgeService.getAllEdgeNodesList();
    }

    @GetMapping("/enum")
    @ResponseStatus(HttpStatus.OK)
    public EdgeEnums getEdgeEnums() {
        log.info("GET request for Edge enums");
        return edgeService.getEdgeEnums();
    }
}
