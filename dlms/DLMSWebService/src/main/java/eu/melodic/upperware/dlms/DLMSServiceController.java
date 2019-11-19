/*
 * Melodic EU Project
 * DLMS WebService
 * DMS Service Controller
 * @author: ferox
 */

package eu.melodic.upperware.dlms;

import camel.core.CamelModel;
import camel.deployment.*;
import eu.melodic.models.interfaces.dlms.*;
import eu.melodic.models.interfaces.dlms.Configuration;
import eu.melodic.upperware.dlms.component.ComponentId;
import eu.melodic.upperware.dlms.component.SendToDlmsAgent;
//import eu.melodic.upperware.guibackend.service.cdo.CdoService;
//import eu.paasage.mddb.cdo.client.CDOClient;
//import eu.paasage.mddb.cdo.client.exp.CDOClientX;
//import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.CDOClient;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.passage.upperware.commons.model.tools.CdoTool;
import io.github.cloudiator.rest.ApiException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.emf.cdo.view.CDOQuery;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.net4j.connector.ConnectorException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Webservice controller for the DLMS service.
 */
@RestController
@Slf4j
@RequiredArgsConstructor
public class DLMSServiceController {

	private final DLMSCoordinator dlmsCoordinator;

	private final DLMSService dlmsService;
	private final ComponentId comp;

	/**
	 * Returns all datasources in the database.
	 */
	@GetMapping(value = "/ds")
	public List<DataSource> getDataSources() {
		return dlmsService.getAllDataSources();
	}

	/**
	 * Returns one datasource matching the given id.
	 */
	@GetMapping(value = "/ds/id/{id}")
	public DataSource getDataSource(@PathVariable("id") Long id) {
		return dlmsService.getDataSourceById(id);
	}

	/**
	 * Returns one datasource matching the given name.
	 */
	@GetMapping(value = "/ds/name/{name}")
	public DataSource getDataSource(@PathVariable("name") String name) {
		return dlmsService.getDataSourceByName(name);
	}

	/**
	 * Unmounts and deletes the datasource matching the given id.
	 */
	@DeleteMapping("/ds/id/{id}")
	public void deleteDataSource(@PathVariable Long id) {
		dlmsService.deleteById(id);
	}

	/**
	 * Unmounts and deletes the datasource matching the given name.
	 */
	@DeleteMapping("/ds/name/{name}")
	public void deleteDataSource(@PathVariable String name) {
		dlmsService.deleteByName(name);
	}

	/**
	 * Get a list of application component and linked data sources
	 */
	@GetMapping("/ac")
	public List<AcDsMountPoint> getAppCompDataSource() {
		return dlmsService.getAllAcDsMp();
	}

	/**
	 * Returns one data source and mount point linked with the component name.
	 */
	@GetMapping(value = "/ac/{name}")
	public AcDsMountPoint getAppCompDataSource(@PathVariable("name") String name) {
		return dlmsService.getAcDsMpByName(name);
	}

	/**
	 * Returns command and the component name.
	 */
	@GetMapping(value = "/getDlmsAgentParams/{ip}")
	public SendToDlmsAgent getDlmsAgentParams(@PathVariable("ip") String ip) throws ApiException {
		log.info("Invoking getDlmsAgentParams with IP: {}", ip);

		final Optional<String> componentId = comp.findComponentId(ip);
		if (componentId.isPresent()) {
			final String cmpName = componentId.get();
			final SendToDlmsAgent toSend = dlmsService.getDlmsAgentParams(cmpName);
			log.info("Sending getAlluxioCmd response for IP: {}. Result: [cmpId:{}, metric:{}]", ip, cmpName,
					toSend.getMetricName());
			return toSend;
		} else {
			log.info("There is no data source for componentId with IP: {}.", ip);
			return new SendToDlmsAgent("", "", null, null);
		}
	}

	/**
	 * Adds/updates the datasource from the camel model to the database and mounts
	 * the mount point
	 */
	@PostMapping("/dataModel")
	public void addUpdateDataSources(@Valid @RequestBody DataModelRequest dataModelRequest) {
		log.info("The name of the camel model is {}", dataModelRequest.getApplicationId());
		dlmsCoordinator.doAddUpdateDataSourcesWork(
				dataModelRequest.getApplicationId(),
				dataModelRequest.getNotificationURI(),
				dataModelRequest.getWatermark().getUuid());
	}

	/**
	 * Updates the datasource with the given id with the data provided in the
	 * datasource object.
	 */
	@PutMapping("/ds/id/{id}")
	public ResponseEntity<Object> updateDataSource(@RequestBody DataSource ds, @PathVariable Long id) {
		dlmsService.updateDataSource(ds, id);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Updates the datasource with the given name with the data provided in the
	 * datasource object.
	 */
	@PutMapping("/ds/name/{name}")
	public ResponseEntity<Object> updateDataSource(@RequestBody DataSource ds, @PathVariable String name) {
		dlmsService.updateDataSource(ds, name);
		return ResponseEntity.noContent().build();
	}

	/**
	 * Migrates (moves) a file from pathFrom to pathTo in the MigrationData object.
	 */
	@PostMapping("/migrate/file")
	public void migrateFile(@RequestBody MigrationData migrationData) {
		dlmsService.migrateFile(migrationData.getPathFrom(), migrationData.getPathTo());
	}

	/**
	 * Migrates (moves) a directory from pathFrom to pathTo in the MigrationData
	 * object.
	 */
	@PostMapping("/migrate/dir")
	public void migrateDirectory(@RequestBody MigrationData migrationData) {
		dlmsService.migrateDirectory(migrationData.getPathFrom(), migrationData.getPathTo());
	}

	/**
	 * Migrates (moves) the complete datasource (and all data) with the given id in
	 * the MigrationData object to the location in pathTo (in the MigrationData
	 * object).
	 */
	@PostMapping("/migrate/ds")
	public void migrateDatasource(@RequestBody MigrationData migrationData) {
		dlmsService.migrateDatasource(migrationData.getId(), migrationData.getPathTo());
	}


	@Getter
	static
	class ModelExplorer {
		private String agentNodeName;
		private List<CamelModel> camelModels;
		private EList<SoftwareComponentInstance> deploymentSoftwareComponentsInstances;
		private DeploymentTypeModel deploymentTypeModel;
		private DeploymentInstanceModel deploymentInstanceModel;

		ModelExplorer(String agentNodeName) {
			log.info("Loading camel models from database.");
			this.agentNodeName = agentNodeName;
			camelModels = this.getCamelModels();
			CamelModel camelModel = camelModels.get(0);
			EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();
			assert deploymentModels.size() == 1;
			this.deploymentTypeModel = (DeploymentTypeModel) CdoTool.getFirstElement(deploymentModels);
			this.deploymentInstanceModel = (DeploymentInstanceModel) CdoTool.getLastElement(deploymentModels);
			assert deploymentInstanceModel != null;
			this.deploymentSoftwareComponentsInstances = deploymentInstanceModel.getSoftwareComponentInstances();
		}

		// Find component with the same id as the id from cloudiator and save its provided port
		private List<Integer> findProvidedPorts() {
			// A list of component's provided ports - linked components have it as a required port:
			List<Integer> providedPorts = new ArrayList<>();
			for (SoftwareComponentInstance comp : deploymentSoftwareComponentsInstances) {
				if (comp.getName().equals(this.agentNodeName)) {
					for (ProvidedCommunicationInstance prov: comp.getProvidedCommunicationInstances()) {
						providedPorts.add(prov.getType().getPortNumber());
					}
				}
			}
			return providedPorts;
		}

		// Create a list of names of components, which have one of the ports in providedPorts as a required port
		List<String> findCommunicatingComponentsNames() {
			log.info("Looking for components communicating with " + this.agentNodeName);
			List<Integer> providedPorts = this.findProvidedPorts();
			List<String> names = new ArrayList<>();
			for (SoftwareComponentInstance comp : deploymentSoftwareComponentsInstances) {
				for (RequiredCommunicationInstance req : comp.getRequiredCommunicationInstances()) {
					if(providedPorts.contains(req.getType().getPortNumber())) {
						names.add(comp.getName());
					}
				}
			}
			return names;
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

	/**
	 * Returns configuration for the given IP as a tuple:
	 */
	@GetMapping(value = "/getConfiguration/{ip}")
	public ConfigurationResponse getConfiguration(@PathVariable("ip") String ip) throws ApiException {
		log.info("Invoking getConfiguration with IP: {}", ip);

		ConfigurationResponse configResp = new ConfigurationResponseImpl();

		// Get agent node name from cloudiator
		final Optional<String> agentNodeNameOptional = comp.findNodeName(ip);
		final String agentNodeName;
		if (!agentNodeNameOptional.isPresent()) {
			log.error("Cloudiator could not find node for a given agent IP: " + ip);
			return configResp;
		}
		agentNodeName = agentNodeNameOptional.get();
		log.info("Cloudiator found this agent node name for given IP: " + agentNodeName);

		// Get agent node location from cloudiator
		final Optional<String> agentNodeLocationOptional = comp.findNodeLocation(agentNodeName);

		// Object responsible for analysing the connections between components
		ModelExplorer explorer = new ModelExplorer(agentNodeName);

		// Create a list of names of software components, which have one of the ports in requiredPorts as provided port
		List<String> names = explorer.findCommunicatingComponentsNames();
		log.info("Found components providing communication with requested component:" + names);

		// find IP's of those components and add them to response
		List<Configuration> configs = new ArrayList<>();
		for (String name : names) {
			LatencyConfiguration latConf = new LatencyConfigurationImpl();
			latConf.setComponentName(name);

			final Optional<String> nodeIpOptional = comp.findNodeIp(name);
			if (!nodeIpOptional.isPresent()) {
				log.error("Cloudiator could not find node ip for a given component: " + name);
				latConf.setComponentIP("ERROR");
			} else
				latConf.setComponentIP(nodeIpOptional.get());

			final Optional<String> nodeLocationOptional = comp.findNodeLocation(name);
			if (!nodeLocationOptional.isPresent()) {
				log.error("Cloudiator could not find node location for a given component: " + name);
				latConf.setComponentRegion("ERROR");
			} else
				latConf.setComponentRegion(nodeLocationOptional.get());

			if (!agentNodeLocationOptional.isPresent()) {
				log.error("Cloudiator could not find node location for a given IP: " + ip);
				latConf.setAgentRegion("ERROR");
			} else {
				latConf.setAgentRegion(agentNodeLocationOptional.get());
			}

			configs.add(new Configuration(latConf));
		}
		configResp.setConfigurations(configs);

		return configResp;
	}
}