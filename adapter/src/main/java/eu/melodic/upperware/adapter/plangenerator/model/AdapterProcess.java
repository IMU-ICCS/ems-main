package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

import java.util.function.BiPredicate;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdapterProcess implements Data {

    public static final BiPredicate<AdapterProcess, AdapterProcess> PROCESS_BI_PREDICATE = (newReq, oldReq) ->
            newReq.getNodeName().equals(oldReq.getNodeName()) &&
                    newReq.getScheduleName().equals(oldReq.getScheduleName()) &&
                    newReq.getJobName().equals(oldReq.getJobName()) &&
                    newReq.getTaskName().equals(oldReq.getTaskName());

    private String nodeName;
    private String scheduleName;
    private String jobName;
    private String taskName;

    @Override
    public String getName() {
        return String.format("AdapterProcess_%s_%s_%s_%s", nodeName, scheduleName, jobName, taskName);
    }
}
