/**
 */
package camel.cerif;

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
 * @see camel.cerif.CerifFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface CerifPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "cerif";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/camel/cerif";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "cerif";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CerifPackage eINSTANCE = camel.cerif.impl.CerifPackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.cerif.impl.CerifModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.CerifModelImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getCerifModel()
	 * @generated
	 */
	int CERIF_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Users</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__USERS = 0;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__PROVIDER = 1;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__ORGANIZATION = 2;

	/**
	 * The feature id for the '<em><b>User Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__USER_GROUPS = 3;

	/**
	 * The feature id for the '<em><b>Data Centres</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__DATA_CENTRES = 4;

	/**
	 * The feature id for the '<em><b>Role Assigments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__ROLE_ASSIGMENTS = 5;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__ROLES = 6;

	/**
	 * The feature id for the '<em><b>Permissions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__PERMISSIONS = 7;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__RESOURCES = 8;

	/**
	 * The feature id for the '<em><b>Locations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__LOCATIONS = 9;

	/**
	 * The feature id for the '<em><b>External Identifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__EXTERNAL_IDENTIFIERS = 10;

	/**
	 * The feature id for the '<em><b>Allowed Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL__ALLOWED_ACTIONS = 11;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL_FEATURE_COUNT = 12;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CERIF_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.EntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.EntityImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 14;

	/**
	 * The number of structural features of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Entity</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENTITY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.UserImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getUser()
	 * @generated
	 */
	int USER = 1;

	/**
	 * The feature id for the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__FIRST_NAME = ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__LAST_NAME = ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__EMAIL = ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Www</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__WWW = ENTITY_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__LOGIN = ENTITY_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Works For</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__WORKS_FOR = ENTITY_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Has External Identifier</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__HAS_EXTERNAL_IDENTIFIER = ENTITY_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Has Requirement</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__HAS_REQUIREMENT = ENTITY_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Has Credentials</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__HAS_CREDENTIALS = ENTITY_FEATURE_COUNT + 8;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 9;

	/**
	 * The number of operations of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_OPERATION_COUNT = ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.OrganizationImpl <em>Organization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.OrganizationImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getOrganization()
	 * @generated
	 */
	int ORGANIZATION = 2;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION__NAME = ENTITY_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Www</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION__WWW = ENTITY_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Postal Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION__POSTAL_ADDRESS = ENTITY_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION__EMAIL = ENTITY_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Organization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Organization</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANIZATION_OPERATION_COUNT = ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.CloudProviderImpl <em>Cloud Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.CloudProviderImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getCloudProvider()
	 * @generated
	 */
	int CLOUD_PROVIDER = 3;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__NAME = ORGANIZATION__NAME;

	/**
	 * The feature id for the '<em><b>Www</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__WWW = ORGANIZATION__WWW;

	/**
	 * The feature id for the '<em><b>Postal Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__POSTAL_ADDRESS = ORGANIZATION__POSTAL_ADDRESS;

	/**
	 * The feature id for the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__EMAIL = ORGANIZATION__EMAIL;

	/**
	 * The feature id for the '<em><b>Public</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__PUBLIC = ORGANIZATION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Saa S</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__SAA_S = ORGANIZATION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Paa S</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__PAA_S = ORGANIZATION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Iaa S</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__IAA_S = ORGANIZATION_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Cloud Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER_FEATURE_COUNT = ORGANIZATION_FEATURE_COUNT + 4;

	/**
	 * The number of operations of the '<em>Cloud Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER_OPERATION_COUNT = ORGANIZATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.RoleImpl <em>Role</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.RoleImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getRole()
	 * @generated
	 */
	int ROLE = 4;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Role</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.UserGroupImpl <em>User Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.UserGroupImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getUserGroup()
	 * @generated
	 */
	int USER_GROUP = 5;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_GROUP__NAME = 0;

	/**
	 * The feature id for the '<em><b>Member</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_GROUP__MEMBER = 1;

	/**
	 * The number of structural features of the '<em>User Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_GROUP_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>User Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_GROUP_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.RoleAssignmentImpl <em>Role Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.RoleAssignmentImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getRoleAssignment()
	 * @generated
	 */
	int ROLE_ASSIGNMENT = 6;

	/**
	 * The feature id for the '<em><b>Of User</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT__OF_USER = 0;

	/**
	 * The feature id for the '<em><b>To Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT__TO_ROLE = 1;

	/**
	 * The feature id for the '<em><b>Of Group</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT__OF_GROUP = 2;

	/**
	 * The feature id for the '<em><b>Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT__START = 3;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT__END = 4;

	/**
	 * The feature id for the '<em><b>Assigned On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT__ASSIGNED_ON = 5;

	/**
	 * The feature id for the '<em><b>Assigned By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT__ASSIGNED_BY = 6;

	/**
	 * The number of structural features of the '<em>Role Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Role Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.PermissionImpl <em>Permission</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.PermissionImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getPermission()
	 * @generated
	 */
	int PERMISSION = 7;

	/**
	 * The feature id for the '<em><b>For Role</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION__FOR_ROLE = 0;

	/**
	 * The feature id for the '<em><b>Start</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION__START = 1;

	/**
	 * The feature id for the '<em><b>End</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION__END = 2;

	/**
	 * The feature id for the '<em><b>Issued By</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION__ISSUED_BY = 3;

	/**
	 * The feature id for the '<em><b>On Resource</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION__ON_RESOURCE = 4;

	/**
	 * The feature id for the '<em><b>To Action</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION__TO_ACTION = 5;

	/**
	 * The number of structural features of the '<em>Permission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Permission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.ResourceImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 8;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.ResourceGroupImpl <em>Resource Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.ResourceGroupImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getResourceGroup()
	 * @generated
	 */
	int RESOURCE_GROUP = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP__NAME = RESOURCE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Contains Resource</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP__CONTAINS_RESOURCE = RESOURCE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Level</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP__LEVEL = RESOURCE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Resource Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP_FEATURE_COUNT = RESOURCE_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Resource Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP_OPERATION_COUNT = RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.AllowedActionsImpl <em>Allowed Actions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.AllowedActionsImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getAllowedActions()
	 * @generated
	 */
	int ALLOWED_ACTIONS = 10;

	/**
	 * The feature id for the '<em><b>Resource Class</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALLOWED_ACTIONS__RESOURCE_CLASS = 0;

	/**
	 * The feature id for the '<em><b>Actions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALLOWED_ACTIONS__ACTIONS = 1;

	/**
	 * The number of structural features of the '<em>Allowed Actions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALLOWED_ACTIONS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Allowed Actions</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ALLOWED_ACTIONS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.ExternalIdentifierImpl <em>External Identifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.ExternalIdentifierImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getExternalIdentifier()
	 * @generated
	 */
	int EXTERNAL_IDENTIFIER = 11;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_IDENTIFIER__IDENTIFIER = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_IDENTIFIER__NAME = 1;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_IDENTIFIER__DESCRIPTION = 2;

	/**
	 * The number of structural features of the '<em>External Identifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_IDENTIFIER_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>External Identifier</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXTERNAL_IDENTIFIER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.DataCenterImpl <em>Data Center</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.DataCenterImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getDataCenter()
	 * @generated
	 */
	int DATA_CENTER = 12;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CENTER__NAME = 0;

	/**
	 * The feature id for the '<em><b>Code Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CENTER__CODE_NAME = 1;

	/**
	 * The feature id for the '<em><b>Has Location</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CENTER__HAS_LOCATION = 2;

	/**
	 * The feature id for the '<em><b>Of Cloud Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CENTER__OF_CLOUD_PROVIDER = 3;

	/**
	 * The number of structural features of the '<em>Data Center</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CENTER_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Data Center</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATA_CENTER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.LocationImpl <em>Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.LocationImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 13;

	/**
	 * The feature id for the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__CITY = 0;

	/**
	 * The feature id for the '<em><b>Country</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__COUNTRY = 1;

	/**
	 * The feature id for the '<em><b>Country Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__COUNTRY_CODE = 2;

	/**
	 * The feature id for the '<em><b>Latitude</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__LATITUDE = 3;

	/**
	 * The feature id for the '<em><b>Longitude</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__LONGITUDE = 4;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__ADDRESS = 5;

	/**
	 * The number of structural features of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_FEATURE_COUNT = 6;

	/**
	 * The number of operations of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.cerif.impl.CredentialsImpl <em>Credentials</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.cerif.impl.CredentialsImpl
	 * @see camel.cerif.impl.CerifPackageImpl#getCredentials()
	 * @generated
	 */
	int CREDENTIALS = 15;

	/**
	 * The feature id for the '<em><b>Cloud Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDENTIALS__CLOUD_PROVIDER = 0;

	/**
	 * The number of structural features of the '<em>Credentials</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDENTIALS_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Credentials</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDENTIALS_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link camel.cerif.CerifModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see camel.cerif.CerifModel
	 * @generated
	 */
	EClass getCerifModel();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getUsers <em>Users</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Users</em>'.
	 * @see camel.cerif.CerifModel#getUsers()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_Users();

	/**
	 * Returns the meta object for the containment reference '{@link camel.cerif.CerifModel#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Provider</em>'.
	 * @see camel.cerif.CerifModel#getProvider()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_Provider();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getOrganization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Organization</em>'.
	 * @see camel.cerif.CerifModel#getOrganization()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_Organization();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getUserGroups <em>User Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>User Groups</em>'.
	 * @see camel.cerif.CerifModel#getUserGroups()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_UserGroups();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getDataCentres <em>Data Centres</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Centres</em>'.
	 * @see camel.cerif.CerifModel#getDataCentres()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_DataCentres();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getRoleAssigments <em>Role Assigments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Role Assigments</em>'.
	 * @see camel.cerif.CerifModel#getRoleAssigments()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_RoleAssigments();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getRoles <em>Roles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Roles</em>'.
	 * @see camel.cerif.CerifModel#getRoles()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_Roles();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getPermissions <em>Permissions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Permissions</em>'.
	 * @see camel.cerif.CerifModel#getPermissions()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_Permissions();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getResources <em>Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resources</em>'.
	 * @see camel.cerif.CerifModel#getResources()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_Resources();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getLocations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Locations</em>'.
	 * @see camel.cerif.CerifModel#getLocations()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_Locations();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getExternalIdentifiers <em>External Identifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>External Identifiers</em>'.
	 * @see camel.cerif.CerifModel#getExternalIdentifiers()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_ExternalIdentifiers();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.cerif.CerifModel#getAllowedActions <em>Allowed Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Allowed Actions</em>'.
	 * @see camel.cerif.CerifModel#getAllowedActions()
	 * @see #getCerifModel()
	 * @generated
	 */
	EReference getCerifModel_AllowedActions();

	/**
	 * Returns the meta object for class '{@link camel.cerif.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see camel.cerif.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.User#getFirstName <em>First Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>First Name</em>'.
	 * @see camel.cerif.User#getFirstName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_FirstName();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.User#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see camel.cerif.User#getLastName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_LastName();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.User#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see camel.cerif.User#getEmail()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Email();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.User#getWww <em>Www</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Www</em>'.
	 * @see camel.cerif.User#getWww()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Www();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.User#getLogin <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Login</em>'.
	 * @see camel.cerif.User#getLogin()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Login();

	/**
	 * Returns the meta object for the reference list '{@link camel.cerif.User#getWorksFor <em>Works For</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Works For</em>'.
	 * @see camel.cerif.User#getWorksFor()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_WorksFor();

	/**
	 * Returns the meta object for the reference list '{@link camel.cerif.User#getHasExternalIdentifier <em>Has External Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Has External Identifier</em>'.
	 * @see camel.cerif.User#getHasExternalIdentifier()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_HasExternalIdentifier();

	/**
	 * Returns the meta object for the reference list '{@link camel.cerif.User#getHasRequirement <em>Has Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Has Requirement</em>'.
	 * @see camel.cerif.User#getHasRequirement()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_HasRequirement();

	/**
	 * Returns the meta object for the reference list '{@link camel.cerif.User#getHasCredentials <em>Has Credentials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Has Credentials</em>'.
	 * @see camel.cerif.User#getHasCredentials()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_HasCredentials();

	/**
	 * Returns the meta object for class '{@link camel.cerif.Organization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Organization</em>'.
	 * @see camel.cerif.Organization
	 * @generated
	 */
	EClass getOrganization();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Organization#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.cerif.Organization#getName()
	 * @see #getOrganization()
	 * @generated
	 */
	EAttribute getOrganization_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Organization#getWww <em>Www</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Www</em>'.
	 * @see camel.cerif.Organization#getWww()
	 * @see #getOrganization()
	 * @generated
	 */
	EAttribute getOrganization_Www();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Organization#getPostalAddress <em>Postal Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Postal Address</em>'.
	 * @see camel.cerif.Organization#getPostalAddress()
	 * @see #getOrganization()
	 * @generated
	 */
	EAttribute getOrganization_PostalAddress();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Organization#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see camel.cerif.Organization#getEmail()
	 * @see #getOrganization()
	 * @generated
	 */
	EAttribute getOrganization_Email();

	/**
	 * Returns the meta object for class '{@link camel.cerif.CloudProvider <em>Cloud Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cloud Provider</em>'.
	 * @see camel.cerif.CloudProvider
	 * @generated
	 */
	EClass getCloudProvider();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.CloudProvider#isPublic <em>Public</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Public</em>'.
	 * @see camel.cerif.CloudProvider#isPublic()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EAttribute getCloudProvider_Public();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.CloudProvider#isSaaS <em>Saa S</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Saa S</em>'.
	 * @see camel.cerif.CloudProvider#isSaaS()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EAttribute getCloudProvider_SaaS();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.CloudProvider#isPaaS <em>Paa S</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Paa S</em>'.
	 * @see camel.cerif.CloudProvider#isPaaS()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EAttribute getCloudProvider_PaaS();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.CloudProvider#isIaaS <em>Iaa S</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Iaa S</em>'.
	 * @see camel.cerif.CloudProvider#isIaaS()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EAttribute getCloudProvider_IaaS();

	/**
	 * Returns the meta object for class '{@link camel.cerif.Role <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role</em>'.
	 * @see camel.cerif.Role
	 * @generated
	 */
	EClass getRole();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Role#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.cerif.Role#getName()
	 * @see #getRole()
	 * @generated
	 */
	EAttribute getRole_Name();

	/**
	 * Returns the meta object for class '{@link camel.cerif.UserGroup <em>User Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Group</em>'.
	 * @see camel.cerif.UserGroup
	 * @generated
	 */
	EClass getUserGroup();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.UserGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.cerif.UserGroup#getName()
	 * @see #getUserGroup()
	 * @generated
	 */
	EAttribute getUserGroup_Name();

	/**
	 * Returns the meta object for the reference list '{@link camel.cerif.UserGroup#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Member</em>'.
	 * @see camel.cerif.UserGroup#getMember()
	 * @see #getUserGroup()
	 * @generated
	 */
	EReference getUserGroup_Member();

	/**
	 * Returns the meta object for class '{@link camel.cerif.RoleAssignment <em>Role Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Assignment</em>'.
	 * @see camel.cerif.RoleAssignment
	 * @generated
	 */
	EClass getRoleAssignment();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.RoleAssignment#getOfUser <em>Of User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of User</em>'.
	 * @see camel.cerif.RoleAssignment#getOfUser()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EReference getRoleAssignment_OfUser();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.RoleAssignment#getToRole <em>To Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To Role</em>'.
	 * @see camel.cerif.RoleAssignment#getToRole()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EReference getRoleAssignment_ToRole();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.RoleAssignment#getOfGroup <em>Of Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of Group</em>'.
	 * @see camel.cerif.RoleAssignment#getOfGroup()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EReference getRoleAssignment_OfGroup();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.RoleAssignment#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start</em>'.
	 * @see camel.cerif.RoleAssignment#getStart()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EAttribute getRoleAssignment_Start();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.RoleAssignment#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End</em>'.
	 * @see camel.cerif.RoleAssignment#getEnd()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EAttribute getRoleAssignment_End();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.RoleAssignment#getAssignedOn <em>Assigned On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assigned On</em>'.
	 * @see camel.cerif.RoleAssignment#getAssignedOn()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EAttribute getRoleAssignment_AssignedOn();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.RoleAssignment#getAssignedBy <em>Assigned By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Assigned By</em>'.
	 * @see camel.cerif.RoleAssignment#getAssignedBy()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EReference getRoleAssignment_AssignedBy();

	/**
	 * Returns the meta object for class '{@link camel.cerif.Permission <em>Permission</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Permission</em>'.
	 * @see camel.cerif.Permission
	 * @generated
	 */
	EClass getPermission();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.Permission#getForRole <em>For Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>For Role</em>'.
	 * @see camel.cerif.Permission#getForRole()
	 * @see #getPermission()
	 * @generated
	 */
	EReference getPermission_ForRole();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Permission#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start</em>'.
	 * @see camel.cerif.Permission#getStart()
	 * @see #getPermission()
	 * @generated
	 */
	EAttribute getPermission_Start();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Permission#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End</em>'.
	 * @see camel.cerif.Permission#getEnd()
	 * @see #getPermission()
	 * @generated
	 */
	EAttribute getPermission_End();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.Permission#getIssuedBy <em>Issued By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Issued By</em>'.
	 * @see camel.cerif.Permission#getIssuedBy()
	 * @see #getPermission()
	 * @generated
	 */
	EReference getPermission_IssuedBy();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.Permission#getOnResource <em>On Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>On Resource</em>'.
	 * @see camel.cerif.Permission#getOnResource()
	 * @see #getPermission()
	 * @generated
	 */
	EReference getPermission_OnResource();

	/**
	 * Returns the meta object for the reference list '{@link camel.cerif.Permission#getToAction <em>To Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>To Action</em>'.
	 * @see camel.cerif.Permission#getToAction()
	 * @see #getPermission()
	 * @generated
	 */
	EReference getPermission_ToAction();

	/**
	 * Returns the meta object for class '{@link camel.cerif.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see camel.cerif.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for class '{@link camel.cerif.ResourceGroup <em>Resource Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Group</em>'.
	 * @see camel.cerif.ResourceGroup
	 * @generated
	 */
	EClass getResourceGroup();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.ResourceGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.cerif.ResourceGroup#getName()
	 * @see #getResourceGroup()
	 * @generated
	 */
	EAttribute getResourceGroup_Name();

	/**
	 * Returns the meta object for the reference list '{@link camel.cerif.ResourceGroup#getContainsResource <em>Contains Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Contains Resource</em>'.
	 * @see camel.cerif.ResourceGroup#getContainsResource()
	 * @see #getResourceGroup()
	 * @generated
	 */
	EReference getResourceGroup_ContainsResource();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.ResourceGroup#getLevel <em>Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Level</em>'.
	 * @see camel.cerif.ResourceGroup#getLevel()
	 * @see #getResourceGroup()
	 * @generated
	 */
	EAttribute getResourceGroup_Level();

	/**
	 * Returns the meta object for class '{@link camel.cerif.AllowedActions <em>Allowed Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Allowed Actions</em>'.
	 * @see camel.cerif.AllowedActions
	 * @generated
	 */
	EClass getAllowedActions();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.AllowedActions#getResourceClass <em>Resource Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Resource Class</em>'.
	 * @see camel.cerif.AllowedActions#getResourceClass()
	 * @see #getAllowedActions()
	 * @generated
	 */
	EReference getAllowedActions_ResourceClass();

	/**
	 * Returns the meta object for the reference list '{@link camel.cerif.AllowedActions#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Actions</em>'.
	 * @see camel.cerif.AllowedActions#getActions()
	 * @see #getAllowedActions()
	 * @generated
	 */
	EReference getAllowedActions_Actions();

	/**
	 * Returns the meta object for class '{@link camel.cerif.ExternalIdentifier <em>External Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Identifier</em>'.
	 * @see camel.cerif.ExternalIdentifier
	 * @generated
	 */
	EClass getExternalIdentifier();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.ExternalIdentifier#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see camel.cerif.ExternalIdentifier#getIdentifier()
	 * @see #getExternalIdentifier()
	 * @generated
	 */
	EAttribute getExternalIdentifier_Identifier();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.ExternalIdentifier#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.cerif.ExternalIdentifier#getName()
	 * @see #getExternalIdentifier()
	 * @generated
	 */
	EAttribute getExternalIdentifier_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.ExternalIdentifier#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see camel.cerif.ExternalIdentifier#getDescription()
	 * @see #getExternalIdentifier()
	 * @generated
	 */
	EAttribute getExternalIdentifier_Description();

	/**
	 * Returns the meta object for class '{@link camel.cerif.DataCenter <em>Data Center</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Center</em>'.
	 * @see camel.cerif.DataCenter
	 * @generated
	 */
	EClass getDataCenter();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.DataCenter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.cerif.DataCenter#getName()
	 * @see #getDataCenter()
	 * @generated
	 */
	EAttribute getDataCenter_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.DataCenter#getCodeName <em>Code Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Code Name</em>'.
	 * @see camel.cerif.DataCenter#getCodeName()
	 * @see #getDataCenter()
	 * @generated
	 */
	EAttribute getDataCenter_CodeName();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.DataCenter#getHasLocation <em>Has Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Has Location</em>'.
	 * @see camel.cerif.DataCenter#getHasLocation()
	 * @see #getDataCenter()
	 * @generated
	 */
	EReference getDataCenter_HasLocation();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.DataCenter#getOfCloudProvider <em>Of Cloud Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of Cloud Provider</em>'.
	 * @see camel.cerif.DataCenter#getOfCloudProvider()
	 * @see #getDataCenter()
	 * @generated
	 */
	EReference getDataCenter_OfCloudProvider();

	/**
	 * Returns the meta object for class '{@link camel.cerif.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location</em>'.
	 * @see camel.cerif.Location
	 * @generated
	 */
	EClass getLocation();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Location#getCity <em>City</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>City</em>'.
	 * @see camel.cerif.Location#getCity()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_City();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Location#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Country</em>'.
	 * @see camel.cerif.Location#getCountry()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Country();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Location#getCountryCode <em>Country Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Country Code</em>'.
	 * @see camel.cerif.Location#getCountryCode()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_CountryCode();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Location#getLatitude <em>Latitude</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Latitude</em>'.
	 * @see camel.cerif.Location#getLatitude()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Latitude();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Location#getLongitude <em>Longitude</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Longitude</em>'.
	 * @see camel.cerif.Location#getLongitude()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Longitude();

	/**
	 * Returns the meta object for the attribute '{@link camel.cerif.Location#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Address</em>'.
	 * @see camel.cerif.Location#getAddress()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Address();

	/**
	 * Returns the meta object for class '{@link camel.cerif.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see camel.cerif.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for class '{@link camel.cerif.Credentials <em>Credentials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Credentials</em>'.
	 * @see camel.cerif.Credentials
	 * @generated
	 */
	EClass getCredentials();

	/**
	 * Returns the meta object for the reference '{@link camel.cerif.Credentials#getCloudProvider <em>Cloud Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Cloud Provider</em>'.
	 * @see camel.cerif.Credentials#getCloudProvider()
	 * @see #getCredentials()
	 * @generated
	 */
	EReference getCredentials_CloudProvider();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	CerifFactory getCerifFactory();

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
		 * The meta object literal for the '{@link camel.cerif.impl.CerifModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.CerifModelImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getCerifModel()
		 * @generated
		 */
		EClass CERIF_MODEL = eINSTANCE.getCerifModel();

		/**
		 * The meta object literal for the '<em><b>Users</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__USERS = eINSTANCE.getCerifModel_Users();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__PROVIDER = eINSTANCE.getCerifModel_Provider();

		/**
		 * The meta object literal for the '<em><b>Organization</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__ORGANIZATION = eINSTANCE.getCerifModel_Organization();

		/**
		 * The meta object literal for the '<em><b>User Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__USER_GROUPS = eINSTANCE.getCerifModel_UserGroups();

		/**
		 * The meta object literal for the '<em><b>Data Centres</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__DATA_CENTRES = eINSTANCE.getCerifModel_DataCentres();

		/**
		 * The meta object literal for the '<em><b>Role Assigments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__ROLE_ASSIGMENTS = eINSTANCE.getCerifModel_RoleAssigments();

		/**
		 * The meta object literal for the '<em><b>Roles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__ROLES = eINSTANCE.getCerifModel_Roles();

		/**
		 * The meta object literal for the '<em><b>Permissions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__PERMISSIONS = eINSTANCE.getCerifModel_Permissions();

		/**
		 * The meta object literal for the '<em><b>Resources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__RESOURCES = eINSTANCE.getCerifModel_Resources();

		/**
		 * The meta object literal for the '<em><b>Locations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__LOCATIONS = eINSTANCE.getCerifModel_Locations();

		/**
		 * The meta object literal for the '<em><b>External Identifiers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__EXTERNAL_IDENTIFIERS = eINSTANCE.getCerifModel_ExternalIdentifiers();

		/**
		 * The meta object literal for the '<em><b>Allowed Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CERIF_MODEL__ALLOWED_ACTIONS = eINSTANCE.getCerifModel_AllowedActions();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.UserImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getUser()
		 * @generated
		 */
		EClass USER = eINSTANCE.getUser();

		/**
		 * The meta object literal for the '<em><b>First Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__FIRST_NAME = eINSTANCE.getUser_FirstName();

		/**
		 * The meta object literal for the '<em><b>Last Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__LAST_NAME = eINSTANCE.getUser_LastName();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__EMAIL = eINSTANCE.getUser_Email();

		/**
		 * The meta object literal for the '<em><b>Www</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__WWW = eINSTANCE.getUser_Www();

		/**
		 * The meta object literal for the '<em><b>Login</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER__LOGIN = eINSTANCE.getUser_Login();

		/**
		 * The meta object literal for the '<em><b>Works For</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__WORKS_FOR = eINSTANCE.getUser_WorksFor();

		/**
		 * The meta object literal for the '<em><b>Has External Identifier</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__HAS_EXTERNAL_IDENTIFIER = eINSTANCE.getUser_HasExternalIdentifier();

		/**
		 * The meta object literal for the '<em><b>Has Requirement</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__HAS_REQUIREMENT = eINSTANCE.getUser_HasRequirement();

		/**
		 * The meta object literal for the '<em><b>Has Credentials</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__HAS_CREDENTIALS = eINSTANCE.getUser_HasCredentials();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.OrganizationImpl <em>Organization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.OrganizationImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getOrganization()
		 * @generated
		 */
		EClass ORGANIZATION = eINSTANCE.getOrganization();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORGANIZATION__NAME = eINSTANCE.getOrganization_Name();

		/**
		 * The meta object literal for the '<em><b>Www</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORGANIZATION__WWW = eINSTANCE.getOrganization_Www();

		/**
		 * The meta object literal for the '<em><b>Postal Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORGANIZATION__POSTAL_ADDRESS = eINSTANCE.getOrganization_PostalAddress();

		/**
		 * The meta object literal for the '<em><b>Email</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ORGANIZATION__EMAIL = eINSTANCE.getOrganization_Email();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.CloudProviderImpl <em>Cloud Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.CloudProviderImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getCloudProvider()
		 * @generated
		 */
		EClass CLOUD_PROVIDER = eINSTANCE.getCloudProvider();

		/**
		 * The meta object literal for the '<em><b>Public</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_PROVIDER__PUBLIC = eINSTANCE.getCloudProvider_Public();

		/**
		 * The meta object literal for the '<em><b>Saa S</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_PROVIDER__SAA_S = eINSTANCE.getCloudProvider_SaaS();

		/**
		 * The meta object literal for the '<em><b>Paa S</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_PROVIDER__PAA_S = eINSTANCE.getCloudProvider_PaaS();

		/**
		 * The meta object literal for the '<em><b>Iaa S</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CLOUD_PROVIDER__IAA_S = eINSTANCE.getCloudProvider_IaaS();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.RoleImpl <em>Role</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.RoleImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getRole()
		 * @generated
		 */
		EClass ROLE = eINSTANCE.getRole();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE__NAME = eINSTANCE.getRole_Name();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.UserGroupImpl <em>User Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.UserGroupImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getUserGroup()
		 * @generated
		 */
		EClass USER_GROUP = eINSTANCE.getUserGroup();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_GROUP__NAME = eINSTANCE.getUserGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Member</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER_GROUP__MEMBER = eINSTANCE.getUserGroup_Member();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.RoleAssignmentImpl <em>Role Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.RoleAssignmentImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getRoleAssignment()
		 * @generated
		 */
		EClass ROLE_ASSIGNMENT = eINSTANCE.getRoleAssignment();

		/**
		 * The meta object literal for the '<em><b>Of User</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_ASSIGNMENT__OF_USER = eINSTANCE.getRoleAssignment_OfUser();

		/**
		 * The meta object literal for the '<em><b>To Role</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_ASSIGNMENT__TO_ROLE = eINSTANCE.getRoleAssignment_ToRole();

		/**
		 * The meta object literal for the '<em><b>Of Group</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_ASSIGNMENT__OF_GROUP = eINSTANCE.getRoleAssignment_OfGroup();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_ASSIGNMENT__START = eINSTANCE.getRoleAssignment_Start();

		/**
		 * The meta object literal for the '<em><b>End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_ASSIGNMENT__END = eINSTANCE.getRoleAssignment_End();

		/**
		 * The meta object literal for the '<em><b>Assigned On</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ROLE_ASSIGNMENT__ASSIGNED_ON = eINSTANCE.getRoleAssignment_AssignedOn();

		/**
		 * The meta object literal for the '<em><b>Assigned By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ROLE_ASSIGNMENT__ASSIGNED_BY = eINSTANCE.getRoleAssignment_AssignedBy();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.PermissionImpl <em>Permission</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.PermissionImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getPermission()
		 * @generated
		 */
		EClass PERMISSION = eINSTANCE.getPermission();

		/**
		 * The meta object literal for the '<em><b>For Role</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERMISSION__FOR_ROLE = eINSTANCE.getPermission_ForRole();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERMISSION__START = eINSTANCE.getPermission_Start();

		/**
		 * The meta object literal for the '<em><b>End</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PERMISSION__END = eINSTANCE.getPermission_End();

		/**
		 * The meta object literal for the '<em><b>Issued By</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERMISSION__ISSUED_BY = eINSTANCE.getPermission_IssuedBy();

		/**
		 * The meta object literal for the '<em><b>On Resource</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERMISSION__ON_RESOURCE = eINSTANCE.getPermission_OnResource();

		/**
		 * The meta object literal for the '<em><b>To Action</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PERMISSION__TO_ACTION = eINSTANCE.getPermission_ToAction();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.ResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.ResourceImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getResource()
		 * @generated
		 */
		EClass RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.ResourceGroupImpl <em>Resource Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.ResourceGroupImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getResourceGroup()
		 * @generated
		 */
		EClass RESOURCE_GROUP = eINSTANCE.getResourceGroup();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_GROUP__NAME = eINSTANCE.getResourceGroup_Name();

		/**
		 * The meta object literal for the '<em><b>Contains Resource</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_GROUP__CONTAINS_RESOURCE = eINSTANCE.getResourceGroup_ContainsResource();

		/**
		 * The meta object literal for the '<em><b>Level</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RESOURCE_GROUP__LEVEL = eINSTANCE.getResourceGroup_Level();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.AllowedActionsImpl <em>Allowed Actions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.AllowedActionsImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getAllowedActions()
		 * @generated
		 */
		EClass ALLOWED_ACTIONS = eINSTANCE.getAllowedActions();

		/**
		 * The meta object literal for the '<em><b>Resource Class</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALLOWED_ACTIONS__RESOURCE_CLASS = eINSTANCE.getAllowedActions_ResourceClass();

		/**
		 * The meta object literal for the '<em><b>Actions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ALLOWED_ACTIONS__ACTIONS = eINSTANCE.getAllowedActions_Actions();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.ExternalIdentifierImpl <em>External Identifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.ExternalIdentifierImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getExternalIdentifier()
		 * @generated
		 */
		EClass EXTERNAL_IDENTIFIER = eINSTANCE.getExternalIdentifier();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_IDENTIFIER__IDENTIFIER = eINSTANCE.getExternalIdentifier_Identifier();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_IDENTIFIER__NAME = eINSTANCE.getExternalIdentifier_Name();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXTERNAL_IDENTIFIER__DESCRIPTION = eINSTANCE.getExternalIdentifier_Description();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.DataCenterImpl <em>Data Center</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.DataCenterImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getDataCenter()
		 * @generated
		 */
		EClass DATA_CENTER = eINSTANCE.getDataCenter();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_CENTER__NAME = eINSTANCE.getDataCenter_Name();

		/**
		 * The meta object literal for the '<em><b>Code Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATA_CENTER__CODE_NAME = eINSTANCE.getDataCenter_CodeName();

		/**
		 * The meta object literal for the '<em><b>Has Location</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_CENTER__HAS_LOCATION = eINSTANCE.getDataCenter_HasLocation();

		/**
		 * The meta object literal for the '<em><b>Of Cloud Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DATA_CENTER__OF_CLOUD_PROVIDER = eINSTANCE.getDataCenter_OfCloudProvider();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.LocationImpl <em>Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.LocationImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getLocation()
		 * @generated
		 */
		EClass LOCATION = eINSTANCE.getLocation();

		/**
		 * The meta object literal for the '<em><b>City</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__CITY = eINSTANCE.getLocation_City();

		/**
		 * The meta object literal for the '<em><b>Country</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__COUNTRY = eINSTANCE.getLocation_Country();

		/**
		 * The meta object literal for the '<em><b>Country Code</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__COUNTRY_CODE = eINSTANCE.getLocation_CountryCode();

		/**
		 * The meta object literal for the '<em><b>Latitude</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__LATITUDE = eINSTANCE.getLocation_Latitude();

		/**
		 * The meta object literal for the '<em><b>Longitude</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__LONGITUDE = eINSTANCE.getLocation_Longitude();

		/**
		 * The meta object literal for the '<em><b>Address</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__ADDRESS = eINSTANCE.getLocation_Address();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.EntityImpl <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.EntityImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getEntity()
		 * @generated
		 */
		EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '{@link camel.cerif.impl.CredentialsImpl <em>Credentials</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.cerif.impl.CredentialsImpl
		 * @see camel.cerif.impl.CerifPackageImpl#getCredentials()
		 * @generated
		 */
		EClass CREDENTIALS = eINSTANCE.getCredentials();

		/**
		 * The meta object literal for the '<em><b>Cloud Provider</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CREDENTIALS__CLOUD_PROVIDER = eINSTANCE.getCredentials_CloudProvider();

	}

} //CerifPackage
