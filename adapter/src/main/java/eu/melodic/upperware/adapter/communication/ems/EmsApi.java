package eu.melodic.upperware.adapter.communication.ems;

import eu.melodic.models.commons.Watermark;
import eu.melodic.models.services.adapter.Monitor;

import java.util.List;

public interface EmsApi {

    List<Monitor> getMonitors(String applicationId, Watermark watermark, String authorization);

}
