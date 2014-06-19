/**
 */
package camel.util;

import camel.*;

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
 * @see camel.CamelPackage
 * @generated
 */
public class CamelValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final CamelValidator INSTANCE = new CamelValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "camel";

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
	public CamelValidator() {
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
	  return CamelPackage.eINSTANCE;
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
			case CamelPackage.CAMEL_MODEL:
				return validateCamelModel((CamelModel)value, diagnostics, context);
			case CamelPackage.ACTION:
				return validateAction((Action)value, diagnostics, context);
			case CamelPackage.PHYSICAL_NODE:
				return validatePhysicalNode((PhysicalNode)value, diagnostics, context);
			case CamelPackage.DATA_OBJECT:
				return validateDataObject((DataObject)value, diagnostics, context);
			case CamelPackage.REQUIREMENT_GROUP:
				return validateRequirementGroup((RequirementGroup)value, diagnostics, context);
			case CamelPackage.REQUIREMENT:
				return validateRequirement((Requirement)value, diagnostics, context);
			case CamelPackage.SCALABILITY_POLICY:
				return validateScalabilityPolicy((ScalabilityPolicy)value, diagnostics, context);
			case CamelPackage.VM_INFO:
				return validateVMInfo((VMInfo)value, diagnostics, context);
			case CamelPackage.CLOUD_INDEPENDENT_VM_INFO:
				return validateCloudIndependentVMInfo((CloudIndependentVMInfo)value, diagnostics, context);
			case CamelPackage.VM_TYPE:
				return validateVMType((VMType)value, diagnostics, context);
			case CamelPackage.APPLICATION:
				return validateApplication((Application)value, diagnostics, context);
			case CamelPackage.UNIT:
				return validateUnit((Unit)value, diagnostics, context);
			case CamelPackage.MONETARY_UNIT:
				return validateMonetaryUnit((MonetaryUnit)value, diagnostics, context);
			case CamelPackage.STORAGE_UNIT:
				return validateStorageUnit((StorageUnit)value, diagnostics, context);
			case CamelPackage.TIME_INTERVAL_UNIT:
				return validateTimeIntervalUnit((TimeIntervalUnit)value, diagnostics, context);
			case CamelPackage.THROUGHPUT_UNIT:
				return validateThroughputUnit((ThroughputUnit)value, diagnostics, context);
			case CamelPackage.REQUEST_UNIT:
				return validateRequestUnit((RequestUnit)value, diagnostics, context);
			case CamelPackage.UNITLESS:
				return validateUnitless((Unitless)value, diagnostics, context);
			case CamelPackage.ACTION_TYPE:
				return validateActionType((ActionType)value, diagnostics, context);
			case CamelPackage.REQUIREMENT_OPERATOR_TYPE:
				return validateRequirementOperatorType((RequirementOperatorType)value, diagnostics, context);
			case CamelPackage.SCALABILITY_TYPE:
				return validateScalabilityType((ScalabilityType)value, diagnostics, context);
			case CamelPackage.UNIT_TYPE:
				return validateUnitType((UnitType)value, diagnostics, context);
			case CamelPackage.UNIT_DIMENSION_TYPE:
				return validateUnitDimensionType((UnitDimensionType)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCamelModel(CamelModel camelModel, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)camelModel, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateAction(Action action, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)action, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePhysicalNode(PhysicalNode physicalNode, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)physicalNode, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDataObject(DataObject dataObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)dataObject, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequirementGroup(RequirementGroup requirementGroup, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)requirementGroup, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequirement(Requirement requirement, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)requirement, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalabilityPolicy(ScalabilityPolicy scalabilityPolicy, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)scalabilityPolicy, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVMInfo(VMInfo vmInfo, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)vmInfo, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCloudIndependentVMInfo(CloudIndependentVMInfo cloudIndependentVMInfo, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)cloudIndependentVMInfo, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVMType(VMType vmType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)vmType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateApplication(Application application, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint((EObject)application, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnit(Unit unit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)unit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)unit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)unit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_correctUnit(unit, diagnostics, context);
		return result;
	}

	/**
	 * The cached validation expression for the correctUnit constraint of '<em>Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final String UNIT__CORRECT_UNIT__EEXPRESSION = "\n" +
		"\t\t\t\t\t\tcheckUnit()";

	/**
	 * Validates the correctUnit constraint of '<em>Unit</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnit_correctUnit(Unit unit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return
			validate
				(CamelPackage.Literals.UNIT,
				 unit,
				 diagnostics,
				 context,
				 "http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot",
				 "correctUnit",
				 UNIT__CORRECT_UNIT__EEXPRESSION,
				 Diagnostic.ERROR,
				 DIAGNOSTIC_SOURCE,
				 0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMonetaryUnit(MonetaryUnit monetaryUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)monetaryUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)monetaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)monetaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)monetaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)monetaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)monetaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)monetaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)monetaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)monetaryUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_correctUnit(monetaryUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStorageUnit(StorageUnit storageUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)storageUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)storageUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)storageUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)storageUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)storageUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)storageUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)storageUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)storageUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)storageUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_correctUnit(storageUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTimeIntervalUnit(TimeIntervalUnit timeIntervalUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)timeIntervalUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)timeIntervalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)timeIntervalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)timeIntervalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)timeIntervalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)timeIntervalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)timeIntervalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)timeIntervalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)timeIntervalUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_correctUnit(timeIntervalUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateThroughputUnit(ThroughputUnit throughputUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)throughputUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)throughputUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)throughputUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)throughputUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)throughputUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)throughputUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)throughputUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)throughputUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)throughputUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_correctUnit(throughputUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequestUnit(RequestUnit requestUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)requestUnit, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)requestUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)requestUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)requestUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)requestUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)requestUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)requestUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)requestUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)requestUnit, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_correctUnit(requestUnit, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnitless(Unitless unitless, DiagnosticChain diagnostics, Map<Object, Object> context) {
		if (!validate_NoCircularContainment((EObject)unitless, diagnostics, context)) return false;
		boolean result = validate_EveryMultiplicityConforms((EObject)unitless, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryDataValueConforms((EObject)unitless, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryReferenceIsContained((EObject)unitless, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryBidirectionalReferenceIsPaired((EObject)unitless, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryProxyResolves((EObject)unitless, diagnostics, context);
		if (result || diagnostics != null) result &= validate_UniqueID((EObject)unitless, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryKeyUnique((EObject)unitless, diagnostics, context);
		if (result || diagnostics != null) result &= validate_EveryMapEntryUnique((EObject)unitless, diagnostics, context);
		if (result || diagnostics != null) result &= validateUnit_correctUnit(unitless, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateActionType(ActionType actionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRequirementOperatorType(RequirementOperatorType requirementOperatorType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScalabilityType(ScalabilityType scalabilityType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnitType(UnitType unitType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUnitDimensionType(UnitDimensionType unitDimensionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
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

} //CamelValidator
