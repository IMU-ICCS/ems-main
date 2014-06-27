/**
 */
package camel.type;

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
 * @see camel.type.TypeFactory
 * @model kind="package"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore invocationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' settingDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot' validationDelegates='http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot'"
 * @generated
 */
public interface TypePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "type";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.paasage.eu/camel/type";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "type";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	TypePackage eINSTANCE = camel.type.impl.TypePackageImpl.init();

	/**
	 * The meta object id for the '{@link camel.type.impl.TypeRepositoryImpl <em>Repository</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.TypeRepositoryImpl
	 * @see camel.type.impl.TypePackageImpl#getTypeRepository()
	 * @generated
	 */
	int TYPE_REPOSITORY = 0;

	/**
	 * The feature id for the '<em><b>Data Types</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REPOSITORY__DATA_TYPES = 0;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REPOSITORY__VALUES = 1;

	/**
	 * The number of structural features of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REPOSITORY_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Repository</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TYPE_REPOSITORY_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.LimitImpl <em>Limit</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.LimitImpl
	 * @see camel.type.impl.TypePackageImpl#getLimit()
	 * @generated
	 */
	int LIMIT = 1;

	/**
	 * The feature id for the '<em><b>Included</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIMIT__INCLUDED = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIMIT__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Limit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIMIT_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Limit</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIMIT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.ValueImpl <em>Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.ValueImpl
	 * @see camel.type.impl.TypePackageImpl#getValue()
	 * @generated
	 */
	int VALUE = 2;

	/**
	 * The number of structural features of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_FEATURE_COUNT = 0;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE___VALUE_EQUALS__VALUE = 0;

	/**
	 * The number of operations of the '<em>Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_OPERATION_COUNT = 1;

	/**
	 * The meta object id for the '{@link camel.type.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.BooleanValueImpl
	 * @see camel.type.impl.TypePackageImpl#getBooleanValue()
	 * @generated
	 */
	int BOOLEAN_VALUE = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE___VALUE_EQUALS__VALUE = VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>Boolean Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.EnumerateValueImpl <em>Enumerate Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.EnumerateValueImpl
	 * @see camel.type.impl.TypePackageImpl#getEnumerateValue()
	 * @generated
	 */
	int ENUMERATE_VALUE = 4;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATE_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATE_VALUE__NAME = VALUE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Enumerate Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATE_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATE_VALUE___VALUE_EQUALS__VALUE = VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>Enumerate Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATE_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.NumericValueImpl <em>Numeric Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.NumericValueImpl
	 * @see camel.type.impl.TypePackageImpl#getNumericValue()
	 * @generated
	 */
	int NUMERIC_VALUE = 5;

	/**
	 * The number of structural features of the '<em>Numeric Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE___VALUE_EQUALS__VALUE = VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>Numeric Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.IntValueImpl <em>Int Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.IntValueImpl
	 * @see camel.type.impl.TypePackageImpl#getIntValue()
	 * @generated
	 */
	int INT_VALUE = 6;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_VALUE__VALUE = NUMERIC_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Int Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_VALUE_FEATURE_COUNT = NUMERIC_VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_VALUE___VALUE_EQUALS__VALUE = NUMERIC_VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>Int Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int INT_VALUE_OPERATION_COUNT = NUMERIC_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.FloatValueImpl <em>Float Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.FloatValueImpl
	 * @see camel.type.impl.TypePackageImpl#getFloatValue()
	 * @generated
	 */
	int FLOAT_VALUE = 7;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_VALUE__VALUE = NUMERIC_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Float Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_VALUE_FEATURE_COUNT = NUMERIC_VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_VALUE___VALUE_EQUALS__VALUE = NUMERIC_VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>Float Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOAT_VALUE_OPERATION_COUNT = NUMERIC_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.DoubleValueImpl <em>Double Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.DoubleValueImpl
	 * @see camel.type.impl.TypePackageImpl#getDoubleValue()
	 * @generated
	 */
	int DOUBLE_VALUE = 8;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE__VALUE = NUMERIC_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Double Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE_FEATURE_COUNT = NUMERIC_VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE___VALUE_EQUALS__VALUE = NUMERIC_VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>Double Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOUBLE_VALUE_OPERATION_COUNT = NUMERIC_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.NegativeInfImpl <em>Negative Inf</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.NegativeInfImpl
	 * @see camel.type.impl.TypePackageImpl#getNegativeInf()
	 * @generated
	 */
	int NEGATIVE_INF = 9;

	/**
	 * The number of structural features of the '<em>Negative Inf</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_INF_FEATURE_COUNT = NUMERIC_VALUE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_INF___VALUE_EQUALS__VALUE = NUMERIC_VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>Negative Inf</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_INF_OPERATION_COUNT = NUMERIC_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.PositiveInfImpl <em>Positive Inf</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.PositiveInfImpl
	 * @see camel.type.impl.TypePackageImpl#getPositiveInf()
	 * @generated
	 */
	int POSITIVE_INF = 10;

	/**
	 * The number of structural features of the '<em>Positive Inf</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITIVE_INF_FEATURE_COUNT = NUMERIC_VALUE_FEATURE_COUNT + 0;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITIVE_INF___VALUE_EQUALS__VALUE = NUMERIC_VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>Positive Inf</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POSITIVE_INF_OPERATION_COUNT = NUMERIC_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.ValueToIncreaseImpl <em>Value To Increase</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.ValueToIncreaseImpl
	 * @see camel.type.impl.TypePackageImpl#getValueToIncrease()
	 * @generated
	 */
	int VALUE_TO_INCREASE = 11;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TO_INCREASE__VALUE = NUMERIC_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Value To Increase</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TO_INCREASE_FEATURE_COUNT = NUMERIC_VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TO_INCREASE___VALUE_EQUALS__VALUE = NUMERIC_VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>Value To Increase</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TO_INCREASE_OPERATION_COUNT = NUMERIC_VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.StringValueImpl <em>String Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.StringValueImpl
	 * @see camel.type.impl.TypePackageImpl#getStringValue()
	 * @generated
	 */
	int STRING_VALUE = 12;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE__VALUE = VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_FEATURE_COUNT = VALUE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Value Equals</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE___VALUE_EQUALS__VALUE = VALUE___VALUE_EQUALS__VALUE;

	/**
	 * The number of operations of the '<em>String Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_OPERATION_COUNT = VALUE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.ValueTypeImpl <em>Value Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.ValueTypeImpl
	 * @see camel.type.impl.TypePackageImpl#getValueType()
	 * @generated
	 */
	int VALUE_TYPE = 13;

	/**
	 * The number of structural features of the '<em>Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VALUE_TYPE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.BooleanValueTypeImpl <em>Boolean Value Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.BooleanValueTypeImpl
	 * @see camel.type.impl.TypePackageImpl#getBooleanValueType()
	 * @generated
	 */
	int BOOLEAN_VALUE_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Primitive Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_TYPE__PRIMITIVE_TYPE = VALUE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_TYPE_FEATURE_COUNT = VALUE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Boolean Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_VALUE_TYPE_OPERATION_COUNT = VALUE_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.impl.EnumerationImpl <em>Enumeration</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.EnumerationImpl
	 * @see camel.type.impl.TypePackageImpl#getEnumeration()
	 * @generated
	 */
	int ENUMERATION = 15;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION__VALUES = VALUE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Enumeration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_FEATURE_COUNT = VALUE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The operation id for the '<em>Includes Name</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION___INCLUDES_NAME__STRING = VALUE_TYPE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Includes Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION___INCLUDES_VALUE__INT = VALUE_TYPE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Enumeration</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUMERATION_OPERATION_COUNT = VALUE_TYPE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link camel.type.impl.ListImpl <em>List</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.ListImpl
	 * @see camel.type.impl.TypePackageImpl#getList()
	 * @generated
	 */
	int LIST = 16;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST__VALUES = VALUE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Primitive Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST__PRIMITIVE_TYPE = VALUE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Type</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST__TYPE = VALUE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_FEATURE_COUNT = VALUE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Includes Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST___INCLUDES_VALUE__VALUE = VALUE_TYPE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Check Value Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST___CHECK_VALUE_TYPE__VALUE = VALUE_TYPE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>List</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_OPERATION_COUNT = VALUE_TYPE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link camel.type.impl.RangeImpl <em>Range</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.RangeImpl
	 * @see camel.type.impl.TypePackageImpl#getRange()
	 * @generated
	 */
	int RANGE = 17;

	/**
	 * The feature id for the '<em><b>Lower Limit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__LOWER_LIMIT = VALUE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Upper Limit</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__UPPER_LIMIT = VALUE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Primitive Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE__PRIMITIVE_TYPE = VALUE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_FEATURE_COUNT = VALUE_TYPE_FEATURE_COUNT + 3;

	/**
	 * The operation id for the '<em>Check Type</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE___CHECK_TYPE__LIMIT_TYPEENUM_BOOLEAN = VALUE_TYPE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Includes Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE___INCLUDES_VALUE__DOUBLE = VALUE_TYPE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Range</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_OPERATION_COUNT = VALUE_TYPE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link camel.type.impl.RangeUnionImpl <em>Range Union</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.RangeUnionImpl
	 * @see camel.type.impl.TypePackageImpl#getRangeUnion()
	 * @generated
	 */
	int RANGE_UNION = 18;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_UNION__RANGES = VALUE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Primitive Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_UNION__PRIMITIVE_TYPE = VALUE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Range Union</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_UNION_FEATURE_COUNT = VALUE_TYPE_FEATURE_COUNT + 2;

	/**
	 * The operation id for the '<em>Includes Value</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_UNION___INCLUDES_VALUE__DOUBLE = VALUE_TYPE_OPERATION_COUNT + 0;

	/**
	 * The operation id for the '<em>Invalid Range Sequence</em>' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_UNION___INVALID_RANGE_SEQUENCE__RANGEUNION = VALUE_TYPE_OPERATION_COUNT + 1;

	/**
	 * The number of operations of the '<em>Range Union</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_UNION_OPERATION_COUNT = VALUE_TYPE_OPERATION_COUNT + 2;

	/**
	 * The meta object id for the '{@link camel.type.impl.StringValueTypeImpl <em>String Value Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.impl.StringValueTypeImpl
	 * @see camel.type.impl.TypePackageImpl#getStringValueType()
	 * @generated
	 */
	int STRING_VALUE_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Primitive Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_TYPE__PRIMITIVE_TYPE = VALUE_TYPE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_TYPE_FEATURE_COUNT = VALUE_TYPE_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>String Value Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_VALUE_TYPE_OPERATION_COUNT = VALUE_TYPE_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link camel.type.TypeEnum <em>Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see camel.type.TypeEnum
	 * @see camel.type.impl.TypePackageImpl#getTypeEnum()
	 * @generated
	 */
	int TYPE_ENUM = 20;


	/**
	 * Returns the meta object for class '{@link camel.type.TypeRepository <em>Repository</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Repository</em>'.
	 * @see camel.type.TypeRepository
	 * @generated
	 */
	EClass getTypeRepository();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.type.TypeRepository#getDataTypes <em>Data Types</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Data Types</em>'.
	 * @see camel.type.TypeRepository#getDataTypes()
	 * @see #getTypeRepository()
	 * @generated
	 */
	EReference getTypeRepository_DataTypes();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.type.TypeRepository#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see camel.type.TypeRepository#getValues()
	 * @see #getTypeRepository()
	 * @generated
	 */
	EReference getTypeRepository_Values();

	/**
	 * Returns the meta object for class '{@link camel.type.Limit <em>Limit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Limit</em>'.
	 * @see camel.type.Limit
	 * @generated
	 */
	EClass getLimit();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.Limit#isIncluded <em>Included</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Included</em>'.
	 * @see camel.type.Limit#isIncluded()
	 * @see #getLimit()
	 * @generated
	 */
	EAttribute getLimit_Included();

	/**
	 * Returns the meta object for the containment reference '{@link camel.type.Limit#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see camel.type.Limit#getValue()
	 * @see #getLimit()
	 * @generated
	 */
	EReference getLimit_Value();

	/**
	 * Returns the meta object for class '{@link camel.type.Value <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value</em>'.
	 * @see camel.type.Value
	 * @generated
	 */
	EClass getValue();

	/**
	 * Returns the meta object for the '{@link camel.type.Value#valueEquals(camel.type.Value) <em>Value Equals</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Value Equals</em>' operation.
	 * @see camel.type.Value#valueEquals(camel.type.Value)
	 * @generated
	 */
	EOperation getValue__ValueEquals__Value();

	/**
	 * Returns the meta object for class '{@link camel.type.BooleanValue <em>Boolean Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Value</em>'.
	 * @see camel.type.BooleanValue
	 * @generated
	 */
	EClass getBooleanValue();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.BooleanValue#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see camel.type.BooleanValue#isValue()
	 * @see #getBooleanValue()
	 * @generated
	 */
	EAttribute getBooleanValue_Value();

	/**
	 * Returns the meta object for class '{@link camel.type.EnumerateValue <em>Enumerate Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumerate Value</em>'.
	 * @see camel.type.EnumerateValue
	 * @generated
	 */
	EClass getEnumerateValue();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.EnumerateValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see camel.type.EnumerateValue#getValue()
	 * @see #getEnumerateValue()
	 * @generated
	 */
	EAttribute getEnumerateValue_Value();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.EnumerateValue#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see camel.type.EnumerateValue#getName()
	 * @see #getEnumerateValue()
	 * @generated
	 */
	EAttribute getEnumerateValue_Name();

	/**
	 * Returns the meta object for class '{@link camel.type.NumericValue <em>Numeric Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Value</em>'.
	 * @see camel.type.NumericValue
	 * @generated
	 */
	EClass getNumericValue();

	/**
	 * Returns the meta object for class '{@link camel.type.IntValue <em>Int Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Int Value</em>'.
	 * @see camel.type.IntValue
	 * @generated
	 */
	EClass getIntValue();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.IntValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see camel.type.IntValue#getValue()
	 * @see #getIntValue()
	 * @generated
	 */
	EAttribute getIntValue_Value();

	/**
	 * Returns the meta object for class '{@link camel.type.FloatValue <em>Float Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Float Value</em>'.
	 * @see camel.type.FloatValue
	 * @generated
	 */
	EClass getFloatValue();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.FloatValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see camel.type.FloatValue#getValue()
	 * @see #getFloatValue()
	 * @generated
	 */
	EAttribute getFloatValue_Value();

	/**
	 * Returns the meta object for class '{@link camel.type.DoubleValue <em>Double Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Double Value</em>'.
	 * @see camel.type.DoubleValue
	 * @generated
	 */
	EClass getDoubleValue();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.DoubleValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see camel.type.DoubleValue#getValue()
	 * @see #getDoubleValue()
	 * @generated
	 */
	EAttribute getDoubleValue_Value();

	/**
	 * Returns the meta object for class '{@link camel.type.NegativeInf <em>Negative Inf</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Negative Inf</em>'.
	 * @see camel.type.NegativeInf
	 * @generated
	 */
	EClass getNegativeInf();

	/**
	 * Returns the meta object for class '{@link camel.type.PositiveInf <em>Positive Inf</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Positive Inf</em>'.
	 * @see camel.type.PositiveInf
	 * @generated
	 */
	EClass getPositiveInf();

	/**
	 * Returns the meta object for class '{@link camel.type.ValueToIncrease <em>Value To Increase</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value To Increase</em>'.
	 * @see camel.type.ValueToIncrease
	 * @generated
	 */
	EClass getValueToIncrease();

	/**
	 * Returns the meta object for the containment reference '{@link camel.type.ValueToIncrease#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see camel.type.ValueToIncrease#getValue()
	 * @see #getValueToIncrease()
	 * @generated
	 */
	EReference getValueToIncrease_Value();

	/**
	 * Returns the meta object for class '{@link camel.type.StringValue <em>String Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Value</em>'.
	 * @see camel.type.StringValue
	 * @generated
	 */
	EClass getStringValue();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.StringValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see camel.type.StringValue#getValue()
	 * @see #getStringValue()
	 * @generated
	 */
	EAttribute getStringValue_Value();

	/**
	 * Returns the meta object for class '{@link camel.type.ValueType <em>Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Value Type</em>'.
	 * @see camel.type.ValueType
	 * @generated
	 */
	EClass getValueType();

	/**
	 * Returns the meta object for class '{@link camel.type.BooleanValueType <em>Boolean Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Value Type</em>'.
	 * @see camel.type.BooleanValueType
	 * @generated
	 */
	EClass getBooleanValueType();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.BooleanValueType#getPrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primitive Type</em>'.
	 * @see camel.type.BooleanValueType#getPrimitiveType()
	 * @see #getBooleanValueType()
	 * @generated
	 */
	EAttribute getBooleanValueType_PrimitiveType();

	/**
	 * Returns the meta object for class '{@link camel.type.Enumeration <em>Enumeration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enumeration</em>'.
	 * @see camel.type.Enumeration
	 * @generated
	 */
	EClass getEnumeration();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.type.Enumeration#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see camel.type.Enumeration#getValues()
	 * @see #getEnumeration()
	 * @generated
	 */
	EReference getEnumeration_Values();

	/**
	 * Returns the meta object for the '{@link camel.type.Enumeration#includesName(java.lang.String) <em>Includes Name</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Includes Name</em>' operation.
	 * @see camel.type.Enumeration#includesName(java.lang.String)
	 * @generated
	 */
	EOperation getEnumeration__IncludesName__String();

	/**
	 * Returns the meta object for the '{@link camel.type.Enumeration#includesValue(int) <em>Includes Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Includes Value</em>' operation.
	 * @see camel.type.Enumeration#includesValue(int)
	 * @generated
	 */
	EOperation getEnumeration__IncludesValue__int();

	/**
	 * Returns the meta object for class '{@link camel.type.List <em>List</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List</em>'.
	 * @see camel.type.List
	 * @generated
	 */
	EClass getList();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.type.List#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see camel.type.List#getValues()
	 * @see #getList()
	 * @generated
	 */
	EReference getList_Values();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.List#getPrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primitive Type</em>'.
	 * @see camel.type.List#getPrimitiveType()
	 * @see #getList()
	 * @generated
	 */
	EAttribute getList_PrimitiveType();

	/**
	 * Returns the meta object for the reference '{@link camel.type.List#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Type</em>'.
	 * @see camel.type.List#getType()
	 * @see #getList()
	 * @generated
	 */
	EReference getList_Type();

	/**
	 * Returns the meta object for the '{@link camel.type.List#includesValue(camel.type.Value) <em>Includes Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Includes Value</em>' operation.
	 * @see camel.type.List#includesValue(camel.type.Value)
	 * @generated
	 */
	EOperation getList__IncludesValue__Value();

	/**
	 * Returns the meta object for the '{@link camel.type.List#checkValueType(camel.type.Value) <em>Check Value Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Value Type</em>' operation.
	 * @see camel.type.List#checkValueType(camel.type.Value)
	 * @generated
	 */
	EOperation getList__CheckValueType__Value();

	/**
	 * Returns the meta object for class '{@link camel.type.Range <em>Range</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range</em>'.
	 * @see camel.type.Range
	 * @generated
	 */
	EClass getRange();

	/**
	 * Returns the meta object for the containment reference '{@link camel.type.Range#getLowerLimit <em>Lower Limit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Lower Limit</em>'.
	 * @see camel.type.Range#getLowerLimit()
	 * @see #getRange()
	 * @generated
	 */
	EReference getRange_LowerLimit();

	/**
	 * Returns the meta object for the containment reference '{@link camel.type.Range#getUpperLimit <em>Upper Limit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Upper Limit</em>'.
	 * @see camel.type.Range#getUpperLimit()
	 * @see #getRange()
	 * @generated
	 */
	EReference getRange_UpperLimit();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.Range#getPrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primitive Type</em>'.
	 * @see camel.type.Range#getPrimitiveType()
	 * @see #getRange()
	 * @generated
	 */
	EAttribute getRange_PrimitiveType();

	/**
	 * Returns the meta object for the '{@link camel.type.Range#checkType(camel.type.Limit, camel.type.TypeEnum, boolean) <em>Check Type</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Check Type</em>' operation.
	 * @see camel.type.Range#checkType(camel.type.Limit, camel.type.TypeEnum, boolean)
	 * @generated
	 */
	EOperation getRange__CheckType__Limit_TypeEnum_boolean();

	/**
	 * Returns the meta object for the '{@link camel.type.Range#includesValue(double) <em>Includes Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Includes Value</em>' operation.
	 * @see camel.type.Range#includesValue(double)
	 * @generated
	 */
	EOperation getRange__IncludesValue__double();

	/**
	 * Returns the meta object for class '{@link camel.type.RangeUnion <em>Range Union</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range Union</em>'.
	 * @see camel.type.RangeUnion
	 * @generated
	 */
	EClass getRangeUnion();

	/**
	 * Returns the meta object for the containment reference list '{@link camel.type.RangeUnion#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ranges</em>'.
	 * @see camel.type.RangeUnion#getRanges()
	 * @see #getRangeUnion()
	 * @generated
	 */
	EReference getRangeUnion_Ranges();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.RangeUnion#getPrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primitive Type</em>'.
	 * @see camel.type.RangeUnion#getPrimitiveType()
	 * @see #getRangeUnion()
	 * @generated
	 */
	EAttribute getRangeUnion_PrimitiveType();

	/**
	 * Returns the meta object for the '{@link camel.type.RangeUnion#includesValue(double) <em>Includes Value</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Includes Value</em>' operation.
	 * @see camel.type.RangeUnion#includesValue(double)
	 * @generated
	 */
	EOperation getRangeUnion__IncludesValue__double();

	/**
	 * Returns the meta object for the '{@link camel.type.RangeUnion#invalidRangeSequence(camel.type.RangeUnion) <em>Invalid Range Sequence</em>}' operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the '<em>Invalid Range Sequence</em>' operation.
	 * @see camel.type.RangeUnion#invalidRangeSequence(camel.type.RangeUnion)
	 * @generated
	 */
	EOperation getRangeUnion__InvalidRangeSequence__RangeUnion();

	/**
	 * Returns the meta object for class '{@link camel.type.StringValueType <em>String Value Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Value Type</em>'.
	 * @see camel.type.StringValueType
	 * @generated
	 */
	EClass getStringValueType();

	/**
	 * Returns the meta object for the attribute '{@link camel.type.StringValueType#getPrimitiveType <em>Primitive Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Primitive Type</em>'.
	 * @see camel.type.StringValueType#getPrimitiveType()
	 * @see #getStringValueType()
	 * @generated
	 */
	EAttribute getStringValueType_PrimitiveType();

	/**
	 * Returns the meta object for enum '{@link camel.type.TypeEnum <em>Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Enum</em>'.
	 * @see camel.type.TypeEnum
	 * @generated
	 */
	EEnum getTypeEnum();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	TypeFactory getTypeFactory();

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
		 * The meta object literal for the '{@link camel.type.impl.TypeRepositoryImpl <em>Repository</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.TypeRepositoryImpl
		 * @see camel.type.impl.TypePackageImpl#getTypeRepository()
		 * @generated
		 */
		EClass TYPE_REPOSITORY = eINSTANCE.getTypeRepository();

		/**
		 * The meta object literal for the '<em><b>Data Types</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_REPOSITORY__DATA_TYPES = eINSTANCE.getTypeRepository_DataTypes();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TYPE_REPOSITORY__VALUES = eINSTANCE.getTypeRepository_Values();

		/**
		 * The meta object literal for the '{@link camel.type.impl.LimitImpl <em>Limit</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.LimitImpl
		 * @see camel.type.impl.TypePackageImpl#getLimit()
		 * @generated
		 */
		EClass LIMIT = eINSTANCE.getLimit();

		/**
		 * The meta object literal for the '<em><b>Included</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIMIT__INCLUDED = eINSTANCE.getLimit_Included();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIMIT__VALUE = eINSTANCE.getLimit_Value();

		/**
		 * The meta object literal for the '{@link camel.type.impl.ValueImpl <em>Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.ValueImpl
		 * @see camel.type.impl.TypePackageImpl#getValue()
		 * @generated
		 */
		EClass VALUE = eINSTANCE.getValue();

		/**
		 * The meta object literal for the '<em><b>Value Equals</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation VALUE___VALUE_EQUALS__VALUE = eINSTANCE.getValue__ValueEquals__Value();

		/**
		 * The meta object literal for the '{@link camel.type.impl.BooleanValueImpl <em>Boolean Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.BooleanValueImpl
		 * @see camel.type.impl.TypePackageImpl#getBooleanValue()
		 * @generated
		 */
		EClass BOOLEAN_VALUE = eINSTANCE.getBooleanValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_VALUE__VALUE = eINSTANCE.getBooleanValue_Value();

		/**
		 * The meta object literal for the '{@link camel.type.impl.EnumerateValueImpl <em>Enumerate Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.EnumerateValueImpl
		 * @see camel.type.impl.TypePackageImpl#getEnumerateValue()
		 * @generated
		 */
		EClass ENUMERATE_VALUE = eINSTANCE.getEnumerateValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUMERATE_VALUE__VALUE = eINSTANCE.getEnumerateValue_Value();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUMERATE_VALUE__NAME = eINSTANCE.getEnumerateValue_Name();

		/**
		 * The meta object literal for the '{@link camel.type.impl.NumericValueImpl <em>Numeric Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.NumericValueImpl
		 * @see camel.type.impl.TypePackageImpl#getNumericValue()
		 * @generated
		 */
		EClass NUMERIC_VALUE = eINSTANCE.getNumericValue();

		/**
		 * The meta object literal for the '{@link camel.type.impl.IntValueImpl <em>Int Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.IntValueImpl
		 * @see camel.type.impl.TypePackageImpl#getIntValue()
		 * @generated
		 */
		EClass INT_VALUE = eINSTANCE.getIntValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute INT_VALUE__VALUE = eINSTANCE.getIntValue_Value();

		/**
		 * The meta object literal for the '{@link camel.type.impl.FloatValueImpl <em>Float Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.FloatValueImpl
		 * @see camel.type.impl.TypePackageImpl#getFloatValue()
		 * @generated
		 */
		EClass FLOAT_VALUE = eINSTANCE.getFloatValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOAT_VALUE__VALUE = eINSTANCE.getFloatValue_Value();

		/**
		 * The meta object literal for the '{@link camel.type.impl.DoubleValueImpl <em>Double Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.DoubleValueImpl
		 * @see camel.type.impl.TypePackageImpl#getDoubleValue()
		 * @generated
		 */
		EClass DOUBLE_VALUE = eINSTANCE.getDoubleValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOUBLE_VALUE__VALUE = eINSTANCE.getDoubleValue_Value();

		/**
		 * The meta object literal for the '{@link camel.type.impl.NegativeInfImpl <em>Negative Inf</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.NegativeInfImpl
		 * @see camel.type.impl.TypePackageImpl#getNegativeInf()
		 * @generated
		 */
		EClass NEGATIVE_INF = eINSTANCE.getNegativeInf();

		/**
		 * The meta object literal for the '{@link camel.type.impl.PositiveInfImpl <em>Positive Inf</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.PositiveInfImpl
		 * @see camel.type.impl.TypePackageImpl#getPositiveInf()
		 * @generated
		 */
		EClass POSITIVE_INF = eINSTANCE.getPositiveInf();

		/**
		 * The meta object literal for the '{@link camel.type.impl.ValueToIncreaseImpl <em>Value To Increase</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.ValueToIncreaseImpl
		 * @see camel.type.impl.TypePackageImpl#getValueToIncrease()
		 * @generated
		 */
		EClass VALUE_TO_INCREASE = eINSTANCE.getValueToIncrease();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VALUE_TO_INCREASE__VALUE = eINSTANCE.getValueToIncrease_Value();

		/**
		 * The meta object literal for the '{@link camel.type.impl.StringValueImpl <em>String Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.StringValueImpl
		 * @see camel.type.impl.TypePackageImpl#getStringValue()
		 * @generated
		 */
		EClass STRING_VALUE = eINSTANCE.getStringValue();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_VALUE__VALUE = eINSTANCE.getStringValue_Value();

		/**
		 * The meta object literal for the '{@link camel.type.impl.ValueTypeImpl <em>Value Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.ValueTypeImpl
		 * @see camel.type.impl.TypePackageImpl#getValueType()
		 * @generated
		 */
		EClass VALUE_TYPE = eINSTANCE.getValueType();

		/**
		 * The meta object literal for the '{@link camel.type.impl.BooleanValueTypeImpl <em>Boolean Value Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.BooleanValueTypeImpl
		 * @see camel.type.impl.TypePackageImpl#getBooleanValueType()
		 * @generated
		 */
		EClass BOOLEAN_VALUE_TYPE = eINSTANCE.getBooleanValueType();

		/**
		 * The meta object literal for the '<em><b>Primitive Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_VALUE_TYPE__PRIMITIVE_TYPE = eINSTANCE.getBooleanValueType_PrimitiveType();

		/**
		 * The meta object literal for the '{@link camel.type.impl.EnumerationImpl <em>Enumeration</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.EnumerationImpl
		 * @see camel.type.impl.TypePackageImpl#getEnumeration()
		 * @generated
		 */
		EClass ENUMERATION = eINSTANCE.getEnumeration();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUMERATION__VALUES = eINSTANCE.getEnumeration_Values();

		/**
		 * The meta object literal for the '<em><b>Includes Name</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ENUMERATION___INCLUDES_NAME__STRING = eINSTANCE.getEnumeration__IncludesName__String();

		/**
		 * The meta object literal for the '<em><b>Includes Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation ENUMERATION___INCLUDES_VALUE__INT = eINSTANCE.getEnumeration__IncludesValue__int();

		/**
		 * The meta object literal for the '{@link camel.type.impl.ListImpl <em>List</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.ListImpl
		 * @see camel.type.impl.TypePackageImpl#getList()
		 * @generated
		 */
		EClass LIST = eINSTANCE.getList();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIST__VALUES = eINSTANCE.getList_Values();

		/**
		 * The meta object literal for the '<em><b>Primitive Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LIST__PRIMITIVE_TYPE = eINSTANCE.getList_PrimitiveType();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIST__TYPE = eINSTANCE.getList_Type();

		/**
		 * The meta object literal for the '<em><b>Includes Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LIST___INCLUDES_VALUE__VALUE = eINSTANCE.getList__IncludesValue__Value();

		/**
		 * The meta object literal for the '<em><b>Check Value Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation LIST___CHECK_VALUE_TYPE__VALUE = eINSTANCE.getList__CheckValueType__Value();

		/**
		 * The meta object literal for the '{@link camel.type.impl.RangeImpl <em>Range</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.RangeImpl
		 * @see camel.type.impl.TypePackageImpl#getRange()
		 * @generated
		 */
		EClass RANGE = eINSTANCE.getRange();

		/**
		 * The meta object literal for the '<em><b>Lower Limit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RANGE__LOWER_LIMIT = eINSTANCE.getRange_LowerLimit();

		/**
		 * The meta object literal for the '<em><b>Upper Limit</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RANGE__UPPER_LIMIT = eINSTANCE.getRange_UpperLimit();

		/**
		 * The meta object literal for the '<em><b>Primitive Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE__PRIMITIVE_TYPE = eINSTANCE.getRange_PrimitiveType();

		/**
		 * The meta object literal for the '<em><b>Check Type</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RANGE___CHECK_TYPE__LIMIT_TYPEENUM_BOOLEAN = eINSTANCE.getRange__CheckType__Limit_TypeEnum_boolean();

		/**
		 * The meta object literal for the '<em><b>Includes Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RANGE___INCLUDES_VALUE__DOUBLE = eINSTANCE.getRange__IncludesValue__double();

		/**
		 * The meta object literal for the '{@link camel.type.impl.RangeUnionImpl <em>Range Union</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.RangeUnionImpl
		 * @see camel.type.impl.TypePackageImpl#getRangeUnion()
		 * @generated
		 */
		EClass RANGE_UNION = eINSTANCE.getRangeUnion();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RANGE_UNION__RANGES = eINSTANCE.getRangeUnion_Ranges();

		/**
		 * The meta object literal for the '<em><b>Primitive Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANGE_UNION__PRIMITIVE_TYPE = eINSTANCE.getRangeUnion_PrimitiveType();

		/**
		 * The meta object literal for the '<em><b>Includes Value</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RANGE_UNION___INCLUDES_VALUE__DOUBLE = eINSTANCE.getRangeUnion__IncludesValue__double();

		/**
		 * The meta object literal for the '<em><b>Invalid Range Sequence</b></em>' operation.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EOperation RANGE_UNION___INVALID_RANGE_SEQUENCE__RANGEUNION = eINSTANCE.getRangeUnion__InvalidRangeSequence__RangeUnion();

		/**
		 * The meta object literal for the '{@link camel.type.impl.StringValueTypeImpl <em>String Value Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.impl.StringValueTypeImpl
		 * @see camel.type.impl.TypePackageImpl#getStringValueType()
		 * @generated
		 */
		EClass STRING_VALUE_TYPE = eINSTANCE.getStringValueType();

		/**
		 * The meta object literal for the '<em><b>Primitive Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_VALUE_TYPE__PRIMITIVE_TYPE = eINSTANCE.getStringValueType_PrimitiveType();

		/**
		 * The meta object literal for the '{@link camel.type.TypeEnum <em>Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see camel.type.TypeEnum
		 * @see camel.type.impl.TypePackageImpl#getTypeEnum()
		 * @generated
		 */
		EEnum TYPE_ENUM = eINSTANCE.getTypeEnum();

	}

} //TypePackage
