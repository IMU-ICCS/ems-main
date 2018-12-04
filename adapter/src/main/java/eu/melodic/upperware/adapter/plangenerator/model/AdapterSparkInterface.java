package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString
public class AdapterSparkInterface extends AdapterTaskInterface {

    private String file;
    private String className;
    private List<String> arguments;
    private Map<String, String> sparkArguments;
    private Map<String, String> sparkConfiguration;
}
