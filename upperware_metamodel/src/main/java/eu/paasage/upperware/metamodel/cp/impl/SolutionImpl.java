/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;
import eu.paasage.upperware.metamodel.cp.Solution;
import eu.paasage.upperware.metamodel.cp.VariableValue;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Solution</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.SolutionImpl#getTimestamp <em>Timestamp</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.SolutionImpl#getVariableValue <em>Variable Value</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.SolutionImpl#getMetricVariableValue <em>Metric Variable Value</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.SolutionImpl#getUtilityValue <em>Utility Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SolutionImpl extends CDOObjectImpl implements Solution {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SolutionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.SOLUTION;
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
	public long getTimestamp() {
		return (Long)eGet(CpPackage.Literals.SOLUTION__TIMESTAMP, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTimestamp(long newTimestamp) {
		eSet(CpPackage.Literals.SOLUTION__TIMESTAMP, newTimestamp);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<VariableValue> getVariableValue() {
		return (EList<VariableValue>)eGet(CpPackage.Literals.SOLUTION__VARIABLE_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public EList<MetricVariableValue> getMetricVariableValue() {
		return (EList<MetricVariableValue>)eGet(CpPackage.Literals.SOLUTION__METRIC_VARIABLE_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValueUpperware getUtilityValue() {
		return (NumericValueUpperware)eGet(CpPackage.Literals.SOLUTION__UTILITY_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUtilityValue(NumericValueUpperware newUtilityValue) {
		eSet(CpPackage.Literals.SOLUTION__UTILITY_VALUE, newUtilityValue);
	}

} //SolutionImpl
