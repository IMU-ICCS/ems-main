/**
 */
package camel.type;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.type.Range#getLowerLimit <em>Lower Limit</em>}</li>
 *   <li>{@link camel.type.Range#getUpperLimit <em>Upper Limit</em>}</li>
 *   <li>{@link camel.type.Range#getPrimitiveType <em>Primitive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.type.TypePackage#getRange()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='correct_range_type enforce_correct_range_type Range_low_less_than_upper Range_Infs_At_Appropriate_Pos'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot correct_range_type='\n\t\t\t\t\t\t(primitiveType = TypeEnum::IntType) or (primitiveType = TypeEnum::FloatType) or (primitiveType = TypeEnum::DoubleType)' enforce_correct_range_type='\n\t\t\t\t\t\tself.checkType(self.lowerLimit, primitiveType, true) and self.checkType(self.upperLimit, primitiveType, false)' Range_low_less_than_upper='\n\t\t\t\t\tif (not(lowerLimit.value.oclIsTypeOf(NegativeInf)) and not(upperLimit.value.oclIsTypeOf(PositiveInf))) then\n\t\t\t\t\t\tif (primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\tif (not(upperLimit.included) and not(lowerLimit.included)) then (upperLimit.value.oclAsType(IntValue).value - lowerLimit.value.oclAsType(IntValue).value.oclAsType(ecore::EInt)) >= 2 \n\t\t\t\t\t\t\telse if (upperLimit.included) then lowerLimit.value.oclAsType(IntValue).value.oclAsType(ecore::EInt) <= upperLimit.value.oclAsType(IntValue).value\n\t\t\t\t\t\t\t\t else lowerLimit.value.oclAsType(IntValue).value.oclAsType(ecore::EInt) < upperLimit.value.oclAsType(IntValue).value\n\t\t\t\t\t\t\t\t endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\telse (\n\t\t\t\t\t\t\tif (primitiveType = TypeEnum::FloatType) then \n\t\t\t\t\t\t\t\tif (upperLimit.included) then lowerLimit.value.oclAsType(FloatValue).value.oclAsType(ecore::EFloat) <= upperLimit.value.oclAsType(FloatValue).value\n\t\t\t\t\t\t\t\telse lowerLimit.value.oclAsType(FloatValue).value.oclAsType(ecore::EFloat) < upperLimit.value.oclAsType(FloatValue).value\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (upperLimit.included) then lowerLimit.value.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble) <= upperLimit.value.oclAsType(DoubleValue).value\n\t\t\t\t\t\t\t\telse lowerLimit.value.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble) < upperLimit.value.oclAsType(DoubleValue).value\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t) \t\n\t\t\t\t\t\tendif\n\t\t\t\t\telse true\n\t\t\t\t\tendif' Range_Infs_At_Appropriate_Pos='\n\t\t\t\t\tnot(lowerLimit.value.oclIsTypeOf(PositiveInf) or upperLimit.value.oclIsTypeOf(NegativeInf))'"
 * @generated
 */
public interface Range extends ValueType {
	/**
	 * Returns the value of the '<em><b>Lower Limit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Lower Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Lower Limit</em>' containment reference.
	 * @see #setLowerLimit(Limit)
	 * @see camel.type.TypePackage#getRange_LowerLimit()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Limit getLowerLimit();

	/**
	 * Sets the value of the '{@link camel.type.Range#getLowerLimit <em>Lower Limit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Lower Limit</em>' containment reference.
	 * @see #getLowerLimit()
	 * @generated
	 */
	void setLowerLimit(Limit value);

	/**
	 * Returns the value of the '<em><b>Upper Limit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Upper Limit</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Upper Limit</em>' containment reference.
	 * @see #setUpperLimit(Limit)
	 * @see camel.type.TypePackage#getRange_UpperLimit()
	 * @model containment="true" required="true"
	 * @generated
	 */
	Limit getUpperLimit();

	/**
	 * Sets the value of the '{@link camel.type.Range#getUpperLimit <em>Upper Limit</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Upper Limit</em>' containment reference.
	 * @see #getUpperLimit()
	 * @generated
	 */
	void setUpperLimit(Limit value);

	/**
	 * Returns the value of the '<em><b>Primitive Type</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.type.TypeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Primitive Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Primitive Type</em>' attribute.
	 * @see camel.type.TypeEnum
	 * @see #setPrimitiveType(TypeEnum)
	 * @see camel.type.TypePackage#getRange_PrimitiveType()
	 * @model required="true"
	 * @generated
	 */
	TypeEnum getPrimitiveType();

	/**
	 * Sets the value of the '{@link camel.type.Range#getPrimitiveType <em>Primitive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primitive Type</em>' attribute.
	 * @see camel.type.TypeEnum
	 * @see #getPrimitiveType()
	 * @generated
	 */
	void setPrimitiveType(TypeEnum value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model lRequired="true" typeRequired="true" lowerRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\tif (type = TypeEnum::IntType) then\n\t\t\t\t\t\t\tif (lower and not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then lowerLimit.value.oclIsTypeOf(IntValue)\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (not(lower) and not(upperLimit.value.oclIsTypeOf(PositiveInf))) then upperLimit.value.oclIsTypeOf(IntValue) else true endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\telse\n\t\t\t\t\t\t\tif (type = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\tif (lower and not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then lowerLimit.value.oclIsTypeOf(FloatValue)\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (not(lower) and not(upperLimit.value.oclIsTypeOf(PositiveInf))) then upperLimit.value.oclIsTypeOf(FloatValue) else true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (lower and not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then lowerLimit.value.oclIsTypeOf(DoubleValue)\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (not(lower) and not(upperLimit.value.oclIsTypeOf(PositiveInf))) then upperLimit.value.oclIsTypeOf(DoubleValue) else true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean checkType(Limit l, TypeEnum type, boolean lower);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model nRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\tif (primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\tif (not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then ((lowerLimit.included and lowerLimit.value.oclAsType(IntValue).value <= n) or (not(lowerLimit.included) and lowerLimit.value.oclAsType(IntValue).value < n)) and \n\t\t\t\t\t\t\t\tif (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(IntValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(IntValue).value > n)\n\t\t\t\t\t\t\t\telse true endif \n\t\t\t\t\t\t\telse if (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(IntValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(IntValue).value > n)\n\t\t\t\t\t\t\t\t else true endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\telse\n\t\t\t\t\t\t\tif (primitiveType = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\tif (not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then ((lowerLimit.included and lowerLimit.value.oclAsType(FloatValue).value <= n) or (not(lowerLimit.included) and lowerLimit.value.oclAsType(FloatValue).value < n)) and \n\t\t\t\t\t\t\t\t\tif (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(FloatValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(FloatValue).value > n)\n\t\t\t\t\t\t\t\t\telse true endif \n\t\t\t\t\t\t\t\telse if (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(FloatValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(FloatValue).value > n)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then ((lowerLimit.included and lowerLimit.value.oclAsType(DoubleValue).value <= n) or (not(lowerLimit.included) and lowerLimit.value.oclAsType(DoubleValue).value < n)) and \n\t\t\t\t\t\t\t\t\tif (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(DoubleValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(DoubleValue).value > n)\n\t\t\t\t\t\t\t\t\telse true endif \n\t\t\t\t\t\t\t\telse if (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(DoubleValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(DoubleValue).value > n)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean includesValue(double n);

} // Range
