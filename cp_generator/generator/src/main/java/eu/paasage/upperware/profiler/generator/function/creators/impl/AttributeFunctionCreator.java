package eu.paasage.upperware.profiler.generator.function.creators.impl;

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
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.profiler.generator.db.IDatabaseProxy;
import eu.paasage.upperware.profiler.generator.function.creators.FunctionCreator;
import eu.paasage.upperware.profiler.generator.service.camel.ConstantService;
import eu.paasage.upperware.profiler.generator.service.camel.ConstraintService;
import fr.inria.paasage.saloon.price.model.tools.ProviderModelTool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AttributeFunctionCreator implements FunctionCreator {

    public static final String NAME = "Attribute";

    private final IDatabaseProxy database;
    private final ConstantService constantService;
    private final ConstraintService constraintService;

    private String featureName;
    private String attributeName;

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public void createFunction(ConstraintProblem cp, PaaSageGoal goal) {
        setAttributeName(goal.getOptimisationAttribute());

        EList<Variable> variables = cp.getVariables();
        PaasageConfiguration pc = (PaasageConfiguration) goal.eContainer();

        NumericExpression goalExpression = null;
        if (variables.size() > 1) {

            ComposedExpression ce = constraintService.createComposedExpression(OperatorEnum.PLUS);
            cp.getAuxExpressions().add(ce);

            for (Variable v : variables) {
                ce.getExpressions().add(createMultiplication(cp, v, pc.getId()));
            }

            goalExpression = ce;

        } else {
            goalExpression = createMultiplication(cp, variables.get(0), pc.getId());
        }

        Goal goalCP = CPModelTool.createGoal(goal.getGoal(), goal.getId(), goalExpression, 0); //0 is the default priority as here we are not using an optimisation requirement for the creation of the goal
        cp.getGoals().add(goalCP);
    }

    public void setAttributeName(String pathToAttribute) {
        String[] split = pathToAttribute.split("\\.");
        this.featureName = split[0];
        this.attributeName = split[1];
    }

    protected ComposedExpression createMultiplication(ConstraintProblem cp, Variable v, String appId) {


        String providerId = v.getProviderId();
        String vmId = v.getVmId();

        ProviderModel pm = database.loadPM(appId, providerId);

        Constant constant = null;
        Feature feature = ProviderModelTool.getFeatureByName(pm, this.featureName);
        if (feature != null) {
            Attribute attribute = ProviderModelTool.getAttributeByName(feature, this.attributeName);
            if (attribute != null) {
                //TODO - PSZKUP value below is always null!!!
                SingleValue value = attribute.getValue();
                if (value instanceof IntegerValue) {
                    constant = constantService.createIntegerConstant(((IntegerValue) value).getValue());
                    log.info("providerId: " + providerId + ", vmId: " + vmId + ", constant: " + ((IntegerValue) value).getValue());
                } else if (value instanceof DoublePrecisionValue) {
                    constant = constantService.createDoubleConstant(((DoublePrecisionValue) value).getValue());
                    log.info("providerId: " + providerId + ", vmId: " + vmId + ", constant: " + ((DoublePrecisionValue) value).getValue());
                } else if (value instanceof FloatsValue) {
                    constant = constantService.createDoubleConstant(((FloatsValue) value).getValue());
                    log.info("providerId: " + providerId + ", vmId: " + vmId + ", constant: " + ((FloatsValue) value).getValue());

                } else {
                    log.error("Value type: " + value.getClass() + " not allowed");
                }
            } else {
                log.error("Attribute " + this.attributeName + " not found!");
            }
        } else {
            log.error("Feature " + this.featureName + " not found!");
        }

        if (constant == null) {
            constant = constantService.createIntegerConstant(0);
        }

        cp.getConstants().add(constant);


        ComposedExpression aux = constraintService.createComposedExpression(OperatorEnum.TIMES);
        aux.getExpressions().add(v);
        aux.getExpressions().add(constant);

        cp.getAuxExpressions().add(aux);

        return aux;
    }
}
