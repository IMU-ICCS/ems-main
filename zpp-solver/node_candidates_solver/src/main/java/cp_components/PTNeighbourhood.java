package cp_components;

import lombok.AllArgsConstructor;
import node_candidate.NodeCandidatesPool;
import org.jamesframework.core.search.neigh.Move;
import org.jamesframework.core.search.neigh.Neighbourhood;

import java.util.List;
import java.util.Random;
@AllArgsConstructor
public class PTNeighbourhood implements Neighbourhood<PTSolution> {
    private NodeCandidatesPool nodeCandidatesPool;
    @Override
    public Move<? super PTSolution> getRandomMove(PTSolution ptSolution, Random random) {
        List<? extends Move<? super PTSolution>> moves = getAllMoves(ptSolution);
        return moves.get(random.nextInt(moves.size()));
    }

    @Override
    public List<? extends Move<? super PTSolution>> getAllMoves(PTSolution ptSolution) {
        return nodeCandidatesPool.getAllMoves(ptSolution.getVarAssignments());
    }
}
