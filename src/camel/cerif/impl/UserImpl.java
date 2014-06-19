/**
 */
package camel.cerif.impl;

import camel.Requirement;

import camel.cerif.CerifPackage;
import camel.cerif.Credentials;
import camel.cerif.ExternalIdentifier;
import camel.cerif.Organization;
import camel.cerif.User;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.UserImpl#getFirstName <em>First Name</em>}</li>
 *   <li>{@link camel.cerif.impl.UserImpl#getLastName <em>Last Name</em>}</li>
 *   <li>{@link camel.cerif.impl.UserImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link camel.cerif.impl.UserImpl#getWww <em>Www</em>}</li>
 *   <li>{@link camel.cerif.impl.UserImpl#getLogin <em>Login</em>}</li>
 *   <li>{@link camel.cerif.impl.UserImpl#getWorksFor <em>Works For</em>}</li>
 *   <li>{@link camel.cerif.impl.UserImpl#getHasExternalIdentifier <em>Has External Identifier</em>}</li>
 *   <li>{@link camel.cerif.impl.UserImpl#getHasRequirement <em>Has Requirement</em>}</li>
 *   <li>{@link camel.cerif.impl.UserImpl#getHasCredentials <em>Has Credentials</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class UserImpl extends EntityImpl implements User {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected UserImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CerifPackage.Literals.USER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFirstName() {
		return (String)eGet(CerifPackage.Literals.USER__FIRST_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFirstName(String newFirstName) {
		eSet(CerifPackage.Literals.USER__FIRST_NAME, newFirstName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastName() {
		return (String)eGet(CerifPackage.Literals.USER__LAST_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastName(String newLastName) {
		eSet(CerifPackage.Literals.USER__LAST_NAME, newLastName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEmail() {
		return (String)eGet(CerifPackage.Literals.USER__EMAIL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmail(String newEmail) {
		eSet(CerifPackage.Literals.USER__EMAIL, newEmail);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWww() {
		return (String)eGet(CerifPackage.Literals.USER__WWW, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWww(String newWww) {
		eSet(CerifPackage.Literals.USER__WWW, newWww);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLogin() {
		return (String)eGet(CerifPackage.Literals.USER__LOGIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLogin(String newLogin) {
		eSet(CerifPackage.Literals.USER__LOGIN, newLogin);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Organization> getWorksFor() {
		return (EList<Organization>)eGet(CerifPackage.Literals.USER__WORKS_FOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ExternalIdentifier> getHasExternalIdentifier() {
		return (EList<ExternalIdentifier>)eGet(CerifPackage.Literals.USER__HAS_EXTERNAL_IDENTIFIER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Requirement> getHasRequirement() {
		return (EList<Requirement>)eGet(CerifPackage.Literals.USER__HAS_REQUIREMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Credentials> getHasCredentials() {
		return (EList<Credentials>)eGet(CerifPackage.Literals.USER__HAS_CREDENTIALS, true);
	}

} //UserImpl
