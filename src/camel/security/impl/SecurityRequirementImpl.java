/**
 */
package camel.security.impl;

import camel.impl.RequirementImpl;

import camel.security.SecurityControl;
import camel.security.SecurityPackage;
import camel.security.SecurityRequirement;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requirement</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.security.impl.SecurityRequirementImpl#getOnSecControl <em>On Sec Control</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SecurityRequirementImpl extends RequirementImpl implements SecurityRequirement {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SecurityRequirementImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SecurityPackage.Literals.SECURITY_REQUIREMENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SecurityControl getOnSecControl() {
		return (SecurityControl)eGet(SecurityPackage.Literals.SECURITY_REQUIREMENT__ON_SEC_CONTROL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnSecControl(SecurityControl newOnSecControl) {
		eSet(SecurityPackage.Literals.SECURITY_REQUIREMENT__ON_SEC_CONTROL, newOnSecControl);
	}

} //SecurityRequirementImpl
