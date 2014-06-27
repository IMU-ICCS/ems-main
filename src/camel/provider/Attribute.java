/**
 */
package camel.provider;

import camel.type.Value;
import camel.type.ValueType;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Attribute</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.provider.Attribute#getName <em>Name</em>}</li>
 *   <li>{@link camel.provider.Attribute#getValue <em>Value</em>}</li>
 *   <li>{@link camel.provider.Attribute#getValueType <em>Value Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.provider.ProviderPackage#getAttribute()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Attribute_must_have_at_least_value_or_valueType Attribute_value_in_value_type'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Attribute_must_have_at_least_value_or_valueType='\n\t\t\t\t\t\tvalue <> null or valueType <> null' Attribute_value_in_value_type='\n\t\t\t\t\t\t(value <> null and valueType <> null) implies self.checkValue(value,false)'"
 * @extends CDOObject
 * @generated
 */
public interface Attribute extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see camel.provider.ProviderPackage#getAttribute_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.provider.Attribute#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value</em>' containment reference.
	 * @see #setValue(Value)
	 * @see camel.provider.ProviderPackage#getAttribute_Value()
	 * @model containment="true"
	 * @generated
	 */
	Value getValue();

	/**
	 * Sets the value of the '{@link camel.provider.Attribute#getValue <em>Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value</em>' containment reference.
	 * @see #getValue()
	 * @generated
	 */
	void setValue(Value value);

	/**
	 * Returns the value of the '<em><b>Value Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Value Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Value Type</em>' containment reference.
	 * @see #setValueType(ValueType)
	 * @see camel.provider.ProviderPackage#getAttribute_ValueType()
	 * @model containment="true"
	 * @generated
	 */
	ValueType getValueType();

	/**
	 * Sets the value of the '{@link camel.provider.Attribute#getValueType <em>Value Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Value Type</em>' containment reference.
	 * @see #getValueType()
	 * @generated
	 */
	void setValueType(ValueType value);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model vRequired="true" diffRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\tif (self.valueType <> null) then \n\t\t\tif (self.valueType.oclIsTypeOf(camel::type::Range)) then\n\t\t\t\tif (v.oclIsTypeOf(camel::type::BooleanValue) or v.oclIsTypeOf(camel::type::StringValue)) then false\n\t\t\t\telse \n\t\t\t\t\tif (v.oclIsTypeOf(camel::type::IntValue)) then self.valueType.oclAsType(camel::type::Range).includesValue(v.oclAsType(camel::type::IntValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\telse \n\t\t\t\t\t\tif (v.oclIsTypeOf(camel::type::FloatValue)) then self.valueType.oclAsType(camel::type::Range).includesValue(v.oclAsType(camel::type::FloatValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\telse self.valueType.oclAsType(camel::type::Range).includesValue(v.oclAsType(camel::type::DoubleValue).value)\n\t\t\t\t\t\tendif\n\t\t\t\t\tendif\n\t\t\t\tendif\n\t\t\telse \n\t\t\t\tif (self.valueType.oclIsTypeOf(camel::type::RangeUnion)) then\t\n\t\t\t\t\tif (v.oclIsTypeOf(camel::type::BooleanValue) or v.oclIsTypeOf(camel::type::StringValue)) then false\n\t\t\t\t\telse \n\t\t\t\t\t\tif (v.oclIsTypeOf(camel::type::IntValue)) then self.valueType.oclAsType(camel::type::RangeUnion).includesValue(v.oclAsType(camel::type::IntValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\telse \n\t\t\t\t\t\t\tif (v.oclIsTypeOf(camel::type::FloatValue)) then self.valueType.oclAsType(camel::type::RangeUnion).includesValue(v.oclAsType(camel::type::FloatValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\telse self.valueType.oclAsType(camel::type::RangeUnion).includesValue(v.oclAsType(camel::type::DoubleValue).value)\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\tendif\n\t\t\t\t\tendif\n\t\t\t\telse \n\t\t\t\t\tif (self.valueType.oclIsTypeOf(camel::type::List)) then (self.valueType.oclAsType(camel::type::List).checkValueType(v) and self.valueType.oclAsType(camel::type::List).includesValue(v))\n\t\t\t\t\telse \n\t\t\t\t\t\tif (self.valueType.oclIsTypeOf(camel::type::Enumeration) and v.oclIsTypeOf(camel::type::StringValue)) then self.valueType.oclAsType(camel::type::Enumeration).includesName(v.oclAsType(camel::type::StringValue).value)\n\t\t\t\t\t\telse \n\t\t\t\t\t\t\tif (self.valueType.oclIsTypeOf(camel::type::Enumeration) and v.oclIsTypeOf(camel::type::EnumerateValue)) then (self.valueType.oclAsType(camel::type::Enumeration).includesName(v.oclAsType(camel::type::EnumerateValue).name) and self.valueType.oclAsType(camel::type::Enumeration).includesValue(v.oclAsType(camel::type::EnumerateValue).value))\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (self.valueType.oclIsTypeOf(camel::type::StringValueType)) then v.oclIsTypeOf(camel::type::StringValue)\n\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\tif (self.valueType.oclIsTypeOf(camel::type::BooleanValueType)) then v.oclIsTypeOf(camel::type::BooleanValue)\n\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t endif\n\t\t\t\t\tendif\n\t\t\t\tendif\n\t\t\tendif\n\t\telse \n\t\t\tif (diff and self.value <> null) then self.value.valueEquals(v)\n\t\t\telse false\n\t\t\tendif\n\t\tendif'"
	 * @generated
	 */
	boolean checkValue(Value v, boolean diff);

} // Attribute
