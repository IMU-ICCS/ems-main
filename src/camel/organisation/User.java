/**
 */
package camel.organisation;

import camel.Requirement;

import camel.deployment.DeploymentModel;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.organisation.User#getFirstName <em>First Name</em>}</li>
 *   <li>{@link camel.organisation.User#getLastName <em>Last Name</em>}</li>
 *   <li>{@link camel.organisation.User#getEmail <em>Email</em>}</li>
 *   <li>{@link camel.organisation.User#getWww <em>Www</em>}</li>
 *   <li>{@link camel.organisation.User#getLogin <em>Login</em>}</li>
 *   <li>{@link camel.organisation.User#getWorksFor <em>Works For</em>}</li>
 *   <li>{@link camel.organisation.User#getHasExternalIdentifier <em>Has External Identifier</em>}</li>
 *   <li>{@link camel.organisation.User#getHasRequirement <em>Has Requirement</em>}</li>
 *   <li>{@link camel.organisation.User#getHasCredentials <em>Has Credentials</em>}</li>
 *   <li>{@link camel.organisation.User#getDeploymentModel <em>Deployment Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.organisation.OrganisationPackage#getUser()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='UniqueName_ExternalIdentifier'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot UniqueName_ExternalIdentifier='\n\t\t\tUser.allInstances()->forAll(p1, p2 |p1 <> p2 implies p1.hasExternalIdentifier->intersection(p2.hasExternalIdentifier)->size() = 0  )'"
 * @generated
 */
public interface User extends Entity {
	/**
	 * Returns the value of the '<em><b>First Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>First Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>First Name</em>' attribute.
	 * @see #setFirstName(String)
	 * @see camel.organisation.OrganisationPackage#getUser_FirstName()
	 * @model required="true"
	 * @generated
	 */
	String getFirstName();

	/**
	 * Sets the value of the '{@link camel.organisation.User#getFirstName <em>First Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>First Name</em>' attribute.
	 * @see #getFirstName()
	 * @generated
	 */
	void setFirstName(String value);

	/**
	 * Returns the value of the '<em><b>Last Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Last Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Last Name</em>' attribute.
	 * @see #setLastName(String)
	 * @see camel.organisation.OrganisationPackage#getUser_LastName()
	 * @model required="true"
	 * @generated
	 */
	String getLastName();

	/**
	 * Sets the value of the '{@link camel.organisation.User#getLastName <em>Last Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Last Name</em>' attribute.
	 * @see #getLastName()
	 * @generated
	 */
	void setLastName(String value);

	/**
	 * Returns the value of the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Email</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email</em>' attribute.
	 * @see #setEmail(String)
	 * @see camel.organisation.OrganisationPackage#getUser_Email()
	 * @model
	 * @generated
	 */
	String getEmail();

	/**
	 * Sets the value of the '{@link camel.organisation.User#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 * @generated
	 */
	void setEmail(String value);

	/**
	 * Returns the value of the '<em><b>Www</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Www</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Www</em>' attribute.
	 * @see #setWww(String)
	 * @see camel.organisation.OrganisationPackage#getUser_Www()
	 * @model
	 * @generated
	 */
	String getWww();

	/**
	 * Sets the value of the '{@link camel.organisation.User#getWww <em>Www</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Www</em>' attribute.
	 * @see #getWww()
	 * @generated
	 */
	void setWww(String value);

	/**
	 * Returns the value of the '<em><b>Login</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Login</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Login</em>' attribute.
	 * @see #setLogin(String)
	 * @see camel.organisation.OrganisationPackage#getUser_Login()
	 * @model
	 * @generated
	 */
	String getLogin();

	/**
	 * Sets the value of the '{@link camel.organisation.User#getLogin <em>Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Login</em>' attribute.
	 * @see #getLogin()
	 * @generated
	 */
	void setLogin(String value);

	/**
	 * Returns the value of the '<em><b>Works For</b></em>' reference list.
	 * The list contents are of type {@link camel.organisation.Organization}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Works For</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Works For</em>' reference list.
	 * @see camel.organisation.OrganisationPackage#getUser_WorksFor()
	 * @model
	 * @generated
	 */
	EList<Organization> getWorksFor();

	/**
	 * Returns the value of the '<em><b>Has External Identifier</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.ExternalIdentifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has External Identifier</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has External Identifier</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getUser_HasExternalIdentifier()
	 * @model containment="true"
	 * @generated
	 */
	EList<ExternalIdentifier> getHasExternalIdentifier();

	/**
	 * Returns the value of the '<em><b>Has Requirement</b></em>' reference list.
	 * The list contents are of type {@link camel.Requirement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Requirement</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Requirement</em>' reference list.
	 * @see camel.organisation.OrganisationPackage#getUser_HasRequirement()
	 * @model
	 * @generated
	 */
	EList<Requirement> getHasRequirement();

	/**
	 * Returns the value of the '<em><b>Has Credentials</b></em>' containment reference list.
	 * The list contents are of type {@link camel.organisation.Credentials}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Credentials</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Credentials</em>' containment reference list.
	 * @see camel.organisation.OrganisationPackage#getUser_HasCredentials()
	 * @model containment="true"
	 * @generated
	 */
	EList<Credentials> getHasCredentials();

	/**
	 * Returns the value of the '<em><b>Deployment Model</b></em>' reference list.
	 * The list contents are of type {@link camel.deployment.DeploymentModel}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Model</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Model</em>' reference list.
	 * @see camel.organisation.OrganisationPackage#getUser_DeploymentModel()
	 * @model
	 * @generated
	 */
	EList<DeploymentModel> getDeploymentModel();

} // User
