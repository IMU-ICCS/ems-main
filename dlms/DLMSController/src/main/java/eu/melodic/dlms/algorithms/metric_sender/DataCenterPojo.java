package eu.melodic.dlms.algorithms.metric_sender;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DataCenterPojo {

    private DataCenter dataCenter1;
    private DataCenter dataCenter2;

    private long latencyVal;
    private long bandwidthVal;

    @Builder
    @Getter
    static class DataCenter {
        private String cloudProvider;
        private boolean cpPublic;
        private String dataCenter;
        private String region;
    }

}