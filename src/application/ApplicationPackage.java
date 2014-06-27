/**
 */
package application;

import cp.CpPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

import types.typesPaasage.TypesPaasagePackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see application.ApplicationFactory
 * @model kind="package"
 * @generated
 */
public interface ApplicationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "application";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://paasage.inria.fr/metamodel/application";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "application";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ApplicationPackage eINSTANCE = application.impl.ApplicationPackageImpl.init();

	/**
	 * The meta object id for the '{@link application.impl.PaasageConfigurationImpl <em>Paasage Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.PaasageConfigurationImpl
	 * @see application.impl.ApplicationPackageImpl#getPaasageConfiguration()
	 * @generated
	 */
	int PAASAGE_CONFIGURATION = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__ID = 0;

	/**
	 * The feature id for the '<em><b>Goals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__GOALS = 1;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__CONSTRAINTS = 2;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__VARIABLES = 3;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__RULES = 4;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__COMPONENTS = 5;

	/**
	 * The feature id for the '<em><b>Providers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__PROVIDERS = 6;

	/**
	 * The feature id for the '<em><b>Vm Profiles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__VM_PROFILES = 7;

	/**
	 * The feature id for the '<em><b>Aux Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__AUX_EXPRESSIONS = 8;

	/**
	 * The feature id for the '<em><b>Vms</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__VMS = 9;

	/**
	 * The feature id for the '<em><b>Monitored Dimensions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__MONITORED_DIMENSIONS = 10;

	/**
	 * The number of structural features of the '<em>Paasage Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION_FEATURE_COUNT = 11;

	/**
	 * The number of operations of the '<em>Paasage Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link application.impl.VirtualMachineImpl <em>Virtual Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.VirtualMachineImpl
	 * @see application.impl.ApplicationPackageImpl#getVirtualMachine()
	 * @generated
	 */
	int VIRTUAL_MACHINE = 1;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE__TYPE_ID = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE__ID = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Profile</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE__PROFILE = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Virtual Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_FEATURE_COUNT = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Virtual Machine</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_OPERATION_COUNT = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link application.impl.CloudML_ElementImpl <em>Cloud ML Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.CloudML_ElementImpl
	 * @see application.impl.ApplicationPackageImpl#getCloudML_Element()
	 * @generated
	 */
	int CLOUD_ML_ELEMENT = 3;

	/**
	 * The feature id for the '<em><b>Cloud ML Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT__CLOUD_ML_ID = 0;

	/**
	 * The number of structural features of the '<em>Cloud ML Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Cloud ML Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link application.impl.VirtualMachineProfileImpl <em>Virtual Machine Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.VirtualMachineProfileImpl
	 * @see application.impl.ApplicationPackageImpl#getVirtualMachineProfile()
	 * @generated
	 */
	int VIRTUAL_MACHINE_PROFILE = 2;

	/**
	 * The feature id for the '<em><b>Cloud ML Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__CLOUD_ML_ID = CLOUD_ML_ELEMENT__CLOUD_ML_ID;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__SIZE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Memory</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__MEMORY = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Storage</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__STORAGE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Cpu</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__CPU = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Os</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__OS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__PROVIDER = CLOUD_ML_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Virtual Machine Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Virtual Machine Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link application.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.ResourceImpl
	 * @see application.impl.ApplicationPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 4;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__TYPE_ID = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE__VALUE = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_OPERATION_COUNT = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link application.impl.MemoryImpl <em>Memory</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.MemoryImpl
	 * @see application.impl.ApplicationPackageImpl#getMemory()
	 * @generated
	 */
	int MEMORY = 5;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY__TYPE_ID = RESOURCE__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY__VALUE = RESOURCE__VALUE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY__UNIT = RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Memory</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Memory</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_OPERATION_COUNT = RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link application.impl.StorageImpl <em>Storage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.StorageImpl
	 * @see application.impl.ApplicationPackageImpl#getStorage()
	 * @generated
	 */
	int STORAGE = 6;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE__TYPE_ID = RESOURCE__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE__VALUE = RESOURCE__VALUE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE__UNIT = RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Storage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Storage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_OPERATION_COUNT = RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link application.impl.CPUImpl <em>CPU</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.CPUImpl
	 * @see application.impl.ApplicationPackageImpl#getCPU()
	 * @generated
	 */
	int CPU = 7;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU__TYPE_ID = RESOURCE__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU__VALUE = RESOURCE__VALUE;

	/**
	 * The feature id for the '<em><b>Frequency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU__FREQUENCY = RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU__CORES = RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>CPU</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>CPU</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU_OPERATION_COUNT = RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link application.impl.ProviderImpl <em>Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.ProviderImpl
	 * @see application.impl.ApplicationPackageImpl#getProvider()
	 * @generated
	 */
	int PROVIDER = 8;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER__TYPE_ID = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER__ID = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER__LOCATION = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER__TYPE = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_FEATURE_COUNT = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_OPERATION_COUNT = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link application.impl.ApplicationComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.ApplicationComponentImpl
	 * @see application.impl.ApplicationPackageImpl#getApplicationComponent()
	 * @generated
	 */
	int APPLICATION_COMPONENT = 9;

	/**
	 * The feature id for the '<em><b>Cloud ML Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__CLOUD_ML_ID = CLOUD_ML_ELEMENT__CLOUD_ML_ID;

	/**
	 * The feature id for the '<em><b>Vm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__VM = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Preferred Locations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__PREFERRED_LOCATIONS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Required Profile</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__REQUIRED_PROFILE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Profile</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__PROFILE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Features</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__FEATURES = CLOUD_ML_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Required Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__REQUIRED_FEATURES = CLOUD_ML_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Preferred Providers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__PREFERRED_PROVIDERS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of operations of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link application.impl.ElasticityRuleImpl <em>Elasticity Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.ElasticityRuleImpl
	 * @see application.impl.ApplicationPackageImpl#getElasticityRule()
	 * @generated
	 */
	int ELASTICITY_RULE = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELASTICITY_RULE__ID = 0;

	/**
	 * The feature id for the '<em><b>Action</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELASTICITY_RULE__ACTION = 1;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELASTICITY_RULE__CONDITION = 2;

	/**
	 * The number of structural features of the '<em>Elasticity Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELASTICITY_RULE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Elasticity Rule</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELASTICITY_RULE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link application.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.ActionImpl
	 * @see application.impl.ApplicationPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 11;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__PARAMETERS = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Action</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link application.impl.ConditionImpl <em>Condition</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.ConditionImpl
	 * @see application.impl.ApplicationPackageImpl#getCondition()
	 * @generated
	 */
	int CONDITION = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__ID = CpPackage.BOOLEAN_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__OPERATOR = CpPackage.BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exp1</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__EXP1 = CpPackage.BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Exp2</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION__EXP2 = CpPackage.BOOLEAN_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_FEATURE_COUNT = CpPackage.BOOLEAN_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Condition</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_OPERATION_COUNT = CpPackage.BOOLEAN_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link application.impl.PaaSageVariableImpl <em>Paa Sage Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.PaaSageVariableImpl
	 * @see application.impl.ApplicationPackageImpl#getPaaSageVariable()
	 * @generated
	 */
	int PAA_SAGE_VARIABLE = 13;

	/**
	 * The feature id for the '<em><b>Paasage Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE__PAASAGE_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Related Elements</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE__RELATED_ELEMENTS = 1;

	/**
	 * The feature id for the '<em><b>Cp Variable Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE__CP_VARIABLE_ID = 2;

	/**
	 * The number of structural features of the '<em>Paa Sage Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Paa Sage Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link application.impl.PaaSageGoalImpl <em>Paa Sage Goal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.PaaSageGoalImpl
	 * @see application.impl.ApplicationPackageImpl#getPaaSageGoal()
	 * @generated
	 */
	int PAA_SAGE_GOAL = 14;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_GOAL__ID = 0;

	/**
	 * The feature id for the '<em><b>Goal</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_GOAL__GOAL = 1;

	/**
	 * The feature id for the '<em><b>Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_GOAL__FUNCTION = 2;

	/**
	 * The number of structural features of the '<em>Paa Sage Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_GOAL_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Paa Sage Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_GOAL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link application.impl.RequiredFeatureImpl <em>Required Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.RequiredFeatureImpl
	 * @see application.impl.ApplicationPackageImpl#getRequiredFeature()
	 * @generated
	 */
	int REQUIRED_FEATURE = 15;

	/**
	 * The feature id for the '<em><b>Feature</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE__FEATURE = 0;

	/**
	 * The feature id for the '<em><b>Provided By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE__PROVIDED_BY = 1;

	/**
	 * The feature id for the '<em><b>Remote</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE__REMOTE = 2;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE__OPTIONAL = 3;

	/**
	 * The number of structural features of the '<em>Required Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Required Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link application.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.DimensionImpl
	 * @see application.impl.ApplicationPackageImpl#getDimension()
	 * @generated
	 */
	int DIMENSION = 16;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION__ID = 0;

	/**
	 * The number of structural features of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DIMENSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link application.impl.ProviderCostImpl <em>Provider Cost</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see application.impl.ProviderCostImpl
	 * @see application.impl.ApplicationPackageImpl#getProviderCost()
	 * @generated
	 */
	int PROVIDER_COST = 17;

	/**
	 * The feature id for the '<em><b>Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_COST__COST = 0;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_COST__PROVIDER = 1;

	/**
	 * The number of structural features of the '<em>Provider Cost</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_COST_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Provider Cost</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_COST_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link application.PaasageConfiguration <em>Paasage Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paasage Configuration</em>'.
	 * @see application.PaasageConfiguration
	 * @generated
	 */
	EClass getPaasageConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link application.PaasageConfiguration#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see application.PaasageConfiguration#getId()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EAttribute getPaasageConfiguration_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link application.PaasageConfiguration#getGoals <em>Goals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Goals</em>'.
	 * @see application.PaasageConfiguration#getGoals()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Goals();

	/**
	 * Returns the meta object for the containment reference list '{@link application.PaasageConfiguration#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see application.PaasageConfiguration#getConstraints()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Constraints();

	/**
	 * Returns the meta object for the containment reference list '{@link application.PaasageConfiguration#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see application.PaasageConfiguration#getVariables()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Variables();

	/**
	 * Returns the meta object for the containment reference list '{@link application.PaasageConfiguration#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see application.PaasageConfiguration#getRules()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Rules();

	/**
	 * Returns the meta object for the containment reference list '{@link application.PaasageConfiguration#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see application.PaasageConfiguration#getComponents()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Components();

	/**
	 * Returns the meta object for the containment reference list '{@link application.PaasageConfiguration#getProviders <em>Providers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Providers</em>'.
	 * @see application.PaasageConfiguration#getProviders()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Providers();

	/**
	 * Returns the meta object for the containment reference list '{@link application.PaasageConfiguration#getVmProfiles <em>Vm Profiles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vm Profiles</em>'.
	 * @see application.PaasageConfiguration#getVmProfiles()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_VmProfiles();

	/**
	 * Returns the meta object for the containment reference list '{@link application.PaasageConfiguration#getAuxExpressions <em>Aux Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Aux Expressions</em>'.
	 * @see application.PaasageConfiguration#getAuxExpressions()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_AuxExpressions();

	/**
	 * Returns the meta object for the containment reference list '{@link application.PaasageConfiguration#getVms <em>Vms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vms</em>'.
	 * @see application.PaasageConfiguration#getVms()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Vms();

	/**
	 * Returns the meta object for the reference list '{@link application.PaasageConfiguration#getMonitoredDimensions <em>Monitored Dimensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Monitored Dimensions</em>'.
	 * @see application.PaasageConfiguration#getMonitoredDimensions()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_MonitoredDimensions();

	/**
	 * Returns the meta object for class '{@link application.VirtualMachine <em>Virtual Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Virtual Machine</em>'.
	 * @see application.VirtualMachine
	 * @generated
	 */
	EClass getVirtualMachine();

	/**
	 * Returns the meta object for the attribute '{@link application.VirtualMachine#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see application.VirtualMachine#getId()
	 * @see #getVirtualMachine()
	 * @generated
	 */
	EAttribute getVirtualMachine_Id();

	/**
	 * Returns the meta object for the reference '{@link application.VirtualMachine#getProfile <em>Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Profile</em>'.
	 * @see application.VirtualMachine#getProfile()
	 * @see #getVirtualMachine()
	 * @generated
	 */
	EReference getVirtualMachine_Profile();

	/**
	 * Returns the meta object for class '{@link application.VirtualMachineProfile <em>Virtual Machine Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Virtual Machine Profile</em>'.
	 * @see application.VirtualMachineProfile
	 * @generated
	 */
	EClass getVirtualMachineProfile();

	/**
	 * Returns the meta object for the attribute '{@link application.VirtualMachineProfile#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see application.VirtualMachineProfile#getSize()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EAttribute getVirtualMachineProfile_Size();

	/**
	 * Returns the meta object for the containment reference '{@link application.VirtualMachineProfile#getMemory <em>Memory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Memory</em>'.
	 * @see application.VirtualMachineProfile#getMemory()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Memory();

	/**
	 * Returns the meta object for the containment reference '{@link application.VirtualMachineProfile#getStorage <em>Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Storage</em>'.
	 * @see application.VirtualMachineProfile#getStorage()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Storage();

	/**
	 * Returns the meta object for the containment reference '{@link application.VirtualMachineProfile#getCpu <em>Cpu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cpu</em>'.
	 * @see application.VirtualMachineProfile#getCpu()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Cpu();

	/**
	 * Returns the meta object for the reference '{@link application.VirtualMachineProfile#getOs <em>Os</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Os</em>'.
	 * @see application.VirtualMachineProfile#getOs()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Os();

	/**
	 * Returns the meta object for the containment reference '{@link application.VirtualMachineProfile#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Provider</em>'.
	 * @see application.VirtualMachineProfile#getProvider()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Provider();

	/**
	 * Returns the meta object for class '{@link application.CloudML_Element <em>Cloud ML Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cloud ML Element</em>'.
	 * @see application.CloudML_Element
	 * @generated
	 */
	EClass getCloudML_Element();

	/**
	 * Returns the meta object for the attribute '{@link application.CloudML_Element#getCloudMLId <em>Cloud ML Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cloud ML Id</em>'.
	 * @see application.CloudML_Element#getCloudMLId()
	 * @see #getCloudML_Element()
	 * @generated
	 */
	EAttribute getCloudML_Element_CloudMLId();

	/**
	 * Returns the meta object for class '{@link application.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see application.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the containment reference '{@link application.Resource#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see application.Resource#getValue()
	 * @see #getResource()
	 * @generated
	 */
	EReference getResource_Value();

	/**
	 * Returns the meta object for class '{@link application.Memory <em>Memory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Memory</em>'.
	 * @see application.Memory
	 * @generated
	 */
	EClass getMemory();

	/**
	 * Returns the meta object for the attribute '{@link application.Memory#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see application.Memory#getUnit()
	 * @see #getMemory()
	 * @generated
	 */
	EAttribute getMemory_Unit();

	/**
	 * Returns the meta object for class '{@link application.Storage <em>Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Storage</em>'.
	 * @see application.Storage
	 * @generated
	 */
	EClass getStorage();

	/**
	 * Returns the meta object for the attribute '{@link application.Storage#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see application.Storage#getUnit()
	 * @see #getStorage()
	 * @generated
	 */
	EAttribute getStorage_Unit();

	/**
	 * Returns the meta object for class '{@link application.CPU <em>CPU</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CPU</em>'.
	 * @see application.CPU
	 * @generated
	 */
	EClass getCPU();

	/**
	 * Returns the meta object for the attribute '{@link application.CPU#getFrequency <em>Frequency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Frequency</em>'.
	 * @see application.CPU#getFrequency()
	 * @see #getCPU()
	 * @generated
	 */
	EAttribute getCPU_Frequency();

	/**
	 * Returns the meta object for the attribute '{@link application.CPU#getCores <em>Cores</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cores</em>'.
	 * @see application.CPU#getCores()
	 * @see #getCPU()
	 * @generated
	 */
	EAttribute getCPU_Cores();

	/**
	 * Returns the meta object for class '{@link application.Provider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provider</em>'.
	 * @see application.Provider
	 * @generated
	 */
	EClass getProvider();

	/**
	 * Returns the meta object for the attribute '{@link application.Provider#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see application.Provider#getId()
	 * @see #getProvider()
	 * @generated
	 */
	EAttribute getProvider_Id();

	/**
	 * Returns the meta object for the reference '{@link application.Provider#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Location</em>'.
	 * @see application.Provider#getLocation()
	 * @see #getProvider()
	 * @generated
	 */
	EReference getProvider_Location();

	/**
	 * Returns the meta object for the reference '{@link application.Provider#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see application.Provider#getType()
	 * @see #getProvider()
	 * @generated
	 */
	EReference getProvider_Type();

	/**
	 * Returns the meta object for class '{@link application.ApplicationComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see application.ApplicationComponent
	 * @generated
	 */
	EClass getApplicationComponent();

	/**
	 * Returns the meta object for the reference '{@link application.ApplicationComponent#getVm <em>Vm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Vm</em>'.
	 * @see application.ApplicationComponent#getVm()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_Vm();

	/**
	 * Returns the meta object for the reference list '{@link application.ApplicationComponent#getPreferredLocations <em>Preferred Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Preferred Locations</em>'.
	 * @see application.ApplicationComponent#getPreferredLocations()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_PreferredLocations();

	/**
	 * Returns the meta object for the reference '{@link application.ApplicationComponent#getRequiredProfile <em>Required Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Required Profile</em>'.
	 * @see application.ApplicationComponent#getRequiredProfile()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_RequiredProfile();

	/**
	 * Returns the meta object for the reference '{@link application.ApplicationComponent#getProfile <em>Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Profile</em>'.
	 * @see application.ApplicationComponent#getProfile()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_Profile();

	/**
	 * Returns the meta object for the attribute list '{@link application.ApplicationComponent#getFeatures <em>Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Features</em>'.
	 * @see application.ApplicationComponent#getFeatures()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EAttribute getApplicationComponent_Features();

	/**
	 * Returns the meta object for the containment reference list '{@link application.ApplicationComponent#getRequiredFeatures <em>Required Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Required Features</em>'.
	 * @see application.ApplicationComponent#getRequiredFeatures()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_RequiredFeatures();

	/**
	 * Returns the meta object for the reference list '{@link application.ApplicationComponent#getPreferredProviders <em>Preferred Providers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Preferred Providers</em>'.
	 * @see application.ApplicationComponent#getPreferredProviders()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_PreferredProviders();

	/**
	 * Returns the meta object for class '{@link application.ElasticityRule <em>Elasticity Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Elasticity Rule</em>'.
	 * @see application.ElasticityRule
	 * @generated
	 */
	EClass getElasticityRule();

	/**
	 * Returns the meta object for the attribute '{@link application.ElasticityRule#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see application.ElasticityRule#getId()
	 * @see #getElasticityRule()
	 * @generated
	 */
	EAttribute getElasticityRule_Id();

	/**
	 * Returns the meta object for the containment reference '{@link application.ElasticityRule#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action</em>'.
	 * @see application.ElasticityRule#getAction()
	 * @see #getElasticityRule()
	 * @generated
	 */
	EReference getElasticityRule_Action();

	/**
	 * Returns the meta object for the containment reference '{@link application.ElasticityRule#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see application.ElasticityRule#getCondition()
	 * @see #getElasticityRule()
	 * @generated
	 */
	EReference getElasticityRule_Condition();

	/**
	 * Returns the meta object for class '{@link application.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see application.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the attribute list '{@link application.Action#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameters</em>'.
	 * @see application.Action#getParameters()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Parameters();

	/**
	 * Returns the meta object for the reference '{@link application.Action#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see application.Action#getType()
	 * @see #getAction()
	 * @generated
	 */
	EReference getAction_Type();

	/**
	 * Returns the meta object for class '{@link application.Condition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition</em>'.
	 * @see application.Condition
	 * @generated
	 */
	EClass getCondition();

	/**
	 * Returns the meta object for the attribute '{@link application.Condition#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see application.Condition#getOperator()
	 * @see #getCondition()
	 * @generated
	 */
	EAttribute getCondition_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link application.Condition#getExp1 <em>Exp1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exp1</em>'.
	 * @see application.Condition#getExp1()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_Exp1();

	/**
	 * Returns the meta object for the containment reference '{@link application.Condition#getExp2 <em>Exp2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exp2</em>'.
	 * @see application.Condition#getExp2()
	 * @see #getCondition()
	 * @generated
	 */
	EReference getCondition_Exp2();

	/**
	 * Returns the meta object for class '{@link application.PaaSageVariable <em>Paa Sage Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paa Sage Variable</em>'.
	 * @see application.PaaSageVariable
	 * @generated
	 */
	EClass getPaaSageVariable();

	/**
	 * Returns the meta object for the attribute '{@link application.PaaSageVariable#getPaasageType <em>Paasage Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Paasage Type</em>'.
	 * @see application.PaaSageVariable#getPaasageType()
	 * @see #getPaaSageVariable()
	 * @generated
	 */
	EAttribute getPaaSageVariable_PaasageType();

	/**
	 * Returns the meta object for the reference list '{@link application.PaaSageVariable#getRelatedElements <em>Related Elements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Related Elements</em>'.
	 * @see application.PaaSageVariable#getRelatedElements()
	 * @see #getPaaSageVariable()
	 * @generated
	 */
	EReference getPaaSageVariable_RelatedElements();

	/**
	 * Returns the meta object for the attribute '{@link application.PaaSageVariable#getCpVariableId <em>Cp Variable Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cp Variable Id</em>'.
	 * @see application.PaaSageVariable#getCpVariableId()
	 * @see #getPaaSageVariable()
	 * @generated
	 */
	EAttribute getPaaSageVariable_CpVariableId();

	/**
	 * Returns the meta object for class '{@link application.PaaSageGoal <em>Paa Sage Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paa Sage Goal</em>'.
	 * @see application.PaaSageGoal
	 * @generated
	 */
	EClass getPaaSageGoal();

	/**
	 * Returns the meta object for the attribute '{@link application.PaaSageGoal#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see application.PaaSageGoal#getId()
	 * @see #getPaaSageGoal()
	 * @generated
	 */
	EAttribute getPaaSageGoal_Id();

	/**
	 * Returns the meta object for the attribute '{@link application.PaaSageGoal#getGoal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Goal</em>'.
	 * @see application.PaaSageGoal#getGoal()
	 * @see #getPaaSageGoal()
	 * @generated
	 */
	EAttribute getPaaSageGoal_Goal();

	/**
	 * Returns the meta object for the reference '{@link application.PaaSageGoal#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Function</em>'.
	 * @see application.PaaSageGoal#getFunction()
	 * @see #getPaaSageGoal()
	 * @generated
	 */
	EReference getPaaSageGoal_Function();

	/**
	 * Returns the meta object for class '{@link application.RequiredFeature <em>Required Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Feature</em>'.
	 * @see application.RequiredFeature
	 * @generated
	 */
	EClass getRequiredFeature();

	/**
	 * Returns the meta object for the attribute '{@link application.RequiredFeature#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature</em>'.
	 * @see application.RequiredFeature#getFeature()
	 * @see #getRequiredFeature()
	 * @generated
	 */
	EAttribute getRequiredFeature_Feature();

	/**
	 * Returns the meta object for the reference '{@link application.RequiredFeature#getProvidedBy <em>Provided By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provided By</em>'.
	 * @see application.RequiredFeature#getProvidedBy()
	 * @see #getRequiredFeature()
	 * @generated
	 */
	EReference getRequiredFeature_ProvidedBy();

	/**
	 * Returns the meta object for the attribute '{@link application.RequiredFeature#isRemote <em>Remote</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Remote</em>'.
	 * @see application.RequiredFeature#isRemote()
	 * @see #getRequiredFeature()
	 * @generated
	 */
	EAttribute getRequiredFeature_Remote();

	/**
	 * Returns the meta object for the attribute '{@link application.RequiredFeature#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see application.RequiredFeature#isOptional()
	 * @see #getRequiredFeature()
	 * @generated
	 */
	EAttribute getRequiredFeature_Optional();

	/**
	 * Returns the meta object for class '{@link application.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see application.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the attribute '{@link application.Dimension#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see application.Dimension#getId()
	 * @see #getDimension()
	 * @generated
	 */
	EAttribute getDimension_Id();

	/**
	 * Returns the meta object for class '{@link application.ProviderCost <em>Provider Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provider Cost</em>'.
	 * @see application.ProviderCost
	 * @generated
	 */
	EClass getProviderCost();

	/**
	 * Returns the meta object for the attribute '{@link application.ProviderCost#getCost <em>Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cost</em>'.
	 * @see application.ProviderCost#getCost()
	 * @see #getProviderCost()
	 * @generated
	 */
	EAttribute getProviderCost_Cost();

	/**
	 * Returns the meta object for the reference '{@link application.ProviderCost#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provider</em>'.
	 * @see application.ProviderCost#getProvider()
	 * @see #getProviderCost()
	 * @generated
	 */
	EReference getProviderCost_Provider();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ApplicationFactory getApplicationFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link application.impl.PaasageConfigurationImpl <em>Paasage Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.PaasageConfigurationImpl
		 * @see application.impl.ApplicationPackageImpl#getPaasageConfiguration()
		 * @generated
		 */
		EClass PAASAGE_CONFIGURATION = eINSTANCE.getPaasageConfiguration();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAASAGE_CONFIGURATION__ID = eINSTANCE.getPaasageConfiguration_Id();

		/**
		 * The meta object literal for the '<em><b>Goals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__GOALS = eINSTANCE.getPaasageConfiguration_Goals();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__CONSTRAINTS = eINSTANCE.getPaasageConfiguration_Constraints();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__VARIABLES = eINSTANCE.getPaasageConfiguration_Variables();

		/**
		 * The meta object literal for the '<em><b>Rules</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__RULES = eINSTANCE.getPaasageConfiguration_Rules();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__COMPONENTS = eINSTANCE.getPaasageConfiguration_Components();

		/**
		 * The meta object literal for the '<em><b>Providers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__PROVIDERS = eINSTANCE.getPaasageConfiguration_Providers();

		/**
		 * The meta object literal for the '<em><b>Vm Profiles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__VM_PROFILES = eINSTANCE.getPaasageConfiguration_VmProfiles();

		/**
		 * The meta object literal for the '<em><b>Aux Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__AUX_EXPRESSIONS = eINSTANCE.getPaasageConfiguration_AuxExpressions();

		/**
		 * The meta object literal for the '<em><b>Vms</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__VMS = eINSTANCE.getPaasageConfiguration_Vms();

		/**
		 * The meta object literal for the '<em><b>Monitored Dimensions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAASAGE_CONFIGURATION__MONITORED_DIMENSIONS = eINSTANCE.getPaasageConfiguration_MonitoredDimensions();

		/**
		 * The meta object literal for the '{@link application.impl.VirtualMachineImpl <em>Virtual Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.VirtualMachineImpl
		 * @see application.impl.ApplicationPackageImpl#getVirtualMachine()
		 * @generated
		 */
		EClass VIRTUAL_MACHINE = eINSTANCE.getVirtualMachine();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIRTUAL_MACHINE__ID = eINSTANCE.getVirtualMachine_Id();

		/**
		 * The meta object literal for the '<em><b>Profile</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE__PROFILE = eINSTANCE.getVirtualMachine_Profile();

		/**
		 * The meta object literal for the '{@link application.impl.VirtualMachineProfileImpl <em>Virtual Machine Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.VirtualMachineProfileImpl
		 * @see application.impl.ApplicationPackageImpl#getVirtualMachineProfile()
		 * @generated
		 */
		EClass VIRTUAL_MACHINE_PROFILE = eINSTANCE.getVirtualMachineProfile();

		/**
		 * The meta object literal for the '<em><b>Size</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIRTUAL_MACHINE_PROFILE__SIZE = eINSTANCE.getVirtualMachineProfile_Size();

		/**
		 * The meta object literal for the '<em><b>Memory</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE_PROFILE__MEMORY = eINSTANCE.getVirtualMachineProfile_Memory();

		/**
		 * The meta object literal for the '<em><b>Storage</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE_PROFILE__STORAGE = eINSTANCE.getVirtualMachineProfile_Storage();

		/**
		 * The meta object literal for the '<em><b>Cpu</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE_PROFILE__CPU = eINSTANCE.getVirtualMachineProfile_Cpu();

		/**
		 * The meta object literal for the '<em><b>Os</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE_PROFILE__OS = eINSTANCE.getVirtualMachineProfile_Os();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE_PROFILE__PROVIDER = eINSTANCE.getVirtualMachineProfile_Provider();

		/**
		 * The meta object literal for the '{@link application.impl.CloudML_ElementImpl <em>Cloud ML Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.CloudML_ElementImpl
		 * @see application.impl.ApplicationPackageImpl#getCloudML_Element()
		 * @generated
		 */
		EClass CLOUD_ML_ELEMENT = eINSTANCE.getCloudML_Element();

		/**
		 * The meta object literal for the '<em><b>Cloud ML Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_ML_ELEMENT__CLOUD_ML_ID = eINSTANCE.getCloudML_Element_CloudMLId();

		/**
		 * The meta object literal for the '{@link application.impl.ResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.ResourceImpl
		 * @see application.impl.ApplicationPackageImpl#getResource()
		 * @generated
		 */
		EClass RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE__VALUE = eINSTANCE.getResource_Value();

		/**
		 * The meta object literal for the '{@link application.impl.MemoryImpl <em>Memory</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.MemoryImpl
		 * @see application.impl.ApplicationPackageImpl#getMemory()
		 * @generated
		 */
		EClass MEMORY = eINSTANCE.getMemory();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute MEMORY__UNIT = eINSTANCE.getMemory_Unit();

		/**
		 * The meta object literal for the '{@link application.impl.StorageImpl <em>Storage</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.StorageImpl
		 * @see application.impl.ApplicationPackageImpl#getStorage()
		 * @generated
		 */
		EClass STORAGE = eINSTANCE.getStorage();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STORAGE__UNIT = eINSTANCE.getStorage_Unit();

		/**
		 * The meta object literal for the '{@link application.impl.CPUImpl <em>CPU</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.CPUImpl
		 * @see application.impl.ApplicationPackageImpl#getCPU()
		 * @generated
		 */
		EClass CPU = eINSTANCE.getCPU();

		/**
		 * The meta object literal for the '<em><b>Frequency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CPU__FREQUENCY = eINSTANCE.getCPU_Frequency();

		/**
		 * The meta object literal for the '<em><b>Cores</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CPU__CORES = eINSTANCE.getCPU_Cores();

		/**
		 * The meta object literal for the '{@link application.impl.ProviderImpl <em>Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.ProviderImpl
		 * @see application.impl.ApplicationPackageImpl#getProvider()
		 * @generated
		 */
		EClass PROVIDER = eINSTANCE.getProvider();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROVIDER__ID = eINSTANCE.getProvider_Id();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDER__LOCATION = eINSTANCE.getProvider_Location();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDER__TYPE = eINSTANCE.getProvider_Type();

		/**
		 * The meta object literal for the '{@link application.impl.ApplicationComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.ApplicationComponentImpl
		 * @see application.impl.ApplicationPackageImpl#getApplicationComponent()
		 * @generated
		 */
		EClass APPLICATION_COMPONENT = eINSTANCE.getApplicationComponent();

		/**
		 * The meta object literal for the '<em><b>Vm</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT__VM = eINSTANCE.getApplicationComponent_Vm();

		/**
		 * The meta object literal for the '<em><b>Preferred Locations</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT__PREFERRED_LOCATIONS = eINSTANCE.getApplicationComponent_PreferredLocations();

		/**
		 * The meta object literal for the '<em><b>Required Profile</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT__REQUIRED_PROFILE = eINSTANCE.getApplicationComponent_RequiredProfile();

		/**
		 * The meta object literal for the '<em><b>Profile</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT__PROFILE = eINSTANCE.getApplicationComponent_Profile();

		/**
		 * The meta object literal for the '<em><b>Features</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLICATION_COMPONENT__FEATURES = eINSTANCE.getApplicationComponent_Features();

		/**
		 * The meta object literal for the '<em><b>Required Features</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT__REQUIRED_FEATURES = eINSTANCE.getApplicationComponent_RequiredFeatures();

		/**
		 * The meta object literal for the '<em><b>Preferred Providers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT__PREFERRED_PROVIDERS = eINSTANCE.getApplicationComponent_PreferredProviders();

		/**
		 * The meta object literal for the '{@link application.impl.ElasticityRuleImpl <em>Elasticity Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.ElasticityRuleImpl
		 * @see application.impl.ApplicationPackageImpl#getElasticityRule()
		 * @generated
		 */
		EClass ELASTICITY_RULE = eINSTANCE.getElasticityRule();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELASTICITY_RULE__ID = eINSTANCE.getElasticityRule_Id();

		/**
		 * The meta object literal for the '<em><b>Action</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELASTICITY_RULE__ACTION = eINSTANCE.getElasticityRule_Action();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELASTICITY_RULE__CONDITION = eINSTANCE.getElasticityRule_Condition();

		/**
		 * The meta object literal for the '{@link application.impl.ActionImpl <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.ActionImpl
		 * @see application.impl.ApplicationPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__PARAMETERS = eINSTANCE.getAction_Parameters();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION__TYPE = eINSTANCE.getAction_Type();

		/**
		 * The meta object literal for the '{@link application.impl.ConditionImpl <em>Condition</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.ConditionImpl
		 * @see application.impl.ApplicationPackageImpl#getCondition()
		 * @generated
		 */
		EClass CONDITION = eINSTANCE.getCondition();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION__OPERATOR = eINSTANCE.getCondition_Operator();

		/**
		 * The meta object literal for the '<em><b>Exp1</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__EXP1 = eINSTANCE.getCondition_Exp1();

		/**
		 * The meta object literal for the '<em><b>Exp2</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION__EXP2 = eINSTANCE.getCondition_Exp2();

		/**
		 * The meta object literal for the '{@link application.impl.PaaSageVariableImpl <em>Paa Sage Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.PaaSageVariableImpl
		 * @see application.impl.ApplicationPackageImpl#getPaaSageVariable()
		 * @generated
		 */
		EClass PAA_SAGE_VARIABLE = eINSTANCE.getPaaSageVariable();

		/**
		 * The meta object literal for the '<em><b>Paasage Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAA_SAGE_VARIABLE__PAASAGE_TYPE = eINSTANCE.getPaaSageVariable_PaasageType();

		/**
		 * The meta object literal for the '<em><b>Related Elements</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAA_SAGE_VARIABLE__RELATED_ELEMENTS = eINSTANCE.getPaaSageVariable_RelatedElements();

		/**
		 * The meta object literal for the '<em><b>Cp Variable Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAA_SAGE_VARIABLE__CP_VARIABLE_ID = eINSTANCE.getPaaSageVariable_CpVariableId();

		/**
		 * The meta object literal for the '{@link application.impl.PaaSageGoalImpl <em>Paa Sage Goal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.PaaSageGoalImpl
		 * @see application.impl.ApplicationPackageImpl#getPaaSageGoal()
		 * @generated
		 */
		EClass PAA_SAGE_GOAL = eINSTANCE.getPaaSageGoal();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAA_SAGE_GOAL__ID = eINSTANCE.getPaaSageGoal_Id();

		/**
		 * The meta object literal for the '<em><b>Goal</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAA_SAGE_GOAL__GOAL = eINSTANCE.getPaaSageGoal_Goal();

		/**
		 * The meta object literal for the '<em><b>Function</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAA_SAGE_GOAL__FUNCTION = eINSTANCE.getPaaSageGoal_Function();

		/**
		 * The meta object literal for the '{@link application.impl.RequiredFeatureImpl <em>Required Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.RequiredFeatureImpl
		 * @see application.impl.ApplicationPackageImpl#getRequiredFeature()
		 * @generated
		 */
		EClass REQUIRED_FEATURE = eINSTANCE.getRequiredFeature();

		/**
		 * The meta object literal for the '<em><b>Feature</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED_FEATURE__FEATURE = eINSTANCE.getRequiredFeature_Feature();

		/**
		 * The meta object literal for the '<em><b>Provided By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRED_FEATURE__PROVIDED_BY = eINSTANCE.getRequiredFeature_ProvidedBy();

		/**
		 * The meta object literal for the '<em><b>Remote</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED_FEATURE__REMOTE = eINSTANCE.getRequiredFeature_Remote();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED_FEATURE__OPTIONAL = eINSTANCE.getRequiredFeature_Optional();

		/**
		 * The meta object literal for the '{@link application.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.DimensionImpl
		 * @see application.impl.ApplicationPackageImpl#getDimension()
		 * @generated
		 */
		EClass DIMENSION = eINSTANCE.getDimension();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DIMENSION__ID = eINSTANCE.getDimension_Id();

		/**
		 * The meta object literal for the '{@link application.impl.ProviderCostImpl <em>Provider Cost</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see application.impl.ProviderCostImpl
		 * @see application.impl.ApplicationPackageImpl#getProviderCost()
		 * @generated
		 */
		EClass PROVIDER_COST = eINSTANCE.getProviderCost();

		/**
		 * The meta object literal for the '<em><b>Cost</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROVIDER_COST__COST = eINSTANCE.getProviderCost_Cost();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDER_COST__PROVIDER = eINSTANCE.getProviderCost_Provider();

	}

} //ApplicationPackage
