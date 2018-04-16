/**
 */
package eu.paasage.upperware.metamodel.cp;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Goal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Goal#getExpression <em>Expression</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Goal#getGoalType <em>Goal Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.Goal#getPriority <em>Priority</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getGoal()
 * @model
 * @generated
 */
public interface Goal extends CPElement {
	/**
	 * Returns the value of the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression</em>' containment reference.
	 * @see #setExpression(NumericExpression)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getGoal_Expression()
	 * @model containment="true" required="true"
	 * @generated
	 */
	NumericExpression getExpression();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Goal#getExpression <em>Expression</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression</em>' containment reference.
	 * @see #getExpression()
	 * @generated
	 */
	void setExpression(NumericExpression value);

	/**
	 * Returns the value of the '<em><b>Goal Type</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goal Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goal Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @see #setGoalType(GoalOperatorEnum)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getGoal_GoalType()
	 * @model required="true"
	 * @generated
	 */
	GoalOperatorEnum getGoalType();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Goal#getGoalType <em>Goal Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goal Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @see #getGoalType()
	 * @generated
	 */
	void setGoalType(GoalOperatorEnum value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(double)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getGoal_Priority()
	 * @model
	 * @generated
	 */
	double getPriority();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.Goal#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(double value);

} // Goal
