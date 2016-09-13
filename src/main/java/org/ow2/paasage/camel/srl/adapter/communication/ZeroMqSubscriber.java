/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.communication;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import org.eclipse.emf.ecore.EObject;
import org.ow2.paasage.camel.srl.adapter.config.CommandLinePropertiesAccessor;
import org.ow2.paasage.camel.srl.adapter.config.ModelSourceType;
import org.ow2.paasage.camel.srl.adapter.execution.Execution;
import org.ow2.paasage.camel.srl.adapter.execution.ImportModelSource;
import org.zeromq.ZMQ;
import org.zeromq.ZMQ.Context;
import org.zeromq.ZMQ.Socket;

/**
 * Created by Frank on 16.11.2015.
 */
public class ZeroMqSubscriber implements Runnable {
    private static org.apache.log4j.Logger logger;

    static {
        logger = org.apache.log4j.Logger.getLogger(ZeroMqSubscriber.class);
    }

    private final CommandLinePropertiesAccessor conf;
    private final Context context;
    private final Socket socket;
    private final static int IO_THREAD_NUM = 1;
    public static final String SEPARATOR = ":";


    public ZeroMqSubscriber(CommandLinePropertiesAccessor conf) {
        this(conf, null);
    }

    public ZeroMqSubscriber(CommandLinePropertiesAccessor conf, String alternativeUri) {
        this.conf = conf;
        String uri;
        if (alternativeUri == null) {
            uri = conf.getZeroMqUri();
        } else {
            uri = alternativeUri;
        }
        String queueName = conf.getZeroMqQueue();
        context = ZMQ.context(IO_THREAD_NUM);
        socket = context.socket(ZMQ.SUB);

        socket.connect(uri);
        socket.subscribe(queueName.getBytes(ZMQ.CHARSET));
    }

    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            // Read envelope with address
            String address = socket.recvStr();
            // Read message contents
            String contents = socket.recvStr();

            logger.info(String.format("Received raw message: %s - %s", address, contents));

            CdoConfigTuple converted = convertLine(contents);

            logger.info(String.format("Parsed message as %s", converted));

            // TODO this is blocking - check if this is a problem for frequent requests
            try {
                Execution ex = new Execution(conf);
                ImportModelSource ims = ModelSourceType.mapToIms(conf);

                String camelModelName = null;
                // search deployment model inside the camel model
                // we have to do this, as adapter only sends the deploymentModel name
                for (EObject eObject : ims.getResources(converted.getResourceName())) {
                    if (eObject instanceof CamelModel) {
                        for (DeploymentModel deploymentModel : ((CamelModel) eObject)
                            .getDeploymentModels()) {
                            if (deploymentModel.getName()
                                .equalsIgnoreCase(converted.getDeploymentModelName())) {
                                camelModelName = ((CamelModel) eObject).getName();
                            }
                        }
                    }
                }

                if (camelModelName == null) {
                    throw new NullPointerException(
                        "Could not find a CamelModel containing the DeploymentModel with the name "
                            + converted.getDeploymentModelName());
                }

                logger.info("Forward this message to the ZeroMQ of Metrics-Collector-Accessor.");
                org.ow2.paasage.camel.srl.metrics_collector_accessor.communication.ZeroMqServer server =
                        new org.ow2.paasage.camel.srl.metrics_collector_accessor.communication.ZeroMqServer(conf.getMcaZeroMqPort());
                server.submitValue(conf.getMcaZeroMqQueue(), converted.getResourceName());

                logger.info("Run execution based on incoming ZMQ message.");
                ex.run(ims, converted.getResourceName(), camelModelName,
                    converted.getExecutionContext());
            } catch (Exception ex) {
                logger.error("Error when executing Task: " + contents
                    + ". Ignoring so far and continue listening to requests.", ex);
            }
        }
        socket.close();
        context.term();
    }

    public static CdoConfigTuple convertLine(String contents) {

        // Content format: resource:model:executioncontext

        int indexOfSeperator1 = contents.indexOf(SEPARATOR);
        int indexOfSeperator2 =
            indexOfSeperator1 + contents.substring(indexOfSeperator1 + 1).indexOf(SEPARATOR) + 1;

        String resourceName = contents.substring(0, indexOfSeperator1);

        String modelName = contents.substring(indexOfSeperator1 + 1, indexOfSeperator2);

        String executionContext = contents.substring(indexOfSeperator2 + 1, contents.length());
        if ("".equals(executionContext)) {
            executionContext = null;
        }

        return new CdoConfigTuple(resourceName, modelName, executionContext);
    }
}
