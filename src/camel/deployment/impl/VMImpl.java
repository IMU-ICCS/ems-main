/**
 */
package camel.deployment.impl;

import camel.StorageUnit;
import camel.VMType;

import camel.deployment.DeploymentPackage;
import camel.deployment.VM;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>VM</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.VMImpl#getMinRam <em>Min Ram</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getMaxRam <em>Max Ram</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getRamUnit <em>Ram Unit</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getMinCores <em>Min Cores</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getMaxCores <em>Max Cores</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getMinStorage <em>Min Storage</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getMaxStorage <em>Max Storage</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getStorageUnit <em>Storage Unit</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getMinCPU <em>Min CPU</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getMaxCPU <em>Max CPU</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getOs <em>Os</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#isIs64os <em>Is64os</em>}</li>
 *   <li>{@link camel.deployment.impl.VMImpl#getVmType <em>Vm Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VMImpl extends ExternalComponentImpl implements VM {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VMImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.VM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinRam() {
		return (Integer)eGet(DeploymentPackage.Literals.VM__MIN_RAM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinRam(int newMinRam) {
		eSet(DeploymentPackage.Literals.VM__MIN_RAM, newMinRam);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxRam() {
		return (Integer)eGet(DeploymentPackage.Literals.VM__MAX_RAM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxRam(int newMaxRam) {
		eSet(DeploymentPackage.Literals.VM__MAX_RAM, newMaxRam);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StorageUnit getRamUnit() {
		return (StorageUnit)eGet(DeploymentPackage.Literals.VM__RAM_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRamUnit(StorageUnit newRamUnit) {
		eSet(DeploymentPackage.Literals.VM__RAM_UNIT, newRamUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinCores() {
		return (Integer)eGet(DeploymentPackage.Literals.VM__MIN_CORES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinCores(int newMinCores) {
		eSet(DeploymentPackage.Literals.VM__MIN_CORES, newMinCores);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxCores() {
		return (Integer)eGet(DeploymentPackage.Literals.VM__MAX_CORES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxCores(int newMaxCores) {
		eSet(DeploymentPackage.Literals.VM__MAX_CORES, newMaxCores);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinStorage() {
		return (Integer)eGet(DeploymentPackage.Literals.VM__MIN_STORAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinStorage(int newMinStorage) {
		eSet(DeploymentPackage.Literals.VM__MIN_STORAGE, newMinStorage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxStorage() {
		return (Integer)eGet(DeploymentPackage.Literals.VM__MAX_STORAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxStorage(int newMaxStorage) {
		eSet(DeploymentPackage.Literals.VM__MAX_STORAGE, newMaxStorage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StorageUnit getStorageUnit() {
		return (StorageUnit)eGet(DeploymentPackage.Literals.VM__STORAGE_UNIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStorageUnit(StorageUnit newStorageUnit) {
		eSet(DeploymentPackage.Literals.VM__STORAGE_UNIT, newStorageUnit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMinCPU() {
		return (Double)eGet(DeploymentPackage.Literals.VM__MIN_CPU, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinCPU(double newMinCPU) {
		eSet(DeploymentPackage.Literals.VM__MIN_CPU, newMinCPU);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMaxCPU() {
		return (Double)eGet(DeploymentPackage.Literals.VM__MAX_CPU, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxCPU(double newMaxCPU) {
		eSet(DeploymentPackage.Literals.VM__MAX_CPU, newMaxCPU);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getOs() {
		return (String)eGet(DeploymentPackage.Literals.VM__OS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOs(String newOs) {
		eSet(DeploymentPackage.Literals.VM__OS, newOs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIs64os() {
		return (Boolean)eGet(DeploymentPackage.Literals.VM__IS64OS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIs64os(boolean newIs64os) {
		eSet(DeploymentPackage.Literals.VM__IS64OS, newIs64os);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMType getVmType() {
		return (VMType)eGet(DeploymentPackage.Literals.VM__VM_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVmType(VMType newVmType) {
		eSet(DeploymentPackage.Literals.VM__VM_TYPE, newVmType);
	}

} //VMImpl
