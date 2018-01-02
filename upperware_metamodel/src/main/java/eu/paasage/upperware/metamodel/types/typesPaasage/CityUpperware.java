/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>City Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware#getCountry <em>Country</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getCityUpperware()
 * @model
 * @generated
 */
public interface CityUpperware extends LocationUpperware {
	/**
	 * Returns the value of the '<em><b>Country</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Country</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Country</em>' reference.
	 * @see #setCountry(CountryUpperware)
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getCityUpperware_Country()
	 * @model
	 * @generated
	 */
	CountryUpperware getCountry();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.CityUpperware#getCountry <em>Country</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Country</em>' reference.
	 * @see #getCountry()
	 * @generated
	 */
	void setCountry(CountryUpperware value);

} // CityUpperware
