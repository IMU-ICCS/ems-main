package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterWaitData;
import lombok.ToString;

import java.util.function.Function;

import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;

@ToString(callSuper = true)
public class WaitTask extends ConfigurationTask<AdapterWaitData> {

    public static final Function<AdapterWaitData, WaitTask> WAIT_TASK_CREATE = adapterWaitData -> new WaitTask(CREATE, adapterWaitData);
    public static final Function<AdapterWaitData, WaitTask> WAIT_TASK_DELETE = adapterWaitData -> new WaitTask(DELETE, adapterWaitData);

    private WaitTask(Type type, AdapterWaitData data) {
        super(type, data);
    }
}
