package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterProcess;
import lombok.ToString;

@ToString(callSuper = true)
public class ProcessTask extends ConfigurationTask<AdapterProcess> {

    public ProcessTask(Type type, AdapterProcess data) {
        super(type, data);
    }
}
