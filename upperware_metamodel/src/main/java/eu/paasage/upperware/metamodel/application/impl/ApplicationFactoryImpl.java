/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * 
 */
public class ApplicationFactoryImpl extends EFactoryImpl implements ApplicationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public static ApplicationFactory init() {
		try {
			ApplicationFactory theApplicationFactory = (ApplicationFactory)EPackage.Registry.INSTANCE.getEFactory(ApplicationPackage.eNS_URI);
			if (theApplicationFactory != null) {
				return theApplicationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ApplicationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ApplicationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ApplicationPackage.PAASAGE_CONFIGURATION: return (EObject)createPaasageConfiguration();
			case ApplicationPackage.VIRTUAL_MACHINE: return (EObject)createVirtualMachine();
			case ApplicationPackage.VIRTUAL_MACHINE_PROFILE: return (EObject)createVirtualMachineProfile();
			case ApplicationPackage.MEMORY: return (EObject)createMemory();
			case ApplicationPackage.STORAGE: return (EObject)createStorage();
			case ApplicationPackage.CPU: return (EObject)createCPU();
			case ApplicationPackage.PROVIDER: return (EObject)createProvider();
			case ApplicationPackage.APPLICATION_COMPONENT: return (EObject)createApplicationComponent();
			case ApplicationPackage.ELASTICITY_RULE: return (EObject)createElasticityRule();
			case ApplicationPackage.ACTION_UPPERWARE: return (EObject)createActionUpperware();
			case ApplicationPackage.CONDITION_UPPERWARE: return (EObject)createConditionUpperware();
			case ApplicationPackage.PAA_SAGE_VARIABLE: return (EObject)createPaaSageVariable();
			case ApplicationPackage.PAA_SAGE_GOAL: return (EObject)createPaaSageGoal();
			case ApplicationPackage.REQUIRED_FEATURE: return (EObject)createRequiredFeature();
			case ApplicationPackage.DIMENSION: return (EObject)createDimension();
			case ApplicationPackage.PROVIDER_DIMENSION: return (EObject)createProviderDimension();
			case ApplicationPackage.IMAGE_UPPERWARE: return (EObject)createImageUpperware();
			case ApplicationPackage.COMPONENT_METRIC_RELATIONSHIP: return (EObject)createComponentMetricRelationship();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public PaasageConfiguration createPaasageConfiguration() {
		PaasageConfigurationImpl paasageConfiguration = new PaasageConfigurationImpl();
		return paasageConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public VirtualMachine createVirtualMachine() {
		VirtualMachineImpl virtualMachine = new VirtualMachineImpl();
		return virtualMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public VirtualMachineProfile createVirtualMachineProfile() {
		VirtualMachineProfileImpl virtualMachineProfile = new VirtualMachineProfileImpl();
		return virtualMachineProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Memory createMemory() {
		MemoryImpl memory = new MemoryImpl();
		return memory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Storage createStorage() {
		StorageImpl storage = new StorageImpl();
		return storage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public CPU createCPU() {
		CPUImpl cpu = new CPUImpl();
		return cpu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Provider createProvider() {
		ProviderImpl provider = new ProviderImpl();
		return provider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ApplicationComponent createApplicationComponent() {
		ApplicationComponentImpl applicationComponent = new ApplicationComponentImpl();
		return applicationComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ElasticityRule createElasticityRule() {
		ElasticityRuleImpl elasticityRule = new ElasticityRuleImpl();
		return elasticityRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ActionUpperware createActionUpperware() {
		ActionUpperwareImpl actionUpperware = new ActionUpperwareImpl();
		return actionUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ConditionUpperware createConditionUpperware() {
		ConditionUpperwareImpl conditionUpperware = new ConditionUpperwareImpl();
		return conditionUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public PaaSageVariable createPaaSageVariable() {
		PaaSageVariableImpl paaSageVariable = new PaaSageVariableImpl();
		return paaSageVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public PaaSageGoal createPaaSageGoal() {
		PaaSageGoalImpl paaSageGoal = new PaaSageGoalImpl();
		return paaSageGoal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public RequiredFeature createRequiredFeature() {
		RequiredFeatureImpl requiredFeature = new RequiredFeatureImpl();
		return requiredFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public Dimension createDimension() {
		DimensionImpl dimension = new DimensionImpl();
		return dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ProviderDimension createProviderDimension() {
		ProviderDimensionImpl providerDimension = new ProviderDimensionImpl();
		return providerDimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ImageUpperware createImageUpperware() {
		ImageUpperwareImpl imageUpperware = new ImageUpperwareImpl();
		return imageUpperware;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ComponentMetricRelationship createComponentMetricRelationship() {
		ComponentMetricRelationshipImpl componentMetricRelationship = new ComponentMetricRelationshipImpl();
		return componentMetricRelationship;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public ApplicationPackage getApplicationPackage() {
		return (ApplicationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * 
	 */
	@Deprecated
	public static ApplicationPackage getPackage() {
		return ApplicationPackage.eINSTANCE;
	}

} //ApplicationFactoryImpl
