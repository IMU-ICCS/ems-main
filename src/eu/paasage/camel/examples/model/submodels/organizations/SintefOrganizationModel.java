package eu.paasage.camel.examples.model.submodels.organizations;

import eu.paasage.camel.organisation.*;
import eu.paasage.camel.provider.ProviderModel;
import org.eclipse.emf.common.util.EList;
import org.javatuples.Quartet;

/**
 * Created by orzech on 27/07/14.
 */
public class SintefOrganizationModel {

    public static Quartet<OrganisationModel, CloudProvider, Location, User> createSintefOrganizationModel(ProviderModel providerModel, CloudProvider amazonProvider, CloudProvider flexiantProvider) {
        ////// START definition of Sintef Nova Organisation model

        OrganisationModel sintefOrgModel = OrganisationFactory.eINSTANCE.createOrganisationModel();
        EList<DataCenter> sintefDCs = sintefOrgModel.getDataCentres();
        EList<Location> sintefLocs = sintefOrgModel.getLocations();
        EList<User> sintefUsers = sintefOrgModel.getUsers();
        EList<Credentials> sintefCredentials = sintefOrgModel.getCredentials();

        User user1 = OrganisationFactory.eINSTANCE.createUser();
        user1.setEmail("user@sintef.no");
        user1.setFirstName("User1");
        user1.setLastName("User");

        sintefUsers.add(user1);

        Credentials user1AmazonCredentials = OrganisationFactory.eINSTANCE.createCredentials();
        user1AmazonCredentials.setCloudProvider(amazonProvider);
        sintefCredentials.add(user1AmazonCredentials);
        Credentials user1FlexiantCredentials = OrganisationFactory.eINSTANCE.createCredentials();
        user1FlexiantCredentials.setCloudProvider(flexiantProvider);
        sintefCredentials.add(user1FlexiantCredentials);

        CloudProvider sintefNovaProvider = OrganisationFactory.eINSTANCE.createCloudProvider();
        sintefNovaProvider.setEmail("contact@sintef.no");
        sintefNovaProvider.setIaaS(true);
        sintefNovaProvider.setName("Sintef-Nova");
        sintefNovaProvider.setPaaS(true);
        sintefNovaProvider.setProviderModel(providerModel);
        sintefNovaProvider.setPublic(false);
        sintefNovaProvider.setSaaS(false);

        sintefOrgModel.setProvider(sintefNovaProvider);

        Location osloNovaLocation = OrganisationFactory.eINSTANCE.createLocation();
        osloNovaLocation.setCountry("Norway");
        osloNovaLocation.setCity("Oslo");
        osloNovaLocation.setLatitude(0);
        osloNovaLocation.setLongitude(0);
        osloNovaLocation.setName("oslo-nova");

        sintefLocs.add(osloNovaLocation);

        DataCenter sintefDataCenter = OrganisationFactory.eINSTANCE.createDataCenter();
        sintefDataCenter.setCloudProvider(sintefNovaProvider);
        sintefDataCenter.setCodeName("nova");
        sintefDataCenter.setLocation(osloNovaLocation);
        sintefDataCenter.setName("Sintef Nova Data Centre");

        sintefDCs.add(sintefDataCenter);

        Credentials user1SintefNovaCredentials = OrganisationFactory.eINSTANCE.createCredentials();
        user1SintefNovaCredentials.setCloudProvider(sintefNovaProvider);
        sintefCredentials.add(user1SintefNovaCredentials);

        ////// END definition of Sintef Nova Organisation model
        return new Quartet<OrganisationModel, CloudProvider, Location, User>(sintefOrgModel, sintefNovaProvider, osloNovaLocation, user1);
    }

}
