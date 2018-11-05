package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
public class AdapterPortProvided extends AdapterPort {

    private Integer port;

    @Builder
    AdapterPortProvided(String type, String name, Integer port) {
        super(type, name);
        this.port = port;
    }

}
