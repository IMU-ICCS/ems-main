/**
 */
package camel.type;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Limit</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.type.Limit#isIncluded <em>Included</em>}</li>
 *   <li>{@link camel.type.Limit#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.type.TypePackage#getLimit()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Limit extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Included</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Included</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Included</em>' attribute.
	 * @see #setIncluded(boolean)
	 * @see camel.type.TypePackage#getLimit_Included()
	 * @model required="true"
	 * @generated
	 */
	boolean isIncluded();

	/**
	 * Sets the value of the '{@link camel.type.Limit#isIncluded <em>Included</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Included</em>' attribute.
	 * @see #isIncluded()
	 * @generated
	 */
	void setIncluded(boolean value);

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
	 * @see camel.type.TypePackage#getLimit_Value()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NumericValue getValue();

	/**
	 * Sets the value of the '{@link camel.type.Limit#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(NumericValue value);

} // Limit
