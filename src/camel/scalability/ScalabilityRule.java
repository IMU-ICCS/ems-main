/**
 */
package camel.scalability;

import camel.Action;

import camel.organisation.Entity;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Rule</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.ScalabilityRule#getName <em>Name</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityRule#getRelatedEvent <em>Related Event</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityRule#getMapsToActions <em>Maps To Actions</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityRule#getDefinedBy <em>Defined By</em>}</li>
 *   <li>{@link camel.scalability.ScalabilityRule#getInvariantPolicies <em>Invariant Policies</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getScalabilityRule()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Scal_Rule_Horiz_Policy_Count Scal_Rule_Vert_Policy_Correct_Vals Scal_Rule_No_Conficting_Policies'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Scal_Rule_Horiz_Policy_Count='\n\t\t\t\t\t\tself.mapsToActions->forAll(p | (p.oclIsTypeOf(ScalingAction) and p.oclAsType(ScalingAction).count > 0 and (p.type = camel::ActionType::SCALE_IN or p.type = camel::ActionType::SCALE_OUT and self.invariantPolicies->exists(q | q.oclIsTypeOf(camel::scalability::HorizontalScalabilityPolicy))) implies self.invariantPolicies->forAll(t | \n\t\t\t\t\t\t\tif (t.oclIsTypeOf(camel::scalability::HorizontalScalabilityPolicy)) then p.oclAsType(ScalingAction).count <= (t.oclAsType(camel::scalability::HorizontalScalabilityPolicy).maxInstances-t.oclAsType(camel::scalability::HorizontalScalabilityPolicy).minInstances)\n\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t)))' Scal_Rule_Vert_Policy_Correct_Vals='\n\t\t\t\t\t\tself.mapsToActions->forAll(p | (p.oclIsTypeOf(ScalingAction) and (p.type = camel::ActionType::SCALE_UP or p.type = camel::ActionType::SCALE_DOWN and self.invariantPolicies->exists(t | t.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy))) implies self.invariantPolicies->forAll(q | q.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy) implies \n\t\t\t\t\t\t\t\t\t((p.oclAsType(ScalingAction).coreUpdate > 0) implies (p.oclAsType(ScalingAction).coreUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxCores - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minCores)))\n\t\t\t\t\t\t\t\t\tand ((p.oclAsType(ScalingAction).storageUpdate > 0) implies (p.oclAsType(ScalingAction).storageUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxStorage - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minStorage)))\n\t\t\t\t\t\t\t\t\tand ((p.oclAsType(ScalingAction).CPUUpdate > 0) implies (p.oclAsType(ScalingAction).CPUUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxCPU - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minCPU)))\n\t\t\t\t\t\t\t\t\tand ((p.oclAsType(ScalingAction).memoryUpdate > 0) implies (p.oclAsType(ScalingAction).memoryUpdate <= (q.oclAsType(camel::scalability::VerticalScalabilityPolicy).maxMemory - q.oclAsType(camel::scalability::VerticalScalabilityPolicy).minMemory)))\n\t\t\t\t\t\t)))' Scal_Rule_No_Conficting_Policies='\n\t\t\t\t\t\tself.invariantPolicies->forAll(p1, p2 | \n\t\t\t\t\t\t\tif (p1 <> p2 and p1.oclType() = p2.oclType()) then\n\t\t\t\t\t\t\t\tif (p1.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy)) then p1.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm <> p2.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm\n\t\t\t\t\t\t\t\telse p1.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component <> p2.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse true\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t)'"
 * @extends CDOObject
 * @generated
 */
public interface ScalabilityRule extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see camel.scalability.ScalabilityPackage#getScalabilityRule_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalabilityRule#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Related Event</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Related Event</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Related Event</em>' reference.
	 * @see #setRelatedEvent(Event)
	 * @see camel.scalability.ScalabilityPackage#getScalabilityRule_RelatedEvent()
	 * @model required="true"
	 * @generated
	 */
	Event getRelatedEvent();

	/**
	 * Sets the value of the '{@link camel.scalability.ScalabilityRule#getRelatedEvent <em>Related Event</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Related Event</em>' reference.
	 * @see #getRelatedEvent()
	 * @generated
	 */
	void setRelatedEvent(Event value);

	/**
	 * Returns the value of the '<em><b>Maps To Actions</b></em>' reference list.
	 * The list contents are of type {@link camel.Action}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Maps To Actions</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Maps To Actions</em>' reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityRule_MapsToActions()
	 * @model required="true"
	 * @generated
	 */
	EList<Action> getMapsToActions();

	/**
	 * Returns the value of the '<em><b>Defined By</b></em>' reference list.
	 * The list contents are of type {@link camel.organisation.Entity}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Defined By</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Defined By</em>' reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityRule_DefinedBy()
	 * @model
	 * @generated
	 */
	EList<Entity> getDefinedBy();

	/**
	 * Returns the value of the '<em><b>Invariant Policies</b></em>' reference list.
	 * The list contents are of type {@link camel.scalability.ScalabilityPolicy}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Invariant Policies</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Invariant Policies</em>' reference list.
	 * @see camel.scalability.ScalabilityPackage#getScalabilityRule_InvariantPolicies()
	 * @model
	 * @generated
	 */
	EList<ScalabilityPolicy> getInvariantPolicies();

} // ScalabilityRule
