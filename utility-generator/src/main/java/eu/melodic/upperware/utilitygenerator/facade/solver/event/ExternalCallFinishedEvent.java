package eu.melodic.upperware.utilitygenerator.facade.solver.event;

import org.springframework.context.ApplicationEvent;

import java.util.Map;

public abstract class ExternalCallFinishedEvent extends ApplicationEvent {

    private final Map<String, Object> data;

    protected ExternalCallFinishedEvent(Map<String, Object> data, Object source) {
        super(source);
        this.data = data;
    }

    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ExternalCallFinishedEvent{" +
                "data=" + data +
                '}';
    }
}
