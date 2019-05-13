package eu.melodic.upperware.guibackend.controller.provider;

import eu.melodic.upperware.guibackend.model.provider.CloudDefinition;
import eu.melodic.upperware.guibackend.service.provider.ProviderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/provider")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProviderController {

    private ProviderService providerService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CloudDefinition> getCloudDefinitionsForAllProviders() {
        log.info("GET request for cloud definitions for providers");
        return providerService.getCloudDefinitionsForAllProviders();
    }

    @GetMapping("/{cloudDefId}")
    @ResponseStatus(HttpStatus.OK)
    public CloudDefinition getCloudDefinition(@PathVariable(value = "cloudDefId") int cloudDefId) {
        log.info("GET request for cloud definition with id: {}", cloudDefId);
        return providerService.getCloudDefinition(cloudDefId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CloudDefinition createCloudDefinition(@RequestBody CloudDefinition cloudDefinition) {
        log.info("POST request for create cloud definition for provider: {}", cloudDefinition.getApi().getProviderName());
        return providerService.createCloudDefinition(cloudDefinition);
    }

    @DeleteMapping("/{cloudDefId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCloudDefinition(@PathVariable(value = "cloudDefId") int cloudDefId) {
        log.info("DELETE request for cloud definition with id: {}", cloudDefId);
        providerService.deleteCloudDefinition(cloudDefId);
    }

    @PutMapping("/{cloudDefId}")
    @ResponseStatus(HttpStatus.CREATED)
    public CloudDefinition updateCloudDefinition(@PathVariable(value = "cloudDefId") int cloudDefId,
                                                 @RequestBody CloudDefinition cloudDefinitionToUpdate) {
        log.info("PUT request for cloud definition with id: {}", cloudDefId);
        return providerService.updateCloudDefinition(cloudDefId, cloudDefinitionToUpdate);
    }
}
