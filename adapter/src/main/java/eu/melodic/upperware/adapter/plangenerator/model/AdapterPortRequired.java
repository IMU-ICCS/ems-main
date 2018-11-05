package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AdapterPortRequired extends AdapterPort {

    private String updateAction;
    private Boolean isMandatory;

    @Builder
    public AdapterPortRequired(String type, String name, String updateAction, Boolean isMandatory) {
        super(type, name);
        this.updateAction = updateAction;
        this.isMandatory = isMandatory;
    }
}
