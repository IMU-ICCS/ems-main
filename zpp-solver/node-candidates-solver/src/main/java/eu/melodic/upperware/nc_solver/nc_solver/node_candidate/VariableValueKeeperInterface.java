package eu.melodic.upperware.nc_solver.nc_solver.node_candidate;

import eu.melodic.upperware.cp_wrapper.utils.numeric_value.NumericValueInterface;
import org.javatuples.Pair;

import java.util.List;

public interface VariableValueKeeperInterface {
    /*
        Pairs <variable Value, variable index>
     */
    List<Pair<NumericValueInterface, Integer>> getValues(int component);
}
