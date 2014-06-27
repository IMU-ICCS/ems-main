/**
 */
package camel.sla;

import camel.CamelPackage;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see camel.sla.SlaFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface SlaPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "sla";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/camel/wsag";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "sla";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SlaPackage eINSTANCE = camel.sla.impl.SlaPackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.sla.impl.AgreementContextTypeImpl <em>Agreement Context Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.AgreementContextTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getAgreementContextType()
	 * @generated
	 */
	int AGREEMENT_CONTEXT_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Template Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE__TEMPLATE_ID = 0;

	/**
	 * The feature id for the '<em><b>Template Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE__TEMPLATE_NAME = 1;

	/**
	 * The feature id for the '<em><b>Service Provider</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE__SERVICE_PROVIDER = 2;

	/**
	 * The feature id for the '<em><b>Service Consumer</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE__SERVICE_CONSUMER = 3;

	/**
	 * The feature id for the '<em><b>Expiration Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE__EXPIRATION_TIME = 4;

	/**
	 * The feature id for the '<em><b>Agreement Initiator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE__AGREEMENT_INITIATOR = 5;

	/**
	 * The feature id for the '<em><b>Agreement Responder</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE__AGREEMENT_RESPONDER = 6;

	/**
	 * The feature id for the '<em><b>Deployment Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE__DEPLOYMENT_MODEL = 7;

	/**
	 * The number of structural features of the '<em>Agreement Context Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Agreement Context Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_CONTEXT_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.AgreementTypeImpl <em>Agreement Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.AgreementTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getAgreementType()
	 * @generated
	 */
	int AGREEMENT_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Agreement Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_TYPE__AGREEMENT_ID = 1;

	/**
	 * The feature id for the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_TYPE__CONTEXT = 2;

	/**
	 * The feature id for the '<em><b>Terms</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_TYPE__TERMS = 3;

	/**
	 * The feature id for the '<em><b>Slos</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_TYPE__SLOS = 4;

	/**
	 * The number of structural features of the '<em>Agreement Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_TYPE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Agreement Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int AGREEMENT_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.AssessmentIntervalTypeImpl <em>Assessment Interval Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.AssessmentIntervalTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getAssessmentIntervalType()
	 * @generated
	 */
	int ASSESSMENT_INTERVAL_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Time Interval</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSESSMENT_INTERVAL_TYPE__TIME_INTERVAL = 0;

	/**
	 * The feature id for the '<em><b>Count</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSESSMENT_INTERVAL_TYPE__COUNT = 1;

	/**
	 * The feature id for the '<em><b>Time Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSESSMENT_INTERVAL_TYPE__TIME_UNIT = 2;

	/**
	 * The number of structural features of the '<em>Assessment Interval Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSESSMENT_INTERVAL_TYPE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Assessment Interval Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ASSESSMENT_INTERVAL_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.BusinessValueListTypeImpl <em>Business Value List Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.BusinessValueListTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getBusinessValueListType()
	 * @generated
	 */
	int BUSINESS_VALUE_LIST_TYPE = 3;

	/**
	 * The feature id for the '<em><b>Importance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_VALUE_LIST_TYPE__IMPORTANCE = 0;

	/**
	 * The feature id for the '<em><b>Penalty</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_VALUE_LIST_TYPE__PENALTY = 1;

	/**
	 * The feature id for the '<em><b>Reward</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_VALUE_LIST_TYPE__REWARD = 2;

	/**
	 * The feature id for the '<em><b>Preference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_VALUE_LIST_TYPE__PREFERENCE = 3;

	/**
	 * The feature id for the '<em><b>Custom Business Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_VALUE_LIST_TYPE__CUSTOM_BUSINESS_VALUE = 4;

	/**
	 * The number of structural features of the '<em>Business Value List Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_VALUE_LIST_TYPE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Business Value List Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BUSINESS_VALUE_LIST_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.CompensationTypeImpl <em>Compensation Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.CompensationTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getCompensationType()
	 * @generated
	 */
	int COMPENSATION_TYPE = 4;

	/**
	 * The feature id for the '<em><b>Assessment Interval</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPENSATION_TYPE__ASSESSMENT_INTERVAL = 0;

	/**
	 * The feature id for the '<em><b>Value Unit</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPENSATION_TYPE__VALUE_UNIT = 1;

	/**
	 * The feature id for the '<em><b>Value Expression</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPENSATION_TYPE__VALUE_EXPRESSION = 2;

	/**
	 * The number of structural features of the '<em>Compensation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPENSATION_TYPE_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Compensation Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPENSATION_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.GuaranteeTermTypeImpl <em>Guarantee Term Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.GuaranteeTermTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getGuaranteeTermType()
	 * @generated
	 */
	int GUARANTEE_TERM_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Service Scope</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARANTEE_TERM_TYPE__SERVICE_SCOPE = 0;

	/**
	 * The feature id for the '<em><b>Service Level Objective</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARANTEE_TERM_TYPE__SERVICE_LEVEL_OBJECTIVE = 1;

	/**
	 * The feature id for the '<em><b>Business Value List</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARANTEE_TERM_TYPE__BUSINESS_VALUE_LIST = 2;

	/**
	 * The feature id for the '<em><b>Obligated</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARANTEE_TERM_TYPE__OBLIGATED = 3;

	/**
	 * The feature id for the '<em><b>Qualifying Condition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARANTEE_TERM_TYPE__QUALIFYING_CONDITION = 4;

	/**
	 * The number of structural features of the '<em>Guarantee Term Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARANTEE_TERM_TYPE_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Guarantee Term Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GUARANTEE_TERM_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.KPITargetTypeImpl <em>KPI Target Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.KPITargetTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getKPITargetType()
	 * @generated
	 */
	int KPI_TARGET_TYPE = 6;

	/**
	 * The feature id for the '<em><b>KPI Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KPI_TARGET_TYPE__KPI_NAME = 0;

	/**
	 * The feature id for the '<em><b>Custom Service Level</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KPI_TARGET_TYPE__CUSTOM_SERVICE_LEVEL = 1;

	/**
	 * The number of structural features of the '<em>KPI Target Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KPI_TARGET_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>KPI Target Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int KPI_TARGET_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.PreferenceTypeImpl <em>Preference Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.PreferenceTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getPreferenceType()
	 * @generated
	 */
	int PREFERENCE_TYPE = 7;

	/**
	 * The feature id for the '<em><b>Service Term Reference</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCE_TYPE__SERVICE_TERM_REFERENCE = 0;

	/**
	 * The feature id for the '<em><b>Utility</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCE_TYPE__UTILITY = 1;

	/**
	 * The number of structural features of the '<em>Preference Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCE_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Preference Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PREFERENCE_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.ServiceLevelObjectiveTemplateImpl <em>Service Level Objective Template</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.ServiceLevelObjectiveTemplateImpl
	 * @see camel.sla.impl.SlaPackageImpl#getServiceLevelObjectiveTemplate()
	 * @generated
	 */
	int SERVICE_LEVEL_OBJECTIVE_TEMPLATE = 8;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TEMPLATE__PRIORITY = CamelPackage.REQUIREMENT__PRIORITY;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TEMPLATE__ID = CamelPackage.REQUIREMENT__ID;

	/**
	 * The feature id for the '<em><b>Condition</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TEMPLATE__CONDITION = CamelPackage.REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Service Level Objective Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TEMPLATE_FEATURE_COUNT = CamelPackage.REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Service Level Objective Template</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TEMPLATE_OPERATION_COUNT = CamelPackage.REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.ServiceLevelObjectiveTypeImpl <em>Service Level Objective Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.ServiceLevelObjectiveTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getServiceLevelObjectiveType()
	 * @generated
	 */
	int SERVICE_LEVEL_OBJECTIVE_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TYPE__PRIORITY = CamelPackage.REQUIREMENT__PRIORITY;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TYPE__ID = CamelPackage.REQUIREMENT__ID;

	/**
	 * The feature id for the '<em><b>KPI Target</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TYPE__KPI_TARGET = CamelPackage.REQUIREMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Custom Service Level</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TYPE__CUSTOM_SERVICE_LEVEL = CamelPackage.REQUIREMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Service Level Objective Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TYPE_FEATURE_COUNT = CamelPackage.REQUIREMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Service Level Objective Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_LEVEL_OBJECTIVE_TYPE_OPERATION_COUNT = CamelPackage.REQUIREMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.ServiceSelectorTypeImpl <em>Service Selector Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.ServiceSelectorTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getServiceSelectorType()
	 * @generated
	 */
	int SERVICE_SELECTOR_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Service Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SELECTOR_TYPE__SERVICE_NAME = 0;

	/**
	 * The feature id for the '<em><b>Service Reference</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SELECTOR_TYPE__SERVICE_REFERENCE = 1;

	/**
	 * The number of structural features of the '<em>Service Selector Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SELECTOR_TYPE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Service Selector Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SERVICE_SELECTOR_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.impl.TermCompositorTypeImpl <em>Term Compositor Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.TermCompositorTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getTermCompositorType()
	 * @generated
	 */
	int TERM_COMPOSITOR_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_COMPOSITOR_TYPE__ID = 0;

	/**
	 * The feature id for the '<em><b>Exactly One</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_COMPOSITOR_TYPE__EXACTLY_ONE = 1;

	/**
	 * The feature id for the '<em><b>One Or More</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_COMPOSITOR_TYPE__ONE_OR_MORE = 2;

	/**
	 * The feature id for the '<em><b>All</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_COMPOSITOR_TYPE__ALL = 3;

	/**
	 * The feature id for the '<em><b>Service Reference</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_COMPOSITOR_TYPE__SERVICE_REFERENCE = 4;

	/**
	 * The feature id for the '<em><b>Guarantee Term</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_COMPOSITOR_TYPE__GUARANTEE_TERM = 5;

	/**
	 * The number of structural features of the '<em>Term Compositor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_COMPOSITOR_TYPE_FEATURE_COUNT = 6;

	/**
	 * The operation id for the '<em>Check Recursiveness</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_COMPOSITOR_TYPE___CHECK_RECURSIVENESS__TERMCOMPOSITORTYPE_TERMCOMPOSITORTYPE_ELIST = 0;

	/**
	 * The number of operations of the '<em>Term Compositor Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_COMPOSITOR_TYPE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.sla.impl.TermTreeTypeImpl <em>Term Tree Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.impl.TermTreeTypeImpl
	 * @see camel.sla.impl.SlaPackageImpl#getTermTreeType()
	 * @generated
	 */
	int TERM_TREE_TYPE = 12;

	/**
	 * The feature id for the '<em><b>All</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_TREE_TYPE__ALL = 0;

	/**
	 * The number of structural features of the '<em>Term Tree Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_TREE_TYPE_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Term Tree Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TERM_TREE_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.sla.AgreementRoleType <em>Agreement Role Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.AgreementRoleType
	 * @see camel.sla.impl.SlaPackageImpl#getAgreementRoleType()
	 * @generated
	 */
	int AGREEMENT_ROLE_TYPE = 13;

	/**
	 * The meta object id for the '{@link camel.sla.ServiceRoleType <em>Service Role Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.sla.ServiceRoleType
	 * @see camel.sla.impl.SlaPackageImpl#getServiceRoleType()
	 * @generated
	 */
	int SERVICE_ROLE_TYPE = 14;


	/**
	 * Returns the meta object for class '{@link camel.sla.AgreementContextType <em>Agreement Context Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agreement Context Type</em>'.
	 * @see camel.sla.AgreementContextType
	 * @generated
	 */
	EClass getAgreementContextType();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.AgreementContextType#getTemplateId <em>Template Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Template Id</em>'.
	 * @see camel.sla.AgreementContextType#getTemplateId()
	 * @see #getAgreementContextType()
	 * @generated
	 */
	EAttribute getAgreementContextType_TemplateId();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.AgreementContextType#getTemplateName <em>Template Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Template Name</em>'.
	 * @see camel.sla.AgreementContextType#getTemplateName()
	 * @see #getAgreementContextType()
	 * @generated
	 */
	EAttribute getAgreementContextType_TemplateName();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.AgreementContextType#getServiceProvider <em>Service Provider</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Service Provider</em>'.
	 * @see camel.sla.AgreementContextType#getServiceProvider()
	 * @see #getAgreementContextType()
	 * @generated
	 */
	EAttribute getAgreementContextType_ServiceProvider();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.AgreementContextType#getServiceConsumer <em>Service Consumer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Service Consumer</em>'.
	 * @see camel.sla.AgreementContextType#getServiceConsumer()
	 * @see #getAgreementContextType()
	 * @generated
	 */
	EAttribute getAgreementContextType_ServiceConsumer();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.AgreementContextType#getExpirationTime <em>Expiration Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Expiration Time</em>'.
	 * @see camel.sla.AgreementContextType#getExpirationTime()
	 * @see #getAgreementContextType()
	 * @generated
	 */
	EAttribute getAgreementContextType_ExpirationTime();

	/**
	 * Returns the meta object for the reference '{@link camel.sla.AgreementContextType#getAgreementInitiator <em>Agreement Initiator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Agreement Initiator</em>'.
	 * @see camel.sla.AgreementContextType#getAgreementInitiator()
	 * @see #getAgreementContextType()
	 * @generated
	 */
	EReference getAgreementContextType_AgreementInitiator();

	/**
	 * Returns the meta object for the reference '{@link camel.sla.AgreementContextType#getAgreementResponder <em>Agreement Responder</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Agreement Responder</em>'.
	 * @see camel.sla.AgreementContextType#getAgreementResponder()
	 * @see #getAgreementContextType()
	 * @generated
	 */
	EReference getAgreementContextType_AgreementResponder();

	/**
	 * Returns the meta object for the reference '{@link camel.sla.AgreementContextType#getDeploymentModel <em>Deployment Model</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Deployment Model</em>'.
	 * @see camel.sla.AgreementContextType#getDeploymentModel()
	 * @see #getAgreementContextType()
	 * @generated
	 */
	EReference getAgreementContextType_DeploymentModel();

	/**
	 * Returns the meta object for class '{@link camel.sla.AgreementType <em>Agreement Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Agreement Type</em>'.
	 * @see camel.sla.AgreementType
	 * @generated
	 */
	EClass getAgreementType();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.AgreementType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.sla.AgreementType#getName()
	 * @see #getAgreementType()
	 * @generated
	 */
	EAttribute getAgreementType_Name();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.AgreementType#getAgreementId <em>Agreement Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Agreement Id</em>'.
	 * @see camel.sla.AgreementType#getAgreementId()
	 * @see #getAgreementType()
	 * @generated
	 */
	EAttribute getAgreementType_AgreementId();

	/**
	 * Returns the meta object for the containment reference '{@link camel.sla.AgreementType#getContext <em>Context</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Context</em>'.
	 * @see camel.sla.AgreementType#getContext()
	 * @see #getAgreementType()
	 * @generated
	 */
	EReference getAgreementType_Context();

	/**
	 * Returns the meta object for the containment reference '{@link camel.sla.AgreementType#getTerms <em>Terms</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Terms</em>'.
	 * @see camel.sla.AgreementType#getTerms()
	 * @see #getAgreementType()
	 * @generated
	 */
	EReference getAgreementType_Terms();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.sla.AgreementType#getSlos <em>Slos</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Slos</em>'.
	 * @see camel.sla.AgreementType#getSlos()
	 * @see #getAgreementType()
	 * @generated
	 */
	EReference getAgreementType_Slos();

	/**
	 * Returns the meta object for class '{@link camel.sla.AssessmentIntervalType <em>Assessment Interval Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Assessment Interval Type</em>'.
	 * @see camel.sla.AssessmentIntervalType
	 * @generated
	 */
	EClass getAssessmentIntervalType();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.AssessmentIntervalType#getTimeInterval <em>Time Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Interval</em>'.
	 * @see camel.sla.AssessmentIntervalType#getTimeInterval()
	 * @see #getAssessmentIntervalType()
	 * @generated
	 */
	EAttribute getAssessmentIntervalType_TimeInterval();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.AssessmentIntervalType#getCount <em>Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Count</em>'.
	 * @see camel.sla.AssessmentIntervalType#getCount()
	 * @see #getAssessmentIntervalType()
	 * @generated
	 */
	EAttribute getAssessmentIntervalType_Count();

	/**
	 * Returns the meta object for the reference '{@link camel.sla.AssessmentIntervalType#getTimeUnit <em>Time Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Time Unit</em>'.
	 * @see camel.sla.AssessmentIntervalType#getTimeUnit()
	 * @see #getAssessmentIntervalType()
	 * @generated
	 */
	EReference getAssessmentIntervalType_TimeUnit();

	/**
	 * Returns the meta object for class '{@link camel.sla.BusinessValueListType <em>Business Value List Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Business Value List Type</em>'.
	 * @see camel.sla.BusinessValueListType
	 * @generated
	 */
	EClass getBusinessValueListType();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.BusinessValueListType#getImportance <em>Importance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Importance</em>'.
	 * @see camel.sla.BusinessValueListType#getImportance()
	 * @see #getBusinessValueListType()
	 * @generated
	 */
	EAttribute getBusinessValueListType_Importance();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.sla.BusinessValueListType#getPenalty <em>Penalty</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Penalty</em>'.
	 * @see camel.sla.BusinessValueListType#getPenalty()
	 * @see #getBusinessValueListType()
	 * @generated
	 */
	EReference getBusinessValueListType_Penalty();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.sla.BusinessValueListType#getReward <em>Reward</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Reward</em>'.
	 * @see camel.sla.BusinessValueListType#getReward()
	 * @see #getBusinessValueListType()
	 * @generated
	 */
	EReference getBusinessValueListType_Reward();

	/**
	 * Returns the meta object for the containment reference '{@link camel.sla.BusinessValueListType#getPreference <em>Preference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Preference</em>'.
	 * @see camel.sla.BusinessValueListType#getPreference()
	 * @see #getBusinessValueListType()
	 * @generated
	 */
	EReference getBusinessValueListType_Preference();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.sla.BusinessValueListType#getCustomBusinessValue <em>Custom Business Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Custom Business Value</em>'.
	 * @see camel.sla.BusinessValueListType#getCustomBusinessValue()
	 * @see #getBusinessValueListType()
	 * @generated
	 */
	EReference getBusinessValueListType_CustomBusinessValue();

	/**
	 * Returns the meta object for class '{@link camel.sla.CompensationType <em>Compensation Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Compensation Type</em>'.
	 * @see camel.sla.CompensationType
	 * @generated
	 */
	EClass getCompensationType();

	/**
	 * Returns the meta object for the containment reference '{@link camel.sla.CompensationType#getAssessmentInterval <em>Assessment Interval</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Assessment Interval</em>'.
	 * @see camel.sla.CompensationType#getAssessmentInterval()
	 * @see #getCompensationType()
	 * @generated
	 */
	EReference getCompensationType_AssessmentInterval();

	/**
	 * Returns the meta object for the reference '{@link camel.sla.CompensationType#getValueUnit <em>Value Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value Unit</em>'.
	 * @see camel.sla.CompensationType#getValueUnit()
	 * @see #getCompensationType()
	 * @generated
	 */
	EReference getCompensationType_ValueUnit();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.CompensationType#getValueExpression <em>Value Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value Expression</em>'.
	 * @see camel.sla.CompensationType#getValueExpression()
	 * @see #getCompensationType()
	 * @generated
	 */
	EAttribute getCompensationType_ValueExpression();

	/**
	 * Returns the meta object for class '{@link camel.sla.GuaranteeTermType <em>Guarantee Term Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Guarantee Term Type</em>'.
	 * @see camel.sla.GuaranteeTermType
	 * @generated
	 */
	EClass getGuaranteeTermType();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.sla.GuaranteeTermType#getServiceScope <em>Service Scope</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Service Scope</em>'.
	 * @see camel.sla.GuaranteeTermType#getServiceScope()
	 * @see #getGuaranteeTermType()
	 * @generated
	 */
	EReference getGuaranteeTermType_ServiceScope();

	/**
	 * Returns the meta object for the containment reference '{@link camel.sla.GuaranteeTermType#getServiceLevelObjective <em>Service Level Objective</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Service Level Objective</em>'.
	 * @see camel.sla.GuaranteeTermType#getServiceLevelObjective()
	 * @see #getGuaranteeTermType()
	 * @generated
	 */
	EReference getGuaranteeTermType_ServiceLevelObjective();

	/**
	 * Returns the meta object for the containment reference '{@link camel.sla.GuaranteeTermType#getBusinessValueList <em>Business Value List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Business Value List</em>'.
	 * @see camel.sla.GuaranteeTermType#getBusinessValueList()
	 * @see #getGuaranteeTermType()
	 * @generated
	 */
	EReference getGuaranteeTermType_BusinessValueList();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.GuaranteeTermType#getObligated <em>Obligated</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Obligated</em>'.
	 * @see camel.sla.GuaranteeTermType#getObligated()
	 * @see #getGuaranteeTermType()
	 * @generated
	 */
	EAttribute getGuaranteeTermType_Obligated();

	/**
	 * Returns the meta object for the containment reference '{@link camel.sla.GuaranteeTermType#getQualifyingCondition <em>Qualifying Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Qualifying Condition</em>'.
	 * @see camel.sla.GuaranteeTermType#getQualifyingCondition()
	 * @see #getGuaranteeTermType()
	 * @generated
	 */
	EReference getGuaranteeTermType_QualifyingCondition();

	/**
	 * Returns the meta object for class '{@link camel.sla.KPITargetType <em>KPI Target Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>KPI Target Type</em>'.
	 * @see camel.sla.KPITargetType
	 * @generated
	 */
	EClass getKPITargetType();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.KPITargetType#getKPIName <em>KPI Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>KPI Name</em>'.
	 * @see camel.sla.KPITargetType#getKPIName()
	 * @see #getKPITargetType()
	 * @generated
	 */
	EAttribute getKPITargetType_KPIName();

	/**
	 * Returns the meta object for the reference '{@link camel.sla.KPITargetType#getCustomServiceLevel <em>Custom Service Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Custom Service Level</em>'.
	 * @see camel.sla.KPITargetType#getCustomServiceLevel()
	 * @see #getKPITargetType()
	 * @generated
	 */
	EReference getKPITargetType_CustomServiceLevel();

	/**
	 * Returns the meta object for class '{@link camel.sla.PreferenceType <em>Preference Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Preference Type</em>'.
	 * @see camel.sla.PreferenceType
	 * @generated
	 */
	EClass getPreferenceType();

	/**
	 * Returns the meta object for the attribute list '{@link camel.sla.PreferenceType#getServiceTermReference <em>Service Term Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Service Term Reference</em>'.
	 * @see camel.sla.PreferenceType#getServiceTermReference()
	 * @see #getPreferenceType()
	 * @generated
	 */
	EAttribute getPreferenceType_ServiceTermReference();

	/**
	 * Returns the meta object for the attribute list '{@link camel.sla.PreferenceType#getUtility <em>Utility</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Utility</em>'.
	 * @see camel.sla.PreferenceType#getUtility()
	 * @see #getPreferenceType()
	 * @generated
	 */
	EAttribute getPreferenceType_Utility();

	/**
	 * Returns the meta object for class '{@link camel.sla.ServiceLevelObjectiveTemplate <em>Service Level Objective Template</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Level Objective Template</em>'.
	 * @see camel.sla.ServiceLevelObjectiveTemplate
	 * @generated
	 */
	EClass getServiceLevelObjectiveTemplate();

	/**
	 * Returns the meta object for the reference '{@link camel.sla.ServiceLevelObjectiveTemplate#getCondition <em>Condition</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Condition</em>'.
	 * @see camel.sla.ServiceLevelObjectiveTemplate#getCondition()
	 * @see #getServiceLevelObjectiveTemplate()
	 * @generated
	 */
	EReference getServiceLevelObjectiveTemplate_Condition();

	/**
	 * Returns the meta object for class '{@link camel.sla.ServiceLevelObjectiveType <em>Service Level Objective Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Level Objective Type</em>'.
	 * @see camel.sla.ServiceLevelObjectiveType
	 * @generated
	 */
	EClass getServiceLevelObjectiveType();

	/**
	 * Returns the meta object for the containment reference '{@link camel.sla.ServiceLevelObjectiveType#getKPITarget <em>KPI Target</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>KPI Target</em>'.
	 * @see camel.sla.ServiceLevelObjectiveType#getKPITarget()
	 * @see #getServiceLevelObjectiveType()
	 * @generated
	 */
	EReference getServiceLevelObjectiveType_KPITarget();

	/**
	 * Returns the meta object for the reference '{@link camel.sla.ServiceLevelObjectiveType#getCustomServiceLevel <em>Custom Service Level</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Custom Service Level</em>'.
	 * @see camel.sla.ServiceLevelObjectiveType#getCustomServiceLevel()
	 * @see #getServiceLevelObjectiveType()
	 * @generated
	 */
	EReference getServiceLevelObjectiveType_CustomServiceLevel();

	/**
	 * Returns the meta object for class '{@link camel.sla.ServiceSelectorType <em>Service Selector Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Service Selector Type</em>'.
	 * @see camel.sla.ServiceSelectorType
	 * @generated
	 */
	EClass getServiceSelectorType();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.ServiceSelectorType#getServiceName <em>Service Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Service Name</em>'.
	 * @see camel.sla.ServiceSelectorType#getServiceName()
	 * @see #getServiceSelectorType()
	 * @generated
	 */
	EAttribute getServiceSelectorType_ServiceName();

	/**
	 * Returns the meta object for the reference '{@link camel.sla.ServiceSelectorType#getServiceReference <em>Service Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Service Reference</em>'.
	 * @see camel.sla.ServiceSelectorType#getServiceReference()
	 * @see #getServiceSelectorType()
	 * @generated
	 */
	EReference getServiceSelectorType_ServiceReference();

	/**
	 * Returns the meta object for class '{@link camel.sla.TermCompositorType <em>Term Compositor Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Term Compositor Type</em>'.
	 * @see camel.sla.TermCompositorType
	 * @generated
	 */
	EClass getTermCompositorType();

	/**
	 * Returns the meta object for the attribute '{@link camel.sla.TermCompositorType#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see camel.sla.TermCompositorType#getId()
	 * @see #getTermCompositorType()
	 * @generated
	 */
	EAttribute getTermCompositorType_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.sla.TermCompositorType#getExactlyOne <em>Exactly One</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Exactly One</em>'.
	 * @see camel.sla.TermCompositorType#getExactlyOne()
	 * @see #getTermCompositorType()
	 * @generated
	 */
	EReference getTermCompositorType_ExactlyOne();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.sla.TermCompositorType#getOneOrMore <em>One Or More</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>One Or More</em>'.
	 * @see camel.sla.TermCompositorType#getOneOrMore()
	 * @see #getTermCompositorType()
	 * @generated
	 */
	EReference getTermCompositorType_OneOrMore();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.sla.TermCompositorType#getAll <em>All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>All</em>'.
	 * @see camel.sla.TermCompositorType#getAll()
	 * @see #getTermCompositorType()
	 * @generated
	 */
	EReference getTermCompositorType_All();

	/**
	 * Returns the meta object for the reference list '{@link camel.sla.TermCompositorType#getServiceReference <em>Service Reference</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Service Reference</em>'.
	 * @see camel.sla.TermCompositorType#getServiceReference()
	 * @see #getTermCompositorType()
	 * @generated
	 */
	EReference getTermCompositorType_ServiceReference();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.sla.TermCompositorType#getGuaranteeTerm <em>Guarantee Term</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Guarantee Term</em>'.
	 * @see camel.sla.TermCompositorType#getGuaranteeTerm()
	 * @see #getTermCompositorType()
	 * @generated
	 */
	EReference getTermCompositorType_GuaranteeTerm();

	/**
	 * Returns the meta object for the '{@link camel.sla.TermCompositorType#checkRecursiveness(camel.sla.TermCompositorType, camel.sla.TermCompositorType, org.eclipse.emf.common.util.EList) <em>Check Recursiveness</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Recursiveness</em>' operation.
	 * @see camel.sla.TermCompositorType#checkRecursiveness(camel.sla.TermCompositorType, camel.sla.TermCompositorType, org.eclipse.emf.common.util.EList)
	 * @generated
	 */
	EOperation getTermCompositorType__CheckRecursiveness__TermCompositorType_TermCompositorType_EList();

	/**
	 * Returns the meta object for class '{@link camel.sla.TermTreeType <em>Term Tree Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Term Tree Type</em>'.
	 * @see camel.sla.TermTreeType
	 * @generated
	 */
	EClass getTermTreeType();

	/**
	 * Returns the meta object for the containment reference '{@link camel.sla.TermTreeType#getAll <em>All</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>All</em>'.
	 * @see camel.sla.TermTreeType#getAll()
	 * @see #getTermTreeType()
	 * @generated
	 */
	EReference getTermTreeType_All();

	/**
	 * Returns the meta object for enum '{@link camel.sla.AgreementRoleType <em>Agreement Role Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Agreement Role Type</em>'.
	 * @see camel.sla.AgreementRoleType
	 * @generated
	 */
	EEnum getAgreementRoleType();

	/**
	 * Returns the meta object for enum '{@link camel.sla.ServiceRoleType <em>Service Role Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Service Role Type</em>'.
	 * @see camel.sla.ServiceRoleType
	 * @generated
	 */
	EEnum getServiceRoleType();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	SlaFactory getSlaFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link camel.sla.impl.AgreementContextTypeImpl <em>Agreement Context Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.AgreementContextTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getAgreementContextType()
		 * @generated
		 */
		EClass AGREEMENT_CONTEXT_TYPE = eINSTANCE.getAgreementContextType();

		/**
		 * The meta object literal for the '<em><b>Template Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGREEMENT_CONTEXT_TYPE__TEMPLATE_ID = eINSTANCE.getAgreementContextType_TemplateId();

		/**
		 * The meta object literal for the '<em><b>Template Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGREEMENT_CONTEXT_TYPE__TEMPLATE_NAME = eINSTANCE.getAgreementContextType_TemplateName();

		/**
		 * The meta object literal for the '<em><b>Service Provider</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGREEMENT_CONTEXT_TYPE__SERVICE_PROVIDER = eINSTANCE.getAgreementContextType_ServiceProvider();

		/**
		 * The meta object literal for the '<em><b>Service Consumer</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGREEMENT_CONTEXT_TYPE__SERVICE_CONSUMER = eINSTANCE.getAgreementContextType_ServiceConsumer();

		/**
		 * The meta object literal for the '<em><b>Expiration Time</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGREEMENT_CONTEXT_TYPE__EXPIRATION_TIME = eINSTANCE.getAgreementContextType_ExpirationTime();

		/**
		 * The meta object literal for the '<em><b>Agreement Initiator</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGREEMENT_CONTEXT_TYPE__AGREEMENT_INITIATOR = eINSTANCE.getAgreementContextType_AgreementInitiator();

		/**
		 * The meta object literal for the '<em><b>Agreement Responder</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGREEMENT_CONTEXT_TYPE__AGREEMENT_RESPONDER = eINSTANCE.getAgreementContextType_AgreementResponder();

		/**
		 * The meta object literal for the '<em><b>Deployment Model</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGREEMENT_CONTEXT_TYPE__DEPLOYMENT_MODEL = eINSTANCE.getAgreementContextType_DeploymentModel();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.AgreementTypeImpl <em>Agreement Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.AgreementTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getAgreementType()
		 * @generated
		 */
		EClass AGREEMENT_TYPE = eINSTANCE.getAgreementType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGREEMENT_TYPE__NAME = eINSTANCE.getAgreementType_Name();

		/**
		 * The meta object literal for the '<em><b>Agreement Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute AGREEMENT_TYPE__AGREEMENT_ID = eINSTANCE.getAgreementType_AgreementId();

		/**
		 * The meta object literal for the '<em><b>Context</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGREEMENT_TYPE__CONTEXT = eINSTANCE.getAgreementType_Context();

		/**
		 * The meta object literal for the '<em><b>Terms</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGREEMENT_TYPE__TERMS = eINSTANCE.getAgreementType_Terms();

		/**
		 * The meta object literal for the '<em><b>Slos</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference AGREEMENT_TYPE__SLOS = eINSTANCE.getAgreementType_Slos();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.AssessmentIntervalTypeImpl <em>Assessment Interval Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.AssessmentIntervalTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getAssessmentIntervalType()
		 * @generated
		 */
		EClass ASSESSMENT_INTERVAL_TYPE = eINSTANCE.getAssessmentIntervalType();

		/**
		 * The meta object literal for the '<em><b>Time Interval</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSESSMENT_INTERVAL_TYPE__TIME_INTERVAL = eINSTANCE.getAssessmentIntervalType_TimeInterval();

		/**
		 * The meta object literal for the '<em><b>Count</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ASSESSMENT_INTERVAL_TYPE__COUNT = eINSTANCE.getAssessmentIntervalType_Count();

		/**
		 * The meta object literal for the '<em><b>Time Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ASSESSMENT_INTERVAL_TYPE__TIME_UNIT = eINSTANCE.getAssessmentIntervalType_TimeUnit();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.BusinessValueListTypeImpl <em>Business Value List Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.BusinessValueListTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getBusinessValueListType()
		 * @generated
		 */
		EClass BUSINESS_VALUE_LIST_TYPE = eINSTANCE.getBusinessValueListType();

		/**
		 * The meta object literal for the '<em><b>Importance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BUSINESS_VALUE_LIST_TYPE__IMPORTANCE = eINSTANCE.getBusinessValueListType_Importance();

		/**
		 * The meta object literal for the '<em><b>Penalty</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUSINESS_VALUE_LIST_TYPE__PENALTY = eINSTANCE.getBusinessValueListType_Penalty();

		/**
		 * The meta object literal for the '<em><b>Reward</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUSINESS_VALUE_LIST_TYPE__REWARD = eINSTANCE.getBusinessValueListType_Reward();

		/**
		 * The meta object literal for the '<em><b>Preference</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUSINESS_VALUE_LIST_TYPE__PREFERENCE = eINSTANCE.getBusinessValueListType_Preference();

		/**
		 * The meta object literal for the '<em><b>Custom Business Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference BUSINESS_VALUE_LIST_TYPE__CUSTOM_BUSINESS_VALUE = eINSTANCE.getBusinessValueListType_CustomBusinessValue();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.CompensationTypeImpl <em>Compensation Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.CompensationTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getCompensationType()
		 * @generated
		 */
		EClass COMPENSATION_TYPE = eINSTANCE.getCompensationType();

		/**
		 * The meta object literal for the '<em><b>Assessment Interval</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPENSATION_TYPE__ASSESSMENT_INTERVAL = eINSTANCE.getCompensationType_AssessmentInterval();

		/**
		 * The meta object literal for the '<em><b>Value Unit</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPENSATION_TYPE__VALUE_UNIT = eINSTANCE.getCompensationType_ValueUnit();

		/**
		 * The meta object literal for the '<em><b>Value Expression</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPENSATION_TYPE__VALUE_EXPRESSION = eINSTANCE.getCompensationType_ValueExpression();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.GuaranteeTermTypeImpl <em>Guarantee Term Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.GuaranteeTermTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getGuaranteeTermType()
		 * @generated
		 */
		EClass GUARANTEE_TERM_TYPE = eINSTANCE.getGuaranteeTermType();

		/**
		 * The meta object literal for the '<em><b>Service Scope</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GUARANTEE_TERM_TYPE__SERVICE_SCOPE = eINSTANCE.getGuaranteeTermType_ServiceScope();

		/**
		 * The meta object literal for the '<em><b>Service Level Objective</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GUARANTEE_TERM_TYPE__SERVICE_LEVEL_OBJECTIVE = eINSTANCE.getGuaranteeTermType_ServiceLevelObjective();

		/**
		 * The meta object literal for the '<em><b>Business Value List</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GUARANTEE_TERM_TYPE__BUSINESS_VALUE_LIST = eINSTANCE.getGuaranteeTermType_BusinessValueList();

		/**
		 * The meta object literal for the '<em><b>Obligated</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GUARANTEE_TERM_TYPE__OBLIGATED = eINSTANCE.getGuaranteeTermType_Obligated();

		/**
		 * The meta object literal for the '<em><b>Qualifying Condition</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GUARANTEE_TERM_TYPE__QUALIFYING_CONDITION = eINSTANCE.getGuaranteeTermType_QualifyingCondition();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.KPITargetTypeImpl <em>KPI Target Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.KPITargetTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getKPITargetType()
		 * @generated
		 */
		EClass KPI_TARGET_TYPE = eINSTANCE.getKPITargetType();

		/**
		 * The meta object literal for the '<em><b>KPI Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute KPI_TARGET_TYPE__KPI_NAME = eINSTANCE.getKPITargetType_KPIName();

		/**
		 * The meta object literal for the '<em><b>Custom Service Level</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference KPI_TARGET_TYPE__CUSTOM_SERVICE_LEVEL = eINSTANCE.getKPITargetType_CustomServiceLevel();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.PreferenceTypeImpl <em>Preference Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.PreferenceTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getPreferenceType()
		 * @generated
		 */
		EClass PREFERENCE_TYPE = eINSTANCE.getPreferenceType();

		/**
		 * The meta object literal for the '<em><b>Service Term Reference</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFERENCE_TYPE__SERVICE_TERM_REFERENCE = eINSTANCE.getPreferenceType_ServiceTermReference();

		/**
		 * The meta object literal for the '<em><b>Utility</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PREFERENCE_TYPE__UTILITY = eINSTANCE.getPreferenceType_Utility();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.ServiceLevelObjectiveTemplateImpl <em>Service Level Objective Template</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.ServiceLevelObjectiveTemplateImpl
		 * @see camel.sla.impl.SlaPackageImpl#getServiceLevelObjectiveTemplate()
		 * @generated
		 */
		EClass SERVICE_LEVEL_OBJECTIVE_TEMPLATE = eINSTANCE.getServiceLevelObjectiveTemplate();

		/**
		 * The meta object literal for the '<em><b>Condition</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_LEVEL_OBJECTIVE_TEMPLATE__CONDITION = eINSTANCE.getServiceLevelObjectiveTemplate_Condition();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.ServiceLevelObjectiveTypeImpl <em>Service Level Objective Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.ServiceLevelObjectiveTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getServiceLevelObjectiveType()
		 * @generated
		 */
		EClass SERVICE_LEVEL_OBJECTIVE_TYPE = eINSTANCE.getServiceLevelObjectiveType();

		/**
		 * The meta object literal for the '<em><b>KPI Target</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_LEVEL_OBJECTIVE_TYPE__KPI_TARGET = eINSTANCE.getServiceLevelObjectiveType_KPITarget();

		/**
		 * The meta object literal for the '<em><b>Custom Service Level</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_LEVEL_OBJECTIVE_TYPE__CUSTOM_SERVICE_LEVEL = eINSTANCE.getServiceLevelObjectiveType_CustomServiceLevel();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.ServiceSelectorTypeImpl <em>Service Selector Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.ServiceSelectorTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getServiceSelectorType()
		 * @generated
		 */
		EClass SERVICE_SELECTOR_TYPE = eINSTANCE.getServiceSelectorType();

		/**
		 * The meta object literal for the '<em><b>Service Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SERVICE_SELECTOR_TYPE__SERVICE_NAME = eINSTANCE.getServiceSelectorType_ServiceName();

		/**
		 * The meta object literal for the '<em><b>Service Reference</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SERVICE_SELECTOR_TYPE__SERVICE_REFERENCE = eINSTANCE.getServiceSelectorType_ServiceReference();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.TermCompositorTypeImpl <em>Term Compositor Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.TermCompositorTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getTermCompositorType()
		 * @generated
		 */
		EClass TERM_COMPOSITOR_TYPE = eINSTANCE.getTermCompositorType();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TERM_COMPOSITOR_TYPE__ID = eINSTANCE.getTermCompositorType_Id();

		/**
		 * The meta object literal for the '<em><b>Exactly One</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERM_COMPOSITOR_TYPE__EXACTLY_ONE = eINSTANCE.getTermCompositorType_ExactlyOne();

		/**
		 * The meta object literal for the '<em><b>One Or More</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERM_COMPOSITOR_TYPE__ONE_OR_MORE = eINSTANCE.getTermCompositorType_OneOrMore();

		/**
		 * The meta object literal for the '<em><b>All</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERM_COMPOSITOR_TYPE__ALL = eINSTANCE.getTermCompositorType_All();

		/**
		 * The meta object literal for the '<em><b>Service Reference</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERM_COMPOSITOR_TYPE__SERVICE_REFERENCE = eINSTANCE.getTermCompositorType_ServiceReference();

		/**
		 * The meta object literal for the '<em><b>Guarantee Term</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERM_COMPOSITOR_TYPE__GUARANTEE_TERM = eINSTANCE.getTermCompositorType_GuaranteeTerm();

		/**
		 * The meta object literal for the '<em><b>Check Recursiveness</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation TERM_COMPOSITOR_TYPE___CHECK_RECURSIVENESS__TERMCOMPOSITORTYPE_TERMCOMPOSITORTYPE_ELIST = eINSTANCE.getTermCompositorType__CheckRecursiveness__TermCompositorType_TermCompositorType_EList();

		/**
		 * The meta object literal for the '{@link camel.sla.impl.TermTreeTypeImpl <em>Term Tree Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.impl.TermTreeTypeImpl
		 * @see camel.sla.impl.SlaPackageImpl#getTermTreeType()
		 * @generated
		 */
		EClass TERM_TREE_TYPE = eINSTANCE.getTermTreeType();

		/**
		 * The meta object literal for the '<em><b>All</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TERM_TREE_TYPE__ALL = eINSTANCE.getTermTreeType_All();

		/**
		 * The meta object literal for the '{@link camel.sla.AgreementRoleType <em>Agreement Role Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.AgreementRoleType
		 * @see camel.sla.impl.SlaPackageImpl#getAgreementRoleType()
		 * @generated
		 */
		EEnum AGREEMENT_ROLE_TYPE = eINSTANCE.getAgreementRoleType();

		/**
		 * The meta object literal for the '{@link camel.sla.ServiceRoleType <em>Service Role Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.sla.ServiceRoleType
		 * @see camel.sla.impl.SlaPackageImpl#getServiceRoleType()
		 * @generated
		 */
		EEnum SERVICE_ROLE_TYPE = eINSTANCE.getServiceRoleType();

	}

} //SlaPackage
