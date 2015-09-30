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
 * A representation of the literals of the enumeration '<em><b>VM Size Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getVMSizeEnum()
 * 
 * 
 */
public enum VMSizeEnum implements Enumerator {
	/**
	 * The '<em><b>XS</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XS_VALUE
	 * 
	 * 
	 */
	XS(0, "XS", "XS"),

	/**
	 * The '<em><b>S</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #S_VALUE
	 * 
	 * 
	 */
	S(1, "S", "S"),

	/**
	 * The '<em><b>L</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #L_VALUE
	 * 
	 * 
	 */
	L(2, "L", "L"),

	/**
	 * The '<em><b>XL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XL_VALUE
	 * 
	 * 
	 */
	XL(3, "XL", "XL"),

	/**
	 * The '<em><b>XXL</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #XXL_VALUE
	 * 
	 * 
	 */
	XXL(4, "XXL", "XXL"),

	/**
	 * The '<em><b>A6</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #A6_VALUE
	 * 
	 * 
	 */
	A6(5, "A6", "A6"),

	/**
	 * The '<em><b>A7</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #A7_VALUE
	 * 
	 * 
	 */
	A7(6, "A7", "A7");

	/**
	 * The '<em><b>XS</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XS</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XS
	 * 
	 * 
	 * 
	 */
	public static final int XS_VALUE = 0;

	/**
	 * The '<em><b>S</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>S</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #S
	 * 
	 * 
	 * 
	 */
	public static final int S_VALUE = 1;

	/**
	 * The '<em><b>L</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>L</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #L
	 * 
	 * 
	 * 
	 */
	public static final int L_VALUE = 2;

	/**
	 * The '<em><b>XL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XL
	 * 
	 * 
	 * 
	 */
	public static final int XL_VALUE = 3;

	/**
	 * The '<em><b>XXL</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>XXL</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #XXL
	 * 
	 * 
	 * 
	 */
	public static final int XXL_VALUE = 4;

	/**
	 * The '<em><b>A6</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>A6</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #A6
	 * 
	 * 
	 * 
	 */
	public static final int A6_VALUE = 5;

	/**
	 * The '<em><b>A7</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>A7</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #A7
	 * 
	 * 
	 * 
	 */
	public static final int A7_VALUE = 6;

	/**
	 * An array of all the '<em><b>VM Size Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private static final VMSizeEnum[] VALUES_ARRAY =
		new VMSizeEnum[] {
			XS,
			S,
			L,
			XL,
			XXL,
			A6,
			A7,
		};

	/**
	 * A public read-only list of all the '<em><b>VM Size Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public static final List<VMSizeEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));


	public static VMSizeEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VMSizeEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}


	public static VMSizeEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VMSizeEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}


	public static VMSizeEnum get(int value) {
		switch (value) {
			case XS_VALUE: return XS;
			case S_VALUE: return S;
			case L_VALUE: return L;
			case XL_VALUE: return XL;
			case XXL_VALUE: return XXL;
			case A6_VALUE: return A6;
			case A7_VALUE: return A7;
		}
		return null;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private final int value;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private final String name;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private final String literal;


	private VMSizeEnum(int value, String name, String literal) {
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
	
} //VMSizeEnum
