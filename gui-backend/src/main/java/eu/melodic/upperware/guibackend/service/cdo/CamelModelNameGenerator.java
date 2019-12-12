package eu.melodic.upperware.guibackend.service.cdo;

import camel.core.CamelModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import org.springframework.stereotype.Service;

import java.io.File;

@Service
public class CamelModelNameGenerator implements ModelNameGenerator {

    @Override
    public String getModelName(File modelFile) {
        CamelModel camelModel = (CamelModel) CDOClient.loadModel(modelFile.getAbsolutePath());
        return camelModel.getName();
    }

}
