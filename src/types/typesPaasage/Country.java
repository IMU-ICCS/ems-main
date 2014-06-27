/**
 */
package types.typesPaasage;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Country</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link types.typesPaasage.Country#getContinent <em>Continent</em>}</li>
 * </ul>
 * </p>
 *
 * @see types.typesPaasage.TypesPaasagePackage#getCountry()
 * @model
 * @generated
 */
public interface Country extends Location {
	/**
	 * Returns the value of the '<em><b>Continent</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Continent</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Continent</em>' reference.
	 * @see #setContinent(Continent)
	 * @see types.typesPaasage.TypesPaasagePackage#getCountry_Continent()
	 * @model
	 * @generated
	 */
	Continent getContinent();

	/**
	 * Sets the value of the '{@link types.typesPaasage.Country#getContinent <em>Continent</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Continent</em>' reference.
	 * @see #getContinent()
	 * @generated
	 */
	void setContinent(Continent value);

} // Country
