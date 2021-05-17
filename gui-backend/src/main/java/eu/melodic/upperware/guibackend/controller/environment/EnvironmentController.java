package eu.melodic.upperware.guibackend.controller.environment;

import eu.melodic.upperware.guibackend.service.environment.EnvironmentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/auth/environment")
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class EnvironmentController {

    private EnvironmentService environmentService;

    // todo
    @PutMapping(value = "/restart")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void restartMachine() {
        log.info("PUT request for restarting machine");
        log.info("Machine successfully restarted");
    }

    // todo
    @PutMapping(value = "/space")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void prepareSpaceOnMachine() {
        log.info("PUT request for preparing machine");
        log.info("Machine successfully prepared");
    }

    // todo
    @GetMapping(value = "/status")
    public String checkComponentsStatus() {
        this.environmentService.checkComponentsStatus();
        return "Checking components status not implemented";
    }

    @PutMapping(value = "/restartDB")
    @ResponseStatus(HttpStatus.OK)
    public void restartMelodic() throws IOException {
        log.info("PUT request for restarting databases");
        this.environmentService.restartMelodic();
        log.info("Databases successfully restarted");
    }
}
