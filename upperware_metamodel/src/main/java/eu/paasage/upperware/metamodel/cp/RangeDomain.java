/**
 */
package eu.paasage.upperware.metamodel.cp;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Domain</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getFrom <em>From</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getTo <em>To</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getRangeDomain()
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
	 * @see #setFrom(NumericValueUpperware)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getRangeDomain_From()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NumericValueUpperware getFrom();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getFrom <em>From</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>From</em>' containment reference.
	 * @see #getFrom()
	 * @generated
	 */
	void setFrom(NumericValueUpperware value);

	/**
	 * Returns the value of the '<em><b>To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>To</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>To</em>' containment reference.
	 * @see #setTo(NumericValueUpperware)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getRangeDomain_To()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NumericValueUpperware getTo();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getTo <em>To</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>To</em>' containment reference.
	 * @see #getTo()
	 * @generated
	 */
	void setTo(NumericValueUpperware value);

} // RangeDomain
