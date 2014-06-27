/**
 */
package cp.impl;

import cp.Constant;
import cp.CpPackage;

import org.eclipse.emf.ecore.EClass;

import types.BasicTypeEnum;
import types.NumericValue;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constant</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.ConstantImpl#getType <em>Type</em>}</li>
 *   <li>{@link cp.impl.ConstantImpl#getValue <em>Value</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ConstantImpl extends NumericExpressionImpl implements Constant {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstantImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.CONSTANT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicTypeEnum getType() {
		return (BasicTypeEnum)eGet(CpPackage.Literals.CONSTANT__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(BasicTypeEnum newType) {
		eSet(CpPackage.Literals.CONSTANT__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValue getValue() {
		return (NumericValue)eGet(CpPackage.Literals.CONSTANT__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(NumericValue newValue) {
		eSet(CpPackage.Literals.CONSTANT__VALUE, newValue);
	}

} //ConstantImpl
