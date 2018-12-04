package eu.melodic.dlms.algorithms.metric_sender;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AcDsDataWritePojo {
    private long ac;
    private long ds;
    private long amountWrite;
}