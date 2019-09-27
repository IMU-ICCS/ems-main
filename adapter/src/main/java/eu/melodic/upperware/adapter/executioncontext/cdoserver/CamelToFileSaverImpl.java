package eu.melodic.upperware.adapter.executioncontext.cdoserver;

import camel.core.CamelModel;
import eu.melodic.upperware.adapter.communication.cdoserver.CdoServerApi;
import eu.melodic.upperware.adapter.exception.AdapterException;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.function.Function;

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CamelToFileSaverImpl implements CamelToFileSaver {

    private static final String DEFAULT_PATH = "/logs/adapter_camel_models/" + CDO_SERVER_PATH + "%s%s%s.xmi";

    public static final Function<CamelModel, String> DEFAULT_NAME_FUNCTION = camelModel -> String.format(DEFAULT_PATH, camelModel.getName(), System.currentTimeMillis(), "");
    public static final Function<CamelModel, String> DEFAULT_NAME_BEFORE_DEPLOYMENT_FUNCTION = camelModel -> String.format(DEFAULT_PATH, camelModel.getName(), System.currentTimeMillis(), "BEFORE_DEP");
    public static final Function<CamelModel, String> DEFAULT_NAME_AFTER_DEPLOYMENT_FUNCTION = camelModel -> String.format(DEFAULT_PATH, camelModel.getName(), System.currentTimeMillis(), "AFTER_DEP");

    private CDOClientX cdoClientX;
    private CdoServerApi cdoServerApi;

    @Override
    public void toFile(CamelModel camelModel) {
        toFile(camelModel, DEFAULT_NAME_FUNCTION);
    }

    @Override
    public void toFile(CamelModel camelModel, Function<CamelModel, String> fileNameFunction) {
        toFile(camelModel, fileNameFunction.apply(camelModel));
    }

    @Override
    public void toFile(CamelModel camelModel, String filePath) {
        log.debug("CDODatabaseProxy - saveModels to file...");
        cdoClientX.exportModel(camelModel, filePath);
        log.debug("CDODatabaseProxy - saveModels - Models saved to file {}!", filePath);
    }

    @Override
    public void toFile(String resourceName) {
        toFile(resourceName, DEFAULT_NAME_FUNCTION);
    }

    @Override
    public void toFile(String resourceName, Function<CamelModel, String> fileNameFunction) {
        CDOSessionX cdoSessionX = cdoServerApi.openSession();
        CDOTransaction tr = cdoSessionX.openTransaction();
        try {
            CamelModel camelModel = cdoServerApi.getCamelModel(resourceName, tr);
            toFile(camelModel, fileNameFunction);
        } catch (Exception e) {
            throw new AdapterException("Exception during adding HistoryRecord", e);
        } finally {
            cdoSessionX.closeTransaction(tr);
            cdoSessionX.closeSession();
        }
    }

    @Override
    public void toFile(String resourceName, String filePath) {
        toFile(resourceName, camelModel -> filePath);
    }


}
