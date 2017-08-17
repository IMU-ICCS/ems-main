package eu.paasage.upperware.profiler.generator.service.camel;

import org.eclipse.emf.ecore.resource.Resource;

import java.io.InputStream;

/**
 * Created by pszkup on 17.08.17.
 */
public interface ModelService {

    Resource loadModelFromInputStream(String path, InputStream is);

    void saveModel(Resource resourceToSave, String path);
}
