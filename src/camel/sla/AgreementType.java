/**
 */
package camel.sla;

import camel.scalability.MetricCondition;

import org.eclipse.emf.cdo.CDOObject;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Agreement Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.AgreementType#getName <em>Name</em>}</li>
 *   <li>{@link camel.sla.AgreementType#getAgreementId <em>Agreement Id</em>}</li>
 *   <li>{@link camel.sla.AgreementType#getContext <em>Context</em>}</li>
 *   <li>{@link camel.sla.AgreementType#getTerms <em>Terms</em>}</li>
 *   <li>{@link camel.sla.AgreementType#getSlos <em>Slos</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getAgreementType()
 * @model
 * @extends CDOObject
 * @generated
 */
public interface AgreementType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see camel.sla.SlaPackage#getAgreementType_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementType#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Agreement Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agreement Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agreement Id</em>' attribute.
	 * @see #setAgreementId(String)
	 * @see camel.sla.SlaPackage#getAgreementType_AgreementId()
	 * @model required="true"
	 * @generated
	 */
	String getAgreementId();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementType#getAgreementId <em>Agreement Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agreement Id</em>' attribute.
	 * @see #getAgreementId()
	 * @generated
	 */
	void setAgreementId(String value);

	/**
	 * Returns the value of the '<em><b>Context</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Context</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Context</em>' containment reference.
	 * @see #setContext(AgreementContextType)
	 * @see camel.sla.SlaPackage#getAgreementType_Context()
	 * @model containment="true" required="true"
	 * @generated
	 */
	AgreementContextType getContext();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementType#getContext <em>Context</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Context</em>' containment reference.
	 * @see #getContext()
	 * @generated
	 */
	void setContext(AgreementContextType value);

	/**
	 * Returns the value of the '<em><b>Terms</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Terms</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Terms</em>' containment reference.
	 * @see #setTerms(TermTreeType)
	 * @see camel.sla.SlaPackage#getAgreementType_Terms()
	 * @model containment="true" required="true"
	 * @generated
	 */
	TermTreeType getTerms();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementType#getTerms <em>Terms</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Terms</em>' containment reference.
	 * @see #getTerms()
	 * @generated
	 */
	void setTerms(TermTreeType value);

	/**
	 * Returns the value of the '<em><b>Slos</b></em>' containment reference list.
	 * The list contents are of type {@link camel.scalability.MetricCondition}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Slos</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Slos</em>' containment reference list.
	 * @see camel.sla.SlaPackage#getAgreementType_Slos()
	 * @model containment="true" ordered="false"
	 * @generated
	 */
	EList<MetricCondition> getSlos();

} // AgreementType
