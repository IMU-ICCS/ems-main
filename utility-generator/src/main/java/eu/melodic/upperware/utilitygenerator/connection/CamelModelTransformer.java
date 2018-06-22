package eu.melodic.upperware.utilitygenerator.connection;

import camel.core.CamelModel;
import camel.metric.Metric;
import camel.metric.MetricModel;
import camel.metric.MetricVariable;
import eu.paasage.mddb.cdo.client.CDOClient;
import org.eclipse.emf.common.util.EList;
import org.mariuszgromada.math.mxparser.Argument;

import java.util.Collection;
import java.util.stream.Collectors;

public class CamelModelTransformer {

    private CamelModel model;

    public CamelModelTransformer(String path){
        this.model = (CamelModel) CDOClient.loadModel(path);
    }

    public String getFormula(){

        MetricModel metricModel = model.getMetricModels().get(0);//can I assume that it will be always only 1 metric model?
        EList<Metric> metrics = metricModel.getMetrics();

        Metric utility = metrics.stream()
                .filter(m -> m.getMetricTemplate().getAttribute().getName().equals("costUtility"))
                .findAny()
                .orElseThrow(() -> new IllegalStateException("formula with function does not exists"));

        return ((MetricVariable) utility).getFormula();
    }

    public Collection<Argument> getArgumentsFromCamelModel(){

        return model.getMetricModels()
                .get(0)
                .getMetrics()
                .stream()
                .map(m -> new Argument(m.getName()))
                .collect(Collectors.toList());


    }


}
