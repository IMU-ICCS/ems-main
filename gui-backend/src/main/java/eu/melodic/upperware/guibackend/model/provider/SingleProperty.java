package eu.melodic.upperware.guibackend.model.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class SingleProperty {

    private long id;

    private String key;

    private String value;
}
