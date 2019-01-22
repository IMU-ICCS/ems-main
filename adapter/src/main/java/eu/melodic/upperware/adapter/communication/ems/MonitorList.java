package eu.melodic.upperware.adapter.communication.ems;

import eu.melodic.models.interfaces.ems.Monitor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MonitorList {
    private List<Monitor> monitors;
}
