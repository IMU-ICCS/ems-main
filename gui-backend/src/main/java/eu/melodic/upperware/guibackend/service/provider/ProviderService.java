package eu.melodic.upperware.guibackend.service.provider;

import eu.melodic.upperware.guibackend.model.provider.CloudDefinition;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProviderService {

    // todo get from DB
    public List<CloudDefinition> getCloudDefinitionsForAllProviders() {
        Yaml yaml = new Yaml();
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(new File(System.getenv("MELODIC_CONFIG_DIR") + "/gui_providers_data.yaml"));
        } catch (FileNotFoundException e) {
            log.error("File with providers configuration is missing", e);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "File with providers configuration: gui_providers_data.yaml is missing.");
        }
        return yaml.load(fileInputStream);
    }

    // todo get from DB
    public CloudDefinition getCloudDefinition(int cloudDefId) {
        return getCloudDefinitionsForAllProviders().get(cloudDefId);
    }
}
