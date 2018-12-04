package eu.melodic.upperware.adapter.executioncontext.colosseum;

import lombok.Getter;

@Getter
public class ShelveSchedule extends ShelveElement {

    private String jobId;

    public ShelveSchedule(String id, String queueId, String jobId) {
        super(id, queueId);
        this.jobId = jobId;
    }

}
