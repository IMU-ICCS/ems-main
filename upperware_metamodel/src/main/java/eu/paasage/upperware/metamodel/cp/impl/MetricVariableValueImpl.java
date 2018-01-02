/**
 */
package eu.paasage.upperware.metamodel.cp.impl;

import eu.paasage.upperware.metamodel.cp.CpPackage;
import eu.paasage.upperware.metamodel.cp.MetricVariable;
import eu.paasage.upperware.metamodel.cp.MetricVariableValue;

import eu.paasage.upperware.metamodel.types.NumericValueUpperware;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric Variable Value</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl#getVariable <em>Variable</em>}</li>
 *   <li>{@link eu.paasage.upperware.metamodel.cp.impl.MetricVariableValueImpl#getValue <em>Value</em>}</li>
 * </ul>
 *
 * @generated
 */
public class MetricVariableValueImpl extends CDOObjectImpl implements MetricVariableValue {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricVariableValueImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return CpPackage.Literals.METRIC_VARIABLE_VALUE;
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
	public MetricVariable getVariable() {
		return (MetricVariable)eGet(CpPackage.Literals.METRIC_VARIABLE_VALUE__VARIABLE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVariable(MetricVariable newVariable) {
		eSet(CpPackage.Literals.METRIC_VARIABLE_VALUE__VARIABLE, newVariable);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericValueUpperware getValue() {
		return (NumericValueUpperware)eGet(CpPackage.Literals.METRIC_VARIABLE_VALUE__VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setValue(NumericValueUpperware newValue) {
		eSet(CpPackage.Literals.METRIC_VARIABLE_VALUE__VALUE, newValue);
	}

} //MetricVariableValueImpl
