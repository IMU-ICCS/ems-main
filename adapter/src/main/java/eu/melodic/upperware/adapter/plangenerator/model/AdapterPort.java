package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdapterPort {

    private String type;
    private String name;
}
