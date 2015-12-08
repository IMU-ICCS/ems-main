/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.execution;

import de.uniulm.omi.cloudiator.colosseum.client.Client;
import de.uniulm.omi.cloudiator.colosseum.client.ClientBuilder;
import de.uniulm.omi.cloudiator.colosseum.client.SingletonFactory;
import de.uniulm.omi.cloudiator.colosseum.client.entities.*;
import de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.*;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FilterType;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FlowOperator;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.FormulaOperator;
import de.uniulm.omi.cloudiator.colosseum.client.entities.enums.SubscriptionType;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.metric.*;
import eu.paasage.camel.metric.Schedule;
import eu.paasage.camel.metric.Window;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.scalability.EventPattern;
import eu.paasage.camel.scalability.NonFunctionalEvent;
import eu.paasage.camel.scalability.ScalabilityRule;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.ow2.paasage.camel.srl.adapter.adapter.*;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.communication.RestFrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.ow2.paasage.camel.srl.adapter.config.ModelSourceType;
import org.ow2.paasage.camel.srl.adapter.utils.CamelFinder;
import org.ow2.paasage.camel.srl.adapter.utils.Finder;
import org.ow2.paasage.camel.srl.adapter.utils.Printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Frank on 16.11.2015.
 */
public class Execution {
    private static final Map<Long, String> mapScalingActionEventName = new HashMap<>(); /* Cache for Scaling Actions */
    private static org.apache.log4j.Logger logger;

    static {
        logger = org.apache.log4j.Logger.getLogger(Execution.class);
    }

    private final CommandLinePropertiesAccessor conf;

    public Execution(CommandLinePropertiesAccessor conf) {
        this.conf = conf;
    }

    public void run(ImportModelSource ims){
        run(ims, null, null, null, null);
    }

    public void run(ImportModelSource ims, String dynamicResourceName, String dynamicModelName, String dynamicExecutionContextName) {
        run(ims, dynamicResourceName, dynamicModelName, dynamicExecutionContextName, null);
    }

    public void run(ImportModelSource ims, String dynamicResourceName, String dynamicModelName, String dynamicExecutionContextName, Client alternativeColosseumClient){
        boolean createNew = conf.getSaveExample();
        boolean createMetricInstances = conf.getCreateMetricInstances();
        boolean createMonitorSubscriptions = conf.getCreateMonitorSubscriptions();
        String modelName = conf.getModelName();
        String resourceName = conf.getResourceName();
        String executionContextName = conf.getExecutionContextName();
        String colUrl = conf.getColosseumUrl();
        String colUser = conf.getColosseumUser();
        String colPassword = conf.getColosseumPassword();
        String colTenant = conf.getColosseumTenant();


        if(dynamicModelName != null){
            createNew = false;
            createMetricInstances = true;
            modelName = dynamicModelName;
        }

        if(dynamicResourceName != null){
            resourceName = dynamicResourceName;
        }

        if(dynamicExecutionContextName != null){
            executionContextName = dynamicExecutionContextName;
        }

        Client colosseumClient;

        if(alternativeColosseumClient == null) {
            // Orchestrate SRL Engine
            logger.info("Connect to colosseum");
            ClientBuilder clientBuilder = ClientBuilder.getNew()
                    // the base url
                    .url(colUrl)
                    // the login credentials
                    .credentials(colUser, colTenant, colPassword);

            colosseumClient = clientBuilder.build();
        } else {
            colosseumClient = alternativeColosseumClient;
        }


        FrontendCommunicator fc = new RestFrontendCommunicator(colosseumClient);



        // Save the Couchbase example:
        if (createNew) {
            logger.info("Create new Couchbase-Example into the resources");
            ims.createExampleModel(resourceName);
        }


        EList<EObject> objs = ims.getResources(resourceName);

        try {

            for (EObject obj : objs) {
                logger.info("The objs stored are: " + obj.toString());

                if(!(obj instanceof CamelModel && ((CamelModel)obj).getName().equals(modelName))){
                    logger.info("Model is not of type CamelModel or name is not equal to '" + modelName + "': " + obj.toString());
                    continue;
                }

                // Get Couchbase Camel Model
                CamelModel model = (CamelModel) obj;
                CamelFinder finder = new CamelFinder(model);
                ExecutionContext ec = finder.getExecutionContext(executionContextName, objs);

                // Create MetricInstances
                if (createMetricInstances) {
                    ims.createMetricInstances(fc, finder, ec, model, objs);
                }

                /*************************************************************************
                 *
                 *  CLEAN MONITORING
                 *
                 *
                 *************************************************************************/
                fc.cleanMonitoring();

                /*************************************************************************
                 *
                 *  TRANSFORM SENSORS
                 *
                 *
                 *************************************************************************/
                for (Sensor sensor : finder.getSensors()) {
                    Adapter adapter = new SensorAdapter(fc, sensor);
                    adapter.adapt();
                }

                /*************************************************************************
                 *
                 *  TRANSFORM SCHEDULES
                 *
                 *
                 *************************************************************************/
                for (eu.paasage.camel.metric.Schedule schedule : finder.getSchedules()) {
                    Adapter adapter = new ScheduleAdapter(fc, schedule);
                    adapter.adapt();
                }

                /*************************************************************************
                 *
                 *  TRANSFORM WINDOWS
                 *
                 *
                 *************************************************************************/
                for (Window w : finder.getWindows()){
                    Adapter adapter = new WindowsAdapter(fc, w);
                    adapter.adapt();
                }

                /*************************************************************************
                 *
                 *  TRANSFORM SCALING ACTIONS
                 *
                 *
                 *************************************************************************/
                for (eu.paasage.camel.scalability.HorizontalScalingAction sa : finder.getScalingActions()){
                    ScalingActionAdapterFactory factory = new ScalingActionAdapterFactoryImpl();

                    List<ScalabilityRule> associatedRules = finder.getAssociatedRules(sa);
                    List<HorizontalScaleRequirement> associatedHorizontalScaleRequirements = finder.getAssociatedHorizontalScaleRequirements(sa.getInternalComponent());

                    Adapter adapter = factory.create(fc, sa, associatedRules, associatedHorizontalScaleRequirements);
                    Object o = adapter.adapt();

                    ScalingAction componentHorizontalScalingAction = (ScalingAction) o;


                    ///////////////////////////////////////////////////////////////////////////
                    //
                    // Add Subscription to all rules by the generated composed monitor
                    // (events are mapped to composed monitors)
                    //
                    ///////////////////////////////////////////////////////////////////////////
                    for (ScalabilityRule rule : associatedRules) {
                        mapScalingActionEventName.put(componentHorizontalScalingAction.getId(), rule.getEvent().cdoID().toString());
                    }
                }

                /*************************************************************************
                 *
                 *  TRANSFORM RAW METRIC CONTEXTS
                 *
                 *
                 *************************************************************************/
                for (RawMetricContext rmc : finder.getRawMetricContexts()){
                    List<MetricInstance> mis = finder.getMetricInstances(rmc, ec);
                    Adapter adapter = new RawMetricContextAdapter(fc, rmc, mis);
                    Object o = adapter.adapt();
                    Monitor rawMonitor = (Monitor) o;

                    ///////////////////////////////////////////////////////////////////////////
                    //
                    // Add Subscription to all composite monitor to send to CDO
                    //
                    ///////////////////////////////////////////////////////////////////////////
                    if(createMonitorSubscriptions) {
                        List<Long> rawMonitors = new ArrayList<>();
                        rawMonitors.add(rawMonitor.getId());

                        SingletonFactory factory = new SingletonFactory(colosseumClient);
                        de.uniulm.omi.cloudiator.colosseum.client.entities.abstracts.Window in5minutes = factory.singleton(new TimeWindow(5l, TimeUnit.MINUTES));
                        FormulaQuantifier quantifier = factory.singleton(new FormulaQuantifier(true, 1.0));

                        de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule secondly = factory.singleton(new de.uniulm.omi.cloudiator.colosseum.client.entities.Schedule(1l, TimeUnit.SECONDS));
                        Long schedule;
                        if(rawMonitor instanceof RawMonitor){
                            schedule = ((RawMonitor) rawMonitor).getSchedule();
                        } else {
                            schedule = secondly.getId();
                        }
                        ComposedMonitor identityMonitor = factory.singleton(
                                new ComposedMonitor(
                                        FlowOperator.MAP,
                                        FormulaOperator.IDENTITY,
                                        quantifier.getId(),
                                        in5minutes.getId(),
                                        rawMonitors,
                                        null,
                                        schedule));

                        for(MonitorInstance mi : fc.getMonitorInstances(identityMonitor.getId())){
                            for(MetricInstance metricInstance : mis){
                                if(mi.getExternalReferences().isEmpty()){
                                    fc.addExternalId(mi, metricInstance.cdoID().toString());
                                    break; // only one CDOID per monitor instance
                                }
                            }
                        }


                        fc.addMonitorSubscription(identityMonitor.getId(), conf.getVisorEndpoint(),
                                SubscriptionType.CDO, FilterType.ANY, 0);
                    }
                }

                /*************************************************************************
                 *
                 *  TRANSFORM COMPOSITE METRIC CONTEXTS
                 *
                 *
                 *************************************************************************/
                for (CompositeMetricContext cmc : finder.getCompositeMetricContexts()){
                    List<MetricInstance> mis = finder.getMetricInstances(cmc, ec);
                    CompositeMetricContextAdapter adapter = new CompositeMetricContextAdapter(fc, cmc, mis);
                    Object o = adapter.adapt();
                    Monitor compositeMonitor = (Monitor) o;

                    ///////////////////////////////////////////////////////////////////////////
                    //
                    // Add Subscription to all composite monitor to send to CDO
                    //
                    ///////////////////////////////////////////////////////////////////////////
                    if(createMonitorSubscriptions) {
                        fc.addMonitorSubscription(compositeMonitor.getId(), conf.getVisorEndpoint(),
                                SubscriptionType.CDO, FilterType.ANY, 0);
                    }
                }

                /*************************************************************************
                 *
                 *  TRANSFORM METRIC CONDITIONS / NON-FUNCTIONAL EVENTS
                 *
                 *
                 *************************************************************************/
                for (MetricCondition mc : finder.getMetricConditions()){
                    NonFunctionalEvent event = finder.getNfeToCondition(mc);
                    MetricConditionAdapter adapter = new MetricConditionAdapter(fc, mc, event);
                    Object o = adapter.adapt();

                    ComposedMonitor conditionMonitor = (ComposedMonitor)o;

                    ///////////////////////////////////////////////////////////////////////////
                    //
                    // Add Subscription to all conditions / nfe to send to CDO
                    //
                    ///////////////////////////////////////////////////////////////////////////
                    if(createMonitorSubscriptions) {
                        fc.addMonitorSubscription(conditionMonitor.getId(), conf.getVisorEndpoint(),
                                SubscriptionType.CDO_EVENT, FilterType.GT, 0.99);
                    }
                }

                /*************************************************************************
                 *
                 *  TRANSFORM EVENT PATTERNS
                 *
                 *
                 *************************************************************************/
                for (EventPattern ep : finder.getEventPatterns()){
                    EventPatternAdapterFactory factory = new EventPatternAdapterFactoryImpl();
                    Adapter adapter = factory.create(fc, ep);
                    Object o = adapter.adapt();
                    ComposedMonitor composedMonitor = (ComposedMonitor)o;

                    ///////////////////////////////////////////////////////////////////////////
                    //
                    // Add Subscription to all conditions / nfe to send to CDO
                    //
                    ///////////////////////////////////////////////////////////////////////////
                    if(createMonitorSubscriptions) {
                        fc.addMonitorSubscription(composedMonitor.getId(), conf.getVisorEndpoint(),
                                SubscriptionType.CDO_EVENT, FilterType.GT, 0.99);
                    }
                }



                /*************************************************************************
                 *
                 *  Subscribe to SCALING ACTIONS
                 *
                 *
                 *************************************************************************/
                for (Map.Entry<Long, String> entrySet : mapScalingActionEventName.entrySet()){
                    ///////////////////////////////////////////////////////////////////////////
                    //
                    // Add Subscription to all rules by the generated composed monitor
                    // (events are mapped to composed monitors)
                    //
                    ///////////////////////////////////////////////////////////////////////////
                    ComposedMonitor m = fc.getComposedMonitorByExternalId(entrySet.getValue());

                    if(createMonitorSubscriptions) {
                        fc.addMonitorSubscription(m.getId(), conf.getVisorEndpoint(),
                                SubscriptionType.CDO_EVENT, FilterType.GT, 0.99);
                    }
                }

                // Printing
                Printer printer = new Printer(fc);
                // Print out raw monitors:
                logger.info(printer.printRawMetrics());
                // Print out composed monitors:
                logger.info(printer.printCompositeMetrics());
                // Print out instances:
                logger.info(printer.printMonitorInstances());
            }

            ims.terminate();
            System.out.println("The SRL adapter was executed.");
        } catch(Exception ex){
            ims.terminate();
            System.out.println("Error occurred during execution of the SRL adapter.");
            ex.printStackTrace();
        }
    }

    public static List<Long> getScalingActionById(String eventId){
        return Finder.getScalingActionsByEventId(mapScalingActionEventName, eventId);
    }
}
