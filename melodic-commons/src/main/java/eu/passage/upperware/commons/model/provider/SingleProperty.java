package eu.passage.upperware.commons.model.provider;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class SingleProperty {

    private long id;

    private String key;

    private String value;
}
