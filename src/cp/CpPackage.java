/**
 */
package cp;

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
 * @see cp.CpFactory
 * @model kind="package"
 * @generated
 */
public interface CpPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "cp";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://paasage.inria.fr/metamodel/cp";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "cp";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	CpPackage eINSTANCE = cp.impl.CpPackageImpl.init();

	/**
	 * The meta object id for the '{@link cp.impl.CPElementImpl <em>CP Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.CPElementImpl
	 * @see cp.impl.CpPackageImpl#getCPElement()
	 * @generated
	 */
	int CP_ELEMENT = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CP_ELEMENT__ID = 0;

	/**
	 * The number of structural features of the '<em>CP Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CP_ELEMENT_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>CP Element</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CP_ELEMENT_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link cp.impl.ConstraintProblemImpl <em>Constraint Problem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.ConstraintProblemImpl
	 * @see cp.impl.CpPackageImpl#getConstraintProblem()
	 * @generated
	 */
	int CONSTRAINT_PROBLEM = 1;

	/**
	 * The feature id for the '<em><b>Goals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__GOALS = 0;

	/**
	 * The feature id for the '<em><b>Constants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__CONSTANTS = 1;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__VARIABLES = 2;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__CONSTRAINTS = 3;

	/**
	 * The feature id for the '<em><b>Aux Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__AUX_EXPRESSIONS = 4;

	/**
	 * The number of structural features of the '<em>Constraint Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM_FEATURE_COUNT = 5;

	/**
	 * The number of operations of the '<em>Constraint Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link cp.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.ExpressionImpl
	 * @see cp.impl.CpPackageImpl#getExpression()
	 * @generated
	 */
	int EXPRESSION = 2;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION__ID = CP_ELEMENT__ID;

	/**
	 * The number of structural features of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_FEATURE_COUNT = CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_OPERATION_COUNT = CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.NumericExpressionImpl <em>Numeric Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.NumericExpressionImpl
	 * @see cp.impl.CpPackageImpl#getNumericExpression()
	 * @generated
	 */
	int NUMERIC_EXPRESSION = 3;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_EXPRESSION__ID = EXPRESSION__ID;

	/**
	 * The number of structural features of the '<em>Numeric Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Numeric Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.VariableImpl
	 * @see cp.impl.CpPackageImpl#getVariable()
	 * @generated
	 */
	int VARIABLE = 4;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__VALUE = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__DOMAIN = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.DomainImpl <em>Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.DomainImpl
	 * @see cp.impl.CpPackageImpl#getDomain()
	 * @generated
	 */
	int DOMAIN = 5;

	/**
	 * The number of structural features of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_FEATURE_COUNT = 0;

	/**
	 * The number of operations of the '<em>Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOMAIN_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link cp.impl.NumericDomainImpl <em>Numeric Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.NumericDomainImpl
	 * @see cp.impl.CpPackageImpl#getNumericDomain()
	 * @generated
	 */
	int NUMERIC_DOMAIN = 6;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_DOMAIN__TYPE = DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_DOMAIN__VALUE = DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Numeric Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_DOMAIN_FEATURE_COUNT = DOMAIN_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Numeric Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_DOMAIN_OPERATION_COUNT = DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.RangeDomainImpl <em>Range Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.RangeDomainImpl
	 * @see cp.impl.CpPackageImpl#getRangeDomain()
	 * @generated
	 */
	int RANGE_DOMAIN = 7;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_DOMAIN__TYPE = NUMERIC_DOMAIN__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_DOMAIN__VALUE = NUMERIC_DOMAIN__VALUE;

	/**
	 * The feature id for the '<em><b>From</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_DOMAIN__FROM = NUMERIC_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>To</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_DOMAIN__TO = NUMERIC_DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Range Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_DOMAIN_FEATURE_COUNT = NUMERIC_DOMAIN_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Range Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANGE_DOMAIN_OPERATION_COUNT = NUMERIC_DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.NumericListDomainImpl <em>Numeric List Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.NumericListDomainImpl
	 * @see cp.impl.CpPackageImpl#getNumericListDomain()
	 * @generated
	 */
	int NUMERIC_LIST_DOMAIN = 8;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN__TYPE = NUMERIC_DOMAIN__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN__VALUE = NUMERIC_DOMAIN__VALUE;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN__VALUES = NUMERIC_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Numeric List Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN_FEATURE_COUNT = NUMERIC_DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Numeric List Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_LIST_DOMAIN_OPERATION_COUNT = NUMERIC_DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.ConstantImpl <em>Constant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.ConstantImpl
	 * @see cp.impl.CpPackageImpl#getConstant()
	 * @generated
	 */
	int CONSTANT = 9;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__TYPE = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT__VALUE = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Constant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Constant</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.ComposedExpressionImpl <em>Composed Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.ComposedExpressionImpl
	 * @see cp.impl.CpPackageImpl#getComposedExpression()
	 * @generated
	 */
	int COMPOSED_EXPRESSION = 10;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_EXPRESSION__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Expressions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_EXPRESSION__EXPRESSIONS = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_EXPRESSION__OPERATOR = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Composed Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_EXPRESSION_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Composed Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_EXPRESSION_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.BooleanExpressionImpl
	 * @see cp.impl.CpPackageImpl#getBooleanExpression()
	 * @generated
	 */
	int BOOLEAN_EXPRESSION = 13;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION__ID = EXPRESSION__ID;

	/**
	 * The number of structural features of the '<em>Boolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION_FEATURE_COUNT = EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_EXPRESSION_OPERATION_COUNT = EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.ComparisonExpressionImpl <em>Comparison Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.ComparisonExpressionImpl
	 * @see cp.impl.CpPackageImpl#getComparisonExpression()
	 * @generated
	 */
	int COMPARISON_EXPRESSION = 11;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_EXPRESSION__ID = BOOLEAN_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Exp1</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_EXPRESSION__EXP1 = BOOLEAN_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Exp2</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_EXPRESSION__EXP2 = BOOLEAN_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Comparator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_EXPRESSION__COMPARATOR = BOOLEAN_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Comparison Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_EXPRESSION_FEATURE_COUNT = BOOLEAN_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Comparison Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPARISON_EXPRESSION_OPERATION_COUNT = BOOLEAN_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.GoalImpl <em>Goal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.GoalImpl
	 * @see cp.impl.CpPackageImpl#getGoal()
	 * @generated
	 */
	int GOAL = 12;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL__ID = CP_ELEMENT__ID;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL__EXPRESSION = CP_ELEMENT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Goal Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL__GOAL_TYPE = CP_ELEMENT_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_FEATURE_COUNT = CP_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_OPERATION_COUNT = CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.ListDomainImpl <em>List Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.ListDomainImpl
	 * @see cp.impl.CpPackageImpl#getListDomain()
	 * @generated
	 */
	int LIST_DOMAIN = 14;

	/**
	 * The feature id for the '<em><b>Values</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_DOMAIN__VALUES = DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_DOMAIN__VALUE = DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>List Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_DOMAIN_FEATURE_COUNT = DOMAIN_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>List Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LIST_DOMAIN_OPERATION_COUNT = DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.MultiRangeDomainImpl <em>Multi Range Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.MultiRangeDomainImpl
	 * @see cp.impl.CpPackageImpl#getMultiRangeDomain()
	 * @generated
	 */
	int MULTI_RANGE_DOMAIN = 15;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN__TYPE = NUMERIC_DOMAIN__TYPE;

	/**
	 * The feature id for the '<em><b>Value</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN__VALUE = NUMERIC_DOMAIN__VALUE;

	/**
	 * The feature id for the '<em><b>Ranges</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN__RANGES = NUMERIC_DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Multi Range Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN_FEATURE_COUNT = NUMERIC_DOMAIN_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Multi Range Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int MULTI_RANGE_DOMAIN_OPERATION_COUNT = NUMERIC_DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.UnaryExpressionImpl
	 * @see cp.impl.CpPackageImpl#getUnaryExpression()
	 * @generated
	 */
	int UNARY_EXPRESSION = 16;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION__EXPRESSION = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNARY_EXPRESSION_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.SimpleUnaryExpressionImpl <em>Simple Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.SimpleUnaryExpressionImpl
	 * @see cp.impl.CpPackageImpl#getSimpleUnaryExpression()
	 * @generated
	 */
	int SIMPLE_UNARY_EXPRESSION = 17;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION__ID = UNARY_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION__EXPRESSION = UNARY_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION__OPERATOR = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Simple Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Simple Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SIMPLE_UNARY_EXPRESSION_OPERATION_COUNT = UNARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.ComposedUnaryExpressionImpl <em>Composed Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.ComposedUnaryExpressionImpl
	 * @see cp.impl.CpPackageImpl#getComposedUnaryExpression()
	 * @generated
	 */
	int COMPOSED_UNARY_EXPRESSION = 18;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION__ID = UNARY_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION__EXPRESSION = UNARY_EXPRESSION__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION__OPERATOR = UNARY_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION__VALUE = UNARY_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Composed Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION_FEATURE_COUNT = UNARY_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Composed Unary Expression</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COMPOSED_UNARY_EXPRESSION_OPERATION_COUNT = UNARY_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.impl.BooleanDomainImpl <em>Boolean Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.impl.BooleanDomainImpl
	 * @see cp.impl.CpPackageImpl#getBooleanDomain()
	 * @generated
	 */
	int BOOLEAN_DOMAIN = 19;

	/**
	 * The number of structural features of the '<em>Boolean Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_DOMAIN_FEATURE_COUNT = DOMAIN_FEATURE_COUNT + 0;

	/**
	 * The number of operations of the '<em>Boolean Domain</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_DOMAIN_OPERATION_COUNT = DOMAIN_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link cp.OperatorEnum <em>Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.OperatorEnum
	 * @see cp.impl.CpPackageImpl#getOperatorEnum()
	 * @generated
	 */
	int OPERATOR_ENUM = 20;

	/**
	 * The meta object id for the '{@link cp.GoalOperatorEnum <em>Goal Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.GoalOperatorEnum
	 * @see cp.impl.CpPackageImpl#getGoalOperatorEnum()
	 * @generated
	 */
	int GOAL_OPERATOR_ENUM = 21;

	/**
	 * The meta object id for the '{@link cp.ComparatorEnum <em>Comparator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.ComparatorEnum
	 * @see cp.impl.CpPackageImpl#getComparatorEnum()
	 * @generated
	 */
	int COMPARATOR_ENUM = 22;

	/**
	 * The meta object id for the '{@link cp.SimpleUnaryOperatorEnum <em>Simple Unary Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.SimpleUnaryOperatorEnum
	 * @see cp.impl.CpPackageImpl#getSimpleUnaryOperatorEnum()
	 * @generated
	 */
	int SIMPLE_UNARY_OPERATOR_ENUM = 23;

	/**
	 * The meta object id for the '{@link cp.ComposedUnaryOperatorEnum <em>Composed Unary Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see cp.ComposedUnaryOperatorEnum
	 * @see cp.impl.CpPackageImpl#getComposedUnaryOperatorEnum()
	 * @generated
	 */
	int COMPOSED_UNARY_OPERATOR_ENUM = 24;


	/**
	 * Returns the meta object for class '{@link cp.CPElement <em>CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CP Element</em>'.
	 * @see cp.CPElement
	 * @generated
	 */
	EClass getCPElement();

	/**
	 * Returns the meta object for the attribute '{@link cp.CPElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see cp.CPElement#getId()
	 * @see #getCPElement()
	 * @generated
	 */
	EAttribute getCPElement_Id();

	/**
	 * Returns the meta object for class '{@link cp.ConstraintProblem <em>Constraint Problem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Problem</em>'.
	 * @see cp.ConstraintProblem
	 * @generated
	 */
	EClass getConstraintProblem();

	/**
	 * Returns the meta object for the containment reference list '{@link cp.ConstraintProblem#getGoals <em>Goals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Goals</em>'.
	 * @see cp.ConstraintProblem#getGoals()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_Goals();

	/**
	 * Returns the meta object for the containment reference list '{@link cp.ConstraintProblem#getConstants <em>Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constants</em>'.
	 * @see cp.ConstraintProblem#getConstants()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_Constants();

	/**
	 * Returns the meta object for the containment reference list '{@link cp.ConstraintProblem#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see cp.ConstraintProblem#getVariables()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_Variables();

	/**
	 * Returns the meta object for the containment reference list '{@link cp.ConstraintProblem#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see cp.ConstraintProblem#getConstraints()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_Constraints();

	/**
	 * Returns the meta object for the containment reference list '{@link cp.ConstraintProblem#getAuxExpressions <em>Aux Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Aux Expressions</em>'.
	 * @see cp.ConstraintProblem#getAuxExpressions()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_AuxExpressions();

	/**
	 * Returns the meta object for class '{@link cp.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see cp.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '{@link cp.NumericExpression <em>Numeric Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Expression</em>'.
	 * @see cp.NumericExpression
	 * @generated
	 */
	EClass getNumericExpression();

	/**
	 * Returns the meta object for class '{@link cp.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see cp.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the containment reference '{@link cp.Variable#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see cp.Variable#getValue()
	 * @see #getVariable()
	 * @generated
	 */
	EReference getVariable_Value();

	/**
	 * Returns the meta object for the containment reference '{@link cp.Variable#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain</em>'.
	 * @see cp.Variable#getDomain()
	 * @see #getVariable()
	 * @generated
	 */
	EReference getVariable_Domain();

	/**
	 * Returns the meta object for class '{@link cp.Domain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain</em>'.
	 * @see cp.Domain
	 * @generated
	 */
	EClass getDomain();

	/**
	 * Returns the meta object for class '{@link cp.NumericDomain <em>Numeric Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Domain</em>'.
	 * @see cp.NumericDomain
	 * @generated
	 */
	EClass getNumericDomain();

	/**
	 * Returns the meta object for the attribute '{@link cp.NumericDomain#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see cp.NumericDomain#getType()
	 * @see #getNumericDomain()
	 * @generated
	 */
	EAttribute getNumericDomain_Type();

	/**
	 * Returns the meta object for the reference '{@link cp.NumericDomain#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see cp.NumericDomain#getValue()
	 * @see #getNumericDomain()
	 * @generated
	 */
	EReference getNumericDomain_Value();

	/**
	 * Returns the meta object for class '{@link cp.RangeDomain <em>Range Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range Domain</em>'.
	 * @see cp.RangeDomain
	 * @generated
	 */
	EClass getRangeDomain();

	/**
	 * Returns the meta object for the containment reference '{@link cp.RangeDomain#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>From</em>'.
	 * @see cp.RangeDomain#getFrom()
	 * @see #getRangeDomain()
	 * @generated
	 */
	EReference getRangeDomain_From();

	/**
	 * Returns the meta object for the containment reference '{@link cp.RangeDomain#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>To</em>'.
	 * @see cp.RangeDomain#getTo()
	 * @see #getRangeDomain()
	 * @generated
	 */
	EReference getRangeDomain_To();

	/**
	 * Returns the meta object for class '{@link cp.NumericListDomain <em>Numeric List Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric List Domain</em>'.
	 * @see cp.NumericListDomain
	 * @generated
	 */
	EClass getNumericListDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link cp.NumericListDomain#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see cp.NumericListDomain#getValues()
	 * @see #getNumericListDomain()
	 * @generated
	 */
	EReference getNumericListDomain_Values();

	/**
	 * Returns the meta object for class '{@link cp.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant</em>'.
	 * @see cp.Constant
	 * @generated
	 */
	EClass getConstant();

	/**
	 * Returns the meta object for the attribute '{@link cp.Constant#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see cp.Constant#getType()
	 * @see #getConstant()
	 * @generated
	 */
	EAttribute getConstant_Type();

	/**
	 * Returns the meta object for the containment reference '{@link cp.Constant#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see cp.Constant#getValue()
	 * @see #getConstant()
	 * @generated
	 */
	EReference getConstant_Value();

	/**
	 * Returns the meta object for class '{@link cp.ComposedExpression <em>Composed Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composed Expression</em>'.
	 * @see cp.ComposedExpression
	 * @generated
	 */
	EClass getComposedExpression();

	/**
	 * Returns the meta object for the reference list '{@link cp.ComposedExpression#getExpressions <em>Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Expressions</em>'.
	 * @see cp.ComposedExpression#getExpressions()
	 * @see #getComposedExpression()
	 * @generated
	 */
	EReference getComposedExpression_Expressions();

	/**
	 * Returns the meta object for the attribute '{@link cp.ComposedExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see cp.ComposedExpression#getOperator()
	 * @see #getComposedExpression()
	 * @generated
	 */
	EAttribute getComposedExpression_Operator();

	/**
	 * Returns the meta object for class '{@link cp.ComparisonExpression <em>Comparison Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comparison Expression</em>'.
	 * @see cp.ComparisonExpression
	 * @generated
	 */
	EClass getComparisonExpression();

	/**
	 * Returns the meta object for the reference '{@link cp.ComparisonExpression#getExp1 <em>Exp1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Exp1</em>'.
	 * @see cp.ComparisonExpression#getExp1()
	 * @see #getComparisonExpression()
	 * @generated
	 */
	EReference getComparisonExpression_Exp1();

	/**
	 * Returns the meta object for the reference '{@link cp.ComparisonExpression#getExp2 <em>Exp2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Exp2</em>'.
	 * @see cp.ComparisonExpression#getExp2()
	 * @see #getComparisonExpression()
	 * @generated
	 */
	EReference getComparisonExpression_Exp2();

	/**
	 * Returns the meta object for the attribute '{@link cp.ComparisonExpression#getComparator <em>Comparator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comparator</em>'.
	 * @see cp.ComparisonExpression#getComparator()
	 * @see #getComparisonExpression()
	 * @generated
	 */
	EAttribute getComparisonExpression_Comparator();

	/**
	 * Returns the meta object for class '{@link cp.Goal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Goal</em>'.
	 * @see cp.Goal
	 * @generated
	 */
	EClass getGoal();

	/**
	 * Returns the meta object for the containment reference '{@link cp.Goal#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see cp.Goal#getExpression()
	 * @see #getGoal()
	 * @generated
	 */
	EReference getGoal_Expression();

	/**
	 * Returns the meta object for the attribute '{@link cp.Goal#getGoalType <em>Goal Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Goal Type</em>'.
	 * @see cp.Goal#getGoalType()
	 * @see #getGoal()
	 * @generated
	 */
	EAttribute getGoal_GoalType();

	/**
	 * Returns the meta object for class '{@link cp.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Expression</em>'.
	 * @see cp.BooleanExpression
	 * @generated
	 */
	EClass getBooleanExpression();

	/**
	 * Returns the meta object for class '{@link cp.ListDomain <em>List Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Domain</em>'.
	 * @see cp.ListDomain
	 * @generated
	 */
	EClass getListDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link cp.ListDomain#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see cp.ListDomain#getValues()
	 * @see #getListDomain()
	 * @generated
	 */
	EReference getListDomain_Values();

	/**
	 * Returns the meta object for the reference '{@link cp.ListDomain#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see cp.ListDomain#getValue()
	 * @see #getListDomain()
	 * @generated
	 */
	EReference getListDomain_Value();

	/**
	 * Returns the meta object for class '{@link cp.MultiRangeDomain <em>Multi Range Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Range Domain</em>'.
	 * @see cp.MultiRangeDomain
	 * @generated
	 */
	EClass getMultiRangeDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link cp.MultiRangeDomain#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ranges</em>'.
	 * @see cp.MultiRangeDomain#getRanges()
	 * @see #getMultiRangeDomain()
	 * @generated
	 */
	EReference getMultiRangeDomain_Ranges();

	/**
	 * Returns the meta object for class '{@link cp.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Expression</em>'.
	 * @see cp.UnaryExpression
	 * @generated
	 */
	EClass getUnaryExpression();

	/**
	 * Returns the meta object for the reference '{@link cp.UnaryExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Expression</em>'.
	 * @see cp.UnaryExpression#getExpression()
	 * @see #getUnaryExpression()
	 * @generated
	 */
	EReference getUnaryExpression_Expression();

	/**
	 * Returns the meta object for class '{@link cp.SimpleUnaryExpression <em>Simple Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Unary Expression</em>'.
	 * @see cp.SimpleUnaryExpression
	 * @generated
	 */
	EClass getSimpleUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link cp.SimpleUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see cp.SimpleUnaryExpression#getOperator()
	 * @see #getSimpleUnaryExpression()
	 * @generated
	 */
	EAttribute getSimpleUnaryExpression_Operator();

	/**
	 * Returns the meta object for class '{@link cp.ComposedUnaryExpression <em>Composed Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composed Unary Expression</em>'.
	 * @see cp.ComposedUnaryExpression
	 * @generated
	 */
	EClass getComposedUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link cp.ComposedUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see cp.ComposedUnaryExpression#getOperator()
	 * @see #getComposedUnaryExpression()
	 * @generated
	 */
	EAttribute getComposedUnaryExpression_Operator();

	/**
	 * Returns the meta object for the attribute '{@link cp.ComposedUnaryExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see cp.ComposedUnaryExpression#getValue()
	 * @see #getComposedUnaryExpression()
	 * @generated
	 */
	EAttribute getComposedUnaryExpression_Value();

	/**
	 * Returns the meta object for class '{@link cp.BooleanDomain <em>Boolean Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Domain</em>'.
	 * @see cp.BooleanDomain
	 * @generated
	 */
	EClass getBooleanDomain();

	/**
	 * Returns the meta object for enum '{@link cp.OperatorEnum <em>Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operator Enum</em>'.
	 * @see cp.OperatorEnum
	 * @generated
	 */
	EEnum getOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link cp.GoalOperatorEnum <em>Goal Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Goal Operator Enum</em>'.
	 * @see cp.GoalOperatorEnum
	 * @generated
	 */
	EEnum getGoalOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link cp.ComparatorEnum <em>Comparator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Comparator Enum</em>'.
	 * @see cp.ComparatorEnum
	 * @generated
	 */
	EEnum getComparatorEnum();

	/**
	 * Returns the meta object for enum '{@link cp.SimpleUnaryOperatorEnum <em>Simple Unary Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Simple Unary Operator Enum</em>'.
	 * @see cp.SimpleUnaryOperatorEnum
	 * @generated
	 */
	EEnum getSimpleUnaryOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link cp.ComposedUnaryOperatorEnum <em>Composed Unary Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Composed Unary Operator Enum</em>'.
	 * @see cp.ComposedUnaryOperatorEnum
	 * @generated
	 */
	EEnum getComposedUnaryOperatorEnum();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
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
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link cp.impl.CPElementImpl <em>CP Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.CPElementImpl
		 * @see cp.impl.CpPackageImpl#getCPElement()
		 * @generated
		 */
		EClass CP_ELEMENT = eINSTANCE.getCPElement();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CP_ELEMENT__ID = eINSTANCE.getCPElement_Id();

		/**
		 * The meta object literal for the '{@link cp.impl.ConstraintProblemImpl <em>Constraint Problem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.ConstraintProblemImpl
		 * @see cp.impl.CpPackageImpl#getConstraintProblem()
		 * @generated
		 */
		EClass CONSTRAINT_PROBLEM = eINSTANCE.getConstraintProblem();

		/**
		 * The meta object literal for the '<em><b>Goals</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROBLEM__GOALS = eINSTANCE.getConstraintProblem_Goals();

		/**
		 * The meta object literal for the '<em><b>Constants</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROBLEM__CONSTANTS = eINSTANCE.getConstraintProblem_Constants();

		/**
		 * The meta object literal for the '<em><b>Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROBLEM__VARIABLES = eINSTANCE.getConstraintProblem_Variables();

		/**
		 * The meta object literal for the '<em><b>Constraints</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROBLEM__CONSTRAINTS = eINSTANCE.getConstraintProblem_Constraints();

		/**
		 * The meta object literal for the '<em><b>Aux Expressions</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROBLEM__AUX_EXPRESSIONS = eINSTANCE.getConstraintProblem_AuxExpressions();

		/**
		 * The meta object literal for the '{@link cp.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.ExpressionImpl
		 * @see cp.impl.CpPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '{@link cp.impl.NumericExpressionImpl <em>Numeric Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.NumericExpressionImpl
		 * @see cp.impl.CpPackageImpl#getNumericExpression()
		 * @generated
		 */
		EClass NUMERIC_EXPRESSION = eINSTANCE.getNumericExpression();

		/**
		 * The meta object literal for the '{@link cp.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.VariableImpl
		 * @see cp.impl.CpPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE__VALUE = eINSTANCE.getVariable_Value();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE__DOMAIN = eINSTANCE.getVariable_Domain();

		/**
		 * The meta object literal for the '{@link cp.impl.DomainImpl <em>Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.DomainImpl
		 * @see cp.impl.CpPackageImpl#getDomain()
		 * @generated
		 */
		EClass DOMAIN = eINSTANCE.getDomain();

		/**
		 * The meta object literal for the '{@link cp.impl.NumericDomainImpl <em>Numeric Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.NumericDomainImpl
		 * @see cp.impl.CpPackageImpl#getNumericDomain()
		 * @generated
		 */
		EClass NUMERIC_DOMAIN = eINSTANCE.getNumericDomain();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMERIC_DOMAIN__TYPE = eINSTANCE.getNumericDomain_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NUMERIC_DOMAIN__VALUE = eINSTANCE.getNumericDomain_Value();

		/**
		 * The meta object literal for the '{@link cp.impl.RangeDomainImpl <em>Range Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.RangeDomainImpl
		 * @see cp.impl.CpPackageImpl#getRangeDomain()
		 * @generated
		 */
		EClass RANGE_DOMAIN = eINSTANCE.getRangeDomain();

		/**
		 * The meta object literal for the '<em><b>From</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RANGE_DOMAIN__FROM = eINSTANCE.getRangeDomain_From();

		/**
		 * The meta object literal for the '<em><b>To</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RANGE_DOMAIN__TO = eINSTANCE.getRangeDomain_To();

		/**
		 * The meta object literal for the '{@link cp.impl.NumericListDomainImpl <em>Numeric List Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.NumericListDomainImpl
		 * @see cp.impl.CpPackageImpl#getNumericListDomain()
		 * @generated
		 */
		EClass NUMERIC_LIST_DOMAIN = eINSTANCE.getNumericListDomain();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NUMERIC_LIST_DOMAIN__VALUES = eINSTANCE.getNumericListDomain_Values();

		/**
		 * The meta object literal for the '{@link cp.impl.ConstantImpl <em>Constant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.ConstantImpl
		 * @see cp.impl.CpPackageImpl#getConstant()
		 * @generated
		 */
		EClass CONSTANT = eINSTANCE.getConstant();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTANT__TYPE = eINSTANCE.getConstant_Type();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTANT__VALUE = eINSTANCE.getConstant_Value();

		/**
		 * The meta object literal for the '{@link cp.impl.ComposedExpressionImpl <em>Composed Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.ComposedExpressionImpl
		 * @see cp.impl.CpPackageImpl#getComposedExpression()
		 * @generated
		 */
		EClass COMPOSED_EXPRESSION = eINSTANCE.getComposedExpression();

		/**
		 * The meta object literal for the '<em><b>Expressions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPOSED_EXPRESSION__EXPRESSIONS = eINSTANCE.getComposedExpression_Expressions();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOSED_EXPRESSION__OPERATOR = eINSTANCE.getComposedExpression_Operator();

		/**
		 * The meta object literal for the '{@link cp.impl.ComparisonExpressionImpl <em>Comparison Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.ComparisonExpressionImpl
		 * @see cp.impl.CpPackageImpl#getComparisonExpression()
		 * @generated
		 */
		EClass COMPARISON_EXPRESSION = eINSTANCE.getComparisonExpression();

		/**
		 * The meta object literal for the '<em><b>Exp1</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARISON_EXPRESSION__EXP1 = eINSTANCE.getComparisonExpression_Exp1();

		/**
		 * The meta object literal for the '<em><b>Exp2</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COMPARISON_EXPRESSION__EXP2 = eINSTANCE.getComparisonExpression_Exp2();

		/**
		 * The meta object literal for the '<em><b>Comparator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPARISON_EXPRESSION__COMPARATOR = eINSTANCE.getComparisonExpression_Comparator();

		/**
		 * The meta object literal for the '{@link cp.impl.GoalImpl <em>Goal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.GoalImpl
		 * @see cp.impl.CpPackageImpl#getGoal()
		 * @generated
		 */
		EClass GOAL = eINSTANCE.getGoal();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference GOAL__EXPRESSION = eINSTANCE.getGoal_Expression();

		/**
		 * The meta object literal for the '<em><b>Goal Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GOAL__GOAL_TYPE = eINSTANCE.getGoal_GoalType();

		/**
		 * The meta object literal for the '{@link cp.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.BooleanExpressionImpl
		 * @see cp.impl.CpPackageImpl#getBooleanExpression()
		 * @generated
		 */
		EClass BOOLEAN_EXPRESSION = eINSTANCE.getBooleanExpression();

		/**
		 * The meta object literal for the '{@link cp.impl.ListDomainImpl <em>List Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.ListDomainImpl
		 * @see cp.impl.CpPackageImpl#getListDomain()
		 * @generated
		 */
		EClass LIST_DOMAIN = eINSTANCE.getListDomain();

		/**
		 * The meta object literal for the '<em><b>Values</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIST_DOMAIN__VALUES = eINSTANCE.getListDomain_Values();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference LIST_DOMAIN__VALUE = eINSTANCE.getListDomain_Value();

		/**
		 * The meta object literal for the '{@link cp.impl.MultiRangeDomainImpl <em>Multi Range Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.MultiRangeDomainImpl
		 * @see cp.impl.CpPackageImpl#getMultiRangeDomain()
		 * @generated
		 */
		EClass MULTI_RANGE_DOMAIN = eINSTANCE.getMultiRangeDomain();

		/**
		 * The meta object literal for the '<em><b>Ranges</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference MULTI_RANGE_DOMAIN__RANGES = eINSTANCE.getMultiRangeDomain_Ranges();

		/**
		 * The meta object literal for the '{@link cp.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.UnaryExpressionImpl
		 * @see cp.impl.CpPackageImpl#getUnaryExpression()
		 * @generated
		 */
		EClass UNARY_EXPRESSION = eINSTANCE.getUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Expression</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference UNARY_EXPRESSION__EXPRESSION = eINSTANCE.getUnaryExpression_Expression();

		/**
		 * The meta object literal for the '{@link cp.impl.SimpleUnaryExpressionImpl <em>Simple Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.SimpleUnaryExpressionImpl
		 * @see cp.impl.CpPackageImpl#getSimpleUnaryExpression()
		 * @generated
		 */
		EClass SIMPLE_UNARY_EXPRESSION = eINSTANCE.getSimpleUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SIMPLE_UNARY_EXPRESSION__OPERATOR = eINSTANCE.getSimpleUnaryExpression_Operator();

		/**
		 * The meta object literal for the '{@link cp.impl.ComposedUnaryExpressionImpl <em>Composed Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.ComposedUnaryExpressionImpl
		 * @see cp.impl.CpPackageImpl#getComposedUnaryExpression()
		 * @generated
		 */
		EClass COMPOSED_UNARY_EXPRESSION = eINSTANCE.getComposedUnaryExpression();

		/**
		 * The meta object literal for the '<em><b>Operator</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOSED_UNARY_EXPRESSION__OPERATOR = eINSTANCE.getComposedUnaryExpression_Operator();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COMPOSED_UNARY_EXPRESSION__VALUE = eINSTANCE.getComposedUnaryExpression_Value();

		/**
		 * The meta object literal for the '{@link cp.impl.BooleanDomainImpl <em>Boolean Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.impl.BooleanDomainImpl
		 * @see cp.impl.CpPackageImpl#getBooleanDomain()
		 * @generated
		 */
		EClass BOOLEAN_DOMAIN = eINSTANCE.getBooleanDomain();

		/**
		 * The meta object literal for the '{@link cp.OperatorEnum <em>Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.OperatorEnum
		 * @see cp.impl.CpPackageImpl#getOperatorEnum()
		 * @generated
		 */
		EEnum OPERATOR_ENUM = eINSTANCE.getOperatorEnum();

		/**
		 * The meta object literal for the '{@link cp.GoalOperatorEnum <em>Goal Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.GoalOperatorEnum
		 * @see cp.impl.CpPackageImpl#getGoalOperatorEnum()
		 * @generated
		 */
		EEnum GOAL_OPERATOR_ENUM = eINSTANCE.getGoalOperatorEnum();

		/**
		 * The meta object literal for the '{@link cp.ComparatorEnum <em>Comparator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.ComparatorEnum
		 * @see cp.impl.CpPackageImpl#getComparatorEnum()
		 * @generated
		 */
		EEnum COMPARATOR_ENUM = eINSTANCE.getComparatorEnum();

		/**
		 * The meta object literal for the '{@link cp.SimpleUnaryOperatorEnum <em>Simple Unary Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.SimpleUnaryOperatorEnum
		 * @see cp.impl.CpPackageImpl#getSimpleUnaryOperatorEnum()
		 * @generated
		 */
		EEnum SIMPLE_UNARY_OPERATOR_ENUM = eINSTANCE.getSimpleUnaryOperatorEnum();

		/**
		 * The meta object literal for the '{@link cp.ComposedUnaryOperatorEnum <em>Composed Unary Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see cp.ComposedUnaryOperatorEnum
		 * @see cp.impl.CpPackageImpl#getComposedUnaryOperatorEnum()
		 * @generated
		 */
		EEnum COMPOSED_UNARY_OPERATOR_ENUM = eINSTANCE.getComposedUnaryOperatorEnum();

	}

} //CpPackage
