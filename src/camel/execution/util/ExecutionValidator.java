/**
 */
package camel.execution.util;

import camel.execution.*;

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
 * @see camel.execution.ExecutionPackage
 * @generated
 */
public class ExecutionValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ExecutionValidator INSTANCE = new ExecutionValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "camel.execution";

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
	public ExecutionValidator() {
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
	  return ExecutionPackage.eINSTANCE;
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
			case ExecutionPackage.EXECUTION_MODEL:
				return validateExecutionModel((ExecutionModel)value, diagnostics, context);
			case ExecutionPackage.ACTION_REALIZATION:
				return validateActionRealization((ActionRealization)value, diagnostics, context);
			case ExecutionPackage.EXECUTION_CONTEXT:
				return validateExecutionContext((ExecutionContext)value, diagnostics, context);
			case ExecutionPackage.MEASUREMENT:
				return validateMeasurement((Measurement)value, diagnostics, context);
			case ExecutionPackage.APPLICATION_MEASUREMENT:
				return validateApplicationMeasurement((ApplicationMeasurement)value, diagnostics, context);
			case ExecutionPackage.INTERNAL_COMPONENT_MEASUREMENT:
				return validateInternalComponentMeasurement((InternalComponentMeasurement)value, diagnostics, context);
			case ExecutionPackage.RESOURCE_COUPLING_MEASUREMENT:
				return validateResourceCouplingMeasurement((ResourceCouplingMeasurement)value, diagnostics, context);
			case ExecutionPackage.RESOURCE_MEASUREMENT:
				return validateResourceMeasurement((ResourceMeasurement)value, diagnostics, context);
			case ExecutionPackage.RULE_TRIGGER:
				return validateRuleTrigger((RuleTrigger)value, diagnostics, context);
			case ExecutionPackage.SLO_ASSESSMENT:
				return validateSLOAssessment((SLOAssessment)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExecutionModel(ExecutionModel executionModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)executionModel, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateActionRealization(ActionRealization actionRealization, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)actionRealization, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExecutionContext(ExecutionContext executionContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)executionContext, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateExecutionContext_ExecutionContext_Total_Cost(executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateExecutionContext_ExecutionContext_Unit_Cost(executionContext, diagnostics, context);
		if (result || diagnostics != null) result &= validateExecutionContext_ExecutionContext_Correct_Reqs(executionContext, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the ExecutionContext_Total_Cost constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String EXECUTION_CONTEXT__EXECUTION_CONTEXT_TOTAL_COST__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.totalCost >= 0";

	/**
	 * Validates the ExecutionContext_Total_Cost constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExecutionContext_ExecutionContext_Total_Cost(ExecutionContext executionContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.EXECUTION_CONTEXT,
				 executionContext,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ExecutionContext_Total_Cost",
				 EXECUTION_CONTEXT__EXECUTION_CONTEXT_TOTAL_COST__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the ExecutionContext_Unit_Cost constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String EXECUTION_CONTEXT__EXECUTION_CONTEXT_UNIT_COST__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.totalCost > 0 implies costUnit <> null";

	/**
	 * Validates the ExecutionContext_Unit_Cost constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExecutionContext_ExecutionContext_Unit_Cost(ExecutionContext executionContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.EXECUTION_CONTEXT,
				 executionContext,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ExecutionContext_Unit_Cost",
				 EXECUTION_CONTEXT__EXECUTION_CONTEXT_UNIT_COST__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the ExecutionContext_Correct_Reqs constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String EXECUTION_CONTEXT__EXECUTION_CONTEXT_CORRECT_REQS__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tbasedOnRequirements.requirement->forAll(p | p.oclIsTypeOf(camel::sla::ServiceLevelObjectiveType) implies (p.oclAsType(camel::sla::ServiceLevelObjectiveType).customServiceLevel.metric.objectBinding.executionContext=self))";

	/**
	 * Validates the ExecutionContext_Correct_Reqs constraint of '<em>Context</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExecutionContext_ExecutionContext_Correct_Reqs(ExecutionContext executionContext, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.EXECUTION_CONTEXT,
				 executionContext,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "ExecutionContext_Correct_Reqs",
				 EXECUTION_CONTEXT__EXECUTION_CONTEXT_CORRECT_REQS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMeasurement(Measurement measurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)measurement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_ItSLO_refer_to_Correct_Metric(measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_eventInstance_same_Metric(measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Correct_Measurement_Value(measurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_metric_refers_to_correct_ec(measurement, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Measurement_ItSLO_refer_to_Correct_Metric constraint of '<em>Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String MEASUREMENT__MEASUREMENT_IT_SLO_REFER_TO_CORRECT_METRIC__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tself.itSLO <> null implies self.itSLO.customServiceLevel.metric = self.ofMetric";

	/**
	 * Validates the Measurement_ItSLO_refer_to_Correct_Metric constraint of '<em>Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMeasurement_Measurement_ItSLO_refer_to_Correct_Metric(Measurement measurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.MEASUREMENT,
				 measurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Measurement_ItSLO_refer_to_Correct_Metric",
				 MEASUREMENT__MEASUREMENT_IT_SLO_REFER_TO_CORRECT_METRIC__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Measurement_eventInstance_same_Metric constraint of '<em>Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String MEASUREMENT__MEASUREMENT_EVENT_INSTANCE_SAME_METRIC__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t(self.triggers <> null and self.triggers.onEvent.oclIsTypeOf(camel::scalability::NonFunctionalEvent)) implies self.ofMetric = self.triggers.onEvent.oclAsType(camel::scalability::NonFunctionalEvent).condition.metric";

	/**
	 * Validates the Measurement_eventInstance_same_Metric constraint of '<em>Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMeasurement_Measurement_eventInstance_same_Metric(Measurement measurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.MEASUREMENT,
				 measurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Measurement_eventInstance_same_Metric",
				 MEASUREMENT__MEASUREMENT_EVENT_INSTANCE_SAME_METRIC__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Correct_Measurement_Value constraint of '<em>Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String MEASUREMENT__CORRECT_MEASUREMENT_VALUE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tif (ofMetric.valueType.oclIsTypeOf(camel::type::Range)) then ofMetric.valueType.oclAsType(camel::type::Range).includesValue(self.value)\n" +
		"\t\t\t\t\t\telse if (ofMetric.valueType.oclIsTypeOf(camel::type::RangeUnion)) then ofMetric.valueType.oclAsType(camel::type::RangeUnion).includesValue(self.value)\n" +
		"\t\t\t\t\t\telse true endif\n" +
		"\t\t\t\t\t\tendif";

	/**
	 * Validates the Correct_Measurement_Value constraint of '<em>Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMeasurement_Correct_Measurement_Value(Measurement measurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.MEASUREMENT,
				 measurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Correct_Measurement_Value",
				 MEASUREMENT__CORRECT_MEASUREMENT_VALUE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Measurement_metric_refers_to_correct_ec constraint of '<em>Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String MEASUREMENT__MEASUREMENT_METRIC_REFERS_TO_CORRECT_EC__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tinExecutionContext = ofMetric.objectBinding.executionContext";

	/**
	 * Validates the Measurement_metric_refers_to_correct_ec constraint of '<em>Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMeasurement_Measurement_metric_refers_to_correct_ec(Measurement measurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.MEASUREMENT,
				 measurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Measurement_metric_refers_to_correct_ec",
				 MEASUREMENT__MEASUREMENT_METRIC_REFERS_TO_CORRECT_EC__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateApplicationMeasurement(ApplicationMeasurement applicationMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)applicationMeasurement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_ItSLO_refer_to_Correct_Metric(applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_eventInstance_same_Metric(applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Correct_Measurement_Value(applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_metric_refers_to_correct_ec(applicationMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateApplicationMeasurement_AM_Same_App(applicationMeasurement, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the AM_Same_App constraint of '<em>Application Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String APPLICATION_MEASUREMENT__AM_SAME_APP__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tself.inExecutionContext.ofApplication = self.forApplication";

	/**
	 * Validates the AM_Same_App constraint of '<em>Application Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateApplicationMeasurement_AM_Same_App(ApplicationMeasurement applicationMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.APPLICATION_MEASUREMENT,
				 applicationMeasurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "AM_Same_App",
				 APPLICATION_MEASUREMENT__AM_SAME_APP__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponentMeasurement(InternalComponentMeasurement internalComponentMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)internalComponentMeasurement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_ItSLO_refer_to_Correct_Metric(internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_eventInstance_same_Metric(internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Correct_Measurement_Value(internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_metric_refers_to_correct_ec(internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateInternalComponentMeasurement_SCM_metric_refer_to_same_component(internalComponentMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateInternalComponentMeasurement_SCM_included_in_EC(internalComponentMeasurement, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the SCM_metric_refer_to_same_component constraint of '<em>Internal Component Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String INTERNAL_COMPONENT_MEASUREMENT__SCM_METRIC_REFER_TO_SAME_COMPONENT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tofMetric.objectBinding.oclIsTypeOf(camel::scalability::MetricComponentInstanceBinding) and ofMetric.objectBinding.oclAsType(camel::scalability::MetricComponentInstanceBinding).componentInstance = ofComponentInstance";

	/**
	 * Validates the SCM_metric_refer_to_same_component constraint of '<em>Internal Component Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponentMeasurement_SCM_metric_refer_to_same_component(InternalComponentMeasurement internalComponentMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.INTERNAL_COMPONENT_MEASUREMENT,
				 internalComponentMeasurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "SCM_metric_refer_to_same_component",
				 INTERNAL_COMPONENT_MEASUREMENT__SCM_METRIC_REFER_TO_SAME_COMPONENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the SCM_included_in_EC constraint of '<em>Internal Component Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String INTERNAL_COMPONENT_MEASUREMENT__SCM_INCLUDED_IN_EC__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tinExecutionContext.involvesDeployment.componentInstances->includes(ofComponentInstance)";

	/**
	 * Validates the SCM_included_in_EC constraint of '<em>Internal Component Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateInternalComponentMeasurement_SCM_included_in_EC(InternalComponentMeasurement internalComponentMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.INTERNAL_COMPONENT_MEASUREMENT,
				 internalComponentMeasurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "SCM_included_in_EC",
				 INTERNAL_COMPONENT_MEASUREMENT__SCM_INCLUDED_IN_EC__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceCouplingMeasurement(ResourceCouplingMeasurement resourceCouplingMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)resourceCouplingMeasurement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_ItSLO_refer_to_Correct_Metric(resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_eventInstance_same_Metric(resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Correct_Measurement_Value(resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_metric_refers_to_correct_ec(resourceCouplingMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateResourceCouplingMeasurement_RCMeasurement_Diff_VM_Instances(resourceCouplingMeasurement, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the RCMeasurement_Diff_VM_Instances constraint of '<em>Resource Coupling Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RESOURCE_COUPLING_MEASUREMENT__RC_MEASUREMENT_DIFF_VM_INSTANCES__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tsourceVMInstance <> destinationVMInstance and inExecutionContext.involvesDeployment.componentInstances->includes(sourceVMInstance) and inExecutionContext.involvesDeployment.componentInstances->includes(destinationVMInstance)";

	/**
	 * Validates the RCMeasurement_Diff_VM_Instances constraint of '<em>Resource Coupling Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceCouplingMeasurement_RCMeasurement_Diff_VM_Instances(ResourceCouplingMeasurement resourceCouplingMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RESOURCE_COUPLING_MEASUREMENT,
				 resourceCouplingMeasurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "RCMeasurement_Diff_VM_Instances",
				 RESOURCE_COUPLING_MEASUREMENT__RC_MEASUREMENT_DIFF_VM_INSTANCES__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceMeasurement(ResourceMeasurement resourceMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)resourceMeasurement, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_ItSLO_refer_to_Correct_Metric(resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_eventInstance_same_Metric(resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Correct_Measurement_Value(resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateMeasurement_Measurement_metric_refers_to_correct_ec(resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateResourceMeasurement_RM_At_Least_One_Not_Null(resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateResourceMeasurement_RM_DataObject_Alone(resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateResourceMeasurement_RM_EC_ClouMLModel_VM_INSTANCE(resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateResourceMeasurement_RM_Metric_VM_Instance(resourceMeasurement, diagnostics, context);
		if (result || diagnostics != null) result &= validateResourceMeasurement_RM_DataObject_ec(resourceMeasurement, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the RM_At_Least_One_Not_Null constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RESOURCE_MEASUREMENT__RM_AT_LEAST_ONE_NOT_NULL__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tofVMInstance <> null or physicalNode <> null or dataObject <> null";

	/**
	 * Validates the RM_At_Least_One_Not_Null constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceMeasurement_RM_At_Least_One_Not_Null(ResourceMeasurement resourceMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RESOURCE_MEASUREMENT,
				 resourceMeasurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "RM_At_Least_One_Not_Null",
				 RESOURCE_MEASUREMENT__RM_AT_LEAST_ONE_NOT_NULL__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the RM_DataObject_Alone constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RESOURCE_MEASUREMENT__RM_DATA_OBJECT_ALONE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tdataObject <> null implies (ofVMInstance = null and physicalNode = null)";

	/**
	 * Validates the RM_DataObject_Alone constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceMeasurement_RM_DataObject_Alone(ResourceMeasurement resourceMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RESOURCE_MEASUREMENT,
				 resourceMeasurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "RM_DataObject_Alone",
				 RESOURCE_MEASUREMENT__RM_DATA_OBJECT_ALONE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the RM_EC_ClouMLModel_VM_INSTANCE constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RESOURCE_MEASUREMENT__RM_EC_CLOU_ML_MODEL_VM_INSTANCE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tofVMInstance <> null implies inExecutionContext.involvesDeployment.componentInstances->includes(ofVMInstance)";

	/**
	 * Validates the RM_EC_ClouMLModel_VM_INSTANCE constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceMeasurement_RM_EC_ClouMLModel_VM_INSTANCE(ResourceMeasurement resourceMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RESOURCE_MEASUREMENT,
				 resourceMeasurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "RM_EC_ClouMLModel_VM_INSTANCE",
				 RESOURCE_MEASUREMENT__RM_EC_CLOU_ML_MODEL_VM_INSTANCE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the RM_Metric_VM_Instance constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RESOURCE_MEASUREMENT__RM_METRIC_VM_INSTANCE__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tofVMInstance <> null implies (ofMetric.objectBinding.oclIsTypeOf(camel::scalability::MetricVMInstanceBinding) and ofMetric.objectBinding.oclAsType(camel::scalability::MetricVMInstanceBinding).vmInstance = ofVMInstance)";

	/**
	 * Validates the RM_Metric_VM_Instance constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceMeasurement_RM_Metric_VM_Instance(ResourceMeasurement resourceMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RESOURCE_MEASUREMENT,
				 resourceMeasurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "RM_Metric_VM_Instance",
				 RESOURCE_MEASUREMENT__RM_METRIC_VM_INSTANCE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the RM_DataObject_ec constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RESOURCE_MEASUREMENT__RM_DATA_OBJECT_EC__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tdataObject <> null implies inExecutionContext.involvesDeployment.communications->exists(p | p.ofDataObject = dataObject)";

	/**
	 * Validates the RM_DataObject_ec constraint of '<em>Resource Measurement</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceMeasurement_RM_DataObject_ec(ResourceMeasurement resourceMeasurement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RESOURCE_MEASUREMENT,
				 resourceMeasurement,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "RM_DataObject_ec",
				 RESOURCE_MEASUREMENT__RM_DATA_OBJECT_EC__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRuleTrigger(RuleTrigger ruleTrigger, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)ruleTrigger, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validateRuleTrigger_Rule_Trig_Event_Ins_Correct_Events(ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validateRuleTrigger_Rule_Trig_Correct_Action(ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validateRuleTrigger_Rule_Trig_Event_Metric_EC(ruleTrigger, diagnostics, context);
		if (result || diagnostics != null) result &= validateRuleTrigger_Rule_Trig_Scal_Policies_of_Correct_Dep_Model(ruleTrigger, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Rule_Trig_Event_Ins_Correct_Events constraint of '<em>Rule Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RULE_TRIGGER__RULE_TRIG_EVENT_INS_CORRECT_EVENTS__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tif (self.scalabilityRule.relatedEvent.oclIsKindOf(camel::scalability::SimpleEvent)) then (self.eventInstances->size() = 1 and self.eventInstances->exists(p | p.onEvent.oclAsType(camel::scalability::SimpleEvent) = self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::SimpleEvent)))\n" +
		"\t\t\t\t\t\t\t\telse self.eventInstances->forAll(p | self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::EventPattern).includesEvent(p.onEvent)) \n" +
		"\t\t\t\t\t\t\t\tendif";

	/**
	 * Validates the Rule_Trig_Event_Ins_Correct_Events constraint of '<em>Rule Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRuleTrigger_Rule_Trig_Event_Ins_Correct_Events(RuleTrigger ruleTrigger, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RULE_TRIGGER,
				 ruleTrigger,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Rule_Trig_Event_Ins_Correct_Events",
				 RULE_TRIGGER__RULE_TRIG_EVENT_INS_CORRECT_EVENTS__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Rule_Trig_Correct_Action constraint of '<em>Rule Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RULE_TRIGGER__RULE_TRIG_CORRECT_ACTION__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\t(self.actionRealizations->size() = self.scalabilityRule.mapsToActions->size()) and (self.actionRealizations->forAll(p | self.scalabilityRule.mapsToActions->exists(q | q = p.action)))";

	/**
	 * Validates the Rule_Trig_Correct_Action constraint of '<em>Rule Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRuleTrigger_Rule_Trig_Correct_Action(RuleTrigger ruleTrigger, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RULE_TRIGGER,
				 ruleTrigger,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Rule_Trig_Correct_Action",
				 RULE_TRIGGER__RULE_TRIG_CORRECT_ACTION__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Rule_Trig_Event_Metric_EC constraint of '<em>Rule Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RULE_TRIGGER__RULE_TRIG_EVENT_METRIC_EC__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tif (self.scalabilityRule.relatedEvent.oclIsTypeOf(camel::scalability::NonFunctionalEvent)) then (self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::NonFunctionalEvent).condition.metric.objectBinding.executionContext = executionContext)\n" +
		"\t\t\t\t\t\t\t\telse \n" +
		"\t\t\t\t\t\t\t\t\tif (self.scalabilityRule.relatedEvent.oclIsKindOf(camel::scalability::EventPattern)) then self.scalabilityRule.relatedEvent.oclAsType(camel::scalability::EventPattern).relatedToExecutionContext(executionContext)\n" +
		"\t\t\t\t\t\t\t\t\telse true endif\n" +
		"\t\t\t\t\t\t\t\tendif";

	/**
	 * Validates the Rule_Trig_Event_Metric_EC constraint of '<em>Rule Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRuleTrigger_Rule_Trig_Event_Metric_EC(RuleTrigger ruleTrigger, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RULE_TRIGGER,
				 ruleTrigger,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Rule_Trig_Event_Metric_EC",
				 RULE_TRIGGER__RULE_TRIG_EVENT_METRIC_EC__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the Rule_Trig_Scal_Policies_of_Correct_Dep_Model constraint of '<em>Rule Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RULE_TRIGGER__RULE_TRIG_SCAL_POLICIES_OF_CORRECT_DEP_MODEL__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tscalabilityRule.invariantPolicies->forAll(p | \n" +
		"\t\t\t\t\t\t\t\t\tif (p.oclIsTypeOf(camel::scalability::HorizontalScalabilityPolicy) and p.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component <> null) then executionContext.involvesDeployment.components->includes(p.oclAsType(camel::scalability::HorizontalScalabilityPolicy).component)\n" +
		"\t\t\t\t\t\t\t\t\telse \n" +
		"\t\t\t\t\t\t\t\t\t\tif (p.oclIsTypeOf(camel::scalability::VerticalScalabilityPolicy) and p.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm <> null) then executionContext.involvesDeployment.components->includes(p.oclAsType(camel::scalability::VerticalScalabilityPolicy).vm)\n" +
		"\t\t\t\t\t\t\t\t\t\telse true\n" +
		"\t\t\t\t\t\t\t\t\t\tendif\n" +
		"\t\t\t\t\t\t\t\t\tendif \n" +
		"\t\t\t\t\t\t\t\t)";

	/**
	 * Validates the Rule_Trig_Scal_Policies_of_Correct_Dep_Model constraint of '<em>Rule Trigger</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRuleTrigger_Rule_Trig_Scal_Policies_of_Correct_Dep_Model(RuleTrigger ruleTrigger, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.RULE_TRIGGER,
				 ruleTrigger,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Rule_Trig_Scal_Policies_of_Correct_Dep_Model",
				 RULE_TRIGGER__RULE_TRIG_SCAL_POLICIES_OF_CORRECT_DEP_MODEL__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSLOAssessment(SLOAssessment sloAssessment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)sloAssessment, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validateSLOAssessment_SLOAss_Same_metric(sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validateSLOAssessment_SLOAss_itSLO_in_Reqs_for_EC(sloAssessment, diagnostics, context);
		if (result || diagnostics != null) result &= validateSLOAssessment_SLOAss_same_exec_context(sloAssessment, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the SLOAss_Same_metric constraint of '<em>SLO Assessment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SLO_ASSESSMENT__SLO_ASS_SAME_METRIC__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tmeasurement.ofMetric = itSLO.customServiceLevel.metric";

	/**
	 * Validates the SLOAss_Same_metric constraint of '<em>SLO Assessment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSLOAssessment_SLOAss_Same_metric(SLOAssessment sloAssessment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.SLO_ASSESSMENT,
				 sloAssessment,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "SLOAss_Same_metric",
				 SLO_ASSESSMENT__SLO_ASS_SAME_METRIC__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the SLOAss_itSLO_in_Reqs_for_EC constraint of '<em>SLO Assessment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SLO_ASSESSMENT__SLO_ASS_IT_SLO_IN_REQS_FOR_EC__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tself.inExecutionContext.basedOnRequirements.requirement->includes(self.itSLO)";

	/**
	 * Validates the SLOAss_itSLO_in_Reqs_for_EC constraint of '<em>SLO Assessment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSLOAssessment_SLOAss_itSLO_in_Reqs_for_EC(SLOAssessment sloAssessment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.SLO_ASSESSMENT,
				 sloAssessment,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "SLOAss_itSLO_in_Reqs_for_EC",
				 SLO_ASSESSMENT__SLO_ASS_IT_SLO_IN_REQS_FOR_EC__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the SLOAss_same_exec_context constraint of '<em>SLO Assessment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String SLO_ASSESSMENT__SLO_ASS_SAME_EXEC_CONTEXT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\t\t\tinExecutionContext = measurement.inExecutionContext";

	/**
	 * Validates the SLOAss_same_exec_context constraint of '<em>SLO Assessment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateSLOAssessment_SLOAss_same_exec_context(SLOAssessment sloAssessment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(ExecutionPackage.Literals.SLO_ASSESSMENT,
				 sloAssessment,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "SLOAss_same_exec_context",
				 SLO_ASSESSMENT__SLO_ASS_SAME_EXEC_CONTEXT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
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

} //ExecutionValidator
