/**
 */
package types.typesPaasage;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Paa Sage CP Element</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link types.typesPaasage.PaaSageCPElement#getTypeId <em>Type Id</em>}</li>
 * </ul>
 * </p>
 *
 * @see types.typesPaasage.TypesPaasagePackage#getPaaSageCPElement()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface PaaSageCPElement extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Type Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type Id</em>' attribute.
	 * @see #setTypeId(int)
	 * @see types.typesPaasage.TypesPaasagePackage#getPaaSageCPElement_TypeId()
	 * @model id="true" required="true"
	 * @generated
	 */
	int getTypeId();

	/**
	 * Sets the value of the '{@link types.typesPaasage.PaaSageCPElement#getTypeId <em>Type Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type Id</em>' attribute.
	 * @see #getTypeId()
	 * @generated
	 */
	void setTypeId(int value);

} // PaaSageCPElement
