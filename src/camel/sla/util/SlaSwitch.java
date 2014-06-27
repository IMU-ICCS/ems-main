/**
 */
package camel.sla.util;

import camel.Requirement;

import camel.sla.*;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.util.Switch;

/**
 * <!-- begin-user-doc -->
 * The <b>Switch</b> for the model's inheritance hierarchy.
 * It supports the call {@link #doSwitch(EObject) doSwitch(object)}
 * to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object
 * and proceeding up the inheritance hierarchy
 * until a non-null result is returned,
 * which is the result of the switch.
 * <!-- end-user-doc -->
 * @see camel.sla.SlaPackage
 * @generated
 */
public class SlaSwitch<T> extends Switch<T> {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SlaPackage modelPackage;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlaSwitch() {
		if (modelPackage == null) {
			modelPackage = SlaPackage.eINSTANCE;
		}
	}

	/**
	 * Checks whether this is a switch for the given package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @parameter ePackage the package in question.
	 * @return whether this is a switch for the given package.
	 * @generated
	 */
	@Override
	protected boolean isSwitchFor(EPackage ePackage) {
		return ePackage == modelPackage;
	}

	/**
	 * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that result.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the first non-null result returned by a <code>caseXXX</code> call.
	 * @generated
	 */
	@Override
	protected T doSwitch(int classifierID, EObject theEObject) {
		switch (classifierID) {
			case SlaPackage.AGREEMENT_CONTEXT_TYPE: {
				AgreementContextType agreementContextType = (AgreementContextType)theEObject;
				T result = caseAgreementContextType(agreementContextType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.AGREEMENT_TYPE: {
				AgreementType agreementType = (AgreementType)theEObject;
				T result = caseAgreementType(agreementType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.ASSESSMENT_INTERVAL_TYPE: {
				AssessmentIntervalType assessmentIntervalType = (AssessmentIntervalType)theEObject;
				T result = caseAssessmentIntervalType(assessmentIntervalType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.BUSINESS_VALUE_LIST_TYPE: {
				BusinessValueListType businessValueListType = (BusinessValueListType)theEObject;
				T result = caseBusinessValueListType(businessValueListType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.COMPENSATION_TYPE: {
				CompensationType compensationType = (CompensationType)theEObject;
				T result = caseCompensationType(compensationType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.GUARANTEE_TERM_TYPE: {
				GuaranteeTermType guaranteeTermType = (GuaranteeTermType)theEObject;
				T result = caseGuaranteeTermType(guaranteeTermType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.KPI_TARGET_TYPE: {
				KPITargetType kpiTargetType = (KPITargetType)theEObject;
				T result = caseKPITargetType(kpiTargetType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.PREFERENCE_TYPE: {
				PreferenceType preferenceType = (PreferenceType)theEObject;
				T result = casePreferenceType(preferenceType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.SERVICE_LEVEL_OBJECTIVE_TEMPLATE: {
				ServiceLevelObjectiveTemplate serviceLevelObjectiveTemplate = (ServiceLevelObjectiveTemplate)theEObject;
				T result = caseServiceLevelObjectiveTemplate(serviceLevelObjectiveTemplate);
				if (result == null) result = caseRequirement(serviceLevelObjectiveTemplate);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.SERVICE_LEVEL_OBJECTIVE_TYPE: {
				ServiceLevelObjectiveType serviceLevelObjectiveType = (ServiceLevelObjectiveType)theEObject;
				T result = caseServiceLevelObjectiveType(serviceLevelObjectiveType);
				if (result == null) result = caseRequirement(serviceLevelObjectiveType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.SERVICE_SELECTOR_TYPE: {
				ServiceSelectorType serviceSelectorType = (ServiceSelectorType)theEObject;
				T result = caseServiceSelectorType(serviceSelectorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.TERM_COMPOSITOR_TYPE: {
				TermCompositorType termCompositorType = (TermCompositorType)theEObject;
				T result = caseTermCompositorType(termCompositorType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			case SlaPackage.TERM_TREE_TYPE: {
				TermTreeType termTreeType = (TermTreeType)theEObject;
				T result = caseTermTreeType(termTreeType);
				if (result == null) result = defaultCase(theEObject);
				return result;
			}
			default: return defaultCase(theEObject);
		}
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agreement Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agreement Context Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgreementContextType(AgreementContextType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Agreement Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Agreement Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAgreementType(AgreementType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Assessment Interval Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Assessment Interval Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseAssessmentIntervalType(AssessmentIntervalType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Business Value List Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Business Value List Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseBusinessValueListType(BusinessValueListType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Compensation Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Compensation Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseCompensationType(CompensationType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Guarantee Term Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Guarantee Term Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseGuaranteeTermType(GuaranteeTermType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>KPI Target Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>KPI Target Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseKPITargetType(KPITargetType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Preference Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Preference Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T casePreferenceType(PreferenceType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service Level Objective Template</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service Level Objective Template</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceLevelObjectiveTemplate(ServiceLevelObjectiveTemplate object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service Level Objective Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service Level Objective Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceLevelObjectiveType(ServiceLevelObjectiveType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Service Selector Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Service Selector Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseServiceSelectorType(ServiceSelectorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Term Compositor Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Term Compositor Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTermCompositorType(TermCompositorType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Term Tree Type</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Term Tree Type</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseTermTreeType(TermTreeType object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>Requirement</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>Requirement</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
	 * @generated
	 */
	public T caseRequirement(Requirement object) {
		return null;
	}

	/**
	 * Returns the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * <!-- begin-user-doc -->
	 * This implementation returns null;
	 * returning a non-null result will terminate the switch, but this is the last case anyway.
	 * <!-- end-user-doc -->
	 * @param object the target of the switch.
	 * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
	 * @see #doSwitch(org.eclipse.emf.ecore.EObject)
	 * @generated
	 */
	@Override
	public T defaultCase(EObject object) {
		return null;
	}

} //SlaSwitch
