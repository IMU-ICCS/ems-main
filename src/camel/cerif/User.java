/**
 */
package camel.cerif;

import camel.Requirement;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.cerif.User#getFirstName <em>First Name</em>}</li>
 *   <li>{@link camel.cerif.User#getLastName <em>Last Name</em>}</li>
 *   <li>{@link camel.cerif.User#getEmail <em>Email</em>}</li>
 *   <li>{@link camel.cerif.User#getWww <em>Www</em>}</li>
 *   <li>{@link camel.cerif.User#getLogin <em>Login</em>}</li>
 *   <li>{@link camel.cerif.User#getWorksFor <em>Works For</em>}</li>
 *   <li>{@link camel.cerif.User#getHasExternalIdentifier <em>Has External Identifier</em>}</li>
 *   <li>{@link camel.cerif.User#getHasRequirement <em>Has Requirement</em>}</li>
 *   <li>{@link camel.cerif.User#getHasCredentials <em>Has Credentials</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.cerif.CerifPackage#getUser()
 * @model
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
	 * @see camel.cerif.CerifPackage#getUser_FirstName()
	 * @model
	 * @generated
	 */
	String getFirstName();

	/**
	 * Sets the value of the '{@link camel.cerif.User#getFirstName <em>First Name</em>}' attribute.
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
	 * @see camel.cerif.CerifPackage#getUser_LastName()
	 * @model
	 * @generated
	 */
	String getLastName();

	/**
	 * Sets the value of the '{@link camel.cerif.User#getLastName <em>Last Name</em>}' attribute.
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
	 * @see camel.cerif.CerifPackage#getUser_Email()
	 * @model
	 * @generated
	 */
	String getEmail();

	/**
	 * Sets the value of the '{@link camel.cerif.User#getEmail <em>Email</em>}' attribute.
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
	 * @see camel.cerif.CerifPackage#getUser_Www()
	 * @model
	 * @generated
	 */
	String getWww();

	/**
	 * Sets the value of the '{@link camel.cerif.User#getWww <em>Www</em>}' attribute.
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
	 * @see camel.cerif.CerifPackage#getUser_Login()
	 * @model
	 * @generated
	 */
	String getLogin();

	/**
	 * Sets the value of the '{@link camel.cerif.User#getLogin <em>Login</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Login</em>' attribute.
	 * @see #getLogin()
	 * @generated
	 */
	void setLogin(String value);

	/**
	 * Returns the value of the '<em><b>Works For</b></em>' reference list.
	 * The list contents are of type {@link camel.cerif.Organization}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Works For</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Works For</em>' reference list.
	 * @see camel.cerif.CerifPackage#getUser_WorksFor()
	 * @model
	 * @generated
	 */
	EList<Organization> getWorksFor();

	/**
	 * Returns the value of the '<em><b>Has External Identifier</b></em>' reference list.
	 * The list contents are of type {@link camel.cerif.ExternalIdentifier}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has External Identifier</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has External Identifier</em>' reference list.
	 * @see camel.cerif.CerifPackage#getUser_HasExternalIdentifier()
	 * @model
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
	 * @see camel.cerif.CerifPackage#getUser_HasRequirement()
	 * @model
	 * @generated
	 */
	EList<Requirement> getHasRequirement();

	/**
	 * Returns the value of the '<em><b>Has Credentials</b></em>' reference list.
	 * The list contents are of type {@link camel.cerif.Credentials}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Has Credentials</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Has Credentials</em>' reference list.
	 * @see camel.cerif.CerifPackage#getUser_HasCredentials()
	 * @model
	 * @generated
	 */
	EList<Credentials> getHasCredentials();

} // User
