/**
 */
package application.impl;

import application.ApplicationPackage;
import application.Provider;

import org.eclipse.emf.ecore.EClass;

import types.typesPaasage.Location;
import types.typesPaasage.ProviderType;

import types.typesPaasage.impl.PaaSageCPElementImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Provider</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link application.impl.ProviderImpl#getId <em>Id</em>}</li>
 *   <li>{@link application.impl.ProviderImpl#getLocation <em>Location</em>}</li>
 *   <li>{@link application.impl.ProviderImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProviderImpl extends PaaSageCPElementImpl implements Provider {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProviderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.PROVIDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return (String)eGet(ApplicationPackage.Literals.PROVIDER__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(ApplicationPackage.Literals.PROVIDER__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Location getLocation() {
		return (Location)eGet(ApplicationPackage.Literals.PROVIDER__LOCATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLocation(Location newLocation) {
		eSet(ApplicationPackage.Literals.PROVIDER__LOCATION, newLocation);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderType getType() {
		return (ProviderType)eGet(ApplicationPackage.Literals.PROVIDER__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ProviderType newType) {
		eSet(ApplicationPackage.Literals.PROVIDER__TYPE, newType);
	}

} //ProviderImpl
