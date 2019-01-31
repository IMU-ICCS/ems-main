package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.Data;
import lombok.ToString;

@ToString(callSuper = true)
public class WaitTask extends ConfigurationTask {
    public WaitTask(Type type, Data data) {
        super(type, data);
    }
}
