package eu.melodic.upperware.adapter.communication.ems;

import eu.melodic.models.interfaces.ems.Monitor;

import java.util.List;

public interface EmsApi {

    List<Monitor> getMonitors(String applicationId);

}
