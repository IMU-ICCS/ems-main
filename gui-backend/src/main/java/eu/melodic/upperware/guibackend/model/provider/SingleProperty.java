package eu.melodic.upperware.guibackend.model.provider;

import lombok.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SingleProperty {

    private long id;

    private String key;

    private String value;
}
