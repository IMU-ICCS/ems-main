/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.VirtualMachine;
import eu.paasage.upperware.metamodel.application.VirtualMachineProfile;

import eu.paasage.upperware.metamodel.types.typesPaasage.impl.PaaSageCPElementImpl;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Virtual Machine</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineImpl#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.VirtualMachineImpl#getProfile <em>Profile</em>}</li>
 * </ul>
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
