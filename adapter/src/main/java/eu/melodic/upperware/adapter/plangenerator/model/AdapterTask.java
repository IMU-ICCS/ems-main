package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
@ToString(callSuper = true)
public class AdapterTask implements Data{

    private String name;
    private List<AdapterPort> ports;
    private List<AdapterTaskInterface> interfaces;
    private AdapterExecutionEnvironment executionEnvironment;
}
