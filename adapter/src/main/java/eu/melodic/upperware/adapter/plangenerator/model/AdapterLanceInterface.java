package eu.melodic.upperware.adapter.plangenerator.model;

import lombok.*;

@Getter
@Setter
@Builder
@ToString
@EqualsAndHashCode(callSuper = true)
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
