/**
 */
package types.typesPaasage;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Location</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link types.typesPaasage.Location#getName <em>Name</em>}</li>
 *   <li>{@link types.typesPaasage.Location#getAlternativeNames <em>Alternative Names</em>}</li>
 * </ul>
 * </p>
 *
 * @see types.typesPaasage.TypesPaasagePackage#getLocation()
 * @model abstract="true"
 * @generated
 */
public interface Location extends PaaSageCPElement {
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
	 * @see types.typesPaasage.TypesPaasagePackage#getLocation_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link types.typesPaasage.Location#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Alternative Names</b></em>' attribute list.
	 * The list contents are of type {@link java.lang.String}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Alternative Names</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Alternative Names</em>' attribute list.
	 * @see types.typesPaasage.TypesPaasagePackage#getLocation_AlternativeNames()
	 * @model
	 * @generated
	 */
	EList<String> getAlternativeNames();

} // Location
