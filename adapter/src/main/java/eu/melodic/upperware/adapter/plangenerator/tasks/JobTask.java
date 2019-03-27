package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterJob;
import lombok.ToString;

import java.util.function.Function;

import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;

@ToString(callSuper = true)
public class JobTask extends ConfigurationTask<AdapterJob> {

    public static final Function<AdapterJob, JobTask> JOB_TASK_CREATE = adapterJob -> new JobTask(CREATE, adapterJob);
    public static final Function<AdapterJob, JobTask> JOB_TASK_DELETE = adapterJob -> new JobTask(DELETE, adapterJob);

    private JobTask(Type type, AdapterJob data) {
        super(type, data);
    }
}
