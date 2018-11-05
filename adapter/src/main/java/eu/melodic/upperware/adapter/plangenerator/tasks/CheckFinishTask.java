package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterCheckFinish;
import lombok.ToString;

@ToString(callSuper = true)
public class CheckFinishTask extends ConfigurationTask<AdapterCheckFinish> {

    public CheckFinishTask(Type type, AdapterCheckFinish data) {
        super(type, data);
    }
}
