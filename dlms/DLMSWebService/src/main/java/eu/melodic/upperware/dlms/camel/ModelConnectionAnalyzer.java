package eu.melodic.upperware.dlms.camel;

import camel.core.CamelModel;
import camel.core.NamedElement;
import camel.deployment.*;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.passage.upperware.commons.model.tools.CdoTool;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.net4j.connector.ConnectorException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Slf4j
public
class ModelConnectionAnalyzer {
    private String agentNodeName;
    private List<CamelModel> camelModels;
    private EList<SoftwareComponentInstance> deploymentSoftwareComponentsInstances;
    private DeploymentTypeModel deploymentTypeModel;
    private DeploymentInstanceModel deploymentInstanceModel;

    public ModelConnectionAnalyzer(String agentNodeName) {
        log.info("Loading camel models from database.");
        this.agentNodeName = agentNodeName;
        camelModels = this.getCamelModels();
        CamelModel camelModel = camelModels.get(0);
        EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();
        this.deploymentTypeModel = (DeploymentTypeModel) CdoTool.getFirstElement(deploymentModels);
        this.deploymentInstanceModel = (DeploymentInstanceModel) CdoTool.getLastElement(deploymentModels);
        assert deploymentInstanceModel != null : "deploymentInstanceModel is null";
        this.deploymentSoftwareComponentsInstances = deploymentInstanceModel.getSoftwareComponentInstances();
    }

    // Find component with the same id as the id from cloudiator and save its provided ports.
    private List<Integer> findProvidedPorts() {
        // A list of component's provided ports - linked components have it as a required port:
        List<Integer> providedPorts = new ArrayList<>();
        for (SoftwareComponentInstance comp : deploymentSoftwareComponentsInstances) {
            if (comp.getName().equals(this.agentNodeName)) {
                for (ProvidedCommunicationInstance prov : comp.getProvidedCommunicationInstances()) {
                    providedPorts.add(prov.getType().getPortNumber());
                }
            }
        }
//        return providedPorts;

        return deploymentSoftwareComponentsInstances.stream()
                .filter(softwareComponentInstance -> this.agentNodeName.equals(softwareComponentInstance.getName()))
                .map(ComponentInstance::getProvidedCommunicationInstances)
                .flatMap(Collection::stream)
                .map(providedCommunicationInstance -> providedCommunicationInstance.getType().getPortNumber())
                .collect(Collectors.toList());
    }

    // Create a list of names of components, which have one of the ports in providedPorts as a required port
    public List<String> findCommunicatingComponentsNames() {
        log.info("Looking for components communicating with " + this.agentNodeName);
        List<Integer> providedPorts = this.findProvidedPorts();
        List<String> names = new ArrayList<>();
        for (SoftwareComponentInstance comp : deploymentSoftwareComponentsInstances) {
            for (RequiredCommunicationInstance req : comp.getRequiredCommunicationInstances()) {
                if (providedPorts.contains(req.getType().getPortNumber())) {
                    names.add(comp.getName());
                }
            }
        }
//        return names;
        return deploymentSoftwareComponentsInstances.stream()
                .flatMap(comp -> comp.getRequiredCommunicationInstances().stream())
                .filter(req -> providedPorts.contains(req.getType().getPortNumber()))
                .map(NamedElement::getName)
                .collect(Collectors.toCollection(() -> names));
    }

    private List<CamelModel> getCamelModels() {
        List<CamelModel> result = new ArrayList<>();
        CDOView cdoView = null;
        try {
            CDOClient cdoClient = new CDOClient();
            cdoClient.registerPackage(CpPackage.eINSTANCE);
            cdoClient.registerPackage(TypesPackage.eINSTANCE);
            cdoView = cdoClient.openView();
            CDOQuery sql = cdoView.createQuery("sql", "select * from repo1.camel_core_camelmodel;");

            result = sql.getResult().stream()
                    .map(o -> (CamelModel) o)
                    .collect(Collectors.toList());
        } catch (ConnectorException ex) {
            log.error("Error by getting uploaded models. CDO is not responding", ex);
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error by getting uploaded models. CDO does not respond.");
        } catch (RuntimeException ex) {
            log.debug("List of available models is empty:", ex);
        } //finally {
//				if (cdoView != null) {
//					cdoView.close();
//				}
//			}
        return result;
    }
}