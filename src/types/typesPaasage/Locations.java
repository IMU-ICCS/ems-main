/**
 */
package types.typesPaasage;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Locations</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link types.typesPaasage.Locations#getLocations <em>Locations</em>}</li>
 * </ul>
 * </p>
 *
 * @see types.typesPaasage.TypesPaasagePackage#getLocations()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Locations extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Locations</b></em>' containment reference list.
	 * The list contents are of type {@link types.typesPaasage.Location}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Locations</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Locations</em>' containment reference list.
	 * @see types.typesPaasage.TypesPaasagePackage#getLocations_Locations()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Location> getLocations();

} // Locations
