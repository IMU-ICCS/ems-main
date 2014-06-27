/**
 */
package camel.type;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see camel.type.TypePackage#getTypeEnum()
 * @model
 * @generated
 */
public enum TypeEnum implements Enumerator {
	/**
	 * The '<em><b>Int Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INT_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	INT_TYPE(0, "IntType", "IntType"),

	/**
	 * The '<em><b>String Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STRING_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	STRING_TYPE(1, "StringType", "StringType"),

	/**
	 * The '<em><b>Boolean Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOOLEAN_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	BOOLEAN_TYPE(2, "BooleanType", "BooleanType"),

	/**
	 * The '<em><b>Float Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FLOAT_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	FLOAT_TYPE(3, "FloatType", "FloatType"),

	/**
	 * The '<em><b>Double Type</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #DOUBLE_TYPE_VALUE
	 * @generated
	 * @ordered
	 */
	DOUBLE_TYPE(4, "DoubleType", "DoubleType");

	/**
	 * The '<em><b>Int Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Int Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INT_TYPE
	 * @model name="IntType"
	 * @generated
	 * @ordered
	 */
	public static final int INT_TYPE_VALUE = 0;

	/**
	 * The '<em><b>String Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>String Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STRING_TYPE
	 * @model name="StringType"
	 * @generated
	 * @ordered
	 */
	public static final int STRING_TYPE_VALUE = 1;

	/**
	 * The '<em><b>Boolean Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Boolean Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BOOLEAN_TYPE
	 * @model name="BooleanType"
	 * @generated
	 * @ordered
	 */
	public static final int BOOLEAN_TYPE_VALUE = 2;

	/**
	 * The '<em><b>Float Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Float Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FLOAT_TYPE
	 * @model name="FloatType"
	 * @generated
	 * @ordered
	 */
	public static final int FLOAT_TYPE_VALUE = 3;

	/**
	 * The '<em><b>Double Type</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Double Type</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #DOUBLE_TYPE
	 * @model name="DoubleType"
	 * @generated
	 * @ordered
	 */
	public static final int DOUBLE_TYPE_VALUE = 4;

	/**
	 * An array of all the '<em><b>Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TypeEnum[] VALUES_ARRAY =
		new TypeEnum[] {
			INT_TYPE,
			STRING_TYPE,
			BOOLEAN_TYPE,
			FLOAT_TYPE,
			DOUBLE_TYPE,
		};

	/**
	 * A public read-only list of all the '<em><b>Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TypeEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypeEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TypeEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypeEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TypeEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TypeEnum get(int value) {
		switch (value) {
			case INT_TYPE_VALUE: return INT_TYPE;
			case STRING_TYPE_VALUE: return STRING_TYPE;
			case BOOLEAN_TYPE_VALUE: return BOOLEAN_TYPE;
			case FLOAT_TYPE_VALUE: return FLOAT_TYPE;
			case DOUBLE_TYPE_VALUE: return DOUBLE_TYPE;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private final String literal;

	/**
	 * Only this class can construct instances.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private TypeEnum(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
	  return value;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
	  return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getLiteral() {
	  return literal;
	}

	/**
	 * Returns the literal value of the enumerator, which is its string representation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		return literal;
	}
	
} //TypeEnum
