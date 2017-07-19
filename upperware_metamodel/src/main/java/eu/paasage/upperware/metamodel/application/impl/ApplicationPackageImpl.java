/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ActionUpperware;
import eu.paasage.upperware.metamodel.application.ApplicationComponent;
import eu.paasage.upperware.metamodel.application.ApplicationFactory;
import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.CloudMLElementUpperware;
import eu.paasage.upperware.metamodel.application.ComponentMetricRelationship;
import eu.paasage.upperware.metamodel.application.ConditionUpperware;
import eu.paasage.upperware.metamodel.application.Dimension;
import eu.paasage.upperware.metamodel.application.ElasticityRule;
import eu.paasage.upperware.metamodel.application.ImageUpperware;
import eu.paasage.upperware.metamodel.application.Memory;
import eu.paasage.upperware.metamodel.application.PaaSageGoal;
import eu.paasage.upperware.metamodel.application.PaaSageVariable;
import eu.paasage.upperware.metamodel.application.PaasageConfiguration;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.ProviderDimension;
import eu.paasage.upperware.metamodel.application.RequiredFeature;
import eu.paasage.upperware.metamodel.application.ResourceUpperware;
import eu.paasage.upperware.metamodel.application.Storage;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;

import eu.paasage.upperware.metamodel.cp.CpPackage;

import eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl;

import eu.paasage.upperware.metamodel.types.TypesPackage;

import eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl;

import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

import eu.paasage.upperware.metamodel.types.typesPaasage.impl.TypesPaasagePackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ApplicationPackageImpl extends EPackageImpl implements ApplicationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paasageConfigurationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass virtualMachineEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass virtualMachineProfileEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cloudMLElementUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass memoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass storageEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cpuEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providerEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass applicationComponentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elasticityRuleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass actionUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass conditionUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paaSageVariableEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass paaSageGoalEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass requiredFeatureEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dimensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass providerDimensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass imageUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass componentMetricRelationshipEClass = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ApplicationPackageImpl() {
		super(eNS_URI, ApplicationFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ApplicationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ApplicationPackage init() {
		if (isInited) return (ApplicationPackage)EPackage.Registry.INSTANCE.getEPackage(ApplicationPackage.eNS_URI);

		// Obtain or create and register package
		ApplicationPackageImpl theApplicationPackage = (ApplicationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ApplicationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ApplicationPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		CpPackageImpl theCpPackage = (CpPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CpPackage.eNS_URI) instanceof CpPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CpPackage.eNS_URI) : CpPackage.eINSTANCE);
		TypesPackageImpl theTypesPackage = (TypesPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI) instanceof TypesPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI) : TypesPackage.eINSTANCE);
		TypesPaasagePackageImpl theTypesPaasagePackage = (TypesPaasagePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypesPaasagePackage.eNS_URI) instanceof TypesPaasagePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypesPaasagePackage.eNS_URI) : TypesPaasagePackage.eINSTANCE);

		// Create package meta-data objects
		theApplicationPackage.createPackageContents();
		theCpPackage.createPackageContents();
		theTypesPackage.createPackageContents();
		theTypesPaasagePackage.createPackageContents();

		// Initialize created meta-data
		theApplicationPackage.initializePackageContents();
		theCpPackage.initializePackageContents();
		theTypesPackage.initializePackageContents();
		theTypesPaasagePackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theApplicationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ApplicationPackage.eNS_URI, theApplicationPackage);
		return theApplicationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPaasageConfiguration() {
		return paasageConfigurationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaasageConfiguration_Id() {
		return (EAttribute)paasageConfigurationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaasageConfiguration_Goals() {
		return (EReference)paasageConfigurationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaasageConfiguration_Variables() {
		return (EReference)paasageConfigurationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaasageConfiguration_Rules() {
		return (EReference)paasageConfigurationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaasageConfiguration_Components() {
		return (EReference)paasageConfigurationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaasageConfiguration_Providers() {
		return (EReference)paasageConfigurationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaasageConfiguration_VmProfiles() {
		return (EReference)paasageConfigurationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaasageConfiguration_AuxExpressions() {
		return (EReference)paasageConfigurationEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaasageConfiguration_Vms() {
		return (EReference)paasageConfigurationEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaasageConfiguration_MonitoredDimensions() {
		return (EReference)paasageConfigurationEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVirtualMachine() {
		return virtualMachineEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVirtualMachine_Id() {
		return (EAttribute)virtualMachineEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualMachine_Profile() {
		return (EReference)virtualMachineEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVirtualMachineProfile() {
		return virtualMachineProfileEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVirtualMachineProfile_Size() {
		return (EAttribute)virtualMachineProfileEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualMachineProfile_Memory() {
		return (EReference)virtualMachineProfileEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualMachineProfile_Storage() {
		return (EReference)virtualMachineProfileEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualMachineProfile_Cpu() {
		return (EReference)virtualMachineProfileEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualMachineProfile_Os() {
		return (EReference)virtualMachineProfileEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualMachineProfile_ProviderDimension() {
		return (EReference)virtualMachineProfileEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualMachineProfile_Location() {
		return (EReference)virtualMachineProfileEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getVirtualMachineProfile_Image() {
		return (EReference)virtualMachineProfileEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVirtualMachineProfile_RelatedCloudVMId() {
		return (EAttribute)virtualMachineProfileEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCloudMLElementUpperware() {
		return cloudMLElementUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCloudMLElementUpperware_CloudMLId() {
		return (EAttribute)cloudMLElementUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceUpperware() {
		return resourceUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceUpperware_Value() {
		return (EReference)resourceUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMemory() {
		return memoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMemory_Unit() {
		return (EAttribute)memoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStorage() {
		return storageEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStorage_Unit() {
		return (EAttribute)storageEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCPU() {
		return cpuEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCPU_Frequency() {
		return (EAttribute)cpuEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCPU_Cores() {
		return (EAttribute)cpuEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProvider() {
		return providerEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProvider_Id() {
		return (EAttribute)providerEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProvider_Location() {
		return (EReference)providerEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProvider_Type() {
		return (EReference)providerEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getApplicationComponent() {
		return applicationComponentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationComponent_Vm() {
		return (EReference)applicationComponentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationComponent_PreferredLocations() {
		return (EReference)applicationComponentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationComponent_RequiredProfile() {
		return (EReference)applicationComponentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApplicationComponent_Features() {
		return (EAttribute)applicationComponentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationComponent_RequiredFeatures() {
		return (EReference)applicationComponentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getApplicationComponent_PreferredProviders() {
		return (EReference)applicationComponentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApplicationComponent_Min() {
		return (EAttribute)applicationComponentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getApplicationComponent_Max() {
		return (EAttribute)applicationComponentEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElasticityRule() {
		return elasticityRuleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElasticityRule_Id() {
		return (EAttribute)elasticityRuleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElasticityRule_Action() {
		return (EReference)elasticityRuleEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElasticityRule_Condition() {
		return (EReference)elasticityRuleEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getActionUpperware() {
		return actionUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getActionUpperware_Parameters() {
		return (EAttribute)actionUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getActionUpperware_Type() {
		return (EReference)actionUpperwareEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConditionUpperware() {
		return conditionUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getConditionUpperware_Operator() {
		return (EAttribute)conditionUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionUpperware_Exp1() {
		return (EReference)conditionUpperwareEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getConditionUpperware_Exp2() {
		return (EReference)conditionUpperwareEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPaaSageVariable() {
		return paaSageVariableEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaaSageVariable_PaasageType() {
		return (EAttribute)paaSageVariableEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaaSageVariable_RelatedComponent() {
		return (EReference)paaSageVariableEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaaSageVariable_CpVariableId() {
		return (EAttribute)paaSageVariableEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaaSageVariable_RelatedProvider() {
		return (EReference)paaSageVariableEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaaSageVariable_RelatedVirtualMachineProfile() {
		return (EReference)paaSageVariableEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPaaSageGoal() {
		return paaSageGoalEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaaSageGoal_Id() {
		return (EAttribute)paaSageGoalEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaaSageGoal_Goal() {
		return (EAttribute)paaSageGoalEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaaSageGoal_Function() {
		return (EReference)paaSageGoalEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPaaSageGoal_ApplicationComponent() {
		return (EReference)paaSageGoalEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPaaSageGoal_ApplicationMetric() {
		return (EAttribute)paaSageGoalEClass.getEStructuralFeatures().get(4);
	}

	public EAttribute getPaaSageGoal_ExtOptimisationAttribute() {
		return (EAttribute)paaSageGoalEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRequiredFeature() {
		return requiredFeatureEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequiredFeature_Feature() {
		return (EAttribute)requiredFeatureEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRequiredFeature_ProvidedBy() {
		return (EReference)requiredFeatureEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequiredFeature_CommunicationType() {
		return (EAttribute)requiredFeatureEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequiredFeature_Optional() {
		return (EAttribute)requiredFeatureEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRequiredFeature_Contaiment() {
		return (EAttribute)requiredFeatureEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDimension() {
		return dimensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDimension_Id() {
		return (EAttribute)dimensionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProviderDimension() {
		return providerDimensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProviderDimension_Value() {
		return (EAttribute)providerDimensionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProviderDimension_Provider() {
		return (EReference)providerDimensionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProviderDimension_MetricID() {
		return (EAttribute)providerDimensionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImageUpperware() {
		return imageUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImageUpperware_Id() {
		return (EAttribute)imageUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getComponentMetricRelationship() {
		return componentMetricRelationshipEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getComponentMetricRelationship_Component() {
		return (EReference)componentMetricRelationshipEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getComponentMetricRelationship_MetricId() {
		return (EAttribute)componentMetricRelationshipEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationFactory getApplicationFactory() {
		return (ApplicationFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		paasageConfigurationEClass = createEClass(PAASAGE_CONFIGURATION);
		createEAttribute(paasageConfigurationEClass, PAASAGE_CONFIGURATION__ID);
		createEReference(paasageConfigurationEClass, PAASAGE_CONFIGURATION__GOALS);
		createEReference(paasageConfigurationEClass, PAASAGE_CONFIGURATION__VARIABLES);
		createEReference(paasageConfigurationEClass, PAASAGE_CONFIGURATION__RULES);
		createEReference(paasageConfigurationEClass, PAASAGE_CONFIGURATION__COMPONENTS);
		createEReference(paasageConfigurationEClass, PAASAGE_CONFIGURATION__PROVIDERS);
		createEReference(paasageConfigurationEClass, PAASAGE_CONFIGURATION__VM_PROFILES);
		createEReference(paasageConfigurationEClass, PAASAGE_CONFIGURATION__AUX_EXPRESSIONS);
		createEReference(paasageConfigurationEClass, PAASAGE_CONFIGURATION__VMS);
		createEReference(paasageConfigurationEClass, PAASAGE_CONFIGURATION__MONITORED_DIMENSIONS);

		virtualMachineEClass = createEClass(VIRTUAL_MACHINE);
		createEAttribute(virtualMachineEClass, VIRTUAL_MACHINE__ID);
		createEReference(virtualMachineEClass, VIRTUAL_MACHINE__PROFILE);

		virtualMachineProfileEClass = createEClass(VIRTUAL_MACHINE_PROFILE);
		createEAttribute(virtualMachineProfileEClass, VIRTUAL_MACHINE_PROFILE__SIZE);
		createEReference(virtualMachineProfileEClass, VIRTUAL_MACHINE_PROFILE__MEMORY);
		createEReference(virtualMachineProfileEClass, VIRTUAL_MACHINE_PROFILE__STORAGE);
		createEReference(virtualMachineProfileEClass, VIRTUAL_MACHINE_PROFILE__CPU);
		createEReference(virtualMachineProfileEClass, VIRTUAL_MACHINE_PROFILE__OS);
		createEReference(virtualMachineProfileEClass, VIRTUAL_MACHINE_PROFILE__PROVIDER_DIMENSION);
		createEReference(virtualMachineProfileEClass, VIRTUAL_MACHINE_PROFILE__LOCATION);
		createEReference(virtualMachineProfileEClass, VIRTUAL_MACHINE_PROFILE__IMAGE);
		createEAttribute(virtualMachineProfileEClass, VIRTUAL_MACHINE_PROFILE__RELATED_CLOUD_VM_ID);

		cloudMLElementUpperwareEClass = createEClass(CLOUD_ML_ELEMENT_UPPERWARE);
		createEAttribute(cloudMLElementUpperwareEClass, CLOUD_ML_ELEMENT_UPPERWARE__CLOUD_ML_ID);

		resourceUpperwareEClass = createEClass(RESOURCE_UPPERWARE);
		createEReference(resourceUpperwareEClass, RESOURCE_UPPERWARE__VALUE);

		memoryEClass = createEClass(MEMORY);
		createEAttribute(memoryEClass, MEMORY__UNIT);

		storageEClass = createEClass(STORAGE);
		createEAttribute(storageEClass, STORAGE__UNIT);

		cpuEClass = createEClass(CPU);
		createEAttribute(cpuEClass, CPU__FREQUENCY);
		createEAttribute(cpuEClass, CPU__CORES);

		providerEClass = createEClass(PROVIDER);
		createEAttribute(providerEClass, PROVIDER__ID);
		createEReference(providerEClass, PROVIDER__LOCATION);
		createEReference(providerEClass, PROVIDER__TYPE);

		applicationComponentEClass = createEClass(APPLICATION_COMPONENT);
		createEReference(applicationComponentEClass, APPLICATION_COMPONENT__VM);
		createEReference(applicationComponentEClass, APPLICATION_COMPONENT__PREFERRED_LOCATIONS);
		createEReference(applicationComponentEClass, APPLICATION_COMPONENT__REQUIRED_PROFILE);
		createEAttribute(applicationComponentEClass, APPLICATION_COMPONENT__FEATURES);
		createEReference(applicationComponentEClass, APPLICATION_COMPONENT__REQUIRED_FEATURES);
		createEReference(applicationComponentEClass, APPLICATION_COMPONENT__PREFERRED_PROVIDERS);
		createEAttribute(applicationComponentEClass, APPLICATION_COMPONENT__MIN);
		createEAttribute(applicationComponentEClass, APPLICATION_COMPONENT__MAX);

		elasticityRuleEClass = createEClass(ELASTICITY_RULE);
		createEAttribute(elasticityRuleEClass, ELASTICITY_RULE__ID);
		createEReference(elasticityRuleEClass, ELASTICITY_RULE__ACTION);
		createEReference(elasticityRuleEClass, ELASTICITY_RULE__CONDITION);

		actionUpperwareEClass = createEClass(ACTION_UPPERWARE);
		createEAttribute(actionUpperwareEClass, ACTION_UPPERWARE__PARAMETERS);
		createEReference(actionUpperwareEClass, ACTION_UPPERWARE__TYPE);

		conditionUpperwareEClass = createEClass(CONDITION_UPPERWARE);
		createEAttribute(conditionUpperwareEClass, CONDITION_UPPERWARE__OPERATOR);
		createEReference(conditionUpperwareEClass, CONDITION_UPPERWARE__EXP1);
		createEReference(conditionUpperwareEClass, CONDITION_UPPERWARE__EXP2);

		paaSageVariableEClass = createEClass(PAA_SAGE_VARIABLE);
		createEAttribute(paaSageVariableEClass, PAA_SAGE_VARIABLE__PAASAGE_TYPE);
		createEReference(paaSageVariableEClass, PAA_SAGE_VARIABLE__RELATED_COMPONENT);
		createEAttribute(paaSageVariableEClass, PAA_SAGE_VARIABLE__CP_VARIABLE_ID);
		createEReference(paaSageVariableEClass, PAA_SAGE_VARIABLE__RELATED_PROVIDER);
		createEReference(paaSageVariableEClass, PAA_SAGE_VARIABLE__RELATED_VIRTUAL_MACHINE_PROFILE);

		paaSageGoalEClass = createEClass(PAA_SAGE_GOAL);
		createEAttribute(paaSageGoalEClass, PAA_SAGE_GOAL__ID);
		createEAttribute(paaSageGoalEClass, PAA_SAGE_GOAL__GOAL);
		createEReference(paaSageGoalEClass, PAA_SAGE_GOAL__FUNCTION);
		createEReference(paaSageGoalEClass, PAA_SAGE_GOAL__APPLICATION_COMPONENT);
		createEAttribute(paaSageGoalEClass, PAA_SAGE_GOAL__APPLICATION_METRIC);
		createEAttribute(paaSageGoalEClass, PAA_SAGE_GOAL__EXT_OPTIMISATION_ATTRIBUTE);

		requiredFeatureEClass = createEClass(REQUIRED_FEATURE);
		createEAttribute(requiredFeatureEClass, REQUIRED_FEATURE__FEATURE);
		createEReference(requiredFeatureEClass, REQUIRED_FEATURE__PROVIDED_BY);
		createEAttribute(requiredFeatureEClass, REQUIRED_FEATURE__COMMUNICATION_TYPE);
		createEAttribute(requiredFeatureEClass, REQUIRED_FEATURE__OPTIONAL);
		createEAttribute(requiredFeatureEClass, REQUIRED_FEATURE__CONTAIMENT);

		dimensionEClass = createEClass(DIMENSION);
		createEAttribute(dimensionEClass, DIMENSION__ID);

		providerDimensionEClass = createEClass(PROVIDER_DIMENSION);
		createEAttribute(providerDimensionEClass, PROVIDER_DIMENSION__VALUE);
		createEReference(providerDimensionEClass, PROVIDER_DIMENSION__PROVIDER);
		createEAttribute(providerDimensionEClass, PROVIDER_DIMENSION__METRIC_ID);

		imageUpperwareEClass = createEClass(IMAGE_UPPERWARE);
		createEAttribute(imageUpperwareEClass, IMAGE_UPPERWARE__ID);

		componentMetricRelationshipEClass = createEClass(COMPONENT_METRIC_RELATIONSHIP);
		createEReference(componentMetricRelationshipEClass, COMPONENT_METRIC_RELATIONSHIP__COMPONENT);
		createEAttribute(componentMetricRelationshipEClass, COMPONENT_METRIC_RELATIONSHIP__METRIC_ID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		CpPackage theCpPackage = (CpPackage)EPackage.Registry.INSTANCE.getEPackage(CpPackage.eNS_URI);
		TypesPaasagePackage theTypesPaasagePackage = (TypesPaasagePackage)EPackage.Registry.INSTANCE.getEPackage(TypesPaasagePackage.eNS_URI);
		TypesPackage theTypesPackage = (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		virtualMachineEClass.getESuperTypes().add(theTypesPaasagePackage.getPaaSageCPElement());
		virtualMachineProfileEClass.getESuperTypes().add(this.getCloudMLElementUpperware());
		resourceUpperwareEClass.getESuperTypes().add(theTypesPaasagePackage.getPaaSageCPElement());
		memoryEClass.getESuperTypes().add(this.getResourceUpperware());
		storageEClass.getESuperTypes().add(this.getResourceUpperware());
		cpuEClass.getESuperTypes().add(this.getResourceUpperware());
		providerEClass.getESuperTypes().add(theTypesPaasagePackage.getPaaSageCPElement());
		applicationComponentEClass.getESuperTypes().add(this.getCloudMLElementUpperware());
		conditionUpperwareEClass.getESuperTypes().add(theCpPackage.getBooleanExpression());

		// Initialize classes, features, and operations; add parameters
		initEClass(paasageConfigurationEClass, PaasageConfiguration.class, "PaasageConfiguration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPaasageConfiguration_Id(), ecorePackage.getEString(), "id", null, 1, 1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaasageConfiguration_Goals(), this.getPaaSageGoal(), null, "goals", null, 0, -1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaasageConfiguration_Variables(), this.getPaaSageVariable(), null, "variables", null, 0, -1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaasageConfiguration_Rules(), this.getElasticityRule(), null, "rules", null, 0, -1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaasageConfiguration_Components(), this.getApplicationComponent(), null, "components", null, 0, -1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaasageConfiguration_Providers(), this.getProvider(), null, "providers", null, 1, -1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaasageConfiguration_VmProfiles(), this.getVirtualMachineProfile(), null, "vmProfiles", null, 0, -1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaasageConfiguration_AuxExpressions(), theCpPackage.getExpression(), null, "auxExpressions", null, 0, -1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaasageConfiguration_Vms(), this.getVirtualMachine(), null, "vms", null, 0, -1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaasageConfiguration_MonitoredDimensions(), this.getDimension(), null, "monitoredDimensions", null, 0, -1, PaasageConfiguration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(virtualMachineEClass, VirtualMachine.class, "VirtualMachine", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVirtualMachine_Id(), ecorePackage.getEString(), "id", null, 1, 1, VirtualMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVirtualMachine_Profile(), this.getVirtualMachineProfile(), null, "profile", null, 1, 1, VirtualMachine.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(virtualMachineProfileEClass, VirtualMachineProfile.class, "VirtualMachineProfile", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVirtualMachineProfile_Size(), theTypesPaasagePackage.getVMSizeEnum(), "size", null, 1, 1, VirtualMachineProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVirtualMachineProfile_Memory(), this.getMemory(), null, "memory", null, 0, 1, VirtualMachineProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVirtualMachineProfile_Storage(), this.getStorage(), null, "storage", null, 0, 1, VirtualMachineProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVirtualMachineProfile_Cpu(), this.getCPU(), null, "cpu", null, 0, 1, VirtualMachineProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVirtualMachineProfile_Os(), theTypesPaasagePackage.getOS(), null, "os", null, 0, 1, VirtualMachineProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVirtualMachineProfile_ProviderDimension(), this.getProviderDimension(), null, "providerDimension", null, 0, -1, VirtualMachineProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVirtualMachineProfile_Location(), theTypesPaasagePackage.getLocationUpperware(), null, "location", null, 0, 1, VirtualMachineProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getVirtualMachineProfile_Image(), this.getImageUpperware(), null, "image", null, 0, 1, VirtualMachineProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVirtualMachineProfile_RelatedCloudVMId(), ecorePackage.getEString(), "relatedCloudVMId", null, 1, 1, VirtualMachineProfile.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cloudMLElementUpperwareEClass, CloudMLElementUpperware.class, "CloudMLElementUpperware", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCloudMLElementUpperware_CloudMLId(), ecorePackage.getEString(), "cloudMLId", null, 0, 1, CloudMLElementUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceUpperwareEClass, ResourceUpperware.class, "ResourceUpperware", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceUpperware_Value(), theTypesPackage.getNumericValueUpperware(), null, "value", null, 1, 1, ResourceUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(memoryEClass, Memory.class, "Memory", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMemory_Unit(), theTypesPaasagePackage.getDataUnitEnum(), "unit", null, 0, 1, Memory.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(storageEClass, Storage.class, "Storage", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStorage_Unit(), theTypesPaasagePackage.getDataUnitEnum(), "unit", null, 0, 1, Storage.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cpuEClass, eu.paasage.upperware.metamodel.application.CPU.class, "CPU", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCPU_Frequency(), theTypesPaasagePackage.getFrequencyEnum(), "frequency", null, 0, 1, eu.paasage.upperware.metamodel.application.CPU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCPU_Cores(), ecorePackage.getEInt(), "cores", null, 1, 1, eu.paasage.upperware.metamodel.application.CPU.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(providerEClass, Provider.class, "Provider", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProvider_Id(), ecorePackage.getEString(), "id", null, 1, 1, Provider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProvider_Location(), theTypesPaasagePackage.getLocationUpperware(), null, "location", null, 0, 1, Provider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProvider_Type(), theTypesPaasagePackage.getProviderType(), null, "type", null, 1, 1, Provider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(applicationComponentEClass, ApplicationComponent.class, "ApplicationComponent", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getApplicationComponent_Vm(), this.getVirtualMachine(), null, "vm", null, 0, 1, ApplicationComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplicationComponent_PreferredLocations(), theTypesPaasagePackage.getLocationUpperware(), null, "preferredLocations", null, 0, -1, ApplicationComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplicationComponent_RequiredProfile(), this.getVirtualMachineProfile(), null, "requiredProfile", null, 0, -1, ApplicationComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApplicationComponent_Features(), ecorePackage.getEString(), "features", null, 0, -1, ApplicationComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplicationComponent_RequiredFeatures(), this.getRequiredFeature(), null, "requiredFeatures", null, 0, -1, ApplicationComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getApplicationComponent_PreferredProviders(), theTypesPaasagePackage.getProviderType(), null, "preferredProviders", null, 0, -1, ApplicationComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApplicationComponent_Min(), ecorePackage.getEInt(), "min", null, 1, 1, ApplicationComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getApplicationComponent_Max(), ecorePackage.getEInt(), "max", null, 1, 1, ApplicationComponent.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(elasticityRuleEClass, ElasticityRule.class, "ElasticityRule", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getElasticityRule_Id(), ecorePackage.getEString(), "id", null, 1, 1, ElasticityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElasticityRule_Action(), this.getActionUpperware(), null, "action", null, 1, 1, ElasticityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElasticityRule_Condition(), this.getConditionUpperware(), null, "condition", null, 1, 1, ElasticityRule.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(actionUpperwareEClass, ActionUpperware.class, "ActionUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getActionUpperware_Parameters(), ecorePackage.getEString(), "parameters", null, 0, -1, ActionUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getActionUpperware_Type(), theTypesPaasagePackage.getActionType(), null, "type", null, 1, 1, ActionUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(conditionUpperwareEClass, ConditionUpperware.class, "ConditionUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getConditionUpperware_Operator(), theTypesPaasagePackage.getLogicOperatorEnum(), "operator", null, 1, 1, ConditionUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConditionUpperware_Exp1(), theCpPackage.getBooleanExpression(), null, "exp1", null, 1, 1, ConditionUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getConditionUpperware_Exp2(), theCpPackage.getBooleanExpression(), null, "exp2", null, 0, 1, ConditionUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(paaSageVariableEClass, PaaSageVariable.class, "PaaSageVariable", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPaaSageVariable_PaasageType(), theTypesPaasagePackage.getVariableElementTypeEnum(), "paasageType", null, 0, 1, PaaSageVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaaSageVariable_RelatedComponent(), this.getApplicationComponent(), null, "relatedComponent", null, 0, 1, PaaSageVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaaSageVariable_CpVariableId(), ecorePackage.getEString(), "cpVariableId", null, 1, 1, PaaSageVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaaSageVariable_RelatedProvider(), this.getProvider(), null, "relatedProvider", null, 0, 1, PaaSageVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaaSageVariable_RelatedVirtualMachineProfile(), this.getVirtualMachineProfile(), null, "relatedVirtualMachineProfile", null, 0, 1, PaaSageVariable.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(paaSageGoalEClass, PaaSageGoal.class, "PaaSageGoal", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPaaSageGoal_Id(), ecorePackage.getEString(), "id", null, 1, 1, PaaSageGoal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaaSageGoal_Goal(), theCpPackage.getGoalOperatorEnum(), "goal", null, 1, 1, PaaSageGoal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaaSageGoal_Function(), theTypesPaasagePackage.getFunctionType(), null, "function", null, 1, 1, PaaSageGoal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPaaSageGoal_ApplicationComponent(), this.getComponentMetricRelationship(), null, "applicationComponent", null, 0, -1, PaaSageGoal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaaSageGoal_ApplicationMetric(), ecorePackage.getEString(), "applicationMetric", null, 0, 1, PaaSageGoal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPaaSageGoal_ExtOptimisationAttribute(), ecorePackage.getEString(), "optimisationAttribute", null, 1, 1, PaaSageGoal.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(requiredFeatureEClass, RequiredFeature.class, "RequiredFeature", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRequiredFeature_Feature(), ecorePackage.getEString(), "feature", null, 1, 1, RequiredFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRequiredFeature_ProvidedBy(), this.getCloudMLElementUpperware(), null, "providedBy", null, 1, 1, RequiredFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequiredFeature_CommunicationType(), theTypesPaasagePackage.getCommunicationTypeUpperware(), "communicationType", null, 1, 1, RequiredFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequiredFeature_Optional(), ecorePackage.getEBoolean(), "optional", null, 1, 1, RequiredFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRequiredFeature_Contaiment(), ecorePackage.getEBoolean(), "contaiment", null, 1, 1, RequiredFeature.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dimensionEClass, Dimension.class, "Dimension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDimension_Id(), ecorePackage.getEString(), "id", null, 1, 1, Dimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(providerDimensionEClass, ProviderDimension.class, "ProviderDimension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProviderDimension_Value(), ecorePackage.getEDouble(), "value", null, 0, 1, ProviderDimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProviderDimension_Provider(), this.getProvider(), null, "provider", null, 1, 1, ProviderDimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getProviderDimension_MetricID(), ecorePackage.getEString(), "metricID", "", 0, 1, ProviderDimension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(imageUpperwareEClass, ImageUpperware.class, "ImageUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImageUpperware_Id(), ecorePackage.getEString(), "id", null, 1, 1, ImageUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(componentMetricRelationshipEClass, ComponentMetricRelationship.class, "ComponentMetricRelationship", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getComponentMetricRelationship_Component(), this.getApplicationComponent(), null, "component", null, 1, 1, ComponentMetricRelationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getComponentMetricRelationship_MetricId(), ecorePackage.getEString(), "metricId", null, 0, 1, ComponentMetricRelationship.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Create resource
		createResource(eNS_URI);
	}

} //ApplicationPackageImpl
