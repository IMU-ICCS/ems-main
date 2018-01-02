/**
 */
package eu.paasage.upperware.metamodel.cp;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Simple Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getSimpleUnaryExpression()
 * @model abstract="true"
 * @generated
 */
public interface SimpleUnaryExpression extends UnaryExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum
	 * @see #setOperator(SimpleUnaryOperatorEnum)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getSimpleUnaryExpression_Operator()
	 * @model required="true"
	 * @generated
	 */
	SimpleUnaryOperatorEnum getOperator();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(SimpleUnaryOperatorEnum value);

} // SimpleUnaryExpression
