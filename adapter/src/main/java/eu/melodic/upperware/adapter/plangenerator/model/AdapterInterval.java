package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdapterInterval {

    private AdapterUnit unit;
    private long period;
}
