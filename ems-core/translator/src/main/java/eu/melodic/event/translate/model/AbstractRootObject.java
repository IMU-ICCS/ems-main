package eu.melodic.event.translate.model;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractRootObject {
    protected NamedElement container;

    public <T extends NamedElement> T getContainer(Class<T> c) {
        return (T)container;
    }
}
