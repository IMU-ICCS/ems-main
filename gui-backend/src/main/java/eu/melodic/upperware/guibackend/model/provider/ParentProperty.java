package eu.melodic.upperware.guibackend.model.provider;

import lombok.*;

import java.util.List;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ParentProperty {

    private long id;

    private String name;

    private List<SingleProperty> properties;
}
