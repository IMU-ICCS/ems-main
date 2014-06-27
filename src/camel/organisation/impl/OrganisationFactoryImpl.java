/**
 */
package camel.organisation.impl;

import camel.organisation.*;

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
public class OrganisationFactoryImpl extends EFactoryImpl implements OrganisationFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OrganisationFactory init() {
		try {
			OrganisationFactory theOrganisationFactory = (OrganisationFactory)EPackage.Registry.INSTANCE.getEFactory(OrganisationPackage.eNS_URI);
			if (theOrganisationFactory != null) {
				return theOrganisationFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new OrganisationFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrganisationFactoryImpl() {
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
			case OrganisationPackage.ORGANISATION_MODEL: return (EObject)createOrganisationModel();
			case OrganisationPackage.ALLOWED_ACTIONS: return (EObject)createAllowedActions();
			case OrganisationPackage.CREDENTIALS: return (EObject)createCredentials();
			case OrganisationPackage.DATA_CENTER: return (EObject)createDataCenter();
			case OrganisationPackage.ENTITY: return (EObject)createEntity();
			case OrganisationPackage.ORGANIZATION: return (EObject)createOrganization();
			case OrganisationPackage.CLOUD_PROVIDER: return (EObject)createCloudProvider();
			case OrganisationPackage.USER: return (EObject)createUser();
			case OrganisationPackage.EXTERNAL_IDENTIFIER: return (EObject)createExternalIdentifier();
			case OrganisationPackage.LOCATION: return (EObject)createLocation();
			case OrganisationPackage.PERMISSION: return (EObject)createPermission();
			case OrganisationPackage.RESOURCE: return (EObject)createResource();
			case OrganisationPackage.RESOURCE_GROUP: return (EObject)createResourceGroup();
			case OrganisationPackage.ROLE: return (EObject)createRole();
			case OrganisationPackage.ROLE_ASSIGNMENT: return (EObject)createRoleAssignment();
			case OrganisationPackage.USER_GROUP: return (EObject)createUserGroup();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrganisationModel createOrganisationModel() {
		OrganisationModelImpl organisationModel = new OrganisationModelImpl();
		return organisationModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AllowedActions createAllowedActions() {
		AllowedActionsImpl allowedActions = new AllowedActionsImpl();
		return allowedActions;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Credentials createCredentials() {
		CredentialsImpl credentials = new CredentialsImpl();
		return credentials;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataCenter createDataCenter() {
		DataCenterImpl dataCenter = new DataCenterImpl();
		return dataCenter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity createEntity() {
		EntityImpl entity = new EntityImpl();
		return entity;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Organization createOrganization() {
		OrganizationImpl organization = new OrganizationImpl();
		return organization;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloudProvider createCloudProvider() {
		CloudProviderImpl cloudProvider = new CloudProviderImpl();
		return cloudProvider;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public User createUser() {
		UserImpl user = new UserImpl();
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExternalIdentifier createExternalIdentifier() {
		ExternalIdentifierImpl externalIdentifier = new ExternalIdentifierImpl();
		return externalIdentifier;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Location createLocation() {
		LocationImpl location = new LocationImpl();
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Permission createPermission() {
		PermissionImpl permission = new PermissionImpl();
		return permission;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Resource createResource() {
		ResourceImpl resource = new ResourceImpl();
		return resource;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceGroup createResourceGroup() {
		ResourceGroupImpl resourceGroup = new ResourceGroupImpl();
		return resourceGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Role createRole() {
		RoleImpl role = new RoleImpl();
		return role;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RoleAssignment createRoleAssignment() {
		RoleAssignmentImpl roleAssignment = new RoleAssignmentImpl();
		return roleAssignment;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserGroup createUserGroup() {
		UserGroupImpl userGroup = new UserGroupImpl();
		return userGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrganisationPackage getOrganisationPackage() {
		return (OrganisationPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static OrganisationPackage getPackage() {
		return OrganisationPackage.eINSTANCE;
	}

} //OrganisationFactoryImpl
