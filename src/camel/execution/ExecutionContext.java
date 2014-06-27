/**
 */
package camel.execution;

import camel.Application;
import camel.MonetaryUnit;
import camel.RequirementGroup;

import camel.deployment.DeploymentModel;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Context</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.ExecutionContext#getOfApplication <em>Of Application</em>}</li>
 *   <li>{@link camel.execution.ExecutionContext#getID <em>ID</em>}</li>
 *   <li>{@link camel.execution.ExecutionContext#getStartTime <em>Start Time</em>}</li>
 *   <li>{@link camel.execution.ExecutionContext#getEndTime <em>End Time</em>}</li>
 *   <li>{@link camel.execution.ExecutionContext#getTotalCost <em>Total Cost</em>}</li>
 *   <li>{@link camel.execution.ExecutionContext#getCostUnit <em>Cost Unit</em>}</li>
 *   <li>{@link camel.execution.ExecutionContext#getInvolvesDeployment <em>Involves Deployment</em>}</li>
 *   <li>{@link camel.execution.ExecutionContext#getBasedOnRequirements <em>Based On Requirements</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getExecutionContext()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='ExecutionContext_Total_Cost ExecutionContext_Unit_Cost ExecutionContext_Correct_Reqs'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot ExecutionContext_Total_Cost='\n\t\t\t\t\t\tself.totalCost >= 0' ExecutionContext_Unit_Cost='\n\t\t\t\t\t\tself.totalCost > 0 implies costUnit <> null' ExecutionContext_Correct_Reqs='\n\t\t\t\t\t\t\t\tbasedOnRequirements.requirement->forAll(p | p.oclIsTypeOf(camel::sla::ServiceLevelObjectiveType) implies (p.oclAsType(camel::sla::ServiceLevelObjectiveType).customServiceLevel.metric.objectBinding.executionContext=self))'"
 * @extends CDOObject
 * @generated
 */
public interface ExecutionContext extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Of Application</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Of Application</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Of Application</em>' reference.
	 * @see #setOfApplication(Application)
	 * @see camel.execution.ExecutionPackage#getExecutionContext_OfApplication()
	 * @model required="true"
	 * @generated
	 */
	Application getOfApplication();

	/**
	 * Sets the value of the '{@link camel.execution.ExecutionContext#getOfApplication <em>Of Application</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of Application</em>' reference.
	 * @see #getOfApplication()
	 * @generated
	 */
	void setOfApplication(Application value);

	/**
	 * Returns the value of the '<em><b>ID</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>ID</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>ID</em>' attribute.
	 * @see #setID(String)
	 * @see camel.execution.ExecutionPackage#getExecutionContext_ID()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getID();

	/**
	 * Sets the value of the '{@link camel.execution.ExecutionContext#getID <em>ID</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>ID</em>' attribute.
	 * @see #getID()
	 * @generated
	 */
	void setID(String value);

	/**
	 * Returns the value of the '<em><b>Start Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Start Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Start Time</em>' attribute.
	 * @see #setStartTime(Date)
	 * @see camel.execution.ExecutionPackage#getExecutionContext_StartTime()
	 * @model
	 * @generated
	 */
	Date getStartTime();

	/**
	 * Sets the value of the '{@link camel.execution.ExecutionContext#getStartTime <em>Start Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Start Time</em>' attribute.
	 * @see #getStartTime()
	 * @generated
	 */
	void setStartTime(Date value);

	/**
	 * Returns the value of the '<em><b>End Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>End Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>End Time</em>' attribute.
	 * @see #setEndTime(Date)
	 * @see camel.execution.ExecutionPackage#getExecutionContext_EndTime()
	 * @model
	 * @generated
	 */
	Date getEndTime();

	/**
	 * Sets the value of the '{@link camel.execution.ExecutionContext#getEndTime <em>End Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>End Time</em>' attribute.
	 * @see #getEndTime()
	 * @generated
	 */
	void setEndTime(Date value);

	/**
	 * Returns the value of the '<em><b>Total Cost</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Total Cost</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Total Cost</em>' attribute.
	 * @see #setTotalCost(double)
	 * @see camel.execution.ExecutionPackage#getExecutionContext_TotalCost()
	 * @model
	 * @generated
	 */
	double getTotalCost();

	/**
	 * Sets the value of the '{@link camel.execution.ExecutionContext#getTotalCost <em>Total Cost</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Total Cost</em>' attribute.
	 * @see #getTotalCost()
	 * @generated
	 */
	void setTotalCost(double value);

	/**
	 * Returns the value of the '<em><b>Cost Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cost Unit</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cost Unit</em>' reference.
	 * @see #setCostUnit(MonetaryUnit)
	 * @see camel.execution.ExecutionPackage#getExecutionContext_CostUnit()
	 * @model
	 * @generated
	 */
	MonetaryUnit getCostUnit();

	/**
	 * Sets the value of the '{@link camel.execution.ExecutionContext#getCostUnit <em>Cost Unit</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cost Unit</em>' reference.
	 * @see #getCostUnit()
	 * @generated
	 */
	void setCostUnit(MonetaryUnit value);

	/**
	 * Returns the value of the '<em><b>Involves Deployment</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Involves Deployment</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Involves Deployment</em>' reference.
	 * @see #setInvolvesDeployment(DeploymentModel)
	 * @see camel.execution.ExecutionPackage#getExecutionContext_InvolvesDeployment()
	 * @model required="true"
	 * @generated
	 */
	DeploymentModel getInvolvesDeployment();

	/**
	 * Sets the value of the '{@link camel.execution.ExecutionContext#getInvolvesDeployment <em>Involves Deployment</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Involves Deployment</em>' reference.
	 * @see #getInvolvesDeployment()
	 * @generated
	 */
	void setInvolvesDeployment(DeploymentModel value);

	/**
	 * Returns the value of the '<em><b>Based On Requirements</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Based On Requirements</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Based On Requirements</em>' reference.
	 * @see #setBasedOnRequirements(RequirementGroup)
	 * @see camel.execution.ExecutionPackage#getExecutionContext_BasedOnRequirements()
	 * @model required="true"
	 * @generated
	 */
	RequirementGroup getBasedOnRequirements();

	/**
	 * Sets the value of the '{@link camel.execution.ExecutionContext#getBasedOnRequirements <em>Based On Requirements</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Based On Requirements</em>' reference.
	 * @see #getBasedOnRequirements()
	 * @generated
	 */
	void setBasedOnRequirements(RequirementGroup value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model thisRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"CHECKING ExecutionContext_Start_Before_End: \" + this + \" \" + this.getStartTime() + \" \" + this.getEndTime()); java.util.Date date1 = this.getStartTime(); java.util.Date date2 = this.getEndTime(); if (date1 == null || date2 == null || (date1 != null && date2 != null && date1.before(date2))) return Boolean.TRUE; return Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkStartEndDates(ExecutionContext this_);

} // ExecutionContext
