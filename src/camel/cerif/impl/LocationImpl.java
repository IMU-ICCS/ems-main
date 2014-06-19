/**
 */
package camel.cerif.impl;

import camel.cerif.CerifPackage;
import camel.cerif.Location;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Location</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.LocationImpl#getCity <em>City</em>}</li>
 *   <li>{@link camel.cerif.impl.LocationImpl#getCountry <em>Country</em>}</li>
 *   <li>{@link camel.cerif.impl.LocationImpl#getCountryCode <em>Country Code</em>}</li>
 *   <li>{@link camel.cerif.impl.LocationImpl#getLatitude <em>Latitude</em>}</li>
 *   <li>{@link camel.cerif.impl.LocationImpl#getLongitude <em>Longitude</em>}</li>
 *   <li>{@link camel.cerif.impl.LocationImpl#getAddress <em>Address</em>}</li>
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
		return CerifPackage.Literals.LOCATION;
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
	public String getCity() {
		return (String)eGet(CerifPackage.Literals.LOCATION__CITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCity(String newCity) {
		eSet(CerifPackage.Literals.LOCATION__CITY, newCity);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCountry() {
		return (String)eGet(CerifPackage.Literals.LOCATION__COUNTRY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCountry(String newCountry) {
		eSet(CerifPackage.Literals.LOCATION__COUNTRY, newCountry);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getCountryCode() {
		return (String)eGet(CerifPackage.Literals.LOCATION__COUNTRY_CODE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCountryCode(String newCountryCode) {
		eSet(CerifPackage.Literals.LOCATION__COUNTRY_CODE, newCountryCode);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getLatitude() {
		return (Double)eGet(CerifPackage.Literals.LOCATION__LATITUDE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLatitude(double newLatitude) {
		eSet(CerifPackage.Literals.LOCATION__LATITUDE, newLatitude);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public double getLongitude() {
		return (Double)eGet(CerifPackage.Literals.LOCATION__LONGITUDE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLongitude(double newLongitude) {
		eSet(CerifPackage.Literals.LOCATION__LONGITUDE, newLongitude);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getAddress() {
		return (String)eGet(CerifPackage.Literals.LOCATION__ADDRESS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAddress(String newAddress) {
		eSet(CerifPackage.Literals.LOCATION__ADDRESS, newAddress);
	}

} //LocationImpl
