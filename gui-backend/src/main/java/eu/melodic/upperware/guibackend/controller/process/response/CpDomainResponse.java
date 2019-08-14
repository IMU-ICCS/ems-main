package eu.melodic.upperware.guibackend.controller.process.response;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CpDomainResponse {
    private Object to;
    private Object from;
    private List<Object> values;
    private String type;
}
