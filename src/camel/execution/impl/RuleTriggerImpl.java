/**
 */
package camel.execution.impl;

import camel.execution.ActionRealization;
import camel.execution.ExecutionContext;
import camel.execution.ExecutionPackage;
import camel.execution.RuleTrigger;

import camel.scalability.EventInstance;
import camel.scalability.ScalabilityRule;

import java.lang.reflect.InvocationTargetException;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Rule Trigger</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.RuleTriggerImpl#getScalabilityRule <em>Scalability Rule</em>}</li>
 *   <li>{@link camel.execution.impl.RuleTriggerImpl#getEventInstances <em>Event Instances</em>}</li>
 *   <li>{@link camel.execution.impl.RuleTriggerImpl#getActionRealizations <em>Action Realizations</em>}</li>
 *   <li>{@link camel.execution.impl.RuleTriggerImpl#getFiredOn <em>Fired On</em>}</li>
 *   <li>{@link camel.execution.impl.RuleTriggerImpl#getExecutionContext <em>Execution Context</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RuleTriggerImpl extends CDOObjectImpl implements RuleTrigger {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RuleTriggerImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.RULE_TRIGGER;
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
	public ScalabilityRule getScalabilityRule() {
		return (ScalabilityRule)eGet(ExecutionPackage.Literals.RULE_TRIGGER__SCALABILITY_RULE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScalabilityRule(ScalabilityRule newScalabilityRule) {
		eSet(ExecutionPackage.Literals.RULE_TRIGGER__SCALABILITY_RULE, newScalabilityRule);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<EventInstance> getEventInstances() {
		return (EList<EventInstance>)eGet(ExecutionPackage.Literals.RULE_TRIGGER__EVENT_INSTANCES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ActionRealization> getActionRealizations() {
		return (EList<ActionRealization>)eGet(ExecutionPackage.Literals.RULE_TRIGGER__ACTION_REALIZATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getFiredOn() {
		return (Date)eGet(ExecutionPackage.Literals.RULE_TRIGGER__FIRED_ON, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFiredOn(Date newFiredOn) {
		eSet(ExecutionPackage.Literals.RULE_TRIGGER__FIRED_ON, newFiredOn);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionContext getExecutionContext() {
		return (ExecutionContext)eGet(ExecutionPackage.Literals.RULE_TRIGGER__EXECUTION_CONTEXT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExecutionContext(ExecutionContext newExecutionContext) {
		eSet(ExecutionPackage.Literals.RULE_TRIGGER__EXECUTION_CONTEXT, newExecutionContext);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDate(final RuleTrigger rt) {
		Date d = rt.getFiredOn();
				for (ActionRealization ar: rt.getActionRealizations()){
					if (ar.getStartedOn().before(d)) return Boolean.FALSE;
				}
				ExecutionContext ec = rt.getExecutionContext();
				Date start = ec.getStartTime();
				Date end = ec.getEndTime();
				if ((start != null && (d.before(start) || d.equals(start))) || (end != null && end.before(d))) return Boolean.FALSE;
				return Boolean.TRUE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ExecutionPackage.RULE_TRIGGER___CHECK_DATE__RULETRIGGER:
				return checkDate((RuleTrigger)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //RuleTriggerImpl
