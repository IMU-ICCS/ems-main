package eu.paasage.executionware.metric_collector.web;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.emf.cdo.common.id.CDOID;

/**
 * Created by pszkup on 25.10.17.
 */
@Getter
@Setter
public class DeleteMetrics {

    private CDOID execContextId;
}
