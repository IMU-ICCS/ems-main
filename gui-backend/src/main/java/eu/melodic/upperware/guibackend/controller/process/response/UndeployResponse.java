package eu.melodic.upperware.guibackend.controller.process.response;

import eu.melodic.upperware.guibackend.controller.common.UndeployState;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UndeployResponse {
    UndeployState state;

}
