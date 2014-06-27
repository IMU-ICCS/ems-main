/**
 */
package camel.scalability;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Timer Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see camel.scalability.ScalabilityPackage#getTimerType()
 * @model
 * @generated
 */
public enum TimerType implements Enumerator {
	/**
	 * The '<em><b>WITHIN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WITHIN_VALUE
	 * @generated
	 * @ordered
	 */
	WITHIN(0, "WITHIN", "WITHIN"),

	/**
	 * The '<em><b>WITHIN MAX</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #WITHIN_MAX_VALUE
	 * @generated
	 * @ordered
	 */
	WITHIN_MAX(1, "WITHIN_MAX", "WITHIN_MAX"),

	/**
	 * The '<em><b>INTERVAL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #INTERVAL_VALUE
	 * @generated
	 * @ordered
	 */
	INTERVAL(2, "INTERVAL", "INTERVAL");

	/**
	 * The '<em><b>WITHIN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WITHIN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WITHIN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WITHIN_VALUE = 0;

	/**
	 * The '<em><b>WITHIN MAX</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>WITHIN MAX</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #WITHIN_MAX
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int WITHIN_MAX_VALUE = 1;

	/**
	 * The '<em><b>INTERVAL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>INTERVAL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #INTERVAL
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int INTERVAL_VALUE = 2;

	/**
	 * An array of all the '<em><b>Timer Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final TimerType[] VALUES_ARRAY =
		new TimerType[] {
			WITHIN,
			WITHIN_MAX,
			INTERVAL,
		};

	/**
	 * A public read-only list of all the '<em><b>Timer Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<TimerType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Timer Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TimerType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TimerType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Timer Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TimerType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			TimerType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Timer Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static TimerType get(int value) {
		switch (value) {
			case WITHIN_VALUE: return WITHIN;
			case WITHIN_MAX_VALUE: return WITHIN_MAX;
			case INTERVAL_VALUE: return INTERVAL;
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
	private TimerType(int value, String name, String literal) {
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
	
} //TimerType
