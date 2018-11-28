package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterJob;
import lombok.ToString;

@ToString(callSuper = true)
public class JobTask extends ConfigurationTask<AdapterJob> {

    public JobTask(Type type, AdapterJob data) {
        super(type, data);
    }
}
