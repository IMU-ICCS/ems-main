/**
 */
package camel.cerif;

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
 *   <li>{@link camel.cerif.CerifModel#getUsers <em>Users</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getProvider <em>Provider</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getOrganization <em>Organization</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getUserGroups <em>User Groups</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getDataCentres <em>Data Centres</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getRoleAssigments <em>Role Assigments</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getRoles <em>Roles</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getPermissions <em>Permissions</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getResources <em>Resources</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getLocations <em>Locations</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getExternalIdentifiers <em>External Identifiers</em>}</li>
 *   <li>{@link camel.cerif.CerifModel#getAllowedActions <em>Allowed Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.cerif.CerifPackage#getCerifModel()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface CerifModel extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Users</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.User}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Users</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Users</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_Users()
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
	 * @see camel.cerif.CerifPackage#getCerifModel_Provider()
	 * @model containment="true"
	 * @generated
	 */
	CloudProvider getProvider();

	/**
	 * Sets the value of the '{@link camel.cerif.CerifModel#getProvider <em>Provider</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Provider</em>' containment reference.
	 * @see #getProvider()
	 * @generated
	 */
	void setProvider(CloudProvider value);

	/**
	 * Returns the value of the '<em><b>Organization</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.Organization}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Organization</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Organization</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_Organization()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Organization> getOrganization();

	/**
	 * Returns the value of the '<em><b>User Groups</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.UserGroup}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Groups</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Groups</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_UserGroups()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<UserGroup> getUserGroups();

	/**
	 * Returns the value of the '<em><b>Data Centres</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.DataCenter}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Data Centres</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Data Centres</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_DataCentres()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<DataCenter> getDataCentres();

	/**
	 * Returns the value of the '<em><b>Role Assigments</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.RoleAssignment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Role Assigments</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Role Assigments</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_RoleAssigments()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<RoleAssignment> getRoleAssigments();

	/**
	 * Returns the value of the '<em><b>Roles</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.Role}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Roles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Roles</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_Roles()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Role> getRoles();

	/**
	 * Returns the value of the '<em><b>Permissions</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.Permission}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Permissions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Permissions</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_Permissions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Permission> getPermissions();

	/**
	 * Returns the value of the '<em><b>Resources</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.Resource}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Resources</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Resources</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_Resources()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Resource> getResources();

	/**
	 * Returns the value of the '<em><b>Locations</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.Location}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locations</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_Locations()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Location> getLocations();

	/**
	 * Returns the value of the '<em><b>External Identifiers</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.ExternalIdentifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>External Identifiers</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>External Identifiers</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_ExternalIdentifiers()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ExternalIdentifier> getExternalIdentifiers();

	/**
	 * Returns the value of the '<em><b>Allowed Actions</b></em>' containment reference list.
	 * The list contents are of type {@link camel.cerif.AllowedActions}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Allowed Actions</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Allowed Actions</em>' containment reference list.
	 * @see camel.cerif.CerifPackage#getCerifModel_AllowedActions()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<AllowedActions> getAllowedActions();

} // CerifModel
