/**
 */
package camel.cerif;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Credentials</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.cerif.Credentials#getCloudProvider <em>Cloud Provider</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.cerif.CerifPackage#getCredentials()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface Credentials extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Cloud Provider</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Cloud Provider</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Cloud Provider</em>' reference.
	 * @see #setCloudProvider(CloudProvider)
	 * @see camel.cerif.CerifPackage#getCredentials_CloudProvider()
	 * @model required="true"
	 * @generated
	 */
	CloudProvider getCloudProvider();

	/**
	 * Sets the value of the '{@link camel.cerif.Credentials#getCloudProvider <em>Cloud Provider</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cloud Provider</em>' reference.
	 * @see #getCloudProvider()
	 * @generated
	 */
	void setCloudProvider(CloudProvider value);

} // Credentials
