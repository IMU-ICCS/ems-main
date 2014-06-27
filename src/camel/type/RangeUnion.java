/**
 */
package camel.type;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Range Union</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.type.RangeUnion#getRanges <em>Ranges</em>}</li>
 *   <li>{@link camel.type.RangeUnion#getPrimitiveType <em>Primitive Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.type.TypePackage#getRangeUnion()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='Same_Primitive_Types CorrectRangeUnionSequence'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot Same_Primitive_Types='\n\t\t\t\t\t\tself.ranges->forAll(p | p.primitiveType = self.primitiveType)' CorrectRangeUnionSequence='\n\t\t\t\t\t\tnot(invalidRangeSequence(self))'"
 * @generated
 */
public interface RangeUnion extends ValueType {
	/**
	 * Returns the value of the '<em><b>Ranges</b></em>' containment reference list.
	 * The list contents are of type {@link camel.type.Range}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Ranges</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Ranges</em>' containment reference list.
	 * @see camel.type.TypePackage#getRangeUnion_Ranges()
	 * @model containment="true" required="true"
	 * @generated
	 */
	EList<Range> getRanges();

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
	 * @see camel.type.TypePackage#getRangeUnion_PrimitiveType()
	 * @model required="true"
	 * @generated
	 */
	TypeEnum getPrimitiveType();

	/**
	 * Sets the value of the '{@link camel.type.RangeUnion#getPrimitiveType <em>Primitive Type</em>}' attribute.
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
	 * @model nRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot body='ranges->exists(p | p.includesValue(n))'"
	 * @generated
	 */
	boolean includesValue(double n);

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @model ruRequired="true"
	 *        annotation="http://www.eclipse.org/emf/2002/GenModel body='EList<Range> ranges = ru.getRanges();\r\n\t\tRange prev = ranges.get(0);\r\n\t\tfor (int i = 1; i < ranges.size(); i++){\r\n\t\t\tRange next = ranges.get(i);\r\n\t\t\tcamel.type.Limit lowerLimit = prev.getUpperLimit();\r\n\t\t\tcamel.type.Limit upperLimit = next.getLowerLimit();\r\n\t\t\tboolean lowerInclusive = lowerLimit.isIncluded();\r\n\t\t\tboolean upperInclusive = upperLimit.isIncluded();\r\n\t\t\tif (!(lowerLimit instanceof camel.type.NegativeInf) && !(upperLimit instanceof camel.type.PositiveInf)){\r\n\t\t\t\tdouble low = 0.0, upper = 0.0;\r\n\t\t\t\t//Checking if already at end (positive infinity or next range starts with negative infinity\r\n\t\t\t\tcamel.type.NumericValue prevVal = lowerLimit.getValue();\r\n\t\t\t\tif (prevVal instanceof camel.type.PositiveInf) return Boolean.TRUE;\r\n\t\t\t\tcamel.type.NumericValue nextVal = upperLimit.getValue();\r\n\t\t\t\tif (nextVal instanceof camel.type.NegativeInf) return Boolean.TRUE;\r\n\t\t\t\t//Checking now that low is less or equal to upper\r\n\t\t\t\tif (prevVal instanceof camel.type.IntValue){\r\n\t\t\t\t\tlow = ((camel.type.IntValue)prevVal).getValue();\r\n\t\t\t\t\tif (!lowerInclusive){\r\n\t\t\t\t\t\tlow = low -1;\r\n\t\t\t\t\t\tlowerInclusive = true;\r\n\t\t\t\t\t}\r\n\t\t\t\t}\r\n\t\t\t\telse if (prevVal instanceof camel.type.FloatValue) low = ((camel.type.FloatValue)prevVal).getValue();\r\n\t\t\t\telse low = ((camel.type.DoubleValue)prevVal).getValue();\r\n\t\t\t\tif (nextVal instanceof camel.type.IntValue){\r\n\t\t\t\t\tupper = ((camel.type.IntValue)nextVal).getValue();\r\n\t\t\t\t\tif (!upperInclusive){\r\n\t\t\t\t\t\tupper = upper + 1;\r\n\t\t\t\t\t\tupperInclusive = true;\r\n\t\t\t\t\t}\r\n\t\t\t\t}\r\n\t\t\t\telse if (nextVal instanceof camel.type.FloatValue) upper = ((camel.type.FloatValue)nextVal).getValue();\r\n\t\t\t\telse upper = ((camel.type.DoubleValue)nextVal).getValue();\r\n\t\t\t\tSystem.out.println(\"Low is: \" + low + \" upper is: \" + upper);\r\n\t\t\t\tif (low > upper || (low == upper && lowerInclusive == true )) return Boolean.TRUE;\r\n\t\t\t}\r\n\t\t\tprev = next;\r\n\t\t}\r\n\t\treturn Boolean.FALSE;'"
	 * @generated
	 */
	boolean invalidRangeSequence(RangeUnion ru);

} // RangeUnion
