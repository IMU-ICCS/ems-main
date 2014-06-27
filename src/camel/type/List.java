/**
 */
package camel.type;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>List</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.type.List#getValues <em>Values</em>}</li>
 *   <li>{@link camel.type.List#getPrimitiveType <em>Primitive Type</em>}</li>
 *   <li>{@link camel.type.List#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.type.TypePackage#getList()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='list_must_have_type all_list_values_correct_type'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot list_must_have_type='\n\t\t\t\t\t\t\t\t(primitiveType <> null and type = null) or (type <> null and primitiveType = null)' all_list_values_correct_type='\n\t\t\t\t\t\tvalues->forAll(p | \n\t\t\t\t\t\t\tself.checkValueType(p)\n\t\t\t\t\t\t)'"
 * @generated
 */
public interface List extends ValueType {
	/**
	 * Returns the value of the '<em><b>Values</b></em>' containment reference list.
	 * The list contents are of type {@link camel.type.Value}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Values</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Values</em>' containment reference list.
	 * @see camel.type.TypePackage#getList_Values()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Value> getValues();

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
	 * @see camel.type.TypePackage#getList_PrimitiveType()
	 * @model
	 * @generated
	 */
	TypeEnum getPrimitiveType();

	/**
	 * Sets the value of the '{@link camel.type.List#getPrimitiveType <em>Primitive Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Primitive Type</em>' attribute.
	 * @see camel.type.TypeEnum
	 * @see #getPrimitiveType()
	 * @generated
	 */
	void setPrimitiveType(TypeEnum value);

	/**
	 * Returns the value of the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' reference.
	 * @see #setType(ValueType)
	 * @see camel.type.TypePackage#getList_Type()
	 * @model
	 * @generated
	 */
	ValueType getType();

	/**
	 * Sets the value of the '{@link camel.type.List#getType <em>Type</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' reference.
	 * @see #getType()
	 * @generated
	 */
	void setType(ValueType value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model vRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\tif (type <> null) then\n\t\t\t\t\t\t\tif (type.oclIsTypeOf(Range)) then\n\t\t\t\t\t\t\t\tif (type.oclAsType(Range).primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(IntValue).value = v.oclAsType(IntValue).value)\n\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\tif (type.oclAsType(Range).primitiveType = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(FloatValue).value = v.oclAsType(FloatValue).value)\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(DoubleValue).value = v.oclAsType(DoubleValue).value)\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(RangeUnion)) then\n\t\t\t\t\t\t\t\t\tif (type.oclAsType(RangeUnion).primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(IntValue).value = v.oclAsType(IntValue).value)\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (type.oclAsType(RangeUnion).primitiveType = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(FloatValue).value = v.oclAsType(FloatValue).value)\n\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(DoubleValue).value = v.oclAsType(DoubleValue).value)\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(StringValueType)) then \n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(StringValue).value = v.oclAsType(StringValue).value)\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(BooleanValueType)) then\n\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(BooleanValue).value = v.oclAsType(BooleanValue).value)\n\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\telse\n\t\t\t\t\t\t\tif (primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(IntValue).value = v.oclAsType(IntValue).value)\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(FloatValue).value = v.oclAsType(FloatValue).value)\n\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::DoubleType) then \n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(DoubleValue).value = v.oclAsType(DoubleValue).value)\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::StringType) then\n\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(StringValue).value = v.oclAsType(StringValue).value)\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::BooleanType) then\n\t\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(BooleanValue).value = v.oclAsType(BooleanValue).value)\n\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean includesValue(Value v);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model pRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\tif (type <> null) then \n\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(Range)) then \n\t\t\t\t\t\t\t\t\tif (type.oclAsType(Range).primitiveType = TypeEnum::IntType) then p.oclIsTypeOf(IntValue) and type.oclAsType(Range).includesValue(p.oclAsType(IntValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (type.oclAsType(Range).primitiveType = TypeEnum::FloatType) then p.oclIsTypeOf(FloatValue) and type.oclAsType(Range).includesValue(p.oclAsType(FloatValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\telse  p.oclIsTypeOf(DoubleValue) and type.oclAsType(Range).includesValue(p.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(RangeUnion)) then \n\t\t\t\t\t\t\t\t\t\tif (type.oclAsType(RangeUnion).primitiveType = TypeEnum::IntType) then p.oclIsTypeOf(IntValue) and type.oclAsType(RangeUnion).includesValue(p.oclAsType(IntValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (type.oclAsType(RangeUnion).primitiveType = TypeEnum::FloatType) then p.oclIsTypeOf(FloatValue) and type.oclAsType(RangeUnion).includesValue(p.oclAsType(FloatValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\t\telse p.oclIsTypeOf(DoubleValue) and type.oclAsType(RangeUnion).includesValue(p.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(StringValueType)) then\n\t\t\t\t\t\t\t\t\t\t\tp.oclIsTypeOf(StringValue)\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(BooleanValueType)) then\n\t\t\t\t\t\t\t\t\t\t\t\tp.oclIsTypeOf(BooleanValue)\n\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::IntType) then p.oclIsTypeOf(IntValue)\n\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::StringType) then p.oclIsTypeOf(StringValue)\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::BooleanType) then p.oclIsTypeOf(BooleanValue)\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::FloatType) then p.oclIsTypeOf(FloatValue) \n\t\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::DoubleType) then p.oclIsTypeOf(DoubleValue) \n\t\t\t\t\t\t\t\t\t\t\t\telse true \n\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean checkValueType(Value p);

} // List
