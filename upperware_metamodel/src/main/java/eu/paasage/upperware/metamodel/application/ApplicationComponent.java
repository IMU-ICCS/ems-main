/**
 */
package eu.paasage.upperware.metamodel.application;

import eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware;
import eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Component</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getVm <em>Vm</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getPreferredLocations <em>Preferred Locations</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getRequiredProfile <em>Required Profile</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getFeatures <em>Features</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getRequiredFeatures <em>Required Features</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getPreferredProviders <em>Preferred Providers</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getMin <em>Min</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getMax <em>Max</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getApplicationComponent()
 * @model
 * @generated
 */
public interface ApplicationComponent extends CloudMLElementUpperware {
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
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getApplicationComponent_Vm()
	 * @model
	 * @generated
	 */
	VirtualMachine getVm();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getVm <em>Vm</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vm</em>' reference.
	 * @see #getVm()
	 * @generated
	 */
	void setVm(VirtualMachine value);

	/**
	 * Returns the value of the '<em><b>Preferred Locations</b></em>' reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.types.typesPaasage.LocationUpperware}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preferred Locations</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preferred Locations</em>' reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getApplicationComponent_PreferredLocations()
	 * @model
	 * @generated
	 */
	EList<LocationUpperware> getPreferredLocations();

	/**
	 * Returns the value of the '<em><b>Required Profile</b></em>' reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.VirtualMachineProfile}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Profile</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Profile</em>' reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getApplicationComponent_RequiredProfile()
	 * @model
	 * @generated
	 */
	EList<VirtualMachineProfile> getRequiredProfile();

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
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getApplicationComponent_Features()
	 * @model
	 * @generated
	 */
	EList<String> getFeatures();

	/**
	 * Returns the value of the '<em><b>Required Features</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.application.RequiredFeature}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Required Features</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Required Features</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getApplicationComponent_RequiredFeatures()
	 * @model containment="true"
	 * @generated
	 */
	EList<RequiredFeature> getRequiredFeatures();

	/**
	 * Returns the value of the '<em><b>Preferred Providers</b></em>' reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.types.typesPaasage.ProviderType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Preferred Providers</em>' reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Preferred Providers</em>' reference list.
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getApplicationComponent_PreferredProviders()
	 * @model
	 * @generated
	 */
	EList<ProviderType> getPreferredProviders();

	/**
	 * Returns the value of the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Min</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Min</em>' attribute.
	 * @see #setMin(int)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getApplicationComponent_Min()
	 * @model required="true"
	 * @generated
	 */
	int getMin();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getMin <em>Min</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Min</em>' attribute.
	 * @see #getMin()
	 * @generated
	 */
	void setMin(int value);

	/**
	 * Returns the value of the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Max</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Max</em>' attribute.
	 * @see #setMax(int)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getApplicationComponent_Max()
	 * @model required="true"
	 * @generated
	 */
	int getMax();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.ApplicationComponent#getMax <em>Max</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Max</em>' attribute.
	 * @see #getMax()
	 * @generated
	 */
	void setMax(int value);

} // ApplicationComponent
