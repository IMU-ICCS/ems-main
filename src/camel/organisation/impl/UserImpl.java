/**
 */
package camel.organisation.impl;

import camel.Requirement;

import camel.deployment.DeploymentModel;

import camel.organisation.Credentials;
import camel.organisation.ExternalIdentifier;
import camel.organisation.OrganisationPackage;
import camel.organisation.Organization;
import camel.organisation.User;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>User</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.organisation.impl.UserImpl#getFirstName <em>First Name</em>}</li>
 *   <li>{@link camel.organisation.impl.UserImpl#getLastName <em>Last Name</em>}</li>
 *   <li>{@link camel.organisation.impl.UserImpl#getEmail <em>Email</em>}</li>
 *   <li>{@link camel.organisation.impl.UserImpl#getWww <em>Www</em>}</li>
 *   <li>{@link camel.organisation.impl.UserImpl#getLogin <em>Login</em>}</li>
 *   <li>{@link camel.organisation.impl.UserImpl#getWorksFor <em>Works For</em>}</li>
 *   <li>{@link camel.organisation.impl.UserImpl#getHasExternalIdentifier <em>Has External Identifier</em>}</li>
 *   <li>{@link camel.organisation.impl.UserImpl#getHasRequirement <em>Has Requirement</em>}</li>
 *   <li>{@link camel.organisation.impl.UserImpl#getHasCredentials <em>Has Credentials</em>}</li>
 *   <li>{@link camel.organisation.impl.UserImpl#getDeploymentModel <em>Deployment Model</em>}</li>
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
		return OrganisationPackage.Literals.USER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getFirstName() {
		return (String)eGet(OrganisationPackage.Literals.USER__FIRST_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFirstName(String newFirstName) {
		eSet(OrganisationPackage.Literals.USER__FIRST_NAME, newFirstName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLastName() {
		return (String)eGet(OrganisationPackage.Literals.USER__LAST_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLastName(String newLastName) {
		eSet(OrganisationPackage.Literals.USER__LAST_NAME, newLastName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEmail() {
		return (String)eGet(OrganisationPackage.Literals.USER__EMAIL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmail(String newEmail) {
		eSet(OrganisationPackage.Literals.USER__EMAIL, newEmail);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWww() {
		return (String)eGet(OrganisationPackage.Literals.USER__WWW, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWww(String newWww) {
		eSet(OrganisationPackage.Literals.USER__WWW, newWww);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLogin() {
		return (String)eGet(OrganisationPackage.Literals.USER__LOGIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLogin(String newLogin) {
		eSet(OrganisationPackage.Literals.USER__LOGIN, newLogin);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Organization> getWorksFor() {
		return (EList<Organization>)eGet(OrganisationPackage.Literals.USER__WORKS_FOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ExternalIdentifier> getHasExternalIdentifier() {
		return (EList<ExternalIdentifier>)eGet(OrganisationPackage.Literals.USER__HAS_EXTERNAL_IDENTIFIER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Requirement> getHasRequirement() {
		return (EList<Requirement>)eGet(OrganisationPackage.Literals.USER__HAS_REQUIREMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Credentials> getHasCredentials() {
		return (EList<Credentials>)eGet(OrganisationPackage.Literals.USER__HAS_CREDENTIALS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<DeploymentModel> getDeploymentModel() {
		return (EList<DeploymentModel>)eGet(OrganisationPackage.Literals.USER__DEPLOYMENT_MODEL, true);
	}

} //UserImpl
