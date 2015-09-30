/**
 * Copyright (C) 2015 INRIA, Université Lille 1
 *
 * Contacts: daniel.romero@inria.fr laurence.duchien@inria.fr & lionel.seinturier@inria.fr
 * Date: 09/2015
 
 * This Source Code Form is subject to the terms of the Mozilla Public 
 * License, v. 2.0. If a copy of the MPL was not distributed with this 
 * file, You can obtain one at https://mozilla.org/MPL/2.0/.
 */
package eu.paasage.upperware.metamodel.cp;

import eu.paasage.upperware.metamodel.types.BasicTypeEnum;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Metric Variable</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.MetricVariable#getType <em>Type</em>}</li>
 * </ul>
 * </p>
 *
 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getMetricVariable()
 * 
 * 
 */
public interface MetricVariable extends NumericExpression {
	/**
	 * Returns the value of the '<em><b>Type</b></em>' attribute.
	 * The literals are from the enumeration {@link eu.paasage.upperware.metamodel.types.BasicTypeEnum}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Type</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.BasicTypeEnum
	 * @see #setType(BasicTypeEnum)
	 * @see eu.paasage.upperware.metamodel.cp.CpPackage#getMetricVariable_Type()
	 *  required="true"
	 * 
	 */
	BasicTypeEnum getType();

	/**
	 * Sets the value of the '{@link eu.paasage.upperware.metamodel.cp.MetricVariable#getType <em>Type</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Type</em>' attribute.
	 * @see eu.paasage.upperware.metamodel.types.BasicTypeEnum
	 * @see #getType()
	 * 
	 */
	void setType(BasicTypeEnum value);

} // MetricVariable
