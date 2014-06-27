/**
 */
package camel.organisation;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.organisation.OrganisationModel#getUsers <em>Users</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getProvider <em>Provider</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getOrganization <em>Organization</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getUserGroups <em>User Groups</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getDataCentres <em>Data Centres</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getRoleAssigments <em>Role Assigments</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getRoles <em>Roles</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getPermissions <em>Permissions</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getResources <em>Resources</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getLocations <em>Locations</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getExternalIdentifiers <em>External Identifiers</em>}</li>
 *   <li>{@link camel.organisation.OrganisationModel#getAllowedActions <em>Allowed Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.organisation.OrganisationPackage#getOrganisationModel()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface OrganisationModel extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Users</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.User}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Users</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Users</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_Users()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<User> getUsers();

	/**
	 * Returns the value of the '<em><b>Provider</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Provider</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Provider</em>' containment reference.
	 * @see #setProvider(CloudProvider)
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_Provider()
	 * @model containment="true"
	 * @generated
	 */
	CloudProvider getProvider();

	/**
	 * Sets the value of the '{@link camel.organisation.OrganisationModel#getProvider <em>Provider</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' containment reference.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(CloudProvider value);

	/**
	 * Returns the value of the '<em><b>Organization</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization</em>' containment reference.
	 * @see #setOrganization(Organization)
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_Organization()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Organization getOrganization();

	/**
	 * Sets the value of the '{@link camel.organisation.OrganisationModel#getOrganization <em>Organization</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Organization</em>' containment reference.
	 * @see #getOrganization()
	 * @generated
	 */
	void setOrganization(Organization value);

	/**
	 * Returns the value of the '<em><b>User Groups</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.UserGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Groups</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_UserGroups()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<UserGroup> getUserGroups();

	/**
	 * Returns the value of the '<em><b>Data Centres</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.DataCenter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Centres</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Centres</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_DataCentres()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<DataCenter> getDataCentres();

	/**
	 * Returns the value of the '<em><b>Role Assigments</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.RoleAssignment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role Assigments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role Assigments</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_RoleAssigments()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<RoleAssignment> getRoleAssigments();

	/**
	 * Returns the value of the '<em><b>Roles</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.Role}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Roles</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_Roles()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Role> getRoles();

	/**
	 * Returns the value of the '<em><b>Permissions</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.Permission}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Permissions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Permissions</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_Permissions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Permission> getPermissions();

	/**
	 * Returns the value of the '<em><b>Resources</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.Resource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resources</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_Resources()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Resource> getResources();

	/**
	 * Returns the value of the '<em><b>Locations</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.Location}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locations</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_Locations()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Location> getLocations();

	/**
	 * Returns the value of the '<em><b>External Identifiers</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.ExternalIdentifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External Identifiers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Identifiers</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_ExternalIdentifiers()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ExternalIdentifier> getExternalIdentifiers();

	/**
	 * Returns the value of the '<em><b>Allowed Actions</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.AllowedActions}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allowed Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allowed Actions</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getOrganisationModel_AllowedActions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<AllowedActions> getAllowedActions();

} // OrganisationModel
