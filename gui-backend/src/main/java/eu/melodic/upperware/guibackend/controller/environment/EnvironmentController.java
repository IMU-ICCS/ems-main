package eu.melodic.upperware.guibackend.controller.environment;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/environment")
@Slf4j
public class EnvironmentController {

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
        return "Components in OK status";
    }
}
