package eu.melodic.upperware.utilitygenerator.reconfiguration_penalty;

import eu.passage.upperware.commons.model.tools.metadata.CamelMetadata;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@RequiredArgsConstructor
@ToString
public class PenaltyAttribute {


    private final String name;
    private final String componentId;
    private final CamelMetadata type;
    @Setter
    double value;
}
