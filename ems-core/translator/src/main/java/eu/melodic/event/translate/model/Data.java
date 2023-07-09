package eu.melodic.event.translate.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@lombok.Data
@SuperBuilder()
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Data extends Feature {
    private DataSource dataSource;
    @Builder.Default
    private List<Data> includedData = new ArrayList<>();
}
