/**
 */
package camel.scalability;

import camel.deployment.Component;
import camel.deployment.VM;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Component Binding</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.scalability.MetricComponentBinding#getVm <em>Vm</em>}</li>
 *   <li>{@link camel.scalability.MetricComponentBinding#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.scalability.ScalabilityPackage#getMetricComponentBinding()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='vm_and_sw_comp_connected'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot vm_and_sw_comp_connected='\n\t\t\t\t\tif (component.oclIsTypeOf(camel::deployment::InternalComponent)) then application.deploymentModels->exists(d | d.hostings->exists(c | c.requiredHost.owner=vm and c.providedHost.owner=component))\n\t\t\t\t\telse \n\t\t\t\t\t\tif (component.oclIsTypeOf(camel::deployment::ExternalComponent)) then vm = null\n\t\t\t\t\t\telse false endif\n\t\t\t\t\tendif'"
 * @generated
 */
public interface MetricComponentBinding extends MetricObjectBinding {
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
	 * @see camel.scalability.ScalabilityPackage#getMetricComponentBinding_Vm()
	 * @model
	 * @generated
	 */
	VM getVm();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricComponentBinding#getVm <em>Vm</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm</em>' reference.
	 * @see #getVm()
	 * @generated
	 */
	void setVm(VM value);

	/**
	 * Returns the value of the '<em><b>Component</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Component</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Component</em>' reference.
	 * @see #setComponent(Component)
	 * @see camel.scalability.ScalabilityPackage#getMetricComponentBinding_Component()
	 * @model required="true"
	 * @generated
	 */
	Component getComponent();

	/**
	 * Sets the value of the '{@link camel.scalability.MetricComponentBinding#getComponent <em>Component</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Component</em>' reference.
	 * @see #getComponent()
	 * @generated
	 */
	void setComponent(Component value);

} // MetricComponentBinding
