/**
 */
package camel.execution.impl;

import camel.Application;
import camel.MonetaryUnit;
import camel.RequirementGroup;

import camel.deployment.DeploymentModel;

import camel.execution.ExecutionContext;
import camel.execution.ExecutionPackage;

import java.lang.reflect.InvocationTargetException;

import java.util.Date;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.execution.impl.ExecutionContextImpl#getOfApplication <em>Of Application</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionContextImpl#getID <em>ID</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionContextImpl#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionContextImpl#getEndTime <em>End Time</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionContextImpl#getTotalCost <em>Total Cost</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionContextImpl#getCostUnit <em>Cost Unit</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionContextImpl#getInvolvesDeployment <em>Involves Deployment</em>}</li>
 *   <li>{@link camel.execution.impl.ExecutionContextImpl#getBasedOnRequirements <em>Based On Requirements</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ExecutionContextImpl extends CDOObjectImpl implements ExecutionContext {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ExecutionContextImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ExecutionPackage.Literals.EXECUTION_CONTEXT;
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
	public Application getOfApplication() {
		return (Application)eGet(ExecutionPackage.Literals.EXECUTION_CONTEXT__OF_APPLICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfApplication(Application newOfApplication) {
		eSet(ExecutionPackage.Literals.EXECUTION_CONTEXT__OF_APPLICATION, newOfApplication);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getID() {
		return (String)eGet(ExecutionPackage.Literals.EXECUTION_CONTEXT__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setID(String newID) {
		eSet(ExecutionPackage.Literals.EXECUTION_CONTEXT__ID, newID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getStartTime() {
		return (Date)eGet(ExecutionPackage.Literals.EXECUTION_CONTEXT__START_TIME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStartTime(Date newStartTime) {
		eSet(ExecutionPackage.Literals.EXECUTION_CONTEXT__START_TIME, newStartTime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getEndTime() {
		return (Date)eGet(ExecutionPackage.Literals.EXECUTION_CONTEXT__END_TIME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEndTime(Date newEndTime) {
		eSet(ExecutionPackage.Literals.EXECUTION_CONTEXT__END_TIME, newEndTime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getTotalCost() {
		return (Double)eGet(ExecutionPackage.Literals.EXECUTION_CONTEXT__TOTAL_COST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTotalCost(double newTotalCost) {
		eSet(ExecutionPackage.Literals.EXECUTION_CONTEXT__TOTAL_COST, newTotalCost);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MonetaryUnit getCostUnit() {
		return (MonetaryUnit)eGet(ExecutionPackage.Literals.EXECUTION_CONTEXT__COST_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCostUnit(MonetaryUnit newCostUnit) {
		eSet(ExecutionPackage.Literals.EXECUTION_CONTEXT__COST_UNIT, newCostUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentModel getInvolvesDeployment() {
		return (DeploymentModel)eGet(ExecutionPackage.Literals.EXECUTION_CONTEXT__INVOLVES_DEPLOYMENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setInvolvesDeployment(DeploymentModel newInvolvesDeployment) {
		eSet(ExecutionPackage.Literals.EXECUTION_CONTEXT__INVOLVES_DEPLOYMENT, newInvolvesDeployment);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementGroup getBasedOnRequirements() {
		return (RequirementGroup)eGet(ExecutionPackage.Literals.EXECUTION_CONTEXT__BASED_ON_REQUIREMENTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBasedOnRequirements(RequirementGroup newBasedOnRequirements) {
		eSet(ExecutionPackage.Literals.EXECUTION_CONTEXT__BASED_ON_REQUIREMENTS, newBasedOnRequirements);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkStartEndDates(final ExecutionContext this_) {
		System.out.println("CHECKING ExecutionContext_Start_Before_End: " + this + " " + this.getStartTime() + " " + this.getEndTime()); java.util.Date date1 = this.getStartTime(); java.util.Date date2 = this.getEndTime(); if (date1 == null || date2 == null || (date1 != null && date2 != null && date1.before(date2))) return Boolean.TRUE; return Boolean.FALSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case ExecutionPackage.EXECUTION_CONTEXT___CHECK_START_END_DATES__EXECUTIONCONTEXT:
				return checkStartEndDates((ExecutionContext)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //ExecutionContextImpl
