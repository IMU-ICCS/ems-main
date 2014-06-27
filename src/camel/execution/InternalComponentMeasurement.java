/**
 */
package camel.execution;

import camel.deployment.ComponentInstance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Internal Component Measurement</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.execution.InternalComponentMeasurement#getOfComponentInstance <em>Of Component Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.execution.ExecutionPackage#getInternalComponentMeasurement()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='SCM_metric_refer_to_same_component SCM_included_in_EC'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot SCM_metric_refer_to_same_component='\n\t\t\t\t\t\t\t\tofMetric.objectBinding.oclIsTypeOf(camel::scalability::MetricComponentInstanceBinding) and ofMetric.objectBinding.oclAsType(camel::scalability::MetricComponentInstanceBinding).componentInstance = ofComponentInstance' SCM_included_in_EC='\n\t\t\t\t\t\t\t\tinExecutionContext.involvesDeployment.componentInstances->includes(ofComponentInstance)'"
 * @generated
 */
public interface InternalComponentMeasurement extends Measurement {
	/**
	 * Returns the value of the '<em><b>Of Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Of Component Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Of Component Instance</em>' reference.
	 * @see #setOfComponentInstance(ComponentInstance)
	 * @see camel.execution.ExecutionPackage#getInternalComponentMeasurement_OfComponentInstance()
	 * @model
	 * @generated
	 */
	ComponentInstance getOfComponentInstance();

	/**
	 * Sets the value of the '{@link camel.execution.InternalComponentMeasurement#getOfComponentInstance <em>Of Component Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Of Component Instance</em>' reference.
	 * @see #getOfComponentInstance()
	 * @generated
	 */
	void setOfComponentInstance(ComponentInstance value);

} // InternalComponentMeasurement
