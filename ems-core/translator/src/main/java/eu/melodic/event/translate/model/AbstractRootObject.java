package eu.melodic.event.translate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractRootObject implements Serializable {
    @JsonIgnore
    protected transient Object object;
    protected NamedElement container;

    public <T> T getObject(Class<T> c) {
        return c.cast(object);
    }

    public <T extends NamedElement> T getContainer(Class<T> c) {
        return c.cast(container);
    }
}
