
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import eu.melodic.upperware.cp_sampler.Sampler;
import eu.melodic.upperware.cp_sampler.constraint_problem_data.ConstraintProblemData;
import eu.melodic.upperware.cp_sampler.xmi_writer.XMIWriter;
import org.javatuples.Pair;
import org.junit.jupiter.api.Test;
import java.io.IOException;

import static org.junit.Assert.fail;

class SamplerTest {
    private final String pathToSampledCPModel = "src/test/resources/SAMPLE_CP.xmi";
    private final String pathToNCModel = "src/test/resources/Genom-NC";
    private final String pathToSampledNC = "src/test/resources/SAMPLE_NC";

    @Test
    void sample() throws IOException {
        Sampler sampler = new Sampler(2,5, 2);
        FilecacheService filecacheService = new FilecacheService();
        NodeCandidates nodeCandidates = filecacheService.load(pathToNCModel);
        Pair<ConstraintProblemData, NodeCandidates> cpNodes = sampler.sample(nodeCandidates);
        XMIWriter writer = new XMIWriter();
        writer.writeToFile(cpNodes.getValue0(), pathToSampledCPModel);
        filecacheService.store(pathToSampledNC, cpNodes.getValue1());

        try {
            filecacheService.load(pathToSampledNC);
        } catch (Exception e) {
            fail("Node candidates should load withour errors");
        }
    }
}