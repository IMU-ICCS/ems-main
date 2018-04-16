/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.MetricVariable;

import eu.paasage.upperware.metamodel.types.BasicTypeEnum;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric Variable</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableImpl#getType <em>Type</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MetricVariableImpl extends NumericExpressionImpl implements MetricVariable {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricVariableImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.METRIC_VARIABLE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicTypeEnum getType() {
		return (BasicTypeEnum)eGet(CpPackage.Literals.METRIC_VARIABLE__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(BasicTypeEnum newType) {
		eSet(CpPackage.Literals.METRIC_VARIABLE__TYPE, newType);
	}

} //MetricVariableImpl
