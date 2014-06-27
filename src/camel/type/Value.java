/**
 */
package camel.type;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Value</b></em>'.
 * <!-- end-user-doc -->
 *
 *
 * @see camel.type.TypePackage#getValue()
 * @model abstract="true"
 * @extends CDOObject
 * @generated
 */
public interface Value extends CDOObject {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model vRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='\n\t\t\t\t\t\tif (self.oclIsTypeOf(IntValue) and v.oclIsTypeOf(IntValue)) then self.oclAsType(IntValue).value = v.oclAsType(IntValue).value\n\t\t\t\t\t\telse\n\t\t\t\t\t\t\tif (self.oclIsTypeOf(FloatValue) and v.oclIsTypeOf(FloatValue)) then self.oclAsType(FloatValue).value = v.oclAsType(FloatValue).value\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(DoubleValue) and v.oclIsTypeOf(DoubleValue)) then self.oclAsType(DoubleValue).value = v.oclAsType(DoubleValue).value\n\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(StringValue) and v.oclIsTypeOf(StringValue)) then self.oclAsType(StringValue).value = v.oclAsType(StringValue).value\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(BooleanValue) and v.oclIsTypeOf(BooleanValue)) then self.oclAsType(BooleanValue).value = v.oclAsType(BooleanValue).value\n\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\tendif'"
	 * @generated
	 */
	boolean valueEquals(Value v);

} // Value
