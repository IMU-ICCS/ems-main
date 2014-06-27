/**
 */
package camel.scalability.impl;

import camel.deployment.VM;

import camel.scalability.MetricVMBinding;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric VM Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricVMBindingImpl#getVm <em>Vm</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricVMBindingImpl extends MetricObjectBindingImpl implements MetricVMBinding {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricVMBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC_VM_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VM getVm() {
		return (VM)eGet(ScalabilityPackage.Literals.METRIC_VM_BINDING__VM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVm(VM newVm) {
		eSet(ScalabilityPackage.Literals.METRIC_VM_BINDING__VM, newVm);
	}

} //MetricVMBindingImpl
