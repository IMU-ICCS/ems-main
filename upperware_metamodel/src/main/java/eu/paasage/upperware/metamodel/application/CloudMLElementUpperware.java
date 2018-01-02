/**
 */
package eu.paasage.upperware.metamodel.application;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Cloud ML Element Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.CloudMLElementUpperware#getCloudMLId <em>Cloud ML Id</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCloudMLElementUpperware()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface CloudMLElementUpperware extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Cloud ML Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cloud ML Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cloud ML Id</em>' attribute.
	 * @see #setCloudMLId(String)
	 * @see eu.paasage.upperware.metamodel.application.ApplicationPackage#getCloudMLElementUpperware_CloudMLId()
	 * @model
	 * @generated
	 */
	String getCloudMLId();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.application.CloudMLElementUpperware#getCloudMLId <em>Cloud ML Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cloud ML Id</em>' attribute.
	 * @see #getCloudMLId()
	 * @generated
	 */
	void setCloudMLId(String value);

} // CloudMLElementUpperware
