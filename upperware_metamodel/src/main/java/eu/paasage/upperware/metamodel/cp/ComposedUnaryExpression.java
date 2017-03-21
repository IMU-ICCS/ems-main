/**
 */
package eu.paasage.upperware.metamodel.cp;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composed Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getOperator <em>Operator</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComposedUnaryExpression()
 * @model abstract="true"
 * @generated
 */
public interface ComposedUnaryExpression extends UnaryExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
	 * @see #setOperator(ComposedUnaryOperatorEnum)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComposedUnaryExpression_Operator()
	 * @model required="true"
	 * @generated
	 */
	ComposedUnaryOperatorEnum getOperator();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(ComposedUnaryOperatorEnum value);

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
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getComposedUnaryExpression_Value()
	 * @model required="true"
	 * @generated
	 */
	int getValue();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getValue <em>Value</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' attribute.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(int value);

} // ComposedUnaryExpression
