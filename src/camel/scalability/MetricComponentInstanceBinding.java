/**
 */
package camel.scalability;

import camel.deployment.ComponentInstance;
import camel.deployment.VMInstance;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Component Instance Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricComponentInstanceBinding#getVmInstance <em>Vm Instance</em>}</li>
 *   <li>{@link camel.scalability.MetricComponentInstanceBinding#getComponentInstance <em>Component Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricComponentInstanceBinding()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='vm_and_sw_comp_connected'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot vm_and_sw_comp_connected='\n\t\t\t\t\tif (componentInstance.oclIsTypeOf(camel::deployment::InternalComponentInstance)) then executionContext.involvesDeployment.hostingInstances->exists(c | c.requiredHostInstance.componentInstance=vmInstance and c.providedHostInstance.componentInstance=componentInstance)\n\t\t\t\t\telse \n\t\t\t\t\t\tif (componentInstance.type.oclIsTypeOf(camel::deployment::ExternalComponent)) then vmInstance = null\n\t\t\t\t\t\telse false endif\n\t\t\t\t\tendif'"
 * @generated
 */
public interface MetricComponentInstanceBinding extends MetricObjectInstanceBinding {
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
	 * @see camel.scalability.ScalabilityPackage#getMetricComponentInstanceBinding_VmInstance()
	 * @model
	 * @generated
	 */
	VMInstance getVmInstance();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricComponentInstanceBinding#getVmInstance <em>Vm Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm Instance</em>' reference.
	 * @see #getVmInstance()
	 * @generated
	 */
	void setVmInstance(VMInstance value);

	/**
	 * Returns the value of the '<em><b>Component Instance</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component Instance</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component Instance</em>' reference.
	 * @see #setComponentInstance(ComponentInstance)
	 * @see camel.scalability.ScalabilityPackage#getMetricComponentInstanceBinding_ComponentInstance()
	 * @model required="true"
	 * @generated
	 */
	ComponentInstance getComponentInstance();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricComponentInstanceBinding#getComponentInstance <em>Component Instance</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component Instance</em>' reference.
	 * @see #getComponentInstance()
	 * @generated
	 */
	void setComponentInstance(ComponentInstance value);

} // MetricComponentInstanceBinding
