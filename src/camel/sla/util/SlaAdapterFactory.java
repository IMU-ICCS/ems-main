/**
 */
package camel.sla.util;

import camel.Requirement;

import camel.sla.*;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notifier;

import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * The <b>Adapter Factory</b> for the model.
 * It provides an adapter <code>createXXX</code> method for each class of the model.
 * <!-- end-user-doc -->
 * @see camel.sla.SlaPackage
 * @generated
 */
public class SlaAdapterFactory extends AdapterFactoryImpl {
	/**
	 * The cached model package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static SlaPackage modelPackage;

	/**
	 * Creates an instance of the adapter factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlaAdapterFactory() {
		if (modelPackage == null) {
			modelPackage = SlaPackage.eINSTANCE;
		}
	}

	/**
	 * Returns whether this factory is applicable for the type of the object.
	 * <!-- begin-user-doc -->
	 * This implementation returns <code>true</code> if the object is either the model's package or is an instance object of the model.
	 * <!-- end-user-doc -->
	 * @return whether this factory is applicable for the type of the object.
	 * @generated
	 */
	@Override
	public boolean isFactoryForType(Object object) {
		if (object == modelPackage) {
			return true;
		}
		if (object instanceof EObject) {
			return ((EObject)object).eClass().getEPackage() == modelPackage;
		}
		return false;
	}

	/**
	 * The switch that delegates to the <code>createXXX</code> methods.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SlaSwitch<Adapter> modelSwitch =
		new SlaSwitch<Adapter>() {
			@Override
			public Adapter caseAgreementContextType(AgreementContextType object) {
				return createAgreementContextTypeAdapter();
			}
			@Override
			public Adapter caseAgreementType(AgreementType object) {
				return createAgreementTypeAdapter();
			}
			@Override
			public Adapter caseAssessmentIntervalType(AssessmentIntervalType object) {
				return createAssessmentIntervalTypeAdapter();
			}
			@Override
			public Adapter caseBusinessValueListType(BusinessValueListType object) {
				return createBusinessValueListTypeAdapter();
			}
			@Override
			public Adapter caseCompensationType(CompensationType object) {
				return createCompensationTypeAdapter();
			}
			@Override
			public Adapter caseGuaranteeTermType(GuaranteeTermType object) {
				return createGuaranteeTermTypeAdapter();
			}
			@Override
			public Adapter caseKPITargetType(KPITargetType object) {
				return createKPITargetTypeAdapter();
			}
			@Override
			public Adapter casePreferenceType(PreferenceType object) {
				return createPreferenceTypeAdapter();
			}
			@Override
			public Adapter caseServiceLevelObjectiveTemplate(ServiceLevelObjectiveTemplate object) {
				return createServiceLevelObjectiveTemplateAdapter();
			}
			@Override
			public Adapter caseServiceLevelObjectiveType(ServiceLevelObjectiveType object) {
				return createServiceLevelObjectiveTypeAdapter();
			}
			@Override
			public Adapter caseServiceSelectorType(ServiceSelectorType object) {
				return createServiceSelectorTypeAdapter();
			}
			@Override
			public Adapter caseTermCompositorType(TermCompositorType object) {
				return createTermCompositorTypeAdapter();
			}
			@Override
			public Adapter caseTermTreeType(TermTreeType object) {
				return createTermTreeTypeAdapter();
			}
			@Override
			public Adapter caseRequirement(Requirement object) {
				return createRequirementAdapter();
			}
			@Override
			public Adapter defaultCase(EObject object) {
				return createEObjectAdapter();
			}
		};

	/**
	 * Creates an adapter for the <code>target</code>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param target the object to adapt.
	 * @return the adapter for the <code>target</code>.
	 * @generated
	 */
	@Override
	public Adapter createAdapter(Notifier target) {
		return modelSwitch.doSwitch((EObject)target);
	}


	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.AgreementContextType <em>Agreement Context Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.AgreementContextType
	 * @generated
	 */
	public Adapter createAgreementContextTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.AgreementType <em>Agreement Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.AgreementType
	 * @generated
	 */
	public Adapter createAgreementTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.AssessmentIntervalType <em>Assessment Interval Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.AssessmentIntervalType
	 * @generated
	 */
	public Adapter createAssessmentIntervalTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.BusinessValueListType <em>Business Value List Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.BusinessValueListType
	 * @generated
	 */
	public Adapter createBusinessValueListTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.CompensationType <em>Compensation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.CompensationType
	 * @generated
	 */
	public Adapter createCompensationTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.GuaranteeTermType <em>Guarantee Term Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.GuaranteeTermType
	 * @generated
	 */
	public Adapter createGuaranteeTermTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.KPITargetType <em>KPI Target Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.KPITargetType
	 * @generated
	 */
	public Adapter createKPITargetTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.PreferenceType <em>Preference Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.PreferenceType
	 * @generated
	 */
	public Adapter createPreferenceTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.ServiceLevelObjectiveTemplate <em>Service Level Objective Template</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.ServiceLevelObjectiveTemplate
	 * @generated
	 */
	public Adapter createServiceLevelObjectiveTemplateAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.ServiceLevelObjectiveType <em>Service Level Objective Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.ServiceLevelObjectiveType
	 * @generated
	 */
	public Adapter createServiceLevelObjectiveTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.ServiceSelectorType <em>Service Selector Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.ServiceSelectorType
	 * @generated
	 */
	public Adapter createServiceSelectorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.TermCompositorType <em>Term Compositor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.TermCompositorType
	 * @generated
	 */
	public Adapter createTermCompositorTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.sla.TermTreeType <em>Term Tree Type</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.sla.TermTreeType
	 * @generated
	 */
	public Adapter createTermTreeTypeAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for an object of class '{@link camel.Requirement <em>Requirement</em>}'.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null so that we can easily ignore cases;
	 * it's useful to ignore a case when inheritance will catch all the cases anyway.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @see camel.Requirement
	 * @generated
	 */
	public Adapter createRequirementAdapter() {
		return null;
	}

	/**
	 * Creates a new adapter for the default case.
	 * <!-- begin-user-doc -->
	 * This default implementation returns null.
	 * <!-- end-user-doc -->
	 * @return the new adapter.
	 * @generated
	 */
	public Adapter createEObjectAdapter() {
		return null;
	}

} //SlaAdapterFactory
