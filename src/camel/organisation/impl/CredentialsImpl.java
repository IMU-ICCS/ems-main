/**
 */
package camel.organisation.impl;

import camel.organisation.CloudProvider;
import camel.organisation.Credentials;
import camel.organisation.OrganisationPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Credentials</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.organisation.impl.CredentialsImpl#getCloudProvider <em>Cloud Provider</em>}</li>
 *   <li>{@link camel.organisation.impl.CredentialsImpl#getSecurityGroup <em>Security Group</em>}</li>
 *   <li>{@link camel.organisation.impl.CredentialsImpl#getPublicSSHKey <em>Public SSH Key</em>}</li>
 *   <li>{@link camel.organisation.impl.CredentialsImpl#getPrivateSSHKey <em>Private SSH Key</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CredentialsImpl extends CDOObjectImpl implements Credentials {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CredentialsImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return OrganisationPackage.Literals.CREDENTIALS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloudProvider getCloudProvider() {
		return (CloudProvider)eGet(OrganisationPackage.Literals.CREDENTIALS__CLOUD_PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCloudProvider(CloudProvider newCloudProvider) {
		eSet(OrganisationPackage.Literals.CREDENTIALS__CLOUD_PROVIDER, newCloudProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getSecurityGroup() {
		return (String)eGet(OrganisationPackage.Literals.CREDENTIALS__SECURITY_GROUP, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSecurityGroup(String newSecurityGroup) {
		eSet(OrganisationPackage.Literals.CREDENTIALS__SECURITY_GROUP, newSecurityGroup);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPublicSSHKey() {
		return (String)eGet(OrganisationPackage.Literals.CREDENTIALS__PUBLIC_SSH_KEY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublicSSHKey(String newPublicSSHKey) {
		eSet(OrganisationPackage.Literals.CREDENTIALS__PUBLIC_SSH_KEY, newPublicSSHKey);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPrivateSSHKey() {
		return (String)eGet(OrganisationPackage.Literals.CREDENTIALS__PRIVATE_SSH_KEY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrivateSSHKey(String newPrivateSSHKey) {
		eSet(OrganisationPackage.Literals.CREDENTIALS__PRIVATE_SSH_KEY, newPrivateSSHKey);
	}

} //CredentialsImpl
