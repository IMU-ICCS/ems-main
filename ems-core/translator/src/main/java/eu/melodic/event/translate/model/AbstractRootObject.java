package eu.melodic.event.translate.model;

import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractRootObject {
    protected Object object;
    protected NamedElement container;

    public <T> T getObject(Class<T> c) {
        return c.cast(object);
    }

    public <T extends NamedElement> T getContainer(Class<T> c) {
        return c.cast(container);
    }
}
