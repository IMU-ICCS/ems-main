package eu.paasage.executionware.metric_collector;

import eu.paasage.executionware.metric_collector.web.DeleteMetrics;
import eu.paasage.executionware.metric_collector.web.ReadMetrics;
import eu.paasage.executionware.metric_collector.web.ReadMetrics2;
import eu.paasage.executionware.metric_collector.web.UpdateMetrics;
import eu.paasage.mddb.cdo.client.CDOClient;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.common.id.CDOID;
import org.eclipse.emf.cdo.internal.common.id.CDOIDObjectLongWithClassifierImpl;
import org.eclipse.emf.cdo.view.CDOView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.BadRequestException;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

//import eu.paasage.upperware.profiler.generator.orchestrator.RequestSynchronizer;

@Slf4j
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class MetricsCollectorController {

    private MetricCollector metricCollector;


    //Read metric definitions based on their ID & start measuring
    @RequestMapping(value = "/readMetrics", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void readMetrics(@RequestBody ReadMetrics readMetrics){
        metricCollector.readMetrics(readMetrics.getMetricIDs(), readMetrics.getExecContextId());
    };

    //Read metric definitions based on their ID & start measuring
    @RequestMapping(value = "/readMetrics2", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void readMetrics(@RequestBody ReadMetrics2 readMetrics2){

        CDOID ecId = CDOIDObjectLongWithClassifierImpl.create(readMetrics2.getAppId());
//        Set<CDOID> dupa = dupa(readMetrics2.getAppId());

        CDOIDObjectLongWithClassifierImpl metricCDOID = CDOIDObjectLongWithClassifierImpl.create("http://www.paasage.eu/2015/06/camel/metric#CompositeMetric#3");

        Set<CDOID> xxx = new HashSet<>();
        xxx.add(metricCDOID);

        metricCollector.readMetrics(xxx, ecId);
    };

    //Read updated metric definitions based on their ID & change measurement process
    @RequestMapping(value = "/updateMetrics", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void updateMetrics(@RequestBody UpdateMetrics updateMetrics){
        metricCollector.readMetrics(updateMetrics.getMetricIDs(), updateMetrics.getExecContextId());
    };

    //Stop measuring metrics mapping to the specific execution context id
    @RequestMapping(value = "/deleteMetrics", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void deleteMetrics(@RequestBody DeleteMetrics deleteMetrics){
        metricCollector.deleteMetrics(deleteMetrics.getExecContextId());
    };

    //Terminate everything - all execution context & metric handlers plus any other
    //Thread will be terminated
    @RequestMapping(value = "/terminate", method = POST, consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    public void terminate(){
        metricCollector.terminate();
    };


    @ExceptionHandler
    @ResponseStatus(BAD_REQUEST)
    public String handleException(BadRequestException exception) {
        log.error("Returning error response: invalid request {}", exception.getMessage());
        return exception.getMessage();
    }

//    private CDOIDObjectLongWithClassifierImpl toCDOID(long value) {
//        CDOIDObjectLongWithClassifierImpl.create("");
//        CDOIDObjectLongWithClassifierImpl.create(value, CDOClassifierRef)
//    }

    private Set<CDOID> dupa(String id){
        CDOClient cl = null;
        CDOView view = null;
        try {
            cl = new CDOClient();
            view = cl.openView();
            CDOID ecId = CDOIDObjectLongWithClassifierImpl.create(id);
            Set<CDOID> topMetrics = CDOUtils.getTopMetrics(view, ecId);
            Set<CDOID> globalMetrics = CDOUtils.getGlobalMetrics(topMetrics, view);
            return globalMetrics;
        } finally {
            view.close();
            cl.closeSession();
        }
    }

}