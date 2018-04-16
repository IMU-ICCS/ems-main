/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.RangeDomain;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Range Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl#getFrom <em>From</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl#getTo <em>To</em>}</li>
 * </ul>
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
	public NumericValueUpperware getFrom() {
		return (NumericValueUpperware)eGet(CpPackage.Literals.RANGE_DOMAIN__FROM, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFrom(NumericValueUpperware newFrom) {
		eSet(CpPackage.Literals.RANGE_DOMAIN__FROM, newFrom);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValueUpperware getTo() {
		return (NumericValueUpperware)eGet(CpPackage.Literals.RANGE_DOMAIN__TO, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTo(NumericValueUpperware newTo) {
		eSet(CpPackage.Literals.RANGE_DOMAIN__TO, newTo);
	}

} //RangeDomainImpl
