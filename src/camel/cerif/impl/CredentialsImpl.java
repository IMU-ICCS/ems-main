/**
 */
package camel.cerif.impl;

import camel.cerif.CerifPackage;
import camel.cerif.CloudProvider;
import camel.cerif.Credentials;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Credentials</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.CredentialsImpl#getCloudProvider <em>Cloud Provider</em>}</li>
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
		return CerifPackage.Literals.CREDENTIALS;
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
		return (CloudProvider)eGet(CerifPackage.Literals.CREDENTIALS__CLOUD_PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCloudProvider(CloudProvider newCloudProvider) {
		eSet(CerifPackage.Literals.CREDENTIALS__CLOUD_PROVIDER, newCloudProvider);
	}

} //CredentialsImpl
