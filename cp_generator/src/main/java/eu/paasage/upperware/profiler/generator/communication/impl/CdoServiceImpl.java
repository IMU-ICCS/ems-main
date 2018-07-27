package eu.paasage.upperware.profiler.generator.communication.impl;

import camel.core.CamelModel;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.profiler.generator.communication.CdoService;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

import static eu.passage.upperware.commons.MelodicConstants.CDO_SERVER_PATH;

@Slf4j
@Service
@AllArgsConstructor(onConstructor = @__({@Autowired}))
public class CdoServiceImpl implements CdoService {

    private CDOClientX cdoClientX;

    static {
        XMIResToResFact();
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
    public CamelModel getCamelModel(String resourceName, CDOTransaction tr) {
        EList<EObject> contents = tr.getOrCreateResource(resourceName).getContents();

        return CdoTool.getLastCamelModel(contents)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Cannot load Camel Model for resourceName=%s. " +
                "Check the value is valid and the model is available in CDO Server.", resourceName)));
    }

    @Override
    public CDOSessionX openSession() {
        return cdoClientX.getSession();
    }

    @Override
    public void saveModels(PaasageConfiguration pc, ConstraintProblem cp, CDOSessionX cdoSessionX) {
        String pcId= cp.getId();

        log.debug("CDODatabaseProxy - saveModels - Storing Models ");
        String cpPath = CDO_SERVER_PATH + pcId;

        cdoClientX.exportModel(pc, "/logs/pc_model_"+cpPath+".xmi");
        cdoClientX.exportModel(cp, "/logs/cp_model_"+cpPath+".xmi");

        cdoSessionX.storeModels(Arrays.asList(pc, cp), cpPath);
        log.debug("CDODatabaseProxy - saveModels - Models stored! ");
    }

}
