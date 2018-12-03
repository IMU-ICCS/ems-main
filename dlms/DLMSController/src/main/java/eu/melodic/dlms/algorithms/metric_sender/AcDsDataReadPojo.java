package eu.melodic.dlms.algorithms.metric_sender;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AcDsDataReadPojo {
    private long ac;
    private long ds;
    private long amountRead;
}