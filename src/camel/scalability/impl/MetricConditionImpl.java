/**
 */
package camel.scalability.impl;

import camel.scalability.Metric;
import camel.scalability.MetricCondition;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricConditionImpl#getMetric <em>Metric</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricConditionImpl extends ConditionImpl implements MetricCondition {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricConditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC_CONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Metric getMetric() {
		return (Metric)eGet(ScalabilityPackage.Literals.METRIC_CONDITION__METRIC, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetric(Metric newMetric) {
		eSet(ScalabilityPackage.Literals.METRIC_CONDITION__METRIC, newMetric);
	}

} //MetricConditionImpl
