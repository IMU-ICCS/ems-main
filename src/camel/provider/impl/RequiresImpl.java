/**
 */
package camel.provider.impl;

import camel.provider.FeatCardinality;
import camel.provider.ProviderPackage;
import camel.provider.Requires;
import camel.provider.Scope;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Requires</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.provider.impl.RequiresImpl#getScopeFrom <em>Scope From</em>}</li>
 *   <li>{@link camel.provider.impl.RequiresImpl#getScopeTo <em>Scope To</em>}</li>
 *   <li>{@link camel.provider.impl.RequiresImpl#getCardFrom <em>Card From</em>}</li>
 *   <li>{@link camel.provider.impl.RequiresImpl#getCardTo <em>Card To</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RequiresImpl extends ConstraintImpl implements Requires {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequiresImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProviderPackage.Literals.REQUIRES;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Scope getScopeFrom() {
		return (Scope)eGet(ProviderPackage.Literals.REQUIRES__SCOPE_FROM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScopeFrom(Scope newScopeFrom) {
		eSet(ProviderPackage.Literals.REQUIRES__SCOPE_FROM, newScopeFrom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Scope getScopeTo() {
		return (Scope)eGet(ProviderPackage.Literals.REQUIRES__SCOPE_TO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setScopeTo(Scope newScopeTo) {
		eSet(ProviderPackage.Literals.REQUIRES__SCOPE_TO, newScopeTo);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatCardinality getCardFrom() {
		return (FeatCardinality)eGet(ProviderPackage.Literals.REQUIRES__CARD_FROM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCardFrom(FeatCardinality newCardFrom) {
		eSet(ProviderPackage.Literals.REQUIRES__CARD_FROM, newCardFrom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FeatCardinality getCardTo() {
		return (FeatCardinality)eGet(ProviderPackage.Literals.REQUIRES__CARD_TO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCardTo(FeatCardinality newCardTo) {
		eSet(ProviderPackage.Literals.REQUIRES__CARD_TO, newCardTo);
	}

} //RequiresImpl
