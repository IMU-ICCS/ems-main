package eu.paasage.upperware.profiler.generator.db;

import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.profiler.generator.service.camel.ModelService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CDOClientExtended extends CDOClient {

    private ModelService modelService;

    public void storeModel(EObject model, String resourceName) {
        storeModels(Collections.singletonList(model), resourceName);
    }

    public void storeModels(List<EObject> models, String resourceName) {
        CDOTransaction trans = openTransaction();
        CDOResource cdo = trans.getOrCreateResource(resourceName);
        EList<EObject> list = cdo.getContents();

        list.addAll(models);

        try {
            trans.commit();
            trans.close();
        } catch (Exception e) {
            log.error("Problem during saving {} models under path: {}", models.size(), resourceName);
            e.printStackTrace();
        }
    }

    public void storeModelsWithCrossReferences(List<EObject> models, String resourceName) {

        EcoreUtil.Copier copier = new EcoreUtil.Copier();
        CDOTransaction trans = openTransaction();
        CDOResource cdo = trans.getOrCreateResource(resourceName);
        EList<EObject> list = cdo.getContents();

        for (EObject m : models) {
            list.add(copier.copy(m));
        }

        try {
            copier.copyReferences();
            trans.commit();
            trans.close();
        } catch (Exception e) {
            log.error("Problem during saving {} models under path: {}", models.size(), resourceName);
            e.printStackTrace();
        }
    }

    public void storeModelsWithCrossReferences(ResourceSet rs, String resourceName) {
        List<EObject> collect = rs.getResources().stream()
                .map(Resource::getContents)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());

        storeModelsWithCrossReferences(collect, resourceName);
    }

    public void storeModels(String resourceName) {
        CDOTransaction trans = openTransaction();
        CDOResource cdo = trans.getResource(resourceName);
        EList<EObject> list = cdo.getContents();

        ResourceSet rs = new ResourceSetImpl();

        try {
            File pcFile = new File("/temp/appModel.xmi");
            Resource pcResource = rs.createResource(URI.createFileURI(pcFile.getCanonicalPath()));
            pcResource.getContents().add(list.get(0));
            modelService.saveModel(pcResource, pcFile.getCanonicalPath());


            File cpFile = new File("/temp/cpModel.xmi");
            Resource cpResource = rs.createResource(URI.createFileURI(cpFile.getCanonicalPath()));
            cpResource.getContents().add(list.get(1));
            modelService.saveModel(cpResource, cpFile.getCanonicalPath());

            trans.close();

        } catch (IOException e) {
            log.error("Problem during saving models under path: " + resourceName, e);
            e.printStackTrace();
        }
    }

    /* This method is used to obtain the content of a CDOResource with a
     * particular path/name. You should open a view before using this method
     * and then close it. Input parameter: the name/path of the CDOResource.
     */
    public List<EObject> getResourceContents(String path) {
        CDOView view = openTransaction();//openView();
        CDOResource resource = view.getResource(path);
        EList<EObject> content = resource.getContents();

        List<EObject> qr = new ArrayList<EObject>();
        log.debug("CDOClientExtended - getResourceContents - Retrieved Resource " + resource + " path " + path);

        if (!content.isEmpty()) {
            log.debug("CDOClientExtended - getResourceContents - Resource path " + path + " size " + content.size());

            for (EObject o : content) {
                log.debug("CDOClientExtended - getResourceContents - Content " + o);
                qr.add(o);
            }

        } else {
            log.warn("CDOClientExtended - getResourceContents - Resource path " + path + " is empty ");
        }

        return qr;
    }

    public boolean existResource(String path) {
        try {
            CDOView view = openView();
            CDOResource resource = view.getResource(path);
            return resource != null;
        } catch (Exception ex ) {
//        ignore exception
//        org.eclipse.emf.cdo.eresource.impl.CDOResourceImpl
//        in method private void registerProxy(InternalCDOView view) there is unnecessary error log
        }
        return false;
    }

    public List<EObject> getResourceContents(String path, CDOView view) {

        CDOResource resource = view.getResource(path);
        EList<EObject> content = resource.getContents();

        List<EObject> qr = new ArrayList<EObject>();
        log.debug("CDOClientExtended - getResourceContents - Retrieved Resource " + resource + " path " + path);

        if (!content.isEmpty()) {
            log.debug("CDOClientExtended - getResourceContents - Resource path " + path + " size " + content.size());

            for (EObject o : content) {
                log.debug("CDOClientExtended - getResourceContents - Content " + o);
                qr.add(o);
            }

        } else {
            log.warn("CDOClientExtended - getResourceContents - Resource path " + path + " is empty ");
        }

        return qr;
    }

}