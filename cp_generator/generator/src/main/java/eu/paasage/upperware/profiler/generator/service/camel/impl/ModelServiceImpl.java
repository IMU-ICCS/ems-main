package eu.paasage.upperware.profiler.generator.service.camel.impl;

import eu.paasage.upperware.profiler.generator.service.camel.ModelService;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class ModelServiceImpl implements ModelService {


    @Override
    public Resource loadModelFromInputStream(String path, InputStream is) {
        ResourceSet rs = new ResourceSetImpl();
        Resource r = rs.createResource(URI.createFileURI((new File(path)).getAbsolutePath()));

        try {
            r.load(is, null);
            EcoreUtil.resolveAll(r);

            for (Resource.Diagnostic d : r.getWarnings()) {
                log.info(d.toString());
            }

            for (Resource.Diagnostic d : r.getErrors()) {
                log.info(d.toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return r;
    }

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

        //TODO - is this needed??
//            File pathFile = new File(path);
//            File dirs = pathFile.getParentFile();
//            dirs.mkdirs();
//            fos = new FileOutputStream(pathFile);


        try (FileOutputStream fos = new FileOutputStream("people.bin")) {
            resourceToSave.save(fos, options);
        } catch(IOException e) {
            log.error("Model under path "+ path +" not saved", e);
        }
    }

}
