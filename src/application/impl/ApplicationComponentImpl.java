/**
 */
package application.impl;

import application.ApplicationComponent;
import application.ApplicationPackage;
import application.RequiredFeature;
import application.VirtualMachine;
import application.VirtualMachineProfile;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.ApplicationComponentProfile;
import types.typesPaasage.Location;
import types.typesPaasage.ProviderType;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.ApplicationComponentImpl#getVm <em>Vm</em>}</li>
 *   <li>{@link application.impl.ApplicationComponentImpl#getPreferredLocations <em>Preferred Locations</em>}</li>
 *   <li>{@link application.impl.ApplicationComponentImpl#getRequiredProfile <em>Required Profile</em>}</li>
 *   <li>{@link application.impl.ApplicationComponentImpl#getProfile <em>Profile</em>}</li>
 *   <li>{@link application.impl.ApplicationComponentImpl#getFeatures <em>Features</em>}</li>
 *   <li>{@link application.impl.ApplicationComponentImpl#getRequiredFeatures <em>Required Features</em>}</li>
 *   <li>{@link application.impl.ApplicationComponentImpl#getPreferredProviders <em>Preferred Providers</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ApplicationComponentImpl extends CloudML_ElementImpl implements ApplicationComponent {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ApplicationComponentImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.APPLICATION_COMPONENT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualMachine getVm() {
		return (VirtualMachine)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__VM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVm(VirtualMachine newVm) {
		eSet(ApplicationPackage.Literals.APPLICATION_COMPONENT__VM, newVm);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Location> getPreferredLocations() {
		return (EList<Location>)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__PREFERRED_LOCATIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VirtualMachineProfile getRequiredProfile() {
		return (VirtualMachineProfile)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__REQUIRED_PROFILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredProfile(VirtualMachineProfile newRequiredProfile) {
		eSet(ApplicationPackage.Literals.APPLICATION_COMPONENT__REQUIRED_PROFILE, newRequiredProfile);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ApplicationComponentProfile getProfile() {
		return (ApplicationComponentProfile)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__PROFILE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProfile(ApplicationComponentProfile newProfile) {
		eSet(ApplicationPackage.Literals.APPLICATION_COMPONENT__PROFILE, newProfile);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<String> getFeatures() {
		return (EList<String>)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__FEATURES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RequiredFeature> getRequiredFeatures() {
		return (EList<RequiredFeature>)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__REQUIRED_FEATURES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ProviderType> getPreferredProviders() {
		return (EList<ProviderType>)eGet(ApplicationPackage.Literals.APPLICATION_COMPONENT__PREFERRED_PROVIDERS, true);
	}

} //ApplicationComponentImpl
