/**
 */
package eu.paasage.upperware.metamodel.types;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Boolean Value Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.BooleanValueUpperware#isValue <em>Value</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.TypesPackage#getBooleanValueUpperware()
 * @model
 * @generated
 */
public interface BooleanValueUpperware extends ValueUpperware {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(boolean)
	 * @see eu.paasage.upperware.metamodel.types.TypesPackage#getBooleanValueUpperware_Value()
	 * @model required="true"
	 * @generated
	 */
	boolean isValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.BooleanValueUpperware#isValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #isValue()
	 * @generated
	 */
	void setValue(boolean value);

} // BooleanValueUpperware
