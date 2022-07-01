/*
 * Copyright (C) 2017-2022 Institute of Communication and Computer Systems (imu.iccs.gr)
 *
 * This Source Code Form is subject to the terms of the Mozilla Public License, v2.0.
 * If a copy of the MPL was not distributed with this file, You can obtain one at
 * https://www.mozilla.org/en-US/MPL/2.0/
 */

package eu.melodic.event.translate.util;

import camel.constraint.ConstraintPackage;
import camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.eresource.CDOResourceFolder;
import org.eclipse.emf.cdo.eresource.CDOResourceNode;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.LinkedBlockingDeque;

@Slf4j
@Service
public class CdoCpModelUtil {

    /*public static void main(String[] args) {
        CdoCpModelUtil util = new CdoCpModelUtil();
        log.info("-------------------->\n{}", util.getResourceTree(true));
    }*/

    public enum IMPORT_OP { GET, CREATE, GET_OR_CREATE }

    private final static String FOLDER_RESOURCE = "FOLDER";
    private final static List<EPackage> registeredPackages;

    private boolean keepCdoSessionAlive = true;
    private CDOClientXImpl cdoClient;
    private CDOSessionX session;

    static {
        log.debug("Initialising CP model packages...");

        TypesPackage.eINSTANCE.eClass();
        CpPackage.eINSTANCE.eClass();
        TypePackage.eINSTANCE.eClass();
        ConstraintPackage.eINSTANCE.eClass();

        // Register the XMI resource factory for the .xmi extension
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("*", new XMIResourceFactoryImpl());

        registeredPackages = Arrays.asList(
                CpPackage.eINSTANCE, ConstraintPackage.eINSTANCE);
        log.debug("Initialising CP model packages... ok");
    }

    private void openSession() {
        if (session==null) {
            log.debug("Initializing CDO session...");
            cdoClient = new CDOClientXImpl(registeredPackages);
            session = cdoClient.getSession();
            log.info("Initializing CDO session... connected");
        }
    }

    private void closeSession() {
        if (session!=null && !keepCdoSessionAlive) {
            log.debug("Closing CDO session...");
            session.closeSession();
            cdoClient = null;
            log.info("Closing CDO session... disconnected");
        }
    }

    public Map<String,String> getResourceTree(boolean filterResources) {
        log.debug("getResourceTree: Listing resource tree: filter={}", filterResources);

        // Connect to CDO repository
        openSession();
        CDOView cdoView = session.openView();
        try {
            // Get resources with CAMEL and ConstraintProblem models
            Map<String,String> resources = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

            String baseUri = cdoView.getRootResource().getURI().toString();
            log.trace("getResourceTree: Base URI: {}", baseUri);

            LinkedBlockingDeque<CDOResourceNode> queue = new LinkedBlockingDeque<>(
                    Arrays.asList(cdoView.getElements()));

            while (!queue.isEmpty()) {
                CDOResourceNode e = queue.removeFirst();

                String uri = e.getURI().toString();
                String path = e.getPath();
                String name = e.getName();
                String className = e.eClass().getName();
                String classFQN = e.eClass().getInstanceClassName();
                log.debug("getResourceTree: uri={}, path={}, name={}, class={} / {}", uri, path, name, className, classFQN);

                if (e instanceof CDOResourceFolder) {
                    log.debug("getResourceTree:    Processing resource folder: {}", path);
                    CDOResourceFolder folder = (CDOResourceFolder) e;
                    EList<CDOResourceNode> children = folder.getNodes();    // or 'folder.eContents()'
                    resources.put(path, FOLDER_RESOURCE);

                    //Collections.reverse(children);
                    children.forEach(queue::addFirst);
                } else
                if (e instanceof CDOResource) {
                    log.trace("getResourceTree:    Processing model resource: {}", path);
                    EList<EObject> contents = ((CDOResource) e).getContents();
                    log.trace("getResourceTree:    Resource contains {} models", contents.size());
                    if (contents.size()>1)
                        log.trace("getResourceTree:    Only the first model will be used: {}", path);
                    if (contents.size()>0) {
                        String contentClassName = contents.get(0).eClass().getName();
                        String contentClassFQN = contents.get(0).eClass().getInstanceClassName();
                        log.debug("getResourceTree:    Resource model is {} ({})", contentClassName, contentClassFQN);
                        if ("CamelModel".equals(contentClassName) || "ConstraintProblem".equals(contentClassName)) {
                            log.debug("getResourceTree:     Resource added to results: {}", path);
                            resources.put(path, contentClassName);
                        } else {
                            if (filterResources) {
                                log.debug("getResourceTree:     Ignore resource containing an unknown model type: {}", path);
                            } else {
                                log.debug("getResourceTree:     Resource added to results: {}", path);
                                resources.put(path, null);
                            }
                        }
                    }
                } else {
                    if (filterResources) {
                        log.debug("getResourceTree:     Ignore resource not containing a model: {}", path);
                    } else {
                        log.debug("getResourceTree:     Resource added to results: {}", path);
                        resources.put(path, null);
                    }
                }
            }
            log.debug("getResourceTree: Resource tree: {}", resources);

            return resources;
        } catch (Exception ex) {
            log.error("getResourceTree: Exception: ", ex);
            throw ex;
        } finally {
            // Close CDO session
            if (cdoView != null) {
                cdoView.close();
            }
            closeSession();
        }
    }

    public Resource loadFile(@NonNull String filePath) {
        log.debug("loadFile: Loading resource from file: {}", filePath);
        if (StringUtils.isBlank(filePath)) throw new IllegalArgumentException("Argument 'filePath' cannot be blank or null");

        URI uri = URI.createURI(filePath);
        ResourceSet resSet = new ResourceSetImpl();

        // Load the CP model from XMI file
        Resource resource = resSet.getResource(uri, true);
        EcoreUtil.resolveAll(resSet);
        try {
            resource.load(null);
            for (Resource.Diagnostic diagnostic : resource.getWarnings()) {
                // print the issues
                log.warn("loadFile: Loading resource '{}' produced warning: {}", filePath, diagnostic.toString());
            }
            for (Resource.Diagnostic error : resource.getErrors()) {
                // print the errors
                log.error("loadFile: Loading resource '{}' produced error: {}", filePath, error.toString());
            }
        } catch (IOException ioe) {
            log.error("loadFile: Loading resource '{}' caused IOException: ", filePath, ioe);
        } catch (Exception e) {
            log.error("loadFile: Loading resource '{}' caused Exception: ", filePath, e);
        }
        return resource;
    }

    public Resource loadString(@NonNull String contentsStr, @NonNull String uriStr) {
        log.debug("loadString: Loading resource from string: {}\n{}", uriStr, contentsStr);
        if (StringUtils.isBlank(uriStr)) throw new IllegalArgumentException("Argument 'uriStr' cannot be blank or null");

        URI uri = URI.createURI(uriStr);
        ResourceSet resSet = new ResourceSetImpl();

        // Load the CP model from XMI string
        Resource resource = resSet.createResource(uri);
        EcoreUtil.resolveAll(resSet);
        try {
            // Load CP model (XMI)
            java.io.ByteArrayInputStream input = new java.io.ByteArrayInputStream(contentsStr.getBytes(StandardCharsets.UTF_8));
            resource.load(input, null);
            //log.debug("Resource loaded:\n{}", contentsStr);

            for (Resource.Diagnostic diagnostic : resource.getWarnings()) {
                // print the issues
                log.warn("loadString: Loading resource produced warning: {}", diagnostic.toString());
            }
            for (Resource.Diagnostic error : resource.getErrors()) {
                // print the errors
                log.error("loadString: Loading resource produced error: {}", error.toString());
            }
        } catch (IOException ioe) {
            log.error("loadString: Loading resource caused IOException: ", ioe);
        } catch (Exception e) {
            log.error("loadString: Loading resource caused Exception: ", e);
        }
        return resource;
    }

    public String getResource(@NonNull String resourceId) throws IOException {
        log.info("getResource: BEGIN: {}", resourceId);

        // Connect to CDO repository
        openSession();
        CDOView cdoView = session.openView();
        try {
            // Get CP model from resource
            log.debug("getResource: Retrieving resource: {}", resourceId);
            CDOResource resource = cdoView.getResource(resourceId);
            //ConstraintProblem cpModel = (ConstraintProblem) resource.getContents().get(0);

            // Print resource (XMI)
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            resource.save(output, null);
            String outputStr = output.toString();
            log.debug("getResource: Resource retrieved: {}\n{}", resourceId, outputStr);

            return outputStr;

        } catch (Exception ex) {
            log.error("getResource: Exception: ", ex);
            throw ex;
        } finally {
            // Close CDO session
            if (cdoView != null) {
                cdoView.close();
            }
            closeSession();
        }
    }

    public void importResourceFromFile(@NonNull String xmiFile, @NonNull String resourceId, @NonNull IMPORT_OP op) throws CommitException, IOException {
        // Load resource from XMI file
        Resource resource = loadFile(xmiFile);
        importResource(resource, resourceId, op);
    }

    public void importResourceFromString(@NonNull String xmiStr, @NonNull String resourceId, @NonNull IMPORT_OP op) throws CommitException, IOException {
        // Load CP model from XMI string
        Resource resource = loadString(xmiStr, resourceId);
        importResource(resource, resourceId, op);
    }

    private void importResource(@NonNull Resource resXmi, @NonNull String resourceId, @NonNull IMPORT_OP op) throws CommitException, IOException {
        log.info("importResource: BEGIN: {} {}", op, resourceId);

        // Connect to CDO repository
        openSession();
        CDOTransaction transaction = null;
        try {
            // Print CP model (XMI)
            java.io.ByteArrayOutputStream output = new java.io.ByteArrayOutputStream();
            resXmi.getContents().get(0).eResource().save(output, null);
            //log.debug("Resource to import:\n{}", output);

            // Connect to CDO repository
            CDOClientXImpl cdoClient = new CDOClientXImpl(registeredPackages);
            session = cdoClient.getSession();
            transaction = session.openTransaction();
            log.info("importResource: Saving to resource: {}", resourceId);

            // ...get or create resource in CDO repository
            CDOResource resource = null;
            if (op==IMPORT_OP.GET) resource = transaction.getResource(resourceId);
            if (op==IMPORT_OP.CREATE) resource = transaction.createResource(resourceId);
            if (op==IMPORT_OP.GET_OR_CREATE) resource = transaction.getOrCreateResource(resourceId);

            // ...clear previous contents
            resource.getContents().clear();
            // ...import CP model into resource
            resource.getContents().addAll(resXmi.getContents());

            // ...commit changes
            transaction.commit();
            log.info("importResource: Saved: {}", resourceId);
            transaction = null;

        } catch (Exception ex) {
            log.error("importResource: Exception: ", ex);
            throw ex;
        } finally {
            // Close CDO session
            if (transaction != null) {
                transaction.rollback();
                transaction.close();
            }
            closeSession();
        }
    }

    public boolean deleteResource(@NonNull String resourceId, boolean deleteChildren) throws CommitException, IOException {
        log.info("deleteResource: BEGIN: {}", resourceId);

        // Connect to CDO repository
        openSession();
        CDOTransaction transaction = session.openTransaction();
        try {
            // Delete resource from CDO repository
            log.info("deleteResource: Deleting resource: {}", resourceId);
            CDOResourceNode resource = transaction.getResourceNode(resourceId);
            if (!deleteChildren && resource instanceof CDOResourceFolder && ((CDOResourceFolder) resource).getNodes().size() > 0) {
                log.warn("deleteResource: Resource is folder with contents. Will not be deleted: {}", resourceId);
                return false;
            } else {
                resource.delete(null);

                // Close CDO session
                transaction.commit();
                log.info("deleteResource: Deleted: {}", resourceId);
                return true;
            }

        } catch (Exception ex) {
            log.error("deleteResource: Exception: ", ex);
            throw ex;
        } finally {
            // Close CDO session
            if (transaction != null) {
                transaction.rollback();
                transaction.close();
            }
            closeSession();
        }
    }
}