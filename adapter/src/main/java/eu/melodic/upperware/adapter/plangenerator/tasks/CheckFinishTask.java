package eu.melodic.upperware.adapter.plangenerator.tasks;

import eu.melodic.upperware.adapter.plangenerator.model.AdapterCheckFinish;
import lombok.Getter;
import lombok.ToString;

import java.util.function.Function;

@ToString(callSuper = true)
@Getter
public class CheckFinishTask extends ConfigurationTask<AdapterCheckFinish> {

    private Function<String, Boolean> function;

    public CheckFinishTask(Type type, AdapterCheckFinish data, Function<String, Boolean> function) {
        super(type, data);
        this.function = function;
    }

}
