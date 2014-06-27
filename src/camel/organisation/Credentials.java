/**
 */
package camel.organisation;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Credentials</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.organisation.Credentials#getCloudProvider <em>Cloud Provider</em>}</li>
 *   <li>{@link camel.organisation.Credentials#getSecurityGroup <em>Security Group</em>}</li>
 *   <li>{@link camel.organisation.Credentials#getPublicSSHKey <em>Public SSH Key</em>}</li>
 *   <li>{@link camel.organisation.Credentials#getPrivateSSHKey <em>Private SSH Key</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.organisation.OrganisationPackage#getCredentials()
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
	 * @see camel.organisation.OrganisationPackage#getCredentials_CloudProvider()
	 * @model required="true"
	 * @generated
	 */
	CloudProvider getCloudProvider();

	/**
	 * Sets the value of the '{@link camel.organisation.Credentials#getCloudProvider <em>Cloud Provider</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Cloud Provider</em>' reference.
	 * @see #getCloudProvider()
	 * @generated
	 */
	void setCloudProvider(CloudProvider value);

	/**
	 * Returns the value of the '<em><b>Security Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Security Group</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Security Group</em>' attribute.
	 * @see #setSecurityGroup(String)
	 * @see camel.organisation.OrganisationPackage#getCredentials_SecurityGroup()
	 * @model
	 * @generated
	 */
	String getSecurityGroup();

	/**
	 * Sets the value of the '{@link camel.organisation.Credentials#getSecurityGroup <em>Security Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Security Group</em>' attribute.
	 * @see #getSecurityGroup()
	 * @generated
	 */
	void setSecurityGroup(String value);

	/**
	 * Returns the value of the '<em><b>Public SSH Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Public SSH Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Public SSH Key</em>' attribute.
	 * @see #setPublicSSHKey(String)
	 * @see camel.organisation.OrganisationPackage#getCredentials_PublicSSHKey()
	 * @model
	 * @generated
	 */
	String getPublicSSHKey();

	/**
	 * Sets the value of the '{@link camel.organisation.Credentials#getPublicSSHKey <em>Public SSH Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Public SSH Key</em>' attribute.
	 * @see #getPublicSSHKey()
	 * @generated
	 */
	void setPublicSSHKey(String value);

	/**
	 * Returns the value of the '<em><b>Private SSH Key</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Private SSH Key</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Private SSH Key</em>' attribute.
	 * @see #setPrivateSSHKey(String)
	 * @see camel.organisation.OrganisationPackage#getCredentials_PrivateSSHKey()
	 * @model
	 * @generated
	 */
	String getPrivateSSHKey();

	/**
	 * Sets the value of the '{@link camel.organisation.Credentials#getPrivateSSHKey <em>Private SSH Key</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Private SSH Key</em>' attribute.
	 * @see #getPrivateSSHKey()
	 * @generated
	 */
	void setPrivateSSHKey(String value);

} // Credentials
