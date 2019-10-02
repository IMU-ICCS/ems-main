package eu.melodic.upperware.dlms.camel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.StringUtils;
import org.eclipse.emf.cdo.eresource.CDOResource;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.springframework.stereotype.Component;

import camel.core.Attribute;
import camel.core.CamelModel;
import camel.core.CorePackage;
import camel.core.Feature;
import camel.data.Data;
import camel.data.DataModel;
import camel.data.DataTypeModel;
import camel.deployment.DeploymentModel;
import camel.deployment.DeploymentTypeModel;
import camel.deployment.SoftwareComponent;
import camel.type.BooleanValue;
import camel.type.StringValue;
import camel.type.Value;
import camel.type.impl.BooleanValueImpl;
import camel.type.impl.StringValueImpl;
import eu.melodic.upperware.dlms.AppCompDataSource;
import eu.melodic.upperware.dlms.DataSource;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to read the camel model
 */
@Component
@Getter
@Slf4j
public class ModelAnalyzer {
	private CDOClientX cdoClient;
	private List<DataSource> dataSourceList;

	// list of component id along with their datasource
	private List<AppCompDataSource> appCompDSList;
	
	/**
	 * Read the camel model to get a list of datasource(s) to update and/or create
	 */
	public void readModel(String modelId) {
		initializeVar();
		translateModel(modelId);
	}

	private void initializeVar() {
		appCompDSList = new ArrayList<>();
		cdoClient = new CDOClientXImpl(Collections.singletonList(CorePackage.eINSTANCE));
	}

	/**
	 * Translate the camel model to objects and store the datasources
	 */
	private void translateModel(String camelId) {
		CDOSessionX session = null;
		CDOView view = null;
		dataSourceList = new ArrayList<>();
		try {
			// Open CDO session
			session = cdoClient.getSession();
			view = session.openView();

			CamelModel camelModel = null;
			if (camelId != null && !camelId.trim().isEmpty()) {
				CDOResource camelModelRes = view.getResource(camelId);
				EList<EObject> contents = camelModelRes.getContents();
				camelModel = (CamelModel) contents.get(contents.size() - 1);
				log.info("CamelModel was loaded succesfuly: {}", camelModel);

				EList<DeploymentModel> deploymentModelList = camelModel.getDeploymentModels();
				Map<String, String> componentDataSourceMap = addComponentDataSource(deploymentModelList);

				EList<DataModel> dataModelList = camelModel.getDataModels();
				for (DataModel dataModel : dataModelList) {
					boolean result = DataTypeModel.class.isAssignableFrom(dataModel.getClass());
					if (result) {
						DataTypeModel dataTypeModel = (DataTypeModel) dataModel;
						EList<Data> dataList = dataTypeModel.getData();

						for (Data data : dataList) {
							if (data.getDataSource() != null) {
								EList<Feature> ftList = data.getSubFeatures();
								DataSource dataSource = new DataSource();
								for (Feature feature : ftList) {
									EList<Attribute> attrList = feature.getAttributes();

									for (Attribute attribute : attrList) {
										// check attributes for the datasource
										checkUfsUri(attribute, dataSource);
										checkAccess(attribute, dataSource);
										checkReadOnly(attribute, dataSource);
										checkLocalMountPoint(attribute, dataSource);
									}
									if (StringUtils.isNotBlank(dataSource.getUfsURI())) {
										dataSource.setName(data.getDataSource().getName());
										// set the mount point with the initial "melodic" for all
										// not needed based on discussions in June
//										dataSource.setMountPoint("/melodic/" + data.getName());
										dataSource.setMountPoint(data.getName());
										validLocalMountPoint(dataSource);
						
										dataSourceList.add(dataSource);
										log.debug("DataSource was added: {}", camelModel);
									}
								}
							}
						}
						break;
					}
				}
				getAppCompDS(componentDataSourceMap);
			} else {
				log.info("Camel id is missing");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} finally {
			if (view != null)
				view.close();
			if (session != null)
				session.closeSession();
		}
	}
	
	
	/**
	 * link between datasource name and the name of component linked to it
	 */
	private Map<String, String> addComponentDataSource(EList<DeploymentModel> deploymentModelList) {
		Map<String, String> componentDataSourceMap = new HashMap<>();
		for (DeploymentModel deployModel : deploymentModelList) {
			boolean result = DeploymentTypeModel.class.isAssignableFrom(deployModel.getClass());
			if (result) {
				DeploymentTypeModel deployTypeModel = (DeploymentTypeModel) deployModel;
				EList<SoftwareComponent> softCompList = deployTypeModel.getSoftwareComponents();
				for (SoftwareComponent softComp : softCompList) {
					EList<Data> dataList = softComp.getConsumesData();

					for (Data data : dataList) {
						if (data.getDataSource() != null) {
							// what kind of datasources will be mounted
							// needs to be changed to be read from the ufsuri later or type if it becomes available later...
							String dataSourceName = data.getDataSource().getName();
							if (dataSourceName.contains("S3")
									|| dataSourceName.contains("HDFS")) {
								componentDataSourceMap.put(softComp.getName(), dataSourceName);
							}
						}
					}
				}
				break;
			}
		}
		return componentDataSourceMap;
	}
	
	private void getAppCompDS(Map<String, String> componentDataSourceMap) {
		for (Entry<String, String> entry : componentDataSourceMap.entrySet()) {
			AppCompDataSource appCompDS = new AppCompDataSource(entry.getKey(), entry.getValue());
			this.appCompDSList.add(appCompDS);
		}
	}

	private boolean checkStringValueImpl(Attribute attribute) {
		return attribute.getValue() instanceof StringValueImpl;
	}

	private boolean checkBooleanValueImpl(Attribute attribute) {
		return attribute.getValue() instanceof BooleanValueImpl;
	}

	private String attrVal(Attribute attribute) {
		Value val = attribute.getValue();
		// read the camel model and get the properties
		StringValue strVal = (StringValue) val;
		return strVal.getValue();
	}

	private boolean attrValBool(Attribute attribute) {
		Value val = attribute.getValue();
		// read the camel model and get the properties
		BooleanValue boolVal = (BooleanValue) val;
		return boolVal.isValue();
	}

	private void checkUfsUri(Attribute attribute, DataSource dataSource) {
		if ("ufsUri".equalsIgnoreCase(attribute.getName())) {
			if (checkStringValueImpl(attribute)) {
				String ufsURI = attrVal(attribute);
				dataSource.setUfsURI(ufsURI);
			}
		}
	}

	private void checkAccess(Attribute attribute, DataSource dataSource) {
		if ("accessUserId".equalsIgnoreCase(attribute.getName())) {
			if (checkStringValueImpl(attribute)) {
				String accessKey = attrVal(attribute);
				dataSource.setAccessKey(accessKey);
			}
		}
	}

	private void checkReadOnly(Attribute attribute, DataSource dataSource) {
		if ("isReadOnly".equalsIgnoreCase(attribute.getName())) {
			if (checkBooleanValueImpl(attribute)) {
				boolean isReadOnly = attrValBool(attribute);
				dataSource.setReadOnly(isReadOnly);
			}
		}
	}
	
	private void checkLocalMountPoint(Attribute attribute, DataSource dataSource) {
		if ("localMountPoint".equalsIgnoreCase(attribute.getName())) {
			if (checkStringValueImpl(attribute)) {
				String localMountPoint = attrVal(attribute);
				dataSource.setLocalMountPont(localMountPoint);
			}
		}
	}
	
	/**
	 * Change local mount point as same mount point if not valid
	 */
	private void validLocalMountPoint(DataSource dataSource) {
		if (StringUtils.isBlank(dataSource.getLocalMountPont())) {
			dataSource.setLocalMountPont(dataSource.getMountPoint());
		}
			
	}

}
