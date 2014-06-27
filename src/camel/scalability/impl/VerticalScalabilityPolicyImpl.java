/**
 */
package camel.scalability.impl;

import camel.deployment.VM;

import camel.scalability.ScalabilityPackage;
import camel.scalability.VerticalScalabilityPolicy;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Vertical Scalability Policy</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.VerticalScalabilityPolicyImpl#getMinCores <em>Min Cores</em>}</li>
 *   <li>{@link camel.scalability.impl.VerticalScalabilityPolicyImpl#getMaxCores <em>Max Cores</em>}</li>
 *   <li>{@link camel.scalability.impl.VerticalScalabilityPolicyImpl#getMinMemory <em>Min Memory</em>}</li>
 *   <li>{@link camel.scalability.impl.VerticalScalabilityPolicyImpl#getMaxMemory <em>Max Memory</em>}</li>
 *   <li>{@link camel.scalability.impl.VerticalScalabilityPolicyImpl#getMinCPU <em>Min CPU</em>}</li>
 *   <li>{@link camel.scalability.impl.VerticalScalabilityPolicyImpl#getMaxCPU <em>Max CPU</em>}</li>
 *   <li>{@link camel.scalability.impl.VerticalScalabilityPolicyImpl#getMinStorage <em>Min Storage</em>}</li>
 *   <li>{@link camel.scalability.impl.VerticalScalabilityPolicyImpl#getMaxStorage <em>Max Storage</em>}</li>
 *   <li>{@link camel.scalability.impl.VerticalScalabilityPolicyImpl#getVm <em>Vm</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VerticalScalabilityPolicyImpl extends ScalabilityPolicyImpl implements VerticalScalabilityPolicy {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VerticalScalabilityPolicyImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinCores() {
		return (Integer)eGet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MIN_CORES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinCores(int newMinCores) {
		eSet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MIN_CORES, newMinCores);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxCores() {
		return (Integer)eGet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MAX_CORES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxCores(int newMaxCores) {
		eSet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MAX_CORES, newMaxCores);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinMemory() {
		return (Integer)eGet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MIN_MEMORY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinMemory(int newMinMemory) {
		eSet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MIN_MEMORY, newMinMemory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxMemory() {
		return (Integer)eGet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MAX_MEMORY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxMemory(int newMaxMemory) {
		eSet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MAX_MEMORY, newMaxMemory);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMinCPU() {
		return (Double)eGet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MIN_CPU, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinCPU(double newMinCPU) {
		eSet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MIN_CPU, newMinCPU);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getMaxCPU() {
		return (Double)eGet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MAX_CPU, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxCPU(double newMaxCPU) {
		eSet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MAX_CPU, newMaxCPU);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMinStorage() {
		return (Integer)eGet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MIN_STORAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMinStorage(int newMinStorage) {
		eSet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MIN_STORAGE, newMinStorage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getMaxStorage() {
		return (Integer)eGet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MAX_STORAGE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMaxStorage(int newMaxStorage) {
		eSet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__MAX_STORAGE, newMaxStorage);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VM getVm() {
		return (VM)eGet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__VM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVm(VM newVm) {
		eSet(ScalabilityPackage.Literals.VERTICAL_SCALABILITY_POLICY__VM, newVm);
	}

} //VerticalScalabilityPolicyImpl
