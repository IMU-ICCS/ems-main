/**
 */
package camel.provider.impl;

import camel.provider.Functional;
import camel.provider.Operator;
import camel.provider.ProviderPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Functional</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.provider.impl.FunctionalImpl#getType <em>Type</em>}</li>
 *   <li>{@link camel.provider.impl.FunctionalImpl#getOrder <em>Order</em>}</li>
 *   <li>{@link camel.provider.impl.FunctionalImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class FunctionalImpl extends RequiresImpl implements Functional {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected FunctionalImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ProviderPackage.Literals.FUNCTIONAL;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Operator getType() {
		return (Operator)eGet(ProviderPackage.Literals.FUNCTIONAL__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(Operator newType) {
		eSet(ProviderPackage.Literals.FUNCTIONAL__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getOrder() {
		return (Integer)eGet(ProviderPackage.Literals.FUNCTIONAL__ORDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOrder(int newOrder) {
		eSet(ProviderPackage.Literals.FUNCTIONAL__ORDER, newOrder);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
		return (Integer)eGet(ProviderPackage.Literals.FUNCTIONAL__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(int newValue) {
		eSet(ProviderPackage.Literals.FUNCTIONAL__VALUE, newValue);
	}

} //FunctionalImpl
