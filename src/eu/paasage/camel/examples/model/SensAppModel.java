package eu.paasage.camel.examples.model;

import eu.paasage.camel.examples.model.submodels.*;
import eu.paasage.camel.examples.model.submodels.organizations.AmazonOrganizationModel;
import eu.paasage.camel.examples.model.submodels.organizations.FlexiantOrganizationModel;
import eu.paasage.camel.examples.model.submodels.organizations.SintefOrganizationModel;
import eu.paasage.camel.CamelFactory;
import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.*;
import eu.paasage.camel.execution.ExecutionContext;
import eu.paasage.camel.execution.ExecutionModel;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.Location;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.provider.Constraint;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.scalability.HorizontalScalabilityPolicy;
import eu.paasage.camel.scalability.ScalabilityModel;
import eu.paasage.camel.scalability.VerticalScalabilityPolicy;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.javatuples.Quartet;
import org.javatuples.Quintet;
import org.javatuples.Septet;
import org.javatuples.Triplet;

/**
 * Created by orzech on 27/07/14.
 */
public class SensAppModel {

    public static EObject createMyAppModel() {
        // complete mapping of the SensApp examples
        CamelModel camelModel = CamelFactory.eINSTANCE.createCamelModel();
        EList<OrganisationModel> orgModels = camelModel.getOrganisationModels();

        //ProviderModel
        Quintet<ProviderModel, Feature, Constraint, Constraint, Constraint> providerReturns = ProviderModelClass.createProviderModel();
        ProviderModel providerModel = providerReturns.getValue0();
        Feature vmFeature = providerReturns.getValue1();
        Constraint smallVmConstraint = providerReturns.getValue2();
        Constraint mediumVmConstraint = providerReturns.getValue3();
        Constraint largeVmConstraint = providerReturns.getValue4();
        camelModel.getProviderModels().add(providerModel);

        //amazonOrgModel
        Triplet<OrganisationModel, CloudProvider, Location> amazonReturns = AmazonOrganizationModel.createAmazonOrganizationModel(providerModel);
        OrganisationModel amazonOrgModel = amazonReturns.getValue0();
        CloudProvider amazonProvider = amazonReturns.getValue1();
        Location amazonEuLocation = amazonReturns.getValue2();
        orgModels.add(amazonOrgModel);

        //flexiantOrgModel
        Triplet<OrganisationModel, CloudProvider, Location> flexiantReturns = FlexiantOrganizationModel.createFlexiantOrganizationModel(providerModel);
        OrganisationModel flexiantOrgModel = flexiantReturns.getValue0();
        CloudProvider flexiantProvider = flexiantReturns.getValue1();
        Location flexiantLocation = flexiantReturns.getValue2();
        orgModels.add(flexiantOrgModel);

        //sintefOrgModel
        Quartet<OrganisationModel, CloudProvider, Location, User> sintefReturns = SintefOrganizationModel.createSintefOrganizationModel(providerModel, amazonProvider, flexiantProvider);
        OrganisationModel sintefOrgModel = sintefReturns.getValue0();
        CloudProvider sintefNovaProvider = sintefReturns.getValue1();
        Location osloNovaLocation = sintefReturns.getValue2();
        User user1 = sintefReturns.getValue3();
        orgModels.add(sintefOrgModel);

        //Deployment
        Septet<eu.paasage.camel.deployment.DeploymentModel, InternalComponent, InternalComponentInstance, InternalComponentInstance, VMInstance, VMInstance, VM> deploymentReturn = DeploymentModelClass.createMyAppDeploymentModel(amazonProvider, flexiantProvider, sintefNovaProvider, amazonEuLocation, flexiantLocation, osloNovaLocation, camelModel, vmFeature, smallVmConstraint, mediumVmConstraint, largeVmConstraint);
        eu.paasage.camel.deployment.DeploymentModel sensAppDeploymentModel = deploymentReturn.getValue0();
        InternalComponent sensAppIc = deploymentReturn.getValue1();
        InternalComponentInstance sensApp1Ici = deploymentReturn.getValue2();
        InternalComponentInstance mongoDb1Ici = deploymentReturn.getValue3();
        VMInstance mlInstance = deploymentReturn.getValue4();
        VMInstance llInstance = deploymentReturn.getValue5();
        VM mlVm = deploymentReturn.getValue6();
        camelModel.getDeploymentModels().add(sensAppDeploymentModel);

        //Scalability
        Quartet<ScalabilityModel, ExecutionContext, VerticalScalabilityPolicy, HorizontalScalabilityPolicy> scalabilitReturns = ScalabilityModelClass.createMyScalabilityModel(sensAppIc, sensApp1Ici, mongoDb1Ici, mlInstance, llInstance, mlVm);
        ScalabilityModel scalabilityModel = scalabilitReturns.getValue0();
        ExecutionContext sensAppExecutionContext = scalabilitReturns.getValue1();
        VerticalScalabilityPolicy verticalPolicyMongoDb = scalabilitReturns.getValue2();
        HorizontalScalabilityPolicy horizPolicySensApp = scalabilitReturns.getValue3();
        camelModel.getScalabilityModels().add(scalabilityModel);

        //Execution
        ExecutionModel execModel = ExecutionModelClass.createMyExecutionModel(sensAppDeploymentModel, camelModel, sensAppExecutionContext, verticalPolicyMongoDb, horizPolicySensApp, user1);
        camelModel.getExecutionModels().add(execModel);
        return camelModel;
    }
}
