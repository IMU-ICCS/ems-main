/**
 */
package eu.paasage.upperware.metamodel.cp;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Variable Value</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.CpVariableValue#getVariable <em>Variable</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.CpVariableValue#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getCpVariableValue()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface CpVariableValue extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Variable</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Variable</em>' reference.
	 * @see #setVariable(CpVariable)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getCpVariableValue_Variable()
	 * @model required="true"
	 * @generated
	 */
	CpVariable getVariable();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.CpVariableValue#getVariable <em>Variable</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Variable</em>' reference.
	 * @see #getVariable()
	 * @generated
	 */
	void setVariable(CpVariable value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(NumericValueUpperware)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getCpVariableValue_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NumericValueUpperware getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.CpVariableValue#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(NumericValueUpperware value);

} // CpVariableValue
