package eu.paasage.upperware.profiler.cp.generator;

import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OrchestratorUtils {

    public static String  generateCPModel(String modelId){
        log.debug("model id "+modelId);
        log.debug("Creating GenerationOrchestrator");
        GenerationOrchestrator go= new GenerationOrchestrator();
        log.debug("Generating CP Model");
        String paasageConfigID= go.generateCPModel(modelId);
        log.debug("CP Model Generated");
        return paasageConfigID;
    }
}
