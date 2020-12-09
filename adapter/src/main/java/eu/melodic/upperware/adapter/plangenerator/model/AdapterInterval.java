package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(callSuper = true)
public class AdapterInterval {

    private AdapterUnit unit;
    private long period;
}
