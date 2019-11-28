package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class AdapterOperatingSystem {
    private AdapterOperatingSystemArchitecture operatingSystemArchitecture;
    private AdapterOperatingSystemFamily operatingSystemFamily;
    private BigDecimal operatingSystemVersion;
}
