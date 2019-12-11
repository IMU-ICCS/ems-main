package CPComponents;

import org.jamesframework.core.search.neigh.Move;

import java.util.Collections;
import java.util.Map;

public class PTMover implements Move<PTSolution> {
    enum MOVE_DIRECTION {
        UP,
        DOWN
    };

    private PTMover(Map<Integer, MOVE_DIRECTION> move) {
        this.move = move;
    }
    /*
        Should always be a singleton map x ---> y.
        X denotes index of variable which should be changed.
     */
    private Map<Integer, MOVE_DIRECTION> move;

    /*
        Here we may assume that the move is possible
     */
    @Override
    public void apply(PTSolution cpSolution) {
            if (move.values().contains(MOVE_DIRECTION.UP)) {
                cpSolution.increaseValue(move.keySet().iterator().next());
            } else {
                cpSolution.decreaseValue(move.keySet().iterator().next());
            }
    }

    @Override
    public void undo(PTSolution cpSolution) {
        if (move.values().contains(MOVE_DIRECTION.DOWN)) {
            cpSolution.increaseValue(move.keySet().iterator().next());
        } else {
            cpSolution.decreaseValue(move.keySet().iterator().next());
        }
    }

    public static PTMover newUpMove(int var) {
        return new PTMover(Collections.singletonMap(var, MOVE_DIRECTION.UP));
    }

    public static PTMover newDownMove(int var) {
        return new PTMover(Collections.singletonMap(var, MOVE_DIRECTION.DOWN));
    }
}
