/**
 */
package camel.sla.impl;

import camel.sla.*;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EFactoryImpl;

import org.eclipse.emf.ecore.plugin.EcorePlugin;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SlaFactoryImpl extends EFactoryImpl implements SlaFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static SlaFactory init() {
		try {
			SlaFactory theSlaFactory = (SlaFactory)EPackage.Registry.INSTANCE.getEFactory(SlaPackage.eNS_URI);
			if (theSlaFactory != null) {
				return theSlaFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new SlaFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlaFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case SlaPackage.AGREEMENT_CONTEXT_TYPE: return (EObject)createAgreementContextType();
			case SlaPackage.AGREEMENT_TYPE: return (EObject)createAgreementType();
			case SlaPackage.ASSESSMENT_INTERVAL_TYPE: return (EObject)createAssessmentIntervalType();
			case SlaPackage.BUSINESS_VALUE_LIST_TYPE: return (EObject)createBusinessValueListType();
			case SlaPackage.COMPENSATION_TYPE: return (EObject)createCompensationType();
			case SlaPackage.GUARANTEE_TERM_TYPE: return (EObject)createGuaranteeTermType();
			case SlaPackage.KPI_TARGET_TYPE: return (EObject)createKPITargetType();
			case SlaPackage.PREFERENCE_TYPE: return (EObject)createPreferenceType();
			case SlaPackage.SERVICE_LEVEL_OBJECTIVE_TEMPLATE: return (EObject)createServiceLevelObjectiveTemplate();
			case SlaPackage.SERVICE_LEVEL_OBJECTIVE_TYPE: return (EObject)createServiceLevelObjectiveType();
			case SlaPackage.SERVICE_SELECTOR_TYPE: return (EObject)createServiceSelectorType();
			case SlaPackage.TERM_COMPOSITOR_TYPE: return (EObject)createTermCompositorType();
			case SlaPackage.TERM_TREE_TYPE: return (EObject)createTermTreeType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case SlaPackage.AGREEMENT_ROLE_TYPE:
				return createAgreementRoleTypeFromString(eDataType, initialValue);
			case SlaPackage.SERVICE_ROLE_TYPE:
				return createServiceRoleTypeFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case SlaPackage.AGREEMENT_ROLE_TYPE:
				return convertAgreementRoleTypeToString(eDataType, instanceValue);
			case SlaPackage.SERVICE_ROLE_TYPE:
				return convertServiceRoleTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgreementContextType createAgreementContextType() {
		AgreementContextTypeImpl agreementContextType = new AgreementContextTypeImpl();
		return agreementContextType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgreementType createAgreementType() {
		AgreementTypeImpl agreementType = new AgreementTypeImpl();
		return agreementType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AssessmentIntervalType createAssessmentIntervalType() {
		AssessmentIntervalTypeImpl assessmentIntervalType = new AssessmentIntervalTypeImpl();
		return assessmentIntervalType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessValueListType createBusinessValueListType() {
		BusinessValueListTypeImpl businessValueListType = new BusinessValueListTypeImpl();
		return businessValueListType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CompensationType createCompensationType() {
		CompensationTypeImpl compensationType = new CompensationTypeImpl();
		return compensationType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GuaranteeTermType createGuaranteeTermType() {
		GuaranteeTermTypeImpl guaranteeTermType = new GuaranteeTermTypeImpl();
		return guaranteeTermType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KPITargetType createKPITargetType() {
		KPITargetTypeImpl kpiTargetType = new KPITargetTypeImpl();
		return kpiTargetType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PreferenceType createPreferenceType() {
		PreferenceTypeImpl preferenceType = new PreferenceTypeImpl();
		return preferenceType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceLevelObjectiveTemplate createServiceLevelObjectiveTemplate() {
		ServiceLevelObjectiveTemplateImpl serviceLevelObjectiveTemplate = new ServiceLevelObjectiveTemplateImpl();
		return serviceLevelObjectiveTemplate;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceLevelObjectiveType createServiceLevelObjectiveType() {
		ServiceLevelObjectiveTypeImpl serviceLevelObjectiveType = new ServiceLevelObjectiveTypeImpl();
		return serviceLevelObjectiveType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceSelectorType createServiceSelectorType() {
		ServiceSelectorTypeImpl serviceSelectorType = new ServiceSelectorTypeImpl();
		return serviceSelectorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TermCompositorType createTermCompositorType() {
		TermCompositorTypeImpl termCompositorType = new TermCompositorTypeImpl();
		return termCompositorType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TermTreeType createTermTreeType() {
		TermTreeTypeImpl termTreeType = new TermTreeTypeImpl();
		return termTreeType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgreementRoleType createAgreementRoleTypeFromString(EDataType eDataType, String initialValue) {
		AgreementRoleType result = AgreementRoleType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertAgreementRoleTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceRoleType createServiceRoleTypeFromString(EDataType eDataType, String initialValue) {
		ServiceRoleType result = ServiceRoleType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertServiceRoleTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlaPackage getSlaPackage() {
		return (SlaPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static SlaPackage getPackage() {
		return SlaPackage.eINSTANCE;
	}

} //SlaFactoryImpl
