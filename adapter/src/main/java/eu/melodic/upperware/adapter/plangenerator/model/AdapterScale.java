package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdapterScale implements Data {

    private List<String> nodeNames;
    private String scheduleName;
    private String jobName;
    private String taskName;

    @Override
    public String getName() {
        return String.format("AdapterProcess_%s_%s_%s_%s", nodeNames, scheduleName, jobName, taskName);
    }
}
