/**
 */
package cp.impl;

import cp.CpPackage;
import cp.RangeDomain;

import org.eclipse.emf.ecore.EClass;

import types.NumericValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Range Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.RangeDomainImpl#getFrom <em>From</em>}</li>
 *   <li>{@link cp.impl.RangeDomainImpl#getTo <em>To</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RangeDomainImpl extends NumericDomainImpl implements RangeDomain {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RangeDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.RANGE_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValue getFrom() {
		return (NumericValue)eGet(CpPackage.Literals.RANGE_DOMAIN__FROM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrom(NumericValue newFrom) {
		eSet(CpPackage.Literals.RANGE_DOMAIN__FROM, newFrom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValue getTo() {
		return (NumericValue)eGet(CpPackage.Literals.RANGE_DOMAIN__TO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(NumericValue newTo) {
		eSet(CpPackage.Literals.RANGE_DOMAIN__TO, newTo);
	}

} //RangeDomainImpl
