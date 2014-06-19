/**
 */
package camel.cerif.impl;

import camel.cerif.*;

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
public class CerifFactoryImpl extends EFactoryImpl implements CerifFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CerifFactory init() {
		try {
			CerifFactory theCerifFactory = (CerifFactory)EPackage.Registry.INSTANCE.getEFactory(CerifPackage.eNS_URI);
			if (theCerifFactory != null) {
				return theCerifFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CerifFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CerifFactoryImpl() {
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
			case CerifPackage.CERIF_MODEL: return (EObject)createCerifModel();
			case CerifPackage.USER: return (EObject)createUser();
			case CerifPackage.ORGANIZATION: return (EObject)createOrganization();
			case CerifPackage.CLOUD_PROVIDER: return (EObject)createCloudProvider();
			case CerifPackage.ROLE: return (EObject)createRole();
			case CerifPackage.USER_GROUP: return (EObject)createUserGroup();
			case CerifPackage.ROLE_ASSIGNMENT: return (EObject)createRoleAssignment();
			case CerifPackage.PERMISSION: return (EObject)createPermission();
			case CerifPackage.RESOURCE: return (EObject)createResource();
			case CerifPackage.RESOURCE_GROUP: return (EObject)createResourceGroup();
			case CerifPackage.ALLOWED_ACTIONS: return (EObject)createAllowedActions();
			case CerifPackage.EXTERNAL_IDENTIFIER: return (EObject)createExternalIdentifier();
			case CerifPackage.DATA_CENTER: return (EObject)createDataCenter();
			case CerifPackage.LOCATION: return (EObject)createLocation();
			case CerifPackage.ENTITY: return (EObject)createEntity();
			case CerifPackage.CREDENTIALS: return (EObject)createCredentials();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CerifModel createCerifModel() {
		CerifModelImpl cerifModel = new CerifModelImpl();
		return cerifModel;
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
	public Role createRole() {
		RoleImpl role = new RoleImpl();
		return role;
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
	public RoleAssignment createRoleAssignment() {
		RoleAssignmentImpl roleAssignment = new RoleAssignmentImpl();
		return roleAssignment;
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
	public AllowedActions createAllowedActions() {
		AllowedActionsImpl allowedActions = new AllowedActionsImpl();
		return allowedActions;
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
	public DataCenter createDataCenter() {
		DataCenterImpl dataCenter = new DataCenterImpl();
		return dataCenter;
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
	public Entity createEntity() {
		EntityImpl entity = new EntityImpl();
		return entity;
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
	public CerifPackage getCerifPackage() {
		return (CerifPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CerifPackage getPackage() {
		return CerifPackage.eINSTANCE;
	}

} //CerifFactoryImpl
