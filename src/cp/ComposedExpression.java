/**
 */
package cp;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Composed Expression</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link cp.ComposedExpression#getExpressions <em>Expressions</em>}</li>
 *   <li>{@link cp.ComposedExpression#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @see cp.CpPackage#getComposedExpression()
 * @model
 * @generated
 */
public interface ComposedExpression extends NumericExpression {
	/**
	 * Returns the value of the '<em><b>Expressions</b></em>' reference list.
	 * The list contents are of type {@link cp.NumericExpression}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expressions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expressions</em>' reference list.
	 * @see cp.CpPackage#getComposedExpression_Expressions()
	 * @model lower="2"
	 * @generated
	 */
	EList<NumericExpression> getExpressions();

	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link cp.OperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see cp.OperatorEnum
	 * @see #setOperator(OperatorEnum)
	 * @see cp.CpPackage#getComposedExpression_Operator()
	 * @model
	 * @generated
	 */
	OperatorEnum getOperator();

	/**
	 * Sets the value of the '{@link cp.ComposedExpression#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see cp.OperatorEnum
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(OperatorEnum value);

} // ComposedExpression
