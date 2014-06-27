/**
 */
package camel.deployment.impl;

import camel.deployment.DeploymentPackage;
import camel.deployment.RequiredCommunication;

import org.eclipse.emf.ecore.EClass;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Required Communication</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link camel.deployment.impl.RequiredCommunicationImpl#isIsMandatory <em>Is Mandatory</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class RequiredCommunicationImpl extends CommunicationPortImpl implements RequiredCommunication {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected RequiredCommunicationImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return DeploymentPackage.Literals.REQUIRED_COMMUNICATION;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean isIsMandatory() {
		return (Boolean)eGet(DeploymentPackage.Literals.REQUIRED_COMMUNICATION__IS_MANDATORY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setIsMandatory(boolean newIsMandatory) {
		eSet(DeploymentPackage.Literals.REQUIRED_COMMUNICATION__IS_MANDATORY, newIsMandatory);
	}

} //RequiredCommunicationImpl
