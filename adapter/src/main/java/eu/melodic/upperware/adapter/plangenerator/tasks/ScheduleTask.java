package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterSchedule;
import lombok.ToString;

import java.util.function.Function;

import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;

@ToString(callSuper = true)
public class ScheduleTask extends ConfigurationTask<AdapterSchedule> {

    public static final Function<AdapterSchedule, ScheduleTask> SCHEDULE_TASK_CREATE = adapterSchedule -> new ScheduleTask(CREATE, adapterSchedule);
    public static final Function<AdapterSchedule, ScheduleTask> SCHEDULE_TASK_DELETE = adapterSchedule -> new ScheduleTask(DELETE, adapterSchedule);

    private ScheduleTask(Type type, AdapterSchedule data) {
        super(type, data);
    }
}
