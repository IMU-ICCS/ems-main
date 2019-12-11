package CPComponents;

import CPWrapper.PTCPWrapper;
import org.jamesframework.core.problems.sol.RandomSolutionGenerator;

import java.util.Random;

public class PTRandomGenerator implements RandomSolutionGenerator<PTSolution, PTCPWrapper> {
    @Override
    public PTSolution create(Random random, PTCPWrapper ptcpWrapper) {
        return ptcpWrapper.generateRandom(random);
    }
}
