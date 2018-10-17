/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpMetric;
import eu.paasage.upperware.metamodel.cp.CpPackage;

import eu.paasage.upperware.metamodel.types.BasicTypeEnum;
import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.CpMetricImpl#getType <em>Type</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.CpMetricImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class CpMetricImpl extends NumericExpressionImpl implements CpMetric {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CpMetricImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.CP_METRIC;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BasicTypeEnum getType() {
		return (BasicTypeEnum)eGet(CpPackage.Literals.CP_METRIC__TYPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setType(BasicTypeEnum newType) {
		eSet(CpPackage.Literals.CP_METRIC__TYPE, newType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValueUpperware getValue() {
		return (NumericValueUpperware)eGet(CpPackage.Literals.CP_METRIC__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(NumericValueUpperware newValue) {
		eSet(CpPackage.Literals.CP_METRIC__VALUE, newValue);
	}

} //CpMetricImpl
