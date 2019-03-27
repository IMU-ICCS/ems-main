package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterMonitor;
import lombok.ToString;

import java.util.function.Function;

import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;

@ToString(callSuper = true)
public class MonitorTask extends ConfigurationTask<AdapterMonitor>{

    public static final Function<AdapterMonitor, MonitorTask> MONITOR_TASK_CREATE = adapterMonitor -> new MonitorTask(CREATE, adapterMonitor);
    public static final Function<AdapterMonitor, MonitorTask> MONITOR_TASK_DELETE = adapterMonitor -> new MonitorTask(DELETE, adapterMonitor);

    private MonitorTask(Type type, AdapterMonitor data) {
        super(type, data);
    }
}
