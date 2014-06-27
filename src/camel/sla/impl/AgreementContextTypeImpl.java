/**
 */
package camel.sla.impl;

import camel.deployment.DeploymentModel;

import camel.organisation.Entity;

import camel.sla.AgreementContextType;
import camel.sla.AgreementRoleType;
import camel.sla.SlaPackage;

import java.util.Date;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.internal.cdo.CDOObjectImpl;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Agreement Context Type</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.sla.impl.AgreementContextTypeImpl#getTemplateId <em>Template Id</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementContextTypeImpl#getTemplateName <em>Template Name</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementContextTypeImpl#getServiceProvider <em>Service Provider</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementContextTypeImpl#getServiceConsumer <em>Service Consumer</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementContextTypeImpl#getExpirationTime <em>Expiration Time</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementContextTypeImpl#getAgreementInitiator <em>Agreement Initiator</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementContextTypeImpl#getAgreementResponder <em>Agreement Responder</em>}</li>
 *   <li>{@link camel.sla.impl.AgreementContextTypeImpl#getDeploymentModel <em>Deployment Model</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class AgreementContextTypeImpl extends CDOObjectImpl implements AgreementContextType {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected AgreementContextTypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE;
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
	public String getTemplateId() {
		return (String)eGet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__TEMPLATE_ID, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplateId(String newTemplateId) {
		eSet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__TEMPLATE_ID, newTemplateId);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTemplateName() {
		return (String)eGet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__TEMPLATE_NAME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTemplateName(String newTemplateName) {
		eSet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__TEMPLATE_NAME, newTemplateName);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgreementRoleType getServiceProvider() {
		return (AgreementRoleType)eGet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__SERVICE_PROVIDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServiceProvider(AgreementRoleType newServiceProvider) {
		eSet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__SERVICE_PROVIDER, newServiceProvider);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public AgreementRoleType getServiceConsumer() {
		return (AgreementRoleType)eGet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__SERVICE_CONSUMER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setServiceConsumer(AgreementRoleType newServiceConsumer) {
		eSet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__SERVICE_CONSUMER, newServiceConsumer);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Date getExpirationTime() {
		return (Date)eGet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__EXPIRATION_TIME, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpirationTime(Date newExpirationTime) {
		eSet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__EXPIRATION_TIME, newExpirationTime);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity getAgreementInitiator() {
		return (Entity)eGet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__AGREEMENT_INITIATOR, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgreementInitiator(Entity newAgreementInitiator) {
		eSet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__AGREEMENT_INITIATOR, newAgreementInitiator);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Entity getAgreementResponder() {
		return (Entity)eGet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__AGREEMENT_RESPONDER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setAgreementResponder(Entity newAgreementResponder) {
		eSet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__AGREEMENT_RESPONDER, newAgreementResponder);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DeploymentModel getDeploymentModel() {
		return (DeploymentModel)eGet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__DEPLOYMENT_MODEL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDeploymentModel(DeploymentModel newDeploymentModel) {
		eSet(SlaPackage.Literals.AGREEMENT_CONTEXT_TYPE__DEPLOYMENT_MODEL, newDeploymentModel);
	}

} //AgreementContextTypeImpl
