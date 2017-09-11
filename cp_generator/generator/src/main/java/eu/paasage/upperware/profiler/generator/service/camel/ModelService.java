package eu.paasage.upperware.profiler.generator.service.camel;

import org.eclipse.emf.ecore.resource.Resource;

/**
 * Created by pszkup on 17.08.17.
 */
public interface ModelService {

    void saveModel(Resource resourceToSave, String path);
}
