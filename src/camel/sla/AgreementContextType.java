/**
 */
package camel.sla;

import camel.deployment.DeploymentModel;

import camel.organisation.Entity;

import java.util.Date;

import org.eclipse.emf.cdo.CDOObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Agreement Context Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link camel.sla.AgreementContextType#getTemplateId <em>Template Id</em>}</li>
 *   <li>{@link camel.sla.AgreementContextType#getTemplateName <em>Template Name</em>}</li>
 *   <li>{@link camel.sla.AgreementContextType#getServiceProvider <em>Service Provider</em>}</li>
 *   <li>{@link camel.sla.AgreementContextType#getServiceConsumer <em>Service Consumer</em>}</li>
 *   <li>{@link camel.sla.AgreementContextType#getExpirationTime <em>Expiration Time</em>}</li>
 *   <li>{@link camel.sla.AgreementContextType#getAgreementInitiator <em>Agreement Initiator</em>}</li>
 *   <li>{@link camel.sla.AgreementContextType#getAgreementResponder <em>Agreement Responder</em>}</li>
 *   <li>{@link camel.sla.AgreementContextType#getDeploymentModel <em>Deployment Model</em>}</li>
 * </ul>
 * </p>
 *
 * @see camel.sla.SlaPackage#getAgreementContextType()
 * @model annotation="http://www.eclipse.org/emf/2002/Ecore constraints='WSAG_Context_Different_Role WSAG_Context_Diff_Entities'"
 *        annotation="http://www.eclipse.org/emf/2002/Ecore/OCL/Pivot WSAG_Context_Different_Role='\n\t\t\t\t\t\tself.serviceProvider <> self.serviceConsumer' WSAG_Context_Diff_Entities='\n\t\t\t\t\t\t(self.agreementInitiator <> null and self.agreementResponder <> null) implies self.agreementInitiator <> self.agreementResponder'"
 * @extends CDOObject
 * @generated
 */
public interface AgreementContextType extends CDOObject {
	/**
	 * Returns the value of the '<em><b>Template Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Template Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template Id</em>' attribute.
	 * @see #setTemplateId(String)
	 * @see camel.sla.SlaPackage#getAgreementContextType_TemplateId()
	 * @model
	 * @generated
	 */
	String getTemplateId();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementContextType#getTemplateId <em>Template Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template Id</em>' attribute.
	 * @see #getTemplateId()
	 * @generated
	 */
	void setTemplateId(String value);

	/**
	 * Returns the value of the '<em><b>Template Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Template Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Template Name</em>' attribute.
	 * @see #setTemplateName(String)
	 * @see camel.sla.SlaPackage#getAgreementContextType_TemplateName()
	 * @model
	 * @generated
	 */
	String getTemplateName();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementContextType#getTemplateName <em>Template Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Template Name</em>' attribute.
	 * @see #getTemplateName()
	 * @generated
	 */
	void setTemplateName(String value);

	/**
	 * Returns the value of the '<em><b>Service Provider</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.sla.AgreementRoleType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Provider</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Provider</em>' attribute.
	 * @see camel.sla.AgreementRoleType
	 * @see #setServiceProvider(AgreementRoleType)
	 * @see camel.sla.SlaPackage#getAgreementContextType_ServiceProvider()
	 * @model
	 * @generated
	 */
	AgreementRoleType getServiceProvider();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementContextType#getServiceProvider <em>Service Provider</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Provider</em>' attribute.
	 * @see camel.sla.AgreementRoleType
	 * @see #getServiceProvider()
	 * @generated
	 */
	void setServiceProvider(AgreementRoleType value);

	/**
	 * Returns the value of the '<em><b>Service Consumer</b></em>' attribute.
	 * The literals are from the enumeration {@link camel.sla.AgreementRoleType}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Service Consumer</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Service Consumer</em>' attribute.
	 * @see camel.sla.AgreementRoleType
	 * @see #setServiceConsumer(AgreementRoleType)
	 * @see camel.sla.SlaPackage#getAgreementContextType_ServiceConsumer()
	 * @model
	 * @generated
	 */
	AgreementRoleType getServiceConsumer();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementContextType#getServiceConsumer <em>Service Consumer</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Service Consumer</em>' attribute.
	 * @see camel.sla.AgreementRoleType
	 * @see #getServiceConsumer()
	 * @generated
	 */
	void setServiceConsumer(AgreementRoleType value);

	/**
	 * Returns the value of the '<em><b>Expiration Time</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expiration Time</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expiration Time</em>' attribute.
	 * @see #setExpirationTime(Date)
	 * @see camel.sla.SlaPackage#getAgreementContextType_ExpirationTime()
	 * @model
	 * @generated
	 */
	Date getExpirationTime();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementContextType#getExpirationTime <em>Expiration Time</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expiration Time</em>' attribute.
	 * @see #getExpirationTime()
	 * @generated
	 */
	void setExpirationTime(Date value);

	/**
	 * Returns the value of the '<em><b>Agreement Initiator</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agreement Initiator</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agreement Initiator</em>' reference.
	 * @see #setAgreementInitiator(Entity)
	 * @see camel.sla.SlaPackage#getAgreementContextType_AgreementInitiator()
	 * @model
	 * @generated
	 */
	Entity getAgreementInitiator();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementContextType#getAgreementInitiator <em>Agreement Initiator</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agreement Initiator</em>' reference.
	 * @see #getAgreementInitiator()
	 * @generated
	 */
	void setAgreementInitiator(Entity value);

	/**
	 * Returns the value of the '<em><b>Agreement Responder</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Agreement Responder</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Agreement Responder</em>' reference.
	 * @see #setAgreementResponder(Entity)
	 * @see camel.sla.SlaPackage#getAgreementContextType_AgreementResponder()
	 * @model
	 * @generated
	 */
	Entity getAgreementResponder();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementContextType#getAgreementResponder <em>Agreement Responder</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Agreement Responder</em>' reference.
	 * @see #getAgreementResponder()
	 * @generated
	 */
	void setAgreementResponder(Entity value);

	/**
	 * Returns the value of the '<em><b>Deployment Model</b></em>' reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Deployment Model</em>' reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Deployment Model</em>' reference.
	 * @see #setDeploymentModel(DeploymentModel)
	 * @see camel.sla.SlaPackage#getAgreementContextType_DeploymentModel()
	 * @model
	 * @generated
	 */
	DeploymentModel getDeploymentModel();

	/**
	 * Sets the value of the '{@link camel.sla.AgreementContextType#getDeploymentModel <em>Deployment Model</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Deployment Model</em>' reference.
	 * @see #getDeploymentModel()
	 * @generated
	 */
	void setDeploymentModel(DeploymentModel value);

} // AgreementContextType
