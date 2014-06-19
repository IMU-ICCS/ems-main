/**
 */
package camel.cerif;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Organization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.cerif.Organization#getName <em>Name</em>}</li>
 *   <li>{@link camel.cerif.Organization#getWww <em>Www</em>}</li>
 *   <li>{@link camel.cerif.Organization#getPostalAddress <em>Postal Address</em>}</li>
 *   <li>{@link camel.cerif.Organization#getEmail <em>Email</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.cerif.CerifPackage#getOrganization()
 * @model
 * @generated
 */
public interface Organization extends Entity {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see camel.cerif.CerifPackage#getOrganization_Name()
	 * @model
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.cerif.Organization#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Www</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Www</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Www</em>' attribute.
	 * @see #setWww(String)
	 * @see camel.cerif.CerifPackage#getOrganization_Www()
	 * @model
	 * @generated
	 */
	String getWww();

	/**
	 * Sets the value of the '{@link camel.cerif.Organization#getWww <em>Www</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Www</em>' attribute.
	 * @see #getWww()
	 * @generated
	 */
	void setWww(String value);

	/**
	 * Returns the value of the '<em><b>Postal Address</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Postal Address</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Postal Address</em>' attribute.
	 * @see #setPostalAddress(String)
	 * @see camel.cerif.CerifPackage#getOrganization_PostalAddress()
	 * @model
	 * @generated
	 */
	String getPostalAddress();

	/**
	 * Sets the value of the '{@link camel.cerif.Organization#getPostalAddress <em>Postal Address</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Postal Address</em>' attribute.
	 * @see #getPostalAddress()
	 * @generated
	 */
	void setPostalAddress(String value);

	/**
	 * Returns the value of the '<em><b>Email</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Email</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Email</em>' attribute.
	 * @see #setEmail(String)
	 * @see camel.cerif.CerifPackage#getOrganization_Email()
	 * @model
	 * @generated
	 */
	String getEmail();

	/**
	 * Sets the value of the '{@link camel.cerif.Organization#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 * @generated
	 */
	void setEmail(String value);

} // Organization
