/**
 */
package camel.organisation;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Organization</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.organisation.Organization#getName <em>Name</em>}</li>
 *   <li>{@link camel.organisation.Organization#getWww <em>Www</em>}</li>
 *   <li>{@link camel.organisation.Organization#getPostalAddress <em>Postal Address</em>}</li>
 *   <li>{@link camel.organisation.Organization#getEmail <em>Email</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.organisation.OrganisationPackage#getOrganization()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='UniqueName_Email'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot UniqueName_Email='\n\t\t\tOrganization.allInstances()->forAll(p1, p2 |p1 <> p2 implies p1.email <> p2.email )'"
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
	 * @see camel.organisation.OrganisationPackage#getOrganization_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.organisation.Organization#getName <em>Name</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getOrganization_Www()
	 * @model
	 * @generated
	 */
	String getWww();

	/**
	 * Sets the value of the '{@link camel.organisation.Organization#getWww <em>Www</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getOrganization_PostalAddress()
	 * @model
	 * @generated
	 */
	String getPostalAddress();

	/**
	 * Sets the value of the '{@link camel.organisation.Organization#getPostalAddress <em>Postal Address</em>}' attribute.
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
	 * @see camel.organisation.OrganisationPackage#getOrganization_Email()
	 * @model required="true"
	 * @generated
	 */
	String getEmail();

	/**
	 * Sets the value of the '{@link camel.organisation.Organization#getEmail <em>Email</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Email</em>' attribute.
	 * @see #getEmail()
	 * @generated
	 */
	void setEmail(String value);

} // Organization
