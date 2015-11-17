/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter;

import de.uniulm.omi.cloudiator.colosseum.client.Client;
import de.uniulm.omi.cloudiator.colosseum.client.ClientBuilder;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.VMInstance;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.metric.*;
import eu.paasage.camel.scalability.EventPattern;
import eu.paasage.camel.scalability.NonFunctionalEvent;
import eu.paasage.mddb.cdo.client.CDOClient;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.ow2.paasage.camel.srl.adapter.adapter.*;
import org.ow2.paasage.camel.srl.adapter.communication.FrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.communication.RestFrontendCommunicator;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.ow2.paasage.camel.srl.adapter.test.CouchbaseExample;
import org.ow2.paasage.camel.srl.adapter.utils.CamelFinder;
import org.ow2.paasage.camel.srl.adapter.utils.Instantiator;
import org.ow2.paasage.camel.srl.adapter.utils.Printer;

import java.util.List;

/**
 * Created by Frank on 16.11.2015.
 */
public class Execution {
    private static org.apache.log4j.Logger logger;

    static {
        logger = org.apache.log4j.Logger.getLogger(CDOClient.class);
    }

    private final CommandLinePropertiesAccessor conf;

    public Execution(CommandLinePropertiesAccessor conf) {
        this.conf = conf;
    }

    public void run(){
        run(null, null, null);
    }

    public void run(String dynamiceResourceName, String dynamicModelName, String dynamicExecutionContextName){
        boolean createNew = conf.getSaveExample();
        boolean createMetricInstances = conf.getCreateMetricInstances();
        String cdoUser = conf.getCdoUser();
        String cdoPassword = conf.getCdoPassword();
        String modelName = conf.getModelName();
        String resourceName = conf.getResourceName();
        String executionContextName = conf.getExecutionContextName();
        String colUrl = conf.getColosseumUrl();
        String colUser = conf.getColosseumUser();
        String colPassword = conf.getColosseumPassword();
        String colTenant = conf.getColosseumTenant();
        String visorEndpoint = conf.getVisorEndpoint();

        if(dynamicModelName != null){
            createNew = false;
            createMetricInstances = true;
            modelName = dynamicModelName;
        }

        if(dynamiceResourceName != null){
            resourceName = dynamiceResourceName;
        }

        if(dynamicExecutionContextName != null){
            executionContextName = dynamicExecutionContextName;
        }

        //Create the CDOClient
        logger.info("Create CDO client...");
        CDOClient cl = new CDOClient(cdoUser, cdoPassword);


        // Orchestrate SRL Engine
        logger.info("Connect to colosseum");
        ClientBuilder clientBuilder = ClientBuilder.getNew()
                // the base url
                .url(colUrl)
                // the login credentials
                .credentials(colUser, colTenant, colPassword);

        Client colosseumClient = clientBuilder.build();
        FrontendCommunicator fc = new RestFrontendCommunicator(colosseumClient);

        // For testin purpose only:
        //        fc.addMonitorSubscription(491544l, visorEndpoint, SubscriptionType.CDO_EVENT,
        //            FilterType.GT, 0.99);
        //        Thread.sleep(2000);
        //        fc.addMonitorSubscription(491557l, visorEndpoint, SubscriptionType.CDO_EVENT,
        //            FilterType.GT, 0.99);
        //        if(true)return;



        // Save the Couchbase example:
        if (createNew) {
            logger.info("Create new Couchbase-Example into the CDO");
            cl.storeModel(CouchbaseExample.get(null), resourceName, false);
        }


        CDOTransaction trans = cl.openTransaction();
        EList<EObject> objs = trans.getResource(resourceName).getContents();


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
                ExecutionContext ec = finder.getExecutionContext(executionContextName);

                // Create MetricInstances
                if (createMetricInstances) {
                    Instantiator.createMetricInstances(model, ec, objs);

                    // TODO THIS IS A HACK AND NOT MEANT TO BE HERE
                    for(VMInstance instance : finder.getVMInstances()){
                        if(instance.getIp() == null || "".equals(instance.getIp())){
                            String ip = fc.getPublicIpOfVmByName(instance.getName());
                            instance.setIp(ip);
                        }
                    }

                    //trans.commit(); return;
                }

                // Save them
                trans.commit();

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
                    Adapter adapter = new SensorAdapter(conf, fc, sensor);
                    adapter.adapt();
                }

                /*************************************************************************
                 *
                 *  TRANSFORM SCHEDULES
                 *
                 *
                 *************************************************************************/
                for (eu.paasage.camel.metric.Schedule schedule : finder.getSchedules()) {
                    Adapter adapter = new ScheduleAdapter(conf, fc, schedule);
                    adapter.adapt();
                }

                /*************************************************************************
                 *
                 *  TRANSFORM WINDOWS
                 *
                 *
                 *************************************************************************/
                for (Window w : finder.getWindows()){
                    Adapter adapter = new WindowsAdapter(conf, fc, w);
                    adapter.adapt();
                }

                /*************************************************************************
                 *
                 *  TRANSFORM RAW METRIC CONTEXTS
                 *
                 *
                 *************************************************************************/
                for (RawMetricContext rmc : finder.getRawMetricContexts()){
                    List<MetricInstance> mis = finder.getMetricInstances(rmc, ec);
                    Adapter adapter = new RawMetricContextAdapter(conf, fc, rmc, mis);
                    adapter.adapt();
                }

                /*************************************************************************
                 *
                 *  TRANSFORM COMPOSITE METRIC CONTEXTS
                 *
                 *
                 *************************************************************************/
                for (CompositeMetricContext cmc : finder.getCompositeMetricContexts()){
                    List<MetricInstance> mis = finder.getMetricInstances(cmc, ec);
                    CompositeMetricContextAdapter adapter = new CompositeMetricContextAdapter(conf, fc, cmc, mis);
                    adapter.adapt();
                }

                /*************************************************************************
                 *
                 *  TRANSFORM METRIC CONDITIONS / NON-FUNCTIONAL EVENTS
                 *
                 *
                 *************************************************************************/
                for (MetricCondition mc : finder.getMetricConditions()){
                    NonFunctionalEvent event = finder.getNfeToCondition(mc);
                    MetricConditionAdapter adapter = new MetricConditionAdapter(conf, fc, mc, event);
                    adapter.adapt();
                }

                /*************************************************************************
                 *
                 *  TRANSFORM EVENT PATTERNS
                 *
                 *
                 *************************************************************************/
                for (EventPattern ep : finder.getEventPatterns()){
                    EventPatternAdapterFactory factory = new EventPatternAdapterFactoryImpl();
                    Adapter adapter = factory.create(conf, fc, ep);
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
                    Adapter adapter = factory.create(conf, fc, sa, finder.getAssociatedRules(sa), finder.getAssociatedHorizontalScaleRequirements(sa.getInternalComponent()));
                    adapter.adapt();
                }


                // Printing
                Printer printer = new Printer(fc);
                // Print out raw monitors:
                logger.info(printer.printRawMetrics());
                // Print out composed monitors:
                logger.info(printer.printCompositeMetrics());
                // Print out instances:
                logger.info(printer.printMonitorInstances());


                // Add Listener to MAIN event to each rule TODO complete observer pattern

            }

            cl.closeTransaction(trans);
            cl.closeSession();
            System.out.println("The SRL adapter was executed.");
        } catch(Exception ex){
            if(cl != null){
                cl.closeTransaction(trans);
                cl.closeSession();
            }
            System.out.println("Error occurred during execution of the SRL adapter.");
            ex.printStackTrace();
        }
    }
}
