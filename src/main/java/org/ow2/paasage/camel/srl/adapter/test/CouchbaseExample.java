/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.test;

import eu.paasage.camel.*;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.location.Country;
import eu.paasage.camel.location.GeographicalRegion;
import eu.paasage.camel.location.LocationFactory;
import eu.paasage.camel.location.LocationModel;
import eu.paasage.camel.metric.*;
import eu.paasage.camel.organisation.*;
import eu.paasage.camel.requirement.*;
import eu.paasage.camel.scalability.*;
import eu.paasage.camel.type.FloatsValue;
import eu.paasage.camel.type.TypeFactory;
import eu.paasage.camel.type.TypeModel;
import eu.paasage.camel.type.ValueType;
import eu.paasage.camel.unit.*;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.ow2.paasage.camel.srl.adapter.utils.SingleFactory;

import java.sql.Date;
import java.time.Instant;

/**
 * Created by Frank on 17.05.2015.
 */
public class CouchbaseExample {
    private static final int LOW_LIMIT = 50;
    private static final int HIGH_LIMIT = 80;

    public static CamelModel get(EList<EObject> resourceContents){


        /*


            Camel model


         */
        CamelModel CouchModel = CamelFactory.eINSTANCE.createCamelModel();
        CouchModel.setName("CouchModel");
        if(resourceContents != null) resourceContents.add(CouchModel);

        Application cloudbaseApp = CamelFactory.eINSTANCE.createApplication();
        cloudbaseApp.setDescription("Nice couchbase application");
        cloudbaseApp.setName("Cloudbase");
        cloudbaseApp.setVersion("v1.0");
        CouchModel.getApplications().add(cloudbaseApp);
        if(resourceContents != null) resourceContents.add(cloudbaseApp);



        /*


            Organisation model


         */
        OrganisationModel CouchOrganisation = OrganisationFactory.eINSTANCE.createOrganisationModel();
        CouchOrganisation.setName("CouchOrganisation");
        CouchModel.getOrganisationModels().add(CouchOrganisation);
        if(resourceContents != null) resourceContents.add(CouchOrganisation);

        Organisation UULM = OrganisationFactory.eINSTANCE.createOrganisation();
        UULM.setName("UULM");
        UULM.setEmail("http://www.uni-ulm.de/");
        UULM.setWww("joerg.domaschka@uni-ulm.de");
        UULM.setPostalAddress("...");
        CouchModel.getOrganisationModels().get(0).setOrganisation(UULM);
        if(resourceContents != null) resourceContents.add(UULM);

        User joerg = OrganisationFactory.eINSTANCE.createUser();
        joerg.setName("joerg");
        joerg.setFirstName("Joerg");
        joerg.setLastName("Domaschka");
        joerg.setEmail("joerg.domaschka@uni-ulm.de");
        CouchModel.getOrganisationModels().get(0).getUsers().add(joerg);
        cloudbaseApp.setOwner(joerg);
        if(resourceContents != null) resourceContents.add(joerg);

        PaaSageCredentials credentials = OrganisationFactory.eINSTANCE.createPaaSageCredentials();
        // TODO where does this stuff go now?
        //credentials.setName("credentials");
        //credentials.setUsername("domaschka");
        credentials.setPassword("...");
        joerg.setPaasageCredentials(credentials);
        if(resourceContents != null) resourceContents.add(credentials);



        /*


            Unit model


         */
        UnitModel CouchUnit = UnitFactory.eINSTANCE.createUnitModel();
        CouchUnit.setName("CouchUnit");
        CouchModel.getUnitModels().add(CouchUnit);
        if(resourceContents != null) resourceContents.add(CouchUnit);

        TimeIntervalUnit seconds = SingleFactory.getOrCreateTimeIntervalUnit(resourceContents, UnitType.SECONDS, UnitDimensionType.TIME_INTERVAL, "seconds");
        CouchModel.getUnitModels().get(0).getUnits().add(seconds);
        //if(resourceContents != null) resourceContents.add(seconds);

        TimeIntervalUnit minutes = SingleFactory.getOrCreateTimeIntervalUnit(resourceContents, UnitType.MINUTES, UnitDimensionType.TIME_INTERVAL, "minutes");
        CouchModel.getUnitModels().get(0).getUnits().add(minutes);
        //if(resourceContents != null) resourceContents.add(minutes);

        Unit CPU_load = UnitFactory.eINSTANCE.createCoreUnit();
        // TODO not used anymore? CPU_load.setDimensionType(UnitDimensionType.DIMENSIONLESS);
        CPU_load.setName("utilization");
        CPU_load.setUnit(UnitType.PERCENTAGE);
        CouchModel.getUnitModels().get(0).getUnits().add(CPU_load);
        if(resourceContents != null) resourceContents.add(CPU_load);



        /*


            Type model


         */
        TypeModel CouchTypes = TypeFactory.eINSTANCE.createTypeModel();
        CouchTypes.setName("CouchTypes");
        CouchModel.getTypeModels().add(CouchTypes);
        if(resourceContents != null) resourceContents.add(CouchTypes);

        ValueType valueType = TypeFactory.eINSTANCE.createStringValueType();
        CouchTypes.getDataTypes().add(valueType);
        if(resourceContents != null) resourceContents.add(valueType);

        FloatsValue floatValue = TypeFactory.eINSTANCE.createFloatsValue();
        CouchTypes.getValues().add(floatValue);
        if(resourceContents != null) resourceContents.add(floatValue);



        /*


            Location model


         */
        LocationModel couchLocation = LocationFactory.eINSTANCE.createLocationModel();
        couchLocation.setName("CouchLocation");
        CouchModel.getLocationModels().add(couchLocation);

        GeographicalRegion europe = LocationFactory.eINSTANCE.createGeographicalRegion();
        europe.setId("eu");
        europe.setName("Europe");
        europe.getAlternativeNames().add("eu");
        couchLocation.getRegions().add(europe);

        Country germany = LocationFactory.eINSTANCE.createCountry();
        germany.setId("de");
        germany.setName("Germany");
        germany.getParentRegions().add(europe);
        germany.getAlternativeNames().add("de");
        couchLocation.getCountries().add(germany);



        /*


            Requirement model


         */
        RequirementModel couchRequirement = RequirementFactory.eINSTANCE.createRequirementModel();
        couchRequirement.setName("CouchRequirement");
        CouchModel.getRequirementModels().add(couchRequirement);

        LocationRequirement GermanyReq = RequirementFactory.eINSTANCE.createLocationRequirement();
        GermanyReq.setName("GermanyReq");
        GermanyReq.getLocations().add(germany);
        couchRequirement.getRequirements().add(GermanyReq);
        if(resourceContents != null) resourceContents.add(GermanyReq);

        /* TODO rename in textual model */
        HorizontalScaleRequirement CouchScaleRequirement = RequirementFactory.eINSTANCE.createHorizontalScaleRequirement();
        CouchScaleRequirement.setName("CouchScaleRequirement");
        // CouchScaleRequirement.setComponent(CouchComponent); IS SET LATER!
        CouchScaleRequirement.setMaxInstances(6); /** TODO CHANGE IN TEXTUAL MODEL */
        CouchScaleRequirement.setMinInstances(3);
        couchRequirement.getRequirements().add(CouchScaleRequirement);
        if(resourceContents != null) resourceContents.add(GermanyReq);

        OSRequirement UbuntuReq = RequirementFactory.eINSTANCE.createOSRequirement();
        UbuntuReq.setName("UbuntuReq");
        UbuntuReq.setIs64os(true);
        UbuntuReq.setOs("Ubuntu");
        couchRequirement.getRequirements().add(UbuntuReq);
        if(resourceContents != null) resourceContents.add(UbuntuReq);

        QuantitativeHardwareRequirement CPUIntensive = RequirementFactory.eINSTANCE.createQuantitativeHardwareRequirement();
        CPUIntensive.setName("CPUIntensive");
        CPUIntensive.setMinCores(4);
        CPUIntensive.setMinRAM(4096);
        CPUIntensive.setMaxRAM(8192);
        CPUIntensive.setMinCPU(1.0);
        couchRequirement.getRequirements().add(CPUIntensive);
        if(resourceContents != null) resourceContents.add(CPUIntensive);

        RequirementGroup couchRequirementGroup = RequirementFactory.eINSTANCE.createRequirementGroup();
        couchRequirementGroup.setName("couchRequirementGroup");
        couchRequirementGroup.setRequirementOperator(RequirementOperatorType.AND);
        couchRequirementGroup.getApplication().add(cloudbaseApp);
        couchRequirementGroup.getRequirements().add(CPUIntensive);
        couchRequirementGroup.getRequirements().add(UbuntuReq);
        couchRequirementGroup.getRequirements().add(CouchScaleRequirement);
        couchRequirementGroup.getRequirements().add(GermanyReq);
        couchRequirement.getRequirements().add(couchRequirementGroup);
        if(resourceContents != null) resourceContents.add(couchRequirementGroup);



        /*


            Deployment model


         */
        DeploymentModel couchDeployment = DeploymentFactory.eINSTANCE.createDeploymentModel();
        couchDeployment.setName("CouchDeployment");
        cloudbaseApp.getDeploymentModels().add(couchDeployment);
        CouchModel.getDeploymentModels().add(couchDeployment);

        /* TODO ADD TO TEXTUAL MODEL */
        RequiredHost CPUIntensiveUbuntuGermanyReq = DeploymentFactory.eINSTANCE.createRequiredHost();
        CPUIntensiveUbuntuGermanyReq.setName("CPUIntensiveUbuntuGermanyReq");
        // TODO: BELONGS TO WHICH SUB-PACKAGE? couchDeployment.getPACKAGE().add(CPUIntensiveUbuntuGermanyReq);
        if(resourceContents != null) resourceContents.add(CPUIntensiveUbuntuGermanyReq);

        /** TODO do we use internal component or vm component? */
        InternalComponent CouchComponent = DeploymentFactory.eINSTANCE.createInternalComponent();
        CouchComponent.setName("CouchbaseComponent");
        CouchComponent.setRequiredHost(CPUIntensiveUbuntuGermanyReq);
        CouchScaleRequirement.setComponent(CouchComponent);
        couchDeployment.getInternalComponents().add(CouchComponent);
        if(resourceContents != null) resourceContents.add(CouchComponent);

        eu.paasage.camel.deployment.Configuration CouchbaseComponentConf = DeploymentFactory.eINSTANCE.createConfiguration();
        CouchbaseComponentConf.setName("CouchbaseComponentConf");
        CouchbaseComponentConf.setDownloadCommand("...");
        CouchbaseComponentConf.setInstallCommand("...");
        CouchbaseComponentConf.setStartCommand("...");
        CouchComponent.getConfigurations().add(CouchbaseComponentConf);
        if(resourceContents != null) resourceContents.add(CouchbaseComponentConf);

        VMRequirementSet CPUIntensiveUbuntuGermanyRS = DeploymentFactory.eINSTANCE.createVMRequirementSet();
        CPUIntensiveUbuntuGermanyRS.setName("CPUIntensiveUbuntuGermanyRS");
        CPUIntensiveUbuntuGermanyRS.setOsOrImageRequirement(UbuntuReq);
        CPUIntensiveUbuntuGermanyRS.setQuantitativeHardwareRequirement(CPUIntensive);
        couchDeployment.getVmRequirementSets().add(CPUIntensiveUbuntuGermanyRS);
        if(resourceContents != null) resourceContents.add(CPUIntensiveUbuntuGermanyRS);

        VM CPUIntensiveUbuntuGermany = DeploymentFactory.eINSTANCE.createVM();
        CPUIntensiveUbuntuGermany.setName("CPUIntensiveUbuntuGermany");
        CPUIntensiveUbuntuGermany.setVmRequirementSet(CPUIntensiveUbuntuGermanyRS);
        couchDeployment.getVms().add(CPUIntensiveUbuntuGermany);
        if(resourceContents != null) resourceContents.add(CPUIntensiveUbuntuGermany);



        /*


            Execution model


         */
        ExecutionModel CouchExecutions = ExecutionFactory.eINSTANCE.createExecutionModel();
        CouchExecutions.setName("CouchExecutions");
        CouchModel.getExecutionModels().add(CouchExecutions);
        if(resourceContents != null) resourceContents.add(CouchExecutions);

        ExecutionContext ec = ExecutionFactory.eINSTANCE.createExecutionContext();
        ec.setName("ExecutionContext");
        ec.setStartTime(Date.from(Instant.now()));
        ec.setApplication(cloudbaseApp);
        ec.setDeploymentModel(couchDeployment);
        ec.setRequirementGroup(couchRequirementGroup);
        CouchExecutions.getExecutionContexts().add(ec);
        if(resourceContents != null) resourceContents.add(ec);



        /*


            Metric model


         */
        MetricModel CouchMetric = MetricFactory.eINSTANCE.createMetricModel();
        CouchMetric.setName("CouchMetric");
        CouchModel.getMetricModels().add(CouchMetric);
        if(resourceContents != null) resourceContents.add(CouchMetric);

        Window win5minutes = SingleFactory.getOrCreateWindow(CouchModel, resourceContents, 5, 5, WindowType.SLIDING, minutes, "5_MIN", WindowSizeType.TIME_ONLY);
        CouchMetric.getWindows().add(win5minutes);

        Window win1minute = SingleFactory.getOrCreateWindow(CouchModel, resourceContents, 1, 1, WindowType.SLIDING, minutes, "1_MIN", WindowSizeType.TIME_ONLY);
        CouchMetric.getWindows().add(win1minute);

        Schedule everyMinute = SingleFactory.getOrCreateFixedSchedule(CouchModel, resourceContents, 5, seconds, "EVERY_MINUTE", ScheduleType.FIXED_RATE); //TODO for debugging: 1, minutes
        CouchMetric.getSchedules().add(everyMinute);

        Schedule everySecond = SingleFactory.getOrCreateFixedSchedule(CouchModel, resourceContents, 1, seconds, "EVERY_SECOND", ScheduleType.FIXED_RATE);
        CouchMetric.getSchedules().add(everySecond);

        Sensor CPU = MetricFactory.eINSTANCE.createSensor();
        CPU.setName("CPU");
        CPU.setConfiguration("cpu_usage;de.uniulm.omi.cloudiator.visor.sensors.CpuUsageSensor");
        CPU.setIsPush(true);
        CouchMetric.getSensors().add(CPU);
        if(resourceContents != null) resourceContents.add(CPU);

        Property cpuLoad = MetricFactory.eINSTANCE.createProperty();
        cpuLoad.setName("CPU_property");
        cpuLoad.setDescription("cpu property description");
        cpuLoad.setType(PropertyType.MEASURABLE);
        cpuLoad.getSensors().add(CPU);
        CouchMetric.getProperties().add(cpuLoad);
        if(resourceContents != null) resourceContents.add(cpuLoad);

        RawMetric rawCPU = MetricFactory.eINSTANCE.createRawMetric();
        rawCPU.setName("CPU_raw_metric");
        rawCPU.setDescription("Raw CPU");
        rawCPU.setLayer(LayerType.IAA_S);
        rawCPU.setValueDirection((short)0);
        rawCPU.setUnit(CPU_load);
        rawCPU.setProperty(cpuLoad);
        //rawCPU.setValue(floatValue);
        CouchMetric.getMetrics().add(rawCPU);
        if(resourceContents != null) resourceContents.add(rawCPU);

        MetricFormula averageFormula = MetricFactory.eINSTANCE.createMetricFormula();
        averageFormula.setName("Formula_Average");
        averageFormula.setFunctionArity(MetricFunctionArityType.UNARY);
        averageFormula.setFunction(MetricFunctionType.MEAN);
        averageFormula.setFunctionPattern(FunctionPatternType.MAP);
        averageFormula.getParameters().add(rawCPU);
        //averageFormula.setValue(floatValue);
        if(resourceContents != null) resourceContents.add(averageFormula);

        CompositeMetric averageCPU = MetricFactory.eINSTANCE.createCompositeMetric();
        averageCPU.setName("CPU_Average");
        averageCPU.setDescription("Average CPU");
        averageCPU.setLayer(LayerType.PAA_S);
        averageCPU.setValueDirection((short) 0);
        averageCPU.setUnit(CPU_load);
        averageCPU.setFormula(averageFormula);
        averageCPU.setProperty(cpuLoad);
        //averageCPU.setValue(floatValue);
        CouchMetric.getMetrics().add(averageCPU);
        if(resourceContents != null) resourceContents.add(averageCPU);

        RawMetricContext CPU_raw_metric_context = MetricFactory.eINSTANCE.createRawMetricContext();
        CPU_raw_metric_context.setName("CPU_raw_metric_context");
        CPU_raw_metric_context.setSensor(CPU);
        CPU_raw_metric_context.setMetric(rawCPU);
        // TODO: CPU_raw_metric_context.setWindow(win5minutes);
        CPU_raw_metric_context.setSchedule(everySecond);
        CPU_raw_metric_context.setComponent(CouchComponent);
        CPU_raw_metric_context.setApplication(cloudbaseApp);
        //MCX1.getComposingMetricContexts().add(MCX1_composed);
        CouchMetric.getContexts().add(CPU_raw_metric_context);
        if(resourceContents != null) resourceContents.add(CPU_raw_metric_context);

        CompositeMetricContext CPU_avg_metric_context_ALL = MetricFactory.eINSTANCE.createCompositeMetricContext();
        CPU_avg_metric_context_ALL.setName("CPU_avg_metric_context_ALL");
        CPU_avg_metric_context_ALL.setApplication(cloudbaseApp);
        CPU_avg_metric_context_ALL.setMetric(averageCPU);
        CPU_avg_metric_context_ALL.setComponent(CouchComponent);
        CPU_avg_metric_context_ALL.getComposingMetricContexts().add(CPU_raw_metric_context);
        CPU_avg_metric_context_ALL.setWindow(win5minutes); /** TODO add in textual model */
        CPU_avg_metric_context_ALL.setSchedule(everyMinute); /** TODO add in textual model */
        CPU_avg_metric_context_ALL.setQuantifier(QuantifierType.ALL);
        //MCX1.setMinQuantity(-1.0);
        //MCX1.setMaxQuantity(-1.0);
        CouchMetric.getContexts().add(CPU_avg_metric_context_ALL);
        if(resourceContents != null) resourceContents.add(CPU_avg_metric_context_ALL);

        MetricCondition CPU_avg_metric_condition_ALL = MetricFactory.eINSTANCE.createMetricCondition();
        CPU_avg_metric_condition_ALL.setName("CPU_avg_metric_condition_ALL");
        CPU_avg_metric_condition_ALL.setThreshold(LOW_LIMIT);
        CPU_avg_metric_condition_ALL.setComparisonOperator(ComparisonOperatorType.GREATER_EQUAL_THAN);
        CPU_avg_metric_condition_ALL.setMetricContext(CPU_avg_metric_context_ALL);
        CouchMetric.getConditions().add(CPU_avg_metric_condition_ALL);
        if(resourceContents != null) resourceContents.add(CPU_avg_metric_condition_ALL);

        CompositeMetricContext CPU_avg_metric_context_ANY = MetricFactory.eINSTANCE.createCompositeMetricContext();
        CPU_avg_metric_context_ANY.setName("CPU_avg_metric_context_ANY");
        CPU_avg_metric_context_ANY.setApplication(cloudbaseApp);
        CPU_avg_metric_context_ANY.setMetric(averageCPU);
        CPU_avg_metric_context_ANY.getComposingMetricContexts().add(CPU_raw_metric_context);
        CPU_avg_metric_context_ANY.setWindow(win1minute); /** TODO add in textual model */
        CPU_avg_metric_context_ANY.setSchedule(everyMinute); /** TODO add in textual model */
        CPU_avg_metric_context_ANY.setQuantifier(QuantifierType.ANY);
        //MCX2.setMinQuantity(1.0);
        //MCX2.setMaxQuantity(-1.0);
        CouchMetric.getContexts().add(CPU_avg_metric_context_ANY);
        if(resourceContents != null) resourceContents.add(CPU_avg_metric_context_ANY);

        /** TODO ADD THIS TO TEXTUAL MODEL : REALLY? ACTUALLY NOT, SINCE ONE INSTANTIATION IS ENOUGH
         RawMetricContext CPU_avg_metric_condition_ANY = MetricFactory.eINSTANCE.createRawMetricContext();
         MCX2_composed.setMetric(rawCPU);
         MCX2_composed.setName("RAW_CPU_1_MIN");
         MCX2_composed.setWindow(win1minute);
         MCX2.getComposingMetricContexts().add(MCX2_composed);
         if(resourceContents != null) resourceContents.add(MCX2_composed);
         */

        MetricCondition CPU_avg_metric_condition_ANY = MetricFactory.eINSTANCE.createMetricCondition();
        CPU_avg_metric_condition_ANY.setName("CPU_avg_metric_condition_ANY");
        CPU_avg_metric_condition_ANY.setThreshold(HIGH_LIMIT);
        CPU_avg_metric_condition_ANY.setComparisonOperator(ComparisonOperatorType.GREATER_EQUAL_THAN); /** TODO: CHANGE IN TEXTUAL MODEL */
        //MC2.setApplication(/*TODO*/);
        //MC2.setComponent(/*TODO*/);
        CPU_avg_metric_condition_ANY.setMetricContext(CPU_avg_metric_context_ANY);
        CouchMetric.getConditions().add(CPU_avg_metric_condition_ANY);
        if(resourceContents != null) resourceContents.add(CPU_avg_metric_condition_ANY);



        /*


            Scalability model


         */
        ScalabilityModel CouchScalability = ScalabilityFactory.eINSTANCE.createScalabilityModel();
        CouchScalability.setName("CouchScalability");
        CouchModel.getScalabilityModels().add(CouchScalability);
        if(resourceContents != null) resourceContents.add(CouchScalability);

        NonFunctionalEvent CPU_avg_metric_nfe_ALL = ScalabilityFactory.eINSTANCE.createNonFunctionalEvent();
        CPU_avg_metric_nfe_ALL.setName("CPU_avg_metric_nfe_ALL");
        CPU_avg_metric_nfe_ALL.setIsViolation(true);
        CPU_avg_metric_nfe_ALL.setMetricCondition(CPU_avg_metric_condition_ALL);
        CouchScalability.getEvents().add(CPU_avg_metric_nfe_ALL);
        if(resourceContents != null) resourceContents.add(CPU_avg_metric_nfe_ALL);

        NonFunctionalEvent CPU_avg_metric_nfe_ANY = ScalabilityFactory.eINSTANCE.createNonFunctionalEvent();
        CPU_avg_metric_nfe_ANY.setName("CPU_avg_metric_nfe_ANY");
        CPU_avg_metric_nfe_ANY.setIsViolation(true);
        CPU_avg_metric_nfe_ANY.setMetricCondition(CPU_avg_metric_condition_ANY);
        CouchScalability.getEvents().add(CPU_avg_metric_nfe_ANY);
        if(resourceContents != null) resourceContents.add(CPU_avg_metric_nfe_ANY);

        BinaryEventPattern CPU_avg_metric_bep_AND = ScalabilityFactory.eINSTANCE.createBinaryEventPattern();
        CPU_avg_metric_bep_AND.setName("CPU_avg_metric_bep_AND");
        CPU_avg_metric_bep_AND.setLeftEvent(CPU_avg_metric_nfe_ALL);
        CPU_avg_metric_bep_AND.setRightEvent(CPU_avg_metric_nfe_ANY);
        CPU_avg_metric_bep_AND.setOperator(BinaryPatternOperatorType.AND);
        CouchScalability.getPatterns().add(CPU_avg_metric_bep_AND);
        if(resourceContents != null) resourceContents.add(CPU_avg_metric_bep_AND);

        HorizontalScalingAction HorizontalScalingCouchbaseComponent = ScalabilityFactory.eINSTANCE.createHorizontalScalingAction();
        HorizontalScalingCouchbaseComponent.setName("HorizontalScalingCouchbaseComponent");
        HorizontalScalingCouchbaseComponent.setInternalComponent(CouchComponent);
        HorizontalScalingCouchbaseComponent.setVm(CPUIntensiveUbuntuGermany);
        HorizontalScalingCouchbaseComponent.setType(ActionType.SCALE_OUT);
        CouchScalability.getActions().add(HorizontalScalingCouchbaseComponent);
        if(resourceContents != null) resourceContents.add(HorizontalScalingCouchbaseComponent);

        ScalabilityRule RawCPUScalabilityRule = ScalabilityFactory.eINSTANCE.createScalabilityRule();
        RawCPUScalabilityRule.setName("RawCPUScalabilityRule");
        RawCPUScalabilityRule.setEvent(CPU_avg_metric_bep_AND);
        RawCPUScalabilityRule.getActions().add(HorizontalScalingCouchbaseComponent);
        CouchScalability.getRules().add(RawCPUScalabilityRule);
        if(resourceContents != null) resourceContents.add(RawCPUScalabilityRule);

        //TODO check duplicate reference in Requirements and ScaleRequirements:
        CouchScalability.getScaleRequirements().add(CouchScaleRequirement);




        /*

            Instances
            TODO Just hard-coded for show-case

         */
        VMInstance cloudbaseVM_1 = DeploymentFactory.eINSTANCE.createVMInstance();
        cloudbaseVM_1.setName("cloudbaseVM_1");
        cloudbaseVM_1.setType(CouchComponent);
        cloudbaseVM_1.setIp("134.60.64.49");
        couchDeployment.getVmInstances().add(cloudbaseVM_1);
        if(resourceContents != null) resourceContents.add(cloudbaseVM_1);

        VMInstance cloudbaseVM_2 = DeploymentFactory.eINSTANCE.createVMInstance();
        cloudbaseVM_2.setName("cloudbaseVM_2");
        cloudbaseVM_2.setType(CouchComponent);
        cloudbaseVM_2.setIp("134.60.64.48");
        couchDeployment.getVmInstances().add(cloudbaseVM_2);
        if(resourceContents != null) resourceContents.add(cloudbaseVM_2);

        VMInstance cloudbaseVM_3 = DeploymentFactory.eINSTANCE.createVMInstance();
        cloudbaseVM_3.setName("cloudbaseVM_3");
        cloudbaseVM_3.setType(CouchComponent);
        cloudbaseVM_3.setIp("134.60.64.40");
        couchDeployment.getVmInstances().add(cloudbaseVM_3);
        if(resourceContents != null) resourceContents.add(cloudbaseVM_3);





        return CouchModel;
    }
}
