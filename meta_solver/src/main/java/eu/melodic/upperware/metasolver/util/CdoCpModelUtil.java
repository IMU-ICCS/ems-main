/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.upperware.metasolver.util;

import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Collections;

@Slf4j
public class CdoCpModelUtil {

    protected enum COMMAND { HELP, NEW, IMPORT, UPDATE, REPLACE, DELETE, GET }
    protected enum IMPORT_OP { GET, CREATE, GET_OR_CREATE }

    public static void main(String[] args) throws Exception {
        COMMAND cmd = COMMAND.HELP;
        String fileName = null;
        String resourceId = null;
        if (args.length > 0 && StringUtils.isNotBlank(args[0])) {
            cmd = COMMAND.valueOf( args[0].trim().toUpperCase() );
            if (cmd==COMMAND.IMPORT || cmd==COMMAND.NEW || cmd==COMMAND.UPDATE || cmd==COMMAND.REPLACE) {
                fileName = args[1].trim();
                resourceId = args[2].trim();
                if (StringUtils.isNotBlank(fileName) && !fileName.trim().startsWith("file:"))
                    fileName = "file:" + fileName.trim();
            } else if (cmd==COMMAND.GET || cmd==COMMAND.DELETE) {
                resourceId = args[1].trim();
            }
        }
        switch (cmd) {
            case GET:
                log.info("Retrieving resource from CDO repository: {}", resourceId);
                String xmiStr = getCpModel(resourceId);
                if (args.length>=3) {
                    fileName = args[2].trim();
                    if (!fileName.isEmpty()) {
                        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
                            writer.println(xmiStr);
                        }
                    }
                }
                break;
            case NEW:
                log.info("Creating new CDO resource from XMI file: file={}, resource={}", fileName, resourceId);
                checkArgs(fileName, resourceId);
                importCpModel(fileName, resourceId, IMPORT_OP.CREATE);
                getCpModel(resourceId);
                break;
            case IMPORT:
                log.info("Importing XMI file into CDO resource: file={}, resource={}", fileName, resourceId);
                checkArgs(fileName, resourceId);
                importCpModel(fileName, resourceId, IMPORT_OP.GET_OR_CREATE);
                break;
            case UPDATE:
            case REPLACE:
                log.info("Updating/Replacing CDO resource from XMI file: file={}, resource={}", fileName, resourceId);
                checkArgs(fileName, resourceId);
                importCpModel(fileName, resourceId, IMPORT_OP.GET);
                break;
            case DELETE:
                log.info("Deleting resource from CDO repository: {}", resourceId);
                deleteCpModel(resourceId);
                break;
            case HELP:
            default:
                log.info("Usage:");
                log.info("  get <ID> [<FILE>]   Retrieve an existing CP model from CDO repository and optionally save to file");
                log.info("  new <FILE> <ID>     Create a new CP model in CDO repository from an XMI file");
                log.info("  import <FILE> <ID>  Create or replace a CP model in CDO repository from an XMI file");
                log.info("  update <FILE> <ID>  Replace an existing CP model in CDO repository from an XMI file");
                log.info("  delete <ID>         Delete an existing CP model from CDO repository");
                log.info("  help                Print this message");
        }
    }

    protected static void checkArgs(@NonNull String xmiFile, @NonNull String resourceId) {
        if (StringUtils.isBlank(xmiFile)) throw new IllegalArgumentException("'xmiFile' argument cannot be empty or null");
        if (StringUtils.isBlank(resourceId)) throw new IllegalArgumentException("'resourceId' argument cannot be empty or null");
    }

    protected static void initPackages() {
        //log.debug("initialising packages ....");

        TypesPackage.eINSTANCE.eClass();
        CpPackage.eINSTANCE.eClass();

        // Register the XMI resource factory for the .xmi extension
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());
    }

    protected static Resource loadFile(String cpModelFilePath) {
        URI uri = URI.createURI(cpModelFilePath);
        ResourceSet resSet = new ResourceSetImpl();

        // Load the CP model from XMI file
        Resource resource = resSet.getResource(uri, true);
        EcoreUtil.resolveAll(resSet);
        try {
            resource.load(null);
            for (Resource.Diagnostic diagnostic : resource.getWarnings()) {
                // print the issues
                log.warn("loading resource '{}' produced warning: {}", cpModelFilePath, diagnostic.toString());
            }
            for (Resource.Diagnostic error : resource.getErrors()) {
                // print the errors
                log.error("loading resource '{}' produced error: {}", cpModelFilePath, error.toString());
            }
        } catch (IOException ioe) {
            log.error("loading resource '{}' caused IOException: ", cpModelFilePath, ioe);
        } catch (Exception e) {
            log.error("loading resource '{}' caused Exception: ", cpModelFilePath, e);
        }
        return resource;
    }

    protected static String getCpModel(@NonNull String resourceId) throws IOException {
        try {
            // Initialize CDO classes
            initPackages();

            // Connect to CDO repository
            CDOClientXImpl cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE));
            CDOSessionX session = cdoClient.getSession();
            CDOView cdoView = session.openView();

            // Get CP model from resource
            CDOResource resource = cdoView.getResource(resourceId);
            //ConstraintProblem cpModel = (ConstraintProblem) resource.getContents().get(0);

            // Print CP model (XMI)
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            resource.getContents().get(0).eResource().save(output, null);
            String outputStr = output.toString();
            //log.debug("Resource retrieved:\n{}", outputStr);

            // Close CDO session
            cdoView.close();
            session.closeSession();

            return outputStr;

        } catch (Exception ex) {
            log.error("", ex);
            throw ex;
        }
    }

    protected static void importCpModel(@NonNull String xmiFile, @NonNull String resourceId, @NonNull IMPORT_OP op) throws CommitException, IOException {
        CDOSessionX session = null;
        CDOTransaction transaction = null;
        try {
            // Initialize CDO classes
            initPackages();

            // Load CP model from XMI file
            Resource resModel = loadFile(xmiFile);

            // Print CP model (XMI)
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            resModel.getContents().get(0).eResource().save(output, null);
            //log.debug("Resource to import:\n{}", output);

            //ConstraintProblem cpModel = (ConstraintProblem) resModel.getContents().get(0);

            // Connect to CDO repository
            CDOClientXImpl cdoClient = new CDOClientXImpl(Collections.singletonList(CpPackage.eINSTANCE));
            session = cdoClient.getSession();
            transaction = session.openTransaction();
            log.info("Saving to resource: {}", resourceId);

            // ...get or create resource in CDO repository
            CDOResource resource = null;
            if (op==IMPORT_OP.GET) resource = transaction.getResource(resourceId);
            if (op==IMPORT_OP.CREATE) resource = transaction.createResource(resourceId);
            if (op==IMPORT_OP.GET_OR_CREATE) resource = transaction.getOrCreateResource(resourceId);

            // ...clear previous contents
            resource.getContents().clear();
            // ...import CP model into resource
            resource.getContents().add(resModel.getContents().get(0));

            // ...commit changes
            transaction.commit();
            log.info("Saved!");
            transaction = null;

            // Close CDO session
            session.closeSession();

        } catch (Exception ex) {
            log.error("", ex);
            throw ex;
        } finally {
            if (transaction != null) {
                transaction.rollback();
                transaction.close();
            }
            if (session != null) {
                session.closeSession();
                session = null;
            }
        }
    }

    protected static void deleteCpModel(@NonNull String resourceId) throws IOException {
        try {
            // Initialize CDO classes
            initPackages();

            // Connect to CDO repository
            CDOClientXImpl cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE));
            CDOSessionX session = cdoClient.getSession();
            CDOTransaction transaction = session.openTransaction();

            // Delete resource from CDO repository
            CDOResource resource = transaction.getResource(resourceId);
            resource.delete(null);

            // Close CDO session
            transaction.close();
            session.closeSession();

        } catch (Exception ex) {
            log.error("", ex);
            throw ex;
        }
    }
}