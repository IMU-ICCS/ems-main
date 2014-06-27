/**
 */
package cp.impl;

import cp.CpPackage;
import cp.SimpleUnaryExpression;
import cp.SimpleUnaryOperatorEnum;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Simple Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link cp.impl.SimpleUnaryExpressionImpl#getOperator <em>Operator</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public abstract class SimpleUnaryExpressionImpl extends UnaryExpressionImpl implements SimpleUnaryExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SimpleUnaryExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.SIMPLE_UNARY_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SimpleUnaryOperatorEnum getOperator() {
		return (SimpleUnaryOperatorEnum)eGet(CpPackage.Literals.SIMPLE_UNARY_EXPRESSION__OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(SimpleUnaryOperatorEnum newOperator) {
		eSet(CpPackage.Literals.SIMPLE_UNARY_EXPRESSION__OPERATOR, newOperator);
	}

} //SimpleUnaryExpressionImpl
