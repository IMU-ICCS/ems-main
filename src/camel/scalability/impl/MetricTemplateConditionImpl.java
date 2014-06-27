/**
 */
package camel.scalability.impl;

import camel.scalability.MetricTemplate;
import camel.scalability.MetricTemplateCondition;
import camel.scalability.ScalabilityPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metric Template Condition</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.scalability.impl.MetricTemplateConditionImpl#getMetricTemplate <em>Metric Template</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class MetricTemplateConditionImpl extends ConditionImpl implements MetricTemplateCondition {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected MetricTemplateConditionImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ScalabilityPackage.Literals.METRIC_TEMPLATE_CONDITION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricTemplate getMetricTemplate() {
		return (MetricTemplate)eGet(ScalabilityPackage.Literals.METRIC_TEMPLATE_CONDITION__METRIC_TEMPLATE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetricTemplate(MetricTemplate newMetricTemplate) {
		eSet(ScalabilityPackage.Literals.METRIC_TEMPLATE_CONDITION__METRIC_TEMPLATE, newMetricTemplate);
	}

} //MetricTemplateConditionImpl
