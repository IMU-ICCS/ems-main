/**
 */
package camel.execution.impl;

import camel.execution.ExecutionContext;
import camel.execution.ExecutionPackage;
import camel.execution.Measurement;
import camel.execution.SLOAssessment;

import camel.sla.ServiceLevelObjectiveType;

import java.lang.reflect.InvocationTargetException;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>SLO Assessment</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.SLOAssessmentImpl#getItSLO <em>It SLO</em>}</li>
 *   <li>{@link camel.execution.impl.SLOAssessmentImpl#isAssessment <em>Assessment</em>}</li>
 *   <li>{@link camel.execution.impl.SLOAssessmentImpl#getInExecutionContext <em>In Execution Context</em>}</li>
 *   <li>{@link camel.execution.impl.SLOAssessmentImpl#getMeasurement <em>Measurement</em>}</li>
 *   <li>{@link camel.execution.impl.SLOAssessmentImpl#getAssessmentTime <em>Assessment Time</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class SLOAssessmentImpl extends CDOObjectImpl implements SLOAssessment {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SLOAssessmentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.SLO_ASSESSMENT;
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
	public ServiceLevelObjectiveType getItSLO() {
		return (ServiceLevelObjectiveType)eGet(ExecutionPackage.Literals.SLO_ASSESSMENT__IT_SLO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setItSLO(ServiceLevelObjectiveType newItSLO) {
		eSet(ExecutionPackage.Literals.SLO_ASSESSMENT__IT_SLO, newItSLO);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isAssessment() {
		return (Boolean)eGet(ExecutionPackage.Literals.SLO_ASSESSMENT__ASSESSMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssessment(boolean newAssessment) {
		eSet(ExecutionPackage.Literals.SLO_ASSESSMENT__ASSESSMENT, newAssessment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExecutionContext getInExecutionContext() {
		return (ExecutionContext)eGet(ExecutionPackage.Literals.SLO_ASSESSMENT__IN_EXECUTION_CONTEXT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInExecutionContext(ExecutionContext newInExecutionContext) {
		eSet(ExecutionPackage.Literals.SLO_ASSESSMENT__IN_EXECUTION_CONTEXT, newInExecutionContext);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Measurement getMeasurement() {
		return (Measurement)eGet(ExecutionPackage.Literals.SLO_ASSESSMENT__MEASUREMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMeasurement(Measurement newMeasurement) {
		eSet(ExecutionPackage.Literals.SLO_ASSESSMENT__MEASUREMENT, newMeasurement);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getAssessmentTime() {
		return (Date)eGet(ExecutionPackage.Literals.SLO_ASSESSMENT__ASSESSMENT_TIME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAssessmentTime(Date newAssessmentTime) {
		eSet(ExecutionPackage.Literals.SLO_ASSESSMENT__ASSESSMENT_TIME, newAssessmentTime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkDate(final SLOAssessment sa) {
		Date reportedOn = sa.getAssessmentTime();
				Measurement m = sa.getMeasurement();
				Date date = m.getReportedOn();
				if (reportedOn.before(date)) return Boolean.FALSE;
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
			case ExecutionPackage.SLO_ASSESSMENT___CHECK_DATE__SLOASSESSMENT:
				return checkDate((SLOAssessment)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //SLOAssessmentImpl
