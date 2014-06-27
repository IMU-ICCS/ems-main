/**
 */
package cp.impl;

import cp.CpPackage;
import cp.NumericDomain;

import org.eclipse.emf.ecore.EClass;

import types.BasicTypeEnum;
import types.NumericValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Numeric Domain</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.NumericDomainImpl#getType <em>Type</em>}</li>
 *   <li>{@link cp.impl.NumericDomainImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class NumericDomainImpl extends DomainImpl implements NumericDomain {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected NumericDomainImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.NUMERIC_DOMAIN;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicTypeEnum getType() {
		return (BasicTypeEnum)eGet(CpPackage.Literals.NUMERIC_DOMAIN__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(BasicTypeEnum newType) {
		eSet(CpPackage.Literals.NUMERIC_DOMAIN__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValue getValue() {
		return (NumericValue)eGet(CpPackage.Literals.NUMERIC_DOMAIN__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(NumericValue newValue) {
		eSet(CpPackage.Literals.NUMERIC_DOMAIN__VALUE, newValue);
	}

} //NumericDomainImpl
