package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterProcess;
import lombok.ToString;

import java.util.function.Function;

import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;

@ToString(callSuper = true)
public class ProcessTask extends ConfigurationTask<AdapterProcess> {

    public static final Function<AdapterProcess, ProcessTask> PROCESS_TASK_CREATE = adapterProcess -> new ProcessTask(CREATE, adapterProcess);
    public static final Function<AdapterProcess, ProcessTask> PROCESS_TASK_DELETE = adapterProcess -> new ProcessTask(DELETE, adapterProcess);

    private ProcessTask(Type type, AdapterProcess data) {
        super(type, data);
    }
}
