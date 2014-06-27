/**
 */
package camel.organisation.impl;

import camel.organisation.CloudProvider;
import camel.organisation.OrganisationPackage;

import camel.provider.ProviderModel;

import camel.security.SecurityControl;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cloud Provider</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.organisation.impl.CloudProviderImpl#isPublic <em>Public</em>}</li>
 *   <li>{@link camel.organisation.impl.CloudProviderImpl#isSaaS <em>Saa S</em>}</li>
 *   <li>{@link camel.organisation.impl.CloudProviderImpl#isPaaS <em>Paa S</em>}</li>
 *   <li>{@link camel.organisation.impl.CloudProviderImpl#isIaaS <em>Iaa S</em>}</li>
 *   <li>{@link camel.organisation.impl.CloudProviderImpl#getOffersSecControl <em>Offers Sec Control</em>}</li>
 *   <li>{@link camel.organisation.impl.CloudProviderImpl#getProviderModel <em>Provider Model</em>}</li>
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
		return OrganisationPackage.Literals.CLOUD_PROVIDER;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPublic() {
		return (Boolean)eGet(OrganisationPackage.Literals.CLOUD_PROVIDER__PUBLIC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPublic(boolean newPublic) {
		eSet(OrganisationPackage.Literals.CLOUD_PROVIDER__PUBLIC, newPublic);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isSaaS() {
		return (Boolean)eGet(OrganisationPackage.Literals.CLOUD_PROVIDER__SAA_S, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setSaaS(boolean newSaaS) {
		eSet(OrganisationPackage.Literals.CLOUD_PROVIDER__SAA_S, newSaaS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isPaaS() {
		return (Boolean)eGet(OrganisationPackage.Literals.CLOUD_PROVIDER__PAA_S, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPaaS(boolean newPaaS) {
		eSet(OrganisationPackage.Literals.CLOUD_PROVIDER__PAA_S, newPaaS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIaaS() {
		return (Boolean)eGet(OrganisationPackage.Literals.CLOUD_PROVIDER__IAA_S, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIaaS(boolean newIaaS) {
		eSet(OrganisationPackage.Literals.CLOUD_PROVIDER__IAA_S, newIaaS);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<SecurityControl> getOffersSecControl() {
		return (EList<SecurityControl>)eGet(OrganisationPackage.Literals.CLOUD_PROVIDER__OFFERS_SEC_CONTROL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProviderModel getProviderModel() {
		return (ProviderModel)eGet(OrganisationPackage.Literals.CLOUD_PROVIDER__PROVIDER_MODEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProviderModel(ProviderModel newProviderModel) {
		eSet(OrganisationPackage.Literals.CLOUD_PROVIDER__PROVIDER_MODEL, newProviderModel);
	}

} //CloudProviderImpl
