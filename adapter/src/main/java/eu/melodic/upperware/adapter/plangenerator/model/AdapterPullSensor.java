package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Getter
@Builder
public class AdapterPullSensor implements AdapterSensor {

    private String className;
    private AdapterInterval interval;
    private List<Pair<String, String>> configuration;

}
