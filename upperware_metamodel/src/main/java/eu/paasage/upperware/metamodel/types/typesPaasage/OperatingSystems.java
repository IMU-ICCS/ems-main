/**
 */
package eu.paasage.upperware.metamodel.types.typesPaasage;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Operating Systems</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.typesPaasage.OperatingSystems#getOss <em>Oss</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOperatingSystems()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface OperatingSystems extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Oss</b></em>' containment reference list.
	 * The list contents are of type {@link eu.paasage.upperware.metamodel.types.typesPaasage.OS}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Oss</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Oss</em>' containment reference list.
	 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOperatingSystems_Oss()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<OS> getOss();

} // OperatingSystems
