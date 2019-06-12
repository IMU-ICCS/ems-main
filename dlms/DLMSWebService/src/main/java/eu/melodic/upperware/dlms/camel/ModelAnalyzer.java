package eu.melodic.upperware.dlms.camel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
import camel.type.StringValue;
import camel.type.Value;
import camel.type.impl.StringValueImpl;
import eu.melodic.upperware.dlms.DataSource;
import eu.paasage.mddb.cdo.client.exp.CDOClientX;
import eu.paasage.mddb.cdo.client.exp.CDOClientXImpl;
import eu.paasage.mddb.cdo.client.exp.CDOSessionX;
import lombok.extern.slf4j.Slf4j;

/**
 * Class to read the camel model
 */
@Component
@Slf4j
public class ModelAnalyzer {
	private CDOClientX cdoClient;
	private List<DataSource> dataSourceList;

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
		dataSourceList = new ArrayList<DataSource>();
		try {
			// Open CDO session
			session = cdoClient.getSession();
			view = session.openView();

			CamelModel camelModel = null;
			if (camelId != null && !camelId.trim().isEmpty()) {
				CDOResource camelModelRes = view.getResource(camelId);
				EList<EObject> contents = camelModelRes.getContents();
				camelModel = (CamelModel) contents.get(contents.size() - 1);
				log.info("CamelModel was loaded succesfuly: " + camelModel);

				EList<DataModel> dataModelList = camelModel.getDataModels();
				for (DataModel dataModel : dataModelList) {
					boolean result = DataTypeModel.class.isAssignableFrom(dataModel.getClass());
					if (result) {
						DataTypeModel dataTypeModel = (DataTypeModel) dataModel;
						EList<Data> dataList = dataTypeModel.getData();

						for (Data data : dataList) {
							if (data.getDataSource() != null) {
								EList<Feature> ftList = data.getSubFeatures();
								for (Feature feature : ftList) {
									EList<Attribute> attrList = feature.getAttributes();
									DataSource dataSource = new DataSource();
									boolean added = false;

									for (Attribute attribute : attrList) {
										if (attribute.getName().equalsIgnoreCase("ufsUri")) {
											if (checkStringValueImpl(attribute)) {
												String attr = attrVal(attribute);
												dataSource.setUfsURI(attr);

												added = true;
											}
										} else if (attribute.getName().equalsIgnoreCase("accessUserId")) {
											if (checkStringValueImpl(attribute)) {
												String accessKey = attrVal(attribute);
												dataSource.setAccessKey(accessKey);
											}
										}
									}
									if (added) {
										dataSource.setName(data.getName());
										// set the mount point with the initial "melodic" for all
//										dataSource.setMountPoint("/melodic/" + data.getName());
										dataSource.setMountPoint(data.getName());
										dataSourceList.add(dataSource);
										log.debug("DataSource was added: " + camelModel);
									}
								}
							}
						}
					}
				}
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

	private boolean checkStringValueImpl(Attribute attribute) {
		Value val = attribute.getValue();
		return (val.getClass() == StringValueImpl.class);
	}

	private String attrVal(Attribute attribute) {
		Value val = attribute.getValue();
		// read the camel model and set the properties
		StringValue strVal = (StringValue) val;
		return strVal.getValue();
	}

	public List<DataSource> getDataSourceList() {
		return dataSourceList;
	}

	public void setDataSourceList(List<DataSource> dataSourceList) {
		this.dataSourceList = dataSourceList;
	}

}
