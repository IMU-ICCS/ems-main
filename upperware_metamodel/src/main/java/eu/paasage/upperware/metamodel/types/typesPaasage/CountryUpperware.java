/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Country Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware#getContinent <em>Continent</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getCountryUpperware()
 * @model
 * @generated
 */
public interface CountryUpperware extends LocationUpperware {
	/**
	 * Returns the value of the '<em><b>Continent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Continent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Continent</em>' reference.
	 * @see #setContinent(ContinentUpperware)
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getCountryUpperware_Continent()
	 * @model
	 * @generated
	 */
	ContinentUpperware getContinent();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.types.typesPaasage.CountryUpperware#getContinent <em>Continent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Continent</em>' reference.
	 * @see #getContinent()
	 * @generated
	 */
	void setContinent(ContinentUpperware value);

} // CountryUpperware
