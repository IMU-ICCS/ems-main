/**
 */
package camel.sla;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Term Tree Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.TermTreeType#getAll <em>All</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getTermTreeType()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface TermTreeType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>All</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>All</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>All</em>' containment reference.
	 * @see #setAll(TermCompositorType)
	 * @see camel.sla.SlaPackage#getTermTreeType_All()
	 * @model containment="true"
	 * @generated
	 */
	TermCompositorType getAll();

	/**
	 * Sets the value of the '{@link camel.sla.TermTreeType#getAll <em>All</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>All</em>' containment reference.
	 * @see #getAll()
	 * @generated
	 */
	void setAll(TermCompositorType value);

} // TermTreeType
