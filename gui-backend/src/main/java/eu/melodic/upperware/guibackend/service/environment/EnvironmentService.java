package eu.melodic.upperware.guibackend.service.environment;

import eu.melodic.upperware.guibackend.service.deployment.DeploymentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EnvironmentService {

    private DeploymentService deploymentService;

    // todo
    public void checkComponentsStatus() {
    }


    public void restartMelodic() throws IOException {
        List<String> allXmiModels = deploymentService.getAllXmiModels();
        for(String xmiModel : allXmiModels) {
            deploymentService.deleteXmiModel(xmiModel);
        }
        runRestartScript();
    }

    public void runRestartScript() throws IOException {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("sh", "restartscript.sh");
            Process process = processBuilder.start();

            StringBuilder output = new StringBuilder();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream()));

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }
            log.info(String.valueOf(output));
            process.waitFor();

            log.info("restart script executed");
            } catch (IOException | InterruptedException exception){
            exception.printStackTrace();
        }
    }
}
