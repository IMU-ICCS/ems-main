package eu.melodic.upperware.guibackend.service.environment;

import eu.melodic.upperware.guibackend.properties.GuiBackendProperties;
import eu.melodic.upperware.guibackend.service.deployment.DeploymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EnvironmentService {
    private GuiBackendProperties guiBackendProperties;
    private DeploymentService deploymentService;

    // todo
    public void checkComponentsStatus() {
    }

    public void restartMelodic() throws IOException {
        List<String> allXmiModels = deploymentService.getAllXmiModels();
        for(String xmiModel : allXmiModels)
        deploymentService.deleteXmiModel(xmiModel);
        runRestartScript();
    }

    public void runRestartScript() throws IOException {
        boolean isWindows = System.getProperty("os.name")
                .toLowerCase().startsWith("windows");

        if (isWindows) {
            Runtime.getRuntime().exec("cmd.exe");
        } else {
            Runtime.getRuntime().exec("./restartscript.sh");
        }
    }
}
