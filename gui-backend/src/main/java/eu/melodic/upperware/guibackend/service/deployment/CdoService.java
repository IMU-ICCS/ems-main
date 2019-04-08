package eu.melodic.upperware.guibackend.service.deployment;

import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.util.CommitException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
@Slf4j
public class CdoService {


    public String getCdoName(String fileName, String fileExtension) {
        return fileName
                .substring(0, fileName.indexOf(fileExtension) - 1);
    }

    public boolean storeFileInCdo(String cdoName, File file) {

        log.info("Storing Model {} into CDO", cdoName);
        EObject model = null;
        CDOClient client = getCdoClient();
        try {
            model = CDOClient.loadModel(file.getAbsolutePath());
        } catch (RuntimeException e) {
            return false;
        }

        boolean successfullyStored = client.storeModel(model, cdoName, true);
        log.info("Model {} successfully stored into CDO", cdoName);
        client.closeSession();
        return successfullyStored;
    }

    public boolean deleteXmi(String cdoName) {
        log.info("Deleting model {} from CDO", cdoName);
        CDOClient client = getCdoClient();
        CDOTransaction cdoTransaction = client.openTransaction();
        CDOResource cdoResource = cdoTransaction.getOrCreateResource(cdoName);
        EList<EObject> contents = cdoResource.getContents();
        if (contents.size() != 0) {
            log.info("CDO has {} resources for {} in CDO", contents.size(), cdoName);
            contents.remove(0);
            try {
                cdoTransaction.commit();
            } catch (CommitException e) {
                cdoTransaction.close();
                log.error("Error by commit transaction with deleting model", e);
                return false;
            }
            return true;
        } else {
            cdoTransaction.close();
            log.error("Such model doesn't exist in CDO");
            return false;
        }
    }

    private CDOClient getCdoClient() {
        CDOClient client = new CDOClient();
        registerPackages(client);
        return client;
    }

    private void registerPackages(CDOClient cdoClient) {
        cdoClient.registerPackage(CpPackage.eINSTANCE);
        cdoClient.registerPackage(TypesPackage.eINSTANCE);
    }
}
