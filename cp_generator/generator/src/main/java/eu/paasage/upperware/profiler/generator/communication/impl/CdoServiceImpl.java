package eu.paasage.upperware.profiler.generator.communication.impl;

import eu.paasage.camel.CamelModel;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.profiler.generator.communication.CdoService;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.XMIResource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServiceImpl implements CdoService {

    private CDOClient cdoClient;

    private static Map<String, Object> opts = new HashMap<>();

    static {
        XMIResToResFact();
        opts.put(XMIResource.OPTION_SCHEMA_LOCATION, true);
    }

    private static void XMIResToResFact(){
        Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap( ).put("*",
                        new XMIResourceFactoryImpl() {
                            public Resource createResource(URI uri) {
                                return new XMIResourceImpl(uri);
                            }
                        }
                );
    }

    @Override
    public CDOTransaction openTransaction() {
        return cdoClient.openTransaction();
    }

    @Override
    public void closeTransaction(CDOTransaction tr) {
        tr.close();
    }

    @Override
    public CamelModel getCamelModel(String resourceName, CDOTransaction tr) {
        EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();

        return CdoTool.getLastCamelModel(contents)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Cannot load Camel Model for resourceName=%s. " +
                "Check the value is valid and the model is available in CDO Server.", resourceName)));
    }

//    public boolean exportModel(EObject model, String filePath){
//        try{
//            final ResourceSet rs = new ResourceSetImpl();
//            rs.getPackageRegistry().put(CamelPackage.eNS_URI, CamelPackage.eINSTANCE);
//            Resource res = rs.createResource(URI.createFileURI(filePath));
//            res.getContents().add(model);
//            res.save(opts);
//            return true;
//        }
//        catch(Exception e){
//            log.error("Something went wrong while exporting model: " + model + " at path: " + filePath,e);
//        }
//        return false;
//    }

}
