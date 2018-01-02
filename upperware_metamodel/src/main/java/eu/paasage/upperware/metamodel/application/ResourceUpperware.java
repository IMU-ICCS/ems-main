/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import eu.paasage.upperware.metamodel.types.typesPaasage.PaaSageCPElement;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Resource Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ResourceUpperware#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getResourceUpperware()
 * @model abstract="true"
 * @generated
 */
public interface ResourceUpperware extends PaaSageCPElement {
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
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getResourceUpperware_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NumericValueUpperware getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ResourceUpperware#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(NumericValueUpperware value);

} // ResourceUpperware
