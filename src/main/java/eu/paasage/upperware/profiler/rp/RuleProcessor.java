/* 
 * Copyright (C) 2014-2015 University of Stuttgart
 * 
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.profiler.rp;

import java.io.File;
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
import eu.paasage.camel.metric.Condition;
import eu.paasage.camel.metric.Metric;
import eu.paasage.camel.metric.MetricFormula;
import eu.paasage.camel.metric.MetricFormulaParameter;
import eu.paasage.camel.metric.impl.CompositeMetricImpl;
import eu.paasage.camel.organisation.CloudCredentials;
import eu.paasage.camel.organisation.CloudProvider;
import eu.paasage.camel.organisation.OrganisationModel;
import eu.paasage.camel.organisation.User;
import eu.paasage.camel.organisation.impl.OrganisationModelImpl;
import eu.paasage.camel.provider.Attribute;
import eu.paasage.camel.provider.Feature;
import eu.paasage.camel.provider.ProviderModel;
import eu.paasage.camel.provider.impl.ProviderModelImpl;
import eu.paasage.camel.requirement.HorizontalScaleRequirement;
import eu.paasage.camel.requirement.OptimisationRequirement;
import eu.paasage.camel.requirement.Requirement;
import eu.paasage.camel.requirement.RequirementModel;
import eu.paasage.camel.requirement.ServiceLevelObjective;
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
import eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.ComposedExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.Domain;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.NumericExpression;
import eu.paasage.upperware.metamodel.cp.RangeDomain;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.Variable;
import eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.profiler.cp.generator.db.api.IDatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.db.lib.CDODatabaseProxy;
import eu.paasage.upperware.profiler.cp.generator.model.tools.CPModelTool;
import eu.paasage.upperware.profiler.rp.algebra.Algebra;
import eu.paasage.upperware.profiler.rp.algebra.AlgebraVariable;
import eu.paasage.upperware.profiler.rp.algebra.ExpressionUtils;
import eu.paasage.upperware.profiler.rp.algebra.exceptions.MissingVariablesException;
import eu.paasage.upperware.profiler.rp.algebra.exceptions.NotSolvableException;
import eu.paasage.upperware.profiler.rp.algebra.exceptions.WrongStatementException;
import eu.paasage.upperware.profiler.rp.util.PropertiesReader;
import eu.paasage.upperware.profiler.rp.util.RPOutput;
import eu.paasage.upperware.profiler.rp.util.UnavailableModelException;
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
		ERROR, NO_CHANGE_REQUIRED, NO_SOLUTION_AVAILABLE, MODEL_CHANGED
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
				cdoClient_.storeModels(cloneList_, cloneResId_);
			} else {
				System.out.println("commitAndCloseCDOSession(): Warning - empty resource Id for the cloned model");
			}
		}
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

		System.out.println("List of available cloud providers:");
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

	public SOLUTION_STATUS removeProvider(
			String resId,
			String camelModel,
			String providerType,
			Map<String, String> providersToKeep) {
		if (providerType == null) {
			return SOLUTION_STATUS.ERROR;
		}

		List<EObject> objList;
		try {
			objList = this.getCloneModel();
		} catch (Exception e) {
			System.out.println("ERROR while trying to clone the model.");
			System.out.println(e.getMessage());
			return SOLUTION_STATUS.ERROR;
		}
		String[] strArray = resId.split("/"); // splitting
												// upperware-models/1414751126815
		String newPaaSageConfigId = strArray[1] + "v2"; // take the latter part:
														// 1414751126815
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
			if (obj instanceof PaasageConfigurationImpl) {
				pc = (PaasageConfiguration) obj;
				pc.setId(newPaaSageConfigId); // set a new resource ID for this clone version

				Iterator<VirtualMachineProfile> it = pc.getVmProfiles().iterator();
				while (it.hasNext()) {
					VirtualMachineProfile vmProfile = it.next();
					String vmID = vmProfile.getCloudMLId();
					for (ProviderDimension provider : vmProfile.getProviderDimension()) {
						String pId = provider.getProvider().getId();
						
						if (providersToKeep.size() >= 1) {
							String shortName = null;
							try {
								shortName = pId.split("-")[0];
							} catch (Exception e) {
								// we should report a warning
							}
							/*
							 * overrule public/private preferences if cloud provider
							 * is set via user model -> cloud credentials -> RP_ProviderRequirements
							 */
							if (!providersToKeep.containsKey(shortName)) {
								willBeRemoved.add(pId);
								vmRemoveList.add(vmID);
								delTable.put(pId, null);
								delTable.put(vmID, null);
							}
							/* do not continue; otherwise some user-defined requirements might be overriden */
							continue;
						}
						
						try {
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
						} catch (UnavailableModelException e) {
							return SOLUTION_STATUS.ERROR;
						}
					}
				}
			}
		}

		System.out.println("List of cloud providers to be REMOVED:");
		if (willBeRemoved.isEmpty()) {
			System.out.println("    -> None. No "
							+ providerType
							+ " cloud provider were selected by the CP generator for deployment.\n");
			return SOLUTION_STATUS.NO_CHANGE_REQUIRED;
		} else {
			for (String provider : willBeRemoved) {
				System.out.println("    -> " + provider);
			}
		}

		return removeModelFromCDO(resId, camelModel, delTable, vmRemoveList,
				objList);
	}

	private boolean isProviderPublic(String cpModelId, String cloudProviderId)
			throws UnavailableModelException {
		IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
		CamelModel model = proxy.getCamelModel("upperware-models/fms/"
				+ cpModelId + "/" + cloudProviderId);
		if (model == null) {
			String error = "> ERROR: Could not retrieve the following model: "
					+ "upperware-models/fms/" + cpModelId + "/"
					+ cloudProviderId;
			System.out.println(error);
			throw new UnavailableModelException(error);
		}
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

		IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
		CamelModel cModel = proxy.getCamelModel(camelModel);
		CamelModel updatedCamelModel = null;

		for (int i = 0; i < objList.size(); i++) {
			obj = objList.get(i);
			if (obj instanceof eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl) {
				PaasageConfiguration pc = (PaasageConfiguration) obj;
				removeConfigurationFromCDO(pc, vmRemoveList, delTable);
			} else if (obj instanceof eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl) {
				ConstraintProblem cpModel = (ConstraintProblem) obj;
				updatedCamelModel = removeProvidersFromOptimisationRequirement(cModel, camelModel, cpModel, vmRemoveList, delTable);
				removeConstraintFromCDO(cpModel, vmRemoveList, delTable);
			}
		}

		Set<String> requiredComponents = new HashSet<String>();
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
			System.out.println();
			System.out.println("*************************************************");
			System.out.println("RESULT");
			System.out.println("*************************************************");
			
			System.out.println();
			System.out.println("No suitable cloud provider found for the following components:");
			for (String cloudMLId : requiredComponents) {
				System.out.println("    -> " + cloudMLId);
			}
			System.out.println();
			System.out.println("NO SOLUTION");
			System.out.println();
			System.out.println("*************************************************");

			return SOLUTION_STATUS.NO_SOLUTION_AVAILABLE;
		}
		
		if (updatedCamelModel != null) { // update camel model if optimization requirement is obsolete
			cdoClient_.storeModelOverwritten(updatedCamelModel, camelModel + "v2");
		}

		return SOLUTION_STATUS.MODEL_CHANGED;
	}

	private CamelModel removeProvidersFromOptimisationRequirement(
			CamelModel camelModel,
			String strCamelModelId,
			ConstraintProblem cpModel,
			ArrayList<String> vmRemoveList,
			HashMap<String, Boolean> delTable) {
		System.out.println();
		System.out.println("*************************************************");
		System.out.println("CHECKING OPTIMISATION REQUIREMENTS");
		System.out.println("*************************************************");
		System.out.println();
		
		List<Metric> metrics = new ArrayList<Metric>();
		List<Requirement> requirements = new ArrayList<Requirement>();

		List<RequirementModel> requirementModels = camelModel.getRequirementModels();
		for (RequirementModel requirementModel : requirementModels) {
			for (Requirement r : requirementModel.getRequirements()) {
				if (r instanceof OptimisationRequirement) {
					OptimisationRequirement optimisation = (OptimisationRequirement) r;
					requirements.add(r);
					metrics.add(optimisation.getMetric());
				}
			}
		}
		
		if (metrics.isEmpty()) {
			System.out.println("    -> DONE");
			return null; // CAMEL model not updated
		}
		
		Set<String> metricsOfInterest = new HashSet<String>();
		Set<String> removedMetrics = new HashSet<String>();
		
		for (Metric metric : metrics) {
			if (metric instanceof CompositeMetricImpl) {
				CompositeMetricImpl compositeMetric = (CompositeMetricImpl) metric;
				MetricFormula formula = compositeMetric.getFormula();
				for (MetricFormulaParameter parameter : formula.getParameters()) {
					metricsOfInterest.add(parameter.getName());
				}
			}
			
			if (!metricsOfInterest.isEmpty()) {
				Iterator<Expression> iterExpression = cpModel.getAuxExpressions().iterator();
				while (iterExpression.hasNext()) {
					Expression expression = iterExpression.next();
					if (metricsOfInterest.contains(expression.getId())) {
						if (expression instanceof ComposedExpression) {
							ComposedExpression composed = (ComposedExpression) expression;
							Iterator<NumericExpression> iter = composed.getExpressions().iterator();
							while (iter.hasNext()) {
								NumericExpression numericExpression = iter.next();
								if (numericExpression instanceof Variable) {
									Variable variable = (Variable) numericExpression;
									if (delTable.containsKey(variable.getId())) {
										System.out.println("    -> REMOVE " + variable.getId());
										iter.remove();
									}
								} else if (numericExpression instanceof ComposedExpression) {
									// TODO
								} else {
									// TODO
								}
							}
							
							if (composed.getExpressions().isEmpty()) {
								System.out.println("    -> ... all parameters were removed.");
								System.out.println("    -> REMOVE " + expression.getId());
								iterExpression.remove();
								removedMetrics.add(expression.getId());
							}
						}
					}
				}
			} else {
				System.out.println("    -> DONE");
				return null; // CAMEL model not updated
			}
		}

		// Delete optimization requirement from CAMEL if some metric is deleted
		// testing
		if (!removedMetrics.isEmpty()) {
			CamelModel obj = (CamelModel) EcoreUtil.copy(camelModel);
			requirementModels = obj.getRequirementModels();
			for (RequirementModel requirementModel : requirementModels) {
				Iterator<Requirement> rIter = requirementModel.getRequirements().iterator();
				while (rIter.hasNext()) {
					Requirement r = rIter.next();
					if (r instanceof OptimisationRequirement) {
						Iterator<Requirement> iter = requirements.iterator();
						while (iter.hasNext()) {
							Requirement next = iter.next();
							if (r.getName().equals(next.getName())) {
								System.out.println("    -> REMOVE " + next.getName());
								rIter.remove();
								iter.remove();
							}
						}
					}
				}
			}

			return obj;
		}
		
		return null;
	}

	private void removeConfigurationFromCDO(PaasageConfiguration pc,
			ArrayList<String> vmRemoveList, HashMap<String, Boolean> delTable) {
		System.out.println();
		System.out.println("*************************************************");
		System.out.println("REMOVING VARIABLES FROM PaaSage Configuration");
		System.out.println("*************************************************");
		System.out.println();

		// Removing the variables first. It's a simple thing to do since no
		// dependencies nor child elements
		System.out.println("[1] Checking VARIABLES ...");
		Iterator<PaaSageVariable> it = pc.getVariables().iterator();
		while (it.hasNext()) {
			PaaSageVariable pv = it.next();
			String cpVarId = pv.getCpVariableId();
			for (int n = 0; n < vmRemoveList.size(); n++) {
				// if the variable is related to a particular vm ID, e.g.
				// "*_vm_LL"
				String vmName = vmRemoveList.get(n);
				if (cpVarId.contains(vmName)) {
					System.out.println("    -> REMOVE " + cpVarId);
					delTable.put(cpVarId, null);
					it.remove();
					break;
				}
			} // end for
		} // end while

		System.out.println();

		System.out.println("[2] Checking PROVIDERS ...");
		Iterator<Provider> pIt = pc.getProviders().iterator();
		while (pIt.hasNext()) {
			Provider p = pIt.next();
			String providerId = p.getId();
			if (delTable.containsKey(providerId) == true) {
				System.out.println("    -> REMOVE " + providerId);
				pIt.remove();
			}
		}

		System.out.println();

		System.out.println("[3] Checking VM PROFILES ...");
		VirtualMachineProfile vmProfile = null;
		EList<VirtualMachineProfile> vmProfileList = pc.getVmProfiles();
		for (int i = vmProfileList.size() - 1; i >= 0; i--)
		{
			vmProfile = vmProfileList.get(i);
			String vmId = vmProfile.getCloudMLId();
			if (delTable.containsKey(vmId)) {
				System.out.println("    -> REMOVE " + vmId);

				// Removing the child elements first
				EcoreUtil.delete(vmProfile.getMemory().getValue(), true);
				EcoreUtil.delete(vmProfile.getMemory(), true);
				EcoreUtil.delete(vmProfile.getStorage().getValue(), true);
				EcoreUtil.delete(vmProfile.getStorage(), true);
				EcoreUtil.delete(vmProfile.getCpu().getValue(), true);
				EcoreUtil.delete(vmProfile.getCpu(), true);
				EcoreUtil.delete(vmProfile.getOs(), true);
				EcoreUtil.delete(vmProfile, true);
			}
		}
	}

	private void removeConstraintFromCDO(
			ConstraintProblem cpModel,
			ArrayList<String> vmRemoveList,
			HashMap<String, Boolean> delTable)
	{
		Set<String> constantsToRemove = new HashSet<String>();
	
		System.out.println();
		System.out.println("*************************************************");
		System.out.println("VALIDATING REDUNDANT CONSTRAINTS AND EXPRESSIONS");
		System.out.println("*************************************************");
		ArrayList<ComposedExpressionImpl> removeStack = new ArrayList<ComposedExpressionImpl>();

		System.out.println();
		System.out.println("[0] Validating CONSTANTS ...");
		
		Iterator<Constant> constIt = cpModel.getConstants().iterator();
		while (constIt.hasNext()) {
			Constant c = constIt.next();
			String id = c.getId();
			for (String candidate : delTable.keySet()) {
				if (id.contains(candidate)) {
					System.out.println("    -> REMOVE " + id);
					constIt.remove();
					constantsToRemove.add(id);
				}
			}
		}
		
		System.out.println();
		System.out.println("[1] Validating CONSTRAINTS ...");
		
		Iterator<ComparisonExpression> compIter = cpModel.getConstraints().iterator();
		while (compIter.hasNext()) {
			ComparisonExpression ce = compIter.next();
			Expression e1 = ce.getExp1();
			Expression e2 = ce.getExp2();
			if (e1 instanceof Variable && e2 instanceof Constant) {
				Variable var = (Variable) e1;
				if (delTable.containsKey(var.getId())) {
					System.out.println("    -> REMOVE " + ce.getId());
					compIter.remove();
				}
			} else if (e1 instanceof Constant && e2 instanceof Variable) {
				Variable var = (Variable) e2;
				if (delTable.containsKey(var.getId())) {
					System.out.println("    -> REMOVE " + ce.getId());
					compIter.remove();
				}
			} else {
				// ComposedExpressions will be handled later
			}
		}


		System.out.println();
		System.out.println("[2] Validating VARIABLES ...");
		
		Iterator<Variable> varIt = cpModel.getVariables().iterator();
		while (varIt.hasNext()) {
			Variable var = varIt.next();
			if (delTable.containsKey(var.getId())) {
				System.out.println("    -> REMOVE " + var.getId());
				varIt.remove();
			}
		}

		System.out.println();
		System.out.println("[3] Validating EXPRESSIONS ...");

		Iterator<Expression> auxExpIt = cpModel.getAuxExpressions().iterator();
		while (auxExpIt.hasNext()) {
			Expression ex = auxExpIt.next();
			if (ex instanceof ComposedExpressionImpl) {
				ComposedExpressionImpl ceImpl = (ComposedExpressionImpl) ex;
				Iterator<NumericExpression> neIt = ceImpl.getExpressions().iterator();

				while (neIt.hasNext()) {
					NumericExpression ne = neIt.next();
					String neId = ne.getId();
					if (delTable.containsKey(neId)) {
						neIt.remove();
					}
				}

				if (ceImpl.getExpressions().isEmpty()) {
					System.out.println("    -> REMOVE " + ceImpl.getId());
					delTable.put(ceImpl.getId(), null);
					removeStack.add(ceImpl);
					
					Iterator<ComparisonExpression> iter = cpModel.getConstraints().iterator();
					while (iter.hasNext()) {
						ComparisonExpression v = iter.next();
						Expression e = v.getExp1();
						if (e instanceof ComposedExpressionImpl) {
							ComposedExpressionImpl ce = (ComposedExpressionImpl) e;
							if (ce.getId().equals(ceImpl.getId())) {
								System.out.println("    -> REMOVE " + v.getId());
								iter.remove();
							}
						}
						e = v.getExp2();
						if (e instanceof ComposedExpressionImpl) {
							ComposedExpressionImpl ce = (ComposedExpressionImpl) e;
							if (ce.getId().equals(ceImpl.getId())) {
								System.out.println("    -> REMOVE " + v.getId());
								iter.remove();
							}
						}
						
					}
					continue;
				}

				if (ceImpl.getExpressions().size() == 1) {
					NumericExpression ne = ceImpl.getExpressions().get(0);
					if (ne instanceof Constant) {
						System.out.println("    -> REMOVE " + ceImpl.getId());
						delTable.put(ceImpl.getId(), null);
						constantsToRemove.remove(ne.getId());
						removeStack.add(ceImpl);
						
						Iterator<ComparisonExpression> iter = cpModel.getConstraints().iterator();
						while (iter.hasNext()) {
							ComparisonExpression v = iter.next();
							Expression e = v.getExp1();
							if (e instanceof ComposedExpressionImpl) {
								ComposedExpressionImpl ce = (ComposedExpressionImpl) e;
								if (ce.getId().equals(ceImpl.getId())) {
									System.out.println("    -> REMOVE " + v.getId());
									iter.remove();
								}
							}
							e = v.getExp2();
							if (e instanceof ComposedExpressionImpl) {
								ComposedExpressionImpl ce = (ComposedExpressionImpl) e;
								if (ce.getId().equals(ceImpl.getId())) {
									System.out.println("    -> REMOVE " + v.getId());
									iter.remove();
								}
							}
							
						}
					}
					continue;
				}
			} else {
				throw new UnsupportedOperationException(ex.getId());
			}
		}

		System.out.println();
		System.out.print("[4] Removing EXPRESSIONS ...");
		
		for (int k = removeStack.size() - 1; k >= 0; k--) {
			ComposedExpressionImpl cdoObj = removeStack.get(k);
			EcoreUtil.delete(cdoObj, true);
		}
		
		System.out.println(" DONE");
		
		System.out.println();
		System.out.println("[5] Removing CONSTANTS ...");
		
		constIt = cpModel.getConstants().iterator();
		while (constIt.hasNext()) {
			Constant c = constIt.next();
			String id = c.getId();
			if (constantsToRemove.contains(id)) {
				System.out.println("    -> REMOVE " + id);
				constIt.remove();
			}
		}
		
		System.out.println();
		System.out.println("[6] Removing METRIC VARIABLES ... ");
		
		// FIXME: Not tested
		Iterator<MetricVariable> metricIt = cpModel.getMetricVariables().iterator();
		while (metricIt.hasNext()) {
			MetricVariable mv = metricIt.next();
			String id = mv.getId();
			for (int n = 0; n < vmRemoveList.size(); n++) {
				String vmName = vmRemoveList.get(n);
				if (id.contains(vmName) == true) {
					System.out.println("    -> REMOVE " + id);

					EList<Solution> solutions = cpModel.getSolution();
					Solution sol = CPModelTool.searchLastSolution(solutions);
					MetricVariableValue mvValue = CPModelTool
							.searchMetricValue(sol, mv);

					if (mvValue != null) {
						EcoreUtil.delete(mvValue.getValue(), true);
					}
					delTable.put(id, null);
					metricIt.remove();
					break;
				}
			}
		}
	}

	// get providers defined in the Organisation Model and identify the provider
	// to be deleted.
	public static Map<String, String> getProviderFromOrganisationModel(String cModel) {
		Map<String, String> foundProviders = new HashMap<String, String>();

		IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
		CamelModel camelModel = proxy.getCamelModel(cModel);
		if (camelModel == null) {
			System.out.println("Error: The given CAMEL model (" + cModel
					+ ") was not found in the CDO database!");
			return null;
		}
		EList<OrganisationModel> orgModels = camelModel.getOrganisationModels();
		for (int i = 0; i < orgModels.size(); i++) {
			EObject obj = orgModels.get(i);
			if (obj instanceof eu.paasage.camel.organisation.impl.OrganisationModelImpl) {
				OrganisationModelImpl omObj = (OrganisationModelImpl) obj;
				if (!omObj.getName().equalsIgnoreCase("RP_ProviderRequirements")) {
					continue;
				}
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
	
	public static Map<String, String> getProviderFromUserModel(String cModel) {
		Map<String, String> foundProviders = new HashMap<String, String>();
		
		IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
		CamelModel camelModel = proxy.getCamelModel(cModel);
		if (camelModel == null) {
			System.out.println("Error: The given CAMEL model (" + cModel
					+ ") was not found in the CDO database!");
			return null;
		}
		EList<OrganisationModel> orgModels = camelModel.getOrganisationModels();
		for (int i = 0; i < orgModels.size(); i++) {
			EObject obj = orgModels.get(i);
			if (obj instanceof eu.paasage.camel.organisation.impl.OrganisationModelImpl) {
				OrganisationModelImpl omObj = (OrganisationModelImpl) obj;
				EList<User> users = omObj.getUsers();
				for (User user : users) {
					EList<CloudCredentials> cloudCredentials = user.getCloudCredentials();
					for (CloudCredentials cloudCredential : cloudCredentials) {
						CloudProvider cloudProvider = cloudCredential.getCloudProvider();
						String providerName = cloudProvider.getName();
						if (providerName.equalsIgnoreCase("GlobalProviderRequirements")) {
							String name = cloudCredential.getName();
							foundProviders.put(name, null);							
						}
					}
					break; // we are currently supporting only one user
				}
			}
		}
		
		return foundProviders;
	}

	public RPOutput processRequest(String camelModel, String cdoIdentifier,
			String outputFile, boolean runAsDaemon) {
		Map<String, String> providerToKeep = getProviderFromUserModel(camelModel); // new
		if (providerToKeep.size() >= 1) {
			System.out.println();
			System.out.println("*************************************************");
			System.out.println("LIST OF USER-DEFINED PROVIDERS FOR DEPLOYMENT");
			System.out.println("*************************************************");
			System.out.println();
			for (String key : providerToKeep.keySet()) {
				System.out.println("    -> " + key);
			}
			System.out.println();
			System.out.println("*************************************************");
		}
		
		Map<String, String> camelProviders = getProviderFromOrganisationModel(camelModel);
		if (camelProviders == null) { // no CAMEL model was found
			return new RPOutput(0, outputFile);
		}

		// fallback, if no filename was passed to this process
		if (outputFile == null) {
			outputFile = "rp_output";
		}
		
		/* (z) checking user requirements (new feature) */
		SOLUTION_STATUS sloStatus = this.validateMetricConditions(cdoIdentifier, camelModel);
		
		// don't continue cloud provider checking if there is an error in SLOs
		switch (sloStatus) {
		case ERROR:
			System.out.println("*************************************************");
			System.out.println("RESULT");
			System.out.println("*************************************************");
			System.out.println();
			System.out.println("User requirements:");
			System.out.println("    -> ERROR: Please read the log output for more information.");
			System.out.println();
			System.out.println("*************************************************");
			
			int success = 0;
			return new RPOutput(success, cdoIdentifier);
		case MODEL_CHANGED:
			break;
		case NO_CHANGE_REQUIRED:
			break;
		case NO_SOLUTION_AVAILABLE:
			System.out.println("*************************************************");
			System.out.println("RESULT");
			System.out.println("*************************************************");
			System.out.println();
			System.out.println("User requirements:");
			System.out.println("    -> ERROR: The equation is not solvable. Please adapt your conditions and/or SLOs.");
			System.out.println();
			System.out.println("*************************************************");
			
			success = 0;
			return new RPOutput(success, cdoIdentifier);
		default:
			break;
		}

		/* (a) no cloud provider given in organization model */
		if (camelProviders.isEmpty()) {
			System.out.println();
			System.out.println("*************************************************");
			System.out.println("RESULT");
			System.out.println("*************************************************");
			
			System.out.println();
			
			switch (sloStatus) {
			case ERROR:
				System.out.println("User requirements:");
				System.out.println("    -> ERROR: Please read the log output for more information.");
				System.out.println("Cloud Provider:");
				System.out.println("    -> PASSED: No cloud provider requirements found in the CAMEL model. No actions required.");
				System.out.println();
				System.out.println("*************************************************");
				
				int success = 0;
				return new RPOutput(success, cdoIdentifier);
			case MODEL_CHANGED:
				// commit or save the clone model to the CDO server
				this.commitCloneModelToCDO();

				// NOTE: debugging - check the result
				CDOClientExtended cdoClient = this.getCDOClient();
				CDOView cdoView = cdoClient.openView();
				String newResId = this.getCloneResId();
				cdoClient.closeView(cdoView);

				// finally, need to commit & close the CDO connection
				this.closeCDOSession();

				printFile(outputFile, newResId, runAsDaemon);

				System.out.println("User requirements:");
				System.out.println("    -> PASSED: MODEL UPDATED (" + newResId + ")");
				System.out.println("Cloud Provider:");
				System.out.println("    -> PASSED: No cloud provider requirements found in the CAMEL model. No actions required.");
				System.out.println();
				System.out.println("*************************************************");

				success = 1;
				return new RPOutput(success, newResId);
			case NO_CHANGE_REQUIRED:
				System.out.println("User requirements:");
				System.out.println("    -> No changes to apply. OK.");
				break;
			case NO_SOLUTION_AVAILABLE:
				System.out.println("User requirements:");
				System.out.println("    -> ERROR: The equation is not solvable. Please adapt your conditions and/or SLOs.");
				System.out.println("Cloud Provider:");
				System.out.println("    -> PASSED: No cloud provider requirements found in the CAMEL model. No actions required.");
				System.out.println();
				System.out.println("*************************************************");
				
				success = 0;
				return new RPOutput(success, cdoIdentifier);
			default:
				break;
			}
			
			System.out.println("Cloud Provider:");
			System.out.println("    -> PASSED: No cloud provider requirements found in the CAMEL model. No actions required.");
			System.out.println();
			System.out.println("*************************************************");

			int success = 1;
			return new RPOutput(success, cdoIdentifier);
		}

		Set<String> distinctProviders = new HashSet<String>();
		if (camelProviders.size() >= 1) {
			System.out.println();
			System.out.println("*************************************************");
			System.out.println("CHECKING Cloud provider requirements");
			System.out.println("*************************************************");
			System.out.println();
			
			for (String name : camelProviders.keySet()) {
				String type = camelProviders.get(name);
				if (name.trim().isEmpty()) {
					name = "reqirement set to";
				}
				System.out.println("    -> " + name + " [" + type + "]");
				distinctProviders.add(type);
			}
			
			System.out.println();

			/* (b) both private and public providers are given */
			if (distinctProviders.size() == 2) {
				System.out.println("*************************************************");
				System.out.println("RESULT");
				System.out.println("*************************************************");
				System.out.println();
				
				switch (sloStatus) {
				case ERROR:
					System.out.println("User requirements:");
					System.out.println("    -> ERROR: Please read the log output for more information.");
					System.out.println("Cloud Provider:");
					System.out.println("    -> WARNING: Both public and private cloud provider requirements found. No rules were applied.\n");
					System.out.println();
					System.out.println("*************************************************");
					
					int success = 0;
					return new RPOutput(success, cdoIdentifier);
				case MODEL_CHANGED:
					// commit or save the clone model to the CDO server
					this.commitCloneModelToCDO();

					// NOTE: debugging - check the result
					CDOClientExtended cdoClient = this.getCDOClient();
					CDOView cdoView = cdoClient.openView();
					String newResId = this.getCloneResId();
					cdoClient.closeView(cdoView);

					// finally, need to commit & close the CDO connection
					this.closeCDOSession();

					printFile(outputFile, newResId, runAsDaemon);

					System.out.println("User requirements:");
					System.out.println("    -> PASSED: MODEL UPDATED (" + newResId + ")");
					System.out.println("Cloud Provider:");
					System.out.println("    -> WARNING: Both public and private cloud provider requirements found. No rules were applied.\n");
					System.out.println();
					System.out.println("*************************************************");

					success = 1;
					return new RPOutput(success, newResId);
				case NO_CHANGE_REQUIRED:
					System.out.println("User requirements:");
					System.out.println("    -> No changes to apply. OK.");
					break;
				case NO_SOLUTION_AVAILABLE:
					System.out.println("User requirements:");
					System.out.println("    -> ERROR: The equation is not solvable. Please adapt your conditions and/or SLOs.");
					System.out.println("Cloud Provider:");
					System.out.println("    -> WARNING: Both public and private cloud provider requirements found. No rules were applied.\n");
					System.out.println();
					System.out.println("*************************************************");
					
					success = 0;
					return new RPOutput(success, cdoIdentifier);
				default:
					break;
				}
				
				System.out.println("Cloud Provider:");
				System.out.println("    -> WARNING: Both public and private cloud provider requirements found. No rules were applied.\n");
				System.out.println();
				System.out.println("*************************************************");
				
				int success = 1;
				return new RPOutput(success, cdoIdentifier);
			}
		}	
		
		/*
		 * (c) either a public or private cloud provider is declared
		 */
		this.openCDOSession(cdoIdentifier);
		this.cloneModel(cdoIdentifier); // clone the model

		SOLUTION_STATUS status = SOLUTION_STATUS.NO_CHANGE_REQUIRED;
		String detectedProvider = camelProviders.values().iterator().next();

		System.out.println();
		System.out.println("*************************************************");
		System.out.println("EVALUATING IF CLOUD PROVIDERS CAN BE REMOVED");
		System.out.println("*************************************************");
		System.out.println();
		if (detectedProvider.equalsIgnoreCase("public")) {
			status = this.removeProvider(cdoIdentifier, camelModel, "private", providerToKeep);
		} else {
			status = this.removeProvider(cdoIdentifier, camelModel, "public", providerToKeep);
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
			log.debug("\nPASSED");

			System.out.println();
			System.out.println("*************************************************");
			System.out.println("RESULT");
			System.out.println("*************************************************");
			System.out.println();
			
			switch (sloStatus) {
			case ERROR:
				break;
			case MODEL_CHANGED:
				// commit or save the clone model to the CDO server
				this.commitCloneModelToCDO();

				// NOTE: debugging - check the result
				CDOClientExtended cdoClient = this.getCDOClient();
				CDOView cdoView = cdoClient.openView();
				String newResId = this.getCloneResId();
				cdoClient.closeView(cdoView);

				// finally, need to commit & close the CDO connection
				this.closeCDOSession();

				printFile(outputFile, newResId, runAsDaemon);

				System.out.println("User requirements:");
				System.out.println("    -> PASSED: MODEL UPDATED (" + newResId + ")");
				System.out.println("Cloud Provider:");
				System.out.println("    -> PASSED.");
				System.out.println();
				System.out.println("*************************************************");

				success = 1;
				return new RPOutput(success, newResId);
			case NO_CHANGE_REQUIRED:
				System.out.println("User requirements:");
				System.out.println("    -> PASSED.");
				break;
			case NO_SOLUTION_AVAILABLE:
				break;
			default:
				break;
			}
			
			System.out.println("Cloud Provider:");
			System.out.println("    -> PASSED.");
			System.out.println();
			System.out.println("*************************************************");

			printFile(outputFile, cdoIdentifier, runAsDaemon);

			return new RPOutput(success, cdoIdentifier);

		case NO_SOLUTION_AVAILABLE:
			success = 0;
			log.debug("\nNO SOLUTION");
			
			switch (sloStatus) {
			case ERROR:
				break;
			case MODEL_CHANGED:
				System.out.println("User requirements:");
				System.out.println("    -> WARNING: Model not updated due to error in cloud provider processing.");
				break;
			case NO_CHANGE_REQUIRED:
				System.out.println("User requirements:");
				System.out.println("    -> PASSED.");
				break;
			case NO_SOLUTION_AVAILABLE:
				break;
			default:
				break;
			}
			
			System.out.println("Cloud Provider:");
			System.out.println("    -> ERROR: NO SOLUTION.");
			System.out.println();
			System.out.println("*************************************************");

			printFile(outputFile, cdoIdentifier, runAsDaemon);

			return new RPOutput(success, cdoIdentifier);
		}
		
		System.out.println();
		System.out.println("*************************************************");
		System.out.println("RESULT");
		System.out.println("*************************************************");

		// commit or save the clone model to the CDO server
		this.commitCloneModelToCDO();

		// NOTE: debugging - check the result
		CDOClientExtended cdoClient = this.getCDOClient();
		CDOView cdoView = cdoClient.openView();
		String newResId = this.getCloneResId();
		cdoClient.closeView(cdoView);

		// finally, need to commit & close the CDO connection
		// rp.commitAndCloseCDOSession();
		this.closeCDOSession();
		
		success = 0; // 0 means success and pass the RP validation checks on the CP models
		if (this.getValidationResult()) {
			log.debug("\nMODEL UPDATED (" + newResId + ")");
			System.out.println();
			
			switch (sloStatus) {
			case ERROR:
				break;
			case MODEL_CHANGED:
				System.out.println("User requirements:");
				System.out.println("    -> PASSED. MODEL UPDATED (" + newResId + ")");
				break;
			case NO_CHANGE_REQUIRED:
				System.out.println("User requirements:");
				System.out.println("    -> PASSED.");
				break;
			case NO_SOLUTION_AVAILABLE:
				break;
			default:
				break;
			}
			
			System.out.println("Cloud Provider:");
			System.out.println("    -> PASSED. MODEL UPDATED (" + newResId + ")");
			success = 1;

			printFile(outputFile, newResId, runAsDaemon);
		} else {
			log.debug("\nERROR");
			System.out.println();
			System.out.println("Cloud Provider:");
			System.out.println("    -> ERROR");
			success = 0;
		}
		
		System.out.println();
		System.out.println("*************************************************");

		return new RPOutput(success, newResId);
	}

	
	private SOLUTION_STATUS validateMetricConditions(
			String cdoIdentifier,
			String camelModel) {
		
		IDatabaseProxy proxy = CDODatabaseProxy.getInstance();
		CamelModel cModel = proxy.getCamelModel(camelModel);
		if (camelModel == null) {
			StringBuilder message = new StringBuilder();
			message.append("User requirements:");
			message.append("    -> Error: The given CAMEL model (");
			message.append(camelModel);
			message.append(") was not found in the CDO database!");
			System.out.println(message.toString());
			return SOLUTION_STATUS.ERROR;
		}
		
		/* detect available SLOs in the CAMEL model */
		Set<String> metricConditions = new HashSet<String>();
		List<AlgebraVariable> varList = new ArrayList<AlgebraVariable>();
		
		for (RequirementModel requirementModel : cModel.getRequirementModels()) {
			for (Requirement requirement: requirementModel.getRequirements()) {
				if (requirement instanceof ServiceLevelObjective) {
					ServiceLevelObjective slo = (ServiceLevelObjective) requirement;
					Condition condition = slo.getCustomServiceLevel();
					metricConditions.add(condition.getName());
				} else if (requirement instanceof HorizontalScaleRequirement) {
					HorizontalScaleRequirement hsr = (HorizontalScaleRequirement) requirement;
					String name = hsr.getComponent().getName();
					int min = hsr.getMinInstances();
					int max = hsr.getMaxInstances();
					AlgebraVariable av = new AlgebraVariable(name, min, max);
					varList.add(av);
				}
			}
		}
		
		/* (a) nothing defined */
		if (metricConditions.isEmpty()) {
			System.out.println();
			System.out.println("*************************************************");
			System.out.println("VALIDATING USER REQUIREMENTS");
			System.out.println("*************************************************");
			System.out.println();
			System.out.println("No SLO found. DONE.");
			System.out.println();
			System.out.println("*************************************************");
			return SOLUTION_STATUS.NO_CHANGE_REQUIRED;
		}
		if (varList.isEmpty()) {
			System.out.println();
			System.out.println("*************************************************");
			System.out.println("VALIDATING USER REQUIREMENTS");
			System.out.println("*************************************************");
			System.out.println();
			System.out.println("No metric conditions defined. DONE.");
			System.out.println();
			System.out.println("*************************************************");
			return SOLUTION_STATUS.NO_CHANGE_REQUIRED;
		}
		
		/* (b) */
		openCDOSession(cdoIdentifier);
		List<EObject> objList;
		try {
			objList = this.getCloneModel();
		} catch (Exception e) {
			System.out.println("    -> Error: Cloning the model was not successful: " + e.getMessage());
			System.out.println("    -> Please have a look at the output of the previous component.");
			return SOLUTION_STATUS.ERROR;
		}
		cloneResId_ = cdoIdentifier + "v2";
		
		System.out.println();
		System.out.println("*************************************************");
		System.out.println("VALIDATING USER REQUIREMENTS");
		System.out.println("*************************************************");
		System.out.println();

		Map<AlgebraVariable, Integer> varLeftMap = new HashMap<AlgebraVariable, Integer>();
		Map<AlgebraVariable, Integer> varRightMap = new HashMap<AlgebraVariable, Integer>();
		Set<String> slos = new HashSet<String>();
		
		ConstraintProblem cpModel = null;
		for (EObject eObject : objList) {
			if (eObject instanceof ConstraintProblem) {
				cpModel = (ConstraintProblem) eObject;
				for (ComparisonExpression cExpression : cpModel.getConstraints()) {
					for (String metricCondition : metricConditions) {
						if (cExpression.getId().startsWith(metricCondition)) {
							String sloExpression = ExpressionUtils.toString(cExpression, varList, varLeftMap, varRightMap);
							slos.add(sloExpression);
						}
					}
				}
			}
		}
		
		if (slos.isEmpty()) {
			System.out.println("NO SLOs detected. Continue.");
			return SOLUTION_STATUS.NO_CHANGE_REQUIRED;
		}
		
		StringBuilder expression = new StringBuilder();
		List<AlgebraVariable> variables = new ArrayList<AlgebraVariable>();
		
		System.out.println("GIVEN:");
		for (String slo : slos) {
			System.out.println("    -> SLO: " + slo);
			expression.append(slo);
		}
		for (AlgebraVariable av : varLeftMap.keySet()) {
			String from = av.getVariable() + " >= " + av.getFrom();
			String to = av.getVariable() + " <= " + av.getTo();
			expression.append(" && ");
			expression.append(from);
			expression.append(" && ");
			expression.append(to);
			variables.add(av);
			System.out.println("    -> " + from);
			System.out.println("    -> " + to);
		}
		for (AlgebraVariable av : varRightMap.keySet()) {
			String from = av.getVariable() + " >= " + av.getFrom();
			String to = av.getVariable() + " <= " + av.getTo();
			variables.add(av);
			expression.append(" && ");
			expression.append(from);
			expression.append(" && ");
			expression.append(to);
			System.out.println("    -> " + from);
			System.out.println("    -> " + to);
		}
		
		System.out.println();
		
		// evaluate expressions
		System.out.println("EVALUATE:");
		System.out.println("    -> " + expression.toString());
		System.out.println();
		
		
		Set<Integer> domain = new HashSet<Integer>();
		Map<AlgebraVariable, AlgebraVariable> replaceMap = new HashMap<AlgebraVariable, AlgebraVariable>();

		List<AlgebraVariable> ranges;
		try {
			ranges = Algebra.getInstance().test(expression.toString(), variables);
			System.out.println("RESULT:");
			System.out.println("    -> CP model will be updated to comply with new domain ranges.");
			System.out.println();

			for (AlgebraVariable av : ranges) {
				domain.add(av.getFrom());
				domain.add(av.getTo());

				String from = av.getVariable() + " >= " + av.getFrom();
				String to = av.getVariable() + " <= " + av.getTo();
				System.out.println("    -> " + from);
				System.out.println("    -> " + to);
			}
			
			for (int i = 0; i != variables.size(); ++i) {
				replaceMap.put(variables.get(i), ranges.get(i));
			}
			
			System.out.println();
			
			/* update CP model */
			int nextId = 0;
			Set<Integer> availableDomain = new HashSet<Integer>();
			if (cpModel != null) {
				/* modify/add constants */
				Map<Integer, Constant> newConstants = new HashMap<Integer, Constant>();

				for (Constant c : cpModel.getConstants()) {
					if (c.getValue() instanceof IntegerValueUpperware) {
						IntegerValueUpperware ivu = (IntegerValueUpperware) c.getValue();
						availableDomain.add(ivu.getValue());

						newConstants.put(ivu.getValue(), c);
					}
					if (c.getId().startsWith("constant_")) {
						nextId = Integer.valueOf(c.getId().split("_")[1]);
					}
				}
				domain.removeAll(availableDomain);
				for (Integer value : domain) {
					Constant c = CPModelTool.createIntegerConstant(value, "constant_" + ++nextId);
					cpModel.getConstants().add(c);
					newConstants.put(value, c);
					System.out.println("    -> ADDED " + c.getId());
				}

				/* check comparison expressions */
				for (ComparisonExpression comp : cpModel.getConstraints()) {
					Set<String> unique = new HashSet<String>();
					if (comp.getExp1() instanceof ComposedExpression) {
						ComposedExpression composed = (ComposedExpression) comp.getExp1();
						for (Expression exp : composed.getExpressions()) {
							if (exp instanceof Variable) {
								Variable v = (Variable) exp;
								String id = v.getId();
								if (id.startsWith("U_app_component_")) {
									String var = id.split("_")[3];
									unique.add(var);
								}
							}
						}
					}
					
					if (unique.size() != 1) {
						continue;
					}
					
					String component = unique.iterator().next();
					AlgebraVariable oldVariable = null;
					for (AlgebraVariable av : variables) {
						if (av.getVariable().equals(component)) {
							oldVariable = av;
							break;
						}
					}
					
					if (oldVariable == null) {
						continue;
					}

					String comparator = ExpressionUtils.comparatorToString(comp.getComparator());
					
					if (comp.getExp2() instanceof Constant) {
						Constant old = (Constant) comp.getExp2();
						if (old.getValue() instanceof IntegerValueUpperware) {
							IntegerValueUpperware ivu = (IntegerValueUpperware) old.getValue();
							int value = ivu.getValue();
							
							if (comparator.equals(">=")) {
								if (value == oldVariable.getFrom()) {
									AlgebraVariable replaceVar = replaceMap.get(oldVariable);
									if (replaceVar.getFrom() != value) {
										System.out.println("    -> UPDATED " + comp.getId());
										Constant replaceConstant = newConstants.get(replaceVar.getFrom());
										comp.setExp2(replaceConstant);
									}
								}
							} else if (comparator.equals("<=")) {
								if (value == oldVariable.getTo()) {
									AlgebraVariable replaceVar = replaceMap.get(oldVariable);
									if (replaceVar.getTo() != value) {
										System.out.println("    -> UPDATED " + comp.getId());
										Constant replaceConstant = newConstants.get(replaceVar.getTo());
										comp.setExp2(replaceConstant);
									}
								}
							}
						}
					}
				}
				
				/* update variables */
				for (Variable variable : cpModel.getVariables()) {
					String id = variable.getId();
					if (id.startsWith("U_app_component_")) {
						id = id.split("_")[3];
						for (AlgebraVariable av : ranges) {
							if (av.getVariable().equals(id)) {
								Domain d = variable.getDomain();
								if (d instanceof RangeDomain) {
									RangeDomain rd = (RangeDomain) d;
									// Don't update minInstances, because its set to 0 by CP generator
									//IntegerValueUpperware from = (IntegerValueUpperware) rd.getFrom();
									//from.setValue(av.getFrom());
									IntegerValueUpperware to = (IntegerValueUpperware) rd.getTo();
									if (to.getValue() != av.getTo()) {
										System.out.println("    -> UPDATED " + variable.getId());
										to.setValue(av.getTo());
									}
								}
							}
						}
					}
				}
			} else {
				System.out.println("User requirements:");
				System.out.println("    -> ERROR: CP Model not found: " + cdoIdentifier);
				return SOLUTION_STATUS.ERROR;
			}

		} catch (MissingVariablesException e) {
			System.out.println("User requirements:");
			System.out.println("    -> ERROR: Missing variables: " + e.getMessage());
			return SOLUTION_STATUS.ERROR;
		} catch (WrongStatementException e) {
			System.out.println("User requirements:");
			System.out.println("    -> ERROR: Invalid statement. Please check the model.");
			return SOLUTION_STATUS.ERROR;
		} catch (NotSolvableException e) {
			return SOLUTION_STATUS.NO_SOLUTION_AVAILABLE;
		}
		
		return SOLUTION_STATUS.MODEL_CHANGED;
	}

	public void printFile(String filename, String cpModelId, boolean runAsDaemon) {
		// no output when running in daemon mode
		if (runAsDaemon) {
			return;
		}

		try {
			new File(filename).delete();
		} catch (Exception e) {
			System.out.println("Could not delete given file: " + filename);
		}

		try {
			OutputStream output = new FileOutputStream(filename);
			PrintStream printer = new PrintStream(output);
			printer.print(cpModelId);
			printer.close();
		} catch (IOException e) {
			System.out.println("Could not write to given file: " + filename);
		}
	}

	/**
	 * @param args
	 *            Command-line arguments
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
		final String outputFile = arguments.get("o");

		if (!Utilities.validateArguments(camelModel, cpModel)) {
			System.exit(1);
		}

		log.info("Parsing provider information...");

		RuleProcessor rp = new RuleProcessor();
		RPOutput output = rp.processRequest(camelModel, cpModel, outputFile,
				false);

		System.exit(output.getErrorCode());
	}
}
