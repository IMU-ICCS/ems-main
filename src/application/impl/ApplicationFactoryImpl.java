/**
 */
package application.impl;

import application.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ApplicationFactoryImpl extends EFactoryImpl implements ApplicationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
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
	 * @generated
	 */
	public ApplicationFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
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
			case ApplicationPackage.ACTION: return (EObject)createAction();
			case ApplicationPackage.CONDITION: return (EObject)createCondition();
			case ApplicationPackage.PAA_SAGE_VARIABLE: return (EObject)createPaaSageVariable();
			case ApplicationPackage.PAA_SAGE_GOAL: return (EObject)createPaaSageGoal();
			case ApplicationPackage.REQUIRED_FEATURE: return (EObject)createRequiredFeature();
			case ApplicationPackage.DIMENSION: return (EObject)createDimension();
			case ApplicationPackage.PROVIDER_COST: return (EObject)createProviderCost();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaasageConfiguration createPaasageConfiguration() {
		PaasageConfigurationImpl paasageConfiguration = new PaasageConfigurationImpl();
		return paasageConfiguration;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualMachine createVirtualMachine() {
		VirtualMachineImpl virtualMachine = new VirtualMachineImpl();
		return virtualMachine;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualMachineProfile createVirtualMachineProfile() {
		VirtualMachineProfileImpl virtualMachineProfile = new VirtualMachineProfileImpl();
		return virtualMachineProfile;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Memory createMemory() {
		MemoryImpl memory = new MemoryImpl();
		return memory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Storage createStorage() {
		StorageImpl storage = new StorageImpl();
		return storage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CPU createCPU() {
		CPUImpl cpu = new CPUImpl();
		return cpu;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Provider createProvider() {
		ProviderImpl provider = new ProviderImpl();
		return provider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationComponent createApplicationComponent() {
		ApplicationComponentImpl applicationComponent = new ApplicationComponentImpl();
		return applicationComponent;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElasticityRule createElasticityRule() {
		ElasticityRuleImpl elasticityRule = new ElasticityRuleImpl();
		return elasticityRule;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action createAction() {
		ActionImpl action = new ActionImpl();
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Condition createCondition() {
		ConditionImpl condition = new ConditionImpl();
		return condition;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaaSageVariable createPaaSageVariable() {
		PaaSageVariableImpl paaSageVariable = new PaaSageVariableImpl();
		return paaSageVariable;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PaaSageGoal createPaaSageGoal() {
		PaaSageGoalImpl paaSageGoal = new PaaSageGoalImpl();
		return paaSageGoal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredFeature createRequiredFeature() {
		RequiredFeatureImpl requiredFeature = new RequiredFeatureImpl();
		return requiredFeature;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Dimension createDimension() {
		DimensionImpl dimension = new DimensionImpl();
		return dimension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderCost createProviderCost() {
		ProviderCostImpl providerCost = new ProviderCostImpl();
		return providerCost;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationPackage getApplicationPackage() {
		return (ApplicationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ApplicationPackage getPackage() {
		return ApplicationPackage.eINSTANCE;
	}

} //ApplicationFactoryImpl
