/**
 */
package cp;

import types.NumericValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link cp.Variable#getValue <em>Value</em>}</li>
 *   <li>{@link cp.Variable#getDomain <em>Domain</em>}</li>
 * </ul>
 * </p>
 *
 * @see cp.CpPackage#getVariable()
 * @model
 * @generated
 */
public interface Variable extends NumericExpression {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(NumericValue)
	 * @see cp.CpPackage#getVariable_Value()
	 * @model containment="true"
	 * @generated
	 */
	NumericValue getValue();

	/**
	 * Sets the value of the '{@link cp.Variable#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(NumericValue value);

	/**
	 * Returns the value of the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Domain</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Domain</em>' containment reference.
	 * @see #setDomain(Domain)
	 * @see cp.CpPackage#getVariable_Domain()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Domain getDomain();

	/**
	 * Sets the value of the '{@link cp.Variable#getDomain <em>Domain</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Domain</em>' containment reference.
	 * @see #getDomain()
	 * @generated
	 */
	void setDomain(Domain value);

} // Variable
