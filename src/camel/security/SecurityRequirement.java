/**
 */
package camel.security;

import camel.Requirement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Requirement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.security.SecurityRequirement#getOnSecControl <em>On Sec Control</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.security.SecurityPackage#getSecurityRequirement()
 * @model
 * @generated
 */
public interface SecurityRequirement extends Requirement {
	/**
	 * Returns the value of the '<em><b>On Sec Control</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Sec Control</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Sec Control</em>' reference.
	 * @see #setOnSecControl(SecurityControl)
	 * @see camel.security.SecurityPackage#getSecurityRequirement_OnSecControl()
	 * @model required="true"
	 * @generated
	 */
	SecurityControl getOnSecControl();

	/**
	 * Sets the value of the '{@link camel.security.SecurityRequirement#getOnSecControl <em>On Sec Control</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>On Sec Control</em>' reference.
	 * @see #getOnSecControl()
	 * @generated
	 */
	void setOnSecControl(SecurityControl value);

} // SecurityRequirement
