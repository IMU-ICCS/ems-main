package node_candidate;

import cp_wrapper.utils.numeric_value.NumericValueInterface;
import cp_wrapper.utils.numeric_value.implementations.DoubleValue;
import eu.paasage.upperware.metamodel.cp.VariableType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import org.javatuples.Pair;
import variable_orderer.ComponentVariableOrderer;
import variable_orderer.VariableTypeOrderer;

import java.util.Arrays;
import java.util.List;

@Data
@AllArgsConstructor
public class GeographicCoordinate implements Comparable<GeographicCoordinate>, VariableValueKeeperInterface{
    private double latitude;
    private double longitude;

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
                new Pair( new DoubleValue(latitude), component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT
                        + VariableTypeOrderer.mapTypeToIndex(VariableType.LATITUDE)),
                new Pair( new DoubleValue(longitude), component * ComponentVariableOrderer.VARIABLES_PER_COMPONENT
                        + VariableTypeOrderer.mapTypeToIndex(VariableType.LONGITUDE))
        );
    }
}
