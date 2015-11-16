/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

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
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.impl.OrganisationModelImpl;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.impl.ProviderModelImpl;
import eu.paasage.camel.type.impl.StringsValueImpl;
import eu.paasage.upperware.cp.cloner.CDOClientExtended; // for cloning the CDO objects
import eu.paasage.upperware.cp.cloner.CPCloner;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
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
		 * // TODO: redundant?
		 * catch(CommitException ce) {
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
	 * @param appId application identifier
	 * @param cdoView CDO view
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
	 * @param appId application identifier
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

	public void removeProvider(String resId, String providerType) {
		if (providerType == null) {
			return;
		}

		// find out the type of each provider, i.e. private / public
		// e.g. <Amazon-Ireland-1415008389854, Public>,
		// <Sintef-Nova-Norway-1415008389864, Private>, etc
		// Hashtable<String, String> resProvider =
		// this.getProviderDeploymentModel(resId);

		// clone the model and delete objects from this version NOT the original
		// one, since it doesn't work
		// this.cloneModel(resId); // clone the model
		List<EObject> objList = this.getCloneModel();

		String[] strArray = resId.split("/"); // splitting
												// upperware-models/1414751126815
		String newPaaSageConfigId = strArray[1] + "v2"; // take the latter part:
														// 1414751126815
		cloneResId_ = resId + "v2";

		EObject obj = null;
		PaasageConfiguration pc = null;
		HashMap<String, Boolean> delTable = new HashMap<String, Boolean>(); // allows
																			// null
																			// value
																			// vs
																			// hashtable
		ArrayList<String> vmRemoveList = new ArrayList<String>();
		// queryVMProfile(providerType, resProvider, vmRemoveList); // CDO query
		// version

		// find out the exact VM profile ID for a particular resource provider
		// e.g. for Amazon, it has VM profile with ID = "SL", in the xmi file:
		// <vmProfiles cloudMLId="SL">
		for (int i = 0; i < objList.size(); i++) {
			obj = objList.get(i);
			if (obj instanceof eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl) {
				pc = (PaasageConfiguration) obj;
				pc.setId(newPaaSageConfigId); // set a new resource ID for this
												// clone version

				Iterator<VirtualMachineProfile> it = pc.getVmProfiles().iterator();
				while (it.hasNext()) {
					VirtualMachineProfile vmProfile = it.next();
					/*
					 * TODO: check with the new changes ProviderCost pCost =
					 * vmProfile.getProvider(); String pID =
					 * pCost.getProvider().getId(); // e.g. Amazon-Ireland-xxxx
					 * 
					 * String val = resProvider.get(pID); if
					 * (providerType.equalsIgnoreCase(val) == true) {
					 * vmRemoveList.add(vmID); // add to the to-be-removed list
					 * System.out.println("- " + pID + " - vmID: " + vmID);
					 * delTable.put(pID, null); // add the provider ID
					 * delTable.put(vmID, null); // and its vm ID to the delete
					 * list }
					 */
				} // end while
			}
		}

		System.out
				.println("\nList of cloud providers to be REMOVED since they are "
						+ providerType);
		if (vmRemoveList.size() == 0) {
			System.out.println("- None. Can't find " + providerType
					+ " cloud providers in the model.\n");
			return;
		}

		// do the actual removal
		removeModelFromCDO(resId, delTable, vmRemoveList, objList);
	}

	private void removeModelFromCDO(String resId,
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
	}

	private void removeConfigurationFromCDO(PaasageConfiguration pc,
			ArrayList<String> vmRemoveList, HashMap<String, Boolean> delTable) {
		// Removing the variables first. It's a simple thing to do since no
		// dependencies nor child elements
		Iterator<PaaSageVariable> it = pc.getVariables().iterator();
		while (it.hasNext()) {
			PaaSageVariable pv = it.next();
			String cpVarId = pv.getCpVariableId();
			System.out.print("varId = " + cpVarId);
			for (int n = 0; n < vmRemoveList.size(); n++) {
				// if the variable is related to a particular vm ID, e.g.
				// "*_vm_LL"
				String vmName = vmRemoveList.get(n);
				if (cpVarId.endsWith(vmName) == true) {
					System.out.print(" --> is being REMOVED");
					delTable.put(cpVarId, null);
					it.remove();
					break;
				}
			} // end for
			System.out.println();
		} // end while

		// Removing Providers that are not needed as defined in the SLA
		System.out.println();
		Iterator<Provider> pIt = pc.getProviders().iterator();
		while (pIt.hasNext()) {
			Provider p = pIt.next();
			String providerId = p.getId();
			System.out.print("Provider ID = " + providerId);
			if (delTable.containsKey(providerId) == true) {
				System.out.print(" --> is being REMOVED"); // + p.cdoID());
				pIt.remove();
			}
			System.out.println();
		}

		// Removing VM Profiles that are not needed as defined in the SLA
		System.out.println();
		VirtualMachineProfile vmProfile = null;
		EList<VirtualMachineProfile> vmProfileList = pc.getVmProfiles();
		for (int i = vmProfileList.size() - 1; i >= 0; i--) // deleting from
															// last to first
		{
			vmProfile = vmProfileList.get(i);
			String vmId = vmProfile.getCloudMLId();
			System.out.print("vmProfileId = " + vmId);
			if (delTable.containsKey(vmId) == true) {
				System.out.print(" --> is being REMOVED"); // +
															// vmProfile.cdoID());

				// Removing the child elements first
				EcoreUtil.delete(vmProfile.getMemory().getValue(), true);
				EcoreUtil.delete(vmProfile.getMemory(), true);
				EcoreUtil.delete(vmProfile.getStorage().getValue(), true);
				EcoreUtil.delete(vmProfile.getStorage(), true);
				EcoreUtil.delete(vmProfile.getCpu().getValue(), true);
				EcoreUtil.delete(vmProfile.getCpu(), true);
				// EcoreUtil.delete(vmProfile.getOs(), false); // OS can't be
				// removed
				// TODO: check with the new changes
				// EcoreUtil.delete(vmProfile.getProvider(), true);
				EcoreUtil.delete(vmProfile, true);
			}
			System.out.println();
		} // end while
	}

	private void removeConstraintFromCDO(ConstraintProblem cpModel,
			ArrayList<String> vmRemoveList, HashMap<String, Boolean> delTable) {
		System.out
				.println("\n\n***** Removing constraints and expressions from Constraint Problem *****\n");
		int SIZE = 50;
		ArrayList<ComposedExpressionImpl> removeStack = new ArrayList<ComposedExpressionImpl>(
				SIZE); // for CDO Client to remove

		System.out.println("\nDeleting constraint variables: ");
		Iterator<Variable> varIt = cpModel.getVariables().iterator();
		while (varIt.hasNext()) {
			Variable v = varIt.next();
			String id = v.getId();
			System.out.print("* varID: " + id);
			if (delTable.containsKey(id) == true) {
				System.out.print(" --> is being REMOVED.");
				if (v.getDomain() != null) {
					System.out.print(" Deleting also its child object: "
							+ v.getDomain());
					EcoreUtil.delete(v.getDomain(), true);
				}

				// if (v.getValue() != null)
				// {
				// System.out.println(" Deleting also its child object: " +
				// v.getValue());
				// EcoreUtil.delete(v.getValue(), true);
				// }
				EList<Solution> solutions = cpModel.getSolution();
				Solution sol = CPModelTool.searchLastSolution(solutions);
				VariableValue varValue = CPModelTool
						.searchVariableValue(sol, v);
				if (varValue != null) {
					System.out.println(" Deleting also its child object: "
							+ varValue.getValue());
					EcoreUtil.delete(varValue.getValue(), true);
				}

				varIt.remove();
			}
			System.out.println();
		}

		// Marking the aux expressions for removal. However, the removal is done
		// at the end
		boolean delete = false;
		System.out.println("\n\nDeleting Aux Expressions: ");
		Iterator<Expression> auxExpIt = cpModel.getAuxExpressions().iterator();
		while (auxExpIt.hasNext()) {
			delete = false;
			Expression ex = auxExpIt.next();
			System.out.println("* " + ex.getId()
					+ " has the following variables and/or expressions:");

			if (ex instanceof eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl) {
				ComposedExpressionImpl ceImpl = (ComposedExpressionImpl) ex;
				Iterator<NumericExpression> neIt = ceImpl.getExpressions()
						.iterator();
				while (neIt.hasNext()) {
					NumericExpression ne = neIt.next();
					String neId = ne.getId();
					System.out.print("  -- " + neId);
					if (delTable.containsKey(neId) == true) {
						System.out
								.println(" --> is already marked for REMOVAL");
						delete = true; // no need to remove this since it is
										// done above or already marked
					} else {
						System.out.println();
					}
				} // end while

				if (delete == true) // remove at the end because it will be
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

			System.out.println();
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
	public static String getProviderFromOrganisationModel(String cModel) {

		IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
		CamelModel camelModel = proxy.getCamelModel(cModel);
		if (camelModel == null) {
			System.out.println("Error: The given CAMEL model (" + 
					cModel + ") was not found in the CDO database!");
			return null;
		}
		EList<OrganisationModel> orgModels = camelModel.getOrganisationModels();
		EObject obj = null;
		Hashtable<String, String> omProviders = new Hashtable<String, String>();
		String providerType = "";

		for (int i = 0; i < orgModels.size(); i++) {
			obj = orgModels.get(i);
			if (obj instanceof eu.paasage.camel.organisation.impl.OrganisationModelImpl) {
				OrganisationModelImpl omObj = (OrganisationModelImpl) obj;
				CloudProvider provider = omObj.getProvider();
				// System.out.println("-- Provider = " + provider);
				if (provider != null) {
					// get the list of the providers defined in the
					// OrganisationModel with the type (public or private)
					if (provider.isPublic() == true) {
						providerType = "private"; // remove private providers
						System.out.println("-- run this app on PUBLIC cloud\n");
					} else {
						providerType = "public";
						System.out.println("-- run this app on PRIVATE cloud\n");
					}
					// System.out.println("-- Provider Name= " +
					// provider.getName() + " --- type =" + providerType);
					omProviders.put(provider.getName(), providerType);
				}
			}
		}// end for
			// return omProviders; //It is not clear if the user can define more
			// than one provider in the OM
		return providerType;
	}
	
	public int processRequest(String camelModel, String cdoIdentifier) {
		String providerType = getProviderFromOrganisationModel(camelModel);

		if (providerType == "") {
			System.out.println("there is not provider defined in the Organisation Model (CAMEL) nothing to do. RP Pass \n ");
		} else {
			this.openCDOSession(cdoIdentifier);
			this.cloneModel(cdoIdentifier); // clone the model
			// List<EObject> objList = rp.getCloneModel(); // get the clone
			// model
			this.removeProvider(cdoIdentifier, providerType); // NOTE: move to
													// checkSLA.java file ??

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
		}

		int success = 0; // 0 means sucess and pass the RP validation checks on the CP models
		if (this.getValidationResult() == true) {
			log.debug("\nRP_result: PASS - code: " + success);
			System.out.println("\nRP_result: PASS - code: " + success);
			success = 1;
		} else {
			log.debug("\nRP_result: FAIL - code: " + success);
			System.out.println("\nRP_result: FAIL - code: " + success);
			success = 0;
		}
		
		return success;
	}

	// //////////////////////////////////////////////////////
	public static void main(String[] args) throws Exception {
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
        
 		String providerType;
		RuleProcessor rp = new RuleProcessor();

		providerType = getProviderFromOrganisationModel(camelModel);
		if (providerType == null) {
			System.exit(1);
		}

		if (providerType == "") {
			System.out.println("there is no provider defined in the Organisation Model (CAMEL) nothing to do. RP Pass \n ");
		} else {
			if (!rp.openCDOSession(cpModel)) {
				rp.closeCDOSession();
				System.out.println("RuleProcessor.java: Error - Given CP Model ID (" + cpModel + ") could not be found in the database. Please check previous components for errors.");
				log.error("RuleProcessor.java: Error - Given CP Model ID (" + cpModel + ") could not be found in the database. Please check previous components for errors.");
				System.exit(1);
			}
			rp.cloneModel(cpModel); // clone the model
			// List<EObject> objList = rp.getCloneModel(); // get the clone model
			rp.removeProvider(cpModel, providerType); // NOTE: move to checkSLA.java file ??

			// commit or save the clone model to the CDO server
			rp.commitCloneModelToCDO();

			// NOTE: debugging - check the result
			CDOClientExtended cdoClient = rp.getCDOClient();
			CDOView cdoView = cdoClient.openView();

			System.out.println("\n-------------------------------------------------------------------");
			String newResId = rp.getCloneResId();
			ModelData data = rp.getModelDataFromCDO(newResId, cdoView);
			data.printPaasageConfiguration();
			System.out.println("\n-------------------------------------------------------------------");
			data.printConstraintProblem();
			cdoClient.closeView(cdoView);

			// finally, need to commit & close the CDO connection
			// rp.commitAndCloseCDOSession();
			rp.closeCDOSession();
		}

		int code = 0; // 0 means sucess and pass the RP validation checks on the CP models
		if (rp.getValidationResult() == true) {
			log.debug("\nRP_result: PASS - code: " + code);
			System.out.println("\nRP_result: PASS - code: " + code);
			code = 0;
		} else {
			log.debug("\nRP_result: FAIL - code: " + code);
			System.out.println("\nRP_result: FAIL - code: " + code);
			code = 1;
		}
		System.out.println();
		System.exit(code);
	}
}
