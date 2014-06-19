/**
 */
package camel;

import camel.cerif.CerifPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
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
 * @see camel.CamelFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/OCL/Import ecore='http://www.eclipse.org/emf/2002/Ecore#/'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface CamelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "camel";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/camel";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "camel";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CamelPackage eINSTANCE = camel.impl.CamelPackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.impl.CamelModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.CamelModelImpl
	 * @see camel.impl.CamelPackageImpl#getCamelModel()
	 * @generated
	 */
	int CAMEL_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Cerif Models</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__CERIF_MODELS = 0;

	/**
	 * The feature id for the '<em><b>Units</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__UNITS = 1;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__ACTIONS = 2;

	/**
	 * The feature id for the '<em><b>Nodes</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__NODES = 3;

	/**
	 * The feature id for the '<em><b>Objects</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__OBJECTS = 4;

	/**
	 * The feature id for the '<em><b>Requirements</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__REQUIREMENTS = 5;

	/**
	 * The feature id for the '<em><b>Policies</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__POLICIES = 6;

	/**
	 * The feature id for the '<em><b>Vm Infos</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__VM_INFOS = 7;

	/**
	 * The feature id for the '<em><b>Independent Vm Infos</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__INDEPENDENT_VM_INFOS = 8;

	/**
	 * The feature id for the '<em><b>Vm Type</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__VM_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Applications</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL__APPLICATIONS = 10;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL_FEATURE_COUNT = 11;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CAMEL_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.impl.ActionImpl <em>Action</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.ActionImpl
	 * @see camel.impl.CamelPackageImpl#getAction()
	 * @generated
	 */
	int ACTION = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ACTION__NAME = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
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
	 * The meta object id for the '{@link camel.impl.PhysicalNodeImpl <em>Physical Node</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.PhysicalNodeImpl
	 * @see camel.impl.CamelPackageImpl#getPhysicalNode()
	 * @generated
	 */
	int PHYSICAL_NODE = 2;

	/**
	 * The feature id for the '<em><b>IP</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE__IP = CerifPackage.RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Hardware</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE__HARDWARE = CerifPackage.RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>In Data Center</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE__IN_DATA_CENTER = CerifPackage.RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Physical Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE_FEATURE_COUNT = CerifPackage.RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Physical Node</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PHYSICAL_NODE_OPERATION_COUNT = CerifPackage.RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.DataObjectImpl <em>Data Object</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.DataObjectImpl
	 * @see camel.impl.CamelPackageImpl#getDataObject()
	 * @generated
	 */
	int DATA_OBJECT = 3;

	/**
	 * The feature id for the '<em><b>Replication</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OBJECT__REPLICATION = CerifPackage.RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Partitioning</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OBJECT__PARTITIONING = CerifPackage.RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Consistency</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OBJECT__CONSISTENCY = CerifPackage.RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OBJECT__NAME = CerifPackage.RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Data Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OBJECT_FEATURE_COUNT = CerifPackage.RESOURCE_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Data Object</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_OBJECT_OPERATION_COUNT = CerifPackage.RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.RequirementImpl <em>Requirement</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.RequirementImpl
	 * @see camel.impl.CamelPackageImpl#getRequirement()
	 * @generated
	 */
	int REQUIREMENT = 5;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__PRIORITY = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT__ID = 1;

	/**
	 * The number of structural features of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Requirement</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.impl.RequirementGroupImpl <em>Requirement Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.RequirementGroupImpl
	 * @see camel.impl.CamelPackageImpl#getRequirementGroup()
	 * @generated
	 */
	int REQUIREMENT_GROUP = 4;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_GROUP__PRIORITY = REQUIREMENT__PRIORITY;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_GROUP__ID = REQUIREMENT__ID;

	/**
	 * The feature id for the '<em><b>Posed By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_GROUP__POSED_BY = REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Requirement</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_GROUP__REQUIREMENT = REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>On Application</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_GROUP__ON_APPLICATION = REQUIREMENT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Requirement Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_GROUP__REQUIREMENT_OPERATOR = REQUIREMENT_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Requirement Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_GROUP_FEATURE_COUNT = REQUIREMENT_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Requirement Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUIREMENT_GROUP_OPERATION_COUNT = REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.ScalabilityPolicyImpl <em>Scalability Policy</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.ScalabilityPolicyImpl
	 * @see camel.impl.CamelPackageImpl#getScalabilityPolicy()
	 * @generated
	 */
	int SCALABILITY_POLICY = 6;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY__PRIORITY = REQUIREMENT__PRIORITY;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY__ID = REQUIREMENT__ID;

	/**
	 * The feature id for the '<em><b>Min Instances</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY__MIN_INSTANCES = REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Max Instances</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY__MAX_INSTANCES = REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY__TYPE = REQUIREMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Scalability Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY_FEATURE_COUNT = REQUIREMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Scalability Policy</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCALABILITY_POLICY_OPERATION_COUNT = REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.VMInfoImpl <em>VM Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.VMInfoImpl
	 * @see camel.impl.CamelPackageImpl#getVMInfo()
	 * @generated
	 */
	int VM_INFO = 7;

	/**
	 * The feature id for the '<em><b>Of VM</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO__OF_VM = 0;

	/**
	 * The feature id for the '<em><b>Data Center</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO__DATA_CENTER = 1;

	/**
	 * The feature id for the '<em><b>Cost Per Hour</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO__COST_PER_HOUR = 2;

	/**
	 * The feature id for the '<em><b>Cost Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO__COST_UNIT = 3;

	/**
	 * The feature id for the '<em><b>Cloud Independent VM Info</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO__CLOUD_INDEPENDENT_VM_INFO = 4;

	/**
	 * The feature id for the '<em><b>Benchmark Rate</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO__BENCHMARK_RATE = 5;

	/**
	 * The feature id for the '<em><b>Evaluated On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO__EVALUATED_ON = 6;

	/**
	 * The feature id for the '<em><b>Classified On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO__CLASSIFIED_ON = 7;

	/**
	 * The number of structural features of the '<em>VM Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>VM Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.impl.CloudIndependentVMInfoImpl <em>Cloud Independent VM Info</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.CloudIndependentVMInfoImpl
	 * @see camel.impl.CamelPackageImpl#getCloudIndependentVMInfo()
	 * @generated
	 */
	int CLOUD_INDEPENDENT_VM_INFO = 8;

	/**
	 * The feature id for the '<em><b>Cpu Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_INDEPENDENT_VM_INFO__CPU_CLASS = 0;

	/**
	 * The feature id for the '<em><b>Memory Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_INDEPENDENT_VM_INFO__MEMORY_CLASS = 1;

	/**
	 * The feature id for the '<em><b>Io Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_INDEPENDENT_VM_INFO__IO_CLASS = 2;

	/**
	 * The feature id for the '<em><b>Network Class</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_INDEPENDENT_VM_INFO__NETWORK_CLASS = 3;

	/**
	 * The number of structural features of the '<em>Cloud Independent VM Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_INDEPENDENT_VM_INFO_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Cloud Independent VM Info</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_INDEPENDENT_VM_INFO_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.impl.VMTypeImpl <em>VM Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.VMTypeImpl
	 * @see camel.impl.CamelPackageImpl#getVMType()
	 * @generated
	 */
	int VM_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_TYPE__NAME = CerifPackage.RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>VM Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_TYPE_FEATURE_COUNT = CerifPackage.RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>VM Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VM_TYPE_OPERATION_COUNT = CerifPackage.RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.ApplicationImpl <em>Application</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.ApplicationImpl
	 * @see camel.impl.CamelPackageImpl#getApplication()
	 * @generated
	 */
	int APPLICATION = 10;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__NAME = CerifPackage.RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__VERSION = CerifPackage.RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Owner</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION__OWNER = CerifPackage.RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_FEATURE_COUNT = CerifPackage.RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Application</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int APPLICATION_OPERATION_COUNT = CerifPackage.RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.UnitImpl <em>Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.UnitImpl
	 * @see camel.impl.CamelPackageImpl#getUnit()
	 * @generated
	 */
	int UNIT = 11;

	/**
	 * The feature id for the '<em><b>Dimension Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT__DIMENSION_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT__UNIT = 1;

	/**
	 * The number of structural features of the '<em>Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_FEATURE_COUNT = 2;

	/**
	 * The operation id for the '<em>Check Unit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT___CHECK_UNIT = 0;

	/**
	 * The number of operations of the '<em>Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIT_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.impl.MonetaryUnitImpl <em>Monetary Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.MonetaryUnitImpl
	 * @see camel.impl.CamelPackageImpl#getMonetaryUnit()
	 * @generated
	 */
	int MONETARY_UNIT = 12;

	/**
	 * The feature id for the '<em><b>Dimension Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONETARY_UNIT__DIMENSION_TYPE = UNIT__DIMENSION_TYPE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONETARY_UNIT__UNIT = UNIT__UNIT;

	/**
	 * The number of structural features of the '<em>Monetary Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONETARY_UNIT_FEATURE_COUNT = UNIT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Check Unit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONETARY_UNIT___CHECK_UNIT = UNIT___CHECK_UNIT;

	/**
	 * The number of operations of the '<em>Monetary Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MONETARY_UNIT_OPERATION_COUNT = UNIT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.StorageUnitImpl <em>Storage Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.StorageUnitImpl
	 * @see camel.impl.CamelPackageImpl#getStorageUnit()
	 * @generated
	 */
	int STORAGE_UNIT = 13;

	/**
	 * The feature id for the '<em><b>Dimension Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_UNIT__DIMENSION_TYPE = UNIT__DIMENSION_TYPE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_UNIT__UNIT = UNIT__UNIT;

	/**
	 * The number of structural features of the '<em>Storage Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_UNIT_FEATURE_COUNT = UNIT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Check Unit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_UNIT___CHECK_UNIT = UNIT___CHECK_UNIT;

	/**
	 * The number of operations of the '<em>Storage Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STORAGE_UNIT_OPERATION_COUNT = UNIT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.TimeIntervalUnitImpl <em>Time Interval Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.TimeIntervalUnitImpl
	 * @see camel.impl.CamelPackageImpl#getTimeIntervalUnit()
	 * @generated
	 */
	int TIME_INTERVAL_UNIT = 14;

	/**
	 * The feature id for the '<em><b>Dimension Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_INTERVAL_UNIT__DIMENSION_TYPE = UNIT__DIMENSION_TYPE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_INTERVAL_UNIT__UNIT = UNIT__UNIT;

	/**
	 * The number of structural features of the '<em>Time Interval Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_INTERVAL_UNIT_FEATURE_COUNT = UNIT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Check Unit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_INTERVAL_UNIT___CHECK_UNIT = UNIT___CHECK_UNIT;

	/**
	 * The number of operations of the '<em>Time Interval Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_INTERVAL_UNIT_OPERATION_COUNT = UNIT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.ThroughputUnitImpl <em>Throughput Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.ThroughputUnitImpl
	 * @see camel.impl.CamelPackageImpl#getThroughputUnit()
	 * @generated
	 */
	int THROUGHPUT_UNIT = 15;

	/**
	 * The feature id for the '<em><b>Dimension Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROUGHPUT_UNIT__DIMENSION_TYPE = UNIT__DIMENSION_TYPE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROUGHPUT_UNIT__UNIT = UNIT__UNIT;

	/**
	 * The number of structural features of the '<em>Throughput Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROUGHPUT_UNIT_FEATURE_COUNT = UNIT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Check Unit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROUGHPUT_UNIT___CHECK_UNIT = UNIT___CHECK_UNIT;

	/**
	 * The number of operations of the '<em>Throughput Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int THROUGHPUT_UNIT_OPERATION_COUNT = UNIT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.RequestUnitImpl <em>Request Unit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.RequestUnitImpl
	 * @see camel.impl.CamelPackageImpl#getRequestUnit()
	 * @generated
	 */
	int REQUEST_UNIT = 16;

	/**
	 * The feature id for the '<em><b>Dimension Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_UNIT__DIMENSION_TYPE = UNIT__DIMENSION_TYPE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_UNIT__UNIT = UNIT__UNIT;

	/**
	 * The number of structural features of the '<em>Request Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_UNIT_FEATURE_COUNT = UNIT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Check Unit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_UNIT___CHECK_UNIT = UNIT___CHECK_UNIT;

	/**
	 * The number of operations of the '<em>Request Unit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int REQUEST_UNIT_OPERATION_COUNT = UNIT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.impl.UnitlessImpl <em>Unitless</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.impl.UnitlessImpl
	 * @see camel.impl.CamelPackageImpl#getUnitless()
	 * @generated
	 */
	int UNITLESS = 17;

	/**
	 * The feature id for the '<em><b>Dimension Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNITLESS__DIMENSION_TYPE = UNIT__DIMENSION_TYPE;

	/**
	 * The feature id for the '<em><b>Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNITLESS__UNIT = UNIT__UNIT;

	/**
	 * The number of structural features of the '<em>Unitless</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNITLESS_FEATURE_COUNT = UNIT_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Check Unit</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNITLESS___CHECK_UNIT = UNIT___CHECK_UNIT;

	/**
	 * The number of operations of the '<em>Unitless</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNITLESS_OPERATION_COUNT = UNIT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.ActionType <em>Action Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.ActionType
	 * @see camel.impl.CamelPackageImpl#getActionType()
	 * @generated
	 */
	int ACTION_TYPE = 18;

	/**
	 * The meta object id for the '{@link camel.RequirementOperatorType <em>Requirement Operator Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.RequirementOperatorType
	 * @see camel.impl.CamelPackageImpl#getRequirementOperatorType()
	 * @generated
	 */
	int REQUIREMENT_OPERATOR_TYPE = 19;

	/**
	 * The meta object id for the '{@link camel.ScalabilityType <em>Scalability Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.ScalabilityType
	 * @see camel.impl.CamelPackageImpl#getScalabilityType()
	 * @generated
	 */
	int SCALABILITY_TYPE = 20;

	/**
	 * The meta object id for the '{@link camel.UnitType <em>Unit Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.UnitType
	 * @see camel.impl.CamelPackageImpl#getUnitType()
	 * @generated
	 */
	int UNIT_TYPE = 21;

	/**
	 * The meta object id for the '{@link camel.UnitDimensionType <em>Unit Dimension Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.UnitDimensionType
	 * @see camel.impl.CamelPackageImpl#getUnitDimensionType()
	 * @generated
	 */
	int UNIT_DIMENSION_TYPE = 22;


	/**
	 * Returns the meta object for class '{@link camel.CamelModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see camel.CamelModel
	 * @generated
	 */
	EClass getCamelModel();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getCerifModels <em>Cerif Models</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Cerif Models</em>'.
	 * @see camel.CamelModel#getCerifModels()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_CerifModels();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getUnits <em>Units</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Units</em>'.
	 * @see camel.CamelModel#getUnits()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_Units();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Actions</em>'.
	 * @see camel.CamelModel#getActions()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_Actions();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getNodes <em>Nodes</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Nodes</em>'.
	 * @see camel.CamelModel#getNodes()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_Nodes();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getObjects <em>Objects</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Objects</em>'.
	 * @see camel.CamelModel#getObjects()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_Objects();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getRequirements <em>Requirements</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Requirements</em>'.
	 * @see camel.CamelModel#getRequirements()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_Requirements();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getPolicies <em>Policies</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Policies</em>'.
	 * @see camel.CamelModel#getPolicies()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_Policies();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getVmInfos <em>Vm Infos</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vm Infos</em>'.
	 * @see camel.CamelModel#getVmInfos()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_VmInfos();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getIndependentVmInfos <em>Independent Vm Infos</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Independent Vm Infos</em>'.
	 * @see camel.CamelModel#getIndependentVmInfos()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_IndependentVmInfos();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getVmType <em>Vm Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vm Type</em>'.
	 * @see camel.CamelModel#getVmType()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_VmType();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.CamelModel#getApplications <em>Applications</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Applications</em>'.
	 * @see camel.CamelModel#getApplications()
	 * @see #getCamelModel()
	 * @generated
	 */
	EReference getCamelModel_Applications();

	/**
	 * Returns the meta object for class '{@link camel.Action <em>Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Action</em>'.
	 * @see camel.Action
	 * @generated
	 */
	EClass getAction();

	/**
	 * Returns the meta object for the attribute '{@link camel.Action#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.Action#getName()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.Action#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see camel.Action#getType()
	 * @see #getAction()
	 * @generated
	 */
	EAttribute getAction_Type();

	/**
	 * Returns the meta object for class '{@link camel.PhysicalNode <em>Physical Node</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Physical Node</em>'.
	 * @see camel.PhysicalNode
	 * @generated
	 */
	EClass getPhysicalNode();

	/**
	 * Returns the meta object for the attribute '{@link camel.PhysicalNode#getIP <em>IP</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>IP</em>'.
	 * @see camel.PhysicalNode#getIP()
	 * @see #getPhysicalNode()
	 * @generated
	 */
	EAttribute getPhysicalNode_IP();

	/**
	 * Returns the meta object for the attribute '{@link camel.PhysicalNode#getHardware <em>Hardware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hardware</em>'.
	 * @see camel.PhysicalNode#getHardware()
	 * @see #getPhysicalNode()
	 * @generated
	 */
	EAttribute getPhysicalNode_Hardware();

	/**
	 * Returns the meta object for the reference '{@link camel.PhysicalNode#getInDataCenter <em>In Data Center</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>In Data Center</em>'.
	 * @see camel.PhysicalNode#getInDataCenter()
	 * @see #getPhysicalNode()
	 * @generated
	 */
	EReference getPhysicalNode_InDataCenter();

	/**
	 * Returns the meta object for class '{@link camel.DataObject <em>Data Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Object</em>'.
	 * @see camel.DataObject
	 * @generated
	 */
	EClass getDataObject();

	/**
	 * Returns the meta object for the attribute '{@link camel.DataObject#getReplication <em>Replication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Replication</em>'.
	 * @see camel.DataObject#getReplication()
	 * @see #getDataObject()
	 * @generated
	 */
	EAttribute getDataObject_Replication();

	/**
	 * Returns the meta object for the attribute '{@link camel.DataObject#getPartitioning <em>Partitioning</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Partitioning</em>'.
	 * @see camel.DataObject#getPartitioning()
	 * @see #getDataObject()
	 * @generated
	 */
	EAttribute getDataObject_Partitioning();

	/**
	 * Returns the meta object for the attribute '{@link camel.DataObject#getConsistency <em>Consistency</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Consistency</em>'.
	 * @see camel.DataObject#getConsistency()
	 * @see #getDataObject()
	 * @generated
	 */
	EAttribute getDataObject_Consistency();

	/**
	 * Returns the meta object for the attribute '{@link camel.DataObject#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.DataObject#getName()
	 * @see #getDataObject()
	 * @generated
	 */
	EAttribute getDataObject_Name();

	/**
	 * Returns the meta object for class '{@link camel.RequirementGroup <em>Requirement Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Requirement Group</em>'.
	 * @see camel.RequirementGroup
	 * @generated
	 */
	EClass getRequirementGroup();

	/**
	 * Returns the meta object for the reference '{@link camel.RequirementGroup#getPosedBy <em>Posed By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Posed By</em>'.
	 * @see camel.RequirementGroup#getPosedBy()
	 * @see #getRequirementGroup()
	 * @generated
	 */
	EReference getRequirementGroup_PosedBy();

	/**
	 * Returns the meta object for the reference list '{@link camel.RequirementGroup#getRequirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Requirement</em>'.
	 * @see camel.RequirementGroup#getRequirement()
	 * @see #getRequirementGroup()
	 * @generated
	 */
	EReference getRequirementGroup_Requirement();

	/**
	 * Returns the meta object for the reference list '{@link camel.RequirementGroup#getOnApplication <em>On Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>On Application</em>'.
	 * @see camel.RequirementGroup#getOnApplication()
	 * @see #getRequirementGroup()
	 * @generated
	 */
	EReference getRequirementGroup_OnApplication();

	/**
	 * Returns the meta object for the attribute '{@link camel.RequirementGroup#getRequirementOperator <em>Requirement Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Requirement Operator</em>'.
	 * @see camel.RequirementGroup#getRequirementOperator()
	 * @see #getRequirementGroup()
	 * @generated
	 */
	EAttribute getRequirementGroup_RequirementOperator();

	/**
	 * Returns the meta object for class '{@link camel.Requirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Requirement</em>'.
	 * @see camel.Requirement
	 * @generated
	 */
	EClass getRequirement();

	/**
	 * Returns the meta object for the attribute '{@link camel.Requirement#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see camel.Requirement#getPriority()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_Priority();

	/**
	 * Returns the meta object for the attribute '{@link camel.Requirement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see camel.Requirement#getId()
	 * @see #getRequirement()
	 * @generated
	 */
	EAttribute getRequirement_Id();

	/**
	 * Returns the meta object for class '{@link camel.ScalabilityPolicy <em>Scalability Policy</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scalability Policy</em>'.
	 * @see camel.ScalabilityPolicy
	 * @generated
	 */
	EClass getScalabilityPolicy();

	/**
	 * Returns the meta object for the attribute '{@link camel.ScalabilityPolicy#getMinInstances <em>Min Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min Instances</em>'.
	 * @see camel.ScalabilityPolicy#getMinInstances()
	 * @see #getScalabilityPolicy()
	 * @generated
	 */
	EAttribute getScalabilityPolicy_MinInstances();

	/**
	 * Returns the meta object for the attribute '{@link camel.ScalabilityPolicy#getMaxInstances <em>Max Instances</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max Instances</em>'.
	 * @see camel.ScalabilityPolicy#getMaxInstances()
	 * @see #getScalabilityPolicy()
	 * @generated
	 */
	EAttribute getScalabilityPolicy_MaxInstances();

	/**
	 * Returns the meta object for the attribute '{@link camel.ScalabilityPolicy#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see camel.ScalabilityPolicy#getType()
	 * @see #getScalabilityPolicy()
	 * @generated
	 */
	EAttribute getScalabilityPolicy_Type();

	/**
	 * Returns the meta object for class '{@link camel.VMInfo <em>VM Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VM Info</em>'.
	 * @see camel.VMInfo
	 * @generated
	 */
	EClass getVMInfo();

	/**
	 * Returns the meta object for the reference '{@link camel.VMInfo#getOfVM <em>Of VM</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of VM</em>'.
	 * @see camel.VMInfo#getOfVM()
	 * @see #getVMInfo()
	 * @generated
	 */
	EReference getVMInfo_OfVM();

	/**
	 * Returns the meta object for the reference '{@link camel.VMInfo#getDataCenter <em>Data Center</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Data Center</em>'.
	 * @see camel.VMInfo#getDataCenter()
	 * @see #getVMInfo()
	 * @generated
	 */
	EReference getVMInfo_DataCenter();

	/**
	 * Returns the meta object for the attribute '{@link camel.VMInfo#getCostPerHour <em>Cost Per Hour</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cost Per Hour</em>'.
	 * @see camel.VMInfo#getCostPerHour()
	 * @see #getVMInfo()
	 * @generated
	 */
	EAttribute getVMInfo_CostPerHour();

	/**
	 * Returns the meta object for the reference '{@link camel.VMInfo#getCostUnit <em>Cost Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Cost Unit</em>'.
	 * @see camel.VMInfo#getCostUnit()
	 * @see #getVMInfo()
	 * @generated
	 */
	EReference getVMInfo_CostUnit();

	/**
	 * Returns the meta object for the reference '{@link camel.VMInfo#getCloudIndependentVMInfo <em>Cloud Independent VM Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Cloud Independent VM Info</em>'.
	 * @see camel.VMInfo#getCloudIndependentVMInfo()
	 * @see #getVMInfo()
	 * @generated
	 */
	EReference getVMInfo_CloudIndependentVMInfo();

	/**
	 * Returns the meta object for the attribute '{@link camel.VMInfo#getBenchmarkRate <em>Benchmark Rate</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Benchmark Rate</em>'.
	 * @see camel.VMInfo#getBenchmarkRate()
	 * @see #getVMInfo()
	 * @generated
	 */
	EAttribute getVMInfo_BenchmarkRate();

	/**
	 * Returns the meta object for the attribute '{@link camel.VMInfo#getEvaluatedOn <em>Evaluated On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Evaluated On</em>'.
	 * @see camel.VMInfo#getEvaluatedOn()
	 * @see #getVMInfo()
	 * @generated
	 */
	EAttribute getVMInfo_EvaluatedOn();

	/**
	 * Returns the meta object for the attribute '{@link camel.VMInfo#getClassifiedOn <em>Classified On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Classified On</em>'.
	 * @see camel.VMInfo#getClassifiedOn()
	 * @see #getVMInfo()
	 * @generated
	 */
	EAttribute getVMInfo_ClassifiedOn();

	/**
	 * Returns the meta object for class '{@link camel.CloudIndependentVMInfo <em>Cloud Independent VM Info</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cloud Independent VM Info</em>'.
	 * @see camel.CloudIndependentVMInfo
	 * @generated
	 */
	EClass getCloudIndependentVMInfo();

	/**
	 * Returns the meta object for the attribute '{@link camel.CloudIndependentVMInfo#getCpuClass <em>Cpu Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Cpu Class</em>'.
	 * @see camel.CloudIndependentVMInfo#getCpuClass()
	 * @see #getCloudIndependentVMInfo()
	 * @generated
	 */
	EAttribute getCloudIndependentVMInfo_CpuClass();

	/**
	 * Returns the meta object for the attribute '{@link camel.CloudIndependentVMInfo#getMemoryClass <em>Memory Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Memory Class</em>'.
	 * @see camel.CloudIndependentVMInfo#getMemoryClass()
	 * @see #getCloudIndependentVMInfo()
	 * @generated
	 */
	EAttribute getCloudIndependentVMInfo_MemoryClass();

	/**
	 * Returns the meta object for the attribute '{@link camel.CloudIndependentVMInfo#getIoClass <em>Io Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Io Class</em>'.
	 * @see camel.CloudIndependentVMInfo#getIoClass()
	 * @see #getCloudIndependentVMInfo()
	 * @generated
	 */
	EAttribute getCloudIndependentVMInfo_IoClass();

	/**
	 * Returns the meta object for the attribute '{@link camel.CloudIndependentVMInfo#getNetworkClass <em>Network Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Network Class</em>'.
	 * @see camel.CloudIndependentVMInfo#getNetworkClass()
	 * @see #getCloudIndependentVMInfo()
	 * @generated
	 */
	EAttribute getCloudIndependentVMInfo_NetworkClass();

	/**
	 * Returns the meta object for class '{@link camel.VMType <em>VM Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>VM Type</em>'.
	 * @see camel.VMType
	 * @generated
	 */
	EClass getVMType();

	/**
	 * Returns the meta object for the attribute '{@link camel.VMType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.VMType#getName()
	 * @see #getVMType()
	 * @generated
	 */
	EAttribute getVMType_Name();

	/**
	 * Returns the meta object for class '{@link camel.Application <em>Application</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Application</em>'.
	 * @see camel.Application
	 * @generated
	 */
	EClass getApplication();

	/**
	 * Returns the meta object for the attribute '{@link camel.Application#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.Application#getName()
	 * @see #getApplication()
	 * @generated
	 */
	EAttribute getApplication_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.Application#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see camel.Application#getVersion()
	 * @see #getApplication()
	 * @generated
	 */
	EAttribute getApplication_Version();

	/**
	 * Returns the meta object for the reference '{@link camel.Application#getOwner <em>Owner</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Owner</em>'.
	 * @see camel.Application#getOwner()
	 * @see #getApplication()
	 * @generated
	 */
	EReference getApplication_Owner();

	/**
	 * Returns the meta object for class '{@link camel.Unit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unit</em>'.
	 * @see camel.Unit
	 * @generated
	 */
	EClass getUnit();

	/**
	 * Returns the meta object for the attribute '{@link camel.Unit#getDimensionType <em>Dimension Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Dimension Type</em>'.
	 * @see camel.Unit#getDimensionType()
	 * @see #getUnit()
	 * @generated
	 */
	EAttribute getUnit_DimensionType();

	/**
	 * Returns the meta object for the attribute '{@link camel.Unit#getUnit <em>Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Unit</em>'.
	 * @see camel.Unit#getUnit()
	 * @see #getUnit()
	 * @generated
	 */
	EAttribute getUnit_Unit();

	/**
	 * Returns the meta object for the '{@link camel.Unit#checkUnit() <em>Check Unit</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Unit</em>' operation.
	 * @see camel.Unit#checkUnit()
	 * @generated
	 */
	EOperation getUnit__CheckUnit();

	/**
	 * Returns the meta object for class '{@link camel.MonetaryUnit <em>Monetary Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Monetary Unit</em>'.
	 * @see camel.MonetaryUnit
	 * @generated
	 */
	EClass getMonetaryUnit();

	/**
	 * Returns the meta object for class '{@link camel.StorageUnit <em>Storage Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Storage Unit</em>'.
	 * @see camel.StorageUnit
	 * @generated
	 */
	EClass getStorageUnit();

	/**
	 * Returns the meta object for class '{@link camel.TimeIntervalUnit <em>Time Interval Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Interval Unit</em>'.
	 * @see camel.TimeIntervalUnit
	 * @generated
	 */
	EClass getTimeIntervalUnit();

	/**
	 * Returns the meta object for class '{@link camel.ThroughputUnit <em>Throughput Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Throughput Unit</em>'.
	 * @see camel.ThroughputUnit
	 * @generated
	 */
	EClass getThroughputUnit();

	/**
	 * Returns the meta object for class '{@link camel.RequestUnit <em>Request Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Request Unit</em>'.
	 * @see camel.RequestUnit
	 * @generated
	 */
	EClass getRequestUnit();

	/**
	 * Returns the meta object for class '{@link camel.Unitless <em>Unitless</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unitless</em>'.
	 * @see camel.Unitless
	 * @generated
	 */
	EClass getUnitless();

	/**
	 * Returns the meta object for enum '{@link camel.ActionType <em>Action Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Action Type</em>'.
	 * @see camel.ActionType
	 * @generated
	 */
	EEnum getActionType();

	/**
	 * Returns the meta object for enum '{@link camel.RequirementOperatorType <em>Requirement Operator Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Requirement Operator Type</em>'.
	 * @see camel.RequirementOperatorType
	 * @generated
	 */
	EEnum getRequirementOperatorType();

	/**
	 * Returns the meta object for enum '{@link camel.ScalabilityType <em>Scalability Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Scalability Type</em>'.
	 * @see camel.ScalabilityType
	 * @generated
	 */
	EEnum getScalabilityType();

	/**
	 * Returns the meta object for enum '{@link camel.UnitType <em>Unit Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Unit Type</em>'.
	 * @see camel.UnitType
	 * @generated
	 */
	EEnum getUnitType();

	/**
	 * Returns the meta object for enum '{@link camel.UnitDimensionType <em>Unit Dimension Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Unit Dimension Type</em>'.
	 * @see camel.UnitDimensionType
	 * @generated
	 */
	EEnum getUnitDimensionType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CamelFactory getCamelFactory();

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
		 * The meta object literal for the '{@link camel.impl.CamelModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.CamelModelImpl
		 * @see camel.impl.CamelPackageImpl#getCamelModel()
		 * @generated
		 */
		EClass CAMEL_MODEL = eINSTANCE.getCamelModel();

		/**
		 * The meta object literal for the '<em><b>Cerif Models</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__CERIF_MODELS = eINSTANCE.getCamelModel_CerifModels();

		/**
		 * The meta object literal for the '<em><b>Units</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__UNITS = eINSTANCE.getCamelModel_Units();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__ACTIONS = eINSTANCE.getCamelModel_Actions();

		/**
		 * The meta object literal for the '<em><b>Nodes</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__NODES = eINSTANCE.getCamelModel_Nodes();

		/**
		 * The meta object literal for the '<em><b>Objects</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__OBJECTS = eINSTANCE.getCamelModel_Objects();

		/**
		 * The meta object literal for the '<em><b>Requirements</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__REQUIREMENTS = eINSTANCE.getCamelModel_Requirements();

		/**
		 * The meta object literal for the '<em><b>Policies</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__POLICIES = eINSTANCE.getCamelModel_Policies();

		/**
		 * The meta object literal for the '<em><b>Vm Infos</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__VM_INFOS = eINSTANCE.getCamelModel_VmInfos();

		/**
		 * The meta object literal for the '<em><b>Independent Vm Infos</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__INDEPENDENT_VM_INFOS = eINSTANCE.getCamelModel_IndependentVmInfos();

		/**
		 * The meta object literal for the '<em><b>Vm Type</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__VM_TYPE = eINSTANCE.getCamelModel_VmType();

		/**
		 * The meta object literal for the '<em><b>Applications</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CAMEL_MODEL__APPLICATIONS = eINSTANCE.getCamelModel_Applications();

		/**
		 * The meta object literal for the '{@link camel.impl.ActionImpl <em>Action</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.ActionImpl
		 * @see camel.impl.CamelPackageImpl#getAction()
		 * @generated
		 */
		EClass ACTION = eINSTANCE.getAction();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__NAME = eINSTANCE.getAction_Name();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ACTION__TYPE = eINSTANCE.getAction_Type();

		/**
		 * The meta object literal for the '{@link camel.impl.PhysicalNodeImpl <em>Physical Node</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.PhysicalNodeImpl
		 * @see camel.impl.CamelPackageImpl#getPhysicalNode()
		 * @generated
		 */
		EClass PHYSICAL_NODE = eINSTANCE.getPhysicalNode();

		/**
		 * The meta object literal for the '<em><b>IP</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PHYSICAL_NODE__IP = eINSTANCE.getPhysicalNode_IP();

		/**
		 * The meta object literal for the '<em><b>Hardware</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PHYSICAL_NODE__HARDWARE = eINSTANCE.getPhysicalNode_Hardware();

		/**
		 * The meta object literal for the '<em><b>In Data Center</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PHYSICAL_NODE__IN_DATA_CENTER = eINSTANCE.getPhysicalNode_InDataCenter();

		/**
		 * The meta object literal for the '{@link camel.impl.DataObjectImpl <em>Data Object</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.DataObjectImpl
		 * @see camel.impl.CamelPackageImpl#getDataObject()
		 * @generated
		 */
		EClass DATA_OBJECT = eINSTANCE.getDataObject();

		/**
		 * The meta object literal for the '<em><b>Replication</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_OBJECT__REPLICATION = eINSTANCE.getDataObject_Replication();

		/**
		 * The meta object literal for the '<em><b>Partitioning</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_OBJECT__PARTITIONING = eINSTANCE.getDataObject_Partitioning();

		/**
		 * The meta object literal for the '<em><b>Consistency</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_OBJECT__CONSISTENCY = eINSTANCE.getDataObject_Consistency();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_OBJECT__NAME = eINSTANCE.getDataObject_Name();

		/**
		 * The meta object literal for the '{@link camel.impl.RequirementGroupImpl <em>Requirement Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.RequirementGroupImpl
		 * @see camel.impl.CamelPackageImpl#getRequirementGroup()
		 * @generated
		 */
		EClass REQUIREMENT_GROUP = eINSTANCE.getRequirementGroup();

		/**
		 * The meta object literal for the '<em><b>Posed By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIREMENT_GROUP__POSED_BY = eINSTANCE.getRequirementGroup_PosedBy();

		/**
		 * The meta object literal for the '<em><b>Requirement</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIREMENT_GROUP__REQUIREMENT = eINSTANCE.getRequirementGroup_Requirement();

		/**
		 * The meta object literal for the '<em><b>On Application</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference REQUIREMENT_GROUP__ON_APPLICATION = eINSTANCE.getRequirementGroup_OnApplication();

		/**
		 * The meta object literal for the '<em><b>Requirement Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT_GROUP__REQUIREMENT_OPERATOR = eINSTANCE.getRequirementGroup_RequirementOperator();

		/**
		 * The meta object literal for the '{@link camel.impl.RequirementImpl <em>Requirement</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.RequirementImpl
		 * @see camel.impl.CamelPackageImpl#getRequirement()
		 * @generated
		 */
		EClass REQUIREMENT = eINSTANCE.getRequirement();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__PRIORITY = eINSTANCE.getRequirement_Priority();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute REQUIREMENT__ID = eINSTANCE.getRequirement_Id();

		/**
		 * The meta object literal for the '{@link camel.impl.ScalabilityPolicyImpl <em>Scalability Policy</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.ScalabilityPolicyImpl
		 * @see camel.impl.CamelPackageImpl#getScalabilityPolicy()
		 * @generated
		 */
		EClass SCALABILITY_POLICY = eINSTANCE.getScalabilityPolicy();

		/**
		 * The meta object literal for the '<em><b>Min Instances</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALABILITY_POLICY__MIN_INSTANCES = eINSTANCE.getScalabilityPolicy_MinInstances();

		/**
		 * The meta object literal for the '<em><b>Max Instances</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALABILITY_POLICY__MAX_INSTANCES = eINSTANCE.getScalabilityPolicy_MaxInstances();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCALABILITY_POLICY__TYPE = eINSTANCE.getScalabilityPolicy_Type();

		/**
		 * The meta object literal for the '{@link camel.impl.VMInfoImpl <em>VM Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.VMInfoImpl
		 * @see camel.impl.CamelPackageImpl#getVMInfo()
		 * @generated
		 */
		EClass VM_INFO = eINSTANCE.getVMInfo();

		/**
		 * The meta object literal for the '<em><b>Of VM</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VM_INFO__OF_VM = eINSTANCE.getVMInfo_OfVM();

		/**
		 * The meta object literal for the '<em><b>Data Center</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VM_INFO__DATA_CENTER = eINSTANCE.getVMInfo_DataCenter();

		/**
		 * The meta object literal for the '<em><b>Cost Per Hour</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM_INFO__COST_PER_HOUR = eINSTANCE.getVMInfo_CostPerHour();

		/**
		 * The meta object literal for the '<em><b>Cost Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VM_INFO__COST_UNIT = eINSTANCE.getVMInfo_CostUnit();

		/**
		 * The meta object literal for the '<em><b>Cloud Independent VM Info</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VM_INFO__CLOUD_INDEPENDENT_VM_INFO = eINSTANCE.getVMInfo_CloudIndependentVMInfo();

		/**
		 * The meta object literal for the '<em><b>Benchmark Rate</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM_INFO__BENCHMARK_RATE = eINSTANCE.getVMInfo_BenchmarkRate();

		/**
		 * The meta object literal for the '<em><b>Evaluated On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM_INFO__EVALUATED_ON = eINSTANCE.getVMInfo_EvaluatedOn();

		/**
		 * The meta object literal for the '<em><b>Classified On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM_INFO__CLASSIFIED_ON = eINSTANCE.getVMInfo_ClassifiedOn();

		/**
		 * The meta object literal for the '{@link camel.impl.CloudIndependentVMInfoImpl <em>Cloud Independent VM Info</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.CloudIndependentVMInfoImpl
		 * @see camel.impl.CamelPackageImpl#getCloudIndependentVMInfo()
		 * @generated
		 */
		EClass CLOUD_INDEPENDENT_VM_INFO = eINSTANCE.getCloudIndependentVMInfo();

		/**
		 * The meta object literal for the '<em><b>Cpu Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_INDEPENDENT_VM_INFO__CPU_CLASS = eINSTANCE.getCloudIndependentVMInfo_CpuClass();

		/**
		 * The meta object literal for the '<em><b>Memory Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_INDEPENDENT_VM_INFO__MEMORY_CLASS = eINSTANCE.getCloudIndependentVMInfo_MemoryClass();

		/**
		 * The meta object literal for the '<em><b>Io Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_INDEPENDENT_VM_INFO__IO_CLASS = eINSTANCE.getCloudIndependentVMInfo_IoClass();

		/**
		 * The meta object literal for the '<em><b>Network Class</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_INDEPENDENT_VM_INFO__NETWORK_CLASS = eINSTANCE.getCloudIndependentVMInfo_NetworkClass();

		/**
		 * The meta object literal for the '{@link camel.impl.VMTypeImpl <em>VM Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.VMTypeImpl
		 * @see camel.impl.CamelPackageImpl#getVMType()
		 * @generated
		 */
		EClass VM_TYPE = eINSTANCE.getVMType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VM_TYPE__NAME = eINSTANCE.getVMType_Name();

		/**
		 * The meta object literal for the '{@link camel.impl.ApplicationImpl <em>Application</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.ApplicationImpl
		 * @see camel.impl.CamelPackageImpl#getApplication()
		 * @generated
		 */
		EClass APPLICATION = eINSTANCE.getApplication();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLICATION__NAME = eINSTANCE.getApplication_Name();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute APPLICATION__VERSION = eINSTANCE.getApplication_Version();

		/**
		 * The meta object literal for the '<em><b>Owner</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference APPLICATION__OWNER = eINSTANCE.getApplication_Owner();

		/**
		 * The meta object literal for the '{@link camel.impl.UnitImpl <em>Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.UnitImpl
		 * @see camel.impl.CamelPackageImpl#getUnit()
		 * @generated
		 */
		EClass UNIT = eINSTANCE.getUnit();

		/**
		 * The meta object literal for the '<em><b>Dimension Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT__DIMENSION_TYPE = eINSTANCE.getUnit_DimensionType();

		/**
		 * The meta object literal for the '<em><b>Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIT__UNIT = eINSTANCE.getUnit_Unit();

		/**
		 * The meta object literal for the '<em><b>Check Unit</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation UNIT___CHECK_UNIT = eINSTANCE.getUnit__CheckUnit();

		/**
		 * The meta object literal for the '{@link camel.impl.MonetaryUnitImpl <em>Monetary Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.MonetaryUnitImpl
		 * @see camel.impl.CamelPackageImpl#getMonetaryUnit()
		 * @generated
		 */
		EClass MONETARY_UNIT = eINSTANCE.getMonetaryUnit();

		/**
		 * The meta object literal for the '{@link camel.impl.StorageUnitImpl <em>Storage Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.StorageUnitImpl
		 * @see camel.impl.CamelPackageImpl#getStorageUnit()
		 * @generated
		 */
		EClass STORAGE_UNIT = eINSTANCE.getStorageUnit();

		/**
		 * The meta object literal for the '{@link camel.impl.TimeIntervalUnitImpl <em>Time Interval Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.TimeIntervalUnitImpl
		 * @see camel.impl.CamelPackageImpl#getTimeIntervalUnit()
		 * @generated
		 */
		EClass TIME_INTERVAL_UNIT = eINSTANCE.getTimeIntervalUnit();

		/**
		 * The meta object literal for the '{@link camel.impl.ThroughputUnitImpl <em>Throughput Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.ThroughputUnitImpl
		 * @see camel.impl.CamelPackageImpl#getThroughputUnit()
		 * @generated
		 */
		EClass THROUGHPUT_UNIT = eINSTANCE.getThroughputUnit();

		/**
		 * The meta object literal for the '{@link camel.impl.RequestUnitImpl <em>Request Unit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.RequestUnitImpl
		 * @see camel.impl.CamelPackageImpl#getRequestUnit()
		 * @generated
		 */
		EClass REQUEST_UNIT = eINSTANCE.getRequestUnit();

		/**
		 * The meta object literal for the '{@link camel.impl.UnitlessImpl <em>Unitless</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.impl.UnitlessImpl
		 * @see camel.impl.CamelPackageImpl#getUnitless()
		 * @generated
		 */
		EClass UNITLESS = eINSTANCE.getUnitless();

		/**
		 * The meta object literal for the '{@link camel.ActionType <em>Action Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.ActionType
		 * @see camel.impl.CamelPackageImpl#getActionType()
		 * @generated
		 */
		EEnum ACTION_TYPE = eINSTANCE.getActionType();

		/**
		 * The meta object literal for the '{@link camel.RequirementOperatorType <em>Requirement Operator Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.RequirementOperatorType
		 * @see camel.impl.CamelPackageImpl#getRequirementOperatorType()
		 * @generated
		 */
		EEnum REQUIREMENT_OPERATOR_TYPE = eINSTANCE.getRequirementOperatorType();

		/**
		 * The meta object literal for the '{@link camel.ScalabilityType <em>Scalability Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.ScalabilityType
		 * @see camel.impl.CamelPackageImpl#getScalabilityType()
		 * @generated
		 */
		EEnum SCALABILITY_TYPE = eINSTANCE.getScalabilityType();

		/**
		 * The meta object literal for the '{@link camel.UnitType <em>Unit Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.UnitType
		 * @see camel.impl.CamelPackageImpl#getUnitType()
		 * @generated
		 */
		EEnum UNIT_TYPE = eINSTANCE.getUnitType();

		/**
		 * The meta object literal for the '{@link camel.UnitDimensionType <em>Unit Dimension Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.UnitDimensionType
		 * @see camel.impl.CamelPackageImpl#getUnitDimensionType()
		 * @generated
		 */
		EEnum UNIT_DIMENSION_TYPE = eINSTANCE.getUnitDimensionType();

	}

} //CamelPackage
