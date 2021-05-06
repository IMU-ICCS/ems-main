package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AdapterSparkInterface extends AdapterTaskInterface {

    private String file;
    private String className;
    private List<String> arguments;
    private Map<String, String> sparkArguments;
    private Map<String, String> sparkConfiguration;
}
