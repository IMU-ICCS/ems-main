/**
 */
package camel.organisation;

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
 * @see camel.organisation.OrganisationFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface OrganisationPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "organisation";

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
	String eNS_PREFIX = "organisation";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	OrganisationPackage eINSTANCE = camel.organisation.impl.OrganisationPackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.organisation.impl.OrganisationModelImpl <em>Model</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.OrganisationModelImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getOrganisationModel()
	 * @generated
	 */
	int ORGANISATION_MODEL = 0;

	/**
	 * The feature id for the '<em><b>Users</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__USERS = 0;

	/**
	 * The feature id for the '<em><b>Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__PROVIDER = 1;

	/**
	 * The feature id for the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__ORGANIZATION = 2;

	/**
	 * The feature id for the '<em><b>User Groups</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__USER_GROUPS = 3;

	/**
	 * The feature id for the '<em><b>Data Centres</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__DATA_CENTRES = 4;

	/**
	 * The feature id for the '<em><b>Role Assigments</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__ROLE_ASSIGMENTS = 5;

	/**
	 * The feature id for the '<em><b>Roles</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__ROLES = 6;

	/**
	 * The feature id for the '<em><b>Permissions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__PERMISSIONS = 7;

	/**
	 * The feature id for the '<em><b>Resources</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__RESOURCES = 8;

	/**
	 * The feature id for the '<em><b>Locations</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__LOCATIONS = 9;

	/**
	 * The feature id for the '<em><b>External Identifiers</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__EXTERNAL_IDENTIFIERS = 10;

	/**
	 * The feature id for the '<em><b>Allowed Actions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL__ALLOWED_ACTIONS = 11;

	/**
	 * The number of structural features of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL_FEATURE_COUNT = 12;

	/**
	 * The number of operations of the '<em>Model</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ORGANISATION_MODEL_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.organisation.impl.AllowedActionsImpl <em>Allowed Actions</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.AllowedActionsImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getAllowedActions()
	 * @generated
	 */
	int ALLOWED_ACTIONS = 1;

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
	 * The meta object id for the '{@link camel.organisation.impl.CredentialsImpl <em>Credentials</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.CredentialsImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getCredentials()
	 * @generated
	 */
	int CREDENTIALS = 2;

	/**
	 * The feature id for the '<em><b>Cloud Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDENTIALS__CLOUD_PROVIDER = 0;

	/**
	 * The feature id for the '<em><b>Security Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDENTIALS__SECURITY_GROUP = 1;

	/**
	 * The feature id for the '<em><b>Public SSH Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDENTIALS__PUBLIC_SSH_KEY = 2;

	/**
	 * The feature id for the '<em><b>Private SSH Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDENTIALS__PRIVATE_SSH_KEY = 3;

	/**
	 * The number of structural features of the '<em>Credentials</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDENTIALS_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Credentials</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CREDENTIALS_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.organisation.impl.DataCenterImpl <em>Data Center</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.DataCenterImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getDataCenter()
	 * @generated
	 */
	int DATA_CENTER = 3;

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
	 * The meta object id for the '{@link camel.organisation.impl.EntityImpl <em>Entity</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.EntityImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getEntity()
	 * @generated
	 */
	int ENTITY = 4;

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
	 * The meta object id for the '{@link camel.organisation.impl.OrganizationImpl <em>Organization</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.OrganizationImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getOrganization()
	 * @generated
	 */
	int ORGANIZATION = 5;

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
	 * The meta object id for the '{@link camel.organisation.impl.CloudProviderImpl <em>Cloud Provider</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.CloudProviderImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getCloudProvider()
	 * @generated
	 */
	int CLOUD_PROVIDER = 6;

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
	 * The feature id for the '<em><b>Offers Sec Control</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__OFFERS_SEC_CONTROL = ORGANIZATION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Provider Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER__PROVIDER_MODEL = ORGANIZATION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Cloud Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER_FEATURE_COUNT = ORGANIZATION_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Cloud Provider</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CLOUD_PROVIDER_OPERATION_COUNT = ORGANIZATION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.organisation.impl.UserImpl <em>User</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.UserImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getUser()
	 * @generated
	 */
	int USER = 7;

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
	 * The feature id for the '<em><b>Has External Identifier</b></em>' containment reference list.
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
	 * The feature id for the '<em><b>Has Credentials</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__HAS_CREDENTIALS = ENTITY_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Deployment Model</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER__DEPLOYMENT_MODEL = ENTITY_FEATURE_COUNT + 9;

	/**
	 * The number of structural features of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_FEATURE_COUNT = ENTITY_FEATURE_COUNT + 10;

	/**
	 * The number of operations of the '<em>User</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_OPERATION_COUNT = ENTITY_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.organisation.impl.ExternalIdentifierImpl <em>External Identifier</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.ExternalIdentifierImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getExternalIdentifier()
	 * @generated
	 */
	int EXTERNAL_IDENTIFIER = 8;

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
	 * The meta object id for the '{@link camel.organisation.impl.LocationImpl <em>Location</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.LocationImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getLocation()
	 * @generated
	 */
	int LOCATION = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__NAME = 0;

	/**
	 * The feature id for the '<em><b>City</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__CITY = 1;

	/**
	 * The feature id for the '<em><b>Country</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__COUNTRY = 2;

	/**
	 * The feature id for the '<em><b>Country Code</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__COUNTRY_CODE = 3;

	/**
	 * The feature id for the '<em><b>Latitude</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__LATITUDE = 4;

	/**
	 * The feature id for the '<em><b>Longitude</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__LONGITUDE = 5;

	/**
	 * The feature id for the '<em><b>Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION__ADDRESS = 6;

	/**
	 * The number of structural features of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Location</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOCATION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.organisation.impl.PermissionImpl <em>Permission</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.PermissionImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getPermission()
	 * @generated
	 */
	int PERMISSION = 10;

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
	 * The operation id for the '<em>Check Start End Dates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION___CHECK_START_END_DATES__PERMISSION = 0;

	/**
	 * The number of operations of the '<em>Permission</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PERMISSION_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.organisation.impl.ResourceImpl <em>Resource</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.ResourceImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getResource()
	 * @generated
	 */
	int RESOURCE = 11;

	/**
	 * The number of structural features of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Of Class</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE___OF_CLASS__ECLASS = 0;

	/**
	 * The operation id for the '<em>Allows Actions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE___ALLOWS_ACTIONS__ELIST = 1;

	/**
	 * The number of operations of the '<em>Resource</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link camel.organisation.impl.ResourceGroupImpl <em>Resource Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.ResourceGroupImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getResourceGroup()
	 * @generated
	 */
	int RESOURCE_GROUP = 12;

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
	 * The operation id for the '<em>Of Class</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP___OF_CLASS__ECLASS = RESOURCE___OF_CLASS__ECLASS;

	/**
	 * The operation id for the '<em>Allows Actions</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP___ALLOWS_ACTIONS__ELIST = RESOURCE___ALLOWS_ACTIONS__ELIST;

	/**
	 * The operation id for the '<em>Allows Actions On Resources</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP___ALLOWS_ACTIONS_ON_RESOURCES__ELIST = RESOURCE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Check Recursiveness</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP___CHECK_RECURSIVENESS__RESOURCEGROUP_RESOURCE = RESOURCE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Resource Group</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_GROUP_OPERATION_COUNT = RESOURCE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link camel.organisation.impl.RoleImpl <em>Role</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.RoleImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getRole()
	 * @generated
	 */
	int ROLE = 13;

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
	 * The meta object id for the '{@link camel.organisation.impl.RoleAssignmentImpl <em>Role Assignment</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.RoleAssignmentImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getRoleAssignment()
	 * @generated
	 */
	int ROLE_ASSIGNMENT = 14;

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
	 * The operation id for the '<em>Check Assigned On Dates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT___CHECK_ASSIGNED_ON_DATES__ROLEASSIGNMENT = 0;

	/**
	 * The operation id for the '<em>Check Start End Dates</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT___CHECK_START_END_DATES__ROLEASSIGNMENT = 1;

	/**
	 * The number of operations of the '<em>Role Assignment</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ROLE_ASSIGNMENT_OPERATION_COUNT = 2;

	/**
	 * The meta object id for the '{@link camel.organisation.impl.UserGroupImpl <em>User Group</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.organisation.impl.UserGroupImpl
	 * @see camel.organisation.impl.OrganisationPackageImpl#getUserGroup()
	 * @generated
	 */
	int USER_GROUP = 15;

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
	 * Returns the meta object for class '{@link camel.organisation.OrganisationModel <em>Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Model</em>'.
	 * @see camel.organisation.OrganisationModel
	 * @generated
	 */
	EClass getOrganisationModel();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getUsers <em>Users</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Users</em>'.
	 * @see camel.organisation.OrganisationModel#getUsers()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_Users();

	/**
	 * Returns the meta object for the containment reference '{@link camel.organisation.OrganisationModel#getProvider <em>Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Provider</em>'.
	 * @see camel.organisation.OrganisationModel#getProvider()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_Provider();

	/**
	 * Returns the meta object for the containment reference '{@link camel.organisation.OrganisationModel#getOrganization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Organization</em>'.
	 * @see camel.organisation.OrganisationModel#getOrganization()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_Organization();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getUserGroups <em>User Groups</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>User Groups</em>'.
	 * @see camel.organisation.OrganisationModel#getUserGroups()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_UserGroups();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getDataCentres <em>Data Centres</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Centres</em>'.
	 * @see camel.organisation.OrganisationModel#getDataCentres()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_DataCentres();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getRoleAssigments <em>Role Assigments</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Role Assigments</em>'.
	 * @see camel.organisation.OrganisationModel#getRoleAssigments()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_RoleAssigments();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getRoles <em>Roles</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Roles</em>'.
	 * @see camel.organisation.OrganisationModel#getRoles()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_Roles();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getPermissions <em>Permissions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Permissions</em>'.
	 * @see camel.organisation.OrganisationModel#getPermissions()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_Permissions();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getResources <em>Resources</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Resources</em>'.
	 * @see camel.organisation.OrganisationModel#getResources()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_Resources();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getLocations <em>Locations</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Locations</em>'.
	 * @see camel.organisation.OrganisationModel#getLocations()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_Locations();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getExternalIdentifiers <em>External Identifiers</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>External Identifiers</em>'.
	 * @see camel.organisation.OrganisationModel#getExternalIdentifiers()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_ExternalIdentifiers();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.OrganisationModel#getAllowedActions <em>Allowed Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Allowed Actions</em>'.
	 * @see camel.organisation.OrganisationModel#getAllowedActions()
	 * @see #getOrganisationModel()
	 * @generated
	 */
	EReference getOrganisationModel_AllowedActions();

	/**
	 * Returns the meta object for class '{@link camel.organisation.AllowedActions <em>Allowed Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Allowed Actions</em>'.
	 * @see camel.organisation.AllowedActions
	 * @generated
	 */
	EClass getAllowedActions();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.AllowedActions#getResourceClass <em>Resource Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Resource Class</em>'.
	 * @see camel.organisation.AllowedActions#getResourceClass()
	 * @see #getAllowedActions()
	 * @generated
	 */
	EReference getAllowedActions_ResourceClass();

	/**
	 * Returns the meta object for the reference list '{@link camel.organisation.AllowedActions#getActions <em>Actions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Actions</em>'.
	 * @see camel.organisation.AllowedActions#getActions()
	 * @see #getAllowedActions()
	 * @generated
	 */
	EReference getAllowedActions_Actions();

	/**
	 * Returns the meta object for class '{@link camel.organisation.Credentials <em>Credentials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Credentials</em>'.
	 * @see camel.organisation.Credentials
	 * @generated
	 */
	EClass getCredentials();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.Credentials#getCloudProvider <em>Cloud Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Cloud Provider</em>'.
	 * @see camel.organisation.Credentials#getCloudProvider()
	 * @see #getCredentials()
	 * @generated
	 */
	EReference getCredentials_CloudProvider();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Credentials#getSecurityGroup <em>Security Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Security Group</em>'.
	 * @see camel.organisation.Credentials#getSecurityGroup()
	 * @see #getCredentials()
	 * @generated
	 */
	EAttribute getCredentials_SecurityGroup();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Credentials#getPublicSSHKey <em>Public SSH Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Public SSH Key</em>'.
	 * @see camel.organisation.Credentials#getPublicSSHKey()
	 * @see #getCredentials()
	 * @generated
	 */
	EAttribute getCredentials_PublicSSHKey();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Credentials#getPrivateSSHKey <em>Private SSH Key</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Private SSH Key</em>'.
	 * @see camel.organisation.Credentials#getPrivateSSHKey()
	 * @see #getCredentials()
	 * @generated
	 */
	EAttribute getCredentials_PrivateSSHKey();

	/**
	 * Returns the meta object for class '{@link camel.organisation.DataCenter <em>Data Center</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Data Center</em>'.
	 * @see camel.organisation.DataCenter
	 * @generated
	 */
	EClass getDataCenter();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.DataCenter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.organisation.DataCenter#getName()
	 * @see #getDataCenter()
	 * @generated
	 */
	EAttribute getDataCenter_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.DataCenter#getCodeName <em>Code Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Code Name</em>'.
	 * @see camel.organisation.DataCenter#getCodeName()
	 * @see #getDataCenter()
	 * @generated
	 */
	EAttribute getDataCenter_CodeName();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.DataCenter#getHasLocation <em>Has Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Has Location</em>'.
	 * @see camel.organisation.DataCenter#getHasLocation()
	 * @see #getDataCenter()
	 * @generated
	 */
	EReference getDataCenter_HasLocation();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.DataCenter#getOfCloudProvider <em>Of Cloud Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of Cloud Provider</em>'.
	 * @see camel.organisation.DataCenter#getOfCloudProvider()
	 * @see #getDataCenter()
	 * @generated
	 */
	EReference getDataCenter_OfCloudProvider();

	/**
	 * Returns the meta object for class '{@link camel.organisation.Entity <em>Entity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Entity</em>'.
	 * @see camel.organisation.Entity
	 * @generated
	 */
	EClass getEntity();

	/**
	 * Returns the meta object for class '{@link camel.organisation.Organization <em>Organization</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Organization</em>'.
	 * @see camel.organisation.Organization
	 * @generated
	 */
	EClass getOrganization();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Organization#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.organisation.Organization#getName()
	 * @see #getOrganization()
	 * @generated
	 */
	EAttribute getOrganization_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Organization#getWww <em>Www</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Www</em>'.
	 * @see camel.organisation.Organization#getWww()
	 * @see #getOrganization()
	 * @generated
	 */
	EAttribute getOrganization_Www();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Organization#getPostalAddress <em>Postal Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Postal Address</em>'.
	 * @see camel.organisation.Organization#getPostalAddress()
	 * @see #getOrganization()
	 * @generated
	 */
	EAttribute getOrganization_PostalAddress();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Organization#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see camel.organisation.Organization#getEmail()
	 * @see #getOrganization()
	 * @generated
	 */
	EAttribute getOrganization_Email();

	/**
	 * Returns the meta object for class '{@link camel.organisation.CloudProvider <em>Cloud Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cloud Provider</em>'.
	 * @see camel.organisation.CloudProvider
	 * @generated
	 */
	EClass getCloudProvider();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.CloudProvider#isPublic <em>Public</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Public</em>'.
	 * @see camel.organisation.CloudProvider#isPublic()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EAttribute getCloudProvider_Public();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.CloudProvider#isSaaS <em>Saa S</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Saa S</em>'.
	 * @see camel.organisation.CloudProvider#isSaaS()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EAttribute getCloudProvider_SaaS();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.CloudProvider#isPaaS <em>Paa S</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Paa S</em>'.
	 * @see camel.organisation.CloudProvider#isPaaS()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EAttribute getCloudProvider_PaaS();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.CloudProvider#isIaaS <em>Iaa S</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Iaa S</em>'.
	 * @see camel.organisation.CloudProvider#isIaaS()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EAttribute getCloudProvider_IaaS();

	/**
	 * Returns the meta object for the reference list '{@link camel.organisation.CloudProvider#getOffersSecControl <em>Offers Sec Control</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Offers Sec Control</em>'.
	 * @see camel.organisation.CloudProvider#getOffersSecControl()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EReference getCloudProvider_OffersSecControl();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.CloudProvider#getProviderModel <em>Provider Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Provider Model</em>'.
	 * @see camel.organisation.CloudProvider#getProviderModel()
	 * @see #getCloudProvider()
	 * @generated
	 */
	EReference getCloudProvider_ProviderModel();

	/**
	 * Returns the meta object for class '{@link camel.organisation.User <em>User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User</em>'.
	 * @see camel.organisation.User
	 * @generated
	 */
	EClass getUser();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.User#getFirstName <em>First Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>First Name</em>'.
	 * @see camel.organisation.User#getFirstName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_FirstName();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.User#getLastName <em>Last Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Last Name</em>'.
	 * @see camel.organisation.User#getLastName()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_LastName();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.User#getEmail <em>Email</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Email</em>'.
	 * @see camel.organisation.User#getEmail()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Email();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.User#getWww <em>Www</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Www</em>'.
	 * @see camel.organisation.User#getWww()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Www();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.User#getLogin <em>Login</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Login</em>'.
	 * @see camel.organisation.User#getLogin()
	 * @see #getUser()
	 * @generated
	 */
	EAttribute getUser_Login();

	/**
	 * Returns the meta object for the reference list '{@link camel.organisation.User#getWorksFor <em>Works For</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Works For</em>'.
	 * @see camel.organisation.User#getWorksFor()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_WorksFor();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.User#getHasExternalIdentifier <em>Has External Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Has External Identifier</em>'.
	 * @see camel.organisation.User#getHasExternalIdentifier()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_HasExternalIdentifier();

	/**
	 * Returns the meta object for the reference list '{@link camel.organisation.User#getHasRequirement <em>Has Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Has Requirement</em>'.
	 * @see camel.organisation.User#getHasRequirement()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_HasRequirement();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.organisation.User#getHasCredentials <em>Has Credentials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Has Credentials</em>'.
	 * @see camel.organisation.User#getHasCredentials()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_HasCredentials();

	/**
	 * Returns the meta object for the reference list '{@link camel.organisation.User#getDeploymentModel <em>Deployment Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Deployment Model</em>'.
	 * @see camel.organisation.User#getDeploymentModel()
	 * @see #getUser()
	 * @generated
	 */
	EReference getUser_DeploymentModel();

	/**
	 * Returns the meta object for class '{@link camel.organisation.ExternalIdentifier <em>External Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>External Identifier</em>'.
	 * @see camel.organisation.ExternalIdentifier
	 * @generated
	 */
	EClass getExternalIdentifier();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.ExternalIdentifier#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see camel.organisation.ExternalIdentifier#getIdentifier()
	 * @see #getExternalIdentifier()
	 * @generated
	 */
	EAttribute getExternalIdentifier_Identifier();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.ExternalIdentifier#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.organisation.ExternalIdentifier#getName()
	 * @see #getExternalIdentifier()
	 * @generated
	 */
	EAttribute getExternalIdentifier_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.ExternalIdentifier#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see camel.organisation.ExternalIdentifier#getDescription()
	 * @see #getExternalIdentifier()
	 * @generated
	 */
	EAttribute getExternalIdentifier_Description();

	/**
	 * Returns the meta object for class '{@link camel.organisation.Location <em>Location</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Location</em>'.
	 * @see camel.organisation.Location
	 * @generated
	 */
	EClass getLocation();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Location#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.organisation.Location#getName()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Location#getCity <em>City</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>City</em>'.
	 * @see camel.organisation.Location#getCity()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_City();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Location#getCountry <em>Country</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Country</em>'.
	 * @see camel.organisation.Location#getCountry()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Country();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Location#getCountryCode <em>Country Code</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Country Code</em>'.
	 * @see camel.organisation.Location#getCountryCode()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_CountryCode();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Location#getLatitude <em>Latitude</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Latitude</em>'.
	 * @see camel.organisation.Location#getLatitude()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Latitude();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Location#getLongitude <em>Longitude</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Longitude</em>'.
	 * @see camel.organisation.Location#getLongitude()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Longitude();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Location#getAddress <em>Address</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Address</em>'.
	 * @see camel.organisation.Location#getAddress()
	 * @see #getLocation()
	 * @generated
	 */
	EAttribute getLocation_Address();

	/**
	 * Returns the meta object for class '{@link camel.organisation.Permission <em>Permission</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Permission</em>'.
	 * @see camel.organisation.Permission
	 * @generated
	 */
	EClass getPermission();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.Permission#getForRole <em>For Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>For Role</em>'.
	 * @see camel.organisation.Permission#getForRole()
	 * @see #getPermission()
	 * @generated
	 */
	EReference getPermission_ForRole();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Permission#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start</em>'.
	 * @see camel.organisation.Permission#getStart()
	 * @see #getPermission()
	 * @generated
	 */
	EAttribute getPermission_Start();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Permission#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End</em>'.
	 * @see camel.organisation.Permission#getEnd()
	 * @see #getPermission()
	 * @generated
	 */
	EAttribute getPermission_End();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.Permission#getIssuedBy <em>Issued By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Issued By</em>'.
	 * @see camel.organisation.Permission#getIssuedBy()
	 * @see #getPermission()
	 * @generated
	 */
	EReference getPermission_IssuedBy();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.Permission#getOnResource <em>On Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>On Resource</em>'.
	 * @see camel.organisation.Permission#getOnResource()
	 * @see #getPermission()
	 * @generated
	 */
	EReference getPermission_OnResource();

	/**
	 * Returns the meta object for the reference list '{@link camel.organisation.Permission#getToAction <em>To Action</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>To Action</em>'.
	 * @see camel.organisation.Permission#getToAction()
	 * @see #getPermission()
	 * @generated
	 */
	EReference getPermission_ToAction();

	/**
	 * Returns the meta object for the '{@link camel.organisation.Permission#checkStartEndDates(camel.organisation.Permission) <em>Check Start End Dates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Start End Dates</em>' operation.
	 * @see camel.organisation.Permission#checkStartEndDates(camel.organisation.Permission)
	 * @generated
	 */
	EOperation getPermission__CheckStartEndDates__Permission();

	/**
	 * Returns the meta object for class '{@link camel.organisation.Resource <em>Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource</em>'.
	 * @see camel.organisation.Resource
	 * @generated
	 */
	EClass getResource();

	/**
	 * Returns the meta object for the '{@link camel.organisation.Resource#ofClass(org.eclipse.emf.ecore.EClass) <em>Of Class</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Of Class</em>' operation.
	 * @see camel.organisation.Resource#ofClass(org.eclipse.emf.ecore.EClass)
	 * @generated
	 */
	EOperation getResource__OfClass__EClass();

	/**
	 * Returns the meta object for the '{@link camel.organisation.Resource#allowsActions(org.eclipse.emf.common.util.EList) <em>Allows Actions</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Allows Actions</em>' operation.
	 * @see camel.organisation.Resource#allowsActions(org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	EOperation getResource__AllowsActions__EList();

	/**
	 * Returns the meta object for class '{@link camel.organisation.ResourceGroup <em>Resource Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Group</em>'.
	 * @see camel.organisation.ResourceGroup
	 * @generated
	 */
	EClass getResourceGroup();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.ResourceGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.organisation.ResourceGroup#getName()
	 * @see #getResourceGroup()
	 * @generated
	 */
	EAttribute getResourceGroup_Name();

	/**
	 * Returns the meta object for the reference list '{@link camel.organisation.ResourceGroup#getContainsResource <em>Contains Resource</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Contains Resource</em>'.
	 * @see camel.organisation.ResourceGroup#getContainsResource()
	 * @see #getResourceGroup()
	 * @generated
	 */
	EReference getResourceGroup_ContainsResource();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.ResourceGroup#getLevel <em>Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Level</em>'.
	 * @see camel.organisation.ResourceGroup#getLevel()
	 * @see #getResourceGroup()
	 * @generated
	 */
	EAttribute getResourceGroup_Level();

	/**
	 * Returns the meta object for the '{@link camel.organisation.ResourceGroup#allowsActionsOnResources(org.eclipse.emf.common.util.EList) <em>Allows Actions On Resources</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Allows Actions On Resources</em>' operation.
	 * @see camel.organisation.ResourceGroup#allowsActionsOnResources(org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	EOperation getResourceGroup__AllowsActionsOnResources__EList();

	/**
	 * Returns the meta object for the '{@link camel.organisation.ResourceGroup#checkRecursiveness(camel.organisation.ResourceGroup, camel.organisation.Resource) <em>Check Recursiveness</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Recursiveness</em>' operation.
	 * @see camel.organisation.ResourceGroup#checkRecursiveness(camel.organisation.ResourceGroup, camel.organisation.Resource)
	 * @generated
	 */
	EOperation getResourceGroup__CheckRecursiveness__ResourceGroup_Resource();

	/**
	 * Returns the meta object for class '{@link camel.organisation.Role <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role</em>'.
	 * @see camel.organisation.Role
	 * @generated
	 */
	EClass getRole();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.Role#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.organisation.Role#getName()
	 * @see #getRole()
	 * @generated
	 */
	EAttribute getRole_Name();

	/**
	 * Returns the meta object for class '{@link camel.organisation.RoleAssignment <em>Role Assignment</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Role Assignment</em>'.
	 * @see camel.organisation.RoleAssignment
	 * @generated
	 */
	EClass getRoleAssignment();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.RoleAssignment#getOfUser <em>Of User</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of User</em>'.
	 * @see camel.organisation.RoleAssignment#getOfUser()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EReference getRoleAssignment_OfUser();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.RoleAssignment#getToRole <em>To Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>To Role</em>'.
	 * @see camel.organisation.RoleAssignment#getToRole()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EReference getRoleAssignment_ToRole();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.RoleAssignment#getOfGroup <em>Of Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Of Group</em>'.
	 * @see camel.organisation.RoleAssignment#getOfGroup()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EReference getRoleAssignment_OfGroup();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.RoleAssignment#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Start</em>'.
	 * @see camel.organisation.RoleAssignment#getStart()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EAttribute getRoleAssignment_Start();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.RoleAssignment#getEnd <em>End</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>End</em>'.
	 * @see camel.organisation.RoleAssignment#getEnd()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EAttribute getRoleAssignment_End();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.RoleAssignment#getAssignedOn <em>Assigned On</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Assigned On</em>'.
	 * @see camel.organisation.RoleAssignment#getAssignedOn()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EAttribute getRoleAssignment_AssignedOn();

	/**
	 * Returns the meta object for the reference '{@link camel.organisation.RoleAssignment#getAssignedBy <em>Assigned By</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Assigned By</em>'.
	 * @see camel.organisation.RoleAssignment#getAssignedBy()
	 * @see #getRoleAssignment()
	 * @generated
	 */
	EReference getRoleAssignment_AssignedBy();

	/**
	 * Returns the meta object for the '{@link camel.organisation.RoleAssignment#checkAssignedOnDates(camel.organisation.RoleAssignment) <em>Check Assigned On Dates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Assigned On Dates</em>' operation.
	 * @see camel.organisation.RoleAssignment#checkAssignedOnDates(camel.organisation.RoleAssignment)
	 * @generated
	 */
	EOperation getRoleAssignment__CheckAssignedOnDates__RoleAssignment();

	/**
	 * Returns the meta object for the '{@link camel.organisation.RoleAssignment#checkStartEndDates(camel.organisation.RoleAssignment) <em>Check Start End Dates</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Start End Dates</em>' operation.
	 * @see camel.organisation.RoleAssignment#checkStartEndDates(camel.organisation.RoleAssignment)
	 * @generated
	 */
	EOperation getRoleAssignment__CheckStartEndDates__RoleAssignment();

	/**
	 * Returns the meta object for class '{@link camel.organisation.UserGroup <em>User Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Group</em>'.
	 * @see camel.organisation.UserGroup
	 * @generated
	 */
	EClass getUserGroup();

	/**
	 * Returns the meta object for the attribute '{@link camel.organisation.UserGroup#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.organisation.UserGroup#getName()
	 * @see #getUserGroup()
	 * @generated
	 */
	EAttribute getUserGroup_Name();

	/**
	 * Returns the meta object for the reference list '{@link camel.organisation.UserGroup#getMember <em>Member</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Member</em>'.
	 * @see camel.organisation.UserGroup#getMember()
	 * @see #getUserGroup()
	 * @generated
	 */
	EReference getUserGroup_Member();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	OrganisationFactory getOrganisationFactory();

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
		 * The meta object literal for the '{@link camel.organisation.impl.OrganisationModelImpl <em>Model</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.OrganisationModelImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getOrganisationModel()
		 * @generated
		 */
		EClass ORGANISATION_MODEL = eINSTANCE.getOrganisationModel();

		/**
		 * The meta object literal for the '<em><b>Users</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__USERS = eINSTANCE.getOrganisationModel_Users();

		/**
		 * The meta object literal for the '<em><b>Provider</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__PROVIDER = eINSTANCE.getOrganisationModel_Provider();

		/**
		 * The meta object literal for the '<em><b>Organization</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__ORGANIZATION = eINSTANCE.getOrganisationModel_Organization();

		/**
		 * The meta object literal for the '<em><b>User Groups</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__USER_GROUPS = eINSTANCE.getOrganisationModel_UserGroups();

		/**
		 * The meta object literal for the '<em><b>Data Centres</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__DATA_CENTRES = eINSTANCE.getOrganisationModel_DataCentres();

		/**
		 * The meta object literal for the '<em><b>Role Assigments</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__ROLE_ASSIGMENTS = eINSTANCE.getOrganisationModel_RoleAssigments();

		/**
		 * The meta object literal for the '<em><b>Roles</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__ROLES = eINSTANCE.getOrganisationModel_Roles();

		/**
		 * The meta object literal for the '<em><b>Permissions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__PERMISSIONS = eINSTANCE.getOrganisationModel_Permissions();

		/**
		 * The meta object literal for the '<em><b>Resources</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__RESOURCES = eINSTANCE.getOrganisationModel_Resources();

		/**
		 * The meta object literal for the '<em><b>Locations</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__LOCATIONS = eINSTANCE.getOrganisationModel_Locations();

		/**
		 * The meta object literal for the '<em><b>External Identifiers</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__EXTERNAL_IDENTIFIERS = eINSTANCE.getOrganisationModel_ExternalIdentifiers();

		/**
		 * The meta object literal for the '<em><b>Allowed Actions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ORGANISATION_MODEL__ALLOWED_ACTIONS = eINSTANCE.getOrganisationModel_AllowedActions();

		/**
		 * The meta object literal for the '{@link camel.organisation.impl.AllowedActionsImpl <em>Allowed Actions</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.AllowedActionsImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getAllowedActions()
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
		 * The meta object literal for the '{@link camel.organisation.impl.CredentialsImpl <em>Credentials</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.CredentialsImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getCredentials()
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

		/**
		 * The meta object literal for the '<em><b>Security Group</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDENTIALS__SECURITY_GROUP = eINSTANCE.getCredentials_SecurityGroup();

		/**
		 * The meta object literal for the '<em><b>Public SSH Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDENTIALS__PUBLIC_SSH_KEY = eINSTANCE.getCredentials_PublicSSHKey();

		/**
		 * The meta object literal for the '<em><b>Private SSH Key</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CREDENTIALS__PRIVATE_SSH_KEY = eINSTANCE.getCredentials_PrivateSSHKey();

		/**
		 * The meta object literal for the '{@link camel.organisation.impl.DataCenterImpl <em>Data Center</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.DataCenterImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getDataCenter()
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
		 * The meta object literal for the '{@link camel.organisation.impl.EntityImpl <em>Entity</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.EntityImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getEntity()
		 * @generated
		 */
		EClass ENTITY = eINSTANCE.getEntity();

		/**
		 * The meta object literal for the '{@link camel.organisation.impl.OrganizationImpl <em>Organization</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.OrganizationImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getOrganization()
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
		 * The meta object literal for the '{@link camel.organisation.impl.CloudProviderImpl <em>Cloud Provider</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.CloudProviderImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getCloudProvider()
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
		 * The meta object literal for the '<em><b>Offers Sec Control</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLOUD_PROVIDER__OFFERS_SEC_CONTROL = eINSTANCE.getCloudProvider_OffersSecControl();

		/**
		 * The meta object literal for the '<em><b>Provider Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CLOUD_PROVIDER__PROVIDER_MODEL = eINSTANCE.getCloudProvider_ProviderModel();

		/**
		 * The meta object literal for the '{@link camel.organisation.impl.UserImpl <em>User</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.UserImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getUser()
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
		 * The meta object literal for the '<em><b>Has External Identifier</b></em>' containment reference list feature.
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
		 * The meta object literal for the '<em><b>Has Credentials</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__HAS_CREDENTIALS = eINSTANCE.getUser_HasCredentials();

		/**
		 * The meta object literal for the '<em><b>Deployment Model</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER__DEPLOYMENT_MODEL = eINSTANCE.getUser_DeploymentModel();

		/**
		 * The meta object literal for the '{@link camel.organisation.impl.ExternalIdentifierImpl <em>External Identifier</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.ExternalIdentifierImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getExternalIdentifier()
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
		 * The meta object literal for the '{@link camel.organisation.impl.LocationImpl <em>Location</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.LocationImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getLocation()
		 * @generated
		 */
		EClass LOCATION = eINSTANCE.getLocation();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOCATION__NAME = eINSTANCE.getLocation_Name();

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
		 * The meta object literal for the '{@link camel.organisation.impl.PermissionImpl <em>Permission</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.PermissionImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getPermission()
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
		 * The meta object literal for the '<em><b>Check Start End Dates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation PERMISSION___CHECK_START_END_DATES__PERMISSION = eINSTANCE.getPermission__CheckStartEndDates__Permission();

		/**
		 * The meta object literal for the '{@link camel.organisation.impl.ResourceImpl <em>Resource</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.ResourceImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getResource()
		 * @generated
		 */
		EClass RESOURCE = eINSTANCE.getResource();

		/**
		 * The meta object literal for the '<em><b>Of Class</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RESOURCE___OF_CLASS__ECLASS = eINSTANCE.getResource__OfClass__EClass();

		/**
		 * The meta object literal for the '<em><b>Allows Actions</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RESOURCE___ALLOWS_ACTIONS__ELIST = eINSTANCE.getResource__AllowsActions__EList();

		/**
		 * The meta object literal for the '{@link camel.organisation.impl.ResourceGroupImpl <em>Resource Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.ResourceGroupImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getResourceGroup()
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
		 * The meta object literal for the '<em><b>Allows Actions On Resources</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RESOURCE_GROUP___ALLOWS_ACTIONS_ON_RESOURCES__ELIST = eINSTANCE.getResourceGroup__AllowsActionsOnResources__EList();

		/**
		 * The meta object literal for the '<em><b>Check Recursiveness</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RESOURCE_GROUP___CHECK_RECURSIVENESS__RESOURCEGROUP_RESOURCE = eINSTANCE.getResourceGroup__CheckRecursiveness__ResourceGroup_Resource();

		/**
		 * The meta object literal for the '{@link camel.organisation.impl.RoleImpl <em>Role</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.RoleImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getRole()
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
		 * The meta object literal for the '{@link camel.organisation.impl.RoleAssignmentImpl <em>Role Assignment</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.RoleAssignmentImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getRoleAssignment()
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
		 * The meta object literal for the '<em><b>Check Assigned On Dates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ROLE_ASSIGNMENT___CHECK_ASSIGNED_ON_DATES__ROLEASSIGNMENT = eINSTANCE.getRoleAssignment__CheckAssignedOnDates__RoleAssignment();

		/**
		 * The meta object literal for the '<em><b>Check Start End Dates</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ROLE_ASSIGNMENT___CHECK_START_END_DATES__ROLEASSIGNMENT = eINSTANCE.getRoleAssignment__CheckStartEndDates__RoleAssignment();

		/**
		 * The meta object literal for the '{@link camel.organisation.impl.UserGroupImpl <em>User Group</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.organisation.impl.UserGroupImpl
		 * @see camel.organisation.impl.OrganisationPackageImpl#getUserGroup()
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

	}

} //OrganisationPackage
