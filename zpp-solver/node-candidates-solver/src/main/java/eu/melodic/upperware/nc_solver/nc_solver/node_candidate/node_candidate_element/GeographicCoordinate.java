package eu.melodic.upperware.nc_solver.nc_solver.node_candidate.node_candidate_element;

import eu.melodic.upperware.cp_wrapper.utils.numeric_value.NumericValueInterface;
import eu.melodic.upperware.cp_wrapper.utils.numeric_value.implementations.LongValue;
import eu.melodic.upperware.nc_solver.nc_solver.node_candidate.VariableValueKeeperInterface;
import eu.melodic.upperware.nc_solver.nc_solver.variable_orderer.ComponentVariableOrderer;
import eu.melodic.upperware.nc_solver.nc_solver.variable_orderer.VariableTypeOrderer;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.javatuples.Pair;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class GeographicCoordinate implements Comparable<GeographicCoordinate>,
        VariableValueKeeperInterface, NodeCandidateElementInterface {
    private long latitude;
    private long longitude;

    @Override
    public int compareTo(GeographicCoordinate o) {
        if (this.equals(o)) {
            return 0;
        } else if (latitude > o.latitude || (latitude == o.latitude && longitude > o.longitude)) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public List<Pair<NumericValueInterface, Integer>> getValues(int component) {
        return Arrays.asList(
                new Pair<>( new LongValue(latitude), component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT
                        + VariableTypeOrderer.mapTypeToIndex(VariableType.LATITUDE)),
                new Pair<>( new LongValue(longitude), component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT
                        + VariableTypeOrderer.mapTypeToIndex(VariableType.LONGITUDE))
        );
    }
}
