/**
 */
package application.impl;

import application.ApplicationPackage;
import application.CPU;
import application.Memory;
import application.ProviderCost;
import application.Storage;
import application.VirtualMachineProfile;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.OS;
import types.typesPaasage.VMSizeEnum;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Virtual Machine Profile</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.VirtualMachineProfileImpl#getSize <em>Size</em>}</li>
 *   <li>{@link application.impl.VirtualMachineProfileImpl#getMemory <em>Memory</em>}</li>
 *   <li>{@link application.impl.VirtualMachineProfileImpl#getStorage <em>Storage</em>}</li>
 *   <li>{@link application.impl.VirtualMachineProfileImpl#getCpu <em>Cpu</em>}</li>
 *   <li>{@link application.impl.VirtualMachineProfileImpl#getOs <em>Os</em>}</li>
 *   <li>{@link application.impl.VirtualMachineProfileImpl#getProvider <em>Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VirtualMachineProfileImpl extends CloudML_ElementImpl implements VirtualMachineProfile {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VirtualMachineProfileImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMSizeEnum getSize() {
		return (VMSizeEnum)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__SIZE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSize(VMSizeEnum newSize) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__SIZE, newSize);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Memory getMemory() {
		return (Memory)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__MEMORY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMemory(Memory newMemory) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__MEMORY, newMemory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Storage getStorage() {
		return (Storage)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__STORAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStorage(Storage newStorage) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__STORAGE, newStorage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CPU getCpu() {
		return (CPU)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__CPU, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCpu(CPU newCpu) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__CPU, newCpu);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OS getOs() {
		return (OS)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__OS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOs(OS newOs) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__OS, newOs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderCost getProvider() {
		return (ProviderCost)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(ProviderCost newProvider) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__PROVIDER, newProvider);
	}

} //VirtualMachineProfileImpl
