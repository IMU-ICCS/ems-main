/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.cp.GoalOperatorEnum;

import eu.paasage.upperware.metamodel.types.typesPaasage.FunctionType;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paa Sage Goal</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getGoal <em>Goal</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getFunction <em>Function</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationComponent <em>Application Component</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationMetric <em>Application Metric</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal()
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
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_Id()
	 * @model required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

	/**
	 * Returns the value of the '<em><b>Goal</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Goal</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Goal</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @see #setGoal(GoalOperatorEnum)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_Goal()
	 * @model required="true"
	 * @generated
	 */
	GoalOperatorEnum getGoal();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getGoal <em>Goal</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Goal</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
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
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_Function()
	 * @model required="true"
	 * @generated
	 */
	FunctionType getFunction();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getFunction <em>Function</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Function</em>' reference.
	 * @see #getFunction()
	 * @generated
	 */
	void setFunction(FunctionType value);

	/**
	 * Returns the value of the '<em><b>Application Component</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.ComponentMetricRelationship}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application Component</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application Component</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_ApplicationComponent()
	 * @model containment="true"
	 * @generated
	 */
	EList<ComponentMetricRelationship> getApplicationComponent();

	/**
	 * Returns the value of the '<em><b>Application Metric</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Application Metric</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Application Metric</em>' attribute.
	 * @see #setApplicationMetric(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getPaaSageGoal_ApplicationMetric()
	 * @model
	 * @generated
	 */
	String getApplicationMetric();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.PaaSageGoal#getApplicationMetric <em>Application Metric</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Application Metric</em>' attribute.
	 * @see #getApplicationMetric()
	 * @generated
	 */
	void setApplicationMetric(String value);

} // PaaSageGoal
