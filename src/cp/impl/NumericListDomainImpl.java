/**
 */
package cp.impl;

import cp.CpPackage;
import cp.NumericListDomain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import types.NumericValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Numeric List Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.NumericListDomainImpl#getValues <em>Values</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NumericListDomainImpl extends NumericDomainImpl implements NumericListDomain {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NumericListDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.NUMERIC_LIST_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<NumericValue> getValues() {
		return (EList<NumericValue>)eGet(CpPackage.Literals.NUMERIC_LIST_DOMAIN__VALUES, true);
	}

} //NumericListDomainImpl
