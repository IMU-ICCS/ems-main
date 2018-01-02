/**
 */
package eu.paasage.upperware.metamodel.types;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Double Value Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.DoubleValueUpperware#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.TypesPackage#getDoubleValueUpperware()
 * @model
 * @generated
 */
public interface DoubleValueUpperware extends NumericValueUpperware {
	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(double)
	 * @see eu.paasage.upperware.metamodel.types.TypesPackage#getDoubleValueUpperware_Value()
	 * @model required="true"
	 * @generated
	 */
	double getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.DoubleValueUpperware#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(double value);

} // DoubleValueUpperware
