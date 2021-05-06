package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class AdapterFaasInterface extends AdapterTaskInterface {

    private String functionName;
    private String sourceCodeUrl;
    private String handler;
    private List<AdapterFaasTrigger> triggers;
    private int timeout;
    private Map<String, String> functionEnvironment;
}
