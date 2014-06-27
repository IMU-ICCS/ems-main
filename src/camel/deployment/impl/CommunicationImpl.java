/**
 */
package camel.deployment.impl;

import camel.DataObject;

import camel.deployment.Communication;
import camel.deployment.ComputationalResource;
import camel.deployment.DeploymentPackage;
import camel.deployment.ProvidedCommunication;
import camel.deployment.RequiredCommunication;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Communication</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.CommunicationImpl#getRequiredCommunication <em>Required Communication</em>}</li>
 *   <li>{@link camel.deployment.impl.CommunicationImpl#getProvidedCommunication <em>Provided Communication</em>}</li>
 *   <li>{@link camel.deployment.impl.CommunicationImpl#getRequiredPortResource <em>Required Port Resource</em>}</li>
 *   <li>{@link camel.deployment.impl.CommunicationImpl#getProvidedPortResource <em>Provided Port Resource</em>}</li>
 *   <li>{@link camel.deployment.impl.CommunicationImpl#getOfDataObject <em>Of Data Object</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class CommunicationImpl extends CloudMLElementImpl implements Communication {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected CommunicationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.COMMUNICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RequiredCommunication getRequiredCommunication() {
		return (RequiredCommunication)eGet(DeploymentPackage.Literals.COMMUNICATION__REQUIRED_COMMUNICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredCommunication(RequiredCommunication newRequiredCommunication) {
		eSet(DeploymentPackage.Literals.COMMUNICATION__REQUIRED_COMMUNICATION, newRequiredCommunication);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProvidedCommunication getProvidedCommunication() {
		return (ProvidedCommunication)eGet(DeploymentPackage.Literals.COMMUNICATION__PROVIDED_COMMUNICATION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvidedCommunication(ProvidedCommunication newProvidedCommunication) {
		eSet(DeploymentPackage.Literals.COMMUNICATION__PROVIDED_COMMUNICATION, newProvidedCommunication);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComputationalResource getRequiredPortResource() {
		return (ComputationalResource)eGet(DeploymentPackage.Literals.COMMUNICATION__REQUIRED_PORT_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRequiredPortResource(ComputationalResource newRequiredPortResource) {
		eSet(DeploymentPackage.Literals.COMMUNICATION__REQUIRED_PORT_RESOURCE, newRequiredPortResource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ComputationalResource getProvidedPortResource() {
		return (ComputationalResource)eGet(DeploymentPackage.Literals.COMMUNICATION__PROVIDED_PORT_RESOURCE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProvidedPortResource(ComputationalResource newProvidedPortResource) {
		eSet(DeploymentPackage.Literals.COMMUNICATION__PROVIDED_PORT_RESOURCE, newProvidedPortResource);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DataObject getOfDataObject() {
		return (DataObject)eGet(DeploymentPackage.Literals.COMMUNICATION__OF_DATA_OBJECT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOfDataObject(DataObject newOfDataObject) {
		eSet(DeploymentPackage.Literals.COMMUNICATION__OF_DATA_OBJECT, newOfDataObject);
	}

} //CommunicationImpl
