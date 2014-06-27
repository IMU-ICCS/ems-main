/**
 */
package cp.impl;

import cp.CpPackage;
import cp.Domain;
import cp.Variable;

import org.eclipse.emf.ecore.EClass;

import types.NumericValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.VariableImpl#getValue <em>Value</em>}</li>
 *   <li>{@link cp.impl.VariableImpl#getDomain <em>Domain</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class VariableImpl extends NumericExpressionImpl implements Variable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected VariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValue getValue() {
		return (NumericValue)eGet(CpPackage.Literals.VARIABLE__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(NumericValue newValue) {
		eSet(CpPackage.Literals.VARIABLE__VALUE, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Domain getDomain() {
		return (Domain)eGet(CpPackage.Literals.VARIABLE__DOMAIN, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDomain(Domain newDomain) {
		eSet(CpPackage.Literals.VARIABLE__DOMAIN, newDomain);
	}

} //VariableImpl
