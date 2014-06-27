/**
 */
package camel.sla.impl;

import camel.scalability.MetricCondition;

import camel.sla.KPITargetType;
import camel.sla.SlaPackage;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>KPI Target Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.KPITargetTypeImpl#getKPIName <em>KPI Name</em>}</li>
 *   <li>{@link camel.sla.impl.KPITargetTypeImpl#getCustomServiceLevel <em>Custom Service Level</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class KPITargetTypeImpl extends CDOObjectImpl implements KPITargetType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected KPITargetTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.KPI_TARGET_TYPE;
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
	public String getKPIName() {
		return (String)eGet(SlaPackage.Literals.KPI_TARGET_TYPE__KPI_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setKPIName(String newKPIName) {
		eSet(SlaPackage.Literals.KPI_TARGET_TYPE__KPI_NAME, newKPIName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricCondition getCustomServiceLevel() {
		return (MetricCondition)eGet(SlaPackage.Literals.KPI_TARGET_TYPE__CUSTOM_SERVICE_LEVEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setCustomServiceLevel(MetricCondition newCustomServiceLevel) {
		eSet(SlaPackage.Literals.KPI_TARGET_TYPE__CUSTOM_SERVICE_LEVEL, newCustomServiceLevel);
	}

} //KPITargetTypeImpl
