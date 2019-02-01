package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.WaitData;
import lombok.ToString;

@ToString(callSuper = true)
public class WaitTask extends ConfigurationTask<WaitData> {
    public WaitTask(Type type, WaitData data) {
        super(type, data);
    }
}
