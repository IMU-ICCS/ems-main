/**
 */
package camel.execution;

import camel.Action;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Action Realization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.ActionRealization#getAction <em>Action</em>}</li>
 *   <li>{@link camel.execution.ActionRealization#getStartedOn <em>Started On</em>}</li>
 *   <li>{@link camel.execution.ActionRealization#getEndedOn <em>Ended On</em>}</li>
 *   <li>{@link camel.execution.ActionRealization#getLowLevelActions <em>Low Level Actions</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getActionRealization()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ActionRealization extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Action</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action</em>' reference.
	 * @see #setAction(Action)
	 * @see camel.execution.ExecutionPackage#getActionRealization_Action()
	 * @model required="true"
	 * @generated
	 */
	Action getAction();

	/**
	 * Sets the value of the '{@link camel.execution.ActionRealization#getAction <em>Action</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Action</em>' reference.
	 * @see #getAction()
	 * @generated
	 */
	void setAction(Action value);

	/**
	 * Returns the value of the '<em><b>Started On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Started On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Started On</em>' attribute.
	 * @see #setStartedOn(Date)
	 * @see camel.execution.ExecutionPackage#getActionRealization_StartedOn()
	 * @model
	 * @generated
	 */
	Date getStartedOn();

	/**
	 * Sets the value of the '{@link camel.execution.ActionRealization#getStartedOn <em>Started On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Started On</em>' attribute.
	 * @see #getStartedOn()
	 * @generated
	 */
	void setStartedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Ended On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ended On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ended On</em>' attribute.
	 * @see #setEndedOn(Date)
	 * @see camel.execution.ExecutionPackage#getActionRealization_EndedOn()
	 * @model
	 * @generated
	 */
	Date getEndedOn();

	/**
	 * Sets the value of the '{@link camel.execution.ActionRealization#getEndedOn <em>Ended On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Ended On</em>' attribute.
	 * @see #getEndedOn()
	 * @generated
	 */
	void setEndedOn(Date value);

	/**
	 * Returns the value of the '<em><b>Low Level Actions</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Low Level Actions</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Low Level Actions</em>' attribute.
	 * @see #setLowLevelActions(String)
	 * @see camel.execution.ExecutionPackage#getActionRealization_LowLevelActions()
	 * @model
	 * @generated
	 */
	String getLowLevelActions();

	/**
	 * Sets the value of the '{@link camel.execution.ActionRealization#getLowLevelActions <em>Low Level Actions</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Low Level Actions</em>' attribute.
	 * @see #getLowLevelActions()
	 * @generated
	 */
	void setLowLevelActions(String value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model thisRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='System.out.println(\"CHECKING ActionRealization_Start_Before_End: \" + this + \" \" + this.getStartedOn() + \" \" + this.getEndedOn()); java.util.Date date1 = this.getStartedOn(); java.util.Date date2 = this.getEndedOn(); if (date1 == null || date2 == null || (date1 != null && date2 != null && date1.before(date2))) return Boolean.TRUE; return Boolean.FALSE;'"
	 * @generated
	 */
	boolean checkStartEndDates(ActionRealization this_);

} // ActionRealization
