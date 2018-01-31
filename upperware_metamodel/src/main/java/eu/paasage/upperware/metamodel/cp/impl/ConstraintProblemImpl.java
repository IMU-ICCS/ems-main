/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.Constant;
import eu.paasage.upperware.metamodel.cp.ConstraintProblem;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Expression;
import eu.paasage.upperware.metamodel.cp.Goal;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.Variable;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Constraint Problem</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getId <em>Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getGoals <em>Goals</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getConstants <em>Constants</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getVariables <em>Variables</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getConstraints <em>Constraints</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getAuxExpressions <em>Aux Expressions</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getMetricVariables <em>Metric Variables</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getSolution <em>Solution</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getDeployedSolutionId <em>Deployed Solution Id</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ConstraintProblemImpl#getCandidateSolutionId <em>Candidate Solution Id</em>}</li>
 * </ul>
 *
 * @generated
 */
public class ConstraintProblemImpl extends CDOObjectImpl implements ConstraintProblem {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ConstraintProblemImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.CONSTRAINT_PROBLEM;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected int eStaticFeatureCount() {
		return 0;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getId() {
		return (String)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setId(String newId) {
		eSet(CpPackage.Literals.CONSTRAINT_PROBLEM__ID, newId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Goal> getGoals() {
		return (EList<Goal>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__GOALS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Constant> getConstants() {
		return (EList<Constant>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__CONSTANTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Variable> getVariables() {
		return (EList<Variable>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__VARIABLES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<ComparisonExpression> getConstraints() {
		return (EList<ComparisonExpression>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__CONSTRAINTS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Expression> getAuxExpressions() {
		return (EList<Expression>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__AUX_EXPRESSIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<MetricVariable> getMetricVariables() {
		return (EList<MetricVariable>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__METRIC_VARIABLES, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<Solution> getSolution() {
		return (EList<Solution>)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__SOLUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getDeployedSolutionId() {
		return (Integer)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__DEPLOYED_SOLUTION_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeployedSolutionId(int newDeployedSolutionId) {
		eSet(CpPackage.Literals.CONSTRAINT_PROBLEM__DEPLOYED_SOLUTION_ID, newDeployedSolutionId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getCandidateSolutionId() {
		return (Integer)eGet(CpPackage.Literals.CONSTRAINT_PROBLEM__CANDIDATE_SOLUTION_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCandidateSolutionId(int newCandidateSolutionId) {
		eSet(CpPackage.Literals.CONSTRAINT_PROBLEM__CANDIDATE_SOLUTION_ID, newCandidateSolutionId);
	}

} //ConstraintProblemImpl
