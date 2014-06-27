/**
 */
package application;

import cp.BooleanExpression;

import types.typesPaasage.LogicOperatorEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Condition</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link application.Condition#getOperator <em>Operator</em>}</li>
 *   <li>{@link application.Condition#getExp1 <em>Exp1</em>}</li>
 *   <li>{@link application.Condition#getExp2 <em>Exp2</em>}</li>
 * </ul>
 * </p>
 *
 * @see application.ApplicationPackage#getCondition()
 * @model
 * @generated
 */
public interface Condition extends BooleanExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link types.typesPaasage.LogicOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see types.typesPaasage.LogicOperatorEnum
	 * @see #setOperator(LogicOperatorEnum)
	 * @see application.ApplicationPackage#getCondition_Operator()
	 * @model required="true"
	 * @generated
	 */
	LogicOperatorEnum getOperator();

	/**
	 * Sets the value of the '{@link application.Condition#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see types.typesPaasage.LogicOperatorEnum
	 * @see #getOperator()
	 * @generated
	 */
	void setOperator(LogicOperatorEnum value);

	/**
	 * Returns the value of the '<em><b>Exp1</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exp1</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exp1</em>' containment reference.
	 * @see #setExp1(BooleanExpression)
	 * @see application.ApplicationPackage#getCondition_Exp1()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BooleanExpression getExp1();

	/**
	 * Sets the value of the '{@link application.Condition#getExp1 <em>Exp1</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exp1</em>' containment reference.
	 * @see #getExp1()
	 * @generated
	 */
	void setExp1(BooleanExpression value);

	/**
	 * Returns the value of the '<em><b>Exp2</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Exp2</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Exp2</em>' containment reference.
	 * @see #setExp2(BooleanExpression)
	 * @see application.ApplicationPackage#getCondition_Exp2()
	 * @model containment="true"
	 * @generated
	 */
	BooleanExpression getExp2();

	/**
	 * Sets the value of the '{@link application.Condition#getExp2 <em>Exp2</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exp2</em>' containment reference.
	 * @see #getExp2()
	 * @generated
	 */
	void setExp2(BooleanExpression value);

} // Condition
