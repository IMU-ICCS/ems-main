/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.MultiRangeDomain;
import eu.paasage.upperware.metamodel.cp.RangeDomain;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Multi Range Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.MultiRangeDomainImpl#getRanges <em>Ranges</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MultiRangeDomainImpl extends NumericDomainImpl implements MultiRangeDomain {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MultiRangeDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.MULTI_RANGE_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<RangeDomain> getRanges() {
		return (EList<RangeDomain>)eGet(CpPackage.Literals.MULTI_RANGE_DOMAIN__RANGES, true);
	}

} //MultiRangeDomainImpl
