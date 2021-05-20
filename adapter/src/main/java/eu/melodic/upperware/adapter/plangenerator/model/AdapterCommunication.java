package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdapterCommunication {

    private String portRequired;
    private String portProvided;
}
