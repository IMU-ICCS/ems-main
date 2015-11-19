/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.emf.cdo.eresource.CDOResourceFolder;
import org.eclipse.emf.cdo.eresource.CDOResourceNode;
import org.eclipse.emf.cdo.transaction.CDOTransaction;
import org.eclipse.emf.cdo.view.CDOView;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;

import eu.paasage.camel.CamelModel;
import eu.paasage.camel.deployment.DeploymentModel;
import eu.paasage.camel.deployment.InternalComponent;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.impl.OrganisationModelImpl;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.provider.impl.ProviderModelImpl;
import eu.paasage.camel.type.StringsValue;
import eu.paasage.camel.type.impl.StringsValueImpl;
import eu.paasage.upperware.cp.cloner.CDOClientExtended;
import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.ProviderDimension;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.NumericExpression;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.VariableValue;
import eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.profiler.rp.util.PropertiesReader;
import eu.paasage.upperware.profiler.rp.util.RPOutput;
import eu.paasage.upperware.profiler.rp.util.Utilities;
import eu.paasage.upperware.profiler.rp.zeromq.RuleProcessorService;

public class RuleProcessor {
	/** Log4j logger instance. */
	private static Logger log = Logger.getLogger(RuleProcessor.class);
	private boolean validationResult_ = true;

	private CDOClientExtended cdoClient_;
	private CPCloner cpCloner_;
	private List<EObject> cloneList_;

	private String resId_; // CDO's resource ID
	private String cloneResId_; // the clone version of the original resource ID

	public enum SOLUTION_STATUS {
		ERROR,
		NO_CHANGE_REQUIRED,
		NO_SOLUTION_AVAILABLE,
		MODEL_CHANGED
	}
	
	public RuleProcessor() {
		Properties paasageProperties = PropertiesReader.loadPropertyFile();

		PropertyConfigurator.configure(paasageProperties);
		log.debug("RP_TEMP_DIR = "
				+ paasageProperties.getProperty("RP_TEMP_DIR"));
		System.out.println("RP_TEMP_DIR = "
				+ paasageProperties.getProperty("RP_TEMP_DIR"));
		log.debug("PROFILER_QUEUE_NAME = "
				+ paasageProperties.getProperty("PROFILER_QUEUE_NAME"));
		System.out.println("PROFILER_QUEUE_NAME = "
				+ paasageProperties.getProperty("PROFILER_QUEUE_NAME"));

		cdoClient_ = null;
		cpCloner_ = null;
		cloneList_ = null;
		cloneResId_ = null;
		resId_ = null;
		validationResult_ = true;
	}

	public String getCloneResId() {
		return cloneResId_;
	}

	public boolean getValidationResult() {
		return validationResult_;
	}

	public void setValidationResult(boolean result) {
		validationResult_ = result;
	}

	public CDOClientExtended getCDOClient() {
		return cdoClient_;
	}

	public boolean openCDOSession(String resId) {
		resId_ = resId;
		return this.openCDOSession();
	}

	public boolean openCDOSession() {
		if (cdoClient_ != null) {
			return false;
		}

		cpCloner_ = new CPCloner();
		cdoClient_ = CPCloner.createCDOClient();
		log.debug("RuleProcessor.openCDOSession(): Opening a new CDO session.");
		return cdoClient_.existResource(resId_);
	}

	public void commitCloneModelToCDO() {
		try {
			// deleteResource(resId_);
			if (cloneResId_ != null) {
				cdoClient_.storeModels(cloneList_, cloneResId_); // copy the
																	// clone
																	// model to
																	// CDO
																	// server
			} else {
				System.out
						.println("commitAndCloseCDOSession(): Warning - empty resource Id for the cloned model");
			}
		}
		/*
		 * // TODO: redundant? catch(CommitException ce) {
		 * System.out.println("\n*** Storing the clone mode fails\n");
		 * System.out.println(ce.toString()); }
		 */
		catch (Exception e) {
			System.out
					.println("commitAndCloseCDOSession(): Commit operation fails\n");
			System.out.println(e.toString());
		}
	}

	public void commitAndCloseCDOSession() {
		commitCloneModelToCDO();
		closeCDOSession();
	}

	public void closeCDOSession() {
		cdoClient_.closeSession();
		cdoClient_ = null;
	}

	/*
	 * TODO: not working //Unable to delete existing model due to not-null
	 * property! //org.eclipse.emf.cdo.util.CommitException: Rollback in
	 * HibernateStore: org.hibernate.PropertyValueException: not-null property
	 * references a null or transient value : Variable.domain public void
	 * deleteResource(String resId) { CDOTransaction cdoTrans =
	 * cdoClient_.openTransaction(); EList<EObject> objList =
	 * cdoTrans.getResource(resId).getContents();
	 * 
	 * for (int n = objList.size()-1; n >= 0; n--) { EObject cdoObj =
	 * objList.get(n); EcoreUtil.delete(cdoObj, true); }
	 * 
	 * try { cdoTrans.commit(); cdoTrans.close(); } catch(Exception e) {
	 * System.out.println("deleteResource(): Commit operation fails\n");
	 * System.out.println(e.toString()); }
	 * 
	 * }
	 */

	/**
	 * 
	 * @param appId
	 *            application identifier
	 * @param cdoView
	 *            CDO view
	 * @return data model
	 */
	public ModelData getModelDataFromCDO(String appId, CDOView cdoView) {
		// TODO: redundant?
		ModelData data = null;

		try {
			EList<EObject> objList = cdoView.getResource(appId).getContents();
			EObject obj = null;
			data = new ModelData();
			for (int i = 0; i < objList.size(); i++) {
				obj = objList.get(i);
				if (obj instanceof eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl) {
					PaasageConfiguration pc = (PaasageConfiguration) obj;
					data.setPaasageConfiguration(pc);
				} else if (obj instanceof eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl) {
					ConstraintProblem cpModel = (ConstraintProblem) obj;
					data.setConstraintProblem(cpModel);
				}
			}

			data.setAppId(appId);
		} catch (Exception e) {
			log.error("RuleProcessor.processModelDataFromCDO(): Unknown resource with ID = "
					+ appId);
			log.error(e.toString());
		}

		return data;
	}

	/********************/

	// return: a tuple of <resouce name, type>, e.g.
	// <Amazon-Ireland-1415008389854, Public>,
	// <Sintef-Nova-Norway-1415008389864, Private>, etc
	/**
	 * 
	 * @param appId
	 *            application identifier
	 * @return deployment model represented through hashtable
	 */
	public Hashtable<String, String> getProviderDeploymentModel(String appId) {
		// feature models to determine whether the provider is private or public
		int index = appId.indexOf('/');
		StringBuilder sb = new StringBuilder(appId);
		sb.insert(index + 1, "fms/");
		String resId = sb.toString();

		log.debug("\n-----------------------------------------------");
		log.debug("Location of feature models (fms) = " + resId);
		log.debug("\n");

		// need to use CDOTrans to get the nodes inside a folder -- can't do
		// that in CDOView
		CDOTransaction cdoTrans = cdoClient_.openTransaction();
		CDOResourceFolder resFolder = cdoTrans.getOrCreateResourceFolder(resId);

		EList<CDOResourceNode> nodeList = resFolder.getNodes();
		CDOResourceNode obj = null;
		Hashtable<String, String> resProvider = new Hashtable<String, String>();

		// get inside each node
		for (int i = 0; i < nodeList.size(); i++) {
			obj = nodeList.get(i);
			String resName = obj.getName();
			String nodeId = obj.getPath();

			log.debug("CDOResourceNode = " + obj);
			log.debug("CDOResourceNode.getPath() = " + nodeId); // the resource
																// ID in CDO
																// server e.g.
																// /upperware-models/fms/1437049278990/Flexiant-Scotland-1437049280380
			log.debug("CDOResourceNode.getName() = " + resName); // e.g.
																	// Flexiant-Scotland-1437049280380

			getDeploymentModel(nodeId, resName, cdoTrans, resProvider);
			log.debug("\n");
		}

		cdoTrans.close();

		System.out.println("\nList of available cloud providers:");
		for (String key : resProvider.keySet()) {
			System.out.println("* " + key + " - type: " + resProvider.get(key));
		}

		return resProvider;
	}

	// get the deployment model of each resource provider, i.e. either public or
	// private
	// then store the result in the Hashtable
	private void getDeploymentModel(String resId, String resName,
			CDOTransaction cdoTrans, Hashtable<String, String> resProvider) {
		try {
			EList<EObject> objList = cdoTrans.getResource(resId).getContents();
			EObject obj = null;
			for (int i = 0; i < objList.size(); i++) {
				obj = objList.get(i);
				if (obj instanceof eu.paasage.camel.provider.impl.ProviderModelImpl) {
					// camel/src/eu/paasage/camel/provider/impl/ProviderModelImpl.java
					// camel/src/eu/paasage/camel/provider/impl/FeatureImpl.java
					ProviderModelImpl pmObj = (ProviderModelImpl) obj;
					Feature f = pmObj.getRootFeature();

					EList<Attribute> attrList = f.getAttributes();
					for (int n = 0; n < attrList.size(); n++) {
						// camel/src/eu/paasage/camel/provider/impl/AttributeImpl.java
						Attribute attr = attrList.get(n);
						log.debug("-- attr.getName() = " + attr.getName()); // e.g
																			// deploymentModel

						if (attr.getName().startsWith("deploymentModel") == true) {
							// camel/src/eu/paasage/camel/type/impl/ValueImpl.java
							StringsValueImpl value = (StringsValueImpl) attr
									.getValue();
							log.debug("-- attr.getName().getValue().getValue() = "
									+ value.getValue()); // e.g Public

							resProvider.put(resName, value.getValue()); // Flexiant-Scotland-1437049280380,
																		// Public
							break;
						}
					} // end for
				}

			}
		} catch (Exception e) {
			log.error("RuleProcessor.getDeploymentModel(): Unknown resource with ID = "
					+ resId);
			log.error(e.toString());
		}
	}

	// clone the model from the CDO server
	public boolean cloneModel(String resId) {
		if (resId == null || cloneList_ != null) {
			return false;
		}

		cloneList_ = cpCloner_.cloneModel(resId);
		return true;
	}

	public List<EObject> getCloneModel() {
		if (cloneList_ == null && resId_ != null) {
			this.cloneModel(resId_);
		}
		return cloneList_;
	}

	public SOLUTION_STATUS removeProvider(String resId, String camelModel, String providerType) {
		if (providerType == null) {
			return SOLUTION_STATUS.ERROR;
		}

		List<EObject> objList = this.getCloneModel();
		String[] strArray = resId.split("/"); // splitting upperware-models/1414751126815
		String newPaaSageConfigId = strArray[1] + "v2"; // take the latter part: 1414751126815
		cloneResId_ = resId + "v2";

		EObject obj = null;
		PaasageConfiguration pc = null;
		HashMap<String, Boolean> delTable = new HashMap<String, Boolean>();
		ArrayList<String> vmRemoveList = new ArrayList<String>();
		Set<String> willBeRemoved = new HashSet<String>();
		
		// find out the exact VM profile ID for a particular resource provider
		// e.g. for Amazon, it has VM profile with ID = "SL", in the xmi file:
		// <vmProfiles cloudMLId="SL">
		for (int i = 0; i < objList.size(); i++) {
			obj = objList.get(i);
			if (obj instanceof eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl) {
				pc = (PaasageConfiguration) obj;
				pc.setId(newPaaSageConfigId); // set a new resource ID for this clone version

				Iterator<VirtualMachineProfile> it = pc.getVmProfiles().iterator();
                while (it.hasNext()) {
                    VirtualMachineProfile vmProfile = it.next();
                    String vmID = vmProfile.getCloudMLId();
                    for (ProviderDimension provider : vmProfile.getProviderDimension()) {
                    	String pId = provider.getProvider().getId();
                    	if (isProviderPublic(strArray[1], pId)) {
                    		if (providerType.equals("public")) {
                    			willBeRemoved.add(pId);
                    			vmRemoveList.add(vmID);
                    			delTable.put(pId, null);
                    			delTable.put(vmID, null);
                    		}
                    	} else if (providerType.equals("private")) {
                    		willBeRemoved.add(pId);
                    		vmRemoveList.add(vmID);
                    		delTable.put(pId, null);
                    		delTable.put(vmID, null);
                    	}
                    }
                }
			}
		}

		System.out.println("\n> List of cloud providers to be REMOVED since they are " + providerType + ":");
		if (willBeRemoved.isEmpty()) {
			System.out.println("- None. No " + providerType
					+ " cloud providers were selected by the CP generator for deployment.\n");
			return SOLUTION_STATUS.NO_CHANGE_REQUIRED;
		} else {
			for (String provider : willBeRemoved) {
				System.out.println("  - " + provider);
			}
		}

		return removeModelFromCDO(resId, camelModel, delTable, vmRemoveList, objList);
	}
	
	private boolean isProviderPublic(String cpModelId, String cloudProviderId) {
		IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
        CamelModel model = proxy.getCamelModel("upperware-models/fms/" + cpModelId + "/" + cloudProviderId);
        for (ProviderModel pm : model.getProviderModels()) {
        	Feature f = pm.getRootFeature();
        	for (Attribute a : f.getAttributes()) {
        		if (a.getName().equals("DeploymentModel")) {
        			StringsValue sv = (StringsValue) a.getValue();
        			if (sv.getValue().equalsIgnoreCase("public")) {
            			return true;
            		}
        		}
        	}
        }

        return false;
	}

	private SOLUTION_STATUS removeModelFromCDO(String resId, String camelModel,
			HashMap<String, Boolean> delTable, ArrayList<String> vmRemoveList,
			List<EObject> objList) {
		EObject obj = null;
		System.out
				.println("\n\n***** Removing variables from PaaSage Configuration *****\n");
		for (int i = 0; i < objList.size(); i++) {
			obj = objList.get(i);
			if (obj instanceof eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl) {
				PaasageConfiguration pc = (PaasageConfiguration) obj;
				removeConfigurationFromCDO(pc, vmRemoveList, delTable);
			} else if (obj instanceof eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl) {
				ConstraintProblem cpModel = (ConstraintProblem) obj;
				removeConstraintFromCDO(cpModel, vmRemoveList, delTable);
			}
		}
		
		Set<String> requiredComponents = new HashSet<String>();
		IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
		CamelModel cModel = proxy.getCamelModel(camelModel);
		for (DeploymentModel dm : cModel.getDeploymentModels()) {
			for (InternalComponent ic : dm.getInternalComponents()) {
				requiredComponents.add(ic.getName());
			}
		}
		
		for (EObject eObj : objList) {
			if (eObj instanceof eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl) {
				PaasageConfiguration pc = (PaasageConfiguration) eObj;
				for (PaaSageVariable ps : pc.getVariables()) {
					ApplicationComponent ac = ps.getRelatedComponent();
					if (ac != null) {
						String componentName = ac.getCloudMLId();
						requiredComponents.remove(componentName);
					}
					if (requiredComponents.isEmpty()) {
						break;
					}
				}
			}
		}
		
		if (!requiredComponents.isEmpty()) {
			System.out.println("\nRESULT: No solution available.");
			System.out.println("> No suitable cloud provider found for the following components:");
			for (String cloudMLId : requiredComponents) {
				System.out.println("  - " + cloudMLId);
			}
			System.out.println("> Please adapt the requirements in your CAMEL model.");
			return SOLUTION_STATUS.NO_SOLUTION_AVAILABLE;
		}
		
		return SOLUTION_STATUS.MODEL_CHANGED;
	}

	private void removeConfigurationFromCDO(PaasageConfiguration pc,
			ArrayList<String> vmRemoveList, HashMap<String, Boolean> delTable) {
		// Removing the variables first. It's a simple thing to do since no
		// dependencies nor child elements
		Iterator<PaaSageVariable> it = pc.getVariables().iterator();
		while (it.hasNext()) {
			PaaSageVariable pv = it.next();
			String cpVarId = pv.getCpVariableId();
			for (int n = 0; n < vmRemoveList.size(); n++) {
				// if the variable is related to a particular vm ID, e.g.
				// "*_vm_LL"
				String vmName = vmRemoveList.get(n);
				if (cpVarId.contains(vmName)) {
					System.out.println("varId = " + cpVarId + "--> is being REMOVED");
					delTable.put(cpVarId, null);
					it.remove();
					break;
				}
			} // end for
		} // end while

		System.out.println();
		
		// Removing Providers that are not needed as defined in the SLA
		Iterator<Provider> pIt = pc.getProviders().iterator();
		while (pIt.hasNext()) {
			Provider p = pIt.next();
			String providerId = p.getId();
			if (delTable.containsKey(providerId) == true) {
				System.out.println("Provider ID = " + providerId + " --> is being REMOVED");
				pIt.remove();
			}
		}

		System.out.println();

		// Removing VM Profiles that are not needed as defined in the SLA		
		VirtualMachineProfile vmProfile = null;
		EList<VirtualMachineProfile> vmProfileList = pc.getVmProfiles();
		for (int i = vmProfileList.size() - 1; i >= 0; i--) // deleting from
															// last to first
		{
			vmProfile = vmProfileList.get(i);
			String vmId = vmProfile.getCloudMLId();
			if (delTable.containsKey(vmId)) {
				System.out.println("vmProfileId = " + vmId + " --> is being REMOVED"); // +
															// vmProfile.cdoID());

				// Removing the child elements first
				EcoreUtil.delete(vmProfile.getMemory().getValue(), true);
				EcoreUtil.delete(vmProfile.getMemory(), true);
				EcoreUtil.delete(vmProfile.getStorage().getValue(), true);
				EcoreUtil.delete(vmProfile.getStorage(), true);
				EcoreUtil.delete(vmProfile.getCpu().getValue(), true);
				EcoreUtil.delete(vmProfile.getCpu(), true);
				EcoreUtil.delete(vmProfile.getOs(), true);
				// TODO: check with the new changes
				//for (ProviderDimension pdim : vmProfile.getProviderDimension()) {
				//	EcoreUtil.delete(pdim.getMetricID(), true);
				//}
				EcoreUtil.delete(vmProfile, true);
			}
		} // end while
	}

	private void removeConstraintFromCDO(ConstraintProblem cpModel,
			ArrayList<String> vmRemoveList, HashMap<String, Boolean> delTable) {
		System.out
				.println("\n\n***** Removing constraints and expressions from Constraint Problem *****\n");
		int SIZE = 50;
		ArrayList<ComposedExpressionImpl> removeStack = new ArrayList<ComposedExpressionImpl>(
				SIZE); // for CDO Client to remove
		

		System.out.println("\nDeleting constants...");
		Iterator<Constant> constIt = cpModel.getConstants().iterator();
		while (constIt.hasNext()) {
			Constant c = constIt.next();
			String id = c.getId();
			for (String candidate : delTable.keySet()) {
				if (id.contains(candidate)) {
					System.out.println("* constant: " + id + " --> is being REMOVED.");
					constIt.remove();
				}
			}
		}
		
		System.out.println();

		System.out.println("\nDeleting constraint variables: ");
		Iterator<Variable> varIt = cpModel.getVariables().iterator();
		while (varIt.hasNext()) {
			Variable v = varIt.next();
			String id = v.getId();
			if (delTable.containsKey(id)) {
				System.out.println("* varID: " + id + " --> is being REMOVED.");
				if (v.getDomain() != null) {
					System.out.println("  > Deleting also its child object: " + v.getDomain());
					EcoreUtil.delete(v.getDomain(), true);
				}

				EList<Solution> solutions = cpModel.getSolution();
				Solution sol = CPModelTool.searchLastSolution(solutions);
				if (sol != null) {
					VariableValue varValue = CPModelTool.searchVariableValue(sol, v);
					if (varValue != null) {
						System.out.println(" Deleting also its child object: " + varValue.getValue());
						EcoreUtil.delete(varValue.getValue(), true);
					}
				}

				varIt.remove();
			}
		}

		System.out.println();

		// Marking the aux expressions for removal. However, the removal is done
		// at the end
		boolean delete = false;
		System.out.println("\n\nDeleting Aux Expressions: ");
		Iterator<Expression> auxExpIt = cpModel.getAuxExpressions().iterator();
		while (auxExpIt.hasNext()) {
			delete = false;
			Expression ex = auxExpIt.next();
			//System.out.println("* " + ex.getId()
			//		+ " has the following variables and/or expressions:");

			if (ex instanceof eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl) {
				ComposedExpressionImpl ceImpl = (ComposedExpressionImpl) ex;
				Iterator<NumericExpression> neIt = ceImpl.getExpressions()
						.iterator();
				while (neIt.hasNext()) {
					NumericExpression ne = neIt.next();
					String neId = ne.getId();
					if (delTable.containsKey(neId)) {
						System.out.println("  -- " + neId + " --> is already marked for REMOVAL");
						delete = true; // no need to remove this since it is
										// done above or already marked
					}
				} // end while

				if (delete) // remove at the end because it will be
									// referenced by the constraints (below)
				{
					System.out.println("  >>> Thus, " + ceImpl.getId()
							+ " is also marked for REMOVAL");
					delTable.put(ceImpl.getId(), null);
					// ceImpl.getExpressions().clear();
					removeStack.add(ceImpl); // add to the stack for deletion
					delete = false; // reset
				}
			} else {
				System.out.println("  -- a constant expression.");
			}
		} // end while

		// Removing the constraints
		System.out.println("\nDeleting Constraints: ");
		Iterator<ComparisonExpression> ceIt = cpModel.getConstraints()
				.iterator();
		while (ceIt.hasNext()) {
			ComparisonExpression ce = ceIt.next();
			System.out.print("* " + ce.getId());
			System.out.print(" contains expressions: " + ce.getExp1().getId());
			// System.out.print(" -- " + ce.getComparator().getName());
			// System.out.print(" (" + ce.getComparator().getValue());
			System.out.print("  " + ce.getExp2().getId());
			// System.out.println();

			if (delTable.containsKey(ce.getExp1().getId()) == true
					|| delTable.containsKey(ce.getExp2().getId()) == true) {
				System.out.print(" --> is being REMOVED");
				delTable.put(ce.getId(), null);
				ceIt.remove();
			}
			System.out.println();
		}

		// Do the actual removal of the aux expressions from the stack
		System.out.println("\n\nStart removing the above aux expressions");
		System.out.println("Total size to remove = " + removeStack.size());
		for (int k = removeStack.size() - 1; k >= 0; k--) {
			ComposedExpressionImpl cdoObj = removeStack.get(k);
			System.out.println("Removing at stack[" + k + "] = "
					+ cdoObj.getId());
			EcoreUtil.delete(cdoObj, true);
		}

		System.out.println("\nDeleting metric variables: ");
		Iterator<MetricVariable> metricIt = cpModel.getMetricVariables()
				.iterator();
		while (metricIt.hasNext()) {
			MetricVariable mv = metricIt.next();
			String id = mv.getId();
			System.out.print("* metricID: " + id);
			for (int n = 0; n < vmRemoveList.size(); n++) {
				// if the variable is related to a particular vm ID, e.g.
				// "*_vm_LL"
				String vmName = vmRemoveList.get(n);
				if (id.contains(vmName) == true) {
					System.out.print(" --> is being REMOVED.");

					EList<Solution> solutions = cpModel.getSolution();
					Solution sol = CPModelTool.searchLastSolution(solutions);
					MetricVariableValue mvValue = CPModelTool
							.searchMetricValue(sol, mv);

					if (mvValue != null) {
						System.out.println(" Deleting also its child object: "
								+ mvValue.getValue());
						EcoreUtil.delete(mvValue.getValue(), true);
					}
					// if (mv.getValue() != null)
					// {
					// System.out.println(" Deleting also its child object: " +
					// mv.getValue());
					// EcoreUtil.delete(mv.getValue(), true);
					// }
					delTable.put(id, null);
					metricIt.remove();
					break;
				}
			} // end for
			System.out.println();
		}
	}

	// get providers defined in the Organisation Model and identify the provider
	// to be deleted.
	public static Map<String, String> getProviderFromOrganisationModel(
			String cModel) {
		Map<String, String> foundProviders = new HashMap<String, String>();

		IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
		CamelModel camelModel = proxy.getCamelModel(cModel);
		if (camelModel == null) {
			System.out.println("Error: The given CAMEL model (" + cModel + ") was not found in the CDO database!");
			return null;
		}
		EList<OrganisationModel> orgModels = camelModel.getOrganisationModels();
		for (int i = 0; i < orgModels.size(); i++) {
			EObject obj = orgModels.get(i);
			if (obj instanceof eu.paasage.camel.organisation.impl.OrganisationModelImpl) {
				OrganisationModelImpl omObj = (OrganisationModelImpl) obj;
				CloudProvider provider = omObj.getProvider();
				if (provider != null) {
					if (provider.isPublic()) {
						foundProviders.put(provider.getName(), "public");
					} else {
						foundProviders.put(provider.getName(), "private");
					}
				}
			}
		}

		return foundProviders;
	}

	public RPOutput processRequest(String camelModel, String cdoIdentifier) {
		Map<String, String> camelProviders = getProviderFromOrganisationModel(camelModel);

		/* (a) no cloud provider given in organisation model */
		if (camelProviders.isEmpty()) {
			System.out.println("\nNo cloud provider requirements found in the CAMEL model. No rules were applied.\n");
			int success = 1;
			log.debug("\nRP_result: PASS - code: " + success);
			System.out.println("\nRP_result: PASS - code: " + success);
			return new RPOutput(success, cdoIdentifier);
		}

		Set<String> distinctProviders = new HashSet<String>();
		if (camelProviders.size() >= 1) {
			System.out.println("\n> Cloud providers declared in the CAMEL model:");
			for (String name : camelProviders.keySet()) {
				String type = camelProviders.get(name);
				System.out.println("  - " + name + " [" + type + "]");
				distinctProviders.add(type);
			}
			System.out.println("\n");
			
			/* (b) both private and public providers are given */
			if (distinctProviders.size() == 2) {
				System.out.println("\nDetected both public and private cloud providers in Camel model. No rules were applied.\n");
				int success = 1;
				log.debug("\nRP_result: PASS - code: " + success);
				System.out.println("\nRP_result: PASS - code: " + success);
				return new RPOutput(success, cdoIdentifier);
			}
		}

		/* (c) either a public or private cloud provider is declared in the organisation model */
		this.openCDOSession(cdoIdentifier);
		this.cloneModel(cdoIdentifier); // clone the model
		
		SOLUTION_STATUS status = SOLUTION_STATUS.NO_CHANGE_REQUIRED;
		String detectedProvider = camelProviders.values().iterator().next();
		if (detectedProvider.equalsIgnoreCase("public")) {
			System.out.println("\n> Going to remove private cloud providers...");
			status = this.removeProvider(cdoIdentifier, camelModel, "private");
		} else {
			System.out.println("\n> Going to remove public cloud providers...");
			status = this.removeProvider(cdoIdentifier, camelModel, "public");
		}

		int success = 0;
		switch (status) {
			case ERROR:
				success = 0;
				return new RPOutput(success, cdoIdentifier);

			case MODEL_CHANGED:
				/* cp model has to be modified and committed; continue */
				break;

			case NO_CHANGE_REQUIRED:
				success = 1;
				log.debug("\nRP_result: PASS - code: " + success);
				System.out.println("\nRP_result: PASS - code: " + success);
				
				printFile(cdoIdentifier);
				
				return new RPOutput(success, cdoIdentifier);

			case NO_SOLUTION_AVAILABLE:
				success = 0;
				log.debug("\nRP_result: NO_SOLUTION - code: " + success);
				System.out.println("\nRP_result: NO_SOLUTION - code: " + success);
				
				printFile(cdoIdentifier);
				
				return new RPOutput(success, cdoIdentifier);
		}

		// commit or save the clone model to the CDO server
		this.commitCloneModelToCDO();

		// NOTE: debugging - check the result
		CDOClientExtended cdoClient = this.getCDOClient();
		CDOView cdoView = cdoClient.openView();

		System.out.println("\n-------------------------------------------------------------------");
		String newResId = this.getCloneResId();
		ModelData data = this.getModelDataFromCDO(newResId, cdoView);
		data.printPaasageConfiguration();
		System.out.println("\n-------------------------------------------------------------------");
		data.printConstraintProblem();
		cdoClient.closeView(cdoView);

		// finally, need to commit & close the CDO connection
		// rp.commitAndCloseCDOSession();
		this.closeCDOSession();

		success = 0; // 0 means sucess and pass the RP validation checks on the CP models
		if (this.getValidationResult()) {
			log.debug("\nRP_result: PASS - code: " + success);
			System.out.println("\nRP_result: PASS - code: " + success);
			success = 1;

			printFile(newResId);
			
		} else {
			log.debug("\nRP_result: FAIL - code: " + success);
			System.out.println("\nRP_result: FAIL - code: " + success);
			success = 0;
		}

		return new RPOutput(success, newResId);
	}
	
	public void printFile(String cpModelId) {
		try {
			OutputStream output = new FileOutputStream("rp_output");
			PrintStream printer = new PrintStream(output);
			printer.print(cpModelId);
			printer.close();
		} catch (IOException e) {
			System.out.println("Could not write file rp_output!");
		}
	}

	/**
	 * @param args Command-line arguments
	 */
	public static void main(String[] args) {
		Map<String, String> arguments = Utilities.parseArguments(args);

		if (arguments.get("d") != null) {
			log.info("Starting as a service...");
			RuleProcessorService.getInstance().run();
			System.exit(0);
		}
		final String camelModel = arguments.get("m");
		final String cpModel = arguments.get("c");

		if (!Utilities.validateArguments(camelModel, cpModel)) {
			System.exit(1);
		}

		log.info("Parsing provider information...");

		RuleProcessor rp = new RuleProcessor();
		RPOutput output = rp.processRequest(camelModel, cpModel);

		System.exit(output.getErrorCode());
	}
}
