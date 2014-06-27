/**
 */
package camel.security.util;

import camel.scalability.util.ScalabilityValidator;

import camel.security.*;

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
 * @see camel.security.SecurityPackage
 * @generated
 */
public class SecurityValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final SecurityValidator INSTANCE = new SecurityValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "camel.security";

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
	protected ScalabilityValidator scalabilityValidator;

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
	public SecurityValidator() {
		super();
		scalabilityValidator = ScalabilityValidator.INSTANCE;
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
	  return SecurityPackage.eINSTANCE;
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
			case SecurityPackage.SECURITY_MODEL:
				return validateSecurityModel((SecurityModel)value, diagnostics, context);
			case SecurityPackage.SECURITY_CONTROL:
				return validateSecurityControl((SecurityControl)value, diagnostics, context);
			case SecurityPackage.SECURITY_METRIC:
				return validateSecurityMetric((SecurityMetric)value, diagnostics, context);
			case SecurityPackage.SECURITY_PROPERTY:
				return validateSecurityProperty((SecurityProperty)value, diagnostics, context);
			case SecurityPackage.CERTIFIABLE:
				return validateCertifiable((Certifiable)value, diagnostics, context);
			case SecurityPackage.SECURITY_REQUIREMENT:
				return validateSecurityRequirement((SecurityRequirement)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSecurityModel(SecurityModel securityModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)securityModel, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSecurityControl(SecurityControl securityControl, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)securityControl, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)securityControl, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)securityControl, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)securityControl, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)securityControl, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)securityControl, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)securityControl, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)securityControl, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)securityControl, diagnostics, context);
		if (result || diagnostics != null) result &= validateSecurityControl_SecurityControl_diff(securityControl, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the SecurityControl_diff constraint of '<em>Control</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SECURITY_CONTROL__SECURITY_CONTROL_DIFF__EEXPRESSION = "\n" +
		"\t\t\tSecurityControl.allInstances()->forAll(p1,p2 | p1 <> p2 implies (p1.id <> p2.id and p1.specification <> p2.specification))";

	/**
	 * Validates the SecurityControl_diff constraint of '<em>Control</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSecurityControl_SecurityControl_diff(SecurityControl securityControl, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SecurityPackage.Literals.SECURITY_CONTROL,
				 securityControl,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "SecurityControl_diff",
				 SECURITY_CONTROL__SECURITY_CONTROL_DIFF__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSecurityMetric(SecurityMetric securityMetric, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)securityMetric, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= scalabilityValidator.validateMetric_RAW_Metric_To_Sensor(securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= scalabilityValidator.validateMetric_Composite_Metric_To_Components(securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= scalabilityValidator.validateMetric_component_metrics_map_formula_templates(securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= scalabilityValidator.validateMetric_bindings_as_in_template(securityMetric, diagnostics, context);
		if (result || diagnostics != null) result &= scalabilityValidator.validateMetric_component_metrics_refer_to_same_level_or_lower(securityMetric, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSecurityProperty(SecurityProperty securityProperty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)securityProperty, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)securityProperty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)securityProperty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)securityProperty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)securityProperty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)securityProperty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)securityProperty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)securityProperty, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)securityProperty, diagnostics, context);
		if (result || diagnostics != null) result &= validateSecurityProperty_Sec_Prop_Realized_Only_Certifiable(securityProperty, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Sec_Prop_Realized_Only_Certifiable constraint of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SECURITY_PROPERTY__SEC_PROP_REALIZED_ONLY_CERTIFIABLE__EEXPRESSION = "\n" +
		"\t\t\tself.type = camel::scalability::PropertyType::ABSTRACT implies self.realizedBy->forAll(p | p.oclIsTypeOf(Certifiable))";

	/**
	 * Validates the Sec_Prop_Realized_Only_Certifiable constraint of '<em>Property</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSecurityProperty_Sec_Prop_Realized_Only_Certifiable(SecurityProperty securityProperty, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SecurityPackage.Literals.SECURITY_PROPERTY,
				 securityProperty,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Sec_Prop_Realized_Only_Certifiable",
				 SECURITY_PROPERTY__SEC_PROP_REALIZED_ONLY_CERTIFIABLE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCertifiable(Certifiable certifiable, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)certifiable, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)certifiable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)certifiable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)certifiable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)certifiable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)certifiable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)certifiable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)certifiable, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)certifiable, diagnostics, context);
		if (result || diagnostics != null) result &= validateSecurityProperty_Sec_Prop_Realized_Only_Certifiable(certifiable, diagnostics, context);
		if (result || diagnostics != null) result &= validateCertifiable_Certifiable_realized_by(certifiable, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Certifiable_realized_by constraint of '<em>Certifiable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String CERTIFIABLE__CERTIFIABLE_REALIZED_BY__EEXPRESSION = "\n" +
		"\t\t\tself.realizedBy->isEmpty()";

	/**
	 * Validates the Certifiable_realized_by constraint of '<em>Certifiable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCertifiable_Certifiable_realized_by(Certifiable certifiable, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(SecurityPackage.Literals.CERTIFIABLE,
				 certifiable,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Certifiable_realized_by",
				 CERTIFIABLE__CERTIFIABLE_REALIZED_BY__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSecurityRequirement(SecurityRequirement securityRequirement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)securityRequirement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)securityRequirement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)securityRequirement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)securityRequirement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)securityRequirement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)securityRequirement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)securityRequirement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)securityRequirement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)securityRequirement, diagnostics, context);
		if (result || diagnostics != null) result &= camelValidator.validateRequirement_non_negative_priorities_for_requirement(securityRequirement, diagnostics, context);
		return result;
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

} //SecurityValidator
