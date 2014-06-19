/**
 */
package camel.impl;

import camel.CamelPackage;
import camel.CloudIndependentVMInfo;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cloud Independent VM Info</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.impl.CloudIndependentVMInfoImpl#getCpuClass <em>Cpu Class</em>}</li>
 *   <li>{@link camel.impl.CloudIndependentVMInfoImpl#getMemoryClass <em>Memory Class</em>}</li>
 *   <li>{@link camel.impl.CloudIndependentVMInfoImpl#getIoClass <em>Io Class</em>}</li>
 *   <li>{@link camel.impl.CloudIndependentVMInfoImpl#getNetworkClass <em>Network Class</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CloudIndependentVMInfoImpl extends CDOObjectImpl implements CloudIndependentVMInfo {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CloudIndependentVMInfoImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CamelPackage.Literals.CLOUD_INDEPENDENT_VM_INFO;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCpuClass() {
		return (String)eGet(CamelPackage.Literals.CLOUD_INDEPENDENT_VM_INFO__CPU_CLASS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCpuClass(String newCpuClass) {
		eSet(CamelPackage.Literals.CLOUD_INDEPENDENT_VM_INFO__CPU_CLASS, newCpuClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMemoryClass() {
		return (String)eGet(CamelPackage.Literals.CLOUD_INDEPENDENT_VM_INFO__MEMORY_CLASS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemoryClass(String newMemoryClass) {
		eSet(CamelPackage.Literals.CLOUD_INDEPENDENT_VM_INFO__MEMORY_CLASS, newMemoryClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getIoClass() {
		return (String)eGet(CamelPackage.Literals.CLOUD_INDEPENDENT_VM_INFO__IO_CLASS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIoClass(String newIoClass) {
		eSet(CamelPackage.Literals.CLOUD_INDEPENDENT_VM_INFO__IO_CLASS, newIoClass);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getNetworkClass() {
		return (String)eGet(CamelPackage.Literals.CLOUD_INDEPENDENT_VM_INFO__NETWORK_CLASS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNetworkClass(String newNetworkClass) {
		eSet(CamelPackage.Literals.CLOUD_INDEPENDENT_VM_INFO__NETWORK_CLASS, newNetworkClass);
	}

} //CloudIndependentVMInfoImpl
