/**
 */
package camel.type.impl;

import camel.type.Limit;
import camel.type.Range;
import camel.type.TypeEnum;
import camel.type.TypePackage;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Range</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.type.impl.RangeImpl#getLowerLimit <em>Lower Limit</em>}</li>
 *   <li>{@link camel.type.impl.RangeImpl#getUpperLimit <em>Upper Limit</em>}</li>
 *   <li>{@link camel.type.impl.RangeImpl#getPrimitiveType <em>Primitive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RangeImpl extends ValueTypeImpl implements Range {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RangeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypePackage.Literals.RANGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Limit getLowerLimit() {
		return (Limit)eGet(TypePackage.Literals.RANGE__LOWER_LIMIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLowerLimit(Limit newLowerLimit) {
		eSet(TypePackage.Literals.RANGE__LOWER_LIMIT, newLowerLimit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Limit getUpperLimit() {
		return (Limit)eGet(TypePackage.Literals.RANGE__UPPER_LIMIT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUpperLimit(Limit newUpperLimit) {
		eSet(TypePackage.Literals.RANGE__UPPER_LIMIT, newUpperLimit);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeEnum getPrimitiveType() {
		return (TypeEnum)eGet(TypePackage.Literals.RANGE__PRIMITIVE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimitiveType(TypeEnum newPrimitiveType) {
		eSet(TypePackage.Literals.RANGE__PRIMITIVE_TYPE, newPrimitiveType);
	}

	/**
	 * The cached invocation delegate for the '{@link #checkType(camel.type.Limit, camel.type.TypeEnum, boolean) <em>Check Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #checkType(camel.type.Limit, camel.type.TypeEnum, boolean)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate CHECK_TYPE_LIMIT_TYPE_ENUM_BOOLEAN__EINVOCATION_DELEGATE = ((EOperation.Internal)TypePackage.Literals.RANGE___CHECK_TYPE__LIMIT_TYPEENUM_BOOLEAN).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkType(Limit l, TypeEnum type, boolean lower) {
		try {
			return (Boolean)CHECK_TYPE_LIMIT_TYPE_ENUM_BOOLEAN__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(3, new Object[]{l, type, lower}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #includesValue(double) <em>Includes Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #includesValue(double)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate INCLUDES_VALUE_DOUBLE__EINVOCATION_DELEGATE = ((EOperation.Internal)TypePackage.Literals.RANGE___INCLUDES_VALUE__DOUBLE).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean includesValue(double n) {
		try {
			return (Boolean)INCLUDES_VALUE_DOUBLE__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{n}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypePackage.RANGE___CHECK_TYPE__LIMIT_TYPEENUM_BOOLEAN:
				return checkType((Limit)arguments.get(0), (TypeEnum)arguments.get(1), (Boolean)arguments.get(2));
			case TypePackage.RANGE___INCLUDES_VALUE__DOUBLE:
				return includesValue((Double)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //RangeImpl
