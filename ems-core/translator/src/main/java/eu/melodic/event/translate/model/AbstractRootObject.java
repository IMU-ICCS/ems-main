package eu.melodic.event.translate.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@lombok.Data
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractRootObject implements Serializable {
    @JsonProperty("@objectClass")
    private final String _objectClass = getClass().getName();

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
