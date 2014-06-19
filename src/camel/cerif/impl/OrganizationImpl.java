/**
 */
package camel.cerif.impl;

import camel.cerif.CerifPackage;
import camel.cerif.Organization;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Organization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.OrganizationImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.cerif.impl.OrganizationImpl#getWww <em>Www</em>}</li>
 *   <li>{@link camel.cerif.impl.OrganizationImpl#getPostalAddress <em>Postal Address</em>}</li>
 *   <li>{@link camel.cerif.impl.OrganizationImpl#getEmail <em>Email</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class OrganizationImpl extends EntityImpl implements Organization {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected OrganizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CerifPackage.Literals.ORGANIZATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(CerifPackage.Literals.ORGANIZATION__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(CerifPackage.Literals.ORGANIZATION__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWww() {
		return (String)eGet(CerifPackage.Literals.ORGANIZATION__WWW, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWww(String newWww) {
		eSet(CerifPackage.Literals.ORGANIZATION__WWW, newWww);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPostalAddress() {
		return (String)eGet(CerifPackage.Literals.ORGANIZATION__POSTAL_ADDRESS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPostalAddress(String newPostalAddress) {
		eSet(CerifPackage.Literals.ORGANIZATION__POSTAL_ADDRESS, newPostalAddress);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getEmail() {
		return (String)eGet(CerifPackage.Literals.ORGANIZATION__EMAIL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEmail(String newEmail) {
		eSet(CerifPackage.Literals.ORGANIZATION__EMAIL, newEmail);
	}

} //OrganizationImpl
