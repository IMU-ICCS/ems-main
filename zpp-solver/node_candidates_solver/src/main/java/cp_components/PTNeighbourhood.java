package cp_components;

import node_candidate.NodeCandidatesPool;
import org.jamesframework.core.search.neigh.Move;
import org.jamesframework.core.search.neigh.Neighbourhood;

import java.util.List;
import java.util.Random;

public class PTNeighbourhood implements Neighbourhood<PTSolution> {
    private NodeCandidatesPool nodeCandidatesPool;
    @Override
    public Move<? super PTSolution> getRandomMove(PTSolution ptSolution, Random random) {
        return nodeCandidatesPool.getRandomMove(ptSolution.getVarAssignments());
    }

    @Override
    public List<? extends Move<? super PTSolution>> getAllMoves(PTSolution ptSolution) {
        return null;
    }
}
