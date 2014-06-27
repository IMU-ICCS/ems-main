/**
 */
package camel.sla;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc -->
 * The <b>Factory</b> for the model.
 * It provides a create method for each non-abstract class of the model.
 * <!-- end-user-doc -->
 * @see camel.sla.SlaPackage
 * @generated
 */
public interface SlaFactory extends EFactory {
	/**
	 * The singleton instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	SlaFactory eINSTANCE = camel.sla.impl.SlaFactoryImpl.init();

	/**
	 * Returns a new object of class '<em>Agreement Context Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Agreement Context Type</em>'.
	 * @generated
	 */
	AgreementContextType createAgreementContextType();

	/**
	 * Returns a new object of class '<em>Agreement Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Agreement Type</em>'.
	 * @generated
	 */
	AgreementType createAgreementType();

	/**
	 * Returns a new object of class '<em>Assessment Interval Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Assessment Interval Type</em>'.
	 * @generated
	 */
	AssessmentIntervalType createAssessmentIntervalType();

	/**
	 * Returns a new object of class '<em>Business Value List Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Business Value List Type</em>'.
	 * @generated
	 */
	BusinessValueListType createBusinessValueListType();

	/**
	 * Returns a new object of class '<em>Compensation Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Compensation Type</em>'.
	 * @generated
	 */
	CompensationType createCompensationType();

	/**
	 * Returns a new object of class '<em>Guarantee Term Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Guarantee Term Type</em>'.
	 * @generated
	 */
	GuaranteeTermType createGuaranteeTermType();

	/**
	 * Returns a new object of class '<em>KPI Target Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>KPI Target Type</em>'.
	 * @generated
	 */
	KPITargetType createKPITargetType();

	/**
	 * Returns a new object of class '<em>Preference Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Preference Type</em>'.
	 * @generated
	 */
	PreferenceType createPreferenceType();

	/**
	 * Returns a new object of class '<em>Service Level Objective Template</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Level Objective Template</em>'.
	 * @generated
	 */
	ServiceLevelObjectiveTemplate createServiceLevelObjectiveTemplate();

	/**
	 * Returns a new object of class '<em>Service Level Objective Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Level Objective Type</em>'.
	 * @generated
	 */
	ServiceLevelObjectiveType createServiceLevelObjectiveType();

	/**
	 * Returns a new object of class '<em>Service Selector Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Service Selector Type</em>'.
	 * @generated
	 */
	ServiceSelectorType createServiceSelectorType();

	/**
	 * Returns a new object of class '<em>Term Compositor Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Term Compositor Type</em>'.
	 * @generated
	 */
	TermCompositorType createTermCompositorType();

	/**
	 * Returns a new object of class '<em>Term Tree Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return a new object of class '<em>Term Tree Type</em>'.
	 * @generated
	 */
	TermTreeType createTermTreeType();

	/**
	 * Returns the package supported by this factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the package supported by this factory.
	 * @generated
	 */
	SlaPackage getSlaPackage();

} //SlaFactory
