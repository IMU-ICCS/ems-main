/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.CPU;
import eu.paasage.upperware.metamodel.application.ImageUpperware;
import eu.paasage.upperware.metamodel.application.Memory;
import eu.paasage.upperware.metamodel.application.ProviderDimension;
import eu.paasage.upperware.metamodel.application.Storage;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;

import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.OS;
import eu.paasage.upperware.metamodel.types.typesPaasage.VMSizeEnum;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Virtual Machine Profile</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getSize <em>Size</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getMemory <em>Memory</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getStorage <em>Storage</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getCpu <em>Cpu</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getOs <em>Os</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getProviderDimension <em>Provider Dimension</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getImage <em>Image</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineProfileImpl#getRelatedCloudVMId <em>Related Cloud VM Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class VirtualMachineProfileImpl extends CloudMLElementUpperwareImpl implements VirtualMachineProfile {
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
	@SuppressWarnings("unchecked")
	public EList<ProviderDimension> getProviderDimension() {
		return (EList<ProviderDimension>)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__PROVIDER_DIMENSION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LocationUpperware getLocation() {
		return (LocationUpperware)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(LocationUpperware newLocation) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__LOCATION, newLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImageUpperware getImage() {
		return (ImageUpperware)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__IMAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setImage(ImageUpperware newImage) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__IMAGE, newImage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRelatedCloudVMId() {
		return (String)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__RELATED_CLOUD_VM_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRelatedCloudVMId(String newRelatedCloudVMId) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE_PROFILE__RELATED_CLOUD_VM_ID, newRelatedCloudVMId);
	}

} //VirtualMachineProfileImpl
