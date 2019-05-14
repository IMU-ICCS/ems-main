package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class AdapterPortRequired extends AdapterPort {

    private Boolean isMandatory;

    @Builder
    public AdapterPortRequired(String type, String name, Boolean isMandatory) {
        super(type, name);
        this.isMandatory = isMandatory;
    }
}
