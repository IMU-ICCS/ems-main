/**
 */
package camel.type.util;

import camel.type.*;

import java.util.Map;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.EObjectValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see camel.type.TypePackage
 * @generated
 */
public class TypeValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final TypeValidator INSTANCE = new TypeValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "camel.type";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeValidator() {
		super();
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return TypePackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case TypePackage.TYPE_REPOSITORY:
				return validateTypeRepository((TypeRepository)value, diagnostics, context);
			case TypePackage.LIMIT:
				return validateLimit((Limit)value, diagnostics, context);
			case TypePackage.VALUE:
				return validateValue((Value)value, diagnostics, context);
			case TypePackage.BOOLEAN_VALUE:
				return validateBooleanValue((BooleanValue)value, diagnostics, context);
			case TypePackage.ENUMERATE_VALUE:
				return validateEnumerateValue((EnumerateValue)value, diagnostics, context);
			case TypePackage.NUMERIC_VALUE:
				return validateNumericValue((NumericValue)value, diagnostics, context);
			case TypePackage.INT_VALUE:
				return validateIntValue((IntValue)value, diagnostics, context);
			case TypePackage.FLOAT_VALUE:
				return validateFloatValue((FloatValue)value, diagnostics, context);
			case TypePackage.DOUBLE_VALUE:
				return validateDoubleValue((DoubleValue)value, diagnostics, context);
			case TypePackage.NEGATIVE_INF:
				return validateNegativeInf((NegativeInf)value, diagnostics, context);
			case TypePackage.POSITIVE_INF:
				return validatePositiveInf((PositiveInf)value, diagnostics, context);
			case TypePackage.VALUE_TO_INCREASE:
				return validateValueToIncrease((ValueToIncrease)value, diagnostics, context);
			case TypePackage.STRING_VALUE:
				return validateStringValue((StringValue)value, diagnostics, context);
			case TypePackage.VALUE_TYPE:
				return validateValueType((ValueType)value, diagnostics, context);
			case TypePackage.BOOLEAN_VALUE_TYPE:
				return validateBooleanValueType((BooleanValueType)value, diagnostics, context);
			case TypePackage.ENUMERATION:
				return validateEnumeration((Enumeration)value, diagnostics, context);
			case TypePackage.LIST:
				return validateList((List)value, diagnostics, context);
			case TypePackage.RANGE:
				return validateRange((Range)value, diagnostics, context);
			case TypePackage.RANGE_UNION:
				return validateRangeUnion((RangeUnion)value, diagnostics, context);
			case TypePackage.STRING_VALUE_TYPE:
				return validateStringValueType((StringValueType)value, diagnostics, context);
			case TypePackage.TYPE_ENUM:
				return validateTypeEnum((TypeEnum)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypeRepository(TypeRepository typeRepository, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)typeRepository, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLimit(Limit limit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)limit, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateValue(Value value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)value, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBooleanValue(BooleanValue booleanValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)booleanValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEnumerateValue(EnumerateValue enumerateValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)enumerateValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNumericValue(NumericValue numericValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)numericValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateIntValue(IntValue intValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)intValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFloatValue(FloatValue floatValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)floatValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDoubleValue(DoubleValue doubleValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)doubleValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNegativeInf(NegativeInf negativeInf, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)negativeInf, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePositiveInf(PositiveInf positiveInf, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)positiveInf, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateValueToIncrease(ValueToIncrease valueToIncrease, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)valueToIncrease, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStringValue(StringValue stringValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)stringValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateValueType(ValueType valueType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)valueType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBooleanValueType(BooleanValueType booleanValueType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)booleanValueType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEnumeration(Enumeration enumeration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)enumeration, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)enumeration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)enumeration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)enumeration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)enumeration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)enumeration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)enumeration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)enumeration, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)enumeration, diagnostics, context);
		if (result || diagnostics != null) result &= validateEnumeration_Enumeration_All_Values_Diff(enumeration, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Enumeration_All_Values_Diff constraint of '<em>Enumeration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ENUMERATION__ENUMERATION_ALL_VALUES_DIFF__EEXPRESSION = "\n" +
		"\t\t\tvalues->forAll(p1, p2 | p1 <> p2 implies (p1.name <> p2.name and p1.value <> p2.value))";

	/**
	 * Validates the Enumeration_All_Values_Diff constraint of '<em>Enumeration</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEnumeration_Enumeration_All_Values_Diff(Enumeration enumeration, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(TypePackage.Literals.ENUMERATION,
				 enumeration,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Enumeration_All_Values_Diff",
				 ENUMERATION__ENUMERATION_ALL_VALUES_DIFF__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateList(List list, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)list, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)list, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)list, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)list, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)list, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)list, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)list, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)list, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)list, diagnostics, context);
		if (result || diagnostics != null) result &= validateList_list_must_have_type(list, diagnostics, context);
		if (result || diagnostics != null) result &= validateList_all_list_values_correct_type(list, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the list_must_have_type constraint of '<em>List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String LIST__LIST_MUST_HAVE_TYPE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\t(primitiveType <> null and type = null) or (type <> null and primitiveType = null)";

	/**
	 * Validates the list_must_have_type constraint of '<em>List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateList_list_must_have_type(List list, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(TypePackage.Literals.LIST,
				 list,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "list_must_have_type",
				 LIST__LIST_MUST_HAVE_TYPE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the all_list_values_correct_type constraint of '<em>List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String LIST__ALL_LIST_VALUES_CORRECT_TYPE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tvalues->forAll(p | \n" +
		"\t\t\t\t\t\t\tself.checkValueType(p)\n" +
		"\t\t\t\t\t\t)";

	/**
	 * Validates the all_list_values_correct_type constraint of '<em>List</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateList_all_list_values_correct_type(List list, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(TypePackage.Literals.LIST,
				 list,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "all_list_values_correct_type",
				 LIST__ALL_LIST_VALUES_CORRECT_TYPE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRange(Range range, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)range, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)range, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)range, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)range, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)range, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)range, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)range, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)range, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)range, diagnostics, context);
		if (result || diagnostics != null) result &= validateRange_correct_range_type(range, diagnostics, context);
		if (result || diagnostics != null) result &= validateRange_enforce_correct_range_type(range, diagnostics, context);
		if (result || diagnostics != null) result &= validateRange_Range_low_less_than_upper(range, diagnostics, context);
		if (result || diagnostics != null) result &= validateRange_Range_Infs_At_Appropriate_Pos(range, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the correct_range_type constraint of '<em>Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RANGE__CORRECT_RANGE_TYPE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t(primitiveType = TypeEnum::IntType) or (primitiveType = TypeEnum::FloatType) or (primitiveType = TypeEnum::DoubleType)";

	/**
	 * Validates the correct_range_type constraint of '<em>Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRange_correct_range_type(Range range, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(TypePackage.Literals.RANGE,
				 range,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "correct_range_type",
				 RANGE__CORRECT_RANGE_TYPE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the enforce_correct_range_type constraint of '<em>Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RANGE__ENFORCE_CORRECT_RANGE_TYPE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.checkType(self.lowerLimit, primitiveType, true) and self.checkType(self.upperLimit, primitiveType, false)";

	/**
	 * Validates the enforce_correct_range_type constraint of '<em>Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRange_enforce_correct_range_type(Range range, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(TypePackage.Literals.RANGE,
				 range,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "enforce_correct_range_type",
				 RANGE__ENFORCE_CORRECT_RANGE_TYPE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Range_low_less_than_upper constraint of '<em>Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RANGE__RANGE_LOW_LESS_THAN_UPPER__EEXPRESSION = "\n" +
		"\t\t\t\t\tif (not(lowerLimit.value.oclIsTypeOf(NegativeInf)) and not(upperLimit.value.oclIsTypeOf(PositiveInf))) then\n" +
		"\t\t\t\t\t\tif (primitiveType = TypeEnum::IntType) then\n" +
		"\t\t\t\t\t\t\tif (not(upperLimit.included) and not(lowerLimit.included)) then (upperLimit.value.oclAsType(IntValue).value - lowerLimit.value.oclAsType(IntValue).value.oclAsType(ecore::EInt)) >= 2 \n" +
		"\t\t\t\t\t\t\telse if (upperLimit.included) then lowerLimit.value.oclAsType(IntValue).value.oclAsType(ecore::EInt) <= upperLimit.value.oclAsType(IntValue).value\n" +
		"\t\t\t\t\t\t\t\t else lowerLimit.value.oclAsType(IntValue).value.oclAsType(ecore::EInt) < upperLimit.value.oclAsType(IntValue).value\n" +
		"\t\t\t\t\t\t\t\t endif\n" +
		"\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\telse (\n" +
		"\t\t\t\t\t\t\tif (primitiveType = TypeEnum::FloatType) then \n" +
		"\t\t\t\t\t\t\t\tif (upperLimit.included) then lowerLimit.value.oclAsType(FloatValue).value.oclAsType(ecore::EFloat) <= upperLimit.value.oclAsType(FloatValue).value\n" +
		"\t\t\t\t\t\t\t\telse lowerLimit.value.oclAsType(FloatValue).value.oclAsType(ecore::EFloat) < upperLimit.value.oclAsType(FloatValue).value\n" +
		"\t\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t\telse \n" +
		"\t\t\t\t\t\t\t\tif (upperLimit.included) then lowerLimit.value.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble) <= upperLimit.value.oclAsType(DoubleValue).value\n" +
		"\t\t\t\t\t\t\t\telse lowerLimit.value.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble) < upperLimit.value.oclAsType(DoubleValue).value\n" +
		"\t\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t) \t\n" +
		"\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\telse true\n" +
		"\t\t\t\t\tendif";

	/**
	 * Validates the Range_low_less_than_upper constraint of '<em>Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRange_Range_low_less_than_upper(Range range, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(TypePackage.Literals.RANGE,
				 range,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Range_low_less_than_upper",
				 RANGE__RANGE_LOW_LESS_THAN_UPPER__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Range_Infs_At_Appropriate_Pos constraint of '<em>Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RANGE__RANGE_INFS_AT_APPROPRIATE_POS__EEXPRESSION = "\n" +
		"\t\t\t\t\tnot(lowerLimit.value.oclIsTypeOf(PositiveInf) or upperLimit.value.oclIsTypeOf(NegativeInf))";

	/**
	 * Validates the Range_Infs_At_Appropriate_Pos constraint of '<em>Range</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRange_Range_Infs_At_Appropriate_Pos(Range range, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(TypePackage.Literals.RANGE,
				 range,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Range_Infs_At_Appropriate_Pos",
				 RANGE__RANGE_INFS_AT_APPROPRIATE_POS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRangeUnion(RangeUnion rangeUnion, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)rangeUnion, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)rangeUnion, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)rangeUnion, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)rangeUnion, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)rangeUnion, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)rangeUnion, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)rangeUnion, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)rangeUnion, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)rangeUnion, diagnostics, context);
		if (result || diagnostics != null) result &= validateRangeUnion_Same_Primitive_Types(rangeUnion, diagnostics, context);
		if (result || diagnostics != null) result &= validateRangeUnion_CorrectRangeUnionSequence(rangeUnion, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Same_Primitive_Types constraint of '<em>Range Union</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RANGE_UNION__SAME_PRIMITIVE_TYPES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.ranges->forAll(p | p.primitiveType = self.primitiveType)";

	/**
	 * Validates the Same_Primitive_Types constraint of '<em>Range Union</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRangeUnion_Same_Primitive_Types(RangeUnion rangeUnion, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(TypePackage.Literals.RANGE_UNION,
				 rangeUnion,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Same_Primitive_Types",
				 RANGE_UNION__SAME_PRIMITIVE_TYPES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the CorrectRangeUnionSequence constraint of '<em>Range Union</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RANGE_UNION__CORRECT_RANGE_UNION_SEQUENCE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tnot(invalidRangeSequence(self))";

	/**
	 * Validates the CorrectRangeUnionSequence constraint of '<em>Range Union</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRangeUnion_CorrectRangeUnionSequence(RangeUnion rangeUnion, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(TypePackage.Literals.RANGE_UNION,
				 rangeUnion,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "CorrectRangeUnionSequence",
				 RANGE_UNION__CORRECT_RANGE_UNION_SEQUENCE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStringValueType(StringValueType stringValueType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)stringValueType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTypeEnum(TypeEnum typeEnum, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //TypeValidator
