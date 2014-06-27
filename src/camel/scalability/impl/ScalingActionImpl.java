/**
 */
package camel.scalability.impl;

import camel.deployment.ComponentInstance;
import camel.deployment.HostingInstance;
import camel.deployment.VMInstance;

import camel.impl.ActionImpl;

import camel.scalability.ScalabilityPackage;
import camel.scalability.ScalingAction;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Scaling Action</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getCount <em>Count</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getMemoryUpdate <em>Memory Update</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getCPUUpdate <em>CPU Update</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getCoreUpdate <em>Core Update</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getStorageUpdate <em>Storage Update</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getIoUpdate <em>Io Update</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getNetworkUpdate <em>Network Update</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getVmInstance <em>Vm Instance</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getComponentInstance <em>Component Instance</em>}</li>
 *   <li>{@link camel.scalability.impl.ScalingActionImpl#getContainmentInstance <em>Containment Instance</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ScalingActionImpl extends ActionImpl implements ScalingAction {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ScalingActionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.SCALING_ACTION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCount() {
		return (Integer)eGet(ScalabilityPackage.Literals.SCALING_ACTION__COUNT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCount(int newCount) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__COUNT, newCount);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMemoryUpdate() {
		return (Integer)eGet(ScalabilityPackage.Literals.SCALING_ACTION__MEMORY_UPDATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemoryUpdate(int newMemoryUpdate) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__MEMORY_UPDATE, newMemoryUpdate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getCPUUpdate() {
		return (Double)eGet(ScalabilityPackage.Literals.SCALING_ACTION__CPU_UPDATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCPUUpdate(double newCPUUpdate) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__CPU_UPDATE, newCPUUpdate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCoreUpdate() {
		return (Integer)eGet(ScalabilityPackage.Literals.SCALING_ACTION__CORE_UPDATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCoreUpdate(int newCoreUpdate) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__CORE_UPDATE, newCoreUpdate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getStorageUpdate() {
		return (Integer)eGet(ScalabilityPackage.Literals.SCALING_ACTION__STORAGE_UPDATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStorageUpdate(int newStorageUpdate) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__STORAGE_UPDATE, newStorageUpdate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getIoUpdate() {
		return (Integer)eGet(ScalabilityPackage.Literals.SCALING_ACTION__IO_UPDATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIoUpdate(int newIoUpdate) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__IO_UPDATE, newIoUpdate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getNetworkUpdate() {
		return (Integer)eGet(ScalabilityPackage.Literals.SCALING_ACTION__NETWORK_UPDATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNetworkUpdate(int newNetworkUpdate) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__NETWORK_UPDATE, newNetworkUpdate);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMInstance getVmInstance() {
		return (VMInstance)eGet(ScalabilityPackage.Literals.SCALING_ACTION__VM_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVmInstance(VMInstance newVmInstance) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__VM_INSTANCE, newVmInstance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComponentInstance getComponentInstance() {
		return (ComponentInstance)eGet(ScalabilityPackage.Literals.SCALING_ACTION__COMPONENT_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComponentInstance(ComponentInstance newComponentInstance) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__COMPONENT_INSTANCE, newComponentInstance);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public HostingInstance getContainmentInstance() {
		return (HostingInstance)eGet(ScalabilityPackage.Literals.SCALING_ACTION__CONTAINMENT_INSTANCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setContainmentInstance(HostingInstance newContainmentInstance) {
		eSet(ScalabilityPackage.Literals.SCALING_ACTION__CONTAINMENT_INSTANCE, newContainmentInstance);
	}

} //ScalingActionImpl
