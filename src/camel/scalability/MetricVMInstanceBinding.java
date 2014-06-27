/**
 */
package camel.scalability;

import camel.deployment.VMInstance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric VM Instance Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricVMInstanceBinding#getVmInstance <em>Vm Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricVMInstanceBinding()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='MetricVMBinding_vm_in_dep_model_of_app'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot MetricVMBinding_vm_in_dep_model_of_app='\n\t\t\t\t\t\texecutionContext.involvesDeployment.componentInstances->includes(vmInstance)'"
 * @generated
 */
public interface MetricVMInstanceBinding extends MetricObjectInstanceBinding {
	/**
	 * Returns the value of the '<em><b>Vm Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm Instance</em>' reference.
	 * @see #setVmInstance(VMInstance)
	 * @see camel.scalability.ScalabilityPackage#getMetricVMInstanceBinding_VmInstance()
	 * @model required="true"
	 * @generated
	 */
	VMInstance getVmInstance();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricVMInstanceBinding#getVmInstance <em>Vm Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm Instance</em>' reference.
	 * @see #getVmInstance()
	 * @generated
	 */
	void setVmInstance(VMInstance value);

} // MetricVMInstanceBinding
