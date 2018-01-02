package eu.paasage.upperware.profiler.generator.service.camel;


import eu.paasage.camel.metric.Property;
//import eu.paasage.upperware.profiler.cp.generator.model.camel.lib.DeploymentModelParser;
//import eu.paasage.upperware.profiler.cp.generator.model.camel.lib.ProviderModelParser;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import lombok.extern.slf4j.Slf4j;

import eu.paasage.camel.requirement.OptimisationFunctionType;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;
import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;
import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType;
//import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
//import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * This class offers the functionality to deal with camel models
 * @author danielromero
 *
 */
@Slf4j
public class CamelModelProcessorService {

    public static final String ATTRIBUTE = "Attribute";

//    protected DeploymentModelParser deploymentModelParser;
//    protected ProviderModelParser providerModelParser;

    protected IDatabaseProxy proxy;

    @Autowired
    public CamelModelProcessorService(IDatabaseProxy proxy) {
        this.proxy = proxy;
    }

    //    /**

    private String getKey(FunctionType ft, OptimisationRequirement optReq){
        String id = ft.getId();
        if (!ATTRIBUTE.equalsIgnoreCase(id)){
            return id;
        }
        return id + "_" + getPathToAttribute(optReq);
    }

    private String getFunctionName(OptimisationRequirement optReq) {
        return getProperty(optReq).getName();
    }

    private String getPathToAttribute(OptimisationRequirement optReq) {
        return getProperty(optReq).getDescription();
    }

    private Property getProperty(OptimisationRequirement optReq){
        return optReq.getMetric() != null ? optReq.getMetric().getProperty() : optReq.getProperty();
    }

    protected ComponentMetricRelationship createComponentMetricRelationship(ApplicationComponent appc, String metricId) {
        ComponentMetricRelationship cmr= ApplicationFactory.eINSTANCE.createComponentMetricRelationship();
        cmr.setComponent(appc);

        if(metricId!=null){
            cmr.setMetricId(metricId);
        }
        return cmr;
    }

    /**
     * Finds the selected goal for a given ontology
     * @param type The optimisation function type
     * @return The selected goal
     */
    protected GoalOperatorEnum getSelectedGoal(OptimisationFunctionType type) {
        return type.getValue()==OptimisationFunctionType.MAXIMISE_VALUE ? GoalOperatorEnum.MAX : GoalOperatorEnum.MIN;
    }

}