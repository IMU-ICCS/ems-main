package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AdapterCheckFinish implements Data {

    private String queueName;
    private String taskName;

    @Override
    public String getName() {
        return "AdapterCheckFinish_" + taskName + "_" + queueName;
    }
}
