package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterRequirement;
import lombok.ToString;

import java.util.function.Function;

import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.CREATE;
import static eu.melodic.upperware.adapter.plangenerator.tasks.Type.DELETE;

@ToString(callSuper = true)
public class NodeTask extends ConfigurationTask<AdapterRequirement> {

    public static final Function<AdapterRequirement, NodeTask> NODE_TASK_CREATE = adapterRequirement -> new NodeTask(CREATE, adapterRequirement);
    public static final Function<AdapterRequirement, NodeTask> NODE_TASK_DELETE = adapterRequirement -> new NodeTask(DELETE, adapterRequirement);

    private NodeTask(Type type, AdapterRequirement data) {
        super(type, data);
    }
}
