/**
 */
package camel.scalability;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Window Size Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see camel.scalability.ScalabilityPackage#getWindowSizeType()
 * @model
 * @generated
 */
public enum WindowSizeType implements Enumerator {
	/**
	 * The '<em><b>MEASUREMENTS ONLY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #MEASUREMENTS_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	MEASUREMENTS_ONLY(0, "MEASUREMENTS_ONLY", "MEASUREMENTS_ONLY"),

	/**
	 * The '<em><b>TIME ONLY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #TIME_ONLY_VALUE
	 * @generated
	 * @ordered
	 */
	TIME_ONLY(1, "TIME_ONLY", "TIME_ONLY"),

	/**
	 * The '<em><b>FIRST MATCH</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIRST_MATCH_VALUE
	 * @generated
	 * @ordered
	 */
	FIRST_MATCH(2, "FIRST_MATCH", "FIRST_MATCH"),

	/**
	 * The '<em><b>BOTH MATCH</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BOTH_MATCH_VALUE
	 * @generated
	 * @ordered
	 */
	BOTH_MATCH(3, "BOTH_MATCH", "BOTH_MATCH");

	/**
	 * The '<em><b>MEASUREMENTS ONLY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>MEASUREMENTS ONLY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #MEASUREMENTS_ONLY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int MEASUREMENTS_ONLY_VALUE = 0;

	/**
	 * The '<em><b>TIME ONLY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>TIME ONLY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #TIME_ONLY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int TIME_ONLY_VALUE = 1;

	/**
	 * The '<em><b>FIRST MATCH</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FIRST MATCH</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FIRST_MATCH
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FIRST_MATCH_VALUE = 2;

	/**
	 * The '<em><b>BOTH MATCH</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BOTH MATCH</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BOTH_MATCH
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BOTH_MATCH_VALUE = 3;

	/**
	 * An array of all the '<em><b>Window Size Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final WindowSizeType[] VALUES_ARRAY =
		new WindowSizeType[] {
			MEASUREMENTS_ONLY,
			TIME_ONLY,
			FIRST_MATCH,
			BOTH_MATCH,
		};

	/**
	 * A public read-only list of all the '<em><b>Window Size Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<WindowSizeType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Window Size Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static WindowSizeType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			WindowSizeType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Window Size Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static WindowSizeType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			WindowSizeType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Window Size Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static WindowSizeType get(int value) {
		switch (value) {
			case MEASUREMENTS_ONLY_VALUE: return MEASUREMENTS_ONLY;
			case TIME_ONLY_VALUE: return TIME_ONLY;
			case FIRST_MATCH_VALUE: return FIRST_MATCH;
			case BOTH_MATCH_VALUE: return BOTH_MATCH;
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
	private WindowSizeType(int value, String name, String literal) {
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
	
} //WindowSizeType
