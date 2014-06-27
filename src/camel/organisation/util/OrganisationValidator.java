/**
 */
package camel.organisation.util;

import camel.organisation.*;

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
 * @see camel.organisation.OrganisationPackage
 * @generated
 */
public class OrganisationValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final OrganisationValidator INSTANCE = new OrganisationValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "camel.organisation";

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
	public OrganisationValidator() {
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
	  return OrganisationPackage.eINSTANCE;
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
			case OrganisationPackage.ORGANISATION_MODEL:
				return validateOrganisationModel((OrganisationModel)value, diagnostics, context);
			case OrganisationPackage.ALLOWED_ACTIONS:
				return validateAllowedActions((AllowedActions)value, diagnostics, context);
			case OrganisationPackage.CREDENTIALS:
				return validateCredentials((Credentials)value, diagnostics, context);
			case OrganisationPackage.DATA_CENTER:
				return validateDataCenter((DataCenter)value, diagnostics, context);
			case OrganisationPackage.ENTITY:
				return validateEntity((Entity)value, diagnostics, context);
			case OrganisationPackage.ORGANIZATION:
				return validateOrganization((Organization)value, diagnostics, context);
			case OrganisationPackage.CLOUD_PROVIDER:
				return validateCloudProvider((CloudProvider)value, diagnostics, context);
			case OrganisationPackage.USER:
				return validateUser((User)value, diagnostics, context);
			case OrganisationPackage.EXTERNAL_IDENTIFIER:
				return validateExternalIdentifier((ExternalIdentifier)value, diagnostics, context);
			case OrganisationPackage.LOCATION:
				return validateLocation((Location)value, diagnostics, context);
			case OrganisationPackage.PERMISSION:
				return validatePermission((Permission)value, diagnostics, context);
			case OrganisationPackage.RESOURCE:
				return validateResource((Resource)value, diagnostics, context);
			case OrganisationPackage.RESOURCE_GROUP:
				return validateResourceGroup((ResourceGroup)value, diagnostics, context);
			case OrganisationPackage.ROLE:
				return validateRole((Role)value, diagnostics, context);
			case OrganisationPackage.ROLE_ASSIGNMENT:
				return validateRoleAssignment((RoleAssignment)value, diagnostics, context);
			case OrganisationPackage.USER_GROUP:
				return validateUserGroup((UserGroup)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOrganisationModel(OrganisationModel organisationModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)organisationModel, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAllowedActions(AllowedActions allowedActions, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)allowedActions, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCredentials(Credentials credentials, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)credentials, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDataCenter(DataCenter dataCenter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)dataCenter, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)dataCenter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)dataCenter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)dataCenter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)dataCenter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)dataCenter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)dataCenter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)dataCenter, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)dataCenter, diagnostics, context);
		if (result || diagnostics != null) result &= validateDataCenter_Unique_DataCenter_Per_Provider(dataCenter, diagnostics, context);
		if (result || diagnostics != null) result &= validateDataCenter_No_DataCenter_SameLocation_SameProvider(dataCenter, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Unique_DataCenter_Per_Provider constraint of '<em>Data Center</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String DATA_CENTER__UNIQUE_DATA_CENTER_PER_PROVIDER__EEXPRESSION = "\n" +
		"\t\t\tDataCenter.allInstances()->forAll(p1, p2 | p1 <> p2 and p1.ofCloudProvider=p2.ofCloudProvider implies p1.name <> p2.name and p1.codeName <> p2.codeName)";

	/**
	 * Validates the Unique_DataCenter_Per_Provider constraint of '<em>Data Center</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDataCenter_Unique_DataCenter_Per_Provider(DataCenter dataCenter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.DATA_CENTER,
				 dataCenter,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Unique_DataCenter_Per_Provider",
				 DATA_CENTER__UNIQUE_DATA_CENTER_PER_PROVIDER__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the No_DataCenter_SameLocation_SameProvider constraint of '<em>Data Center</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String DATA_CENTER__NO_DATA_CENTER_SAME_LOCATION_SAME_PROVIDER__EEXPRESSION = "\n" +
		"\t\t\tDataCenter.allInstances()->forAll(p1, p2 | p1 <> p2 and p1.name <> p2.name and p1.ofCloudProvider = p2.ofCloudProvider implies p1.hasLocation <> p2.hasLocation)";

	/**
	 * Validates the No_DataCenter_SameLocation_SameProvider constraint of '<em>Data Center</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDataCenter_No_DataCenter_SameLocation_SameProvider(DataCenter dataCenter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.DATA_CENTER,
				 dataCenter,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "No_DataCenter_SameLocation_SameProvider",
				 DATA_CENTER__NO_DATA_CENTER_SAME_LOCATION_SAME_PROVIDER__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEntity(Entity entity, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)entity, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOrganization(Organization organization, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)organization, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)organization, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)organization, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)organization, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)organization, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)organization, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)organization, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)organization, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)organization, diagnostics, context);
		if (result || diagnostics != null) result &= validateOrganization_UniqueName_Email(organization, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the UniqueName_Email constraint of '<em>Organization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ORGANIZATION__UNIQUE_NAME_EMAIL__EEXPRESSION = "\n" +
		"\t\t\tOrganization.allInstances()->forAll(p1, p2 |p1 <> p2 implies p1.email <> p2.email )";

	/**
	 * Validates the UniqueName_Email constraint of '<em>Organization</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOrganization_UniqueName_Email(Organization organization, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.ORGANIZATION,
				 organization,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "UniqueName_Email",
				 ORGANIZATION__UNIQUE_NAME_EMAIL__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCloudProvider(CloudProvider cloudProvider, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)cloudProvider, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)cloudProvider, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)cloudProvider, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)cloudProvider, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)cloudProvider, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)cloudProvider, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)cloudProvider, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)cloudProvider, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)cloudProvider, diagnostics, context);
		if (result || diagnostics != null) result &= validateOrganization_UniqueName_Email(cloudProvider, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUser(User user, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)user, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)user, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)user, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)user, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)user, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)user, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)user, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)user, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)user, diagnostics, context);
		if (result || diagnostics != null) result &= validateUser_UniqueName_ExternalIdentifier(user, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the UniqueName_ExternalIdentifier constraint of '<em>User</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String USER__UNIQUE_NAME_EXTERNAL_IDENTIFIER__EEXPRESSION = "\n" +
		"\t\t\tUser.allInstances()->forAll(p1, p2 |p1 <> p2 implies p1.hasExternalIdentifier->intersection(p2.hasExternalIdentifier)->size() = 0  )";

	/**
	 * Validates the UniqueName_ExternalIdentifier constraint of '<em>User</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUser_UniqueName_ExternalIdentifier(User user, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.USER,
				 user,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "UniqueName_ExternalIdentifier",
				 USER__UNIQUE_NAME_EXTERNAL_IDENTIFIER__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExternalIdentifier(ExternalIdentifier externalIdentifier, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)externalIdentifier, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLocation(Location location, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)location, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)location, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)location, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)location, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)location, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)location, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)location, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)location, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)location, diagnostics, context);
		if (result || diagnostics != null) result &= validateLocation_Correct_Lon_Lat(location, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Correct_Lon_Lat constraint of '<em>Location</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String LOCATION__CORRECT_LON_LAT__EEXPRESSION = "\n" +
		"\t\t\tself.latitude >= -90.0 and self.latitude <= 90.0 and self.longitude >= -180.0 and self.longitude <= 180.0";

	/**
	 * Validates the Correct_Lon_Lat constraint of '<em>Location</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLocation_Correct_Lon_Lat(Location location, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.LOCATION,
				 location,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Correct_Lon_Lat",
				 LOCATION__CORRECT_LON_LAT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePermission(Permission permission, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)permission, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)permission, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)permission, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)permission, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)permission, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)permission, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)permission, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)permission, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)permission, diagnostics, context);
		if (result || diagnostics != null) result &= validatePermission_Actions_Allowed(permission, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Actions_Allowed constraint of '<em>Permission</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String PERMISSION__ACTIONS_ALLOWED__EEXPRESSION = "\n" +
		"\t\t\t(self.onResource.oclIsTypeOf(ResourceGroup) and self.onResource.oclAsType(ResourceGroup).allowsActionsOnResources(self.toAction)) or (not(self.onResource.oclIsTypeOf(ResourceGroup)) and self.onResource.oclAsType(Resource).allowsActions(self.toAction))";

	/**
	 * Validates the Actions_Allowed constraint of '<em>Permission</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePermission_Actions_Allowed(Permission permission, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.PERMISSION,
				 permission,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Actions_Allowed",
				 PERMISSION__ACTIONS_ALLOWED__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResource(Resource resource, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)resource, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceGroup(ResourceGroup resourceGroup, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)resourceGroup, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)resourceGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)resourceGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)resourceGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)resourceGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)resourceGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)resourceGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)resourceGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)resourceGroup, diagnostics, context);
		if (result || diagnostics != null) result &= validateResourceGroup_Resource_Group_Not_Any_Cycle(resourceGroup, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the Resource_Group_Not_Any_Cycle constraint of '<em>Resource Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String RESOURCE_GROUP__RESOURCE_GROUP_NOT_ANY_CYCLE__EEXPRESSION = "\n" +
		"\t\t\tnot(self.checkRecursiveness(self,self.oclAsType(Resource))) and self.containsResource->forAll(p1, p2 | (p1.oclIsTypeOf(ResourceGroup) and p2.oclIsTypeOf(ResourceGroup) and p1 <> p2) implies not(p1.oclAsType(ResourceGroup).checkRecursiveness(p1.oclAsType(ResourceGroup),p2)))";

	/**
	 * Validates the Resource_Group_Not_Any_Cycle constraint of '<em>Resource Group</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceGroup_Resource_Group_Not_Any_Cycle(ResourceGroup resourceGroup, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.RESOURCE_GROUP,
				 resourceGroup,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "Resource_Group_Not_Any_Cycle",
				 RESOURCE_GROUP__RESOURCE_GROUP_NOT_ANY_CYCLE__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRole(Role role, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)role, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleAssignment(RoleAssignment roleAssignment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)roleAssignment, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validateRoleAssignment_At_least_User_or_Group(roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validateRoleAssignment_SameRoleConcurrentAssignment(roleAssignment, diagnostics, context);
		if (result || diagnostics != null) result &= validateRoleAssignment_UserWorksForOrganization(roleAssignment, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the At_least_User_or_Group constraint of '<em>Role Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ROLE_ASSIGNMENT__AT_LEAST_USER_OR_GROUP__EEXPRESSION = "\n" +
		"\t\t\tself.ofUser <> null or self.ofGroup <> null";

	/**
	 * Validates the At_least_User_or_Group constraint of '<em>Role Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleAssignment_At_least_User_or_Group(RoleAssignment roleAssignment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.ROLE_ASSIGNMENT,
				 roleAssignment,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "At_least_User_or_Group",
				 ROLE_ASSIGNMENT__AT_LEAST_USER_OR_GROUP__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the SameRoleConcurrentAssignment constraint of '<em>Role Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ROLE_ASSIGNMENT__SAME_ROLE_CONCURRENT_ASSIGNMENT__EEXPRESSION = "\n" +
		"\t\t\tRoleAssignment.allInstances()->forAll(p1, p2 |p1 <> p2  and p1.assignedBy = p2.assignedBy and (p1.ofUser = p2.ofUser or p1.ofGroup = p2.ofGroup) implies p1.toRole <> p2.toRole  )";

	/**
	 * Validates the SameRoleConcurrentAssignment constraint of '<em>Role Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleAssignment_SameRoleConcurrentAssignment(RoleAssignment roleAssignment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.ROLE_ASSIGNMENT,
				 roleAssignment,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "SameRoleConcurrentAssignment",
				 ROLE_ASSIGNMENT__SAME_ROLE_CONCURRENT_ASSIGNMENT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * The cached validation expression for the UserWorksForOrganization constraint of '<em>Role Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String ROLE_ASSIGNMENT__USER_WORKS_FOR_ORGANIZATION__EEXPRESSION = "\n" +
		"\t\t\t(self.ofUser = null or (self.ofUser <> null and self.ofUser.worksFor->includes(self.assignedBy)) or (self.ofGroup <> null and self.ofGroup.member->forAll(worksFor->includes(self.assignedBy))))";

	/**
	 * Validates the UserWorksForOrganization constraint of '<em>Role Assignment</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRoleAssignment_UserWorksForOrganization(RoleAssignment roleAssignment, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(OrganisationPackage.Literals.ROLE_ASSIGNMENT,
				 roleAssignment,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "UserWorksForOrganization",
				 ROLE_ASSIGNMENT__USER_WORKS_FOR_ORGANIZATION__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUserGroup(UserGroup userGroup, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)userGroup, diagnostics, context);
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

} //OrganisationValidator
