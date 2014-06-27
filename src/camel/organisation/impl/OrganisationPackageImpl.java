/**
 */
package camel.organisation.impl;

import camel.CamelPackage;

import camel.deployment.DeploymentPackage;

import camel.deployment.impl.DeploymentPackageImpl;

import camel.execution.ExecutionPackage;

import camel.execution.impl.ExecutionPackageImpl;

import camel.impl.CamelPackageImpl;

import camel.organisation.AllowedActions;
import camel.organisation.CloudProvider;
import camel.organisation.Credentials;
import camel.organisation.DataCenter;
import camel.organisation.Entity;
import camel.organisation.ExternalIdentifier;
import camel.organisation.Location;
import camel.organisation.OrganisationFactory;
import camel.organisation.OrganisationModel;
import camel.organisation.OrganisationPackage;
import camel.organisation.Organization;
import camel.organisation.Permission;
import camel.organisation.Resource;
import camel.organisation.ResourceGroup;
import camel.organisation.Role;
import camel.organisation.RoleAssignment;
import camel.organisation.User;
import camel.organisation.UserGroup;

import camel.organisation.util.OrganisationValidator;

import camel.provider.ProviderPackage;

import camel.provider.impl.ProviderPackageImpl;

import camel.scalability.ScalabilityPackage;

import camel.scalability.impl.ScalabilityPackageImpl;

import camel.security.SecurityPackage;

import camel.security.impl.SecurityPackageImpl;

import camel.sla.SlaPackage;

import camel.sla.impl.SlaPackageImpl;

import camel.type.TypePackage;

import camel.type.impl.TypePackageImpl;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
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
public class OrganisationPackageImpl extends EPackageImpl implements OrganisationPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass organisationModelEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass allowedActionsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass credentialsEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dataCenterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass entityEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass organizationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass cloudProviderEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass externalIdentifierEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass locationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass permissionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceGroupEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roleEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass roleAssignmentEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userGroupEClass = null;

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
	 * @see camel.organisation.OrganisationPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private OrganisationPackageImpl() {
		super(eNS_URI, OrganisationFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link OrganisationPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static OrganisationPackage init() {
		if (isInited) return (OrganisationPackage)EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI);

		// Obtain or create and register package
		OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new OrganisationPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		CamelPackageImpl theCamelPackage = (CamelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) instanceof CamelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) : CamelPackage.eINSTANCE);
		DeploymentPackageImpl theDeploymentPackage = (DeploymentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) instanceof DeploymentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) : DeploymentPackage.eINSTANCE);
		ExecutionPackageImpl theExecutionPackage = (ExecutionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) instanceof ExecutionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) : ExecutionPackage.eINSTANCE);
		ProviderPackageImpl theProviderPackage = (ProviderPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) instanceof ProviderPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) : ProviderPackage.eINSTANCE);
		ScalabilityPackageImpl theScalabilityPackage = (ScalabilityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) instanceof ScalabilityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) : ScalabilityPackage.eINSTANCE);
		SecurityPackageImpl theSecurityPackage = (SecurityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) instanceof SecurityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) : SecurityPackage.eINSTANCE);
		SlaPackageImpl theSlaPackage = (SlaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) instanceof SlaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) : SlaPackage.eINSTANCE);
		TypePackageImpl theTypePackage = (TypePackageImpl)(EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) instanceof TypePackageImpl ? EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI) : TypePackage.eINSTANCE);

		// Create package meta-data objects
		theOrganisationPackage.createPackageContents();
		theCamelPackage.createPackageContents();
		theDeploymentPackage.createPackageContents();
		theExecutionPackage.createPackageContents();
		theProviderPackage.createPackageContents();
		theScalabilityPackage.createPackageContents();
		theSecurityPackage.createPackageContents();
		theSlaPackage.createPackageContents();
		theTypePackage.createPackageContents();

		// Initialize created meta-data
		theOrganisationPackage.initializePackageContents();
		theCamelPackage.initializePackageContents();
		theDeploymentPackage.initializePackageContents();
		theExecutionPackage.initializePackageContents();
		theProviderPackage.initializePackageContents();
		theScalabilityPackage.initializePackageContents();
		theSecurityPackage.initializePackageContents();
		theSlaPackage.initializePackageContents();
		theTypePackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theOrganisationPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return OrganisationValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theOrganisationPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(OrganisationPackage.eNS_URI, theOrganisationPackage);
		return theOrganisationPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOrganisationModel() {
		return organisationModelEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_Users() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_Provider() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_Organization() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_UserGroups() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_DataCentres() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_RoleAssigments() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_Roles() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_Permissions() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_Resources() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_Locations() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_ExternalIdentifiers() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getOrganisationModel_AllowedActions() {
		return (EReference)organisationModelEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getAllowedActions() {
		return allowedActionsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAllowedActions_ResourceClass() {
		return (EReference)allowedActionsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getAllowedActions_Actions() {
		return (EReference)allowedActionsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCredentials() {
		return credentialsEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCredentials_CloudProvider() {
		return (EReference)credentialsEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCredentials_SecurityGroup() {
		return (EAttribute)credentialsEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCredentials_PublicSSHKey() {
		return (EAttribute)credentialsEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCredentials_PrivateSSHKey() {
		return (EAttribute)credentialsEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDataCenter() {
		return dataCenterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataCenter_Name() {
		return (EAttribute)dataCenterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDataCenter_CodeName() {
		return (EAttribute)dataCenterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataCenter_HasLocation() {
		return (EReference)dataCenterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDataCenter_OfCloudProvider() {
		return (EReference)dataCenterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEntity() {
		return entityEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOrganization() {
		return organizationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOrganization_Name() {
		return (EAttribute)organizationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOrganization_Www() {
		return (EAttribute)organizationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOrganization_PostalAddress() {
		return (EAttribute)organizationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOrganization_Email() {
		return (EAttribute)organizationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCloudProvider() {
		return cloudProviderEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCloudProvider_Public() {
		return (EAttribute)cloudProviderEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCloudProvider_SaaS() {
		return (EAttribute)cloudProviderEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCloudProvider_PaaS() {
		return (EAttribute)cloudProviderEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCloudProvider_IaaS() {
		return (EAttribute)cloudProviderEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCloudProvider_OffersSecControl() {
		return (EReference)cloudProviderEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCloudProvider_ProviderModel() {
		return (EReference)cloudProviderEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUser() {
		return userEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_FirstName() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_LastName() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_Email() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_Www() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUser_Login() {
		return (EAttribute)userEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUser_WorksFor() {
		return (EReference)userEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUser_HasExternalIdentifier() {
		return (EReference)userEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUser_HasRequirement() {
		return (EReference)userEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUser_HasCredentials() {
		return (EReference)userEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUser_DeploymentModel() {
		return (EReference)userEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExternalIdentifier() {
		return externalIdentifierEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalIdentifier_Identifier() {
		return (EAttribute)externalIdentifierEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalIdentifier_Name() {
		return (EAttribute)externalIdentifierEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExternalIdentifier_Description() {
		return (EAttribute)externalIdentifierEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLocation() {
		return locationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocation_Name() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocation_City() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocation_Country() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocation_CountryCode() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocation_Latitude() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocation_Longitude() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLocation_Address() {
		return (EAttribute)locationEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPermission() {
		return permissionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPermission_ForRole() {
		return (EReference)permissionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPermission_Start() {
		return (EAttribute)permissionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPermission_End() {
		return (EAttribute)permissionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPermission_IssuedBy() {
		return (EReference)permissionEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPermission_OnResource() {
		return (EReference)permissionEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPermission_ToAction() {
		return (EReference)permissionEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getPermission__CheckStartEndDates__Permission() {
		return permissionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResource() {
		return resourceEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getResource__OfClass__EClass() {
		return resourceEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getResource__AllowsActions__EList() {
		return resourceEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceGroup() {
		return resourceGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResourceGroup_Name() {
		return (EAttribute)resourceGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceGroup_ContainsResource() {
		return (EReference)resourceGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getResourceGroup_Level() {
		return (EAttribute)resourceGroupEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getResourceGroup__AllowsActionsOnResources__EList() {
		return resourceGroupEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getResourceGroup__CheckRecursiveness__ResourceGroup_Resource() {
		return resourceGroupEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRole() {
		return roleEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRole_Name() {
		return (EAttribute)roleEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRoleAssignment() {
		return roleAssignmentEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoleAssignment_OfUser() {
		return (EReference)roleAssignmentEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoleAssignment_ToRole() {
		return (EReference)roleAssignmentEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoleAssignment_OfGroup() {
		return (EReference)roleAssignmentEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoleAssignment_Start() {
		return (EAttribute)roleAssignmentEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoleAssignment_End() {
		return (EAttribute)roleAssignmentEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRoleAssignment_AssignedOn() {
		return (EAttribute)roleAssignmentEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRoleAssignment_AssignedBy() {
		return (EReference)roleAssignmentEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRoleAssignment__CheckAssignedOnDates__RoleAssignment() {
		return roleAssignmentEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRoleAssignment__CheckStartEndDates__RoleAssignment() {
		return roleAssignmentEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUserGroup() {
		return userGroupEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserGroup_Name() {
		return (EAttribute)userGroupEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUserGroup_Member() {
		return (EReference)userGroupEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OrganisationFactory getOrganisationFactory() {
		return (OrganisationFactory)getEFactoryInstance();
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
		organisationModelEClass = createEClass(ORGANISATION_MODEL);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__USERS);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__PROVIDER);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__ORGANIZATION);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__USER_GROUPS);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__DATA_CENTRES);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__ROLE_ASSIGMENTS);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__ROLES);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__PERMISSIONS);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__RESOURCES);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__LOCATIONS);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__EXTERNAL_IDENTIFIERS);
		createEReference(organisationModelEClass, ORGANISATION_MODEL__ALLOWED_ACTIONS);

		allowedActionsEClass = createEClass(ALLOWED_ACTIONS);
		createEReference(allowedActionsEClass, ALLOWED_ACTIONS__RESOURCE_CLASS);
		createEReference(allowedActionsEClass, ALLOWED_ACTIONS__ACTIONS);

		credentialsEClass = createEClass(CREDENTIALS);
		createEReference(credentialsEClass, CREDENTIALS__CLOUD_PROVIDER);
		createEAttribute(credentialsEClass, CREDENTIALS__SECURITY_GROUP);
		createEAttribute(credentialsEClass, CREDENTIALS__PUBLIC_SSH_KEY);
		createEAttribute(credentialsEClass, CREDENTIALS__PRIVATE_SSH_KEY);

		dataCenterEClass = createEClass(DATA_CENTER);
		createEAttribute(dataCenterEClass, DATA_CENTER__NAME);
		createEAttribute(dataCenterEClass, DATA_CENTER__CODE_NAME);
		createEReference(dataCenterEClass, DATA_CENTER__HAS_LOCATION);
		createEReference(dataCenterEClass, DATA_CENTER__OF_CLOUD_PROVIDER);

		entityEClass = createEClass(ENTITY);

		organizationEClass = createEClass(ORGANIZATION);
		createEAttribute(organizationEClass, ORGANIZATION__NAME);
		createEAttribute(organizationEClass, ORGANIZATION__WWW);
		createEAttribute(organizationEClass, ORGANIZATION__POSTAL_ADDRESS);
		createEAttribute(organizationEClass, ORGANIZATION__EMAIL);

		cloudProviderEClass = createEClass(CLOUD_PROVIDER);
		createEAttribute(cloudProviderEClass, CLOUD_PROVIDER__PUBLIC);
		createEAttribute(cloudProviderEClass, CLOUD_PROVIDER__SAA_S);
		createEAttribute(cloudProviderEClass, CLOUD_PROVIDER__PAA_S);
		createEAttribute(cloudProviderEClass, CLOUD_PROVIDER__IAA_S);
		createEReference(cloudProviderEClass, CLOUD_PROVIDER__OFFERS_SEC_CONTROL);
		createEReference(cloudProviderEClass, CLOUD_PROVIDER__PROVIDER_MODEL);

		userEClass = createEClass(USER);
		createEAttribute(userEClass, USER__FIRST_NAME);
		createEAttribute(userEClass, USER__LAST_NAME);
		createEAttribute(userEClass, USER__EMAIL);
		createEAttribute(userEClass, USER__WWW);
		createEAttribute(userEClass, USER__LOGIN);
		createEReference(userEClass, USER__WORKS_FOR);
		createEReference(userEClass, USER__HAS_EXTERNAL_IDENTIFIER);
		createEReference(userEClass, USER__HAS_REQUIREMENT);
		createEReference(userEClass, USER__HAS_CREDENTIALS);
		createEReference(userEClass, USER__DEPLOYMENT_MODEL);

		externalIdentifierEClass = createEClass(EXTERNAL_IDENTIFIER);
		createEAttribute(externalIdentifierEClass, EXTERNAL_IDENTIFIER__IDENTIFIER);
		createEAttribute(externalIdentifierEClass, EXTERNAL_IDENTIFIER__NAME);
		createEAttribute(externalIdentifierEClass, EXTERNAL_IDENTIFIER__DESCRIPTION);

		locationEClass = createEClass(LOCATION);
		createEAttribute(locationEClass, LOCATION__NAME);
		createEAttribute(locationEClass, LOCATION__CITY);
		createEAttribute(locationEClass, LOCATION__COUNTRY);
		createEAttribute(locationEClass, LOCATION__COUNTRY_CODE);
		createEAttribute(locationEClass, LOCATION__LATITUDE);
		createEAttribute(locationEClass, LOCATION__LONGITUDE);
		createEAttribute(locationEClass, LOCATION__ADDRESS);

		permissionEClass = createEClass(PERMISSION);
		createEReference(permissionEClass, PERMISSION__FOR_ROLE);
		createEAttribute(permissionEClass, PERMISSION__START);
		createEAttribute(permissionEClass, PERMISSION__END);
		createEReference(permissionEClass, PERMISSION__ISSUED_BY);
		createEReference(permissionEClass, PERMISSION__ON_RESOURCE);
		createEReference(permissionEClass, PERMISSION__TO_ACTION);
		createEOperation(permissionEClass, PERMISSION___CHECK_START_END_DATES__PERMISSION);

		resourceEClass = createEClass(RESOURCE);
		createEOperation(resourceEClass, RESOURCE___OF_CLASS__ECLASS);
		createEOperation(resourceEClass, RESOURCE___ALLOWS_ACTIONS__ELIST);

		resourceGroupEClass = createEClass(RESOURCE_GROUP);
		createEAttribute(resourceGroupEClass, RESOURCE_GROUP__NAME);
		createEReference(resourceGroupEClass, RESOURCE_GROUP__CONTAINS_RESOURCE);
		createEAttribute(resourceGroupEClass, RESOURCE_GROUP__LEVEL);
		createEOperation(resourceGroupEClass, RESOURCE_GROUP___ALLOWS_ACTIONS_ON_RESOURCES__ELIST);
		createEOperation(resourceGroupEClass, RESOURCE_GROUP___CHECK_RECURSIVENESS__RESOURCEGROUP_RESOURCE);

		roleEClass = createEClass(ROLE);
		createEAttribute(roleEClass, ROLE__NAME);

		roleAssignmentEClass = createEClass(ROLE_ASSIGNMENT);
		createEReference(roleAssignmentEClass, ROLE_ASSIGNMENT__OF_USER);
		createEReference(roleAssignmentEClass, ROLE_ASSIGNMENT__TO_ROLE);
		createEReference(roleAssignmentEClass, ROLE_ASSIGNMENT__OF_GROUP);
		createEAttribute(roleAssignmentEClass, ROLE_ASSIGNMENT__START);
		createEAttribute(roleAssignmentEClass, ROLE_ASSIGNMENT__END);
		createEAttribute(roleAssignmentEClass, ROLE_ASSIGNMENT__ASSIGNED_ON);
		createEReference(roleAssignmentEClass, ROLE_ASSIGNMENT__ASSIGNED_BY);
		createEOperation(roleAssignmentEClass, ROLE_ASSIGNMENT___CHECK_ASSIGNED_ON_DATES__ROLEASSIGNMENT);
		createEOperation(roleAssignmentEClass, ROLE_ASSIGNMENT___CHECK_START_END_DATES__ROLEASSIGNMENT);

		userGroupEClass = createEClass(USER_GROUP);
		createEAttribute(userGroupEClass, USER_GROUP__NAME);
		createEReference(userGroupEClass, USER_GROUP__MEMBER);
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
		CamelPackage theCamelPackage = (CamelPackage)EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI);
		SecurityPackage theSecurityPackage = (SecurityPackage)EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI);
		ProviderPackage theProviderPackage = (ProviderPackage)EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI);
		DeploymentPackage theDeploymentPackage = (DeploymentPackage)EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		organizationEClass.getESuperTypes().add(this.getEntity());
		cloudProviderEClass.getESuperTypes().add(this.getOrganization());
		userEClass.getESuperTypes().add(this.getEntity());
		resourceGroupEClass.getESuperTypes().add(this.getResource());

		// Initialize classes, features, and operations; add parameters
		initEClass(organisationModelEClass, OrganisationModel.class, "OrganisationModel", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getOrganisationModel_Users(), this.getUser(), null, "users", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOrganisationModel_Provider(), this.getCloudProvider(), null, "provider", null, 0, 1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOrganisationModel_Organization(), this.getOrganization(), null, "organization", null, 1, 1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getOrganisationModel_UserGroups(), this.getUserGroup(), null, "userGroups", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOrganisationModel_DataCentres(), this.getDataCenter(), null, "dataCentres", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOrganisationModel_RoleAssigments(), this.getRoleAssignment(), null, "roleAssigments", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOrganisationModel_Roles(), this.getRole(), null, "roles", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOrganisationModel_Permissions(), this.getPermission(), null, "permissions", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOrganisationModel_Resources(), this.getResource(), null, "resources", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOrganisationModel_Locations(), this.getLocation(), null, "locations", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOrganisationModel_ExternalIdentifiers(), this.getExternalIdentifier(), null, "externalIdentifiers", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);
		initEReference(getOrganisationModel_AllowedActions(), this.getAllowedActions(), null, "allowedActions", null, 0, -1, OrganisationModel.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, !IS_ORDERED);

		initEClass(allowedActionsEClass, AllowedActions.class, "AllowedActions", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getAllowedActions_ResourceClass(), ecorePackage.getEClass(), null, "resourceClass", null, 1, 1, AllowedActions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getAllowedActions_Actions(), theCamelPackage.getAction(), null, "actions", null, 1, -1, AllowedActions.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(credentialsEClass, Credentials.class, "Credentials", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCredentials_CloudProvider(), this.getCloudProvider(), null, "cloudProvider", null, 1, 1, Credentials.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCredentials_SecurityGroup(), ecorePackage.getEString(), "securityGroup", null, 0, 1, Credentials.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCredentials_PublicSSHKey(), ecorePackage.getEString(), "publicSSHKey", null, 0, 1, Credentials.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCredentials_PrivateSSHKey(), ecorePackage.getEString(), "privateSSHKey", null, 0, 1, Credentials.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dataCenterEClass, DataCenter.class, "DataCenter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDataCenter_Name(), ecorePackage.getEString(), "name", null, 1, 1, DataCenter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDataCenter_CodeName(), ecorePackage.getEString(), "codeName", null, 1, 1, DataCenter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDataCenter_HasLocation(), this.getLocation(), null, "hasLocation", null, 1, 1, DataCenter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getDataCenter_OfCloudProvider(), this.getCloudProvider(), null, "ofCloudProvider", null, 1, 1, DataCenter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(entityEClass, Entity.class, "Entity", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(organizationEClass, Organization.class, "Organization", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOrganization_Name(), ecorePackage.getEString(), "name", null, 1, 1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOrganization_Www(), ecorePackage.getEString(), "www", null, 0, 1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOrganization_PostalAddress(), ecorePackage.getEString(), "postalAddress", null, 0, 1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOrganization_Email(), ecorePackage.getEString(), "email", null, 1, 1, Organization.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(cloudProviderEClass, CloudProvider.class, "CloudProvider", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCloudProvider_Public(), ecorePackage.getEBoolean(), "public", null, 0, 1, CloudProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCloudProvider_SaaS(), ecorePackage.getEBoolean(), "SaaS", null, 0, 1, CloudProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCloudProvider_PaaS(), ecorePackage.getEBoolean(), "PaaS", null, 0, 1, CloudProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCloudProvider_IaaS(), ecorePackage.getEBoolean(), "IaaS", null, 0, 1, CloudProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCloudProvider_OffersSecControl(), theSecurityPackage.getSecurityControl(), null, "offersSecControl", null, 0, -1, CloudProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCloudProvider_ProviderModel(), theProviderPackage.getProviderModel(), null, "providerModel", null, 0, 1, CloudProvider.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userEClass, User.class, "User", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUser_FirstName(), ecorePackage.getEString(), "firstName", null, 1, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_LastName(), ecorePackage.getEString(), "lastName", null, 1, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_Email(), ecorePackage.getEString(), "email", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_Www(), ecorePackage.getEString(), "www", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUser_Login(), ecorePackage.getEString(), "login", null, 0, 1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUser_WorksFor(), this.getOrganization(), null, "worksFor", null, 0, -1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUser_HasExternalIdentifier(), this.getExternalIdentifier(), null, "hasExternalIdentifier", null, 0, -1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUser_HasRequirement(), theCamelPackage.getRequirement(), null, "hasRequirement", null, 0, -1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUser_HasCredentials(), this.getCredentials(), null, "hasCredentials", null, 0, -1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUser_DeploymentModel(), theDeploymentPackage.getDeploymentModel(), null, "deploymentModel", null, 0, -1, User.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(externalIdentifierEClass, ExternalIdentifier.class, "ExternalIdentifier", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExternalIdentifier_Identifier(), ecorePackage.getEString(), "identifier", null, 1, 1, ExternalIdentifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExternalIdentifier_Name(), ecorePackage.getEString(), "name", null, 1, 1, ExternalIdentifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getExternalIdentifier_Description(), ecorePackage.getEString(), "description", null, 0, 1, ExternalIdentifier.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(locationEClass, Location.class, "Location", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLocation_Name(), ecorePackage.getEString(), "name", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_City(), ecorePackage.getEString(), "city", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_Country(), ecorePackage.getEString(), "country", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_CountryCode(), ecorePackage.getEString(), "countryCode", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_Latitude(), ecorePackage.getEDouble(), "latitude", null, 1, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_Longitude(), ecorePackage.getEDouble(), "longitude", null, 1, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLocation_Address(), ecorePackage.getEString(), "address", null, 0, 1, Location.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(permissionEClass, Permission.class, "Permission", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPermission_ForRole(), this.getRole(), null, "forRole", null, 1, 1, Permission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPermission_Start(), ecorePackage.getEDate(), "start", null, 1, 1, Permission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getPermission_End(), ecorePackage.getEDate(), "end", null, 0, 1, Permission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPermission_IssuedBy(), this.getOrganization(), null, "issuedBy", null, 1, 1, Permission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPermission_OnResource(), this.getResource(), null, "onResource", null, 1, 1, Permission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPermission_ToAction(), theCamelPackage.getAction(), null, "toAction", null, 1, -1, Permission.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		EOperation op = initEOperation(getPermission__CheckStartEndDates__Permission(), ecorePackage.getEBoolean(), "checkStartEndDates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getPermission(), "this_", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(resourceEClass, Resource.class, "Resource", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		op = initEOperation(getResource__OfClass__EClass(), ecorePackage.getEBoolean(), "ofClass", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEClass(), "x", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getResource__AllowsActions__EList(), ecorePackage.getEBoolean(), "allowsActions", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCamelPackage.getAction(), "acts", 0, -1, IS_UNIQUE, IS_ORDERED);

		initEClass(resourceGroupEClass, ResourceGroup.class, "ResourceGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getResourceGroup_Name(), ecorePackage.getEString(), "name", null, 1, 1, ResourceGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceGroup_ContainsResource(), this.getResource(), null, "containsResource", null, 1, -1, ResourceGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getResourceGroup_Level(), ecorePackage.getEInt(), "level", null, 1, 1, ResourceGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		op = initEOperation(getResourceGroup__AllowsActionsOnResources__EList(), ecorePackage.getEBoolean(), "allowsActionsOnResources", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, theCamelPackage.getAction(), "actions", 0, -1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getResourceGroup__CheckRecursiveness__ResourceGroup_Resource(), ecorePackage.getEBoolean(), "checkRecursiveness", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResourceGroup(), "rg", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getResource(), "resource", 0, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(roleEClass, Role.class, "Role", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRole_Name(), ecorePackage.getEString(), "name", null, 1, 1, Role.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(roleAssignmentEClass, RoleAssignment.class, "RoleAssignment", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRoleAssignment_OfUser(), this.getUser(), null, "ofUser", null, 0, 1, RoleAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoleAssignment_ToRole(), this.getRole(), null, "toRole", null, 1, 1, RoleAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoleAssignment_OfGroup(), this.getUserGroup(), null, "ofGroup", null, 0, 1, RoleAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoleAssignment_Start(), ecorePackage.getEDate(), "start", null, 0, 1, RoleAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoleAssignment_End(), ecorePackage.getEDate(), "end", null, 0, 1, RoleAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRoleAssignment_AssignedOn(), ecorePackage.getEDate(), "assignedOn", null, 1, 1, RoleAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRoleAssignment_AssignedBy(), this.getOrganization(), null, "assignedBy", null, 1, 1, RoleAssignment.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getRoleAssignment__CheckAssignedOnDates__RoleAssignment(), ecorePackage.getEBoolean(), "checkAssignedOnDates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRoleAssignment(), "this_", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRoleAssignment__CheckStartEndDates__RoleAssignment(), ecorePackage.getEBoolean(), "checkStartEndDates", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRoleAssignment(), "this_", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(userGroupEClass, UserGroup.class, "UserGroup", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUserGroup_Name(), ecorePackage.getEString(), "name", null, 1, 1, UserGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUserGroup_Member(), this.getUser(), null, "member", null, 1, -1, UserGroup.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

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
		  (dataCenterEClass, 
		   source, 
		   new String[] {
			 "constraints", "Unique_DataCenter_Per_Provider No_DataCenter_SameLocation_SameProvider"
		   });			
		addAnnotation
		  (organizationEClass, 
		   source, 
		   new String[] {
			 "constraints", "UniqueName_Email"
		   });			
		addAnnotation
		  (userEClass, 
		   source, 
		   new String[] {
			 "constraints", "UniqueName_ExternalIdentifier"
		   });			
		addAnnotation
		  (locationEClass, 
		   source, 
		   new String[] {
			 "constraints", "Correct_Lon_Lat"
		   });			
		addAnnotation
		  (permissionEClass, 
		   source, 
		   new String[] {
			 "constraints", "Actions_Allowed"
		   });	
		addAnnotation
		  (permissionEClass, 
		   1,
		   "http://www.eclipse.org/emf/2002/GenModel",
		   new String[] {
			 "documentation", "invariant Permission_Start_Before_End:\nself.checkStartEndDates(self);"
		   });					
		addAnnotation
		  (resourceGroupEClass, 
		   source, 
		   new String[] {
			 "constraints", "Resource_Group_Not_Any_Cycle"
		   });						
		addAnnotation
		  (roleAssignmentEClass, 
		   source, 
		   new String[] {
			 "constraints", "At_least_User_or_Group SameRoleConcurrentAssignment UserWorksForOrganization"
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
		  (dataCenterEClass, 
		   source, 
		   new String[] {
			 "Unique_DataCenter_Per_Provider", "\n\t\t\tDataCenter.allInstances()->forAll(p1, p2 | p1 <> p2 and p1.ofCloudProvider=p2.ofCloudProvider implies p1.name <> p2.name and p1.codeName <> p2.codeName)",
			 "No_DataCenter_SameLocation_SameProvider", "\n\t\t\tDataCenter.allInstances()->forAll(p1, p2 | p1 <> p2 and p1.name <> p2.name and p1.ofCloudProvider = p2.ofCloudProvider implies p1.hasLocation <> p2.hasLocation)"
		   });			
		addAnnotation
		  (organizationEClass, 
		   source, 
		   new String[] {
			 "UniqueName_Email", "\n\t\t\tOrganization.allInstances()->forAll(p1, p2 |p1 <> p2 implies p1.email <> p2.email )"
		   });			
		addAnnotation
		  (userEClass, 
		   source, 
		   new String[] {
			 "UniqueName_ExternalIdentifier", "\n\t\t\tUser.allInstances()->forAll(p1, p2 |p1 <> p2 implies p1.hasExternalIdentifier->intersection(p2.hasExternalIdentifier)->size() = 0  )"
		   });			
		addAnnotation
		  (locationEClass, 
		   source, 
		   new String[] {
			 "Correct_Lon_Lat", "\n\t\t\tself.latitude >= -90.0 and self.latitude <= 90.0 and self.longitude >= -180.0 and self.longitude <= 180.0"
		   });			
		addAnnotation
		  (permissionEClass, 
		   source, 
		   new String[] {
			 "Actions_Allowed", "\n\t\t\t(self.onResource.oclIsTypeOf(ResourceGroup) and self.onResource.oclAsType(ResourceGroup).allowsActionsOnResources(self.toAction)) or (not(self.onResource.oclIsTypeOf(ResourceGroup)) and self.onResource.oclAsType(Resource).allowsActions(self.toAction))"
		   });
		addAnnotation
		  (permissionEClass, 
		   1,
		   "http://www.eclipse.org/emf/2002/GenModel",
		   new String[] {
			 "documentation", "invariant Permission_Start_Before_End:\nself.checkStartEndDates(self);"
		   });			
		addAnnotation
		  (getResource__OfClass__EClass(), 
		   source, 
		   new String[] {
			 "body", "self.oclType().name = x.name"
		   });		
		addAnnotation
		  (getResource__AllowsActions__EList(), 
		   source, 
		   new String[] {
			 "body", "acts->forAll(p | AllowedActions.allInstances()->exists(actions->select(type = p.type)->size() = 1 and self.ofClass(resourceClass)))"
		   });			
		addAnnotation
		  (resourceGroupEClass, 
		   source, 
		   new String[] {
			 "Resource_Group_Not_Any_Cycle", "\n\t\t\tnot(self.checkRecursiveness(self,self.oclAsType(Resource))) and self.containsResource->forAll(p1, p2 | (p1.oclIsTypeOf(ResourceGroup) and p2.oclIsTypeOf(ResourceGroup) and p1 <> p2) implies not(p1.oclAsType(ResourceGroup).checkRecursiveness(p1.oclAsType(ResourceGroup),p2)))"
		   });		
		addAnnotation
		  (getResourceGroup__AllowsActionsOnResources__EList(), 
		   source, 
		   new String[] {
			 "body", "self.containsResource->forAll(p | (p.oclIsTypeOf(ResourceGroup) and p.oclAsType(ResourceGroup).allowsActionsOnResources(actions)) or not(p.oclIsTypeOf(ResourceGroup)) and p.oclAsType(Resource).allowsActions(actions))"
		   });			
		addAnnotation
		  (getResourceGroup_Level(), 
		   source, 
		   new String[] {
			 "derivation", "if (self.containsResource->select(p | p.oclIsTypeOf(ResourceGroup))->size() = 0) then 1 else (self.containsResource->select(p | p.oclIsTypeOf(ResourceGroup) and (self.containsResource->forAll(p1 | p1.oclIsTypeOf(ResourceGroup) implies p.oclAsType(ResourceGroup).level >= p1.oclAsType(ResourceGroup).level)))->asOrderedSet()->first().oclAsType(ResourceGroup).level + 1) endif"
		   });			
		addAnnotation
		  (roleAssignmentEClass, 
		   source, 
		   new String[] {
			 "At_least_User_or_Group", "\n\t\t\tself.ofUser <> null or self.ofGroup <> null",
			 "SameRoleConcurrentAssignment", "\n\t\t\tRoleAssignment.allInstances()->forAll(p1, p2 |p1 <> p2  and p1.assignedBy = p2.assignedBy and (p1.ofUser = p2.ofUser or p1.ofGroup = p2.ofGroup) implies p1.toRole <> p2.toRole  )",
			 "UserWorksForOrganization", "\n\t\t\t(self.ofUser = null or (self.ofUser <> null and self.ofUser.worksFor->includes(self.assignedBy)) or (self.ofGroup <> null and self.ofGroup.member->forAll(worksFor->includes(self.assignedBy))))"
		   });		
	}

} //OrganisationPackageImpl
