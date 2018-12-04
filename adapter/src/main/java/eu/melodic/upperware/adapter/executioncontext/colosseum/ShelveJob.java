package eu.melodic.upperware.adapter.executioncontext.colosseum;

import lombok.Getter;

@Getter
public class ShelveJob extends ShelveElement {

    private String jobName;

    public ShelveJob(String id, String jobName) {
        super(id, "dummy/dummy");
        this.jobName = jobName;
    }
}
