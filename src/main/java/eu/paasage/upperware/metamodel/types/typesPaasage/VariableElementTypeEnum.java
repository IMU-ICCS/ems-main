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
 * A representation of the literals of the enumeration '<em><b>Variable Element Type Enum</b></em>',
 * and utility methods for working with them.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.typesPaasage.TypesPaasagePackage#getVariableElementTypeEnum()
 * 
 * 
 */
public enum VariableElementTypeEnum implements Enumerator {
	/**
	 * The '<em><b>Geo Location</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #GEO_LOCATION_VALUE
	 * 
	 * 
	 */
	GEO_LOCATION(0, "GeoLocation", "GeoLocation"),

	/**
	 * The '<em><b>Physical Loccation</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PHYSICAL_LOCCATION_VALUE
	 * 
	 * 
	 */
	PHYSICAL_LOCCATION(1, "PhysicalLoccation", "PhysicalLoccation"),

	/**
	 * The '<em><b>Virtual Location</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #VIRTUAL_LOCATION_VALUE
	 * 
	 * 
	 */
	VIRTUAL_LOCATION(2, "VirtualLocation", "VirtualLocation"),

	/**
	 * The '<em><b>Response Time</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #RESPONSE_TIME_VALUE
	 * 
	 * 
	 */
	RESPONSE_TIME(3, "ResponseTime", "ResponseTime"),

	/**
	 * The '<em><b>Provider</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #PROVIDER_VALUE
	 * 
	 * 
	 */
	PROVIDER(4, "Provider", "Provider"),

	/**
	 * The '<em><b>Bandwidth</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #BANDWIDTH_VALUE
	 * 
	 * 
	 */
	BANDWIDTH(5, "Bandwidth", "Bandwidth"),

	/**
	 * The '<em><b>Users</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #USERS_VALUE
	 * 
	 * 
	 */
	USERS(6, "Users", "Users"),

	/**
	 * The '<em><b>Quantity</b></em>' literal object.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #QUANTITY_VALUE
	 * 
	 * 
	 */
	QUANTITY(7, "Quantity", "Quantity");

	/**
	 * The '<em><b>Geo Location</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Geo Location</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #GEO_LOCATION
	 *  name="GeoLocation"
	 * 
	 * 
	 */
	public static final int GEO_LOCATION_VALUE = 0;

	/**
	 * The '<em><b>Physical Loccation</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Physical Loccation</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PHYSICAL_LOCCATION
	 *  name="PhysicalLoccation"
	 * 
	 * 
	 */
	public static final int PHYSICAL_LOCCATION_VALUE = 1;

	/**
	 * The '<em><b>Virtual Location</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Virtual Location</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #VIRTUAL_LOCATION
	 *  name="VirtualLocation"
	 * 
	 * 
	 */
	public static final int VIRTUAL_LOCATION_VALUE = 2;

	/**
	 * The '<em><b>Response Time</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Response Time</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #RESPONSE_TIME
	 *  name="ResponseTime"
	 * 
	 * 
	 */
	public static final int RESPONSE_TIME_VALUE = 3;

	/**
	 * The '<em><b>Provider</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Provider</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #PROVIDER
	 *  name="Provider"
	 * 
	 * 
	 */
	public static final int PROVIDER_VALUE = 4;

	/**
	 * The '<em><b>Bandwidth</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Bandwidth</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #BANDWIDTH
	 *  name="Bandwidth"
	 * 
	 * 
	 */
	public static final int BANDWIDTH_VALUE = 5;

	/**
	 * The '<em><b>Users</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Users</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #USERS
	 *  name="Users"
	 * 
	 * 
	 */
	public static final int USERS_VALUE = 6;

	/**
	 * The '<em><b>Quantity</b></em>' literal value.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of '<em><b>Quantity</b></em>' literal object isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @see #QUANTITY
	 *  name="Quantity"
	 * 
	 * 
	 */
	public static final int QUANTITY_VALUE = 7;

	/**
	 * An array of all the '<em><b>Variable Element Type Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	private static final VariableElementTypeEnum[] VALUES_ARRAY =
		new VariableElementTypeEnum[] {
			GEO_LOCATION,
			PHYSICAL_LOCCATION,
			VIRTUAL_LOCATION,
			RESPONSE_TIME,
			PROVIDER,
			BANDWIDTH,
			USERS,
			QUANTITY,
		};

	/**
	 * A public read-only list of all the '<em><b>Variable Element Type Enum</b></em>' enumerators.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	public static final List<VariableElementTypeEnum> VALUES = Collections.unmodifiableList(Arrays.asList(VALUES_ARRAY));


	public static VariableElementTypeEnum get(String literal) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VariableElementTypeEnum result = VALUES_ARRAY[i];
			if (result.toString().equals(literal)) {
				return result;
			}
		}
		return null;
	}

	public static VariableElementTypeEnum getByName(String name) {
		for (int i = 0; i < VALUES_ARRAY.length; ++i) {
			VariableElementTypeEnum result = VALUES_ARRAY[i];
			if (result.getName().equals(name)) {
				return result;
			}
		}
		return null;
	}

	public static VariableElementTypeEnum get(int value) {
		switch (value) {
			case GEO_LOCATION_VALUE: return GEO_LOCATION;
			case PHYSICAL_LOCCATION_VALUE: return PHYSICAL_LOCCATION;
			case VIRTUAL_LOCATION_VALUE: return VIRTUAL_LOCATION;
			case RESPONSE_TIME_VALUE: return RESPONSE_TIME;
			case PROVIDER_VALUE: return PROVIDER;
			case BANDWIDTH_VALUE: return BANDWIDTH;
			case USERS_VALUE: return USERS;
			case QUANTITY_VALUE: return QUANTITY;
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

	private VariableElementTypeEnum(int value, String name, String literal) {
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
	
} //VariableElementTypeEnum
