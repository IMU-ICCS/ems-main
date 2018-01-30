/**
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
	String eNS_URI = "http://www.paasage.eu/eu/paasage/upperware/metamodel/cp";

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
	CpPackage eINSTANCE = eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl.init();

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.CPElementImpl <em>CP Element</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.CPElementImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getCPElement()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl <em>Constraint Problem</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConstraintProblem()
	 * @generated
	 */
	int CONSTRAINT_PROBLEM = 1;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__ID = 0;

	/**
	 * The feature id for the '<em><b>Goals</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__GOALS = 1;

	/**
	 * The feature id for the '<em><b>Constants</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__CONSTANTS = 2;

	/**
	 * The feature id for the '<em><b>Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__VARIABLES = 3;

	/**
	 * The feature id for the '<em><b>Constraints</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__CONSTRAINTS = 4;

	/**
	 * The feature id for the '<em><b>Aux Expressions</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__AUX_EXPRESSIONS = 5;

	/**
	 * The feature id for the '<em><b>Metric Variables</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__METRIC_VARIABLES = 6;

	/**
	 * The feature id for the '<em><b>Solution</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM__SOLUTION = 7;

	/**
	 * The number of structural features of the '<em>Constraint Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM_FEATURE_COUNT = 8;

	/**
	 * The number of operations of the '<em>Constraint Problem</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTRAINT_PROBLEM_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ExpressionImpl <em>Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getExpression()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl <em>Numeric Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericExpression()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl <em>Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.VariableImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariable()
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
	 * The feature id for the '<em><b>Domain</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__DOMAIN = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Location Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__LOCATION_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Provider Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__PROVIDER_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Vm Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__VM_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Os Image Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__OS_IMAGE_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Hardware Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__HARDWARE_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Variable Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__VARIABLE_TYPE = NUMERIC_EXPRESSION_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Component Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE__COMPONENT_ID = NUMERIC_EXPRESSION_FEATURE_COUNT + 7;

	/**
	 * The number of structural features of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 8;

	/**
	 * The number of operations of the '<em>Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.DomainImpl <em>Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.DomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getDomain()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericDomainImpl <em>Numeric Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.NumericDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericDomain()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl <em>Range Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getRangeDomain()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericListDomainImpl <em>Numeric List Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.NumericListDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericListDomain()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConstantImpl <em>Constant</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ConstantImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConstant()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl <em>Composed Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedExpression()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getBooleanExpression()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl <em>Comparison Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComparisonExpression()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.GoalImpl <em>Goal</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.GoalImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getGoal()
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
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL__PRIORITY = CP_ELEMENT_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_FEATURE_COUNT = CP_ELEMENT_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Goal</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GOAL_OPERATION_COUNT = CP_ELEMENT_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl <em>List Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getListDomain()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.MultiRangeDomainImpl <em>Multi Range Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.MultiRangeDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMultiRangeDomain()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.UnaryExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getUnaryExpression()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.SimpleUnaryExpressionImpl <em>Simple Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.SimpleUnaryExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSimpleUnaryExpression()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl <em>Composed Unary Expression</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedUnaryExpression()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.BooleanDomainImpl <em>Boolean Domain</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.BooleanDomainImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getBooleanDomain()
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
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableImpl <em>Metric Variable</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.MetricVariableImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMetricVariable()
	 * @generated
	 */
	int METRIC_VARIABLE = 20;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VARIABLE__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VARIABLE__TYPE = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Metric Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VARIABLE_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Metric Variable</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VARIABLE_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.SolutionImpl <em>Solution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.SolutionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSolution()
	 * @generated
	 */
	int SOLUTION = 21;

	/**
	 * The feature id for the '<em><b>Timestamp</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLUTION__TIMESTAMP = 0;

	/**
	 * The feature id for the '<em><b>Variable Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLUTION__VARIABLE_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Metric Variable Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLUTION__METRIC_VARIABLE_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Utility Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLUTION__UTILITY_VALUE = 3;

	/**
	 * The number of structural features of the '<em>Solution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLUTION_FEATURE_COUNT = 4;

	/**
	 * The number of operations of the '<em>Solution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOLUTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.VariableValueImpl <em>Variable Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.VariableValueImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariableValue()
	 * @generated
	 */
	int VARIABLE_VALUE = 22;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_VALUE__VARIABLE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_VALUE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Variable Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_VALUE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Variable Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VARIABLE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl <em>Metric Variable Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMetricVariableValue()
	 * @generated
	 */
	int METRIC_VARIABLE_VALUE = 23;

	/**
	 * The feature id for the '<em><b>Variable</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VARIABLE_VALUE__VARIABLE = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VARIABLE_VALUE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Metric Variable Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VARIABLE_VALUE_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Metric Variable Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METRIC_VARIABLE_VALUE_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ParameterImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 24;

	/**
	 * The feature id for the '<em><b>Solution</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__SOLUTION = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__NAME = 1;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.FunctionImpl <em>Function</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.FunctionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getFunction()
	 * @generated
	 */
	int FUNCTION = 26;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__ID = COMPOSED_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Expressions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__EXPRESSIONS = COMPOSED_EXPRESSION__EXPRESSIONS;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__OPERATOR = COMPOSED_EXPRESSION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION__VALUE = COMPOSED_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_FEATURE_COUNT = COMPOSED_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The number of operations of the '<em>Function</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FUNCTION_OPERATION_COUNT = COMPOSED_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.NormalisedUtilityDimensionImpl <em>Normalised Utility Dimension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.NormalisedUtilityDimensionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNormalisedUtilityDimension()
	 * @generated
	 */
	int NORMALISED_UTILITY_DIMENSION = 25;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMALISED_UTILITY_DIMENSION__ID = FUNCTION__ID;

	/**
	 * The feature id for the '<em><b>Expressions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMALISED_UTILITY_DIMENSION__EXPRESSIONS = FUNCTION__EXPRESSIONS;

	/**
	 * The feature id for the '<em><b>Operator</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMALISED_UTILITY_DIMENSION__OPERATOR = FUNCTION__OPERATOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMALISED_UTILITY_DIMENSION__VALUE = FUNCTION__VALUE;

	/**
	 * The feature id for the '<em><b>Solutions</b></em>' reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMALISED_UTILITY_DIMENSION__SOLUTIONS = FUNCTION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Goal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMALISED_UTILITY_DIMENSION__GOAL = FUNCTION_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Normalised Utility Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMALISED_UTILITY_DIMENSION_FEATURE_COUNT = FUNCTION_FEATURE_COUNT + 2;

	/**
	 * The number of operations of the '<em>Normalised Utility Dimension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMALISED_UTILITY_DIMENSION_OPERATION_COUNT = FUNCTION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConfigurationUpperwareImpl <em>Configuration Upperware</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.ConfigurationUpperwareImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConfigurationUpperware()
	 * @generated
	 */
	int CONFIGURATION_UPPERWARE = 27;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_UPPERWARE__ID = NUMERIC_EXPRESSION__ID;

	/**
	 * The feature id for the '<em><b>Solution</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_UPPERWARE__SOLUTION = NUMERIC_EXPRESSION_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_UPPERWARE__VALUE = NUMERIC_EXPRESSION_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Goal</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_UPPERWARE__GOAL = NUMERIC_EXPRESSION_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Configuration Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_UPPERWARE_FEATURE_COUNT = NUMERIC_EXPRESSION_FEATURE_COUNT + 3;

	/**
	 * The number of operations of the '<em>Configuration Upperware</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONFIGURATION_UPPERWARE_OPERATION_COUNT = NUMERIC_EXPRESSION_OPERATION_COUNT + 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.impl.DeployedSolutionImpl <em>Deployed Solution</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.impl.DeployedSolutionImpl
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getDeployedSolution()
	 * @generated
	 */
	int DEPLOYED_SOLUTION = 28;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYED_SOLUTION__ID = 0;

	/**
	 * The number of structural features of the '<em>Deployed Solution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYED_SOLUTION_FEATURE_COUNT = 1;

	/**
	 * The number of operations of the '<em>Deployed Solution</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DEPLOYED_SOLUTION_OPERATION_COUNT = 0;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.OperatorEnum <em>Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.OperatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getOperatorEnum()
	 * @generated
	 */
	int OPERATOR_ENUM = 29;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum <em>Goal Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getGoalOperatorEnum()
	 * @generated
	 */
	int GOAL_OPERATOR_ENUM = 30;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.ComparatorEnum <em>Comparator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.ComparatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComparatorEnum()
	 * @generated
	 */
	int COMPARATOR_ENUM = 31;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum <em>Simple Unary Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSimpleUnaryOperatorEnum()
	 * @generated
	 */
	int SIMPLE_UNARY_OPERATOR_ENUM = 32;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum <em>Composed Unary Operator Enum</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedUnaryOperatorEnum()
	 * @generated
	 */
	int COMPOSED_UNARY_OPERATOR_ENUM = 33;

	/**
	 * The meta object id for the '{@link eu.paasage.upperware.metamodel.cp.VariableType <em>Variable Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see eu.paasage.upperware.metamodel.cp.VariableType
	 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariableType()
	 * @generated
	 */
	int VARIABLE_TYPE = 34;


	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.CPElement <em>CP Element</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>CP Element</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.CPElement
	 * @generated
	 */
	EClass getCPElement();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.CPElement#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.CPElement#getId()
	 * @see #getCPElement()
	 * @generated
	 */
	EAttribute getCPElement_Id();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem <em>Constraint Problem</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constraint Problem</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem
	 * @generated
	 */
	EClass getConstraintProblem();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getId()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EAttribute getConstraintProblem_Id();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getGoals <em>Goals</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Goals</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getGoals()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_Goals();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstants <em>Constants</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constants</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstants()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_Constants();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getVariables <em>Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variables</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getVariables()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_Variables();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstraints <em>Constraints</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Constraints</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getConstraints()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_Constraints();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getAuxExpressions <em>Aux Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Aux Expressions</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getAuxExpressions()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_AuxExpressions();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getMetricVariables <em>Metric Variables</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metric Variables</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getMetricVariables()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_MetricVariables();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ConstraintProblem#getSolution <em>Solution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Solution</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConstraintProblem#getSolution()
	 * @see #getConstraintProblem()
	 * @generated
	 */
	EReference getConstraintProblem_Solution();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Expression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Expression
	 * @generated
	 */
	EClass getExpression();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.NumericExpression <em>Numeric Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericExpression
	 * @generated
	 */
	EClass getNumericExpression();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Variable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable
	 * @generated
	 */
	EClass getVariable();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.Variable#getDomain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getDomain()
	 * @see #getVariable()
	 * @generated
	 */
	EReference getVariable_Domain();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getLocationId <em>Location Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Location Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getLocationId()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_LocationId();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getProviderId <em>Provider Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Provider Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getProviderId()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_ProviderId();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getVmId <em>Vm Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vm Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getVmId()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_VmId();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getOsImageId <em>Os Image Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Os Image Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getOsImageId()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_OsImageId();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getHardwareId <em>Hardware Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Hardware Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getHardwareId()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_HardwareId();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getVariableType <em>Variable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Variable Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getVariableType()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_VariableType();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Variable#getComponentId <em>Component Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Component Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Variable#getComponentId()
	 * @see #getVariable()
	 * @generated
	 */
	EAttribute getVariable_ComponentId();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Domain <em>Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Domain
	 * @generated
	 */
	EClass getDomain();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.NumericDomain <em>Numeric Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericDomain
	 * @generated
	 */
	EClass getNumericDomain();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.NumericDomain#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericDomain#getType()
	 * @see #getNumericDomain()
	 * @generated
	 */
	EAttribute getNumericDomain_Type();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.NumericDomain#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericDomain#getValue()
	 * @see #getNumericDomain()
	 * @generated
	 */
	EReference getNumericDomain_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.RangeDomain <em>Range Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Range Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.RangeDomain
	 * @generated
	 */
	EClass getRangeDomain();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getFrom <em>From</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>From</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.RangeDomain#getFrom()
	 * @see #getRangeDomain()
	 * @generated
	 */
	EReference getRangeDomain_From();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.RangeDomain#getTo <em>To</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>To</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.RangeDomain#getTo()
	 * @see #getRangeDomain()
	 * @generated
	 */
	EReference getRangeDomain_To();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.NumericListDomain <em>Numeric List Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric List Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericListDomain
	 * @generated
	 */
	EClass getNumericListDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.NumericListDomain#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NumericListDomain#getValues()
	 * @see #getNumericListDomain()
	 * @generated
	 */
	EReference getNumericListDomain_Values();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Constant <em>Constant</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Constant
	 * @generated
	 */
	EClass getConstant();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Constant#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Constant#getType()
	 * @see #getConstant()
	 * @generated
	 */
	EAttribute getConstant_Type();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.Constant#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Constant#getValue()
	 * @see #getConstant()
	 * @generated
	 */
	EReference getConstant_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ComposedExpression <em>Composed Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composed Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedExpression
	 * @generated
	 */
	EClass getComposedExpression();

	/**
	 * Returns the meta object for the reference list '{@link eu.paasage.upperware.metamodel.cp.ComposedExpression#getExpressions <em>Expressions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Expressions</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedExpression#getExpressions()
	 * @see #getComposedExpression()
	 * @generated
	 */
	EReference getComposedExpression_Expressions();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.ComposedExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedExpression#getOperator()
	 * @see #getComposedExpression()
	 * @generated
	 */
	EAttribute getComposedExpression_Operator();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression <em>Comparison Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Comparison Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparisonExpression
	 * @generated
	 */
	EClass getComparisonExpression();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp1 <em>Exp1</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Exp1</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp1()
	 * @see #getComparisonExpression()
	 * @generated
	 */
	EReference getComparisonExpression_Exp1();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp2 <em>Exp2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Exp2</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparisonExpression#getExp2()
	 * @see #getComparisonExpression()
	 * @generated
	 */
	EReference getComparisonExpression_Exp2();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.ComparisonExpression#getComparator <em>Comparator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Comparator</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparisonExpression#getComparator()
	 * @see #getComparisonExpression()
	 * @generated
	 */
	EAttribute getComparisonExpression_Comparator();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Goal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Goal</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Goal
	 * @generated
	 */
	EClass getGoal();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.Goal#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Goal#getExpression()
	 * @see #getGoal()
	 * @generated
	 */
	EReference getGoal_Expression();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Goal#getGoalType <em>Goal Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Goal Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Goal#getGoalType()
	 * @see #getGoal()
	 * @generated
	 */
	EAttribute getGoal_GoalType();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Goal#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Goal#getPriority()
	 * @see #getGoal()
	 * @generated
	 */
	EAttribute getGoal_Priority();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.BooleanExpression <em>Boolean Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.BooleanExpression
	 * @generated
	 */
	EClass getBooleanExpression();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ListDomain <em>List Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>List Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ListDomain
	 * @generated
	 */
	EClass getListDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.ListDomain#getValues <em>Values</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Values</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ListDomain#getValues()
	 * @see #getListDomain()
	 * @generated
	 */
	EReference getListDomain_Values();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.ListDomain#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ListDomain#getValue()
	 * @see #getListDomain()
	 * @generated
	 */
	EReference getListDomain_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.MultiRangeDomain <em>Multi Range Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Multi Range Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MultiRangeDomain
	 * @generated
	 */
	EClass getMultiRangeDomain();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.MultiRangeDomain#getRanges <em>Ranges</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Ranges</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MultiRangeDomain#getRanges()
	 * @see #getMultiRangeDomain()
	 * @generated
	 */
	EReference getMultiRangeDomain_Ranges();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.UnaryExpression <em>Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Unary Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.UnaryExpression
	 * @generated
	 */
	EClass getUnaryExpression();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.UnaryExpression#getExpression <em>Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.UnaryExpression#getExpression()
	 * @see #getUnaryExpression()
	 * @generated
	 */
	EReference getUnaryExpression_Expression();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression <em>Simple Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Simple Unary Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression
	 * @generated
	 */
	EClass getSimpleUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryExpression#getOperator()
	 * @see #getSimpleUnaryExpression()
	 * @generated
	 */
	EAttribute getSimpleUnaryExpression_Operator();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression <em>Composed Unary Expression</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Composed Unary Expression</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression
	 * @generated
	 */
	EClass getComposedUnaryExpression();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getOperator <em>Operator</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Operator</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getOperator()
	 * @see #getComposedUnaryExpression()
	 * @generated
	 */
	EAttribute getComposedUnaryExpression_Operator();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression#getValue()
	 * @see #getComposedUnaryExpression()
	 * @generated
	 */
	EAttribute getComposedUnaryExpression_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.BooleanDomain <em>Boolean Domain</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Domain</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.BooleanDomain
	 * @generated
	 */
	EClass getBooleanDomain();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.MetricVariable <em>Metric Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Variable</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariable
	 * @generated
	 */
	EClass getMetricVariable();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.MetricVariable#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariable#getType()
	 * @see #getMetricVariable()
	 * @generated
	 */
	EAttribute getMetricVariable_Type();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Solution <em>Solution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Solution</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Solution
	 * @generated
	 */
	EClass getSolution();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Solution#getTimestamp <em>Timestamp</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Timestamp</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Solution#getTimestamp()
	 * @see #getSolution()
	 * @generated
	 */
	EAttribute getSolution_Timestamp();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.Solution#getVariableValue <em>Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Variable Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Solution#getVariableValue()
	 * @see #getSolution()
	 * @generated
	 */
	EReference getSolution_VariableValue();

	/**
	 * Returns the meta object for the containment reference list '{@link eu.paasage.upperware.metamodel.cp.Solution#getMetricVariableValue <em>Metric Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metric Variable Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Solution#getMetricVariableValue()
	 * @see #getSolution()
	 * @generated
	 */
	EReference getSolution_MetricVariableValue();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.Solution#getUtilityValue <em>Utility Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Utility Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Solution#getUtilityValue()
	 * @see #getSolution()
	 * @generated
	 */
	EReference getSolution_UtilityValue();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.VariableValue <em>Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Variable Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.VariableValue
	 * @generated
	 */
	EClass getVariableValue();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.VariableValue#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.VariableValue#getVariable()
	 * @see #getVariableValue()
	 * @generated
	 */
	EReference getVariableValue_Variable();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.VariableValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.VariableValue#getValue()
	 * @see #getVariableValue()
	 * @generated
	 */
	EReference getVariableValue_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.MetricVariableValue <em>Metric Variable Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metric Variable Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariableValue
	 * @generated
	 */
	EClass getMetricVariableValue();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.MetricVariableValue#getVariable <em>Variable</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Variable</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariableValue#getVariable()
	 * @see #getMetricVariableValue()
	 * @generated
	 */
	EReference getMetricVariableValue_Variable();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.MetricVariableValue#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.MetricVariableValue#getValue()
	 * @see #getMetricVariableValue()
	 * @generated
	 */
	EReference getMetricVariableValue_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.Parameter#getSolution <em>Solution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Solution</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Parameter#getSolution()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_Solution();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.Parameter#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Parameter#getName()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Name();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension <em>Normalised Utility Dimension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Normalised Utility Dimension</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension
	 * @generated
	 */
	EClass getNormalisedUtilityDimension();

	/**
	 * Returns the meta object for the reference list '{@link eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension#getSolutions <em>Solutions</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference list '<em>Solutions</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension#getSolutions()
	 * @see #getNormalisedUtilityDimension()
	 * @generated
	 */
	EReference getNormalisedUtilityDimension_Solutions();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension#getGoal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Goal</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.NormalisedUtilityDimension#getGoal()
	 * @see #getNormalisedUtilityDimension()
	 * @generated
	 */
	EReference getNormalisedUtilityDimension_Goal();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.Function <em>Function</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Function</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Function
	 * @generated
	 */
	EClass getFunction();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.Function#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.Function#getValue()
	 * @see #getFunction()
	 * @generated
	 */
	EReference getFunction_Value();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware <em>Configuration Upperware</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Configuration Upperware</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConfigurationUpperware
	 * @generated
	 */
	EClass getConfigurationUpperware();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getSolution <em>Solution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Solution</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getSolution()
	 * @see #getConfigurationUpperware()
	 * @generated
	 */
	EReference getConfigurationUpperware_Solution();

	/**
	 * Returns the meta object for the containment reference '{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Value</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getValue()
	 * @see #getConfigurationUpperware()
	 * @generated
	 */
	EReference getConfigurationUpperware_Value();

	/**
	 * Returns the meta object for the reference '{@link eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getGoal <em>Goal</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the reference '<em>Goal</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ConfigurationUpperware#getGoal()
	 * @see #getConfigurationUpperware()
	 * @generated
	 */
	EReference getConfigurationUpperware_Goal();

	/**
	 * Returns the meta object for class '{@link eu.paasage.upperware.metamodel.cp.DeployedSolution <em>Deployed Solution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Deployed Solution</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.DeployedSolution
	 * @generated
	 */
	EClass getDeployedSolution();

	/**
	 * Returns the meta object for the attribute '{@link eu.paasage.upperware.metamodel.cp.DeployedSolution#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.DeployedSolution#getId()
	 * @see #getDeployedSolution()
	 * @generated
	 */
	EAttribute getDeployedSolution_Id();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.OperatorEnum <em>Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Operator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.OperatorEnum
	 * @generated
	 */
	EEnum getOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum <em>Goal Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Goal Operator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
	 * @generated
	 */
	EEnum getGoalOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.ComparatorEnum <em>Comparator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Comparator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComparatorEnum
	 * @generated
	 */
	EEnum getComparatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum <em>Simple Unary Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Simple Unary Operator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum
	 * @generated
	 */
	EEnum getSimpleUnaryOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum <em>Composed Unary Operator Enum</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Composed Unary Operator Enum</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
	 * @generated
	 */
	EEnum getComposedUnaryOperatorEnum();

	/**
	 * Returns the meta object for enum '{@link eu.paasage.upperware.metamodel.cp.VariableType <em>Variable Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Variable Type</em>'.
	 * @see eu.paasage.upperware.metamodel.cp.VariableType
	 * @generated
	 */
	EEnum getVariableType();

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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.CPElementImpl <em>CP Element</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.CPElementImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getCPElement()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl <em>Constraint Problem</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConstraintProblem()
		 * @generated
		 */
		EClass CONSTRAINT_PROBLEM = eINSTANCE.getConstraintProblem();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CONSTRAINT_PROBLEM__ID = eINSTANCE.getConstraintProblem_Id();

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
		 * The meta object literal for the '<em><b>Metric Variables</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROBLEM__METRIC_VARIABLES = eINSTANCE.getConstraintProblem_MetricVariables();

		/**
		 * The meta object literal for the '<em><b>Solution</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONSTRAINT_PROBLEM__SOLUTION = eINSTANCE.getConstraintProblem_Solution();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ExpressionImpl <em>Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getExpression()
		 * @generated
		 */
		EClass EXPRESSION = eINSTANCE.getExpression();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl <em>Numeric Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.NumericExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericExpression()
		 * @generated
		 */
		EClass NUMERIC_EXPRESSION = eINSTANCE.getNumericExpression();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.VariableImpl <em>Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.VariableImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariable()
		 * @generated
		 */
		EClass VARIABLE = eINSTANCE.getVariable();

		/**
		 * The meta object literal for the '<em><b>Domain</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE__DOMAIN = eINSTANCE.getVariable_Domain();

		/**
		 * The meta object literal for the '<em><b>Location Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__LOCATION_ID = eINSTANCE.getVariable_LocationId();

		/**
		 * The meta object literal for the '<em><b>Provider Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__PROVIDER_ID = eINSTANCE.getVariable_ProviderId();

		/**
		 * The meta object literal for the '<em><b>Vm Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__VM_ID = eINSTANCE.getVariable_VmId();

		/**
		 * The meta object literal for the '<em><b>Os Image Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__OS_IMAGE_ID = eINSTANCE.getVariable_OsImageId();

		/**
		 * The meta object literal for the '<em><b>Hardware Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__HARDWARE_ID = eINSTANCE.getVariable_HardwareId();

		/**
		 * The meta object literal for the '<em><b>Variable Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__VARIABLE_TYPE = eINSTANCE.getVariable_VariableType();

		/**
		 * The meta object literal for the '<em><b>Component Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VARIABLE__COMPONENT_ID = eINSTANCE.getVariable_ComponentId();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.DomainImpl <em>Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.DomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getDomain()
		 * @generated
		 */
		EClass DOMAIN = eINSTANCE.getDomain();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericDomainImpl <em>Numeric Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.NumericDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericDomain()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl <em>Range Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.RangeDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getRangeDomain()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.NumericListDomainImpl <em>Numeric List Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.NumericListDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNumericListDomain()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConstantImpl <em>Constant</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ConstantImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConstant()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl <em>Composed Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ComposedExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedExpression()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl <em>Comparison Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComparisonExpression()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.GoalImpl <em>Goal</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.GoalImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getGoal()
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
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GOAL__PRIORITY = eINSTANCE.getGoal_Priority();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl <em>Boolean Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.BooleanExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getBooleanExpression()
		 * @generated
		 */
		EClass BOOLEAN_EXPRESSION = eINSTANCE.getBooleanExpression();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl <em>List Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ListDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getListDomain()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.MultiRangeDomainImpl <em>Multi Range Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.MultiRangeDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMultiRangeDomain()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.UnaryExpressionImpl <em>Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.UnaryExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getUnaryExpression()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.SimpleUnaryExpressionImpl <em>Simple Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.SimpleUnaryExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSimpleUnaryExpression()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl <em>Composed Unary Expression</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedUnaryExpression()
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
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.BooleanDomainImpl <em>Boolean Domain</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.BooleanDomainImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getBooleanDomain()
		 * @generated
		 */
		EClass BOOLEAN_DOMAIN = eINSTANCE.getBooleanDomain();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableImpl <em>Metric Variable</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.MetricVariableImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMetricVariable()
		 * @generated
		 */
		EClass METRIC_VARIABLE = eINSTANCE.getMetricVariable();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METRIC_VARIABLE__TYPE = eINSTANCE.getMetricVariable_Type();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.SolutionImpl <em>Solution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.SolutionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSolution()
		 * @generated
		 */
		EClass SOLUTION = eINSTANCE.getSolution();

		/**
		 * The meta object literal for the '<em><b>Timestamp</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOLUTION__TIMESTAMP = eINSTANCE.getSolution_Timestamp();

		/**
		 * The meta object literal for the '<em><b>Variable Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOLUTION__VARIABLE_VALUE = eINSTANCE.getSolution_VariableValue();

		/**
		 * The meta object literal for the '<em><b>Metric Variable Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOLUTION__METRIC_VARIABLE_VALUE = eINSTANCE.getSolution_MetricVariableValue();

		/**
		 * The meta object literal for the '<em><b>Utility Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SOLUTION__UTILITY_VALUE = eINSTANCE.getSolution_UtilityValue();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.VariableValueImpl <em>Variable Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.VariableValueImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariableValue()
		 * @generated
		 */
		EClass VARIABLE_VALUE = eINSTANCE.getVariableValue();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_VALUE__VARIABLE = eINSTANCE.getVariableValue_Variable();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference VARIABLE_VALUE__VALUE = eINSTANCE.getVariableValue_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl <em>Metric Variable Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getMetricVariableValue()
		 * @generated
		 */
		EClass METRIC_VARIABLE_VALUE = eINSTANCE.getMetricVariableValue();

		/**
		 * The meta object literal for the '<em><b>Variable</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_VARIABLE_VALUE__VARIABLE = eINSTANCE.getMetricVariableValue_Variable();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METRIC_VARIABLE_VALUE__VALUE = eINSTANCE.getMetricVariableValue_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ParameterImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Solution</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__SOLUTION = eINSTANCE.getParameter_Solution();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__NAME = eINSTANCE.getParameter_Name();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.NormalisedUtilityDimensionImpl <em>Normalised Utility Dimension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.NormalisedUtilityDimensionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getNormalisedUtilityDimension()
		 * @generated
		 */
		EClass NORMALISED_UTILITY_DIMENSION = eINSTANCE.getNormalisedUtilityDimension();

		/**
		 * The meta object literal for the '<em><b>Solutions</b></em>' reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NORMALISED_UTILITY_DIMENSION__SOLUTIONS = eINSTANCE.getNormalisedUtilityDimension_Solutions();

		/**
		 * The meta object literal for the '<em><b>Goal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference NORMALISED_UTILITY_DIMENSION__GOAL = eINSTANCE.getNormalisedUtilityDimension_Goal();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.FunctionImpl <em>Function</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.FunctionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getFunction()
		 * @generated
		 */
		EClass FUNCTION = eINSTANCE.getFunction();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference FUNCTION__VALUE = eINSTANCE.getFunction_Value();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.ConfigurationUpperwareImpl <em>Configuration Upperware</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.ConfigurationUpperwareImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getConfigurationUpperware()
		 * @generated
		 */
		EClass CONFIGURATION_UPPERWARE = eINSTANCE.getConfigurationUpperware();

		/**
		 * The meta object literal for the '<em><b>Solution</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION_UPPERWARE__SOLUTION = eINSTANCE.getConfigurationUpperware_Solution();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION_UPPERWARE__VALUE = eINSTANCE.getConfigurationUpperware_Value();

		/**
		 * The meta object literal for the '<em><b>Goal</b></em>' reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONFIGURATION_UPPERWARE__GOAL = eINSTANCE.getConfigurationUpperware_Goal();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.impl.DeployedSolutionImpl <em>Deployed Solution</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.impl.DeployedSolutionImpl
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getDeployedSolution()
		 * @generated
		 */
		EClass DEPLOYED_SOLUTION = eINSTANCE.getDeployedSolution();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DEPLOYED_SOLUTION__ID = eINSTANCE.getDeployedSolution_Id();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.OperatorEnum <em>Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.OperatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getOperatorEnum()
		 * @generated
		 */
		EEnum OPERATOR_ENUM = eINSTANCE.getOperatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.GoalOperatorEnum <em>Goal Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.GoalOperatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getGoalOperatorEnum()
		 * @generated
		 */
		EEnum GOAL_OPERATOR_ENUM = eINSTANCE.getGoalOperatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.ComparatorEnum <em>Comparator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.ComparatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComparatorEnum()
		 * @generated
		 */
		EEnum COMPARATOR_ENUM = eINSTANCE.getComparatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum <em>Simple Unary Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.SimpleUnaryOperatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getSimpleUnaryOperatorEnum()
		 * @generated
		 */
		EEnum SIMPLE_UNARY_OPERATOR_ENUM = eINSTANCE.getSimpleUnaryOperatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum <em>Composed Unary Operator Enum</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getComposedUnaryOperatorEnum()
		 * @generated
		 */
		EEnum COMPOSED_UNARY_OPERATOR_ENUM = eINSTANCE.getComposedUnaryOperatorEnum();

		/**
		 * The meta object literal for the '{@link eu.paasage.upperware.metamodel.cp.VariableType <em>Variable Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see eu.paasage.upperware.metamodel.cp.VariableType
		 * @see eu.paasage.upperware.metamodel.cp.impl.CpPackageImpl#getVariableType()
		 * @generated
		 */
		EEnum VARIABLE_TYPE = eINSTANCE.getVariableType();

	}

} //CpPackage
