/**
 */
package camel.scalability.impl;

import camel.deployment.ComponentInstance;
import camel.deployment.VMInstance;

import camel.scalability.MetricComponentInstanceBinding;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric Component Instance Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricComponentInstanceBindingImpl#getVmInstance <em>Vm Instance</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricComponentInstanceBindingImpl#getComponentInstance <em>Component Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricComponentInstanceBindingImpl extends MetricObjectInstanceBindingImpl implements MetricComponentInstanceBinding {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricComponentInstanceBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC_COMPONENT_INSTANCE_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMInstance getVmInstance() {
		return (VMInstance)eGet(ScalabilityPackage.Literals.METRIC_COMPONENT_INSTANCE_BINDING__VM_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVmInstance(VMInstance newVmInstance) {
		eSet(ScalabilityPackage.Literals.METRIC_COMPONENT_INSTANCE_BINDING__VM_INSTANCE, newVmInstance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance getComponentInstance() {
		return (ComponentInstance)eGet(ScalabilityPackage.Literals.METRIC_COMPONENT_INSTANCE_BINDING__COMPONENT_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentInstance(ComponentInstance newComponentInstance) {
		eSet(ScalabilityPackage.Literals.METRIC_COMPONENT_INSTANCE_BINDING__COMPONENT_INSTANCE, newComponentInstance);
	}

} //MetricComponentInstanceBindingImpl
