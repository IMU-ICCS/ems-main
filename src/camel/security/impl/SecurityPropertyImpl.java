/**
 */
package camel.security.impl;

import camel.scalability.impl.PropertyImpl;

import camel.security.SecurityPackage;
import camel.security.SecurityProperty;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Property</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.security.impl.SecurityPropertyImpl#getDomain <em>Domain</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SecurityPropertyImpl extends PropertyImpl implements SecurityProperty {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SecurityPropertyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecurityPackage.Literals.SECURITY_PROPERTY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getDomain() {
		return (String)eGet(SecurityPackage.Literals.SECURITY_PROPERTY__DOMAIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomain(String newDomain) {
		eSet(SecurityPackage.Literals.SECURITY_PROPERTY__DOMAIN, newDomain);
	}

} //SecurityPropertyImpl
