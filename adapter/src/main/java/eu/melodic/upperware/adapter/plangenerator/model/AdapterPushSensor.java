package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString(callSuper = true)
public class AdapterPushSensor implements AdapterSensor {

    private int port;
}
