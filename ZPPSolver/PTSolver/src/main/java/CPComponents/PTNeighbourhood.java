package CPComponents;
/*
    Neighbourhood of a search space element - V -
    is defined to be a set of all elements which may be transformed to
    V with one move.
 */
import org.jamesframework.core.search.neigh.Move;
import org.jamesframework.core.search.neigh.Neighbourhood;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PTNeighbourhood implements Neighbourhood<PTSolution> {
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
            if (cpSolution.canMoveUp(i)) {
                moves.add(PTMover.newUpMove(i));
            }
            if (cpSolution.canMoveDown(i)) {
                moves.add(PTMover.newDownMove(i));
            }
        }
        return moves;
    }
}
