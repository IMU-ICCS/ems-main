/**
 */
package cp;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link cp.SimpleUnaryExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see cp.CpPackage#getSimpleUnaryExpression()
 * @model abstract="true"
 * @generated
 */
public interface SimpleUnaryExpression extends UnaryExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link cp.SimpleUnaryOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see cp.SimpleUnaryOperatorEnum
	 * @see #setOperator(SimpleUnaryOperatorEnum)
	 * @see cp.CpPackage#getSimpleUnaryExpression_Operator()
	 * @model required="true"
	 * @generated
	 */
	SimpleUnaryOperatorEnum getOperator();

	/**
	 * Sets the value of the '{@link cp.SimpleUnaryExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see cp.SimpleUnaryOperatorEnum
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(SimpleUnaryOperatorEnum value);

} // SimpleUnaryExpression
