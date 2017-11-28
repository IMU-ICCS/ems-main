package eu.paasage.executionware.metric_collector.web;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.emf.cdo.common.id.CDOID;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by pszkup on 25.10.17.
 */
@Getter
@Setter
public class ReadMetrics {

    private Set<CDOID> metricIDs = new HashSet<>();
    private CDOID execContextId;
}
