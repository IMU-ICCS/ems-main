/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.ComposedUnaryExpression;
import eu.paasage.upperware.metamodel.cp.ComposedUnaryOperatorEnum;
import eu.paasage.upperware.metamodel.cp.CpPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Composed Unary Expression</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl#getOperator <em>Operator</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.ComposedUnaryExpressionImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class ComposedUnaryExpressionImpl extends UnaryExpressionImpl implements ComposedUnaryExpression {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ComposedUnaryExpressionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.COMPOSED_UNARY_EXPRESSION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComposedUnaryOperatorEnum getOperator() {
		return (ComposedUnaryOperatorEnum)eGet(CpPackage.Literals.COMPOSED_UNARY_EXPRESSION__OPERATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOperator(ComposedUnaryOperatorEnum newOperator) {
		eSet(CpPackage.Literals.COMPOSED_UNARY_EXPRESSION__OPERATOR, newOperator);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public int getValue() {
		return (Integer)eGet(CpPackage.Literals.COMPOSED_UNARY_EXPRESSION__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(int newValue) {
		eSet(CpPackage.Literals.COMPOSED_UNARY_EXPRESSION__VALUE, newValue);
	}

} //ComposedUnaryExpressionImpl
