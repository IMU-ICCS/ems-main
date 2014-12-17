package eu.paasage.camel.examples.model.submodels;

import eu.paasage.camel.*;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionFactory;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.scalability.HorizontalScalingPolicy;
import eu.paasage.camel.scalability.VerticalScalingPolicy;

/**
 * Created by orzech on 27/07/14.
 */
public class ExecutionModelClass {

    public static ExecutionModel createMyExecutionModel(DeploymentModel sensAppDeploymentModel, CamelModel camelModel, ExecutionContext sensAppExecutionContext,
                                                        VerticalScalingPolicy verticalPolicyMongoDb, HorizontalScalingPolicy horizPolicySensApp, User user1) {
        ////// START definition of Execution model

        ExecutionModel execModel = ExecutionFactory.eINSTANCE.createExecutionModel();
        execModel.setName("SensApp Execution Model");

        Application sensAppApplication = CamelFactory.eINSTANCE.createApplication();
        sensAppApplication.getDeploymentModels().add(sensAppDeploymentModel);
        sensAppApplication.setName("SensApp");
        sensAppApplication.setOwner(user1);
        sensAppApplication.setVersion("v1.0");

        camelModel.getApplications().add(sensAppApplication);

        sensAppExecutionContext.setApplication(sensAppApplication);
        sensAppExecutionContext.setDeploymentModel(sensAppDeploymentModel);
        sensAppExecutionContext.setID("SensAppEC1");

        RequirementGroup user1RG = CamelFactory.eINSTANCE.createRequirementGroup();

        user1RG.setId("");
        user1RG.setPriority(0);
        user1RG.setRequirementOperator(RequirementOperatorType.AND);
        user1RG.getRequirements().add(verticalPolicyMongoDb);
        user1RG.getRequirements().add(horizPolicySensApp);
        user1RG.setUser(user1);

        camelModel.getRequirements().add(user1RG);

        sensAppExecutionContext.setRequirementGroup(user1RG);
        sensAppExecutionContext.setTotalCost(0);

        execModel.getExecutionContexts().add(sensAppExecutionContext);

        // END definition of Execution model
        return execModel;

    }
}
