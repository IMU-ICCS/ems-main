package eu.melodic.upperware.adapter.planexecutor;

import eu.melodic.upperware.adapter.exception.AdapterException;
import io.github.cloudiator.rest.model.Queue;

import java.util.function.Function;

public interface TaskWatchDog {

    Queue watch(String queueId) throws AdapterException;

    Queue watch(String queueId, Function<String, Boolean> function) throws AdapterException;
}
