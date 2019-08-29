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
