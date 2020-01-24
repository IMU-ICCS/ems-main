import static org.junit.jupiter.api.Assertions.*;

import constraint_problem_data.ConstraintProblemData_;
import eu.melodic.cache.NodeCandidates;
import eu.melodic.cache.impl.FilecacheService;
import org.junit.jupiter.api.Test;
import xmi_writer.XMIWriter;

import java.io.IOException;

class SamplerTest {
    @Test
    void sample() throws IOException {
        Sampler sampler = new Sampler();
        FilecacheService filecacheService = new FilecacheService();
        NodeCandidates nodeCandidates = filecacheService.load("C:/Users/ZPP/Downloads/Genom-NC");
        ConstraintProblemData_ cp = sampler.sample(nodeCandidates);
        System.out.println(cp.toString());
        XMIWriter writer = new XMIWriter();
        writer.writeToFile(cp, "C:/Users/ZPP/Desktop/SAMPLE_CP.xmi");

    }
}