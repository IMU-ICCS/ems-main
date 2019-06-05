package eu.melodic.upperware.guibackend.controller.process.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpConstantResponse {
    private String id;
    private String type;
    private Object value;
}
