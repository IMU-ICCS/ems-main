/**
 */
package camel.cerif.impl;

import camel.cerif.AllowedActions;
import camel.cerif.CerifModel;
import camel.cerif.CerifPackage;
import camel.cerif.CloudProvider;
import camel.cerif.DataCenter;
import camel.cerif.ExternalIdentifier;
import camel.cerif.Location;
import camel.cerif.Organization;
import camel.cerif.Permission;
import camel.cerif.Resource;
import camel.cerif.Role;
import camel.cerif.RoleAssignment;
import camel.cerif.User;
import camel.cerif.UserGroup;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getUsers <em>Users</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getOrganization <em>Organization</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getUserGroups <em>User Groups</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getDataCentres <em>Data Centres</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getRoleAssigments <em>Role Assigments</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getRoles <em>Roles</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getPermissions <em>Permissions</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getLocations <em>Locations</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getExternalIdentifiers <em>External Identifiers</em>}</li>
 *   <li>{@link camel.cerif.impl.CerifModelImpl#getAllowedActions <em>Allowed Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CerifModelImpl extends CDOObjectImpl implements CerifModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CerifModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CerifPackage.Literals.CERIF_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<User> getUsers() {
		return (EList<User>)eGet(CerifPackage.Literals.CERIF_MODEL__USERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloudProvider getProvider() {
		return (CloudProvider)eGet(CerifPackage.Literals.CERIF_MODEL__PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(CloudProvider newProvider) {
		eSet(CerifPackage.Literals.CERIF_MODEL__PROVIDER, newProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Organization> getOrganization() {
		return (EList<Organization>)eGet(CerifPackage.Literals.CERIF_MODEL__ORGANIZATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<UserGroup> getUserGroups() {
		return (EList<UserGroup>)eGet(CerifPackage.Literals.CERIF_MODEL__USER_GROUPS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<DataCenter> getDataCentres() {
		return (EList<DataCenter>)eGet(CerifPackage.Literals.CERIF_MODEL__DATA_CENTRES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RoleAssignment> getRoleAssigments() {
		return (EList<RoleAssignment>)eGet(CerifPackage.Literals.CERIF_MODEL__ROLE_ASSIGMENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Role> getRoles() {
		return (EList<Role>)eGet(CerifPackage.Literals.CERIF_MODEL__ROLES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Permission> getPermissions() {
		return (EList<Permission>)eGet(CerifPackage.Literals.CERIF_MODEL__PERMISSIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Resource> getResources() {
		return (EList<Resource>)eGet(CerifPackage.Literals.CERIF_MODEL__RESOURCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Location> getLocations() {
		return (EList<Location>)eGet(CerifPackage.Literals.CERIF_MODEL__LOCATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ExternalIdentifier> getExternalIdentifiers() {
		return (EList<ExternalIdentifier>)eGet(CerifPackage.Literals.CERIF_MODEL__EXTERNAL_IDENTIFIERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<AllowedActions> getAllowedActions() {
		return (EList<AllowedActions>)eGet(CerifPackage.Literals.CERIF_MODEL__ALLOWED_ACTIONS, true);
	}

} //CerifModelImpl
