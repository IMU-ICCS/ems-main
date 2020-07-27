package eu.functionizer.functionizertestingtool;


import eu.passage.upperware.commons.model.testing.FunctionizerTestResult;
import eu.functionizer.functionizertestingtool.service.TestRunner;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FunctionizerTestingToolController {

    private final TestRunner testRunner;

    @RequestMapping("/")
    @ResponseStatus(HttpStatus.OK)
    public String index() {
        return "Welcome to the Functionizer testing tool!";
    }

    @GetMapping(value = "/health")
    @ResponseStatus(HttpStatus.OK)
    public void health() {
    }

    @PostMapping("/test")
    @ResponseStatus(HttpStatus.OK)
    public FunctionizerTestResult runTests() {
        return testRunner.runTests();
    }

}
