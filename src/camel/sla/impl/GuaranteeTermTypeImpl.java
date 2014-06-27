/**
 */
package camel.sla.impl;

import camel.scalability.MetricCondition;

import camel.sla.BusinessValueListType;
import camel.sla.GuaranteeTermType;
import camel.sla.ServiceLevelObjectiveType;
import camel.sla.ServiceRoleType;
import camel.sla.ServiceSelectorType;
import camel.sla.SlaPackage;

import org.eclipse.emf.common.util.EList;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Guarantee Term Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.GuaranteeTermTypeImpl#getServiceScope <em>Service Scope</em>}</li>
 *   <li>{@link camel.sla.impl.GuaranteeTermTypeImpl#getServiceLevelObjective <em>Service Level Objective</em>}</li>
 *   <li>{@link camel.sla.impl.GuaranteeTermTypeImpl#getBusinessValueList <em>Business Value List</em>}</li>
 *   <li>{@link camel.sla.impl.GuaranteeTermTypeImpl#getObligated <em>Obligated</em>}</li>
 *   <li>{@link camel.sla.impl.GuaranteeTermTypeImpl#getQualifyingCondition <em>Qualifying Condition</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class GuaranteeTermTypeImpl extends CDOObjectImpl implements GuaranteeTermType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected GuaranteeTermTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.GUARANTEE_TERM_TYPE;
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
	@SuppressWarnings("unchecked")
	public EList<ServiceSelectorType> getServiceScope() {
		return (EList<ServiceSelectorType>)eGet(SlaPackage.Literals.GUARANTEE_TERM_TYPE__SERVICE_SCOPE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceLevelObjectiveType getServiceLevelObjective() {
		return (ServiceLevelObjectiveType)eGet(SlaPackage.Literals.GUARANTEE_TERM_TYPE__SERVICE_LEVEL_OBJECTIVE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServiceLevelObjective(ServiceLevelObjectiveType newServiceLevelObjective) {
		eSet(SlaPackage.Literals.GUARANTEE_TERM_TYPE__SERVICE_LEVEL_OBJECTIVE, newServiceLevelObjective);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BusinessValueListType getBusinessValueList() {
		return (BusinessValueListType)eGet(SlaPackage.Literals.GUARANTEE_TERM_TYPE__BUSINESS_VALUE_LIST, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBusinessValueList(BusinessValueListType newBusinessValueList) {
		eSet(SlaPackage.Literals.GUARANTEE_TERM_TYPE__BUSINESS_VALUE_LIST, newBusinessValueList);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ServiceRoleType getObligated() {
		return (ServiceRoleType)eGet(SlaPackage.Literals.GUARANTEE_TERM_TYPE__OBLIGATED, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setObligated(ServiceRoleType newObligated) {
		eSet(SlaPackage.Literals.GUARANTEE_TERM_TYPE__OBLIGATED, newObligated);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetricCondition getQualifyingCondition() {
		return (MetricCondition)eGet(SlaPackage.Literals.GUARANTEE_TERM_TYPE__QUALIFYING_CONDITION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setQualifyingCondition(MetricCondition newQualifyingCondition) {
		eSet(SlaPackage.Literals.GUARANTEE_TERM_TYPE__QUALIFYING_CONDITION, newQualifyingCondition);
	}

} //GuaranteeTermTypeImpl
