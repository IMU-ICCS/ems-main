/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.cp.BooleanExpression;

import eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Condition Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ConditionUpperware#getOperator <em>Operator</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ConditionUpperware#getExp1 <em>Exp1</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ConditionUpperware#getExp2 <em>Exp2</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getConditionUpperware()
 * @model
 * @generated
 */
public interface ConditionUpperware extends BooleanExpression {
	/**
	 * Returns the value of the '<em><b>Operator</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Operator</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum
	 * @see #setOperator(LogicOperatorEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getConditionUpperware_Operator()
	 * @model required="true"
	 * @generated
	 */
	LogicOperatorEnum getOperator();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ConditionUpperware#getOperator <em>Operator</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Operator</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.LogicOperatorEnum
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
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getConditionUpperware_Exp1()
	 * @model containment="true" required="true"
	 * @generated
	 */
	BooleanExpression getExp1();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ConditionUpperware#getExp1 <em>Exp1</em>}' containment reference.
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
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getConditionUpperware_Exp2()
	 * @model containment="true"
	 * @generated
	 */
	BooleanExpression getExp2();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ConditionUpperware#getExp2 <em>Exp2</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Exp2</em>' containment reference.
	 * @see #getExp2()
	 * @generated
	 */
	void setExp2(BooleanExpression value);

} // ConditionUpperware
