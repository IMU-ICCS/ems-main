package eu.paasage.camel.examples.model.submodels.organizations;

import eu.paasage.camel.organisation.*;
import eu.paasage.camel.provider.ProviderModel;
import org.eclipse.emf.common.util.EList;
import org.javatuples.Triplet;

/**
 * Created by orzech on 27/07/14.
 */
public class AmazonOrganizationModel {

    public static Triplet<OrganisationModel, CloudProvider, Location> createAmazonOrganizationModel(ProviderModel providerModel) {
        ////// BEGIN definition of Amazon Organization model

        OrganisationModel amazonOrgModel = OrganisationFactory.eINSTANCE.createOrganisationModel();
        EList<DataCenter> amazonDCs = amazonOrgModel.getDataCentres();
        EList<Location> amazonLocs = amazonOrgModel.getLocations();

        CloudProvider amazonProvider = OrganisationFactory.eINSTANCE.createCloudProvider();
        amazonProvider.setEmail("contact@amazon.com");
        amazonProvider.setIaaS(true);
        amazonProvider.setName("Amazon");
        amazonProvider.setPaaS(true);
        amazonProvider.setProviderModel(providerModel);
        amazonProvider.setPublic(true);
        amazonProvider.setSaaS(true);

        amazonOrgModel.setProvider(amazonProvider);

        Location amazonEuLocation = OrganisationFactory.eINSTANCE.createLocation();
        amazonEuLocation.setCountry("Ireland");
        amazonEuLocation.setLatitude(0);
        amazonEuLocation.setLongitude(0);
        amazonEuLocation.setName("amazon-eu");
        amazonLocs.add(amazonEuLocation);

        DataCenter amazonEuDataCenter = OrganisationFactory.eINSTANCE.createDataCenter();
        amazonEuDataCenter.setCloudProvider(amazonProvider);
        amazonEuDataCenter.setCodeName("amazon-eu");
        amazonEuDataCenter.setLocation(amazonEuLocation);
        amazonEuDataCenter.setName("European Amazon Data Centre");

        amazonDCs.add(amazonEuDataCenter);

        ////// END definition of Amazon Organisation model
        return new Triplet<OrganisationModel, CloudProvider, Location>(amazonOrgModel, amazonProvider, amazonEuLocation);
    }
}
