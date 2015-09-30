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

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.cp.CpPackage
 * 
 */
public interface CpFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	CpFactory eINSTANCE = eu.paasage.upperware.metamodel.cp.impl.CpFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Constraint Problem</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constraint Problem</em>'.
	 * 
	 */
	ConstraintProblem createConstraintProblem();

	/**
	 * Returns a new object of class '<em>Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable</em>'.
	 * 
	 */
	Variable createVariable();

	/**
	 * Returns a new object of class '<em>Numeric Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Numeric Domain</em>'.
	 * 
	 */
	NumericDomain createNumericDomain();

	/**
	 * Returns a new object of class '<em>Range Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Range Domain</em>'.
	 * 
	 */
	RangeDomain createRangeDomain();

	/**
	 * Returns a new object of class '<em>Numeric List Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Numeric List Domain</em>'.
	 * 
	 */
	NumericListDomain createNumericListDomain();

	/**
	 * Returns a new object of class '<em>Constant</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Constant</em>'.
	 * 
	 */
	Constant createConstant();

	/**
	 * Returns a new object of class '<em>Composed Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Composed Expression</em>'.
	 * 
	 */
	ComposedExpression createComposedExpression();

	/**
	 * Returns a new object of class '<em>Comparison Expression</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Comparison Expression</em>'.
	 * 
	 */
	ComparisonExpression createComparisonExpression();

	/**
	 * Returns a new object of class '<em>Goal</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Goal</em>'.
	 * 
	 */
	Goal createGoal();

	/**
	 * Returns a new object of class '<em>List Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>List Domain</em>'.
	 * 
	 */
	ListDomain createListDomain();

	/**
	 * Returns a new object of class '<em>Multi Range Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Multi Range Domain</em>'.
	 * 
	 */
	MultiRangeDomain createMultiRangeDomain();

	/**
	 * Returns a new object of class '<em>Boolean Domain</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Boolean Domain</em>'.
	 * 
	 */
	BooleanDomain createBooleanDomain();

	/**
	 * Returns a new object of class '<em>Metric Variable</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Variable</em>'.
	 * 
	 */
	MetricVariable createMetricVariable();

	/**
	 * Returns a new object of class '<em>Solution</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Solution</em>'.
	 * 
	 */
	Solution createSolution();

	/**
	 * Returns a new object of class '<em>Variable Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Variable Value</em>'.
	 * 
	 */
	VariableValue createVariableValue();

	/**
	 * Returns a new object of class '<em>Metric Variable Value</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Metric Variable Value</em>'.
	 * 
	 */
	MetricVariableValue createMetricVariableValue();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * 
	 */
	CpPackage getCpPackage();

} //CpFactory
