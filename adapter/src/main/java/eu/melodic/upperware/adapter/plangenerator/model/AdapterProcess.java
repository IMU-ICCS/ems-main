package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
public class AdapterProcess implements Data {

    private String nodeName;
    private String scheduleName;
    private String jobName;
    private String taskName;

    @Override
    public String getName() {
        return "AdapterProcess_" + nodeName + "_" + scheduleName + "_" + jobName + "_" + taskName;
    }
}
