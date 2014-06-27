/**
 */
package camel.provider.impl;

import camel.provider.Alternative;
import camel.provider.Feature;
import camel.provider.GroupCardinality;
import camel.provider.ProviderPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Alternative</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.provider.impl.AlternativeImpl#getGroupCardinality <em>Group Cardinality</em>}</li>
 *   <li>{@link camel.provider.impl.AlternativeImpl#getVariants <em>Variants</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AlternativeImpl extends FeatureImpl implements Alternative {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AlternativeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProviderPackage.Literals.ALTERNATIVE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GroupCardinality getGroupCardinality() {
		return (GroupCardinality)eGet(ProviderPackage.Literals.ALTERNATIVE__GROUP_CARDINALITY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGroupCardinality(GroupCardinality newGroupCardinality) {
		eSet(ProviderPackage.Literals.ALTERNATIVE__GROUP_CARDINALITY, newGroupCardinality);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Feature> getVariants() {
		return (EList<Feature>)eGet(ProviderPackage.Literals.ALTERNATIVE__VARIANTS, true);
	}

} //AlternativeImpl
