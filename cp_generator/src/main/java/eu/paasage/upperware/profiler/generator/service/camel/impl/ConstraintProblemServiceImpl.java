package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.upperware.metamodel.application.*;
import eu.paasage.upperware.metamodel.cp.*;
import eu.paasage.upperware.profiler.generator.service.camel.*;
import eu.passage.upperware.commons.model.tools.CPModelTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.EList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ConstraintProblemServiceImpl implements ConstraintProblemService {

    private static final String MIN_NUM_VM ="min_num_vm";
    private static final int MAX_NUMBER_OF_VMS= 128;

    public static final String ONE_CONSTANT ="one_constant";
    public static final String CERO_CONSTANT ="cero_constant";

    private CpFactory cpFactory;

    private ConstantService constantService;
    private ConstraintService constraintService;
    private VariableService variableService;
    private PaasageConfigurationServiceImpl paasageConfigurationService;
    private PaasageConfigurationUtilsService paasageConfigurationUtilsService;

    private List<GeneratorService> generatorServices;

    @Override
    public ConstraintProblem derivateConstraintProblem(CamelModel camel, PaasageConfiguration configuration) {

        resetServices();

        log.debug("CPModelDerivator - derivateConstraintProblem - Deriving CP " + configuration.getGoals().size());
        log.info("** 	Derivating Constraint Problem Model");

        //CP creation
        ConstraintProblem cp = cpFactory.createConstraintProblem();

        log.info("**         Creating constants");
        createConstants(cp, configuration);

        log.info("** 		Creating default variables");
        createVariables(cp, configuration);

        log.info("** 		Creating default constraints");
        createConstraints(cp, configuration);

        log.info("** 		Creating User objective functions ");

        cp.getSolution().add(createSolution());

        log.debug("** 		CP Creation ended");
        log.debug(cp.toString());
        printCpModel(cp);
        log.debug("** 		CP Creation ended2");
        return cp;
    }

    private Solution createSolution() {
        Solution sol = cpFactory.createSolution();
        sol.setTimestamp(System.currentTimeMillis());
        return sol;
    }

    private void resetServices() {
        for (GeneratorService generatorService : generatorServices) {
            generatorService.reset();
            log.debug("Reseting service {}", generatorService.getClass().getName());
        }
    }

    /**
     * Creates constants for a constraint problem
     * @param cp The constraint problem
     * @param configuration The paasage configuration
     */
    private void createConstants(ConstraintProblem cp, PaasageConfiguration configuration) {

        //Minimal number of VM profiles selected
        Constant minNumberOfVM= constantService.createIntegerConstant(1, MIN_NUM_VM);
        cp.getConstants().add(minNumberOfVM);

        //Max number of VMs per VM type
        for(VirtualMachineProfile vmp:configuration.getVmProfiles()) {
            Constant vmConstant= constantService.createIntegerConstant(MAX_NUMBER_OF_VMS, constantService.getVMProfileConstantName(vmp.getCloudMLId()));
            cp.getConstants().add(vmConstant);
        }

        //One constant
        Constant oneConstant= constantService.createIntegerConstant(1, ONE_CONSTANT);
        cp.getConstants().add(oneConstant);


        //Cero constant
        Constant ceroConstant= constantService.createIntegerConstant(0, CERO_CONSTANT);
        cp.getConstants().add(ceroConstant);
    }


    /**
     * Creates the variables for a constraint problem
     * @param cp The constraint problem
     * @param configuration The paasage configuration to create the variables
     */
    protected void createVariables(ConstraintProblem cp, PaasageConfiguration configuration) {
        //App components
        createAppComponentVariables(cp, configuration);
    }

    /**
     * Creation of variables related to appComponents and vm profiles
     * @param cp The constraint problem to create the component variables
     * @param configuration The paasage configuration for creating the variables
     */
    protected void createAppComponentVariables(ConstraintProblem cp, PaasageConfiguration configuration) {
        //App components
        log.debug("CPModelDerivator - createAppComponentVariables  - Number of components "+configuration.getComponents().size());
        for(ApplicationComponent ac:configuration.getComponents()) {
            log.debug("CPModelDerivator - createAppComponentVariables  - Component "+ac.getCloudMLId()+ " Required Profiles "+ac.getRequiredProfile().size());
            //for(VirtualMachineProfile vmp:configuration.getVmProfiles())
            for(VirtualMachineProfile vmp:ac.getRequiredProfile()) {
                for(ProviderDimension pd: vmp.getProviderDimension()) {
                    //The zero value is necessary as several providers are considered now
                    Variable appComponentVariable = variableService.createVariable(ac, vmp, pd.getProvider(), 0, ac.getMax());
                    cp.getVariables().add(appComponentVariable);

                    PaaSageVariable paaSageVariable = paasageConfigurationService.createPaaSageVariable(ac, vmp, pd.getProvider(), appComponentVariable);
                    configuration.getVariables().add(paaSageVariable);
                }
            }
        }
    }

    /**
     * Creates constraints for the constraint problem model
     * @param cp The constraint problem being created
     * @param configuration The paasage configuration
     */
    protected void createConstraints(ConstraintProblem cp, PaasageConfiguration configuration) {
        log.debug("CPModelDerivator - createConstraints - 1");

        Constant ceroConstant = constantService.searchConstantByName(cp.getConstants(), CERO_CONSTANT).orElse(null);

        log.debug("CPModelDerivator - createConstraints - 2");
        log.debug("CPModelDerivator - createConstraints - 3");

        Constant oneConstant = constantService.searchConstantByName(cp.getConstants(), ONE_CONSTANT).orElse(null);

        log.debug("CPModelDerivator - createConstraints - 5");
        for(ApplicationComponent ac: configuration.getComponents()) {
            log.debug("CPModelDerivator - createConstraints - 6 component name {}", ac.getCloudMLId());
            List<Variable> vars= paasageConfigurationUtilsService.getVariablesRelatedToAppComponent(ac, cp);

            log.debug("CPModelDerivator - for app {} min components {} ", ac.getCloudMLId(), ac.getMin());
            Constant minVMNumberConstant = generateMinVMNumberConstant(cp.getConstants(), ac.getMin(), oneConstant);
            Expression exp1 = createFirstExpression(cp, vars);

            ComparisonExpression ce = constraintService.createComparisonExpression(exp1, ComparatorEnum.GREATER_OR_EQUAL_TO , minVMNumberConstant);
            cp.getConstraints().add(ce);

            if (ac.getMax() > 0) {
                log.debug("CPModelDerivator - for app {} max components {} ", ac.getCloudMLId(), ac.getMax());
                Constant numOfVMForComponentConstant = generateNumOfVMForComponentConstant(cp.getConstants(), ac.getMax(), null);

                ComparisonExpression ce2 = constraintService.createComparisonExpression(exp1, ComparatorEnum.LESS_OR_EQUAL_TO, numOfVMForComponentConstant);
                cp.getConstraints().add(ce2);
            }

        }

    }

    private Expression createFirstExpression(ConstraintProblem cp, List<Variable> vars) {
        Expression result = null;

        if(vars.size()>1) {
            //Create a sum with the concerned variables
            log.debug("CPModelDerivator - createConstraints - creating sum");
            ComposedExpression sum= constraintService.createComposedExpression(OperatorEnum.PLUS);

            for(Variable var: vars) {
                sum.getExpressions().add(var);
            }

            cp.getAuxExpressions().add(sum);
            result = sum;
        } else {
            log.debug("CPModelDerivator - createConstraints - single value");
            result = vars.get(0);
        }
        return result;
    }

    private Constant generateNumOfVMForComponentConstant(EList<Constant> constants, int numOfVMForComponent, Constant defaultConstantValue) {
        return getOrCreateConstantByValue(constants, numOfVMForComponent, defaultConstantValue);
    }

    private Constant generateMinVMNumberConstant(EList<Constant> constants, int minVMNumber, Constant defaultConstantValue) {
        return getOrCreateConstantByValue(constants, minVMNumber, defaultConstantValue);
    }

    private Constant getOrCreateConstantByValue(EList<Constant> constants, int value, Constant defaultConstantValue){
        Optional<Constant> constantOpt = Optional.ofNullable(defaultConstantValue);
        if(value>1){
            constantOpt = constantService.searchConstantByValue(constants, value);
        }

        Constant constant = constantOpt.orElseGet(() -> {
            log.debug("CPModelDerivator - createConstraints - 14");
            Constant integerConstant = constantService.createIntegerConstant(value);
            constants.add(integerConstant);
            return integerConstant;
        });

        return constant;
    }

    private void printCpModel(ConstraintProblem cp) {

        //print constants
        log.info("********* CP MODEL **************");
        log.info("CONSTANTS");
        for(Constant cons : cp.getConstants()) {
            log.info("   " + CPModelTool.toString(cons));
        }

        log.info("VARIABLES");
        for(Variable var : cp.getVariables()){
            log.info(CPModelTool.toString(var));
        }

        log.info("CONSTRAINTS");
        for(ComparisonExpression ce : cp.getConstraints()){
            log.info(ce.getId() + ": " +CPModelTool.toString(ce));
        }

        log.info("AUX Expressions");
        for(Expression aux : cp.getAuxExpressions()){
            log.info(aux.getId() + ": " +CPModelTool.toString(aux));
        }

        log.info("METRICS");
        for(MetricVariable met : cp.getMetricVariables()){
            log.info(met.getId() + ": " +CPModelTool.toString(met));
        }

        log.info("SOLUTION");
        for(Solution sol : cp.getSolution()){
            log.info("Solution: " + sol.getClass());
        }

    }

}
