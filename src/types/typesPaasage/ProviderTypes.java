/**
 */
package types.typesPaasage;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Provider Types</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link types.typesPaasage.ProviderTypes#getTypes <em>Types</em>}</li>
 * </ul>
 * </p>
 *
 * @see types.typesPaasage.TypesPaasagePackage#getProviderTypes()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ProviderTypes extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link types.typesPaasage.ProviderType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see types.typesPaasage.TypesPaasagePackage#getProviderTypes_Types()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ProviderType> getTypes();

} // ProviderTypes
