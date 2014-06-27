/**
 */
package camel.scalability;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Schedule Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see camel.scalability.ScalabilityPackage#getScheduleType()
 * @model
 * @generated
 */
public enum ScheduleType implements Enumerator {
	/**
	 * The '<em><b>FIXED RATE</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIXED_RATE_VALUE
	 * @generated
	 * @ordered
	 */
	FIXED_RATE(0, "FIXED_RATE", "FIXED_RATE"),

	/**
	 * The '<em><b>FIXED DELAY</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #FIXED_DELAY_VALUE
	 * @generated
	 * @ordered
	 */
	FIXED_DELAY(1, "FIXED_DELAY", "FIXED_DELAY"),

	/**
	 * The '<em><b>SINGLE EVENT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SINGLE_EVENT_VALUE
	 * @generated
	 * @ordered
	 */
	SINGLE_EVENT(2, "SINGLE_EVENT", "SINGLE_EVENT");

	/**
	 * The '<em><b>FIXED RATE</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FIXED RATE</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FIXED_RATE
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FIXED_RATE_VALUE = 0;

	/**
	 * The '<em><b>FIXED DELAY</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>FIXED DELAY</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #FIXED_DELAY
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int FIXED_DELAY_VALUE = 1;

	/**
	 * The '<em><b>SINGLE EVENT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SINGLE EVENT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SINGLE_EVENT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SINGLE_EVENT_VALUE = 2;

	/**
	 * An array of all the '<em><b>Schedule Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ScheduleType[] VALUES_ARRAY =
		new ScheduleType[] {
			FIXED_RATE,
			FIXED_DELAY,
			SINGLE_EVENT,
		};

	/**
	 * A public read-only list of all the '<em><b>Schedule Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ScheduleType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Schedule Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScheduleType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScheduleType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Schedule Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScheduleType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ScheduleType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Schedule Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ScheduleType get(int value) {
		switch (value) {
			case FIXED_RATE_VALUE: return FIXED_RATE;
			case FIXED_DELAY_VALUE: return FIXED_DELAY;
			case SINGLE_EVENT_VALUE: return SINGLE_EVENT;
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
	private ScheduleType(int value, String name, String literal) {
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
	
} //ScheduleType
