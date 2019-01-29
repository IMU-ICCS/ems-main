package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterMonitor;
import lombok.ToString;

@ToString(callSuper = true)
public class MonitorTask extends ConfigurationTask<AdapterMonitor>{

    public MonitorTask(Type type, AdapterMonitor data) {
        super(type, data);
    }
}
