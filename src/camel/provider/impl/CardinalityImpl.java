/**
 */
package camel.provider.impl;

import camel.provider.Cardinality;
import camel.provider.ProviderPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Cardinality</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.provider.impl.CardinalityImpl#getCardinalityMin <em>Cardinality Min</em>}</li>
 *   <li>{@link camel.provider.impl.CardinalityImpl#getCardinalityMax <em>Cardinality Max</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class CardinalityImpl extends CDOObjectImpl implements Cardinality {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CardinalityImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProviderPackage.Literals.CARDINALITY;
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
	public int getCardinalityMin() {
		return (Integer)eGet(ProviderPackage.Literals.CARDINALITY__CARDINALITY_MIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCardinalityMin(int newCardinalityMin) {
		eSet(ProviderPackage.Literals.CARDINALITY__CARDINALITY_MIN, newCardinalityMin);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCardinalityMax() {
		return (Integer)eGet(ProviderPackage.Literals.CARDINALITY__CARDINALITY_MAX, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCardinalityMax(int newCardinalityMax) {
		eSet(ProviderPackage.Literals.CARDINALITY__CARDINALITY_MAX, newCardinalityMax);
	}

} //CardinalityImpl
