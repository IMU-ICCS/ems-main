/**
 */
package camel.deployment;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;
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
 * @see camel.deployment.DeploymentFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface DeploymentPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "deployment";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/camel/cloudml";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "deployment";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	DeploymentPackage eINSTANCE = camel.deployment.impl.DeploymentPackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.deployment.impl.CloudMLElementImpl <em>Cloud ML Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.CloudMLElementImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getCloudMLElement()
	 * @generated
	 */
	int CLOUD_ML_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT__NAME = 0;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT__PROPERTIES = 1;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT__RESOURCES = 2;

	/**
	 * The number of structural features of the '<em>Cloud ML Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Cloud ML Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_ML_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.DeploymentModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.DeploymentModelImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getDeploymentModel()
	 * @generated
	 */
	int DEPLOYMENT_MODEL = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__COMPONENTS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Communications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__COMMUNICATIONS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Hostings</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__HOSTINGS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Images</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__IMAGES = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Providers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__PROVIDERS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Component Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__COMPONENT_INSTANCES = CLOUD_ML_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Communication Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__COMMUNICATION_INSTANCES = CLOUD_ML_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Hosting Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL__HOSTING_INSTANCES = CLOUD_ML_ELEMENT_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYMENT_MODEL_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ComponentImpl <em>Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ComponentImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getComponent()
	 * @generated
	 */
	int COMPONENT = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Communications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PROVIDED_COMMUNICATIONS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Provided Hosts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT__PROVIDED_HOSTS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.InternalComponentImpl <em>Internal Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.InternalComponentImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getInternalComponent()
	 * @generated
	 */
	int INTERNAL_COMPONENT = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT__PROPERTIES = COMPONENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT__RESOURCES = COMPONENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Communications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT__PROVIDED_COMMUNICATIONS = COMPONENT__PROVIDED_COMMUNICATIONS;

	/**
	 * The feature id for the '<em><b>Provided Hosts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT__PROVIDED_HOSTS = COMPONENT__PROVIDED_HOSTS;

	/**
	 * The feature id for the '<em><b>Required Communications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT__REQUIRED_COMMUNICATIONS = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Composite Internal Components</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT__COMPOSITE_INTERNAL_COMPONENTS = COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Required Host</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT__REQUIRED_HOST = COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Internal Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Contains</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT___CONTAINS__INTERNALCOMPONENT_INTERNALCOMPONENT = COMPONENT_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>Internal Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_OPERATION_COUNT = COMPONENT_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ExternalComponentImpl <em>External Component</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ExternalComponentImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getExternalComponent()
	 * @generated
	 */
	int EXTERNAL_COMPONENT = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT__NAME = COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT__PROPERTIES = COMPONENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT__RESOURCES = COMPONENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Communications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT__PROVIDED_COMMUNICATIONS = COMPONENT__PROVIDED_COMMUNICATIONS;

	/**
	 * The feature id for the '<em><b>Provided Hosts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT__PROVIDED_HOSTS = COMPONENT__PROVIDED_HOSTS;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT__PROVIDER = COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT__LOCATION = COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>External Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_FEATURE_COUNT = COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>External Component</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_OPERATION_COUNT = COMPONENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.VMImpl <em>VM</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.VMImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getVM()
	 * @generated
	 */
	int VM = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__NAME = EXTERNAL_COMPONENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__PROPERTIES = EXTERNAL_COMPONENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__RESOURCES = EXTERNAL_COMPONENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Communications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__PROVIDED_COMMUNICATIONS = EXTERNAL_COMPONENT__PROVIDED_COMMUNICATIONS;

	/**
	 * The feature id for the '<em><b>Provided Hosts</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__PROVIDED_HOSTS = EXTERNAL_COMPONENT__PROVIDED_HOSTS;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__PROVIDER = EXTERNAL_COMPONENT__PROVIDER;

	/**
	 * The feature id for the '<em><b>Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__LOCATION = EXTERNAL_COMPONENT__LOCATION;

	/**
	 * The feature id for the '<em><b>Min Ram</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__MIN_RAM = EXTERNAL_COMPONENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max Ram</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__MAX_RAM = EXTERNAL_COMPONENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Ram Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__RAM_UNIT = EXTERNAL_COMPONENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Min Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__MIN_CORES = EXTERNAL_COMPONENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Max Cores</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__MAX_CORES = EXTERNAL_COMPONENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Min Storage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__MIN_STORAGE = EXTERNAL_COMPONENT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Max Storage</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__MAX_STORAGE = EXTERNAL_COMPONENT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Storage Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__STORAGE_UNIT = EXTERNAL_COMPONENT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Min CPU</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__MIN_CPU = EXTERNAL_COMPONENT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Max CPU</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__MAX_CPU = EXTERNAL_COMPONENT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Os</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__OS = EXTERNAL_COMPONENT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Is64os</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__IS64OS = EXTERNAL_COMPONENT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Vm Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM__VM_TYPE = EXTERNAL_COMPONENT_FEATURE_COUNT + 12;

	/**
	 * The number of structural features of the '<em>VM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_FEATURE_COUNT = EXTERNAL_COMPONENT_FEATURE_COUNT + 13;

	/**
	 * The number of operations of the '<em>VM</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_OPERATION_COUNT = EXTERNAL_COMPONENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ComponentGroupImpl <em>Component Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ComponentGroupImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getComponentGroup()
	 * @generated
	 */
	int COMPONENT_GROUP = 6;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GROUP__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GROUP__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GROUP__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GROUP__ID = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Component Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GROUP_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Component Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_GROUP_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ComputationalResourceImpl <em>Computational Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ComputationalResourceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getComputationalResource()
	 * @generated
	 */
	int COMPUTATIONAL_RESOURCE = 7;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Download Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE__DOWNLOAD_COMMAND = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Upload Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE__UPLOAD_COMMAND = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Install Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE__INSTALL_COMMAND = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Configure Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE__CONFIGURE_COMMAND = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Start Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE__START_COMMAND = CLOUD_ML_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Stop Command</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE__STOP_COMMAND = CLOUD_ML_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Computational Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Computational Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPUTATIONAL_RESOURCE_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.CommunicationImpl <em>Communication</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.CommunicationImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getCommunication()
	 * @generated
	 */
	int COMMUNICATION = 8;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Required Communication</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION__REQUIRED_COMMUNICATION = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Provided Communication</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION__PROVIDED_COMMUNICATION = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Required Port Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION__REQUIRED_PORT_RESOURCE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Provided Port Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION__PROVIDED_PORT_RESOURCE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Of Data Object</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION__OF_DATA_OBJECT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>Communication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 5;

	/**
	 * The number of operations of the '<em>Communication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.CommunicationPortImpl <em>Communication Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.CommunicationPortImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getCommunicationPort()
	 * @generated
	 */
	int COMMUNICATION_PORT = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT__COMPONENT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is Local</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT__IS_LOCAL = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Port Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT__PORT_NUMBER = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Communication Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Communication Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ProvidedCommunicationImpl <em>Provided Communication</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ProvidedCommunicationImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getProvidedCommunication()
	 * @generated
	 */
	int PROVIDED_COMMUNICATION = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION__NAME = COMMUNICATION_PORT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION__PROPERTIES = COMMUNICATION_PORT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION__RESOURCES = COMMUNICATION_PORT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION__COMPONENT = COMMUNICATION_PORT__COMPONENT;

	/**
	 * The feature id for the '<em><b>Is Local</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION__IS_LOCAL = COMMUNICATION_PORT__IS_LOCAL;

	/**
	 * The feature id for the '<em><b>Port Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION__PORT_NUMBER = COMMUNICATION_PORT__PORT_NUMBER;

	/**
	 * The number of structural features of the '<em>Provided Communication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION_FEATURE_COUNT = COMMUNICATION_PORT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Provided Communication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION_OPERATION_COUNT = COMMUNICATION_PORT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.RequiredCommunicationImpl <em>Required Communication</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.RequiredCommunicationImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getRequiredCommunication()
	 * @generated
	 */
	int REQUIRED_COMMUNICATION = 11;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION__NAME = COMMUNICATION_PORT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION__PROPERTIES = COMMUNICATION_PORT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION__RESOURCES = COMMUNICATION_PORT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION__COMPONENT = COMMUNICATION_PORT__COMPONENT;

	/**
	 * The feature id for the '<em><b>Is Local</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION__IS_LOCAL = COMMUNICATION_PORT__IS_LOCAL;

	/**
	 * The feature id for the '<em><b>Port Number</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION__PORT_NUMBER = COMMUNICATION_PORT__PORT_NUMBER;

	/**
	 * The feature id for the '<em><b>Is Mandatory</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION__IS_MANDATORY = COMMUNICATION_PORT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Required Communication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION_FEATURE_COUNT = COMMUNICATION_PORT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Required Communication</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION_OPERATION_COUNT = COMMUNICATION_PORT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.HostingImpl <em>Hosting</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.HostingImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getHosting()
	 * @generated
	 */
	int HOSTING = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Host</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING__PROVIDED_HOST = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Required Host</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING__REQUIRED_HOST = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Required Host Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING__REQUIRED_HOST_RESOURCE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Provided Host Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING__PROVIDED_HOST_RESOURCE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Hosting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Hosting</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.HostingPortImpl <em>Hosting Port</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.HostingPortImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getHostingPort()
	 * @generated
	 */
	int HOSTING_PORT = 13;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT__OWNER = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Hosting Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Hosting Port</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ProvidedHostImpl <em>Provided Host</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ProvidedHostImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getProvidedHost()
	 * @generated
	 */
	int PROVIDED_HOST = 14;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST__NAME = HOSTING_PORT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST__PROPERTIES = HOSTING_PORT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST__RESOURCES = HOSTING_PORT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST__OWNER = HOSTING_PORT__OWNER;

	/**
	 * The feature id for the '<em><b>Offers</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST__OFFERS = HOSTING_PORT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Provided Host</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST_FEATURE_COUNT = HOSTING_PORT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Provided Host</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST_OPERATION_COUNT = HOSTING_PORT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.RequiredHostImpl <em>Required Host</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.RequiredHostImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getRequiredHost()
	 * @generated
	 */
	int REQUIRED_HOST = 15;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST__NAME = HOSTING_PORT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST__PROPERTIES = HOSTING_PORT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST__RESOURCES = HOSTING_PORT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST__OWNER = HOSTING_PORT__OWNER;

	/**
	 * The feature id for the '<em><b>Demands</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST__DEMANDS = HOSTING_PORT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Required Host</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST_FEATURE_COUNT = HOSTING_PORT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Required Host</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST_OPERATION_COUNT = HOSTING_PORT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ImageImpl <em>Image</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ImageImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getImage()
	 * @generated
	 */
	int IMAGE = 16;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Os</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE__OS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Is64os</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE__IS64OS = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Image Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE__IMAGE_ID = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Image Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE__IMAGE_GROUP = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Image</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Image</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMAGE_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ComponentInstanceImpl <em>Component Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ComponentInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getComponentInstance()
	 * @generated
	 */
	int COMPONENT_INSTANCE = 17;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_INSTANCE__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_INSTANCE__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_INSTANCE__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Communication Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_INSTANCE__TYPE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Provided Host Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_INSTANCE__PROVIDED_HOST_INSTANCES = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Component Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_INSTANCE_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Component Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPONENT_INSTANCE_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.InternalComponentInstanceImpl <em>Internal Component Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.InternalComponentInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getInternalComponentInstance()
	 * @generated
	 */
	int INTERNAL_COMPONENT_INSTANCE = 18;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE__NAME = COMPONENT_INSTANCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE__PROPERTIES = COMPONENT_INSTANCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE__RESOURCES = COMPONENT_INSTANCE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Communication Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES = COMPONENT_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE__TYPE = COMPONENT_INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Provided Host Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE__PROVIDED_HOST_INSTANCES = COMPONENT_INSTANCE__PROVIDED_HOST_INSTANCES;

	/**
	 * The feature id for the '<em><b>Required Communication Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE__REQUIRED_COMMUNICATION_INSTANCES = COMPONENT_INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Required Host Instance</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE__REQUIRED_HOST_INSTANCE = COMPONENT_INSTANCE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Internal Component Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE_FEATURE_COUNT = COMPONENT_INSTANCE_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Internal Component Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTERNAL_COMPONENT_INSTANCE_OPERATION_COUNT = COMPONENT_INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ExternalComponentInstanceImpl <em>External Component Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ExternalComponentInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getExternalComponentInstance()
	 * @generated
	 */
	int EXTERNAL_COMPONENT_INSTANCE = 19;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_INSTANCE__NAME = COMPONENT_INSTANCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_INSTANCE__PROPERTIES = COMPONENT_INSTANCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_INSTANCE__RESOURCES = COMPONENT_INSTANCE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Communication Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES = COMPONENT_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_INSTANCE__TYPE = COMPONENT_INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Provided Host Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_INSTANCE__PROVIDED_HOST_INSTANCES = COMPONENT_INSTANCE__PROVIDED_HOST_INSTANCES;

	/**
	 * The feature id for the '<em><b>Ips</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_INSTANCE__IPS = COMPONENT_INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>External Component Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_INSTANCE_FEATURE_COUNT = COMPONENT_INSTANCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>External Component Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_COMPONENT_INSTANCE_OPERATION_COUNT = COMPONENT_INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.VMInstanceImpl <em>VM Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.VMInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getVMInstance()
	 * @generated
	 */
	int VM_INSTANCE = 20;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__NAME = EXTERNAL_COMPONENT_INSTANCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__PROPERTIES = EXTERNAL_COMPONENT_INSTANCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__RESOURCES = EXTERNAL_COMPONENT_INSTANCE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Communication Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES = EXTERNAL_COMPONENT_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__TYPE = EXTERNAL_COMPONENT_INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Provided Host Instances</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__PROVIDED_HOST_INSTANCES = EXTERNAL_COMPONENT_INSTANCE__PROVIDED_HOST_INSTANCES;

	/**
	 * The feature id for the '<em><b>Ips</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__IPS = EXTERNAL_COMPONENT_INSTANCE__IPS;

	/**
	 * The feature id for the '<em><b>Public Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__PUBLIC_ADDRESS = EXTERNAL_COMPONENT_INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Created On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__CREATED_ON = EXTERNAL_COMPONENT_INSTANCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Destroyed On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__DESTROYED_ON = EXTERNAL_COMPONENT_INSTANCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Has Config</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__HAS_CONFIG = EXTERNAL_COMPONENT_INSTANCE_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Has Info</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE__HAS_INFO = EXTERNAL_COMPONENT_INSTANCE_FEATURE_COUNT + 4;

	/**
	 * The number of structural features of the '<em>VM Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE_FEATURE_COUNT = EXTERNAL_COMPONENT_INSTANCE_FEATURE_COUNT + 5;

	/**
	 * The operation id for the '<em>Check Dates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE___CHECK_DATES__VMINSTANCE = EXTERNAL_COMPONENT_INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The number of operations of the '<em>VM Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INSTANCE_OPERATION_COUNT = EXTERNAL_COMPONENT_INSTANCE_OPERATION_COUNT + 1;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.CommunicationInstanceImpl <em>Communication Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.CommunicationInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getCommunicationInstance()
	 * @generated
	 */
	int COMMUNICATION_INSTANCE = 21;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_INSTANCE__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_INSTANCE__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_INSTANCE__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_INSTANCE__TYPE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Required Communication Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_INSTANCE__REQUIRED_COMMUNICATION_INSTANCE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Provided Communication Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_INSTANCE__PROVIDED_COMMUNICATION_INSTANCE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Communication Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_INSTANCE_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Communication Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_INSTANCE_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.CommunicationPortInstanceImpl <em>Communication Port Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.CommunicationPortInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getCommunicationPortInstance()
	 * @generated
	 */
	int COMMUNICATION_PORT_INSTANCE = 22;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT_INSTANCE__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT_INSTANCE__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT_INSTANCE__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT_INSTANCE__TYPE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT_INSTANCE__COMPONENT_INSTANCE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Communication Port Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT_INSTANCE_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Communication Port Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMMUNICATION_PORT_INSTANCE_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ProvidedCommunicationInstanceImpl <em>Provided Communication Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ProvidedCommunicationInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getProvidedCommunicationInstance()
	 * @generated
	 */
	int PROVIDED_COMMUNICATION_INSTANCE = 23;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION_INSTANCE__NAME = COMMUNICATION_PORT_INSTANCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION_INSTANCE__PROPERTIES = COMMUNICATION_PORT_INSTANCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION_INSTANCE__RESOURCES = COMMUNICATION_PORT_INSTANCE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION_INSTANCE__TYPE = COMMUNICATION_PORT_INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION_INSTANCE__COMPONENT_INSTANCE = COMMUNICATION_PORT_INSTANCE__COMPONENT_INSTANCE;

	/**
	 * The number of structural features of the '<em>Provided Communication Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION_INSTANCE_FEATURE_COUNT = COMMUNICATION_PORT_INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Provided Communication Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_COMMUNICATION_INSTANCE_OPERATION_COUNT = COMMUNICATION_PORT_INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.RequiredCommunicationInstanceImpl <em>Required Communication Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.RequiredCommunicationInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getRequiredCommunicationInstance()
	 * @generated
	 */
	int REQUIRED_COMMUNICATION_INSTANCE = 24;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION_INSTANCE__NAME = COMMUNICATION_PORT_INSTANCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION_INSTANCE__PROPERTIES = COMMUNICATION_PORT_INSTANCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION_INSTANCE__RESOURCES = COMMUNICATION_PORT_INSTANCE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION_INSTANCE__TYPE = COMMUNICATION_PORT_INSTANCE__TYPE;

	/**
	 * The feature id for the '<em><b>Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION_INSTANCE__COMPONENT_INSTANCE = COMMUNICATION_PORT_INSTANCE__COMPONENT_INSTANCE;

	/**
	 * The number of structural features of the '<em>Required Communication Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION_INSTANCE_FEATURE_COUNT = COMMUNICATION_PORT_INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Required Communication Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_COMMUNICATION_INSTANCE_OPERATION_COUNT = COMMUNICATION_PORT_INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.HostingInstanceImpl <em>Hosting Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.HostingInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getHostingInstance()
	 * @generated
	 */
	int HOSTING_INSTANCE = 25;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_INSTANCE__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_INSTANCE__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_INSTANCE__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Provided Host Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_INSTANCE__PROVIDED_HOST_INSTANCE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Required Host Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_INSTANCE__REQUIRED_HOST_INSTANCE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_INSTANCE__TYPE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Hosting Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_INSTANCE_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Hosting Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_INSTANCE_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.HostingPortInstanceImpl <em>Hosting Port Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.HostingPortInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getHostingPortInstance()
	 * @generated
	 */
	int HOSTING_PORT_INSTANCE = 26;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT_INSTANCE__NAME = CLOUD_ML_ELEMENT__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT_INSTANCE__PROPERTIES = CLOUD_ML_ELEMENT__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT_INSTANCE__RESOURCES = CLOUD_ML_ELEMENT__RESOURCES;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT_INSTANCE__OWNER = CLOUD_ML_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT_INSTANCE__TYPE = CLOUD_ML_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Hosting Port Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT_INSTANCE_FEATURE_COUNT = CLOUD_ML_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Hosting Port Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int HOSTING_PORT_INSTANCE_OPERATION_COUNT = CLOUD_ML_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.ProvidedHostInstanceImpl <em>Provided Host Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.ProvidedHostInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getProvidedHostInstance()
	 * @generated
	 */
	int PROVIDED_HOST_INSTANCE = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST_INSTANCE__NAME = HOSTING_PORT_INSTANCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST_INSTANCE__PROPERTIES = HOSTING_PORT_INSTANCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST_INSTANCE__RESOURCES = HOSTING_PORT_INSTANCE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST_INSTANCE__OWNER = HOSTING_PORT_INSTANCE__OWNER;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST_INSTANCE__TYPE = HOSTING_PORT_INSTANCE__TYPE;

	/**
	 * The number of structural features of the '<em>Provided Host Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST_INSTANCE_FEATURE_COUNT = HOSTING_PORT_INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Provided Host Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROVIDED_HOST_INSTANCE_OPERATION_COUNT = HOSTING_PORT_INSTANCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.deployment.impl.RequiredHostInstanceImpl <em>Required Host Instance</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.deployment.impl.RequiredHostInstanceImpl
	 * @see camel.deployment.impl.DeploymentPackageImpl#getRequiredHostInstance()
	 * @generated
	 */
	int REQUIRED_HOST_INSTANCE = 28;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST_INSTANCE__NAME = HOSTING_PORT_INSTANCE__NAME;

	/**
	 * The feature id for the '<em><b>Properties</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST_INSTANCE__PROPERTIES = HOSTING_PORT_INSTANCE__PROPERTIES;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST_INSTANCE__RESOURCES = HOSTING_PORT_INSTANCE__RESOURCES;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST_INSTANCE__OWNER = HOSTING_PORT_INSTANCE__OWNER;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST_INSTANCE__TYPE = HOSTING_PORT_INSTANCE__TYPE;

	/**
	 * The number of structural features of the '<em>Required Host Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST_INSTANCE_FEATURE_COUNT = HOSTING_PORT_INSTANCE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Required Host Instance</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIRED_HOST_INSTANCE_OPERATION_COUNT = HOSTING_PORT_INSTANCE_OPERATION_COUNT + 0;


	/**
	 * Returns the meta object for class '{@link camel.deployment.CloudMLElement <em>Cloud ML Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cloud ML Element</em>'.
	 * @see camel.deployment.CloudMLElement
	 * @generated
	 */
	EClass getCloudMLElement();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.CloudMLElement#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.deployment.CloudMLElement#getName()
	 * @see #getCloudMLElement()
	 * @generated
	 */
	EAttribute getCloudMLElement_Name();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.CloudMLElement#getProperties <em>Properties</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Properties</em>'.
	 * @see camel.deployment.CloudMLElement#getProperties()
	 * @see #getCloudMLElement()
	 * @generated
	 */
	EReference getCloudMLElement_Properties();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.CloudMLElement#getResources <em>Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resources</em>'.
	 * @see camel.deployment.CloudMLElement#getResources()
	 * @see #getCloudMLElement()
	 * @generated
	 */
	EReference getCloudMLElement_Resources();

	/**
	 * Returns the meta object for class '{@link camel.deployment.DeploymentModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see camel.deployment.DeploymentModel
	 * @generated
	 */
	EClass getDeploymentModel();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.DeploymentModel#getComponents <em>Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Components</em>'.
	 * @see camel.deployment.DeploymentModel#getComponents()
	 * @see #getDeploymentModel()
	 * @generated
	 */
	EReference getDeploymentModel_Components();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.DeploymentModel#getCommunications <em>Communications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Communications</em>'.
	 * @see camel.deployment.DeploymentModel#getCommunications()
	 * @see #getDeploymentModel()
	 * @generated
	 */
	EReference getDeploymentModel_Communications();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.DeploymentModel#getHostings <em>Hostings</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hostings</em>'.
	 * @see camel.deployment.DeploymentModel#getHostings()
	 * @see #getDeploymentModel()
	 * @generated
	 */
	EReference getDeploymentModel_Hostings();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.DeploymentModel#getImages <em>Images</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Images</em>'.
	 * @see camel.deployment.DeploymentModel#getImages()
	 * @see #getDeploymentModel()
	 * @generated
	 */
	EReference getDeploymentModel_Images();

	/**
	 * Returns the meta object for the reference list '{@link camel.deployment.DeploymentModel#getProviders <em>Providers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Providers</em>'.
	 * @see camel.deployment.DeploymentModel#getProviders()
	 * @see #getDeploymentModel()
	 * @generated
	 */
	EReference getDeploymentModel_Providers();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.DeploymentModel#getComponentInstances <em>Component Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Component Instances</em>'.
	 * @see camel.deployment.DeploymentModel#getComponentInstances()
	 * @see #getDeploymentModel()
	 * @generated
	 */
	EReference getDeploymentModel_ComponentInstances();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.DeploymentModel#getCommunicationInstances <em>Communication Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Communication Instances</em>'.
	 * @see camel.deployment.DeploymentModel#getCommunicationInstances()
	 * @see #getDeploymentModel()
	 * @generated
	 */
	EReference getDeploymentModel_CommunicationInstances();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.DeploymentModel#getHostingInstances <em>Hosting Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Hosting Instances</em>'.
	 * @see camel.deployment.DeploymentModel#getHostingInstances()
	 * @see #getDeploymentModel()
	 * @generated
	 */
	EReference getDeploymentModel_HostingInstances();

	/**
	 * Returns the meta object for class '{@link camel.deployment.Component <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component</em>'.
	 * @see camel.deployment.Component
	 * @generated
	 */
	EClass getComponent();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.Component#getProvidedCommunications <em>Provided Communications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Provided Communications</em>'.
	 * @see camel.deployment.Component#getProvidedCommunications()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_ProvidedCommunications();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.Component#getProvidedHosts <em>Provided Hosts</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Provided Hosts</em>'.
	 * @see camel.deployment.Component#getProvidedHosts()
	 * @see #getComponent()
	 * @generated
	 */
	EReference getComponent_ProvidedHosts();

	/**
	 * Returns the meta object for class '{@link camel.deployment.InternalComponent <em>Internal Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Internal Component</em>'.
	 * @see camel.deployment.InternalComponent
	 * @generated
	 */
	EClass getInternalComponent();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.InternalComponent#getRequiredCommunications <em>Required Communications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Required Communications</em>'.
	 * @see camel.deployment.InternalComponent#getRequiredCommunications()
	 * @see #getInternalComponent()
	 * @generated
	 */
	EReference getInternalComponent_RequiredCommunications();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.InternalComponent#getCompositeInternalComponents <em>Composite Internal Components</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Composite Internal Components</em>'.
	 * @see camel.deployment.InternalComponent#getCompositeInternalComponents()
	 * @see #getInternalComponent()
	 * @generated
	 */
	EReference getInternalComponent_CompositeInternalComponents();

	/**
	 * Returns the meta object for the containment reference '{@link camel.deployment.InternalComponent#getRequiredHost <em>Required Host</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Required Host</em>'.
	 * @see camel.deployment.InternalComponent#getRequiredHost()
	 * @see #getInternalComponent()
	 * @generated
	 */
	EReference getInternalComponent_RequiredHost();

	/**
	 * Returns the meta object for the '{@link camel.deployment.InternalComponent#contains(camel.deployment.InternalComponent, camel.deployment.InternalComponent) <em>Contains</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Contains</em>' operation.
	 * @see camel.deployment.InternalComponent#contains(camel.deployment.InternalComponent, camel.deployment.InternalComponent)
	 * @generated
	 */
	EOperation getInternalComponent__Contains__InternalComponent_InternalComponent();

	/**
	 * Returns the meta object for class '{@link camel.deployment.ExternalComponent <em>External Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Component</em>'.
	 * @see camel.deployment.ExternalComponent
	 * @generated
	 */
	EClass getExternalComponent();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.ExternalComponent#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provider</em>'.
	 * @see camel.deployment.ExternalComponent#getProvider()
	 * @see #getExternalComponent()
	 * @generated
	 */
	EReference getExternalComponent_Provider();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.ExternalComponent#getLocation <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Location</em>'.
	 * @see camel.deployment.ExternalComponent#getLocation()
	 * @see #getExternalComponent()
	 * @generated
	 */
	EReference getExternalComponent_Location();

	/**
	 * Returns the meta object for class '{@link camel.deployment.VM <em>VM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VM</em>'.
	 * @see camel.deployment.VM
	 * @generated
	 */
	EClass getVM();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#getMinRam <em>Min Ram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Ram</em>'.
	 * @see camel.deployment.VM#getMinRam()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_MinRam();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#getMaxRam <em>Max Ram</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Ram</em>'.
	 * @see camel.deployment.VM#getMaxRam()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_MaxRam();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.VM#getRamUnit <em>Ram Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Ram Unit</em>'.
	 * @see camel.deployment.VM#getRamUnit()
	 * @see #getVM()
	 * @generated
	 */
	EReference getVM_RamUnit();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#getMinCores <em>Min Cores</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Cores</em>'.
	 * @see camel.deployment.VM#getMinCores()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_MinCores();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#getMaxCores <em>Max Cores</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Cores</em>'.
	 * @see camel.deployment.VM#getMaxCores()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_MaxCores();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#getMinStorage <em>Min Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Storage</em>'.
	 * @see camel.deployment.VM#getMinStorage()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_MinStorage();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#getMaxStorage <em>Max Storage</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Storage</em>'.
	 * @see camel.deployment.VM#getMaxStorage()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_MaxStorage();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.VM#getStorageUnit <em>Storage Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Storage Unit</em>'.
	 * @see camel.deployment.VM#getStorageUnit()
	 * @see #getVM()
	 * @generated
	 */
	EReference getVM_StorageUnit();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#getMinCPU <em>Min CPU</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min CPU</em>'.
	 * @see camel.deployment.VM#getMinCPU()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_MinCPU();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#getMaxCPU <em>Max CPU</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max CPU</em>'.
	 * @see camel.deployment.VM#getMaxCPU()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_MaxCPU();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#getOs <em>Os</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Os</em>'.
	 * @see camel.deployment.VM#getOs()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_Os();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VM#isIs64os <em>Is64os</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is64os</em>'.
	 * @see camel.deployment.VM#isIs64os()
	 * @see #getVM()
	 * @generated
	 */
	EAttribute getVM_Is64os();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.VM#getVmType <em>Vm Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Vm Type</em>'.
	 * @see camel.deployment.VM#getVmType()
	 * @see #getVM()
	 * @generated
	 */
	EReference getVM_VmType();

	/**
	 * Returns the meta object for class '{@link camel.deployment.ComponentGroup <em>Component Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component Group</em>'.
	 * @see camel.deployment.ComponentGroup
	 * @generated
	 */
	EClass getComponentGroup();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.ComponentGroup#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see camel.deployment.ComponentGroup#getId()
	 * @see #getComponentGroup()
	 * @generated
	 */
	EAttribute getComponentGroup_Id();

	/**
	 * Returns the meta object for class '{@link camel.deployment.ComputationalResource <em>Computational Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Computational Resource</em>'.
	 * @see camel.deployment.ComputationalResource
	 * @generated
	 */
	EClass getComputationalResource();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.ComputationalResource#getDownloadCommand <em>Download Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Download Command</em>'.
	 * @see camel.deployment.ComputationalResource#getDownloadCommand()
	 * @see #getComputationalResource()
	 * @generated
	 */
	EAttribute getComputationalResource_DownloadCommand();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.ComputationalResource#getUploadCommand <em>Upload Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Upload Command</em>'.
	 * @see camel.deployment.ComputationalResource#getUploadCommand()
	 * @see #getComputationalResource()
	 * @generated
	 */
	EAttribute getComputationalResource_UploadCommand();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.ComputationalResource#getInstallCommand <em>Install Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Install Command</em>'.
	 * @see camel.deployment.ComputationalResource#getInstallCommand()
	 * @see #getComputationalResource()
	 * @generated
	 */
	EAttribute getComputationalResource_InstallCommand();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.ComputationalResource#getConfigureCommand <em>Configure Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Configure Command</em>'.
	 * @see camel.deployment.ComputationalResource#getConfigureCommand()
	 * @see #getComputationalResource()
	 * @generated
	 */
	EAttribute getComputationalResource_ConfigureCommand();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.ComputationalResource#getStartCommand <em>Start Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start Command</em>'.
	 * @see camel.deployment.ComputationalResource#getStartCommand()
	 * @see #getComputationalResource()
	 * @generated
	 */
	EAttribute getComputationalResource_StartCommand();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.ComputationalResource#getStopCommand <em>Stop Command</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Stop Command</em>'.
	 * @see camel.deployment.ComputationalResource#getStopCommand()
	 * @see #getComputationalResource()
	 * @generated
	 */
	EAttribute getComputationalResource_StopCommand();

	/**
	 * Returns the meta object for class '{@link camel.deployment.Communication <em>Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Communication</em>'.
	 * @see camel.deployment.Communication
	 * @generated
	 */
	EClass getCommunication();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.Communication#getRequiredCommunication <em>Required Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Required Communication</em>'.
	 * @see camel.deployment.Communication#getRequiredCommunication()
	 * @see #getCommunication()
	 * @generated
	 */
	EReference getCommunication_RequiredCommunication();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.Communication#getProvidedCommunication <em>Provided Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provided Communication</em>'.
	 * @see camel.deployment.Communication#getProvidedCommunication()
	 * @see #getCommunication()
	 * @generated
	 */
	EReference getCommunication_ProvidedCommunication();

	/**
	 * Returns the meta object for the containment reference '{@link camel.deployment.Communication#getRequiredPortResource <em>Required Port Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Required Port Resource</em>'.
	 * @see camel.deployment.Communication#getRequiredPortResource()
	 * @see #getCommunication()
	 * @generated
	 */
	EReference getCommunication_RequiredPortResource();

	/**
	 * Returns the meta object for the containment reference '{@link camel.deployment.Communication#getProvidedPortResource <em>Provided Port Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Provided Port Resource</em>'.
	 * @see camel.deployment.Communication#getProvidedPortResource()
	 * @see #getCommunication()
	 * @generated
	 */
	EReference getCommunication_ProvidedPortResource();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.Communication#getOfDataObject <em>Of Data Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of Data Object</em>'.
	 * @see camel.deployment.Communication#getOfDataObject()
	 * @see #getCommunication()
	 * @generated
	 */
	EReference getCommunication_OfDataObject();

	/**
	 * Returns the meta object for class '{@link camel.deployment.CommunicationPort <em>Communication Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Communication Port</em>'.
	 * @see camel.deployment.CommunicationPort
	 * @generated
	 */
	EClass getCommunicationPort();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.CommunicationPort#getComponent <em>Component</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Component</em>'.
	 * @see camel.deployment.CommunicationPort#getComponent()
	 * @see #getCommunicationPort()
	 * @generated
	 */
	EReference getCommunicationPort_Component();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.CommunicationPort#isIsLocal <em>Is Local</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Local</em>'.
	 * @see camel.deployment.CommunicationPort#isIsLocal()
	 * @see #getCommunicationPort()
	 * @generated
	 */
	EAttribute getCommunicationPort_IsLocal();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.CommunicationPort#getPortNumber <em>Port Number</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Port Number</em>'.
	 * @see camel.deployment.CommunicationPort#getPortNumber()
	 * @see #getCommunicationPort()
	 * @generated
	 */
	EAttribute getCommunicationPort_PortNumber();

	/**
	 * Returns the meta object for class '{@link camel.deployment.ProvidedCommunication <em>Provided Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provided Communication</em>'.
	 * @see camel.deployment.ProvidedCommunication
	 * @generated
	 */
	EClass getProvidedCommunication();

	/**
	 * Returns the meta object for class '{@link camel.deployment.RequiredCommunication <em>Required Communication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Communication</em>'.
	 * @see camel.deployment.RequiredCommunication
	 * @generated
	 */
	EClass getRequiredCommunication();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.RequiredCommunication#isIsMandatory <em>Is Mandatory</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is Mandatory</em>'.
	 * @see camel.deployment.RequiredCommunication#isIsMandatory()
	 * @see #getRequiredCommunication()
	 * @generated
	 */
	EAttribute getRequiredCommunication_IsMandatory();

	/**
	 * Returns the meta object for class '{@link camel.deployment.Hosting <em>Hosting</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hosting</em>'.
	 * @see camel.deployment.Hosting
	 * @generated
	 */
	EClass getHosting();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.Hosting#getProvidedHost <em>Provided Host</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provided Host</em>'.
	 * @see camel.deployment.Hosting#getProvidedHost()
	 * @see #getHosting()
	 * @generated
	 */
	EReference getHosting_ProvidedHost();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.Hosting#getRequiredHost <em>Required Host</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Required Host</em>'.
	 * @see camel.deployment.Hosting#getRequiredHost()
	 * @see #getHosting()
	 * @generated
	 */
	EReference getHosting_RequiredHost();

	/**
	 * Returns the meta object for the containment reference '{@link camel.deployment.Hosting#getRequiredHostResource <em>Required Host Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Required Host Resource</em>'.
	 * @see camel.deployment.Hosting#getRequiredHostResource()
	 * @see #getHosting()
	 * @generated
	 */
	EReference getHosting_RequiredHostResource();

	/**
	 * Returns the meta object for the containment reference '{@link camel.deployment.Hosting#getProvidedHostResource <em>Provided Host Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Provided Host Resource</em>'.
	 * @see camel.deployment.Hosting#getProvidedHostResource()
	 * @see #getHosting()
	 * @generated
	 */
	EReference getHosting_ProvidedHostResource();

	/**
	 * Returns the meta object for class '{@link camel.deployment.HostingPort <em>Hosting Port</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hosting Port</em>'.
	 * @see camel.deployment.HostingPort
	 * @generated
	 */
	EClass getHostingPort();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.HostingPort#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner</em>'.
	 * @see camel.deployment.HostingPort#getOwner()
	 * @see #getHostingPort()
	 * @generated
	 */
	EReference getHostingPort_Owner();

	/**
	 * Returns the meta object for class '{@link camel.deployment.ProvidedHost <em>Provided Host</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provided Host</em>'.
	 * @see camel.deployment.ProvidedHost
	 * @generated
	 */
	EClass getProvidedHost();

	/**
	 * Returns the meta object for the reference list '{@link camel.deployment.ProvidedHost#getOffers <em>Offers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Offers</em>'.
	 * @see camel.deployment.ProvidedHost#getOffers()
	 * @see #getProvidedHost()
	 * @generated
	 */
	EReference getProvidedHost_Offers();

	/**
	 * Returns the meta object for class '{@link camel.deployment.RequiredHost <em>Required Host</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Host</em>'.
	 * @see camel.deployment.RequiredHost
	 * @generated
	 */
	EClass getRequiredHost();

	/**
	 * Returns the meta object for the reference list '{@link camel.deployment.RequiredHost#getDemands <em>Demands</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Demands</em>'.
	 * @see camel.deployment.RequiredHost#getDemands()
	 * @see #getRequiredHost()
	 * @generated
	 */
	EReference getRequiredHost_Demands();

	/**
	 * Returns the meta object for class '{@link camel.deployment.Image <em>Image</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Image</em>'.
	 * @see camel.deployment.Image
	 * @generated
	 */
	EClass getImage();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.Image#getOs <em>Os</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Os</em>'.
	 * @see camel.deployment.Image#getOs()
	 * @see #getImage()
	 * @generated
	 */
	EAttribute getImage_Os();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.Image#isIs64os <em>Is64os</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Is64os</em>'.
	 * @see camel.deployment.Image#isIs64os()
	 * @see #getImage()
	 * @generated
	 */
	EAttribute getImage_Is64os();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.Image#getImageId <em>Image Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image Id</em>'.
	 * @see camel.deployment.Image#getImageId()
	 * @see #getImage()
	 * @generated
	 */
	EAttribute getImage_ImageId();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.Image#getImageGroup <em>Image Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Image Group</em>'.
	 * @see camel.deployment.Image#getImageGroup()
	 * @see #getImage()
	 * @generated
	 */
	EAttribute getImage_ImageGroup();

	/**
	 * Returns the meta object for class '{@link camel.deployment.ComponentInstance <em>Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Component Instance</em>'.
	 * @see camel.deployment.ComponentInstance
	 * @generated
	 */
	EClass getComponentInstance();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.ComponentInstance#getProvidedCommunicationInstances <em>Provided Communication Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Provided Communication Instances</em>'.
	 * @see camel.deployment.ComponentInstance#getProvidedCommunicationInstances()
	 * @see #getComponentInstance()
	 * @generated
	 */
	EReference getComponentInstance_ProvidedCommunicationInstances();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.ComponentInstance#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see camel.deployment.ComponentInstance#getType()
	 * @see #getComponentInstance()
	 * @generated
	 */
	EReference getComponentInstance_Type();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.ComponentInstance#getProvidedHostInstances <em>Provided Host Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Provided Host Instances</em>'.
	 * @see camel.deployment.ComponentInstance#getProvidedHostInstances()
	 * @see #getComponentInstance()
	 * @generated
	 */
	EReference getComponentInstance_ProvidedHostInstances();

	/**
	 * Returns the meta object for class '{@link camel.deployment.InternalComponentInstance <em>Internal Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Internal Component Instance</em>'.
	 * @see camel.deployment.InternalComponentInstance
	 * @generated
	 */
	EClass getInternalComponentInstance();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.deployment.InternalComponentInstance#getRequiredCommunicationInstances <em>Required Communication Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Required Communication Instances</em>'.
	 * @see camel.deployment.InternalComponentInstance#getRequiredCommunicationInstances()
	 * @see #getInternalComponentInstance()
	 * @generated
	 */
	EReference getInternalComponentInstance_RequiredCommunicationInstances();

	/**
	 * Returns the meta object for the containment reference '{@link camel.deployment.InternalComponentInstance#getRequiredHostInstance <em>Required Host Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Required Host Instance</em>'.
	 * @see camel.deployment.InternalComponentInstance#getRequiredHostInstance()
	 * @see #getInternalComponentInstance()
	 * @generated
	 */
	EReference getInternalComponentInstance_RequiredHostInstance();

	/**
	 * Returns the meta object for class '{@link camel.deployment.ExternalComponentInstance <em>External Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Component Instance</em>'.
	 * @see camel.deployment.ExternalComponentInstance
	 * @generated
	 */
	EClass getExternalComponentInstance();

	/**
	 * Returns the meta object for the attribute list '{@link camel.deployment.ExternalComponentInstance#getIps <em>Ips</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Ips</em>'.
	 * @see camel.deployment.ExternalComponentInstance#getIps()
	 * @see #getExternalComponentInstance()
	 * @generated
	 */
	EAttribute getExternalComponentInstance_Ips();

	/**
	 * Returns the meta object for class '{@link camel.deployment.VMInstance <em>VM Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VM Instance</em>'.
	 * @see camel.deployment.VMInstance
	 * @generated
	 */
	EClass getVMInstance();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VMInstance#getPublicAddress <em>Public Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Public Address</em>'.
	 * @see camel.deployment.VMInstance#getPublicAddress()
	 * @see #getVMInstance()
	 * @generated
	 */
	EAttribute getVMInstance_PublicAddress();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VMInstance#getCreatedOn <em>Created On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created On</em>'.
	 * @see camel.deployment.VMInstance#getCreatedOn()
	 * @see #getVMInstance()
	 * @generated
	 */
	EAttribute getVMInstance_CreatedOn();

	/**
	 * Returns the meta object for the attribute '{@link camel.deployment.VMInstance#getDestroyedOn <em>Destroyed On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Destroyed On</em>'.
	 * @see camel.deployment.VMInstance#getDestroyedOn()
	 * @see #getVMInstance()
	 * @generated
	 */
	EAttribute getVMInstance_DestroyedOn();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.VMInstance#getHasConfig <em>Has Config</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Has Config</em>'.
	 * @see camel.deployment.VMInstance#getHasConfig()
	 * @see #getVMInstance()
	 * @generated
	 */
	EReference getVMInstance_HasConfig();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.VMInstance#getHasInfo <em>Has Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Has Info</em>'.
	 * @see camel.deployment.VMInstance#getHasInfo()
	 * @see #getVMInstance()
	 * @generated
	 */
	EReference getVMInstance_HasInfo();

	/**
	 * Returns the meta object for the '{@link camel.deployment.VMInstance#checkDates(camel.deployment.VMInstance) <em>Check Dates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Dates</em>' operation.
	 * @see camel.deployment.VMInstance#checkDates(camel.deployment.VMInstance)
	 * @generated
	 */
	EOperation getVMInstance__CheckDates__VMInstance();

	/**
	 * Returns the meta object for class '{@link camel.deployment.CommunicationInstance <em>Communication Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Communication Instance</em>'.
	 * @see camel.deployment.CommunicationInstance
	 * @generated
	 */
	EClass getCommunicationInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.CommunicationInstance#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see camel.deployment.CommunicationInstance#getType()
	 * @see #getCommunicationInstance()
	 * @generated
	 */
	EReference getCommunicationInstance_Type();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.CommunicationInstance#getRequiredCommunicationInstance <em>Required Communication Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Required Communication Instance</em>'.
	 * @see camel.deployment.CommunicationInstance#getRequiredCommunicationInstance()
	 * @see #getCommunicationInstance()
	 * @generated
	 */
	EReference getCommunicationInstance_RequiredCommunicationInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.CommunicationInstance#getProvidedCommunicationInstance <em>Provided Communication Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provided Communication Instance</em>'.
	 * @see camel.deployment.CommunicationInstance#getProvidedCommunicationInstance()
	 * @see #getCommunicationInstance()
	 * @generated
	 */
	EReference getCommunicationInstance_ProvidedCommunicationInstance();

	/**
	 * Returns the meta object for class '{@link camel.deployment.CommunicationPortInstance <em>Communication Port Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Communication Port Instance</em>'.
	 * @see camel.deployment.CommunicationPortInstance
	 * @generated
	 */
	EClass getCommunicationPortInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.CommunicationPortInstance#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see camel.deployment.CommunicationPortInstance#getType()
	 * @see #getCommunicationPortInstance()
	 * @generated
	 */
	EReference getCommunicationPortInstance_Type();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.CommunicationPortInstance#getComponentInstance <em>Component Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Component Instance</em>'.
	 * @see camel.deployment.CommunicationPortInstance#getComponentInstance()
	 * @see #getCommunicationPortInstance()
	 * @generated
	 */
	EReference getCommunicationPortInstance_ComponentInstance();

	/**
	 * Returns the meta object for class '{@link camel.deployment.ProvidedCommunicationInstance <em>Provided Communication Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provided Communication Instance</em>'.
	 * @see camel.deployment.ProvidedCommunicationInstance
	 * @generated
	 */
	EClass getProvidedCommunicationInstance();

	/**
	 * Returns the meta object for class '{@link camel.deployment.RequiredCommunicationInstance <em>Required Communication Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Communication Instance</em>'.
	 * @see camel.deployment.RequiredCommunicationInstance
	 * @generated
	 */
	EClass getRequiredCommunicationInstance();

	/**
	 * Returns the meta object for class '{@link camel.deployment.HostingInstance <em>Hosting Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hosting Instance</em>'.
	 * @see camel.deployment.HostingInstance
	 * @generated
	 */
	EClass getHostingInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.HostingInstance#getProvidedHostInstance <em>Provided Host Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provided Host Instance</em>'.
	 * @see camel.deployment.HostingInstance#getProvidedHostInstance()
	 * @see #getHostingInstance()
	 * @generated
	 */
	EReference getHostingInstance_ProvidedHostInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.HostingInstance#getRequiredHostInstance <em>Required Host Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Required Host Instance</em>'.
	 * @see camel.deployment.HostingInstance#getRequiredHostInstance()
	 * @see #getHostingInstance()
	 * @generated
	 */
	EReference getHostingInstance_RequiredHostInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.HostingInstance#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see camel.deployment.HostingInstance#getType()
	 * @see #getHostingInstance()
	 * @generated
	 */
	EReference getHostingInstance_Type();

	/**
	 * Returns the meta object for class '{@link camel.deployment.HostingPortInstance <em>Hosting Port Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Hosting Port Instance</em>'.
	 * @see camel.deployment.HostingPortInstance
	 * @generated
	 */
	EClass getHostingPortInstance();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.HostingPortInstance#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner</em>'.
	 * @see camel.deployment.HostingPortInstance#getOwner()
	 * @see #getHostingPortInstance()
	 * @generated
	 */
	EReference getHostingPortInstance_Owner();

	/**
	 * Returns the meta object for the reference '{@link camel.deployment.HostingPortInstance#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see camel.deployment.HostingPortInstance#getType()
	 * @see #getHostingPortInstance()
	 * @generated
	 */
	EReference getHostingPortInstance_Type();

	/**
	 * Returns the meta object for class '{@link camel.deployment.ProvidedHostInstance <em>Provided Host Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Provided Host Instance</em>'.
	 * @see camel.deployment.ProvidedHostInstance
	 * @generated
	 */
	EClass getProvidedHostInstance();

	/**
	 * Returns the meta object for class '{@link camel.deployment.RequiredHostInstance <em>Required Host Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Required Host Instance</em>'.
	 * @see camel.deployment.RequiredHostInstance
	 * @generated
	 */
	EClass getRequiredHostInstance();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	DeploymentFactory getDeploymentFactory();

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
		 * The meta object literal for the '{@link camel.deployment.impl.CloudMLElementImpl <em>Cloud ML Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.CloudMLElementImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getCloudMLElement()
		 * @generated
		 */
		EClass CLOUD_ML_ELEMENT = eINSTANCE.getCloudMLElement();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_ML_ELEMENT__NAME = eINSTANCE.getCloudMLElement_Name();

		/**
		 * The meta object literal for the '<em><b>Properties</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLOUD_ML_ELEMENT__PROPERTIES = eINSTANCE.getCloudMLElement_Properties();

		/**
		 * The meta object literal for the '<em><b>Resources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLOUD_ML_ELEMENT__RESOURCES = eINSTANCE.getCloudMLElement_Resources();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.DeploymentModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.DeploymentModelImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getDeploymentModel()
		 * @generated
		 */
		EClass DEPLOYMENT_MODEL = eINSTANCE.getDeploymentModel();

		/**
		 * The meta object literal for the '<em><b>Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_MODEL__COMPONENTS = eINSTANCE.getDeploymentModel_Components();

		/**
		 * The meta object literal for the '<em><b>Communications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_MODEL__COMMUNICATIONS = eINSTANCE.getDeploymentModel_Communications();

		/**
		 * The meta object literal for the '<em><b>Hostings</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_MODEL__HOSTINGS = eINSTANCE.getDeploymentModel_Hostings();

		/**
		 * The meta object literal for the '<em><b>Images</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_MODEL__IMAGES = eINSTANCE.getDeploymentModel_Images();

		/**
		 * The meta object literal for the '<em><b>Providers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_MODEL__PROVIDERS = eINSTANCE.getDeploymentModel_Providers();

		/**
		 * The meta object literal for the '<em><b>Component Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_MODEL__COMPONENT_INSTANCES = eINSTANCE.getDeploymentModel_ComponentInstances();

		/**
		 * The meta object literal for the '<em><b>Communication Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_MODEL__COMMUNICATION_INSTANCES = eINSTANCE.getDeploymentModel_CommunicationInstances();

		/**
		 * The meta object literal for the '<em><b>Hosting Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DEPLOYMENT_MODEL__HOSTING_INSTANCES = eINSTANCE.getDeploymentModel_HostingInstances();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ComponentImpl <em>Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ComponentImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getComponent()
		 * @generated
		 */
		EClass COMPONENT = eINSTANCE.getComponent();

		/**
		 * The meta object literal for the '<em><b>Provided Communications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__PROVIDED_COMMUNICATIONS = eINSTANCE.getComponent_ProvidedCommunications();

		/**
		 * The meta object literal for the '<em><b>Provided Hosts</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT__PROVIDED_HOSTS = eINSTANCE.getComponent_ProvidedHosts();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.InternalComponentImpl <em>Internal Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.InternalComponentImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getInternalComponent()
		 * @generated
		 */
		EClass INTERNAL_COMPONENT = eINSTANCE.getInternalComponent();

		/**
		 * The meta object literal for the '<em><b>Required Communications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_COMPONENT__REQUIRED_COMMUNICATIONS = eINSTANCE.getInternalComponent_RequiredCommunications();

		/**
		 * The meta object literal for the '<em><b>Composite Internal Components</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_COMPONENT__COMPOSITE_INTERNAL_COMPONENTS = eINSTANCE.getInternalComponent_CompositeInternalComponents();

		/**
		 * The meta object literal for the '<em><b>Required Host</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_COMPONENT__REQUIRED_HOST = eINSTANCE.getInternalComponent_RequiredHost();

		/**
		 * The meta object literal for the '<em><b>Contains</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation INTERNAL_COMPONENT___CONTAINS__INTERNALCOMPONENT_INTERNALCOMPONENT = eINSTANCE.getInternalComponent__Contains__InternalComponent_InternalComponent();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ExternalComponentImpl <em>External Component</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ExternalComponentImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getExternalComponent()
		 * @generated
		 */
		EClass EXTERNAL_COMPONENT = eINSTANCE.getExternalComponent();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_COMPONENT__PROVIDER = eINSTANCE.getExternalComponent_Provider();

		/**
		 * The meta object literal for the '<em><b>Location</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference EXTERNAL_COMPONENT__LOCATION = eINSTANCE.getExternalComponent_Location();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.VMImpl <em>VM</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.VMImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getVM()
		 * @generated
		 */
		EClass VM = eINSTANCE.getVM();

		/**
		 * The meta object literal for the '<em><b>Min Ram</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__MIN_RAM = eINSTANCE.getVM_MinRam();

		/**
		 * The meta object literal for the '<em><b>Max Ram</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__MAX_RAM = eINSTANCE.getVM_MaxRam();

		/**
		 * The meta object literal for the '<em><b>Ram Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VM__RAM_UNIT = eINSTANCE.getVM_RamUnit();

		/**
		 * The meta object literal for the '<em><b>Min Cores</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__MIN_CORES = eINSTANCE.getVM_MinCores();

		/**
		 * The meta object literal for the '<em><b>Max Cores</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__MAX_CORES = eINSTANCE.getVM_MaxCores();

		/**
		 * The meta object literal for the '<em><b>Min Storage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__MIN_STORAGE = eINSTANCE.getVM_MinStorage();

		/**
		 * The meta object literal for the '<em><b>Max Storage</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__MAX_STORAGE = eINSTANCE.getVM_MaxStorage();

		/**
		 * The meta object literal for the '<em><b>Storage Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VM__STORAGE_UNIT = eINSTANCE.getVM_StorageUnit();

		/**
		 * The meta object literal for the '<em><b>Min CPU</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__MIN_CPU = eINSTANCE.getVM_MinCPU();

		/**
		 * The meta object literal for the '<em><b>Max CPU</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__MAX_CPU = eINSTANCE.getVM_MaxCPU();

		/**
		 * The meta object literal for the '<em><b>Os</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__OS = eINSTANCE.getVM_Os();

		/**
		 * The meta object literal for the '<em><b>Is64os</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM__IS64OS = eINSTANCE.getVM_Is64os();

		/**
		 * The meta object literal for the '<em><b>Vm Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VM__VM_TYPE = eINSTANCE.getVM_VmType();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ComponentGroupImpl <em>Component Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ComponentGroupImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getComponentGroup()
		 * @generated
		 */
		EClass COMPONENT_GROUP = eINSTANCE.getComponentGroup();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPONENT_GROUP__ID = eINSTANCE.getComponentGroup_Id();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ComputationalResourceImpl <em>Computational Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ComputationalResourceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getComputationalResource()
		 * @generated
		 */
		EClass COMPUTATIONAL_RESOURCE = eINSTANCE.getComputationalResource();

		/**
		 * The meta object literal for the '<em><b>Download Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPUTATIONAL_RESOURCE__DOWNLOAD_COMMAND = eINSTANCE.getComputationalResource_DownloadCommand();

		/**
		 * The meta object literal for the '<em><b>Upload Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPUTATIONAL_RESOURCE__UPLOAD_COMMAND = eINSTANCE.getComputationalResource_UploadCommand();

		/**
		 * The meta object literal for the '<em><b>Install Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPUTATIONAL_RESOURCE__INSTALL_COMMAND = eINSTANCE.getComputationalResource_InstallCommand();

		/**
		 * The meta object literal for the '<em><b>Configure Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPUTATIONAL_RESOURCE__CONFIGURE_COMMAND = eINSTANCE.getComputationalResource_ConfigureCommand();

		/**
		 * The meta object literal for the '<em><b>Start Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPUTATIONAL_RESOURCE__START_COMMAND = eINSTANCE.getComputationalResource_StartCommand();

		/**
		 * The meta object literal for the '<em><b>Stop Command</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPUTATIONAL_RESOURCE__STOP_COMMAND = eINSTANCE.getComputationalResource_StopCommand();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.CommunicationImpl <em>Communication</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.CommunicationImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getCommunication()
		 * @generated
		 */
		EClass COMMUNICATION = eINSTANCE.getCommunication();

		/**
		 * The meta object literal for the '<em><b>Required Communication</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION__REQUIRED_COMMUNICATION = eINSTANCE.getCommunication_RequiredCommunication();

		/**
		 * The meta object literal for the '<em><b>Provided Communication</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION__PROVIDED_COMMUNICATION = eINSTANCE.getCommunication_ProvidedCommunication();

		/**
		 * The meta object literal for the '<em><b>Required Port Resource</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION__REQUIRED_PORT_RESOURCE = eINSTANCE.getCommunication_RequiredPortResource();

		/**
		 * The meta object literal for the '<em><b>Provided Port Resource</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION__PROVIDED_PORT_RESOURCE = eINSTANCE.getCommunication_ProvidedPortResource();

		/**
		 * The meta object literal for the '<em><b>Of Data Object</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION__OF_DATA_OBJECT = eINSTANCE.getCommunication_OfDataObject();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.CommunicationPortImpl <em>Communication Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.CommunicationPortImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getCommunicationPort()
		 * @generated
		 */
		EClass COMMUNICATION_PORT = eINSTANCE.getCommunicationPort();

		/**
		 * The meta object literal for the '<em><b>Component</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION_PORT__COMPONENT = eINSTANCE.getCommunicationPort_Component();

		/**
		 * The meta object literal for the '<em><b>Is Local</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMUNICATION_PORT__IS_LOCAL = eINSTANCE.getCommunicationPort_IsLocal();

		/**
		 * The meta object literal for the '<em><b>Port Number</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMMUNICATION_PORT__PORT_NUMBER = eINSTANCE.getCommunicationPort_PortNumber();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ProvidedCommunicationImpl <em>Provided Communication</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ProvidedCommunicationImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getProvidedCommunication()
		 * @generated
		 */
		EClass PROVIDED_COMMUNICATION = eINSTANCE.getProvidedCommunication();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.RequiredCommunicationImpl <em>Required Communication</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.RequiredCommunicationImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getRequiredCommunication()
		 * @generated
		 */
		EClass REQUIRED_COMMUNICATION = eINSTANCE.getRequiredCommunication();

		/**
		 * The meta object literal for the '<em><b>Is Mandatory</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIRED_COMMUNICATION__IS_MANDATORY = eINSTANCE.getRequiredCommunication_IsMandatory();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.HostingImpl <em>Hosting</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.HostingImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getHosting()
		 * @generated
		 */
		EClass HOSTING = eINSTANCE.getHosting();

		/**
		 * The meta object literal for the '<em><b>Provided Host</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING__PROVIDED_HOST = eINSTANCE.getHosting_ProvidedHost();

		/**
		 * The meta object literal for the '<em><b>Required Host</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING__REQUIRED_HOST = eINSTANCE.getHosting_RequiredHost();

		/**
		 * The meta object literal for the '<em><b>Required Host Resource</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING__REQUIRED_HOST_RESOURCE = eINSTANCE.getHosting_RequiredHostResource();

		/**
		 * The meta object literal for the '<em><b>Provided Host Resource</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING__PROVIDED_HOST_RESOURCE = eINSTANCE.getHosting_ProvidedHostResource();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.HostingPortImpl <em>Hosting Port</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.HostingPortImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getHostingPort()
		 * @generated
		 */
		EClass HOSTING_PORT = eINSTANCE.getHostingPort();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING_PORT__OWNER = eINSTANCE.getHostingPort_Owner();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ProvidedHostImpl <em>Provided Host</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ProvidedHostImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getProvidedHost()
		 * @generated
		 */
		EClass PROVIDED_HOST = eINSTANCE.getProvidedHost();

		/**
		 * The meta object literal for the '<em><b>Offers</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROVIDED_HOST__OFFERS = eINSTANCE.getProvidedHost_Offers();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.RequiredHostImpl <em>Required Host</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.RequiredHostImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getRequiredHost()
		 * @generated
		 */
		EClass REQUIRED_HOST = eINSTANCE.getRequiredHost();

		/**
		 * The meta object literal for the '<em><b>Demands</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIRED_HOST__DEMANDS = eINSTANCE.getRequiredHost_Demands();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ImageImpl <em>Image</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ImageImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getImage()
		 * @generated
		 */
		EClass IMAGE = eINSTANCE.getImage();

		/**
		 * The meta object literal for the '<em><b>Os</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMAGE__OS = eINSTANCE.getImage_Os();

		/**
		 * The meta object literal for the '<em><b>Is64os</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMAGE__IS64OS = eINSTANCE.getImage_Is64os();

		/**
		 * The meta object literal for the '<em><b>Image Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMAGE__IMAGE_ID = eINSTANCE.getImage_ImageId();

		/**
		 * The meta object literal for the '<em><b>Image Group</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMAGE__IMAGE_GROUP = eINSTANCE.getImage_ImageGroup();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ComponentInstanceImpl <em>Component Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ComponentInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getComponentInstance()
		 * @generated
		 */
		EClass COMPONENT_INSTANCE = eINSTANCE.getComponentInstance();

		/**
		 * The meta object literal for the '<em><b>Provided Communication Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_INSTANCE__PROVIDED_COMMUNICATION_INSTANCES = eINSTANCE.getComponentInstance_ProvidedCommunicationInstances();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_INSTANCE__TYPE = eINSTANCE.getComponentInstance_Type();

		/**
		 * The meta object literal for the '<em><b>Provided Host Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPONENT_INSTANCE__PROVIDED_HOST_INSTANCES = eINSTANCE.getComponentInstance_ProvidedHostInstances();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.InternalComponentInstanceImpl <em>Internal Component Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.InternalComponentInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getInternalComponentInstance()
		 * @generated
		 */
		EClass INTERNAL_COMPONENT_INSTANCE = eINSTANCE.getInternalComponentInstance();

		/**
		 * The meta object literal for the '<em><b>Required Communication Instances</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_COMPONENT_INSTANCE__REQUIRED_COMMUNICATION_INSTANCES = eINSTANCE.getInternalComponentInstance_RequiredCommunicationInstances();

		/**
		 * The meta object literal for the '<em><b>Required Host Instance</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference INTERNAL_COMPONENT_INSTANCE__REQUIRED_HOST_INSTANCE = eINSTANCE.getInternalComponentInstance_RequiredHostInstance();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ExternalComponentInstanceImpl <em>External Component Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ExternalComponentInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getExternalComponentInstance()
		 * @generated
		 */
		EClass EXTERNAL_COMPONENT_INSTANCE = eINSTANCE.getExternalComponentInstance();

		/**
		 * The meta object literal for the '<em><b>Ips</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_COMPONENT_INSTANCE__IPS = eINSTANCE.getExternalComponentInstance_Ips();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.VMInstanceImpl <em>VM Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.VMInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getVMInstance()
		 * @generated
		 */
		EClass VM_INSTANCE = eINSTANCE.getVMInstance();

		/**
		 * The meta object literal for the '<em><b>Public Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM_INSTANCE__PUBLIC_ADDRESS = eINSTANCE.getVMInstance_PublicAddress();

		/**
		 * The meta object literal for the '<em><b>Created On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM_INSTANCE__CREATED_ON = eINSTANCE.getVMInstance_CreatedOn();

		/**
		 * The meta object literal for the '<em><b>Destroyed On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM_INSTANCE__DESTROYED_ON = eINSTANCE.getVMInstance_DestroyedOn();

		/**
		 * The meta object literal for the '<em><b>Has Config</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VM_INSTANCE__HAS_CONFIG = eINSTANCE.getVMInstance_HasConfig();

		/**
		 * The meta object literal for the '<em><b>Has Info</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VM_INSTANCE__HAS_INFO = eINSTANCE.getVMInstance_HasInfo();

		/**
		 * The meta object literal for the '<em><b>Check Dates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VM_INSTANCE___CHECK_DATES__VMINSTANCE = eINSTANCE.getVMInstance__CheckDates__VMInstance();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.CommunicationInstanceImpl <em>Communication Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.CommunicationInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getCommunicationInstance()
		 * @generated
		 */
		EClass COMMUNICATION_INSTANCE = eINSTANCE.getCommunicationInstance();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION_INSTANCE__TYPE = eINSTANCE.getCommunicationInstance_Type();

		/**
		 * The meta object literal for the '<em><b>Required Communication Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION_INSTANCE__REQUIRED_COMMUNICATION_INSTANCE = eINSTANCE.getCommunicationInstance_RequiredCommunicationInstance();

		/**
		 * The meta object literal for the '<em><b>Provided Communication Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION_INSTANCE__PROVIDED_COMMUNICATION_INSTANCE = eINSTANCE.getCommunicationInstance_ProvidedCommunicationInstance();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.CommunicationPortInstanceImpl <em>Communication Port Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.CommunicationPortInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getCommunicationPortInstance()
		 * @generated
		 */
		EClass COMMUNICATION_PORT_INSTANCE = eINSTANCE.getCommunicationPortInstance();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION_PORT_INSTANCE__TYPE = eINSTANCE.getCommunicationPortInstance_Type();

		/**
		 * The meta object literal for the '<em><b>Component Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMMUNICATION_PORT_INSTANCE__COMPONENT_INSTANCE = eINSTANCE.getCommunicationPortInstance_ComponentInstance();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ProvidedCommunicationInstanceImpl <em>Provided Communication Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ProvidedCommunicationInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getProvidedCommunicationInstance()
		 * @generated
		 */
		EClass PROVIDED_COMMUNICATION_INSTANCE = eINSTANCE.getProvidedCommunicationInstance();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.RequiredCommunicationInstanceImpl <em>Required Communication Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.RequiredCommunicationInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getRequiredCommunicationInstance()
		 * @generated
		 */
		EClass REQUIRED_COMMUNICATION_INSTANCE = eINSTANCE.getRequiredCommunicationInstance();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.HostingInstanceImpl <em>Hosting Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.HostingInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getHostingInstance()
		 * @generated
		 */
		EClass HOSTING_INSTANCE = eINSTANCE.getHostingInstance();

		/**
		 * The meta object literal for the '<em><b>Provided Host Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING_INSTANCE__PROVIDED_HOST_INSTANCE = eINSTANCE.getHostingInstance_ProvidedHostInstance();

		/**
		 * The meta object literal for the '<em><b>Required Host Instance</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING_INSTANCE__REQUIRED_HOST_INSTANCE = eINSTANCE.getHostingInstance_RequiredHostInstance();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING_INSTANCE__TYPE = eINSTANCE.getHostingInstance_Type();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.HostingPortInstanceImpl <em>Hosting Port Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.HostingPortInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getHostingPortInstance()
		 * @generated
		 */
		EClass HOSTING_PORT_INSTANCE = eINSTANCE.getHostingPortInstance();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING_PORT_INSTANCE__OWNER = eINSTANCE.getHostingPortInstance_Owner();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference HOSTING_PORT_INSTANCE__TYPE = eINSTANCE.getHostingPortInstance_Type();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.ProvidedHostInstanceImpl <em>Provided Host Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.ProvidedHostInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getProvidedHostInstance()
		 * @generated
		 */
		EClass PROVIDED_HOST_INSTANCE = eINSTANCE.getProvidedHostInstance();

		/**
		 * The meta object literal for the '{@link camel.deployment.impl.RequiredHostInstanceImpl <em>Required Host Instance</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.deployment.impl.RequiredHostInstanceImpl
		 * @see camel.deployment.impl.DeploymentPackageImpl#getRequiredHostInstance()
		 * @generated
		 */
		EClass REQUIRED_HOST_INSTANCE = eINSTANCE.getRequiredHostInstance();

	}

} //DeploymentPackage
