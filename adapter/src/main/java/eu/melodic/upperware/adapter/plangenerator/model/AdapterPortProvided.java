package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode(callSuper = true)
public class AdapterPortProvided extends AdapterPort {

    private Integer port;

    @Builder
    AdapterPortProvided(String type, String name, Integer port) {
        super(type, name);
        this.port = port;
    }

}
