package eu.melodic.upperware.guibackend.controller.testing.response;

import eu.passage.upperware.commons.model.testing.TestConfiguration;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TestConfigurationResponse {
    private String path;
    private TestConfiguration configuration;
}
