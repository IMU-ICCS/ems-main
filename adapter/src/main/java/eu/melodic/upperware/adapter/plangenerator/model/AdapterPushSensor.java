package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdapterPushSensor implements AdapterSensor {

    private int port;
}
