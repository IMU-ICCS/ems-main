/**
 */
package eu.paasage.upperware.metamodel.cp;

import org.eclipse.emf.common.util.Enumerator;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>Variable Type</b></em>',
 * and eu.melodic.upperware.genetic_solver.utility methods for working with them.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getVariableType()
 * @model
 * @generated
 */
public enum VariableType implements Enumerator {
	/**
	 * The '<em><b>Cpu</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CPU_VALUE
	 * @generated
	 * @ordered
	 */
	CPU(0, "cpu", "cpu"),

	/**
	 * The '<em><b>Cores</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CORES_VALUE
	 * @generated
	 * @ordered
	 */
	CORES(1, "cores", "cores"),

	/**
	 * The '<em><b>Ram</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RAM_VALUE
	 * @generated
	 * @ordered
	 */
	RAM(2, "ram", "ram"),

	/**
	 * The '<em><b>Storage</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #STORAGE_VALUE
	 * @generated
	 * @ordered
	 */
	STORAGE(3, "storage", "storage"),

	/**
	 * The '<em><b>Provider</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROVIDER_VALUE
	 * @generated
	 * @ordered
	 */
	PROVIDER(4, "provider", "provider"),

	/**
	 * The '<em><b>Cardinality</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #CARDINALITY_VALUE
	 * @generated
	 * @ordered
	 */
	CARDINALITY(5, "cardinality", "cardinality"),

	/**
	 * The '<em><b>Os</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #OS_VALUE
	 * @generated
	 * @ordered
	 */
	OS(6, "os", "os"),

	/**
	 * The '<em><b>Location</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #LOCATION_VALUE
	 * @generated
	 * @ordered
	 */
	LOCATION(7, "location", "location"),

	/**
	 * The '<em><b>Cpu</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cpu</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CPU
	 * @model name="cpu"
	 * @generated
	 * @ordered
	 */

	LATITUDE(8, "latitude", "latitude"),

	/**
	 * The '<em><b>Latitude</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Latitude</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LATITUDE
	 * @model name="latitude"
	 * @generated
	 * @ordered
	 */

	LONGITUDE(9, "longitude", "longitude");

	/**
	 * The '<em><b>Longitude</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Longitude</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LONGITUDE
	 * @model name="longitude"
	 * @generated
	 * @ordered
	 */

	public static final int CPU_VALUE = 0;

	/**
	 * The '<em><b>Cores</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cores</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CORES
	 * @model name="cores"
	 * @generated
	 * @ordered
	 */
	public static final int CORES_VALUE = 1;

	/**
	 * The '<em><b>Ram</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Ram</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RAM
	 * @model name="ram"
	 * @generated
	 * @ordered
	 */
	public static final int RAM_VALUE = 2;

	/**
	 * The '<em><b>Storage</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Storage</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #STORAGE
	 * @model name="storage"
	 * @generated
	 * @ordered
	 */
	public static final int STORAGE_VALUE = 3;

	/**
	 * The '<em><b>Provider</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Provider</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROVIDER
	 * @model name="provider"
	 * @generated
	 * @ordered
	 */
	public static final int PROVIDER_VALUE = 4;

	/**
	 * The '<em><b>Cardinality</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Cardinality</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #CARDINALITY
	 * @model name="cardinality"
	 * @generated
	 * @ordered
	 */
	public static final int CARDINALITY_VALUE = 5;

	/**
	 * The '<em><b>Os</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Os</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #OS
	 * @model name="os"
	 * @generated
	 * @ordered
	 */
	public static final int OS_VALUE = 6;

	/**
	 * The '<em><b>Location</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Location</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #LOCATION
	 * @model name="location"
	 * @generated
	 * @ordered
	 */
	public static final int LOCATION_VALUE = 7;

	/**
	 * An array of all the '<em><b>Variable Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */

	public static final int LATITUDE_VALUE = 8;

	/**
	 * An array of all the '<em><b>Variable Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */


	public static final int LONGITUDE_VALUE = 9;

	/**
	 * An array of all the '<em><b>Variable Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */

	private static final VariableType[] VALUES_ARRAY =
		new VariableType[] {
			CPU,
			CORES,
			RAM,
			STORAGE,
			PROVIDER,
			CARDINALITY,
			OS,
			LOCATION,
			LATITUDE,
			LONGITUDE,
		};

	/**
	 * A public read-only list of all the '<em><b>Variable Type</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final List<VariableType> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));

	/**
	 * Returns the '<em><b>Variable Type</b></em>' literal with the specified literal value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param literal the literal.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static VariableType get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VariableType result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Variable Type</b></em>' literal with the specified name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param name the name.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static VariableType getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VariableType result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	/**
	 * Returns the '<em><b>Variable Type</b></em>' literal with the specified integer value.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the integer value.
	 * @return the matching enumerator or <code>null</code>.
	 * @generated
	 */
	public static VariableType get(int value) {
		switch (value) {
			case CPU_VALUE: return CPU;
			case CORES_VALUE: return CORES;
			case RAM_VALUE: return RAM;
			case STORAGE_VALUE: return STORAGE;
			case PROVIDER_VALUE: return PROVIDER;
			case CARDINALITY_VALUE: return CARDINALITY;
			case OS_VALUE: return OS;
			case LOCATION_VALUE: return LOCATION;
			case LATITUDE_VALUE: return LATITUDE;
			case LONGITUDE_VALUE: return LONGITUDE;
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
	private VariableType(int value, String name, String literal) {
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
	
} //VariableType
