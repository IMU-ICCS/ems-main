/**
 */
package camel.execution;

import camel.scalability.EventInstance;
import camel.scalability.ScalabilityRule;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule Trigger</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.RuleTrigger#getScalabilityRule <em>Scalability Rule</em>}</li>
 *   <li>{@link camel.execution.RuleTrigger#getEventInstances <em>Event Instances</em>}</li>
 *   <li>{@link camel.execution.RuleTrigger#getActionRealizations <em>Action Realizations</em>}</li>
 *   <li>{@link camel.execution.RuleTrigger#getFiredOn <em>Fired On</em>}</li>
 *   <li>{@link camel.execution.RuleTrigger#getExecutionContext <em>Execution Context</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getRuleTrigger()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Rule_Trig_Event_Ins_Correct_Events Rule_Trig_Correct_Action Rule_Trig_Event_Metric_EC Rule_Trig_Scal_Policies_of_Correct_Dep_Model'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Rule_Trig_Event_Ins_Correct_Events='\n\t\t\t\t\t\t\t\tif (self.scalabilityRule.relatedEvent.oclIsKindOf(camel::scalability::SimpleEvent)) then (self.eventInstances->size() = 1 and self.eventInstances->exists(p | p.onEvent.oclAsType(camel::scalability::SimpleEvent) = self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::SimpleEvent)))\n\t\t\t\t\t\t\t\telse self.eventInstances->forAll(p | self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::EventPattern).includesEvent(p.onEvent)) \n\t\t\t\t\t\t\t\tendif' Rule_Trig_Correct_Action='\n\t\t\t\t\t\t\t\t(self.actionRealizations->size() = self.scalabilityRule.mapsToActions->size()) and (self.actionRealizations->forAll(p | self.scalabilityRule.mapsToActions->exists(q | q = p.action)))' Rule_Trig_Event_Metric_EC='\n\t\t\t\t\t\t\t\tif (self.scalabilityRule.relatedEvent.oclIsTypeOf(camel::scalability::NonFunctionalEvent)) then (self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::NonFunctionalEvent).condition.metric.objectBinding.executionContext = executionContext)\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (self.scalabilityRule.relatedEvent.oclIsKindOf(camel::scalability::EventPattern)) then self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::EventPattern).relatedToExecutionContext(executionContext)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif' Rule_Trig_Scal_Policies_of_Correct_Dep_Model='\n\t\t\t\t\t\t\t\tscalabilityRule.invariantPolicies->forAll(p | \n\t\t\t\t\t\t\t\t\tif (p.oclIsTypeOf(camel::scalability::HorizontalScalabilityPolicy) and p.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component <> null) then executionContext.involvesDeployment.components->includes(p.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component)\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (p.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy) and p.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm <> null) then executionContext.involvesDeployment.components->includes(p.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm)\n\t\t\t\t\t\t\t\t\t\telse true\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif \n\t\t\t\t\t\t\t\t)'"
 * @extends CDOObject
 * @generated
 */
public interface RuleTrigger extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Scalability Rule</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scalability Rule</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scalability Rule</em>' reference.
	 * @see #setScalabilityRule(ScalabilityRule)
	 * @see camel.execution.ExecutionPackage#getRuleTrigger_ScalabilityRule()
	 * @model required="true"
	 * @generated
	 */
	ScalabilityRule getScalabilityRule();

	/**
	 * Sets the value of the '{@link camel.execution.RuleTrigger#getScalabilityRule <em>Scalability Rule</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Scalability Rule</em>' reference.
	 * @see #getScalabilityRule()
	 * @generated
	 */
	void setScalabilityRule(ScalabilityRule value);

	/**
	 * Returns the value of the '<em><b>Event Instances</b></em>' reference list.
	 * The list contents are of type {@link camel.scalability.EventInstance}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Event Instances</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Event Instances</em>' reference list.
	 * @see camel.execution.ExecutionPackage#getRuleTrigger_EventInstances()
	 * @model required="true"
	 * @generated
	 */
	EList<EventInstance> getEventInstances();

	/**
	 * Returns the value of the '<em><b>Action Realizations</b></em>' reference list.
	 * The list contents are of type {@link camel.execution.ActionRealization}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Action Realizations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Action Realizations</em>' reference list.
	 * @see camel.execution.ExecutionPackage#getRuleTrigger_ActionRealizations()
	 * @model required="true"
	 * @generated
	 */
	EList<ActionRealization> getActionRealizations();

	/**
	 * Returns the value of the '<em><b>Fired On</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Fired On</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Fired On</em>' attribute.
	 * @see #setFiredOn(Date)
	 * @see camel.execution.ExecutionPackage#getRuleTrigger_FiredOn()
	 * @model required="true"
	 * @generated
	 */
	Date getFiredOn();

	/**
	 * Sets the value of the '{@link camel.execution.RuleTrigger#getFiredOn <em>Fired On</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Fired On</em>' attribute.
	 * @see #getFiredOn()
	 * @generated
	 */
	void setFiredOn(Date value);

	/**
	 * Returns the value of the '<em><b>Execution Context</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Execution Context</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Execution Context</em>' reference.
	 * @see #setExecutionContext(ExecutionContext)
	 * @see camel.execution.ExecutionPackage#getRuleTrigger_ExecutionContext()
	 * @model required="true"
	 * @generated
	 */
	ExecutionContext getExecutionContext();

	/**
	 * Sets the value of the '{@link camel.execution.RuleTrigger#getExecutionContext <em>Execution Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execution Context</em>' reference.
	 * @see #getExecutionContext()
	 * @generated
	 */
	void setExecutionContext(ExecutionContext value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model annotation="http://www.eclipse.org/emf/2002/GenModel body='Date d = rt.getFiredOn();\n\t\tfor (ActionRealization ar: rt.getActionRealizations()){\n\t\t\tif (ar.getStartedOn().before(d)) return Boolean.FALSE;\n\t\t}\n\t\tExecutionContext ec = rt.getExecutionContext();\n\t\tDate start = ec.getStartTime();\n\t\tDate end = ec.getEndTime();\n\t\tif ((start != null && (d.before(start) || d.equals(start))) || (end != null && end.before(d))) return Boolean.FALSE;\n\t\treturn Boolean.TRUE;'"
	 * @generated
	 */
	boolean checkDate(RuleTrigger rt);

} // RuleTrigger
