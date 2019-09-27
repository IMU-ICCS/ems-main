package eu.melodic.upperware.guibackend.controller.process.response;

import eu.melodic.upperware.guibackend.controller.common.ProcessState;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProcessInstanceResponse {
    private String processId;
    private String applicationId;
    private ProcessState processState;
    private String finishDate;
    private String startDate;
}
