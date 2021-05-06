package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;

@Getter
@Builder
@ToString(callSuper = true)
public class AdapterSink {

    private AdapterSinkType sinkType;
    private List<Pair<String, String>> configuration;
}
