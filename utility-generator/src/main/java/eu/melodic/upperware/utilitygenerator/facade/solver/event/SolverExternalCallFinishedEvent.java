package eu.melodic.upperware.utilitygenerator.facade.solver.event;

import java.util.Map;

public class SolverExternalCallFinishedEvent extends ExternalCallFinishedEvent {

    public SolverExternalCallFinishedEvent(Map<String, Object> data, Object source) {
        super(data, source);
    }
}
