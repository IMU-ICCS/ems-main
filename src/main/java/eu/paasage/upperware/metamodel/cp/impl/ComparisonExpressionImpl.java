/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.ComparatorEnum;
import eu.paasage.upperware.metamodel.cp.ComparisonExpression;
import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.Expression;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Comparison Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl#getExp1 <em>Exp1</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl#getExp2 <em>Exp2</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComparisonExpressionImpl#getComparator <em>Comparator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComparisonExpressionImpl extends BooleanExpressionImpl implements ComparisonExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComparisonExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.COMPARISON_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getExp1() {
		return (Expression)eGet(CpPackage.Literals.COMPARISON_EXPRESSION__EXP1, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExp1(Expression newExp1) {
		eSet(CpPackage.Literals.COMPARISON_EXPRESSION__EXP1, newExp1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Expression getExp2() {
		return (Expression)eGet(CpPackage.Literals.COMPARISON_EXPRESSION__EXP2, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExp2(Expression newExp2) {
		eSet(CpPackage.Literals.COMPARISON_EXPRESSION__EXP2, newExp2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComparatorEnum getComparator() {
		return (ComparatorEnum)eGet(CpPackage.Literals.COMPARISON_EXPRESSION__COMPARATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setComparator(ComparatorEnum newComparator) {
		eSet(CpPackage.Literals.COMPARISON_EXPRESSION__COMPARATOR, newComparator);
	}

} //ComparisonExpressionImpl
