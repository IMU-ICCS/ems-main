/**
 */
package camel.provider;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Functional</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.provider.Functional#getType <em>Type</em>}</li>
 *   <li>{@link camel.provider.Functional#getOrder <em>Order</em>}</li>
 *   <li>{@link camel.provider.Functional#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.provider.ProviderPackage#getFunctional()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='correct_values_for_Functional'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot correct_values_for_Functional='\n\t\t\t\t\t\torder >= 0 and value > 0'"
 * @generated
 */
public interface Functional extends Requires {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.provider.Operator}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see camel.provider.Operator
	 * @see #setType(Operator)
	 * @see camel.provider.ProviderPackage#getFunctional_Type()
	 * @model
	 * @generated
	 */
	Operator getType();

	/**
	 * Sets the value of the '{@link camel.provider.Functional#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see camel.provider.Operator
	 * @see #getType()
	 * @generated
	 */
	void setType(Operator value);

	/**
	 * Returns the value of the '<em><b>Order</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Order</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Order</em>' attribute.
	 * @see #setOrder(int)
	 * @see camel.provider.ProviderPackage#getFunctional_Order()
	 * @model
	 * @generated
	 */
	int getOrder();

	/**
	 * Sets the value of the '{@link camel.provider.Functional#getOrder <em>Order</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Order</em>' attribute.
	 * @see #getOrder()
	 * @generated
	 */
	void setOrder(int value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' attribute.
	 * @see #setValue(int)
	 * @see camel.provider.ProviderPackage#getFunctional_Value()
	 * @model required="true"
	 * @generated
	 */
	int getValue();

	/**
	 * Sets the value of the '{@link camel.provider.Functional#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(int value);

} // Functional
