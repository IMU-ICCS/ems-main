/**
 */
package application.impl;

import application.ApplicationPackage;
import application.VirtualMachine;
import application.VirtualMachineProfile;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.impl.PaaSageCPElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Virtual Machine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.VirtualMachineImpl#getId <em>Id</em>}</li>
 *   <li>{@link application.impl.VirtualMachineImpl#getProfile <em>Profile</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VirtualMachineImpl extends PaaSageCPElementImpl implements VirtualMachine {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VirtualMachineImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.VIRTUAL_MACHINE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return (String)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualMachineProfile getProfile() {
		return (VirtualMachineProfile)eGet(ApplicationPackage.Literals.VIRTUAL_MACHINE__PROFILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProfile(VirtualMachineProfile newProfile) {
		eSet(ApplicationPackage.Literals.VIRTUAL_MACHINE__PROFILE, newProfile);
	}

} //VirtualMachineImpl
