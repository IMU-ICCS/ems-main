package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AdapterProcess implements Data {

    private String nodeName;
    private String scheduleName;
    private String jobName;
    private String taskName;

    @Override
    public String getName() {
        return String.format("AdapterProcess_%s_%s_%s_%s", nodeName, scheduleName, jobName, taskName);
    }
}
