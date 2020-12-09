package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString(callSuper = true)
public class AdapterCheckFinish implements Data {

    private String queueName;
    private String taskName;

    @Override
    public String getName() {
        return "AdapterCheckFinish_" + taskName + "_" + queueName;
    }
}
