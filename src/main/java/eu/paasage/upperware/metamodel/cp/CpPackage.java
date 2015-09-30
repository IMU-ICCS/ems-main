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

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see eu.paasage.upperware.metamodel.cp.CpFactory
 *  kind="package"
 * 
 */
public interface CpPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	String eNAME = "cp";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	String eNS_URI = "http://www.paasage.eu/eu/paasage/upperware/metamodel/cp";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	String eNS_PREFIX = "cp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 */
	CpPackage eINSTANCE = eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl.init();

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.CPElementImpl <em>CP Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.CPElementImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getCPElement()
	 * 
	 */
	int CP_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CP_ELEMENT__ID = 0;

	/**
	 * The number of structural features of the '<em>CP Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CP_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>CP Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CP_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl <em>Constraint Problem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConstraintProblem()
	 * 
	 */
	int CONSTRAINT_PROBLEM = 1;

	/**
	 * The feature id for the '<em><b>Goals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__GOALS = 0;

	/**
	 * The feature id for the '<em><b>Constants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__CONSTANTS = 1;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__VARIABLES = 2;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__CONSTRAINTS = 3;

	/**
	 * The feature id for the '<em><b>Aux Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__AUX_EXPRESSIONS = 4;

	/**
	 * The feature id for the '<em><b>Metric Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__METRIC_VARIABLES = 5;

	/**
	 * The feature id for the '<em><b>Solution</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__SOLUTION = 6;

	/**
	 * The number of structural features of the '<em>Constraint Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM_FEATURE_COUNT = 7;

	/**
	 * The number of operations of the '<em>Constraint Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getExpression()
	 * 
	 */
	int EXPRESSION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int EXPRESSION__ID = CP_ELEMENT__ID;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int EXPRESSION_OPERATION_COUNT = CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl <em>Numeric Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericExpression()
	 * 
	 */
	int NUMERIC_EXPRESSION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_EXPRESSION__ID = EXPRESSION__ID;

	/**
	 * The number of structural features of the '<em>Numeric Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Numeric Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.VariableImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariable()
	 * 
	 */
	int VARIABLE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE__DOMAIN = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Location Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE__LOCATION_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Provider Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE__PROVIDER_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Vm Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE__VM_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Os Image Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE__OS_IMAGE_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Hardware Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE__HARDWARE_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 5;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 6;

	/**
	 * The number of operations of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.DomainImpl <em>Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.DomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getDomain()
	 * 
	 */
	int DOMAIN = 5;

	/**
	 * The number of structural features of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int DOMAIN_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int DOMAIN_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericDomainImpl <em>Numeric Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.NumericDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericDomain()
	 * 
	 */
	int NUMERIC_DOMAIN = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_DOMAIN__TYPE = DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_DOMAIN__VALUE = DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Numeric Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_DOMAIN_FEATURE_COUNT = DOMAIN_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Numeric Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_DOMAIN_OPERATION_COUNT = DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl <em>Range Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getRangeDomain()
	 * 
	 */
	int RANGE_DOMAIN = 7;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int RANGE_DOMAIN__TYPE = NUMERIC_DOMAIN__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int RANGE_DOMAIN__VALUE = NUMERIC_DOMAIN__VALUE;

	/**
	 * The feature id for the '<em><b>From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int RANGE_DOMAIN__FROM = NUMERIC_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int RANGE_DOMAIN__TO = NUMERIC_DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Range Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int RANGE_DOMAIN_FEATURE_COUNT = NUMERIC_DOMAIN_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Range Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int RANGE_DOMAIN_OPERATION_COUNT = NUMERIC_DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericListDomainImpl <em>Numeric List Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.NumericListDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericListDomain()
	 * 
	 */
	int NUMERIC_LIST_DOMAIN = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN__TYPE = NUMERIC_DOMAIN__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN__VALUE = NUMERIC_DOMAIN__VALUE;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN__VALUES = NUMERIC_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Numeric List Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN_FEATURE_COUNT = NUMERIC_DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Numeric List Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN_OPERATION_COUNT = NUMERIC_DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConstantImpl <em>Constant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ConstantImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConstant()
	 * 
	 */
	int CONSTANT = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTANT__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTANT__TYPE = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTANT__VALUE = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Constant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTANT_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Constant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int CONSTANT_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl <em>Composed Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedExpression()
	 * 
	 */
	int COMPOSED_EXPRESSION = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_EXPRESSION__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Expressions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_EXPRESSION__EXPRESSIONS = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_EXPRESSION__OPERATOR = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Composed Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_EXPRESSION_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Composed Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_EXPRESSION_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getBooleanExpression()
	 * 
	 */
	int BOOLEAN_EXPRESSION = 13;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION__ID = EXPRESSION__ID;

	/**
	 * The number of structural features of the '<em>Boolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl <em>Comparison Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComparisonExpression()
	 * 
	 */
	int COMPARISON_EXPRESSION = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPARISON_EXPRESSION__ID = BOOLEAN_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Exp1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPARISON_EXPRESSION__EXP1 = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exp2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPARISON_EXPRESSION__EXP2 = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Comparator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPARISON_EXPRESSION__COMPARATOR = BOOLEAN_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Comparison Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPARISON_EXPRESSION_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Comparison Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPARISON_EXPRESSION_OPERATION_COUNT = BOOLEAN_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.GoalImpl <em>Goal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.GoalImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getGoal()
	 * 
	 */
	int GOAL = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int GOAL__ID = CP_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int GOAL__EXPRESSION = CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Goal Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int GOAL__GOAL_TYPE = CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int GOAL_FEATURE_COUNT = CP_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int GOAL_OPERATION_COUNT = CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl <em>List Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getListDomain()
	 * 
	 */
	int LIST_DOMAIN = 14;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int LIST_DOMAIN__VALUES = DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int LIST_DOMAIN__VALUE = DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>List Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int LIST_DOMAIN_FEATURE_COUNT = DOMAIN_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>List Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int LIST_DOMAIN_OPERATION_COUNT = DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.MultiRangeDomainImpl <em>Multi Range Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.MultiRangeDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMultiRangeDomain()
	 * 
	 */
	int MULTI_RANGE_DOMAIN = 15;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN__TYPE = NUMERIC_DOMAIN__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN__VALUE = NUMERIC_DOMAIN__VALUE;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN__RANGES = NUMERIC_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Multi Range Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN_FEATURE_COUNT = NUMERIC_DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Multi Range Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN_OPERATION_COUNT = NUMERIC_DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.UnaryExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getUnaryExpression()
	 * 
	 */
	int UNARY_EXPRESSION = 16;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int UNARY_EXPRESSION__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int UNARY_EXPRESSION__EXPRESSION = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int UNARY_EXPRESSION_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int UNARY_EXPRESSION_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.SimpleUnaryExpressionImpl <em>Simple Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.SimpleUnaryExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSimpleUnaryExpression()
	 * 
	 */
	int SIMPLE_UNARY_EXPRESSION = 17;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION__ID = UNARY_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION__EXPRESSION = UNARY_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION__OPERATOR = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simple Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Simple Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION_OPERATION_COUNT = UNARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl <em>Composed Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedUnaryExpression()
	 * 
	 */
	int COMPOSED_UNARY_EXPRESSION = 18;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION__ID = UNARY_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION__EXPRESSION = UNARY_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION__OPERATOR = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION__VALUE = UNARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Composed Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Composed Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION_OPERATION_COUNT = UNARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.BooleanDomainImpl <em>Boolean Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.BooleanDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getBooleanDomain()
	 * 
	 */
	int BOOLEAN_DOMAIN = 19;

	/**
	 * The number of structural features of the '<em>Boolean Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int BOOLEAN_DOMAIN_FEATURE_COUNT = DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int BOOLEAN_DOMAIN_OPERATION_COUNT = DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableImpl <em>Metric Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.MetricVariableImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMetricVariable()
	 * 
	 */
	int METRIC_VARIABLE = 20;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int METRIC_VARIABLE__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int METRIC_VARIABLE__TYPE = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Metric Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int METRIC_VARIABLE_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Metric Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int METRIC_VARIABLE_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.SolutionImpl <em>Solution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.SolutionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSolution()
	 * 
	 */
	int SOLUTION = 21;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SOLUTION__TIMESTAMP = 0;

	/**
	 * The feature id for the '<em><b>Variable Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SOLUTION__VARIABLE_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Metric Variable Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SOLUTION__METRIC_VARIABLE_VALUE = 2;

	/**
	 * The number of structural features of the '<em>Solution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SOLUTION_FEATURE_COUNT = 3;

	/**
	 * The number of operations of the '<em>Solution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int SOLUTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.VariableValueImpl <em>Variable Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.VariableValueImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariableValue()
	 * 
	 */
	int VARIABLE_VALUE = 22;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE_VALUE__VARIABLE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE_VALUE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Variable Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE_VALUE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Variable Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int VARIABLE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl <em>Metric Variable Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMetricVariableValue()
	 * 
	 */
	int METRIC_VARIABLE_VALUE = 23;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int METRIC_VARIABLE_VALUE__VARIABLE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int METRIC_VARIABLE_VALUE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Metric Variable Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int METRIC_VARIABLE_VALUE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Metric Variable Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @ordered
	 */
	int METRIC_VARIABLE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.OperatorEnum <em>Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.OperatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getOperatorEnum()
	 * 
	 */
	int OPERATOR_ENUM = 24;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum <em>Goal Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getGoalOperatorEnum()
	 * 
	 */
	int GOAL_OPERATOR_ENUM = 25;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.ComparatorEnum <em>Comparator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.ComparatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComparatorEnum()
	 * 
	 */
	int COMPARATOR_ENUM = 26;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum <em>Simple Unary Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSimpleUnaryOperatorEnum()
	 * 
	 */
	int SIMPLE_UNARY_OPERATOR_ENUM = 27;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum <em>Composed Unary Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedUnaryOperatorEnum()
	 * 
	 */
	int COMPOSED_UNARY_OPERATOR_ENUM = 28;


	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.CPElement <em>CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CP Element</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.CPElement
	 * 
	 */
	EClass getCPElement();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.CPElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.CPElement#getId()
	 * @see #getCPElement()
	 * 
	 */
	EAttribute getCPElement_Id();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem <em>Constraint Problem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Problem</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem
	 * 
	 */
	EClass getConstraintProblem();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getGoals <em>Goals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Goals</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getGoals()
	 * @see #getConstraintProblem()
	 * 
	 */
	EReference getConstraintProblem_Goals();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstants <em>Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constants</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstants()
	 * @see #getConstraintProblem()
	 * 
	 */
	EReference getConstraintProblem_Constants();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getVariables()
	 * @see #getConstraintProblem()
	 * 
	 */
	EReference getConstraintProblem_Variables();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstraints()
	 * @see #getConstraintProblem()
	 * 
	 */
	EReference getConstraintProblem_Constraints();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getAuxExpressions <em>Aux Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Aux Expressions</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getAuxExpressions()
	 * @see #getConstraintProblem()
	 * 
	 */
	EReference getConstraintProblem_AuxExpressions();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getMetricVariables <em>Metric Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metric Variables</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getMetricVariables()
	 * @see #getConstraintProblem()
	 * 
	 */
	EReference getConstraintProblem_MetricVariables();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getSolution <em>Solution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Solution</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getSolution()
	 * @see #getConstraintProblem()
	 * 
	 */
	EReference getConstraintProblem_Solution();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Expression
	 * 
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.NumericExpression <em>Numeric Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericExpression
	 * 
	 */
	EClass getNumericExpression();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable
	 * 
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.Variable#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getDomain()
	 * @see #getVariable()
	 * 
	 */
	EReference getVariable_Domain();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getLocationId <em>Location Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getLocationId()
	 * @see #getVariable()
	 * 
	 */
	EAttribute getVariable_LocationId();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getProviderId <em>Provider Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Provider Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getProviderId()
	 * @see #getVariable()
	 * 
	 */
	EAttribute getVariable_ProviderId();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getVmId <em>Vm Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vm Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getVmId()
	 * @see #getVariable()
	 * 
	 */
	EAttribute getVariable_VmId();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getOsImageId <em>Os Image Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Os Image Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getOsImageId()
	 * @see #getVariable()
	 * 
	 */
	EAttribute getVariable_OsImageId();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getHardwareId <em>Hardware Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hardware Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getHardwareId()
	 * @see #getVariable()
	 * 
	 */
	EAttribute getVariable_HardwareId();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Domain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Domain
	 * 
	 */
	EClass getDomain();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.NumericDomain <em>Numeric Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericDomain
	 * 
	 */
	EClass getNumericDomain();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.NumericDomain#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericDomain#getType()
	 * @see #getNumericDomain()
	 * 
	 */
	EAttribute getNumericDomain_Type();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.NumericDomain#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericDomain#getValue()
	 * @see #getNumericDomain()
	 * 
	 */
	EReference getNumericDomain_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.RangeDomain <em>Range Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.RangeDomain
	 * 
	 */
	EClass getRangeDomain();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>From</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.RangeDomain#getFrom()
	 * @see #getRangeDomain()
	 * 
	 */
	EReference getRangeDomain_From();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>To</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.RangeDomain#getTo()
	 * @see #getRangeDomain()
	 * 
	 */
	EReference getRangeDomain_To();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.NumericListDomain <em>Numeric List Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric List Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericListDomain
	 * 
	 */
	EClass getNumericListDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.NumericListDomain#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericListDomain#getValues()
	 * @see #getNumericListDomain()
	 * 
	 */
	EReference getNumericListDomain_Values();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Constant
	 * 
	 */
	EClass getConstant();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Constant#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Constant#getType()
	 * @see #getConstant()
	 * 
	 */
	EAttribute getConstant_Type();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.Constant#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Constant#getValue()
	 * @see #getConstant()
	 * 
	 */
	EReference getConstant_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ComposedExpression <em>Composed Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composed Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedExpression
	 * 
	 */
	EClass getComposedExpression();

	/**
	 * Returns the meta object for the reference list '{@link eu.paasage.upperware.metamodel.cp.ComposedExpression#getExpressions <em>Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Expressions</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedExpression#getExpressions()
	 * @see #getComposedExpression()
	 * 
	 */
	EReference getComposedExpression_Expressions();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.ComposedExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedExpression#getOperator()
	 * @see #getComposedExpression()
	 * 
	 */
	EAttribute getComposedExpression_Operator();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression <em>Comparison Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comparison Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparisonExpression
	 * 
	 */
	EClass getComparisonExpression();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp1 <em>Exp1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Exp1</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp1()
	 * @see #getComparisonExpression()
	 * 
	 */
	EReference getComparisonExpression_Exp1();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp2 <em>Exp2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Exp2</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp2()
	 * @see #getComparisonExpression()
	 * 
	 */
	EReference getComparisonExpression_Exp2();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getComparator <em>Comparator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comparator</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparisonExpression#getComparator()
	 * @see #getComparisonExpression()
	 * 
	 */
	EAttribute getComparisonExpression_Comparator();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Goal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Goal</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Goal
	 * 
	 */
	EClass getGoal();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.Goal#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Goal#getExpression()
	 * @see #getGoal()
	 * 
	 */
	EReference getGoal_Expression();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Goal#getGoalType <em>Goal Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Goal Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Goal#getGoalType()
	 * @see #getGoal()
	 * 
	 */
	EAttribute getGoal_GoalType();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.BooleanExpression
	 * 
	 */
	EClass getBooleanExpression();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ListDomain <em>List Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ListDomain
	 * 
	 */
	EClass getListDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ListDomain#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ListDomain#getValues()
	 * @see #getListDomain()
	 * 
	 */
	EReference getListDomain_Values();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.ListDomain#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ListDomain#getValue()
	 * @see #getListDomain()
	 * 
	 */
	EReference getListDomain_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.MultiRangeDomain <em>Multi Range Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Range Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MultiRangeDomain
	 * 
	 */
	EClass getMultiRangeDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.MultiRangeDomain#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ranges</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MultiRangeDomain#getRanges()
	 * @see #getMultiRangeDomain()
	 * 
	 */
	EReference getMultiRangeDomain_Ranges();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.UnaryExpression
	 * 
	 */
	EClass getUnaryExpression();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.UnaryExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.UnaryExpression#getExpression()
	 * @see #getUnaryExpression()
	 * 
	 */
	EReference getUnaryExpression_Expression();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression <em>Simple Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Unary Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression
	 * 
	 */
	EClass getSimpleUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression#getOperator()
	 * @see #getSimpleUnaryExpression()
	 * 
	 */
	EAttribute getSimpleUnaryExpression_Operator();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression <em>Composed Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composed Unary Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression
	 * 
	 */
	EClass getComposedUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getOperator()
	 * @see #getComposedUnaryExpression()
	 * 
	 */
	EAttribute getComposedUnaryExpression_Operator();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getValue()
	 * @see #getComposedUnaryExpression()
	 * 
	 */
	EAttribute getComposedUnaryExpression_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.BooleanDomain <em>Boolean Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.BooleanDomain
	 * 
	 */
	EClass getBooleanDomain();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.MetricVariable <em>Metric Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Variable</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariable
	 * 
	 */
	EClass getMetricVariable();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.MetricVariable#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariable#getType()
	 * @see #getMetricVariable()
	 * 
	 */
	EAttribute getMetricVariable_Type();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Solution <em>Solution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Solution</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Solution
	 * 
	 */
	EClass getSolution();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Solution#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Solution#getTimestamp()
	 * @see #getSolution()
	 * 
	 */
	EAttribute getSolution_Timestamp();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.Solution#getVariableValue <em>Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variable Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Solution#getVariableValue()
	 * @see #getSolution()
	 * 
	 */
	EReference getSolution_VariableValue();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.Solution#getMetricVariableValue <em>Metric Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metric Variable Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Solution#getMetricVariableValue()
	 * @see #getSolution()
	 * 
	 */
	EReference getSolution_MetricVariableValue();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.VariableValue <em>Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.VariableValue
	 * 
	 */
	EClass getVariableValue();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.VariableValue#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.VariableValue#getVariable()
	 * @see #getVariableValue()
	 * 
	 */
	EReference getVariableValue_Variable();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.VariableValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.VariableValue#getValue()
	 * @see #getVariableValue()
	 * 
	 */
	EReference getVariableValue_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.MetricVariableValue <em>Metric Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Variable Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariableValue
	 * 
	 */
	EClass getMetricVariableValue();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.MetricVariableValue#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariableValue#getVariable()
	 * @see #getMetricVariableValue()
	 * 
	 */
	EReference getMetricVariableValue_Variable();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.MetricVariableValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariableValue#getValue()
	 * @see #getMetricVariableValue()
	 * 
	 */
	EReference getMetricVariableValue_Value();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.OperatorEnum <em>Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.OperatorEnum
	 * 
	 */
	EEnum getOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum <em>Goal Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Goal Operator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * 
	 */
	EEnum getGoalOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.ComparatorEnum <em>Comparator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Comparator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparatorEnum
	 * 
	 */
	EEnum getComparatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum <em>Simple Unary Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Simple Unary Operator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum
	 * 
	 */
	EEnum getSimpleUnaryOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum <em>Composed Unary Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Composed Unary Operator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
	 * 
	 */
	EEnum getComposedUnaryOperatorEnum();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * 
	 */
	CpFactory getCpFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * 
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.CPElementImpl <em>CP Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.CPElementImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getCPElement()
		 * 
		 */
		EClass CP_ELEMENT = eINSTANCE.getCPElement();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute CP_ELEMENT__ID = eINSTANCE.getCPElement_Id();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl <em>Constraint Problem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConstraintProblem()
		 * 
		 */
		EClass CONSTRAINT_PROBLEM = eINSTANCE.getConstraintProblem();

		/**
		 * The meta object literal for the '<em><b>Goals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference CONSTRAINT_PROBLEM__GOALS = eINSTANCE.getConstraintProblem_Goals();

		/**
		 * The meta object literal for the '<em><b>Constants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference CONSTRAINT_PROBLEM__CONSTANTS = eINSTANCE.getConstraintProblem_Constants();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference CONSTRAINT_PROBLEM__VARIABLES = eINSTANCE.getConstraintProblem_Variables();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference CONSTRAINT_PROBLEM__CONSTRAINTS = eINSTANCE.getConstraintProblem_Constraints();

		/**
		 * The meta object literal for the '<em><b>Aux Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference CONSTRAINT_PROBLEM__AUX_EXPRESSIONS = eINSTANCE.getConstraintProblem_AuxExpressions();

		/**
		 * The meta object literal for the '<em><b>Metric Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference CONSTRAINT_PROBLEM__METRIC_VARIABLES = eINSTANCE.getConstraintProblem_MetricVariables();

		/**
		 * The meta object literal for the '<em><b>Solution</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference CONSTRAINT_PROBLEM__SOLUTION = eINSTANCE.getConstraintProblem_Solution();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getExpression()
		 * 
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl <em>Numeric Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericExpression()
		 * 
		 */
		EClass NUMERIC_EXPRESSION = eINSTANCE.getNumericExpression();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.VariableImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariable()
		 * 
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference VARIABLE__DOMAIN = eINSTANCE.getVariable_Domain();

		/**
		 * The meta object literal for the '<em><b>Location Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute VARIABLE__LOCATION_ID = eINSTANCE.getVariable_LocationId();

		/**
		 * The meta object literal for the '<em><b>Provider Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute VARIABLE__PROVIDER_ID = eINSTANCE.getVariable_ProviderId();

		/**
		 * The meta object literal for the '<em><b>Vm Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute VARIABLE__VM_ID = eINSTANCE.getVariable_VmId();

		/**
		 * The meta object literal for the '<em><b>Os Image Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute VARIABLE__OS_IMAGE_ID = eINSTANCE.getVariable_OsImageId();

		/**
		 * The meta object literal for the '<em><b>Hardware Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute VARIABLE__HARDWARE_ID = eINSTANCE.getVariable_HardwareId();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.DomainImpl <em>Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.DomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getDomain()
		 * 
		 */
		EClass DOMAIN = eINSTANCE.getDomain();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericDomainImpl <em>Numeric Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.NumericDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericDomain()
		 * 
		 */
		EClass NUMERIC_DOMAIN = eINSTANCE.getNumericDomain();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute NUMERIC_DOMAIN__TYPE = eINSTANCE.getNumericDomain_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference NUMERIC_DOMAIN__VALUE = eINSTANCE.getNumericDomain_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl <em>Range Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getRangeDomain()
		 * 
		 */
		EClass RANGE_DOMAIN = eINSTANCE.getRangeDomain();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference RANGE_DOMAIN__FROM = eINSTANCE.getRangeDomain_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference RANGE_DOMAIN__TO = eINSTANCE.getRangeDomain_To();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericListDomainImpl <em>Numeric List Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.NumericListDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericListDomain()
		 * 
		 */
		EClass NUMERIC_LIST_DOMAIN = eINSTANCE.getNumericListDomain();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference NUMERIC_LIST_DOMAIN__VALUES = eINSTANCE.getNumericListDomain_Values();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConstantImpl <em>Constant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ConstantImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConstant()
		 * 
		 */
		EClass CONSTANT = eINSTANCE.getConstant();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute CONSTANT__TYPE = eINSTANCE.getConstant_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference CONSTANT__VALUE = eINSTANCE.getConstant_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl <em>Composed Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedExpression()
		 * 
		 */
		EClass COMPOSED_EXPRESSION = eINSTANCE.getComposedExpression();

		/**
		 * The meta object literal for the '<em><b>Expressions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference COMPOSED_EXPRESSION__EXPRESSIONS = eINSTANCE.getComposedExpression_Expressions();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute COMPOSED_EXPRESSION__OPERATOR = eINSTANCE.getComposedExpression_Operator();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl <em>Comparison Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComparisonExpression()
		 * 
		 */
		EClass COMPARISON_EXPRESSION = eINSTANCE.getComparisonExpression();

		/**
		 * The meta object literal for the '<em><b>Exp1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference COMPARISON_EXPRESSION__EXP1 = eINSTANCE.getComparisonExpression_Exp1();

		/**
		 * The meta object literal for the '<em><b>Exp2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference COMPARISON_EXPRESSION__EXP2 = eINSTANCE.getComparisonExpression_Exp2();

		/**
		 * The meta object literal for the '<em><b>Comparator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute COMPARISON_EXPRESSION__COMPARATOR = eINSTANCE.getComparisonExpression_Comparator();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.GoalImpl <em>Goal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.GoalImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getGoal()
		 * 
		 */
		EClass GOAL = eINSTANCE.getGoal();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference GOAL__EXPRESSION = eINSTANCE.getGoal_Expression();

		/**
		 * The meta object literal for the '<em><b>Goal Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute GOAL__GOAL_TYPE = eINSTANCE.getGoal_GoalType();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getBooleanExpression()
		 * 
		 */
		EClass BOOLEAN_EXPRESSION = eINSTANCE.getBooleanExpression();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl <em>List Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getListDomain()
		 * 
		 */
		EClass LIST_DOMAIN = eINSTANCE.getListDomain();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference LIST_DOMAIN__VALUES = eINSTANCE.getListDomain_Values();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference LIST_DOMAIN__VALUE = eINSTANCE.getListDomain_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.MultiRangeDomainImpl <em>Multi Range Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.MultiRangeDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMultiRangeDomain()
		 * 
		 */
		EClass MULTI_RANGE_DOMAIN = eINSTANCE.getMultiRangeDomain();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference MULTI_RANGE_DOMAIN__RANGES = eINSTANCE.getMultiRangeDomain_Ranges();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.UnaryExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getUnaryExpression()
		 * 
		 */
		EClass UNARY_EXPRESSION = eINSTANCE.getUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference UNARY_EXPRESSION__EXPRESSION = eINSTANCE.getUnaryExpression_Expression();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.SimpleUnaryExpressionImpl <em>Simple Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.SimpleUnaryExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSimpleUnaryExpression()
		 * 
		 */
		EClass SIMPLE_UNARY_EXPRESSION = eINSTANCE.getSimpleUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute SIMPLE_UNARY_EXPRESSION__OPERATOR = eINSTANCE.getSimpleUnaryExpression_Operator();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl <em>Composed Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedUnaryExpression()
		 * 
		 */
		EClass COMPOSED_UNARY_EXPRESSION = eINSTANCE.getComposedUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute COMPOSED_UNARY_EXPRESSION__OPERATOR = eINSTANCE.getComposedUnaryExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute COMPOSED_UNARY_EXPRESSION__VALUE = eINSTANCE.getComposedUnaryExpression_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.BooleanDomainImpl <em>Boolean Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.BooleanDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getBooleanDomain()
		 * 
		 */
		EClass BOOLEAN_DOMAIN = eINSTANCE.getBooleanDomain();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableImpl <em>Metric Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.MetricVariableImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMetricVariable()
		 * 
		 */
		EClass METRIC_VARIABLE = eINSTANCE.getMetricVariable();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute METRIC_VARIABLE__TYPE = eINSTANCE.getMetricVariable_Type();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.SolutionImpl <em>Solution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.SolutionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSolution()
		 * 
		 */
		EClass SOLUTION = eINSTANCE.getSolution();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EAttribute SOLUTION__TIMESTAMP = eINSTANCE.getSolution_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Variable Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference SOLUTION__VARIABLE_VALUE = eINSTANCE.getSolution_VariableValue();

		/**
		 * The meta object literal for the '<em><b>Metric Variable Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference SOLUTION__METRIC_VARIABLE_VALUE = eINSTANCE.getSolution_MetricVariableValue();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.VariableValueImpl <em>Variable Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.VariableValueImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariableValue()
		 * 
		 */
		EClass VARIABLE_VALUE = eINSTANCE.getVariableValue();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference VARIABLE_VALUE__VARIABLE = eINSTANCE.getVariableValue_Variable();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference VARIABLE_VALUE__VALUE = eINSTANCE.getVariableValue_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl <em>Metric Variable Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMetricVariableValue()
		 * 
		 */
		EClass METRIC_VARIABLE_VALUE = eINSTANCE.getMetricVariableValue();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference METRIC_VARIABLE_VALUE__VARIABLE = eINSTANCE.getMetricVariableValue_Variable();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * 
		 */
		EReference METRIC_VARIABLE_VALUE__VALUE = eINSTANCE.getMetricVariableValue_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.OperatorEnum <em>Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.OperatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getOperatorEnum()
		 * 
		 */
		EEnum OPERATOR_ENUM = eINSTANCE.getOperatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum <em>Goal Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getGoalOperatorEnum()
		 * 
		 */
		EEnum GOAL_OPERATOR_ENUM = eINSTANCE.getGoalOperatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.ComparatorEnum <em>Comparator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.ComparatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComparatorEnum()
		 * 
		 */
		EEnum COMPARATOR_ENUM = eINSTANCE.getComparatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum <em>Simple Unary Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSimpleUnaryOperatorEnum()
		 * 
		 */
		EEnum SIMPLE_UNARY_OPERATOR_ENUM = eINSTANCE.getSimpleUnaryOperatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum <em>Composed Unary Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedUnaryOperatorEnum()
		 * 
		 */
		EEnum COMPOSED_UNARY_OPERATOR_ENUM = eINSTANCE.getComposedUnaryOperatorEnum();

	}

} //CpPackage
