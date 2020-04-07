package eu.melodic.upperware.mcts_solver.solver.utils;

import eu.melodic.upperware.utilitygenerator.evaluator.ConfigurationElement;
import eu.paasage.upperware.metamodel.cp.VariableType;
import io.github.cloudiator.rest.model.NodeCandidate;

public class VariableExtractor {
    public static long getVariableValue(VariableType type, NodeCandidate nodeCandidate) {
        switch(type) {
            case CORES:
                return nodeCandidate.getHardware().getCores();
            case RAM:
                return  nodeCandidate.getHardware().getRam();
            case STORAGE:
                return nodeCandidate.getHardware().getDisk().intValue();
            case LATITUDE:
                return  ((Double) (100*nodeCandidate.getLocation().getGeoLocation().getLatitude())).intValue();
            case LONGITUDE:
                return ((Double) (100*nodeCandidate.getLocation().getGeoLocation().getLongitude())).intValue();
            default:
                throw new RuntimeException("Unsupported variable type" + type +"!");
        }
    }

    public static long getVariableValue(VariableType type, ConfigurationElement configurationElement) {
       return getVariableValue(type, configurationElement.getNodeCandidate());
    }
}
