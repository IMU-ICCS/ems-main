/**
 */
package camel.provider.impl;

import camel.provider.Attribute;
import camel.provider.Clone;
import camel.provider.FeatCardinality;
import camel.provider.Feature;
import camel.provider.ProviderPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Feature</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.provider.impl.FeatureImpl#getAttributes <em>Attributes</em>}</li>
 *   <li>{@link camel.provider.impl.FeatureImpl#getSubFeatures <em>Sub Features</em>}</li>
 *   <li>{@link camel.provider.impl.FeatureImpl#getFeatureCardinality <em>Feature Cardinality</em>}</li>
 *   <li>{@link camel.provider.impl.FeatureImpl#getClones <em>Clones</em>}</li>
 *   <li>{@link camel.provider.impl.FeatureImpl#getName <em>Name</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FeatureImpl extends CDOObjectImpl implements Feature {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FeatureImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProviderPackage.Literals.FEATURE;
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
	public EList<Attribute> getAttributes() {
		return (EList<Attribute>)eGet(ProviderPackage.Literals.FEATURE__ATTRIBUTES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Feature> getSubFeatures() {
		return (EList<Feature>)eGet(ProviderPackage.Literals.FEATURE__SUB_FEATURES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatCardinality getFeatureCardinality() {
		return (FeatCardinality)eGet(ProviderPackage.Literals.FEATURE__FEATURE_CARDINALITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFeatureCardinality(FeatCardinality newFeatureCardinality) {
		eSet(ProviderPackage.Literals.FEATURE__FEATURE_CARDINALITY, newFeatureCardinality);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Clone> getClones() {
		return (EList<Clone>)eGet(ProviderPackage.Literals.FEATURE__CLONES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return (String)eGet(ProviderPackage.Literals.FEATURE__NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		eSet(ProviderPackage.Literals.FEATURE__NAME, newName);
	}

} //FeatureImpl
