/**
 */
package camel.organisation.impl;

import camel.organisation.Location;
import camel.organisation.OrganisationPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Location</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.organisation.impl.LocationImpl#getName <em>Name</em>}</li>
 *   <li>{@link camel.organisation.impl.LocationImpl#getCity <em>City</em>}</li>
 *   <li>{@link camel.organisation.impl.LocationImpl#getCountry <em>Country</em>}</li>
 *   <li>{@link camel.organisation.impl.LocationImpl#getCountryCode <em>Country Code</em>}</li>
 *   <li>{@link camel.organisation.impl.LocationImpl#getLatitude <em>Latitude</em>}</li>
 *   <li>{@link camel.organisation.impl.LocationImpl#getLongitude <em>Longitude</em>}</li>
 *   <li>{@link camel.organisation.impl.LocationImpl#getAddress <em>Address</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LocationImpl extends CDOObjectImpl implements Location {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected LocationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrganisationPackage.Literals.LOCATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(OrganisationPackage.Literals.LOCATION__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(OrganisationPackage.Literals.LOCATION__NAME, newName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCity() {
		return (String)eGet(OrganisationPackage.Literals.LOCATION__CITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCity(String newCity) {
		eSet(OrganisationPackage.Literals.LOCATION__CITY, newCity);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCountry() {
		return (String)eGet(OrganisationPackage.Literals.LOCATION__COUNTRY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCountry(String newCountry) {
		eSet(OrganisationPackage.Literals.LOCATION__COUNTRY, newCountry);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCountryCode() {
		return (String)eGet(OrganisationPackage.Literals.LOCATION__COUNTRY_CODE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCountryCode(String newCountryCode) {
		eSet(OrganisationPackage.Literals.LOCATION__COUNTRY_CODE, newCountryCode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getLatitude() {
		return (Double)eGet(OrganisationPackage.Literals.LOCATION__LATITUDE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLatitude(double newLatitude) {
		eSet(OrganisationPackage.Literals.LOCATION__LATITUDE, newLatitude);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getLongitude() {
		return (Double)eGet(OrganisationPackage.Literals.LOCATION__LONGITUDE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLongitude(double newLongitude) {
		eSet(OrganisationPackage.Literals.LOCATION__LONGITUDE, newLongitude);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAddress() {
		return (String)eGet(OrganisationPackage.Literals.LOCATION__ADDRESS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddress(String newAddress) {
		eSet(OrganisationPackage.Literals.LOCATION__ADDRESS, newAddress);
	}

} //LocationImpl
