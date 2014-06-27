/**
 */
package types.typesPaasage;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application Component Profile</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link types.typesPaasage.ApplicationComponentProfile#getName <em>Name</em>}</li>
 *   <li>{@link types.typesPaasage.ApplicationComponentProfile#getVers <em>Vers</em>}</li>
 *   <li>{@link types.typesPaasage.ApplicationComponentProfile#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see types.typesPaasage.TypesPaasagePackage#getApplicationComponentProfile()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface ApplicationComponentProfile extends CDOObject {
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
	 * @see types.typesPaasage.TypesPaasagePackage#getApplicationComponentProfile_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link types.typesPaasage.ApplicationComponentProfile#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Vers</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Vers</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Vers</em>' attribute.
	 * @see #setVers(String)
	 * @see types.typesPaasage.TypesPaasagePackage#getApplicationComponentProfile_Vers()
	 * @model
	 * @generated
	 */
	String getVers();

	/**
	 * Sets the value of the '{@link types.typesPaasage.ApplicationComponentProfile#getVers <em>Vers</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Vers</em>' attribute.
	 * @see #getVers()
	 * @generated
	 */
	void setVers(String value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(ApplicationComponentType)
	 * @see types.typesPaasage.TypesPaasagePackage#getApplicationComponentProfile_Type()
	 * @model required="true"
	 * @generated
	 */
	ApplicationComponentType getType();

	/**
	 * Sets the value of the '{@link types.typesPaasage.ApplicationComponentProfile#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ApplicationComponentType value);

} // ApplicationComponentProfile
