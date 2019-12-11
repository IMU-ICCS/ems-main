package CPComponents;

import org.jamesframework.core.search.neigh.Move;
import org.jamesframework.core.search.neigh.Neighbourhood;

import java.util.List;
import java.util.Random;

public class PTNeighbourhood implements Neighbourhood<PTSolution> {
    @Override
    public Move<? super PTSolution> getRandomMove(PTSolution cpSolution, Random random) {
        return null;
    }

    @Override
    public List<? extends Move<? super PTSolution>> getAllMoves(PTSolution cpSolution) {
        return null;
    }
}
