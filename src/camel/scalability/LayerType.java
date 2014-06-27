/**
 */
package camel.scalability;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Layer Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see camel.scalability.ScalabilityPackage#getLayerType()
 * @model
 * @generated
 */
public enum LayerType implements Enumerator {
	/**
	 * The '<em><b>Saa S</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SAA_S_VALUE
	 * @generated
	 * @ordered
	 */
	SAA_S(0, "SaaS", "SaaS"),

	/**
	 * The '<em><b>Paa S</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PAA_S_VALUE
	 * @generated
	 * @ordered
	 */
	PAA_S(1, "PaaS", "PaaS"),

	/**
	 * The '<em><b>Iaa S</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #IAA_S_VALUE
	 * @generated
	 * @ordered
	 */
	IAA_S(2, "IaaS", "IaaS"),

	/**
	 * The '<em><b>BPM</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BPM_VALUE
	 * @generated
	 * @ordered
	 */
	BPM(3, "BPM", "BPM"),

	/**
	 * The '<em><b>SCC</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SCC_VALUE
	 * @generated
	 * @ordered
	 */
	SCC(4, "SCC", "SCC");

	/**
	 * The '<em><b>Saa S</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Saa S</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SAA_S
	 * @model name="SaaS"
	 * @generated
	 * @ordered
	 */
	public static final int SAA_S_VALUE = 0;

	/**
	 * The '<em><b>Paa S</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Paa S</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PAA_S
	 * @model name="PaaS"
	 * @generated
	 * @ordered
	 */
	public static final int PAA_S_VALUE = 1;

	/**
	 * The '<em><b>Iaa S</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Iaa S</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #IAA_S
	 * @model name="IaaS"
	 * @generated
	 * @ordered
	 */
	public static final int IAA_S_VALUE = 2;

	/**
	 * The '<em><b>BPM</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>BPM</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BPM
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int BPM_VALUE = 3;

	/**
	 * The '<em><b>SCC</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>SCC</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #SCC
	 * @model
	 * @generated
	 * @ordered
	 */
	public static final int SCC_VALUE = 4;

	/**
	 * An array of all the '<em><b>Layer Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final LayerType[] VALUES_ARRAY =
		new LayerType[] {
			SAA_S,
			PAA_S,
			IAA_S,
			BPM,
			SCC,
		};

	/**
	 * A public read-only list of all the '<em><b>Layer Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<LayerType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Layer Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LayerType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LayerType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Layer Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LayerType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			LayerType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Layer Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static LayerType get(int value) {
		switch (value) {
			case SAA_S_VALUE: return SAA_S;
			case PAA_S_VALUE: return PAA_S;
			case IAA_S_VALUE: return IAA_S;
			case BPM_VALUE: return BPM;
			case SCC_VALUE: return SCC;
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
	private LayerType(int value, String name, String literal) {
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
	
} //LayerType
