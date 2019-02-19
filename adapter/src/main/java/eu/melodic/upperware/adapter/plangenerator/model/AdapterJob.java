package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AdapterJob implements Data {

    private String jobName;

    private List<AdapterTask> tasks;

    private List<AdapterCommunication> communications;

    @Override
    public String getName() {
        return "AdapterJob_" + jobName;
    }

}
