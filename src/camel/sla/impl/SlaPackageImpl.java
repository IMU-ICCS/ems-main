/**
 */
package camel.sla.impl;

import camel.CamelPackage;

import camel.deployment.DeploymentPackage;

import camel.deployment.impl.DeploymentPackageImpl;

import camel.execution.ExecutionPackage;

import camel.execution.impl.ExecutionPackageImpl;

import camel.impl.CamelPackageImpl;

import camel.organisation.OrganisationPackage;

import camel.organisation.impl.OrganisationPackageImpl;

import camel.provider.ProviderPackage;

import camel.provider.impl.ProviderPackageImpl;

import camel.scalability.ScalabilityPackage;

import camel.scalability.impl.ScalabilityPackageImpl;

import camel.security.SecurityPackage;

import camel.security.impl.SecurityPackageImpl;

import camel.sla.AgreementContextType;
import camel.sla.AgreementRoleType;
import camel.sla.AgreementType;
import camel.sla.AssessmentIntervalType;
import camel.sla.BusinessValueListType;
import camel.sla.CompensationType;
import camel.sla.GuaranteeTermType;
import camel.sla.KPITargetType;
import camel.sla.PreferenceType;
import camel.sla.ServiceLevelObjectiveTemplate;
import camel.sla.ServiceLevelObjectiveType;
import camel.sla.ServiceRoleType;
import camel.sla.ServiceSelectorType;
import camel.sla.SlaFactory;
import camel.sla.SlaPackage;
import camel.sla.TermCompositorType;
import camel.sla.TermTreeType;

import camel.sla.util.SlaValidator;

import camel.type.TypePackage;

import camel.type.impl.TypePackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class SlaPackageImpl extends EPackageImpl implements SlaPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agreementContextTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass agreementTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass assessmentIntervalTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass businessValueListTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass compensationTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass guaranteeTermTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass kpiTargetTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass preferenceTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serviceLevelObjectiveTemplateEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serviceLevelObjectiveTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass serviceSelectorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass termCompositorTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass termTreeTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum agreementRoleTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum serviceRoleTypeEEnum = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see camel.sla.SlaPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private SlaPackageImpl() {
		super(eNS_URI, SlaFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link SlaPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static SlaPackage init() {
		if (isInited) return (SlaPackage)EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI);

		// Obtain or create and register package
		SlaPackageImpl theSlaPackage = (SlaPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof SlaPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new SlaPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		CamelPackageImpl theCamelPackage = (CamelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) instanceof CamelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) : CamelPackage.eINSTANCE);
		DeploymentPackageImpl theDeploymentPackage = (DeploymentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) instanceof DeploymentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) : DeploymentPackage.eINSTANCE);
		ExecutionPackageImpl theExecutionPackage = (ExecutionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) instanceof ExecutionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) : ExecutionPackage.eINSTANCE);
		OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
		ProviderPackageImpl theProviderPackage = (ProviderPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) instanceof ProviderPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) : ProviderPackage.eINSTANCE);
		ScalabilityPackageImpl theScalabilityPackage = (ScalabilityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) instanceof ScalabilityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) : ScalabilityPackage.eINSTANCE);
		SecurityPackageImpl theSecurityPackage = (SecurityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) instanceof SecurityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) : SecurityPackage.eINSTANCE);
		TypePackageImpl theTypePackage = (TypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) instanceof TypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) : TypePackage.eINSTANCE);

		// Create package meta-data objects
		theSlaPackage.createPackageContents();
		theCamelPackage.createPackageContents();
		theDeploymentPackage.createPackageContents();
		theExecutionPackage.createPackageContents();
		theOrganisationPackage.createPackageContents();
		theProviderPackage.createPackageContents();
		theScalabilityPackage.createPackageContents();
		theSecurityPackage.createPackageContents();
		theTypePackage.createPackageContents();

		// Initialize created meta-data
		theSlaPackage.initializePackageContents();
		theCamelPackage.initializePackageContents();
		theDeploymentPackage.initializePackageContents();
		theExecutionPackage.initializePackageContents();
		theOrganisationPackage.initializePackageContents();
		theProviderPackage.initializePackageContents();
		theScalabilityPackage.initializePackageContents();
		theSecurityPackage.initializePackageContents();
		theTypePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theSlaPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return SlaValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theSlaPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(SlaPackage.eNS_URI, theSlaPackage);
		return theSlaPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAgreementContextType() {
		return agreementContextTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgreementContextType_TemplateId() {
		return (EAttribute)agreementContextTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgreementContextType_TemplateName() {
		return (EAttribute)agreementContextTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgreementContextType_ServiceProvider() {
		return (EAttribute)agreementContextTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgreementContextType_ServiceConsumer() {
		return (EAttribute)agreementContextTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgreementContextType_ExpirationTime() {
		return (EAttribute)agreementContextTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgreementContextType_AgreementInitiator() {
		return (EReference)agreementContextTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgreementContextType_AgreementResponder() {
		return (EReference)agreementContextTypeEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgreementContextType_DeploymentModel() {
		return (EReference)agreementContextTypeEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAgreementType() {
		return agreementTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgreementType_Name() {
		return (EAttribute)agreementTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAgreementType_AgreementId() {
		return (EAttribute)agreementTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgreementType_Context() {
		return (EReference)agreementTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgreementType_Terms() {
		return (EReference)agreementTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAgreementType_Slos() {
		return (EReference)agreementTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAssessmentIntervalType() {
		return assessmentIntervalTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssessmentIntervalType_TimeInterval() {
		return (EAttribute)assessmentIntervalTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getAssessmentIntervalType_Count() {
		return (EAttribute)assessmentIntervalTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAssessmentIntervalType_TimeUnit() {
		return (EReference)assessmentIntervalTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBusinessValueListType() {
		return businessValueListTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBusinessValueListType_Importance() {
		return (EAttribute)businessValueListTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBusinessValueListType_Penalty() {
		return (EReference)businessValueListTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBusinessValueListType_Reward() {
		return (EReference)businessValueListTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBusinessValueListType_Preference() {
		return (EReference)businessValueListTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getBusinessValueListType_CustomBusinessValue() {
		return (EReference)businessValueListTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCompensationType() {
		return compensationTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompensationType_AssessmentInterval() {
		return (EReference)compensationTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCompensationType_ValueUnit() {
		return (EReference)compensationTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCompensationType_ValueExpression() {
		return (EAttribute)compensationTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGuaranteeTermType() {
		return guaranteeTermTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGuaranteeTermType_ServiceScope() {
		return (EReference)guaranteeTermTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGuaranteeTermType_ServiceLevelObjective() {
		return (EReference)guaranteeTermTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGuaranteeTermType_BusinessValueList() {
		return (EReference)guaranteeTermTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGuaranteeTermType_Obligated() {
		return (EAttribute)guaranteeTermTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getGuaranteeTermType_QualifyingCondition() {
		return (EReference)guaranteeTermTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getKPITargetType() {
		return kpiTargetTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getKPITargetType_KPIName() {
		return (EAttribute)kpiTargetTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getKPITargetType_CustomServiceLevel() {
		return (EReference)kpiTargetTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPreferenceType() {
		return preferenceTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPreferenceType_ServiceTermReference() {
		return (EAttribute)preferenceTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPreferenceType_Utility() {
		return (EAttribute)preferenceTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getServiceLevelObjectiveTemplate() {
		return serviceLevelObjectiveTemplateEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getServiceLevelObjectiveTemplate_Condition() {
		return (EReference)serviceLevelObjectiveTemplateEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getServiceLevelObjectiveType() {
		return serviceLevelObjectiveTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getServiceLevelObjectiveType_KPITarget() {
		return (EReference)serviceLevelObjectiveTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getServiceLevelObjectiveType_CustomServiceLevel() {
		return (EReference)serviceLevelObjectiveTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getServiceSelectorType() {
		return serviceSelectorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getServiceSelectorType_ServiceName() {
		return (EAttribute)serviceSelectorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getServiceSelectorType_ServiceReference() {
		return (EReference)serviceSelectorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTermCompositorType() {
		return termCompositorTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTermCompositorType_Id() {
		return (EAttribute)termCompositorTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTermCompositorType_ExactlyOne() {
		return (EReference)termCompositorTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTermCompositorType_OneOrMore() {
		return (EReference)termCompositorTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTermCompositorType_All() {
		return (EReference)termCompositorTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTermCompositorType_ServiceReference() {
		return (EReference)termCompositorTypeEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTermCompositorType_GuaranteeTerm() {
		return (EReference)termCompositorTypeEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getTermCompositorType__CheckRecursiveness__TermCompositorType_TermCompositorType_EList() {
		return termCompositorTypeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTermTreeType() {
		return termTreeTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTermTreeType_All() {
		return (EReference)termTreeTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getAgreementRoleType() {
		return agreementRoleTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getServiceRoleType() {
		return serviceRoleTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SlaFactory getSlaFactory() {
		return (SlaFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		agreementContextTypeEClass = createEClass(AGREEMENT_CONTEXT_TYPE);
		createEAttribute(agreementContextTypeEClass, AGREEMENT_CONTEXT_TYPE__TEMPLATE_ID);
		createEAttribute(agreementContextTypeEClass, AGREEMENT_CONTEXT_TYPE__TEMPLATE_NAME);
		createEAttribute(agreementContextTypeEClass, AGREEMENT_CONTEXT_TYPE__SERVICE_PROVIDER);
		createEAttribute(agreementContextTypeEClass, AGREEMENT_CONTEXT_TYPE__SERVICE_CONSUMER);
		createEAttribute(agreementContextTypeEClass, AGREEMENT_CONTEXT_TYPE__EXPIRATION_TIME);
		createEReference(agreementContextTypeEClass, AGREEMENT_CONTEXT_TYPE__AGREEMENT_INITIATOR);
		createEReference(agreementContextTypeEClass, AGREEMENT_CONTEXT_TYPE__AGREEMENT_RESPONDER);
		createEReference(agreementContextTypeEClass, AGREEMENT_CONTEXT_TYPE__DEPLOYMENT_MODEL);

		agreementTypeEClass = createEClass(AGREEMENT_TYPE);
		createEAttribute(agreementTypeEClass, AGREEMENT_TYPE__NAME);
		createEAttribute(agreementTypeEClass, AGREEMENT_TYPE__AGREEMENT_ID);
		createEReference(agreementTypeEClass, AGREEMENT_TYPE__CONTEXT);
		createEReference(agreementTypeEClass, AGREEMENT_TYPE__TERMS);
		createEReference(agreementTypeEClass, AGREEMENT_TYPE__SLOS);

		assessmentIntervalTypeEClass = createEClass(ASSESSMENT_INTERVAL_TYPE);
		createEAttribute(assessmentIntervalTypeEClass, ASSESSMENT_INTERVAL_TYPE__TIME_INTERVAL);
		createEAttribute(assessmentIntervalTypeEClass, ASSESSMENT_INTERVAL_TYPE__COUNT);
		createEReference(assessmentIntervalTypeEClass, ASSESSMENT_INTERVAL_TYPE__TIME_UNIT);

		businessValueListTypeEClass = createEClass(BUSINESS_VALUE_LIST_TYPE);
		createEAttribute(businessValueListTypeEClass, BUSINESS_VALUE_LIST_TYPE__IMPORTANCE);
		createEReference(businessValueListTypeEClass, BUSINESS_VALUE_LIST_TYPE__PENALTY);
		createEReference(businessValueListTypeEClass, BUSINESS_VALUE_LIST_TYPE__REWARD);
		createEReference(businessValueListTypeEClass, BUSINESS_VALUE_LIST_TYPE__PREFERENCE);
		createEReference(businessValueListTypeEClass, BUSINESS_VALUE_LIST_TYPE__CUSTOM_BUSINESS_VALUE);

		compensationTypeEClass = createEClass(COMPENSATION_TYPE);
		createEReference(compensationTypeEClass, COMPENSATION_TYPE__ASSESSMENT_INTERVAL);
		createEReference(compensationTypeEClass, COMPENSATION_TYPE__VALUE_UNIT);
		createEAttribute(compensationTypeEClass, COMPENSATION_TYPE__VALUE_EXPRESSION);

		guaranteeTermTypeEClass = createEClass(GUARANTEE_TERM_TYPE);
		createEReference(guaranteeTermTypeEClass, GUARANTEE_TERM_TYPE__SERVICE_SCOPE);
		createEReference(guaranteeTermTypeEClass, GUARANTEE_TERM_TYPE__SERVICE_LEVEL_OBJECTIVE);
		createEReference(guaranteeTermTypeEClass, GUARANTEE_TERM_TYPE__BUSINESS_VALUE_LIST);
		createEAttribute(guaranteeTermTypeEClass, GUARANTEE_TERM_TYPE__OBLIGATED);
		createEReference(guaranteeTermTypeEClass, GUARANTEE_TERM_TYPE__QUALIFYING_CONDITION);

		kpiTargetTypeEClass = createEClass(KPI_TARGET_TYPE);
		createEAttribute(kpiTargetTypeEClass, KPI_TARGET_TYPE__KPI_NAME);
		createEReference(kpiTargetTypeEClass, KPI_TARGET_TYPE__CUSTOM_SERVICE_LEVEL);

		preferenceTypeEClass = createEClass(PREFERENCE_TYPE);
		createEAttribute(preferenceTypeEClass, PREFERENCE_TYPE__SERVICE_TERM_REFERENCE);
		createEAttribute(preferenceTypeEClass, PREFERENCE_TYPE__UTILITY);

		serviceLevelObjectiveTemplateEClass = createEClass(SERVICE_LEVEL_OBJECTIVE_TEMPLATE);
		createEReference(serviceLevelObjectiveTemplateEClass, SERVICE_LEVEL_OBJECTIVE_TEMPLATE__CONDITION);

		serviceLevelObjectiveTypeEClass = createEClass(SERVICE_LEVEL_OBJECTIVE_TYPE);
		createEReference(serviceLevelObjectiveTypeEClass, SERVICE_LEVEL_OBJECTIVE_TYPE__KPI_TARGET);
		createEReference(serviceLevelObjectiveTypeEClass, SERVICE_LEVEL_OBJECTIVE_TYPE__CUSTOM_SERVICE_LEVEL);

		serviceSelectorTypeEClass = createEClass(SERVICE_SELECTOR_TYPE);
		createEAttribute(serviceSelectorTypeEClass, SERVICE_SELECTOR_TYPE__SERVICE_NAME);
		createEReference(serviceSelectorTypeEClass, SERVICE_SELECTOR_TYPE__SERVICE_REFERENCE);

		termCompositorTypeEClass = createEClass(TERM_COMPOSITOR_TYPE);
		createEAttribute(termCompositorTypeEClass, TERM_COMPOSITOR_TYPE__ID);
		createEReference(termCompositorTypeEClass, TERM_COMPOSITOR_TYPE__EXACTLY_ONE);
		createEReference(termCompositorTypeEClass, TERM_COMPOSITOR_TYPE__ONE_OR_MORE);
		createEReference(termCompositorTypeEClass, TERM_COMPOSITOR_TYPE__ALL);
		createEReference(termCompositorTypeEClass, TERM_COMPOSITOR_TYPE__SERVICE_REFERENCE);
		createEReference(termCompositorTypeEClass, TERM_COMPOSITOR_TYPE__GUARANTEE_TERM);
		createEOperation(termCompositorTypeEClass, TERM_COMPOSITOR_TYPE___CHECK_RECURSIVENESS__TERMCOMPOSITORTYPE_TERMCOMPOSITORTYPE_ELIST);

		termTreeTypeEClass = createEClass(TERM_TREE_TYPE);
		createEReference(termTreeTypeEClass, TERM_TREE_TYPE__ALL);

		// Create enums
		agreementRoleTypeEEnum = createEEnum(AGREEMENT_ROLE_TYPE);
		serviceRoleTypeEEnum = createEEnum(SERVICE_ROLE_TYPE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		OrganisationPackage theOrganisationPackage = (OrganisationPackage)EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI);
		DeploymentPackage theDeploymentPackage = (DeploymentPackage)EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI);
		ScalabilityPackage theScalabilityPackage = (ScalabilityPackage)EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI);
		CamelPackage theCamelPackage = (CamelPackage)EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		serviceLevelObjectiveTemplateEClass.getESuperTypes().add(theCamelPackage.getRequirement());
		serviceLevelObjectiveTypeEClass.getESuperTypes().add(theCamelPackage.getRequirement());

		// Initialize classes, features, and operations; add parameters
		initEClass(agreementContextTypeEClass, AgreementContextType.class, "AgreementContextType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAgreementContextType_TemplateId(), ecorePackage.getEString(), "templateId", null, 0, 1, AgreementContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAgreementContextType_TemplateName(), ecorePackage.getEString(), "templateName", null, 0, 1, AgreementContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAgreementContextType_ServiceProvider(), this.getAgreementRoleType(), "serviceProvider", null, 0, 1, AgreementContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAgreementContextType_ServiceConsumer(), this.getAgreementRoleType(), "serviceConsumer", null, 0, 1, AgreementContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAgreementContextType_ExpirationTime(), ecorePackage.getEDate(), "expirationTime", null, 0, 1, AgreementContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgreementContextType_AgreementInitiator(), theOrganisationPackage.getEntity(), null, "agreementInitiator", null, 0, 1, AgreementContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgreementContextType_AgreementResponder(), theOrganisationPackage.getEntity(), null, "agreementResponder", null, 0, 1, AgreementContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgreementContextType_DeploymentModel(), theDeploymentPackage.getDeploymentModel(), null, "deploymentModel", null, 0, 1, AgreementContextType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(agreementTypeEClass, AgreementType.class, "AgreementType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAgreementType_Name(), ecorePackage.getEString(), "name", null, 1, 1, AgreementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAgreementType_AgreementId(), ecorePackage.getEString(), "agreementId", null, 1, 1, AgreementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgreementType_Context(), this.getAgreementContextType(), null, "context", null, 1, 1, AgreementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgreementType_Terms(), this.getTermTreeType(), null, "terms", null, 1, 1, AgreementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAgreementType_Slos(), theScalabilityPackage.getMetricCondition(), null, "slos", null, 0, -1, AgreementType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(assessmentIntervalTypeEClass, AssessmentIntervalType.class, "AssessmentIntervalType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getAssessmentIntervalType_TimeInterval(), ecorePackage.getELong(), "timeInterval", null, 0, 1, AssessmentIntervalType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getAssessmentIntervalType_Count(), ecorePackage.getEInt(), "count", null, 0, 1, AssessmentIntervalType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAssessmentIntervalType_TimeUnit(), theCamelPackage.getTimeIntervalUnit(), null, "timeUnit", null, 1, 1, AssessmentIntervalType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(businessValueListTypeEClass, BusinessValueListType.class, "BusinessValueListType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBusinessValueListType_Importance(), ecorePackage.getEInt(), "importance", null, 0, 1, BusinessValueListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBusinessValueListType_Penalty(), this.getCompensationType(), null, "penalty", null, 0, -1, BusinessValueListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBusinessValueListType_Reward(), this.getCompensationType(), null, "reward", null, 0, -1, BusinessValueListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBusinessValueListType_Preference(), this.getPreferenceType(), null, "preference", null, 0, 1, BusinessValueListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getBusinessValueListType_CustomBusinessValue(), ecorePackage.getEObject(), null, "customBusinessValue", null, 0, -1, BusinessValueListType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(compensationTypeEClass, CompensationType.class, "CompensationType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCompensationType_AssessmentInterval(), this.getAssessmentIntervalType(), null, "assessmentInterval", null, 1, 1, CompensationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCompensationType_ValueUnit(), theCamelPackage.getMonetaryUnit(), null, "valueUnit", null, 1, 1, CompensationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCompensationType_ValueExpression(), ecorePackage.getEString(), "valueExpression", null, 1, 1, CompensationType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(guaranteeTermTypeEClass, GuaranteeTermType.class, "GuaranteeTermType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getGuaranteeTermType_ServiceScope(), this.getServiceSelectorType(), null, "serviceScope", null, 0, -1, GuaranteeTermType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGuaranteeTermType_ServiceLevelObjective(), this.getServiceLevelObjectiveType(), null, "serviceLevelObjective", null, 1, 1, GuaranteeTermType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGuaranteeTermType_BusinessValueList(), this.getBusinessValueListType(), null, "businessValueList", null, 0, 1, GuaranteeTermType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGuaranteeTermType_Obligated(), this.getServiceRoleType(), "obligated", null, 0, 1, GuaranteeTermType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getGuaranteeTermType_QualifyingCondition(), theScalabilityPackage.getMetricCondition(), null, "qualifyingCondition", null, 0, 1, GuaranteeTermType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(kpiTargetTypeEClass, KPITargetType.class, "KPITargetType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getKPITargetType_KPIName(), ecorePackage.getEString(), "kPIName", null, 1, 1, KPITargetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getKPITargetType_CustomServiceLevel(), theScalabilityPackage.getMetricCondition(), null, "customServiceLevel", null, 1, 1, KPITargetType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(preferenceTypeEClass, PreferenceType.class, "PreferenceType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPreferenceType_ServiceTermReference(), ecorePackage.getEString(), "serviceTermReference", null, 1, -1, PreferenceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPreferenceType_Utility(), ecorePackage.getEFloat(), "utility", null, 1, -1, PreferenceType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(serviceLevelObjectiveTemplateEClass, ServiceLevelObjectiveTemplate.class, "ServiceLevelObjectiveTemplate", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getServiceLevelObjectiveTemplate_Condition(), theScalabilityPackage.getMetricTemplateCondition(), null, "condition", null, 1, 1, ServiceLevelObjectiveTemplate.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serviceLevelObjectiveTypeEClass, ServiceLevelObjectiveType.class, "ServiceLevelObjectiveType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getServiceLevelObjectiveType_KPITarget(), this.getKPITargetType(), null, "kPITarget", null, 0, 1, ServiceLevelObjectiveType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getServiceLevelObjectiveType_CustomServiceLevel(), theScalabilityPackage.getMetricCondition(), null, "customServiceLevel", null, 0, 1, ServiceLevelObjectiveType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(serviceSelectorTypeEClass, ServiceSelectorType.class, "ServiceSelectorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getServiceSelectorType_ServiceName(), ecorePackage.getEString(), "serviceName", null, 1, 1, ServiceSelectorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getServiceSelectorType_ServiceReference(), ecorePackage.getEObject(), null, "serviceReference", null, 1, 1, ServiceSelectorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(termCompositorTypeEClass, TermCompositorType.class, "TermCompositorType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTermCompositorType_Id(), ecorePackage.getEString(), "id", null, 1, 1, TermCompositorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTermCompositorType_ExactlyOne(), this.getTermCompositorType(), null, "exactlyOne", null, 0, -1, TermCompositorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTermCompositorType_OneOrMore(), this.getTermCompositorType(), null, "oneOrMore", null, 0, -1, TermCompositorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTermCompositorType_All(), this.getTermCompositorType(), null, "all", null, 0, -1, TermCompositorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTermCompositorType_ServiceReference(), ecorePackage.getEObject(), null, "serviceReference", null, 0, -1, TermCompositorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTermCompositorType_GuaranteeTerm(), this.getGuaranteeTermType(), null, "guaranteeTerm", null, 0, -1, TermCompositorType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getTermCompositorType__CheckRecursiveness__TermCompositorType_TermCompositorType_EList(), ecorePackage.getEBoolean(), "checkRecursiveness", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getTermCompositorType(), "tct1", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getTermCompositorType(), "tct2", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getTermCompositorType(), "context", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(termTreeTypeEClass, TermTreeType.class, "TermTreeType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTermTreeType_All(), this.getTermCompositorType(), null, "all", null, 0, 1, TermTreeType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(agreementRoleTypeEEnum, AgreementRoleType.class, "AgreementRoleType");
		addEEnumLiteral(agreementRoleTypeEEnum, AgreementRoleType.AGREEMENT_INITIATOR);
		addEEnumLiteral(agreementRoleTypeEEnum, AgreementRoleType.AGREEMENT_RESPONDER);

		initEEnum(serviceRoleTypeEEnum, ServiceRoleType.class, "ServiceRoleType");
		addEEnumLiteral(serviceRoleTypeEEnum, ServiceRoleType.SERVICE_CONSUMER);
		addEEnumLiteral(serviceRoleTypeEEnum, ServiceRoleType.SERVICE_PROVIDER);

		// Create annotations
		// http://www.eclipse.org/emf/2002/Ecore
		createEcoreAnnotations();
		// http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot
		createPivotAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createEcoreAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore";		
		addAnnotation
		  (this, 
		   source, 
		   new String[] {
			 "invocationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			 "settingDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
			 "validationDelegates", "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot"
		   });		
		addAnnotation
		  (agreementContextTypeEClass, 
		   source, 
		   new String[] {
			 "constraints", "WSAG_Context_Different_Role WSAG_Context_Diff_Entities"
		   });			
		addAnnotation
		  (assessmentIntervalTypeEClass, 
		   source, 
		   new String[] {
			 "constraints", "AssessmentInterval_attribute_value_enforcement"
		   });			
		addAnnotation
		  (preferenceTypeEClass, 
		   source, 
		   new String[] {
			 "constraints", "Utilities_util_non_negative Utilities_sum_to_One Preference_Type_Same_Size"
		   });			
		addAnnotation
		  (serviceSelectorTypeEClass, 
		   source, 
		   new String[] {
			 "constraints", "correct_service_reference_and_name_SSType"
		   });			
		addAnnotation
		  (termCompositorTypeEClass, 
		   source, 
		   new String[] {
			 "constraints", "TermCompositorType_Non_Recurs correct_service_reference"
		   });		
	}

	/**
	 * Initializes the annotations for <b>http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createPivotAnnotations() {
		String source = "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot";				
		addAnnotation
		  (agreementContextTypeEClass, 
		   source, 
		   new String[] {
			 "WSAG_Context_Different_Role", "\n\t\t\t\t\t\tself.serviceProvider <> self.serviceConsumer",
			 "WSAG_Context_Diff_Entities", "\n\t\t\t\t\t\t(self.agreementInitiator <> null and self.agreementResponder <> null) implies self.agreementInitiator <> self.agreementResponder"
		   });			
		addAnnotation
		  (assessmentIntervalTypeEClass, 
		   source, 
		   new String[] {
			 "AssessmentInterval_attribute_value_enforcement", "\n\t\t\t\t\t\ttimeInterval > 0 and count >= 0"
		   });			
		addAnnotation
		  (preferenceTypeEClass, 
		   source, 
		   new String[] {
			 "Utilities_util_non_negative", "\n\t\t\t\t\t\tself.utility->forAll(p | p >= 0.0)",
			 "Utilities_sum_to_One", "\n\t\t\t\t\t\tself.utility->sum() = 1.0",
			 "Preference_Type_Same_Size", "\n\t\t\t\t\t\tself.serviceTermReference->size() = self.utility->size()"
		   });			
		addAnnotation
		  (serviceSelectorTypeEClass, 
		   source, 
		   new String[] {
			 "correct_service_reference_and_name_SSType", "\n\t\t\t\t\t/* concrete service only at VM level, PaaS needs to be covered also */\n\t\t\t\t\tif (serviceReference.oclIsTypeOf(camel::deployment::Component)) then serviceName = serviceReference.oclAsType(camel::deployment::Component).name \n\t\t\t\t\telse \n\t\t\t\t\t\tif (serviceReference.oclIsTypeOf(camel::VMType)) then serviceName = serviceReference.oclAsType(camel::VMType).name\n\t\t\t\t\t\telse false endif\n\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (termCompositorTypeEClass, 
		   source, 
		   new String[] {
			 "TermCompositorType_Non_Recurs", "\n\t\t\t\t\t\tnot(self.checkRecursiveness(self,self,null)) and self.exactlyOne->forAll(p1,p2 | p1 <> p2 implies not(p1.checkRecursiveness(p1,p2,null))) and self.oneOrMore->forAll(p1,p2 | p1 <> p2 implies not(p1.checkRecursiveness(p1,p2,null))) and self.all->forAll(p1,p2 | p1 <> p2 implies not(p1.checkRecursiveness(p1,p2,null)))",
			 "correct_service_reference", "\n\t\t\t\t\t/* concrete service only at VM level, PaaS needs to be covered also */\n\t\t\t\t\tserviceReference->forAll(p | (p.oclIsKindOf(camel::deployment::Component) or p.oclIsTypeOf(camel::VMType)))"
		   });	
	}

} //SlaPackageImpl
