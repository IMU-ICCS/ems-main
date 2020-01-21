package nc_solver.node_candidate.node_candidate_element;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.AllArgsConstructor;
import lombok.Data;
import nc_solver.node_candidate.VariableValueKeeperInterface;
import org.javatuples.Pair;
import nc_solver.variable_orderer.ComponentVariableOrderer;
import nc_solver.variable_orderer.VariableTypeOrderer;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class VMConfiguration implements Comparable<VMConfiguration>,
        VariableValueKeeperInterface, NodeCandidateElementInterface {
    private long cores;
    private long ram;
    private double disk;

    @Override
    public int compareTo(VMConfiguration o) {
        if (this.equals(o)) {
            return 0;
        } else if (
                (cores > o.cores)
                || (cores == o.cores && ram > o.ram)
                || (cores == o.cores && ram == o.ram && disk > o.disk)
        ) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public List<Pair<NumericValueInterface, Integer>> getValues(int component) {
        return Arrays.asList(
                new Pair(new LongValue(cores), component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT
                        + VariableTypeOrderer.mapTypeToIndex(VariableType.CORES)),
                new Pair(new LongValue(ram), component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT
                        + VariableTypeOrderer.mapTypeToIndex(VariableType.RAM)),
                new Pair(new DoubleValue(disk), component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT
                        + VariableTypeOrderer.mapTypeToIndex(VariableType.STORAGE))
        );
    }
}
