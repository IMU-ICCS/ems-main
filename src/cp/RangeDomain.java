/**
 */
package cp;

import types.NumericValue;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link cp.RangeDomain#getFrom <em>From</em>}</li>
 *   <li>{@link cp.RangeDomain#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * @see cp.CpPackage#getRangeDomain()
 * @model
 * @generated
 */
public interface RangeDomain extends NumericDomain {
	/**
	 * Returns the value of the '<em><b>From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>From</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>From</em>' containment reference.
	 * @see #setFrom(NumericValue)
	 * @see cp.CpPackage#getRangeDomain_From()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NumericValue getFrom();

	/**
	 * Sets the value of the '{@link cp.RangeDomain#getFrom <em>From</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' containment reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(NumericValue value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' containment reference.
	 * @see #setTo(NumericValue)
	 * @see cp.CpPackage#getRangeDomain_To()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NumericValue getTo();

	/**
	 * Sets the value of the '{@link cp.RangeDomain#getTo <em>To</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' containment reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(NumericValue value);

} // RangeDomain
