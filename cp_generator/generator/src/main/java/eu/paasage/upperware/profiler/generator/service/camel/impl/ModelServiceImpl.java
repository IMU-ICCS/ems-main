package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.profiler.generator.service.camel.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ModelServiceImpl implements ModelService {

    /**
     * Writes a file with the model in the parameter
     *
     * @param resourceToSave the  model to save
     * @param path           the path were the file is created
     */
    public void saveModel(Resource resourceToSave, String path) {
        Map options = new HashMap();
        options.put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);
        saveModel(resourceToSave, path, options);

    }

    /**
     * Saves a model using a given resource
     *
     * @param resourceToSave The resource containing the model
     * @param path           To save the model
     * @param options        Related to the save operation
     */
    private void saveModel(Resource resourceToSave, String path, Map options) {

        try (FileOutputStream fos = new FileOutputStream(path)) {
            resourceToSave.save(fos, options);
        } catch(IOException e) {
            log.error("Model under path "+ path +" not saved", e);
        }
    }

}
