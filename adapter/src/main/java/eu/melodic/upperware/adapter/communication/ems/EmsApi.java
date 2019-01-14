package eu.melodic.upperware.adapter.communication.ems;

public interface EmsApi {

    MonitorList getMonitors(String applicationId, String authorization);

}
