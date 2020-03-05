package eu.melodic.upperware.nc_solver.nc_solver.cp_components;
/*
    Neighbourhood of a search space element - V -
    is defined to be a set of all elements which may be transformed to
    V with one move.
 */
import lombok.AllArgsConstructor;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.NodeCandidatesPool;
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
        return nodeCandidatesPool.getAllMoves(ptSolution);
    }
}
