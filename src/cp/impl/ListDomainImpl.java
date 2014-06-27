/**
 */
package cp.impl;

import cp.CpPackage;
import cp.ListDomain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import types.StringValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>List Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.ListDomainImpl#getValues <em>Values</em>}</li>
 *   <li>{@link cp.impl.ListDomainImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ListDomainImpl extends DomainImpl implements ListDomain {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ListDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.LIST_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<StringValue> getValues() {
		return (EList<StringValue>)eGet(CpPackage.Literals.LIST_DOMAIN__VALUES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringValue getValue() {
		return (StringValue)eGet(CpPackage.Literals.LIST_DOMAIN__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(StringValue newValue) {
		eSet(CpPackage.Literals.LIST_DOMAIN__VALUE, newValue);
	}

} //ListDomainImpl
