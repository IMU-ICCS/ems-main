/**
 */
package camel.execution.impl;

import camel.Action;

import camel.execution.ActionRealization;
import camel.execution.ExecutionPackage;

import java.lang.reflect.InvocationTargetException;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Action Realization</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.ActionRealizationImpl#getAction <em>Action</em>}</li>
 *   <li>{@link camel.execution.impl.ActionRealizationImpl#getStartedOn <em>Started On</em>}</li>
 *   <li>{@link camel.execution.impl.ActionRealizationImpl#getEndedOn <em>Ended On</em>}</li>
 *   <li>{@link camel.execution.impl.ActionRealizationImpl#getLowLevelActions <em>Low Level Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ActionRealizationImpl extends CDOObjectImpl implements ActionRealization {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ActionRealizationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.ACTION_REALIZATION;
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
	public Action getAction() {
		return (Action)eGet(ExecutionPackage.Literals.ACTION_REALIZATION__ACTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAction(Action newAction) {
		eSet(ExecutionPackage.Literals.ACTION_REALIZATION__ACTION, newAction);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getStartedOn() {
		return (Date)eGet(ExecutionPackage.Literals.ACTION_REALIZATION__STARTED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartedOn(Date newStartedOn) {
		eSet(ExecutionPackage.Literals.ACTION_REALIZATION__STARTED_ON, newStartedOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEndedOn() {
		return (Date)eGet(ExecutionPackage.Literals.ACTION_REALIZATION__ENDED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndedOn(Date newEndedOn) {
		eSet(ExecutionPackage.Literals.ACTION_REALIZATION__ENDED_ON, newEndedOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLowLevelActions() {
		return (String)eGet(ExecutionPackage.Literals.ACTION_REALIZATION__LOW_LEVEL_ACTIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowLevelActions(String newLowLevelActions) {
		eSet(ExecutionPackage.Literals.ACTION_REALIZATION__LOW_LEVEL_ACTIONS, newLowLevelActions);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkStartEndDates(final ActionRealization this_) {
		System.out.println("CHECKING ActionRealization_Start_Before_End: " + this + " " + this.getStartedOn() + " " + this.getEndedOn()); java.util.Date date1 = this.getStartedOn(); java.util.Date date2 = this.getEndedOn(); if (date1 == null || date2 == null || (date1 != null && date2 != null && date1.before(date2))) return Boolean.TRUE; return Boolean.FALSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ExecutionPackage.ACTION_REALIZATION___CHECK_START_END_DATES__ACTIONREALIZATION:
				return checkStartEndDates((ActionRealization)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //ActionRealizationImpl
