package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterProcess;
import lombok.ToString;

import java.util.function.Function;

import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;

@ToString(callSuper = true)
public class ScaleTask extends ConfigurationTask<AdapterProcess>{

    public static final Function<AdapterProcess, ScaleTask> SCALE_TASK_CREATE = adapterProcess -> new ScaleTask(CREATE, adapterProcess);
    public static final Function<AdapterProcess, ScaleTask> SCALE_TASK_DELETE = adapterProcess -> new ScaleTask(DELETE, adapterProcess);

    public ScaleTask(Type type, AdapterProcess data) {
        super(type, data);
    }
}
