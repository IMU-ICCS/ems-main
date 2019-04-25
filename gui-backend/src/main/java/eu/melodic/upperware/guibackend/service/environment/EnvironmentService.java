package eu.melodic.upperware.guibackend.service.environment;

import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EnvironmentService {

    private GuiBackendProperties guiBackendProperties;

    // todo
    public void checkComponentsStatus() {
    }
}
