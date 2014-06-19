/**
 */
package camel.impl;

import camel.*;

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
public class CamelFactoryImpl extends EFactoryImpl implements CamelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static CamelFactory init() {
		try {
			CamelFactory theCamelFactory = (CamelFactory)EPackage.Registry.INSTANCE.getEFactory(CamelPackage.eNS_URI);
			if (theCamelFactory != null) {
				return theCamelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new CamelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CamelFactoryImpl() {
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
			case CamelPackage.CAMEL_MODEL: return (EObject)createCamelModel();
			case CamelPackage.ACTION: return (EObject)createAction();
			case CamelPackage.PHYSICAL_NODE: return (EObject)createPhysicalNode();
			case CamelPackage.DATA_OBJECT: return (EObject)createDataObject();
			case CamelPackage.REQUIREMENT_GROUP: return (EObject)createRequirementGroup();
			case CamelPackage.REQUIREMENT: return (EObject)createRequirement();
			case CamelPackage.SCALABILITY_POLICY: return (EObject)createScalabilityPolicy();
			case CamelPackage.VM_INFO: return (EObject)createVMInfo();
			case CamelPackage.CLOUD_INDEPENDENT_VM_INFO: return (EObject)createCloudIndependentVMInfo();
			case CamelPackage.VM_TYPE: return (EObject)createVMType();
			case CamelPackage.APPLICATION: return (EObject)createApplication();
			case CamelPackage.MONETARY_UNIT: return (EObject)createMonetaryUnit();
			case CamelPackage.STORAGE_UNIT: return (EObject)createStorageUnit();
			case CamelPackage.TIME_INTERVAL_UNIT: return (EObject)createTimeIntervalUnit();
			case CamelPackage.THROUGHPUT_UNIT: return (EObject)createThroughputUnit();
			case CamelPackage.REQUEST_UNIT: return (EObject)createRequestUnit();
			case CamelPackage.UNITLESS: return (EObject)createUnitless();
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
			case CamelPackage.ACTION_TYPE:
				return createActionTypeFromString(eDataType, initialValue);
			case CamelPackage.REQUIREMENT_OPERATOR_TYPE:
				return createRequirementOperatorTypeFromString(eDataType, initialValue);
			case CamelPackage.SCALABILITY_TYPE:
				return createScalabilityTypeFromString(eDataType, initialValue);
			case CamelPackage.UNIT_TYPE:
				return createUnitTypeFromString(eDataType, initialValue);
			case CamelPackage.UNIT_DIMENSION_TYPE:
				return createUnitDimensionTypeFromString(eDataType, initialValue);
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
			case CamelPackage.ACTION_TYPE:
				return convertActionTypeToString(eDataType, instanceValue);
			case CamelPackage.REQUIREMENT_OPERATOR_TYPE:
				return convertRequirementOperatorTypeToString(eDataType, instanceValue);
			case CamelPackage.SCALABILITY_TYPE:
				return convertScalabilityTypeToString(eDataType, instanceValue);
			case CamelPackage.UNIT_TYPE:
				return convertUnitTypeToString(eDataType, instanceValue);
			case CamelPackage.UNIT_DIMENSION_TYPE:
				return convertUnitDimensionTypeToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CamelModel createCamelModel() {
		CamelModelImpl camelModel = new CamelModelImpl();
		return camelModel;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Action createAction() {
		ActionImpl action = new ActionImpl();
		return action;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PhysicalNode createPhysicalNode() {
		PhysicalNodeImpl physicalNode = new PhysicalNodeImpl();
		return physicalNode;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataObject createDataObject() {
		DataObjectImpl dataObject = new DataObjectImpl();
		return dataObject;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementGroup createRequirementGroup() {
		RequirementGroupImpl requirementGroup = new RequirementGroupImpl();
		return requirementGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Requirement createRequirement() {
		RequirementImpl requirement = new RequirementImpl();
		return requirement;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityPolicy createScalabilityPolicy() {
		ScalabilityPolicyImpl scalabilityPolicy = new ScalabilityPolicyImpl();
		return scalabilityPolicy;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMInfo createVMInfo() {
		VMInfoImpl vmInfo = new VMInfoImpl();
		return vmInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CloudIndependentVMInfo createCloudIndependentVMInfo() {
		CloudIndependentVMInfoImpl cloudIndependentVMInfo = new CloudIndependentVMInfoImpl();
		return cloudIndependentVMInfo;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VMType createVMType() {
		VMTypeImpl vmType = new VMTypeImpl();
		return vmType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Application createApplication() {
		ApplicationImpl application = new ApplicationImpl();
		return application;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MonetaryUnit createMonetaryUnit() {
		MonetaryUnitImpl monetaryUnit = new MonetaryUnitImpl();
		return monetaryUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StorageUnit createStorageUnit() {
		StorageUnitImpl storageUnit = new StorageUnitImpl();
		return storageUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeIntervalUnit createTimeIntervalUnit() {
		TimeIntervalUnitImpl timeIntervalUnit = new TimeIntervalUnitImpl();
		return timeIntervalUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ThroughputUnit createThroughputUnit() {
		ThroughputUnitImpl throughputUnit = new ThroughputUnitImpl();
		return throughputUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequestUnit createRequestUnit() {
		RequestUnitImpl requestUnit = new RequestUnitImpl();
		return requestUnit;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Unitless createUnitless() {
		UnitlessImpl unitless = new UnitlessImpl();
		return unitless;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ActionType createActionTypeFromString(EDataType eDataType, String initialValue) {
		ActionType result = ActionType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertActionTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequirementOperatorType createRequirementOperatorTypeFromString(EDataType eDataType, String initialValue) {
		RequirementOperatorType result = RequirementOperatorType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRequirementOperatorTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScalabilityType createScalabilityTypeFromString(EDataType eDataType, String initialValue) {
		ScalabilityType result = ScalabilityType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertScalabilityTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnitType createUnitTypeFromString(EDataType eDataType, String initialValue) {
		UnitType result = UnitType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUnitTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UnitDimensionType createUnitDimensionTypeFromString(EDataType eDataType, String initialValue) {
		UnitDimensionType result = UnitDimensionType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertUnitDimensionTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CamelPackage getCamelPackage() {
		return (CamelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static CamelPackage getPackage() {
		return CamelPackage.eINSTANCE;
	}

} //CamelFactoryImpl
