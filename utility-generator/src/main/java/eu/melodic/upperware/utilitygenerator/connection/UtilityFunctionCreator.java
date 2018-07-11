package eu.melodic.upperware.utilitygenerator.connection;


import camel.core.CamelModel;
import camel.metric.Metric;
import camel.metric.MetricModel;
import camel.metric.RawMetric;
import camel.metric.impl.MetricTypeModelImpl;
import camel.metric.impl.MetricVariableImpl;
import eu.paasage.mddb.cdo.client.CDOClient;
import org.eclipse.emf.common.util.EList;
import org.mariuszgromada.math.mxparser.Argument;
import org.mariuszgromada.math.mxparser.Expression;

import java.util.ArrayList;
import java.util.Collection;

public class UtilityFunctionCreator {

    public void generateFunction(String formula, String path){

        String formul = formula;
        CamelModel model = (CamelModel) CDOClient.loadModel(path);

        MetricModel metricModel = model.getMetricModels().get(0);//can I assume that it will be always only 1 metric model?
        EList<Metric> metrics = ((MetricTypeModelImpl) metricModel).getMetrics();


        for (Metric m: metrics){
            if (m instanceof MetricVariableImpl){
                if (m.getMetricTemplate().getAttribute().getName().equals("costUtility")){
                    formul = ((MetricVariableImpl)m).getFormula();
                    System.out.println(((MetricVariableImpl)m).getAnnotations().get(0).getId());
                }
            }
            else if (m instanceof RawMetric){
            }
        }

        Collection<Argument> arguments = new ArrayList<Argument>();

        Argument candidateCost = new Argument("CandidateCost");
        Argument workerCost = new Argument("WorkerCost");
        Argument workerCardinality = new Argument("WorkerCardinality");
        Argument niepotrzebny = new Argument("not necessary");

        candidateCost.setArgumentValue(2.0);
        workerCost.setArgumentValue(2.5);
        workerCardinality.setArgumentValue(3);
        niepotrzebny.setArgumentValue(4);

        Expression ex = new Expression(formul, candidateCost, workerCost, workerCardinality);

        double calculate = ex.calculate();
        System.out.println(calculate);

    }



//
//    public void loadModelTest(){
//        CamelModel model = (CamelModel) CDOClient.loadModel("/Users/mrozanska/CRM.xmi");
//        MetricModel metricModel = model.getMetricModels().get(0);
//        MetricVariableImpl metricVariable = (MetricVariableImpl) model.getMetricModels().get(0).getMetrics().get(3);
//        MetricTemplate metricTemplate = metricVariable.getMetricTemplate();
//        String formula = metricVariable.getUtilityFormula();
//        //formula.
//        MeasurableAttribute attribute = metricTemplate.getAttribute();
//        ValueType valueType = attribute.getValueType();
//        model.getUnitModels();
//
//        MmsObject mmsObject = ((CamelModelImpl) model).getRequirementModels().get(0).getRequirements().get(0).getSubFeatures().get(0).getAttributes().get(0).getAnnotations().get(0);
//
//        ((CamelModelImpl)model).getRequirementModels().get(0).getRequirements().get(0).getSubFeatures().get(0).getAnnotations().get(0).getDescription(); //daje CPU z duzych liter - czyli oznacza, że to wymaganie będzie dotyczyło CPU
//
//
//        //model.getRequirementModels().get
//        System.out.println("kkaaa");
//    }






}
