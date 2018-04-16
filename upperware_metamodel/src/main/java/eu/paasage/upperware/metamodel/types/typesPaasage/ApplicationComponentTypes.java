/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Application Component Types</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentTypes#getTypes <em>Types</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getApplicationComponentTypes()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface ApplicationComponentTypes extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Types</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.types.typesPaasage.ApplicationComponentType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Types</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Types</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getApplicationComponentTypes_Types()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<ApplicationComponentType> getTypes();

} // ApplicationComponentTypes
