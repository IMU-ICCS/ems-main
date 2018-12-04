package eu.melodic.upperware.adapter.planexecutor;

import eu.melodic.upperware.adapter.exception.AdapterException;
import io.github.cloudiator.rest.model.Queue;

public interface TaskWatchDog {

    Queue watch(String queueId) throws AdapterException;
}
