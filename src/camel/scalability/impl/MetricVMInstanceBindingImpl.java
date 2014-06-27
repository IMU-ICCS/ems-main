/**
 */
package camel.scalability.impl;

import camel.deployment.VMInstance;

import camel.scalability.MetricVMInstanceBinding;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric VM Instance Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricVMInstanceBindingImpl#getVmInstance <em>Vm Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricVMInstanceBindingImpl extends MetricObjectInstanceBindingImpl implements MetricVMInstanceBinding {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricVMInstanceBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC_VM_INSTANCE_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMInstance getVmInstance() {
		return (VMInstance)eGet(ScalabilityPackage.Literals.METRIC_VM_INSTANCE_BINDING__VM_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVmInstance(VMInstance newVmInstance) {
		eSet(ScalabilityPackage.Literals.METRIC_VM_INSTANCE_BINDING__VM_INSTANCE, newVmInstance);
	}

} //MetricVMInstanceBindingImpl
