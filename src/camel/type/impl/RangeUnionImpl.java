/**
 */
package camel.type.impl;

import camel.type.Range;
import camel.type.RangeUnion;
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
 * An implementation of the model object '<em><b>Range Union</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.type.impl.RangeUnionImpl#getRanges <em>Ranges</em>}</li>
 *   <li>{@link camel.type.impl.RangeUnionImpl#getPrimitiveType <em>Primitive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RangeUnionImpl extends ValueTypeImpl implements RangeUnion {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RangeUnionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return TypePackage.Literals.RANGE_UNION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Range> getRanges() {
		return (EList<Range>)eGet(TypePackage.Literals.RANGE_UNION__RANGES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeEnum getPrimitiveType() {
		return (TypeEnum)eGet(TypePackage.Literals.RANGE_UNION__PRIMITIVE_TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPrimitiveType(TypeEnum newPrimitiveType) {
		eSet(TypePackage.Literals.RANGE_UNION__PRIMITIVE_TYPE, newPrimitiveType);
	}

	/**
	 * The cached invocation delegate for the '{@link #includesValue(double) <em>Includes Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #includesValue(double)
	 * @generated
	 * @ordered
	 */
	protected static final EOperation.Internal.InvocationDelegate INCLUDES_VALUE_DOUBLE__EINVOCATION_DELEGATE = ((EOperation.Internal)TypePackage.Literals.RANGE_UNION___INCLUDES_VALUE__DOUBLE).getInvocationDelegate();

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
	public boolean invalidRangeSequence(final RangeUnion ru) {
		EList<Range> ranges = ru.getRanges();
				Range prev = ranges.get(0);
				for (int i = 1; i < ranges.size(); i++){
					Range next = ranges.get(i);
					camel.type.Limit lowerLimit = prev.getUpperLimit();
					camel.type.Limit upperLimit = next.getLowerLimit();
					boolean lowerInclusive = lowerLimit.isIncluded();
					boolean upperInclusive = upperLimit.isIncluded();
					if (!(lowerLimit instanceof camel.type.NegativeInf) && !(upperLimit instanceof camel.type.PositiveInf)){
						double low = 0.0, upper = 0.0;
						//Checking if already at end (positive infinity or next range starts with negative infinity
						camel.type.NumericValue prevVal = lowerLimit.getValue();
						if (prevVal instanceof camel.type.PositiveInf) return Boolean.TRUE;
						camel.type.NumericValue nextVal = upperLimit.getValue();
						if (nextVal instanceof camel.type.NegativeInf) return Boolean.TRUE;
						//Checking now that low is less or equal to upper
						if (prevVal instanceof camel.type.IntValue){
							low = ((camel.type.IntValue)prevVal).getValue();
							if (!lowerInclusive){
								low = low -1;
								lowerInclusive = true;
							}
						}
						else if (prevVal instanceof camel.type.FloatValue) low = ((camel.type.FloatValue)prevVal).getValue();
						else low = ((camel.type.DoubleValue)prevVal).getValue();
						if (nextVal instanceof camel.type.IntValue){
							upper = ((camel.type.IntValue)nextVal).getValue();
							if (!upperInclusive){
								upper = upper + 1;
								upperInclusive = true;
							}
						}
						else if (nextVal instanceof camel.type.FloatValue) upper = ((camel.type.FloatValue)nextVal).getValue();
						else upper = ((camel.type.DoubleValue)nextVal).getValue();
						System.out.println("Low is: " + low + " upper is: " + upper);
						if (low > upper || (low == upper && lowerInclusive == true )) return Boolean.TRUE;
					}
					prev = next;
				}
				return Boolean.FALSE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eInvoke(int operationID, EList<?> arguments) throws InvocationTargetException {
		switch (operationID) {
			case TypePackage.RANGE_UNION___INCLUDES_VALUE__DOUBLE:
				return includesValue((Double)arguments.get(0));
			case TypePackage.RANGE_UNION___INVALID_RANGE_SEQUENCE__RANGEUNION:
				return invalidRangeSequence((RangeUnion)arguments.get(0));
		}
		return super.eInvoke(operationID, arguments);
	}

} //RangeUnionImpl
