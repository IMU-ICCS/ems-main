package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Builder
@ToString(callSuper = true)
public class AdapterOperatingSystem {
    private AdapterOperatingSystemArchitecture operatingSystemArchitecture;
    private AdapterOperatingSystemFamily operatingSystemFamily;
    private BigDecimal operatingSystemVersion;
}
