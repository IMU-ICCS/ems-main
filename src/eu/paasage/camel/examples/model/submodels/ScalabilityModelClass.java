package eu.paasage.camel.examples.model.submodels;

import eu.paasage.camel.*;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.deployment.InternalComponentInstance;
import eu.paasage.camel.deployment.VM;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.scalability.*;
import eu.paasage.camel.type.*;
import org.javatuples.Quartet;

/**
 * Created by orzech on 27/07/14.
 */
public class ScalabilityModelClass {

    public static Quartet<ScalabilityModel, ExecutionContext, VerticalScalabilityPolicy, HorizontalScalabilityPolicy> createMyScalabilityModel(InternalComponent sensApp, InternalComponentInstance sensApp1, InternalComponentInstance mongoDB1, VMInstance vmML1, VMInstance vmLL1, VM ml) {
        ////// START definition of Scalability model

        ////// START definition of Scalability model

        ScalabilityModel scalabilityModel = ScalabilityFactory.eINSTANCE.createScalabilityModel();

        MetricTemplate rawExecTime = ScalabilityFactory.eINSTANCE.createMetricTemplate();

        rawExecTime.setLayer(LayerType.SAA_S);
        rawExecTime.setName("RAW_EXEC_TIME");

        Property execTime = ScalabilityFactory.eINSTANCE.createProperty();
        execTime.setName("Execution Time");
        execTime.setType(PropertyType.MEASURABLE);
        scalabilityModel.getProperties().add(execTime);

        rawExecTime.setProperty(execTime);
        rawExecTime.setType(MetricType.RAW);

        TimeIntervalUnit timeInterval = CamelFactory.eINSTANCE.createTimeIntervalUnit();
        timeInterval.setDimensionType(UnitDimensionType.TIME_INTERVAL);
        timeInterval.setUnit(UnitType.SECONDS);
        scalabilityModel.getUnits().add(timeInterval);

        rawExecTime.setUnit(timeInterval);
        rawExecTime.setValueDirection((short) 0);

        scalabilityModel.getMetricTemplates().add(rawExecTime);

        MetricTemplate avgExecTime = ScalabilityFactory.eINSTANCE.createMetricTemplate();

        MetricFormula avgExecTimeFormula = ScalabilityFactory.eINSTANCE.createMetricFormula();
        avgExecTimeFormula.setFunction(MetricFunctionType.AVERAGE);
        avgExecTimeFormula.setFunctionArity(MetricFunctionArityType.UNARY);
        avgExecTimeFormula.getParameters().add(rawExecTime);
        scalabilityModel.getParameters().add(avgExecTimeFormula);

        avgExecTime.setFormula(avgExecTimeFormula);
        avgExecTime.setLayer(LayerType.SAA_S);
        avgExecTime.setName("AVG_EXEC_TIME");

        avgExecTime.setProperty(execTime);
        avgExecTime.setType(MetricType.COMPOSITE);

        StorageUnit storageUnit = CamelFactory.eINSTANCE.createStorageUnit();
        storageUnit.setDimensionType(UnitDimensionType.STORAGE);
        storageUnit.setUnit(UnitType.GIGABYTES);

        scalabilityModel.getUnits().add(storageUnit);

        avgExecTime.setUnit(storageUnit);
        avgExecTime.setValueDirection((short) 0);

        scalabilityModel.getMetricTemplates().add(avgExecTime);

        MetricTemplate storageMetricTemp = ScalabilityFactory.eINSTANCE.createMetricTemplate();
        storageMetricTemp.setLayer(LayerType.IAA_S);
        storageMetricTemp.setName("Storage");

        Property storageProperty = ScalabilityFactory.eINSTANCE.createProperty();
        storageProperty.setName("Storage");
        storageProperty.setType(PropertyType.MEASURABLE);
        scalabilityModel.getProperties().add(storageProperty);

        storageMetricTemp.setProperty(storageProperty);
        storageMetricTemp.setType(MetricType.RAW);
        storageMetricTemp.setUnit(storageUnit);
        storageMetricTemp.setValueDirection((short) 0);

        scalabilityModel.getMetricTemplates().add(storageMetricTemp);

        Metric rawEtMetric = ScalabilityFactory.eINSTANCE.createMetric();
        rawEtMetric.setId("RawETMetric1");

        MetricObjectInstanceBinding rawEtMetricAIB = ScalabilityFactory.eINSTANCE.createMetricApplicationInstanceBinding();

        ExecutionContext sensAppExecutionContext = ExecutionFactory.eINSTANCE.createExecutionContext();

        rawEtMetricAIB.setExecutionContext(sensAppExecutionContext);

        scalabilityModel.getBindingInstances().add(rawEtMetricAIB);

        rawEtMetric.setObjectBinding(rawEtMetricAIB);

        Sensor sensor1 = ScalabilityFactory.eINSTANCE.createSensor();
        sensor1.setIsPush(false);

        scalabilityModel.getSensors().add(sensor1);

        rawEtMetric.setSensor(sensor1);
        rawEtMetric.setTemplate(rawExecTime);

        Range rawEtMetricRange = TypeFactory.eINSTANCE.createRange();
        rawEtMetricRange.setPrimitiveType(TypeEnum.FLOAT_TYPE);

        Limit rawEtMetricMin = TypeFactory.eINSTANCE.createLimit();
        rawEtMetricMin.setIncluded(false);

        FloatValue rawEtMetricMinValue = TypeFactory.eINSTANCE.createFloatValue();
        rawEtMetricMinValue.setValue(0);

        rawEtMetricMin.setValue(rawEtMetricMinValue);

        rawEtMetricRange.setLowerLimit(rawEtMetricMin);

        Limit rawEtMetricMax = TypeFactory.eINSTANCE.createLimit();
        rawEtMetricMax.setIncluded(false);

        PositiveInf rawEtMetricMaxValue = TypeFactory.eINSTANCE.createPositiveInf();

        rawEtMetricMax.setValue(rawEtMetricMaxValue);

        rawEtMetricRange.setUpperLimit(rawEtMetricMax);

        rawEtMetric.setValueType(rawEtMetricRange);

        scalabilityModel.getMetrics().add(rawEtMetric);

        Metric avgEtMetric1 = ScalabilityFactory.eINSTANCE.createMetric();
        avgEtMetric1.getComponentMetrics().add(rawEtMetric);
        avgEtMetric1.setId("AVGETMetric1");

        avgEtMetric1.setObjectBinding(rawEtMetricAIB);

        Sensor sensor2 = ScalabilityFactory.eINSTANCE.createSensor();
        sensor2.setIsPush(false);

        scalabilityModel.getSensors().add(sensor2);

        avgEtMetric1.setSensor(sensor2);
        avgEtMetric1.setTemplate(avgExecTime);

        Range avgEtMetricRange = TypeFactory.eINSTANCE.createRange();
        avgEtMetricRange.setPrimitiveType(TypeEnum.FLOAT_TYPE);

        Limit avgEtMetricMin = TypeFactory.eINSTANCE.createLimit();
        avgEtMetricMin.setIncluded(false);

        FloatValue avgEtMetricMinValue = TypeFactory.eINSTANCE.createFloatValue();
        avgEtMetricMinValue.setValue(0);

        avgEtMetricMin.setValue(avgEtMetricMinValue);

        avgEtMetricRange.setLowerLimit(avgEtMetricMin);

        Limit avgEtMetricMax = TypeFactory.eINSTANCE.createLimit();
        avgEtMetricMax.setIncluded(false);

        PositiveInf avgEtMetricMaxValue = TypeFactory.eINSTANCE.createPositiveInf();

        avgEtMetricMax.setValue(avgEtMetricMaxValue);

        avgEtMetricRange.setUpperLimit(avgEtMetricMax);

        avgEtMetric1.setValueType(avgEtMetricRange);

        scalabilityModel.getMetrics().add(avgEtMetric1);

        Metric rawStorageMetric = ScalabilityFactory.eINSTANCE.createMetric();
        rawStorageMetric.setId("RawStorageNum");

        MetricVMInstanceBinding vmInstBinding = ScalabilityFactory.eINSTANCE.createMetricVMInstanceBinding();
        vmInstBinding.setExecutionContext(sensAppExecutionContext);
        vmInstBinding.setVmInstance(vmML1);

        scalabilityModel.getBindingInstances().add(vmInstBinding);

        rawStorageMetric.setObjectBinding(vmInstBinding);

        Sensor sensor3 = ScalabilityFactory.eINSTANCE.createSensor();
        sensor3.setIsPush(false);

        scalabilityModel.getSensors().add(sensor3);

        rawStorageMetric.setSensor(sensor3);

        rawStorageMetric.setTemplate(storageMetricTemp);

        Range rawStorageMetricRange = TypeFactory.eINSTANCE.createRange();
        rawStorageMetricRange.setPrimitiveType(TypeEnum.INT_TYPE);

        Limit rawStorageMetricMin = TypeFactory.eINSTANCE.createLimit();
        rawStorageMetricMin.setIncluded(true);

        IntValue rawStorageMetricMinValue = TypeFactory.eINSTANCE.createIntValue();
        rawStorageMetricMinValue.setValue(200);

        rawStorageMetricMin.setValue(rawStorageMetricMinValue);

        rawStorageMetricRange.setLowerLimit(rawStorageMetricMin);

        Limit rawStorageMetricMax = TypeFactory.eINSTANCE.createLimit();
        rawStorageMetricMax.setIncluded(true);

        IntValue rawStorageMetricMaxValue = TypeFactory.eINSTANCE.createIntValue();
        rawStorageMetricMaxValue.setValue(2048);

        rawStorageMetricMax.setValue(rawStorageMetricMaxValue);

        rawStorageMetricRange.setUpperLimit(rawStorageMetricMax);

        rawStorageMetric.setValueType(rawStorageMetricRange);

        scalabilityModel.getMetrics().add(rawStorageMetric);

        ScalabilityRule avgEtScalabilityRule = ScalabilityFactory.eINSTANCE.createScalabilityRule();

        ScalingAction verticalScalingSensApp = ScalabilityFactory.eINSTANCE.createScalingAction();
        verticalScalingSensApp.setComponentInstance(sensApp1);
        verticalScalingSensApp.setCoreUpdate(0);
        verticalScalingSensApp.setCount(1);
        verticalScalingSensApp.setCPUUpdate(0);
        verticalScalingSensApp.setIoUpdate(0);
        verticalScalingSensApp.setMemoryUpdate(0);
        verticalScalingSensApp.setName("VertScaleSensApp");
        verticalScalingSensApp.setNetworkUpdate(0);
        verticalScalingSensApp.setStorageUpdate(0);
        verticalScalingSensApp.setType(ActionType.SCALE_OUT);
        verticalScalingSensApp.setVmInstance(vmLL1);
        scalabilityModel.getActions().add(verticalScalingSensApp);

        avgEtScalabilityRule.getActions().add(verticalScalingSensApp);

        NonFunctionalEvent avgExecutionTimeViolated = ScalabilityFactory.eINSTANCE.createNonFunctionalEvent();
        avgExecutionTimeViolated.setIsViolation(true);

        MetricCondition avgEtMetricCondition = ScalabilityFactory.eINSTANCE.createMetricCondition();
        avgEtMetricCondition.setComparisonOperator(ComparisonOperatorType.GREATER_THAN);
        avgEtMetricCondition.setMetric(avgEtMetric1);
        avgEtMetricCondition.setThreshold(10);

        scalabilityModel.getConditions().add(avgEtMetricCondition);

        avgExecutionTimeViolated.setMetricCondition(avgEtMetricCondition);
        avgExecutionTimeViolated.setName("NFAvgETViol");

        scalabilityModel.getEvents().add(avgExecutionTimeViolated);

        avgEtScalabilityRule.setEvent(avgExecutionTimeViolated);
        avgEtScalabilityRule.setName("AvgETRule");

        scalabilityModel.getRules().add(avgEtScalabilityRule);

        ScalabilityRule storageViolationScalabilityRule = ScalabilityFactory.eINSTANCE.createScalabilityRule();

        ScalingAction horizontalScaleMongoDBVm = ScalabilityFactory.eINSTANCE.createScalingAction();
        horizontalScaleMongoDBVm.setComponentInstance(mongoDB1);
        horizontalScaleMongoDBVm.setCoreUpdate(0);
        horizontalScaleMongoDBVm.setCount(0);
        horizontalScaleMongoDBVm.setCPUUpdate(0);
        horizontalScaleMongoDBVm.setIoUpdate(0);
        horizontalScaleMongoDBVm.setMemoryUpdate(0);
        horizontalScaleMongoDBVm.setName("HorizScaleMongoDBVM");
        horizontalScaleMongoDBVm.setNetworkUpdate(0);
        horizontalScaleMongoDBVm.setStorageUpdate(512);
        horizontalScaleMongoDBVm.setType(ActionType.SCALE_UP);
        horizontalScaleMongoDBVm.setVmInstance(vmML1);

        storageViolationScalabilityRule.getActions().add(horizontalScaleMongoDBVm);
        scalabilityModel.getActions().add(horizontalScaleMongoDBVm);

        NonFunctionalEvent rawStorageViolated = ScalabilityFactory.eINSTANCE.createNonFunctionalEvent();
        rawStorageViolated.setIsViolation(true);

        MetricCondition rawStorageMetricCondition = ScalabilityFactory.eINSTANCE.createMetricCondition();
        rawStorageMetricCondition.setComparisonOperator(ComparisonOperatorType.GREATER_EQUAL_THAN);
        rawStorageMetricCondition.setMetric(rawStorageMetric);
        rawStorageMetricCondition.setThreshold(500);

        scalabilityModel.getConditions().add(rawStorageMetricCondition);

        rawStorageViolated.setMetricCondition(rawStorageMetricCondition);
        rawStorageViolated.setName("NFRawStorageViol");

        scalabilityModel.getEvents().add(rawStorageViolated);

        storageViolationScalabilityRule.setEvent(rawStorageViolated);
        storageViolationScalabilityRule.setName("StorageViolRule");

        scalabilityModel.getRules().add(storageViolationScalabilityRule);

        HorizontalScalabilityPolicy horizPolicySensApp = ScalabilityFactory.eINSTANCE.createHorizontalScalabilityPolicy();
        horizPolicySensApp.setComponent(sensApp);
        horizPolicySensApp.setId("HorizPolicySensApp");
        horizPolicySensApp.setMaxInstances(4);
        horizPolicySensApp.setMinInstances(1);
        horizPolicySensApp.setPriority(0);

        scalabilityModel.getPolicies().add(horizPolicySensApp);

        VerticalScalabilityPolicy verticalPolicyMongoDb = ScalabilityFactory.eINSTANCE.createVerticalScalabilityPolicy();
        verticalPolicyMongoDb.setId("VertPolMongoDB");
        verticalPolicyMongoDb.setMaxCores(0);
        verticalPolicyMongoDb.setMaxCPU(0);
        verticalPolicyMongoDb.setMaxMemory(0);
        verticalPolicyMongoDb.setMaxStorage(2048);
        verticalPolicyMongoDb.setMinCores(0);
        verticalPolicyMongoDb.setMinCPU(0);
        verticalPolicyMongoDb.setMinMemory(0);
        verticalPolicyMongoDb.setMinStorage(512);
        verticalPolicyMongoDb.setPriority(0);
        verticalPolicyMongoDb.setVm(ml);

        scalabilityModel.getPolicies().add(verticalPolicyMongoDb);

        // //// END definition of Scalability model
        return new Quartet<ScalabilityModel, ExecutionContext, VerticalScalabilityPolicy, HorizontalScalabilityPolicy>(scalabilityModel, sensAppExecutionContext, verticalPolicyMongoDb, horizPolicySensApp);
    }
}
