/**
 */
package types.typesPaasage;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application Component Profiles</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link types.typesPaasage.ApplicationComponentProfiles#getProfiles <em>Profiles</em>}</li>
 * </ul>
 * </p>
 *
 * @see types.typesPaasage.TypesPaasagePackage#getApplicationComponentProfiles()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ApplicationComponentProfiles extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Profiles</b></em>' containment reference list.
	 * The list contents are of type {@link types.typesPaasage.ApplicationComponentProfile}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Profiles</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Profiles</em>' containment reference list.
	 * @see types.typesPaasage.TypesPaasagePackage#getApplicationComponentProfiles_Profiles()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ApplicationComponentProfile> getProfiles();

} // ApplicationComponentProfiles
