/*
 * Copyright (C) 2015 University of Ulm.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/
 */

package org.ow2.paasage.camel.srl.adapter.config;

import org.apache.commons.cli.*;

import javax.annotation.Nullable;

import static com.google.common.base.Preconditions.checkState;

/**
 * Created by Frank on 09.08.2015.
 */
public class CommandLinePropertiesAccessorImpl
    implements CommandLinePropertiesAccessor {

    private final Options options;
    private CommandLine commandLine;
    private final static DefaultParser parser = new DefaultParser();
    private final static HelpFormatter helpFormatter = new HelpFormatter();

    public CommandLinePropertiesAccessorImpl(String[] args) {
        this.options = new Options();
        this.generateOptions(this.options);

        try {
            this.commandLine = this.parser.parse(options, args);
        } catch (ParseException e) {
            this.commandLine = null;
        }
    }

    private void generateOptions(Options options) {
        options.addOption(
            Option.builder("cdoUser").longOpt("cdoUser").desc("Username to access the CDO").hasArg()
                .build());
        options.addOption(
            Option.builder("cdoPass").longOpt("cdoPass").desc("Password to access the CDO").hasArg()
                .build());
        options.addOption(
            Option.builder("modelName").longOpt("modelName").desc("Name of model in the CDO").hasArg()
                .build());
        options.addOption(
            Option.builder("resourceName").longOpt("resourceName").desc("Name of resource in the CDO").hasArg()
                .build());
        options.addOption(
            Option.builder("executionContextName").longOpt("executionContextName").desc("Name of ExecutionContext in the CDO").hasArg()
                .build());
        options.addOption(
            Option.builder("colUser").longOpt("colUser").desc("Username to access the colosseum").hasArg()
                .build());
        options.addOption(
            Option.builder("colTen").longOpt("colTen").desc("Tenant to access the colosseum").hasArg()
                .build());
        options.addOption(
            Option.builder("colPass").longOpt("colPass").desc("Password to access the colosseum")
                .hasArg().build());
        options.addOption(
                Option.builder("colUrl").longOpt("colUrl").desc("Url to access the colosseum")
                        .hasArg().build());
        options.addOption(
            Option.builder("writeExample").longOpt("writeExample").desc("Write example to CDO?")
                .hasArg().build());
        options.addOption(
            Option.builder("createMetricInstances").longOpt("createMetricInstances").desc("Create MetricInstances?")
                .hasArg().build());
        options.addOption(
            Option.builder("visEndpoint").longOpt("visEndpoint").desc("Endpoint of visor to send to CDO.")
                .hasArg().build());
        options.addOption(
                Option.builder("ExecutionMode").longOpt("ExecutionMode").desc("ExecutionMode of the Adapter.")
                        .hasArg().build());
        options.addOption(
                Option.builder("ZeroMqPort").longOpt("ZeroMqPort").desc("Port of the ZeroMQ server to listen to.")
                        .hasArg().build());
        options.addOption(
                Option.builder("ZeroMqUri").longOpt("ZeroMqUri").desc("Endpoint for the ZeroMQ server to listen to.")
                        .hasArg().build());
        options.addOption(
                Option.builder("ZeroMqQueue").longOpt("ZeroMqQueue").desc("Name of the Queue of the ZeroMQ.")
                        .hasArg().build());
    }

    public void printHelp() {
        helpFormatter.printHelp("java -jar [args] SRLAdapter.jar", options);
    }

    @Nullable protected String getCommandLineOption(String name) {
        if (commandLine != null && commandLine.hasOption(name)) {
            String result = commandLine.getOptionValue(name);
            if(result == null){
                return getDefaultValue(name);
            } else {
                return result;
            }
        } else {
            return getDefaultValue(name);
        }
    }

    private String getDefaultValue(String name) {
        switch (name) {
            case "cdoUser": return "SA";
            case "cdoPass": return "";
            case "modelName": return "BewanCamelModel";
            case "resourceName": return "enterprise-service-application.xmi_1442232824";
            case "executionContextName": return "ExecutionContext";
            case "colUser": return "john.doe@example.com";
            case "colTen": return "admin";
            case "colPass": return "admin";
            case "colUrl": return "http://localhost:9000/api";
            case "writeExample": return "false";
            case "createMetricInstances": return "true";
            //case "visEndpoint": return "134.60.64.43";
            case "visEndpoint": return "localhost";
            case "ExecutionMode": return ExecutionMode.STATIC.toString();
            case "ZeroMqPort": return "5563";
            case "ZeroMqUri": return "tcp://localhost:5563";
            case "ZeroMqQueue": return "newModelArrival";
            case "ModelSourceType": return ModelSourceType.CDO.toString();
            case "CreateMonitorSubscriptions": return "true";
            case "ZeroMqTestmessage": return "resourceName:modelName:executionContextName";
            default : return null;
        }
    }

    @Override public String getCdoUser() {
        return this.getCommandLineOption("cdoUser");
    }

    @Override public String getCdoPassword() {
        return this.getCommandLineOption("cdoPass");
    }

    @Override public String getModelName() {
        return this.getCommandLineOption("modelName");
    }

    @Override public String getResourceName() {
        return this.getCommandLineOption("resourceName");
    }

    @Override public String getExecutionContextName() {
        return this.getCommandLineOption("executionContextName");
    }

    @Override public String getColosseumUser() {
        return this.getCommandLineOption("colUser");
    }

    @Override public String getColosseumTenant() {
        return this.getCommandLineOption("colTen");
    }

    @Override public String getColosseumPassword() {
        return this.getCommandLineOption("colPass");
    }

    @Override public String getColosseumUrl() {
        return this.getCommandLineOption("colUrl");
    }

    @Override public boolean getSaveExample() {
        return this.getCommandLineOption("writeExample").equals("true");
    }

    @Override public boolean getCreateMetricInstances() {
        return this.getCommandLineOption("createMetricInstances").equals("true");
    }

    @Override public String getVisorEndpoint() {
        return this.getCommandLineOption("visEndpoint");
    }

    @Override
    public ExecutionMode getExecutionMode() {
        return ExecutionMode.valueOf(this.getCommandLineOption("ExecutionMode"));
    }

    @Override
    public int getZeroMqPort() {
        return Integer.parseInt(this.getCommandLineOption("ZeroMqPort"));
    }

    @Override
    public String getZeroMqUri() {
        return this.getCommandLineOption("ZeroMqUri");
    }

    @Override
    public String getZeroMqQueue() {
        return this.getCommandLineOption("ZeroMqQueue");
    }

    @Override
    public ModelSourceType getModelSourceType() {
        return ModelSourceType.valueOf(this.getCommandLineOption("ModelSourceType"));
    }

    @Override
    public boolean getCreateMonitorSubscriptions() {
        return this.getCommandLineOption("CreateMonitorSubscriptions").equals("true");
    }

    @Override
    public String getZeroMqTestmessage() {
        return this.getCommandLineOption("ZeroMqTestmessage");
    }
}
