package eu.paasage.upperware.solvertodeployment.db.lib;

import camel.deployment.DeploymentPackage;
import camel.organisation.OrganisationPackage;
import camel.type.TypePackage;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;

@Slf4j
public class CDODatabaseProxyNew {

    private static CDODatabaseProxyNew INSTANCE = new CDODatabaseProxyNew();

    @Getter
    private CDOClientX cdoClient;

    private CDODatabaseProxyNew() {
        cdoClient = new CDOClientXImpl(Arrays.asList(CpPackage.eINSTANCE, TypesPackage.eINSTANCE,
                TypePackage.eINSTANCE, OrganisationPackage.eINSTANCE, DeploymentPackage.eINSTANCE));
    }

    public static CDODatabaseProxyNew getInstance() {
        return INSTANCE;
    }
}
