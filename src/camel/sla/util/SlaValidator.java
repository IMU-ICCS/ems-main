/**
 */
package camel.sla.util;

import camel.sla.*;

import camel.util.CamelValidator;

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
 * @see camel.sla.SlaPackage
 * @generated
 */
public class SlaValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final SlaValidator INSTANCE = new SlaValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "camel.sla";

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
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CamelValidator camelValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlaValidator() {
		super();
		camelValidator = CamelValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return SlaPackage.eINSTANCE;
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
			case SlaPackage.AGREEMENT_CONTEXT_TYPE:
				return validateAgreementContextType((AgreementContextType)value, diagnostics, context);
			case SlaPackage.AGREEMENT_TYPE:
				return validateAgreementType((AgreementType)value, diagnostics, context);
			case SlaPackage.ASSESSMENT_INTERVAL_TYPE:
				return validateAssessmentIntervalType((AssessmentIntervalType)value, diagnostics, context);
			case SlaPackage.BUSINESS_VALUE_LIST_TYPE:
				return validateBusinessValueListType((BusinessValueListType)value, diagnostics, context);
			case SlaPackage.COMPENSATION_TYPE:
				return validateCompensationType((CompensationType)value, diagnostics, context);
			case SlaPackage.GUARANTEE_TERM_TYPE:
				return validateGuaranteeTermType((GuaranteeTermType)value, diagnostics, context);
			case SlaPackage.KPI_TARGET_TYPE:
				return validateKPITargetType((KPITargetType)value, diagnostics, context);
			case SlaPackage.PREFERENCE_TYPE:
				return validatePreferenceType((PreferenceType)value, diagnostics, context);
			case SlaPackage.SERVICE_LEVEL_OBJECTIVE_TEMPLATE:
				return validateServiceLevelObjectiveTemplate((ServiceLevelObjectiveTemplate)value, diagnostics, context);
			case SlaPackage.SERVICE_LEVEL_OBJECTIVE_TYPE:
				return validateServiceLevelObjectiveType((ServiceLevelObjectiveType)value, diagnostics, context);
			case SlaPackage.SERVICE_SELECTOR_TYPE:
				return validateServiceSelectorType((ServiceSelectorType)value, diagnostics, context);
			case SlaPackage.TERM_COMPOSITOR_TYPE:
				return validateTermCompositorType((TermCompositorType)value, diagnostics, context);
			case SlaPackage.TERM_TREE_TYPE:
				return validateTermTreeType((TermTreeType)value, diagnostics, context);
			case SlaPackage.AGREEMENT_ROLE_TYPE:
				return validateAgreementRoleType((AgreementRoleType)value, diagnostics, context);
			case SlaPackage.SERVICE_ROLE_TYPE:
				return validateServiceRoleType((ServiceRoleType)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAgreementContextType(AgreementContextType agreementContextType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)agreementContextType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)agreementContextType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)agreementContextType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)agreementContextType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)agreementContextType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)agreementContextType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)agreementContextType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)agreementContextType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)agreementContextType, diagnostics, context);
		if (result || diagnostics != null) result &= validateAgreementContextType_WSAG_Context_Different_Role(agreementContextType, diagnostics, context);
		if (result || diagnostics != null) result &= validateAgreementContextType_WSAG_Context_Diff_Entities(agreementContextType, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the WSAG_Context_Different_Role constraint of '<em>Agreement Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String AGREEMENT_CONTEXT_TYPE__WSAG_CONTEXT_DIFFERENT_ROLE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.serviceProvider <> self.serviceConsumer";

	/**
	 * Validates the WSAG_Context_Different_Role constraint of '<em>Agreement Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAgreementContextType_WSAG_Context_Different_Role(AgreementContextType agreementContextType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE,
				 agreementContextType,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "WSAG_Context_Different_Role",
				 AGREEMENT_CONTEXT_TYPE__WSAG_CONTEXT_DIFFERENT_ROLE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the WSAG_Context_Diff_Entities constraint of '<em>Agreement Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String AGREEMENT_CONTEXT_TYPE__WSAG_CONTEXT_DIFF_ENTITIES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t(self.agreementInitiator <> null and self.agreementResponder <> null) implies self.agreementInitiator <> self.agreementResponder";

	/**
	 * Validates the WSAG_Context_Diff_Entities constraint of '<em>Agreement Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAgreementContextType_WSAG_Context_Diff_Entities(AgreementContextType agreementContextType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE,
				 agreementContextType,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "WSAG_Context_Diff_Entities",
				 AGREEMENT_CONTEXT_TYPE__WSAG_CONTEXT_DIFF_ENTITIES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAgreementType(AgreementType agreementType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)agreementType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAssessmentIntervalType(AssessmentIntervalType assessmentIntervalType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)assessmentIntervalType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)assessmentIntervalType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)assessmentIntervalType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)assessmentIntervalType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)assessmentIntervalType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)assessmentIntervalType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)assessmentIntervalType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)assessmentIntervalType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)assessmentIntervalType, diagnostics, context);
		if (result || diagnostics != null) result &= validateAssessmentIntervalType_AssessmentInterval_attribute_value_enforcement(assessmentIntervalType, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the AssessmentInterval_attribute_value_enforcement constraint of '<em>Assessment Interval Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ASSESSMENT_INTERVAL_TYPE__ASSESSMENT_INTERVAL_ATTRIBUTE_VALUE_ENFORCEMENT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\ttimeInterval > 0 and count >= 0";

	/**
	 * Validates the AssessmentInterval_attribute_value_enforcement constraint of '<em>Assessment Interval Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAssessmentIntervalType_AssessmentInterval_attribute_value_enforcement(AssessmentIntervalType assessmentIntervalType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SlaPackage.Literals.ASSESSMENT_INTERVAL_TYPE,
				 assessmentIntervalType,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "AssessmentInterval_attribute_value_enforcement",
				 ASSESSMENT_INTERVAL_TYPE__ASSESSMENT_INTERVAL_ATTRIBUTE_VALUE_ENFORCEMENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBusinessValueListType(BusinessValueListType businessValueListType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)businessValueListType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCompensationType(CompensationType compensationType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)compensationType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGuaranteeTermType(GuaranteeTermType guaranteeTermType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)guaranteeTermType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateKPITargetType(KPITargetType kpiTargetType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)kpiTargetType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePreferenceType(PreferenceType preferenceType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)preferenceType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validatePreferenceType_Utilities_util_non_negative(preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validatePreferenceType_Utilities_sum_to_One(preferenceType, diagnostics, context);
		if (result || diagnostics != null) result &= validatePreferenceType_Preference_Type_Same_Size(preferenceType, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Utilities_util_non_negative constraint of '<em>Preference Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PREFERENCE_TYPE__UTILITIES_UTIL_NON_NEGATIVE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.utility->forAll(p | p >= 0.0)";

	/**
	 * Validates the Utilities_util_non_negative constraint of '<em>Preference Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePreferenceType_Utilities_util_non_negative(PreferenceType preferenceType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SlaPackage.Literals.PREFERENCE_TYPE,
				 preferenceType,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Utilities_util_non_negative",
				 PREFERENCE_TYPE__UTILITIES_UTIL_NON_NEGATIVE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Utilities_sum_to_One constraint of '<em>Preference Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PREFERENCE_TYPE__UTILITIES_SUM_TO_ONE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.utility->sum() = 1.0";

	/**
	 * Validates the Utilities_sum_to_One constraint of '<em>Preference Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePreferenceType_Utilities_sum_to_One(PreferenceType preferenceType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SlaPackage.Literals.PREFERENCE_TYPE,
				 preferenceType,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Utilities_sum_to_One",
				 PREFERENCE_TYPE__UTILITIES_SUM_TO_ONE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Preference_Type_Same_Size constraint of '<em>Preference Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PREFERENCE_TYPE__PREFERENCE_TYPE_SAME_SIZE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.serviceTermReference->size() = self.utility->size()";

	/**
	 * Validates the Preference_Type_Same_Size constraint of '<em>Preference Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePreferenceType_Preference_Type_Same_Size(PreferenceType preferenceType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SlaPackage.Literals.PREFERENCE_TYPE,
				 preferenceType,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Preference_Type_Same_Size",
				 PREFERENCE_TYPE__PREFERENCE_TYPE_SAME_SIZE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateServiceLevelObjectiveTemplate(ServiceLevelObjectiveTemplate serviceLevelObjectiveTemplate, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)serviceLevelObjectiveTemplate, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)serviceLevelObjectiveTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)serviceLevelObjectiveTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)serviceLevelObjectiveTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)serviceLevelObjectiveTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)serviceLevelObjectiveTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)serviceLevelObjectiveTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)serviceLevelObjectiveTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)serviceLevelObjectiveTemplate, diagnostics, context);
		if (result || diagnostics != null) result &= camelValidator.validateRequirement_non_negative_priorities_for_requirement(serviceLevelObjectiveTemplate, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateServiceLevelObjectiveType(ServiceLevelObjectiveType serviceLevelObjectiveType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)serviceLevelObjectiveType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)serviceLevelObjectiveType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)serviceLevelObjectiveType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)serviceLevelObjectiveType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)serviceLevelObjectiveType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)serviceLevelObjectiveType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)serviceLevelObjectiveType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)serviceLevelObjectiveType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)serviceLevelObjectiveType, diagnostics, context);
		if (result || diagnostics != null) result &= camelValidator.validateRequirement_non_negative_priorities_for_requirement(serviceLevelObjectiveType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateServiceSelectorType(ServiceSelectorType serviceSelectorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)serviceSelectorType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)serviceSelectorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)serviceSelectorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)serviceSelectorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)serviceSelectorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)serviceSelectorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)serviceSelectorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)serviceSelectorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)serviceSelectorType, diagnostics, context);
		if (result || diagnostics != null) result &= validateServiceSelectorType_correct_service_reference_and_name_SSType(serviceSelectorType, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the correct_service_reference_and_name_SSType constraint of '<em>Service Selector Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SERVICE_SELECTOR_TYPE__CORRECT_SERVICE_REFERENCE_AND_NAME_SS_TYPE__EEXPRESSION = "\n" +
		"\t\t\t\t\t/* concrete service only at VM level, PaaS needs to be covered also */\n" +
		"\t\t\t\t\tif (serviceReference.oclIsTypeOf(camel::deployment::Component)) then serviceName = serviceReference.oclAsType(camel::deployment::Component).name \n" +
		"\t\t\t\t\telse \n" +
		"\t\t\t\t\t\tif (serviceReference.oclIsTypeOf(camel::VMType)) then serviceName = serviceReference.oclAsType(camel::VMType).name\n" +
		"\t\t\t\t\t\telse false endif\n" +
		"\t\t\t\t\tendif";

	/**
	 * Validates the correct_service_reference_and_name_SSType constraint of '<em>Service Selector Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateServiceSelectorType_correct_service_reference_and_name_SSType(ServiceSelectorType serviceSelectorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SlaPackage.Literals.SERVICE_SELECTOR_TYPE,
				 serviceSelectorType,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "correct_service_reference_and_name_SSType",
				 SERVICE_SELECTOR_TYPE__CORRECT_SERVICE_REFERENCE_AND_NAME_SS_TYPE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTermCompositorType(TermCompositorType termCompositorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)termCompositorType, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)termCompositorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)termCompositorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)termCompositorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)termCompositorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)termCompositorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)termCompositorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)termCompositorType, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)termCompositorType, diagnostics, context);
		if (result || diagnostics != null) result &= validateTermCompositorType_TermCompositorType_Non_Recurs(termCompositorType, diagnostics, context);
		if (result || diagnostics != null) result &= validateTermCompositorType_correct_service_reference(termCompositorType, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the TermCompositorType_Non_Recurs constraint of '<em>Term Compositor Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String TERM_COMPOSITOR_TYPE__TERM_COMPOSITOR_TYPE_NON_RECURS__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tnot(self.checkRecursiveness(self,self,null)) and self.exactlyOne->forAll(p1,p2 | p1 <> p2 implies not(p1.checkRecursiveness(p1,p2,null))) and self.oneOrMore->forAll(p1,p2 | p1 <> p2 implies not(p1.checkRecursiveness(p1,p2,null))) and self.all->forAll(p1,p2 | p1 <> p2 implies not(p1.checkRecursiveness(p1,p2,null)))";

	/**
	 * Validates the TermCompositorType_Non_Recurs constraint of '<em>Term Compositor Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTermCompositorType_TermCompositorType_Non_Recurs(TermCompositorType termCompositorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SlaPackage.Literals.TERM_COMPOSITOR_TYPE,
				 termCompositorType,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "TermCompositorType_Non_Recurs",
				 TERM_COMPOSITOR_TYPE__TERM_COMPOSITOR_TYPE_NON_RECURS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the correct_service_reference constraint of '<em>Term Compositor Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String TERM_COMPOSITOR_TYPE__CORRECT_SERVICE_REFERENCE__EEXPRESSION = "\n" +
		"\t\t\t\t\t/* concrete service only at VM level, PaaS needs to be covered also */\n" +
		"\t\t\t\t\tserviceReference->forAll(p | (p.oclIsKindOf(camel::deployment::Component) or p.oclIsTypeOf(camel::VMType)))";

	/**
	 * Validates the correct_service_reference constraint of '<em>Term Compositor Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTermCompositorType_correct_service_reference(TermCompositorType termCompositorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SlaPackage.Literals.TERM_COMPOSITOR_TYPE,
				 termCompositorType,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "correct_service_reference",
				 TERM_COMPOSITOR_TYPE__CORRECT_SERVICE_REFERENCE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTermTreeType(TermTreeType termTreeType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)termTreeType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAgreementRoleType(AgreementRoleType agreementRoleType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateServiceRoleType(ServiceRoleType serviceRoleType, DiagnosticChain diagnostics, Map<Object, Object> context) {
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

} //SlaValidator
