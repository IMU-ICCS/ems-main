package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@EqualsAndHashCode
public class AdapterTask implements Data{

    private String name;
    private List<AdapterPort> ports;
    private List<AdapterTaskInterface> interfaces;
    private AdapterExecutionEnvironment executionEnvironment;

    private AdapterTaskType taskType;

}
