package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdapterJob implements Data {

    private String jobName;

    private List<AdapterTask> tasks;

    private List<AdapterCommunication> communications;

    @Override
    public String getName() {
        return "AdapterJob_" + jobName;
    }

}
