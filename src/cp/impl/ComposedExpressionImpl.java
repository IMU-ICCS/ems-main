/**
 */
package cp.impl;

import cp.ComposedExpression;
import cp.CpPackage;
import cp.NumericExpression;
import cp.OperatorEnum;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composed Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.ComposedExpressionImpl#getExpressions <em>Expressions</em>}</li>
 *   <li>{@link cp.impl.ComposedExpressionImpl#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ComposedExpressionImpl extends NumericExpressionImpl implements ComposedExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.COMPOSED_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<NumericExpression> getExpressions() {
		return (EList<NumericExpression>)eGet(CpPackage.Literals.COMPOSED_EXPRESSION__EXPRESSIONS, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OperatorEnum getOperator() {
		return (OperatorEnum)eGet(CpPackage.Literals.COMPOSED_EXPRESSION__OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(OperatorEnum newOperator) {
		eSet(CpPackage.Literals.COMPOSED_EXPRESSION__OPERATOR, newOperator);
	}

} //ComposedExpressionImpl
