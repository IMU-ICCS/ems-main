package eu.melodic.dlms.utility.camel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.stereotype.Component;

import camel.core.CamelModel;
import camel.core.CorePackage;
import camel.deployment.Communication;
import camel.deployment.DeploymentModel;
import camel.deployment.DeploymentTypeModel;
import camel.deployment.ProvidedCommunication;
import camel.deployment.RequiredCommunication;
import camel.deployment.SoftwareComponent;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to read the camel model
 */
@Component
@Slf4j
@Getter
@Setter
public class ModelAnalyzer {
	private CDOClientX cdoClient;
	private Map<String, List<String>> compConMap;

	/**
	 * Read the camel model to get a list of datasource(s) to update and/or create
	 */
	public void readModel(String modelId) {
		cdoClient = new CDOClientXImpl(Collections.singletonList(CorePackage.eINSTANCE));
		translateModel(modelId);
	}

	/**
	 * Translate the camel model to objects and store the datasources
	 */
	public void translateModel(String camelId) {
		CDOSessionX session = null;
		CDOView view = null;
		compConMap = new HashMap<>();
		try {
			// Open CDO session
			session = cdoClient.getSession();
			view = session.openView();

			CamelModel camelModel = null;
			if (StringUtils.isNotBlank(camelId)) {
				camelModel = (CamelModel) cdoClient.loadModel(camelId);
				// newly added different from DLMS web service
				EList<DeploymentModel> deploymentModels = camelModel.getDeploymentModels();
				DeploymentTypeModel deployModel = null;
				for (DeploymentModel model : deploymentModels) {
					if (model instanceof DeploymentTypeModel) {
						deployModel = (DeploymentTypeModel) model;
						break;
					}
				}
				// if deployment model is not null then check for connections
				if (deployModel != null) {
					for (Communication comm : deployModel.getCommunications()) {
						RequiredCommunication reqComm = comm.getRequiredCommunication();
						SoftwareComponent fromComponent = (SoftwareComponent) reqComm.eContainer();

						ProvidedCommunication provComm = comm.getProvidedCommunication();
						// the component is provided by the utility generator and it has location
						SoftwareComponent toComponent = (SoftwareComponent) provComm.eContainer();

						// get component that has connection only to datasource
						if (!toComponent.getManagesDataSource().isEmpty()) {
							// has only one datasource
							List<String> toComponentList = new ArrayList<>();
							if (compConMap.containsKey(fromComponent.getName())) {
								toComponentList = compConMap.get(fromComponent.getName());
								// does component list have the component
								if (!toComponentList.contains(toComponent.getName())) {
									toComponentList.add(toComponent.getName());
								}
							} else {
								toComponentList.add(toComponent.getName());
							}
							compConMap.put(fromComponent.getName(), toComponentList);
						}
					}
					log.debug("CamelModel was loaded succesfully: {} ", camelModel);
				} else
					log.debug("Deployment model is missing");
			} else {
				log.info("Camel id is missing");
			}
		} catch (Exception e) {
//			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (view != null)
				view.close();
			if (session != null)
				session.closeSession();
		}
	}

}
