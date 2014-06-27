/**
 */
package camel.scalability.impl;

import camel.deployment.Component;
import camel.deployment.VM;

import camel.scalability.MetricComponentBinding;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric Component Binding</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricComponentBindingImpl#getVm <em>Vm</em>}</li>
 *   <li>{@link camel.scalability.impl.MetricComponentBindingImpl#getComponent <em>Component</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricComponentBindingImpl extends MetricObjectBindingImpl implements MetricComponentBinding {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricComponentBindingImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC_COMPONENT_BINDING;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VM getVm() {
		return (VM)eGet(ScalabilityPackage.Literals.METRIC_COMPONENT_BINDING__VM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVm(VM newVm) {
		eSet(ScalabilityPackage.Literals.METRIC_COMPONENT_BINDING__VM, newVm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Component getComponent() {
		return (Component)eGet(ScalabilityPackage.Literals.METRIC_COMPONENT_BINDING__COMPONENT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponent(Component newComponent) {
		eSet(ScalabilityPackage.Literals.METRIC_COMPONENT_BINDING__COMPONENT, newComponent);
	}

} //MetricComponentBindingImpl
