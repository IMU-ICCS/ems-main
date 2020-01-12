package node_candidate;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import org.javatuples.Pair;

import java.util.List;

public interface VariableValueKeeperInterface {
    /*
        Pairs <variable Value, variable index>
     */
    List<Pair<NumericValueInterface, Integer>> getValues(int component);
}
