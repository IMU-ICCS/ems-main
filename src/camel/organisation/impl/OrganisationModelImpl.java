/**
 */
package camel.organisation.impl;

import camel.organisation.AllowedActions;
import camel.organisation.CloudProvider;
import camel.organisation.DataCenter;
import camel.organisation.ExternalIdentifier;
import camel.organisation.Location;
import camel.organisation.OrganisationModel;
import camel.organisation.OrganisationPackage;
import camel.organisation.Organization;
import camel.organisation.Permission;
import camel.organisation.Resource;
import camel.organisation.Role;
import camel.organisation.RoleAssignment;
import camel.organisation.User;
import camel.organisation.UserGroup;

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
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getUsers <em>Users</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getOrganization <em>Organization</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getUserGroups <em>User Groups</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getDataCentres <em>Data Centres</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getRoleAssigments <em>Role Assigments</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getRoles <em>Roles</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getPermissions <em>Permissions</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getResources <em>Resources</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getLocations <em>Locations</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getExternalIdentifiers <em>External Identifiers</em>}</li>
 *   <li>{@link camel.organisation.impl.OrganisationModelImpl#getAllowedActions <em>Allowed Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrganisationModelImpl extends CDOObjectImpl implements OrganisationModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OrganisationModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrganisationPackage.Literals.ORGANISATION_MODEL;
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
		return (EList<User>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__USERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloudProvider getProvider() {
		return (CloudProvider)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(CloudProvider newProvider) {
		eSet(OrganisationPackage.Literals.ORGANISATION_MODEL__PROVIDER, newProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Organization getOrganization() {
		return (Organization)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__ORGANIZATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrganization(Organization newOrganization) {
		eSet(OrganisationPackage.Literals.ORGANISATION_MODEL__ORGANIZATION, newOrganization);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<UserGroup> getUserGroups() {
		return (EList<UserGroup>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__USER_GROUPS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<DataCenter> getDataCentres() {
		return (EList<DataCenter>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__DATA_CENTRES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RoleAssignment> getRoleAssigments() {
		return (EList<RoleAssignment>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__ROLE_ASSIGMENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Role> getRoles() {
		return (EList<Role>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__ROLES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Permission> getPermissions() {
		return (EList<Permission>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__PERMISSIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Resource> getResources() {
		return (EList<Resource>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__RESOURCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Location> getLocations() {
		return (EList<Location>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__LOCATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ExternalIdentifier> getExternalIdentifiers() {
		return (EList<ExternalIdentifier>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__EXTERNAL_IDENTIFIERS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<AllowedActions> getAllowedActions() {
		return (EList<AllowedActions>)eGet(OrganisationPackage.Literals.ORGANISATION_MODEL__ALLOWED_ACTIONS, true);
	}

} //OrganisationModelImpl
