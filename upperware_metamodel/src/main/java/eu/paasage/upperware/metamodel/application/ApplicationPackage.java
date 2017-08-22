/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.cp.CpPackage;

import eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
 * @see eu.paasage.upperware.metamodel.application.ApplicationFactory
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
	String eNS_URI = "http://paasage.inria.fr/eu/paasage/upperware/metamodel/application";

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
	ApplicationPackage eINSTANCE = eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl.init();

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl <em>Paasage Configuration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getPaasageConfiguration()
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
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__VARIABLES = 2;

	/**
	 * The feature id for the '<em><b>Rules</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__RULES = 3;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__COMPONENTS = 4;

	/**
	 * The feature id for the '<em><b>Providers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__PROVIDERS = 5;

	/**
	 * The feature id for the '<em><b>Vm Profiles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__VM_PROFILES = 6;

	/**
	 * The feature id for the '<em><b>Aux Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__AUX_EXPRESSIONS = 7;

	/**
	 * The feature id for the '<em><b>Vms</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__VMS = 8;

	/**
	 * The feature id for the '<em><b>Monitored Dimensions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION__MONITORED_DIMENSIONS = 9;

	/**
	 * The number of structural features of the '<em>Paasage Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION_FEATURE_COUNT = 10;

	/**
	 * The number of operations of the '<em>Paasage Configuration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAASAGE_CONFIGURATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineImpl <em>Virtual Machine</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.VirtualMachineImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getVirtualMachine()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.CloudMLElementUpperwareImpl <em>Cloud ML Element Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.CloudMLElementUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getCloudMLElementUpperware()
	 * @generated
	 */
	int CLOUD_ML_ELEMENT_UPPERWARE = 3;

	/**
	 * The feature id for the '<em><b>Cloud ML Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT_UPPERWARE__CLOUD_ML_ID = 0;

	/**
	 * The number of structural features of the '<em>Cloud ML Element Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Cloud ML Element Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT_UPPERWARE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl <em>Virtual Machine Profile</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getVirtualMachineProfile()
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
	int VIRTUAL_MACHINE_PROFILE__CLOUD_ML_ID = CLOUD_ML_ELEMENT_UPPERWARE__CLOUD_ML_ID;

	/**
	 * The feature id for the '<em><b>Size</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__SIZE = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Memory</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__MEMORY = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Storage</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__STORAGE = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Cpu</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__CPU = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Os</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__OS = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Provider Dimension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__PROVIDER_DIMENSION = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__LOCATION = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Image</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__IMAGE = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Related Cloud VM Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE__RELATED_CLOUD_VM_ID = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>Virtual Machine Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE_FEATURE_COUNT = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 9;

	int VIRTUAL_MACHINE_PROFILE__FLAVOUR_NAME_ID = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 10;


	/**
	 * The number of operations of the '<em>Virtual Machine Profile</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VIRTUAL_MACHINE_PROFILE_OPERATION_COUNT = CLOUD_ML_ELEMENT_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.ResourceUpperwareImpl <em>Resource Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.ResourceUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getResourceUpperware()
	 * @generated
	 */
	int RESOURCE_UPPERWARE = 4;

	/**
	 * The feature id for the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_UPPERWARE__TYPE_ID = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_UPPERWARE__VALUE = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Resource Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_UPPERWARE_FEATURE_COUNT = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Resource Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_UPPERWARE_OPERATION_COUNT = TypesPaasagePackage.PAA_SAGE_CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.MemoryImpl <em>Memory</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.MemoryImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getMemory()
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
	int MEMORY__TYPE_ID = RESOURCE_UPPERWARE__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY__VALUE = RESOURCE_UPPERWARE__VALUE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY__UNIT = RESOURCE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Memory</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_FEATURE_COUNT = RESOURCE_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Memory</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MEMORY_OPERATION_COUNT = RESOURCE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.StorageImpl <em>Storage</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.StorageImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getStorage()
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
	int STORAGE__TYPE_ID = RESOURCE_UPPERWARE__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE__VALUE = RESOURCE_UPPERWARE__VALUE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE__UNIT = RESOURCE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Storage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_FEATURE_COUNT = RESOURCE_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Storage</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_OPERATION_COUNT = RESOURCE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.CPUImpl <em>CPU</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.CPUImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getCPU()
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
	int CPU__TYPE_ID = RESOURCE_UPPERWARE__TYPE_ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU__VALUE = RESOURCE_UPPERWARE__VALUE;

	/**
	 * The feature id for the '<em><b>Frequency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU__FREQUENCY = RESOURCE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU__CORES = RESOURCE_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>CPU</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU_FEATURE_COUNT = RESOURCE_UPPERWARE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>CPU</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CPU_OPERATION_COUNT = RESOURCE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.ProviderImpl <em>Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.ProviderImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getProvider()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getApplicationComponent()
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
	int APPLICATION_COMPONENT__CLOUD_ML_ID = CLOUD_ML_ELEMENT_UPPERWARE__CLOUD_ML_ID;

	/**
	 * The feature id for the '<em><b>Vm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__VM = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Preferred Locations</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__PREFERRED_LOCATIONS = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Required Profile</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__REQUIRED_PROFILE = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Features</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__FEATURES = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Required Features</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__REQUIRED_FEATURES = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Preferred Providers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__PREFERRED_PROVIDERS = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__MIN = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT__MAX = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_FEATURE_COUNT = CLOUD_ML_ELEMENT_UPPERWARE_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_COMPONENT_OPERATION_COUNT = CLOUD_ML_ELEMENT_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.ElasticityRuleImpl <em>Elasticity Rule</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.ElasticityRuleImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getElasticityRule()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.ActionUpperwareImpl <em>Action Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.ActionUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getActionUpperware()
	 * @generated
	 */
	int ACTION_UPPERWARE = 11;

	/**
	 * The feature id for the '<em><b>Parameters</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_UPPERWARE__PARAMETERS = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_UPPERWARE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Action Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_UPPERWARE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Action Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION_UPPERWARE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl <em>Condition Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getConditionUpperware()
	 * @generated
	 */
	int CONDITION_UPPERWARE = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_UPPERWARE__ID = CpPackage.BOOLEAN_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_UPPERWARE__OPERATOR = CpPackage.BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exp1</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_UPPERWARE__EXP1 = CpPackage.BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Exp2</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_UPPERWARE__EXP2 = CpPackage.BOOLEAN_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Condition Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_UPPERWARE_FEATURE_COUNT = CpPackage.BOOLEAN_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Condition Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONDITION_UPPERWARE_OPERATION_COUNT = CpPackage.BOOLEAN_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl <em>Paa Sage Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getPaaSageVariable()
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
	 * The feature id for the '<em><b>Related Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE__RELATED_COMPONENT = 1;

	/**
	 * The feature id for the '<em><b>Cp Variable Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE__CP_VARIABLE_ID = 2;

	/**
	 * The feature id for the '<em><b>Related Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE__RELATED_PROVIDER = 3;

	/**
	 * The feature id for the '<em><b>Related Virtual Machine Profile</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE__RELATED_VIRTUAL_MACHINE_PROFILE = 4;

	/**
	 * The number of structural features of the '<em>Paa Sage Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Paa Sage Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_VARIABLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.PaaSageGoalImpl <em>Paa Sage Goal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.PaaSageGoalImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getPaaSageGoal()
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
	 * The feature id for the '<em><b>Application Component</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_GOAL__APPLICATION_COMPONENT = 3;

	/**
	 * The feature id for the '<em><b>Application Metric</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_GOAL__APPLICATION_METRIC = 4;

	/**
	 * The number of structural features of the '<em>Paa Sage Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_GOAL_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Paa Sage Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PAA_SAGE_GOAL_OPERATION_COUNT = 0;

	int PAA_SAGE_GOAL__EXT_OPTIMISATION_ATTRIBUTE = 10000;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl <em>Required Feature</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getRequiredFeature()
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
	 * The feature id for the '<em><b>Communication Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE__COMMUNICATION_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Optional</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE__OPTIONAL = 3;

	/**
	 * The feature id for the '<em><b>Contaiment</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE__CONTAIMENT = 4;

	/**
	 * The number of structural features of the '<em>Required Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Required Feature</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_FEATURE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.DimensionImpl <em>Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.DimensionImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getDimension()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl <em>Provider Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getProviderDimension()
	 * @generated
	 */
	int PROVIDER_DIMENSION = 17;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_DIMENSION__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_DIMENSION__PROVIDER = 1;

	/**
	 * The feature id for the '<em><b>Metric ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_DIMENSION__METRIC_ID = 2;

	/**
	 * The number of structural features of the '<em>Provider Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_DIMENSION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Provider Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDER_DIMENSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.ImageUpperwareImpl <em>Image Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.ImageUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getImageUpperware()
	 * @generated
	 */
	int IMAGE_UPPERWARE = 18;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_UPPERWARE__ID = 0;

	/**
	 * The number of structural features of the '<em>Image Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_UPPERWARE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Image Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_UPPERWARE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.application.impl.ComponentMetricRelationshipImpl <em>Component Metric Relationship</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.application.impl.ComponentMetricRelationshipImpl
	 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getComponentMetricRelationship()
	 * @generated
	 */
	int COMPONENT_METRIC_RELATIONSHIP = 19;

	/**
	 * The feature id for the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_METRIC_RELATIONSHIP__COMPONENT = 0;

	/**
	 * The feature id for the '<em><b>Metric Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_METRIC_RELATIONSHIP__METRIC_ID = 1;

	/**
	 * The number of structural features of the '<em>Component Metric Relationship</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_METRIC_RELATIONSHIP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Component Metric Relationship</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_METRIC_RELATIONSHIP_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration <em>Paasage Configuration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paasage Configuration</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration
	 * @generated
	 */
	EClass getPaasageConfiguration();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getId()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EAttribute getPaasageConfiguration_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getGoals <em>Goals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Goals</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getGoals()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Goals();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getVariables()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Variables();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getRules <em>Rules</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Rules</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getRules()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Rules();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getComponents()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Components();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getProviders <em>Providers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Providers</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getProviders()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Providers();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getVmProfiles <em>Vm Profiles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vm Profiles</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getVmProfiles()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_VmProfiles();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getAuxExpressions <em>Aux Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Aux Expressions</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getAuxExpressions()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_AuxExpressions();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getVms <em>Vms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vms</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getVms()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_Vms();

	/**
	 * Returns the meta object for the reference list '{@link eu.paasage.upperware.metamodel.application.PaasageConfiguration#getMonitoredDimensions <em>Monitored Dimensions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Monitored Dimensions</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaasageConfiguration#getMonitoredDimensions()
	 * @see #getPaasageConfiguration()
	 * @generated
	 */
	EReference getPaasageConfiguration_MonitoredDimensions();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.VirtualMachine <em>Virtual Machine</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Virtual Machine</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachine
	 * @generated
	 */
	EClass getVirtualMachine();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.VirtualMachine#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachine#getId()
	 * @see #getVirtualMachine()
	 * @generated
	 */
	EAttribute getVirtualMachine_Id();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.VirtualMachine#getProfile <em>Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachine#getProfile()
	 * @see #getVirtualMachine()
	 * @generated
	 */
	EReference getVirtualMachine_Profile();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile <em>Virtual Machine Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Virtual Machine Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile
	 * @generated
	 */
	EClass getVirtualMachineProfile();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getSize <em>Size</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Size</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getSize()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EAttribute getVirtualMachineProfile_Size();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getMemory <em>Memory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Memory</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getMemory()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Memory();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getStorage <em>Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Storage</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getStorage()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Storage();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getCpu <em>Cpu</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cpu</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getCpu()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Cpu();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getOs <em>Os</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Os</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getOs()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Os();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getProviderDimension <em>Provider Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Provider Dimension</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getProviderDimension()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_ProviderDimension();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Location</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getLocation()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Location();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getImage <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Image</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getImage()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EReference getVirtualMachineProfile_Image();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getRelatedCloudVMId <em>Related Cloud VM Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Related Cloud VM Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.VirtualMachineProfile#getRelatedCloudVMId()
	 * @see #getVirtualMachineProfile()
	 * @generated
	 */
	EAttribute getVirtualMachineProfile_RelatedCloudVMId();

	EAttribute getVirtualMachineProfile_FlavourName();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.CloudMLElementUpperware <em>Cloud ML Element Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cloud ML Element Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.application.CloudMLElementUpperware
	 * @generated
	 */
	EClass getCloudMLElementUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.CloudMLElementUpperware#getCloudMLId <em>Cloud ML Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cloud ML Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.CloudMLElementUpperware#getCloudMLId()
	 * @see #getCloudMLElementUpperware()
	 * @generated
	 */
	EAttribute getCloudMLElementUpperware_CloudMLId();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.ResourceUpperware <em>Resource Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ResourceUpperware
	 * @generated
	 */
	EClass getResourceUpperware();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.ResourceUpperware#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ResourceUpperware#getValue()
	 * @see #getResourceUpperware()
	 * @generated
	 */
	EReference getResourceUpperware_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.Memory <em>Memory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Memory</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Memory
	 * @generated
	 */
	EClass getMemory();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.Memory#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Memory#getUnit()
	 * @see #getMemory()
	 * @generated
	 */
	EAttribute getMemory_Unit();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.Storage <em>Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Storage</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Storage
	 * @generated
	 */
	EClass getStorage();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.Storage#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Storage#getUnit()
	 * @see #getStorage()
	 * @generated
	 */
	EAttribute getStorage_Unit();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.CPU <em>CPU</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CPU</em>'.
	 * @see eu.paasage.upperware.metamodel.application.CPU
	 * @generated
	 */
	EClass getCPU();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.CPU#getFrequency <em>Frequency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Frequency</em>'.
	 * @see eu.paasage.upperware.metamodel.application.CPU#getFrequency()
	 * @see #getCPU()
	 * @generated
	 */
	EAttribute getCPU_Frequency();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.CPU#getCores <em>Cores</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cores</em>'.
	 * @see eu.paasage.upperware.metamodel.application.CPU#getCores()
	 * @see #getCPU()
	 * @generated
	 */
	EAttribute getCPU_Cores();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.Provider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provider</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Provider
	 * @generated
	 */
	EClass getProvider();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.Provider#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Provider#getId()
	 * @see #getProvider()
	 * @generated
	 */
	EAttribute getProvider_Id();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.Provider#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Location</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Provider#getLocation()
	 * @see #getProvider()
	 * @generated
	 */
	EReference getProvider_Location();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.Provider#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Provider#getType()
	 * @see #getProvider()
	 * @generated
	 */
	EReference getProvider_Type();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent
	 * @generated
	 */
	EClass getApplicationComponent();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getVm <em>Vm</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Vm</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent#getVm()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_Vm();

	/**
	 * Returns the meta object for the reference list '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getPreferredLocations <em>Preferred Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Preferred Locations</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent#getPreferredLocations()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_PreferredLocations();

	/**
	 * Returns the meta object for the reference list '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getRequiredProfile <em>Required Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Required Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent#getRequiredProfile()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_RequiredProfile();

	/**
	 * Returns the meta object for the attribute list '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getFeatures <em>Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Features</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent#getFeatures()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EAttribute getApplicationComponent_Features();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getRequiredFeatures <em>Required Features</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Required Features</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent#getRequiredFeatures()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_RequiredFeatures();

	/**
	 * Returns the meta object for the reference list '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getPreferredProviders <em>Preferred Providers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Preferred Providers</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent#getPreferredProviders()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EReference getApplicationComponent_PreferredProviders();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getMin <em>Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent#getMin()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EAttribute getApplicationComponent_Min();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getMax <em>Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationComponent#getMax()
	 * @see #getApplicationComponent()
	 * @generated
	 */
	EAttribute getApplicationComponent_Max();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.ElasticityRule <em>Elasticity Rule</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Elasticity Rule</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ElasticityRule
	 * @generated
	 */
	EClass getElasticityRule();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ElasticityRule#getId()
	 * @see #getElasticityRule()
	 * @generated
	 */
	EAttribute getElasticityRule_Id();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getAction <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Action</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ElasticityRule#getAction()
	 * @see #getElasticityRule()
	 * @generated
	 */
	EReference getElasticityRule_Action();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.ElasticityRule#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Condition</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ElasticityRule#getCondition()
	 * @see #getElasticityRule()
	 * @generated
	 */
	EReference getElasticityRule_Condition();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.ActionUpperware <em>Action Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ActionUpperware
	 * @generated
	 */
	EClass getActionUpperware();

	/**
	 * Returns the meta object for the attribute list '{@link eu.paasage.upperware.metamodel.application.ActionUpperware#getParameters <em>Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameters</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ActionUpperware#getParameters()
	 * @see #getActionUpperware()
	 * @generated
	 */
	EAttribute getActionUpperware_Parameters();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.ActionUpperware#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ActionUpperware#getType()
	 * @see #getActionUpperware()
	 * @generated
	 */
	EReference getActionUpperware_Type();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.ConditionUpperware <em>Condition Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Condition Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ConditionUpperware
	 * @generated
	 */
	EClass getConditionUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.ConditionUpperware#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ConditionUpperware#getOperator()
	 * @see #getConditionUpperware()
	 * @generated
	 */
	EAttribute getConditionUpperware_Operator();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.ConditionUpperware#getExp1 <em>Exp1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exp1</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ConditionUpperware#getExp1()
	 * @see #getConditionUpperware()
	 * @generated
	 */
	EReference getConditionUpperware_Exp1();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.application.ConditionUpperware#getExp2 <em>Exp2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Exp2</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ConditionUpperware#getExp2()
	 * @see #getConditionUpperware()
	 * @generated
	 */
	EReference getConditionUpperware_Exp2();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable <em>Paa Sage Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paa Sage Variable</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageVariable
	 * @generated
	 */
	EClass getPaaSageVariable();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getPaasageType <em>Paasage Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Paasage Type</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageVariable#getPaasageType()
	 * @see #getPaaSageVariable()
	 * @generated
	 */
	EAttribute getPaaSageVariable_PaasageType();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedComponent <em>Related Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Related Component</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedComponent()
	 * @see #getPaaSageVariable()
	 * @generated
	 */
	EReference getPaaSageVariable_RelatedComponent();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getCpVariableId <em>Cp Variable Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cp Variable Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageVariable#getCpVariableId()
	 * @see #getPaaSageVariable()
	 * @generated
	 */
	EAttribute getPaaSageVariable_CpVariableId();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedProvider <em>Related Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Related Provider</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedProvider()
	 * @see #getPaaSageVariable()
	 * @generated
	 */
	EReference getPaaSageVariable_RelatedProvider();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedVirtualMachineProfile <em>Related Virtual Machine Profile</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Related Virtual Machine Profile</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageVariable#getRelatedVirtualMachineProfile()
	 * @see #getPaaSageVariable()
	 * @generated
	 */
	EReference getPaaSageVariable_RelatedVirtualMachineProfile();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal <em>Paa Sage Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Paa Sage Goal</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageGoal
	 * @generated
	 */
	EClass getPaaSageGoal();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageGoal#getId()
	 * @see #getPaaSageGoal()
	 * @generated
	 */
	EAttribute getPaaSageGoal_Id();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getGoal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Goal</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageGoal#getGoal()
	 * @see #getPaaSageGoal()
	 * @generated
	 */
	EAttribute getPaaSageGoal_Goal();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getFunction <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Function</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageGoal#getFunction()
	 * @see #getPaaSageGoal()
	 * @generated
	 */
	EReference getPaaSageGoal_Function();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationComponent <em>Application Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Application Component</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationComponent()
	 * @see #getPaaSageGoal()
	 * @generated
	 */
	EReference getPaaSageGoal_ApplicationComponent();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationMetric <em>Application Metric</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Application Metric</em>'.
	 * @see eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationMetric()
	 * @see #getPaaSageGoal()
	 * @generated
	 */
	EAttribute getPaaSageGoal_ApplicationMetric();

	EAttribute getPaaSageGoal_ExtOptimisationAttribute();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.RequiredFeature <em>Required Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Feature</em>'.
	 * @see eu.paasage.upperware.metamodel.application.RequiredFeature
	 * @generated
	 */
	EClass getRequiredFeature();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getFeature <em>Feature</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Feature</em>'.
	 * @see eu.paasage.upperware.metamodel.application.RequiredFeature#getFeature()
	 * @see #getRequiredFeature()
	 * @generated
	 */
	EAttribute getRequiredFeature_Feature();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getProvidedBy <em>Provided By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provided By</em>'.
	 * @see eu.paasage.upperware.metamodel.application.RequiredFeature#getProvidedBy()
	 * @see #getRequiredFeature()
	 * @generated
	 */
	EReference getRequiredFeature_ProvidedBy();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#getCommunicationType <em>Communication Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Communication Type</em>'.
	 * @see eu.paasage.upperware.metamodel.application.RequiredFeature#getCommunicationType()
	 * @see #getRequiredFeature()
	 * @generated
	 */
	EAttribute getRequiredFeature_CommunicationType();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isOptional <em>Optional</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Optional</em>'.
	 * @see eu.paasage.upperware.metamodel.application.RequiredFeature#isOptional()
	 * @see #getRequiredFeature()
	 * @generated
	 */
	EAttribute getRequiredFeature_Optional();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.RequiredFeature#isContaiment <em>Contaiment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Contaiment</em>'.
	 * @see eu.paasage.upperware.metamodel.application.RequiredFeature#isContaiment()
	 * @see #getRequiredFeature()
	 * @generated
	 */
	EAttribute getRequiredFeature_Contaiment();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.Dimension <em>Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Dimension</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Dimension
	 * @generated
	 */
	EClass getDimension();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.Dimension#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.Dimension#getId()
	 * @see #getDimension()
	 * @generated
	 */
	EAttribute getDimension_Id();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.ProviderDimension <em>Provider Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provider Dimension</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ProviderDimension
	 * @generated
	 */
	EClass getProviderDimension();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.ProviderDimension#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ProviderDimension#getValue()
	 * @see #getProviderDimension()
	 * @generated
	 */
	EAttribute getProviderDimension_Value();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.ProviderDimension#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provider</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ProviderDimension#getProvider()
	 * @see #getProviderDimension()
	 * @generated
	 */
	EReference getProviderDimension_Provider();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.ProviderDimension#getMetricID <em>Metric ID</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metric ID</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ProviderDimension#getMetricID()
	 * @see #getProviderDimension()
	 * @generated
	 */
	EAttribute getProviderDimension_MetricID();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.ImageUpperware <em>Image Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Image Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ImageUpperware
	 * @generated
	 */
	EClass getImageUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.ImageUpperware#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ImageUpperware#getId()
	 * @see #getImageUpperware()
	 * @generated
	 */
	EAttribute getImageUpperware_Id();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship <em>Component Metric Relationship</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component Metric Relationship</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ComponentMetricRelationship
	 * @generated
	 */
	EClass getComponentMetricRelationship();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Component</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getComponent()
	 * @see #getComponentMetricRelationship()
	 * @generated
	 */
	EReference getComponentMetricRelationship_Component();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getMetricId <em>Metric Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Metric Id</em>'.
	 * @see eu.paasage.upperware.metamodel.application.ComponentMetricRelationship#getMetricId()
	 * @see #getComponentMetricRelationship()
	 * @generated
	 */
	EAttribute getComponentMetricRelationship_MetricId();

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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl <em>Paasage Configuration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.PaasageConfigurationImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getPaasageConfiguration()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineImpl <em>Virtual Machine</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.VirtualMachineImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getVirtualMachine()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl <em>Virtual Machine Profile</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getVirtualMachineProfile()
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
		 * The meta object literal for the '<em><b>Os</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE_PROFILE__OS = eINSTANCE.getVirtualMachineProfile_Os();

		/**
		 * The meta object literal for the '<em><b>Provider Dimension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE_PROFILE__PROVIDER_DIMENSION = eINSTANCE.getVirtualMachineProfile_ProviderDimension();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE_PROFILE__LOCATION = eINSTANCE.getVirtualMachineProfile_Location();

		/**
		 * The meta object literal for the '<em><b>Image</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VIRTUAL_MACHINE_PROFILE__IMAGE = eINSTANCE.getVirtualMachineProfile_Image();

		/**
		 * The meta object literal for the '<em><b>Related Cloud VM Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIRTUAL_MACHINE_PROFILE__RELATED_CLOUD_VM_ID = eINSTANCE.getVirtualMachineProfile_RelatedCloudVMId();

		/**
		 * The meta object literal for the '<em><b>Related Cloud VM Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VIRTUAL_MACHINE_PROFILE__FLAVOUR_NAME_ID = eINSTANCE.getVirtualMachineProfile_FlavourName();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.CloudMLElementUpperwareImpl <em>Cloud ML Element Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.CloudMLElementUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getCloudMLElementUpperware()
		 * @generated
		 */
		EClass CLOUD_ML_ELEMENT_UPPERWARE = eINSTANCE.getCloudMLElementUpperware();

		/**
		 * The meta object literal for the '<em><b>Cloud ML Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_ML_ELEMENT_UPPERWARE__CLOUD_ML_ID = eINSTANCE.getCloudMLElementUpperware_CloudMLId();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.ResourceUpperwareImpl <em>Resource Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.ResourceUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getResourceUpperware()
		 * @generated
		 */
		EClass RESOURCE_UPPERWARE = eINSTANCE.getResourceUpperware();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_UPPERWARE__VALUE = eINSTANCE.getResourceUpperware_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.MemoryImpl <em>Memory</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.MemoryImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getMemory()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.StorageImpl <em>Storage</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.StorageImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getStorage()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.CPUImpl <em>CPU</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.CPUImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getCPU()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.ProviderImpl <em>Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.ProviderImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getProvider()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationComponentImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getApplicationComponent()
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
		 * The meta object literal for the '<em><b>Required Profile</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION_COMPONENT__REQUIRED_PROFILE = eINSTANCE.getApplicationComponent_RequiredProfile();

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
		 * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLICATION_COMPONENT__MIN = eINSTANCE.getApplicationComponent_Min();

		/**
		 * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLICATION_COMPONENT__MAX = eINSTANCE.getApplicationComponent_Max();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.ElasticityRuleImpl <em>Elasticity Rule</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.ElasticityRuleImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getElasticityRule()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.ActionUpperwareImpl <em>Action Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.ActionUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getActionUpperware()
		 * @generated
		 */
		EClass ACTION_UPPERWARE = eINSTANCE.getActionUpperware();

		/**
		 * The meta object literal for the '<em><b>Parameters</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION_UPPERWARE__PARAMETERS = eINSTANCE.getActionUpperware_Parameters();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ACTION_UPPERWARE__TYPE = eINSTANCE.getActionUpperware_Type();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl <em>Condition Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.ConditionUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getConditionUpperware()
		 * @generated
		 */
		EClass CONDITION_UPPERWARE = eINSTANCE.getConditionUpperware();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONDITION_UPPERWARE__OPERATOR = eINSTANCE.getConditionUpperware_Operator();

		/**
		 * The meta object literal for the '<em><b>Exp1</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION_UPPERWARE__EXP1 = eINSTANCE.getConditionUpperware_Exp1();

		/**
		 * The meta object literal for the '<em><b>Exp2</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONDITION_UPPERWARE__EXP2 = eINSTANCE.getConditionUpperware_Exp2();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl <em>Paa Sage Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.PaaSageVariableImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getPaaSageVariable()
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
		 * The meta object literal for the '<em><b>Related Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAA_SAGE_VARIABLE__RELATED_COMPONENT = eINSTANCE.getPaaSageVariable_RelatedComponent();

		/**
		 * The meta object literal for the '<em><b>Cp Variable Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAA_SAGE_VARIABLE__CP_VARIABLE_ID = eINSTANCE.getPaaSageVariable_CpVariableId();

		/**
		 * The meta object literal for the '<em><b>Related Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAA_SAGE_VARIABLE__RELATED_PROVIDER = eINSTANCE.getPaaSageVariable_RelatedProvider();

		/**
		 * The meta object literal for the '<em><b>Related Virtual Machine Profile</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAA_SAGE_VARIABLE__RELATED_VIRTUAL_MACHINE_PROFILE = eINSTANCE.getPaaSageVariable_RelatedVirtualMachineProfile();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.PaaSageGoalImpl <em>Paa Sage Goal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.PaaSageGoalImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getPaaSageGoal()
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

		EAttribute PAA_SAGE_GOAL__EXT_OPTIMISATION_ATTRIBUTE_ID = eINSTANCE.getPaaSageGoal_ExtOptimisationAttribute();

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
		 * The meta object literal for the '<em><b>Application Component</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PAA_SAGE_GOAL__APPLICATION_COMPONENT = eINSTANCE.getPaaSageGoal_ApplicationComponent();

		/**
		 * The meta object literal for the '<em><b>Application Metric</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PAA_SAGE_GOAL__APPLICATION_METRIC = eINSTANCE.getPaaSageGoal_ApplicationMetric();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl <em>Required Feature</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.RequiredFeatureImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getRequiredFeature()
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
		 * The meta object literal for the '<em><b>Communication Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED_FEATURE__COMMUNICATION_TYPE = eINSTANCE.getRequiredFeature_CommunicationType();

		/**
		 * The meta object literal for the '<em><b>Optional</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED_FEATURE__OPTIONAL = eINSTANCE.getRequiredFeature_Optional();

		/**
		 * The meta object literal for the '<em><b>Contaiment</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED_FEATURE__CONTAIMENT = eINSTANCE.getRequiredFeature_Contaiment();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.DimensionImpl <em>Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.DimensionImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getDimension()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl <em>Provider Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getProviderDimension()
		 * @generated
		 */
		EClass PROVIDER_DIMENSION = eINSTANCE.getProviderDimension();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROVIDER_DIMENSION__VALUE = eINSTANCE.getProviderDimension_Value();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDER_DIMENSION__PROVIDER = eINSTANCE.getProviderDimension_Provider();

		/**
		 * The meta object literal for the '<em><b>Metric ID</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROVIDER_DIMENSION__METRIC_ID = eINSTANCE.getProviderDimension_MetricID();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.ImageUpperwareImpl <em>Image Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.ImageUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getImageUpperware()
		 * @generated
		 */
		EClass IMAGE_UPPERWARE = eINSTANCE.getImageUpperware();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMAGE_UPPERWARE__ID = eINSTANCE.getImageUpperware_Id();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.application.impl.ComponentMetricRelationshipImpl <em>Component Metric Relationship</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.application.impl.ComponentMetricRelationshipImpl
		 * @see eu.paasage.upperware.metamodel.application.impl.ApplicationPackageImpl#getComponentMetricRelationship()
		 * @generated
		 */
		EClass COMPONENT_METRIC_RELATIONSHIP = eINSTANCE.getComponentMetricRelationship();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_METRIC_RELATIONSHIP__COMPONENT = eINSTANCE.getComponentMetricRelationship_Component();

		/**
		 * The meta object literal for the '<em><b>Metric Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_METRIC_RELATIONSHIP__METRIC_ID = eINSTANCE.getComponentMetricRelationship_MetricId();

	}

} //ApplicationPackage
