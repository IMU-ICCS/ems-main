package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AdapterSchedule implements Data {

    private String jobName;
    private AdapterSchedule.InstantiationEnum instantiation;

    @Override
    public String getName() {
        return "AdapterSchedule_" + jobName + "_" + instantiation.name();
    }

    public enum InstantiationEnum {
        AUTOMATIC,
        MANUAL
    }
}
