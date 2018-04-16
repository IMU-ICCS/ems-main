/**
 */
package eu.paasage.upperware.metamodel.types;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Float Value Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.FloatValueUpperware#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.TypesPackage#getFloatValueUpperware()
 * @model
 * @generated
 */
public interface FloatValueUpperware extends NumericValueUpperware {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(float)
	 * @see eu.paasage.upperware.metamodel.types.TypesPackage#getFloatValueUpperware_Value()
	 * @model required="true"
	 * @generated
	 */
	float getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.FloatValueUpperware#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(float value);

} // FloatValueUpperware
