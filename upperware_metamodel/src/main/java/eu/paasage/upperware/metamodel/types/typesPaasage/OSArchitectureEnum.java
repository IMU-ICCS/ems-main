/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.types.typesPaasage;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.eclipse.emf.common.util.Enumerator;

/**
 * <!-- begin-user-doc -->
 * A representation of the literals of the enumeration '<em><b>OS Architecture Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getOSArchitectureEnum()
 * 
 * 
 */
public enum OSArchitectureEnum implements Enumerator {
	/**
	 * The '<em><b>Thirty two bits</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #THIRTY_TWO_BITS_VALUE
	 * 
	 * 
	 */
	THIRTY_TWO_BITS(32, "thirty_two_bits", "32bits"),

	/**
	 * The '<em><b>Sixty four bits</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #SIXTY_FOUR_BITS_VALUE
	 * 
	 * 
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
	 *  name="thirty_two_bits" literal="32bits"
	 * 
	 * 
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
	 *  name="sixty_four_bits" literal="64bits"
	 * 
	 * 
	 */
	public static final int SIXTY_FOUR_BITS_VALUE = 64;

	/**
	 * An array of all the '<em><b>OS Architecture Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
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
	 * 
	 */
	public static final List<OSArchitectureEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));


	public static OSArchitectureEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OSArchitectureEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}


	public static OSArchitectureEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			OSArchitectureEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}


	public static OSArchitectureEnum get(int value) {
		switch (value) {
			case THIRTY_TWO_BITS_VALUE: return THIRTY_TWO_BITS;
			case SIXTY_FOUR_BITS_VALUE: return SIXTY_FOUR_BITS;
		}
		return null;
	}

	private final int value;


	private final String name;


	private final String literal;

	private OSArchitectureEnum(int value, String name, String literal) {
		this.value = value;
		this.name = name;
		this.literal = literal;
	}

	public int getValue() {
	  return value;
	}


	public String getName() {
	  return name;
	}

	public String getLiteral() {
	  return literal;
	}

	@Override
	public String toString() {
		return literal;
	}
	
} //OSArchitectureEnum
