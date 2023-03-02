package eu.melodic.upperware.utilitygenerator.facade.solver;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
public class CamundaRestController {

    private final FacadeUtilityService utilityService;

    @Autowired
    public CamundaRestController(FacadeUtilityService utilityService) {
        this.utilityService = utilityService;
    }

    @PostMapping("/utilityGeneratorInitialisation")
    public ResponseEntity<?> inittializeUtitilityGeneratorInstance(@RequestBody Map<String, Object> data) {
        log.info("Received data: {}", data);

        Map<String, Object> result = utilityService.processCamundaRequest(data);
        if("success".equals(result.get(FacadeUtilityService.RESULT_PROPERTY_NAME))) {
            return ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(result.get(FacadeUtilityService.RESULT_PROPERTY_NAME));
        }
    }

}
