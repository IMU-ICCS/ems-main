/*
 * Copyright (C) 2017 7bulls.com
 *
 * This Source Code Form is subject to the terms of the
 * Mozilla Public License, v. 2.0. If a copy of the MPL
 * was not distributed with this file, You can obtain one at
 * http://mozilla.org/MPL/2.0/.
 */

package eu.melodic.upperware.adapter.communication.cdoserver;

import eu.melodic.upperware.adapter.ApplicationContext;
import eu.paasage.camel.Application;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.CamelPackage;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.requirement.RequirementGroup;
import eu.paasage.camel.requirement.RequirementModel;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServerClientApi implements CdoServerApi {

  private ApplicationContext applicationContext;

  private static Map<String, Object> opts = new HashMap<>();

  static {
    XMIResToResFact();
    opts.put(XMIResource.OPTION_SCHEMA_LOCATION, true);
  }


  private static void XMIResToResFact(){
    Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put
            ("*",
                    new XMIResourceFactoryImpl() {
                      public Resource createResource(URI uri) {
                        return new XMIResourceImpl(uri);
                      }
                    }
            );
  }


  @Override
  public DeploymentModel getModelToDeploy(@NonNull String resourceName, CDOTransaction tr) {
    EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();
    if (CollectionUtils.isNotEmpty(contents)) {
      CamelModel model = CdoTool.getLastCamelModel(contents)
              .orElseThrow(() -> new IllegalStateException(String.format("Could not find Camel Model for resourceName=%s", resourceName)));
      if (model != null) {
        EList<DeploymentModel> deploymentModels = model.getDeploymentModels();
        if (CollectionUtils.isNotEmpty(deploymentModels)) {
          return deploymentModels.get(deploymentModels.size() - 1);
        }
      }
    }
    throw new IllegalArgumentException(String.format("Cannot load Camel Deployment Model for resourceName=%s. " +
      "Check the value is valid and the model is available in CDO Server.", resourceName));
  }

  @Override
  public DeploymentModel getDeployedModel(String resourceName, CDOTransaction tr) {
    EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();

    if (CollectionUtils.isNotEmpty(contents)) {
      int numberOfCamelModels = contents.size();

      for (int i = numberOfCamelModels-1; i > -1 ; i--) {
          CamelModel model = (CamelModel) contents.get(i);

          if(model != null) {
            EList<ExecutionModel> executionModels = model.getExecutionModels();
            int numberOfExecModels = executionModels.size();

            for (int j = numberOfExecModels - 1; j > -1; j--) {
              EList<ExecutionContext> executionContexts = executionModels.get(j).getExecutionContexts();
              if (!executionContexts.isEmpty()) {
                exportModel(model, "~/"+resourceName+".xmi");
                return executionContexts.get(executionContexts.size()-1).getDeploymentModel();
              }
            }
          }
      }
      return null;
    }
    throw new IllegalArgumentException(String.format("Cannot load Camel Deployment Model for resourceName=%s. " +
      "Check the value is valid and the model is available in CDO Server.", resourceName));
  }


  public boolean exportModel(EObject model, String filePath){
    try{
      final ResourceSet rs = new ResourceSetImpl();
      rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
      Resource res = rs.createResource(URI.createFileURI(filePath));
      res.getContents().add(model);
      res.save(opts);
      return true;
    }
    catch(Exception e){
      log.error("Something went wrong while exporting model: " + model + " at path: " + filePath,e);
    }
    return false;
  }



  @Override
  public void setExecutionContext(DeploymentModel deploymentModel, String execContextName, String requirementGroupName, CDOTransaction tr) {
    CamelModel camelModel = (CamelModel) deploymentModel.eContainer();
    Collection<ExecutionModel> execModels = camelModel.getExecutionModels();
    Application app = camelModel.getApplications().get(0);

    Optional<RequirementGroup> requirementGroupOpt = getRequirementGroup(camelModel.getRequirementModels());
    RequirementGroup requirementGroup = requirementGroupOpt
            .orElseThrow(() -> new IllegalArgumentException(format("Could not find RequirementGroup for %s application", app.getName())));

      ExecutionModel newExecModel = ExecutionFactory.eINSTANCE.createExecutionModel();
      newExecModel.setName(execContextName);

      ExecutionContext execContext = ExecutionFactory.eINSTANCE.createExecutionContext();
      execContext.setName(execContextName);
      execContext.setApplication(app);
      execContext.setDeploymentModel(deploymentModel);
      execContext.setRequirementGroup(requirementGroup);

      newExecModel.getExecutionContexts().add(execContext);

      execModels.add(newExecModel);
  }

  private Optional<RequirementGroup> getRequirementGroup(EList<RequirementModel> requirementModels) {
    return requirementModels.stream()
            .map(RequirementModel::getRequirements)
            .flatMap(Collection::stream)
            .filter(requirement -> requirement instanceof RequirementGroup)
            .map(requirement -> (RequirementGroup) requirement)
            .findFirst();
  }

  @Override
  public CDOTransaction openTransaction() {
    return applicationContext.getCdoClient().openTransaction();
  }

  @Override
  public void closeTransaction(CDOTransaction tr) {
    tr.close();
  }
}
