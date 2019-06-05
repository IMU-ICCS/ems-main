package eu.melodic.upperware.guibackend.controller.process.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpMetricResponse {
    private String type;
    private String id;
    private Object value;
}
