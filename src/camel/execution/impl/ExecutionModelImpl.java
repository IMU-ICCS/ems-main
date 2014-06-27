/**
 */
package camel.execution.impl;

import camel.execution.ActionRealization;
import camel.execution.ExecutionContext;
import camel.execution.ExecutionModel;
import camel.execution.ExecutionPackage;
import camel.execution.Measurement;
import camel.execution.RuleTrigger;
import camel.execution.SLOAssessment;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.ExecutionModelImpl#getExecutionContext <em>Execution Context</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionModelImpl#getMeasurement <em>Measurement</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionModelImpl#getSloAssess <em>Slo Assess</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionModelImpl#getRuleTrigger <em>Rule Trigger</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionModelImpl#getActionRealization <em>Action Realization</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExecutionModelImpl extends CDOObjectImpl implements ExecutionModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExecutionModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.EXECUTION_MODEL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ExecutionContext> getExecutionContext() {
		return (EList<ExecutionContext>)eGet(ExecutionPackage.Literals.EXECUTION_MODEL__EXECUTION_CONTEXT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Measurement> getMeasurement() {
		return (EList<Measurement>)eGet(ExecutionPackage.Literals.EXECUTION_MODEL__MEASUREMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<SLOAssessment> getSloAssess() {
		return (EList<SLOAssessment>)eGet(ExecutionPackage.Literals.EXECUTION_MODEL__SLO_ASSESS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RuleTrigger> getRuleTrigger() {
		return (EList<RuleTrigger>)eGet(ExecutionPackage.Literals.EXECUTION_MODEL__RULE_TRIGGER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ActionRealization> getActionRealization() {
		return (EList<ActionRealization>)eGet(ExecutionPackage.Literals.EXECUTION_MODEL__ACTION_REALIZATION, true);
	}

} //ExecutionModelImpl
