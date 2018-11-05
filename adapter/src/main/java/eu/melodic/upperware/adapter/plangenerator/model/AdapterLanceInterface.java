package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AdapterLanceInterface extends AdapterTaskInterface {

    private String containterType;
    private String init;
    private String preInstall;
    private String install;
    private String postInstall;
    private String preStart;
    private String start;
    private String startDetection;
    private String stopDetection;
    private String postStart;
    private String preStop;
    private String stop;
    private String postStop;
    private String shutdown;
}
