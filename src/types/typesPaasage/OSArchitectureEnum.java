/**
 */
package types.typesPaasage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>OS Architecture Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see types.typesPaasage.TypesPaasagePackage#getOSArchitectureEnum()
 * @model
 * @generated
 */
public enum OSArchitectureEnum implements Enumerator {
	/**
	 * The '<em><b>Thirty two bits</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #THIRTY_TWO_BITS_VALUE
	 * @generated
	 * @ordered
	 */
	THIRTY_TWO_BITS(32, "thirty_two_bits", "32bits"),

	/**
	 * The '<em><b>Sixty four bits</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIXTY_FOUR_BITS_VALUE
	 * @generated
	 * @ordered
	 */
	SIXTY_FOUR_BITS(64, "sixty_four_bits", "64bits");

	/**
	 * The '<em><b>Thirty two bits</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Thirty two bits</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #THIRTY_TWO_BITS
	 * @model name="thirty_two_bits" literal="32bits"
	 * @generated
	 * @ordered
	 */
	public static final int THIRTY_TWO_BITS_VALUE = 32;

	/**
	 * The '<em><b>Sixty four bits</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Sixty four bits</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SIXTY_FOUR_BITS
	 * @model name="sixty_four_bits" literal="64bits"
	 * @generated
	 * @ordered
	 */
	public static final int SIXTY_FOUR_BITS_VALUE = 64;

	/**
	 * An array of all the '<em><b>OS Architecture Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final OSArchitectureEnum[] VALUES_ARRAY =
		new OSArchitectureEnum[] {
			THIRTY_TWO_BITS,
			SIXTY_FOUR_BITS,
		};

	/**
	 * A public read-only list of all the '<em><b>OS Architecture Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<OSArchitectureEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>OS Architecture Enum</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OSArchitectureEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OSArchitectureEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>OS Architecture Enum</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OSArchitectureEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OSArchitectureEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>OS Architecture Enum</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static OSArchitectureEnum get(int value) {
		switch (value) {
			case THIRTY_TWO_BITS_VALUE: return THIRTY_TWO_BITS;
			case SIXTY_FOUR_BITS_VALUE: return SIXTY_FOUR_BITS;
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
	private OSArchitectureEnum(int value, String name, String literal) {
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
	
} //OSArchitectureEnum
