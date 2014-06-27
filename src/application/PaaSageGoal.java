/**
 */
package application;

import cp.GoalOperatorEnum;

import org.eclipse.emf.cdo.CDOObject;

import types.typesPaasage.FunctionType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paa Sage Goal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link application.PaaSageGoal#getId <em>Id</em>}</li>
 *   <li>{@link application.PaaSageGoal#getGoal <em>Goal</em>}</li>
 *   <li>{@link application.PaaSageGoal#getFunction <em>Function</em>}</li>
 * </ul>
 * </p>
 *
 * @see application.ApplicationPackage#getPaaSageGoal()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface PaaSageGoal extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see application.ApplicationPackage#getPaaSageGoal_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link application.PaaSageGoal#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Goal</b></em>' attribute.
	 * The literals are from the enumeration {@link cp.GoalOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goal</em>' attribute.
	 * @see cp.GoalOperatorEnum
	 * @see #setGoal(GoalOperatorEnum)
	 * @see application.ApplicationPackage#getPaaSageGoal_Goal()
	 * @model required="true"
	 * @generated
	 */
	GoalOperatorEnum getGoal();

	/**
	 * Sets the value of the '{@link application.PaaSageGoal#getGoal <em>Goal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goal</em>' attribute.
	 * @see cp.GoalOperatorEnum
	 * @see #getGoal()
	 * @generated
	 */
	void setGoal(GoalOperatorEnum value);

	/**
	 * Returns the value of the '<em><b>Function</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Function</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Function</em>' reference.
	 * @see #setFunction(FunctionType)
	 * @see application.ApplicationPackage#getPaaSageGoal_Function()
	 * @model required="true"
	 * @generated
	 */
	FunctionType getFunction();

	/**
	 * Sets the value of the '{@link application.PaaSageGoal#getFunction <em>Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' reference.
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(FunctionType value);

} // PaaSageGoal
