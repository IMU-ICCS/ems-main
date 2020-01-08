package cp_components;

import nc_wrapper.NCWrapper;
import org.jamesframework.core.problems.sol.RandomSolutionGenerator;

import java.util.Random;

public class PTRandomGenerator implements RandomSolutionGenerator<PTSolution, NCWrapper> {
    @Override
    public PTSolution create(Random random, NCWrapper ncWrapper) {
        return ncWrapper.generateRandom(random);
    }
}
