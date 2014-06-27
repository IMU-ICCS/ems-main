/**
 */
package camel.execution;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.ExecutionModel#getExecutionContext <em>Execution Context</em>}</li>
 *   <li>{@link camel.execution.ExecutionModel#getMeasurement <em>Measurement</em>}</li>
 *   <li>{@link camel.execution.ExecutionModel#getSloAssess <em>Slo Assess</em>}</li>
 *   <li>{@link camel.execution.ExecutionModel#getRuleTrigger <em>Rule Trigger</em>}</li>
 *   <li>{@link camel.execution.ExecutionModel#getActionRealization <em>Action Realization</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getExecutionModel()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ExecutionModel extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Execution Context</b></em>' containment reference list.
	 * The list contents are of type {@link camel.execution.ExecutionContext}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execution Context</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Execution Context</em>' containment reference list.
	 * @see camel.execution.ExecutionPackage#getExecutionModel_ExecutionContext()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ExecutionContext> getExecutionContext();

	/**
	 * Returns the value of the '<em><b>Measurement</b></em>' containment reference list.
	 * The list contents are of type {@link camel.execution.Measurement}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Measurement</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Measurement</em>' containment reference list.
	 * @see camel.execution.ExecutionPackage#getExecutionModel_Measurement()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<Measurement> getMeasurement();

	/**
	 * Returns the value of the '<em><b>Slo Assess</b></em>' containment reference list.
	 * The list contents are of type {@link camel.execution.SLOAssessment}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slo Assess</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slo Assess</em>' containment reference list.
	 * @see camel.execution.ExecutionPackage#getExecutionModel_SloAssess()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<SLOAssessment> getSloAssess();

	/**
	 * Returns the value of the '<em><b>Rule Trigger</b></em>' containment reference list.
	 * The list contents are of type {@link camel.execution.RuleTrigger}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Trigger</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Trigger</em>' containment reference list.
	 * @see camel.execution.ExecutionPackage#getExecutionModel_RuleTrigger()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<RuleTrigger> getRuleTrigger();

	/**
	 * Returns the value of the '<em><b>Action Realization</b></em>' containment reference list.
	 * The list contents are of type {@link camel.execution.ActionRealization}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Realization</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Realization</em>' containment reference list.
	 * @see camel.execution.ExecutionPackage#getExecutionModel_ActionRealization()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<ActionRealization> getActionRealization();

} // ExecutionModel
