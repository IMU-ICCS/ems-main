package eu.melodic.upperware.dlms.camel;

import camel.core.CamelModel;
import camel.deployment.ComponentInstance;
import camel.deployment.DeploymentInstanceModel;
import camel.deployment.DeploymentModel;
import camel.deployment.SoftwareComponentInstance;
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

/**
 * This class is designed for extracting information about connections between components from current camel model
 * stored in cdo.
 */
@Getter
@Slf4j
public
class ModelConnectionAnalyzer {
    private String agentNodeName;
    private List<CamelModel> camelModels;
    private CDOView cdoView = null; // needs to be closed before returning from any public method

    public ModelConnectionAnalyzer(String agentNodeName) {
        log.info("Loading camel models from database.");
        this.agentNodeName = agentNodeName;
    }

    /**
     * Returns a list of names of components, which have one of the ports in providedPorts as a required port
     */
    public List<String> findCommunicatingComponentsNames() {
        log.info("Looking for components communicating with " + this.agentNodeName);
        EList<SoftwareComponentInstance> deploymentInstances = this.loadDeploymentSoftwareComponentsInstances();
        List<Integer> providedPorts = deploymentInstances
                .stream()
                .filter(softwareComponentInstance -> this.agentNodeName.equals(softwareComponentInstance.getName()))
                .map(ComponentInstance::getProvidedCommunicationInstances)
                .flatMap(Collection::stream)
                .map(providedCommunicationInstance -> providedCommunicationInstance.getType().getPortNumber())
                .collect(Collectors.toList());
        List<String> names = new ArrayList<>();
        deploymentInstances
                .forEach(softwareComponentInstance -> softwareComponentInstance.getRequiredCommunicationInstances()
                        .stream()
                        .filter(req -> providedPorts.contains(req.getType().getPortNumber()))
                        .findFirst()
                        .ifPresent(requiredCommunicationInstance -> names.add(softwareComponentInstance.getName())));
        this.closeCdoView();
        return names;
    }

    /**
     * Checks if cdoView exists. Opens the view, if it doesn't.
     */
    private void openCdoView() {
        if (cdoView == null) {
            CDOClient cdoClient = new CDOClient();
            cdoClient.registerPackage(CpPackage.eINSTANCE);
            cdoClient.registerPackage(TypesPackage.eINSTANCE);
            this.cdoView = cdoClient.openView();
        }
    }

    /**
     * Checks if cdoView exists. Closes the view, if it does.
     */
    private void closeCdoView() {
        if (this.cdoView != null) {
            this.cdoView.close();
        }
    }

    /**
     * Opens the cdoview, loads and returns a list of deployment SoftwareComponentInstance instances from the cdo.
     * Note: a cdoView needs to be closed in caller method after the execution.
     */
    private EList<SoftwareComponentInstance> loadDeploymentSoftwareComponentsInstances() {
        this.openCdoView();
        try {
            CDOQuery sql = this.cdoView.createQuery("sql", "select * from repo1.camel_core_camelmodel;");
            camelModels = sql.getResult().stream()
                    .map(o -> (CamelModel) o)
                    .collect(Collectors.toList());
            CamelModel camelModel = camelModels.get(0);
            EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();
//            DeploymentTypeModel deploymentTypeModel = (DeploymentTypeModel) CdoTool.getFirstElement(deploymentModels);
            DeploymentInstanceModel deploymentInstanceModel = (DeploymentInstanceModel) CdoTool.getLastElement(deploymentModels);
            if (deploymentInstanceModel == null) {
                this.closeCdoView();
                throw new ResponseStatusException(
                        HttpStatus.BAD_REQUEST, "List of available DeploymentInstanceModels is empty.");
            }
            return deploymentInstanceModel.getSoftwareComponentInstances();
        } catch (ConnectorException ex) {
            log.error("Error by getting uploaded models. CDO is not responding", ex);
            this.closeCdoView();
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Error by getting uploaded models. CDO does not respond.");
        }
    }
}