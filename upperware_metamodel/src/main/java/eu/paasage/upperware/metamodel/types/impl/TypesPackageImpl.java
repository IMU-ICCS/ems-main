/**
 */
package eu.paasage.upperware.metamodel.types.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;

import eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl;

import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.BooleanValueUpperware;
import eu.paasage.upperware.metamodel.types.DoubleValueUpperware;
import eu.paasage.upperware.metamodel.types.FloatValueUpperware;
import eu.paasage.upperware.metamodel.types.IntegerValueUpperware;
import eu.paasage.upperware.metamodel.types.LongValueUpperware;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;
import eu.paasage.upperware.metamodel.types.StringValueUpperware;
import eu.paasage.upperware.metamodel.types.TypesFactory;
import eu.paasage.upperware.metamodel.types.TypesPackage;
import eu.paasage.upperware.metamodel.types.ValueUpperware;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

import org.eclipse.emf.ecore.impl.EPackageImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class TypesPackageImpl extends EPackageImpl implements TypesPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass valueUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numericValueUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass integerValueUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass longValueUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass floatValueUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass doubleValueUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringValueUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanValueUpperwareEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum basicTypeEnumEEnum = null;

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
	 * @see eu.paasage.upperware.metamodel.types.TypesPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private TypesPackageImpl() {
		super(eNS_URI, TypesFactory.eINSTANCE);
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
	 * <p>This method is used to initialize {@link TypesPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static TypesPackage init() {
		if (isInited) return (TypesPackage)EPackage.Registry.INSTANCE.getEPackage(TypesPackage.eNS_URI);

		// Obtain or create and register package
		TypesPackageImpl theTypesPackage = (TypesPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof TypesPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new TypesPackageImpl());

		isInited = true;

		// Obtain or create and register interdependencies
		CpPackageImpl theCpPackage = (CpPackageImpl)(EPackage.Registry.INSTANCE.getEPackage(CpPackage.eNS_URI) instanceof CpPackageImpl ? EPackage.Registry.INSTANCE.getEPackage(CpPackage.eNS_URI) : CpPackage.eINSTANCE);

		// Create package meta-data objects
		theTypesPackage.createPackageContents();
		theCpPackage.createPackageContents();

		// Initialize created meta-data
		theTypesPackage.initializePackageContents();
		theCpPackage.initializePackageContents();

		// Mark meta-data to indicate it can't be changed
		theTypesPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(TypesPackage.eNS_URI, theTypesPackage);
		return theTypesPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getValueUpperware() {
		return valueUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumericValueUpperware() {
		return numericValueUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getIntegerValueUpperware() {
		return integerValueUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getIntegerValueUpperware_Value() {
		return (EAttribute)integerValueUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLongValueUpperware() {
		return longValueUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLongValueUpperware_Value() {
		return (EAttribute)longValueUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFloatValueUpperware() {
		return floatValueUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFloatValueUpperware_Value() {
		return (EAttribute)floatValueUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDoubleValueUpperware() {
		return doubleValueUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDoubleValueUpperware_Value() {
		return (EAttribute)doubleValueUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringValueUpperware() {
		return stringValueUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringValueUpperware_Value() {
		return (EAttribute)stringValueUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanValueUpperware() {
		return booleanValueUpperwareEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanValueUpperware_Value() {
		return (EAttribute)booleanValueUpperwareEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getBasicTypeEnum() {
		return basicTypeEnumEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TypesFactory getTypesFactory() {
		return (TypesFactory)getEFactoryInstance();
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
		valueUpperwareEClass = createEClass(VALUE_UPPERWARE);

		numericValueUpperwareEClass = createEClass(NUMERIC_VALUE_UPPERWARE);

		integerValueUpperwareEClass = createEClass(INTEGER_VALUE_UPPERWARE);
		createEAttribute(integerValueUpperwareEClass, INTEGER_VALUE_UPPERWARE__VALUE);

		longValueUpperwareEClass = createEClass(LONG_VALUE_UPPERWARE);
		createEAttribute(longValueUpperwareEClass, LONG_VALUE_UPPERWARE__VALUE);

		floatValueUpperwareEClass = createEClass(FLOAT_VALUE_UPPERWARE);
		createEAttribute(floatValueUpperwareEClass, FLOAT_VALUE_UPPERWARE__VALUE);

		doubleValueUpperwareEClass = createEClass(DOUBLE_VALUE_UPPERWARE);
		createEAttribute(doubleValueUpperwareEClass, DOUBLE_VALUE_UPPERWARE__VALUE);

		stringValueUpperwareEClass = createEClass(STRING_VALUE_UPPERWARE);
		createEAttribute(stringValueUpperwareEClass, STRING_VALUE_UPPERWARE__VALUE);

		booleanValueUpperwareEClass = createEClass(BOOLEAN_VALUE_UPPERWARE);
		createEAttribute(booleanValueUpperwareEClass, BOOLEAN_VALUE_UPPERWARE__VALUE);

		// Create enums
		basicTypeEnumEEnum = createEEnum(BASIC_TYPE_ENUM);
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
		numericValueUpperwareEClass.getESuperTypes().add(this.getValueUpperware());
		integerValueUpperwareEClass.getESuperTypes().add(this.getNumericValueUpperware());
		longValueUpperwareEClass.getESuperTypes().add(this.getNumericValueUpperware());
		floatValueUpperwareEClass.getESuperTypes().add(this.getNumericValueUpperware());
		doubleValueUpperwareEClass.getESuperTypes().add(this.getNumericValueUpperware());
		stringValueUpperwareEClass.getESuperTypes().add(this.getValueUpperware());
		booleanValueUpperwareEClass.getESuperTypes().add(this.getValueUpperware());

		// Initialize classes, features, and operations; add parameters
		initEClass(valueUpperwareEClass, ValueUpperware.class, "ValueUpperware", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(numericValueUpperwareEClass, NumericValueUpperware.class, "NumericValueUpperware", IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(integerValueUpperwareEClass, IntegerValueUpperware.class, "IntegerValueUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getIntegerValueUpperware_Value(), ecorePackage.getEInt(), "value", null, 1, 1, IntegerValueUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(longValueUpperwareEClass, LongValueUpperware.class, "LongValueUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLongValueUpperware_Value(), ecorePackage.getELong(), "value", null, 1, 1, LongValueUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(floatValueUpperwareEClass, FloatValueUpperware.class, "FloatValueUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFloatValueUpperware_Value(), ecorePackage.getEFloat(), "value", null, 1, 1, FloatValueUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(doubleValueUpperwareEClass, DoubleValueUpperware.class, "DoubleValueUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDoubleValueUpperware_Value(), ecorePackage.getEDouble(), "value", null, 1, 1, DoubleValueUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringValueUpperwareEClass, StringValueUpperware.class, "StringValueUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringValueUpperware_Value(), ecorePackage.getEString(), "value", null, 1, 1, StringValueUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanValueUpperwareEClass, BooleanValueUpperware.class, "BooleanValueUpperware", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanValueUpperware_Value(), ecorePackage.getEBoolean(), "value", null, 1, 1, BooleanValueUpperware.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(basicTypeEnumEEnum, BasicTypeEnum.class, "BasicTypeEnum");
		addEEnumLiteral(basicTypeEnumEEnum, BasicTypeEnum.INTEGER);
		addEEnumLiteral(basicTypeEnumEEnum, BasicTypeEnum.FLOAT);
		addEEnumLiteral(basicTypeEnumEEnum, BasicTypeEnum.DOUBLE);
		addEEnumLiteral(basicTypeEnumEEnum, BasicTypeEnum.LONG);

		// Create resource
		createResource(eNS_URI);
	}

} //TypesPackageImpl
