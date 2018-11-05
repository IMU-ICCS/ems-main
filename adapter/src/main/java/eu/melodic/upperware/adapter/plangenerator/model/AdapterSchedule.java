package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Setter
@Builder
public class AdapterSchedule implements Data {

    private String jobName;
    private AdapterSchedule.InstantiationEnum instantiation;

    @Override
    public String getName() {
        return "AdapterSchedule_" + jobName + "_" + instantiation.name();
    }

    public enum InstantiationEnum {
        AUTOMATIC,
        MANUAL;
    }
}
