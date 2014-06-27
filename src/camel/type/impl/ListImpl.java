/**
 */
package camel.type.impl;

import camel.type.List;
import camel.type.TypeEnum;
import camel.type.TypePackage;
import camel.type.Value;
import camel.type.ValueType;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.WrappedException;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EOperation;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>List</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.type.impl.ListImpl#getValues <em>Values</em>}</li>
 *   <li>{@link camel.type.impl.ListImpl#getPrimitiveType <em>Primitive Type</em>}</li>
 *   <li>{@link camel.type.impl.ListImpl#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ListImpl extends ValueTypeImpl implements List {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ListImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypePackage.Literals.LIST;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Value> getValues() {
		return (EList<Value>)eGet(TypePackage.Literals.LIST__VALUES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeEnum getPrimitiveType() {
		return (TypeEnum)eGet(TypePackage.Literals.LIST__PRIMITIVE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimitiveType(TypeEnum newPrimitiveType) {
		eSet(TypePackage.Literals.LIST__PRIMITIVE_TYPE, newPrimitiveType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ValueType getType() {
		return (ValueType)eGet(TypePackage.Literals.LIST__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(ValueType newType) {
		eSet(TypePackage.Literals.LIST__TYPE, newType);
	}

	/**
	 * The cached invocation delegate for the '{@link #includesValue(camel.type.Value) <em>Includes Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #includesValue(camel.type.Value)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate INCLUDES_VALUE_VALUE__EINVOCATION_DELEGATE = ((EOperation.Internal)TypePackage.Literals.LIST___INCLUDES_VALUE__VALUE).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean includesValue(Value v) {
		try {
			return (Boolean)INCLUDES_VALUE_VALUE__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{v}));
		}
		catch (InvocationTargetException ite) {
			throw new WrappedException(ite);
		}
	}

	/**
	 * The cached invocation delegate for the '{@link #checkValueType(camel.type.Value) <em>Check Value Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #checkValueType(camel.type.Value)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate CHECK_VALUE_TYPE_VALUE__EINVOCATION_DELEGATE = ((EOperation.Internal)TypePackage.Literals.LIST___CHECK_VALUE_TYPE__VALUE).getInvocationDelegate();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean checkValueType(Value p) {
		try {
			return (Boolean)CHECK_VALUE_TYPE_VALUE__EINVOCATION_DELEGATE.dynamicInvoke(this, new BasicEList.UnmodifiableEList<Object>(1, new Object[]{p}));
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
			case TypePackage.LIST___INCLUDES_VALUE__VALUE:
				return includesValue((Value)arguments.get(0));
			case TypePackage.LIST___CHECK_VALUE_TYPE__VALUE:
				return checkValueType((Value)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //ListImpl
