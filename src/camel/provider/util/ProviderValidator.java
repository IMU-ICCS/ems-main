/**
 */
package camel.provider.util;

import camel.provider.*;

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
 * @see camel.provider.ProviderPackage
 * @generated
 */
public class ProviderValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ProviderValidator INSTANCE = new ProviderValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "camel.provider";

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
	public ProviderValidator() {
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
	  return ProviderPackage.eINSTANCE;
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
			case ProviderPackage.PROVIDER_MODEL:
				return validateProviderModel((ProviderModel)value, diagnostics, context);
			case ProviderPackage.ATTRIBUTE:
				return validateAttribute((Attribute)value, diagnostics, context);
			case ProviderPackage.ATTRIBUTE_CONSTRAINT:
				return validateAttributeConstraint((AttributeConstraint)value, diagnostics, context);
			case ProviderPackage.CARDINALITY:
				return validateCardinality((Cardinality)value, diagnostics, context);
			case ProviderPackage.FEAT_CARDINALITY:
				return validateFeatCardinality((FeatCardinality)value, diagnostics, context);
			case ProviderPackage.GROUP_CARDINALITY:
				return validateGroupCardinality((GroupCardinality)value, diagnostics, context);
			case ProviderPackage.CLONE:
				return validateClone((Clone)value, diagnostics, context);
			case ProviderPackage.CONSTRAINT:
				return validateConstraint((Constraint)value, diagnostics, context);
			case ProviderPackage.EXCLUDES:
				return validateExcludes((Excludes)value, diagnostics, context);
			case ProviderPackage.IMPLIES:
				return validateImplies((Implies)value, diagnostics, context);
			case ProviderPackage.REQUIRES:
				return validateRequires((Requires)value, diagnostics, context);
			case ProviderPackage.FUNCTIONAL:
				return validateFunctional((Functional)value, diagnostics, context);
			case ProviderPackage.FEATURE:
				return validateFeature((Feature)value, diagnostics, context);
			case ProviderPackage.ALTERNATIVE:
				return validateAlternative((Alternative)value, diagnostics, context);
			case ProviderPackage.EXCLUSIVE:
				return validateExclusive((Exclusive)value, diagnostics, context);
			case ProviderPackage.SCOPE:
				return validateScope((Scope)value, diagnostics, context);
			case ProviderPackage.INSTANCE:
				return validateInstance((Instance)value, diagnostics, context);
			case ProviderPackage.PRODUCT:
				return validateProduct((Product)value, diagnostics, context);
			case ProviderPackage.OPERATOR:
				return validateOperator((Operator)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProviderModel(ProviderModel providerModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)providerModel, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute(Attribute attribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)attribute, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateAttribute_Attribute_must_have_at_least_value_or_valueType(attribute, diagnostics, context);
		if (result || diagnostics != null) result &= validateAttribute_Attribute_value_in_value_type(attribute, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Attribute_must_have_at_least_value_or_valueType constraint of '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ATTRIBUTE__ATTRIBUTE_MUST_HAVE_AT_LEAST_VALUE_OR_VALUE_TYPE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tvalue <> null or valueType <> null";

	/**
	 * Validates the Attribute_must_have_at_least_value_or_valueType constraint of '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute_Attribute_must_have_at_least_value_or_valueType(Attribute attribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.ATTRIBUTE,
				 attribute,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Attribute_must_have_at_least_value_or_valueType",
				 ATTRIBUTE__ATTRIBUTE_MUST_HAVE_AT_LEAST_VALUE_OR_VALUE_TYPE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Attribute_value_in_value_type constraint of '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ATTRIBUTE__ATTRIBUTE_VALUE_IN_VALUE_TYPE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t(value <> null and valueType <> null) implies self.checkValue(value,false)";

	/**
	 * Validates the Attribute_value_in_value_type constraint of '<em>Attribute</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttribute_Attribute_value_in_value_type(Attribute attribute, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.ATTRIBUTE,
				 attribute,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Attribute_value_in_value_type",
				 ATTRIBUTE__ATTRIBUTE_VALUE_IN_VALUE_TYPE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeConstraint(AttributeConstraint attributeConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)attributeConstraint, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)attributeConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)attributeConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)attributeConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)attributeConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)attributeConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)attributeConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)attributeConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)attributeConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validateAttributeConstraint_AttributeConstraint_correct_values(attributeConstraint, diagnostics, context);
		if (result || diagnostics != null) result &= validateAttributeConstraint_differentAttrs_in_Constraint(attributeConstraint, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the AttributeConstraint_correct_values constraint of '<em>Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ATTRIBUTE_CONSTRAINT__ATTRIBUTE_CONSTRAINT_CORRECT_VALUES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tfrom.checkValue(fromValue,true) and to.checkValue(toValue,true)";

	/**
	 * Validates the AttributeConstraint_correct_values constraint of '<em>Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeConstraint_AttributeConstraint_correct_values(AttributeConstraint attributeConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT,
				 attributeConstraint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "AttributeConstraint_correct_values",
				 ATTRIBUTE_CONSTRAINT__ATTRIBUTE_CONSTRAINT_CORRECT_VALUES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the differentAttrs_in_Constraint constraint of '<em>Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ATTRIBUTE_CONSTRAINT__DIFFERENT_ATTRS_IN_CONSTRAINT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tfrom <> to";

	/**
	 * Validates the differentAttrs_in_Constraint constraint of '<em>Attribute Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAttributeConstraint_differentAttrs_in_Constraint(AttributeConstraint attributeConstraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.ATTRIBUTE_CONSTRAINT,
				 attributeConstraint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "differentAttrs_in_Constraint",
				 ATTRIBUTE_CONSTRAINT__DIFFERENT_ATTRS_IN_CONSTRAINT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCardinality(Cardinality cardinality, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)cardinality, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)cardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)cardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)cardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)cardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)cardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)cardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)cardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)cardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validateCardinality_Cardinality_Min_Less_Equal_Than_Max(cardinality, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Cardinality_Min_Less_Equal_Than_Max constraint of '<em>Cardinality</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String CARDINALITY__CARDINALITY_MIN_LESS_EQUAL_THAN_MAX__EEXPRESSION = "\n" +
		"\t\t\tcardinalityMin >= 0 and cardinalityMax >= 0 and cardinalityMin <= cardinalityMax";

	/**
	 * Validates the Cardinality_Min_Less_Equal_Than_Max constraint of '<em>Cardinality</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCardinality_Cardinality_Min_Less_Equal_Than_Max(Cardinality cardinality, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.CARDINALITY,
				 cardinality,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Cardinality_Min_Less_Equal_Than_Max",
				 CARDINALITY__CARDINALITY_MIN_LESS_EQUAL_THAN_MAX__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFeatCardinality(FeatCardinality featCardinality, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)featCardinality, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)featCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)featCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)featCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)featCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)featCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)featCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)featCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)featCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validateCardinality_Cardinality_Min_Less_Equal_Than_Max(featCardinality, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGroupCardinality(GroupCardinality groupCardinality, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)groupCardinality, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)groupCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)groupCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)groupCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)groupCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)groupCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)groupCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)groupCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)groupCardinality, diagnostics, context);
		if (result || diagnostics != null) result &= validateCardinality_Cardinality_Min_Less_Equal_Than_Max(groupCardinality, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateClone(Clone clone, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)clone, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConstraint(Constraint constraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)constraint, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)constraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)constraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)constraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)constraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)constraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)constraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)constraint, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)constraint, diagnostics, context);
		if (result || diagnostics != null) result &= validateConstraint_fromAttributesBelongtoFromFeatureAndSymmetric(constraint, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the fromAttributesBelongtoFromFeatureAndSymmetric constraint of '<em>Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String CONSTRAINT__FROM_ATTRIBUTES_BELONGTO_FROM_FEATURE_AND_SYMMETRIC__EEXPRESSION = "\n" +
		"\t\t\tattributeConstraints->forAll(p | (from.attributes->includes(p.from) and to.attributes->includes(p.to)))";

	/**
	 * Validates the fromAttributesBelongtoFromFeatureAndSymmetric constraint of '<em>Constraint</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConstraint_fromAttributesBelongtoFromFeatureAndSymmetric(Constraint constraint, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.CONSTRAINT,
				 constraint,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "fromAttributesBelongtoFromFeatureAndSymmetric",
				 CONSTRAINT__FROM_ATTRIBUTES_BELONGTO_FROM_FEATURE_AND_SYMMETRIC__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExcludes(Excludes excludes, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)excludes, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)excludes, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)excludes, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)excludes, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)excludes, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)excludes, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)excludes, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)excludes, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)excludes, diagnostics, context);
		if (result || diagnostics != null) result &= validateConstraint_fromAttributesBelongtoFromFeatureAndSymmetric(excludes, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateImplies(Implies implies, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)implies, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)implies, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)implies, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)implies, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)implies, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)implies, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)implies, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)implies, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)implies, diagnostics, context);
		if (result || diagnostics != null) result &= validateConstraint_fromAttributesBelongtoFromFeatureAndSymmetric(implies, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequires(Requires requires, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)requires, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)requires, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)requires, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)requires, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)requires, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)requires, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)requires, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)requires, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)requires, diagnostics, context);
		if (result || diagnostics != null) result &= validateConstraint_fromAttributesBelongtoFromFeatureAndSymmetric(requires, diagnostics, context);
		if (result || diagnostics != null) result &= validateRequires_at_least_one_value_in_requires(requires, diagnostics, context);
		if (result || diagnostics != null) result &= validateRequires_cardFrom_To_Conformance(requires, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the at_least_one_value_in_requires constraint of '<em>Requires</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String REQUIRES__AT_LEAST_ONE_VALUE_IN_REQUIRES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tscopeFrom <> null or scopeTo <> null";

	/**
	 * Validates the at_least_one_value_in_requires constraint of '<em>Requires</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequires_at_least_one_value_in_requires(Requires requires, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.REQUIRES,
				 requires,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "at_least_one_value_in_requires",
				 REQUIRES__AT_LEAST_ONE_VALUE_IN_REQUIRES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the cardFrom_To_Conformance constraint of '<em>Requires</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String REQUIRES__CARD_FROM_TO_CONFORMANCE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t(cardFrom <> null implies (cardFrom.cardinalityMin >= from.featureCardinality.cardinalityMin and cardFrom.cardinalityMax <= from.featureCardinality.cardinalityMax)) \n" +
		"\t\t\t\t\t\tand \n" +
		"\t\t\t\t\t\t(cardTo <> null implies (cardTo.cardinalityMin >= to.featureCardinality.cardinalityMin and cardTo.cardinalityMax <= to.featureCardinality.cardinalityMax))";

	/**
	 * Validates the cardFrom_To_Conformance constraint of '<em>Requires</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequires_cardFrom_To_Conformance(Requires requires, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.REQUIRES,
				 requires,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "cardFrom_To_Conformance",
				 REQUIRES__CARD_FROM_TO_CONFORMANCE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFunctional(Functional functional, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)functional, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)functional, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)functional, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)functional, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)functional, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)functional, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)functional, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)functional, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)functional, diagnostics, context);
		if (result || diagnostics != null) result &= validateConstraint_fromAttributesBelongtoFromFeatureAndSymmetric(functional, diagnostics, context);
		if (result || diagnostics != null) result &= validateRequires_at_least_one_value_in_requires(functional, diagnostics, context);
		if (result || diagnostics != null) result &= validateRequires_cardFrom_To_Conformance(functional, diagnostics, context);
		if (result || diagnostics != null) result &= validateFunctional_correct_values_for_Functional(functional, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the correct_values_for_Functional constraint of '<em>Functional</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String FUNCTIONAL__CORRECT_VALUES_FOR_FUNCTIONAL__EEXPRESSION = "\n" +
		"\t\t\t\t\t\torder >= 0 and value > 0";

	/**
	 * Validates the correct_values_for_Functional constraint of '<em>Functional</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFunctional_correct_values_for_Functional(Functional functional, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.FUNCTIONAL,
				 functional,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "correct_values_for_Functional",
				 FUNCTIONAL__CORRECT_VALUES_FOR_FUNCTIONAL__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFeature(Feature feature, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)feature, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)feature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)feature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)feature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)feature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)feature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)feature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)feature, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)feature, diagnostics, context);
		if (result || diagnostics != null) result &= validateFeature_Correct_FeatCardinality(feature, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Correct_FeatCardinality constraint of '<em>Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String FEATURE__CORRECT_FEAT_CARDINALITY__EEXPRESSION = "\n" +
		"\t\t\t(featureCardinality.cardinalityMin <= featureCardinality.value) and featureCardinality.value <= featureCardinality.cardinalityMax";

	/**
	 * Validates the Correct_FeatCardinality constraint of '<em>Feature</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFeature_Correct_FeatCardinality(Feature feature, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.FEATURE,
				 feature,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Correct_FeatCardinality",
				 FEATURE__CORRECT_FEAT_CARDINALITY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAlternative(Alternative alternative, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)alternative, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)alternative, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)alternative, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)alternative, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)alternative, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)alternative, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)alternative, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)alternative, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)alternative, diagnostics, context);
		if (result || diagnostics != null) result &= validateFeature_Correct_FeatCardinality(alternative, diagnostics, context);
		if (result || diagnostics != null) result &= validateAlternative_Alternative_variants_diff_from_subFeatures(alternative, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Alternative_variants_diff_from_subFeatures constraint of '<em>Alternative</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ALTERNATIVE__ALTERNATIVE_VARIANTS_DIFF_FROM_SUB_FEATURES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tsubFeatures->forAll(p | not(variants->exists(q | q.name = p.name)))";

	/**
	 * Validates the Alternative_variants_diff_from_subFeatures constraint of '<em>Alternative</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAlternative_Alternative_variants_diff_from_subFeatures(Alternative alternative, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ProviderPackage.Literals.ALTERNATIVE,
				 alternative,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Alternative_variants_diff_from_subFeatures",
				 ALTERNATIVE__ALTERNATIVE_VARIANTS_DIFF_FROM_SUB_FEATURES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExclusive(Exclusive exclusive, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)exclusive, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)exclusive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)exclusive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)exclusive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)exclusive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)exclusive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)exclusive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)exclusive, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)exclusive, diagnostics, context);
		if (result || diagnostics != null) result &= validateFeature_Correct_FeatCardinality(exclusive, diagnostics, context);
		if (result || diagnostics != null) result &= validateAlternative_Alternative_variants_diff_from_subFeatures(exclusive, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScope(Scope scope, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)scope, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInstance(Instance instance, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)instance, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProduct(Product product, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)product, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOperator(Operator operator, DiagnosticChain diagnostics, Map<Object, Object> context) {
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

} //ProviderValidator
