/**
 */
package camel.type.impl;

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

import camel.sla.SlaPackage;

import camel.sla.impl.SlaPackageImpl;

import camel.type.BooleanValue;
import camel.type.BooleanValueType;
import camel.type.DoubleValue;
import camel.type.EnumerateValue;
import camel.type.Enumeration;
import camel.type.FloatValue;
import camel.type.IntValue;
import camel.type.Limit;
import camel.type.List;
import camel.type.NegativeInf;
import camel.type.NumericValue;
import camel.type.PositiveInf;
import camel.type.Range;
import camel.type.RangeUnion;
import camel.type.StringValue;
import camel.type.StringValueType;
import camel.type.TypeEnum;
import camel.type.TypeFactory;
import camel.type.TypePackage;
import camel.type.TypeRepository;
import camel.type.Value;
import camel.type.ValueToIncrease;
import camel.type.ValueType;

import camel.type.util.TypeValidator;

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
public class TypePackageImpl extends EPackageImpl implements TypePackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass typeRepositoryEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass limitEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerateValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numericValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass intValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass floatValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass doubleValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass negativeInfEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass positiveInfEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueToIncreaseEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanValueTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumerationEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass listEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rangeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass rangeUnionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringValueTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum typeEnumEEnum = null;

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
	 * @see camel.type.TypePackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TypePackageImpl() {
		super(eNS_URI, TypeFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TypePackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TypePackage init() {
		if (isInited) return (TypePackage)EPackage.Registry.INSTANCE.getEPackage(TypePackage.eNS_URI);

		// Obtain or create and register package
		TypePackageImpl theTypePackage = (TypePackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TypePackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TypePackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		CamelPackageImpl theCamelPackage = (CamelPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) instanceof CamelPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CamelPackage.eNS_URI) : CamelPackage.eINSTANCE);
		DeploymentPackageImpl theDeploymentPackage = (DeploymentPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) instanceof DeploymentPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(DeploymentPackage.eNS_URI) : DeploymentPackage.eINSTANCE);
		ExecutionPackageImpl theExecutionPackage = (ExecutionPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) instanceof ExecutionPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ExecutionPackage.eNS_URI) : ExecutionPackage.eINSTANCE);
		OrganisationPackageImpl theOrganisationPackage = (OrganisationPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) instanceof OrganisationPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(OrganisationPackage.eNS_URI) : OrganisationPackage.eINSTANCE);
		ProviderPackageImpl theProviderPackage = (ProviderPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) instanceof ProviderPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ProviderPackage.eNS_URI) : ProviderPackage.eINSTANCE);
		ScalabilityPackageImpl theScalabilityPackage = (ScalabilityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) instanceof ScalabilityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(ScalabilityPackage.eNS_URI) : ScalabilityPackage.eINSTANCE);
		SecurityPackageImpl theSecurityPackage = (SecurityPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) instanceof SecurityPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SecurityPackage.eNS_URI) : SecurityPackage.eINSTANCE);
		SlaPackageImpl theSlaPackage = (SlaPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) instanceof SlaPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(SlaPackage.eNS_URI) : SlaPackage.eINSTANCE);

		// Create package meta-data objects
		theTypePackage.createPackageContents();
		theCamelPackage.createPackageContents();
		theDeploymentPackage.createPackageContents();
		theExecutionPackage.createPackageContents();
		theOrganisationPackage.createPackageContents();
		theProviderPackage.createPackageContents();
		theScalabilityPackage.createPackageContents();
		theSecurityPackage.createPackageContents();
		theSlaPackage.createPackageContents();

		// Initialize created meta-data
		theTypePackage.initializePackageContents();
		theCamelPackage.initializePackageContents();
		theDeploymentPackage.initializePackageContents();
		theExecutionPackage.initializePackageContents();
		theOrganisationPackage.initializePackageContents();
		theProviderPackage.initializePackageContents();
		theScalabilityPackage.initializePackageContents();
		theSecurityPackage.initializePackageContents();
		theSlaPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theTypePackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return TypeValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theTypePackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TypePackage.eNS_URI, theTypePackage);
		return theTypePackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTypeRepository() {
		return typeRepositoryEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTypeRepository_DataTypes() {
		return (EReference)typeRepositoryEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTypeRepository_Values() {
		return (EReference)typeRepositoryEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLimit() {
		return limitEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLimit_Included() {
		return (EAttribute)limitEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getLimit_Value() {
		return (EReference)limitEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValue() {
		return valueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getValue__ValueEquals__Value() {
		return valueEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanValue() {
		return booleanValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanValue_Value() {
		return (EAttribute)booleanValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumerateValue() {
		return enumerateValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumerateValue_Value() {
		return (EAttribute)enumerateValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumerateValue_Name() {
		return (EAttribute)enumerateValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumericValue() {
		return numericValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntValue() {
		return intValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntValue_Value() {
		return (EAttribute)intValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFloatValue() {
		return floatValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFloatValue_Value() {
		return (EAttribute)floatValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDoubleValue() {
		return doubleValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDoubleValue_Value() {
		return (EAttribute)doubleValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNegativeInf() {
		return negativeInfEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPositiveInf() {
		return positiveInfEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueToIncrease() {
		return valueToIncreaseEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getValueToIncrease_Value() {
		return (EReference)valueToIncreaseEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringValue() {
		return stringValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringValue_Value() {
		return (EAttribute)stringValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueType() {
		return valueTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanValueType() {
		return booleanValueTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanValueType_PrimitiveType() {
		return (EAttribute)booleanValueTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumeration() {
		return enumerationEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumeration_Values() {
		return (EReference)enumerationEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumeration__IncludesName__String() {
		return enumerationEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getEnumeration__IncludesValue__int() {
		return enumerationEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getList() {
		return listEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getList_Values() {
		return (EReference)listEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getList_PrimitiveType() {
		return (EAttribute)listEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getList_Type() {
		return (EReference)listEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getList__IncludesValue__Value() {
		return listEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getList__CheckValueType__Value() {
		return listEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRange() {
		return rangeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRange_LowerLimit() {
		return (EReference)rangeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRange_UpperLimit() {
		return (EReference)rangeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRange_PrimitiveType() {
		return (EAttribute)rangeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRange__CheckType__Limit_TypeEnum_boolean() {
		return rangeEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRange__IncludesValue__double() {
		return rangeEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRangeUnion() {
		return rangeUnionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getRangeUnion_Ranges() {
		return (EReference)rangeUnionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRangeUnion_PrimitiveType() {
		return (EAttribute)rangeUnionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRangeUnion__IncludesValue__double() {
		return rangeUnionEClass.getEOperations().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EOperation getRangeUnion__InvalidRangeSequence__RangeUnion() {
		return rangeUnionEClass.getEOperations().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringValueType() {
		return stringValueTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringValueType_PrimitiveType() {
		return (EAttribute)stringValueTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTypeEnum() {
		return typeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypeFactory getTypeFactory() {
		return (TypeFactory)getEFactoryInstance();
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
		typeRepositoryEClass = createEClass(TYPE_REPOSITORY);
		createEReference(typeRepositoryEClass, TYPE_REPOSITORY__DATA_TYPES);
		createEReference(typeRepositoryEClass, TYPE_REPOSITORY__VALUES);

		limitEClass = createEClass(LIMIT);
		createEAttribute(limitEClass, LIMIT__INCLUDED);
		createEReference(limitEClass, LIMIT__VALUE);

		valueEClass = createEClass(VALUE);
		createEOperation(valueEClass, VALUE___VALUE_EQUALS__VALUE);

		booleanValueEClass = createEClass(BOOLEAN_VALUE);
		createEAttribute(booleanValueEClass, BOOLEAN_VALUE__VALUE);

		enumerateValueEClass = createEClass(ENUMERATE_VALUE);
		createEAttribute(enumerateValueEClass, ENUMERATE_VALUE__VALUE);
		createEAttribute(enumerateValueEClass, ENUMERATE_VALUE__NAME);

		numericValueEClass = createEClass(NUMERIC_VALUE);

		intValueEClass = createEClass(INT_VALUE);
		createEAttribute(intValueEClass, INT_VALUE__VALUE);

		floatValueEClass = createEClass(FLOAT_VALUE);
		createEAttribute(floatValueEClass, FLOAT_VALUE__VALUE);

		doubleValueEClass = createEClass(DOUBLE_VALUE);
		createEAttribute(doubleValueEClass, DOUBLE_VALUE__VALUE);

		negativeInfEClass = createEClass(NEGATIVE_INF);

		positiveInfEClass = createEClass(POSITIVE_INF);

		valueToIncreaseEClass = createEClass(VALUE_TO_INCREASE);
		createEReference(valueToIncreaseEClass, VALUE_TO_INCREASE__VALUE);

		stringValueEClass = createEClass(STRING_VALUE);
		createEAttribute(stringValueEClass, STRING_VALUE__VALUE);

		valueTypeEClass = createEClass(VALUE_TYPE);

		booleanValueTypeEClass = createEClass(BOOLEAN_VALUE_TYPE);
		createEAttribute(booleanValueTypeEClass, BOOLEAN_VALUE_TYPE__PRIMITIVE_TYPE);

		enumerationEClass = createEClass(ENUMERATION);
		createEReference(enumerationEClass, ENUMERATION__VALUES);
		createEOperation(enumerationEClass, ENUMERATION___INCLUDES_NAME__STRING);
		createEOperation(enumerationEClass, ENUMERATION___INCLUDES_VALUE__INT);

		listEClass = createEClass(LIST);
		createEReference(listEClass, LIST__VALUES);
		createEAttribute(listEClass, LIST__PRIMITIVE_TYPE);
		createEReference(listEClass, LIST__TYPE);
		createEOperation(listEClass, LIST___INCLUDES_VALUE__VALUE);
		createEOperation(listEClass, LIST___CHECK_VALUE_TYPE__VALUE);

		rangeEClass = createEClass(RANGE);
		createEReference(rangeEClass, RANGE__LOWER_LIMIT);
		createEReference(rangeEClass, RANGE__UPPER_LIMIT);
		createEAttribute(rangeEClass, RANGE__PRIMITIVE_TYPE);
		createEOperation(rangeEClass, RANGE___CHECK_TYPE__LIMIT_TYPEENUM_BOOLEAN);
		createEOperation(rangeEClass, RANGE___INCLUDES_VALUE__DOUBLE);

		rangeUnionEClass = createEClass(RANGE_UNION);
		createEReference(rangeUnionEClass, RANGE_UNION__RANGES);
		createEAttribute(rangeUnionEClass, RANGE_UNION__PRIMITIVE_TYPE);
		createEOperation(rangeUnionEClass, RANGE_UNION___INCLUDES_VALUE__DOUBLE);
		createEOperation(rangeUnionEClass, RANGE_UNION___INVALID_RANGE_SEQUENCE__RANGEUNION);

		stringValueTypeEClass = createEClass(STRING_VALUE_TYPE);
		createEAttribute(stringValueTypeEClass, STRING_VALUE_TYPE__PRIMITIVE_TYPE);

		// Create enums
		typeEnumEEnum = createEEnum(TYPE_ENUM);
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

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		booleanValueEClass.getESuperTypes().add(this.getValue());
		enumerateValueEClass.getESuperTypes().add(this.getValue());
		numericValueEClass.getESuperTypes().add(this.getValue());
		intValueEClass.getESuperTypes().add(this.getNumericValue());
		floatValueEClass.getESuperTypes().add(this.getNumericValue());
		doubleValueEClass.getESuperTypes().add(this.getNumericValue());
		negativeInfEClass.getESuperTypes().add(this.getNumericValue());
		positiveInfEClass.getESuperTypes().add(this.getNumericValue());
		valueToIncreaseEClass.getESuperTypes().add(this.getNumericValue());
		stringValueEClass.getESuperTypes().add(this.getValue());
		booleanValueTypeEClass.getESuperTypes().add(this.getValueType());
		enumerationEClass.getESuperTypes().add(this.getValueType());
		listEClass.getESuperTypes().add(this.getValueType());
		rangeEClass.getESuperTypes().add(this.getValueType());
		rangeUnionEClass.getESuperTypes().add(this.getValueType());
		stringValueTypeEClass.getESuperTypes().add(this.getValueType());

		// Initialize classes, features, and operations; add parameters
		initEClass(typeRepositoryEClass, TypeRepository.class, "TypeRepository", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTypeRepository_DataTypes(), this.getValueType(), null, "dataTypes", null, 0, -1, TypeRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTypeRepository_Values(), this.getValue(), null, "values", null, 0, -1, TypeRepository.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(limitEClass, Limit.class, "Limit", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLimit_Included(), ecorePackage.getEBoolean(), "included", null, 1, 1, Limit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getLimit_Value(), this.getNumericValue(), null, "value", null, 1, 1, Limit.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueEClass, Value.class, "Value", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		EOperation op = initEOperation(getValue__ValueEquals__Value(), ecorePackage.getEBoolean(), "valueEquals", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getValue(), "v", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(booleanValueEClass, BooleanValue.class, "BooleanValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanValue_Value(), ecorePackage.getEBoolean(), "value", null, 1, 1, BooleanValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumerateValueEClass, EnumerateValue.class, "EnumerateValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumerateValue_Value(), ecorePackage.getEInt(), "value", null, 1, 1, EnumerateValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumerateValue_Name(), ecorePackage.getEString(), "name", null, 1, 1, EnumerateValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(numericValueEClass, NumericValue.class, "NumericValue", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(intValueEClass, IntValue.class, "IntValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntValue_Value(), ecorePackage.getEInt(), "value", null, 1, 1, IntValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(floatValueEClass, FloatValue.class, "FloatValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFloatValue_Value(), ecorePackage.getEFloat(), "value", null, 1, 1, FloatValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(doubleValueEClass, DoubleValue.class, "DoubleValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDoubleValue_Value(), ecorePackage.getEDouble(), "value", null, 1, 1, DoubleValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(negativeInfEClass, NegativeInf.class, "NegativeInf", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(positiveInfEClass, PositiveInf.class, "PositiveInf", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(valueToIncreaseEClass, ValueToIncrease.class, "ValueToIncrease", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getValueToIncrease_Value(), this.getNumericValue(), null, "value", null, 1, 1, ValueToIncrease.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringValueEClass, StringValue.class, "StringValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringValue_Value(), ecorePackage.getEString(), "value", null, 1, 1, StringValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(valueTypeEClass, ValueType.class, "ValueType", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(booleanValueTypeEClass, BooleanValueType.class, "BooleanValueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanValueType_PrimitiveType(), this.getTypeEnum(), "primitiveType", null, 1, 1, BooleanValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(enumerationEClass, Enumeration.class, "Enumeration", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getEnumeration_Values(), this.getEnumerateValue(), null, "values", null, 1, -1, Enumeration.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getEnumeration__IncludesName__String(), ecorePackage.getEBoolean(), "includesName", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEString(), "name", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getEnumeration__IncludesValue__int(), ecorePackage.getEBoolean(), "includesValue", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEInt(), "value", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(listEClass, List.class, "List", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getList_Values(), this.getValue(), null, "values", null, 1, -1, List.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getList_PrimitiveType(), this.getTypeEnum(), "primitiveType", null, 0, 1, List.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getList_Type(), this.getValueType(), null, "type", null, 0, 1, List.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_COMPOSITE, IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getList__IncludesValue__Value(), ecorePackage.getEBoolean(), "includesValue", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getValue(), "v", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getList__CheckValueType__Value(), ecorePackage.getEBoolean(), "checkValueType", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getValue(), "p", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(rangeEClass, Range.class, "Range", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRange_LowerLimit(), this.getLimit(), null, "lowerLimit", null, 1, 1, Range.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getRange_UpperLimit(), this.getLimit(), null, "upperLimit", null, 1, 1, Range.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRange_PrimitiveType(), this.getTypeEnum(), "primitiveType", null, 1, 1, Range.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getRange__CheckType__Limit_TypeEnum_boolean(), ecorePackage.getEBoolean(), "checkType", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getLimit(), "l", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getTypeEnum(), "type", 1, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEBoolean(), "lower", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRange__IncludesValue__double(), ecorePackage.getEBoolean(), "includesValue", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "n", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(rangeUnionEClass, RangeUnion.class, "RangeUnion", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getRangeUnion_Ranges(), this.getRange(), null, "ranges", null, 1, -1, RangeUnion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRangeUnion_PrimitiveType(), this.getTypeEnum(), "primitiveType", null, 1, 1, RangeUnion.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		op = initEOperation(getRangeUnion__IncludesValue__double(), ecorePackage.getEBoolean(), "includesValue", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, ecorePackage.getEDouble(), "n", 1, 1, IS_UNIQUE, IS_ORDERED);

		op = initEOperation(getRangeUnion__InvalidRangeSequence__RangeUnion(), ecorePackage.getEBoolean(), "invalidRangeSequence", 0, 1, IS_UNIQUE, IS_ORDERED);
		addEParameter(op, this.getRangeUnion(), "ru", 1, 1, IS_UNIQUE, IS_ORDERED);

		initEClass(stringValueTypeEClass, StringValueType.class, "StringValueType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringValueType_PrimitiveType(), this.getTypeEnum(), "primitiveType", null, 1, 1, StringValueType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(typeEnumEEnum, TypeEnum.class, "TypeEnum");
		addEEnumLiteral(typeEnumEEnum, TypeEnum.INT_TYPE);
		addEEnumLiteral(typeEnumEEnum, TypeEnum.STRING_TYPE);
		addEEnumLiteral(typeEnumEEnum, TypeEnum.BOOLEAN_TYPE);
		addEEnumLiteral(typeEnumEEnum, TypeEnum.FLOAT_TYPE);
		addEEnumLiteral(typeEnumEEnum, TypeEnum.DOUBLE_TYPE);

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
		  (enumerationEClass, 
		   source, 
		   new String[] {
			 "constraints", "Enumeration_All_Values_Diff"
		   });					
		addAnnotation
		  (listEClass, 
		   source, 
		   new String[] {
			 "constraints", "list_must_have_type all_list_values_correct_type"
		   });					
		addAnnotation
		  (rangeEClass, 
		   source, 
		   new String[] {
			 "constraints", "correct_range_type enforce_correct_range_type Range_low_less_than_upper Range_Infs_At_Appropriate_Pos"
		   });					
		addAnnotation
		  (rangeUnionEClass, 
		   source, 
		   new String[] {
			 "constraints", "Same_Primitive_Types CorrectRangeUnionSequence"
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
		  (getValue__ValueEquals__Value(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\tif (self.oclIsTypeOf(IntValue) and v.oclIsTypeOf(IntValue)) then self.oclAsType(IntValue).value = v.oclAsType(IntValue).value\n\t\t\t\t\t\telse\n\t\t\t\t\t\t\tif (self.oclIsTypeOf(FloatValue) and v.oclIsTypeOf(FloatValue)) then self.oclAsType(FloatValue).value = v.oclAsType(FloatValue).value\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(DoubleValue) and v.oclIsTypeOf(DoubleValue)) then self.oclAsType(DoubleValue).value = v.oclAsType(DoubleValue).value\n\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(StringValue) and v.oclIsTypeOf(StringValue)) then self.oclAsType(StringValue).value = v.oclAsType(StringValue).value\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (self.oclIsTypeOf(BooleanValue) and v.oclIsTypeOf(BooleanValue)) then self.oclAsType(BooleanValue).value = v.oclAsType(BooleanValue).value\n\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\tendif"
		   });		
		addAnnotation
		  (getBooleanValueType_PrimitiveType(), 
		   source, 
		   new String[] {
			 "derivation", "TypeEnum::BooleanType"
		   });			
		addAnnotation
		  (enumerationEClass, 
		   source, 
		   new String[] {
			 "Enumeration_All_Values_Diff", "\n\t\t\tvalues->forAll(p1, p2 | p1 <> p2 implies (p1.name <> p2.name and p1.value <> p2.value))"
		   });		
		addAnnotation
		  (getEnumeration__IncludesName__String(), 
		   source, 
		   new String[] {
			 "body", "self.values->exists(p | p.name = name)"
		   });		
		addAnnotation
		  (getEnumeration__IncludesValue__int(), 
		   source, 
		   new String[] {
			 "body", "self.values->exists(p | p.value = value)"
		   });			
		addAnnotation
		  (listEClass, 
		   source, 
		   new String[] {
			 "list_must_have_type", "\n\t\t\t\t\t\t\t\t(primitiveType <> null and type = null) or (type <> null and primitiveType = null)",
			 "all_list_values_correct_type", "\n\t\t\t\t\t\tvalues->forAll(p | \n\t\t\t\t\t\t\tself.checkValueType(p)\n\t\t\t\t\t\t)"
		   });		
		addAnnotation
		  (getList__IncludesValue__Value(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\tif (type <> null) then\n\t\t\t\t\t\t\tif (type.oclIsTypeOf(Range)) then\n\t\t\t\t\t\t\t\tif (type.oclAsType(Range).primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(IntValue).value = v.oclAsType(IntValue).value)\n\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\tif (type.oclAsType(Range).primitiveType = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(FloatValue).value = v.oclAsType(FloatValue).value)\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(DoubleValue).value = v.oclAsType(DoubleValue).value)\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(RangeUnion)) then\n\t\t\t\t\t\t\t\t\tif (type.oclAsType(RangeUnion).primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(IntValue).value = v.oclAsType(IntValue).value)\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (type.oclAsType(RangeUnion).primitiveType = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(FloatValue).value = v.oclAsType(FloatValue).value)\n\t\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(DoubleValue).value = v.oclAsType(DoubleValue).value)\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(StringValueType)) then \n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(StringValue).value = v.oclAsType(StringValue).value)\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(BooleanValueType)) then\n\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(BooleanValue).value = v.oclAsType(BooleanValue).value)\n\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\telse\n\t\t\t\t\t\t\tif (primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(IntValue).value = v.oclAsType(IntValue).value)\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(FloatValue).value = v.oclAsType(FloatValue).value)\n\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::DoubleType) then \n\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(DoubleValue).value = v.oclAsType(DoubleValue).value)\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::StringType) then\n\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(StringValue).value = v.oclAsType(StringValue).value)\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::BooleanType) then\n\t\t\t\t\t\t\t\t\t\t\t\tvalues->exists(p | p.oclAsType(BooleanValue).value = v.oclAsType(BooleanValue).value)\n\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\tendif"
		   });		
		addAnnotation
		  (getList__CheckValueType__Value(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\tif (type <> null) then \n\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(Range)) then \n\t\t\t\t\t\t\t\t\tif (type.oclAsType(Range).primitiveType = TypeEnum::IntType) then p.oclIsTypeOf(IntValue) and type.oclAsType(Range).includesValue(p.oclAsType(IntValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (type.oclAsType(Range).primitiveType = TypeEnum::FloatType) then p.oclIsTypeOf(FloatValue) and type.oclAsType(Range).includesValue(p.oclAsType(FloatValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\telse  p.oclIsTypeOf(DoubleValue) and type.oclAsType(Range).includesValue(p.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(RangeUnion)) then \n\t\t\t\t\t\t\t\t\t\tif (type.oclAsType(RangeUnion).primitiveType = TypeEnum::IntType) then p.oclIsTypeOf(IntValue) and type.oclAsType(RangeUnion).includesValue(p.oclAsType(IntValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (type.oclAsType(RangeUnion).primitiveType = TypeEnum::FloatType) then p.oclIsTypeOf(FloatValue) and type.oclAsType(RangeUnion).includesValue(p.oclAsType(FloatValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\t\telse p.oclIsTypeOf(DoubleValue) and type.oclAsType(RangeUnion).includesValue(p.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble))\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(StringValueType)) then\n\t\t\t\t\t\t\t\t\t\t\tp.oclIsTypeOf(StringValue)\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (type.oclIsTypeOf(BooleanValueType)) then\n\t\t\t\t\t\t\t\t\t\t\t\tp.oclIsTypeOf(BooleanValue)\n\t\t\t\t\t\t\t\t\t\t\telse false\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::IntType) then p.oclIsTypeOf(IntValue)\n\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::StringType) then p.oclIsTypeOf(StringValue)\n\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::BooleanType) then p.oclIsTypeOf(BooleanValue)\n\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::FloatType) then p.oclIsTypeOf(FloatValue) \n\t\t\t\t\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\t\t\t\t\tif (primitiveType = TypeEnum::DoubleType) then p.oclIsTypeOf(DoubleValue) \n\t\t\t\t\t\t\t\t\t\t\t\telse true \n\t\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (rangeEClass, 
		   source, 
		   new String[] {
			 "correct_range_type", "\n\t\t\t\t\t\t(primitiveType = TypeEnum::IntType) or (primitiveType = TypeEnum::FloatType) or (primitiveType = TypeEnum::DoubleType)",
			 "enforce_correct_range_type", "\n\t\t\t\t\t\tself.checkType(self.lowerLimit, primitiveType, true) and self.checkType(self.upperLimit, primitiveType, false)",
			 "Range_low_less_than_upper", "\n\t\t\t\t\tif (not(lowerLimit.value.oclIsTypeOf(NegativeInf)) and not(upperLimit.value.oclIsTypeOf(PositiveInf))) then\n\t\t\t\t\t\tif (primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\tif (not(upperLimit.included) and not(lowerLimit.included)) then (upperLimit.value.oclAsType(IntValue).value - lowerLimit.value.oclAsType(IntValue).value.oclAsType(ecore::EInt)) >= 2 \n\t\t\t\t\t\t\telse if (upperLimit.included) then lowerLimit.value.oclAsType(IntValue).value.oclAsType(ecore::EInt) <= upperLimit.value.oclAsType(IntValue).value\n\t\t\t\t\t\t\t\t else lowerLimit.value.oclAsType(IntValue).value.oclAsType(ecore::EInt) < upperLimit.value.oclAsType(IntValue).value\n\t\t\t\t\t\t\t\t endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\telse (\n\t\t\t\t\t\t\tif (primitiveType = TypeEnum::FloatType) then \n\t\t\t\t\t\t\t\tif (upperLimit.included) then lowerLimit.value.oclAsType(FloatValue).value.oclAsType(ecore::EFloat) <= upperLimit.value.oclAsType(FloatValue).value\n\t\t\t\t\t\t\t\telse lowerLimit.value.oclAsType(FloatValue).value.oclAsType(ecore::EFloat) < upperLimit.value.oclAsType(FloatValue).value\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (upperLimit.included) then lowerLimit.value.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble) <= upperLimit.value.oclAsType(DoubleValue).value\n\t\t\t\t\t\t\t\telse lowerLimit.value.oclAsType(DoubleValue).value.oclAsType(ecore::EDouble) < upperLimit.value.oclAsType(DoubleValue).value\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t) \t\n\t\t\t\t\t\tendif\n\t\t\t\t\telse true\n\t\t\t\t\tendif",
			 "Range_Infs_At_Appropriate_Pos", "\n\t\t\t\t\tnot(lowerLimit.value.oclIsTypeOf(PositiveInf) or upperLimit.value.oclIsTypeOf(NegativeInf))"
		   });		
		addAnnotation
		  (getRange__CheckType__Limit_TypeEnum_boolean(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\tif (type = TypeEnum::IntType) then\n\t\t\t\t\t\t\tif (lower and not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then lowerLimit.value.oclIsTypeOf(IntValue)\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (not(lower) and not(upperLimit.value.oclIsTypeOf(PositiveInf))) then upperLimit.value.oclIsTypeOf(IntValue) else true endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\telse\n\t\t\t\t\t\t\tif (type = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\tif (lower and not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then lowerLimit.value.oclIsTypeOf(FloatValue)\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (not(lower) and not(upperLimit.value.oclIsTypeOf(PositiveInf))) then upperLimit.value.oclIsTypeOf(FloatValue) else true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\tif (lower and not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then lowerLimit.value.oclIsTypeOf(DoubleValue)\n\t\t\t\t\t\t\t\telse \n\t\t\t\t\t\t\t\t\tif (not(lower) and not(upperLimit.value.oclIsTypeOf(PositiveInf))) then upperLimit.value.oclIsTypeOf(DoubleValue) else true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\tendif"
		   });		
		addAnnotation
		  (getRange__IncludesValue__double(), 
		   source, 
		   new String[] {
			 "body", "\n\t\t\t\t\t\tif (primitiveType = TypeEnum::IntType) then\n\t\t\t\t\t\t\tif (not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then ((lowerLimit.included and lowerLimit.value.oclAsType(IntValue).value <= n) or (not(lowerLimit.included) and lowerLimit.value.oclAsType(IntValue).value < n)) and \n\t\t\t\t\t\t\t\tif (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(IntValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(IntValue).value > n)\n\t\t\t\t\t\t\t\telse true endif \n\t\t\t\t\t\t\telse if (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(IntValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(IntValue).value > n)\n\t\t\t\t\t\t\t\t else true endif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\telse\n\t\t\t\t\t\t\tif (primitiveType = TypeEnum::FloatType) then\n\t\t\t\t\t\t\t\tif (not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then ((lowerLimit.included and lowerLimit.value.oclAsType(FloatValue).value <= n) or (not(lowerLimit.included) and lowerLimit.value.oclAsType(FloatValue).value < n)) and \n\t\t\t\t\t\t\t\t\tif (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(FloatValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(FloatValue).value > n)\n\t\t\t\t\t\t\t\t\telse true endif \n\t\t\t\t\t\t\t\telse if (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(FloatValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(FloatValue).value > n)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\telse\n\t\t\t\t\t\t\t\tif (not(lowerLimit.value.oclIsTypeOf(NegativeInf))) then ((lowerLimit.included and lowerLimit.value.oclAsType(DoubleValue).value <= n) or (not(lowerLimit.included) and lowerLimit.value.oclAsType(DoubleValue).value < n)) and \n\t\t\t\t\t\t\t\t\tif (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(DoubleValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(DoubleValue).value > n)\n\t\t\t\t\t\t\t\t\telse true endif \n\t\t\t\t\t\t\t\telse if (not(upperLimit.value.oclIsTypeOf(PositiveInf))) then (upperLimit.included and upperLimit.value.oclAsType(DoubleValue).value >= n) or (not(upperLimit.included) and upperLimit.value.oclAsType(DoubleValue).value > n)\n\t\t\t\t\t\t\t\t\telse true endif\n\t\t\t\t\t\t\t\tendif\n\t\t\t\t\t\t\tendif\n\t\t\t\t\t\tendif"
		   });			
		addAnnotation
		  (rangeUnionEClass, 
		   source, 
		   new String[] {
			 "Same_Primitive_Types", "\n\t\t\t\t\t\tself.ranges->forAll(p | p.primitiveType = self.primitiveType)",
			 "CorrectRangeUnionSequence", "\n\t\t\t\t\t\tnot(invalidRangeSequence(self))"
		   });		
		addAnnotation
		  (getRangeUnion__IncludesValue__double(), 
		   source, 
		   new String[] {
			 "body", "ranges->exists(p | p.includesValue(n))"
		   });			
		addAnnotation
		  (getStringValueType_PrimitiveType(), 
		   source, 
		   new String[] {
			 "derivation", "TypeEnum::StringType"
		   });
	}

} //TypePackageImpl
