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


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Integer Value Upperware</b></em>'.
 * <!-- end-user-doc -->
 *
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.types.IntegerValueUpperware#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @see eu.paasage.upperware.metamodel.types.TypesPackage#getIntegerValueUpperware()
 * 
 * 
 */
public interface IntegerValueUpperware extends NumericValueUpperware {

	int getValue();

	void setValue(int value);

} // IntegerValueUpperware
