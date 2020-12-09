package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString(callSuper = true)
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
