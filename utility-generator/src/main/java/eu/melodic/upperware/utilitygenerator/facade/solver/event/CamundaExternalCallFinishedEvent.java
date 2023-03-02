package eu.melodic.upperware.utilitygenerator.facade.solver.event;

import java.util.Map;

public class CamundaExternalCallFinishedEvent extends ExternalCallFinishedEvent {

    public CamundaExternalCallFinishedEvent(Map<String, Object> data, Object source) {
        super(data, source);
    }
}
