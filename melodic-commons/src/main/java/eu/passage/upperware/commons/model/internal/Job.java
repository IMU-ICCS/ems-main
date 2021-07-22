package eu.passage.upperware.commons.model.internal;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
@SuperBuilder
@ToString(callSuper = true)
public class Job implements Serializable {
    @JsonProperty("name")
    private String name;
    @JsonProperty("tasks")
    private List<Task> tasks = null;
    @JsonProperty("submittedJobType")
    private SubmittedJobType submittedJobType;
    @JsonProperty("id")
    private String id;
    @JsonProperty("submittedJobId")
    private long submittedJobId;
}
