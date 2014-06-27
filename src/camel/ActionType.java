/**
 */
package camel;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Action Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see camel.CamelPackage#getActionType()
 * @model
 * @generated
 */
public enum ActionType implements Enumerator {
	/**
	 * The '<em><b>CREATES</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CREATES_VALUE
	 * @generated
	 * @ordered
	 */
	CREATES(0, "CREATES", "CREATES"),

	/**
	 * The '<em><b>SCALE IN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCALE_IN_VALUE
	 * @generated
	 * @ordered
	 */
	SCALE_IN(1, "SCALE_IN", "SCALE_IN"),

	/**
	 * The '<em><b>SCALE OUT</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCALE_OUT_VALUE
	 * @generated
	 * @ordered
	 */
	SCALE_OUT(2, "SCALE_OUT", "SCALE_OUT"),

	/**
	 * The '<em><b>SCALE UP</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCALE_UP_VALUE
	 * @generated
	 * @ordered
	 */
	SCALE_UP(3, "SCALE_UP", "SCALE_UP"),

	/**
	 * The '<em><b>SCALE DOWN</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCALE_DOWN_VALUE
	 * @generated
	 * @ordered
	 */
	SCALE_DOWN(4, "SCALE_DOWN", "SCALE_DOWN");

	/**
	 * The '<em><b>CREATES</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>CREATES</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CREATES
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int CREATES_VALUE = 0;

	/**
	 * The '<em><b>SCALE IN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SCALE IN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCALE_IN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SCALE_IN_VALUE = 1;

	/**
	 * The '<em><b>SCALE OUT</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SCALE OUT</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCALE_OUT
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SCALE_OUT_VALUE = 2;

	/**
	 * The '<em><b>SCALE UP</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SCALE UP</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCALE_UP
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SCALE_UP_VALUE = 3;

	/**
	 * The '<em><b>SCALE DOWN</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SCALE DOWN</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCALE_DOWN
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SCALE_DOWN_VALUE = 4;

	/**
	 * An array of all the '<em><b>Action Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final ActionType[] VALUES_ARRAY =
		new ActionType[] {
			CREATES,
			SCALE_IN,
			SCALE_OUT,
			SCALE_UP,
			SCALE_DOWN,
		};

	/**
	 * A public read-only list of all the '<em><b>Action Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<ActionType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Action Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ActionType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ActionType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Action Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ActionType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			ActionType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Action Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ActionType get(int value) {
		switch (value) {
			case CREATES_VALUE: return CREATES;
			case SCALE_IN_VALUE: return SCALE_IN;
			case SCALE_OUT_VALUE: return SCALE_OUT;
			case SCALE_UP_VALUE: return SCALE_UP;
			case SCALE_DOWN_VALUE: return SCALE_DOWN;
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
	private ActionType(int value, String name, String literal) {
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
	
} //ActionType
