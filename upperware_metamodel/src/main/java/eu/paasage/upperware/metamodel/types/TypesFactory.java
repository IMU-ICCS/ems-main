/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.types;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.types.TypesPackage
 * 
 */
public interface TypesFactory extends EFactory {

	TypesFactory eINSTANCE = eu.paasage.upperware.metamodel.types.impl.TypesFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Integer Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Integer Value Upperware</em>'.
	 * 
	 */
	IntegerValueUpperware createIntegerValueUpperware();

	/**
	 * Returns a new object of class '<em>Long Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Long Value Upperware</em>'.
	 * 
	 */
	LongValueUpperware createLongValueUpperware();

	/**
	 * Returns a new object of class '<em>Float Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Float Value Upperware</em>'.
	 * 
	 */
	FloatValueUpperware createFloatValueUpperware();

	/**
	 * Returns a new object of class '<em>Double Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Double Value Upperware</em>'.
	 * 
	 */
	DoubleValueUpperware createDoubleValueUpperware();

	/**
	 * Returns a new object of class '<em>String Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>String Value Upperware</em>'.
	 * 
	 */
	StringValueUpperware createStringValueUpperware();

	/**
	 * Returns a new object of class '<em>Boolean Value Upperware</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Value Upperware</em>'.
	 * 
	 */
	BooleanValueUpperware createBooleanValueUpperware();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * 
	 */
	TypesPackage getTypesPackage();

} //TypesFactory
