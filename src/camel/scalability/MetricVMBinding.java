/**
 */
package camel.scalability;

import camel.deployment.VM;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric VM Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricVMBinding#getVm <em>Vm</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricVMBinding()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='MetricVMBinding_vm_in_dep_model_of_app'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot MetricVMBinding_vm_in_dep_model_of_app='\n\t\t\t\t\t\tapplication.deploymentModels->exists(d | d.components->includes(vm))'"
 * @generated
 */
public interface MetricVMBinding extends MetricObjectBinding {
	/**
	 * Returns the value of the '<em><b>Vm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm</em>' reference.
	 * @see #setVm(VM)
	 * @see camel.scalability.ScalabilityPackage#getMetricVMBinding_Vm()
	 * @model required="true"
	 * @generated
	 */
	VM getVm();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricVMBinding#getVm <em>Vm</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm</em>' reference.
	 * @see #getVm()
	 * @generated
	 */
	void setVm(VM value);

} // MetricVMBinding
