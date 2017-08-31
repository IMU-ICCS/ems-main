package eu.paasage.upperware.profiler.cp.generator.model.derivator.lib;

import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.type.DoublePrecisionValue;
import eu.paasage.camel.type.FloatsValue;
import eu.paasage.camel.type.IntegerValue;
import eu.paasage.camel.type.SingleValue;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.derivator.api.IFunctionCreator;
import eu.paasage.upperware.profiler.cp.generator.model.lib.GenerationOrchestrator;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import fr.inria.paasage.saloon.price.model.tools.ProviderModelTool;
import org.apache.log4j.Logger;
import org.eclipse.emf.common.util.EList;

public class AttributeFunctionCreator implements IFunctionCreator {

    private static Logger logger =GenerationOrchestrator.getLogger();

    private IDatabaseProxy database;
    private String featureName;
    private String attributeName;

    @Override
    public void createFunction(ConstraintProblem cp, PaaSageGoal goal) {
        setAttributeName(goal.getOptimisationAttribute());

        EList<Variable> variables = cp.getVariables();
        PaasageConfiguration pc = (PaasageConfiguration) goal.eContainer();

        NumericExpression goalExpression= null;
        if (variables.size()>1) {
            ComposedExpression ce= CPModelTool.createComposedExpression(OperatorEnum.PLUS,CPModelTool.getAuxExpressionId());
            cp.getAuxExpressions().add(ce);
            goalExpression= ce;

            for(Variable v: variables) {
                ce.getExpressions().add(createMultiplication(cp, v, pc.getId()));
            }
        } else {
            goalExpression= createMultiplication(cp, variables.get(0), pc.getId());
        }

        Goal goalCP= CPModelTool.createGoal(goal.getGoal(), goal.getId(), goalExpression, 0); //0 is the default priority as here we are not using an optimisation requirement for the creation of the goal
        cp.getGoals().add(goalCP);
    }

    @Override
    public void setDatabaseProxy(IDatabaseProxy proxy) {
        this.database = proxy;
    }

    public void setAttributeName(String pathToAttribute){
        String[] split = pathToAttribute.split("\\.");
        this.featureName = split[0];
        this.attributeName = split[1];
    }

    protected ComposedExpression createMultiplication(ConstraintProblem cp, Variable v, String appId) {

        String providerId= CPModelTool.getProviderRelatedToVariable(v);
        String vmId= CPModelTool.getVmProfileRelatedToVariable(v);

        ProviderModel pm= database.loadPM(appId, providerId, vmId);

        Constant constant = null;
        Feature feature = ProviderModelTool.getFeatureByName(pm, this.featureName);
        if (feature != null) {
            Attribute attribute = ProviderModelTool.getAttributeByName(feature, this.attributeName);
            if (attribute != null) {
                SingleValue value = attribute.getValue();
                if (value instanceof IntegerValue){
                    constant = CPModelTool.createIntegerConstant(((IntegerValue)value).getValue(), CPModelTool.getConstantName());
                    logger.info("providerId: " + providerId + ", vmId: " + vmId + ", constant: " + ((IntegerValue)value).getValue());
                } else if (value instanceof DoublePrecisionValue) {
                    constant = CPModelTool.createDoubleConstant(((DoublePrecisionValue)value).getValue(), CPModelTool.getConstantName());
                    logger.info("providerId: " + providerId + ", vmId: " + vmId + ", constant: " + ((DoublePrecisionValue)value).getValue());
                } else if (value instanceof FloatsValue) {
                    constant = CPModelTool.createFloatConstant(((FloatsValue)value).getValue(), CPModelTool.getConstantName());
                    logger.info("providerId: " + providerId + ", vmId: " + vmId + ", constant: " + ((FloatsValue)value).getValue());

                } else {
                    logger.error("Value type: " + value.getClass() + " not allowed");
                }
            } else {
                logger.error("Attribute " + this.attributeName + " not found!");
            }
        } else {
            logger.error("Feature " + this.featureName + " not found!");
        }

        if (constant == null){
            constant = CPModelTool.createIntegerConstant(0, CPModelTool.getConstantName());
        }

        cp.getConstants().add(constant);

        ComposedExpression aux= CPModelTool.createComposedExpression(OperatorEnum.TIMES,CPModelTool.getAuxExpressionId());
        cp.getAuxExpressions().add(aux);

        aux.getExpressions().add(v);
        aux.getExpressions().add(constant);

        return aux;
    }
}
