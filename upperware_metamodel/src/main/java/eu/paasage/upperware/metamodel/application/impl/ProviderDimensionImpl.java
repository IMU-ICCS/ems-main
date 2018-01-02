/**
 */
package eu.paasage.upperware.metamodel.application.impl;

import eu.paasage.upperware.metamodel.application.ApplicationPackage;
import eu.paasage.upperware.metamodel.application.Provider;
import eu.paasage.upperware.metamodel.application.ProviderDimension;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Provider Dimension</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl#getValue <em>Value</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl#getProvider <em>Provider</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.application.impl.ProviderDimensionImpl#getMetricID <em>Metric ID</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ProviderDimensionImpl extends CDOObjectImpl implements ProviderDimension {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProviderDimensionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ApplicationPackage.Literals.PROVIDER_DIMENSION;
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
	public double getValue() {
		return (Double)eGet(ApplicationPackage.Literals.PROVIDER_DIMENSION__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(double newValue) {
		eSet(ApplicationPackage.Literals.PROVIDER_DIMENSION__VALUE, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Provider getProvider() {
		return (Provider)eGet(ApplicationPackage.Literals.PROVIDER_DIMENSION__PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvider(Provider newProvider) {
		eSet(ApplicationPackage.Literals.PROVIDER_DIMENSION__PROVIDER, newProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getMetricID() {
		return (String)eGet(ApplicationPackage.Literals.PROVIDER_DIMENSION__METRIC_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetricID(String newMetricID) {
		eSet(ApplicationPackage.Literals.PROVIDER_DIMENSION__METRIC_ID, newMetricID);
	}

} //ProviderDimensionImpl
