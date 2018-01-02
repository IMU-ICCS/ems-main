/**
 */
package eu.paasage.upperware.metamodel.types;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;

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
 * @see eu.paasage.upperware.metamodel.types.TypesFactory
 * @model kind="package"
 * @generated
 */
public interface TypesPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "types";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/eu/paasage/upperware/metamodel/types";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "types";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypesPackage eINSTANCE = eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl.init();

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.impl.ValueUpperwareImpl <em>Value Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.impl.ValueUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getValueUpperware()
	 * @generated
	 */
	int VALUE_UPPERWARE = 0;

	/**
	 * The number of structural features of the '<em>Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_UPPERWARE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_UPPERWARE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.impl.NumericValueUpperwareImpl <em>Numeric Value Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.impl.NumericValueUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getNumericValueUpperware()
	 * @generated
	 */
	int NUMERIC_VALUE_UPPERWARE = 1;

	/**
	 * The number of structural features of the '<em>Numeric Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_UPPERWARE_FEATURE_COUNT = VALUE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Numeric Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_UPPERWARE_OPERATION_COUNT = VALUE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.impl.IntegerValueUpperwareImpl <em>Integer Value Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.impl.IntegerValueUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getIntegerValueUpperware()
	 * @generated
	 */
	int INTEGER_VALUE_UPPERWARE = 2;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE_UPPERWARE__VALUE = NUMERIC_VALUE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Integer Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE_UPPERWARE_FEATURE_COUNT = NUMERIC_VALUE_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Integer Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INTEGER_VALUE_UPPERWARE_OPERATION_COUNT = NUMERIC_VALUE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.impl.LongValueUpperwareImpl <em>Long Value Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.impl.LongValueUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getLongValueUpperware()
	 * @generated
	 */
	int LONG_VALUE_UPPERWARE = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LONG_VALUE_UPPERWARE__VALUE = NUMERIC_VALUE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Long Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LONG_VALUE_UPPERWARE_FEATURE_COUNT = NUMERIC_VALUE_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Long Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LONG_VALUE_UPPERWARE_OPERATION_COUNT = NUMERIC_VALUE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.impl.FloatValueUpperwareImpl <em>Float Value Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.impl.FloatValueUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getFloatValueUpperware()
	 * @generated
	 */
	int FLOAT_VALUE_UPPERWARE = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_VALUE_UPPERWARE__VALUE = NUMERIC_VALUE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Float Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_VALUE_UPPERWARE_FEATURE_COUNT = NUMERIC_VALUE_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Float Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_VALUE_UPPERWARE_OPERATION_COUNT = NUMERIC_VALUE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.impl.DoubleValueUpperwareImpl <em>Double Value Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.impl.DoubleValueUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getDoubleValueUpperware()
	 * @generated
	 */
	int DOUBLE_VALUE_UPPERWARE = 5;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE_UPPERWARE__VALUE = NUMERIC_VALUE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE_UPPERWARE_FEATURE_COUNT = NUMERIC_VALUE_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Double Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE_UPPERWARE_OPERATION_COUNT = NUMERIC_VALUE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.impl.StringValueUpperwareImpl <em>String Value Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.impl.StringValueUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getStringValueUpperware()
	 * @generated
	 */
	int STRING_VALUE_UPPERWARE = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_UPPERWARE__VALUE = VALUE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_UPPERWARE_FEATURE_COUNT = VALUE_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_UPPERWARE_OPERATION_COUNT = VALUE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.impl.BooleanValueUpperwareImpl <em>Boolean Value Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.impl.BooleanValueUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getBooleanValueUpperware()
	 * @generated
	 */
	int BOOLEAN_VALUE_UPPERWARE = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_UPPERWARE__VALUE = VALUE_UPPERWARE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_UPPERWARE_FEATURE_COUNT = VALUE_UPPERWARE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Boolean Value Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_UPPERWARE_OPERATION_COUNT = VALUE_UPPERWARE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.types.BasicTypeEnum <em>Basic Type Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.types.BasicTypeEnum
	 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getBasicTypeEnum()
	 * @generated
	 */
	int BASIC_TYPE_ENUM = 8;


	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.ValueUpperware <em>Value Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.ValueUpperware
	 * @generated
	 */
	EClass getValueUpperware();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.NumericValueUpperware <em>Numeric Value Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Value Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.NumericValueUpperware
	 * @generated
	 */
	EClass getNumericValueUpperware();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.IntegerValueUpperware <em>Integer Value Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Integer Value Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.IntegerValueUpperware
	 * @generated
	 */
	EClass getIntegerValueUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.IntegerValueUpperware#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.types.IntegerValueUpperware#getValue()
	 * @see #getIntegerValueUpperware()
	 * @generated
	 */
	EAttribute getIntegerValueUpperware_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.LongValueUpperware <em>Long Value Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Long Value Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.LongValueUpperware
	 * @generated
	 */
	EClass getLongValueUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.LongValueUpperware#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.types.LongValueUpperware#getValue()
	 * @see #getLongValueUpperware()
	 * @generated
	 */
	EAttribute getLongValueUpperware_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.FloatValueUpperware <em>Float Value Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Float Value Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.FloatValueUpperware
	 * @generated
	 */
	EClass getFloatValueUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.FloatValueUpperware#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.types.FloatValueUpperware#getValue()
	 * @see #getFloatValueUpperware()
	 * @generated
	 */
	EAttribute getFloatValueUpperware_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.DoubleValueUpperware <em>Double Value Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double Value Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.DoubleValueUpperware
	 * @generated
	 */
	EClass getDoubleValueUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.DoubleValueUpperware#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.types.DoubleValueUpperware#getValue()
	 * @see #getDoubleValueUpperware()
	 * @generated
	 */
	EAttribute getDoubleValueUpperware_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.StringValueUpperware <em>String Value Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Value Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.StringValueUpperware
	 * @generated
	 */
	EClass getStringValueUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.StringValueUpperware#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.types.StringValueUpperware#getValue()
	 * @see #getStringValueUpperware()
	 * @generated
	 */
	EAttribute getStringValueUpperware_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.types.BooleanValueUpperware <em>Boolean Value Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Value Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.types.BooleanValueUpperware
	 * @generated
	 */
	EClass getBooleanValueUpperware();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.types.BooleanValueUpperware#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.types.BooleanValueUpperware#isValue()
	 * @see #getBooleanValueUpperware()
	 * @generated
	 */
	EAttribute getBooleanValueUpperware_Value();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.types.BasicTypeEnum <em>Basic Type Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Basic Type Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.types.BasicTypeEnum
	 * @generated
	 */
	EEnum getBasicTypeEnum();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TypesFactory getTypesFactory();

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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.impl.ValueUpperwareImpl <em>Value Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.impl.ValueUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getValueUpperware()
		 * @generated
		 */
		EClass VALUE_UPPERWARE = eINSTANCE.getValueUpperware();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.impl.NumericValueUpperwareImpl <em>Numeric Value Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.impl.NumericValueUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getNumericValueUpperware()
		 * @generated
		 */
		EClass NUMERIC_VALUE_UPPERWARE = eINSTANCE.getNumericValueUpperware();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.impl.IntegerValueUpperwareImpl <em>Integer Value Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.impl.IntegerValueUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getIntegerValueUpperware()
		 * @generated
		 */
		EClass INTEGER_VALUE_UPPERWARE = eINSTANCE.getIntegerValueUpperware();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INTEGER_VALUE_UPPERWARE__VALUE = eINSTANCE.getIntegerValueUpperware_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.impl.LongValueUpperwareImpl <em>Long Value Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.impl.LongValueUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getLongValueUpperware()
		 * @generated
		 */
		EClass LONG_VALUE_UPPERWARE = eINSTANCE.getLongValueUpperware();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LONG_VALUE_UPPERWARE__VALUE = eINSTANCE.getLongValueUpperware_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.impl.FloatValueUpperwareImpl <em>Float Value Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.impl.FloatValueUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getFloatValueUpperware()
		 * @generated
		 */
		EClass FLOAT_VALUE_UPPERWARE = eINSTANCE.getFloatValueUpperware();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOAT_VALUE_UPPERWARE__VALUE = eINSTANCE.getFloatValueUpperware_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.impl.DoubleValueUpperwareImpl <em>Double Value Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.impl.DoubleValueUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getDoubleValueUpperware()
		 * @generated
		 */
		EClass DOUBLE_VALUE_UPPERWARE = eINSTANCE.getDoubleValueUpperware();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOUBLE_VALUE_UPPERWARE__VALUE = eINSTANCE.getDoubleValueUpperware_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.impl.StringValueUpperwareImpl <em>String Value Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.impl.StringValueUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getStringValueUpperware()
		 * @generated
		 */
		EClass STRING_VALUE_UPPERWARE = eINSTANCE.getStringValueUpperware();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_VALUE_UPPERWARE__VALUE = eINSTANCE.getStringValueUpperware_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.impl.BooleanValueUpperwareImpl <em>Boolean Value Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.impl.BooleanValueUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getBooleanValueUpperware()
		 * @generated
		 */
		EClass BOOLEAN_VALUE_UPPERWARE = eINSTANCE.getBooleanValueUpperware();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_VALUE_UPPERWARE__VALUE = eINSTANCE.getBooleanValueUpperware_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.types.BasicTypeEnum <em>Basic Type Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.types.BasicTypeEnum
		 * @see eu.paasage.upperware.metamodel.types.impl.TypesPackageImpl#getBasicTypeEnum()
		 * @generated
		 */
		EEnum BASIC_TYPE_ENUM = eINSTANCE.getBasicTypeEnum();

	}

} //TypesPackage
