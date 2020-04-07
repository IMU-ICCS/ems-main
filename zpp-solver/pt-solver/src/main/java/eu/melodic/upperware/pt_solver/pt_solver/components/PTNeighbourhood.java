package eu.melodic.upperware.pt_solver.pt_solver.components;
/*
    Neighbourhood of a search space element - V -
    is defined to be a set of all elements which may be transformed to
    V with one move.
 */
import eu.melodic.upperware.pt_solver.pt_solver.ptcp_wrapper.PTCPWrapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jamesframework.core.search.neigh.Move;
import org.jamesframework.core.search.neigh.Neighbourhood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
@AllArgsConstructor
@Slf4j
public class PTNeighbourhood implements Neighbourhood<PTSolution> {
    private PTCPWrapper ptcpWrapper;
    @Override
    public Move<? super PTSolution> getRandomMove(PTSolution cpSolution, Random random) {
        List<? extends Move<? super PTSolution>> moves = getAllMoves(cpSolution);
        return moves.get(random.nextInt(moves.size()));
    }

    @Override
    public List<? extends Move<? super PTSolution>> getAllMoves(PTSolution cpSolution) {
        int vars = cpSolution.getVarAssignments().size();
        List<PTMover> moves = new ArrayList<>();
        for (int i = 0; i < vars; i++) {
            if (ptcpWrapper.canMoveUp(cpSolution, i)) {
                moves.add(PTMover.newUpMove(i));
            }
            if (ptcpWrapper.canMoveDown(cpSolution, i)) {
                moves.add(PTMover.newDownMove(i));
            }
        }
        log.debug("Calculating neighbourhood of solution: " +cpSolution.toString() + " number of moves: " + moves.size());
        return moves;
    }
}
