package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterSchedule;
import io.github.cloudiator.rest.model.Queue;
import lombok.ToString;

@ToString(callSuper = true)
public class ScheduleTask extends ConfigurationTask<AdapterSchedule> {

    public ScheduleTask(Type type, AdapterSchedule data) {
        super(type, data);
    }
}
