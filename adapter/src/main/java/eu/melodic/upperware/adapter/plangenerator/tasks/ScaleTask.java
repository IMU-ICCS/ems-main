package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterScale;
import lombok.ToString;

import java.util.function.Function;

import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;

@ToString(callSuper = true)
public class ScaleTask extends ConfigurationTask<AdapterScale>{

    public static final Function<AdapterScale, ScaleTask> SCALE_TASK_CREATE = adapterScale -> new ScaleTask(CREATE, adapterScale);
    public static final Function<AdapterScale, ScaleTask> SCALE_TASK_DELETE = adapterScale -> new ScaleTask(DELETE, adapterScale);

    public ScaleTask(Type type, AdapterScale data) {
        super(type, data);
    }
}
