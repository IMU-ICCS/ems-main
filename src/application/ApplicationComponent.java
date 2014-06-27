/**
 */
package application;

import org.eclipse.emf.common.util.EList;

import types.typesPaasage.ApplicationComponentProfile;
import types.typesPaasage.Location;
import types.typesPaasage.ProviderType;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link application.ApplicationComponent#getVm <em>Vm</em>}</li>
 *   <li>{@link application.ApplicationComponent#getPreferredLocations <em>Preferred Locations</em>}</li>
 *   <li>{@link application.ApplicationComponent#getRequiredProfile <em>Required Profile</em>}</li>
 *   <li>{@link application.ApplicationComponent#getProfile <em>Profile</em>}</li>
 *   <li>{@link application.ApplicationComponent#getFeatures <em>Features</em>}</li>
 *   <li>{@link application.ApplicationComponent#getRequiredFeatures <em>Required Features</em>}</li>
 *   <li>{@link application.ApplicationComponent#getPreferredProviders <em>Preferred Providers</em>}</li>
 * </ul>
 * </p>
 *
 * @see application.ApplicationPackage#getApplicationComponent()
 * @model
 * @generated
 */
public interface ApplicationComponent extends CloudML_Element {
	/**
	 * Returns the value of the '<em><b>Vm</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vm</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vm</em>' reference.
	 * @see #setVm(VirtualMachine)
	 * @see application.ApplicationPackage#getApplicationComponent_Vm()
	 * @model
	 * @generated
	 */
	VirtualMachine getVm();

	/**
	 * Sets the value of the '{@link application.ApplicationComponent#getVm <em>Vm</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm</em>' reference.
	 * @see #getVm()
	 * @generated
	 */
	void setVm(VirtualMachine value);

	/**
	 * Returns the value of the '<em><b>Preferred Locations</b></em>' reference list.
	 * The list contents are of type {@link types.typesPaasage.Location}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preferred Locations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preferred Locations</em>' reference list.
	 * @see application.ApplicationPackage#getApplicationComponent_PreferredLocations()
	 * @model
	 * @generated
	 */
	EList<Location> getPreferredLocations();

	/**
	 * Returns the value of the '<em><b>Required Profile</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Profile</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Profile</em>' reference.
	 * @see #setRequiredProfile(VirtualMachineProfile)
	 * @see application.ApplicationPackage#getApplicationComponent_RequiredProfile()
	 * @model
	 * @generated
	 */
	VirtualMachineProfile getRequiredProfile();

	/**
	 * Sets the value of the '{@link application.ApplicationComponent#getRequiredProfile <em>Required Profile</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Required Profile</em>' reference.
	 * @see #getRequiredProfile()
	 * @generated
	 */
	void setRequiredProfile(VirtualMachineProfile value);

	/**
	 * Returns the value of the '<em><b>Profile</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Profile</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Profile</em>' reference.
	 * @see #setProfile(ApplicationComponentProfile)
	 * @see application.ApplicationPackage#getApplicationComponent_Profile()
	 * @model required="true"
	 * @generated
	 */
	ApplicationComponentProfile getProfile();

	/**
	 * Sets the value of the '{@link application.ApplicationComponent#getProfile <em>Profile</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Profile</em>' reference.
	 * @see #getProfile()
	 * @generated
	 */
	void setProfile(ApplicationComponentProfile value);

	/**
	 * Returns the value of the '<em><b>Features</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Features</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Features</em>' attribute list.
	 * @see application.ApplicationPackage#getApplicationComponent_Features()
	 * @model
	 * @generated
	 */
	EList<String> getFeatures();

	/**
	 * Returns the value of the '<em><b>Required Features</b></em>' containment reference list.
	 * The list contents are of type {@link application.RequiredFeature}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Features</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Features</em>' containment reference list.
	 * @see application.ApplicationPackage#getApplicationComponent_RequiredFeatures()
	 * @model containment="true"
	 * @generated
	 */
	EList<RequiredFeature> getRequiredFeatures();

	/**
	 * Returns the value of the '<em><b>Preferred Providers</b></em>' reference list.
	 * The list contents are of type {@link types.typesPaasage.ProviderType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preferred Providers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preferred Providers</em>' reference list.
	 * @see application.ApplicationPackage#getApplicationComponent_PreferredProviders()
	 * @model
	 * @generated
	 */
	EList<ProviderType> getPreferredProviders();

} // ApplicationComponent
