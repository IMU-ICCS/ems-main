/**
 */
package camel.provider.impl;

import camel.provider.Constraint;
import camel.provider.Feature;
import camel.provider.ProviderModel;
import camel.provider.ProviderPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Model</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.provider.impl.ProviderModelImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link camel.provider.impl.ProviderModelImpl#getRootFeature <em>Root Feature</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ProviderModelImpl extends CDOObjectImpl implements ProviderModel {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ProviderModelImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProviderPackage.Literals.PROVIDER_MODEL;
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
	@SuppressWarnings("unchecked")
	public EList<Constraint> getConstraints() {
		return (EList<Constraint>)eGet(ProviderPackage.Literals.PROVIDER_MODEL__CONSTRAINTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Feature getRootFeature() {
		return (Feature)eGet(ProviderPackage.Literals.PROVIDER_MODEL__ROOT_FEATURE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRootFeature(Feature newRootFeature) {
		eSet(ProviderPackage.Literals.PROVIDER_MODEL__ROOT_FEATURE, newRootFeature);
	}

} //ProviderModelImpl
