package eu.paasage.upperware.profiler.cp.generator;

import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import org.apache.log4j.Logger;

public class OrchestratorUtils {

    private static Logger logger= GenerationOrchestrator.getLogger();

    public static String  generateCPModel(String modelId){
        logger.debug("model id "+modelId);
        logger.debug("Creating GenerationOrchestrator");
        GenerationOrchestrator go= new GenerationOrchestrator();
        logger.debug("Generating CP Model");
        String paasageConfigID= go.generateCPModel(modelId);
        logger.debug("CP Model Generated");
        return paasageConfigID;
    }
}
