package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import lombok.ToString;

@ToString(callSuper = true)
public class NodeTask extends ConfigurationTask<AdapterRequirement> {

    public NodeTask(Type type, AdapterRequirement data) {
        super(type, data);
    }
}
