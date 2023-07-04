package eu.melodic.event.translate.model;

import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.HashMap;
import java.util.Map;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractInterfaceRootObject extends AbstractRootObject {
    protected final Map<String, Object> additionalProperties = new HashMap<>();
}
