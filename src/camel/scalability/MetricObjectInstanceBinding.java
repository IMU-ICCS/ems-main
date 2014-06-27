/**
 */
package camel.scalability;

import camel.execution.ExecutionContext;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Object Instance Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricObjectInstanceBinding#getExecutionContext <em>Execution Context</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricObjectInstanceBinding()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface MetricObjectInstanceBinding extends CDOObject {
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
	 * @see camel.scalability.ScalabilityPackage#getMetricObjectInstanceBinding_ExecutionContext()
	 * @model required="true"
	 * @generated
	 */
	ExecutionContext getExecutionContext();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricObjectInstanceBinding#getExecutionContext <em>Execution Context</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Execution Context</em>' reference.
	 * @see #getExecutionContext()
	 * @generated
	 */
	void setExecutionContext(ExecutionContext value);

} // MetricObjectInstanceBinding
