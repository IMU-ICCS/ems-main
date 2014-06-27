/**
 */
package camel.sla.impl;

import camel.impl.RequirementImpl;

import camel.scalability.MetricCondition;

import camel.sla.KPITargetType;
import camel.sla.ServiceLevelObjectiveType;
import camel.sla.SlaPackage;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Service Level Objective Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.ServiceLevelObjectiveTypeImpl#getKPITarget <em>KPI Target</em>}</li>
 *   <li>{@link camel.sla.impl.ServiceLevelObjectiveTypeImpl#getCustomServiceLevel <em>Custom Service Level</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class ServiceLevelObjectiveTypeImpl extends RequirementImpl implements ServiceLevelObjectiveType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected ServiceLevelObjectiveTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.SERVICE_LEVEL_OBJECTIVE_TYPE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public KPITargetType getKPITarget() {
		return (KPITargetType)eGet(SlaPackage.Literals.SERVICE_LEVEL_OBJECTIVE_TYPE__KPI_TARGET, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKPITarget(KPITargetType newKPITarget) {
		eSet(SlaPackage.Literals.SERVICE_LEVEL_OBJECTIVE_TYPE__KPI_TARGET, newKPITarget);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricCondition getCustomServiceLevel() {
		return (MetricCondition)eGet(SlaPackage.Literals.SERVICE_LEVEL_OBJECTIVE_TYPE__CUSTOM_SERVICE_LEVEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCustomServiceLevel(MetricCondition newCustomServiceLevel) {
		eSet(SlaPackage.Literals.SERVICE_LEVEL_OBJECTIVE_TYPE__CUSTOM_SERVICE_LEVEL, newCustomServiceLevel);
	}

} //ServiceLevelObjectiveTypeImpl
