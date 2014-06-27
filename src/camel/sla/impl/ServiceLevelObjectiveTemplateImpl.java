/**
 */
package camel.sla.impl;

import camel.impl.RequirementImpl;

import camel.scalability.MetricTemplateCondition;

import camel.sla.ServiceLevelObjectiveTemplate;
import camel.sla.SlaPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Level Objective Template</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.ServiceLevelObjectiveTemplateImpl#getCondition <em>Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceLevelObjectiveTemplateImpl extends RequirementImpl implements ServiceLevelObjectiveTemplate {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServiceLevelObjectiveTemplateImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.SERVICE_LEVEL_OBJECTIVE_TEMPLATE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricTemplateCondition getCondition() {
		return (MetricTemplateCondition)eGet(SlaPackage.Literals.SERVICE_LEVEL_OBJECTIVE_TEMPLATE__CONDITION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCondition(MetricTemplateCondition newCondition) {
		eSet(SlaPackage.Literals.SERVICE_LEVEL_OBJECTIVE_TEMPLATE__CONDITION, newCondition);
	}

} //ServiceLevelObjectiveTemplateImpl
