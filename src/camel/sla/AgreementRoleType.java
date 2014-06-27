/**
 */
package camel.sla;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Agreement Role Type</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see camel.sla.SlaPackage#getAgreementRoleType()
 * @model
 * @generated
 */
public enum AgreementRoleType implements Enumerator {
	/**
	 * The '<em><b>Agreement Initiator</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AGREEMENT_INITIATOR_VALUE
	 * @generated
	 * @ordered
	 */
	AGREEMENT_INITIATOR(0, "AgreementInitiator", "AgreementInitiator"),

	/**
	 * The '<em><b>Agreement Responder</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #AGREEMENT_RESPONDER_VALUE
	 * @generated
	 * @ordered
	 */
	AGREEMENT_RESPONDER(1, "AgreementResponder", "AgreementResponder");

	/**
	 * The '<em><b>Agreement Initiator</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Agreement Initiator</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AGREEMENT_INITIATOR
	 * @model name="AgreementInitiator"
	 * @generated
	 * @ordered
	 */
	public static final int AGREEMENT_INITIATOR_VALUE = 0;

	/**
	 * The '<em><b>Agreement Responder</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Agreement Responder</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #AGREEMENT_RESPONDER
	 * @model name="AgreementResponder"
	 * @generated
	 * @ordered
	 */
	public static final int AGREEMENT_RESPONDER_VALUE = 1;

	/**
	 * An array of all the '<em><b>Agreement Role Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final AgreementRoleType[] VALUES_ARRAY =
		new AgreementRoleType[] {
			AGREEMENT_INITIATOR,
			AGREEMENT_RESPONDER,
		};

	/**
	 * A public read-only list of all the '<em><b>Agreement Role Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<AgreementRoleType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Agreement Role Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AgreementRoleType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AgreementRoleType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Agreement Role Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AgreementRoleType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			AgreementRoleType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Agreement Role Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static AgreementRoleType get(int value) {
		switch (value) {
			case AGREEMENT_INITIATOR_VALUE: return AGREEMENT_INITIATOR;
			case AGREEMENT_RESPONDER_VALUE: return AGREEMENT_RESPONDER;
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
	private AgreementRoleType(int value, String name, String literal) {
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
	
} //AgreementRoleType
