/**
 */
package camel.cerif.impl;

import camel.cerif.CerifPackage;
import camel.cerif.CloudProvider;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cloud Provider</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.cerif.impl.CloudProviderImpl#isPublic <em>Public</em>}</li>
 *   <li>{@link camel.cerif.impl.CloudProviderImpl#isSaaS <em>Saa S</em>}</li>
 *   <li>{@link camel.cerif.impl.CloudProviderImpl#isPaaS <em>Paa S</em>}</li>
 *   <li>{@link camel.cerif.impl.CloudProviderImpl#isIaaS <em>Iaa S</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CloudProviderImpl extends OrganizationImpl implements CloudProvider {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CloudProviderImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CerifPackage.Literals.CLOUD_PROVIDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPublic() {
		return (Boolean)eGet(CerifPackage.Literals.CLOUD_PROVIDER__PUBLIC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublic(boolean newPublic) {
		eSet(CerifPackage.Literals.CLOUD_PROVIDER__PUBLIC, newPublic);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSaaS() {
		return (Boolean)eGet(CerifPackage.Literals.CLOUD_PROVIDER__SAA_S, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSaaS(boolean newSaaS) {
		eSet(CerifPackage.Literals.CLOUD_PROVIDER__SAA_S, newSaaS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPaaS() {
		return (Boolean)eGet(CerifPackage.Literals.CLOUD_PROVIDER__PAA_S, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPaaS(boolean newPaaS) {
		eSet(CerifPackage.Literals.CLOUD_PROVIDER__PAA_S, newPaaS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIaaS() {
		return (Boolean)eGet(CerifPackage.Literals.CLOUD_PROVIDER__IAA_S, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIaaS(boolean newIaaS) {
		eSet(CerifPackage.Literals.CLOUD_PROVIDER__IAA_S, newIaaS);
	}

} //CloudProviderImpl
